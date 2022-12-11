package com.michel.plannings.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.GanttRow;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class ProjetController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projets/access")
	public String accueilProjets(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.testUser(utilisateur, Constants.ACCUEIL_PROJETS);

	}

	@GetMapping("/projets/creation")
	public String creerProjets(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		model.addAttribute("projetAux", new ProjetAux());
		return Constants.testUser(utilisateur, Constants.CREATION_PROJET);

	}

	@PostMapping("/projets/creation") // Enregistrement des éléments de création d'une qualification
	public String enregistrerProjet(Model model, HttpSession session, ProjetAux projetAux) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		if (utilisateur == null) {
			return "redirect:/connexion";
		} else {

			String token = Constants.getToken(session);
			projetAux.setChefId(utilisateur.getId());
			microServicePlannnings.creerProjet(token, projetAux);
			return Constants.ACCUEIL_PROJETS;

		}

	} 
	
	@GetMapping("/projets/voir/tous")
	public String tousProjets(Model model, HttpSession session) {
		
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.projetsTous(token);
		Boolean vide = false;
		if(projets.isEmpty()) {vide = true;}
		model.addAttribute("projets", projets);
		model.addAttribute("vide", vide);
		model.addAttribute("access", "1");
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}
	
	@GetMapping("/projet/voir/{id}")
	public String voirProjetId(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, id);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.PROJET);
		
	}
	
	@GetMapping("/projet/page/statut/{id}")
	public String changerStatutProjetPage(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.modifierStatutProjet(id, token);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.PROJET);
		
	}
	
	@GetMapping("/projet/liste/statut/{id}")
	public String changerStatutProjetListe(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.modifierStatutProjetListe(id, token);
		Boolean vide = false;
		if(projets.isEmpty()) {vide = true;}
		model.addAttribute("projets", projets);
		model.addAttribute("access", "1");
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.PROJETS);
		
	}
	
	@GetMapping("/projets/ressource/{id}")
	public String mesProjetId(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String role = utilisateur.getRole();
		String token = Constants.getToken(session);
		List<ProjetAux> projets = new ArrayList<>();
		if(role.equals("CPROD")) {
			projets = microServicePlannnings.projetsParChef(token, id);
		}
		
		if(role.equals("LABO")) {
			projets = microServicePlannnings.projetsTous(token);
		}
		
		if(role.equals("BE") || role.equals("RESBE")) {
			projets = microServicePlannnings.projetsParRessource(token, id, false);
		}
		
		Boolean vide = false;
		if(projets.isEmpty()) {vide = true;}
		model.addAttribute("projets", projets);
		model.addAttribute("access", "2");
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.PROJETS);
		
	}
	
	@GetMapping("/projets/actifs")
	public String projetsActifs(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.projetsTousActifs(token);
		Boolean vide = false;
		if(projets.isEmpty()) {vide = true;}
		model.addAttribute("projets", projets);
		model.addAttribute("access", "3");
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}
	
	@GetMapping("/projet/voir/ressources/{id}")
	public String projetConsuterRessources(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<UtilisateurAux> ressources = microServicePlannnings.ressourcesParProjet(token, id);
		if(ressources.isEmpty()) {model.addAttribute("vide", true);};
		ProjetAux projet = microServicePlannnings.projetParId(token, id);
		model.addAttribute("ressources", ressources);
		model.addAttribute("projet", projet);
 		return Constants.testUser(utilisateur, Constants.LISTE_RESSOURCES_PROJET);
	}
	
	
	@GetMapping("/projet/ajouter/ressources/{id}")
	public String projetAjouterRessources(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<UtilisateurAux> ressources = microServicePlannnings.ressourcesParProjet(token, id);
		ProjetAux projet = microServicePlannnings.projetParId(token, id);
		model.addAttribute("ressources", ressources);
		model.addAttribute("projet", projet);
		
		List<UtilisateurAux> ressourcesDisponibles = microServicePlannnings.ressourcesDispoParProjet(token, id);
		model.addAttribute("ressourcesDispos", ressourcesDisponibles);
		
		return Constants.testUser(utilisateur, Constants.AJOUTER_RESSOURCES_PROJET);
		
	}
	
	
	@GetMapping("/projet/gantt/{id}")
	public String projetGantt(@PathVariable(name = "id") Integer idProjet, Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		
		GanttRow r1 = new GanttRow("t1", "Tache1", "xxx1 ", "2022-11-10", "2022-11-12", 0, 0, null);
		GanttRow r2 = new GanttRow("t2", "Tache1", "xxx2 ","2022-11-12", "2022-11-16", 0, 0, null);
		GanttRow r3 = new GanttRow("t3", "Tache3", "xxx3 ","2022-11-16", "2022-11-21", 0, 0, null);
		GanttRow r4 = new GanttRow("t4", "Tache4", "xxx4 ","2022-11-21", "2022-11-25", 0, 0, null);
		GanttRow r5 = new GanttRow("t5", "Tache5", "xxx ","2022-11-25", "2022-11-28", 0, 0, null);
		GanttRow r6 = new GanttRow("t6", "Tache6", "xxx ","2022-11-28", "2022-12-3", 0, 0, null);
		GanttRow r7 = new GanttRow("t7", "Tache7", "xxx ","2022-12-3", "2022-12-7", 0, 0, null);
		GanttRow r8 = new GanttRow("t8", "Tache8", "xxx ","2022-12-7", "2022-12-12", 0, 0, null);
		GanttRow r9 = new GanttRow("t9", "Tache9","xxx ", "2022-12-12", "2022-12-15", 0, 0, null);
		GanttRow r10 = new GanttRow("t10", "Tache10", "xxx ","2022-12-15", "2022-12-17", 0, 0, null);
		GanttRow r11 = new GanttRow("t11", "Tache11", "xxx ","2022-12-17", "2022-12-24", 0, 0, null);
		GanttRow r12 = new GanttRow("t12", "Tache12", "xxx ","2022-12-24", "2022-12-28", 0, 0, null);
		GanttRow r13 = new GanttRow("t13", "Tache13", "xxx ","2022-12-28", "2023-1-3", 0, 0, null);
		GanttRow r14 = new GanttRow("t14", "Tache14", "xxx ","2023-1-3", "2023-1-29", 0, 0, null);
		
		GanttRow [] tab = {r1, r2, r3, r4, r5,r6,r7, r8, r9, r10, r11, r12, r13, r14};
		String[] liste = {"AAAA", "BBBBB"};
		model.addAttribute("tab", tab);
		model.addAttribute("liste", liste);
		//return Constants.testUser(utilisateur, "inlineJs");
		return Constants.testUser(utilisateur, "gantt3");
		//return Constants.testUser(utilisateur, "gantt");
		
	}
}
