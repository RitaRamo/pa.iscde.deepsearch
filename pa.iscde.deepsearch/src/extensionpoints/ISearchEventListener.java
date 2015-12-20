package extensionpoints;

import java.util.ArrayList;

public interface ISearchEventListener {

	/**
	 * Invoked when button is selected and gives the information that was used
	 * in the search
	 * 
	 * @param searched
	 *            String to be searched
	 * @param searchIn_element
	 *            Element of SearchIn used in the search
	 * @param specific_element
	 *            Element of Specific Combo used in the search (empty if
	 *            disposed)
	 * @param searchFor_element
	 *            Element of SearchFor in the advanced search (empty if
	 *            disposed)
	 * @param search_atributes
	 *            Collection of Attributes of SearchFor element (can be null if
	 *            disposed or no buttons selected)
	 * 
	 */
	public void widgetSelected(String text_Search, String text_SearchInCombo, String specificText_ComboSearchIn,
			String text_AdvancedCombo, ArrayList<String> buttonsSelected_AdvancedCombo);

}
