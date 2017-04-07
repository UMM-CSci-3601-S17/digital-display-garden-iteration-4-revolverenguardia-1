package umm3601.digitalDisplayGarden;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FeedbackWriter {

    OutputStream outputStream;
    XSSFWorkbook workbook;


    XSSFSheet commentSheet;
    XSSFCellStyle styleCentered;
    XSSFCellStyle styleWordWrap;
    int commentRowCount;
    private static final int    COL_PLANTID = 0,
                                COL_GRDNLOC = 1,
                                COL_CMNNAME = 2,
                                COL_CULTIVR = 3,
                                COL_COMMENT = 4,
                                COL_DATE    = 5;

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

    }

    private void prepareCommentSheet()
    {
        this.commentSheet = workbook.createSheet("Comments");

        Row row1 = commentSheet.createRow(0);
        Row row2 = commentSheet.createRow(1);

        Cell cell;

        cell = row2.createCell(COL_PLANTID);
        cell.setCellValue("#");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_GRDNLOC);
        cell.setCellValue("Garden");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_GRDNLOC);
        cell.setCellValue("Location");
        cell.setCellStyle(styleCentered);

        cell = row1.createCell(COL_CMNNAME);
        cell.setCellValue("Common");
        cell.setCellStyle(styleCentered);
        cell = row2.createCell(COL_CMNNAME);
        cell.setCellValue("Name");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_CULTIVR);
        cell.setCellValue("Cultivar");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_COMMENT);
        cell.setCellValue("Comment");
        cell.setCellStyle(styleCentered);

        cell = row2.createCell(COL_DATE);
        cell.setCellValue("Time of Comment");
        cell.setCellStyle(styleCentered);

        commentSheet.setColumnWidth(COL_PLANTID,1600);
        commentSheet.setColumnWidth(COL_GRDNLOC,1900);
        commentSheet.setColumnWidth(COL_CMNNAME,3200);
        commentSheet.setColumnWidth(COL_CULTIVR,4800);
        commentSheet.setColumnWidth(COL_COMMENT,8400);
        commentSheet.setColumnWidth(COL_DATE,6800);

        commentRowCount = 2;
    }


    /**
     * Adds the given information as a new row to the commentSheet.
     * @param data: an array of strings to write to different cells of a new row in a commentSheet
     * @param timestamp: time the user left the comment
     */
    public void writeComment(String[] data, Date timestamp){
        Row row = commentSheet.createRow(commentRowCount);
        row.setRowStyle(styleWordWrap);
        row.setHeight((short)850);
        int i;
        for(i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data[i]);
        }

        Cell cell = row.createCell(i);
        cell.setCellValue(timestamp.toString());

        commentRowCount++;
    }

    /**
     * Writes the spreadsheet to the outputstream, then closes it.
     * @throws IOException
     */
    public void complete() throws IOException{
        workbook.write(outputStream);
        outputStream.close();
    }
}
