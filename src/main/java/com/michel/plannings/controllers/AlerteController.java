package com.michel.plannings.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.AlerteAux;
import com.michel.plannings.models.NoteAux;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class AlerteController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/alerte/ajouter/{idProjet}")
	public String ajouterAlerte(@PathVariable(name = "idProjet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		AlerteAux alerte = new AlerteAux();
		alerte.setIdProjet(idProjet);
		alerte.setIdAuteur(utilisateur.getId());
		model.addAttribute("alerte", alerte);
		model.addAttribute("projet", projet);
		model.addAttribute("idProjet", idProjet);
		return Constants.testUser(utilisateur, Constants.CREATION_ALERTE);
	}

	@PostMapping("/alerte/ajouter/{idProjet}")
	public String enregistrerAlerte(@PathVariable(name = "idProjet") Integer idProjet, Model model,
			HttpSession session, AlerteAux alerte) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {
			
			ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
			alerte.setPrive(projet.getPrive());
			alerte.setIdProjet(idProjet);
			alerte.setIdAuteur(utilisateur.getId());
			
			microServicePlannnings.creerAlerte(token, alerte);
			return "redirect:/projet/voir/alertes/" + idProjet;

		}

	}

	@GetMapping("/projet/voir/alertes/{idProjet}")
	public String afficherAlertesProjet(@PathVariable(name = "idProjet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		//List<AlerteAux> alertes = microServicePlannnings.obtenirAlertesParProjet(token, idProjet);
		List<AlerteAux> alertes = microServicePlannnings.obtenirAlertesParProjetAuteur(token, idProjet, utilisateur.getId());
		Boolean vide = false;
		if (alertes.isEmpty()) {
			vide = true;
		}
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		model.addAttribute("alertes", alertes);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.PROJET_ALERTES);
	}

	@GetMapping("/alerte/voir/{idAlerte}")
	public String afficherSimpleAlerte(@PathVariable(name = "idAlerte") Integer idAlerte, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		AlerteAux alerte = microServicePlannnings.obtenirSimpleAlerte(token, idAlerte);
		model.addAttribute("alerte", alerte);
		return Constants.testUser(utilisateur, Constants.ALERTE);

	}

	@GetMapping("/alerte/modifier/{idAlerte}")
	public String modifierAlerteProjet(@PathVariable(name = "idAlerte") Integer idAlerte, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		AlerteAux alerte = microServicePlannnings.obtenirSimpleAlerte(token, idAlerte);
		model.addAttribute("alerte", alerte);
		return Constants.testUser(utilisateur, Constants.MODIFIER_ALERTE);

	}

	@PostMapping("/alerte/modifier/{idAlerte}")
	public String enregistrerModificationsAlerte(@PathVariable(name = "idAlerte") Integer idAlerte, Model model,
			HttpSession session, AlerteAux alerte) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			alerte.setId(idAlerte);
			alerte.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.modifierAlerte(token, alerte);
			return "redirect:/alerte/voir/" + idAlerte;

		}
	}

	@GetMapping("/alerte/changer/statut/{alerte}/{active}")
	public String alerteModifierStatut(@PathVariable(name = "alerte") Integer idAlerte,
			@PathVariable(name = "active") Boolean actif, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.alerteChangerStatut(token, idAlerte);
		return "redirect:/alerte/voir/" + idAlerte;
	}
	
	@GetMapping("/alerte/supprimer/{alerte}")
	public String alerteModifierStatut(@PathVariable(name = "alerte") Integer idAlerte,
			 Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.alerteSupprimer(token, idAlerte);
		return Constants.ESPACE_PERSONEL;
	}
	
	@GetMapping("/alertes/liste/actives/{actif}")
	public String alerteListeActives(@PathVariable(name = "actif") Boolean actif,
			 Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<AlerteAux> alertes = microServicePlannnings.obtenirListeActivesAuteur(token, actif, utilisateur.getId());
		Boolean vide = false;
		if (alertes.isEmpty()) {
			vide = true;
		}
		List<AlerteAux> alertesPrivees = new ArrayList<>();
		if(!(Boolean) session.getAttribute("PRIVE")) {
			
			for(AlerteAux a: alertes) {
				
				if(a.getPrive()) {
					
					alertesPrivees.add(a);
				}
			}
			
			alertes.removeAll(alertesPrivees);
		}
		
		model.addAttribute("alertes", alertes);
		model.addAttribute("vide", vide);
		model.addAttribute("statut", actif);
		return Constants.testUser(utilisateur, Constants.ALERTES_STATUT);
		
	}
}
