package com.kp.cms.actions.admission;

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

public class DownloadExportDataAction extends DownloadAction{
	private static final Log log = LogFactory.getLog(DownloadExportDataAction.class);
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			HttpSession session = request.getSession(false);
			byte [] myFileBytes = null;
			if(session!= null){
				myFileBytes = (byte[])session.getAttribute(CMSConstants.ID_DATA_ATTR);
			}
		
			String fileName = "SendDataForIdCard.xls";
			String contentType = "application/octet-stream";
			response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
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
				log.info("DownloadExportDataAction IOException, " ,e);
			} catch (Exception e){
				log.info("DownloadExportDataAction Exception, " ,e);
			}
		log.info("Exit DownloadExportDataAction");
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