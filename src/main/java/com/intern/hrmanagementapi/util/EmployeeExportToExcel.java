package com.intern.hrmanagementapi.util;

import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.service.DepartmentService;
import com.intern.hrmanagementapi.service.EmployeeService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class EmployeeExportToExcel {

  private final XSSFWorkbook workbook = new XSSFWorkbook();
  private XSSFSheet sheet;
  @Autowired
  private DepartmentService departmentService;
  @Autowired
  private EmployeeService employeeService;

  private void writeHeaderLine() {
    sheet = workbook.createSheet("Employees");

    Row row = sheet.createRow(0);
    int columnCount = 0;
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeight(16);
    style.setFont(font);

    createCell(row, columnCount, "ID", style);
    createCell(row, ++columnCount, "FirstName", style);
    createCell(row, ++columnCount, "LastName", style);
    createCell(row, ++columnCount, "Gender", style);
    createCell(row, ++columnCount, "Address", style);
    createCell(row, ++columnCount, "Email", style);
    createCell(row, ++columnCount, "Date of birth", style);
    createCell(row, ++columnCount, "Department Id", style);
    createCell(row, ++columnCount, "Create Date", style);
    createCell(row, ++columnCount, "Update Date", style);
  }

  private void createCell(Row row, int columnCount, Object value, CellStyle style) {
    sheet.autoSizeColumn(columnCount);
    Cell cell = row.createCell(columnCount);
    if (value instanceof Integer) {
      cell.setCellValue((Integer) value);
    } else if (value instanceof Boolean) {
      cell.setCellValue((Boolean) value);
    } else {
      cell.setCellValue((String) value);
    }
    cell.setCellStyle(style);
  }

  private void writeDataLines() {

    List<EmployeeEntity> list = employeeService.getAllEmployeesByUserId();

    int rowCount = 1;
    String[] gender = {"Female", "Male", "Other"};

    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontHeight(14);
    style.setFont(font);
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    for (EmployeeEntity employee : list) {
      Row row = sheet.createRow(rowCount++);
      int columnCount = 0;
      var department = departmentService.getEntityById(employee.getDepartmentId());

      createCell(row, columnCount++, employee.getId().toString(), style);
      createCell(row, columnCount++, employee.getFirstName(), style);
      createCell(row, columnCount++, employee.getLastName(), style);
      createCell(row, columnCount++, gender[employee.getGender()], style);
      createCell(row, columnCount++, employee.getAddress(), style);
      createCell(row, columnCount++, employee.getEmail(), style);
      createCell(row, columnCount++, formatter.format(employee.getDob()), style);
      createCell(row, columnCount++, department.getName(), style);
      createCell(row, columnCount++, formatter.format(employee.getCreateDate()), style);
      if (employee.getUpdateDate() != null) {
        createCell(row, columnCount++, formatter.format(employee.getUpdateDate()), style);
      }
    }
  }

  public void export(HttpServletResponse response) throws IOException {
    writeHeaderLine();
    writeDataLines();

    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    workbook.close();

    outputStream.close();
  }
}
