package api;

public interface ISearchEventListener {

	/**
	 * Invoked when button is selected and gives user the information that was
	 * used in the search
	 * 
	 * @param searched
	 *            String to be searched
	 * @param combo_element
	 *            Element of Combo used in the search
	 * @param specific_element
	 *            Element of Specific Combo used in the search
	 * 
	 */
	public void widgetSelected(String searched, String combo_element, String specific_element);

}
