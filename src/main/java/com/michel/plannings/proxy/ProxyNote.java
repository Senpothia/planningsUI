package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.NoteAux;

public interface ProxyNote {
	
	@PostMapping("/note/ajouter")
	void creerNoteProjet(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
	
	@GetMapping("/notes/projet/{idProjet}")
	List<NoteAux> obtenirNotesProjet(@RequestHeader("Authorization") String token, @PathVariable(name = "idProjet") Integer idProjet);
	
	@GetMapping("/projet/note/voir/{idNote}")
	NoteAux obtenirSimpleNoteProjet(@RequestHeader("Authorization") String token, @PathVariable(name = "idNote") Integer idNote);
	
	@PostMapping("/note/modifier")
	void modifierNoteProjet(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
	
	@GetMapping("/projet/note/supprimer/{idNote}")
	void supprimerSimpleNoteProjet(@RequestHeader("Authorization") String token,  @PathVariable(name = "idNote") Integer idNote);
	
	// *************************************************
	
	@PostMapping("/phase/note/ajouter")
	void creerNotePhase(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
	
	@GetMapping("/notes/liste/{idPhase}")
	List<NoteAux> obtenirListeNotesPhase(@RequestHeader("Authorization") String token,@PathVariable(name = "idPhase") Integer idPhase);
	
	@GetMapping("/phase/note/voir/{idNote}")
	NoteAux obtenirSimpleNotePhase(@RequestHeader("Authorization") String token, @PathVariable(name = "idNote") Integer idNote);
	
	@GetMapping("/phase/note/supprimer/{idNote}")
	void supprimerSimpleNotePhase(@RequestHeader("Authorization") String token, @PathVariable(name = "idNote") Integer idNote);
	
	@PostMapping("/phase/note/modifier")
	void modifierNotePhase(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
}
