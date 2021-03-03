package de.materna.decnet.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import de.materna.decnet.helpers.ServletHelper;
import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.ExecutionResult;
import de.materna.jdec.model.ModelNotFoundException;
import de.materna.jdec.serialization.SerializationHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/{namespace}")
public class ExecutorServlet {
	private final DecisionSession decisionSession;

	public ExecutorServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@POST
	@Path("")
	@Consumes({"application/json", "application/xml"})
	@Produces({"application/json", "application/xml"})
	public Response executeModel(@PathParam("namespace") String namespace, @HeaderParam("Accept") String accept, String input) {
		try {
			// executeModel serializes the inputs automatically and passes them on to the Drools engine.
			// When the output is calculated, it is returned as a Map<String, Object> and can be used freely.
			ExecutionResult executionResult = decisionSession.executeModel(namespace, SerializationHelper.getInstance().toClass(input, new TypeReference<HashMap<String, Object>>() {
			}));

			// TODO: Add header("Cache-Control", "public, max-age=5") to enable caching at load balancer level.
			return Response.status(Response.Status.OK).entity(ServletHelper.convertResponse(accept, executionResult)).build();
		}
		catch (ModelNotFoundException exception) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
