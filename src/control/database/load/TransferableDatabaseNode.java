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

package control.database.load;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * transferable class for the database tree
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TransferableDatabaseNode implements Transferable {
	public static final DataFlavor BBDD_NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "DatabaseNode");
	private DefaultMutableTreeNode databaseNode;
	private DataFlavor[] flavors ={BBDD_NODE_FLAVOR};

    public TransferableDatabaseNode(DefaultMutableTreeNode nd) throws ClassNotFoundException {
    	databaseNode = nd;
   }  
    
	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (BBDD_NODE_FLAVOR.equals(flavor)) {
			return databaseNode;
		}
		else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return BBDD_NODE_FLAVOR.equals(flavor);
	}
}
