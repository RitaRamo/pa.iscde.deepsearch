package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import auxiliary.AutoCompletionCombo;

public class TestComposite extends Composite {

	private static final String[] items = new String[] { "Alpha", "Beta", "gaama", "bye", "pie", "alge", "bata" };

	public TestComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		setLayout(new GridLayout(2, false));

		new AutoCompletionCombo(this, SWT.NONE, items);

	}

}
