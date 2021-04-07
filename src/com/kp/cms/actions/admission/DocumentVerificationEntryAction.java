package com.kp.cms.actions.admission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DocumentVerificationEntryForm;
import com.kp.cms.handlers.admission.DocumentVerificationEntryHandler;

/**
 * @author Nagarjun
 *
 */
public class DocumentVerificationEntryAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(DocumentVerificationEntryAction.class);
	
	/**
	 * Method to open the init jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DocumentVerificationEntryForm documentVerificationEntryForm = (DocumentVerificationEntryForm) form;
		documentVerificationEntryForm.clearData();
		documentVerificationEntryForm.setRegisterNo(null);
		return mapping.findForward(CMSConstants.INIT_DOCUMENTS_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDocumentsForStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DocumentVerificationEntryForm documentVerificationEntryForm = (DocumentVerificationEntryForm) form;
		documentVerificationEntryForm.clearData();
		ActionMessages messages = new ActionMessages();
		try{
			DocumentVerificationEntryHandler.getInstance().setDataToForm(documentVerificationEntryForm);
			if(documentVerificationEntryForm.getDocList() == null || documentVerificationEntryForm.getDocList().isEmpty()){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.norecords"));
				saveMessages(request, messages);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			documentVerificationEntryForm.setErrorMessage(msg);
			documentVerificationEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_DOCUMENTS_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DocumentVerificationEntryForm documentVerificationEntryForm = (DocumentVerificationEntryForm) form;
		setUserId(request, documentVerificationEntryForm);
		ActionMessages messages = new ActionMessages();
		try{
			boolean save = DocumentVerificationEntryHandler.getInstance().saveStudent(documentVerificationEntryForm);
			if(save){
				documentVerificationEntryForm.clearData();
				documentVerificationEntryForm.setRegisterNo(null);
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.addsuccess","Documents "));
				saveMessages(request, messages);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			documentVerificationEntryForm.setErrorMessage(msg);
			documentVerificationEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_DOCUMENTS_ENTRY);
	}
}
