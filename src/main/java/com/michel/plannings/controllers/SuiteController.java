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
import com.michel.plannings.models.NoteAux;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.SuiteAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class SuiteController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/phase/voir/suites/{idPhase}")
	public String afficherHistoriquesPhase(@PathVariable(name = "idPhase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<SuiteAux> suites = microServicePlannnings.obtenirSuitesParPhase(token, idPhase);
		Boolean vide = false;
		if (suites.isEmpty()) {
			vide = true;
		}
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		model.addAttribute("phase", phase);
		model.addAttribute("suites", suites);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.HISTORIQUES_PHASE);

	}

	@GetMapping("/historique/ajouter/suite/{idPhase}")
	public String ajouterSuite(@PathVariable(name = "idPhase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		SuiteAux suite = new SuiteAux();
		suite.setIdProjet(idPhase);
		suite.setIdAuteur(utilisateur.getId());
		model.addAttribute("suite", suite);
		model.addAttribute("idPhase", idPhase);
		return Constants.testUser(utilisateur, Constants.CREATION_SUITE);
	}

	@PostMapping("/historique/ajouter/suite/{idPhase}")
	public String enregistrerSuite(@PathVariable(name = "idPhase") Integer idPhase, Model model, HttpSession session,
			SuiteAux suite) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			suite.setIdProjet(idPhase);
			suite.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerSuite(token, suite);
			return "redirect:/phase/voir/suites/" + idPhase;
		}
	}

	@GetMapping("/phase/historique/voir/{idSuite}")
	public String voirSuite(@PathVariable(name = "idSuite") Integer idSuite, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<NoteAux> notes = microServicePlannnings.obtenirNotesSuiteParId(token, idSuite);
		Boolean vide = false;
		if (notes.isEmpty()) {
			vide = true;
		}
		
		SuiteAux suite = microServicePlannnings.obtenirSuiteParId(token, idSuite);
		// recuperer suite par id et ajouter au model
		// modifier la template notesPhaseHistorique
		Integer idProjet = suite.getPhase().getIdProjet();
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("suite", suite);
		model.addAttribute("projet", projet);
		model.addAttribute("notes", notes);
		model.addAttribute("idSuite", idSuite);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.NOTES_SUITE);
	}
	
	@GetMapping("/ajouter/note/suite/{idSuite}")
	public String ajouterNoteSuite(@PathVariable(name = "idSuite") Integer idSuite, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = new NoteAux();
		note.setIdSource(idSuite);
		note.setIdAuteur(utilisateur.getId());
		model.addAttribute("note", note);
		model.addAttribute("idSuite", idSuite);
		return Constants.testUser(utilisateur, Constants.CREATION_NOTE_SUITE);

	}

	@PostMapping("/ajouter/note/suite/{idSuite}")
	public String enregistrerNoteProjet(@PathVariable(name = "idSuite") Integer idSuite, Model model,
			HttpSession session, NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			note.setIdSource(idSuite);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerNoteSuiteForPhase(token, note);
			return "redirect:/phase/historique/voir/" + idSuite;

		}

	}

	@GetMapping("/note/phase/voir/{idNote}")
	public String afficherNotePhase(@PathVariable(name = "idNote") Integer idNote, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNotePhase(token, idNote);
		Integer idPhase = note.getIdSource();
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		model.addAttribute("note", note);
		model.addAttribute("phase", phase);
		return Constants.testUser(utilisateur, Constants.NOTE_PHASE);

	}
	
	@GetMapping("/phase/historique/supprimer/{idSuite}")
	public String supprimerSuite(@PathVariable(name = "idSuite") Integer idSuite, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		SuiteAux suite = microServicePlannnings.obtenirSuiteParId(token, idSuite);
		Integer idPhase = suite.getPhase().getId();
		microServicePlannnings.supprimerSuite(token, idSuite);
		return "redirect:/phase/voir/suites/" + idPhase;
	}
	
	
	
	

}
