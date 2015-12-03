package visitor;

import enums.SearchEnumType;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement.Visitor;
import view.MainSearchView;

public class DeepSearchVisitor extends Visitor.Adapter {

	private MainSearchView searchView;
	private SearchEnumType myEnumType;
	private ASTVisitorDeepSearch astVisitor_deepSearch;

	public DeepSearchVisitor(MainSearchView search_view, SearchEnumType enum_type, String advancedSpecifications) {
		this.myEnumType = enum_type;
		this.searchView = search_view;
		astVisitor_deepSearch = new ASTVisitorDeepSearch(searchView, searchView.getDataSearch(), myEnumType,
				advancedSpecifications);
	}

	@Override
	public void visitClass(ClassElement c) {
		if (c != null) {
			astVisitor_deepSearch.setFile(c.getFile());
			searchView.getEditorSearch().parseFile(c.getFile(), astVisitor_deepSearch);
		}
	}
}
