package extensionpoints;

import java.util.ArrayList;
import java.util.Collection;

public interface OutputPreview {

	/**
	 * Invoked when button is selected and gives the information that was
	 * used in the search
	 * 
	 * @param text_Search (non-null)
	 *            Text of the Search field
	 *            
	 * @param text_AdvancedCombo (non-null) 
	 *            Text of the AdvancedCombo field (if empty is represented by "")
	 *            
	 * @param   buttonsSelected_AdvancedCombo (can be null, if no button selected)
	 *            Represents the buttons selected of the AdvancedCombo 
	 *            ex:interface,abstract and enum for textAdvancedCombo field with Class
	 *           
	 * @param text_SearchInCombo
	 *             Text of the AdvancedCombo field (if empty is represented by "")
	 * @param specificText_ComboSearchIn
	 *            specific class or package selected for SearchIn
	 * 
	 */

	public void search(String text_Search, String text_SearchInCombo, String specificText_ComboSearchIn, String text_AdvancedCombo, ArrayList<String> buttonsSelected_AdvancedCombo);

	
	/**
	 * obtain the parents for hierarchy output
	 * @return Collection<Item> (non-null) parents items (ex: Package)
	 */
	public Collection<Item> getParents();

	/**
	 * obtain the children of the parent item
	 * @param parent (non-null) parent item (ex:Package)
	 * @return Collection<Item> (non-null) children of the parent item (ex: pt.iscte.pidesco.javaeditor, pa.iscde.test)
	 */
	public Collection<Item> getChildren(String parent);

	
	/**
	 * defines what is to be showed when double-clicking the item
	 * @return Item selected with double-click. 
	 * The attributes of the item (ex: getImage) can be used to what should be showed when double-clicking the item
	 */
	public void doubleClick(Item e);

}
