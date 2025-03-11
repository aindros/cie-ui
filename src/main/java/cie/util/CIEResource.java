package cie.util;

import it.ipzs.cieid.util.Utils;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class CIEResource {
	private static final String IMAGES = "/images/";

	public static Optional<BufferedImage> getImage(String name) {
		return Utils.getResource(IMAGES + name).flatMap(Utils::getImage);
	}
}
