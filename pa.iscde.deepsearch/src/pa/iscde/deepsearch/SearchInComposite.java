package pa.iscde.deepsearch;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SearchInComposite  {
	private Combo comboBox_searchIn;
	private int itemSelected;
	private boolean hasAlreadySelected = false;
	private static final String NULL = "";
	private static final String PACKAGE = SearchInEnum.Package.toString();
	private static final String CLASS = SearchInEnum.Class.toString();
	private static final String METHOD = SearchInEnum.Method.toString();
	private Composite parent;

	private static final String[] comboItems = new String[] { NULL, PACKAGE, CLASS, METHOD };

	public SearchInComposite(Composite parent) {
	
		// TODO Auto-generated constructor stub
		this.parent=parent;
		createContents();
	}

	private void createContents() {
		

		Label search_in_label = new Label(parent, SWT.NONE);
		search_in_label.setText("Search in: ");
		comboBox_searchIn = new Combo(parent, SWT.BORDER);
		comboBox_searchIn.setItems(comboItems);

		comboBox_searchIn.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!hasAlreadySelected) {

					autoCompleteText(comboBox_searchIn.getText());

				} else {

					if (hasSelectedOtherItemWithMouse()) {
						itemSelected = comboBox_searchIn.getSelectionIndex();
					} else { // writtenOtherOption

						int length_ofTextInserted = comboBox_searchIn.getText().length();

						if (length_ofTextInserted < comboItems[itemSelected].length()) {
							if (length_ofTextInserted == 1)
								autoCompleteText(comboBox_searchIn.getText());
							else { // deleting
								itemSelected = 0;
								hasAlreadySelected = false;
							}
						}

					}
				}
				comboBox_searchIn.select(itemSelected);
			}
		});

	}

	private void autoCompleteText(String textInserted) {
		if (textInserted.length() > 0) {
			if (textInserted.equalsIgnoreCase(PACKAGE.substring(0, textInserted.length()))) {
				itemSelected = 1;
			}

			else if (textInserted.equalsIgnoreCase(CLASS.substring(0, textInserted.length()))) {
				itemSelected = 2;
			}

			else if (textInserted.equalsIgnoreCase(METHOD.substring(0, textInserted.length()))) {
				itemSelected = 3;
			} else {
				itemSelected = 0;
			}

			if (itemSelected > 0)
				hasAlreadySelected = true;
		}
	}

	private boolean hasSelectedOtherItemWithMouse() {
		return comboBox_searchIn.getSelectionIndex() != itemSelected && comboBox_searchIn.getSelectionIndex() >= 0;
	}

}