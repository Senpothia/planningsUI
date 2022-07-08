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
import com.michel.plannings.models.FicheAux;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.models.forms.FormFiche;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class FichesController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/fiches/access")
	public String accueilProjets(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.testUser(utilisateur, Constants.ACCUEIL_FICHES);

	}

	@GetMapping("/projet/ajouter/fiches/{phase}")
	public String formulaireCreationFiche(@PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("formFiche", new FormFiche());
		return Constants.testUser(utilisateur, Constants.CREATION_FICHE);
	}

	@PostMapping("/fiches/creation/{phase}/{ressource}/{projet}")
	public String creationFiche(@PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "ressource") Integer idRessource, @PathVariable(name = "projet") Integer idProjet,
			Model model, HttpSession session, FormFiche fiche) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.enregistrerFiche(token, fiche, idPhase, idRessource, idProjet);

		if (idPhase != 0 && idProjet != 0) {
			return Constants.testUser(utilisateur, "redirect:/projet/voir/" + idProjet);
		} else {
			return Constants.testUser(utilisateur, "redirect:/fiches/voir/ressource");

		}

	}

	@GetMapping("/phase/fiches/ajouter/{phase}")
	public String accesformulaireCreationFiche(@PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		model.addAttribute("projet", projet);
		model.addAttribute("phase", phase);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("formFiche", new FormFiche());
		return Constants.testUser(utilisateur, Constants.CREATION_FICHE);
	}

	@GetMapping("/fiches/liste/{phase}")
	public String listeFicheParPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<FicheAux> fiches = microServicePlannnings.listeFicheParPhaseId(token, idPhase);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		Integer idProjet = phase.getIdProjet();
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("fiches", fiches);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);

		return Constants.testUser(utilisateur, Constants.LISTE_FICHES);
	}

	@GetMapping("/fiche/voir/{fiche}")
	public String voirFicheParId(@PathVariable(name = "fiche") Integer idFiche, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		FicheAux fiche = microServicePlannnings.obtenirficheParId(token, idFiche);

		model.addAttribute("fiche", fiche);
		model.addAttribute("supprimer", false);

		return Constants.testUser(utilisateur, Constants.FICHE);
	}

	@GetMapping("/projet/voir/fiches/{projet}")
	public String voirFicheParProjetId(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		List<PhaseAux> phases = microServicePlannnings.phasesParProjetId(token, idProjet);
		phases.remove(0);
		if (phases.isEmpty()) {
			model.addAttribute("vide", true);
		} else {

			int nbreFiches = 0;
			for (PhaseAux p : phases) {

				if (!p.getFiches().isEmpty()) {

					nbreFiches = nbreFiches + p.getFiches().size();
				}
			}
			if (nbreFiches == 0) {
				model.addAttribute("vide", true);
			}

		}

		model.addAttribute("projet", projet);
		model.addAttribute("phases", phases);

		return Constants.testUser(utilisateur, Constants.FICHES_PROJET);
	}
	
	@GetMapping("/projet/voir/fiches/spontanees/{projet}")
	public String voirFicheSpontaneesParProjetId(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		PhaseAux phaseVide = microServicePlannnings.phaseVideParProjetId(token, idProjet);
		List<FicheAux> fiches = phaseVide.getFiches();
		if(fiches.isEmpty()) {
			model.addAttribute("vide", true);
		}
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", false);

		return Constants.testUser(utilisateur, Constants.FICHES_SPONTANEES);
	}


	@GetMapping("/fiches/voir/ressource")
	public String listeFicheParRessourceId(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		Integer idRessource = utilisateur.getId();

		List<FicheAux> fiches = microServicePlannnings.listeFicheParRessourceId(token, idRessource);
		Boolean vide = false;
		if(fiches.isEmpty()) { vide = true;}
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", false);
		model.addAttribute("gestion", false);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.FICHES_RESSOURCE);
	}

	@GetMapping("/fiches/voir/toutes")
	public String toutesLesFiches(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<FicheAux> fiches = microServicePlannnings.toutesLesFiches(token);
		Boolean vide = false;
		if(fiches.isEmpty()) { vide = true;}
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", false);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.FICHES_RESSOURCE);
	}

	@GetMapping("/fiche/modifier/{fiche}")
	public String modifierFiche(@PathVariable(name = "fiche") Integer idFiche, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		FicheAux fiche = microServicePlannnings.obtenirficheParId(token, idFiche);
		model.addAttribute("fiche", fiche);
		return Constants.testUser(utilisateur, Constants.MODIFIER_FICHE);
	}

	@PostMapping("/fiche/modifier/{fiche}")
	public String enregistrerModificationFiche(@PathVariable(name = "fiche") Integer idFiche, Model model,
			HttpSession session, FicheAux fiche) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.modifierFiche(token, fiche, idFiche);
		model.addAttribute("fiche", fiche);
		return Constants.testUser(utilisateur, "redirect:/fiche/voir/" + idFiche);
	}

	@GetMapping("/fiches/gerer")
	public String gererLesFiches(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<FicheAux> fiches = microServicePlannnings.toutesLesFiches(token);
		Boolean vide = false;
		if(fiches.isEmpty()) { vide = true;}
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", true);
		model.addAttribute("vide", vide);


		return Constants.testUser(utilisateur, Constants.FICHES_RESSOURCE);
	}

	@GetMapping("/fiche/changer/statut/{id}")
	public String changerStatutFiches(@PathVariable(name = "id") Integer idFiche, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		microServicePlannnings.changerStatutFiche(idFiche);
		List<FicheAux> fiches = microServicePlannnings.toutesLesFiches(token);

		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", true);

		return Constants.testUser(utilisateur, Constants.FICHES_RESSOURCE);
	}

	@GetMapping("/fiche/supprimer/{fiche}")
	public String demandeSuppressionFicheParId(@PathVariable(name = "fiche") Integer idFiche, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		FicheAux fiche = microServicePlannnings.obtenirficheParId(token, idFiche);

		model.addAttribute("fiche", fiche);
		model.addAttribute("supprimer", true);
		model.addAttribute("login", new Login());

		return Constants.testUser(utilisateur, Constants.FICHE);
	}

	@PostMapping("/supprimer/fiche/{fiche}")
	public String suppressionFicheParId(@PathVariable(name = "fiche") Integer idFiche, Login login, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		Boolean test = userConnexion.confirmerUtilisateur(login, session);
		if (test) {
			microServicePlannnings.supprimerFiche(idFiche, token);
		}
		model.addAttribute("test", test);
		return Constants.testUser(utilisateur, Constants.CONFIRMATION);
	}

	@GetMapping("/fiches/spontanee/ajouter")
	public String accesformulaireCreationFicheSpontanne(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		PhaseAux phase = new PhaseAux();
		phase.setId(0);
		ProjetAux projet = new ProjetAux();
		projet.setId(0);
		model.addAttribute("projet", projet);
		model.addAttribute("phase", phase);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("formFiche", new FormFiche());

		return Constants.testUser(utilisateur, Constants.CREATION_FICHE_SPONTANEE);

	}

	@GetMapping("/fiches/voir/spontanee")
	public String listeFichesSpontannes(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<FicheAux> fiches = microServicePlannnings.toutesLesFichesspontanees(token);
		Boolean vide = false;
		if(fiches.isEmpty()) { vide = true;}
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", false);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.FICHES_SPONTANEES);
	}
	
	
	

	@GetMapping("/fiches/voir/projet/{ressource}/{projet}")
	public String listeFichesPourRessourceSurProjet(@PathVariable(name = "ressource") Integer idRessource, @PathVariable(name = "projet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<FicheAux> fiches = microServicePlannnings.fichesDeRessourceSurProjet(token, idRessource, idProjet);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(idRessource, token);
		ProjetAux  projet = microServicePlannnings.projetParId(token, idProjet);
		Boolean vide = fiches.isEmpty();
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", true);
		model.addAttribute("gestion", false);
		model.addAttribute("ressource", ressource);
		model.addAttribute("projet", projet);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.FICHES_RESSOURCE_PROJET);
	}
	
	@GetMapping("/fiches/voir/active/{active}")
	public String voirLesFichesStatut(@PathVariable(name = "active") Boolean active,Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<FicheAux> fiches = microServicePlannnings.toutesLesFichesStatut(token, active);
		Boolean vide = fiches.isEmpty();
		model.addAttribute("fiches", fiches);
		model.addAttribute("fiches", fiches);
		model.addAttribute("toutes", false);
		model.addAttribute("gestion", false);
		model.addAttribute("statut", true);
		model.addAttribute("active", active);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.FICHES_ACTIVES);
	}
	
	@GetMapping("/fiches/spontanee/ajouter/{projet}")
	public String accesformulaireCreationFicheSpontanneSurProjet(@PathVariable(name = "projet") Integer idProjet,Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		PhaseAux phase = new PhaseAux();
		phase.setId(0);
		ProjetAux projet = new ProjetAux();
		projet.setId(idProjet);
		model.addAttribute("projet", projet);
		model.addAttribute("phase", phase);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("formFiche", new FormFiche());
		return Constants.testUser(utilisateur, Constants.CREATION_FICHE_SPONTANEE_PROJET);

	}

}
