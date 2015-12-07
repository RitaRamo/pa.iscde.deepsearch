package api;

public interface SearchServices {

	/**
	 * Invoked to add new Combo options to search.
	 * @param option_name to insert on existing Combo
	 */
	public void addComboElement(String option_name);

	/**
	 * Invoked to add new Search Specific options to search.
	 * @param specific_name to insert on existing Check Buttons
	 */
	public void addSpecificElement(String specific_name);

	/**
	 * Invoked to add array of elements to combo instead of only one.
	 * @param combo_elements String[] array of elements
	 */
	public void addArrayElementsToCombo(String[] combo_elements);

	/**
	 * Invoked to add array of elements to SearchIn Specific instead of only one.
	 * @param specific_elements String[] array of elements
	 */
	public void addArrayElementsToSpecific(String[] specific_elements);

	/**
	 * Invoked to get an element from Combo.
	 * @param index of Combo Element
	 * @return String Element of Combo
	 */
	public String getComboElement(int index);

	/**
	 * Invoked to get all elements from Combo.
	 * @return String[] all Combo Elements
	 */
	public String[] getComboElements();
	
	/**
	 * Invoked to get an element from Specific.
	 * @param index of Specific Element
	 * @return String Element of Specific
	 */
	public String getSpecificElement(int index);

	/**
	 * Invoked to get all elements from Specific Search.
	 * @param combo_element to specify the searched Combo Element
	 */
	public String[] getSpecificElements();

	/**
	 * Invoked to check if Advanced button is selected.
	 * @return boolean Advanced button selected
	 */
	public boolean advancedButtonIsSelected();

}
