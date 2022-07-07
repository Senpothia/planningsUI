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
	public static final String MODIFIER_FICHE = "modifierfiche";
	public static final String CONFIRMATION = "Confirmation";
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
		
		String convertedDate =  date.format(formatter);
		return convertedDate;
	}
	
	

}
