package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class TestComposite extends Composite {

	private static final String[] items = new String[] { "Alpha", "Beta", "gaama", "bye", "pie", "alge", "bata" };

	public TestComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		setLayout(new GridLayout(2, false));

		// Combo s = new AutoCompletionCombo(this, SWT.NONE, items).getCombo();

		Combo combo = new Combo(this, SWT.NONE);
		combo.setItems(items);

		combo.addModifyListener(new ModifyListener() {

			boolean programmaticalyChanged = false;

			@Override
			public void modifyText(ModifyEvent e) {
				if (!programmaticalyChanged) {
					Combo s = (Combo) e.getSource();
					String text = s.getText();
					int index = findMatching(s);
					programmaticalyChanged = true;
					if (index != -1) {
						s.select(index);
						s.setText(s.getItem(index));
						s.setSelection(new Point(text.length(), s.getText().length()));
					}
					programmaticalyChanged = false;
				}

			}
		});

	}

	private int findMatching(Combo s) {
		String text = s.getText();
		String items[] = s.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].startsWith(text)) {
				return i;
			}
		}
		return -1;
	}

}
