package com.kp.cms.actions.admission;

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
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.UncheckGeneratedSmartCardHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admission.IGensmartCardDataTransaction;
import com.kp.cms.transactionsimpl.admission.GensmartCardDataTransactionimpl;

public class UncheckGeneratedSmartCardAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UncheckGeneratedSmartCardAction.class);
	/**
	 * Method to set the required data to the form to display it in UncheckGensmartCardDataInit.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUncheckGeneratedSmartCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Uncheck Generated Smart Card Batch input");
		UncheckGeneratedSmartCardForm uncheckGenSCForm = (UncheckGeneratedSmartCardForm) form;
		uncheckGenSCForm.resetFields();
		setRequiredDatatoForm(uncheckGenSCForm, request);
		log.info("Exit Init Uncheck Generated Smart Card input");
		
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD);
	}

	/**
	 * setting the required data to form
	 * @param uncheckGenSCForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(UncheckGeneratedSmartCardForm uncheckGenSCForm,HttpServletRequest request) throws Exception  {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler .getInstance().getProgramType();
		uncheckGenSCForm.setProgramTypeList(programTypeList);		
	}

	/**
	 * getting the students data for the selected course
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentsDataToUncheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered UncheckGeneratedSmartCardAction - getStudentsData");
		
		UncheckGeneratedSmartCardForm uncheckForm = (UncheckGeneratedSmartCardForm) form;
		ActionErrors errors = uncheckForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentTO> stuToList= UncheckGeneratedSmartCardHandler.getInstance().getStudentList(uncheckForm);
				if (stuToList.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					setRequiredDatatoForm(uncheckForm, request);
					log.info("Exit UncheckGeneratedSmartCardAction - getStudentsData size 0");
					return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD);
				} 
				setNamesToForm(uncheckForm);
				uncheckForm.setGeneratedStudentList(stuToList);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uncheckForm.setErrorMessage(msg);
				uncheckForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(uncheckForm, request);
			log.info("Exit UncheckGeneratedSmartCardAction - getStudentsData errors not empty ");
			return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD);
		}
		
		log.info("Exit UncheckGeneratedSmartCardAction - getStudentsData");
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_LIST);
	}

	/**
	 * sets properties from UncheckGensmartCardDataInit.jsp to the second/result jsp
	 * @param uncheckForm
	 * @throws Exception
	 */
	private void setNamesToForm(UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
		if(uncheckForm.getProgramTypeId()!=null && !uncheckForm.getProgramTypeId().isEmpty()){
			uncheckForm.setProgramType(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(uncheckForm.getProgramTypeId()),"ProgramType", true, "name"));
		}
		if(uncheckForm.getProgramId()!=null && !uncheckForm.getProgramId().isEmpty()){
			uncheckForm.setProgram(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(uncheckForm.getProgramId()),"Program", true, "name"));
		}
		if(uncheckForm.getCourseId()!=null && !uncheckForm.getCourseId().isEmpty()){
			uncheckForm.setCourse(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(uncheckForm.getCourseId()),"Course", true, "name"));
		}
		if(uncheckForm.getDepartmentId()!=null && !uncheckForm.getDepartmentId().isEmpty()){
			uncheckForm.setDepartmentName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(uncheckForm.getDepartmentId()),"Department", true, "name"));
		}
		
	}
	
	/**
	 * selected list of students SC data generated flag is being changed to false
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uncheckGeneratedFlag(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered UncheckGeneratedSmartCardAction - uncheckGeneratedFlag");
		
		UncheckGeneratedSmartCardForm uncheckForm = (UncheckGeneratedSmartCardForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,uncheckForm);
		boolean setFlag=false;
			try {
				boolean contains= validateMethod(uncheckForm);
				if (!contains) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckFlag.notSelected"));
					saveErrors(request, errors);
					log.info("Exit UncheckGeneratedSmartCardAction - no students checked");
					return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_LIST);
				} 
				setFlag = UncheckGeneratedSmartCardHandler.getInstance().updateGeneratedFlag(uncheckForm.getGeneratedStudentList(),uncheckForm);
				if(setFlag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.uncheckFlag.successful"));
					saveMessages(request, messages);
					log.info("Exit UncheckGeneratedSmartCardAction - uncheckGeneratedFlag");
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckFlag.failure"));
					saveErrors(request, errors);
					log.info("Exit UncheckGeneratedSmartCardAction - update failure");
				}
				uncheckForm.resetFields();
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uncheckForm.setErrorMessage(msg);
				uncheckForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD);
}

	/**
	 * Validating the list to check if atleast one student is been selected
	 * @param uncheckForm
	 * @throws Exception
	 */
	private boolean validateMethod(UncheckGeneratedSmartCardForm uncheckForm) throws Exception{
		// TODO Auto-generated method
		boolean contains=false;
		if(uncheckForm.getGeneratedStudentList()!=null && !uncheckForm.getGeneratedStudentList().isEmpty()){
		List<StudentTO> toList=uncheckForm.getGeneratedStudentList();
		Iterator<StudentTO> itr=toList.iterator();
		while (itr.hasNext()) {
			StudentTO studentTO = (StudentTO) itr.next();
			if(studentTO.getChecked1()!=null && studentTO.getChecked1().equalsIgnoreCase("On")){
				contains=true;
			}
		}
		}
		else if(uncheckForm.getGeneratedEmployeeList()!=null && !uncheckForm.getGeneratedEmployeeList().isEmpty()){
			List<EmployeeTO> empList=uncheckForm.getGeneratedEmployeeList();
			Iterator<EmployeeTO> itrEmp=empList.iterator();
			while (itrEmp.hasNext()) {
				EmployeeTO employeeTO = (EmployeeTO) itrEmp.next();
				if(employeeTO.getChecked1()!=null && employeeTO.getChecked1().equalsIgnoreCase("On")){
					contains=true;
				}
				
			}
		}
		return contains;
	}
	
	/**
	 * Method to set the required data to the form to display it in UncheckGensmartCardDataInit.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUncheckGeneratedSmartCardEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Uncheck Generated Smart Card Batch input");
		UncheckGeneratedSmartCardForm uncheckGenSCForm = (UncheckGeneratedSmartCardForm) form;
		uncheckGenSCForm.resetFields();
		getInitialPageData(uncheckGenSCForm);
		log.info("Exit Init Uncheck Generated Smart Card input");
		
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP);
	}
	/**
	 * setting the department list to form
	 * @param genSmartCardDataForm
	 * @throws Exception
	 */
	public void getInitialPageData(UncheckGeneratedSmartCardForm uncheckGenSCForm) throws Exception{
		 IGensmartCardDataTransaction txn= GensmartCardDataTransactionimpl.getInstance();
		 Map<String,String> departmentMap=txn.getDepartmentMap();
		 if(departmentMap!=null)
		 {
			 uncheckGenSCForm.setDepartmentMap(departmentMap);
		 }
	}
	/**
	 * getting the employees data for the selected department
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeDataForUncheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered UncheckGeneratedSmartCardAction - getEmployeeDataForUncheck");
		
		UncheckGeneratedSmartCardForm uncheckForm = (UncheckGeneratedSmartCardForm) form;
		ActionErrors errors = new ActionErrors();
	//	validateDepartment(errors, uncheckForm);
		if (errors.isEmpty()) {
			try {
				List<EmployeeTO> empToList= UncheckGeneratedSmartCardHandler.getInstance().getEmployeeList(uncheckForm);
				if (empToList.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					uncheckForm.resetFields();
					getInitialPageData(uncheckForm);
					log.info("Exit UncheckGeneratedSmartCardAction - getEmployeeDataForUncheck size 0");
					return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP);
				} 
				setNamesToForm(uncheckForm);
				uncheckForm.setGeneratedEmployeeList(empToList);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uncheckForm.setErrorMessage(msg);
				uncheckForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			getInitialPageData(uncheckForm);
			log.info("Exit UncheckGeneratedSmartCardAction - getEmployeeDataForUncheck errors not empty ");
			return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP);
		}
		
		log.info("Exit UncheckGeneratedSmartCardAction - getEmployeeDataForUncheck");
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP_LIST);
	}

	/**
	 * to validate the department field
	 * @param errors
	 * @param uncheckForm
	 * @throws Exception
	 */
//	private void validateDepartment(ActionErrors errors,UncheckGeneratedSmartCardForm uncheckForm) throws Exception {
//		if(uncheckForm.getDepartmentId()==null || uncheckForm.getDepartmentId().trim().isEmpty()){
//			errors.add("error", new ActionError("knowledgepro.employee.validate.department"));
//		}
//	}
	/**
	 * selected list of employees SC data generated flag is being changed to false
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uncheckGeneratedEmployeeFlag(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered UncheckGeneratedSmartCardAction - uncheckGeneratedEmployeeFlag");
		
		UncheckGeneratedSmartCardForm uncheckForm = (UncheckGeneratedSmartCardForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, uncheckForm);
		boolean setFlag=false;
			try {
				boolean contains= validateMethod(uncheckForm);
				if (!contains) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckGenSmartCardData.employee.selectOne"));
					saveErrors(request, errors);
					log.info("Exit UncheckGeneratedSmartCardAction - no employees checked");
					return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP_LIST);
				} 
				setFlag = UncheckGeneratedSmartCardHandler.getInstance().updateGeneratedEmployeeFlag(uncheckForm.getGeneratedEmployeeList(),uncheckForm);
				if(setFlag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.uncheckFlag.successful.employee"));
					saveMessages(request, messages);
					log.info("Exit UncheckGeneratedSmartCardAction - uncheckGeneratedEmployeeFlag");
				}
				else {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.uncheckFlag.failure.employee"));
					saveErrors(request, errors);
					log.info("Exit UncheckGeneratedSmartCardAction - update failure");
				}
				uncheckForm.resetFields();
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uncheckForm.setErrorMessage(msg);
				uncheckForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		
		return mapping.findForward(CMSConstants.UNCHECK_GEN_SMART_CARD_EMP);
}

}

