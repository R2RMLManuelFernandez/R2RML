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

package model.menu.bookmarks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/** Class to read the XML file containing ontology IRIs/files
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class StaXListOntologySourcesParser {
	
	//XML tag: indicates the start of the list of ontologies
	private final String head;
	//XML tag: indicates the start of an ontology
	private final String item;
	//source XML file
	private final String file;
	
	public StaXListOntologySourcesParser(String paramHead, String paramItemName, String paramFile) {
		
		this.head = paramHead;
		this.item = paramItemName;
		this.file = paramFile;
		
	}
		
	/**
	 * Reads the ontology sources into a list
	 * 
	 * @return
	 */
	public List<OntologySource> read() {
		
		List<OntologySource> sources = new ArrayList<OntologySource>();
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newFactory();
			InputStream input = new FileInputStream(file);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(input);
			OntologySource source = null;
			
			while (eventReader.hasNext()) {
				
				//gets the new event from the event reader
				XMLEvent event = eventReader.nextEvent();
				
				//creates a new ontology
				if (event.isStartElement()) {
					
					StartElement startElement = event.asStartElement();
					
					if (startElement.getName().getLocalPart() == (head)) {
						
						source = new OntologySource();
						
					}
					
				}
				
				if (event.isStartElement()) {
					
					if (event.asStartElement().getName().getLocalPart().equals(item)) {
						
						event = eventReader.nextEvent();
						source.setSource(event.asCharacters().getData());
						
					}

				}
				
				if (event.isEndElement()) {
					
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (head)) {
						
						sources.add(source);
						
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

		return sources;
		
	}

}
