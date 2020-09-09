package de.materna.decnet.servlets;

import de.materna.jdec.DecisionSession;
import de.materna.jdec.model.ImportResult;
import de.materna.jdec.model.ModelImportException;
import de.materna.jdec.model.ModelNotFoundException;
import de.materna.jdec.serialization.SerializationHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/{namespace}")
public class StoreServlet {
	private DecisionSession decisionSession;

	public StoreServlet(DecisionSession decisionSession) {
		this.decisionSession = decisionSession;
	}

	@GET
	@Path("")
	@Consumes({"application/xml", "application/java"})
	@Produces({"application/xml", "application/java"})
	public Response getModel(@PathParam("namespace") String namespace) {
		try {
			return Response.status(Response.Status.OK).entity(decisionSession.getModel(namespace)).build();
		}
		catch (ModelNotFoundException exception) {
			exception.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("")
	@Consumes({"application/xml", "application/java"})
	public Response importModel(@PathParam("namespace") String namespace, String model) {
		try {
			// The model is imported.
			// During import, the Drools instance, among other things, is initialized.
			ImportResult importResult = decisionSession.importModel(namespace, model);
			return Response.status(Response.Status.OK).entity(SerializationHelper.getInstance().toJSON(importResult)).build();
		}
		catch (ModelImportException exception) {
			exception.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(SerializationHelper.getInstance().toJSON(exception.getResult())).build();
		}
	}

	@DELETE
	@Path("")
	@Consumes({"application/xml", "application/java"})
	public Response deleteModel(@PathParam("namespace") String namespace) {
		try {
			decisionSession.deleteModel(namespace);

			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (ModelImportException exception) {
			exception.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(SerializationHelper.getInstance().toJSON(exception.getResult())).build();
		}
	}
}
