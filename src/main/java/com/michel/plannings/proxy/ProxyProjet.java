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

import com.michel.plannings.models.GanttRow;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.UtilisateurAux;

public interface ProxyProjet {

	@PostMapping("/projet/creer")
	public void creerProjet(@RequestHeader("Authorization") String token, @RequestBody ProjetAux projet);

	@PutMapping("/projet/modifier/{id}")
	public void modifierProjet(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody ProjetAux projetAux);

	@DeleteMapping("/projet/supprimer/{id}")
	public void supprimerProjet(@PathVariable Integer id, @RequestHeader("Authorization") String token);

	@GetMapping("/projet/{id}") // récupération projet par son id
	public ProjetAux projetParId(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/projet/liste/chef/{id}") // récupération de la liste de tous les projet par chef
	public List<ProjetAux> projetsParChef(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);

	@GetMapping("/projet/liste/ressource/{id}/{masque}") // récupération de la liste de tous les projets par ressource
	public List<ProjetAux> projetsParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id, @PathVariable(name = "masque") Boolean masque);
	
	@GetMapping("/projet/liste/type")
	public List<ProjetAux> projetsParType(@RequestHeader("Authorization") String token, @RequestParam(name = "type") String type);
	
	@GetMapping("/projet/tous")
	public List<ProjetAux> projetsTous(@RequestHeader("Authorization") String token);
	
	@GetMapping("/projet/prives")
	List<ProjetAux> projetsTousPrives(@RequestHeader("Authorization") String token);
	
	
	@PutMapping("/projet/statut/{id}")
	public  ProjetAux modifierStatutProjet(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@PutMapping("/projet/statut/liste/{id}")
	public  List<ProjetAux> modifierStatutProjetListe(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/projet/tous/actifs")
	public List<ProjetAux> projetsTousActifs(@RequestHeader("Authorization") String token);
	
	@GetMapping("/projet/affecter/ressource/{id}")
	public List<ProjetAux> projetsParRessourceSansAffectation(@RequestHeader("Authorization") String token, @PathVariable Integer id);
	
	@GetMapping("/ressources/affecter/selection/projet/{id}/{ressource}")
	void affecterProjetRessource(@PathVariable Integer id, @PathVariable Integer ressource, @RequestHeader("Authorization") String token);
	
	@GetMapping("/projet/affectation/ressource/{id}")
	public List<ProjetAux> projetsAvecEtatImplication(@RequestHeader("Authorization") String token, @PathVariable Integer id);
	
	@GetMapping("/projet/voir/ressources/{id}")
	public List<UtilisateurAux> ressourcesParProjet(@RequestHeader("Authorization") String token, @PathVariable Integer id);

	@GetMapping("/projet/gantt/{id}") // récupération diagramme de Gantt par id projet
	public List<GanttRow> ganttProjetParId(@RequestHeader("Authorization") String token, @PathVariable(name = "id") Integer id);
	
	@GetMapping("/projet/tous/{statut}")
	List<ProjetAux> projetsParStatut(@RequestHeader("Authorization") String token,  @PathVariable(name = "statut") Boolean statut);
}
