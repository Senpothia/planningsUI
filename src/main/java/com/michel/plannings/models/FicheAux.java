package com.michel.plannings.models;

import java.time.LocalDateTime;

public class FicheAux {

	private Integer id;
	private Integer numero;
	private LocalDateTime date;
	private String dateString;

	private String service;
	private String degre; // degré de gravité
	private String projet;
	private String code;
	private String produit;
	private String circonstance;
	private String observation; // description du symptome, phénomène observé
	private String document; // documents joints
	private String incidence; // conséquences
	private String solution; // proposition
	private String domaine; // électronique, mécanique
	private String objet; // n° de carte, pièce mécanique, organe affecté
	private String reponse;

	private Integer idAuteur;
	private String nomAuteur;
	private String anomalie;
	private Boolean statut; // ouverte, fermée
	private String statutString;
	private Integer niveau;
	private Integer idPhase;

	public FicheAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FicheAux(Integer id, Integer numero, LocalDateTime date, String dateString, String service, String degre,
			String projet, String code, String produit, String circonstance, String observation, String document,
			String incidence, String solution, String domaine, String objet, String reponse, Integer idAuteur,
			String nomAuteur, String anomalie, Boolean statut, String statutString, Integer niveau, Integer idPhase) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.dateString = dateString;
		this.service = service;
		this.degre = degre;
		this.projet = projet;
		this.code = code;
		this.produit = produit;
		this.circonstance = circonstance;
		this.observation = observation;
		this.document = document;
		this.incidence = incidence;
		this.solution = solution;
		this.domaine = domaine;
		this.objet = objet;
		this.reponse = reponse;
		this.idAuteur = idAuteur;
		this.nomAuteur = nomAuteur;
		this.anomalie = anomalie;
		this.statut = statut;
		this.statutString = statutString;
		this.niveau = niveau;
		this.idPhase = idPhase;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDegre() {
		return degre;
	}

	public void setDegre(String degre) {
		this.degre = degre;
	}

	public String getProjet() {
		return projet;
	}

	public void setProjet(String projet) {
		this.projet = projet;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProduit() {
		return produit;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public String getCirconstance() {
		return circonstance;
	}

	public void setCirconstance(String circonstance) {
		this.circonstance = circonstance;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getIncidence() {
		return incidence;
	}

	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Integer getIdAuteur() {
		return idAuteur;
	}

	public void setIdAuteur(Integer idAuteur) {
		this.idAuteur = idAuteur;
	}

	public String getAnomalie() {
		return anomalie;
	}

	public void setAnomalie(String anomalie) {
		this.anomalie = anomalie;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public Integer getNiveau() {
		return niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	public Integer getIdPhase() {
		return idPhase;
	}

	public void setIdPhase(Integer idPhase) {
		this.idPhase = idPhase;
	}

	@Override
	public String toString() {
		return "FicheAux [id=" + id + ", numero=" + numero + ", date=" + date + ", service=" + service + ", degre="
				+ degre + ", projet=" + projet + ", reponse=" + reponse + ", idAuteur=" + idAuteur + "]";
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	

	public String getNomAuteur() {
		return nomAuteur;
	}



	public void setNomAuteur(String nomAuteur) {
		this.nomAuteur = nomAuteur;
	}



	public String getStatutString() {
		return statutString;
	}

	public void setStatutString(String statutString) {
		this.statutString = statutString;
	}
	
	

}
