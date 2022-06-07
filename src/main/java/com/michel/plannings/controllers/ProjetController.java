package com.michel.plannings.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class ProjetController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projets/access")
	public String accueilProjets(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.testUser(utilisateur, Constants.ACCUEIL_PROJETS);

	}

	@GetMapping("/projets/creation")
	public String creerProjets(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("projetAux", new ProjetAux());
		return Constants.testUser(utilisateur, Constants.CREATION_PROJET);

	}

	@PostMapping("/projets/creation") // Enregistrement des éléments de création d'une qualification
	public String enregistrerProjet(Model model, HttpSession session, ProjetAux projetAux) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			String token = Constants.getToken(session);
			projetAux.setChefId(utilisateur.getId());
			microServicePlannnings.creerProjet(token, projetAux);
			return Constants.ACCUEIL_PROJETS;

		}

	}
	
	@GetMapping("/projets/voir/tous")
	public String tousProjets(Model model, HttpSession session) {
		
		System.out.println("demande de tous les projets");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		System.out.println("token 2: " + token);
		List<ProjetAux> projets = microServicePlannnings.projetsTous(token);
		model.addAttribute("projets", projets);
		model.addAttribute("access", "1");
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}
	
	@GetMapping("/projet/voir/{id}")
	public String voirProjetId(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, id);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.PROJET);
		
	}
	
	@GetMapping("/projet/page/statut/{id}")
	public String changerStatutProjetPage(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.modifierStatutProjet(id, token);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.PROJET);
		
	}
	
	@GetMapping("/projet/liste/statut/{id}")
	public String changerStatutProjetListe(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.modifierStatutProjetListe(id, token);
		model.addAttribute("projets", projets);
		model.addAttribute("access", "1");
		return Constants.testUser(utilisateur, Constants.PROJETS);
		
	}

}
