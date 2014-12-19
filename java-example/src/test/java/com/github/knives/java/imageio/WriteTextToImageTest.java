package com.github.knives.java.imageio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;

public class WriteTextToImageTest {

	/**
	 * http://stackoverflow.com/questions/2736320/write-text-onto-image-in-java 
	 */
	
	@Test
	public void testWriteText() throws Exception {
		String key = "Sample";
        BufferedImage bufferedImage = new BufferedImage(170, 30,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 200, 50);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial Black", Font.BOLD, 20));
        graphics.drawString(key, 10, 25);
        
        ImageIO.write(bufferedImage, "jpg", new File("/tmp/test.jpg")); 
	}

	/**
	 * http://stackoverflow.com/questions/2658554/using-graphics2d-to-overlay-text-on-a-bufferedimage-and-return-a-bufferedimage/2658663#2658663
	 */
	@Test
	public void testWriterTextToExistingImage() throws Exception {
		BufferedImage  oldImage = ImageIO.read(new URL("http://sstatic.net/stackoverflow/img/logo.png"));
		
		int w = oldImage.getWidth();
        int h = oldImage.getHeight();
        
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(oldImage, 0, 0, null);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        String s = "Hello, world!";
        
        FontMetrics fm = g2d.getFontMetrics();
        int x = newImage.getWidth() - fm.stringWidth(s) - 5;
        int y = fm.getHeight();
        
        g2d.drawString(s, x, y);
        g2d.dispose();
		
        ImageIO.write(newImage, "png", new File("/tmp/test2.png")); 
	}
	
	@Test
	public void testMerge4Images() throws Exception {
		//we assume the no. of rows and cols are known and each chunk has equal width and height
		int rows = 2;   
        int cols = 2;
        int chunks = rows * cols;

        int chunkWidth, chunkHeight;
        int type;
        
        //creating a bufferd image array from image files
        BufferedImage[] buffImages = new BufferedImage[chunks];
        for (int i = 0; i < chunks; i++) {
            buffImages[i] = ImageIO.read(new URL("https://www.google.com/images/srpr/logo11w.png"));
        }
        type = buffImages[0].getType();
        chunkWidth = buffImages[0].getWidth();
        chunkHeight = buffImages[0].getHeight();

        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);

        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                num++;
            }
        }
        
        ImageIO.write(finalImg, "png", new File("/tmp/test3.png"));
	}
}
