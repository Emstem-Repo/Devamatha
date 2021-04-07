package com.kp.cms.actions.admission;

import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admin.AdmMeritListAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmPromotionForm;
import com.kp.cms.handlers.admission.AdmPromotionHandler;

public class AdmPromotionAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmPromotionAction.class);
	
	public ActionForward initPromoteSecondLang(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPromoteSecondLang input");
		AdmPromotionForm admPromote=(AdmPromotionForm)form;
		admPromote.resetFields();
		log.info("Leaving initPromoteSecondLang input");
		return mapping.findForward(CMSConstants.UPLOAD_SECONDLANG);
	} 
	public ActionForward uploadSecondLang(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadMeritList input");
		AdmPromotionForm admPromote=(AdmPromotionForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, admPromote);
		ActionErrors errors = admPromote.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = admPromote.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	//File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_SECOND_LANG);
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
			//String source=prop.getProperty(CMSConstants.UPLOAD_SECOND_LANG);
			//file = new File(filePath+source);
			boolean isAdded=AdmPromotionHandler.getInstance().uploadSecondLang(file1, admPromote);
			
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
				return mapping.findForward(CMSConstants.UPLOAD_SECONDLANG);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadSecondLang");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admPromote.setErrorMessage(msg);
			admPromote.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=admPromote.getCodes();
		if(duplicate!=null && !duplicate.isEmpty()){
			StringBuilder ids=new StringBuilder();
			Iterator it=duplicate.iterator();
			while(it.hasNext()){
				ids.append(it.next().toString()).append(", ");
			}
			int len=ids.length()-2;
	        if(ids.toString().endsWith(", ")){
	            ids.setCharAt(len, ' ');
	        }
			errors.add("error",new ActionError( "knowledgepro.admission.uploadSecondLanguage.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		log.info("Leaving uploadSecondLang input");
		return mapping.findForward(CMSConstants.UPLOAD_SECONDLANG);
	}
	
	public ActionForward initPromoteSupliMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initPromoteSupliMarks input");
		AdmPromotionForm admPromote=(AdmPromotionForm)form;
		admPromote.resetFields();
		log.info("Leaving initPromoteSupliMarks input");
		return mapping.findForward(CMSConstants.UPLOAD_SUPLI_MARKS);
	} 
	
	public ActionForward uploadSupliMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadSupliMarks input");
		AdmPromotionForm admPromote=(AdmPromotionForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, admPromote);
		ActionErrors errors = admPromote.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = admPromote.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	//File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_SUPLI);
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
			//String source=prop.getProperty(CMSConstants.UPLOAD_SUPLI);
			//file = new File(filePath+source);
			boolean isAdded=AdmPromotionHandler.getInstance().uploadSupliMarks(file1, admPromote);
			
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
				return mapping.findForward(CMSConstants.UPLOAD_SUPLI_MARKS);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadSupliMarks");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admPromote.setErrorMessage(msg);
			admPromote.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=admPromote.getRegNos();
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
			errors.add("error",new ActionError( "knowledgepro.admission.uploadSuplimarks.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		log.info("Leaving uploadSupliMarks input");
		return mapping.findForward(CMSConstants.UPLOAD_SUPLI_MARKS);
	}
	
}
