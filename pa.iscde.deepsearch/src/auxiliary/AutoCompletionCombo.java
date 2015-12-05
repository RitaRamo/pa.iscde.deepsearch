package auxiliary;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class AutoCompletionCombo {

	private String[] combo_items;

	private Combo combo;

	public AutoCompletionCombo(Composite parent, int style, String[] combo_items) {

		this.combo_items = combo_items;

		combo = new Combo(parent, style);
		combo.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == 0) {
					return;
				}
				String text = combo.getText();
				if (text == null) {
					return;
				}
				List<String> matchingSuggestions = updateSuggestions(text);
				int length = text.length();
				if (e.character == SWT.BS) {
					combo.setText(text);
					combo.setSelection(new Point(length, length));
				} else {
					if (length != 0) {
						Point selection;
						if (matchingSuggestions.isEmpty()) {
							combo.setText(text);
							selection = new Point(length, length);
						} else {
							String suggestion = matchingSuggestions.get(0);
							combo.setText(suggestion);
							selection = new Point(length, suggestion.length());
						}
						combo.setSelection(selection);
					}
				}
			}

		});
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = combo.getText();
				updateSuggestions(text);
				combo.setText(text);
			}

		});
		for (String item : combo_items) {
			combo.add(item);
		}
	}

	private List<String> updateSuggestions(String text) {
		ArrayList<String> matchingSuggestions = new ArrayList<String>(combo_items.length);
		for (String suggestion : combo_items) {
			if (suggestion.startsWith(text)) {
				matchingSuggestions.add(suggestion);
			}
		}
		combo.removeAll();
		for (String suggestion : matchingSuggestions) {
			combo.add(suggestion);
		}
		return matchingSuggestions;
	}

	public String getText() {
		return combo.getText();
	}
	
	public Combo getCombo() {
		return combo;
	}

	public void setText(String text) {
		updateSuggestions(text);
		combo.setText(text);
	}

}
