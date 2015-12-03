package view;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import pt.iscte.pidesco.projectbrowser.model.PackageElement.Visitor;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import visitor.ASTVisitor_deepSearch;

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

	public MainSearchView() {
		instance = this;
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {

		checkExtensions();

		images = imageMap;
		browser_search = SearchActivator.getActivatorInstance().getBrowserService();
		editor_search = SearchActivator.getActivatorInstance().getEditorService();

		viewArea.setLayout(new FillLayout(SWT.VERTICAL));

		search_composite = new SearchComposite(viewArea, SWT.BORDER);
		preview_composite = new PreviewComposite(viewArea, SWT.BORDER);

		search_composite.getSearchButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				preview_composite.getPreview().setText("");
				preview_composite.getHierarquies().removeAll();
				root_package = (PackageElement) browser_search.getRootPackage();
				data_search = search_composite.getSearchField().getText();
				if (tree_map != null) {
					tree_map.clear();
				}
				initializeTree();
				if (advancedButtonIsSelected) {
					advanced_composite.getComboSearchFor().clearSelected();
					searchForScanner(advanced_composite.getComboSearchFor().itemSelected, root_package);
				} else if (search_composite.getSearchInCombo().hasAlreadySelected) {
					searchInScanner(search_composite.getSearchInCombo().itemSelected);
				} else {
					root_package.traverse(new MyVisitor(instance, SearchEnumType.SearchInPackage, ""));
				}
				Set<Entry<TreeEnum, TreeInstance>> set = tree_map.entrySet();
				if(!checkFound(set)) {
					TreeItem notFound = new TreeItem(preview_composite.getHierarquies(), 0);
					notFound.setText("Not Found");
					notFound.setImage(images.get("help.gif"));
				}
			}

			private boolean checkFound(Set<Entry<TreeEnum,TreeInstance>> set) {
				boolean found = false;
				for(Entry<TreeEnum, TreeInstance> entry : set) {
					if(entry.getValue().hasChildren()) {
						found = true;
					} else {
						entry.getValue().dispose();	
					}
				}
				return found;
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
			new MyVisitor(instance, enumType, advancedSpecifications)
					.visitClass(getClass(comboSearchIn.getText_ofSearchSpecific(), root_package));
		} else {
			root_package.traverse(new MyVisitor(instance, enumType, advancedSpecifications));
		}
	}

	private void searchIn_orForMethod(SearchEnumType enumType, String advancedSpecifications) {
		root_package.traverse(new MyVisitor(instance, enumType, advancedSpecifications));
	}

	private void searchIn_orForPackage(SearchEnumType enumType) {

		SearchIn comboSearchIn = search_composite.getSearchInCombo();
		if (comboSearchIn.hasAlreadySelected && !comboSearchIn.getText_ofSearchSpecific().equals("")) {
			for (SourceElement sourcePackage : root_package.getChildren()) {
				if (sourcePackage.getName().equals(comboSearchIn.getText_ofSearchSpecific())) {
					((PackageElement) sourcePackage).traverse(new MyVisitor(instance, enumType, ""));
					break;
				}
			}
		} else {

			root_package.traverse(new MyVisitor(instance, enumType, ""));
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
		for (Entry<TreeEnum, TreeInstance> entry : tree_map.entrySet()) {
			if (entry.getKey().equals(parent)) {
				entry.getValue().addChildElement(name, file, result, searched);
			}
		}
	}

	private void initializeTree() {
		tree_map = new TreeMap<TreeEnum, TreeInstance>();
		tree_map.put(TreeEnum.Package, new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Packages",
				"package.gif", images));
		tree_map.put(TreeEnum.Class,
				new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Classes", "class.gif", images));
		tree_map.put(TreeEnum.Interface, new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0),
				"Interfaces", "interface.gif", images));
		tree_map.put(TreeEnum.Enum,
				new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Enums", "enum.gif", images));
		tree_map.put(TreeEnum.Import,
				new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Imports", "import.gif", images));
		tree_map.put(TreeEnum.Field,
				new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Fields", "field.gif", images));
		tree_map.put(TreeEnum.Constructor, new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0),
				"Constructors", "constructor.gif", images));
		tree_map.put(TreeEnum.Method,
				new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0), "Methods", "method.gif", images));
		tree_map.put(TreeEnum.Statement, new TreeInstance(new TreeItem(preview_composite.getHierarquies(), 0),
				"Method Statements", "statement.gif", images));
	}

	private class MyVisitor extends Visitor.Adapter {
		private MainSearchView searchView;
		private SearchEnumType myEnumType;
		private ASTVisitor_deepSearch astVisitor_deepSearch;

		public MyVisitor(MainSearchView search_view, SearchEnumType enum_type, String advancedSpecifications) {
			this.myEnumType = enum_type;
			this.searchView = search_view;
			astVisitor_deepSearch = new ASTVisitor_deepSearch(searchView, data_search, myEnumType,
					advancedSpecifications);
		}

		@Override
		public void visitClass(ClassElement c) {
			if (c != null) {
				astVisitor_deepSearch.setFile(c.getFile());
				editor_search.parseFile(c.getFile(), astVisitor_deepSearch);
			}
		}

	};

	public ClassElement getClass(String className, PackageElement rootPackage) {
		for (SourceElement source_package : rootPackage) {
			System.out.println("MyPackage:" + source_package.getName());
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

}
