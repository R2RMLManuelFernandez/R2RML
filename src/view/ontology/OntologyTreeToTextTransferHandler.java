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

package view.ontology;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import model.mapping.MappingElement;
import model.ontology.OntologyElement;
import view.addMappingItem.MappingTextField;
import control.ontology.TransferableOntologyNode;

/**
 * Implements transferhandler between the ontology view and the view to add a mapping term
 *  
 * @author Manuel Fernandez Perez
 *
 */
public class OntologyTreeToTextTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 636225815038994043L;
	
    public static final DataFlavor ONT_NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "OntologyNode");
    @SuppressWarnings("unused")
	private DataFlavor[] flavors = { ONT_NODE_FLAVOR };

    protected Transferable createTransferable(JComponent c) {

        JTree tree = (JTree)c;
        TreePath path = tree.getSelectionPath();
        if(path != null) {           
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
            try {
				return new TransferableOntologyNode(node);
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
    
    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
     */
    protected void exportDone(JComponent source, Transferable data, int action) {
/*    	dirtOntologyElement(source, action == TransferHandler.COPY_OR_MOVE);
    	System.out.println("Ensuciando modelo");*/
    }

    /* (non-Javadoc)
     * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
     */
    public boolean canImport(TransferHandler.TransferSupport info) {

    	if(!info.isDrop()) {
    		return false;
    	}
    
    	info.setShowDropLocation(true);
    	if(!info.isDataFlavorSupported(ONT_NODE_FLAVOR)) {
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
            node = (DefaultMutableTreeNode)t.getTransferData(ONT_NODE_FLAVOR);
        } catch(UnsupportedFlavorException ufe) {
            System.out.println("UnsuppportedFlavor: " + ufe.getMessage());
        } catch(java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }

        // Add data to model.
        MappingTextField textFieldOntology = (MappingTextField) info.getComponent();
        OntologyElement ontologyElement = (OntologyElement) node.getUserObject();
        MappingElement mappingElement = textFieldOntology.getModel();
//		System.out.println("Se a�ade el elemento de la ontologia");
        textFieldOntology.setText(ontologyElement.getDisplayName());
        mappingElement.setOntologyElement(ontologyElement);
//		System.out.println("Se notifica el elemento de la ontologia: " + ontologyElement.getDisplayName());
        return true;
    }
    
/*    private void dirtOntologyElement(JComponent source, boolean inMapping) {
    	
    	JTree tree = (JTree) source;
        TreePath path = tree.getSelectionPath();
        if(path != null) {           
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
            Object nodeObject = node.getUserObject();
            ((OntologyElement) nodeObject).setInMapping(true);
        	System.out.println("dirtOntologyElement: " + ((OntologyElement) nodeObject).getDisplayName() + ((OntologyElement) nodeObject).getInMapping());
        }
        tree.repaint();
    }*/

}
