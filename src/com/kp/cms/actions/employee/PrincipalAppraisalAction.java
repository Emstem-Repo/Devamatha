package com.kp.cms.actions.employee;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.EmpAppraisalDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.PrincipalAppraisalForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.PrincipalAppraisalHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.AppraisalsTO;
import com.kp.cms.to.employee.EmpAttributeTO;

@SuppressWarnings("deprecation")
public class PrincipalAppraisalAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PrincipalAppraisalAction.class);
	/**
	 * Initializes Principal Appraisal
	 * With all the departments
	 * and employee attributes
	 */
	public ActionForward initPrincipalAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initPrincipalAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		try {
			setAllDepartmentToForm(appraisalForm);
			setAllEmpAttributesToForm(appraisalForm);
			appraisalForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initPrincipalAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initPrincipalAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_PRINCIPAL_APPRAISAL_PAGE);
	}
	
	/**
	 * Used to get all departments
	 */
	public void setAllDepartmentToForm(PrincipalAppraisalForm appraisalForm)throws Exception {
		log.info("entering into setAllDepartmentToForm PrincipalAppraisalAction");
		List<SingleFieldMasterTO> departmentList = PrincipalAppraisalHandler.getInstance().getAllDepartments();
		appraisalForm.setDepartmentList(departmentList);
		log.info("Leaving into setAllDepartmentToForm PrincipalAppraisalAction");
	}
	/**
	 * Used to get all Employee Attributes
	 */
	public void setAllEmpAttributesToForm(PrincipalAppraisalForm appraisalForm)throws Exception {
		log.info("entering into setAllDepartmentToForm PrincipalAppraisalAction");
		boolean isEmployee = true;
		List<EmpAttributeTO>attributeList = PrincipalAppraisalHandler.getInstance().getAllAttributes(isEmployee);
		appraisalForm.setAttriButeList(attributeList);
		
		log.info("Leaving into setAllDepartmentToForm PrincipalAppraisalAction");
	}
	
	
	/**
	 * Submits the princiapl appraisal
	 * 
	 */
	public ActionForward submitPrincipalAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitPrincipalAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		ActionMessages message = new ActionMessages();
		ActionErrors errors = appraisalForm.validate(mapping, request);
		errors = validateAttributeAndValues(appraisalForm, errors);
		errors = validateRecommendation(appraisalForm, errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, appraisalForm);
				boolean isAppraisalSuccess;
				boolean isPrincipal = true;
				HttpSession session = request.getSession(false);
				String loginUserEmpId = "";
				if(session.getAttribute("employeeId")!=null){
					loginUserEmpId = (String) session.getAttribute("employeeId");
				}
				isAppraisalSuccess = PrincipalAppraisalHandler.getInstance().submitAppraisal(appraisalForm, isPrincipal, loginUserEmpId);
				if(isAppraisalSuccess){
					message.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PRINCIAPL_APPRAISAL_COMPLETED_SUCCESS));
					addMessages(request, message);
					setAllDepartmentToForm(appraisalForm);
					setAllEmpAttributesToForm(appraisalForm);
					appraisalForm.clear();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PRINCIAPL_APPRAISAL_COMPLETED_FAILED));
				}
			}
		} catch (Exception e) {
			log.error("Error occured in submitPrincipalAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		setEmployeeMapToRequest(appraisalForm, request);
		log.info("Leaving into submitPrincipalAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_PRINCIPAL_APPRAISAL_PAGE);
	}
	
	private ActionErrors validateRecommendation(PrincipalAppraisalForm appraisalForm, ActionErrors errors)throws Exception {
		if(appraisalForm.getRecommendation()!=null && appraisalForm.getRecommendation().length() > 250){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.tc.recommendation"));
		}
		return errors;
	}

	/**
	 * Set employee map to request
	 */
	public void setEmployeeMapToRequest(PrincipalAppraisalForm appraisalForm,HttpServletRequest request){
		log.info("entering into setEmployeeMapToRequest PrincipalAppraisalAction");
		Map<Integer,String> employeeMap = new HashMap<Integer,String>();
		if(appraisalForm.getDepartmentId()!=null && !StringUtils.isEmpty(appraisalForm.getDepartmentId())){
			employeeMap = CommonAjaxHandler.getInstance().getEmployeesByDepartment(Integer.parseInt(appraisalForm.getDepartmentId()));
		}
		log.info("Leaving into setEmployeeMapToRequest PrincipalAppraisalAction");
		request.setAttribute("employeeMap", employeeMap);
	}
	
	/**
	 * 
	 * @param appraisalForm
	 * @param errors
	 * @return errors after validating the values of the attribute
	 * @throws Exception
	 */
	public ActionErrors validateAttributeAndValues(PrincipalAppraisalForm appraisalForm, ActionErrors errors)throws Exception{
		log.info("entering into validateAttributeValues PrincipalAppraisalAction");
		if(appraisalForm.getAttriButeList() != null && !appraisalForm.getAttriButeList().isEmpty()){
			Iterator<EmpAttributeTO> attributeItr = appraisalForm.getAttriButeList().iterator();
			while (attributeItr.hasNext()) {
				EmpAttributeTO empAttributeTO = attributeItr.next();
				if(empAttributeTO.getValue() == null || StringUtils.isEmpty(empAttributeTO.getValue())){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PRINCIAPL_APPRAISAL_ATTRIBUTE_VALUE_REQUIRED));
				}
			}
		}
		else{
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PRINCIAPL_APPRAISAL_ATTRIBUTE_EMPTY));
		}
		log.info("Leaving into validateAttributeValues PrincipalAppraisalAction");
		return errors;
	}
	/**
	 * Initializes HOD Appraisal
	 * and employee attributes
	 */
	public ActionForward initHODAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initHODAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		try {
			setAllEmpAttributesToForm(appraisalForm);
			appraisalForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initHODAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initHODAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_HOD_APPRAISAL_PAGE);
	}
	/**
	 * Submit the HOD appraisal for an employee
	 * 
	 */
	public ActionForward submitHODAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitHODAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		ActionErrors errors = new ActionErrors();
		errors = validateAttributeAndValues(appraisalForm, errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, appraisalForm);				
			}
		} catch (Exception e) {
			log.error("Error occured in submitHODAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into submitHODAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_HOD_APPRAISAL_PAGE);
	}
	/**
	 * Initializes Student Appraisal
	 * and employee attributes
	 */
	public ActionForward initStudentAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into initStudentAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		try {
			setAllStudentAttributesToForm(appraisalForm);
			appraisalForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initStudentAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initStudentAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_STUDENT_APPRAISAL_PAGE);
	}
	/**
	 * Used to get all Student Attributes
	 */
	public void setAllStudentAttributesToForm(PrincipalAppraisalForm appraisalForm)throws Exception {
		log.info("entering into setAllStudentAttributesToForm PrincipalAppraisalAction");
		boolean isEmployee = false;
		List<EmpAttributeTO>attributeList = PrincipalAppraisalHandler.getInstance().getAllAttributes(isEmployee);
		appraisalForm.setAttriButeList(attributeList);
		log.info("Leaving into setAllStudentAttributesToForm PrincipalAppraisalAction");
	}
	/**
	 * Submit the Student appraisal for an employee
	 * 
	 */
	public ActionForward submitStudentAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering into submitStudentAppraisal PrincipalAppraisalAction");
		PrincipalAppraisalForm appraisalForm = (PrincipalAppraisalForm)form;
		ActionErrors errors = new ActionErrors();
		errors = validateAttributeAndValues(appraisalForm, errors);
		try {
			if (errors.isEmpty()) {
				setUserId(request, appraisalForm);				
			}
		} catch (Exception e) {
			log.error("Error occured in submitStudentAppraisal of PrincipalAppraisalAction", e);
			String msg = super.handleApplicationException(e);
			appraisalForm.setErrorMessage(msg);
			appraisalForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		addErrors(request, errors);
		log.info("Leaving into submitStudentAppraisal PrincipalAppraisalAction");
		return mapping.findForward(CMSConstants.INIT_STUDENT_APPRAISAL_PAGE);
	}
public ActionForward initViewAppraisal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		List <AppraisalsTO> appraisalList=null;
		PrincipalAppraisalForm paForm=(PrincipalAppraisalForm)form;
		try{
			appraisalList=PrincipalAppraisalHandler.getInstance().getAppraisalsDetails();
			paForm.setAppraisalTO(appraisalList);
			HttpSession session=request.getSession();
			session.setAttribute("appraisalTO", appraisalList);
			
		}catch(Exception e){
			log.error("Error occured in initViewAppraisal of PrincipalAppraisalAction", e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		return mapping.findForward(CMSConstants.INIT_VIEW_APPRAISAL);
	}
	
	public ActionForward viewAppraisalDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PrincipalAppraisalForm pincipalAppraise=(PrincipalAppraisalForm)form;
		try{
			setAllEmpAttributesToForm(pincipalAppraise);
			AppraisalsTO appraisals=PrincipalAppraisalHandler.getInstance().getAppraisalDetails(pincipalAppraise);
			pincipalAppraise.setAppraiseTO(appraisals);
			HttpSession session=request.getSession();
			session.setAttribute("appraiseTO", appraisals);
			//pincipalAppraise.setAppraiseId(appraisals.getAppraisalId());
			Set<EmpAppraisalDetails> appraisalDetails=appraisals.getEmpAppraisalDetails();
			pincipalAppraise.setAppraisalDetails(appraisalDetails);
			session.setAttribute("appraisalDetails", appraisalDetails);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error occured in ViewAppraisalDetails of PrincipalAppraisalAction", e);
		}
		return mapping.findForward(CMSConstants.VIEW_APPRAISAL_DETAILS);
	}

}
