package extensionpoints;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Rita Ramos
 *
 *         ExtensionPoint for populating results from the search and show them
 *         in preview for each selection/double-click
 */
public interface OutputPreview extends ISearch {

	/**
	 * Obtains the parents for output hierarchy
	 * 
	 * @return Collection<Item> (non-null) parent Items (ex: Package)
	 */
	public Collection<Item> getParents();

	/**
	 * Obtains the children of the parent Item
	 * 
	 * @param parent
	 *            (non-null) parent Item (ex:Package)
	 * @return Collection<Item> (non-null) children of the parent Item (ex:
	 *         pt.iscte.pidesco.javaeditor)
	 */
	public Collection<Item> getChildren(String parent);

	/**
	 * Defines what is to be shown when double-clicking the Item
	 * 
	 * @return Item selected with double-click. The attributes of the Item (ex:
	 *         getImage) can be used to what should be shown when
	 *         double-clicking the Item
	 */
	public void doubleClick(Item item);

}
