package exercise2;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

public class secondexerciseb {
	public static void main(String[] args) {
        try {
        	
        	Model model = ModelFactory.createDefaultModel();
			FileManager.get().readModel( model, "myContacts.rdf" );      	
        	
			String queryString =
			        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"                                                 +
			        "PREFIX rdfs: <" + RDFS.getURI() + ">"                                                      +
			        "PREFIX owl: <" + OWL.getURI() + ">"                                                        +
			        "SELECT ?name ?lovedPizza WHERE { ?person foaf:name ?name . ?person foaf:loves ?lovedPizza" +
			        "}";
			
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);

	        try {
	            ResultSet rs = qe.execSelect() ;
	            ResultSetFormatter.out(System.out, rs, query) ;
	        } finally {
	            qe.close() ;
	        }
			
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
