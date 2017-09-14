package me.kuykendall.restfest.hackday.dao;

import me.kuykendall.restfest.hackday.OperatingReactorQueryInfo;
import me.kuykendall.restfest.hackday.model.OperatingReactor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XLSOperatingReactorDAO implements OperatingReactorDAO {

    private static Map<String, OperatingReactor> operatingReactors;

    @Override
    public OperatingReactor getOperatingReactorByDocketNumber(String docketNumber){
        OperatingReactor reactor = getOperatingReactors().get(docketNumber);
        return reactor;
    }

    @Override
    public List<OperatingReactor> getOperatingReactors(OperatingReactorQueryInfo queryInfo) {
        List<OperatingReactor> reactors = new ArrayList<>(getOperatingReactors().values());
        return reactors;
    }

    private Map<String, OperatingReactor> getOperatingReactors() {
        if (operatingReactors == null) {
            loadDataFromFile();
        }

        return operatingReactors;
    }

    private void loadDataFromFile() {
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
                operatingReactor.setPlantName(formatter.formatCellValue(row.getCell(0)));
                operatingReactor.setWebPage(formatter.formatCellValue(row.getCell(1)));
                operatingReactor.setDocketNumber(formatter.formatCellValue(row.getCell(2)));

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
