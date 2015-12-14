package auxiliary;

import java.util.ArrayList;
import java.util.LinkedList;

import activator.SearchActivator;
import enums.SearchEnumType;
import extensionpoints.Item;
import extensionpoints.OutputPreview;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import visitor.DeepSearchVisitor;

public class TestImplementationOutput implements OutputPreview {

	String[] split;
	ProjectBrowserServices browser_search = SearchActivator.getActivatorInstance().getBrowserService();;
	JavaEditorServices editor_search = SearchActivator.getActivatorInstance().getEditorService();
	private String searchText;
	private int itemSelected_ComboSearchIn = -1;
	private String specificText_ComboSearchIn;
	private DeepSearchVisitor visitor;
	private PackageElement root_package = (PackageElement) browser_search.getRootPackage();
	private int itemSelected_ComboSearchFor = -1;

	@Override
	public LinkedList<String> getParents() {
		LinkedList<String> parents = new LinkedList<String>();
		parents.add("Parent");
		parents.add("Import");
		parents.add("Class");
		parents.add("Enum");
		parents.add("Interface");
		parents.add("Method");
		parents.add("Field");

		return parents;
	}

	@Override
	public LinkedList<Item> getChildren(String parent) {

		switch (parent) {
		case "Parent":
			return visitor.getASTVisitor_deepSearchMy().getPackageItems();
		case "Class":
			return visitor.getASTVisitor_deepSearchMy().getClassItems();
		case "Enum":
			return visitor.getASTVisitor_deepSearchMy().getEnumItems();
		case "Interface":
			return visitor.getASTVisitor_deepSearchMy().getInterfaceItems();
		case "Import":
			return visitor.getASTVisitor_deepSearchMy().getImportItems();
		case "Method":
			return visitor.getASTVisitor_deepSearchMy().getMethodItems();
		case "Field":
			return visitor.getASTVisitor_deepSearchMy().getFieldItems();
		default:
			return visitor.getASTVisitor_deepSearchMy().getClassItems();

		}
	}

	@Override
	public void searchForScanner(String searchText, int itemSelected_AdvancedCombo, ArrayList<String> buttonsSelected,
			int itemSelected_ComboSearchIn, String specificText_ComboSearchIn) {
		this.itemSelected_ComboSearchIn = itemSelected_ComboSearchIn;
		this.searchText = searchText;
		this.specificText_ComboSearchIn = specificText_ComboSearchIn;
		this.itemSelected_ComboSearchFor = itemSelected_AdvancedCombo;
		if (itemSelected_AdvancedCombo > 0) {
			searchFor(buttonsSelected);
		} else if (itemSelected_ComboSearchIn > 0) {
			searchIn();
		} else {
			visitor = new DeepSearchVisitor(searchText, SearchEnumType.SearchInPackage, "");
			root_package.traverse(visitor);
		}
	}

	private void searchIn() {
		if (itemSelected_ComboSearchIn == 1) {
			searchIn_orForPackage(SearchEnumType.SearchInPackage);
		} else if (itemSelected_ComboSearchIn == 2) {
			visitor = new DeepSearchVisitor(searchText, SearchEnumType.SearchInClass, "");
			searchInClass_orSearchFor();
		} else if (itemSelected_ComboSearchIn == 3) {
			searchIn_orForMethod(SearchEnumType.SearchInMethod, "");
		}
	}

	private void searchFor(ArrayList<String> buttonsSelected) {
		if (itemSelected_ComboSearchFor == 1) {
			searchIn_orForPackage(SearchEnumType.SearchForPackage);
		} else if (itemSelected_ComboSearchFor == 2) {
			searchAdvanced(SearchEnumType.SearchForClass, buttonsSelected);
		} else if (itemSelected_ComboSearchFor == 3) {
			searchAdvanced(SearchEnumType.SearchForMethod, buttonsSelected);
		} else if (itemSelected_ComboSearchFor == 4) {
			searchAdvanced(SearchEnumType.SearchForField, buttonsSelected);
		}
	}

	private void searchAdvanced(SearchEnumType enumType, ArrayList<String> buttonsSelected) {
		if (buttonsSelected.size() > 0) {
			visitor = new DeepSearchVisitor(searchText, enumType, buttonsSelected.get(0));
			for (String advancedSpecification : buttonsSelected) {
				visitor.getASTVisitor_deepSearchMy().setAdvancedSpecifications(advancedSpecification);
				searchInClass_orSearchFor();
			}
		} else {
			visitor = new DeepSearchVisitor(searchText, enumType, "");
			searchInClass_orSearchFor();
		}
	}

	private void searchIn_orForMethod(SearchEnumType enumType, String advancedSpecifications) {
		visitor = new DeepSearchVisitor(searchText, enumType, "");
		root_package.traverse(visitor);

	}

	private void searchIn_orForPackage(SearchEnumType enumType) {
		visitor = new DeepSearchVisitor(searchText, enumType, "");
		if (isComboSearchFor_CoherentWith_ComboSearchIn()) {
			if (itemSelected_ComboSearchIn != -1 && !specificText_ComboSearchIn.equals("")) {
				for (SourceElement sourcePackage : root_package.getChildren()) {
					String packageName = sourcePackage.getParent().getName() + "." + sourcePackage.getName();
					if (packageName.equals(specificText_ComboSearchIn)) {
						((PackageElement) sourcePackage).traverse(visitor);
						break;
					}
				}
			} else {
				root_package.traverse(visitor);
			}
		}
	}

	private void searchInClass_orSearchFor() {
		if (isComboSearchFor_CoherentWith_ComboSearchIn()) {
			if (itemSelected_ComboSearchIn == 2 && !specificText_ComboSearchIn.equals("")) {
				ClassElement classToVisit = visitor.getClass(specificText_ComboSearchIn, root_package);
				visitor.visitClass(classToVisit);
			} else if (itemSelected_ComboSearchIn == 1 && !specificText_ComboSearchIn.equals("")) {
				for (SourceElement sourcePackage : root_package.getChildren()) {
					String packageName = sourcePackage.getParent().getName() + "." + sourcePackage.getName();
					if (packageName.equals(specificText_ComboSearchIn)) {
						((PackageElement) sourcePackage).traverse(visitor);
						break;
					}
				}
			} else {
				root_package.traverse(visitor);
			}
		}
	}

	private boolean isComboSearchFor_CoherentWith_ComboSearchIn() {
		return itemSelected_ComboSearchFor <= 0 || itemSelected_ComboSearchFor >= itemSelected_ComboSearchIn;
	}

	@Override
	public void doubleClick(Item e) {
		editor_search.openFile(e.getFile());
		editor_search.selectText(e.getFile(), e.getPreviewText().indexOf(searchText),
				e.getPreviewText().indexOf(searchText) + searchText.length());
	}

}
