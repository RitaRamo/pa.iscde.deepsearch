package view;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import activator.SearchActivator;
import auxiliary.TreeInstance;
import composites.AdvancedComposite;
import composites.AdvancedComposite.SearchFor;
import composites.PreviewComposite;
import composites.SearchComposite;
import composites.SearchComposite.SearchIn;
import enums.SearchEnumType;
import enums.TreeEnum;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import visitor.DeepSearchVisitor;

public class MainSearchView implements PidescoView {

	private static MainSearchView instance;
	private ProjectBrowserServices browser_search;
	private JavaEditorServices editor_search;
	private SearchComposite search_composite;
	private PreviewComposite preview_composite;
	private AdvancedComposite advanced_composite;
	private PackageElement root_package;

	private String data_search;
	private boolean advancedButtonIsSelected;

	private Map<String, Image> images;
	private TreeMap<TreeEnum, TreeInstance> tree_map;
	
	//private ISearchImpl search_implementation;

	public MainSearchView() {
		instance = this;
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {

		checkExtensions();

		images = imageMap;
		tree_map = new TreeMap<TreeEnum, TreeInstance>();
		browser_search = SearchActivator.getActivatorInstance().getBrowserService();
		editor_search = SearchActivator.getActivatorInstance().getEditorService();

		viewArea.setLayout(new FillLayout(SWT.VERTICAL));

		search_composite = new SearchComposite(viewArea, SWT.BORDER);
		preview_composite = new PreviewComposite(viewArea, SWT.BORDER);
		
		//search_implementation = new ISearchImpl(search_composite);
		//search_implementation.addSearchInComboOption("Zeca");

		search_composite.getSearchButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				preview_composite.getPreview().setText("");
				preview_composite.getHierarquies().removeAll();
				root_package = (PackageElement) browser_search.getRootPackage();
				data_search = search_composite.getSearchField().getText();
				if (!tree_map.isEmpty()) {
					tree_map.clear();
				}
				if (advancedButtonIsSelected) {
					advanced_composite.getComboSearchFor().clearSelected();
					searchForScanner(advanced_composite.getComboSearchFor().itemSelected, root_package);
				} else if (search_composite.getSearchInCombo().hasAlreadySelected) {
					searchInScanner(search_composite.getSearchInCombo().itemSelected);
				} else {
					root_package.traverse(new DeepSearchVisitor(instance, SearchEnumType.SearchInPackage, ""));
				}
				if (!checkFound()) {
					new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Not Found", "help.gif",
							images);
				}
			}

			private boolean checkFound() {
				return tree_map.size() > 0;
			}
		});
		search_composite.getAdvanced().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (search_composite.getAdvanced().getSelection()) {
					advancedButtonIsSelected = true;
					advanced_composite = new AdvancedComposite(viewArea, SWT.BORDER);
					advanced_composite.moveAbove(preview_composite);
					viewArea.layout();
				} else {
					advanced_composite.dispose();
					advancedButtonIsSelected = false;
					viewArea.layout();
				}
			}

		});
		preview_composite.getHierarquies().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem ti = (TreeItem) e.item;
				if (ti.getData() != null) {
					preview_composite.styleText(ti.getData().toString(), ti.getData("searched").toString(),
							data_search);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				TreeItem ti = (TreeItem) e.item;
				File f = (File) ti.getData("file");
				editor_search.openFile(f);
			}

		});
	}

	private void searchInScanner(int itemSelected) {
		if (itemSelected == 1) {
			searchIn_orForPackage(SearchEnumType.SearchInPackage);
		} else if (itemSelected == 2) {
			searchInClass_orSearchFor(SearchEnumType.SearchInClass, "");
		} else if (itemSelected == 3) {
			searchIn_orForMethod(SearchEnumType.SearchInMethod, "");
		}
	}

	private void searchInClass_orSearchFor(SearchEnumType enumType, String advancedSpecifications) {
		SearchIn comboSearchIn = search_composite.getSearchInCombo();
		if (comboSearchIn.hasAlreadySelected && !comboSearchIn.getText_ofSearchSpecific().equals("")) {
			new DeepSearchVisitor(instance, enumType, advancedSpecifications)
					.visitClass(getClass(comboSearchIn.getText_ofSearchSpecific(), root_package));
		} else {
			root_package.traverse(new DeepSearchVisitor(instance, enumType, advancedSpecifications));
		}
	}

	private void searchIn_orForMethod(SearchEnumType enumType, String advancedSpecifications) {
		root_package.traverse(new DeepSearchVisitor(instance, enumType, advancedSpecifications));
	}

	private void searchIn_orForPackage(SearchEnumType enumType) {
		SearchIn comboSearchIn = search_composite.getSearchInCombo();
		if (comboSearchIn.hasAlreadySelected && !comboSearchIn.getText_ofSearchSpecific().equals("")) {
			for (SourceElement sourcePackage : root_package.getChildren()) {
				if (sourcePackage.getName().equals(comboSearchIn.getText_ofSearchSpecific())) {
					((PackageElement) sourcePackage).traverse(new DeepSearchVisitor(instance, enumType, ""));
					break;
				}
			}
		} else {
			root_package.traverse(new DeepSearchVisitor(instance, enumType, ""));
		}
	}

	private void searchForScanner(int itemSelected, PackageElement rootPackage) {
		if (itemSelected == 1) {
			searchIn_orForPackage(SearchEnumType.SearchForPackage);
		} else if (itemSelected == 2) {
			searchAdvanced(SearchEnumType.SearchForClass);
		} else if (itemSelected == 3) {
			searchAdvanced(SearchEnumType.SearchForMethod);
		} else if (itemSelected == 4) {
			searchAdvanced(SearchEnumType.SearchForField);
		}
	}

	private void searchAdvanced(SearchEnumType enumType) {
		SearchFor comboSearchFor = advanced_composite.getComboSearchFor();
		for (String advancedSpecification : comboSearchFor.getButtonsSelected()) {
			searchInClass_orSearchFor(enumType, advancedSpecification);
		}
		if (!comboSearchFor.buttonsSelected()) {
			searchInClass_orSearchFor(enumType, "");
		}
	}

	public void addTreeElement(TreeEnum parent, String name, File file, String result, String searched) {
		if (!tree_map.containsKey(parent)) {
			tree_map.put(parent, new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0),
					parent.toString(), parent.toString().toLowerCase() + ".gif", images));
		}
		tree_map.get(parent).addChildElement(name, file, result, searched);
	}

	public ClassElement getClass(String className, PackageElement rootPackage) {
		for (SourceElement source_package : rootPackage) {
			ClassElement c = getClassOfSearchIn(className, (PackageElement) source_package);
			if (c != null) {
				return c;
			}
		}
		return null;
	}

	private ClassElement getClassOfSearchIn(String className, PackageElement source_package) {
		for (SourceElement e : ((PackageElement) source_package).getChildren()) {
			if (e.isClass()) {
				if (className.equals(e.getParent().getName() + "." + e.getName())) {
					return (ClassElement) e;
				}
			} else {
				ClassElement c = getClassOfSearchIn(className, (PackageElement) e);
				if (c != null)
					return c;
			}
		}
		return null;
	}

	private void checkExtensions() {
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.deepsearch.output_preview");
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				String s = c.getAttribute("name");
				System.out.println(s + " is Connected to US");
				try {
					Object o = c.createExecutableExtension("class");
					System.out.println("And it is using this class reference -> " + o);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static MainSearchView getInstance() {
		return instance;
	}

	public JavaEditorServices getEditorSearch() {
		return editor_search;
	}

	public String getDataSearch() {
		return data_search;
	}

}
