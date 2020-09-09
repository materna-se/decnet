package de.materna.decnet.servlets;

import de.materna.decnet.beans.Structure;
import de.materna.decnet.helpers.ServletHelper;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.InputStructure;
import de.materna.jdec.model.ModelNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/{namespace}")
public class AnalyzerServlet {
	private DecisionSession decisionSession;

	public AnalyzerServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@GET
	@Path("/structure")
	@Consumes("application/xml")
	@Produces({"application/json", "application/xml"})
	public Response getInputStructure(@PathParam("namespace") String namespace, @HeaderParam("Accept") String accept) {
		try {
			Map<String, InputStructure> inputStructure = decisionSession.getInputStructure(namespace);

			// When serializing the decision input to XML, it is automatically wrapped in <LinkedHashMap></LinkedHashMap>.
			// To avoid this "feature", we cast the LinkedHashMap to Input that inherits from LinkedHashMap.
			Structure structure = new Structure();
			structure.putAll(inputStructure);

			return Response.status(Response.Status.OK).entity(ServletHelper.convertResponse(accept, structure)).build();
		}
		catch (ModelNotFoundException exception) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}