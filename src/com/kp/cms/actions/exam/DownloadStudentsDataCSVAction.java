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

import com.kp.cms.constants.CMSConstants;

	public class DownloadStudentsDataCSVAction extends DownloadAction{
		private static final Log log = LogFactory.getLog(DownloadStudentsDataCSVAction.class);
		
		/**
		 * Method to download the document with document id as the input
		 */
		protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			log.info("Entered DownloadCSVAction");
			
//			ApplnDoc applnDoc = DocumentDownloadHandler.getInstance().getDocument(
//					Integer.valueOf(documentDownloadForm.getDocumentId()));
			HttpSession session = request.getSession();		
				byte [] myFileBytes = (byte[])session.getAttribute(CMSConstants.CSV_BYTES);
				String	fileNames=request.getParameter("fileName");
				String fileName=null;
				if(fileNames.equalsIgnoreCase("AddlBioDataCSV"))
					fileName = "AddlBioDataDetails.csv";
				if(fileNames.equalsIgnoreCase("PreviousClassesCSV"))
					fileName = "PreviousClasses.csv";
				if(fileNames.equalsIgnoreCase("ExamSupplementaryImprovementCSV"))
					fileName = "ExamSupplementaryImprovement.csv";
				
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
