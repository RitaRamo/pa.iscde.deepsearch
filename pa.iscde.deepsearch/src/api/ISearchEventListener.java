package api;

public interface ISearchEventListener {

	/**
	 * Invoked when button is selected and gives user the information that was
	 * used in the search
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
	 * @param search_atribute
	 *            Atribute of SearchFor element (empty if disposed)
	 * 
	 */
	public void widgetSelected(String searched, String searchIn_element, String specific_element,
			String searchFor_element, String search_atribute);

}
