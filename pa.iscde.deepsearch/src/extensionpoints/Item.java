package extensionpoints;

import java.io.File;

import org.eclipse.swt.graphics.Image;

public interface Item {

	public void setItem(String item_name, Image image, String preview_text, String highlight_text);

	public String getName();

	public Image getImg();

	public String getPreviewText();

	public String getHighlightText();

	public File getFile();

	public Object getSpecialData();

	public void setFile(File file);

	public void setSpecialData(Object special_data);

}