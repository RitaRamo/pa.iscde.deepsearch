package api;

public interface ISearchEvent {

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
