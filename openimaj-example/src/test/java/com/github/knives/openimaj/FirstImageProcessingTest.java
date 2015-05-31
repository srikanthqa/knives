package com.github.knives.openimaj;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.junit.Test;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;

public class FirstImageProcessingTest {

	@Test
	public void test() throws MalformedURLException, IOException, InterruptedException {
		MBFImage image = ImageUtilities.readMBF(getClass().getResourceAsStream("/sinaface.png"));
		image.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
		image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
		image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
		image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
		image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
		image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
		DisplayUtilities.display(image);
		new Scanner(System.in).nextLine();
	}

}
