/**
 * 
 */
package model.menu.databaseConnection;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Writes the databases descriptions into a XML file
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class StaXDatabaseWriter {

	private String connectionFile;

	public StaXDatabaseWriter(String paramFile) {
		
		this.connectionFile = paramFile;
		
	}
	
	/**
	 * Saves the list of databases descriptions into the XML file
	 * 
	 * @param paramConnections
	 * @throws Exception
	 */
	public void save(ArrayList<String> paramConnections) throws Exception {
		
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(connectionFile));
		
		XMLEventFactory eventFactory = XMLEventFactory.newFactory();
		
		XMLEvent end = eventFactory.createDTD("\n");
		
		StartDocument startDocument = eventFactory.createStartDocument();
		
		eventWriter.add(startDocument);
		
		StartElement connectionStartElement = eventFactory.createStartElement("", "", "connections");
		eventWriter.add(end);
		eventWriter.add(connectionStartElement);
		eventWriter.add(end);
		
		createConnection(eventWriter, paramConnections);

		
/*		createBookmark(eventWriter, "http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		System.out.println("Escrito bookmark");
		createBookmark(eventWriter,  "http://eldelasautores.com");
		System.out.println("Escrito bookmark2");
		createBookmark(eventWriter,  "http://eldeloslournalist.com");
		System.out.println("Escrito bookmark3");
		createBookmark(eventWriter,  "http://eldelRDF.com");
		System.out.println("Escrito bookmark4");*/
		
		eventWriter.add(eventFactory.createEndElement("", "", "bookmarks"));
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	/**
	 * Creates the databases description into the XML file
	 * 
	 * @param eventWriter
	 * @param paramConnections
	 * @throws XMLStreamException
	 */
	private void createConnection(XMLEventWriter eventWriter, ArrayList<String> paramConnections) throws XMLStreamException {
		
		final String connectionName = "connection";
		final String typeName = "databasetype";
		final String dbNameName = "databasename";
		final String hostName = "host";
		final String portName = "port";
		final String userNameName = "username";
		final String passwordName = "password";

		XMLEventFactory eventFactory = XMLEventFactory.newFactory();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		//start of the bookmark
		StartElement bookmarkElement = eventFactory.createStartElement("", "", connectionName);
		eventWriter.add(tab);
		eventWriter.add(bookmarkElement);
		eventWriter.add(end);
		
		//start of the type
		StartElement type = eventFactory.createStartElement("", "", typeName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(type);
		
		//value of the type
		Characters typeValue = eventFactory.createCharacters(paramConnections.get(0));
		eventWriter.add(typeValue);
		
		//end of the type
		EndElement endType = eventFactory.createEndElement("", "", typeName);
		eventWriter.add(endType);
		eventWriter.add(end);
		
		//start of the dbName
		StartElement dbName = eventFactory.createStartElement("", "", dbNameName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(dbName);
		
		//value of the dbName
		Characters dbNameValue = eventFactory.createCharacters(paramConnections.get(1));
		eventWriter.add(dbNameValue);
		
		//end of the dbName
		EndElement endDBName = eventFactory.createEndElement("", "", dbNameName);
		eventWriter.add(endDBName);
		eventWriter.add(end);
		
		//start of the host
		StartElement host = eventFactory.createStartElement("", "", hostName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(host);
		
		//value of the host
		Characters hostValue = eventFactory.createCharacters(paramConnections.get(2));
		eventWriter.add(hostValue);
		
		//end of the host
		EndElement endHost = eventFactory.createEndElement("", "", hostName);
		eventWriter.add(endHost);
		eventWriter.add(end);
		
		//start of the port
		StartElement port = eventFactory.createStartElement("", "", portName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(port);
		
		//value of the port
		Characters portValue = eventFactory.createCharacters(paramConnections.get(3));
		eventWriter.add(portValue);
		
		//end of the port
		EndElement endPort = eventFactory.createEndElement("", "", portName);
		eventWriter.add(endPort);
		eventWriter.add(end);
		
		//start of the userName
		StartElement userName = eventFactory.createStartElement("", "", userNameName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(userName);
		
		//value of the userName
		Characters userNameValue = eventFactory.createCharacters(paramConnections.get(4));
		eventWriter.add(userNameValue);
		
		//end of the userName
		EndElement endUserName = eventFactory.createEndElement("", "", userNameName);
		eventWriter.add(endUserName);
		eventWriter.add(end);
		
		//start of the password
		StartElement password = eventFactory.createStartElement("", "", passwordName);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(password);
		
		//value of the password
		Characters passwordValue = eventFactory.createCharacters(paramConnections.get(5));
		eventWriter.add(passwordValue);
		
		//end of the password
		EndElement endPassword = eventFactory.createEndElement("", "", passwordName);
		eventWriter.add(endPassword);
		eventWriter.add(end);
		
		//end of the connection
		EndElement endConnection = eventFactory.createEndElement("", "", connectionName);
		eventWriter.add(tab);
		eventWriter.add(endConnection);
		eventWriter.add(end);
		
	}
	
}
