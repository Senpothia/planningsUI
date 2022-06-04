package com.michel.plannings.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.michel.plannings.models.Login;
import com.michel.plannings.models.UtilisateurAux;


//@FeignClient(name = "plannings", url = "http://46.105.35.7:8103")
@FeignClient(name = "plannings", url = "http://localhost:8103/")
public interface MicroServicePlannings extends ProxyGlobal{
	
/*
		@PutMapping("/modifier/compte/{id}")
		public void modifierCompte(@PathVariable Integer id, @RequestHeader("Authorization") String token,
				@RequestBody UtilisateurAux utilisateurAux);

		@PostMapping("/connexion")
		public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);

		@PostMapping("/compte")
		public void creerCompte(@RequestBody UtilisateurAux user);

		@PostMapping("/save/qualification") // Enregistrement d'une qualification
		public void saveQualification(@RequestHeader("Authorization") String token, FormQualif formQualif);

		@PostMapping("/save/procedure") // Enregistrement d'une procédure
		public void saveProcedure(@RequestHeader("Authorization") String token,FormProcedure formProcedure);

		@GetMapping("/private/domaines") // récupération de la liste des domaines
		public List<String> tousLesDomaines(@RequestHeader("Authorization") String token);

		@GetMapping("/private/qualifications") // récupération de la liste de toutes les qualifications
		public List<QualificationAux> toutesLesQualifications(@RequestHeader("Authorization") String token);

		@GetMapping("/private/historique/{id}") // récupération de la liste de toutes les qualifications par utilisateur
		public List<QualificationAux> mesQualifications(@RequestHeader("Authorization") String token, @PathVariable(name = "id") Integer id);

		@GetMapping("/private/qualifications/{id}") // récupération de la liste de toutes les qualifications en cours par
													// utilisateur
		public List<QualificationAux> mesQualificationsEnCours(@RequestHeader("Authorization") String token,@PathVariable(name = "id") Integer id);

		@GetMapping("/private/qualification/{id}") // récupération de la liste de toutes les qualifications en cours par											// utilisateur
		public QualificationAux obtenirQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@GetMapping("/private/procedures") // Récupérer la liste des procédures
		public List<ProcedureAux> obtenirProcedures(@RequestHeader("Authorization") String token);

		@GetMapping("/private/liste/domaines") // Récupérer la liste des domaines
		public List<DomaineAux> obtenirDomaines(@RequestHeader("Authorization") String token);

		@GetMapping("/private/liste/domaine/{id}") // Récupérer une liste de procédure pour un domaine
													// id = identifiant du domaine concerné
		public List<ProcedureAux> obtenirProceduresParDomaine(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@PostMapping("/essai/ajouter/procedure/{id}/{qualification}/{idUser}")
		public void ajouterProcedure(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id // id = identifiant procedure
				, @PathVariable(name = "qualification") Integer qualification // qualification = numéro de qualification
				, @PathVariable(name = "idUser") Integer idUser); // utilisateur = identifiant utilisateur

		// Récuperer l'ensemble des procédure sélectionnées pour une qualification
		@PostMapping("/private/liste/procedure/selection") // donnée et un domaine donné
		public List<Integer> obtenirSelectionProcedure(
				@RequestHeader("Authorization") String token,
				Groupe groupe); // Qualification = numéro de la qualification

		@GetMapping("/private/liste/essais/{id}")
		public List<EssaiAux> obtenirEssaisParQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@PostMapping("private/echantillon/save") // Enregistrement d'une procédure
		public void saveEchantillon(
				@RequestHeader("Authorization") String token,
				FormEchantillon formEchantillon);

		@GetMapping("/private/echantillon/voir/{id}")
		public List<EchantillonAux> obtenirEchantillonsParQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@GetMapping("/private/echantillon/desactiver/{id}/{qualification}")
		public void desactiverEchantillon(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id,
				@PathVariable(name = "qualification") Integer qualification);

		@GetMapping("/private/echantillon/voir")
		public List<EchantillonAux> obtenirEchantillonsParQualification2(@RequestParam(name = "id") Integer id);

		@GetMapping("/private/echantillon/activer/{id}/{qualification}")
		public void activerEchantillon(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id,
				@PathVariable(name = "qualification") Integer qualification);

		// @GetMapping("/private/sequences/voir/{id}/{num}/{domaine}")
		@GetMapping("/private/sequences/voir/{id}/{num}")
		public List<SequenceAux> obtenirSequencesParEssai(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id,
				@PathVariable(name = "num") Integer num);
		// @PathVariable(name="domaine") String domaine);

		@GetMapping("/private/essai/{num}")
		public EssaiAux obtenirEssaiParNumero(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "num") Integer num);

		@GetMapping("/private/qualification/numero/{id}")
		public QualificationAux obtenirQualificationParNumero(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@PostMapping("private/sequence/save")
		public void enregistrerSequence(
				@RequestHeader("Authorization") String token,
				FormSequence formSequence);

		@GetMapping("private/sequence/{id}")
		public SequenceAux obtenirSequenceParId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@PostMapping("private/sequence/modifier")
		public void modifierSequence(
				@RequestHeader("Authorization") String token,
				FormSequence formSequence);

		@PostMapping("private/echantillon/ajouter/{echantillon}/{qualification}/{sequence}")
		public void ajouterEchantillon(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "echantillon") Integer idEchantillon,
				@PathVariable(name = "qualification") Integer numQualification,
				@PathVariable(name = "sequence") Integer idSequence
		);
		
		@PostMapping("private/echantillon/retirer/{echantillon}/{qualification}/{sequence}")
		public void retirerEchantillon(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "echantillon") Integer idEchantillon,
				@PathVariable(name = "qualification") Integer numQualification,
				@PathVariable(name = "sequence") Integer idSequence
		);
		
		@GetMapping("/private/echantillon/sequence/selection/{qualification}/{sequence}")
		public List<EchantillonAux> obtenirEchantillonSelectionParSequence(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "qualification") Integer num,
				@PathVariable(name = "sequence") Integer idSequence);
		
		@GetMapping("/private/echantillon/modifier/{id}/{qualification}")
		public EchantillonAux obtenirEchantillon(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@PostMapping("/private/echantillon/modifier")
		public void modifierEchantillon(
				@RequestHeader("Authorization") String token,
				FormEchantillon formEchantillon);
		
		@GetMapping("private/qualification/modifier/statut/{id}")
		public void modifierStatutQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer numQualification);
		
		@GetMapping("private/qualification/modifier/resultat/{id}")
		public void modifierResultatQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer numQualification);
		
		@PostMapping("/private/qualification/modifier")
		public void modifierQualification(
				@RequestHeader("Authorization") String token,
				FormQualif formQualif);
		
		@PostMapping("/private/sequence/supprimer/{id}")
		public void supprimerSequence(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer idSequence);

		
		@PostMapping("/private/essai/modifier")
		public void modifierEssai(
				@RequestHeader("Authorization") String token,
				FormEssai formEssai);
		
		
		@PostMapping("/private/rapport/enregistrer")   // ajout en version pour test!
		public Integer enregistrerInitRapport(
				@RequestHeader("Authorization") String token,
				FormInitRapport formInitRapport);
		
		@GetMapping("/private/rapport/liste/{num}")
		public List<RapportAux> obtenirRapportsParQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "num") Integer numQualification);
		
		@GetMapping("/private/rapport/{id}")
		public RapportAux obtenirRapportsParId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer idRapport);
		
		@GetMapping("/private/qualification/id/{id}") 
		public List<EssaiAux> obtenirEssaisParQualificationId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer qualification);
		
		@GetMapping("/private/echantillons/qualification/id/{qualification}")
		public List<EchantillonAux> obtenirEchantillonParIdQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "qualification") Integer qualification);
		
		@PostMapping("/private/rapport/data/enregistrer")
		public void enregistrerDataRapport(
				@RequestHeader("Authorization") String token,
				GroupeRapport groupeRapport);
		
		@GetMapping("/private/rapport/supprimer/{id}")
		public void supprimerRapportsParId(
				@RequestHeader("Authorization") String token,
				@PathVariable("id") Integer idRapport);
		
		@GetMapping("/private/rapport/echantillons/{id}")
		public List<EchantillonAux> obtenirEchantillonsParRapportId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id")Integer idRapport);
		
		@GetMapping("/private/rapport/essais/{id}")
		public List<EssaiAux> obtenirEssaisParRapportId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id")Integer idRapport);
		
		@GetMapping("/private/note/liste/{id}")
		public List<NoteAux> obtenirListeNotesParQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer numQualification);
		
		@PostMapping("/private/note/enregistrer")
		public void ajouterNote(
				@RequestHeader("Authorization") String token,
				FormNote formNote);
		
		@GetMapping("/private/note/voir/{id}")
		public NoteAux obtenirNote(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer idNote);
		
		@GetMapping("/private/note/supprimer/{id}")
		public void supprimerNote(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer idNote);
		
		@PostMapping("/private/note/modifier")
		public void modifierNote(
				@RequestHeader("Authorization") String token,
				FormNote formNote);
		
		@GetMapping("/private/procedure/liste/domaine/{domaine}")
		public List<ProcedureAux> obtenirProceduresParDomaine(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "domaine") String domaine);
		
		@GetMapping("/private/procedure/obtenir/{id}")
		public ProcedureAux obtenirUneProcedure(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@PostMapping("/private/procedure/modifier")
		public void modifierProcedure(
				@RequestHeader("Authorization") String token,
				FormProcedure formProcedure);
		
		@PostMapping("/private/demande/enregistrer")
		public void enregistrerDemande(
				@RequestHeader("Authorization") String token,
				FormDemande formDemande);
		
		@GetMapping("/private/demande/liste")
		public List<DemandeAux> listeDemandes(@RequestHeader("Authorization") String token);
		
		@GetMapping("/private/demande/voir/{id}")
		public DemandeAux voirDemande(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@GetMapping("/private/demande/supprimer/{id}")
		public void supprimerDemande(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@PostMapping("/private/demande/modifier")
		public void modifierDemande(
				@RequestHeader("Authorization") String token,
				FormDemande formDemande);
		
		@PostMapping("/private/fiche/enregistrer")
		public void enregistrerFiche(
				@RequestHeader("Authorization") String token,
				FormFiche formFiche);
		
		@GetMapping("/private/fiche/voir")
		public List<FicheAux> voirLesFiches(@RequestHeader("Authorization") String token);
		
		@GetMapping("/private/fiche/qualification/{id}")
		public List<FicheAux> voirLesFichesParQualification(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer numQualification);
		
		@PostMapping("/private/fiche/qualification/ajouter")
		public void ajouterFiche(
				@RequestHeader("Authorization") String token,
				FormFiche formFiche);
		
		@GetMapping("/private/fiche/voir/{id}")
		public FicheAux voirLaFiches(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@GetMapping("/private/fiche/supprimer/{id}")
		public void supprimerLaFiches(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		@GetMapping("/private/qualification/identifiant/{id}") 
		public QualificationAux obtenirQualificationParId(
				@RequestHeader("Authorization") String token,
				Integer idQualification);
		
		@PostMapping("/private/fiche/modifier")
		public void modifierLaFiche(
				@RequestHeader("Authorization") String token,
				FormFiche formFiche);
		
		@PostMapping("/private/demande/reponse/enregistrer")
		public void enregistrerReponse(
				@RequestHeader("Authorization") String token,
				FormDemande formDemande);
		
		@GetMapping("/private/demande/traiter/{id}")
		public void traiterDemande(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);
		
		
		
		@GetMapping("/private/utilisateur/{id}")
		public Utilisateur obtenirUtilisateurParId(
				@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id);

		@PostMapping("/private/activite/site/enregistrer")
		public void enregistrerSite(
				@RequestHeader("Authorization") String token,
				FormSite formSite);
		
		@GetMapping("/private/activite/site/liste")
		public List<FormSite> obtenirListeSites(@RequestHeader("Authorization") String token);
		
		@PostMapping("/private/activite/site/defaut/enregistrer")
		public void enregistrerIncident(
				@RequestHeader("Authorization") String token,
				FormIncident formIncident);
		
		@GetMapping("/private/activite/site/defaut/liste")
		public List<FormIncident> obtenirListeIncident(@RequestHeader("Authorization") String token);
		
		@PostMapping("/private/activite/site/defaut/produit")
		public List<FormIncident> obtenirDefautParProduit(@RequestHeader("Authorization") String token, String produit);
		
		@PostMapping("/private/activite/site/defaut/produit/voir")
		public FormIncident obtenirDefautParId(@RequestHeader("Authorization") String token, Integer id);
		
		
		@PostMapping("/private/activite/site/get")
		public FormSite obtenirSiteParId(@RequestHeader("Authorization") String token, Integer idSite);
		
		@PostMapping("/private/activite/site/ajouter/recurrence")
		public void ajouterRecurrence(@RequestHeader("Authorization") String token, RecurrenceAux recurrenceAux);
		
		@PostMapping("/private/activite/site/defaut/cartographier")
		public List<FormSite> cartographier(@RequestHeader("Authorization") String token, Integer idDefaut);
		
		@PostMapping("/private/activite/site/defauts")
		public List<FormIncident> obtenirDefautsParSite(@RequestHeader("Authorization") String token, Integer id);

		@PostMapping("/private/gestion/usine/of/enregistrer")
		public void enregistrerOf(@RequestHeader("Authorization") String token, @RequestBody FormOf formOf);
		
		@GetMapping("/private/gestion/usine/of/liste"
		public List<FormOf> obtenirListeOfs(@RequestHeader("Authorization") String token);

		@PostMapping("/private/gestion/usine/of/voir")
		public FormOf obtenirOfParId(@RequestHeader("Authorization")  String token, Integer id);
		
		@PostMapping("/private/gestion/usine/anomalie/enregistrer")
		public void enregistrerAnomalie(@RequestHeader("Authorization") String token, @RequestBody FormAnomalie formAnomalie);
		
		@GetMapping("/private/gestion/usine/anomalies/liste")
		public List<FormAnomalie> obtenirListeAnomalies(@RequestHeader("Authorization") String token);

		@PostMapping("/private/gestion/usine/anomalie")
		public FormAnomalie obtenirAnomalieParId(@RequestHeader("Authorization")String token, @RequestBody Integer id);
		
		@PostMapping("/private/gestion/usine/anomalie/produits")
		public List<String> listeProduitsAvecAnomalie(@RequestHeader("Authorization") String token);
		
		@PostMapping("/private/gestion/usine/anomalie/produit")
		public List<FormAnomalie> obtenirAnomaliesParProduit(@RequestHeader("Authorization") String token, @RequestBody String produit);

		@PostMapping("/private/gestion/usine/of/anomalies")
		public List<FormAnomalie> obtenirAnomalieParOf(@RequestHeader("Authorization") String token, @RequestBody Integer id);
		
		@PostMapping("/private/gestion/usine/of/produit")
		public List<FormOf> obtenirOfsParProduit(@RequestHeader("Authorization") String token, @RequestBody String produit);
		
		@PostMapping("/private/gestion/usine/enregistrer/repetition")
		public void enregistrerRepetition(@RequestHeader("Authorization") String token, @RequestBody RepetitionAux repetition);
		
		@PostMapping("/private/gestion/usine/repetitions/of")
		public List<RepetitionAux> obtenirRepetitionsParOf(@RequestHeader("Authorization") String token, @RequestBody Integer of);
		
		@PostMapping("/private/gestion/usine/modifier/of")
		public void modifierOf(@RequestHeader("Authorization") String token,  @RequestBody FormOf formOf);
		
		@PostMapping("/private/gestion/usine/supprimer/of")
		public void supprimerOf(@RequestHeader("Authorization")String token, @RequestBody Integer id);
		
		@PostMapping("/private/gestion/usine/modifier/anomalie")
		public void modifierAnomalie(@RequestHeader("Authorization") String token, @RequestBody FormAnomalie formAnomalie);
		
		@PostMapping("/private/gestion/usine/supprimer/anomalie")
		public void supprimerAnomalie(@RequestHeader("Authorization") String token, @RequestBody Integer id);
		
		
		@PostMapping("/private/activite/site/defaut/produit/recurrence")
		public FormIncident obtenirDefautParIdPourProduit(@RequestHeader("Authorization")String token, @RequestBody FormIncident incident);

		@PostMapping("/essai/supprimer/procedure/{id}/{qualification}/{idUser}")
		public void supprimerProcedure(@RequestHeader("Authorization") String token,
				@PathVariable(name = "id") Integer id // id = identifiant procedure
				, @PathVariable(name = "qualification") Integer qualification // qualification = numéro de qualification
				, @PathVariable(name = "idUser") Integer idUser);

		
*/
	
	// code plannings
	
	/*
	
	@PostMapping("/connexion")
	public ResponseEntity<UtilisateurAux> generate(@RequestBody final Login login);
		
	@PutMapping("/modifier/compte/{id}")
	public void modifierCompte(@PathVariable Integer id, @RequestHeader("Authorization") String token,
			@RequestBody UtilisateurAux utilisateurAux);

	@PostMapping("/compte")
	public void creerCompte(@RequestBody UtilisateurAux user);

	*/
		
		  
		

}
