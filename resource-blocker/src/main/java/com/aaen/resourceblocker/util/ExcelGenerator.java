package com.aaen.resourceblocker.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aaen.resourceblocker.model.Reservation;

public class ExcelGenerator {

	public static ByteArrayInputStream customersToExcel(List<Reservation> reservations) throws IOException {

		String[] COLUMNs = { "Id", "Resource id", "Resource quantiny", "Request by", "Approved by", "Denied by",
				"Return approved by", "State", "Aproved date time", "Denied date time", "Cancel date time",
				"Return request date time", "Return request approved date time", "Is return", "Is cancelled" };

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Reservation");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {

				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-m-dTHH:mm:ss.s"));

			int rowIdx = 1;

			for (Reservation reservation : reservations) {

				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(reservation.getId());
				row.createCell(1).setCellValue(reservation.getResourceId());
				row.createCell(2).setCellValue(reservation.getResourceQuantiny());
				row.createCell(3).setCellValue(reservation.getRequestBy());
				row.createCell(4).setCellValue(reservation.getApprovedBy());
				row.createCell(5).setCellValue(reservation.getDeniedBy());
				row.createCell(6).setCellValue(reservation.getReturnApprovedBy());
				row.createCell(7).setCellValue(reservation.getState());

				Cell cell = row.createCell(8);
				cell.setCellValue(DateTimeUtil.convertToDate(reservation.getAprovedDateTime()));
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(9);
				cell.setCellValue(DateTimeUtil.convertToDate(reservation.getDeniedDateTime()));
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(10);
				cell.setCellValue(DateTimeUtil.convertToDate(reservation.getCancelDateTime()));
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(11);
				cell.setCellValue(DateTimeUtil.convertToDate(reservation.getReturnRequestDateTime()));
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(12);
				cell.setCellValue(DateTimeUtil.convertToDate(reservation.getReturnRequestApprovedDateTime()));
				cell.setCellStyle(dateCellStyle);

				if (Objects.nonNull(reservation.getIsReturn()))
					row.createCell(13).setCellValue(reservation.getIsReturn());

				if (Objects.nonNull(reservation.getIsCancelled()))
					row.createCell(14).setCellValue(reservation.getIsCancelled());

			}

			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}