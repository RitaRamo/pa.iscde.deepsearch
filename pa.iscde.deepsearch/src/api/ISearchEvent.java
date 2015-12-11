package api;

public interface ISearchEvent {

	public String[] getSearchInElements();
	
	public String[] getSearchSpecificElements();
	
	public boolean isAdvanceSelected();
	
	public boolean isSearchSpecificDisposed();
	
	public String[] getSearchForElements();
	
	public String[] getSearchForAtributes();
	
	public boolean isAtributesDisposed();
	
	/**
	 * Adds a new ISearchEventListener element
	 * 
	 * @param listener
	 *            Listener element
	 */
	public void addListener(ISearchEventListener listener);

	/**
	 * Removes a ISearchEventListener element
	 * 
	 * @param listener
	 *            Listener element
	 */
	public void removeListener(ISearchEventListener listener);

}
