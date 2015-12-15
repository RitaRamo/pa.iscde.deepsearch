package composites;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
import extensionpoints.ISearchEventListener;
import extensionpoints.Item;
import extensionpoints.OutputPreview;
import implementation.OutputItem;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class MainSearchView implements PidescoView {

	private static MainSearchView MAIN_SEARCH_VIEW_INSTANCE;

	private JavaEditorServices editor_search;

	private SearchComposite search_composite;
	private PreviewComposite preview_composite;
	private AdvancedComposite advanced_composite;

	private String searched_data;
	private boolean advancedButtonIsSelected;

	private LinkedList<OutputPreview> extensionResult = new LinkedList<OutputPreview>();

	private LinkedList<String> parent_results = new LinkedList<String>();
	private Map<String, LinkedList<Item>> results = new HashMap<String, LinkedList<Item>>();

	public MainSearchView() {
		MAIN_SEARCH_VIEW_INSTANCE = this;
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {

		editor_search = SearchActivator.getActivatorInstance().getEditorService();

		viewArea.setLayout(new FillLayout(SWT.VERTICAL));

		search_composite = new SearchComposite(viewArea, SWT.BORDER);
		preview_composite = new PreviewComposite(viewArea, SWT.BORDER);

		checkExtensionsOutput();

		search_composite.getSearchButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				preview_composite.getPreview().setText("");
				preview_composite.getHierarquies().removeAll();
				searched_data = search_composite.getSearchField().getText();
				for (OutputPreview o : extensionResult) {
					if (advancedButtonIsSelected) {
						o.searchForScanner(searched_data, advanced_composite.getComboSearchFor().itemSelected,
								advanced_composite.getComboSearchFor().getButtonsSelected(),
								search_composite.getSearchIn().itemSelected,
								search_composite.getSearchIn().getText_ofSearchSpecific());
						advanced_composite.getComboSearchFor().clearSelected();
					} else {
						o.searchForScanner(searched_data, -1, null, search_composite.getSearchIn().itemSelected,
								search_composite.getSearchIn().getText_ofSearchSpecific());
					}
					createTree(o);
				}
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
				for (@SuppressWarnings("unused")
				OutputPreview o : extensionResult) {
					TreeItem tree_item = (TreeItem) e.item;
					OutputItem item = new OutputItem(tree_item.getText(), tree_item.getImage(),
							tree_item.getData("previewText").toString(),
							tree_item.getData("highlightedText").toString(), (File) tree_item.getData("File"));
					if (item.getPreviewText() != "") {
						preview_composite.styleText(item.getPreviewText(), item.getHighlightText(), searched_data);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				for (OutputPreview o : extensionResult) {
					TreeItem tree_item = (TreeItem) e.item;
					OutputItem item = new OutputItem(tree_item.getText(), tree_item.getImage(),
							tree_item.getData("previewText").toString(),
							tree_item.getData("highlightedText").toString(), (File) tree_item.getData("File"));
					o.doubleClick(item);
				}
			}

		});

		addWidgetSelected();
	}

	public void createTree(OutputPreview outputPreview) {
		for (String parentName : outputPreview.getParents()) {
			TreeItem newParent = new TreeItem(preview_composite.getHierarquies(), 0);
			newParent.setText(parentName);
			newParent.setImage(SearchActivator.getActivatorInstance().getImageFromURL(parentName));
			results.put(parentName, (LinkedList<Item>) outputPreview.getChildren(parentName));
			parent_results.add(parentName);
			for (Item child : outputPreview.getChildren(parentName)) {
				TreeItem newChild = new TreeItem(newParent, 0);
				newChild.setText(child.getName());
				newChild.setImage(child.getImg());
				newChild.setData("previewText", child.getPreviewText());
				newChild.setData("highlightedText", child.getHighlightText());
				newChild.setData("File", child.getFile());
				newChild.setData("SpecialData", child.getSpecialData());
			}
		}
	}

	private void addWidgetSelected() {
		search_composite.getSearchButton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String temp = "";
				String temp_2 = "";
				Collection<String> temp_3 = null;
				if (search_composite.getSearchIn().getComboBox_searchSpecific() != null) {
					if (!search_composite.getSearchIn().getComboBox_searchSpecific().isDisposed()) {
						temp = search_composite.getSearchIn().getComboBox_searchSpecific().getText();
					}
				}
				if (advancedButtonIsSelected) {
					temp_2 = advanced_composite.getComboSearchFor().getComboBox_search().getText();
					if (!advanced_composite.getComboSearchFor().getIsDisposed()) {
						temp_3 = advanced_composite.getComboSearchFor().getItemsSelected();
					} else {
						temp_3 = new LinkedList<>();
					}
				}
				for (ISearchEventListener l : SearchActivator.getActivatorInstance().getListeners()) {
					l.widgetSelected(search_composite.getSearchField().getText(),
							search_composite.getSearchIn().getComboBox_search().getText(), temp, temp_2, temp_3);
				}
			}
		});
	}

	private void checkExtensionsOutput() {
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.deepsearch.output_preview");
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				try {
					extensionResult.add((OutputPreview) c.createExecutableExtension("class"));
				} catch (CoreException e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	public static MainSearchView getInstance() {
		return MAIN_SEARCH_VIEW_INSTANCE;
	}

	public JavaEditorServices getEditorSearch() {
		return editor_search;
	}

	public String getDataSearch() {
		return searched_data;
	}

	public Map<String, LinkedList<Item>> getResults() {
		return results;
	}

	public LinkedList<String> getParentResults() {
		return parent_results;
	}

}
