package com.kp.cms.actions.exam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.forms.exam.DownloadUploadedFileForm;

public class PrintDownloadUploadedFilesAction extends DownloadAction{
	private static final Log log = LogFactory.getLog(PrintDownloadUploadedFilesAction.class);
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("Entered PrintDownloadUploadedFilesAction");
		DownloadUploadedFileForm downloadForm = (DownloadUploadedFileForm)form;
		String formatName=null;
		if( downloadForm.getFormatName()!=null &&  !downloadForm.getFormatName().isEmpty()){
			formatName= downloadForm.getFormatName();
		}
		File fCSV= null;
			String fileName = formatName;
			String s1 = request.getRealPath("");
			s1 = s1 +"/UploadedFiles/" +fileName;
			fCSV = new File(s1);
    	    response.setHeader("Content-disposition", "attachment; filename ="+fileName);
			response.setContentType("application/octet-stream");
		return new FileStreamInfo("application/octet-stream", fCSV);
	}
	/**
	 * @author christ
	 *
	 */
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
