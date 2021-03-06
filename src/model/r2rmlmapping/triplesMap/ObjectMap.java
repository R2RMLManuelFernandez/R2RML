/*
 * Copyright 2015 Manuel Fern�ndez P�rez
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

package model.r2rmlmapping.triplesMap;

/**
 * Interface for object map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public interface ObjectMap {
	
	/**
	 * @return the predicateObjectMap
	 */
	public PredicateObjectMap getPredicateObjectMap();

	/**
	 * @return the type
	 */
	public String getType();

}
