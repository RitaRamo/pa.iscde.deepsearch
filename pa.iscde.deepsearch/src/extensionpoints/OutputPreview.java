package extensionpoints;

import java.util.ArrayList;
import java.util.Collection;

public interface OutputPreview {

	public Collection<String> getParents();

	public Collection<Item> getChildren(String parent);

	public void searchForScanner(String searched, int itemSelected_AdvancedCombo, ArrayList<String> buttonsSelected,
			int itemSelected_ComboSearchIn, String specificText_ComboSearchIn);

	public void doubleClick(Item e);
}
