package implementation;

import api.ISearch;
import composites.SearchComposite;

public class ISearchImpl implements ISearch {

	private SearchComposite search;

	public ISearchImpl(SearchComposite search) {
		this.search = search;
	}

	@Override
	public void addSearchInComboOption(String option_name) {
		search.getSearchInCombo().getComboBox_search().add(option_name);
	}

	@Override
	public void addSearchInSpecificOption(String specific_name) {
		search.getSearchInCombo().getComboBox_search().add(specific_name);
	}

	@Override
	public String getComboElement(int index) {
		if (index < getComboElements().length) {
			return getComboElements()[index];
		} else {
			return null;
		}
	}

	@Override
	public String[] getComboElements() {
		return search.getSearchInCombo().getComboBox_search().getItems();
	}

	@Override
	public String[] getSpecificElements(String combo_element) {
		return search.getSearchInCombo().getComboBox_searchSpecific().getItems();
	}

	@Override
	public boolean advancedButtonIsSelected() {
		return search.getAdvanced().getSelection();
	}

}
