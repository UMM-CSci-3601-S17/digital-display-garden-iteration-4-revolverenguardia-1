package umm3601.digitalDisplayGarden;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FeedbackWriter {

    OutputStream outputStream;
    XSSFWorkbook workbook;
    XSSFSheet bedmetadataSheet;

    XSSFSheet commentSheet;
    XSSFSheet metadataSheet;
    int commentRowCount;
    int metadataRowCount;
    int bedmetadataRowCount;


    XSSFCellStyle styleCentered;
    XSSFCellStyle styleWordWrap;

    //Comment Sheet Column Designations
    public static final int COL_CMT_PLANTID = 0,
                                COL_CMT_COMMONNAME = 1,
                                COL_CMT_CULTIVAR = 2,
                                COL_CMT_GRDNLOC = 3,
                                COL_CMT_COMMENT = 4,
                                COL_CMT_DATE = 5;
    public static final int COL_CMT_FIELDS = 6;

    public static final int COL_PLANT_PLANTID = 0,
                                COL_PLANT_COMMONNAME = 1,
                                COL_PLANT_CULTIVAR = 2,
                                COL_PLANT_GRDNLOC = 3,
                                COL_PLANT_LIKES = 4,
                                COL_PLANT_DISLIKES = 5,
                                COL_PLANT_COMMENTS = 6,
                                COL_PLANT_PAGEVIEWS = 7;
    public static final int COL_PLANT_FIELDS = 8;

    public static final int COL_BED_GRDNLOC = 0,
                            COL_BED_PAGEVIEWS = 1;
    public static final int COL_BED_VISITS = 2;
    public static final int COL_BED_QRSCANS = 3;

    public static final int COL_BED_FIELDS = 4;

    public static final int SHEET_COMMENTS = 0,
                            SHEET_METADATA = 1;
    public static final int SHEET_BEDMETADATA = 2;

    public FeedbackWriter(OutputStream outputStream) throws IOException{
        this.outputStream = outputStream;
        this.workbook = new XSSFWorkbook();

        styleCentered = workbook.createCellStyle();
        styleCentered.setAlignment(HorizontalAlignment.CENTER);

        styleWordWrap = workbook.createCellStyle();
        styleWordWrap.setWrapText(true);
        styleWordWrap.setVerticalAlignment(VerticalAlignment.TOP);
        styleWordWrap.setAlignment(HorizontalAlignment.LEFT);

        prepareCommentSheet();
        prepareMetadataSheet();
        prepareBedMetadataSheet();

    }




    private void prepareCommentSheet()
    {
        this.commentSheet = workbook.createSheet("Comments");

        Row row1 = commentSheet.createRow(0);
        Row row2 = commentSheet.createRow(1);

        Cell cell;

        cell = row2.createCell(COL_CMT_PLANTID);
        cell.setCellValue("#");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_CMT_COMMONNAME);
        cell.setCellValue("Common");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_CMT_COMMONNAME);
        cell.setCellValue("Name");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_CMT_CULTIVAR);
        cell.setCellValue("Cultivar");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_CMT_GRDNLOC);
        cell.setCellValue("Garden");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_CMT_GRDNLOC);
        cell.setCellValue("Location");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_CMT_COMMENT);
        cell.setCellValue("Comment");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_CMT_DATE);
        cell.setCellValue("Time of Comment");
        cell.setCellStyle(styleCentered);

        commentSheet.setColumnWidth(COL_CMT_PLANTID,1600);
        commentSheet.setColumnWidth(COL_CMT_GRDNLOC,1900);
        commentSheet.setColumnWidth(COL_CMT_COMMONNAME,3200);
        commentSheet.setColumnWidth(COL_CMT_CULTIVAR,4800);
        commentSheet.setColumnWidth(COL_CMT_COMMENT,8400);
        commentSheet.setColumnWidth(COL_CMT_DATE,6800);
        commentSheet.createFreezePane(0, 2);

        CellRangeAddress rangeAddress = CellRangeAddress.valueOf("A2:F1200");
        commentSheet.setAutoFilter(rangeAddress);

        commentRowCount = 2;
    }

    private void prepareMetadataSheet()
    {
        this.metadataSheet = workbook.createSheet("Metadata");

        Row row1 = metadataSheet.createRow(0);
        Row row2 = metadataSheet.createRow(1);

        Cell cell;

        cell = row2.createCell(COL_PLANT_PLANTID);
        cell.setCellValue("#");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_PLANT_COMMONNAME);
        cell.setCellValue("Common");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_PLANT_COMMONNAME);
        cell.setCellValue("Name");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_PLANT_CULTIVAR);
        cell.setCellValue("Cultivar");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_PLANT_GRDNLOC);
        cell.setCellValue("Garden");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_PLANT_GRDNLOC);
        cell.setCellValue("Location");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_PLANT_LIKES);
        cell.setCellValue("Likes");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_PLANT_DISLIKES);
        cell.setCellValue("Dislikes");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_PLANT_COMMENTS);
        cell.setCellValue("Comments");
        cell.setCellStyle(styleCentered);

        /*
        cell = row1.createCell(COL_META_QRSCANS);
        cell.setCellValue("QR");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_META_QRSCANS);
        cell.setCellValue("Scans");
        cell.setCellStyle(styleCentered);
        */

        cell = row1.createCell(COL_PLANT_PAGEVIEWS);
        cell.setCellValue("Page");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_PLANT_PAGEVIEWS);
        cell.setCellValue("Views");
        cell.setCellStyle(styleCentered);

        metadataSheet.setColumnWidth(COL_PLANT_PLANTID,1600);
        metadataSheet.setColumnWidth(COL_PLANT_GRDNLOC,1900);
        metadataSheet.setColumnWidth(COL_PLANT_COMMONNAME,3200);
        metadataSheet.setColumnWidth(COL_PLANT_CULTIVAR,7400);
        metadataSheet.createFreezePane(0, 2);


        CellRangeAddress rangeAddress = CellRangeAddress.valueOf("A2:H1200");
        metadataSheet.setAutoFilter(rangeAddress);
        metadataRowCount = 2;
    }

    private void prepareBedMetadataSheet()
    {
        this.bedmetadataSheet = workbook.createSheet("Bed Metadata");

        Row row1 = bedmetadataSheet.createRow(0);
        Row row2 = bedmetadataSheet.createRow(1);

        Cell cell;

        cell = row1.createCell(COL_BED_GRDNLOC);
        cell.setCellValue("Garden");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_BED_GRDNLOC);
        cell.setCellValue("Location");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_BED_PAGEVIEWS);
        cell.setCellValue("Page");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_BED_PAGEVIEWS);
        cell.setCellValue("Views");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_BED_VISITS);
        cell.setCellValue("Bed");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_BED_VISITS);
        cell.setCellValue("Visits");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_BED_QRSCANS);
        cell.setCellValue("QR");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_BED_QRSCANS);
        cell.setCellValue("Scans");
        cell.setCellStyle(styleCentered);


    /*
    cell = row1.createCell(COL_META_QRSCANS);
    cell.setCellValue("QR");
    cell.setCellStyle(styleCentered);
    cell = row2.createCell(COL_META_QRSCANS);
    cell.setCellValue("Scans");
    cell.setCellStyle(styleCentered);
    */


        bedmetadataSheet.setColumnWidth(COL_BED_GRDNLOC,7400);
        bedmetadataSheet.setColumnWidth(COL_BED_PAGEVIEWS,3200);
        bedmetadataSheet.setColumnWidth(COL_BED_VISITS,3200);
        bedmetadataSheet.setColumnWidth(COL_BED_QRSCANS,3200);
        bedmetadataSheet.createFreezePane(0, 2);


        CellRangeAddress rangeAddress = CellRangeAddress.valueOf("A2:D1200");
        bedmetadataSheet.setAutoFilter(rangeAddress);
        bedmetadataRowCount = 2;
    }



    /**
     * Adds the given information as a new row to the commentSheet.
     * @param data: an array of strings to write to different cells of a new row in a commentSheet
     */
    public void writeToSheet(String[] data, int SHEET){
        XSSFSheet sheet;
        Row row;

        //Get the sheet and the next row to edit on that sheet
        switch(SHEET)
        {
            case SHEET_COMMENTS:
                sheet =  commentSheet;
                row = sheet.createRow(commentRowCount++);
                //Custom row stylings for Comment Sheet entries
                row.setRowStyle(styleWordWrap);
                row.setHeight((short)850);
                break;

            case SHEET_METADATA:
                sheet =  metadataSheet;
                row = sheet.createRow(metadataRowCount++);
                //Custom row stylings for Metadata Sheet entries
//                row.setHeight((short)450);
                break;

            case SHEET_BEDMETADATA:
                sheet =  bedmetadataSheet;
                row = sheet.createRow(bedmetadataRowCount++);
                break;

            default:
                throw new IndexOutOfBoundsException("Sheet " + SHEET + " not understood, getSHEET is being called with incorrect input.");
        }


        for(int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data[i]);
        }

    }



    /**
     * Writes the spreadsheet to the outputstream, then closes it.
     * @throws IOException
     */
    public void complete() throws IOException{
        workbook.write(outputStream);

        outputStream.flush();
        outputStream.close();
    }
}
