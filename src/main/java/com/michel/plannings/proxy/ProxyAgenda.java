package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.TacheAux;

public interface ProxyAgenda {
	
	@GetMapping("/agenda/ressource/{idUtilisateur}")
	List<TacheAux> obtenirTachesParRessourceId( @RequestHeader("Authorization") String token, @PathVariable(name = "idUtilisateur") Integer idUtilisateur);
	
	@PostMapping("/agenda/tache/enregistrer")
	void enregistrerTache(@RequestHeader("Authorization") String token, @RequestBody TacheAux tache);
	
	@GetMapping("/tache/voir/{idTache}")
	TacheAux obtenirTacheParId(@RequestHeader("Authorization") String token, @PathVariable(name = "idTache") Integer idTache);
	
	@GetMapping("/tache/changer/statut/{idTache}")
	void tacheChangerStatut(@RequestHeader("Authorization") String token, @PathVariable(name = "idTache") Integer idTache);
	
	@GetMapping("/tache/supprimer/{idTache}")
	void supprimerTacheParId(@RequestHeader("Authorization") String token, @PathVariable(name = "idTache") Integer idTache);
	
	@PostMapping("/tache/modifier")
	void modifierTache(@RequestHeader("Authorization") String token, @RequestBody TacheAux tache);
}
