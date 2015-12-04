package composites;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class TestComposite extends Composite {

	private static final String LCL = "abcdefghijklmnopqrstuvwxyz";
	private static final String UCL = LCL.toUpperCase();
	private static final String NUMS = "0123456789";
	private static final String[] items = new String[] { "Alpha", "Beta", "gaama", "pie", "alge", "bata" };

	public TestComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	private void createContents() {
		setLayout(new GridLayout(2, false));

		Combo combo = new Combo(this, SWT.NONE);
		combo.setItems(items);
		 enableContentProposal(combo);
		
	}
	

	void enableContentProposal(Control control) {

		SimpleContentProposalProvider proposalProvider = null;
		ContentProposalAdapter proposalAdapter = null;
		if (control instanceof Combo) {
			Combo combo = (Combo) control;
			proposalProvider = new SimpleContentProposalProvider(combo.getItems());
			proposalAdapter = new ContentProposalAdapter(combo, new ComboContentAdapter(), proposalProvider,
					getActivationKeystroke(), getAutoactivationChars());
		} else if (control instanceof Text) {

			Text text = (Text) control;
			proposalProvider = new SimpleContentProposalProvider(items);
			proposalAdapter = new ContentProposalAdapter(text, new TextContentAdapter(), proposalProvider,
					getActivationKeystroke(), getAutoactivationChars());
		}
		proposalProvider.setFiltering(true);
		proposalAdapter.setPropagateKeys(true);
		proposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

	static char[] getAutoactivationChars() {
		String delete = new String(new char[] { 8 });
		String allChars = LCL + UCL + NUMS + delete;
		return allChars.toCharArray();
	}

	static KeyStroke getActivationKeystroke() {
		KeyStroke instance = KeyStroke.getInstance(new Integer(SWT.CTRL).intValue(), new Integer(' ').intValue());
		return instance;
	}

}
