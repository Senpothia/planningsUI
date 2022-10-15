package com.michel.plannings.models;

import java.time.LocalDateTime;

public class NoteAux {

	private Integer id;
	private Integer numero;
	private LocalDateTime date;
	private String stringDate;
	private String nomAuteur;
	private String texte;
	private Integer idAuteur;
	private Integer idSource; // source: projet ou phase
	private String nomProjet;
	private String nomPhase;
	private String nomSerie;
	private String nomSuite;
	private Boolean active;
	private String activeString;

	public NoteAux() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoteAux(Integer id, Integer numero, LocalDateTime date, String stringDate, String nomAuteur, String texte,
			Integer idAuteur, Integer idSource, String nomProjet, String nomPhase, String nomSerie, String nomSuite,
			Boolean active, String activeString) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.stringDate = stringDate;
		this.nomAuteur = nomAuteur;
		this.texte = texte;
		this.idAuteur = idAuteur;
		this.idSource = idSource;
		this.nomProjet = nomProjet;
		this.nomPhase = nomPhase;
		this.nomSerie = nomSerie;
		this.nomSuite = nomSuite;
		this.active = active;
		this.activeString = activeString;
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

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Integer getIdAuteur() {
		return idAuteur;
	}

	public void setIdAuteur(Integer idAuteur) {
		this.idAuteur = idAuteur;
	}

	public Integer getIdSource() {
		return idSource;
	}

	public void setIdSource(Integer idSource) {
		this.idSource = idSource;
	}

	public String getNomAuteur() {
		return nomAuteur;
	}

	public void setNomAuteur(String nomAuteur) {
		this.nomAuteur = nomAuteur;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public String getNomPhase() {
		return nomPhase;
	}

	public void setNomPhase(String nomPhase) {
		this.nomPhase = nomPhase;
	}

	public String getNomSerie() {
		return nomSerie;
	}

	public void setNomSerie(String nomSerie) {
		this.nomSerie = nomSerie;
	}

	public String getNomSuite() {
		return nomSuite;
	}

	public void setNomSuite(String nomSuite) {
		this.nomSuite = nomSuite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getActiveString() {
		return activeString;
	}

	public void setActiveString(String activeString) {
		this.activeString = activeString;
	}

}
