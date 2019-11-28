package de.materna.dmn.car.servlets;

import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.ComplexModelInput;
import de.materna.jdec.serialization.SerializationHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/{namespace}/{name}")
public class AnalyzerServlet {
	private DecisionSession decisionSession;

	public AnalyzerServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@GET
	@Path("/input")
	@Consumes("application/xml")
	public Response getInputs(@PathParam("namespace") String namespace, @PathParam("name") String name) {
		System.out.println("getInputs");

		ComplexModelInput input = decisionSession.getInputs(namespace, name);

		return Response.status(Response.Status.OK).entity(SerializationHelper.getInstance().toJSON(input.getValue())).build();
	}
}