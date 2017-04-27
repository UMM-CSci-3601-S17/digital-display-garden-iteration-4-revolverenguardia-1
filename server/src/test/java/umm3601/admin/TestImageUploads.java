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
    public String imageTestDir = "./src/test/java/umm3601/images";
    public File testFile;
    public ImageHandler handler1;

    @Before
    public void createHandler() {
        testStreamPhoto = this.getClass().getResourceAsStream("/TestImage.jpg");
        //testPhoto = this.getClass().getResource("/TestImage.jpg");
        testFlowerName = "TestFlower";
        handler = new ImageHandler(testStreamPhoto);
        //testPhoto = handler.extractImage();
        testFile = new File(imageTestDir + "/" + testFlowerName);
        //handler.storeImage(imageTestDir,testFlowerName,testPhoto);

    }

    @Test
    public void testExtractImage() {
        Image img = handler.extractImage();
        assertNotNull(img);
        assertEquals("java.awt.image.BufferedImage", img.getClass().getName().toString());
    }

    /*@Test
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
        assertEquals(img.toString().substring(img.toString().length()-(img.toString().length()-30)), extImg.toString().substring(extImg.toString().length()-(img.toString().length()-30)));
    }*/

    /*@Test
    public void testReadImage() {

        Image img = handler.extractImage();
        handler.storeImage(imageTestDir,testFlowerName,img);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handler1 = new ImageHandler(outputStream);
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        handler1.getImageOnFilesystem(testFlowerName, imageTestDir + "/");
        BufferedImage extImg = null;
        try {
            extImg = ImageIO.read(testFile.listFiles()[0]);
        } catch (IOException e) {
            System.err.println("IOException when reading filearray");
        }
        DataBufferByte data = (DataBufferByte)(extImg.getData().getDataBuffer());
        try {
            outputStream1.write(data.getData());
        } catch (IOException e) {
            System.err.println("IOException when writing to outputstream");
        }
        assertEquals(outputStream,outputStream1);
    }*/

}
