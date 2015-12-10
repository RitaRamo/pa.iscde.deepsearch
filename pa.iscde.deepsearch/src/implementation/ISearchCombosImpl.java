package implementation;

import api.ISearchCombos;
import composites.SearchComposite;

public class ISearchCombosImpl implements ISearchCombos {
	
	private SearchComposite search_composite;

	public ISearchCombosImpl() {
		search_composite = SearchComposite.getSearchCompositeInstance();
	}

	@Override
	public String getComboElement(int index) {
		if (index < getComboElements().length && index >= 0) {
			return getComboElements()[index];
		}
		return "Not Found!";
	}

	@Override
	public String[] getComboElements() {
		if (search_composite != null) {
			return search_composite.getSearchInCombo().getComboBox_search().getItems();
		}
		return new String[0];
	}

	@Override
	public String getSpecificElement(int index) {
		if (index < getSpecificElements().length && index >= 0) {
			return getSpecificElements()[index];
		}
		return "Not Found!";
	}

	@Override
	public String[] getSpecificElements() {
		if (search_composite != null) {
			if (search_composite.getSearchInCombo().getComboBox_searchSpecific() != null) {
				if (!search_composite.getSearchInCombo().getComboBox_searchSpecific()
						.isDisposed()) {
					return search_composite.getSearchInCombo().getComboBox_searchSpecific()
							.getItems();
				}
			}
		}
		return new String[0];
	}

	@Override
	public boolean advancedButtonIsSelected() {
		return search_composite.getAdvanced().getSelection();
	}

}
