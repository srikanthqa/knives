package com.github.knives.osgi.service.dictionary.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.github.knives.osgi.service.dictionary.DictionaryService;


public class DictionaryClientActivator implements BundleActivator, ServiceListener {

	// Bundle's context.
	private BundleContext bundleContext = null;
	// The service reference being used.
	private ServiceReference<?> serviceReference = null;

	// The service object being used.
	private DictionaryService dictionaryService = null;

	@Override
	public void start(BundleContext context) throws Exception {
		bundleContext = context;

		// We synchronize while registering the service listener and
		// performing our initial dictionary service lookup since we
		// don't want to receive service events when looking up the
		// dictionary service, if one exists.
		synchronized (this) {
			// Listen for events pertaining to dictionary services.
			bundleContext
					.addServiceListener(this, "(&(objectClass="
							+ DictionaryService.class.getName() + ")"
							+ "(Language=*))");

			// Query for any service references matching any language.
			ServiceReference[] refs = bundleContext.getServiceReferences(
					DictionaryService.class.getName(), "(Language=*)");

			// If we found any dictionary services, then just get
			// a reference to the first one so we can use it.
			if (refs != null) {
				serviceReference = refs[0];
				dictionaryService = (DictionaryService) bundleContext
						.getService(serviceReference);
			}
		}

		try {
			System.out.println("Enter a blank line to exit.");
			String word = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			// Loop endlessly.
			while (true) {
				// Ask the user to enter a word.
				System.out.print("Enter word: ");
				word = in.readLine();

				// If the user entered a blank line, then
				// exit the loop.
				if (word.length() == 0) {
					break;
				}
				// If there is no dictionary, then say so.
				else if (dictionaryService == null) {
					System.out.println("No dictionary available.");
				}
				// Otherwise print whether the word is correct or not.
				else if (dictionaryService.checkWord(word)) {
					System.out.println("Correct.");
				} else {
					System.out.println("Incorrect.");
				}
			}
		} catch (Exception ex) {
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Implements ServiceListener.serviceChanged(). Checks to see if the service
	 * we are using is leaving or tries to get a service if we need one.
	 * 
	 * @param event
	 *            the fired service event.
	 **/
	public synchronized void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");

		// If a dictionary service was registered, see if we
		// need one. If so, get a reference to it.
		if (event.getType() == ServiceEvent.REGISTERED) {
			if (serviceReference == null) {
				// Get a reference to the service object.
				serviceReference = event.getServiceReference();
				dictionaryService = (DictionaryService) bundleContext.getService(serviceReference);
			}
		}
		// If a dictionary service was unregistered, see if it
		// was the one we were using. If so, unget the service
		// and try to query to get another one.
		else if (event.getType() == ServiceEvent.UNREGISTERING) {
			if (event.getServiceReference() == serviceReference) {
				// Unget service object and null references.
				bundleContext.ungetService(serviceReference);
				serviceReference = null;
				dictionaryService = null;

				// Query to see if we can get another service.
				ServiceReference[] refs = null;
				try {
					refs = bundleContext.getServiceReferences(
							DictionaryService.class.getName(), "(Language=*)");
				} catch (InvalidSyntaxException ex) {
					// This will never happen.
				}
				if (refs != null) {
					// Get a reference to the first service object.
					serviceReference = refs[0];
					dictionaryService = (DictionaryService) bundleContext
							.getService(serviceReference);
				}
			}
		}
	}
}
