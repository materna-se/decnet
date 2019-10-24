package de.materna.dmn.car.servlets;


import de.materna.jdec.DecisionSession;
import de.materna.jdec.helpers.SerializationHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("")
public class CarServlet {
	private DecisionSession decisionSession = new DecisionSession();

	@PUT
	@Path("/store")
	@Consumes("application/xml")
	public Response importModel(String model) {
		System.out.println("importModel: " + model);

		// The model is imported.
		// During import, the Drools instance, among other things, is initialized.
		decisionSession.importModel(model);

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Path("/executor")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getInputs(String input) {
		System.out.println("getInputs: " + input);

		// executeModel serializes the inputs automatically and passes them on to the Drools engine.
		// When the output is calculated, it is returned as a Map<String, Object> and can be used freely.
		Map<String, Object> calculatedOutput = decisionSession.executeModel(input);

		String output = SerializationHelper.getInstance().toJSON(calculatedOutput);
		return Response.status(Response.Status.OK).header("Cache-Control", "public, max-age=5").entity(output).build();
	}
}
