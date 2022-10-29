package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.michel.plannings.models.AlerteAux;

public interface ProxyAlerte {
	
	@PostMapping("/alerte/ajouter")
	void creerAlerte(@RequestHeader("Authorization") String token, @RequestBody AlerteAux alerte);

	@GetMapping("/alertes/projet/{idProjet}")
	List<AlerteAux> obtenirAlertesParProjet(@RequestHeader("Authorization") String token,@PathVariable(name = "idProjet") Integer idProjet);
	
	@GetMapping("/alerte/voir/{idAlerte}")
	AlerteAux obtenirSimpleAlerte(@RequestHeader("Authorization") String token, @PathVariable(name = "idAlerte") Integer idAlerte);
	
	@PostMapping("/alerte/modifier")
	void modifierAlerte(@RequestHeader("Authorization") String token,  @RequestBody AlerteAux alerte);
	
	@GetMapping("/alerte/changer/statut/{idAlerte}")
	void alerteChangerStatut(@RequestHeader("Authorization") String token, @PathVariable(name = "idAlerte") Integer idAlerte);
	
	@GetMapping("/alerte/supprimer/{idAlerte}")
	void alerteSupprimer(@RequestHeader("Authorization") String token, @PathVariable(name = "idAlerte") Integer idAlerte);
	
	@GetMapping("/alertes/liste/actives/{actif}")
	List<AlerteAux> obtenirListeActives(@RequestHeader("Authorization") String token, @PathVariable(name = "actif") Boolean actif);
	
	@GetMapping("/alertes/projet/{idProjet}/{idAuteur}")
	List<AlerteAux> obtenirAlertesParProjetAuteur(@RequestHeader("Authorization") String token,@PathVariable(name = "idProjet") Integer idProjet,@PathVariable(name = "idAuteur") Integer idAuteur);
	
	@GetMapping("/alertes/liste/actives/{actif}/{idAuteur}")
	List<AlerteAux> obtenirListeActivesAuteur(@RequestHeader("Authorization") String token,@PathVariable(name = "actif") Boolean actif,@PathVariable(name = "idAuteur") Integer idAuteur);
}
