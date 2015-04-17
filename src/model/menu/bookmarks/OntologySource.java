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

/**
 * Represents an ontology source (URL or File)
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class OntologySource {

	private String source;

	/**
	 * @return the source of the ontology
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param paramSource
	 * 			 the new source of the ontology
	 */
	public void setSource(String paramSource) {
		this.source = paramSource;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			
			return false;
			
		}
		if (!(obj instanceof OntologySource)) {
			
			return false;
			
		}
		OntologySource otherSource = (OntologySource) obj;
		
		return this.getSource().equals(otherSource.getSource());
		
	}
	
}
