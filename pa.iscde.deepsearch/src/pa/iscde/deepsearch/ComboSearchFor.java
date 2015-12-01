package pa.iscde.deepsearch;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ComboSearchFor extends ComboBox_Autocompleted {
	
	private final String[] ButtonsName_Package = new String[] { "PACKAGE", "CLASS", "METHOD", "ATRIBUTE" };
	private final String[] ButtonsName_Class = new String[] { "CLASS" };
	private final String[] ButtonsName_Method = new String[] { "PACKAGE", "CLASS", "METHOD", "ATRIBUTE" };
	private final String[] ButtonsName_Atribute = new String[] { "PACKAGE", "CLASS", "METHOD", "ATRIBUTE" };
	private Composite parent;
	private ArrayList<Button> myButtons = new ArrayList<Button>();

	public ComboSearchFor(Composite parent, String[] comboItems) {
		super(parent, "Search For: ", comboItems);
		this.parent=parent;
	}

	@Override
	protected void showRelatedSpecifications() {
		disposeButtons();
		if (hasAlreadySelected) {
			switch (itemSelected) {
			case 1:
				addButtons(ButtonsName_Package);
				break;
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

	
	public ArrayList<Button> getButtonsSelected() {
		return myButtons;
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
