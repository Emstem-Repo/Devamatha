package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.forms.admission.DownloadFormatsForm;

public class PrintDownloadFilesAction extends DownloadAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(PrintDownloadFilesAction.class);

	/* (non-Javadoc)
	 * Method to download the formats
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("deprecation")
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered PrintDownloadFormatsAction");
		DownloadFormatsForm objForm = (DownloadFormatsForm) form;
		String formatName=null;
		if( objForm.getFormatName()!=null &&  !objForm.getFormatName().isEmpty()){
			formatName= objForm.getFormatName();
		}
		File fCSV= null;
		// To download CSV formats
		if(formatName.trim().equalsIgnoreCase("CameraFilesDownload")){
			//String fileName = formatName;
			String s1 = request.getRealPath("");
			s1 = s1 +"/CamFiles/Camera Files.zip";
			fCSV = new File(s1);
    	    response.setHeader("Content-disposition", "attachment; filename = CameraFiles.zip");
			response.setContentType("application/zip");
			
			  
		}
		
		if(formatName.trim().equalsIgnoreCase("PurchaseRequestFormDownload")){
			String s1 = request.getRealPath("");
			s1 = s1 +"/downloadForms/Purchase Request Form.pdf";
			fCSV = new File(s1);
    	    response.setHeader("Content-disposition", "attachment; filename = PurchaseRequestForm.pdf");
			response.setContentType("application/pdf");
			return new FileStreamInfo("write.pdf", fCSV);
			  
		}
		
		return new FileStreamInfo("write.zip", fCSV);
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
