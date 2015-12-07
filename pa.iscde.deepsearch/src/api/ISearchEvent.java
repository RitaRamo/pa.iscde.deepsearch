package api;

public interface ISearchEvent {

	public void addListener(ISearchEventListener listener);

	public void removeListener(ISearchEventListener listener);

}
