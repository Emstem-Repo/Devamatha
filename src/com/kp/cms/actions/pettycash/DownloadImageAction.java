package com.kp.cms.actions.pettycash;

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

import com.kp.cms.actions.admission.DownloadExcelAction;
import com.kp.cms.forms.pettycash.PcAccountEntryForm;
import com.kp.cms.handlers.pettycash.PcAccountEntryHandler;
import com.kp.cms.to.pettycash.PcAccountTo;

public class DownloadImageAction extends DownloadAction {
private static final Log log = LogFactory.getLog(DownloadExcelAction.class);
	
	/**
	 * Method to download the document with document id as the input
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DownloadImageAction");
		PcAccountEntryForm accountEntryForm = (PcAccountEntryForm) form;
		String bankAccid=accountEntryForm.getAccountId();
		PcAccountTo accountTo = PcAccountEntryHandler.getInstance().getPcBankAccDetailsWithId(bankAccid);
		byte [] myFileBytes = accountTo.getLogo();
		String fileName = "Logo.jpeg";
		String contentType = "image/jpeg";
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
		File file = null;
		try {
			file = new File(request.getRealPath("")+ "//TempFiles//"+"outFile.txt");
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
			log.info("DownloadImageAction IOException, " +e);
		} catch (Exception e){
			log.info("DownloadImageAction Exception, " +e);
		}
		log.info("Exit DownloadExcelAction");
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
