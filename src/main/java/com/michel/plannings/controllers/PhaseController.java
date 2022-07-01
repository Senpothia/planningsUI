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
import com.michel.plannings.models.FicheAux;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class PhaseController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projet/ajouter/phase/{projet}")
	public String ajouterPhaseProjet(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		model.addAttribute("ressources", ressources);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.SELECTION_RESSOURCE);
	}

	@GetMapping("/projet/ajouter/phase/associer/{projet}/{ressource}")

	public String associerRessourceProjetPhase(@PathVariable(name = "projet") Integer idProjet,
			@PathVariable(name = "ressource") Integer idRessource, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(idRessource, token);
		model.addAttribute("ressource", ressource);
		model.addAttribute("projet", projet);
		model.addAttribute("phase", new PhaseAux());
		return Constants.testUser(utilisateur, Constants.CREATION_PHASE);

	}

	@PostMapping("/projet/ajouter/phase/associer/{projet}/{ressource}")
	public String enregistrerPhase(@PathVariable(name = "projet") Integer idProjet,
			@PathVariable(name = "ressource") Integer idRessource, Model model, HttpSession session, PhaseAux phase) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.creerPhase(token, phase, idProjet, idRessource);

		return Constants.testUser(utilisateur, "redirect:/projet/voir/" + idProjet);

	}

	@GetMapping("/projet/voir/phases/{projet}")
	public String associerRessourceProjetPhase(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesParProjetId(token, idProjet);

		if (phases.isEmpty() || phases.size() < 2) {
			model.addAttribute("vide", true);
		} else {
			model.addAttribute("vide", false);
		}
		phases.remove(0);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		model.addAttribute("phases", phases);

		return Constants.testUser(utilisateur, Constants.PHASES);
	}

	@GetMapping("/phase/voir/{phase}")
	public String voirPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		model.addAttribute("suppression", false);
		return Constants.testUser(utilisateur, Constants.PHASE);
	}

	@GetMapping("/phase/modifier/{phase}")
	public String modifierPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		return Constants.testUser(utilisateur, Constants.MODIFIER_PHASE);
	}

	@PostMapping("/projet/modifier/phase/{phase}")
	public String modifierPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session,
			PhaseAux phase) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.modifierPhase(token, phase, idPhase);

		return Constants.testUser(utilisateur, "redirect:/phase/voir/" + idPhase);

	}

	@GetMapping("/phase/supprimer/{phase}")
	public String demandeSuppressionPhaseParId(@PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);

		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		model.addAttribute("supprimer", true);
		model.addAttribute("login", new Login());

		return Constants.testUser(utilisateur, Constants.PHASE);
	}

	@PostMapping("/supprimer/phase/{phase}")
	public String suppressionPhaseParId(@PathVariable(name = "phase") Integer idPhase, Login login, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		Boolean test = userConnexion.confirmerUtilisateur(login, session);
		if (test) {
			microServicePlannnings.supprimerPhase(idPhase, token);
		}
		model.addAttribute("test", test);
		return Constants.testUser(utilisateur, Constants.CONFIRMATION);
	}

	@GetMapping("/projets/liste/phases/ressource/{ressource}")
	public String listePhaseParId(@PathVariable(name = "ressource") Integer idRessource, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesParRessource(token, idRessource);
		Boolean vide = false;
		if (phases.isEmpty() || phases.size() < 2) {
			vide = true;
		}
		phases.remove(0);
		model.addAttribute("phases", phases);
		model.addAttribute("vide", vide);
		model.addAttribute("ressource", utilisateur);
		return Constants.testUser(utilisateur, Constants.PHASES_RESSOURCE);
	}

	@GetMapping("/phase/actives/liste/{active}")
	public String listePhaseActives(@PathVariable(name = "active") boolean active, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesActives(token, active);
		Boolean vide = false;
		if (phases.isEmpty()) {
			vide = true;
			model.addAttribute("phases", phases);
		}else {
			List<PhaseAux> phasesSans0 = new ArrayList<>();
			System.out.println("Taille liste phases: " + phases.size());
			for(PhaseAux ph: phases) {
				
				if(ph.getNumero() != 0) {
					phasesSans0.add(ph);
				}
			}
			
			if (phases.isEmpty()) {
				vide = true;
			}
			model.addAttribute("phases", phasesSans0);
		}

		
		model.addAttribute("vide", vide);
		model.addAttribute("ressource", utilisateur);
		return Constants.testUser(utilisateur, Constants.PHASES_ACTIVES);
	}

	@GetMapping("/phase/changer/statut/{phase}/{active}")
	public String changerPhaseStatut(@PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "active") boolean active, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.changerStatutPhase(token, idPhase, active);

		PhaseAux ph = microServicePlannnings.phaseParId(token, idPhase);
		Integer idProjet = ph.getIdProjet();
		return Constants.testUser(utilisateur, "redirect:/projet/voir/phases/" + idProjet);

	}

}
