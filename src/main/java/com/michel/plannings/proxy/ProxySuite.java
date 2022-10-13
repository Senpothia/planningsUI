package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.NoteAux;
import com.michel.plannings.models.SuiteAux;

public interface ProxySuite {
	
	
	@GetMapping("/suites/phase/{idPhase}")
	List<SuiteAux> obtenirSuitesParPhase(@RequestHeader("Authorization") String token, @PathVariable(name = "idPhase") Integer idPhase);

	@PostMapping("/suite/ajouter")
	void creerSuite(@RequestHeader("Authorization") String token, @RequestBody SuiteAux suite);
	
	@GetMapping("/phase/historique/voir/{idSuite}")
	List<NoteAux> obtenirNotesSuiteParId(@RequestHeader("Authorization") String token, @PathVariable(name = "idSuite") Integer idSuite);
	
	@PostMapping("/ajouter/note/suite")
	void creerNoteSuiteForPhase(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
	
	@GetMapping("/suite/{idSuite}")
	SuiteAux obtenirSuiteParId(@RequestHeader("Authorization") String token, @PathVariable(name = "idSuite") Integer idSuite);
	
	@GetMapping("/phase/historique/supprimer/{idSuite}")
	void supprimerSuite(@RequestHeader("Authorization") String token, @PathVariable(name = "idSuite") Integer idSuite);

}
