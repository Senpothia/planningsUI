package com.michel.plannings.proxy;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.plannings.models.Dependance;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;

public interface ProxyPhase {
	
	@PostMapping("/phase/creer/{projet}/{ressource}")
	public void creerPhase(@RequestHeader("Authorization") String token, @RequestBody PhaseAux phase, @PathVariable (name="projet") Integer idProjet, @PathVariable (name="ressource") Integer idRessource);
	
	@DeleteMapping("/phase/supprimer/{id}")
	public void supprimerPhase(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/phase/{id}") // récupération phase par son id
	public List<ProjetAux> phasesParId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/phase/liste/statut")  // récupération phase par statut: actif, inactif, suspendu, conforme, non conforme
	public List<ProjetAux> phasesParStatut(@RequestParam(name = "statut") String statut);
	
	@GetMapping("/phase/liste/projet/{projet}") // récupération de la liste de toutes les phases par projet
	public List<PhaseAux> phasesParProjetId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "projet") Integer idProjet);
	
	@GetMapping("/phase/voir/{phase}")
	PhaseAux phaseParId(@RequestHeader("Authorization") String token, @PathVariable(name = "phase") Integer idPhase);
	
	@PostMapping("/phase/modifier/{phase}")
	void modifierPhase(@RequestHeader("Authorization") String token, @RequestBody PhaseAux phase, @PathVariable(name = "phase") Integer idPhase);
	
	@GetMapping("/phase/liste/ressource/{ressource}") // récupération de la liste de toutes les phases par ressource
	public List<PhaseAux> phasesParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "ressource") Integer idRessource);
	
	@GetMapping("/phase/liste/actives/{active}")
	List<PhaseAux> phasesActives(@RequestHeader("Authorization") String token, @PathVariable(name = "active") Boolean active);
	
	@PutMapping("/phase/changer/statut/{phase}/{active}")
	void changerStatutPhase(@RequestHeader("Authorization") String token,  @PathVariable(name = "phase") Integer idPhase,  @PathVariable(name = "active") boolean active);
	
	@GetMapping("/phase/vide/liste/projet/{projet}")
	PhaseAux phaseVideParProjetId(@RequestHeader("Authorization") String token,@PathVariable (name="projet") Integer idProjet);
	
	@GetMapping("/phase/liaison/selection/{phase}/{dependance}/{statut}")
	void creerLiaison(@RequestHeader("Authorization") String token, @PathVariable(name = "phase") Integer idPhase, @PathVariable(name = "dependance") Integer idDependance, @PathVariable(name = "statut") boolean statut);
	
	@GetMapping("/phase/liaison/dependances/{phase}")
	List<Dependance> obtenirDependances(@RequestHeader("Authorization") String token,@PathVariable(name = "phase") Integer idPhase);
	
	@PostMapping("/phase/modifier/liaison/{phase}")
	void modifierPhasePourLiaison(@RequestHeader("Authorization") String token, @RequestBody PhaseAux phase, @PathVariable(name = "phase") Integer idPhase);
	
	@GetMapping("/phase/liaison/antecedents/{phase}")
	List<Dependance> obtenirAntecedents(@RequestHeader("Authorization") String token, @PathVariable(name = "phase") Integer idPhase);
	
	@GetMapping("/phase/projet/supprimer/liaisons/{projet}")
	void supprimerLiaisions(@RequestHeader("Authorization") String token, @PathVariable(name = "projet") Integer idProjet);
	
	@GetMapping("/phase/projet/dependances/{projet}")
	List<Dependance> obtenirDependancesProjet(@RequestHeader("Authorization") String token, @PathVariable(name = "projet") Integer idProjet);
	
	@GetMapping("/phase/dependances/dates/limite/{phase}")
	List<LocalDateTime> obtenirDatesLimites(@RequestHeader("Authorization") String token,  @PathVariable(name = "phase") Integer idPhase);
}  