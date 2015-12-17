package implementation;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import extensionpoints.Item;

public class OutputItem implements Item {
	private String name;
	private Image img;
	private String text_ToShowOnPreview;
	private String text_ToHightlightOnPreview;
	private File f;

	public OutputItem(String name, Image image, String text_ToShowOnPreview, String text_ToHightlightOnPreview,
			File f) {
		this.name = name;
		this.img = image;
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
		return img;
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
	public Object getSpecialData() {
		return f;
	}

	@Override
	public void setSpecialData(Object obj) {
		this.f = (File)obj;
	}

	@Override
	public void setItem(String name,  String text_ToShowOnPreview, String text_ToHightlightOnPreview) {
		this.name = name;
		this.text_ToShowOnPreview = text_ToShowOnPreview;
		this.text_ToHightlightOnPreview = text_ToHightlightOnPreview;
	}

	@Override
	public void setImg(Image img) {
		this.img=img;
	}
}
