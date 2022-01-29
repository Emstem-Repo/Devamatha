package com.kp.cms.handlers.exam;

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
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.UploadStudentBlockForm;
import com.kp.cms.helpers.exam.UploadStudentBlockHelper;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.transactions.exam.IUploadStudentBlockTransaction;
import com.kp.cms.transactionsimpl.exam.UploadStudentBlockTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class UploadStudentBlockHandler {
	
	IUploadStudentBlockTransaction transaction=UploadStudentBlockTransactionImpl.getInstance();
	
	/**
	 * Singleton object of UploadStudentBlockHandler
	 */
	private static volatile UploadStudentBlockHandler uploadStudentBlockHandler = null;
	private static final Log log = LogFactory.getLog(UploadStudentBlockHandler.class);
	private UploadStudentBlockHandler() {
		
	}
	/**
	 * return singleton object of UploadStudentBlockHandler.
	 * @return
	 */
	public static UploadStudentBlockHandler getInstance() {
		if (uploadStudentBlockHandler == null) {
			uploadStudentBlockHandler = new UploadStudentBlockHandler();
		}
		return uploadStudentBlockHandler;
	}
	/**
	 * @param uploadStudentBlockForm
	 * @return
	 */
	public Map<String, String> getAdmAppDetails(UploadStudentBlockForm uploadStudentBlockForm) throws Exception {
		log.info("entered into getAdmAppDetails in UploadStudentBlockHandler");
		String query =UploadStudentBlockHelper.getInstance().getStudentDetailQuery(uploadStudentBlockForm);
		log.info("exit from getAdmAppDetails in UploadStudentBlockHandler");
		return transaction.getStudentDetails(query);
	}
	/**
	 * @param results
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<ExamBlockUnBlockCandidatesTO> results,
			String user) throws Exception {
		return transaction.uploadData(results,user);
	}
	/**
	 * @param notUploaded
	 * @throws Exception
	 */
	public void exportTOCSV(List<String> notUploaded,HttpServletRequest request) throws Exception {
		// File test = null;
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
//		String contentType = response.getContentType();
//		String fileName = "AdmissionReport.xls";
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		HSSFCell cell = null;
		if(notUploaded!=null){
		Iterator<String> iterator = notUploaded.iterator();
		int count=0;
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Admission Report");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			row.createCell((short)0).setCellValue(CMSConstants.REGNO);
			
			
			while (iterator.hasNext()) {
				String s = (String) iterator.next();
				count = count +1;
				row = sheet.createRow(count);				
				if(s!= null && !s.isEmpty()){
					row.createCell((short)0).setCellValue(s);
				}	
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
//			String excelfilename = "c:/admissionReport.xls";
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(new File(request.getRealPath("")+ "//TempFiles//"+destination),ws);

			// Gets the sheets from workbook
			for (int scount = 0; scount < w.getNumberOfSheets(); scount++)
			{
			Sheet s = w.getSheet(scount);

//			bw.write(s.getName());
//			bw.newLine();

			Cell[] rowcsv = null;

			// Gets the cells from sheet
			for (int i = 0 ; i < s.getRows() ; i++)
			{
				rowcsv = s.getRow(i);

			if (rowcsv.length >= 0)
			{
//				bWriter.write(rowcsv[0].getContents());
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
		      session.setAttribute("NotUpload", outputByte);
	
		}catch(Exception e){
			
			//log

		}
		}
		
	}
}
