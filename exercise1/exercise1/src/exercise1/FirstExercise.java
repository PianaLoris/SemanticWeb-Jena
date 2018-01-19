package exercise1;

import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class FirstExercise {

	public static void main(String args[]) {

		Model model = ModelFactory.createDefaultModel();


		Statement datiContatto1 = model.createStatement(
										model.createResource("http://www.instagram.com/KathrynRose"),
										VCARD.FN,
										model.createLiteral("Kathryn Rose"));
		Statement datiContatto2 = model.createStatement(
										model.createResource("http://www.instagram.com/DeborahGomez"),
										VCARD.FN,
										model.createLiteral("Deborah Gomez"));
		Statement datiContatto3 = model.createStatement(
										model.createResource("http://www.instagram.com/MarieHoward"),
										VCARD.FN,
										model.createLiteral("Marie Howard"));
		Statement datiContatto4 = model.createStatement(
										model.createResource("http://www.instagram.com/CynthiaGrant"),
										VCARD.FN,
										model.createLiteral("Cynthia Grant"));
		Statement datiContatto5 = model.createStatement(
										model.createResource("http://www.instagram.com/DevinMills"),
										VCARD.FN,
										model.createLiteral("Devin Mills"));

		model.add(datiContatto1);
		model.add(datiContatto2);
		model.add(datiContatto3);
		model.add(datiContatto4);
		model.add(datiContatto5);






		File input = new File("test_hCard.html");
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements hCards = doc.select(".vcard");

		for (Element hCard : hCards) {

			String fn = "";
			String firstName = "";
			String familyName = "";
			String email = "";
			String tel = "";
			String streetAddress = "";
			String locality = "";
			String zipCode = "";
			String country = "";

			Resource newContact;


			if (!hCard.getElementsByClass("fn").isEmpty()) {
				fn = hCard.getElementsByClass("fn").first().ownText();
				newContact = model.createResource("http://www.example.com/#" + fn.replace(' ', '_'));
				newContact.addProperty(VCARD.FN, fn);
			}
			else {
				System.out.println("hCard is not valid, the field \"fn\" is required.");
				continue;
			}

			if (!hCard.getElementsByClass("n").isEmpty()) {
				Resource newBlankNode = model.createResource();
				if (!hCard.getElementsByClass("first-name").isEmpty()) {
					firstName = hCard.getElementsByClass("first-name").first().ownText();
					newBlankNode.addProperty(VCARD.NAME, firstName);
				}
				if (!hCard.getElementsByClass("family-name").isEmpty()) {
					familyName = hCard.getElementsByClass("family-name").first().ownText();
					newBlankNode.addProperty(VCARD.Family, familyName);
				}
				newContact.addProperty(VCARD.N, newBlankNode);
			}


			if (!hCard.getElementsByClass("email").isEmpty()) {
				email = hCard.getElementsByClass("email").first().attr("href");
				newContact.addProperty(VCARD.EMAIL, email);
			}

			if (!hCard.getElementsByClass("tel").isEmpty()) {
				tel = hCard.getElementsByClass("tel").first().ownText();
				newContact.addProperty(VCARD.TEL, tel);
			}

			if (!hCard.getElementsByClass("adr").isEmpty()) {
				Resource newBlankNode = model.createResource();
				if (!hCard.getElementsByClass("street-address").isEmpty()) {
					streetAddress = hCard.getElementsByClass("street-address").first().ownText();
					newBlankNode.addProperty(VCARD.Street, streetAddress);
				}

				if (!hCard.getElementsByClass("locality").isEmpty()) {
					locality = hCard.getElementsByClass("locality").first().ownText();
					newBlankNode.addProperty(VCARD.Locality, locality);
				}

				if (!hCard.getElementsByClass("zip-code").isEmpty()) {
					zipCode = hCard.getElementsByClass("zip-code").first().ownText();
					newBlankNode.addProperty(VCARD.Pcode, zipCode);
				}
				if (!hCard.getElementsByClass("country-name").isEmpty()) {
					country = hCard.getElementsByClass("country-name").first().ownText();
					newBlankNode.addProperty(VCARD.Country, country);
				}
				newContact.addProperty(VCARD.ADR, newBlankNode);
			}
		}



		try {
			PrintWriter writer = new PrintWriter("result.rdf", "UTF-8");
			model.write(writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
