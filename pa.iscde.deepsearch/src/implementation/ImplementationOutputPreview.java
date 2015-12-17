package implementation;

import java.io.File;
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

public class ImplementationOutputPreview implements OutputPreview {

	private ProjectBrowserServices browser_search = SearchActivator.getActivatorInstance().getBrowserService();;
	private JavaEditorServices editor_search = SearchActivator.getActivatorInstance().getEditorService();
	private String searchText;
	private String text_SearchInCombo = "";
	private String specificText_ComboSearchIn;
	private DeepSearchVisitor visitor;
	private PackageElement root_package = (PackageElement) browser_search.getRootPackage();
	private String text_AdvancedCombo = "";

	@Override
	public LinkedList<String> getParents() {
		LinkedList<String> parents = new LinkedList<String>();
		parents.add("Package");
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
		case "Package":
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
	public void searchForScanner(String searchText, String text_AdvancedCombo, ArrayList<String> buttonsSelected,
			String text_SearchInCombo, String specificText_ComboSearchIn) {
		this.text_SearchInCombo = text_SearchInCombo;
		this.searchText = searchText;
		this.specificText_ComboSearchIn = specificText_ComboSearchIn;
		this.text_AdvancedCombo = text_AdvancedCombo;
		
		if (text_AdvancedCombo!="") {
			searchFor(buttonsSelected);
		} else if (text_SearchInCombo !="" ) {
			searchIn();
		} else {
			visitor = new DeepSearchVisitor(searchText, SearchEnumType.SearchInPackage, "");
			root_package.traverse(visitor);
		}
	}

	private void searchIn() {
		if (text_SearchInCombo.equals("Package")) {
			searchIn_orForPackage(SearchEnumType.SearchInPackage);
		} else if (text_SearchInCombo.equals("Class")) {
			visitor = new DeepSearchVisitor(searchText, SearchEnumType.SearchInClass, "");
			searchInClass_orSearchFor();
		} else if (text_SearchInCombo.equals("Method")) {
			searchIn_orForMethod(SearchEnumType.SearchInMethod, "");
		}
	}

	private void searchFor(ArrayList<String> buttonsSelected) {
		if (text_AdvancedCombo.equals("Package")) {
			searchIn_orForPackage(SearchEnumType.SearchForPackage);
		} else if (text_AdvancedCombo.equals("TypeDeclaration")) {
			searchAdvanced(SearchEnumType.SearchForClass, buttonsSelected);
		} else if (text_AdvancedCombo.equals("Method")) {
			searchAdvanced(SearchEnumType.SearchForMethod, buttonsSelected);
		} else if (text_AdvancedCombo.equals("Field")) {
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
			if (!text_SearchInCombo.equals("") && !specificText_ComboSearchIn.equals("")) {
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
			if (text_SearchInCombo.equals("Class") && !specificText_ComboSearchIn.equals("")) {
				ClassElement classToVisit = visitor.getClass(specificText_ComboSearchIn, root_package);
				visitor.visitClass(classToVisit);
			} else if (text_SearchInCombo.equals("Package") && !specificText_ComboSearchIn.equals("")) {
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
		//return text_SearchInCombo.equals("") ||  
		return true;
		//itemSelected_ComboSearchFor <= 0 || itemSelected_ComboSearchFor >= text_SearchInCombo;
	}

	@Override
	public void doubleClick(Item e) {
		editor_search.openFile((File) e.getSpecialData());
		//editor_search.selectText(e.getFile(), e.getPreviewText().indexOf(searchText),
			//	e.getPreviewText().indexOf(searchText) + searchText.length());
	}

}
