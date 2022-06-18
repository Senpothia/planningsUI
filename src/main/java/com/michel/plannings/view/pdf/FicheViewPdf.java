package com.michel.plannings.view.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.michel.plannings.models.FicheAux;


@Component("fiche")
public class FicheViewPdf extends AbstractPdfView {

	private static final Phrase HeaderFooter = null;

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {

		super.buildPdfMetadata(model, document, request);

		FicheAux fiche = (FicheAux) model.get("fiche");

		try {
			document.setMargins(30, 30, 30, 30);
			Image entete = Image.getInstance("https://i.ibb.co/61HYK40/Bandeausup1.jpg");
			// Image entete =
			// Image.getInstance("src\\main\\resources\\static\\images\\BandeauSup1.jpg");
			entete.scaleAbsolute(535, 100);
			HeaderFooter header = new HeaderFooter(new Phrase(new Chunk(entete, 0, -35)), false);
			HeaderFooter footer = new HeaderFooter(
					new Phrase("Fiche d'anomalie technique", FontFactory.getFont(FontFactory.TIMES)), false);
			document.setHeader(header);
			document.setFooter(footer);

		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		FicheAux fiche = (FicheAux) model.get("fiche");

		Paragraph titre = new Paragraph(new Chunk(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 28)));
		// titre.setAlignment(Element.ALIGN_CENTER); titre.setSpacingAfter(10);
		document.add(titre);

		PdfPTable table = new PdfPTable(8);
		table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
		table.setWidthPercentage(100);
		PdfPCell cellTitre = null;
		// cell = new PdfPCell(new Phrase("Fiche d'anomalie technique"));
		cellTitre = new PdfPCell(
				new Phrase("Fiche d'anomalie technique", FontFactory.getFont(FontFactory.TIMES_BOLD, 28)));

		cellTitre.setBackgroundColor(new Color(153, 204, 255));
		cellTitre.setColspan(8);
		cellTitre.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cellTitre.setPadding(15);
		table.addCell(cellTitre);

		PdfPCell cellAuteur = new PdfPCell(new Phrase("Auteur:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellAuteur.setColspan(1);
		cellAuteur.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAuteur);

		PdfPCell cellAuteur2 = new PdfPCell(
				new Phrase(fiche.getNomAuteur(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellAuteur2.setColspan(1);
		cellAuteur2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellAuteur2);

		PdfPCell cellService = new PdfPCell(new Phrase("Service:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellAuteur.setColspan(1);
		cellAuteur.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellService);

		PdfPCell cellService2 = new PdfPCell(new Phrase("Labo", FontFactory.getFont(FontFactory.TIMES, 10)));
		cellService2.setColspan(1);
		cellService2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellService2);

		PdfPCell cellDate = new PdfPCell(new Phrase("Date:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellDate.setColspan(1);
		cellDate.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDate);

		PdfPCell cellDate2 = new PdfPCell(new Phrase(fiche.getDateString(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellDate2.setColspan(1);
		cellDate2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDate2);

		PdfPCell cellNumero = new PdfPCell(new Phrase("N° FAT:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellNumero.setColspan(1);
		cellNumero.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumero);

		PdfPCell cellNumero2 = new PdfPCell(
				new Phrase(String.valueOf(fiche.getNumero()), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellNumero2.setColspan(1);
		cellNumero2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNumero2);
		////

		PdfPCell cellProduit = new PdfPCell(new Phrase("Produit:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellProduit.setColspan(1);
		cellProduit.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellProduit);

		PdfPCell cellProduit2 = new PdfPCell(
				new Phrase(String.valueOf(fiche.getProduit()), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellProduit2.setColspan(1);
		cellProduit2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellProduit2);

		PdfPCell cellProjet = new PdfPCell(new Phrase("Projet:", FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
		cellProjet.setColspan(1);
		cellProjet.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellProjet);

		PdfPCell cellProjet2 = new PdfPCell(new Phrase(fiche.getProjet(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellProjet2.setColspan(1);
		cellProjet2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellProjet2);

		PdfPCell cellOrgane = new PdfPCell(
				new Phrase("Organe affecté:", FontFactory.getFont(FontFactory.TIMES_BOLD, 9)));
		cellOrgane.setColspan(1);
		cellOrgane.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellOrgane);

		PdfPCell cellOrgane2 = new PdfPCell(new Phrase(fiche.getObjet(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellOrgane2.setColspan(3);
		cellOrgane2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellOrgane2);

		PdfPCell cellNature = new PdfPCell(
				new Phrase("Nature du problème", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellNature.setBackgroundColor(new Color(153, 204, 255));
		cellNature.setColspan(8);
		cellNature.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		table.addCell(cellNature);

		PdfPCell cellNature2 = new PdfPCell(new Phrase(fiche.getDomaine(), FontFactory.getFont(FontFactory.TIMES, 10)));

		cellNature2.setColspan(8);
		cellNature2.setMinimumHeight(40);
		cellNature2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellNature2);

		PdfPCell cellSubTitre2 = new PdfPCell(
				new Phrase("Niveau de gravité", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre2.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre2.setColspan(8);
		cellSubTitre2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre2);

		PdfPCell cellGravite = new PdfPCell(new Phrase(fiche.getDegre(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellGravite.setColspan(2);
		cellGravite.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellGravite);
		String libelle = null;

		if (fiche.getDegre() != null) {

			if (fiche.getDegre().equals("Remaque")) {

				libelle = "Simple remarque sans incidence";
			}

			if (fiche.getDegre().equals("Faible")) {

				libelle = "Le problème dévalorise légèrement le produit";
			}

			if (fiche.getDegre().equals("Moyen")) {

				libelle = "Le problème est significatif mais pas totalement bloquant";
			}

			if (fiche.getDegre().equals("Elevé")) {

				libelle = "Le problème doit être résolu le plus rapidement possible";
			}

			if (fiche.getDegre().equals("Grave")) {

				libelle = "Le problème est totalement inacceptable";
			}

		}

		PdfPCell cellGravite2 = new PdfPCell(new Phrase(libelle, FontFactory.getFont(FontFactory.TIMES, 10)));

		cellGravite2.setColspan(6);
		cellGravite2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellGravite2);

		/////
		PdfPCell cellSubTitre3 = new PdfPCell(
				new Phrase("Circonstance de l'observation", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre3.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre3.setColspan(8);
		cellSubTitre3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre3);

		PdfPCell cellCirconstances = new PdfPCell(
				new Phrase(fiche.getCirconstance(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellCirconstances.setColspan(8);
		cellCirconstances.setMinimumHeight(40);
		cellCirconstances.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellCirconstances);

		/////

		PdfPCell cellSubTitre4 = new PdfPCell(
				new Phrase("Documents complémentaires joints", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre4.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre4.setColspan(8);
		cellSubTitre4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre4);

		PdfPCell cellDocs = new PdfPCell(
				new Phrase(fiche.getCirconstance(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellDocs.setColspan(8);
		cellDocs.setMinimumHeight(40);
		cellDocs.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellDocs);

		///

		PdfPCell cellSubTitre5 = new PdfPCell(
				new Phrase("Degré d'incidence de l'anomalie", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre5.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre5.setColspan(8);
		cellSubTitre5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre5);

		PdfPCell cellIncidence = new PdfPCell(
				new Phrase(fiche.getIncidence(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellIncidence.setColspan(8);
		cellIncidence.setMinimumHeight(40);
		cellIncidence.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellIncidence);

		///

		PdfPCell cellSubTitre6 = new PdfPCell(
				new Phrase("Opérations correctives", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre6.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre6.setColspan(8);
		cellSubTitre6.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre6);

		PdfPCell cellCorrections = new PdfPCell(
				new Phrase(fiche.getSolution(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellCorrections.setColspan(8);
		cellCorrections.setMinimumHeight(40);
		cellCorrections.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellCorrections);

		///

		PdfPCell cellSubTitre7 = new PdfPCell(
				new Phrase("Réponse envisagée du BE", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));

		cellSubTitre7.setBackgroundColor(new Color(153, 204, 255));
		cellSubTitre7.setColspan(8);
		cellSubTitre7.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cellSubTitre7);

		PdfPCell cellReponse = new PdfPCell(new Phrase(fiche.getReponse(), FontFactory.getFont(FontFactory.TIMES, 10)));
		cellReponse.setColspan(8);
		cellReponse.setMinimumHeight(80);
		cellReponse.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		table.addCell(cellReponse);

		document.add(table);

	}

}
