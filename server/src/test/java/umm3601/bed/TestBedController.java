package umm3601.bed;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import junit.framework.TestCase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.BedController;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Brian on 4/11/2017.
 */
public class TestBedController {
    private final static String databaseName = "data-for-testing-only";
    private BedController bedController;
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        bedController = new BedController(databaseName);
    }

    @Test
    public void TestIncrementBedData(){

        bedController.incrementBedMetadata("7.0","pageViews","first uploadId");
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection beds = db.getCollection("beds");

//        String bedJson = "{ \"_id\" : \"ObjectId(\"58edb5ac0b82b43e307ff9f1\")\", \"gardenLocation\" : \"7.0\", \"metadata\" : " +
//                "{ \"pageViews\" : \"2\", \"visits\" : [ { \"visit\" : \"ObjectId(\"58edb5e90b82b43e307ffa18\")\" }, { \"visit\" : \"ObjectId(\"58edb5ec0b82b43e307ffa1b\")\" } ], \"qrScans\" : [ ] }, \"uploadId\" : \"first uploadId\" }";

        FindIterable doc = beds.find(new Document().append("_id", new ObjectId("58d1c36efb0cac4e15afd302")));
        Iterator iterator = doc.iterator();
        Document result = (Document) iterator.next();

        int bedPageViews =  (int)((Document) result.get("metadata")).get("pageViews");
        System.out.println(bedPageViews);

        assertEquals("this bed should only have 1 pageView",1, bedPageViews);

    }


    @Test
    public void TestBedVisit(){
        bedController.addBedVisit("10.0","second uploadId");

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection beds = db.getCollection("beds");

        FindIterable doc = beds.find(new Document().append("_id", new ObjectId("58d1c36efb0cac4e15afd303")));
        Iterator iterator = doc.iterator();
        Document result = (Document) iterator.next();
        //System.out.println(result);

        List<Document> bedVisits =  (List<Document>)((Document) result.get("metadata")).get("bedVisits");
        //System.out.println(bedVisits);
        assertEquals("",1 ,bedVisits.size());

        Document visits = bedVisits.get(0);
        //System.out.println(visits.get("visit"));
        ObjectId objectId = new ObjectId();

        String v = visits.get("visit").toString();

        //checking to see that the type of visit is an of type/structure of ObjectID
        assertEquals("they should both be of type org.bson.types.ObjectId ",objectId.getClass().getName(),visits.get("visit").getClass().getName());
        assertEquals("the object id produced from a visit must be 24 characters",24,v.length());

    }


}
