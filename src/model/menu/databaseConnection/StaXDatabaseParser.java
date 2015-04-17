/*
 * Copyright 2015 Manuel Fernández Pérez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model.menu.databaseConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import model.database.Database;

/** Class to read the XML file containing databases description
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class StaXDatabaseParser {
	
	private static final String CONNECTION = "connection";
	private static final String TYPE = "databasetype";
	private static final String DBNAME = "databasename";
	private static final String HOST = "host";
	private static final String PORT = "port";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private String connectionFile;
	
	public StaXDatabaseParser(String paramFile) {
		
		this.connectionFile = paramFile;
		
	}
	
	/**
	 * Reads the database descriptions into a list
	 * 
	 * @return
	 */		
	public List<Database> read() throws UnknownHostException {
		
		List<Database> databases = new ArrayList<Database>();
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			InputStream input = new FileInputStream(connectionFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(input);
			Database database = null;
			
			while (eventReader.hasNext()) {
				
				XMLEvent event = eventReader.nextEvent();
				
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					
					if (startElement.getName().getLocalPart() == (CONNECTION)) {
						database = new Database();
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(TYPE)) {
						event = eventReader.nextEvent();
						database.setDBMS(event.asCharacters().getData());
						continue;
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(DBNAME)) {
						event = eventReader.nextEvent();
						database.setDatabaseName(event.asCharacters().getData());
						continue;
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(HOST)) {
						event = eventReader.nextEvent();
						try {
							InetAddress host = InetAddress.getByName(event.asCharacters().getData());
							database.setHost(host);
						}catch(UnknownHostException u) {
							System.out.println("StaXDatabaseParser:read -->  Has leido un host no valido");
							u.printStackTrace();
						}

						continue;
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(PORT)) {
						event = eventReader.nextEvent();
						try {
							int port = Integer.parseInt(event.asCharacters().getData()); 
							database.setPort(port);
						}catch (NumberFormatException n) {
							System.out.println("StaXDatabaseParser:read -->  Has leido un puerto no valido");
							n.printStackTrace();
						}
						continue;
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(USERNAME)) {
						event = eventReader.nextEvent();
						database.setUsername(event.asCharacters().getData());
						continue;
					}
				}
				
				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart().equals(PASSWORD)) {
						event = eventReader.nextEvent();
						database.setPassword(event.asCharacters().getData());
						continue;
					}
				}
				
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (CONNECTION)) {
						databases.add(database);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return databases;
		
	}

}
