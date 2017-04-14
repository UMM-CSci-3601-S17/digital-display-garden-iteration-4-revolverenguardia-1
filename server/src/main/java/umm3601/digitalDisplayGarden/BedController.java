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
/**
 * Created by carav008 on 4/11/17.
 */
public class BedController {

    private final MongoCollection<Document> bedCollection;

    public BedController(String databaseName) throws IOException {
        MongoClient mongoClient = new MongoClient(); // Defaults!

        // Try connecting to a database
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        bedCollection = db.getCollection("beds");
    }

    public boolean incrementBedMetadata(String gardenLocation, String field, String uploadId) {

        Document searchDocument = new Document();

        searchDocument.append("gardenLocation", gardenLocation);
        searchDocument.append("uploadId", uploadId);

        Bson updateDocument = inc("metadata." + field, 1);

        return null != bedCollection.findOneAndUpdate(searchDocument, updateDocument);
    }


    public boolean addBedVisit(String gardenLocation, String uploadId) {

        Document filterDoc = new Document();
        filterDoc.append("gardenLocation", gardenLocation);
        filterDoc.append("uploadId", uploadId);

        Document visit = new Document();
        visit.append("visit", new ObjectId());

        incrementBedMetadata(gardenLocation, "pageViews", uploadId);

        return null != bedCollection.findOneAndUpdate(filterDoc, push("metadata.bedVisits", visit));
    }

    public boolean addBedQRVisit(String gardenLocation, String uploadId) {

        Document filterDoc = new Document();
        filterDoc.append("gardenLocation", gardenLocation);
        filterDoc.append("uploadId", uploadId);

        Document visit = new Document();
        visit.append("visit", new ObjectId());

        Document scan = new Document();
        scan.append("scan", new ObjectId());

        incrementBedMetadata(gardenLocation, "pageViews", uploadId);
        incrementBedMetadata(gardenLocation, "qrScans", uploadId);

        Document a = bedCollection.findOneAndUpdate(filterDoc, push("metadata.bedVisits", visit));
        if(a == null)
            return false;
        Document b = bedCollection.findOneAndUpdate(filterDoc, push("metadata.qrVisits", scan));

        return null != b;
    }

    public int getPageViews(String gardenLocation, String uploadId)
    {
        Document filterDoc = new Document();
        filterDoc.append("gardenLocation", gardenLocation);
        filterDoc.append("uploadId", uploadId);

        Iterator<Document> bedItr = bedCollection.find(filterDoc).iterator();

        try {
            if(bedItr.hasNext()) {
                Document bed = bedItr.next();
                Document bedMetadata = (Document)bed.get("metadata");

                return bedMetadata.getInteger("pageViews");
            }
            else throw new RuntimeException("No bed found for gardenLocation=" + gardenLocation + " uploadId=" + uploadId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

    }



}
