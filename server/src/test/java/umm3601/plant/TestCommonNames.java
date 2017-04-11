package umm3601.plant;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestCommonNames {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }

    @Test
    public void GetCommonNameAsJsonRemovesDuplicates() throws IOException {
        CommonName[] commonNames;
        Gson gson = new Gson();

        String json = plantController.getCommonNamesAsJson("third uploadId");
        commonNames = gson.fromJson(json, CommonName[].class);
        assertEquals("Incorrect number of unique common names", 2, commonNames.length);
        assertEquals("Incorrect value for index 0", "Rose", commonNames[0]._id);
        assertEquals("Incorrect value for index 1", "Tulip", commonNames[1]._id);
    }

}

