package me.kuykendall.restfest.hackday.dao;

import me.kuykendall.restfest.hackday.model.OperatingReactor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XLSOperatingReactorDAO implements OperatingReactorDAO {

    private static Map<String, OperatingReactor> operatingReactors;

    @Override
    public OperatingReactor getOperatingReactorByDocketNumber(String docketNumber) throws Exception {
        OperatingReactor reactor = getOperatingReactors().get(docketNumber);
        return reactor;
    }

    private Map<String, OperatingReactor> getOperatingReactors() throws Exception {
        if (operatingReactors == null) {
            loadDataFromFile();
        }

        return operatingReactors;
    }

    private void loadDataFromFile() throws Exception {
        operatingReactors = new HashMap<>();
        try {
            InputStream myFile = getClass().getClassLoader().getResourceAsStream("../appa.xls");

            Workbook wb = WorkbookFactory.create(myFile);

            Sheet sheet1 = wb.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet1) {
                if (row.getRowNum() < 2) {
                    continue;
                }

                // Process row of sheet

                OperatingReactor operatingReactor = new OperatingReactor();

//                operatingReactor.setPlantName(row.getCell(0).getRichStringCellValue().getString());
                operatingReactor.setPlantName(formatter.formatCellValue(row.getCell(0)));
//                operatingReactor.setWebPage(String.valueOf(row.getCell(1).getRichStringCellValue().getString()));
                operatingReactor.setWebPage(formatter.formatCellValue(row.getCell(1)));
                operatingReactor.setDocketNumber(formatter.formatCellValue(row.getCell(2)));

//                switch (row.getCell(2).getCellTypeEnum()) {
//                    case STRING:
//                    case BLANK:
//                        operatingReactor.setDocketNumber(row.getCell(2).getRichStringCellValue().getString());
//                        break;
//                    case NUMERIC:
//                        operatingReactor.setDocketNumber(String.valueOf(row.getCell(2).getNumericCellValue()));
//                        break;
//                    default:
//                        throw new Exception("Unsupported cell type for docket number: " + operatingReactor.getDocketNumber() + row.getCell(2).getCellTypeEnum());
//
//                }

                if (operatingReactor.getDocketNumber().equals("")) {
                    continue;
                }

                operatingReactors.put(operatingReactor.getDocketNumber(), operatingReactor);
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
