package de.materna.dmn.car.helpers;

import de.materna.dmn.car.beans.Input;
import de.materna.jdec.serialization.SerializationHelper;

public class ServletHelper {
	public static String convertResponse(String accept, Object object) {
		if (accept.equals("text/xml")) {
			return SerializationHelper.getInstance().toXML(object);
		}

		return SerializationHelper.getInstance().toJSON(object);
	}
}
