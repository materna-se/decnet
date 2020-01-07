package de.materna.decnet.helpers;

import de.materna.jdec.serialization.SerializationHelper;

public class ServletHelper {
	public static String convertResponse(String accept, Object object) {
		if (accept.equals("text/xml")) {
			return SerializationHelper.getInstance().toXML(object);
		}

		return SerializationHelper.getInstance().toJSON(object);
	}
}
