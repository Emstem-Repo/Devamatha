package com.kp.cms.actions.phd;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.phd.PhdStudyAggrementHandler;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;

@SuppressWarnings("deprecation")
public class PhdGuideReminderationAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdGuideReminderationAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	// gets initial list of Exam Definition
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPhdStudyRemenderation(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm) form;
		   objForm.clearPage();
		   setUserId(request, objForm);
		   objForm.setPrint(false);
		  return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward guideDetailsSearch(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		 	
		log.debug("Entering feeChallanSearch ");
		PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm) form;
		objForm.setStudentDetailsList(null);
	 	ActionErrors errors = objForm.validate(mapping, request);
	 	objForm.setPrint(false);
	 	try {
	 		if(errors.isEmpty()) {
	 			PhdStudyAggrementHandler.getInstance().guideDetailsSearch(objForm);
	 		} else {
	 			saveErrors(request, errors);
	 			return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	log.debug("Leaving feeChallanSearch ");
		return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
	}
	
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setDataToTemplet(PhdDocumentSubmissionScheduleForm objForm) throws Exception{
		List<String> printList=new ArrayList<String>();
		Iterator<PhdDocumentSubmissionScheduleTO> itr=objForm.getStudentDetailsList().iterator();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLET_GUIDE_REMENDERATION);
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			while (itr.hasNext()) {
				PhdDocumentSubmissionScheduleTO documentTo = (PhdDocumentSubmissionScheduleTO) itr.next();
				if(documentTo.getChecked()!=null){
				String message = desc;
				if(documentTo.getRegisterNo()!=null)
				message = message.replace(CMSConstants.TEMPLATE_REGISTER_NO,documentTo.getRegisterNo());
				if(documentTo.getStudentName()!=null)
				message = message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, documentTo.getStudentName());
				if(documentTo.getCourseName()!=null)
				message = message.replace(CMSConstants.TEMPLATE_COURSE, documentTo.getCourseName());
				if(documentTo.getDocumentName()!=null)
				message = message.replace(CMSConstants.DOCUMENT_NAME, documentTo.getDocumentName());
				if(documentTo.getSubmittedDate()!=null)
				message = message.replace(CMSConstants.SUBMITTED_DATE, documentTo.getSubmittedDate());
				if(documentTo.getGuide()!=null){
				message = message.replace(CMSConstants.TEMPLATE_GUIDE, documentTo.getGuide());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_GUIDE, "");
				}
				if(documentTo.getCoGuide()!=null){
				message = message.replace(CMSConstants.TEMPLATE_COGUIDE, documentTo.getCoGuide());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_COGUIDE,"");
				}
				printList.add(message);
				}
			}
		}
		objForm.setMessageList(printList);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward checkGuideReminders (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.debug("inside printChallan in Action");
		PhdDocumentSubmissionScheduleForm objForm = (PhdDocumentSubmissionScheduleForm) form;
		 ActionErrors errors = objForm.validate(mapping, request);
		try {
		if (objForm.getStudentDetailsList()==null || objForm.getStudentDetailsList().isEmpty()) {
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
			saveErrors(request, errors);
			objForm.setPrint(false);
			log.info("Exit BulkChallanPrintAction - printChallan size 0");
			return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
			} 
		  objForm.setPrint(true);
		  saveErrors(request, errors);
		 saveMessages(request, messages);
		 }catch (Exception exception) {
		 String msg = super.handleApplicationException(exception);
		 objForm.setErrorMessage(msg);
		 objForm.setErrorStack(exception.getMessage());
		 return mapping.findForward(CMSConstants.ERROR_PAGE);
		 }
		 setDataToTemplet(objForm);
		 setUpdateGeneratedDate(objForm);
		 PhdStudyAggrementHandler.getInstance().guideDetailsSearch(objForm);
		 if(objForm.getMessageList()==null || objForm.getMessageList().isEmpty()){
			 objForm.setPrint(false);
			 errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_PLEASESELECT));
			 saveErrors(request, errors);
		 }
		log.info("Exit BulkChallanPrintAction - printChallan");
		return mapping.findForward(CMSConstants.PHD_GUIDE_REMENDERATION);
		 	
	}
	private void setUpdateGeneratedDate(PhdDocumentSubmissionScheduleForm objForm) throws Exception{
		PhdStudyAggrementHandler.getInstance().setUpdateGeneratedDate(objForm);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward printGuideReminders (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward(CMSConstants.PRINT_GUIDE_REMENDERATION);
		}
	
}
