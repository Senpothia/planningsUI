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
import com.michel.plannings.models.Dependance;
import com.michel.plannings.models.GanttRow;
import com.michel.plannings.models.Login;
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
		if (projets.isEmpty()) {
			vide = true;
		}
		model.addAttribute("projets", projets);
		model.addAttribute("vide", vide);
		model.addAttribute("access", "1");
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}
	
	@GetMapping("/projets/voir/tous/{statut}")
	public String tousProjetsParStatut(@PathVariable(name = "statut") Boolean statut, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.projetsParStatut(token, statut);
		Boolean vide = false;
		if (projets.isEmpty()) {
			vide = true;
		}
		model.addAttribute("projets", projets);
		model.addAttribute("vide", vide);
		model.addAttribute("access", "1");
		model.addAttribute("statut", !statut);
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
		if (projets.isEmpty()) {
			vide = true;
		}
		model.addAttribute("projets", projets);
		model.addAttribute("access", "1");
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}

	@GetMapping("/projets/ressource/{id}/{statut}")
	public String mesProjetId(@PathVariable(name = "id") Integer id, @PathVariable(name = "statut") Boolean statut,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String role = utilisateur.getRole();
		String token = Constants.getToken(session);
		List<ProjetAux> projets = new ArrayList<>();
		List<ProjetAux> projetsTries = new ArrayList<>();
		if (role.equals("CPROD")) {
			projets = microServicePlannnings.projetsParChef(token, id);
		}

		if (role.equals("LABO")) {
			projets = microServicePlannnings.projetsTous(token);
		}

		if (role.equals("BE") || role.equals("RESBE")) {
			projets = microServicePlannnings.projetsParRessource(token, id, false);
		}

		for (ProjetAux p : projets) {
			
			if(p.getStatut() == statut) {
				
				projetsTries.add(p);
			}

		}

		Boolean vide = false;
		if (projets.isEmpty()) {
			vide = true;
		}
		model.addAttribute("statut", true);
		model.addAttribute("projets", projetsTries);
		model.addAttribute("access", "2");
		model.addAttribute("vide", vide);
		model.addAttribute("statut", !statut);
		return Constants.testUser(utilisateur, Constants.PROJETS);

	}

	@GetMapping("/projets/actifs")
	public String projetsActifs(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<ProjetAux> projets = microServicePlannnings.projetsTousActifs(token);
		Boolean vide = false;
		if (projets.isEmpty()) {
			vide = true;
		}
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
		if (ressources.isEmpty()) {
			model.addAttribute("vide", true);
		}
		;
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
		List<Dependance> dependances = microServicePlannnings.obtenirDependancesProjet(token, idProjet);

		if (!dependances.isEmpty()) {

			model.addAttribute("liaisons", true);
		}
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}

		model.addAttribute("tab", tab);
		model.addAttribute("supprimer", false);
		model.addAttribute("test", false);

		return Constants.testUser(utilisateur, "gantt");

	}

	@GetMapping("/projet/liaisons/supprimer/{projet}")
	public String supprimerLiaisons(@PathVariable(name = "projet") Integer idProjet, Model model, HttpSession session,
			Login login) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		Boolean test = userConnexion.confirmerUtilisateur(login, session);

		model.addAttribute("supprimer", true);
		model.addAttribute("test", false);
		model.addAttribute("login", login);

		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<Dependance> dependances = microServicePlannnings.obtenirDependancesProjet(token, idProjet);

		if (!dependances.isEmpty()) {

			model.addAttribute("liaisons", true);
		}
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}
		model.addAttribute("tab", tab);
		return Constants.testUser(utilisateur, "gantt");

	}

	@PostMapping("/projet/liaisons/supprimer/{projet}")
	public String traiterSuppressionsLiaisons(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session, Login login) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		Boolean test = userConnexion.confirmerUtilisateur(login, session);
		if (test) {
			microServicePlannnings.supprimerLiaisions(token, idProjet);
		}

		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<Dependance> dependances = microServicePlannnings.obtenirDependancesProjet(token, idProjet);

		if (!dependances.isEmpty()) {

			model.addAttribute("liaisons", true);
		}
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}

		model.addAttribute("tab", tab);
		model.addAttribute("supprimer", false);
		model.addAttribute("test", test);
		model.addAttribute("resultat", true);
		return Constants.testUser(utilisateur, "gantt");
	}

}
