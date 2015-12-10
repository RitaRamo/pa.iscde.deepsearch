package implementation;

import api.ISearchCombos;
import composites.SearchComposite;

public class ISearchCombosImpl implements ISearchCombos {

	public ISearchCombosImpl() {
	}

	@Override
	public void setComboElements(String[] elements) {

	}

	@Override
	public void setSpecificComboElements(String[] elements) {

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
		if (SearchComposite.getSearchCompositeInstance() != null) {
			return SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_search().getItems();
		} else {
			System.out.println("I am Null 1");
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
		if (SearchComposite.getSearchCompositeInstance() != null) {
			if (SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_searchSpecific() != null) {
				if (!SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_searchSpecific()
						.isDisposed()) {
					return SearchComposite.getSearchCompositeInstance().getSearchInCombo().getComboBox_searchSpecific()
							.getItems();
				}
			}
		} else {
			System.out.println("I am Null 2");
		}
		return new String[0];
	}

	@Override
	public boolean advancedButtonIsSelected() {
		return SearchComposite.getSearchCompositeInstance().getAdvanced().getSelection();
	}

}
