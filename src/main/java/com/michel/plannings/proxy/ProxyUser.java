package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.Login;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.UtilisateurAux;

public interface ProxyUser {
	
	
	@PostMapping("/compte/connexion")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);
		
	@PutMapping("/compte/modifier/{id}")
	public void modifierCompte(@PathVariable(name="id") Integer id, @RequestHeader("Authorization") String token,
			@RequestBody UtilisateurAux utilisateurAux);

	@PostMapping("/compte/creer")
	public void creerCompte(@RequestBody UtilisateurAux user);
	
	@GetMapping("/ressources/liste")
	public List<UtilisateurAux> toutesLesRessources(@RequestHeader("Authorization") String token);
	
	@PutMapping("/ressources/statut/liste/{id}")
	public void ChangerStatutRessourcesId(@RequestHeader("Authorization") String token, @PathVariable Integer id);
	
	@GetMapping("/ressources/get/{id}")
	public UtilisateurAux obtenirRessourceParId(@PathVariable Integer id, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ressources/affecter/projet/{id}")
	List<ProjetAux> affectionRessourceParprojet(@RequestHeader("Authorization") String token,
			@PathVariable Integer id);
	
	@PutMapping("/ressources/affectation/retirer/projet/{projet}/{ressource}")
	void retirerAffectation(@PathVariable Integer ressource,@PathVariable Integer projet, @RequestHeader("Authorization") String token);
	

	@PutMapping("/ressources/affectation/ajouter/projet/{projet}/{ressource}")
	void ajouterAffectation(@PathVariable Integer ressource,@PathVariable Integer projet, @RequestHeader("Authorization") String token);
	
	@PostMapping("/compte/ajouter/ressource")
	public void ajouterRessource(@RequestBody UtilisateurAux user, @RequestHeader("Authorization") String token);
	
	@GetMapping("/ressources/disponibles/projet/{id}")
	public List<UtilisateurAux> ressourcesDispoParProjet(@RequestHeader("Authorization") String token, @PathVariable Integer id);
	
	
}
