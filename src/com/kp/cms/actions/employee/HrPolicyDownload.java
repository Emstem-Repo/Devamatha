package com.kp.cms.actions.employee;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import com.kp.cms.bo.admin.EmpHrPolicy;
import com.kp.cms.forms.employee.HrPolicyForm;
import com.kp.cms.handlers.employee.HrPolicyHandler;

public class HrPolicyDownload extends DownloadAction{
	
	private static final Logger log = Logger.getLogger(HrPolicyDownload.class);

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("entering of getStreamInfo in HrPolicyDownloadAction class...");
		HrPolicyForm hrPolicyForm = (HrPolicyForm) form;
		
		EmpHrPolicy empHrPolicy= HrPolicyHandler.getInstance().getHrPolicy(Integer.parseInt(hrPolicyForm.getPolicyId()));

		byte [] myFileBytes = empHrPolicy.getDocument();
		String fileName = empHrPolicy.getFileName();
		String contentType = empHrPolicy.getContentType();
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		
		File file = null;
		try {
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			file = new File(filePath+"outFile.txt");
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
			log.info("HrPolicyDownload IOException, " ,e);
		} catch (Exception e){
			log.info("HrPolicyDownload Exception, " ,e);
		}
		log.info("Exit DocumentDownloadAction");
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