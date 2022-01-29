package com.kp.cms.actions.studentLogin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
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

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.handlers.studentLogin.SyllabusDisplayForStudentHandler;

public class SyllabusDisplayForStudentDownloadsAction extends DownloadAction{
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(SyllabusDisplayForStudentDownloadsAction.class);
	@Override
	
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception
			 
	{
		File filephoto=null;
	
		SyllabusDisplayForStudentForm syllabusDisplayForStudentForm = (SyllabusDisplayForStudentForm) form;
			log.info("Entered PrintDownloadFormatsAction");
			String file=null;
			String soursePath=null;
			Properties prop=new Properties();
			InputStream stream=SyllabusDisplayForStudentHandler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			soursePath=prop.getProperty("knowledgepro.admin.display.syllabus.location");
			
			
			String papercode=syllabusDisplayForStudentForm.getPaperCode();
			 String replacedPaperCode="";
			
			
			if(papercode.endsWith("-09")){
				 replacedPaperCode=papercode.replace("-09", " ");
			 }else if(papercode.endsWith("-10")){
				 replacedPaperCode=papercode.replace("-10", " ");
			 }else if(papercode.endsWith("-11")){
				 replacedPaperCode=papercode.replace("-11", " ");
			 }else if(papercode.endsWith("-12")){
				 replacedPaperCode=papercode.replace("-12", " ");
			 }else if(papercode.endsWith("-13")){
				 replacedPaperCode=papercode.replace("-13", " ");
			 }else{
				 replacedPaperCode=papercode;
			 }
			
				file=syllabusDisplayForStudentForm.getAdmitedYear()+"/"+replacedPaperCode;
				String fileName = file.trim();
				soursePath = soursePath+"/"+ fileName+".pdf";
				 filephoto = new File(soursePath);
			/* Use less code
			 * boolean exists = filephoto.exists();  
            if (!exists) {  
            	           
            }*/
            // To open with out (save and openwith) dialog box 
			//response.setHeader("Content-disposition", "attachment; filename = Document.pdf");
			response.setContentType("application/pdf");	
			
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
