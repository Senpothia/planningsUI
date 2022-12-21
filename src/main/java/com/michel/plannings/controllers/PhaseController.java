package com.michel.plannings.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.michel.plannings.models.FicheAux;
import com.michel.plannings.models.GanttRow;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.PhaseAux;
import com.michel.plannings.models.ProjetAux;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class PhaseController {

	@Autowired
	private MicroServicePlannings microServicePlannnings;

	@Autowired
	private UserConnexion userConnexion;

	@GetMapping("/projet/ajouter/phase/{projet}")
	public String ajouterPhaseProjet(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		System.out.println("ajouter phase");
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		List<UtilisateurAux> ressources = microServicePlannnings.toutesLesRessources(token);
		System.out.println("Taille ressourses: " + ressources.size());
		List<UtilisateurAux> ressourcesAssociees = microServicePlannnings.ressourcesParProjet(token, idProjet);
		System.out.println("Taille ressoursesAssociees: " + ressourcesAssociees.size());
		List<UtilisateurAux> copyListRessources = new ArrayList<>(ressources);
		System.out.println("Taille copie liste ressources: " + copyListRessources.size());

		if (!ressources.isEmpty()) {

			List<Integer> indices = new ArrayList<>();
			for (UtilisateurAux u1 : ressources) {

				Integer idRessource = u1.getId();
				for (UtilisateurAux u2 : ressourcesAssociees) {

					Integer idRassociee = u2.getId();
					if (idRassociee == idRessource) {

						indices.add(ressources.indexOf(u1));
					}
				}
			}

			List<UtilisateurAux> suppressions = new ArrayList<>();
			for (int i = 0; i < indices.size(); i++) {

				suppressions.add(ressources.get(i));

			}
			ressources.removeAll(suppressions);

		}
		Boolean affectationsTotales = false;
		if (ressources.isEmpty()) {

			affectationsTotales = true;
		}

		Boolean affectations = false;
		if (!ressourcesAssociees.isEmpty()) {
			affectations = true;
		}
		model.addAttribute("ressources", ressources);
		model.addAttribute("ressourcesAssociees", ressourcesAssociees);
		model.addAttribute("affectations", affectations);
		model.addAttribute("affectationsTotales", affectationsTotales);
		model.addAttribute("projet", projet);
		return Constants.testUser(utilisateur, Constants.SELECTION_RESSOURCE_POUR_PHASE);
	}

	@GetMapping("/projet/ajouter/phase/associer/{projet}/{ressource}")

	public String associerRessourceProjetPhase(@PathVariable(name = "projet") Integer idProjet,
			@PathVariable(name = "ressource") Integer idRessource, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(idRessource, token);
		model.addAttribute("ressource", ressource);
		model.addAttribute("projet", projet);
		model.addAttribute("phase", new PhaseAux());
		return Constants.testUser(utilisateur, Constants.CREATION_PHASE);

	}

	@PostMapping("/projet/ajouter/phase/associer/{projet}/{ressource}")
	public String enregistrerPhase(@PathVariable(name = "projet") Integer idProjet,
			@PathVariable(name = "ressource") Integer idRessource, Model model, HttpSession session, PhaseAux phase) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.creerPhase(token, phase, idProjet, idRessource);

		return Constants.testUser(utilisateur, "redirect:/projet/voir/" + idProjet);

	}

	@GetMapping("/projet/voir/phases/{projet}")
	public String associerRessourceProjetPhase(@PathVariable(name = "projet") Integer idProjet, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesParProjetId(token, idProjet);

		phases.remove(0);
		if (phases.isEmpty()) {
			model.addAttribute("vide", true);
		} else {
			model.addAttribute("vide", false);
		}

		ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		model.addAttribute("phases", phases);

		// Diagramme de Gantt

		// ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}
		model.addAttribute("tab", tab);

		// ***************************

		return Constants.testUser(utilisateur, Constants.PHASES);
	}

	@GetMapping("/phase/voir/{phase}")
	public String voirPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		model.addAttribute("suppression", false);
		return Constants.testUser(utilisateur, Constants.PHASE);
	}

	@GetMapping("/phase/modifier/{phase}")
	public String modifierPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		return Constants.testUser(utilisateur, Constants.MODIFIER_PHASE);
	}

	@PostMapping("/projet/modifier/phase/{phase}")
	public String modifierPhase(@PathVariable(name = "phase") Integer idPhase, Model model, HttpSession session,
			PhaseAux phase) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.modifierPhase(token, phase, idPhase);

		return Constants.testUser(utilisateur, "redirect:/phase/voir/" + idPhase);

	}

	@GetMapping("/phase/supprimer/{phase}")
	public String demandeSuppressionPhaseParId(@PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);

		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);
		model.addAttribute("supprimer", true);
		model.addAttribute("login", new Login());

		return Constants.testUser(utilisateur, Constants.PHASE);
	}

	@PostMapping("/supprimer/phase/{phase}")
	public String suppressionPhaseParId(@PathVariable(name = "phase") Integer idPhase, Login login, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		Boolean test = userConnexion.confirmerUtilisateur(login, session);
		if (test) {
			microServicePlannnings.supprimerPhase(idPhase, token);
		}
		model.addAttribute("test", test);
		return Constants.testUser(utilisateur, Constants.CONFIRMATION);
	}

	@GetMapping("/projets/liste/phases/ressource/{ressource}")
	public String listePhaseParId(@PathVariable(name = "ressource") Integer idRessource, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesParRessource(token, idRessource);
		Boolean vide = false;
		List<PhaseAux> zeros = new ArrayList<PhaseAux>();
		for (PhaseAux p : phases) {

			if (p.getNumero() == 0) {

				zeros.add(p);
			}

		}

		phases.removeAll(zeros);

		if (phases.isEmpty()) {

			vide = true;
		}

		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(idRessource, token);

		model.addAttribute("phases", phases);
		model.addAttribute("vide", vide);
		model.addAttribute("ressource", ressource);
		return Constants.testUser(utilisateur, Constants.PHASES_RESSOURCE);
	}

	@GetMapping("/phase/actives/liste/{active}")
	public String listePhaseActives(@PathVariable(name = "active") boolean active, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		List<PhaseAux> phases = microServicePlannnings.phasesActives(token, active);
		Boolean vide = false;
		if (phases.isEmpty()) {
			vide = true;
			model.addAttribute("phases", phases);
		} else {
			List<PhaseAux> phasesSans0 = new ArrayList<>();
			for (PhaseAux ph : phases) {

				if (ph.getNumero() != 0) {
					phasesSans0.add(ph);
				}
			}

			if (phasesSans0.isEmpty()) {
				vide = true;
			}
			model.addAttribute("phases", phasesSans0);
		}

		model.addAttribute("vide", vide);
		model.addAttribute("active", active);
		model.addAttribute("ressource", utilisateur);
		return Constants.testUser(utilisateur, Constants.PHASES_ACTIVES);
	}

	@GetMapping("/phase/changer/statut/{phase}/{active}")
	public String changerPhaseStatut(@PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "active") boolean active, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		microServicePlannnings.changerStatutPhase(token, idPhase, active);

		PhaseAux ph = microServicePlannnings.phaseParId(token, idPhase);
		Integer idProjet = ph.getIdProjet();
		return Constants.testUser(utilisateur, "redirect:/projet/voir/phases/" + idProjet);

	}

	@GetMapping("/phase/liaison/ajouter/{idPhase}")
	public String lierPhase(@PathVariable(name = "idPhase") Integer idPhase, Model model, HttpSession session) {
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		System.err.println("taille liste dépendances: " + phase.getDependances().size());
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);

		Integer idProjet = phase.getIdProjet();
		List<PhaseAux> phases = microServicePlannnings.phasesParProjetId(token, idProjet);

		phases.remove(0);
		if (phases.isEmpty()) {
			model.addAttribute("vide", true);
		} else {
			model.addAttribute("vide", false);
		}

		List<PhaseAux> copyPhasesProjet = new ArrayList<PhaseAux>(phases);

		for (PhaseAux p : phases) {

			if (p.getId() == idPhase) {

				copyPhasesProjet.remove(p);
			}
		}

		for (PhaseAux p : copyPhasesProjet) {

			if (phase.getDebut().isBefore(p.getFin())) {
				System.err.println("Conflit");
				p.setConflit(true);
			}
		}

		List<Dependance> dependances = microServicePlannnings.obtenirDependances(token, idPhase);
		System.err.println("Taille liste dependances: " + dependances.size());
		if (!dependances.isEmpty()) {

			System.out.println("Vérification des liaisons");
			for (PhaseAux p : copyPhasesProjet) {

				Integer id1 = p.getId();

				for (Dependance d : dependances) {

					if (id1 == d.getAntecedente()) {

						p.setLiaison(true);
					}
				}
			}
		}

		List<Dependance> antecedents = microServicePlannnings.obtenirAntecedents(token, idPhase);

		if (!antecedents.isEmpty()) {

			System.out.println("Vérification des antécedents");
			for (PhaseAux p : copyPhasesProjet) {

				Integer id1 = p.getId();

				for (Dependance a : antecedents) {

					if (id1 == a.getSuivante()) {

						p.setAntecedent(true);
					}
				}
			}
		}

		model.addAttribute("phases", copyPhasesProjet);

		// Diagramme de gantt

		// ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}
		model.addAttribute("tab", tab);

		// ***************************
		return Constants.testUser(utilisateur, Constants.LIER_PHASE);
	}

	@GetMapping("/phase/liaison/selection/{phase}/{dependance}/{statut}/{conflit}")
	public String selectionnerLiaisonPhase(@PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "dependance") Integer idDependance, @PathVariable(name = "statut") Boolean statut,
			@PathVariable(name = "conflit") Boolean conflit, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		if (!conflit) {

			microServicePlannnings.creerLiaison(token, idPhase, idDependance, statut);
			return Constants.testUser(utilisateur, "redirect:/phase/liaison/ajouter/" + idPhase);

		} else {

			return "redirect:/phase/liste/dependances/modifier/" + idPhase + "/" + idDependance + "/" + conflit;
		}

	}

	@GetMapping("/phase/liaison/modifier/{phase}")
	public String modifierPhaseLiaison(@PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		System.err.println("taille liste dépendances: " + phase.getDependances().size());
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		UtilisateurAux ressource = microServicePlannnings.obtenirRessourceParId(phase.getIdRessource(), token);
		model.addAttribute("phase", phase);
		model.addAttribute("projet", projet);
		model.addAttribute("ressource", ressource);

		Integer idProjet = phase.getIdProjet();
		List<PhaseAux> phases = microServicePlannnings.phasesParProjetId(token, idProjet);

		phases.remove(0);
		if (phases.isEmpty()) {
			model.addAttribute("vide", true);
		} else {
			model.addAttribute("vide", false);
		}

		List<PhaseAux> copyPhasesProjet = new ArrayList<PhaseAux>(phases);

		for (PhaseAux p : phases) {

			if (p.getId() == idPhase) {

				copyPhasesProjet.remove(p);
			}
		}

		for (PhaseAux p : copyPhasesProjet) {

			if (phase.getDebut().isBefore(p.getFin())) {
				System.err.println("Conflit");
				p.setConflit(true);
			}
		}
		List<Dependance> dependances = microServicePlannnings.obtenirDependances(token, idPhase);
		System.err.println("Taille liste dependances: " + dependances.size());
		if (!dependances.isEmpty()) {

			System.out.println("Vérification des liaisons");
			for (PhaseAux p : copyPhasesProjet) {

				Integer id1 = p.getId();

				for (Dependance d : dependances) {

					if (id1 == d.getAntecedente()) {

						p.setLiaison(true);
					}
				}
			}
		}

		// Diagramme de gantt

		// ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, idProjet);

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}
		model.addAttribute("tab", tab);

		// ***************************

		model.addAttribute("phases", copyPhasesProjet);
		return Constants.testUser(utilisateur, Constants.LIER_MODIFIER_PHASE);

	}

	@PostMapping("/phase/modifier/liaison/{phase}")
	public String modifierPhasePourLiaison(PhaseAux phase, @PathVariable(name = "phase") Integer idPhase, Model model,
			HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		LocalDateTime debut = LocalDateTime.parse(phase.getDateDebutString() + " " + "00:00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime fin = LocalDateTime.parse(phase.getDateFinString() + " " + "00:00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		if (fin.isBefore(debut)) {

			model.addAttribute("phase", idPhase);
			return "alerteDates";
		}

		microServicePlannnings.modifierPhasePourLiaison(token, phase, idPhase);

		return "redirect:/phase/liaison/ajouter/" + idPhase;
	}

	@GetMapping("/phase/liste/dependances/modifier/{phase}/{dependance}/{conflit}")
	public String modifierLiaisonDependance(@PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "dependance") Integer idDependance, @PathVariable(name = "conflit") Boolean conflit,
			Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);

		PhaseAux phase = microServicePlannnings.phaseParId(token, idPhase);
		PhaseAux dependance = microServicePlannnings.phaseParId(token, idDependance);
		dependance.setConflit(conflit);
		ProjetAux projet = microServicePlannnings.projetParId(token, phase.getIdProjet());
		List<PhaseAux> phases = new ArrayList<>();
		phases.add(dependance);
		model.addAttribute("phase", phase);
		model.addAttribute("phases", phases);
		model.addAttribute("ressource", utilisateur);
		model.addAttribute("projet", projet);
		model.addAttribute("dependance", dependance);
		if (conflit) {
			model.addAttribute("conflitDates", true);

		} else {

			model.addAttribute("conflitDates", false);
		}

		// Diagramme de gantt

		// ProjetAux projet = microServicePlannnings.projetParId(token, idProjet);
		model.addAttribute("projet", projet);
		List<GanttRow> ganttRows = microServicePlannnings.ganttProjetParId(token, phase.getIdProjet());

		GanttRow[] tab = new GanttRow[ganttRows.size() - 1];
		int i = 1;
		while (i < ganttRows.size()) {

			tab[i - 1] = ganttRows.get(i);

			i++;

		}
		model.addAttribute("tab", tab);

		// ***************************

		return Constants.testUser(utilisateur, Constants.LIAISON_MODIFIER_DEPENDANCE);
	}

	@PostMapping("/phase/modifier/liaison/{phase}/{dependance}")
	public String modifierDependancePourLiaison(PhaseAux dependance, @PathVariable(name = "phase") Integer idPhase,
			@PathVariable(name = "dependance") Integer idDependance, Model model, HttpSession session) {

		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		String token = Constants.getToken(session);
		LocalDateTime debut = LocalDateTime.parse(dependance.getDateDebutString() + " " + "00:00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime fin = LocalDateTime.parse(dependance.getDateFinString() + " " + "00:00:00",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		if (fin.isBefore(debut)) {

			model.addAttribute("phase", idPhase);
			return "alerteDates";
		}
		microServicePlannnings.modifierPhasePourLiaison(token, dependance, idDependance);

		return "redirect:/phase/liaison/ajouter/" + idPhase;
	}
}
