package com.michel.plannings.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.TacheAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class AgendaController {
	
	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;
	
	@GetMapping("/agenda/access")
	private String accessAgenda( Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		//List<TacheAux> taches = microServicePlannnings.obtenirTachesParRessourceId(token, utilisateur.getId());
		List<TacheAux> taches = microServicePlannnings.obtenirTachesParRessourceIdEtStatut(token, utilisateur.getId(), true);
		Boolean vide = true;
		if(!taches.isEmpty()) {
			vide = false;
		}
		model.addAttribute("taches", taches);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("vide", vide);
		model.addAttribute("statut", false);
		return Constants.testUser(utilisateur, Constants.AGENDA);
	}
	
	@GetMapping("/tache/ajouter/{idUtilisateur}")
	public String creerTache (@PathVariable(name= "idUtilisateur") Integer idUtilisateur,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		model.addAttribute("tache", new TacheAux());
		return Constants.testUser(utilisateur, Constants.CREER_TACHE);
	}
	
	
	@PostMapping("/agenda/tache/enregistrer")
	public String enregistrerTache(TacheAux tache,  Model model,
			HttpSession session ) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux u = new UtilisateurAux(utilisateur.getId(), null, null, null, null, null, null, null, false, false, null, null, null, null);
		tache.setRessource(u);
		microServicePlannnings.enregistrerTache(token, tache);
		return "redirect:/agenda/access";
	}
	
	
	@GetMapping("/tache/voir/{idTache}")
	public String voirTache(@PathVariable(name= "idTache") Integer idTache,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		TacheAux  tache = microServicePlannnings.obtenirTacheParId(token, idTache);
		model.addAttribute("tache", tache);
		return Constants.testUser(utilisateur, Constants.TACHE);
	}
	
	
	@GetMapping("/taches/{statut}")
	public String voirTacheParStatut(@PathVariable(name= "statut") Boolean statut,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<TacheAux> taches = microServicePlannnings.obtenirTachesParRessourceIdEtStatut(token, utilisateur.getId(), statut);
		Boolean vide = true;
		if(!taches.isEmpty()) {
			vide = false;
		}
		model.addAttribute("taches", taches);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("vide", vide);
		model.addAttribute("statut", !statut);
		return Constants.testUser(utilisateur, Constants.AGENDA);
	}
	
	
	@GetMapping("/tache/changer/statut/{idTache}/{statut}")
	public String chagerStatutTache(@PathVariable(name= "idTache") Integer idTache,@PathVariable(name= "statut") Boolean statut,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.tacheChangerStatut(token, idTache);

		return Constants.testUser(utilisateur, "redirect:/tache/voir/" + idTache);
	}
	
	@GetMapping("/tache/changer/statut/liste/{idTache}")
	public String chagerStatutTacheListe(@PathVariable(name= "idTache") Integer idTache,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.tacheChangerStatut(token, idTache);

		return Constants.testUser(utilisateur, "redirect:/agenda/access");
	}
	
	@GetMapping("/tache/supprimer/{idTache}")
	public String supprimerTache(@PathVariable(name= "idTache") Integer idTache,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.supprimerTacheParId(token, idTache);
		return Constants.testUser(utilisateur, "redirect:/agenda/access");
	}
	
	
	@GetMapping("/tache/modifier/{idTache}")
	public String accessModificationTache(@PathVariable(name= "idTache") Integer idTache,  Model model,
			HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		TacheAux tache = microServicePlannnings.obtenirTacheParId(token, idTache);
		model.addAttribute("tache", tache);
		return Constants.testUser(utilisateur, Constants.MODIFIER_TACHE);
		
	}
	
	
	@PostMapping("/tache/modifier/{idTache}")
	public String modifierTache(@PathVariable(name= "idTache") Integer idTache,  Model model,
			HttpSession session, TacheAux tache) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		tache.setId(idTache);
	
		microServicePlannnings.modifierTache(token, tache);
		return Constants.testUser(utilisateur, "redirect:/tache/voir/" + idTache);
	}

}
