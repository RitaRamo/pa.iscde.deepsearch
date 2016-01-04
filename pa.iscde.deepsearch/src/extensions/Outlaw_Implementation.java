package extensions;

import pa.iscde.outlaw.Outline.OutlineClass;
import pa.iscde.outlaw.Outline.OutlineField;
import pa.iscde.outlaw.Outline.OutlineMethod;
import pa.iscde.outlaw.extensibility.OutlineFilter;

public class Outlaw_Implementation implements OutlineFilter {

	@Override
	public boolean showMethodFilter(OutlineMethod om) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showFieldFilter(OutlineField of) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showClassFilter(OutlineClass oc) {
		// TODO Auto-generated method stub
		return false;
	}

}
