package umm3601.digitalDisplayGarden;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.bson.conversions.Bson;

import java.io.OutputStream;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.fields;
import static umm3601.digitalDisplayGarden.FeedbackWriter.*;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Updates.push;

public class PlantController {

    private final MongoCollection<Document> plantCollection;
    private final MongoCollection<Document> commentCollection;
    private final MongoCollection<Document> configCollection;
    private final MongoCollection<Document> bedCollection;

    public PlantController(String databaseName) throws IOException {
        // Set up our server address
        // (Default host: 'localhost', default port: 27017)
        // ServerAddress testAddress = new ServerAddress();

        // Try connecting to the server
        //MongoClient mongoClient = new MongoClient(testAddress, credentials);
        MongoClient mongoClient = new MongoClient(); // Defaults!

        // Try connecting to a database
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        plantCollection = db.getCollection("plants");
        commentCollection = db.getCollection("comments");
        configCollection = db.getCollection("config");
        bedCollection = db.getCollection("beds");

    }

    // List plants
    public String listPlants(Map<String, String[]> queryParams, String uploadId) {
        Document filterDoc = new Document();
        filterDoc.append("uploadId", uploadId);

        if (queryParams.containsKey("gardenLocation")) {
            String location =(queryParams.get("gardenLocation")[0]);
            filterDoc = filterDoc.append("gardenLocation", location);
        }


        if (queryParams.containsKey("commonName")) {
            String commonName =(queryParams.get("commonName")[0]);
            filterDoc = filterDoc.append("commonName", commonName);
        }

        FindIterable<Document> matchingPlants = plantCollection.find(filterDoc);

        return JSON.serialize(matchingPlants);
    }

    /**
     * Takes a String representing an ID number of a plant
     * and when the ID is found in the database returns a JSON document
     * as a String of the following form
     *
     * <code>
     * {
     *  "plantID"        : String,
     *  "commonName" : String,
     *  "cultivar"   : String
     * }
     * </code>
     *
     * If the ID is invalid or not found, the following JSON value is
     * returned
     *
     * <code>
     *  null
     * </code>
     *
     * @param plantID an ID number of a plant in the DB
     * @param uploadID Dataset to find the plant
     * @return a string representation of a JSON value
     */
    public String getPlantByPlantID(String plantID, String uploadID) {

        FindIterable<Document> jsonPlant;
        String returnVal;
        try {

            jsonPlant = plantCollection.find(and(eq("id", plantID),
                    eq("uploadId", uploadID)))
                    .projection(fields(include("id", "commonName", "cultivar", "gardenLocation")));

            Iterator<Document> iterator = jsonPlant.iterator();

            if (iterator.hasNext()) {
                incrementMetadata(plantID, "pageViews", uploadID);
                addVisit(plantID, uploadID);
                returnVal = iterator.next().toJson();
            } else {
                returnVal = "null";
            }

        } catch (IllegalArgumentException e) {
            returnVal = "null";
        }

        return returnVal;

    }

    /**
     *
     * @param plantID The plant to get feedback of
     * @param uploadID Dataset to find the plant
     *
     * @return JSON for the number of comments, likes, and dislikes
     * Of the form:
     * {
     *     commentCount: number
     *     likeCount: number
     *     dislikeCount: number
     * }
     */

    public String getFeedbackForPlantByPlantID(String plantID, String uploadID) {
        Document out = new Document();

        Document filter = new Document();
        filter.put("commentOnPlant", plantID);
        filter.put("uploadId", uploadID);
        long comments = commentCollection.count(filter);
        long likes = 0;
        long dislikes = 0;


        //Get a plant by plantID
        FindIterable doc = plantCollection.find(new Document().append("id", plantID).append("uploadId", uploadID));

        Iterator iterator = doc.iterator();
        if(iterator.hasNext()) {
            Document result = (Document) iterator.next();

            //Get metadata.rating array
            List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("ratings");

            //Loop through all of the entries within the array, counting like=true(like) and like=false(dislike)
            for(Document rating : ratings)
            {
                if(rating.get("like").equals(true))
                    likes++;
                else if(rating.get("like").equals(false)) // THIS IS IMPORTANT (do not delete)
                    dislikes++;
            }
        }


        out.put("commentCount", comments);
        out.put("likeCount", likes);
        out.put("dislikeCount", dislikes);
        return JSON.serialize(out);
    }

    public String getGardenLocationsAsJson(String uploadID){
        AggregateIterable<Document> documents
                = plantCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(eq("uploadId", uploadID)), //!! Order is important here
                        Aggregates.group("$gardenLocation"),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        return JSON.serialize(documents);
    }

    public String[] getGardenLocations(String uploadID){
        Document filter = new Document();
        filter.append("uploadId", uploadID);
        DistinctIterable<String>  bedIterator = plantCollection.distinct("gardenLocation", filter, String.class);
        List<String> beds = new ArrayList<String>();
        for(String s : bedIterator)
        {
            beds.add(s);
        }
        return beds.toArray(new String[beds.size()]);
    }

    public String getCommonNamesAsJson(String uploadID){
        AggregateIterable<Document> documents
                = plantCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(eq("uploadId", uploadID)), //!! Order is important here
                        Aggregates.group("$commonName"),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        return JSON.serialize(documents);
    }

    /**
     * Accepts string representation of JSON object containing
     * at least the following.
     * <code>
     *     {
     *         plantId: String,
     *         comment: String
     *     }
     * </code>
     * If either of the keys are missing or the types of the values are
     * wrong, false is returned.
     * @param json string representation of JSON object
     * @param uploadID Dataset to find the plant
     * @return true iff the comment was successfully submitted
     */

    public boolean storePlantComment(String json, String uploadID) {
        try {

            Document toInsert = new Document();
            Document parsedDocument = Document.parse(json);

            //If request contains plantId get the plant json by the plantId and current uploadID
            //Add a comment to commentCollection with the plantId, uploadID, and
            if (parsedDocument.containsKey("plantId") && parsedDocument.get("plantId") instanceof String) {

                FindIterable<Document> jsonPlant = plantCollection.find(and(eq("id",
                        parsedDocument.getString("plantId")), eq("uploadId", uploadID)));

                Iterator<Document> iterator = jsonPlant.iterator();

                if(iterator.hasNext()){
                    toInsert.put("commentOnPlant", iterator.next().getString("id"));
                } else {
                    System.err.println("Was passed malformed storePlantComment request");
                    return false;
                }
//                toInsert.put("liveUploadId", uploadID);

            } else {
                System.err.println("storePlantComment request does not contain plantId");
                return false;
            }

            if (parsedDocument.containsKey("comment") && parsedDocument.get("comment") instanceof String) {
                toInsert.put("comment", parsedDocument.getString("comment"));
            } else {
                System.err.println("storePlantComment request does not contain comment");
                return false;
            }

            toInsert.append("uploadId", uploadID);

            commentCollection.insertOne(toInsert);

        } catch (BsonInvalidOperationException e){
            e.printStackTrace();
            return false;
        } catch (org.bson.json.JsonParseException e){
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean writeFeedback(OutputStream outputStream, String uploadId) throws IOException {

        FeedbackWriter feedbackWriter = new FeedbackWriter(outputStream);

        FindIterable iter = commentCollection.find(
                and(
                        exists("commentOnPlant"),
                        eq("uploadId", uploadId)
                ));
        Iterator iterator = iter.iterator();

        //Loop through all comments and add their entries
        while (iterator.hasNext()) {
            Document comment = (Document) iterator.next();
            Iterator<Document> onPlantItr = plantCollection.find(and(eq("id", comment.getString("commentOnPlant")), eq("uploadId", uploadId))).iterator();
            Document onPlant;
            onPlant = onPlantItr.next(); //NOTE: this should _not_ create an exception, and if it does, the database is corrupt
            try {
                String[] dataToWrite = new String[COL_CMT_FIELDS];
                dataToWrite[COL_CMT_PLANTID] = onPlant.getString("id");
                dataToWrite[COL_CMT_COMMONNAME] = onPlant.getString("commonName");
                dataToWrite[COL_CMT_CULTIVAR] = onPlant.getString("cultivar");
                dataToWrite[COL_CMT_GRDNLOC] = onPlant.getString("gardenLocation");
                dataToWrite[COL_CMT_COMMENT] = comment.getString("comment");
                dataToWrite[COL_CMT_DATE] = ((ObjectId) comment.get("_id")).getDate().toString();

                feedbackWriter.writeToSheet(dataToWrite, FeedbackWriter.SHEET_COMMENTS);
            } catch (Exception e) {
                System.err.println("ERROR ON PLANT " + onPlant);
                e.printStackTrace();
            }
        }

        //Loop through all plants
        iter = plantCollection.find(
                    eq("uploadId", uploadId)

        );
        iterator = iter.iterator();

        while (iterator.hasNext()) {
            Document onPlant = (Document) iterator.next();

            try {
                String[] dataToWrite = new String[COL_PLANT_FIELDS];
                Document metadata = (Document) onPlant.get("metadata");
                Document feedback = Document.parse(getFeedbackForPlantByPlantID(onPlant.getString("id"), uploadId));


                Integer likeCount = feedback.getInteger("likeCount");
                Integer dislikeCount = feedback.getInteger("dislikeCount");
                Integer commentCount = feedback.getInteger("commentCount");
                Integer pageViews = metadata.getInteger("pageViews");

                dataToWrite[COL_PLANT_PLANTID] = onPlant.getString("id");
                dataToWrite[COL_PLANT_COMMONNAME] = onPlant.getString("commonName");
                dataToWrite[COL_PLANT_CULTIVAR] = onPlant.getString("cultivar");
                dataToWrite[COL_PLANT_GRDNLOC] = onPlant.getString("gardenLocation");


                dataToWrite[COL_PLANT_LIKES] = likeCount.toString();
                dataToWrite[COL_PLANT_DISLIKES] = dislikeCount.toString();
                dataToWrite[COL_PLANT_COMMENTS] = commentCount.toString();
                dataToWrite[COL_PLANT_PAGEVIEWS] = pageViews.toString();

                feedbackWriter.writeToSheet(dataToWrite, FeedbackWriter.SHEET_METADATA);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.err.println("ERROR ON PLANT " + onPlant);
            }
        }

        //Loop through all beds
        iter = bedCollection.find(
                eq("uploadId", uploadId)

        );
        iterator = iter.iterator();

        while (iterator.hasNext()) {
            Document onBed = (Document) iterator.next();

            try {
                String[] dataToWrite = new String[COL_BED_FIELDS];
                Document metadata = (Document) onBed.get("metadata");
                //Document stuff = Document.parse(getGardenLocationsAsJson(onBed.getString("id")));


                Integer pageViews = metadata.getInteger("pageViews");
                Integer qrScans = metadata.getInteger("qrScans");

                dataToWrite[COL_BED_GRDNLOC] = onBed.getString("gardenLocation");


                //This wil only write one objectId to the Excel Sheet under visits
//                Document visit = visits.get(0);
//                String bedVisit = visit.get("visit").toString();


                dataToWrite[COL_BED_PAGEVIEWS] = pageViews.toString();
                dataToWrite[COL_BED_QRSCANS] = qrScans.toString();

                feedbackWriter.writeToSheet(dataToWrite, FeedbackWriter.SHEET_BEDMETADATA);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.err.println("ERROR ON BED " + onBed);
            }
        }

        feedbackWriter.complete();
        return true;
    }

    /**
     * Adds a like or dislike to the specified plant.
     *
     * @param id a hexstring specifiying the oid
     * @param like true if this is a like, false if this is a dislike
     * @param uploadID Dataset to find the plant
     * @return true iff the operation succeeded.
     */

    public boolean addFlowerRating(String id, boolean like, String uploadID) {
        System.out.println("id=" + id + " like=" + like + " uploadId=" + uploadID);
        Document filterDoc = new Document();


        filterDoc.append("id", id);
        filterDoc.append("uploadId", uploadID);

        Document plantOID;

        Iterator<Document> itr = plantCollection.find(filterDoc).iterator();
        if(itr.hasNext()) {
            plantOID = itr.next();
        }
        else
        {
            System.err.println("Plant not found with plantId " + id + " for uploadId " + uploadID);
            return false;
        }
        

        Document rating = new Document();
        rating.append("like", like);
        rating.append("ratingOnObjectOfId", new ObjectId(plantOID.get("_id").toString()));

        return null != plantCollection.findOneAndUpdate(filterDoc, push("metadata.ratings", rating));
    }

    /**
     * Accepts string representation of JSON object containing
     * at least the following:
     * <code>
     *     {
     *         id: String,
     *         like: boolean
     *     }
     * </code>
     *
     * @param json string representation of a JSON object
     * @param uploadID Dataset to find the plant
     * @return true iff the operation succeeded.
     */

    public boolean addFlowerRating(String json, String uploadID){
        boolean like;
        String id;

        try {

            Document parsedDocument = Document.parse(json);

            if(parsedDocument.containsKey("id") && parsedDocument.get("id") instanceof String){
                id = parsedDocument.getString("id");
            } else {
                return false;
            }

            if(parsedDocument.containsKey("like") && parsedDocument.get("like") instanceof Boolean){
                like = parsedDocument.getBoolean("like");
            } else {
                return false;
            }

        } catch (BsonInvalidOperationException e){
            e.printStackTrace();
            return false;
        } catch (org.bson.json.JsonParseException e){
            return false;
        }

        return addFlowerRating(id, like, uploadID);
    }







    /**
     * Finds a plant and atomically increments the specified field
     * in its metadata object. This method returns true if the plant was
     * found successfully (false otherwise), but there is no indication of
     * whether the field was found.
     *
     * @param plantID a ID number of a plant in the DB
     * @param field a field to be incremented in the metadata object of the plant
     * @param uploadId the uploadId of the metadata field
     * @return true if a plant was found
     * @throws com.mongodb.MongoCommandException when the id is valid and the field is empty
     */
    public boolean incrementMetadata(String plantID, String field, String uploadId) {

        Document searchDocument = new Document();
        searchDocument.append("id", plantID);
        searchDocument.append("uploadId", uploadId);

        Bson updateDocument = inc("metadata." + field, 1);

        return null != plantCollection.findOneAndUpdate(searchDocument, updateDocument);
    }

    public boolean addVisit(String plantID, String uploadId) {

        Document filterDoc = new Document();
        filterDoc.append("id", plantID);
        filterDoc.append("uploadId", uploadId);

        Document visit = new Document();
        visit.append("visit", new ObjectId());

        return null != plantCollection.findOneAndUpdate(filterDoc, push("metadata.visits", visit));
    }

}