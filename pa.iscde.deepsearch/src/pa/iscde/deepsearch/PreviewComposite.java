package pa.iscde.deepsearch;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class PreviewComposite extends Composite {

	private Tree hierarquies;
	private StyledText preview;

	public PreviewComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		setLayout(new GridLayout(2, true));

		hierarquies = new Tree(this, SWT.IMAGE_GIF | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		hierarquies.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		preview = new StyledText(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
		preview.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		preview.setBackground(new Color(this.getDisplay(), 255, 255, 255));
		preview.setEditable(false);
	}

	public Tree getHierarquies() {
		return hierarquies;
	}

	public StyledText getPreview() {
		return preview;
	}

	public void styleText(String full, String result, String data_search) {
		System.out.println(result);
		getPreview().setText(full);
		if (!data_search.equals("")) {
			getPreview().setStyleRange(new StyleRange(full.indexOf(result), result.length(), null,
					getDisplay().getSystemColor(SWT.COLOR_YELLOW)));
			for (int i = result.indexOf(data_search); i >= 0; i = result.indexOf(data_search, i + 1)) {
				getPreview().setStyleRange(new StyleRange(full.indexOf(result) + i, data_search.length(), null,
						getDisplay().getSystemColor(SWT.COLOR_GREEN)));
			}
		}
	}

}
