package de.materna.dmn.car.servlets;

import de.materna.dmn.car.beans.Input;
import de.materna.dmn.car.helpers.ServletHelper;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.ComplexModelInput;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{namespace}/{name}")
public class AnalyzerServlet {
	private DecisionSession decisionSession;

	public AnalyzerServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@GET
	@Path("/input")
	@Consumes("application/xml")
	@Produces({"application/json", "text/xml"})
	public Response getInputs(@PathParam("namespace") String namespace, @PathParam("name") String name, @HeaderParam("Accept") String accept) {
		System.out.println("getInputs");

		ComplexModelInput calculatedInput = decisionSession.getInputs(namespace, name);

		// When serializing the decision input to XML, it is automatically wrapped in <LinkedHashMap></LinkedHashMap>.
		// To avoid this "feature", we cast the LinkedHashMap to Input that inherits from LinkedHashMap.
		Input input = new Input();
		input.putAll((Map<String, ?>) calculatedInput.getValue());

		return Response.status(Response.Status.OK).entity(ServletHelper.convertResponse(accept, input)).build();
	}
}