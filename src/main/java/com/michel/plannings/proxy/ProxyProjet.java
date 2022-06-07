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

import com.michel.plannings.models.ProjetAux;

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

	@GetMapping("/projet/liste/ressource/{id}") // récupération de la liste de tous les projets par ressource
	public List<ProjetAux> projetsParRessource(@RequestHeader("Authorization") String token,
			@PathVariable(name = "id") Integer id);
	
	@GetMapping("/projet/liste/type")
	public List<ProjetAux> projetsParType(@RequestHeader("Authorization") String token, @RequestParam(name = "type") String type);
	
	@GetMapping("/projet/tous")
	public List<ProjetAux> projetsTous(@RequestHeader("Authorization") String token);
	

	@PutMapping("/projet/statut/{id}")
	public  ProjetAux modifierStatutProjet(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@PutMapping("/projet/statut/liste/{id}")
	public  List<ProjetAux> modifierStatutProjetListe(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
}
