package pa.iscde.deepsearch;

import java.io.File;
import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public interface OutputPreview {
	
	
	
	public void addTreeElement(TreeEnum treeEnum, String treeItemName, File fileToOpen_DoubleClickItem, String fileContent_SelecteItem, String searchResult);
	
	
	
//	/**
//	 * Invoked whenever a mouse double-click is performed on a source element of the tree.
//	 * @param element (non-null) element under the double-click
//	 */
//	void doubleClick(SourceElement element);
//	
//	/**
//	 * Invoked whenever the selection of elements in the tree changes.
//	 * @param selection (non-null) collection of elements of the new selection, which may be empty
//	 */
//	void selectionChanged(Collection<SourceElement> selection);
//	
}
