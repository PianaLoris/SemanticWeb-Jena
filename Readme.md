# Semantic Web Exercises

This repository contains the solutions for the Semantic Web Technologies course assignments at the University of Genoa.
Each of these exercises are based on [Apache Jena Framework](https://jena.apache.org/).

* [Exercise 1](#1)
* [Exercise 2](#2)
* [Exercise 3](#3)

<a name="1"></a>
### Exercise 1
In the first exercise an RDF model is created and filled with five resources that correspond to our contacts. Then the program parses an html page and gets all new elements and adds them into our RDF model.
 <a name="2"></a>
### Exercise 2
##### Exercise 2.a
In this first part we import the myPizzas ontology and make a query against it. The results are printed out both as RDF and XML format. Also a remote query against [DBpedia](http://wiki.dbpedia.org/) is made and results are showed as RDF.
##### Exercise 2.b
In the second part we create an RDF model from the myContacts file which contains our contacts infos and their favourite pizzas. Then we make a query against the model and print out the results.
 <a name="3"></a>
### Exercise 3
In the last exercise the RDF model imported from myContacts is stored in a persistent mode using TDBStore. Then the program parses the file dinner.hCalendar which contains the name of people invited to a dinner. Using a simple query the program identifies the right corrispondence between the person invited and his favourite pizza. The program sends to the person a message for confirmation.
