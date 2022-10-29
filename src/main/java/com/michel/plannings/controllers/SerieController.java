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
import com.michel.plannings.models.AlerteAux;
import com.michel.plannings.models.NoteAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.SuiteAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class SerieController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projet/voir/series/{idProjet}")
	public String afficherHistoriquesProjet(@PathVariable(name = "idProjet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		//List<SuiteAux> series = microServicePlannnings.obtenirSeriesParProjet(token, idProjet);
		List<SuiteAux> series = microServicePlannnings.obtenirSeriesParProjetAuteur(token, idProjet, utilisateur.getId());
		Boolean vide = false;
		if (series.isEmpty()) {
			vide = true;
		}
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		model.addAttribute("suites", series);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.HISTORIQUES_PROJET);

	}

	@GetMapping("/historique/ajouter/serie/{idProjet}")
	public String ajouterSerie(@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		SuiteAux suite = new SuiteAux();
		suite.setIdProjet(idProjet);
		suite.setIdAuteur(utilisateur.getId());
		model.addAttribute("suite", suite);
		model.addAttribute("idProjet", idProjet);
		return Constants.testUser(utilisateur, Constants.CREATION_SERIE);
	}

	@PostMapping("/historique/ajouter/serie/{idProjet}")
	public String enregistrerSerie(@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session,
			SuiteAux suite) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			suite.setIdProjet(idProjet);
			suite.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerSerie(token, suite);
			return "redirect:/projet/voir/series/" + idProjet;
		}
	}

	@GetMapping("/projet/historique/voir/{idSerie}")
	public String voirSerie(@PathVariable(name = "idSerie") Integer idSerie, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		List<NoteAux> notes = microServicePlannnings.obtenirNotesSerieParId(token, idSerie);
		Boolean vide = false;
		if (notes.isEmpty()) {
			vide = true;
		}
		
		SuiteAux serie = microServicePlannnings.obtenirSerieParId(token, idSerie);
		ProjetAux projet = serie.getProjet();
		model.addAttribute("notes", notes);
		model.addAttribute("projet", projet);
		model.addAttribute("serie", serie);
		model.addAttribute("idSerie", idSerie);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.NOTES_SERIE);
	}

	@GetMapping("/ajouter/note/serie/{idSerie}")
	public String ajouterNoteSerie(@PathVariable(name = "idSerie") Integer idSerie, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = new NoteAux();
		note.setIdAuteur(utilisateur.getId());
		model.addAttribute("note", note);
		model.addAttribute("idSerie", idSerie);
		return Constants.testUser(utilisateur, Constants.CREATION_NOTE_SERIE);

	}

	@PostMapping("/ajouter/note/serie/{idSerie}")
	public String enregistrerNoteProjet(@PathVariable(name = "idSerie") Integer idSerie, Model model, HttpSession session,
			NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			note.setIdSource(idSerie);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerNoteSerie(token, note);
			return "redirect:/projet/historique/voir/"  + idSerie;

		}

		}
	
	@GetMapping("/note/voir/{idNote}")
	public String afficherNoteProjet(@PathVariable(name = "idNote") Integer idNote, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNoteProjet(token, idNote);
		Integer idProjet = note.getIdSource();
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("note", note);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.NOTE_PROJET);

	}
	
	@GetMapping("/projet/historique/supprimer/{idSerie}")
	public String supprimerSerie(@PathVariable(name = "idSerie") Integer idSerie, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		SuiteAux serie = microServicePlannnings.obtenirSerieParId(token, idSerie);
		Integer idProjet = serie.getIdProjet();
		microServicePlannnings.supprimerSerie(token, idSerie);
		return "redirect:/projet/voir/series/" + idProjet;
		
	}
	
	
	
	
	
	
}