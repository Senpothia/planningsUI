package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.plannings.models.FicheAux;

public interface ProxyFiche {
	
	@PostMapping("/fiche/creer")
	public void creerFiche(@RequestBody FicheAux projet);
	
	@PutMapping("/fiche/modifier/{id}")
	public void modifierFiche(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody FicheAux ficheAux);
	
	@DeleteMapping("/fiche/supprimer/{id}")
	public void supprimerFiche(@PathVariable Integer id, @RequestHeader("Authorization") String token);

	@GetMapping("/fiche/{id}") // récupération fiche par son id
	public FicheAux ficheParId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/fiche/liste/auteur/{id}") // récupération de la liste de toutes les phases par ressource
	public List<FicheAux> fichesParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/fiche/liste/phase/{id}") // récupération de la liste de toutes les phases par phase
	public List<FicheAux> fichesParPhase(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/fiche/liste/statut")  // récupération phase par statut: actif, inactif
	public List<FicheAux> fichesParStatut(@RequestParam(name = "statut") Boolean statut);
	
	@GetMapping("/fiche/liste/niveau")  // récupération phase par niveau
	public List<FicheAux> fichesParNiveau(@RequestParam(name = "niveau") Integer niveau);
	
}
