package me.kuykendall.restfest.hackday.dao;

import me.kuykendall.restfest.hackday.model.OperatingReactor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

public class XLSOperatingReactorDAO implements OperatingReactorDAO {
    @Override
    public OperatingReactor getOperatingReactorByDocketNumber(String docketNumber) {
        OperatingReactor operatingReactor = null;
        try {
            InputStream myFile = getClass().getClassLoader().getResourceAsStream("../appa.xls");

            Workbook wb = WorkbookFactory.create(myFile);

            operatingReactor = new OperatingReactor();
            Sheet sheet1 = wb.getSheetAt(0);
            for (Row row : sheet1) {
                if (row.getRowNum() < 2) {
                    continue;
                }

                // Process row of sheet

                operatingReactor.setPlantName(row.getCell(0).getRichStringCellValue().getString());
                operatingReactor.setWebPage(String.valueOf(row.getCell(1).getRichStringCellValue().getString()));
                operatingReactor.setDocketNumber(row.getCell(2).getRichStringCellValue().getString());
                if (operatingReactor.getDocketNumber().equals(docketNumber)) {
                    break;
                }
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return operatingReactor;
    }
}
