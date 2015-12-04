package api;

public interface IAdvancedSearch {

	/**
	 * Invoked to add new Combo options to search.
	 * @param option_name to insert on existing Combo
	 */
	public void addComboElement(String option_name);
	
	/**
	 * Invoked to add array of elements to combo instead of only one.
	 * @param combo_elements String[] array of elements
	 */
	public void addArrayElementsToCombo(String[] combo_elements);
	
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
	
	
	
}
