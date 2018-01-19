package exercise2;

import org.apache.jena.ontology.OntModel; 
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query ;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

public class secondexercisea {

	public static void main(String[] args) {
        try {
        	
        	OntModel pizzatype = ModelFactory.createOntologyModel( 
                    OntModelSpec.OWL_MEM);
			FileManager.get().readModel( pizzatype, "myPizzas.owl" );      	
        	
						String queryString =
			        "PREFIX pizza: <http://www.pizza.com/ontologies/pizza.owl#>" +
			        "PREFIX rdfs: <" + RDFS.getURI() + ">"                       +
			        "PREFIX owl: <" + OWL.getURI() + ">"                         +
			        "SELECT DISTINCT ?p WHERE { ?p a owl:Class; "        +
			        "rdfs:subClassOf ?r . "                            +
			        "?r owl:onProperty pizza:hasTopping;"              +
			        "}";
			
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, pizzatype);
			
			try {	            
	            ResultSetRewindable rs = ResultSetFactory.makeRewindable(qe.execSelect());
	            
	            ResultSetFormatter.outputAsJSON(System.out, rs);
	            
	            rs.reset();
	            
	            ResultSetFormatter.outputAsXML(System.out, rs);
	            
	            rs.reset();            

	        }
	        finally
	        {
	            qe.close() ;
	        }
			
			String queryStringRemote = 
		            "SELECT * WHERE { " +
		            "    SERVICE <http://it.dbpedia.org/sparql?timeout=2000> { " +
		            "        SELECT * WHERE { ?pizza <http://purl.org/dc/terms/subject> <http://it.dbpedia.org/resource/Categoria:Pizza> }" +
		            "    }" +
		            "}";
			
	        Query queryRemote = QueryFactory.create(queryStringRemote) ;
	        QueryExecution qexec = QueryExecutionFactory.create(queryRemote, ModelFactory.createDefaultModel()) ;
	        try {
	            ResultSet rs = qexec.execSelect() ;
	            ResultSetFormatter.out(System.out, rs, queryRemote) ;
	        } finally {
	            qexec.close() ;
	        }
			
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
