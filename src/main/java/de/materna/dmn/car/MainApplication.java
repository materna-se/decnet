package de.materna.dmn.car;

import de.materna.dmn.car.servlets.AnalyzerServlet;
import de.materna.dmn.car.servlets.ExecutorServlet;
import de.materna.dmn.car.servlets.StoreServlet;
import de.materna.jdec.DecisionSession;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class MainApplication extends Application {
	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> classes = new HashSet<>();

	public MainApplication() throws IOException {
		DecisionSession decisionSession = new DecisionSession();
		decisionSession.importModel("car", "car", IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("/car.dmn"), StandardCharsets.UTF_8));

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