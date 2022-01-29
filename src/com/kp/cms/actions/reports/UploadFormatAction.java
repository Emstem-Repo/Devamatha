package com.kp.cms.actions.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.UploadFormatForm;
import com.kp.cms.handlers.reports.UploadFormatHandler;

public class UploadFormatAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(UploadFormatAction.class);

	
	public ActionForward initUploadFormat(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		log.info("entered initUploadFormat..");
		UploadFormatForm uploadFormatForm = (UploadFormatForm)form;
		uploadFormatForm.resetFields();
		HttpSession session = request.getSession(false);	
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		session.removeAttribute(CMSConstants.CSV_BYTES);		
		log.info("exit initUploadFormat..");
			return mapping.findForward(CMSConstants.INIT_UPLOAD_FORMAT);
	}
	
	public ActionForward downloadUploadFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered downloadUploadFile..");
		UploadFormatForm uploadFormatForm = (UploadFormatForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = uploadFormatForm.validate(mapping, request);
		String mode=uploadFormatForm.getFormatType();
		boolean isUpdated;
		if (errors.isEmpty()) {	
			try {
		setUserId(request, uploadFormatForm);
		if(mode.equalsIgnoreCase("Excel")){
			 isUpdated =UploadFormatHandler.getInstance().exportTOExcel(uploadFormatForm,request);
		}else
			isUpdated =UploadFormatHandler.getInstance().exportTOCsv(uploadFormatForm,request);
	
 		if(isUpdated){
 			if(mode.equalsIgnoreCase("Excel")){
 				uploadFormatForm.setMode("excel");
 				uploadFormatForm.setDownloadExcel("download");
 			}else{
 				uploadFormatForm.setMode("csv");
 				uploadFormatForm.setDownloadCSV("download");
 			}
			ActionMessage message = new ActionMessage("knowledgepro.reports.format.columnsUpdate");
			messages.add("messages", message);
			saveMessages(request, messages);
			uploadFormatForm.resetFields();
		}
		}catch (ApplicationException ae) {
			log.error("error occured in downloadUploadFile in UploadFormatAction",ae);
			String msg = super.handleApplicationException(ae);
			uploadFormatForm.setErrorMessage(msg);
			uploadFormatForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in downloadUploadFile in UploadFormatAction",e);
			String msg = super.handleApplicationException(e);
			uploadFormatForm.setErrorMessage(msg);
			uploadFormatForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);			
		}
		log.info("exit downloadUploadFile..");
		return mapping.findForward(CMSConstants.INIT_UPLOAD_FORMAT);
	}

}
