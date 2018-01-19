package exercise3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Esercizio3 {
	public static void main(String[] args) {
        try {
        	// creat a dataset TDB.
        	String directory = "TDBStore";
        	Dataset dataset = TDBFactory.createDataset(directory);
        	
        	// memorize the model in persistent mode.
        	Model tdb = dataset.getDefaultModel();
        	FileManager.get().readModel(tdb, "myContacts.rdf");

        	// print the results memorize in data set.
        	String q = "select * where {?s ?p ?o}";
        	Query query = QueryFactory.create(q);
        	QueryExecution qexec = QueryExecutionFactory.create(query, tdb);
        	ResultSet results = qexec.execSelect();        	
        	ResultSetFormatter.outputAsXML(System.out, results);
        	
        	tdb.close();
        	dataset.close();
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
        	
        	// obtain the list of dinner guests "dinner with friends".
        	
        	File input = new File("dinner.hCalendar");
    		Document doc = null;
    		try {
    			doc = Jsoup.parse(input, "UTF-8");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		Elements invitati = doc.select(".attendee");
    		
    		ArrayList<String> contatti = new ArrayList<String>();
    		
    		for (Element invitato : invitati) {
    			contatti.add(invitato.ownText());
    		}    		
    		
    		
        	Model model = ModelFactory.createDefaultModel();
			FileManager.get().readModel( model, "myContacts.rdf" );      	
        	
			// find what type of pizza my friend select.
			String queryString =
			        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"                                                 +
			        "PREFIX rdfs: <" + RDFS.getURI() + ">"                                                      +
			        "PREFIX owl: <" + OWL.getURI() + ">"                                                        +
			        "SELECT ?name ?pizza ?phone WHERE { ?person foaf:name ?name . ?person foaf:loves ?pizza . ?person foaf:phone ?phone" +
			        "}";
			
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);

	        try {
	            ResultSetRewindable rs = ResultSetFactory.makeRewindable(qe.execSelect());
	            
	            ResultSetFormatter.out(System.out, rs, query);
	            
	            rs.reset();
	            
	            rs.forEachRemaining(p -> {
	            		System.out.println("Message to " + p.get("phone") + ":");
	            		System.out.println(p.get("name") + ": do you like " + p.get("pizza") + "?");
	            	}
	            );
	            
	            
	        } finally {
	            qe.close() ;
	        }
			
			
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
