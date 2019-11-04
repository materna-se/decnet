package de.materna.dmn.car.servlets;


import de.materna.jdec.DecisionSession;
import de.materna.jdec.helpers.SerializationHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{namespace}/{name}")
public class AnalyzerServlet {
	private DecisionSession decisionSession;

	public AnalyzerServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}
}
