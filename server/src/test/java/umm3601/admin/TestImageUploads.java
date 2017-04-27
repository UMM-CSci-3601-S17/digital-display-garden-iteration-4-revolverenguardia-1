package umm3601.admin;

import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ImageHandler;

import java.io.InputStream;

/**
 * Created by holma198 on 4/26/17.
 */
public class TestImageUploads {

    public ImageHandler handler;
    public InputStream testStreamPhoto;
    public InputStream testStreamFlowerName;

    @Before
    public void createHandler() {
        String a = "/TestImage.jpg";
        testStreamPhoto = this.getClass().getResourceAsStream("/TestImage.jpg");
        testStreamFlowerName = this.getClass().getResourceAsStream(a);
        handler = new ImageHandler();
    }

}
