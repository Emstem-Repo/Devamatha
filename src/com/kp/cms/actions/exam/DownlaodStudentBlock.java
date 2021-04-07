package com.kp.cms.actions.exam;

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

public class DownlaodStudentBlock extends DownloadAction{
	private static final Log log = LogFactory.getLog(DownlaodStudentBlock.class);
	
	/**
	 * Method to download the document with document id as the input
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DownloadCSVAction");
		
		HttpSession session = request.getSession();		
		byte [] myFileBytes = (byte[])session.getAttribute("NotUpload");
		
		String fileName = "NotUploadStudentBlock.csv";
		String contentType = "text/csv";
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
		File file = null;
		try {
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			file = new File(filePath+"csvFile.txt");
			if(file.exists()){
				file.delete();
			}
			InputStream inputStream = new ByteArrayStreamInfo(contentType,myFileBytes).getInputStream();
			OutputStream out = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}	
				out.close();
				inputStream.close();
				session.removeAttribute("NotUpload");
		} catch (IOException e) {
			log.info("DownloadCSVAction IOException, " +e);
		} catch (Exception e){
			log.info("DownloadCSVAction Exception, " +e);
		}
		log.info("Exit DownloadCSVAction");
		return new FileStreamInfo(contentType, file);
	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myCSVBytes) {
			this.contentType = contentType;
			this.bytes = myCSVBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}

}
