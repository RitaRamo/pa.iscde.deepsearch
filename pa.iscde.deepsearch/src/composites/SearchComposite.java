package composites;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import activator.SearchActivator;
import enums.SearchInEnum;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import view.ComboBox_Autocompleted;

public class SearchComposite extends Composite {

	private Composite SearchComposite = this;
	private Button search_button;
	private ProjectBrowserServices browser_search;
	private Text search_field;
	private Button advanced;
	private SearchIn search_in;
	private static final String NULL = "";
	private static final String PACKAGE = SearchInEnum.Package.toString();
	private static final String CLASS = SearchInEnum.Class.toString();
	private static final String METHOD = SearchInEnum.Method.toString();
	private static final String[] comboItems = new String[] { NULL, PACKAGE, CLASS, METHOD };

	public SearchComposite(Composite parent, int style) {
		super(parent, style);
		browser_search = SearchActivator.getActivator().getBrowserService();
		createContents();
	}

	private void createContents() {
		setLayout(new GridLayout(2, false));

		Label search_field_label = new Label(this, SWT.NONE);
		search_field_label.setText("Search:               ");

		search_field = new Text(this, SWT.BORDER);
		search_field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		search_in = new SearchIn(this, comboItems);

		advanced = new Button(this, SWT.CHECK);
		advanced.setText("   Advanced Search");
		advanced.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		search_button = new Button(this, SWT.NONE);
		search_button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		search_button.setText("Search");
	}
	
	public SearchIn getSearchIn() {
		return search_in;
	}

	public Button getSearchButton() {
		return search_button;
	}

	public Text getSearchField() {
		return search_field;
	}

	public Button getAdvanced() {
		return advanced;
	}

	public class SearchIn extends ComboBox_Autocompleted {
		private Combo comboBox_searchSpecific = null;
		private Label searchSpecific_Label;
		private Composite parent;

		public SearchIn(Composite parent, String[] comboItems) {
			super(parent, "Search in: ", comboItems);
			this.parent = parent;
		}

		@Override
		protected void showRelatedSpecifications() {
			if (comboBox_searchSpecific != null) {
				comboBox_searchSpecific.dispose();
				searchSpecific_Label.dispose();
				SearchComposite.layout();
			}
			if (hasAlreadySelected) {
				if (itemSelected == 1 || itemSelected == 2) {
					searchSpecific_Label = new Label(parent, SWT.NONE);
					searchSpecific_Label.setText("Search Specific:");
					
					comboBox_searchSpecific = new Combo(parent, SWT.BORDER);
					comboBox_searchSpecific.setItems(comboItems());
					
					comboBox_searchSpecific.moveBelow(this.getComboBox_search());
					searchSpecific_Label.moveBelow(this.getComboBox_search());
					fillSearchSpecific();
					SearchComposite.layout();
				}
			}
		}

		private void fillSearchSpecific() {
			browser_search.addListener(new ProjectBrowserListener.Adapter() {
				@Override
				public void selectionChanged(Collection<SourceElement> selection) {
					SourceElement element = (SourceElement) selection.toArray()[0];

					if (itemSelected == 1 && element.isPackage()) {
						comboBox_searchSpecific.setText(element.getName());

					} else if (itemSelected == 2 && element.isClass()) {
						comboBox_searchSpecific.setText(element.getName());

					} else {
						comboBox_searchSpecific.setText("");
					}
				}

			});
		}

		private LinkedList<String> comboItems(PackageElement myPackage, LinkedList<String> comboItemsNames) {
			for (SourceElement e : myPackage) {
				if (e.isPackage()) {
					if (itemSelected == 1)
						comboItemsNames.add(e.getName());
					comboItems((PackageElement) e, comboItemsNames);
				} else if (e.isClass()) {
					if (itemSelected == 2)
						comboItemsNames.add(e.getParent().getName() + "." +e.getName());
				}
			}
			return comboItemsNames;
		}

		private String[] comboItems() {
			LinkedList<String> comboItems_names = comboItems(browser_search.getRootPackage(), new LinkedList<String>());
			String[] myItemsNames_asArray = new String[comboItems_names.size()];
			for (int i = 0; i < myItemsNames_asArray.length; i++) {
				myItemsNames_asArray[i] = comboItems_names.get(i);
			}
			return myItemsNames_asArray;
		}
		
		public String getText_ofSearchSpecific(){
			return comboBox_searchSpecific.getText();
		}
	}

}
