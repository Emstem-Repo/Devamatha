package com.kp.cms.actions.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.actions.exam.ExamValuationStatusDownloadAction;
import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.to.admin.SyllabusTrackerForSupplementaryTo;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;

public class SyllabusDisplayForSupplimentoryDownloadAction extends DownloadAction{

	private static final Log log = LogFactory.getLog(ExamValuationStatusDownloadAction.class);
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered ExamValuationStatusDownloadAction");
		SyllabusTrackerForSupplementaryForm syllabusTrackerForSupplementaryForm = (SyllabusTrackerForSupplementaryForm) form;
		String excelfileName = "SyllabusTrackerForSupplementary";
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
		if(syllabusTrackerForSupplementaryForm.getSyllabusTrackerForSupplementaryTo()!=null){
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("SyllabusTrackerForSupplementary");
			
			
			row = sheet.createRow(count);
			// Creating cells in the row and put some data in it.
			
			row.createCell((short)0).setCellValue("Register No");
			row.createCell((short)1).setCellValue("Joining Year");
			row.createCell((short)2).setCellValue("First Attempted Year");
			row.createCell((short)3).setCellValue("Semester");
			row.createCell((short)4).setCellValue("Paper Code");
			row.createCell((short)5).setCellValue("Paper Name");
			row.createCell((short)6).setCellValue("Chances left");
			row.createCell((short)7).setCellValue("Syllabus Batch Year");
			row.createCell((short)8).setCellValue(" ");
			
			Iterator<SyllabusTrackerForSupplementaryTo> itr=syllabusTrackerForSupplementaryForm.getSyllabusTrackerForSupplementaryTo().iterator();
			while (itr.hasNext()) {
				SyllabusTrackerForSupplementaryTo to = (SyllabusTrackerForSupplementaryTo) itr.next();
				
							count = count +1;
							row = sheet.createRow(count);	
							row.createCell((short)0).setCellValue(to.getRegisterNo());	
							row.createCell((short)1).setCellValue(to.getJoiningYear());
							row.createCell((short)2).setCellValue(to.getExamFirstAttemptedYear());	
							row.createCell((short)3).setCellValue(to.getSemNo());
							row.createCell((short)4).setCellValue(to.getPaperCode());
							row.createCell((short)5).setCellValue(to.getPaperName());
							row.createCell((short)6).setCellValue(to.getNumOfChancesLeft());
							row.createCell((short)7).setCellValue(to.getAdmitedYear());
		}
			csvfos = new FileOutputStream(fCSV,true);
			wb.write(csvfos);
			csvfos.flush();
			csvfos.close();
	}
		catch(Exception e){
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
