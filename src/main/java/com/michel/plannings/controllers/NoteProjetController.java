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
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class NoteProjetController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projet/ajouter/notes/{id}")
	public String ajouterNoteProjet(@PathVariable(name = "id") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		NoteAux note = new NoteAux();
		note.setIdSource(idProjet);
		note.setIdAuteur(utilisateur.getId());
		model.addAttribute("note", note);
		return Constants.testUser(utilisateur, Constants.CREATION_NOTE_SERIE);

	}

	@PostMapping("/projet/ajouter/notes/{id}")
	public String enregistrerNoteProjet(@PathVariable(name = "id") Integer idProjet, Model model, HttpSession session,
			NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			note.setIdSource(idProjet);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerNoteProjet(token, note);
			return "redirect:/projet/voir/" + idProjet;

		}

	}

	@GetMapping("/projet/voir/notes/{id}")
	public String voirNotesProjet(@PathVariable(name = "id") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<NoteAux> notes = microServicePlannnings.obtenirNotesProjet(token, idProjet);
		Boolean vide = false;
		if (notes.isEmpty()) {
			vide = true;
		}
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("noteProjet", true);
		model.addAttribute("projet", projet);
		model.addAttribute("notes", notes);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.NOTES_PROJET);

	}

	@GetMapping("/projet/note/voir/{idNote}/{idProjet}")
	public String voirSimpleNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNoteProjet(token, idNote);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("noteProjet", true);
		model.addAttribute("projet", projet);
		model.addAttribute("note", note);
		return Constants.testUser(utilisateur, Constants.NOTE_PROJET);

	}

	@GetMapping("/projet/note/modifier/{idNote}/{idProjet}")
	public String modifierNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNoteProjet(token, idNote);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("noteProjet", true);
		model.addAttribute("projet", projet);
		model.addAttribute("note", note);
		return Constants.testUser(utilisateur, Constants.MODIFIER_NOTE_PROJET);

	}
	
	@PostMapping("/projet/note/modifier/{idNote}/{idProjet}")
	public String enregistrerModificationsNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session, NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {
			
			note.setId(idNote);
			note.setIdSource(idProjet);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.modifierNoteProjet(token, note);
			return "redirect:/projet/voir/" + idProjet;
		}
	}
	
	@GetMapping("/projet/note/supprimer/{idNote}/{idProjet}")
	public String supprimerNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.supprimerSimpleNoteProjet(token, idNote);
		return "redirect:/projet/voir/" + idProjet;

	}
	
	@GetMapping("/changer/statut/note/projet/{idNote}/{idProjet}")
	public String changerStatutNotePhase(@PathVariable(name = "idNote") Integer idNote, @PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.changerStatutNoteProjet(token, idNote);
		return "redirect:/projet/note/voir/" + idNote +"/" + idProjet;
	}
	
	@GetMapping("/changer/statut/note/projet/liste/{idNote}/{idProjet}")
	public String changerStatutNotePhaseListe(@PathVariable(name = "idNote") Integer idNote, @PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.changerStatutNoteProjet(token, idNote);
		return "redirect:/projet/voir/notes/" + idProjet;
	}
}
