package pa.iscde.deepsearch;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchActivator implements BundleActivator {

	private JavaEditorServices editor_service;
	private ProjectBrowserServices browser_service;

	private static SearchActivator activator;

	@Override
	public void start(BundleContext context) throws Exception {
		activator = this;

		ServiceReference<JavaEditorServices> ref_editor = context.getServiceReference(JavaEditorServices.class);
		editor_service = context.getService(ref_editor);

		ServiceReference<ProjectBrowserServices> ref_browser = context
				.getServiceReference(ProjectBrowserServices.class);
		browser_service = context.getService(ref_browser);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

	public ProjectBrowserServices getBrowserService() {
		return browser_service;
	}

	public JavaEditorServices getEditorService() {
		return editor_service;
	}

	public static SearchActivator getActivator() {
		return activator;
	}
}
