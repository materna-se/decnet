package de.materna.decnet.servlets;

import de.materna.decnet.beans.Input;
import de.materna.decnet.helpers.ServletHelper;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.ComplexInputStructure;
import de.materna.jdec.model.ModelNotFoundException;

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
	@Path("/structure")
	@Consumes("application/xml")
	@Produces({"application/json", "application/xml"})
	public Response getInputStructure(@PathParam("namespace") String namespace, @PathParam("name") String name, @HeaderParam("Accept") String accept) throws ModelNotFoundException {
		System.out.println("getInputStructure");

		ComplexInputStructure inputStructure = decisionSession.getInputStructure(namespace, name);

		// When serializing the decision input to XML, it is automatically wrapped in <LinkedHashMap></LinkedHashMap>.
		// To avoid this "feature", we cast the LinkedHashMap to Input that inherits from LinkedHashMap.
		Input input = new Input();
		input.putAll((Map<String, ?>) inputStructure.getValue());

		return Response.status(Response.Status.OK).entity(ServletHelper.convertResponse(accept, input)).build();
	}
}