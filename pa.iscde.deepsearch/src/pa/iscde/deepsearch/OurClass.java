package pa.iscde.deepsearch;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.ViewLocation;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class OurClass implements PidescoServices{

	@Override
	public void openView(String viewId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getActiveView() {
		// TODO Auto-generated method stub
		return "dsd";
	}

	@Override
	public void layout(List<ViewLocation> viewLocations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImageFromPlugin(String pluginId, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void runTool(String toolId, boolean activate) {
		// TODO Auto-generated method stub
		
	}

	

}
