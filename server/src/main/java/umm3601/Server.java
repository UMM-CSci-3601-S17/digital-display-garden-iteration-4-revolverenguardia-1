package umm3601;

import com.mongodb.DB;
import com.mongodb.MongoCommandException;
import org.bson.Document;
import spark.Route;
import spark.utils.IOUtils;
import com.mongodb.util.JSON;
import umm3601.digitalDisplayGarden.ImageHandler;
import umm3601.digitalDisplayGarden.PlantController;

import spark.Route;
import spark.utils.IOUtils;
import com.mongodb.util.JSON;
import umm3601.digitalDisplayGarden.BedController;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static spark.Spark.*;

import umm3601.digitalDisplayGarden.ExcelParser;
import umm3601.digitalDisplayGarden.QRCodes;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;


public class Server {

    public static final String API_URL = "http://localhost:2538";

    public static String databaseName = "test";

    private static String excelTempDir = "/tmp/digital-display-garden";

    private static String imageDir = "./src/main/java/umm3601/images";

    public static void main(String[] args) throws IOException {

        port(2538);

        // This users looks in the folder `public` for the static web artifacts,
        // which includes all the HTML, CSS, and JS files generated by the Angular
        // build. This `public` directory _must_ be somewhere in the classpath;
        // a problem which is resolved in `server/build.gradle`.
        staticFiles.location("/public");

        PlantController plantController = new PlantController(databaseName);
        GardenCharts chartMaker = new GardenCharts(databaseName);
        BedController bedController = new BedController(databaseName);

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
 
            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Redirects for the "home" page
        redirect.get("", "/");

        Route clientRoute = (req, res) -> {
            InputStream stream = plantController.getClass().getResourceAsStream("/public/index.html");
            return IOUtils.toString(stream);
        };

        get("/", clientRoute);


        /*///////////////////////////////////////////////////////////////////
         * BEGIN VISITOR ENDPOINTS
         *////////////////////////////////////////////////////////////////////

        // Return all plants
        get("api/plants", (req, res) -> {
            res.type("application/json");
            return plantController.listPlants(req.queryMap().toMap(), getLiveUploadId());
        });

        //Get a plant by plantId
        get("api/plant/:plantID", (req, res) -> {
            res.type("application/json");
            String id = req.params("plantID");
            return plantController.getPlantByPlantID(id, getLiveUploadId());
        });

        //Get feedback counts for a plant
        get("api/plant/:plantID/counts", (req, res) -> {
            res.type("application/json");
            String id = req.params("plantID");
            return plantController.getPlantFeedbackByPlantIdJSON(id,getLiveUploadId());
        });

        //List all Beds
        get("api/gardenLocations", (req, res) -> {
            res.type("application/json");
            return plantController.getGardenLocationsJSON(getLiveUploadId());
        });

        //List all Common Names
        get("api/commonNames", (req, res) -> {
            res.type("application/json");
            return plantController.getCommonNamesJSON(getLiveUploadId());
        });

        post("api/plant/rate", (req, res) -> {
            System.out.println("api/plant/rate " + req.body());
            res.type("application/json");
            return plantController.addFlowerRating(req.body(),getLiveUploadId());
        });



        post("api/bedVisit", (req, res) -> {
            res.type("application/json");
            String body = req.body();
            //Increment bedCount
            bedController.addBedVisit(body, getLiveUploadId());
            return true;
        });

        post("api/qrVisit", (req, res) -> {
            res.type("application/json");
            String body = req.body();

            //Increment bedCount
            //Increment qrForBedCount
            bedController.addBedQRVisit(body, getLiveUploadId());
            return true;
        });

        // Posting a comment
        post("api/plant/leaveComment", (req, res) -> {
            res.type("application/json");
            return plantController.storePlantComment(req.body(), getLiveUploadId());
        });

        /*///////////////////////////////////////////////////////////////////
         * END VISITOR ENDPOINTS
         *////////////////////////////////////////////////////////////////////
        /*///////////////////////////////////////////////////////////////////
         * ADMIN ENDPOINTS
         *////////////////////////////////////////////////////////////////////

        // Accept an xls file
        post("api/admin/import", (req, res) -> {

            res.type("application/json");
            try {

                MultipartConfigElement multipartConfigElement = new MultipartConfigElement(excelTempDir);
                req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
                Part part = req.raw().getPart("file[]");

                ExcelParser parser = new ExcelParser(part.getInputStream(), databaseName);

                String id = ExcelParser.generateNewUploadId();
                String[][] excelFile = parser.parseExcel();
                parser.populateDatabase(excelFile, id);

                return JSON.serialize(id);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        });


        //Patch from spreadsheet
        post("api/admin/patch", (req, res) -> {

            res.type("application/json");
            try {

                MultipartConfigElement multipartConfigElement = new MultipartConfigElement(excelTempDir);
                req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
                Part part = req.raw().getPart("file[]");

                ExcelParser parser = new ExcelParser(part.getInputStream(), databaseName);

                String oldUploadId = getLiveUploadId();
                String newUploadId = ExcelParser.generateNewUploadId();
                String[][] excelFile = parser.parseExcel();
                parser.patchDatabase(excelFile, oldUploadId, newUploadId);

                return JSON.serialize(newUploadId);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        });

        get("api/admin/export", (req, res) -> {
            // Note that after flush() or close() is called on
            // res.raw().getOutputStream(), the response can no longer be
            // modified. Since writeComment(..) closes the OutputStream
            // when it is done, it needs to be the last line of this function.
            //REVISED to attempt to fix bug where first write always breaks.
            // If an exception is thrown (specifically within workbook.write() within complete() in FeedbackWriter
            // This loop will attempt to write feedback twice, writing to an intermediate buffer.
            // If the write succeeds, then write it to the response output stream
            int error = 6;
            while(error > 0) {
                try {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    plantController.writeFeedback(buffer, req.queryMap().toMap().get("uploadId")[0]);

                    res.type("application/vnd.ms-excel");
                    res.header("Content-Disposition", "attachment; filename=\"Garden-Visitor data.xlsx\"");

                    OutputStream out = res.raw().getOutputStream();
                    out.write(buffer.toByteArray());
                    out.flush();
                    out.close();
                    error = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    error--;
                    if(error == 0)
                    {
                        //If all attempts fail, produce an Internal Server Error 500
                        throw e;
                    }
                }
            }


            return res;
        });

        // List all uploadIds
        get("api/admin/uploadIds", (req, res) -> {
            res.type("application/json");
            return ExcelParser.listUploadIds(databaseName);
        });

        get("api/admin/qrcodes", (req, res) -> {
            res.type("application/zip");

            //Creates a Zip file, found at zipPath
            String liveUploadID = getLiveUploadId();
            String zipPath = QRCodes.CreateQRCodesFromAllBeds(
                    liveUploadID,
                    plantController.getGardenLocations(liveUploadID),
                    API_URL + "/bed/");

            if(zipPath == null)
                return null;

            res.header("Content-Disposition","attachment; filename=\"" + zipPath + "\"");

            //Get bytes from the file
            File zipFile = new File(zipPath);
            byte[] bytes = spark.utils.IOUtils.toByteArray(new FileInputStream(zipFile));

            //Delete local .zip file
            Files.delete(Paths.get(zipPath));

            return bytes;
        });

        get("api/admin/liveUploadId", (req, res) -> {
            res.type("application/json");
            return JSON.serialize(getLiveUploadId());
        });


        /*///////////////////////////////////////////////////////////////////
            BEGIN CHARTS
        *////////////////////////////////////////////////////////////////////

        // Views per Hour
        get("api/admin/charts/viewsPerHour", (req, res) -> {
            res.type("application/json");
            return chartMaker.getPlantViewsPerHour(getLiveUploadId());
        });

        get("api/admin/charts/plantMetadataMap", (req, res) -> {
            res.type("application/json");

            return chartMaker.getBedMetadataForMap(plantController, getLiveUploadId());
        });

        get("api/admin/charts/plantMetadataBubbleMap", (req, res) -> {
            res.type("application/json");

            return chartMaker.getBedMetadataForBubbleMap(plantController, bedController, getLiveUploadId());
        });

        //Host the aerial image of the Garden
        get("api/admin/gardenPicture", (req, res) -> {
            res.type("application/png");
            String gardenPath = "/Garden.png";

            return plantController.getClass().getResourceAsStream(gardenPath);
        });



        /*///////////////////////////////////////////////////////////////////
            END CHARTS
        *////////////////////////////////////////////////////////////////////
        /*///////////////////////////////////////////////////////////////////
         * BEGIN IMAGES
         */ ///////////////////////////////////////////////////////////////////


        get("api/admin/getImage/:name", (req, res) -> {
            res.type("application/jpg");
            String plantID = req.params("name");

            ImageHandler handler = new ImageHandler(res.raw().getOutputStream());

            handler.getImageOnFilesystem(plantID);

            return res;
        });

        post("api/upload-photo", (req, res) -> {

            res.type("application/json");
            try {

                MultipartConfigElement multipartConfigElement = new MultipartConfigElement(Server.imageDir);
                req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

                MongoClient mongoClient = new MongoClient( "localhost",27017 );

                MongoDatabase photoDB = mongoClient.getDatabase("photoDB");
                photoDB.getCollection("sus");
                try {
                    photoDB.createCollection("photoFilepathCollection");
                } catch (MongoCommandException e) {

                }


                Part part = req.raw().getPart("file[]");
                Part part0 = req.raw().getPart("name");
                Part part1 = req.raw().getPart("flower");

                ImageHandler handler = new ImageHandler(part.getInputStream(), part0.getInputStream(), part1.getInputStream());
                Image img = handler.extractImage();
                String fileName = handler.extractFileName();
                String flowerName = handler.extractFlowerName();

                Random rand = new Random();
                File newDir = new File(Server.imageDir);
                newDir.mkdirs();
                String pathName = Server.imageDir + "/" + flowerName + "/" + fileName + rand.nextInt(9999999);
                //photoDB.getCollection("photoFilepathCollection").insertOne();//(Document.parse("{ " + pathName.substring(1) + " }"));
                File imageDir = new File(pathName);
                imageDir.mkdirs();
                try {
                    ImageIO.write((RenderedImage)img,"png", imageDir);

                } catch (IOException e) {

                }

                String id = ImageHandler.getAvailableUploadId();

                return JSON.serialize(id);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        });

        get("/*", clientRoute);

        // Handle "404" file not found requests:
        notFound((req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!";
        });
    }
        /*///////////////////////////////////////////////////////////////////
         * END IMAGES
         */ ///////////////////////////////////////////////////////////////////

        /*///////////////////////////////////////////////////////////////////
         * END ADMIN ENDPOINTS
         */ ///////////////////////////////////////////////////////////////////


    public static String getLiveUploadId()
    {
        return ExcelParser.getLiveUploadId(databaseName);
    }
}
