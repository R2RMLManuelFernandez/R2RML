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

package view.util;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import model.menu.bookmarks.OntologySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Manuel Fernandez Perez
 *
 */
public class PreferencesMediator {

	private Preferences prefs;

	private String mostRecentInputBookmarksFilePathKey = "mostRecentInputBookmarksFilePath";
	private String mostRecentOutputBookmarksFilePathKey = "mostRecentOutputBookmarksFilePath";
	private String mostRecentInputOntologyFilePathKey = "mostRecentInputOntologyFilePath";
	private String mostRecentInputDatabaseListFilePathKey = "mostRecentInputDatabaseListFilePath";
	private String mostRecentOutputDatabaseListFilePathKey = "mostRecentOutputDatabaseListFilePath";
	private String mostRecentInputR2RMLMappingFilePathKey = "mostRecentInputR2RMLMappingFilePath";
	private String mostRecentOutputR2RMLMappingFilePathKey = "mostRecentOutputR2RMLMappingFilePath";
	
	private String numberOfBookmarksKey = "numberOfBookmarks";
	private String numberOfRecentsKey = "numberOfRecents";
	private final String bookmarkKey = "bookmark";
	private final String recentKey = "recent";
	private int storedNumberBookmarks;
	private int storedNumberRecents;
	private String mostRecentInputBookmarksFilePathVal;
	private String mostRecentOutputBookmarksFilePathVal;
	private String mostRecentInputOntologyFilePathVal;
	private String mostRecentInputDatabaseListFilePathVal;
	private String mostRecentOutputDatabaseListFilePathVal;
	private String mostRecentInputR2RMLMappingFilePathVal;
	private String mostRecentOutputR2RMLMappingFilePathVal;
	
	private List<OntologySource> bookmarks;
	private List<OntologySource> recents;
	
	private static Logger logger = LoggerFactory.getLogger(PreferencesMediator.class);
	
	public PreferencesMediator()
	{
		prefs = Preferences.userRoot().node(this.getClass().getName());
		bookmarks = new ArrayList<OntologySource>(0);
		recents = new ArrayList<OntologySource>(0);
		logger.debug("loading prefs");
		loadPreferences();
		logger.debug("prefs loaded");
	}
	
	/**
	 * 
	 */
	private void loadPreferences() {

		storedNumberBookmarks = prefs.getInt(numberOfBookmarksKey, 0);
		storedNumberRecents = prefs.getInt(numberOfRecentsKey, 0);
		mostRecentInputBookmarksFilePathVal = prefs.get(mostRecentInputBookmarksFilePathKey, "");
		mostRecentOutputBookmarksFilePathVal = prefs.get(mostRecentOutputBookmarksFilePathKey, "");
		mostRecentInputOntologyFilePathVal = prefs.get(mostRecentInputOntologyFilePathKey, "");
		mostRecentInputDatabaseListFilePathVal = prefs.get(mostRecentInputDatabaseListFilePathKey, "");
		mostRecentOutputDatabaseListFilePathVal = prefs.get(mostRecentOutputDatabaseListFilePathKey, "");
		mostRecentInputR2RMLMappingFilePathVal = prefs.get(mostRecentInputR2RMLMappingFilePathKey, "");
		mostRecentOutputR2RMLMappingFilePathVal = prefs.get(mostRecentOutputR2RMLMappingFilePathKey, "");
		
		logger.debug(String.valueOf(storedNumberBookmarks));
		logger.debug(String.valueOf(storedNumberRecents));
		logger.debug(mostRecentInputBookmarksFilePathVal);
		logger.debug(mostRecentOutputBookmarksFilePathVal);
		logger.debug(mostRecentInputOntologyFilePathVal);
		logger.debug(mostRecentInputDatabaseListFilePathVal);
		logger.debug(mostRecentOutputDatabaseListFilePathVal);
		
		for (int i = 0; i < storedNumberBookmarks; i++) {
			
			String bookmarVal = prefs.get(bookmarkKey + (i), "");
			
			if (!bookmarVal.equals("")) {
				
				OntologySource source = new OntologySource();
				source.setSource(bookmarVal);
				bookmarks.add(source);

			}
			
		}
		
		for (int i = 0; i < storedNumberRecents; i++) {
			
			String recentVal = prefs.get(recentKey + (i), "");
			
			if (!recentVal.equals("")) {
				
				OntologySource source = new OntologySource();
				source.setSource(recentVal);
				recents.add(source);

			}
			
		}
		
	}

	/**
	 * @return the mostRecentInputBookmarksFilePathVal
	 */
	public String getMostRecentInputBookmarksFilePathVal() {
		
		return mostRecentInputBookmarksFilePathVal;
		
	}

	/**
	 * @param mostRecentInputBookmarksFilePathVal the mostRecentInputBookmarksFilePathVal to set
	 */
	public void setMostRecentInputBookmarksFilePathVal(String mostRecentInputBookmarksFilePathVal) {
		
		this.mostRecentInputBookmarksFilePathVal = mostRecentInputBookmarksFilePathVal;
		prefs.put(mostRecentInputBookmarksFilePathKey, mostRecentInputBookmarksFilePathVal);

	}

	/**
	 * @return the mostRecentOutputBookmarksFilePathVal
	 */
	public String getMostRecentOutputBookmarksFilePathVal() {
		
		return mostRecentOutputBookmarksFilePathVal;
		
	}

	/**
	 * @param mostRecentOutputBookmarksFilePathVal the mostRecentOutputBookmarksFilePathVal to set
	 */
	public void setMostRecentOutputBookmarksFilePathVal(String mostRecentOutputBookmarksFilePathVal) {
		
		this.mostRecentOutputBookmarksFilePathVal = mostRecentOutputBookmarksFilePathVal;
		prefs.put(mostRecentOutputBookmarksFilePathKey, mostRecentOutputBookmarksFilePathVal);

	}

	/**
	 * @return the mostRecentInputOntologyFilePathVal
	 */
	public String getMostRecentInputOntologyFilePathVal() {
		
		return mostRecentInputOntologyFilePathVal;
		
	}

	/**
	 * @param mostRecentInputOntologyFilePathVal the mostRecentInputOntologyFilePathVal to set
	 */
	public void setMostRecentInputOntologyFilePathVal(String mostRecentInputOntologyFilePathVal) {
		
		this.mostRecentInputOntologyFilePathVal = mostRecentInputOntologyFilePathVal;
		prefs.put(mostRecentInputOntologyFilePathKey, mostRecentInputOntologyFilePathVal);

	}

	/**
	 * @return the mostRecentInputDatabaseListFilePathVal
	 */
	public String getMostRecentInputDatabaseListFilePathVal() {
		
		return mostRecentInputDatabaseListFilePathVal;
		
	}

	/**
	 * @param mostRecentInputDatabaseListFilePathVal the mostRecentInputDatabaseListFilePathVal to set
	 */
	public void setMostRecentInputDatabaseListFilePathVal(String mostRecentInputDatabaseListFilePathVal) {
		
		this.mostRecentInputDatabaseListFilePathVal = mostRecentInputDatabaseListFilePathVal;
		prefs.put(mostRecentInputDatabaseListFilePathKey, mostRecentInputDatabaseListFilePathVal);

	}

	/**
	 * @return the mostRecentOutputDatabaseListFilePathVal
	 */
	public String getMostRecentOutputDatabaseListFilePathVal() {
		
		return mostRecentOutputDatabaseListFilePathVal;
		
	}

	/**
	 * @param mostRecentOutputDatabaseListFilePathVal the mostRecentOutputDatabaseListFilePathVal to set
	 */
	public void setMostRecentOutputDatabaseListFilePathVal(String mostRecentOutputDatabaseListFilePathVal) {
		
		this.mostRecentOutputDatabaseListFilePathVal = mostRecentOutputDatabaseListFilePathVal;
		prefs.put(mostRecentOutputDatabaseListFilePathKey, mostRecentOutputDatabaseListFilePathVal);

	}


	public String getMostRecentInputR2RMLMappingFilePathVal() {
		
		return mostRecentInputR2RMLMappingFilePathVal;
		
	}


	public void setMostRecentInputR2RMLMappingFilePathVal(String mostRecentInputR2RMLMappingFilePathVal) {
		
		this.mostRecentInputR2RMLMappingFilePathVal = mostRecentInputR2RMLMappingFilePathVal;
		prefs.put(mostRecentInputR2RMLMappingFilePathKey, mostRecentInputR2RMLMappingFilePathVal);

	}
	
	public String getMostRecentOutputR2RMLMappingFilePathVal() {
		
		return mostRecentOutputR2RMLMappingFilePathVal;
		
	}


	public void setMostRecentOutputR2RMLMappingFilePathVal(String mostRecentOutputR2RMLMappingFilePathVal) {
		
		this.mostRecentOutputR2RMLMappingFilePathVal = mostRecentOutputR2RMLMappingFilePathVal;
		prefs.put(mostRecentOutputR2RMLMappingFilePathKey, mostRecentOutputR2RMLMappingFilePathVal);

	}
	
	/**
	 * @return the bookmarks
	 */
	public List<OntologySource> getBookmarks() {
		
		return bookmarks;
		
	}

	/**
	 * @param bookmarks the bookmarks to set
	 */
	public void setBookmarks(List<OntologySource> bookmarks) {
		
		this.bookmarks = bookmarks;
		
		int actualNumberBookmarks;
		
		if (storedNumberBookmarks >= bookmarks.size()) {
			
			actualNumberBookmarks = storedNumberBookmarks;
			
		}
		else {
			
			actualNumberBookmarks = bookmarks.size();
			
		}
		
		for (int i = 0; i < actualNumberBookmarks; i++) {
			
			OntologySource source = bookmarks.get(i);
			prefs.put(bookmarkKey + i, source.getSource());
			
		}
		
		this.storedNumberBookmarks = bookmarks.size();
		prefs.putInt(numberOfBookmarksKey, this.storedNumberBookmarks);
		
	}

	/**
	 * @return the recents
	 */
	public List<OntologySource> getRecents() {
		
		return recents;
		
	}

	/**
	 * @param recents the recents to set
	 */
	public void setRecents(List<OntologySource> recents) {
		
		this.recents = recents;
		
		int actualNumberRecents = recents.size();
		
		for (int i = 0; i < actualNumberRecents; i++) {
			
			OntologySource source = recents.get(i);
			prefs.put(recentKey + i, source.getSource());
			
		}

		this.storedNumberRecents = recents.size();
		prefs.putInt(numberOfRecentsKey, this.storedNumberRecents);
		
	}

}
