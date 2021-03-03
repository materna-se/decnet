package de.materna.decnet;

import de.materna.decnet.servlets.AnalyzerServlet;
import de.materna.decnet.servlets.ExecutorServlet;
import de.materna.decnet.servlets.StoreServlet;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.HybridDecisionSession;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class MainApplication extends Application {
	private final Set<Object> singletons = new HashSet<>();
	private final Set<Class<?>> classes = new HashSet<>();

	public MainApplication() throws Exception {
		DecisionSession decisionSession = new HybridDecisionSession();

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