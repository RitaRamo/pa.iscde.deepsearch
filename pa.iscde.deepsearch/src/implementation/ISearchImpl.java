package implementation;

import api.ISearch;
import composites.SearchComposite;

public class ISearchImpl implements ISearch {

	private SearchComposite search;

	public ISearchImpl(SearchComposite search) {
		this.search = search;
	}

	@Override
	public void addComboElement(String option_name) {
		search.getSearchInCombo().getComboBox_search().add(option_name);
	}

	@Override
	public void addSpecificElement(String specific_name) {
		search.getSearchInCombo().getComboBox_searchSpecific().add(specific_name);
	}
	
	@Override
	public void addArrayElementsToCombo(String[] combo_elements) {
		search.getSearchInCombo().getComboBox_search().setItems(combo_elements);
	}

	@Override
	public void addArrayElementsToSpecific(String[] specific_elements) {
		search.getSearchInCombo().getComboBox_searchSpecific().setItems(specific_elements);
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
	public String getSpecificElement(int index) {
		if (index < getSpecificElements().length) {
			return getSpecificElements()[index];
		} else {
			return null;
		}
	}

	@Override
	public String[] getSpecificElements() {
		return search.getSearchInCombo().getComboBox_searchSpecific().getItems();
	}

	@Override
	public boolean advancedButtonIsSelected() {
		return search.getAdvanced().getSelection();
	}

}
