package pa.iscde.deepsearch;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class AdvancedComposite extends Composite {

	private AdvancedComposite instance = this;
	private static final String NULL = "";
	private static final String PACKAGE = SearchInEnum.Package.toString();
	private static final String CLASS = SearchInEnum.Class.toString();
	private static final String METHOD = SearchInEnum.Method.toString();
	private static final String ATRIBUTE = SearchInEnum.Atribute.toString();
	private static final String[] comboItems = new String[] { NULL, PACKAGE, CLASS, METHOD, ATRIBUTE };
	private SearchFor comboSearchFor;

	public AdvancedComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		GridLayout gridLayout = new GridLayout(2, false);
		setLayout(gridLayout);
		comboSearchFor = new SearchFor(instance, comboItems);
	}

	public SearchFor getComboSearchFor() {
		return comboSearchFor;
	}

	public class SearchFor extends ComboBox_Autocompleted {

		private final String[] ButtonsName_Class = new String[] { "abstract", "interface", "enum" };
		private final String[] ButtonsName_Method = new String[] { PACKAGE, CLASS, METHOD, ATRIBUTE };
		private final String[] ButtonsName_Atribute = new String[] { "public", "private", "protected", "static",
				"abstract" };
		private Composite parent;
		private ArrayList<Button> myButtons = new ArrayList<Button>();
		private ArrayList<String> itemsSelected = new ArrayList<String>();

		public SearchFor(Composite parent, String[] comboItems) {
			super(parent, "Search For: ", comboItems);
			this.parent = parent;
		}

		@Override
		protected void showRelatedSpecifications() {
			disposeButtons();
			
			if (hasAlreadySelected) {
				switch (itemSelected) {
				case 2:
					addButtons(ButtonsName_Class);
					break;
				case 3:
					addButtons(ButtonsName_Method);
					break;
				case 4:
					addButtons(ButtonsName_Atribute);
					break;
				default:
					break;
				}
				parent.layout();
			}
		}

		private void addButtons(String[] checkButtons_Name) {
			for (int i = 0; i < checkButtons_Name.length; i++) {
				Button newButton = new Button(parent, SWT.CHECK);
				newButton.setText(checkButtons_Name[i]);
				myButtons.add(newButton);
			}
		}

		public ArrayList<String> getButtonsSelected() {
			for (Button button : myButtons) {
				if (button.getSelection() == true) {
					itemsSelected.add(button.getText());
				}
			}
			return itemsSelected;
		}
		
		public void clearSelected() {
			itemsSelected.clear();
		}

		public boolean buttonsSelected() {
			return itemsSelected.size() > 0;
		}

		public void disposeButtons() {
			for (Button myButton : myButtons) {
				if (myButton != null) {
					myButton.dispose();
				}
			}
			myButtons.clear();
		}
	}
}
