package com.kp.cms.actions.reports;

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

	public class DownloadCSVFormatAction extends DownloadAction{
		private static final Log log = LogFactory.getLog(DownloadCSVFormatAction.class);
		
		/**
		 * Method to download the document with document id as the input
		 */
		protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			log.info("Entered DownloadCSVFormatAction");
			
			HttpSession session = request.getSession();		
				byte [] myFileBytes = (byte[])session.getAttribute(CMSConstants.CSV_BYTES);
				int No = 0;
				No=Integer.parseInt(request.getParameter("fileNo"));
				String fileName =null;
				if(No==1)
					fileName = "MobileFormat.csv";
				if(No==2)
					fileName = "SecondLanguage.csv";
				if(No==3)
					fileName = "RegisterNo.csv";
				if(No==4)
					fileName = "OMRUpload.csv";
				if(No==5)
					fileName = "AdmissionDataUpload.csv";
			String contentType = "text/csv";
			response.setHeader("Content-disposition", "attachment; filename ="+fileName);
			
			File file = null;
			try {
				file = new File(request.getRealPath("")+ "//TempFiles//"+"csvFile.txt");
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
				log.info("DownloadCSVFormatAction IOException, " +e);
			} catch (Exception e){
				log.info("DownloadCSVFormatAction Exception, " +e);
			}
			log.info("Exit DownloadCSVFormatAction");
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
