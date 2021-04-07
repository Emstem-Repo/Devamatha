package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.bo.admin.GuidelinesDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.handlers.admin.GuidelinesEntryHandler;
/**
 * 
 * @author kshirod.k
 * Action class for GuideLinesDownload
 *
 */

public class GuideLineDownloadAction extends DownloadAction{
	private static final Log log = LogFactory.getLog(GuideLineDownloadAction.class);
	/**
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of getStreamInfo of GuideLineDownloadAction");
		String courseId=request.getParameter("courseId");
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());
		int year =cal.get(cal.YEAR);
		GuidelinesDoc guideDoc = GuidelinesEntryHandler.getInstance().getGuidelinesEntry(Integer.valueOf(courseId),year);
		byte [] myFileBytes = guideDoc.getDoc();
		String fileName = guideDoc.getFileName();
		String contentType = guideDoc.getContentType();
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
		File file = null;
		try {
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
				file = new File(filePath+"outFile.txt");
				InputStream inputStream = new ByteArrayStreamInfo(contentType,myFileBytes).getInputStream();
				OutputStream out = new FileOutputStream(file);
				byte buffer[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) > 0)
					out.write(buffer, 0, len);
				out.close();
				inputStream.close();
		}  catch (Exception e){
			log.error("Error occured at getStreamInfo of guidelinesDownload", e);
			throw new ApplicationException(e);
		}
		log.info("End of getStreamInfo of GuideLineDownloadAction");
		return new FileStreamInfo(contentType, file);
	}

	protected class ByteArrayStreamInfo implements StreamInfo {
		
		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myPdfBytes) {
			this.contentType = contentType;
			this.bytes = myPdfBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
