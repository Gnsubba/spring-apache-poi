package com.example.springexcel.helper;

import com.example.springexcel.entity.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = { "Id", "FirstName", "LastName", "Email", "Department" };
    static String SHEET = "Employees";

    public boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public  List<Employee> excelToEmployee(InputStream is) {
        System.out.println("Begin excelToEmployee method................");
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Employee> employees = new ArrayList<Employee>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Employee employee = new Employee();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            employee.setEmployeeId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            employee.setFirstName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            employee.setLastName(currentCell.getStringCellValue());
                            break;

                        case 3:
                            employee.setEmail(currentCell.getStringCellValue());
                            break;

                        case 4:
                            employee.setDepartment(currentCell.getStringCellValue());
                        default:
                            break;
                    }

                    cellIdx++;
                }

                employees.add(employee);
            }

            workbook.close();

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
