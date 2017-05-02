package umm3601.admin;

import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ImageHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by holma198 on 4/26/17.
 */
public class TestImageUploads {

    public ImageHandler handler;
    public InputStream testStreamPhoto;
    public String testFlowerName;
    public Image testPhoto;
    public String imageTestDir = "./";
    Object o = new Object();
    public File testFile;
    public ImageHandler handler1;

    @Before
    public void createHandler() {
        testStreamPhoto = this.getClass().getResourceAsStream("/TestImage.jpg");
        //testPhoto = this.getClass().getResource("/TestImage.jpg");
        testFlowerName = "TestFlower";
        handler = new ImageHandler(testStreamPhoto);
        //testPhoto = handler.extractImage();
        testFile = new File("./" + testFlowerName);
        //handler.storeImage(imageTestDir,testFlowerName,testPhoto);

    }

    @Test
    public void testExtractImage() {
        Image img = handler.extractImage();
        assertNotNull(img);
        assertEquals("java.awt.image.BufferedImage", img.getClass().getName().toString());
    }

    @Test
    public void testStoreImage() {
        Image img = handler.extractImage();
        Image extImg = null;
        try {
            extImg = ImageIO.read(testFile.listFiles()[0]);
        } catch (IOException e) {
            System.err.println("IOException when reading thingy");
        }
        handler.storeImage(imageTestDir,testFlowerName,img);
        assertNotNull(testFile.listFiles()[0]);

    }

}
