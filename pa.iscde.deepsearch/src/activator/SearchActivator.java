package activator;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import api.ISearchEvent;
import api.ISearchEventListener;
import api.SearchServices;
import implementation.ISearchEventImpl;
import implementation.SearchServicesImpl;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchActivator implements BundleActivator {

	private JavaEditorServices editor_service;
	private ProjectBrowserServices browser_service;

	private Set<ISearchEventListener> listeners;
	private ServiceRegistration<SearchServices> service_one;
	private ServiceRegistration<ISearchEvent> service_two;

	private static SearchActivator activator;

	@Override
	public void start(BundleContext context) throws Exception {
		activator = this;
		listeners = new HashSet<ISearchEventListener>();

		ServiceReference<JavaEditorServices> ref_editor = context.getServiceReference(JavaEditorServices.class);
		editor_service = context.getService(ref_editor);
		ServiceReference<ProjectBrowserServices> ref_browser = context
				.getServiceReference(ProjectBrowserServices.class);
		browser_service = context.getService(ref_browser);

		service_one = context.registerService(SearchServices.class, new SearchServicesImpl(), null);

		service_two = context.registerService(ISearchEvent.class, new ISearchEventImpl(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		activator = null;
		service_one.unregister();
		service_two.unregister();
		listeners.clear();
	}

	public ProjectBrowserServices getBrowserService() {
		return browser_service;
	}

	public JavaEditorServices getEditorService() {
		return editor_service;
	}

	public static SearchActivator getActivatorInstance() {
		return activator;
	}

	public Set<ISearchEventListener> getListeners() {
		return listeners;
	}

	public void addListener(ISearchEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ISearchEventListener listener) {
		listeners.remove(listener);

	}
}
