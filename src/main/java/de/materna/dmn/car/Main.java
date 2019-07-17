package de.materna.dmn.car;

import de.materna.jdec.DecisionSession;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException, URISyntaxException {
		// The model "model.dmn" is loaded from the "resources" folder.
		Path decisionPath = Paths.get(Main.class.getClassLoader().getResource("model.dmn").toURI());
		String decision = new String(Files.readAllBytes(decisionPath));

		// The model is imported. During import, the Drools instance, among other things, is initialized.
		DecisionSession decisionSession = new DecisionSession();
		decisionSession.importModel(decision);

		// The input is verbalized using JSON objects.
		// executeModel serializes the inputs automatically and passes them on to the Drools engine.
		// When the outputs are calculated, they are returned as a Map<String, Object> and can be used freely.
		Map<String, Object> outputs = decisionSession.executeModel("{ \"Person\": { \"Alter\": 18, \"Land\": \"Deutschland\" } }");
		System.out.println("executeJSON(): " + outputs);
	}
}