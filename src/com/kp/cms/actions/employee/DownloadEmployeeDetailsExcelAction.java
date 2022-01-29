package com.kp.cms.actions.employee;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.constants.CMSConstants;

public class DownloadEmployeeDetailsExcelAction  extends DownloadAction{
	private static final Log log = LogFactory.getLog(DownloadEmployeeDetailsExcelAction.class);
	
	/**
	 * Method to download the document with document id as the input
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DownloadEmployeeDetailsExcelAction");
		
		HttpSession session = request.getSession();		
			byte [] myFileBytes = (byte[])session.getAttribute(CMSConstants.EXCEL_BYTES);
			String contentType = "application/octet-stream";
			
			//Start added by mehaboob student attendance report
			if(request.getParameter("downloadExcel")!=null){
			if(request.getParameter("downloadExcel").equalsIgnoreCase("Excel")){
				String fileName = "StudentsAttendance.xls";
				response.setHeader("Content-disposition", "attachment; filename ="+fileName);
			}else if(request.getParameter("downloadExcel").equalsIgnoreCase("CSV")){
				String fileName = "StudentsAttendance.csv";
				response.setHeader("Content-disposition", "attachment; filename ="+fileName);
			}
			}else{
				String fileName = "employeeDetailsReport.xls";
				response.setHeader("Content-disposition", "attachment; filename ="+fileName);
			}
			//end
		
		File file = null;
		try {
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			file = new File(filePath+"outFile.txt");
			if(file.exists()){
				file.delete();
			}
			InputStream inputStream = new ByteArrayStreamInfo(contentType,
					myFileBytes).getInputStream();
			OutputStream out = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}	
				out.close();
				inputStream.close();
		} catch (IOException e) {
			log.info("DownloadEmployeeDetailsExcelAction IOException, " +e);
		} catch (Exception e){
			log.info("DownloadEmployeeDetailsExcelAction Exception, " +e);
		}
		log.info("Exit DownloadEmployeeDetailsExcelAction");
		return new FileStreamInfo(contentType, file);
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
