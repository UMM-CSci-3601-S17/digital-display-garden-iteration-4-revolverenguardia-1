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
        // Set up our server address
        // (Default host: 'localhost', default port: 27017)
        // ServerAddress testAddress = new ServerAddress();

        // Try connecting to the server
        //MongoClient mongoClient = new MongoClient(testAddress, credentials);
        MongoClient mongoClient = new MongoClient(); // Defaults!

        // Try connecting to a database
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        bedCollection = db.getCollection("beds");

    }

//    public boolean incrementBedMetadata(String bedNum, String field, String uploadId) {
//
//        Document searchDocument = new Document();
//
//        searchDocument.append("id", bedNum);
//        searchDocument.append("uploadId", uploadId);
//
//        Bson updateDocument = inc("metadata." + field, 1);
//
//        return null != bedCollection.findOneAndUpdate(searchDocument, updateDocument);
//    }
//

//    public boolean addBedVisit(String plantID, String uploadId) {
//
//        Document filterDoc = new Document();
//        filterDoc.append("id", plantID);
//        filterDoc.append("uploadId", uploadId);
//
//        Document visit = new Document();
//        visit.append("visit", new ObjectId());
//
//        return null != bedCollection.findOneAndUpdate(filterDoc, push("metadata.visits", visit));
//    }
//



}
