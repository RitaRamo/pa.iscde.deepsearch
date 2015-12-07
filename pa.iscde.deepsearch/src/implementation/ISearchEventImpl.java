package implementation;

import activator.SearchActivator;
import api.ISearchEvent;
import api.ISearchEventListener;

public class ISearchEventImpl implements ISearchEvent {

	@Override
	public void addListener(ISearchEventListener listener) {
		SearchActivator.getActivatorInstance().addListener(listener);
	}

	@Override
	public void removeListener(ISearchEventListener listener) {
		SearchActivator.getActivatorInstance().removeListener(listener);

	}

}
