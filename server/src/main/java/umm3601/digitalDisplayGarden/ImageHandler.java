package umm3601.digitalDisplayGarden;

import org.joda.time.DateTime;
import umm3601.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by hamme503 on 4/6/17.
 */
public class ImageHandler {

    private InputStream stream;
    private InputStream stream0;
    private OutputStream streamOut;
    private Image image;
    private BufferedImage outputImage;
    private String fileName;
    private String imgFileName;

    public ImageHandler(InputStream stream) {
        this.stream = stream;
    }

    public ImageHandler(OutputStream outStream) {
        streamOut = outStream;
    }

    public Image extractImage() {
        try {
            image = ImageIO.read(stream);
            System.out.println("We got to the handler!");
        } catch (IOException e) {
        }
        return image;
    }

    public void storeImage(String imageDir, String flowerName, Image img) {
        Random rand = new Random();
        File newDir = new File(imageDir);
        File folderDir = new File(imageDir + "/" + flowerName);
        newDir.mkdirs();
        try {
            if (folderDir.listFiles().length != 0) {
                try {
                    Files.delete(Paths.get(imageDir + "/" + flowerName + "/" + folderDir.listFiles()[0].getName()));
                } catch (NoSuchFileException e) {
                    System.err.println("NoSuchFileException when trying to delete old file, path name =" + Paths.get(imageDir + "/" + flowerName + "/" + folderDir.listFiles()[0].getName()).toString());
                } catch (IOException e) {
                    System.err.println("IOException when trying to delete old file");
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Null pointer when trying to list files");
        }
        String pathName = imageDir + "/" + flowerName + "/" + rand.nextInt(9999999);
        //photoDB.getCollection("photoFilepathCollection").insertOne();//(Document.parse("{ " + pathName.substring(1) + " }"));
        File imagePath = new File(pathName);
        imagePath.mkdirs();
        try {
            ImageIO.write((RenderedImage)img,"png", imagePath);

        } catch (IOException e) {

        }
    }

    public String extractFlowerName(InputStream stream) {

        InputStream stream0 = stream;
        String toReturn = convertStreamToString(stream0);
        return toReturn;

    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void getImageOnFilesystem(String flowerName, String filePath) {
        File flowerFolder = new File("./src/main/java/umm3601/images/" + flowerName);
        File fileToBeRead = null;
        //FileOutputStream returnStream = null;
        System.out.println(flowerFolder.toString());
        try {
            fileToBeRead = new File(filePath + flowerName + "/" + flowerFolder.listFiles()[0].getName());
            System.out.println(fileToBeRead.toString());
        } catch (NullPointerException e) {
            System.err.println("Null pointer when trying to read file " + flowerFolder.listFiles());
            //System.out.println(flowerFolder.listFiles().toString());
        }

//        try {
//            returnStream = new FileOutputStream(fileToBeRead);
//        } catch (FileNotFoundException e) {
//
//        }



        try {
            outputImage = ImageIO.read(fileToBeRead);
            System.out.println("Read the file");
        } catch (IOException e) {
            System.err.println("IOException when reading the file");
        } catch (NullPointerException e) {
            System.err.println("No image found for selected flower");
        }

        try {
            ImageIO.write(outputImage, "jpg", streamOut);
        } catch (IOException e) {
            System.err.println("We got an IOException when writing the image to the outputstream");
        }

        // get DataBufferBytes from Raster
//        WritableRaster raster = outputImage.getRaster();
//        DataBuffer data  = raster.getDataBuffer();


//        try {
//            streamOut.write(data.getData());
//        } catch (IOException e) {
//
//        } catch (NullPointerException e) {
//
//        }
    };

    public static String getAvailableUploadId(){

        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(sb);

        java.util.Date juDate = new Date();
        DateTime dt = new DateTime(juDate);

        int day = dt.getDayOfMonth();
        int month = dt.getMonthOfYear();
        int year = dt.getYear();
        int hour = dt.getHourOfDay();
        int minute = dt.getMinuteOfHour();
        int seconds = dt.getSecondOfMinute();

        formatter.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, seconds);
        return sb.toString();

    }



}