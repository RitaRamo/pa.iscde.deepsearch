package pa.iscde.deepsearch;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public class PackageHierarquies {

	private PackageElement packageElement;
	private ClassElement classElement;
	private TypeDeclaration typeDeclaration;
	private EnumDeclaration enumDeclaration;
	private MethodDeclaration methodDeclaration;
	private FieldDeclaration fieldDeclaration;
	private ImportDeclaration importDeclaration;

	public PackageHierarquies(PackageElement packageElement, ClassElement classElement, EnumDeclaration enumDeclaration,
			MethodDeclaration methodDeclaration) {
		this.packageElement = packageElement;
		this.classElement = classElement;
		this.enumDeclaration = enumDeclaration;
		this.methodDeclaration = methodDeclaration;
	}

	public PackageHierarquies(PackageElement packageElement, ClassElement classElement, EnumDeclaration enumDeclaration,
			FieldDeclaration fieldDeclaration) {
		this.packageElement = packageElement;
		this.classElement = classElement;
		this.enumDeclaration = enumDeclaration;
		this.fieldDeclaration = fieldDeclaration;
	}

	public PackageHierarquies(PackageElement packageElement, ClassElement classElement, TypeDeclaration typeDeclaration,
			MethodDeclaration methodDeclaration) {
		this.packageElement = packageElement;
		this.classElement = classElement;
		this.typeDeclaration = typeDeclaration;
		this.methodDeclaration = methodDeclaration;
	}

	public PackageHierarquies(PackageElement packageElement, ClassElement classElement, TypeDeclaration typeDeclaration,
			FieldDeclaration fieldDeclaration) {
		this.packageElement = packageElement;
		this.classElement = classElement;
		this.typeDeclaration = typeDeclaration;
		this.fieldDeclaration = fieldDeclaration;
	}

	public PackageHierarquies(PackageElement packageElement, ClassElement classElement,
			ImportDeclaration importDeclaration) {
		this.packageElement = packageElement;
		this.classElement = classElement;
		this.importDeclaration = importDeclaration;
	}

	public PackageElement getPackageElement() {
		return packageElement;
	}

	public ClassElement getClassElement() {
		return classElement;
	}

	public TypeDeclaration getTypeDeclaration() {
		return typeDeclaration;
	}

	public EnumDeclaration getEnumDeclaration() {
		return enumDeclaration;
	}

	public MethodDeclaration getMethodDeclaration() {
		return methodDeclaration;
	}

	public FieldDeclaration getFieldDeclaration() {
		return fieldDeclaration;
	}

	public ImportDeclaration getImportDeclaration() {
		return importDeclaration;
	}

	public void setPackageElement(PackageElement packageElement) {
		this.packageElement = packageElement;
	}

	public void setClassElement(ClassElement classElement) {
		this.classElement = classElement;
	}

	public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
		this.typeDeclaration = typeDeclaration;
	}

	public void setEnumDeclaration(EnumDeclaration enumDeclaration) {
		this.enumDeclaration = enumDeclaration;
	}

	public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
		this.methodDeclaration = methodDeclaration;
	}

	public void setFieldDeclaration(FieldDeclaration fieldDeclaration) {
		this.fieldDeclaration = fieldDeclaration;
	}

	public void setImportDeclaration(ImportDeclaration importDeclaration) {
		this.importDeclaration = importDeclaration;
	}

}
