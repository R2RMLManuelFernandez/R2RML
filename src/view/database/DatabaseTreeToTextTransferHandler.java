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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import model.database.Column;
import model.r2rmlmapping.triplesMap.TriplesMap;
import control.database.load.TransferableDatabaseNode;

/**
 * Implements transferhandler between the datbase view and the view to add a mapping term
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class DatabaseTreeToTextTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 636225815038994043L;
	
	//speciies the supported data flavors
    public static final DataFlavor BBDD_NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");
    @SuppressWarnings("unused")
	private DataFlavor[] flavors = { BBDD_NODE_FLAVOR };

    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
     */
    protected Transferable createTransferable(JComponent c) {

        JTree tree = (JTree)c;
        TreePath path = tree.getSelectionPath();
        if(path != null) {           
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
            try {
				return new TransferableDatabaseNode(node);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return null;
    }

    /**
     * Defensive copy used in createTransferable. 
     * 
     * @param node
     * @return
     */
    @SuppressWarnings("unused")
	private DefaultMutableTreeNode copy(TreeNode node) {
        return new DefaultMutableTreeNode(node);
    }
  
    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
     */
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }
    
/*    protected void exportDone(JComponent source, Transferable data, int action) {

    }*/
	
    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
     */
    public boolean canImport(TransferHandler.TransferSupport info) {

    	if(!info.isDrop()) {
    		return false;
    	}
    
    	info.setShowDropLocation(true);
    	if(!info.isDataFlavorSupported(BBDD_NODE_FLAVOR)) {
    		return false;
    	}
		return true;

	}
	
    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
     */
    public boolean importData(TransferHandler.TransferSupport info) {
    	
        if(!canImport(info)) {
            return false;
        }
        // Extract transfer data.
        DefaultMutableTreeNode node = null;
        try {
            Transferable t = info.getTransferable();
            @SuppressWarnings("unused")
			DataFlavor[] df = t.getTransferDataFlavors();
            node = (DefaultMutableTreeNode)t.getTransferData(BBDD_NODE_FLAVOR);
        } catch(UnsupportedFlavorException ufe) {
            System.out.println("UnsuppportedFlavor: " + ufe.getMessage());
        } catch(java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
        
        // Add data to model.
        MappingTextField databaseTextField = (MappingTextField) info.getComponent();
        Column databaseColumn = (Column) node.getUserObject();
        TriplesMap mapping = databaseTextField.getModel();
//		System.out.println("Se añade la columna de la BBDD");
        databaseTextField.setText(databaseColumn.getColumnName());
        mapping.setDatabaseColumn(databaseColumn);
//		System.out.println("Se notifica la columna de la BBDD: " + databaseColumn.getColumnName());
        return true;
    }
    
}
