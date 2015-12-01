package pa.iscde.deepsearch;

import java.io.File;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ASTVisitor_deepSearch extends ASTVisitor {

	private MainSearchView searchView;
	private String searchText;
	private String myEnum;
	private String packageName = "";
	private String advancedSpecification;
	private String full_class;
	private File temp_file;

	

	public ASTVisitor_deepSearch(MainSearchView searchView, String searchText, SearchEnumType myEnm,
			String advancedSpecification) {
		this.searchView = searchView;
		this.searchText = searchText;
		this.myEnum = myEnm.toString();
		this.advancedSpecification = advancedSpecification;
		
	}

	public File setFile(File file) {
		return temp_file = file;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		if (isToSearchFor_orIn_Pachage() && !packageName.equals("" + node.getName())) {
			String search_result = "" + node.getName();
		
			String result = node.toString();
			if (search_result.contains(searchText)) {
				packageName = "" + node.getName();
//				searchView.addTreeElement(TreeEnum.Package, packageName, temp_file,  result,
//						search_result);
				searchView.getMyOutput().addTreeElement(TreeEnum.Package, packageName, temp_file,  result,
						search_result);
			}
		}
		return true;

	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (searcForClass_orInPachage_orClass()) {
			
			String search_result = "";
			for (int i = 0; i < node.modifiers().size(); i++) {
				search_result += node.modifiers().get(i) + " ";
				if (i == node.modifiers().size() - 1 && !node.isInterface()) {
					search_result += "class " + node.getName();
				} else if (i == node.modifiers().size() - 1 && node.isInterface()) {
					search_result += "interface " + node.getName();
				}
			}
			if (node.getSuperclassType() != null) {
				search_result += " extends " + node.getSuperclassType();
			}
			if (node.superInterfaceTypes().size() > 0) {
				for (int i = 0; i < node.superInterfaceTypes().size(); i++) {
					if (i == 0) {
						search_result += " implements ";
					}
					search_result += node.superInterfaceTypes().get(i);
					if (i != node.superInterfaceTypes().size() - 1) {
						search_result += ", ";
					}
				}
			}
			String result = node.toString();
			if (!(node.getParent() instanceof TypeDeclaration)) {
				full_class = result;
			}
			if (isArgumentDefined(advancedSpecification)) {
				if (search_result.contains(advancedSpecification)) {
					if (search_result.contains(searchText)) {
						if (node.isInterface()) {
							searchView.addTreeElement(TreeEnum.Interface, node.getName() + "", temp_file,
									 result, search_result);
						} else {
							searchView.addTreeElement(TreeEnum.Class, node.getName() + "", temp_file, 
									result, search_result);
						}
					}
				}
			} else {
				if (search_result.contains(searchText)) {
					if (node.isInterface()) {
						searchView.addTreeElement(TreeEnum.Interface, node.getName() + "", temp_file, 
								result, search_result);
					} else {
						searchView.addTreeElement(TreeEnum.Class, node.getName() + "", temp_file, result,
								search_result);
					}
				}
				
			}
		}
		return true;
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		if (isToSearchInPackage_orClass()) {
			String search_result = node.toString();
			String result = search_result + "\n" + full_class;
			if (search_result.contains(searchText)) {
				searchView.addTreeElement(TreeEnum.Import, node.getName() + "", temp_file,  result,
						search_result);
			}
		}
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		if (isToSearchInPackage_orClass() || isToSearchForClass_or_InClass_orPackage()) {
			String search_result = "";
			for (int i = 0; i < node.modifiers().size(); i++) {
				search_result += node.modifiers().get(i) + " ";
				if (i == node.modifiers().size() - 1) {
					search_result += "enum " + node.getName();
				}
			}
			String result = node.toString();
			full_class = result;
			if (isArgumentDefined(advancedSpecification)) {
				if (search_result.contains(advancedSpecification)) {
					if (search_result.contains(searchText)) {
						searchView.addTreeElement(TreeEnum.Enum, node.getName() + "", temp_file,  result,
								search_result);
					}
				}
			} else {
				if (search_result.contains(searchText)) {
					searchView.addTreeElement(TreeEnum.Enum, node.getName() + "", temp_file,  result,
							search_result);
				}
			}
		}
		return true;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		if (isToSearchForField_orInPachage_orClass()) {
			String search_result = "";
			for (int i = 0; i < node.modifiers().size(); i++) {
				search_result += node.modifiers().get(i) + " ";
			}
			search_result += node.getType() + " " + node.fragments().get(0);
			if (search_result.contains(searchText)) {
				String equals = node.fragments().get(0).toString();
				if (equals.contains("=")) {
					equals = node.fragments().get(0).toString().split("=")[0];
				}
				searchView.addTreeElement(TreeEnum.Field, equals, temp_file,  full_class, search_result);
			}
		}
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		if (isToSearchForMethod_or_InMethod_Class_orPackage()) {
			String class_temp = "";
			for (int i = 0; i < node.modifiers().size(); i++) {
				class_temp += node.modifiers().get(i) + " ";
			}
			if (!node.isConstructor()) {
				class_temp += node.getReturnType2() + " ";
			}
			class_temp += node.getName();
			if (class_temp.contains(searchText)) {
				if (node.isConstructor()) {
					searchView.addTreeElement(TreeEnum.Constructor, node.getName() + "", temp_file, 
							full_class, class_temp);
				} else {
					searchView.addTreeElement(TreeEnum.Method, node.getName() + "", temp_file,  full_class,
							class_temp);
				}
			}
		}
		return true;
	}

	@Override
	public boolean visit(Block node) {
		if (isToSearchInMethod_Class_orPackage()) {
			String search_result = "";
			if (node.statements().size() > 0) {
				search_result += workStatement(node.statements());
			}
			if (search_result.contains(searchText)) {
				searchView.addTreeElement(TreeEnum.Statement, "Statement of" + node.getParent(), temp_file,
						 full_class, search_result);
			}
		}
		return true;
	}

	private String workStatement(List<?> stats) {
		for (int i = 0; i < stats.size(); i++) {
			if (stats.get(i) instanceof Block) {
				Block b = (Block) stats.get(i);
				if (b.statements().size() > 0) {
					workStatement(b.statements());
				} else {
					return b.toString();
				}
			} else {
				return stats.get(i).toString();
			}
		}
		return "";
	}

	private boolean isToSearchForMethod_or_InMethod_Class_orPackage() {
		return myEnum.equals(SearchEnumType.SearchForMethod.toString()) || isToSearchInMethod_Class_orPackage();
	}
	
	private boolean isToSearchForClass_or_InClass_orPackage() {
		return myEnum.equals(SearchEnumType.SearchForClass.toString()) || isToSearchInPackage_orClass();
	}

	private boolean isToSearchInMethod_Class_orPackage() {
		return myEnum.equals(SearchEnumType.SearchInMethod.toString()) || isToSearchInPackage_orClass();
	}

	private boolean isToSearchInPackage_orClass() {
		return myEnum.equals(SearchEnumType.SearchInPackage.toString())
				|| myEnum.equals(SearchEnumType.SearchInClass.toString());
	}

	private boolean isArgumentDefined(String searchFor) {
		return !searchFor.equals("");
	}

	private boolean isToSearchFor_orIn_Pachage() {
		return (myEnum.equals(SearchEnumType.SearchForPackage.toString())
				|| myEnum.equals(SearchEnumType.SearchInPackage.toString()));
	}

	private boolean searcForClass_orInPachage_orClass() {
		return myEnum.equals(SearchEnumType.SearchForClass.toString()) || isToSearchInPackage_orClass();
	}

	private boolean isToSearchForField_orInPachage_orClass() {
		return myEnum.equals(SearchEnumType.SearchForField.toString()) || isToSearchInPackage_orClass();
	}
	
	
	


}
