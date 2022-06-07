package com.michel.plannings.contants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import com.michel.plannings.models.Utilisateur;

public class Constants {

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
	
	

}
