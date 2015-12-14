package extensionpoints;

public interface ISearchEvent {

	/**
	 * Returns all the elements existing in the SearchIn Combo
	 * 
	 * @return String[] SearchIn Elements
	 */
	public String[] getSearchInElements();

	/**
	 * Returns all the elements existing in the SearchSpecific Combo if
	 * SearchSpecific is not disposed, else returns null
	 * 
	 * @return String[] SearchSpecific Elements
	 */
	public String[] getSearchSpecificElements();

	/**
	 * Checks if Advanced button is checked
	 * 
	 * @return boolean Advanced Button Checked
	 */
	public boolean isAdvancedSelected();

	/**
	 * Checks if SearchSpecific Combo is Disposed
	 * 
	 * @return boolean SearchSpecific is Disposed
	 */
	public boolean isSearchSpecificDisposed();

	/**
	 * Returns all the elements existing is the SearchFor Combo if advanced
	 * button is checked, else returns null
	 * 
	 * @return String[] SearchFor Elements
	 */
	public String[] getSearchForElements();

	/**
	 * Returns all Attributes checked of element of SearchFor Combo if advanced
	 * button is checked and attributes buttons are not disposed, else return
	 * null
	 * 
	 * @return String[] Attributes of SearchFor Elements
	 */
	public String[] getSearchForAtributes();

	/**
	 * Checks if elements attributes are disposed
	 * 
	 * @return boolean Attributes are Disposed
	 */
	public boolean isAtributesDisposed();

	/**
	 * Adds a new ISearchEventListener element
	 * 
	 * @param listener
	 *            (non-null) Listener element
	 */
	public void addListener(ISearchEventListener listener);

	/**
	 * Removes a ISearchEventListener element
	 * 
	 * @param listener
	 *            (non-null) Listener element
	 */
	public void removeListener(ISearchEventListener listener);

}
