package com.kp.cms.actions.fee;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.forms.fee.FeeAccountForm;
import com.kp.cms.handlers.fee.FeeAccountHandler;

public class FeeAccountDownloadAction extends DownloadAction{

	private static final Log log = LogFactory.getLog(FeeAccountDownloadAction.class);
	
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getStreamInfo of FeeAccountDownloadAction");
		FeeAccountForm feeAccountForm = (FeeAccountForm)form;
		
		FeeAccount feeAccount = FeeAccountHandler.getInstance().getFeeAccountData(feeAccountForm.getId());
		
		byte [] myFileBytes = feeAccount.getLogo();
		String fileName = feeAccount.getFileName();
		String contentType = feeAccount.getContentType();
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
		File file = null;
		try {
			file = new File("outFile.txt");
			InputStream inputStream = new ByteArrayStreamInfo(contentType,
					myFileBytes).getInputStream();
			OutputStream out = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
		} 
		catch (Exception e){
			log.error("Error occured at getStreamInfo of FeeAccountDownloadAction" , e);
		}
		log.info("Leaving into getStreamInfo of FeeAccountDownloadAction");
		return new FileStreamInfo(contentType, file);
	}
	
	
	protected class ByteArrayStreamInfo implements StreamInfo {
		transient protected String contentType;
		transient protected byte[] bytes;

		public ByteArrayStreamInfo(final String contentType, byte[] myPdfBytes) {
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