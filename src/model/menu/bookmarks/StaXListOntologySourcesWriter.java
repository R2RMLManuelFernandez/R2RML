/**
 * 
 */
package model.menu.bookmarks;

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
 * Writes the ontology sources into a XML file
 * 
 * @author Manuel Fernandez Perez
 * 
 */
public class StaXListOntologySourcesWriter {
	
	private final String head;
	private final String itemName;
	private final String file;
	
	public StaXListOntologySourcesWriter(String paramHead, String paramItemName, String paramFile) {
		
		this.head = paramHead;
		this.itemName = paramItemName;
		this.file = paramFile;
		
	}
	
	/**
	 * Saves the list of ontologies into the XML file
	 * 
	 * @param paramItems
	 * @throws Exception
	 */
	public void save(ArrayList<String> paramItems) throws Exception {
		
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(file));
		
		XMLEventFactory eventFactory = XMLEventFactory.newFactory();
		
		XMLEvent end = eventFactory.createDTD("\n");
		
		StartDocument startDocument = eventFactory.createStartDocument();
		
		eventWriter.add(startDocument);
		eventWriter.add(end);
		
		StartElement itemStartElement = eventFactory.createStartElement("", "", head);
		eventWriter.add(itemStartElement);
		eventWriter.add(end);
		
		int itemsSize = paramItems.size();
		for (int i = 0; i < itemsSize; i++) {
			
			createSource(eventWriter, itemName, paramItems.get(i));
			
		}
		
		eventWriter.add(eventFactory.createEndElement("", "", head));
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	/**
	 * Creates the ontology source into the XML file
	 * 
	 * @param eventWriter
	 * @param paramItemName
	 * @param paramValue
	 * @throws XMLStreamException
	 */
	private void createSource(XMLEventWriter eventWriter, String paramItemName, String paramValue) throws XMLStreamException {
		
		final String ontology = "ontology";
		final String name = paramItemName;

		XMLEventFactory eventFactory = XMLEventFactory.newFactory();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		//start of the item
		StartElement head = eventFactory.createStartElement("", "", ontology);
		eventWriter.add(tab);
		eventWriter.add(head);
		eventWriter.add(end);
		
		//start of the item
		StartElement itemName = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(tab);
		eventWriter.add(itemName);
		
		//value of the item
		Characters value = eventFactory.createCharacters(paramValue);
		eventWriter.add(value);
		
		//end of the item
		EndElement endValue = eventFactory.createEndElement("", "", name);
		eventWriter.add(endValue);
		eventWriter.add(end);
		
		//end of the item
		EndElement endItem = eventFactory.createEndElement("", "", ontology);
		eventWriter.add(tab);
		eventWriter.add(endItem);
		eventWriter.add(end);
		
	}
	
}
