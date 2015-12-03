package auxiliary;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

public class TreeInstance {

	private TreeItem tree_item;

	public TreeInstance(TreeItem tree_item, String name, String image_name, Map<String, Image> images) {
		this.tree_item = tree_item;
		tree_item.setText(name);
		tree_item.setImage(images.get(image_name));
	}

	public void addChildElement(String child_name, File file, String result, String searched) {
		TreeItem tree_child = new TreeItem(tree_item, 0);
		tree_child.setText(child_name);
		tree_child.setImage(tree_item.getImage());
		tree_child.setData("file", file);
		tree_child.setData(result); 
		tree_child.setData("searched", searched);
	}

	public boolean hasChildren() {
		return tree_item.getItems().length > 0;
	}

	public void dispose() {
		tree_item.dispose();
	}
}
