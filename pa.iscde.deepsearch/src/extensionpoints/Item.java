package extensionpoints;

import org.eclipse.swt.graphics.Image;

public interface Item {

	/**
	 * defines the mandatory items to be showed on the preview when selecting
	 * (one-click) the item
	 * 
	 * @return Collection<Item> (non-null) parents items (ex: Package)
	 */
	public void setItem(String name_item, String text_Preview, String text_Highlighted);

	/**
	 * Obtains the mandatory attribute item_name
	 * 
	 * @return name_item (non-null)
	 */
	public String getName();

	/**
	 * Obtains the mandatory attribute text_Preview to be showed on the preview
	 * when selecting (one-click) the item
	 * 
	 * @return text_Preview (non-null)-> (ex:the content of a class)
	 */
	public String getPreviewText();

	/**
	 * Obtains the mandatory attribute text_Highlighted to be showed on the
	 * preview when selecting (one-click) the item
	 * 
	 * @return text_Highlighted (non-null)->(ex: being the text_Preview the
	 *         content of class, the text_Highlighted can be the name of the
	 *         class)
	 */
	public String getHighlightText();

	/**
	 * Defines the image of the item
	 * 
	 * @param Image
	 */
	public void setImg(Image img);

	/**
	 * Obtains the image of the item
	 * 
	 * @return Image
	 */
	public Image getImg();

	/**
	 * defines a special data of the item
	 * 
	 * @return Object -> a specialData defined of the item that can be used when
	 *         double-clicking the item(ex: file of a class)
	 */
	public void setSpecialData(Object special_data);

	/**
	 * Obtains the special data of the item
	 * 
	 * @param Object
	 *            -> a specialData defined of the item that can be used when
	 *            double-clicking the item(ex: file of a class)
	 */
	public Object getSpecialData();

}