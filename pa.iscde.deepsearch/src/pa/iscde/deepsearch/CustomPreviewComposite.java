package pa.iscde.deepsearch;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CustomPreviewComposite extends Composite {

	private Text hierarquies;
	private StyledText preview;

	public CustomPreviewComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {

		GridLayout grid = new GridLayout(2, true);
		setLayout(grid);

		hierarquies = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
		hierarquies.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		preview = new StyledText(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
		preview.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		preview.setBackground(new Color(this.getDisplay(), 255, 255, 255));
		preview.setEditable(false);
	}

	public Text getHierarquies() {
		return hierarquies;
	}

	public StyledText getPreview() {
		return preview;
	}

}
