package com.michel.plannings.contants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import com.michel.plannings.models.Utilisateur;

public class Constants {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static final String PAGE_ACCUEIL = "accueil";
	public static final String AIDE = "aide";
	public static final String PRESENTATION = "presentation";
	public static final String CONNEXION = "connexion";
	public static final String CREATION_COMPTE = "creerCompte";
	public static final String ESPACE_PERSONEL = "espace";
	public static final String MODIFIER_COMPTE = "modifierCompte";
	public static final String ACCUEIL_PROJETS = "accueil_projets";
	public static final String ACCUEIL_RESSOURCES = "accueil_ressources";
	public static final String ACCUEIL_FICHES = "accueil_fiches";
	public static final String CREATION_PROJET = "creerProjet";
	public static final String PROJETS = "projets";
	public static final String PROJET = "projet";
	public static final String LISTE_RESSOURCES = "ressources";
	public static final String AFFECTATIONS = "affectations";
	public static final String CREATION_RESSOURCE = "creerRessource";
	public static final String RESSOURCE = "ressource";
	public static final String LISTE_RESSOURCES_PROJET = "ressourcesParProjet";
	public static final String AJOUTER_RESSOURCES_PROJET = "ajouterRessourceParProjet";
	public static final String SELECTION_RESSOURCE = "selectionRessource";
	public static final String CREATION_PHASE = "creerPhase";
	public static final String PHASES = "phases";
	public static final String PHASE = "phase";
	public static final String MODIFIER_PHASE = "modifierPhase";
	public static final String CREATION_FICHE = "creerFiche";
	public static final String LISTE_FICHES = "fichesParPhases";
	public static final String FICHE = "fiche";
	public static final String FICHES_PROJET = "fichesParProjet";
	public static final String FICHES_RESSOURCE = "fichesParRessource";
	public static final String MODIFIER_FICHE = "modifierFiche";
	public static final String CONFIRMATION = "confirmation";
	public static final String FICHES_SPONTANEES = "fichesSpontanees";
	public static final String LISTE_VISITEURS = "visiteurs";
	public static final String MODIFIER_DROITS = "modifierDroits";
	public static final String DROITS = "droits";
	public static final String ALERTE_BLOCAGE = "alerteBlocage";
	public static final String FICHES_RESSOURCE_PROJET = "fichesParRessourceSurProjet";
	public static final String CREATION_PHASE_RESSOURCE_SUR_PROJET = "creerPhasePourRessourceDansProjet";
	public static final String PHASES_RESSOURCE = "phasesParRessource";
	public static final String PHASES_ACTIVES = "phasesActives";
	public static final String FICHES_ACTIVES = "fichesParStatut";
	public static final String CREATION_FICHE_SPONTANEE = "creerFicheSpontanee";
	public static final String CREATION_FICHE_SPONTANEE_PROJET = "creerFicheSpontaneeProjet";
	public static final String SELECTION_RESSOURCE_POUR_PHASE = "selectionRessourcePourPhase";
	public static final String CREATION_NOTE_PROJET = "creerNoteProjet";
	public static final String NOTES_PROJET = "notesProjet";
	public static final String NOTE_PROJET = "noteProjet";
	public static final String MODIFIER_NOTE_PROJET = "modifierNoteProjet";

	public static final String CREATION_NOTE_PHASE = "creerNotePhase";
	public static final String NOTES_PHASE = "notesPhase";
	public static final String NOTE_PHASE = "notePhase";
	public static final String MODIFIER_NOTE_PHASE = "modifierNotePhase";

	public static final String CREATION_ALERTE = "creerAlerte";
	public static final String PROJET_ALERTES = "alertesProjet";
	public static final String ALERTE = "alerte";
	public static final String MODIFIER_ALERTE = "modifierAlerte";
	public static final String ALERTES_STATUT = "alertesStatut";
	public static final String HISTORIQUES_PROJET = "historiquesProjet";

	public static final String CREATION_SERIE = "creerHistoriqueProjet";
	public static final String CREATION_SUITE = "creerHistoriquePhase";
	public static final String NOTES_SERIE = "notesProjetHistorique";
	public static final String CREATION_NOTE_SERIE = "creerNoteSerie";

	public static final String HISTORIQUES_PHASE = "historiquesPhase";
	public static final String NOTES_SUITE = "notesPhaseHistorique";

	public static final String CREATION_NOTE_SUITE = "creerNoteSuite";

	public static final String AGENDA = "agenda";

	public static final String CREER_TACHE = "creerTache";

	public static final String TACHE = "tache";

	public static final String MODIFIER_TACHE = "modifierTache";

	public static final String LIER_PHASE = "lier";

	public static final String LIER_MODIFIER_PHASE = "liaisonModifierPhase";

	public static final String LIAISON_MODIFIER_DEPENDANCE = "liaisonModifierDependance";

	public static String testUser(Utilisateur utilisateur, String template) {

		if (utilisateur == null) {

			return "redirect:/connexion";

		} else

			return template;
	}

	public static String getToken(HttpSession session) {

		String token = (String) session.getAttribute("TOKEN");
		token = "Bearer " + token;
		return token;
	}

	public static String convertDateToString(LocalDateTime date) {

		String convertedDate = date.format(formatter);
		return convertedDate;
	}

}
