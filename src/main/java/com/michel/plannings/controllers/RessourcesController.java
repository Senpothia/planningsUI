package com.michel.plannings.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.michel.plannings.contants.Constants;
import com.michel.plannings.models.Utilisateur;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.services.UserConnexion;

@Controller
public class RessourcesController {
	
	@Autowired
	private MicroServicePlannings microServicePlannnings;
	
	@Autowired
	private UserConnexion userConnexion;
	
	@GetMapping("/ressources/access")
	public String accueilProjets(Model model, HttpSession session) {
		
		Utilisateur utilisateur = userConnexion.obtenirUtilisateur(session, model);
		return Constants.testUser(utilisateur, Constants.ACCUEIL_RESSOURCES);
		
	}

}
