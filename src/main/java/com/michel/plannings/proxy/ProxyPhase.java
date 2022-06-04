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

import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;

public interface ProxyPhase {
	
	@PostMapping("/phase/creer")
	public void creerPhase(@RequestBody PhaseAux phase);
	
	@PutMapping("/phase/modifier/{id}")
	public void modifierPhase(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody PhaseAux phaseAux);
	
	@DeleteMapping("/phase/supprimer/{id}")
	public void supprimerPhase(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/phase/{id}") // récupération phase par son id
	public List<ProjetAux> phasesParId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/phase/liste/ressource/{id}") // récupération de la liste de toutes les phases par ressource
	public PhaseAux phasesParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/phase/liste/statut")  // récupération phase par statut: actif, inactif, suspendu, conforme, non conforme
	public List<ProjetAux> phasesParStatut(@RequestParam(name = "statut") String statut);
	
	@GetMapping("/phase/liste/projet/{id}") // récupération de la liste de toutes les phases par projet
	public List<ProjetAux> phasesParProjet(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
}
