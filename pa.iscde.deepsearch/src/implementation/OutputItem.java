package implementation;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import extensionpoints.Item;

public class OutputItem implements Item {
	private String name;
	private Image image;
	private String text_ToShowOnPreview;
	private String text_ToHightlightOnPreview;
	private File f;
	private Object obj;

	public OutputItem(String name, Image image, String text_ToShowOnPreview, String text_ToHightlightOnPreview,
			File f) {
		this.name = name;
		this.image = image;
		this.text_ToShowOnPreview = text_ToShowOnPreview;
		this.text_ToHightlightOnPreview = text_ToHightlightOnPreview;
		this.f = f;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Image getImg() {
		return image;
	}

	@Override
	public String getPreviewText() {
		return text_ToShowOnPreview;
	}

	@Override
	public String getHighlightText() {
		return text_ToHightlightOnPreview;
	}

	@Override
	public File getFile() {
		return f;
	}

	@Override
	public Object getSpecialData() {
		return obj;
	}

	@Override
	public void setFile(File f) {
		this.f = f;
	}

	@Override
	public void setSpecialData(Object obj) {
		this.obj = obj;
	}

	@Override
	public void setItem(String name, Image image, String text_ToShowOnPreview, String text_ToHightlightOnPreview) {
		this.name = name;
		this.image = image;
		this.text_ToShowOnPreview = text_ToShowOnPreview;
		this.text_ToHightlightOnPreview = text_ToHightlightOnPreview;
	}
}
