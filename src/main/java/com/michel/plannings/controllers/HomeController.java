package com.michel.plannings.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.models.forms.FormCompte;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller

public class HomeController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/")
	public String accueil(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PAGE_ACCUEIL;
	}

	@GetMapping("/aide")
	public String aide(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.AIDE;
	}

	@GetMapping("/presentation")
	public String presentation(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.PRESENTATION;
	}

	@GetMapping("/connexion")
	public String connexion(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.CONNEXION;
	}

	@PostMapping("/connexion") // Traitement formulaire de connexion
	public String demandeConnexion(Login login, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.identifierUtilisateur(login, session);

		if (utilisateur != null) {
			
			if(!utilisateur.getAutorise()) {
				
				return Constants.ALERTE_BLOCAGE;
			}
			
			utilisateur = userConnexion.obtenirUtilisateur(session, model);
			
			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/connexion?error=true";
		}
	}

	@GetMapping("/compte") // Accès formulaire de création de compte
	public String compte(Model model) {

		FormCompte formCompte = new FormCompte();
		model.addAttribute("formCompte", formCompte);
		return Constants.CREATION_COMPTE;
	}

	@PostMapping("/compte") // Création du compte
	public String creationCompte(Model model, FormCompte formCompte) {

		UtilisateurAux utilisateurAux = new UtilisateurAux();
		utilisateurAux.setPrenom(formCompte.getPrenom());
		utilisateurAux.setNom(formCompte.getNom());
		utilisateurAux.setToken(formCompte.getPassword());
		utilisateurAux.setUsername(formCompte.getUsername());
		utilisateurAux.setType(formCompte.getType());
		utilisateurAux.setRole("USER");

		microServicePlannnings.creerCompte(utilisateurAux);

		return Constants.CONNEXION;
	}

	@GetMapping("/espace")
	public String espace(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {

			return Constants.CONNEXION;

		} else {

			return Constants.ESPACE_PERSONEL;
		}

	}

	@GetMapping("/compte/modifier")
	public String modifierCompte(@RequestParam(name = "error", required = false) boolean error, Model model,
			HttpSession session) {

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		FormCompte formCompte = new FormCompte();
		formCompte.setNom(utilisateur.getNom());
		formCompte.setPrenom(utilisateur.getPrenom());
		formCompte.setUsername(utilisateur.getUsername());
		model.addAttribute("formCompte", formCompte);
		model.addAttribute("authentification", true);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("error", error);

		return Constants.MODIFIER_COMPTE;
	}

	@PostMapping("/compte/modifier")
	public String enregitrementModification(Model model, HttpSession session, FormCompte formCompte) {

		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");

		UtilisateurAux utilisateurAux = new UtilisateurAux();
		utilisateurAux.setId(utilisateur.getId());
		utilisateurAux.setPrenom(formCompte.getPrenom());
		utilisateurAux.setNom(formCompte.getNom());

		if (!formCompte.getPassword().equals("")) {

			utilisateurAux.setToken(formCompte.getPassword());
			utilisateurAux.setPassword(formCompte.getPassword());
			utilisateurAux.setUsername(formCompte.getUsername());
			utilisateurAux.setRole("USER");
			utilisateur.setPrenom(formCompte.getPrenom());
			utilisateur.setNom(formCompte.getNom());

			session.setAttribute("utilisateur", utilisateur);

			microServicePlannnings.modifierCompte(utilisateur.getId(), token, utilisateurAux);
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);

			return Constants.ESPACE_PERSONEL;

		} else {

			return "redirect:/compte/modifier?error=true";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return Constants.PAGE_ACCUEIL;
	}

}
