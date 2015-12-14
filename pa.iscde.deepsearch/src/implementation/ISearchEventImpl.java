package implementation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import activator.SearchActivator;
import composites.AdvancedComposite;
import composites.MainSearchView;
import composites.SearchComposite;
import extensionpoints.ISearchEvent;
import extensionpoints.ISearchEventListener;
import extensionpoints.Item;

public class ISearchEventImpl implements ISearchEvent {

	@Override
	public void addListener(ISearchEventListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		SearchActivator.getActivatorInstance().addListener(listener);
	}

	@Override
	public void removeListener(ISearchEventListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		SearchActivator.getActivatorInstance().removeListener(listener);
	}

	@Override
	public String[] getSearchInElements() {
		return SearchComposite.getSearchCompositeInstance().getSearchIn().getComboBox_search().getItems();
	}

	@Override
	public String[] getSearchSpecificElements() {
		if (!isSearchSpecificDisposed()) {
			return SearchComposite.getSearchCompositeInstance().getSearchIn().getComboBox_searchSpecific().getItems();
		}
		return null;
	}

	@Override
	public boolean isAdvancedSelected() {
		return SearchComposite.getSearchCompositeInstance().getAdvanced().getSelection();
	}

	@Override
	public boolean isSearchSpecificDisposed() {
		return SearchComposite.getSearchCompositeInstance().getSearchIn().getComboBox_searchSpecific().isDisposed();
	}

	@Override
	public String[] getSearchForElements() {
		if (isAdvancedSelected()) {
			return AdvancedComposite.getAdvancedInstance().getComboSearchFor().getComboBox_search().getItems();
		}
		return null;
	}

	@Override
	public String[] getSearchForAtributes() {
		if (isAdvancedSelected() && !isAtributesDisposed()) {
			return AdvancedComposite.getAdvancedInstance().getComboSearchFor().getMyButtons();
		}
		return null;
	}

	@Override
	public boolean isAtributesDisposed() {
		return AdvancedComposite.getAdvancedInstance().getComboSearchFor().getIsDisposed();
	}

	@Override
	public Map<String, LinkedList<Item>> getResults() {
		return MainSearchView.getInstance().getResults();
	}

	@Override
	public Collection<String> getResultParents() {
		return MainSearchView.getInstance().getParentResults();
	}

}
