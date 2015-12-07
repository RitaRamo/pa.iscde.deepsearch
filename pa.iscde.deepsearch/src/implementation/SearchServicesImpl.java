package implementation;

import api.SearchServices;
import composites.SearchComposite;

public class SearchServicesImpl implements SearchServices {

	public SearchServicesImpl() {
	}

	@Override
	public String getComboElement(int index) {
		if (index < getComboElements().length) {
			return getComboElements()[index];
		} else {
			return "";
		}
	}

	@Override
	public String[] getComboElements() {
		return SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_search().getItems();
	}

	@Override
	public String getSpecificElement(int index) {
		if (index < getSpecificElements().length) {
			return getSpecificElements()[index];
		} else {
			return "";
		}
	}

	@Override
	public String[] getSpecificElements() {
		return SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_searchSpecific().getItems();
	}

	@Override
	public boolean advancedButtonIsSelected() {
		return SearchComposite.getSearchCompositeInstance().getAdvanced().getSelection();
	}

}
