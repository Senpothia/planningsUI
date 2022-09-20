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
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class NotePhaseController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/phase/notes/ajouter/{idPhase}/{idProjet}")
	public String ajouterNotePhase(@PathVariable(name = "idPhase") Integer idPhase,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		NoteAux note = new NoteAux();
		note.setIdSource(idPhase);
		note.setIdAuteur(utilisateur.getId());
		model.addAttribute("note", note);
		model.addAttribute("idProjet", idProjet);
		return Constants.testUser(utilisateur, Constants.CREATION_NOTE_PHASE);
	}

	@PostMapping("/phase/ajouter/notes/{idPhase}/{idProjet}")
	public String enregistrerNotePhase(@PathVariable(name = "idPhase") Integer idPhase,
			@PathVariable(name = "idProjet") Integer idProjet, Model model, HttpSession session, NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			note.setIdSource(idPhase);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.creerNotePhase(token, note);
			return "redirect:/projet/voir/phases/" + idProjet;

		}

	}

	@GetMapping("/notes/liste/{idPhase}")
	public String afficherNotesPhase(@PathVariable(name = "idPhase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<NoteAux> notes = microServicePlannnings.obtenirListeNotesPhase(token, idPhase);
		Boolean vide = false;
		if (notes.isEmpty()) {
			vide = true;
		}
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		model.addAttribute("noteProjet", false);
		model.addAttribute("phase", phase);
		model.addAttribute("notes", notes);
		model.addAttribute("vide", vide);

		return Constants.testUser(utilisateur, Constants.NOTES_PHASE);
	}

	@GetMapping("/phase/note/voir/{idNote}/{idPhase}")
	public String afficherSimpleNotePhase(@PathVariable(name = "idNote") Integer idNote, @PathVariable(name = "idPhase") Integer idPhase,
			 Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNotePhase(token, idNote);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		model.addAttribute("noteProjet", true);
		model.addAttribute("phase", phase);
		model.addAttribute("note", note);
		return Constants.testUser(utilisateur, Constants.NOTE_PHASE);
	
	}

	@GetMapping("/phase/note/supprimer/{idNote}/{idPhase}")
	public String supprimerNotePhase(@PathVariable(name = "idNote") Integer idNote,@PathVariable(name = "idPhase") Integer idPhase,Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.supprimerSimpleNotePhase(token, idNote);
		return "redirect:/notes/liste/" + idPhase;
	}
	
	@GetMapping("/phase/note/modifier/{idNote}/{idPhase}")
	public String modifierNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idPhase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		NoteAux note = microServicePlannnings.obtenirSimpleNotePhase(token, idNote);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		model.addAttribute("noteProjet", false);
		model.addAttribute("phase", phase);
		model.addAttribute("note", note);
		return Constants.testUser(utilisateur, Constants.MODIFIER_NOTE_PHASE);

	}
	
	@PostMapping("/phase/note/modifier/{idNote}/{idPhase}")
	public String enregistrerModificationsNoteProjet(@PathVariable(name = "idNote") Integer idNote,
			@PathVariable(name = "idPhase") Integer idPhase, Model model, HttpSession session, NoteAux note) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {
			
			note.setId(idNote);
			note.setIdSource(idPhase);
			note.setIdAuteur(utilisateur.getId());
			String token = Constants.getToken(session);
			microServicePlannnings.modifierNotePhase(token, note);
			return "redirect:/phase/note/voir/" + idNote +"/" + idPhase;
			//return "ok";
		}
	}
}
