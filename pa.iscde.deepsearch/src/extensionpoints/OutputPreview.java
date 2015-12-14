package extensionpoints;

import java.util.ArrayList;
import java.util.Collection;

public interface OutputPreview {

	Collection<String> getParents();

	Collection<Item> getChildren(String parent);

	void searchForScanner(String searchText, int itemSelected_AdvancedCombo, ArrayList<String> buttonsSelected,
			int itemSelected_ComboSearchIn, String specificText_ComboSearchIn);

	void doubleClick(Item e);
}
