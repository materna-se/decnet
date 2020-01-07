package de.materna.decnet.servlets;

import de.materna.jdec.DecisionSession;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/{namespace}/{name}")
public class StoreServlet {
	private DecisionSession decisionSession;

	public StoreServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@GET
	@Path("")
	@Consumes("application/xml")
	public Response getModel(@PathParam("namespace") String namespace, @PathParam("name") String name) {
		System.out.println("getModel");

		String model = decisionSession.getModel(namespace, name);

		return Response.status(Response.Status.OK).entity(model).build();
	}

	@PUT
	@Path("")
	@Consumes("application/xml")
	public Response importModel(@PathParam("namespace") String namespace, @PathParam("name") String name, String model) {
		System.out.println("importModel");

		// The model is imported.
		// During import, the Drools instance, among other things, is initialized.
		decisionSession.importModel(namespace, name, model);

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("")
	@Consumes("application/xml")
	public Response deleteModel(@PathParam("namespace") String namespace, @PathParam("name") String name) {
		System.out.println("deleteModel");

		decisionSession.deleteModel(namespace, name);

		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
