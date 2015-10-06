package com.futoke;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.Math;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Main {

    private static final String DEST = "result/table.pdf";

    private static final Float PAGE_HEIGHT = 595.0f;
    private static final Float TOP_MARGIN = 10.0f;
    private static final Float BOTTOM_MARGIN = 2.0f;
    private static final Float LEFT_MARGIN = 2.0f;
    private static final Float RIGHT_MARGIN = 2.0f;

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Main().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document(
                PageSize.A4.rotate(),
                LEFT_MARGIN,
                RIGHT_MARGIN,
                /*
                Actually, top margin will define in the padding field
                of the title paragraph.
                */
                TOP_MARGIN - TOP_MARGIN,
                BOTTOM_MARGIN
        );
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        ///////////////////////////////////////////////////////////////////

        Paragraph titleParagraph = new Paragraph();
        float spacingAfter = 2.0f;

        titleParagraph.add("Hello World");
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        // Instead of the top page margin.
        titleParagraph.setLeading(TOP_MARGIN - spacingAfter);
        titleParagraph.setSpacingAfter(spacingAfter);

        document.add(titleParagraph);

        /////////////////////////////////////////////////////////////////////

        Paragraph table = new Paragraph();
        PdfPCell cell;

        // The main table fits top and bottom parts.
        PdfPTable mainTable = new PdfPTable(1);
        mainTable.setWidthPercentage(100.0f);
        mainTable.setSpacingBefore(0.0f);
        mainTable.setSpacingAfter(0.0f);

        // Calculate row height.
        float rowHeight;
        rowHeight = (PAGE_HEIGHT - TOP_MARGIN - BOTTOM_MARGIN) / 3;

        // Define the top part.
        PdfPCell topTableCell = new PdfPCell();
        topTableCell.setBorder(PdfPCell.NO_BORDER);
        topTableCell.setPadding(0);

        PdfPTable topTable = new PdfPTable(5);
        topTable.setWidthPercentage(100.0f);
        topTable.setSpacingBefore(0.0f);
        topTable.setSpacingAfter(0.0f);

        // Cell 1 (row 1).
        cell = new PdfPCell(new Phrase("1"));
        cell.setFixedHeight(rowHeight);
        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
        topTable.addCell(cell);

        // Cell 2.
        cell = new PdfPCell(new Phrase("2"));
        topTable.addCell(cell);

        // Cell 3.
        cell = new PdfPCell(new Phrase("3"));
        cell.setRowspan(2);
        topTable.addCell(cell);

        // Cell 4.
        cell = new PdfPCell(new Phrase("1"));
        topTable.addCell(cell);

        // Cell 5.
        cell = new PdfPCell(new Phrase("1"));
        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
        topTable.addCell(cell);

        // Cell 6 (row2).
        cell = new PdfPCell(new Phrase("1"));
        cell.setFixedHeight(rowHeight);
        cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
        topTable.addCell(cell);

        // Cell 7 fits a nested table.
        PdfPTable nestedTable = new PdfPTable(1);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.setSpacingBefore(0.0f);
        nestedTable.setSpacingAfter(0.0f);

        PdfPCell nestedTableCell;

        nestedTableCell = new PdfPCell(new Phrase("Nested 1"));
        nestedTableCell.setBorder(PdfPCell.NO_BORDER);
        nestedTableCell.setFixedHeight(rowHeight / 2.0f);
        nestedTable.addCell(nestedTableCell);

        nestedTableCell = new PdfPCell(new Phrase("Nested 1"));
        nestedTableCell.setBorder(PdfPCell.NO_BORDER);
        nestedTable.addCell(nestedTableCell);

        cell.setPadding(0);
        cell.addElement(nestedTable);
        topTable.addCell(cell);

        // Cell 8.
        cell = new PdfPCell(new Phrase("1"));
        topTable.addCell(cell);

        // Cell 9.
        cell = new PdfPCell(new Phrase("1"));
        cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
        topTable.addCell(cell);

        topTableCell.addElement(topTable);
        mainTable.addCell(topTableCell);

        // Define the bottom part.
        PdfPCell bottomTableCell = new PdfPCell();
        bottomTableCell.setBorder(PdfPCell.NO_BORDER);
        bottomTableCell.setPadding(0);

        PdfPTable bottomTable = new PdfPTable(2);
        bottomTable.setWidthPercentage(100.0f);
        bottomTable.setSpacingBefore(0.0f);
        bottomTable.setSpacingAfter(0.0f);

        cell = new PdfPCell(new Phrase("T2R1C1"));
        cell.setFixedHeight(rowHeight);
        bottomTable.addCell(cell);

        cell = new PdfPCell(new Phrase("T2R1C2"));
        bottomTable.addCell(cell);

        bottomTableCell.addElement(bottomTable);
        mainTable.addCell(bottomTableCell);

        table.add(mainTable);
        document.add(table);


        document.close();
    }
}
