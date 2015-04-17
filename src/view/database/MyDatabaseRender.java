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

package view.database;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.database.Column;
import model.database.Table;

/**
 * Render to display the database tree
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MyDatabaseRender extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 532093803954905531L;
	
	private ImageIcon iconPrimaryKey = new ImageIcon();
	private ImageIcon iconForeignKey = new ImageIcon();
	private ImageIcon iconPrimaryForeignKey = new ImageIcon();
	private ImageIcon iconSimple = new ImageIcon();
	private ImageIcon iconTable = new ImageIcon();
	
	private String start = null;
	
	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start.toLowerCase();
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		Object nodeObject = ((DefaultMutableTreeNode)value).getUserObject();
		
		String name = null;
		

		if(nodeObject instanceof Column) {
			String type = ((Column)nodeObject).getDataType();
			if (type.equals("PRIMARY")) {
				setIcon(iconPrimaryKey);
			}
			else if (type.equals("FOREIGN")) {
				setIcon(iconForeignKey);
			}
			else if (type.equals("PRIMARYFOREIGN")) {
				setIcon(iconPrimaryForeignKey);
			}
			else {
				setIcon(iconSimple);
			}

		}
		else {
			setIcon(iconTable);
		}
		
		if (nodeObject instanceof Column) {
			name = ((Column) nodeObject).getColumnName().toLowerCase();
		}
		else if (nodeObject instanceof Table) {
			name = ((Table) nodeObject).getTableName().toLowerCase();
		}
		else {
			name = nodeObject.toString().toLowerCase();
		}
		
		if (start != null && !start.isEmpty() && name.startsWith(start)) {
			setOpaque(true);
			if (selected) {
				setBackground(Color.BLUE);
			}
			else {
				setBackground(Color.CYAN);
			}
		}
		else {
			setOpaque(false);
			if (selected) {
				setBackground(backgroundNonSelectionColor);
			}
			else {
				setBackground(backgroundNonSelectionColor);
			}
		}

		return this;
		 
	}

}
