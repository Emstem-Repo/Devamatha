package com.kp.cms.actions.attendance;

import java.util.Calendar;
import java.util.HashMap;
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
import com.kp.cms.forms.attendance.SubjectGroupDetailsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.SubjectGroupDetailsHandler;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author Administrator
 *
 */
public class SubjectGroupDetailsAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(SubjectGroupDetailsAction.class);
	
	private static final String CLASSMAP = "classMap";
	private static final String SUBJECTGROUPMAP ="subjectGroupMap";
	@SuppressWarnings("unused")
	private static final String SUBJECTGROUP_ENTRY_OPERATION = "SubjectGroupEntryOperation";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes SubjectGroupDetails
	 * 			Initializes classMap 
	 * @throws Exception
	 */
	
	public ActionForward initSubjectGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initSubjectGroup of SubjectGroupDetailsAction");
		SubjectGroupDetailsForm subjectGroupDetailsForm= (SubjectGroupDetailsForm)form;
		subjectGroupDetailsForm.clearList();
		subjectGroupDetailsForm.setSubjectGroupMap(null);
		subjectGroupDetailsForm.setSpecializationMap1(null);
		try {
			setClassMapToRequest(request,subjectGroupDetailsForm);
		} catch (Exception exception) {
		}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
	}
	
	/**
	 * Performs add subject group action.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public ActionForward addSubjectGroups(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of addSubjectGroups method in SubjectGroupDetailsAction class.");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		SubjectGroupDetailsForm subjectGroupDetailsForm = (SubjectGroupDetailsForm) form;
		validatePromotionProcess(subjectGroupDetailsForm,errors);
		errors = subjectGroupDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				if(subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					subjectGroupDetailsForm.clearList();
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty() && subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					subjectGroupDetailsForm.clearList();
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				//setting user id to form.
				subjectGroupDetailsForm.setDisplayMessage(null);
				subjectGroupDetailsForm.setDisplayMsg(null);
				subjectGroupDetailsForm.setSpecializationMessage(null);
				setUserId(request, subjectGroupDetailsForm);	
				subjectGroupDetailsForm.setList(null);
				subjectGroupDetailsForm.setSpecializationStuList(null);
				subjectGroupDetailsForm.setSpecializationMap1(null);
				boolean isAdded=SubjectGroupDetailsHandler.getInstance().addSubjectGroups(subjectGroupDetailsForm);
				setDatatoForm(subjectGroupDetailsForm);
				if(subjectGroupDetailsForm.getList()!=null && !subjectGroupDetailsForm.getList().isEmpty()){
					List<String> detailsForms = subjectGroupDetailsForm.getList();
					StringBuilder s = new StringBuilder();
					Iterator<String> iterator = detailsForms.iterator();
					while (iterator.hasNext()) {
						String string = (String) iterator.next();
						s.append(string).append(",");
					}
					subjectGroupDetailsForm.setDisplayMessage("Subject Group can not be Updated for "+s);
					subjectGroupDetailsForm.setDisplayMsg("Please check the Second Language for the above register numbers");
					setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				// Specialization
				if(subjectGroupDetailsForm.getSpecializationStuList()!=null && !subjectGroupDetailsForm.getSpecializationStuList().isEmpty()){
					List<String> list = subjectGroupDetailsForm.getSpecializationStuList();
					StringBuilder s = new StringBuilder();
					Iterator<String> iterator = list.iterator();
					while (iterator.hasNext()) {
						String string = (String) iterator.next();
						s.append(string).append(",");
					}
					subjectGroupDetailsForm.setSpecializationMessage("This Specialization is already assigned for Register numbers(s)"+s);
					/*setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);*/
				}
				
				//
				//if adding is success.
				if(isAdded){
				message = new ActionMessage( "knowledgepro.attendance.subjectgroup.name.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				}
				else
				{
					errors.add("error", new ActionError("knowledgepro.attendance.student.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				subjectGroupDetailsForm.setSubjectGroupId(null);
				subjectGroupDetailsForm.setSpecializationId(null);
				//Used to get subjectGroupMap for based on the class
				if (subjectGroupDetailsForm.getClassSchemewiseId() != null
								&& subjectGroupDetailsForm.getClassSchemewiseId().length() != 0) {
					List<SubjectGroupDetailsTo>	subjectGroupMap= SubjectGroupDetailsHandler.getInstance().getSubjectGroupsId(subjectGroupDetailsForm);
					subjectGroupDetailsForm.setSubjectGroupMap(subjectGroupMap);
				} 
				setClassMapToRequest(request,subjectGroupDetailsForm);
				setDatatoForm1(subjectGroupDetailsForm);
				setSpecializationDataToForm(subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.attendance.subjectgroup.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			} catch (ReActivateException reActivateException) {
				errors
						.add(
								"error",
								new ActionError(
										"knowledgepro.attendance.subjectgroup.name.alreadyexist.reactivate"));
				saveErrors(request, errors);

				setDatatoForm(subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, subjectGroupDetailsForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			setDatatoForm(subjectGroupDetailsForm);
			log.info("end of addSubjectGroups method in SubjectGroupDetailsAction class.");
			return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
		}

	}
	/**
	 * @param subjectGroupDetailsForm
	 * @param errors
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void validatePromotionProcess(
			SubjectGroupDetailsForm subjectGroupDetailsForm, ActionErrors errors)throws Exception {
		boolean isChecked=false;
		List<SubjectGroupDetailsTo> list=subjectGroupDetailsForm.getSubjectList();
		Iterator<SubjectGroupDetailsTo> itr=list.iterator();
		while (itr.hasNext()) {
			SubjectGroupDetailsTo to = (SubjectGroupDetailsTo) itr.next();
			if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
				isChecked=true;
				break;
			}
		}
		if(!isChecked){
			errors.add(CMSConstants.ERROR,new ActionError("subjectGroupDetailsForm.checkbox.select"));
		}
		if(subjectGroupDetailsForm.getSubjectList()==null || subjectGroupDetailsForm.getSubjectList().isEmpty())
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","Subject Group Class"));
	}

	/**
	 * Sets required data to the request scope.
	 * 
	 * @param SubjectGroupDetailsForm
	 * @throws Exception 
	 * @throws Exception
	 */
	
	private void setDatatoForm(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception {

		log.info("call of setDatatoForm method in SubjectGroupDetailsAction class.");
		//used to get the data from ApplicantSubjectGroup table and setting to list of type SubjectGroupDetailsTo.
		List<SubjectGroupDetailsTo> subjectGroupTo = SubjectGroupDetailsHandler.getInstance()
				.getSubjectGroups(subjectGroupDetailsForm);
		//setting list to form.
		subjectGroupDetailsForm.setSubjectGroupList(subjectGroupTo);
		log.info("end of setDatatoForm method in SubjectGroupDetailsAction class.");	
	}
	
	/**This Action method is used to get the Students Reg.No and Names from the database.
	 * to get student details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "null" })
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of getStudentDetails method in SubjectGroupDetailsAction class.");
		ActionErrors errors = new ActionErrors();
 		SubjectGroupDetailsForm subjectGroupDetailsForm = (SubjectGroupDetailsForm) form;
 	    boolean flag=true;
		try {
			subjectGroupDetailsForm.setSubjectList(null);
			subjectGroupDetailsForm.setSubjectGroupList(null);
			subjectGroupDetailsForm.setSubjectGroupId(null);
			subjectGroupDetailsForm.setSubjectGroupMap(null);
			subjectGroupDetailsForm.setDisplayMessage(null);
			subjectGroupDetailsForm.setDisplayMsg(null);
			subjectGroupDetailsForm.setExamSpecializationTO(null);
			subjectGroupDetailsForm.setSpecializationMessage(null);
			subjectGroupDetailsForm.setSpecializationMap1(null);
			if(subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
				saveErrors(request, errors);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			SubjectGroupDetailsHandler subjectHandler = SubjectGroupDetailsHandler
					.getInstance();
			List<SubjectGroupDetailsTo> subjectList = subjectHandler.getStudentDetails(subjectGroupDetailsForm);
			
			if(subjectList == null || subjectList.isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
				saveErrors(request, errors);
				flag=false;
				subjectGroupDetailsForm.setFlag(flag);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			subjectGroupDetailsForm.setFlag(flag);
			subjectGroupDetailsForm.setSubjectList(subjectList);
			//Used to get subjectGroupMap for based on the class
			if (subjectGroupDetailsForm.getClassSchemewiseId() != null
							&& subjectGroupDetailsForm.getClassSchemewiseId().length() != 0) {
				List<SubjectGroupDetailsTo>	subjectGroupMap= subjectHandler.getSubjectGroupsId(subjectGroupDetailsForm);
				subjectGroupDetailsForm.setSubjectGroupMap(subjectGroupMap);
			}
			setClassMapToRequest(request,subjectGroupDetailsForm);
			setDatatoForm1(subjectGroupDetailsForm);
			//set specialization data to form
			setSpecializationDataToForm(subjectGroupDetailsForm);
			//
		} catch (Exception exception) {
			log.error("Error occured in getStudentDetails of SubjectGroupDetailsAction",exception);
			String msg = super.handleApplicationException(exception);
			subjectGroupDetailsForm.setErrorMessage(msg);
			subjectGroupDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		//setClassMapToRequest(request, subjectGroupDetailsForm);
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
	}
	
	
	/**This method is used to get the Subject Group Names.
	 * @param subjectGroupDetailsForm
	 * @throws Exception
	 */
	private void setDatatoForm1(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception {
		log.info("call of setDatatoForm1 method in SubjectGroupDetailsAction class.");
	
		List<SubjectGroupDetailsTo> subjectGroupNameTo = SubjectGroupDetailsHandler.getInstance()
				.getSubjectGroupNames(subjectGroupDetailsForm);
		//setting list to form.
		subjectGroupDetailsForm.setSubjectGroupList(subjectGroupNameTo);
		log.info("end of setDatatoForm method in SubjectGroupDetailsAction class.");	
		
	}

	/**
	 * Maps to exception/error page
	 * 
	 * @param mapping
	 * @param feeGroupEntryForm
	 * @param messages
	 * @param exception
	 * @return
	 * @throws Exception
	 */
	private ActionForward addExceptionMessage(ActionMapping mapping,
			SubjectGroupDetailsForm subjectGroupDetailsForm,
			ActionMessages messages, Exception exception) throws Exception {
		if (exception instanceof BusinessException) {
			String msgKey = super.handleBusinessException(exception);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			subjectGroupDetailsForm.setErrorMessage(msg);
			subjectGroupDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} else {
			throw exception;
		}
	}
	/**
	 * 
	 * @param request
	 * @param SubjectGroupDetailsForm
	 * Sets all the classes for the current year in request scope
	 * @throws Exception
	 */

	public void setClassMapToRequest(HttpServletRequest request,
			SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception {
		log
				.info("Entering into setClassMapToRequest of SubjectGroupDetailsAction");
		try {
			if (subjectGroupDetailsForm.getYear() == null || subjectGroupDetailsForm.getYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);

				// code by hari
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}// end
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
				request.setAttribute(CLASSMAP, classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(subjectGroupDetailsForm.getYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
				request.setAttribute(CLASSMAP, classMap);
			}

		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in SubjectGroupDetailsAction",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of SubjectGroupDetailsAction");
	}
	/**This method is used to edit Subject Groups.
	 * Edit Student details 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editSubjectGroups(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into editSubjectGroups of SubjectGroupDetailsAction");	
 		SubjectGroupDetailsForm subjectGroupDetailsForm = (SubjectGroupDetailsForm) form;
 		ActionErrors errors = new ActionErrors();
		
		boolean flag1=true;
		try {
			if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty() && subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
				saveErrors(request, errors);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				subjectGroupDetailsForm.clearList();
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			if(subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
				saveErrors(request, errors);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				subjectGroupDetailsForm.clearList();
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			
			if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.required"));
				saveErrors(request, errors);
				setClassMapToRequest(request,subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			}
			
			SubjectGroupDetailsHandler subjectGroupDetailsHandler = SubjectGroupDetailsHandler.getInstance();
			List<SubjectGroupDetailsTo> subjectGroup=subjectGroupDetailsHandler.getEditSubjectGroups(subjectGroupDetailsForm);
			//specializationMap
			 Map<Integer,String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationBySubGrpWithoutCommSubjAndSecndLang(subjectGroupDetailsForm.getSubjectGroupId());
			subjectGroupDetailsForm.setSpecializationMap1(specializationMap);
			
			//
			subjectGroupDetailsForm.setFlag1(flag1);
			subjectGroupDetailsForm.setSubjectList(subjectGroup);
			subjectGroupDetailsForm.setDisplayMessage(null);
			subjectGroupDetailsForm.setDisplayMsg(null);
			subjectGroupDetailsForm.setSpecializationMessage(null);
			
			request.setAttribute(SUBJECTGROUP_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			//Used to get subjectGroupMap for based on the class
			if (subjectGroupDetailsForm.getClassSchemewiseId() != null
							&& subjectGroupDetailsForm.getClassSchemewiseId().length() != 0) {
				List<SubjectGroupDetailsTo>	subjectGroupMap= subjectGroupDetailsHandler.getSubjectGroupsId(subjectGroupDetailsForm);
				subjectGroupDetailsForm.setSubjectGroupMap(subjectGroupMap);
			}
		
		} catch (Exception exception) {
			log.error("Error occured in editSubjectGroups of SubjectGroupDetailsAction",exception);
			String msg = super.handleApplicationException(exception);
			subjectGroupDetailsForm.setErrorMessage(msg);
			subjectGroupDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setClassMapToRequest(request, subjectGroupDetailsForm);
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
	}
	/**This method is used to Update Subject Group Entries.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward updateSubjectGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("call of updateSubjectGroup method in SubjectGroupDetailsAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		SubjectGroupDetailsForm subjectGroupDetailsForm = (SubjectGroupDetailsForm) form;
		ActionErrors errors = subjectGroupDetailsForm.validate(mapping, request);
		subjectGroupDetailsForm.setList(null);
		subjectGroupDetailsForm.setSpecializationStuList(null);
		if (errors.isEmpty()) {
			try {
				if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty() && subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					subjectGroupDetailsForm.clearList();
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				if(subjectGroupDetailsForm.getClassSchemewiseId() == null || subjectGroupDetailsForm.getClassSchemewiseId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.class"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					subjectGroupDetailsForm.clearList();
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				
				if(subjectGroupDetailsForm.getSubjectGroupId() == null || subjectGroupDetailsForm.getSubjectGroupId().isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.required"));
					saveErrors(request, errors);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				
				setUserId(request, subjectGroupDetailsForm);	
				subjectGroupDetailsForm.setDisplayMessage(null);
				subjectGroupDetailsForm.setDisplayMsg(null);
				subjectGroupDetailsForm.setSpecializationMessage(null);
				subjectGroupDetailsForm.setSpecializationMap1(null);
				boolean isUpdated=SubjectGroupDetailsHandler.getInstance().updateSubjectGroups(subjectGroupDetailsForm);
				
				//Used to get subjectGroupMap for based on the class
				if (subjectGroupDetailsForm.getClassSchemewiseId() != null
								&& subjectGroupDetailsForm.getClassSchemewiseId().length() != 0) {

					List<SubjectGroupDetailsTo>	subjectGroupMap= SubjectGroupDetailsHandler.getInstance().getSubjectGroupsId(subjectGroupDetailsForm);
					subjectGroupDetailsForm.setSubjectGroupMap(subjectGroupMap);
				} 
				//
				if(subjectGroupDetailsForm.getList()!=null && !subjectGroupDetailsForm.getList().isEmpty()){
					List<String> detailsForms = subjectGroupDetailsForm.getList();
					StringBuilder s = new StringBuilder();
					Iterator<String> iterator = detailsForms.iterator();
					while (iterator.hasNext()) {
						String string = (String) iterator.next();
						s.append(string).append(",");
					}
					subjectGroupDetailsForm.setDisplayMessage("Subject Group can not be Updated for "+s);
					subjectGroupDetailsForm.setDisplayMsg("Please check the Second Language for the above register numbers");
					setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					request.setAttribute(SUBJECTGROUP_ENTRY_OPERATION,
							CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				}
				// Specialization
				if(subjectGroupDetailsForm.getSpecializationStuList()!=null && !subjectGroupDetailsForm.getSpecializationStuList().isEmpty()){
					List<String> list = subjectGroupDetailsForm.getSpecializationStuList();
					StringBuilder s = new StringBuilder();
					Iterator<String> iterator = list.iterator();
					while (iterator.hasNext()) {
						String string = (String) iterator.next();
						s.append(string).append(",");
					}
					subjectGroupDetailsForm.setSpecializationMessage("This Specialization is already assigned for Register numbers(s)"+s);
					/*setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);*/
				}
				subjectGroupDetailsForm.setSubjectList(null);
				List<SubjectGroupDetailsTo> subjectList = SubjectGroupDetailsHandler.getInstance().getStudentDetails(subjectGroupDetailsForm);
				subjectGroupDetailsForm.setSubjectList(subjectList);
				subjectGroupDetailsForm.setFlag1(false);
				subjectGroupDetailsForm.setSubjectGroupId(null);
				subjectGroupDetailsForm.setSpecializationId(null);
				if(isUpdated){
					
					message = new ActionMessage("knowledgepro.attendance.subjectgroup.name.update.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					setClassMapToRequest(request,subjectGroupDetailsForm);
					setDatatoForm1(subjectGroupDetailsForm);
					setSpecializationDataToForm(subjectGroupDetailsForm);
					return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
				} 
				setClassMapToRequest(request,subjectGroupDetailsForm);
				setDatatoForm1(subjectGroupDetailsForm);
				setSpecializationDataToForm(subjectGroupDetailsForm);
			} catch (DuplicateException duplicateException) {
				errors.add("error", new ActionError(
						"knowledgepro.attendance.subjectgroup.name.exists"));
				saveErrors(request, errors);
				setDatatoForm(subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			} catch (ReActivateException reActivateException) {
				errors
						.add("error",new ActionError("knowledgepro.attendance.subjectgroup.name.alreadyexist.reactivate"));
				saveErrors(request, errors);

				setDatatoForm(subjectGroupDetailsForm);
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
			} catch (Exception exception) {

				return addExceptionMessage(mapping, subjectGroupDetailsForm,
						messages, exception);
			}
		} else {
			addErrors(request, errors);
			request.setAttribute(SUBJECTGROUP_ENTRY_OPERATION,
					CMSConstants.EDIT_OPERATION);
			setDatatoForm(subjectGroupDetailsForm);
			log.info("end of updateSubjectGroup method in SubjectGroupDetailsAction class.");
			return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
		}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_DETAILS_INIT);
	}
	/** set Specialization Data to Form
	 * @param subjectGroupDetailsForm 
	 * 
	 */
	private void setSpecializationDataToForm(SubjectGroupDetailsForm subjectGroupDetailsForm) throws Exception{
		List<ExamSpecializationTO> specializationTOs = SubjectGroupDetailsHandler.getInstance().getSpecializationData(subjectGroupDetailsForm);
		subjectGroupDetailsForm.setExamSpecializationTO(specializationTOs);
	}
}
