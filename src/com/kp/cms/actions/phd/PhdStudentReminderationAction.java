package com.kp.cms.actions.phd;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.phd.PhdGuidesFeesPaymentHandler;
import com.kp.cms.handlers.phd.PhdStudentReminderationHandler;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class PhdStudentReminderationAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdStudentReminderationAction.class);
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
		  PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		   objForm.clearPage();
		   objForm.clearPage1();
		   setUserId(request, objForm);
		   objForm.setPrint(false);
		  return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward studentDetailsSearch(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		log.debug("Entering feeChallanSearch ");
		 PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		 objForm.clearPage1();
	 	HttpSession session=request.getSession();
		session.setAttribute("GuideDetails","");
		ActionErrors errors = objForm.validate(mapping, request);
	 	objForm.setPrint(false);
	 	try {
	 		if(errors.isEmpty()) {
	 			PhdStudentReminderationHandler.getInstance().studentDetailsSearch(objForm,errors);
	 		} else {
	 			saveErrors(request, errors);
	 			return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	saveErrors(request, errors);
	 	log.debug("Leaving feeChallanSearch ");
		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward generateStudentDetails (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.debug("inside printChallan in Action");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		objForm.setPrint(false);
		HttpSession session=request.getSession();
		session.setAttribute("GuideDetails","");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
		try {
		 PhdStudentReminderationHandler.getInstance().generateStudentDetails(objForm);
		 saveErrors(request, errors);
		 saveMessages(request, messages);
		 }catch (Exception exception) {
		 String msg = super.handleApplicationException(exception);
		 objForm.setErrorMessage(msg);
		 objForm.setErrorStack(exception.getMessage());
		 return mapping.findForward(CMSConstants.ERROR_PAGE);
		 }
		log.info("Exit BulkChallanPrintAction - printChallan");
		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
		 	
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward printGuideDetails (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.debug("inside printGuideDetails in Action");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		HttpSession session=request.getSession();
		session.setAttribute("GuideDetails","");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = objForm.validate(mapping, request);
	 	boolean flag=false;
		try {
			setVoucherNo(objForm);
			setDataToTemplet(objForm);
			if(objForm.getMessageList()==null || objForm.getMessageList().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.phd.reminderation.templet.empty"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
			}
			setUpdateGeneratedDate(objForm);
			flag=PhdStudentReminderationHandler.getInstance().saveGuideRemenderation(objForm);
			setUpdateVoucherNo(objForm);
		    objForm.setPrint(true);
		   if(flag){
			   messages.add(CMSConstants.MESSAGES, new ActionMessage( "KnowledgePro.phd.address.successfully"));
			  saveMessages(request, messages);
		   }else{
				messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.internalGuide.failure"));
				saveMessages(request, messages);
 			}
		  saveErrors(request, errors);
		 saveMessages(request, messages);
		 }catch (Exception exception) {
		 String msg = super.handleApplicationException(exception);
		 objForm.setErrorMessage(msg);
		 objForm.setErrorStack(exception.getMessage());
		 return mapping.findForward(CMSConstants.ERROR_PAGE);
		 } objForm.clearPage2();
		 PhdStudentReminderationHandler.getInstance().studentDetailsSearch(objForm,errors);
		 log.info("Exit BulkChallanPrintAction - printChallan");
		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
		 	
	}
	private void setUpdateVoucherNo(PhdStudentReminderationForm objForm) throws Exception{
		PhdStudentReminderationHandler.getInstance().setUpdateVoucherNo(objForm);
		
		}
	private void setVoucherNo(PhdStudentReminderationForm objForm) throws Exception{
		PhdStudentReminderationHandler.getInstance().setVoucherNo(objForm);
		
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setDataToTemplet(PhdStudentReminderationForm objForm) throws Exception{
		List<String> printList=new ArrayList<String>();
		if(objForm.getGuideDetailList()!=null && !objForm.getGuideDetailList().isEmpty()){
		Iterator<PhdStudentReminderationTO> itr=objForm.getGuideDetailList().iterator();
		}
		if(objForm.getCoGuideDetailList()!=null && !objForm.getCoGuideDetailList().isEmpty()){
		Iterator<PhdStudentReminderationTO> itrr=objForm.getCoGuideDetailList().iterator();
		}
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLET_GUIDE_REMENDERATION);
		if(list != null && !list.isEmpty()) {
			String desc ="";
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
				desc = list.get(0).getTemplateDescription();
			}
			String message = desc;
			String messages = desc;
			
				if(objForm.getGuideId()!=null || objForm.getiGuideId()!=null){
				if(objForm.getRegisterNo()!=null)
				message = message.replace(CMSConstants.TEMPLATE_REGISTER_NO,objForm.getRegisterNo());
				if(objForm.getStudentName()!=null)
				message = message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, objForm.getStudentName());
				if(objForm.getCourseName()!=null)
				message = message.replace(CMSConstants.TEMPLATE_COURSE, objForm.getCourseName());
				if(objForm.getGeneratedDate()!=null){
				message = message.replace(CMSConstants.TEMPLATE_GENERATED, objForm.getGeneratedDate());
				}else{
				 Date date = new Date();
				 message = message.replace(CMSConstants.TEMPLATE_GENERATED,CommonUtil.formatDates(date));
				}
				if(objForm.getGuideName()!=null){
				message = message.replace(CMSConstants.TEMPLATE_GUIDE, objForm.getGuideName());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_GUIDE, "");
				}
				if(objForm.getgAmountTotal()!=null){
					message = message.replace(CMSConstants.TOTAL_AMOUNT,objForm.getgAmountTotal());
					}
				else{
					message = message.replace(CMSConstants.TOTAL_AMOUNT,"");
				}
				if(objForm.getGeneratedNo()!=null){
					message = message.replace(CMSConstants.GENERATED_NO, objForm.getGeneratedNo());
					objForm.setgVoucherNo(objForm.getGeneratedNo());
				    objForm.setGeneratedNo(Integer.toString(Integer.parseInt(objForm.getGeneratedNo())+1));
				}else if(objForm.getgVoucherNo()!=null){
					message = message.replace(CMSConstants.GENERATED_NO, objForm.getgVoucherNo());
				}
				printList.add(message);
				}
			
				if(objForm.getCoGuideId()!=null || objForm.getiCoGuideId()!=null){
				if(objForm.getRegisterNo()!=null)
					messages = messages.replace(CMSConstants.TEMPLATE_REGISTER_NO,objForm.getRegisterNo());
				if(objForm.getStudentName()!=null)
					messages = messages.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, objForm.getStudentName());
				if(objForm.getCourseName()!=null)
					messages = messages.replace(CMSConstants.TEMPLATE_COURSE, objForm.getCourseName());
				if(objForm.getGeneratedDate()!=null){
					messages = messages.replace(CMSConstants.TEMPLATE_GENERATED, objForm.getGeneratedDate());
				}else{
					 Date date = new Date();
					messages = messages.replace(CMSConstants.TEMPLATE_GENERATED,CommonUtil.formatDates(date));
				}
				if(objForm.getCoGuideName()!=null){
					messages = messages.replace(CMSConstants.TEMPLATE_GUIDE, objForm.getCoGuideName());
				}else{
					messages = messages.replace(CMSConstants.TEMPLATE_GUIDE,"");
				}
				
				if(objForm.getcAmountTotal()!=null){
					messages = messages.replace(CMSConstants.TOTAL_AMOUNT,objForm.getcAmountTotal());
				}else{
					messages = messages.replace(CMSConstants.TOTAL_AMOUNT,"");
				}
				if(objForm.getGeneratedNo()!=null){
					messages = messages.replace(CMSConstants.GENERATED_NO, objForm.getGeneratedNo());
					objForm.setcVoucherNo(objForm.getGeneratedNo());
				   objForm.setGeneratedNo(Integer.toString(Integer.parseInt(objForm.getGeneratedNo())+1));
				}else if(objForm.getcVoucherNo()!=null){
					messages = messages.replace(CMSConstants.GENERATED_NO, objForm.getcVoucherNo());
				}printList.add(messages);
			   }
			
		}
		objForm.setMessageList(printList);
	}

	private void setUpdateGeneratedDate(PhdStudentReminderationForm objForm) throws Exception{
		PhdStudentReminderationHandler.getInstance().setUpdateGeneratedDate(objForm);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward printStudentReminders (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward(CMSConstants.PRINT_STUDENT_REMENDERATION);
		}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getGuidetotalAmount (ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside printChallan in Action");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		HttpSession session=request.getSession();
		Object str=session.getAttribute("GuideDetails");
		if(str!=null){
		if(str.equals("edit")){
			session.setAttribute("GuideDetails", "edit");
		}}
	 	errors = objForm.validate(mapping, request);
	 	String gtotal="";
		try {
			gtotal= PhdStudentReminderationHandler.getInstance().getGuidetotalAmount(objForm);
		 }catch (Exception exception) {
		 String msg = super.handleApplicationException(exception);
		 objForm.setErrorMessage(msg);
		 objForm.setErrorStack(exception.getMessage());
		 return mapping.findForward(CMSConstants.ERROR_PAGE);
		 }
		 request.setAttribute("empNo", gtotal);
		log.info("Exit BulkChallanPrintAction - printChallan");
		objForm.setPrint(false);
		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	}
	
	// used for guides fees GuidesFeesPayment
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGuidesFeesPayment(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		  PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		   objForm.clearPage();
		   objForm.setPrint(false);
		   setUserId(request, objForm);
		  return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchGuidesFeesPayment(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		 	
		log.debug("Entering feeChallanSearch ");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		objForm.setGuideFeesPatmentList(null);
		 objForm.setPrint(false);
	 	ActionErrors errors = objForm.validate(mapping, request);
	 	try {
	 		if(errors.isEmpty()) {
	 			PhdGuidesFeesPaymentHandler.getInstance().searchGuidesFeesPayment(objForm,errors);
	 		} else {
	 			saveErrors(request, errors);
	 			return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	saveErrors(request, errors);
	 	log.debug("Leaving feeChallanSearch ");
		return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGuideFeesPayment(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		 	
		log.debug("Entering feeChallanSearch ");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
	 	ActionMessages messages = new ActionMessages();
	 	ActionErrors errors = objForm.validate(mapping, request);
	 	objForm.setPrint(false);
	 	boolean flag=false;
	 	try {
	 		if(errors.isEmpty()) {
	 			flag=PhdGuidesFeesPaymentHandler.getInstance().saveGuideFeesPayment(objForm,errors);
	 			if(flag){
	 				messages.add(CMSConstants.MESSAGES, new ActionMessage( "KnowledgePro.phd.address.successfully"));
					saveMessages(request, messages);
	 			}else{
	 				messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.internalGuide.failure"));
					saveMessages(request, messages);
	 			}
	 		} else {
	 			saveErrors(request, errors);
	 			return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	 		}
	 	} catch(DataNotFoundException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	 	} catch (Exception e) {
	 		log.debug(e.getMessage());
	 		log.warn("Exception while searching");
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
	 	 objForm.clearPage();
	 	saveErrors(request, errors);
	 	log.debug("Leaving feeChallanSearch ");
		return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editGuideFees(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
     {
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		errors = objForm.validate(mapping, request);
		log.debug("Entering ValuatorCharges ");
		HttpSession session=request.getSession();
		objForm.clearPage3();
		try {
			PhdGuidesFeesPaymentHandler.getInstance().editGuideDetails(objForm);
			session.setAttribute("GuideDetails", "edit");
			log.debug("Leaving editValuatorCharges ");
		} catch (Exception e) {
			log.error("error in editing ValuatorCharges...", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		objForm.setPrint(false);
		return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePhdGuidesFees(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
{
		log.debug("Enter: updatevaluatorCharges Action");
		PhdStudentReminderationForm objForm = (PhdStudentReminderationForm) form;
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		ActionErrors errors = objForm.validate(mapping, request);
		boolean isUpdated = false;
       if(errors.isEmpty()){
		try {
			if (isCancelled(request)) {
				objForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdGuidesFeesPaymentHandler.getInstance().editGuideDetails(objForm);
				session.setAttribute("GuideDetails", "edit");
				return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
			}
			setUserId(request, objForm); // setting user id to update
			isUpdated =PhdGuidesFeesPaymentHandler.getInstance().updateGuideRemenderation(objForm);
			if (isUpdated) {
				if(errors.isEmpty()){
				ActionMessage message = new ActionMessage("knowledgepro.phd.internalguide.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setDataToTemplet(objForm);
				objForm.setPrint(true);
				objForm.clearPage2();
				PhdGuidesFeesPaymentHandler.getInstance().searchGuidesFeesPayment(objForm,errors);
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
				}
			} else {
				errors.add("error", new ActionError("knowledgepro.phd.internal.update.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in edit valuatorcharges", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			session.setAttribute("GuideDetails", "edit");
			return mapping.findForward(CMSConstants.PHD_STUDENT_REMENDERATION);
		}
		log.debug("Exit: action class updatevaluatorCharges");
		return mapping.findForward(CMSConstants.PHD_GUIDE_FEES_PAYMENT);
	}
}
