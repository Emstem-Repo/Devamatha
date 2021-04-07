package com.kp.cms.handlers.admission;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kp.cms.bo.admin.GenerateProcess;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DownloadStudentInterviewStatusForm;
import com.kp.cms.helpers.admission.DownloadStudentInterviewStatusHelper;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IDownloadStudentInterviewStatusTransaction;
import com.kp.cms.transactionsimpl.admission.DownloadStudentInterviewStatusTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class DownloadStudentInterviewStatusHandler {
	/**
	 * Singleton object of DownloadStudentInterviewStatusHandler
	 */
	private static volatile DownloadStudentInterviewStatusHandler handler = null;
	private static final Log log = LogFactory.getLog(DownloadStudentInterviewStatusHandler.class);
	private DownloadStudentInterviewStatusHandler() {
		
	}
	/**
	 * return singleton object of DownloadStudentInterviewStatusHandler.
	 * @return
	 */
	public static DownloadStudentInterviewStatusHandler getInstance() {
		if (handler == null) {
			handler = new DownloadStudentInterviewStatusHandler();
		}
		return handler;
	}
	
	/**
	 * @param interviewersPerPanel
	 * @param selectedCandidates
	 * @param request
	 * @throws Exception
	 */
	public void exportToCSV(List<AdmApplnTO> selectedCandidates,HttpServletRequest request) throws Exception {
		

		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of CandidateHelper ",e);
			throw new IOException(e);
		}
		String destination=prop.getProperty(CMSConstants.EXCEL_DESTINATION);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+destination);	
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		if(selectedCandidates!=null){
			int count = 1;
			Iterator<AdmApplnTO> iterator = selectedCandidates.iterator();
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Status Report");
			row = sheet.createRow(0);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			row.createCell((short)0).setCellValue("ApplicationNumber");
			row.createCell((short)1).setCellValue("Student Name");
			row.createCell((short)2).setCellValue("Status");
			row.createCell((short)3).setCellValue("Mobile Number");
			row.createCell((short)4).setCellValue(" ");
			
			count=0;
			while (iterator.hasNext()) {
				AdmApplnTO to = (AdmApplnTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);				
				row.createCell((short)0).setCellValue(to.getApplnNo());
				row.createCell((short)1).setCellValue(to.getPersonalData().getFirstName());
				row.createCell((short)2).setCellValue(to.getStatus());
				row.createCell((short)3).setCellValue(to.getPersonalData().getMobileNo());
			}
			fos = new FileOutputStream(excelFile,true);
			wb.write(fos);
			fos.flush();
			fos.close();
			
			String csvdestination = prop.getProperty(CMSConstants.CSV_DESTINATION);
			//File to store data in form of CSV
			File fCSV = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);

			OutputStream os = (OutputStream)new FileOutputStream(fCSV);
			
			String encoding = "UTF8";
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			BufferedWriter bWriter = new BufferedWriter(osw);

			//Excel document to be imported
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(new File(request.getRealPath("")+ "//TempFiles//"+destination),ws);

			// Gets the sheets from workbook
			for (int scount = 0; scount < w.getNumberOfSheets(); scount++)
			{
			Sheet s = w.getSheet(scount);


			Cell[] rowcsv = null;

			// Gets the cells from sheet
			for (int i = 0 ; i < s.getRows() ; i++)
			{
				rowcsv = s.getRow(i);

			if (rowcsv.length >= 0)
			{
			for (int j = 0; j < rowcsv.length; j++)
			{
			
				bWriter.write(rowcsv[j].getContents());
				bWriter.write(',');
			}
			}
			bWriter.newLine();
			}
			}
			
			bWriter.flush();
			bWriter.close();	
			
		      File file1 = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);
		      FileInputStream fileIn = new FileInputStream(file1);		
		      
		       
		      byte[] outputByte = new byte[fileIn.available()];
		      fileIn.read(outputByte);
		      HttpSession session = request.getSession();
		      session.setAttribute(CMSConstants.CSV_BYTES, outputByte);
	
		}catch(Exception e){
			
		}
		}
	}
	/**
	 * @param downloadStudentInterviewStatusForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<AdmApplnTO> getCandidates(DownloadStudentInterviewStatusForm downloadStudentInterviewStatusForm,HttpServletRequest request) throws Exception {
		String query=DownloadStudentInterviewStatusHelper.getInstance().getsearchQuery(downloadStudentInterviewStatusForm);
		IDownloadStudentInterviewStatusTransaction transaction=DownloadStudentInterviewStatusTransactionImpl.getInstance();
		List<GenerateProcess> list=transaction.getStudentList(query);
		return DownloadStudentInterviewStatusHelper.getInstance().convertBotoTo(list);
	}
}
