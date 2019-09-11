package de.materna.dmn.car.servlets;


import de.materna.jdec.DecisionSession;
import de.materna.jdec.helpers.SerializationHelper;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Path("")
public class CarServlet {
	private DecisionSession decisionSession;

	public CarServlet() throws IOException {
		// The model "model.dmn" is loaded from the "resources" folder.
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/model.dmn");
		String decision = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

		// The model is imported. During import, the Drools instance, among other things, is initialized.
		decisionSession = new DecisionSession();
		decisionSession.importModel(decision);
	}

	@POST
	@Path("")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getInputs(String input) throws NoSuchAlgorithmException {
		// executeModel serializes the inputs automatically and passes them on to the Drools engine.
		// When the output is calculated, it is returned as a Map<String, Object> and can be used freely.
		Map<String, Object> calculatedOutput = decisionSession.executeModel(input);

		return Response.status(Response.Status.OK).header("ETag", calculateHash(input)).entity(SerializationHelper.getInstance().toJSON(calculatedOutput)).build();
	}

	private String calculateHash(String input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(input.getBytes());
		return Base64.getEncoder().encodeToString(messageDigest.digest());
	}
}
