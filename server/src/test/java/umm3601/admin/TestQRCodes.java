package umm3601.admin;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.digitalDisplayGarden.QRCodes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static umm3601.Server.API_URL;
import static umm3601.digitalDisplayGarden.QRCodes.createQRFromBedURL;

/**
 * Created by Brian on 4/5/2017.
 */
public class TestQRCodes {
    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    String path = "test/";

    @Test
    public void TestFormBedURLs() throws IOException, WriterException{
        String bedNames[] = new String[4];
        bedNames[0] = "bed1";
        bedNames[1] = "bed2";
        bedNames[2] = "bed3";
        bedNames[3] = "bed4";

        String urlPrefix = API_URL + "/bed/";


        //Check to see that it makes a bed url of length 4
        assertEquals("this should be 4",4, QRCodes.formBedURLs(bedNames,urlPrefix,this.path).length);
        assertEquals("this should be a url to bed1","http://localhost:2538/bed/bed1", QRCodes.formBedURLs(bedNames,urlPrefix,this.path)[0]);

    }

    @Test
    public void TestCreateBufferedImages() throws IOException,WriterException{
        String bedURLs[] = new String[3];
        bedURLs[0] = "http://localhost:2538/bed/bed1";
        bedURLs[1] = "http://localhost:2538/bed/bed2";
        bedURLs[2] = "http://localhost:2538/bed/bed3";
        System.out.println(QRCodes.createBufferedImages(bedURLs).get(0));

        List<BufferedImage> qrCodeImages = new ArrayList<BufferedImage>();
        for(int i = 0; i < 3; i++) {
            qrCodeImages.add(createQRFromBedURL(bedURLs[i]));
        }

        //test to see if the width is 300 of the created buffered image
        assertEquals("they should return same buffered images", qrCodeImages.get(0).toString().contains("width = 300"),QRCodes.createBufferedImages(bedURLs).get(0).toString().contains("width = 300") );
    }

    @Test
    public void TestWriteZipPathForQRCodes() throws IOException,WriterException{
        String bedNames[] = new String[3];
        bedNames[0] = "bed1";
        bedNames[1] = "bed2";
        bedNames[2] = "bed3";

        String bedURLs[] = new String[3];
        bedURLs[0] = "http://localhost:2538/bed/bed1";
        bedURLs[1] = "http://localhost:2538/bed/bed2";
        bedURLs[2] = "http://localhost:2538/bed/bed3";
        List<BufferedImage> qrCodeImages = QRCodes.createBufferedImages(bedURLs);

        QRCodes.writeBufferedImagesToFile(qrCodeImages,bedNames,this.path);

        String uploadId = "first uploadId";

        //assertEquals("there should be three files in test path after","bed1.png",);
        //QRCodes.writeBufferedImagesToFile(qrCodeImages,bedNames,this.path);

        // Writes it to the right path.
        String qr = QRCodes.writeZipPathForQRCodes(uploadId,path);
        assertEquals("File output names should be the same","QR Code Export " + "first uploadId" + ".zip",qr);
    }


}
