package com.kp.cms.actions.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

import com.kp.cms.actions.admission.PrintDownloadFormatsAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.forms.admission.DownloadFormatsForm;
import com.kp.cms.helpers.admin.NewsEventsDetailsHelper;

public class NewsEventsDownloadsAction extends DownloadAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(NewsEventsDownloadsAction.class);
	@Override
	
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
			log.info("Entered PrintDownloadFormatsAction");
			NewsEventsEntryForm newsEventsForm = (NewsEventsEntryForm) form;
			String file=null;
			String soursePath=null;
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			
			if( newsEventsForm.getDownloadFileName()!=null &&  !newsEventsForm.getDownloadFileName().isEmpty()){
				file= newsEventsForm.getDownloadFileName();
				
			}
			if(newsEventsForm.getFileType()!=null && !newsEventsForm.getFileType().isEmpty()){
				if(newsEventsForm.getFileType().equalsIgnoreCase("Registration")){
					soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadRegForm.path");
				}else if(newsEventsForm.getFileType().equalsIgnoreCase("Invitation")){
					 soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadInvitation.path");
				}else if(newsEventsForm.getFileType().equalsIgnoreCase("Report")){
					 soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadReport.path");
				}else if(newsEventsForm.getFileType().equalsIgnoreCase("Material")){
					 soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadMaterialPublished.path");
				}
			}
			String fileName = file.trim();
			soursePath = soursePath+"/"+ fileName;
			File filephoto = new File(soursePath);
			if((fileName.endsWith(".txt")) ||(fileName.endsWith(".TXT")))
					{
					response.setHeader("Content-disposition", "attachment; filename = Document.txt");
					response.setContentType("application/txt");
			}
			else if((fileName.endsWith(".doc"))|| (fileName.endsWith(".DOC"))
					|| (fileName.endsWith(".docx"))||(fileName.endsWith(".DOCX")))
			{
					response.setHeader("Content-disposition", "attachment; filename = Document.doc");
					response.setContentType("application/doc");
			}else if((fileName.endsWith(".xls"))||(fileName.endsWith(".XLS"))
					||(fileName.endsWith(".xlsx"))||(fileName.endsWith(".XLSX")))
			{
					response.setHeader("Content-disposition", "attachment; filename = Document.xls");
					response.setContentType("application/xls");
			}
			else if((fileName.endsWith(".pdf")) ||(fileName.endsWith(".PDF"))){
						response.setHeader("Content-disposition", "attachment; filename = Document.pdf");
						response.setContentType("application/pdf");
			}
			else if((fileName.endsWith(".ppt"))||(fileName.endsWith(".PPT"))){
						response.setHeader("Content-disposition", "attachment; filename = Document.ppt");
						response.setContentType("application/ppt");
			}
		return new FileStreamInfo("write.pdf", filephoto);
		
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
