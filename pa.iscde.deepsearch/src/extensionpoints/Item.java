package extensionpoints;

import java.io.File;

import org.eclipse.swt.graphics.Image;

public interface Item {

	public void setItem(String name, Image img, String text_ToShowOnPreview, String text_ToHightlightOnPreview);

	public String getName();

	public Image getImg();

	public String getPreviewText();

	public String getHighlightText();

	public File getFile();

	public Object getSpecialData();

	public void setFile(File f);

	public void setSpecialData(Object obj);

}