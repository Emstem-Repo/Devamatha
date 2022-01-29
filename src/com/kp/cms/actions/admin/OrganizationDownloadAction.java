package com.kp.cms.actions.admin;

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

import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.forms.admin.OrganizationForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
/**
 * 
 * @author kshirod.k
 * An action class for OrganizationDownload
 *
 */
public class OrganizationDownloadAction extends DownloadAction{
	private static final Log log = LogFactory.getLog(OrganizationDownloadAction.class);
	
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
			log.info("Start of getStreamInfo of OrganizationDownloadAction");
			OrganizationForm organizationForm=(OrganizationForm)form;
			final int orgId=organizationForm.getId();
			final int value=organizationForm.getValue();
			
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile(orgId);
			String fileName = "";
			String contentType = "";
			byte [] myFileBytes = null;
			
			if(value==1){
					myFileBytes = organisation.getLogo();
					fileName = organisation.getLogoName();
					contentType = organisation.getLogoContentType();
			}else if(value==2) {
					myFileBytes = organisation.getTopbar();
					fileName = organisation.getTopbarName();
					contentType = organisation.getTopbarContentType();
			}else if(value==3) {
				myFileBytes = organisation.getLogo1();
				fileName = organisation.getLogoName1();
				contentType = organisation.getLogoContentType1();
			}else if(value==4) {
				myFileBytes = organisation.getTopbar1();
				fileName = organisation.getTopbarName1();
				contentType = organisation.getTopbarContentType1();
			}
			
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
					while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
			} catch (Exception e){
				log.error("Error occured while downloading" + e);
			}
			log.info("End of getStreamInfo of OrganizationDownloadAction");
			return new FileStreamInfo(contentType, file);
		}

		protected class ByteArrayStreamInfo implements StreamInfo {

			transient protected String contentType;
			transient protected byte[] bytes;

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
