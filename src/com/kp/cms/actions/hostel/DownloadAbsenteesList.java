package com.kp.cms.actions.hostel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.forms.hostel.AbsentiesListForm;
import com.kp.cms.to.hostel.AbsentiesListTo;

public class DownloadAbsenteesList extends DownloadAction{

	protected StreamInfo getStreamInfo(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AbsentiesListForm absentiesListForm=(AbsentiesListForm)form;
		String excelfileName = "AbsenteesDetails";
		String filePath=request.getRealPath("")+ "//TempFiles//";
		File fCSV = new File(filePath+excelfileName+".xls");
		if(fCSV.exists()){
			fCSV.delete();
		}
		 response.setHeader("Content-disposition", "attachment; filename =" + excelfileName + ".xls");
		  response.setContentType("application/xls");
		  
		FileOutputStream csvfos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		int count = 0;
		if(absentiesListForm.getAbsentiesListTosList()!=null && !absentiesListForm.getAbsentiesListTosList().isEmpty()){
			try{
				wb = new HSSFWorkbook();
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("AbsenteesDetails");
				row = sheet.createRow(count);
				// Creating cells in the row and put some data in it.
				row.createCell((short)0).setCellValue("Hostel");// added by sudhir
				row.createCell((short)1).setCellValue("Register No");
				row.createCell((short)2).setCellValue("Student Name");
				row.createCell((short)3).setCellValue("Block");
				row.createCell((short)4).setCellValue("Unit");
				row.createCell((short)5).setCellValue("Room");
				row.createCell((short)6).setCellValue("Bed");
				row.createCell((short)7).setCellValue("Mobile No");
				row.createCell((short)8).setCellValue("Parent Mobile No");
				row.createCell((short)9).setCellValue("Date");
				row.createCell((short)10).setCellValue("Session");
				row.createCell((short)11).setCellValue(" ");
			
				Iterator<AbsentiesListTo> itr=absentiesListForm.getAbsentiesListTosList().iterator();
				while (itr.hasNext()) {
					AbsentiesListTo absentiesListTo = (AbsentiesListTo) itr.next();
					count = count +1;
					row = sheet.createRow(count);	
					row.createCell((short)0).setCellValue(absentiesListTo.getHostelName());	
					row.createCell((short)1).setCellValue(absentiesListTo.getRegNo());	
					row.createCell((short)2).setCellValue(absentiesListTo.getStudentName());	
					row.createCell((short)3).setCellValue(absentiesListTo.getBlock());	
					row.createCell((short)4).setCellValue(absentiesListTo.getUnit());	
					row.createCell((short)5).setCellValue(absentiesListTo.getRoom());	
					row.createCell((short)6).setCellValue(absentiesListTo.getBed());	
					row.createCell((short)7).setCellValue(absentiesListTo.getContactNo());	
					row.createCell((short)8).setCellValue(absentiesListTo.getParentContactNo());	
					row.createCell((short)9).setCellValue(absentiesListTo.getDate());	
					row.createCell((short)10).setCellValue(absentiesListTo.getSession());	
				}
				csvfos = new FileOutputStream(fCSV,true);
				wb.write(csvfos);
				csvfos.flush();
				csvfos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return new FileStreamInfo("write.csv", fCSV);
	}
	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
			this.contentType = contentType;
			this.bytes = myXLSBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
