package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm;
import com.kp.cms.forms.attendance.SubjectGroupDetailsForm;
import com.kp.cms.handlers.admission.AssignSubjectGroupHistoryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.AssignSubjectGroupHistoryTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AssignSubjectGroupHistoryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AssignSubjectGroupHistoryAction.class);
	private static final String CLASSMAP = "classMap";
	private static final String ASSIGN_SUBJECTGROUP_ENTRY_OPERATION = "SubjectGroupEntryOperation";
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initAssignSubjectGroupHistory(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AssignSubjectGroupHistoryForm assignSubGrpHistoryForm = (AssignSubjectGroupHistoryForm)form;
			assignSubGrpHistoryForm.setYear(null);
			assignSubGrpHistoryForm.setClassSchemeWiseId(null);
			assignSubGrpHistoryForm.clearList();
			try{
				setClassMapToRequest(request,assignSubGrpHistoryForm);
			}catch (Exception e) {
				log.error("error in initCancelPromotion...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					assignSubGrpHistoryForm.setErrorMessage(msg);
					assignSubGrpHistoryForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
		}
		/**
		 * @param request
		 * @param subjectGroupDetailsForm
		 * @throws Exception
		 */
		public void setClassMapToRequest(HttpServletRequest request,
				AssignSubjectGroupHistoryForm assignSubGrpHistory) throws Exception {
			log
					.info("Entering into setClassMapToRequest of AssignSubjectGroupHistoryAction");
			try {
				if (assignSubGrpHistory.getYear() == null || assignSubGrpHistory.getYear().isEmpty()) {
					// Below statements is used to get the current year and for the
					// year get the class Map.
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					int year = CurrentAcademicYear.getInstance().getAcademicyear();
					if (year != 0) {
						currentYear = year;
					}// end
					Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
					request.setAttribute(CLASSMAP, classMap);
				}
				// Otherwise get the classMap for the selected year
				else {
					int year = Integer.parseInt(assignSubGrpHistory.getYear().trim());
					Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
					request.setAttribute(CLASSMAP, classMap);
				}

			} catch (Exception e) {
				log.error("Error occured at setClassMapToRequest in AssignSubjectGroupHistoryAction",e);
				throw new ApplicationException(e);
			}
			log.info("Leaving into setClassMapToRequest of AssignSubjectGroupHistoryAction");
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	
		
		public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			@SuppressWarnings("unused")
			ActionErrors errors = new ActionErrors();
			AssignSubjectGroupHistoryForm assignSubGrpHistory = (AssignSubjectGroupHistoryForm)form;
			assignSubGrpHistory.clearList1();
			boolean flag=true;
			try{
				
				if(assignSubGrpHistory.getClassSchemeWiseId()== null || assignSubGrpHistory.getClassSchemeWiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistory);
					assignSubGrpHistory.clearList();
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);				
				}
				List<AssignSubjectGroupHistoryTO> subjectGroupHistoryTOs = AssignSubjectGroupHistoryHandler.getInstance().getStudentDetails(assignSubGrpHistory);
				if(subjectGroupHistoryTOs == null || subjectGroupHistoryTOs.isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					flag=false;
					assignSubGrpHistory.setFlag(flag);
					setClassMapToRequest(request,assignSubGrpHistory);
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
				}
				assignSubGrpHistory.setFlag(flag);
				assignSubGrpHistory.setStudentDetailsList(subjectGroupHistoryTOs);
				//Used to get subjectGroupMap for based on the class
				if(assignSubGrpHistory.getClassSchemeWiseId()!=null && !assignSubGrpHistory.getClassSchemeWiseId().isEmpty()){
					List<SubjectGroupDetailsTo>	subjectGroupMap= AssignSubjectGroupHistoryHandler.getInstance().getSubjectGroups(assignSubGrpHistory);
					assignSubGrpHistory.setSubjectGroupList(subjectGroupMap);
				}
				setClassMapToRequest(request,assignSubGrpHistory);
				setDatatoForm(assignSubGrpHistory);
			}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				assignSubGrpHistory.setErrorMessage(msg);
				assignSubGrpHistory.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
		}
		/**
		 * @param assignSubGrpHistory
		 * @throws Exception 
		 */
		private void setDatatoForm( AssignSubjectGroupHistoryForm assignSubGrpHistory) throws Exception {
		List<SubjectGroupDetailsTo> groupDetailsTos = AssignSubjectGroupHistoryHandler.getInstance().getSubjectGroupsList(assignSubGrpHistory);
			assignSubGrpHistory.setSubjectGroupsDetailsList(groupDetailsTos);
	}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward addSubjectGroupDetails(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AssignSubjectGroupHistoryForm assignSubGrpHistoryForm = (AssignSubjectGroupHistoryForm)form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			validatePromotionProcess(assignSubGrpHistoryForm,errors);
			errors = assignSubGrpHistoryForm.validate(mapping, request);
			boolean isAdded = false;
			if (errors.isEmpty()) {
			try{
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()== null || assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.clearList();
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);				
				}
				if(assignSubGrpHistoryForm.getSubjectGroupId() == null || assignSubGrpHistoryForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);			
				}
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()== null || assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty() && assignSubGrpHistoryForm.getSubjectGroupId() == null && assignSubGrpHistoryForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.clearList();
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);	
				}
				//setting user id to form.
				assignSubGrpHistoryForm.setDisplayMessage(null);
				assignSubGrpHistoryForm.setDisplayMsg(null);
				assignSubGrpHistoryForm.setList(null);
				setUserId(request, assignSubGrpHistoryForm);	
				isAdded=AssignSubjectGroupHistoryHandler.getInstance().addStudentSubjectGroups(assignSubGrpHistoryForm);
				if(assignSubGrpHistoryForm.getList()!=null && !assignSubGrpHistoryForm.getList().isEmpty()){
					List<String> detailsForms = assignSubGrpHistoryForm.getList();
					StringBuilder s = new StringBuilder();
					Iterator<String> iterator = detailsForms.iterator();
					while (iterator.hasNext()) {
						String string = (String) iterator.next();
						s.append(string).append(",");
					}
					assignSubGrpHistoryForm.setDisplayMessage("Subject Group can not be Updated for "+s);
					assignSubGrpHistoryForm.setDisplayMsg("Please check the Second Language for the above register numbers");
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					setDatatoForm(assignSubGrpHistoryForm);
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
				}
				if(isAdded){
					message = new ActionMessage(
							"knowledgepro.attendance.subjectgroup.name.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.student.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					setDatatoForm(assignSubGrpHistoryForm);
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);	
				}
				assignSubGrpHistoryForm.setSubjectGroupId(null);
				//Used to get subjectGroupMap for based on the class
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()!=null && !assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty()){
					List<SubjectGroupDetailsTo>	subjectGroupMap= AssignSubjectGroupHistoryHandler.getInstance().getSubjectGroups(assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.setSubjectGroupList(subjectGroupMap);
				}
				setClassMapToRequest(request,assignSubGrpHistoryForm);
				setDatatoForm(assignSubGrpHistoryForm);
				return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			}catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
				"knowledgepro.attendance.subjectgroup.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(assignSubGrpHistoryForm);
				return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			} catch (ReActivateException reActivateException) {
				errors .add( "error", new ActionError( "knowledgepro.attendance.subjectgroup.name.alreadyexist.reactivate"));
				saveErrors(request, errors);
				setDatatoForm(assignSubGrpHistoryForm);
				return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			} catch (Exception exception) {
				return addExceptionMessage(mapping, assignSubGrpHistoryForm,
				messages, exception);
			}
			}
			else {
				addErrors(request, errors);
				setDatatoForm(assignSubGrpHistoryForm);
				log.info("end of addSubjectGroups method in AssignSubjectGroupHistoryAction class.");
				return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			}
			
}
		/**
		 * @param assignSubGrpHistory
		 * @param errors
		 */
		private void validatePromotionProcess( AssignSubjectGroupHistoryForm assignSubGrpHistory, ActionErrors errors) {
			boolean isChecked=false;
			List<AssignSubjectGroupHistoryTO> list=assignSubGrpHistory.getStudentDetailsList();
			Iterator<AssignSubjectGroupHistoryTO> itr=list.iterator();
			while (itr.hasNext()) {
				AssignSubjectGroupHistoryTO to = (AssignSubjectGroupHistoryTO) itr.next();
				if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
					isChecked=true;
					break;
				}
			}
			if(!isChecked){
				errors.add(CMSConstants.ERROR,new ActionError("subjectGroupDetailsForm.checkbox.select"));
			}
			if(assignSubGrpHistory.getStudentDetailsList()==null || assignSubGrpHistory.getStudentDetailsList().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Subject Group Class"));
		}
		/**
		 * @param mapping
		 * @param subjectGroupDetailsForm
		 * @param messages
		 * @param exception
		 * @return
		 * @throws Exception
		 */
		private ActionForward addExceptionMessage(ActionMapping mapping,
				AssignSubjectGroupHistoryForm assignSubGrpHistoryForm,
				ActionMessages messages, Exception exception) throws Exception {
			if (exception instanceof BusinessException) {
				String msgKey = super.handleBusinessException(exception);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				assignSubGrpHistoryForm.setErrorMessage(msg);
				assignSubGrpHistoryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw exception;
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
		public ActionForward editSubjectGroup(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			AssignSubjectGroupHistoryForm assignSubGrpHistoryForm = (AssignSubjectGroupHistoryForm)form;
			ActionErrors errors = new ActionErrors();
			boolean flag1=true;
			try{
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()== null || assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.clearList();
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);				
				}
				if(assignSubGrpHistoryForm.getSubjectGroupId() == null || assignSubGrpHistoryForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);			
				}
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()== null || assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty() && assignSubGrpHistoryForm.getSubjectGroupId() == null && assignSubGrpHistoryForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.clearList();
					return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);	
				}
				AssignSubjectGroupHistoryHandler subjectGroupHistoryHandler = AssignSubjectGroupHistoryHandler.getInstance();
				List<AssignSubjectGroupHistoryTO> historyTOs=subjectGroupHistoryHandler.getEditSubjectGroups(assignSubGrpHistoryForm);
				assignSubGrpHistoryForm.setFlag1(flag1);
				assignSubGrpHistoryForm.setStudentDetailsList(historyTOs);
				assignSubGrpHistoryForm.setDisplayMessage(null);
				assignSubGrpHistoryForm.setDisplayMsg(null);
				request.setAttribute(ASSIGN_SUBJECTGROUP_ENTRY_OPERATION,
						CMSConstants.EDIT_OPERATION);
				//Used to get subjectGroupMap for based on the class
				if(assignSubGrpHistoryForm.getClassSchemeWiseId()!=null && !assignSubGrpHistoryForm.getClassSchemeWiseId().isEmpty()){
					List<SubjectGroupDetailsTo>	subjectGroupMap= AssignSubjectGroupHistoryHandler.getInstance().getSubjectGroups(assignSubGrpHistoryForm);
					assignSubGrpHistoryForm.setSubjectGroupList(subjectGroupMap);
				}
			}catch (Exception exception) {
				log.error("Error occured in editSubjectGroups of SubjectGroupDetailsAction",exception);
				String msg = super.handleApplicationException(exception);
				assignSubGrpHistoryForm.setErrorMessage(msg);
				assignSubGrpHistoryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			setClassMapToRequest(request,assignSubGrpHistoryForm);
			return mapping.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward updateSubjectGroups(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		AssignSubjectGroupHistoryForm assignSubGrpHistoryForm = (AssignSubjectGroupHistoryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		try {
			assignSubGrpHistoryForm.setDisplayMessage(null);
			assignSubGrpHistoryForm.setDisplayMsg(null);
			assignSubGrpHistoryForm.setList(null);
			setUserId(request, assignSubGrpHistoryForm);
			boolean isUpdated = AssignSubjectGroupHistoryHandler.getInstance()
					.addStudentSubjectGroups(assignSubGrpHistoryForm);
			// Used to get subjectGroupMap for based on the class
			if (assignSubGrpHistoryForm.getClassSchemeWiseId() != null
					&& !assignSubGrpHistoryForm.getClassSchemeWiseId()
							.isEmpty()) {
				List<SubjectGroupDetailsTo> subjectGroupMap = AssignSubjectGroupHistoryHandler
						.getInstance()
						.getSubjectGroups(assignSubGrpHistoryForm);
				assignSubGrpHistoryForm.setSubjectGroupList(subjectGroupMap);
			}
			//
			if(assignSubGrpHistoryForm.getList()!=null && !assignSubGrpHistoryForm.getList().isEmpty()){
				List<String> detailsForms = assignSubGrpHistoryForm.getList();
				StringBuilder s = new StringBuilder();
				Iterator<String> iterator = detailsForms.iterator();
				while (iterator.hasNext()) {
					String string = (String) iterator.next();
					s.append(string).append(",");
				}
				assignSubGrpHistoryForm.setDisplayMessage("Subject Group can not be Updated for "+s);
				assignSubGrpHistoryForm.setDisplayMsg("Please check the Second Language for the above register numbers");
				setClassMapToRequest(request,assignSubGrpHistoryForm);
				setDatatoForm(assignSubGrpHistoryForm);
				request.setAttribute(ASSIGN_SUBJECTGROUP_ENTRY_OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping .findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			}
			assignSubGrpHistoryForm.setStudentDetailsList(null);
			List<AssignSubjectGroupHistoryTO> subjectGroupHistoryTOs = AssignSubjectGroupHistoryHandler
					.getInstance().getStudentDetails(assignSubGrpHistoryForm);
			assignSubGrpHistoryForm.setFlag1(false);
			assignSubGrpHistoryForm
					.setStudentDetailsList(subjectGroupHistoryTOs);
			assignSubGrpHistoryForm.setSubjectGroupId(null);
			
			
			if (isUpdated) {
				message = new ActionMessage(
						"knowledgepro.attendance.subjectgroup.name.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				setClassMapToRequest(request,assignSubGrpHistoryForm);
				setDatatoForm(assignSubGrpHistoryForm);
				return mapping .findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
			}
			setClassMapToRequest(request,assignSubGrpHistoryForm);
			setDatatoForm(assignSubGrpHistoryForm);
			
			//
		} catch (DuplicateException duplicateException) {
			errors.add("error", new ActionError(
					"knowledgepro.attendance.subjectgroup.name.exists"));
			saveErrors(request, errors);
			setDatatoForm(assignSubGrpHistoryForm);
			return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
		} catch (Exception exception) {

			return addExceptionMessage(mapping, assignSubGrpHistoryForm,
					messages, exception);
		}
		return mapping
				.findForward(CMSConstants.ASSIGN_SUBJECT_GROUP_HISTORY_INIT);
	}
}