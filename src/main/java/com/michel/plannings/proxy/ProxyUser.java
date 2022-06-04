package com.michel.plannings.proxy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.Login;
import com.michel.plannings.models.UtilisateurAux;

public interface ProxyUser {
	
	
	@PostMapping("/compte/connexion")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);
		
	@PutMapping("/compte/modifier/{id}")
	public void modifierCompte(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody UtilisateurAux utilisateurAux);

	@DeleteMapping("/compte/creer")
	public void creerCompte(@RequestBody UtilisateurAux user);
	

}
