package de.materna.dmn.car.servlets;


import de.materna.jdec.DecisionSession;
import de.materna.jdec.helpers.SerializationHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{namespace}/{name}")
public class ExecutorServlet {
	private DecisionSession decisionSession;

	public ExecutorServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@POST
	@Path("")
	@Consumes("application/json")
	@Produces("application/json")
	public Response executeModel(@PathParam("namespace") String namespace, @PathParam("name") String name, String input) {
		System.out.println("executeModel");

		// executeModel serializes the inputs automatically and passes them on to the Drools engine.
		// When the output is calculated, it is returned as a Map<String, Object> and can be used freely.
		Map<String, Object> calculatedOutput = decisionSession.executeModel(namespace, name, input);

		String output = SerializationHelper.getInstance().toJSON(calculatedOutput);
		return Response.status(Response.Status.OK).entity(output).build(); // header("Cache-Control", "public, max-age=5")
	}
}
