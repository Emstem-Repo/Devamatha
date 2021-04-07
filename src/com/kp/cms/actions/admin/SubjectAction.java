package com.kp.cms.actions.admin;

import java.util.ArrayList;
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
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SubjectEntryForm;
import com.kp.cms.handlers.admin.SubjectHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.transactions.admin.ISubjectTransaction;
import com.kp.cms.transactionsimpl.admin.SubjectTransactionImp;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class SubjectAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SubjectAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of subjects from DB
	 * @throws Exception
	 */

	public ActionForward initSubjectEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		try {
			subjectEntryForm.reset(mapping, request);
			subjectEntryForm.setSchemeNo(Integer.toString(0));
			List<SubjectTO> subjectList = SubjectHandler.getInstance()
					.getSubjectList(subjectEntryForm.getSchemeNo());// loading subjects from database
			subjectEntryForm.setSubjectList(subjectList);// setting loaded list
			// to ActionForm
			 //department 
			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
					.getInstance().getMajorDeptCodeList());
			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
					.getSubjectTypeList());
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  		    subjectEntryForm.setDepartmentMap(departmentMap);
		//	initsetDataToForm(subjectEntryForm, request);  // getting department
  			 subjectEntryForm.setCourseMap(SubjectHandler.getInstance().getEligibleCourseMap());
  			 //code added by mehaboob to get subjectCodeGroup Map Start
  			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
  			 List<String> subjectCodeArray=SubjectHandler.getInstance().getSubjectCodes();
  			 if(subjectCodeArray!=null && !subjectCodeArray.isEmpty()){
  				 String subjectCode="";
  				 for (String string : subjectCodeArray) {
  					subjectCode=subjectCode+", "+string;
				}
  				subjectCode=subjectCode.substring(1);
  				 subjectEntryForm.setSubjectCodes(subjectCode);
  			 }
  			 List<String> subjectNameList=SubjectHandler.getInstance().getSubjectNameList();
  			 if(subjectNameList!=null && !subjectNameList.isEmpty()){
  				 String subjectName="";
  				 for (String string : subjectNameList) {
  					subjectName=subjectName+", "+string;
				}
  				 subjectName= subjectName.substring(1);
  				 subjectEntryForm.setSubjectNames(subjectName);
  			 }
  			 //end
		} catch (Exception e) {
			log.error("error in loading subject...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @Adds the a new subject according to user.
	 * @returns an updated list of Active subjects in DB
	 * @throws Exception
	 */

	public ActionForward addSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		List<SubjectTO> subjectList = null;
		ActionMessages messages = new ActionMessages();
		boolean isSubjectAdded = false;
		try {
			setUserId(request, subjectEntryForm);
			if (isCancelled(request)) {
				subjectEntryForm.setCode("");
				subjectEntryForm.setName("");
				subjectEntryForm.setTotalmarks("");
				subjectEntryForm.setPassingmarks("");
				subjectEntryForm.setSecondlanguage("");
				subjectEntryForm.setOptional("");
				subjectEntryForm.setSubjectTypeId("");
				subjectEntryForm.setTheoryPractical("");
				subjectEntryForm.setConsMCSName("");
				subjectEntryForm.setSubjectNameprefix("");
				subjectEntryForm.setQuestionbyrequired("true");
				subjectEntryForm.setHourpersem("");
				subjectEntryForm.setSchemeNo(Integer.toString(0));
				subjectEntryForm.setConsolidatedSubjectStreamId("");
				//initsetDataToForm(subjectEntryForm, request);
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  			subjectEntryForm.setDepartmentMap(departmentMap);
				subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
				subjectEntryForm.setSubjectList(subjectList);
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			}
			String consMCSName = "";
			if (subjectEntryForm.getConsMCSName().length() > 0) {
				consMCSName = subjectEntryForm.getConsMCSName().trim();
			} else {
				consMCSName = subjectEntryForm.getName().trim();
			}
			String subjectNameprefix = subjectEntryForm
			.getSubjectNameprefix().trim();
			
			ActionErrors errors = subjectEntryForm.validate(mapping, request);// validation
			// is called
			if (errors.isEmpty()) {
				String subjectcode = subjectEntryForm.getCode();
				String subjectName = subjectEntryForm.getName();
				String theoryOrPractical=null;
				if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("T")){
					theoryOrPractical="Theory";
				}else if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("P")){
					theoryOrPractical="Practical";
				}else if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("B")){
					theoryOrPractical="Both";
				}
				// String totalmarks = subjectEntryForm.getTotalmarks();
				// String passingmarks = subjectEntryForm.getPassingmarks();
				String optional = subjectEntryForm.getOptional();
				String secondlanguage = subjectEntryForm.getSecondlanguage();
				String userId = subjectEntryForm.getUserId();
				//start  by giri duplicate checking theory or practical along subject code, subject name 
				//Subject subject = SubjectHandler.getInstance().existanceCheck(subjectcode,subjectName,subjectEntryForm.getTheoryPractical());
				Subject subject=SubjectHandler.getInstance().subjectCodeDuplicate(subjectcode);
				//end ny giri
				// Checking given code in database
				String subjectTypeId = subjectEntryForm.getSubjectTypeId();
				String hourpersem = subjectEntryForm.getHourpersem();
				String questionbyrequired = subjectEntryForm.getQuestionbyrequired();
				String theoryPractical = subjectEntryForm.getTheoryPractical();
                String isAdditionalSubject = subjectEntryForm.getIsAdditionalSubject();
                String cocurricularsubject = subjectEntryForm.getCoCurricularSubject();
                int departmentId=Integer.parseInt(subjectEntryForm.getDepartmentId());
                int schemeNo=Integer.parseInt(subjectEntryForm.getSchemeNo());
                CertificateCourse certificateCourse=null;
                String isExternalOrInternalSubject=null;
                if(subjectEntryForm.getSubjectTypeBasedOnMarks()!=null && !subjectEntryForm.getSubjectTypeBasedOnMarks().equalsIgnoreCase(""))
                	isExternalOrInternalSubject =subjectEntryForm.getSubjectTypeBasedOnMarks();
                else
                	isExternalOrInternalSubject="both";
                String consolidatedSubjectStreamId=subjectEntryForm.getConsolidatedSubjectStreamId();
                boolean isCertificate=subjectEntryForm.getIsCertificateCourse().equalsIgnoreCase("Yes")?true:false;
//				if(isCertificateCourse.equalsIgnoreCase("yes")){
//					if(subjectEntryForm.getCertificateId()!=null && !subjectEntryForm.getCertificateId().isEmpty()){
//						certificateCourse=new CertificateCourse();
//						certificateCourse.setId(Integer.parseInt(subjectEntryForm.getCertificateId()));
//					}
//					/*else{
//						errors.add("error", new ActionError("knowledgepro.admisn.subject.certificateCourse"));
//						saveErrors(request,errors);
//						request.setAttribute("Update", "valid");
//					}*/
//				}
				String majorDepartmentCodeId = subjectEntryForm
						.getMajorDepartmentCodeId();
				if (subjectEntryForm.getSecondlanguage() == null)// validation
				// for
				// second
				// language
				// required
				{
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.admin.subject.select.secondlanguage"));// Adding
					// error
					saveErrors(request, errors);
					request.setAttribute("Update", "valid");// setting update
					// attribute
				}
				if (subjectEntryForm.getOptional() == null)// validation for
				// optional
				// required.
				{
					errors.add("error", new ActionError(
							"knowledgepro.admin.subject.select.optional"));// Adding
					// error
					saveErrors(request, errors);
					request.setAttribute("Update", "valid");// setting update
					// attribute
				} else if ((optional != null) && (secondlanguage != null)) {
					if (optional.equalsIgnoreCase("Yes")
							&& (secondlanguage.equalsIgnoreCase("Yes"))) {// Subject
						// cannot
						// be
						// Optional
						// and
						// second
						// language
						errors
								.add(
										"error",
										new ActionError(
												"knowledgepro.admin.subject.cannot.optionalandsecond"));// Adding
						// error
						saveErrors(request, errors);
						request.setAttribute("Update", "valid");// setting
						// update
						// attribute
					}
				}
				if (subject != null && subject.getIsActive() != false) {// Code
					// already
					// exist
					// in DB
					errors.add("error", new ActionError(
							"knowledgepro.admin.subject.alreadyexists",
							subjectcode));// Add error
					saveErrors(request, errors);
					request.setAttribute("Update", "valid");// setting update
					// attribute
				} else if (subject != null && !subject.getIsActive()) {
					errors.add("error", new ActionError(
							"knowledgepro.admin.subject.reactivate",
							subjectcode));// Add error
					saveErrors(request, errors);
					request.setAttribute("Update", "valid");// setting update
					// attribute
				} else {

					if (errors.isEmpty()) {
						isSubjectAdded = SubjectHandler
						.getInstance()
						.addSubject(subjectcode, subjectName,
								secondlanguage, optional, userId,
								Integer.parseInt(subjectTypeId),theoryPractical, consMCSName,
								subjectNameprefix,
								majorDepartmentCodeId,isAdditionalSubject,cocurricularsubject ,subjectEntryForm.getIsCertificateCourse()
								,certificateCourse,hourpersem,questionbyrequired, schemeNo,
								departmentId,subjectEntryForm.getEligibleCourseId(),subjectEntryForm.getSubjectCodeGroup(),isExternalOrInternalSubject,Integer.parseInt(consolidatedSubjectStreamId));// Calling
						// handler
						// class
						// for
						// Add
						if (isSubjectAdded) {// Checking whether subject is
							// added
							ActionMessage message = new ActionMessage(
									"knowledgepro.admin.subject.addsuccess",
									subjectName, subjectcode);// Adding added
							// message.
							messages.add("messages", message);
							saveMessages(request, messages);
							subjectEntryForm.reset(mapping, request);
						} else {
							errors.add("error", new ActionError(
									"knowledgepro.admin.subject.addfailure",
									subjectName, subjectcode));// Adding failure
							// message
						}
					}
				}
			} else {
				saveErrors(request, errors);
				request.setAttribute("Update", "valid");// setting update
				// attribute
			}
			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
					.getInstance().getMajorDeptCodeList());
			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
					.getSubjectTypeList());
			subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());// loading
			// subjects
			// from
			// database
			subjectEntryForm.setSubjectList(subjectList);// setting loaded list
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  			subjectEntryForm.setDepartmentMap(departmentMap);
  		    //code added by mehaboob to get subjectCodeGroup Map Start
			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
			 //end
			//initsetDataToForm(subjectEntryForm, request); // getting certificate courses
			// to ActionForm
		} catch (Exception e) {
			log.error("error in Adding subject...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}

		}
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}

	/*
	 * @param mapping
	 * 
	 * @param form
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @loads the required subject record from DB.
	 * 
	 * @returns an updated list of Active subjects in DB
	 * 
	 * @throws Exception
	 */
	public ActionForward editSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		try {
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  			subjectEntryForm.setDepartmentMap(departmentMap);
			// subjectEntryForm =
			// (SubjectEntryForm)SubjectHandler.getInstance().getActionForm(subjectEntryForm);
			Boolean result = SubjectHandler.getInstance().getActionForm(subjectEntryForm);
			List<SubjectTO> subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());// getting the list from DB.
			subjectEntryForm.setCourseMap(SubjectHandler.getInstance().getEligibleCourseMap());
			//code added by mehaboob to get subjectCodeGroup Map Start
 			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
 			 setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
 			 //end
			if (result) {
				subjectEntryForm.setSubjectList(subjectList);
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
			}
  			 List<String> subjectCodeArray=SubjectHandler.getInstance().getSubjectCodes();
  			 if(subjectCodeArray!=null && !subjectCodeArray.isEmpty()){
  				 String subjectCode="";
  				 for (String string : subjectCodeArray) {
  					subjectCode=subjectCode+", "+string;
				}
  				subjectCode=subjectCode.substring(1);
  				 subjectEntryForm.setSubjectCodes(subjectCode);
  			 }
  			 List<String> subjectNameList=SubjectHandler.getInstance().getSubjectNameList();
  			 if(subjectNameList!=null && !subjectNameList.isEmpty()){
  				 String subjectName="";
  				 for (String string : subjectNameList) {
  					subjectName=subjectName+", "+string;
				}
  				 subjectName= subjectName.substring(1);
  				 subjectEntryForm.setSubjectNames(subjectName);
  			 }
			request.setAttribute("Update", "Update");// setting update attribute
			//initsetDataToForm(subjectEntryForm,request);
			// to request
		} catch (Exception e) {
			log.error("error in editing subject...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}

		}
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}

	/*
	 * @param mapping
	 * 
	 * @param form
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @Updates the records which are altered by User.
	 * 
	 * @returns an updated list of Active subjects in DB
	 * 
	 * @throws Exception
	 */
	public ActionForward updateSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		ActionMessages messages = new ActionMessages();

		List<SubjectTO> subjectList = null;
		String name = subjectEntryForm.getName();
		String code = subjectEntryForm.getCode();
		String theoryOrPractical=null;
		if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("T")){
			theoryOrPractical="Theory";
		}else if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("P")){
			theoryOrPractical="Practical";
		}else if(subjectEntryForm.getTheoryPractical().equalsIgnoreCase("B")){
			theoryOrPractical="Both";
		}
		try {
			setUserId(request, subjectEntryForm);
			if (isCancelled(request)) {
				Boolean result = SubjectHandler.getInstance().getActionForm(
						subjectEntryForm);
				if (result) {
					subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				}
				subjectEntryForm.setSubjectList(subjectList);
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
				request.setAttribute("Update", "Update");
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			}
			ActionErrors errors = subjectEntryForm.validate(mapping, request);

			boolean isUpdated = false;
			Subject subject = null;
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
				subjectEntryForm.setSubjectList(subjectList);
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  		    subjectEntryForm.setDepartmentMap(departmentMap);
				request.setAttribute("Update", "Update");
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			}

			/*
			 * if (Integer.parseInt(subjectEntryForm.getTotalmarks()) < Integer
			 * .parseInt(subjectEntryForm.getPassingmarks())) {// Validating
			 * whether total marks are lesser than pass mark errors.add("error",
			 * new
			 * ActionError("knowledgepro.admin.subject.totalmarks.greater"));//
			 * Adding error saveErrors(request, errors); subjectList =
			 * SubjectHandler.getInstance() .getSubjectList();
			 * subjectEntryForm.setSubjectList(subjectList);
			 * request.setAttribute("Update", "Update"); return
			 * mapping.findForward("subjectEntry"); }
			 */
			if ((subjectEntryForm.getOptional().equalsIgnoreCase("Yes") && (subjectEntryForm
					.getSecondlanguage().equalsIgnoreCase("Yes")))) {// Checking
				// whether a language is optional and second language
				errors.add("error", new ActionError(
						"knowledgepro.admin.subject.cannot.optionalandsecond"));// Adding
				saveErrors(request, errors);
				
				subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				subjectEntryForm.setSubjectList(subjectList);
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  			subjectEntryForm.setDepartmentMap(departmentMap);
				request.setAttribute("Update", "Update");
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			}
			Boolean subCodeChanged = false;
			if (!(subjectEntryForm.getEditedcode().trim().equalsIgnoreCase(subjectEntryForm.getCode().trim())) ||
					!(subjectEntryForm.getEditedname().trim().equalsIgnoreCase(subjectEntryForm.getName().trim())) ||
					!(subjectEntryForm.getEditTheoryOrPractical().trim().equalsIgnoreCase(subjectEntryForm.getTheoryPractical().trim()))) {
				subCodeChanged = true;
			}
			//start  by giri duplicate checking theory or practical along subject code, subject name 
			subject = SubjectHandler.getInstance().existanceCheck(subjectEntryForm.getCode(),subjectEntryForm.getName(),subjectEntryForm.getTheoryPractical());
			//end by giri
			// Checking subject code already
			// exists in DB or not

			if (subject != null && subject.getIsActive() && subCodeChanged) {// Code
				// already
				// exist
				// in
				// DB
				errors.add("error", new ActionError(
						"knowledgepro.admin.subject.alreadyexists", code,name,theoryOrPractical));// Add
				// error
				saveErrors(request, errors);
				subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				subjectEntryForm.setSubjectList(subjectList);
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  			subjectEntryForm.setDepartmentMap(departmentMap);
				request.setAttribute("Update", "Update");
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			} else if (subject != null && !subject.getIsActive()
					&& subCodeChanged) {
				errors.add("error", new ActionError(
						"knowledgepro.admin.subject.reactivate", code));// Add
				// error
				saveErrors(request, errors);
				subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
				subjectEntryForm.setSubjectList(subjectList);
				subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
						.getInstance().getMajorDeptCodeList());
				subjectEntryForm.setListSubjectType(SubjectHandler
						.getInstance().getSubjectTypeList());
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  			subjectEntryForm.setDepartmentMap(departmentMap);
				request.setAttribute("Update", "Update");
				setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
				return mapping.findForward("subjectEntry");
			}
            if(errors.isEmpty()){
			isUpdated = SubjectHandler.getInstance().updateSubject(
					subjectEntryForm);// performing the update operation

			if (isUpdated) {
				// success .
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.subject.updatesuccess", name, code);
				messages.add("messages", message);
				saveMessages(request, messages);
				request.setAttribute("Update", "add");
				subjectEntryForm.reset(mapping, request);
			} else {
				// failed
				errors
						.add("error", new ActionError(
								"knowledgepro.admin.subject.updatefailure",
								name, code));
				saveErrors(request, errors);
			}

			subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
			subjectEntryForm.setSubjectList(subjectList);
			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
					.getInstance().getMajorDeptCodeList());
			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
					.getSubjectTypeList());
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  			subjectEntryForm.setDepartmentMap(departmentMap);
			//initsetDataToForm(subjectEntryForm, request);
  		    //code added by mehaboob to get subjectCodeGroup Map Start
			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
			 //end

           }
            else {
	              saveErrors(request, errors);
	              request.setAttribute("Update", "Update");// setting update
	              // attribute
	              subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());
	  			subjectEntryForm.setSubjectList(subjectList);
	  			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
	  					.getInstance().getMajorDeptCodeList());
	  			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
	  					.getSubjectTypeList());
	  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
	  			subjectEntryForm.setDepartmentMap(departmentMap);
	  		//	initsetDataToForm(subjectEntryForm, request);
             }
		} catch (Exception e) {
			log.error("error in editing subject...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}

		}
		
		//request.setAttribute("Update", "Update");
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
		
	}

	/*
	 * @param mapping
	 * 
	 * @param form
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @Inactivate's the subject.
	 * 
	 * @returns an updated list of Active subjects in DB
	 * 
	 * @throws Exception
	 */
	public ActionForward deleteSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = subjectEntryForm.validate(mapping, request);// calling
		// validation
		// method
		boolean isDeleted = false;
		try {
			setUserId(request, subjectEntryForm);
			isDeleted = SubjectHandler.getInstance().deleteSubject(subjectEntryForm.getId(), subjectEntryForm.getUserId(),subjectEntryForm);// Deleting
			// the
			// selected
			// subject
			List<SubjectTO> subjectList = SubjectHandler.getInstance().getSubjectList(subjectEntryForm.getSchemeNo());// loading the updated subject list from
			// DB.
			subjectEntryForm.setSubjectList(subjectList);// Setting list to
			// Action Form.
			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
					.getInstance().getMajorDeptCodeList());
			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
					.getSubjectTypeList());
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  			subjectEntryForm.setDepartmentMap(departmentMap);
		//	initsetDataToForm(subjectEntryForm,request);
  		   //code added by mehaboob to get subjectCodeGroup Map Start
			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
			 //end
			if (isDeleted) {// Checking whether subject is deleted
				ActionMessage message = new ActionMessage(
						"knowledgepro.admin.subject.deleted");// Adding error
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectEntryForm.reset(mapping, request);
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.admin.subject.deletefailure"));// Adding
				// error
			}
		} catch (Exception e) {
			log.error("error in editing subject...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}

		}
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}

	/**
	 * this will reactivate subject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateSubjectEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = subjectEntryForm.validate(mapping, request);
		try {
			setUserId(request, subjectEntryForm);
			if (errors.isEmpty()) {
				// errors are empty
				boolean isActivated = SubjectHandler.getInstance().reActivateSubjectEntry(subjectEntryForm.getCode(),subjectEntryForm.getUserId(),subjectEntryForm);
				if (isActivated) {
					ActionMessage message = new ActionMessage(
							"knowledgrpro.admin.subject.reactivateSuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					subjectEntryForm.reset(mapping, request);
				} else {
					ActionMessage message = new ActionMessage(
							"knowledgepro.admin.guidelinesentry.reactivate.failed");
					messages.add("messages", message);
					saveMessages(request, messages);
					subjectEntryForm.reset(mapping, request);
				}
			} else {
				// errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log
					.error(
							"error in reActivateSubjectEntry of SubjectAction class...",
							e);
			if (e instanceof ApplicationException) {
				log
						.error(
								"error in reActivateSubjectEntry of SubjectAction class...",
								e);
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		List<SubjectTO> subjectList = SubjectHandler.getInstance()
				.getSubjectList(subjectEntryForm.getSchemeNo());// loading the updated subject list from DB.
		subjectEntryForm.setSubjectList(subjectList);// Setting list to
		subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
				.getInstance().getMajorDeptCodeList());
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
		subjectEntryForm.setDepartmentMap(departmentMap);
		subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
				.getSubjectTypeList());
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}
	
	/**
	 * @param subjectEntryForm
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void initsetDataToForm(SubjectEntryForm subjectEntryForm,HttpServletRequest request) throws Exception{
		
		// Code has Commented By Balaji
		
		/*Map<Integer,String> courseMap = setupCourseMapToRequest(subjectEntryForm,request);
		subjectEntryForm.setCourseMap(courseMap);*/
		
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
		subjectEntryForm.setDepartmentMap(departmentMap);
		
	}
	
	/**
	 * @param subjectEntryForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> setupCourseMapToRequest(SubjectEntryForm subjectEntryForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			String isCertificate=subjectEntryForm.getIsCertificateCourse();
			if(isCertificate.equalsIgnoreCase("yes"))
			{
//				CertificateCourse certificateCourse=new CertificateCourse();
//				if(subjectEntryForm.getCertificateId()!=null && !subjectEntryForm.getCertificateId().isEmpty()){
//				certificateCourse.setId(Integer.parseInt(subjectEntryForm.getCertificateId()));
//				if(certificateCourse!=null){
//					currentYear=SubjectHandler.getInstance().getYear(Integer.parseInt(subjectEntryForm.getCertificateId()));
//					subjectEntryForm.setAcademicYear(Integer.toString(currentYear));
//				}}
				
			}
			else if(subjectEntryForm.getAcademicYear()!=null && !subjectEntryForm.getAcademicYear().isEmpty()){
				currentYear=Integer.parseInt(subjectEntryForm.getAcademicYear());
			}
			else{
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				 if(year!=0){
					currentYear=year;
				}
			}
			courseMap = CommonAjaxHandler.getInstance().getCoursesByYear(currentYear);
			request.setAttribute("courseMap", courseMap);
			return courseMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpCourseMapToRequest of FeeAdditionalAction");
		}
		return courseMap;
	}
	public ActionForward getSubjectEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SubjectEntryForm subjectEntryForm = (SubjectEntryForm) form;
		try {
			List<SubjectTO> subjectList = SubjectHandler.getInstance()
					.getSubjectList(subjectEntryForm.getSchemeNo());// loading subjects from database
			subjectEntryForm.setSubjectList(subjectList);// setting loaded list
			// to ActionForm
			 //department 
			subjectEntryForm.setListMajorDepartmentCode(SubjectHandler
					.getInstance().getMajorDeptCodeList());
			subjectEntryForm.setListSubjectType(SubjectHandler.getInstance()
					.getSubjectTypeList());
  			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
  			Map<String,String> departmentMap=subjectIntf.getDepartmentMap();
  			subjectEntryForm.setDepartmentMap(departmentMap);
  			//added by mahi start
  			 subjectEntryForm.setSubjectCodeGroupMap(SubjectHandler.getInstance().getSubjectCodeGroupMap());
  			 List<String> subjectCodeArray=SubjectHandler.getInstance().getSubjectCodes();
  			 if(subjectCodeArray!=null && !subjectCodeArray.isEmpty()){
  				 String subjectCode="";
  				 for (String string : subjectCodeArray) {
  					subjectCode=subjectCode+", "+string;
				}
  				subjectCode=subjectCode.substring(1);
  				 subjectEntryForm.setSubjectCodes(subjectCode);
  			 }
  			 List<String> subjectNameList=SubjectHandler.getInstance().getSubjectNameList();
  			 if(subjectNameList!=null && !subjectNameList.isEmpty()){
  				 String subjectName="";
  				 for (String string : subjectNameList) {
  					subjectName=subjectName+", "+string;
				}
  				 subjectName= subjectName.substring(1);
  				 subjectEntryForm.setSubjectNames(subjectName);
  			 }
  			 //end
		//	initsetDataToForm(subjectEntryForm, request);  // getting department
  			if(subjectEntryForm.getId()>0){
 			request.setAttribute("Update","Update");
 			 }
		} catch (Exception e) {
			log.error("error in loading subject...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				subjectEntryForm.setErrorMessage(msg);
				subjectEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setSubjectStremsForConsolidatedMarksCard(subjectEntryForm);
		return mapping.findForward("subjectEntry");
	}
	
	// by Arun Sudhakaran
	private void setSubjectStremsForConsolidatedMarksCard(SubjectEntryForm subjectEntryForm)
	{
		List<ConsolidatedSubjectStream> subjectStreams = null;
		try
		{
			subjectStreams = SubjectHandler.getInstance().getSubjectStreamsForConsolidatedMarksCard(subjectEntryForm);
		}
		catch(Exception ex)
		{
			
		}		
		Map<Integer, String> streamMap = new HashMap<Integer, String>();
		if(subjectStreams != null && !subjectStreams.isEmpty()) 
		{			
			Iterator<ConsolidatedSubjectStream> it = subjectStreams.iterator();
			while(it.hasNext())
			{
				ConsolidatedSubjectStream subjectStream = it.next();
				streamMap.put(subjectStream.getId(), subjectStream.getStreamName());
			}			
		}
		subjectEntryForm.setConsolidatedSubjectStreams(streamMap);
	}
}
