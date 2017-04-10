package umm3601.plant;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ExcelParser;
import umm3601.digitalDisplayGarden.FeedbackWriter;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by dansa on 4/10/2017.
 */
public class TestExportFeedback {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }

    @Test
    public void testExportFeedback() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        FeedbackWriter feedback = new FeedbackWriter(buffer);
        String [] input = new String[6];
        for(int i = 0; i < 4; i++) {
            input[FeedbackWriter.COL_CMT_PLANTID] = "16001";
            input[FeedbackWriter.COL_CMT_COMMONNAME] = "Alternathera";
            input[FeedbackWriter.COL_CMT_CULTIVAR] = "Experimental";
            input[FeedbackWriter.COL_CMT_GRDNLOC] = "1S";
            input[FeedbackWriter.COL_CMT_COMMENT] = "What a lovely flower! " + i;
            input[FeedbackWriter.COL_CMT_DATE] = "4 10 2017"; //This isn't the format
            feedback.writeToSheet(input, FeedbackWriter.SHEET_COMMENTS);
        }

        input = new String[9];
        for(int i = 0; i < 2; i++) {
            input[FeedbackWriter.COL_META_PLANTID] = "16001";
            input[FeedbackWriter.COL_META_COMMONNAME] = "Alternathera";
            input[FeedbackWriter.COL_META_CULTIVAR] = "Experimental";
            input[FeedbackWriter.COL_META_GRDNLOC] = "1S";
            input[FeedbackWriter.COL_META_LIKES] = Integer.toString(i);
            input[FeedbackWriter.COL_META_DISLIKES] = "0";
            input[FeedbackWriter.COL_META_COMMENTS] = "4";
            input[FeedbackWriter.COL_META_QRSCANS] = "1";
            input[FeedbackWriter.COL_META_PAGEVIEWS] = "4";
            feedback.writeToSheet(input, FeedbackWriter.SHEET_METADATA);
        }

        feedback.complete();

        //Use that output stream and produce an input stream from the bytes to
        //read the spreadsheet into an XSSFWorkbook
        ByteArrayInputStream reRead = new ByteArrayInputStream(buffer.toByteArray());
        Workbook workbook = new XSSFWorkbook(reRead);
        assertEquals("Excel Spreadsheet does not contain two Sheets", workbook.getNumberOfSheets(), 2);

        Sheet sheet;

        sheet = workbook.getSheetAt(0); //Comment Sheet
        assertEquals("Comment Sheet does not contain 4+2 rows",sheet.getLastRowNum() - sheet.getFirstRowNum(), 4+2);

        sheet = workbook.getSheetAt(1); //Metadata Sheet
        assertEquals("Metadata Sheet does not contain 2+2 rows",sheet.getLastRowNum() - sheet.getFirstRowNum(), 2+2);



    }

}
