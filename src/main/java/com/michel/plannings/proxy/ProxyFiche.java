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
import com.michel.plannings.models.forms.FormFiche;

public interface ProxyFiche {
	
	@PostMapping("/fiche/enregistrer/{phase}/{ressource}")
	void enregistrerFiche(@RequestHeader("Authorization") String token, @RequestBody FormFiche ficheForm,@PathVariable(name = "phase") Integer idPhase ,@PathVariable(name = "ressource") Integer idRessource);

	@DeleteMapping("/fiche/supprimer/{id}")
	public void supprimerFiche(@PathVariable Integer id, @RequestHeader("Authorization") String token);

	@GetMapping("/fiche/{fiche}") // récupération fiche par son id
	public FicheAux obtenirficheParId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "fiche") Integer idFiche);
	
	
	@GetMapping("/fiche/liste/auteur/{id}") // récupération de la liste de toutes les phases par ressource
	public List<FicheAux> fichesParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	
	@GetMapping("/fiche/liste/statut")  // récupération phase par statut: actif, inactif
	public List<FicheAux> fichesParStatut(@RequestParam(name = "statut") Boolean statut);
	
	@GetMapping("/fiche/liste/niveau")  // récupération phase par niveau
	public List<FicheAux> fichesParNiveau(@RequestParam(name = "niveau") Integer niveau);
	

	@GetMapping("/fiche/liste/phase/{phase}") // récupération de la liste de toutes les phases par phase
	List<FicheAux> listeFicheParPhaseId(@RequestHeader("Authorization") String token, @PathVariable(name = "phase") Integer idPhase);
	
	@GetMapping("/fiche/liste/projet/{projet}")
	List<FicheAux> fichesParProjetId(@RequestHeader("Authorization") String token, @PathVariable(name = "projet") Integer idProjet);
	
	@GetMapping("/fiche/liste/auteur/{ressource}")
	List<FicheAux> listeFicheParRessourceId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "ressource") Integer idRessource);
	
	@GetMapping("/fiche/liste/toutes")
	List<FicheAux> toutesLesFiches(@RequestHeader("Authorization") String token);
	
	@PutMapping("/fiche/modifier/{fiche}")
	void modifierFiche(@RequestHeader("Authorization")  String token, @RequestBody FicheAux fiche, @PathVariable(name = "fiche") Integer idFiche);
	
	@PutMapping("/fiche/changer/statut/{fiche}")
	void changerStatutFiche(@PathVariable(name = "fiche") Integer idFiche);
}

