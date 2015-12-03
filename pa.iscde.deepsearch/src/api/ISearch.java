package api;

public interface ISearch {

	/**
	 * Invoked when the user wants to add new Combo options to search.
	 * @param option_name to insert on existing Combo
	 */
	public void addSearchInComboOption(String option_name);
	
	/**
	 * Invoked when the user wants to add new Search Specific options to search.
	 * @param specific_name to insert on existing Check Buttons
	 */
	public void addSearchInSpecificOption(String specific_name);
	
	
	
}
