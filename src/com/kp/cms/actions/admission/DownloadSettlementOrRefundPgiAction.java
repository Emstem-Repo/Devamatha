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
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.forms.admission.DownloadFormatsForm;
import com.kp.cms.forms.admission.GenerateSettlementOrRefundPgiForm;
import com.kp.cms.handlers.admission.GenerateSettlementOrRefundPgiHandler;

public class DownloadSettlementOrRefundPgiAction extends DownloadAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(DownloadSettlementOrRefundPgiAction.class);

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		log.info("Entered PrintDownloadFormatsAction");
		GenerateSettlementOrRefundPgiForm objForm = (GenerateSettlementOrRefundPgiForm) form;
		File ftxt= null;
		String fileName="";
		String s1 = request.getRealPath("");
		if(request.getParameter("fileType").equalsIgnoreCase("refund")){
			fileName = objForm.getRefundFileName();
			s1 = s1 +"/TempFiles/"+objForm.getRefundFileName();
			//GenerateSettlementOrRefundPgiHandler.getInstance().updateRefundFlag(objForm);
		}else if(request.getParameter("fileType").equalsIgnoreCase("settlement")){
			fileName = objForm.getSettlementFileName();
			s1 = s1 +"/TempFiles/"+objForm.getSettlementFileName();
		}
			ftxt = new File(s1);
    	    response.setHeader("Content-disposition", "attachment; filename = "+fileName);
			response.setContentType("application/txt");
		
		return new FileStreamInfo("write.txt", ftxt);
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
