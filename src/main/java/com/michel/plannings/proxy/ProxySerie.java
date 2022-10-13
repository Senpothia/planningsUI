package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.NoteAux;
import com.michel.plannings.models.SuiteAux;




public interface ProxySerie {
	
	@GetMapping("/series/projet/{idProjet}")
	List<SuiteAux> obtenirSeriesParProjet(@RequestHeader("Authorization") String token, @PathVariable(name = "idProjet") Integer idProjet);

	@PostMapping("/serie/ajouter")
	void creerSerie(@RequestHeader("Authorization") String token, @RequestBody SuiteAux suite);
	
	@GetMapping("/projet/historique/voir/{idSerie}")
	List<NoteAux> obtenirNotesSerieParId(@RequestHeader("Authorization") String token, @PathVariable(name = "idSerie") Integer idSerie);
	
	@PostMapping("/ajouter/note/serie")
	void creerNoteSerie(@RequestHeader("Authorization") String token, @RequestBody NoteAux note);
	
	@GetMapping("/serie/{idSerie}")
	SuiteAux obtenirSerieParId(@RequestHeader("Authorization") String token,@PathVariable(name = "idSerie") Integer idSerie);
	
	@GetMapping("/projet/historique/supprimer/{idSerie}")
	void supprimerSerie(@RequestHeader("Authorization") String token, @PathVariable(name = "idSerie") Integer idSerie);
}