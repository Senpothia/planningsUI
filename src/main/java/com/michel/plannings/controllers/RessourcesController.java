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
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.models.forms.FormCompte;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class RessourcesController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/ressources/access")
	public String accueilRessources(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.testUser(utilisateur, Constants.ACCUEIL_RESSOURCES);

	}

	@GetMapping("/ressources/liste")
	public String ressourcesListe(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		model.addAttribute("ressources", ressources);
		return Constants.testUser(utilisateur, Constants.LISTE_RESSOURCES);

	}

	@GetMapping("/ressources/liste/statut/{id}")
	public String ressourcesChangeStatutListe(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.ChangerStatutRessourcesId(token, id);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		model.addAttribute("ressources", ressources);
		return Constants.testUser(utilisateur, Constants.LISTE_RESSOURCES);

	}

	// non utilisée - Utiliser ressourcesAffecterProjets()
	@GetMapping("/ressources/affecter/{id}")
	public String ressourcesAffecter(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(id, token);
		List<ProjetAux> projets = microServicePlannnings.projetsParRessourceSansAffectation(token, id);
		model.addAttribute("projets", projets);
		model.addAttribute("ressource", ressource);
		return Constants.testUser(utilisateur, Constants.AFFECTATIONS);

	} 
	/*
	 * Permet d'obtenir la liste complète des projets actifs et de distinguer les
	 * projets affectés à la ressource choisie dans la templete ressource
	 */

	@GetMapping("/ressources/affecter/projet/{id}")
	public String ressourcesAffecterProjets(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(id, token);
		List<ProjetAux> projets = microServicePlannnings.projetsAvecEtatImplication(token, id);
		model.addAttribute("projets", projets);
		model.addAttribute("ressource", ressource);
		return Constants.testUser(utilisateur, Constants.AFFECTATIONS);

	}

	@GetMapping("/ressources/affecter/selection/projet/{projet}/{ressource}")
	public String affecterRessourceProjet(@PathVariable(name = "projet") Integer projet,
			@PathVariable(name = "ressource") Integer ressource, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.affecterProjetRessource(projet, ressource, token);
		UtilisateurAux user = microServicePlannnings.obtenirRessourceParId(ressource, token);
		List<ProjetAux> projets = microServicePlannnings.projetsParRessourceSansAffectation(token, ressource);
		model.addAttribute("projets", projets);
		model.addAttribute("ressource", user);
		return Constants.testUser(utilisateur, Constants.AFFECTATIONS);

	}

	@GetMapping("/ressources/affection/retirer/projet/{projet}/{ressource}")
	public String retirerAffectationProjet(@PathVariable(name = "projet") Integer projet,
			@PathVariable(name = "ressource") Integer ressource, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		microServicePlannnings.retirerAffectation(ressource, projet, token);
		UtilisateurAux user = microServicePlannnings.obtenirRessourceParId(ressource, token);
		List<ProjetAux> projets = microServicePlannnings.projetsParRessourceSansAffectation(token, ressource);
		model.addAttribute("projets", projets);
		model.addAttribute("ressource", user);

		return Constants.testUser(utilisateur, Constants.AFFECTATIONS);

	}

	@GetMapping("/ressources/affection/ajouter/projet/{projet}/{ressource}")
	public String ajouterAffectationProjet(@PathVariable(name = "projet") Integer projet,
			@PathVariable(name = "ressource") Integer ressource, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.ajouterAffectation(ressource, projet, token);
		UtilisateurAux user = microServicePlannnings.obtenirRessourceParId(ressource, token);
		List<ProjetAux> projets = microServicePlannnings.projetsParRessourceSansAffectation(token, ressource);
		model.addAttribute("projets", projets);
		model.addAttribute("ressource", user);
		return Constants.testUser(utilisateur, Constants.AFFECTATIONS);

	}

	/*
	 * Accès formumaire de création d'une ressource
	 * 
	 */

	@GetMapping("/ressources/ajouter")
	public String ajouterRessource(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		FormCompte formCompte = new FormCompte();
		model.addAttribute("formCompte", formCompte);
		return Constants.testUser(utilisateur, Constants.CREATION_RESSOURCE);

	}
	/*
	 * Enregistrement d'une ressource
	 * 
	 */

	@PostMapping("/ressources/ajouter")
	public String creationCompte(Model model, FormCompte formCompte, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux utilisateurAux = new UtilisateurAux();
		utilisateurAux.setPrenom(formCompte.getPrenom());
		utilisateurAux.setNom(formCompte.getNom());
		utilisateurAux.setToken(formCompte.getPassword());
		utilisateurAux.setUsername(formCompte.getUsername());
		utilisateurAux.setType(formCompte.getType());
		utilisateurAux.setRole(formCompte.getRole());
		microServicePlannnings.ajouterRessource(utilisateurAux, token);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		model.addAttribute("ressources", ressources);
		return Constants.testUser(utilisateur, Constants.LISTE_RESSOURCES);
	}

	@GetMapping("/ressources/voir/{id}/{masque}")
	public String voirRessource(@PathVariable(name = "id") Integer id, @PathVariable(name = "masque") Boolean masque,
			Model model, HttpSession session) {

	
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(id, token);
		model.addAttribute("ressource", ressource);
		List<ProjetAux> projets = microServicePlannnings.projetsParRessource(token, id, masque);
		Boolean noInactifs = true;
		if (!masque) {
			noInactifs = false;
			for (ProjetAux p : projets) {

				if (!p.getStatut()) {
					
					noInactifs = true;

				}
			}
		}

		if (projets.isEmpty()) {
			model.addAttribute("vide", true);
		}
		model.addAttribute("projets", projets);
		model.addAttribute("masque", masque);
		model.addAttribute("noInactifs", noInactifs);
		return Constants.testUser(utilisateur, Constants.RESSOURCE);

	}
	
	@GetMapping("/ressources/projet/retirer/{id}/{projet}")
	public String retirerRessourceProjet(@PathVariable(name = "id") Integer idRessource, @PathVariable(name = "projet") Integer IdProjet,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.retirerAffectation(idRessource, IdProjet, token);
		List<UtilisateurAux> ressources = microServicePlannnings.ressourcesParProjet(token, IdProjet);
		ProjetAux projet = microServicePlannnings.projetParId(token, IdProjet);
		model.addAttribute("ressources", ressources);
		model.addAttribute("projet", projet);
		List<UtilisateurAux> ressourcesDisponibles = microServicePlannnings.ressourcesDispoParProjet(token, IdProjet);
		model.addAttribute("ressourcesDispos", ressourcesDisponibles);
		
		return Constants.testUser(utilisateur, Constants.AJOUTER_RESSOURCES_PROJET);
	}
	
	@GetMapping("/ressources/projet/ajouter/{ressource}/{projet}")
	public String ajouterRessourceProjet(@PathVariable(name = "ressource") Integer idRessource, @PathVariable(name = "projet") Integer IdProjet,
			Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.affecterProjetRessource(IdProjet, idRessource, token);
		List<UtilisateurAux> ressources = microServicePlannnings.ressourcesParProjet(token, IdProjet);
		ProjetAux projet = microServicePlannnings.projetParId(token, IdProjet);
		model.addAttribute("ressources", ressources);
		model.addAttribute("projet", projet);
		List<UtilisateurAux> ressourcesDisponibles = microServicePlannnings.ressourcesDispoParProjet(token, IdProjet);
		model.addAttribute("ressourcesDispos", ressourcesDisponibles);
		
		return Constants.testUser(utilisateur, Constants.AJOUTER_RESSOURCES_PROJET);
		
	}
	
	@GetMapping("/ressources/liste/visiteur")
	public String listeVisiteurs(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<UtilisateurAux> ressources = microServicePlannnings.toutsLesVisiteurs(token);
		Boolean vide = false;
		if (ressources.isEmpty()) { vide = true;}
		model.addAttribute("ressources", ressources);
		model.addAttribute("visiteur", true);
		model.addAttribute("vide", vide);
		return Constants.testUser(utilisateur, Constants.LISTE_VISITEURS);

	}
	
	@GetMapping("/ressources/liste/visiteur/statut/{id}")
	public String visiteurChangeStatutListe(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.ChangerStatutRessourcesId(token, id);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		model.addAttribute("ressources", ressources);
		model.addAttribute("visiteur", true);
		return Constants.testUser(utilisateur, "redirect:/ressources/liste/visiteur ");

	}
	
	@GetMapping("/ressources/modifier/droits/{visiteur}/{id}")
	public String ressourcesChangeDroitsListe(@PathVariable(name = "visiteur") Boolean visiteur, @PathVariable(name = "id") Integer id, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(id, token);
		model.addAttribute("ressource", ressource);
		model.addAttribute("visiteur", visiteur);
		return Constants.testUser(utilisateur, Constants.MODIFIER_DROITS);

	}
	
	@PostMapping("/ressources/modifier/droits/{visiteur}/{id}")
	public String enregistrerDroitsRessource(@PathVariable(name = "visiteur") Boolean visiteur, @PathVariable(name = "id") Integer id, Model model, HttpSession session, UtilisateurAux ressource) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.enregistrerDroitsRessource(id, token, ressource);
		if(visiteur) {
			return Constants.testUser(utilisateur, "redirect:/ressources/liste/visiteur");
			}else {
				
				return Constants.testUser(utilisateur, "redirect:/ressources/gerer/droits");
				//return Constants.testUser(utilisateur, "ok");
			}
		
	}
	
	@GetMapping("/ressources/gerer/droits")
	public String gererDroitsUtilisateurs(Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<UtilisateurAux> ressources = microServicePlannnings.toutsLesUtilisateurs(token);
		model.addAttribute("ressources", ressources);
		model.addAttribute("visiteur", false);
		return Constants.testUser(utilisateur, Constants.DROITS);

	}
		

}
