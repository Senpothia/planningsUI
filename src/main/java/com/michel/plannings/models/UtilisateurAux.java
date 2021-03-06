package com.michel.plannings.models;

import java.util.List;

public class UtilisateurAux {

	private Integer id;
	private String nom;
	private String prenom;
	private String nomString;
	private String type; // interne CDVI, pretataire externe
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private boolean autorise;
	private String statutString;
	private String token;
	private String role;

	public UtilisateurAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UtilisateurAux(Integer id, String nom, String prenom, String nomString, String type, String email,
			String username, String password, boolean enabled, boolean autorise, String statutString, String token,
			String role) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.nomString = nomString;
		this.type = type;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.autorise = autorise;
		this.statutString = statutString;
		this.token = token;
		this.role = role;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatutString() {
		return statutString;
	}

	public void setStatutString(String statutString) {
		this.statutString = statutString;
	}

	public String getNomString() {
		return nomString;
	}

	public void setNomString(String nomString) {
		this.nomString = nomString;
	}



	public boolean isAutorise() {
		return autorise;
	}



	public void setAutorise(boolean autorise) {
		this.autorise = autorise;
	}
	
	

}
