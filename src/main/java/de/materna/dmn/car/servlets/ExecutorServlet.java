package de.materna.dmn.car.servlets;

import de.materna.dmn.car.beans.Input;
import de.materna.dmn.car.beans.Output;
import de.materna.dmn.car.helpers.ServletHelper;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.serialization.SerializationHelper;

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
	@Consumes({"application/json", "text/xml"})
	@Produces({"application/json", "text/xml"})
	public Response executeModel(@PathParam("namespace") String namespace, @PathParam("name") String name, @HeaderParam("Accept") String accept, String input) {
		System.out.println("executeModel");

		// executeModel serializes the inputs automatically and passes them on to the Drools engine.
		// When the output is calculated, it is returned as a Map<String, Object> and can be used freely.
		Map<String, Object> calculatedOutput = decisionSession.executeModel(namespace, name, input);

		// When serializing the decision input to XML, it is automatically wrapped in <LinkedHashMap></LinkedHashMap>.
		// To avoid this "feature", we cast the LinkedHashMap to Input that inherits from LinkedHashMap.
		Output output = new Output();
		output.putAll(calculatedOutput);

		// TODO: Add header("Cache-Control", "public, max-age=5") to enable caching at load balancer level.
		return Response.status(Response.Status.OK).entity(ServletHelper.convertResponse(accept, output)).build();
	}
}
