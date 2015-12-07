package api;

public interface SearchServices {

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
