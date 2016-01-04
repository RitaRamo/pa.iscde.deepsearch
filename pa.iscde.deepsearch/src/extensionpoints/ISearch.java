package extensionpoints;

import java.util.Collection;

public interface ISearch {

	/**
	 * Invoked when Search button is selected and gives the information that was
	 * used in the search
	 * 
	 * @param text_Search
	 *            String to be searched
	 * @param text_SearchInCombo
	 *            Text of SearchInCombo field used in the search
	 * @param specificText_SearchInCombo
	 *            Text of SpecificCombo of SearchInCombo used in the search
	 *            (empty if disposed)
	 * @param text_SearchForCombo
	 *            Text of SearchForCombo in the advanced search (empty if
	 *            disposed)
	 * @param buttonsSelected_SearchForCombo
	 *            Collection of ButtonsSelected of SearchForCombo (empty if
	 *            disposed or no buttons selected)
	 * 
	 */
	public void searchEvent(String text_Search, String text_SearchInCombo, String specificText_SearchInCombo,
			String text_SearchForCombo, Collection<String> buttonsSelected_SearchForCombo);

}
