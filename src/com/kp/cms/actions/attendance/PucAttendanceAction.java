package com.kp.cms.actions.attendance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admin.AdmMeritListAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.PucAttendanceForm;
import com.kp.cms.handlers.attendance.PucAttendanceHandler;

public class PucAttendanceAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PucAttendanceAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPucClassHeld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPucClassHeld input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		pucAttendanceForm.reset();
		log.info("Leaving initPucClassHeld input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_CLASSHELD);
	} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadClassHeld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadClassHeld input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, pucAttendanceForm);
		ActionErrors errors = pucAttendanceForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = pucAttendanceForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_CLASS_HELD);
		    	String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+source1);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			String source=prop.getProperty(CMSConstants.UPLOAD_CLASS_HELD);
			file = new File(filePath+source);
			boolean isAdded=PucAttendanceHandler.getInstance().uploadClassHeld(file, pucAttendanceForm);
			
		    if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		
	    	}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
		    }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPLOAD_PUC_CLASSHELD);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadSupliMarks");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			pucAttendanceForm.setErrorMessage(msg);
			pucAttendanceForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		/*List<String> duplicate=pucAttendanceForm.getRegNos();
		if(duplicate!=null && !duplicate.isEmpty()){
			String ids="";
			Iterator<String> it=duplicate.iterator();
			while(it.hasNext()){
				ids= ids+it.next().toString()+", ";
			}
			int len=ids.length()-2;
	        if(ids.endsWith(", ")){
	            StringBuffer buff=new StringBuffer(ids);
	            buff.setCharAt(len, ' ');
	            ids=buff.toString();
	        }
			errors.add("error",new ActionError( "knowledgepro.admission.uploadSuplimarks.duplicate" ,ids));
			saveErrors(request, errors);
		}*/
		log.info("Leaving uploadSupliMarks input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_CLASSHELD);
	}
	
	/**
	 * @author balaji
	 *
	 */
	protected class ByteArrayStreamInfo implements StreamInfo {
	
	protected String contentType;
	protected byte[] bytes;
	
	public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
		this.contentType = contentType;
		this.bytes = myDfBytes;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPucDefineClassSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPucClassHeld input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		pucAttendanceForm.reset();
		log.info("Leaving initPucClassHeld input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_DEFINE_SUBJECTS);
	} 
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadDefineClassSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadDefineClassSubjects input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, pucAttendanceForm);
		ActionErrors errors = pucAttendanceForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = pucAttendanceForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_CLASS_SUBJECT_DEFINE);
		    	String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+source1);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			String source=prop.getProperty(CMSConstants.UPLOAD_CLASS_SUBJECT_DEFINE);
			file = new File(filePath+source);
			boolean isAdded=PucAttendanceHandler.getInstance().uploadDefineClassSubjects(file, pucAttendanceForm);
			
		    if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		
	    	}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
		    }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPLOAD_PUC_DEFINE_SUBJECTS);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadDefineClassSubjects");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			pucAttendanceForm.setErrorMessage(msg);
			pucAttendanceForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		/*List<String> duplicate=pucAttendanceForm.getRegNos();
		if(duplicate!=null && !duplicate.isEmpty()){
			String ids="";
			Iterator<String> it=duplicate.iterator();
			while(it.hasNext()){
				ids= ids+it.next().toString()+", ";
			}
			int len=ids.length()-2;
	        if(ids.endsWith(", ")){
	            StringBuffer buff=new StringBuffer(ids);
	            buff.setCharAt(len, ' ');
	            ids=buff.toString();
	        }
			errors.add("error",new ActionError( "knowledgepro.admission.uploadSuplimarks.duplicate" ,ids));
			saveErrors(request, errors);
		}*/
		log.info("Leaving uploadDefineClassSubjects input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_DEFINE_SUBJECTS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPucattnApprovedLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPucattnApprovedLeaves input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		pucAttendanceForm.reset();
		log.info("Leaving initPucattnApprovedLeaves input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_APPROVED_LEAVES);
	} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadApprovedLeaves(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadDefineClassSubjects input");
		PucAttendanceForm pucAttendanceForm=(PucAttendanceForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, pucAttendanceForm);
		ActionErrors errors = pucAttendanceForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = pucAttendanceForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_PUCATTN_APPROVED_LEAVES);
		    	String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+source1);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			String source=prop.getProperty(CMSConstants.UPLOAD_PUCATTN_APPROVED_LEAVES);
			file = new File(filePath+source);
			boolean isAdded=PucAttendanceHandler.getInstance().uploadApprovedLeaves(file, pucAttendanceForm);
			
		    if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		
	    	}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
		    }else{
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		pucAttendanceForm.reset();
	    		addErrors(request, errors);
		    }
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPLOAD_PUC_APPROVED_LEAVES);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadDefineClassSubjects");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			pucAttendanceForm.setErrorMessage(msg);
			pucAttendanceForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=pucAttendanceForm.getRegNosList();
		if(duplicate!=null && !duplicate.isEmpty()){
			StringBuilder ids=new StringBuilder();
			Iterator<String> it=duplicate.iterator();
			while(it.hasNext()){
				ids.append(it.next().toString()).append(", ");
			}
			int len=ids.length()-2;
	        if(ids.toString().endsWith(", ")){
	        	ids.setCharAt(len, ' ');
	        }
			errors.add("error",new ActionError( "knowledgepro.admission.uploadapprovedleaves.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		log.info("Leaving uploadDefineClassSubjects input");
		return mapping.findForward(CMSConstants.UPLOAD_PUC_APPROVED_LEAVES);
	}
}
