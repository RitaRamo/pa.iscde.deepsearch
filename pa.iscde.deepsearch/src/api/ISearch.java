package api;

public interface ISearch {

	/**
	 * Invoked to add new Combo options to search.
	 * @param option_name to insert on existing Combo
	 */
	public void addSearchInComboOption(String option_name);
	
	/**
	 * Invoked to add new Search Specific options to search.
	 * @param specific_name to insert on existing Check Buttons
	 */
	public void addSearchInSpecificOption(String specific_name);
	
	/**
	 * Invoked to get an element from Combo.
	 * @param index of Combo Element
	 * @return String
	 */
	public String getComboElement(int index);
	
	/**
	 * Invoked to get all elements from Combo.
	 * @return String[] all Combo Elements
	 */
	public String[] getComboElements();
	
	/**
	 * Invoked to get all elements from Specific Search.
	 * @param combo_element to specify the searched Combo Element
	 * @return String[] all Specific Elements
	 */
	public String[] getSpecificElements(String combo_element);
	
	/**
	 * Invoked to check if Advanced button is selected.
	 * @return boolean Advanced button selected
	 */
	public boolean advancedButtonIsSelected();
	
}
