package de.materna.decnet;

import de.materna.decnet.filters.CSRFFilter;
import de.materna.decnet.servlets.AnalyzerServlet;
import de.materna.decnet.servlets.ExecutorServlet;
import de.materna.decnet.servlets.StoreServlet;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.HybridDecisionSession;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class MainApplication extends Application {
	private final Set<Object> singletons = new HashSet<>();
	private final Set<Class<?>> classes = new HashSet<>();

	public MainApplication() throws Exception {
		DecisionSession decisionSession = new HybridDecisionSession();
		decisionSession.importModel("0003-input-data-string-allowed-values", IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("/0003-input-data-string-allowed-values.dmn"), StandardCharsets.UTF_8));

		classes.add(CSRFFilter.class);

		singletons.add(new StoreServlet(decisionSession));
		singletons.add(new AnalyzerServlet(decisionSession));
		singletons.add(new ExecutorServlet(decisionSession));
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}