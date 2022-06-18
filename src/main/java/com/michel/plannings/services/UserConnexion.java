package com.michel.plannings.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.michel.plannings.models.UtilisateurAux;
import com.michel.plannings.proxy.MicroServicePlannings;
import com.michel.plannings.models.Login;
import com.michel.plannings.models.Utilisateur;

@Service
public class UserConnexion {

	@Autowired
	MicroServicePlannings microServicePlannings;

	public Utilisateur identifierUtilisateur(Login login, HttpSession session) {

	
		try {
			ResponseEntity<UtilisateurAux> userBody = microServicePlannings.generate(login);
			HttpStatus code = userBody.getStatusCode();
		
			UtilisateurAux userAux = userBody.getBody();
			
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setId(userAux.getId());
			utilisateur.setNom(userAux.getNom());
			utilisateur.setPrenom(userAux.getPrenom());
			utilisateur.setUsername(userAux.getUsername());
			utilisateur.setRole(userAux.getRole());

			String token = userAux.getToken();

			session.setAttribute("USER", utilisateur);
			session.setAttribute("TOKEN", token);

			return utilisateur;
		} catch (Exception e) {

			
			return null;
		}

	}

	public Utilisateur obtenirUtilisateur(HttpSession session, Model model) {

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("USER");
		if (utilisateur == null) {

			
			model.addAttribute("authentification", false);

		} else {

			
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("authentification", true);

		}

		return utilisateur;
	}

	public Boolean confirmerUtilisateur(Login login, HttpSession session) {

		try {
			ResponseEntity<UtilisateurAux> userBody = microServicePlannings.generate(login);
			HttpStatus code = userBody.getStatusCode();

			UtilisateurAux userAux = userBody.getBody();

			String tokenToBeTested = userAux.getToken();

			String tokenSession = (String) session.getAttribute("TOKEN");
			if (tokenToBeTested.equals(tokenSession)) {
				return true;
			} else {

				return false;

			}

		} catch (Exception e) {

		
			return false;
		}

	}

}
