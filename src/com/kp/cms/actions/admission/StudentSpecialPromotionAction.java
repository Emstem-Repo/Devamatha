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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;
import com.kp.cms.handlers.admission.StudentSpecialPromotionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class StudentSpecialPromotionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentSpecialPromotionAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStudentPromotion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("Entering initStudentPromotion");
		StudentSpecialPromotionForm studentSpecialPromotionForm = (StudentSpecialPromotionForm) form;
		try {
			studentSpecialPromotionForm.resetFields();
			setRequestedDataToForm(studentSpecialPromotionForm);
		} catch (Exception e) {
			log.error("error submit initStudentPromotion page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				studentSpecialPromotionForm.setErrorMessage(msg);
				studentSpecialPromotionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				studentSpecialPromotionForm.setErrorMessage(msg);
				studentSpecialPromotionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Leaving initStudentPromotion ");
		return mapping.findForward(CMSConstants.INIT_STUDENT_PROMOTION);
	}
	/**
	 * @param studentSpecialPromotionForm
	 */
	private void setRequestedDataToForm(StudentSpecialPromotionForm studentSpecialPromotionForm) throws Exception {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}
		if(studentSpecialPromotionForm.getAcademicYear()!=null && !studentSpecialPromotionForm.getAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(studentSpecialPromotionForm.getAcademicYear());
		}
		studentSpecialPromotionForm.setYear(Integer.toString(currentYear));
		// 1. The current year is loaded by default and it's corresponding
		// classes
		setClassListToForm(studentSpecialPromotionForm, currentYear);
	}
	
	/**
	 * @param studentSpecialPromotionForm
	 * @param year
	 * @throws Exception
	 */
	public void setClassListToForm(StudentSpecialPromotionForm studentSpecialPromotionForm, int year)
			throws Exception {
		log.info("Entering into setClassListToForm");
		try {
			Map<Integer, String> classMap = null;
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
			studentSpecialPromotionForm.setClassMap(classMap);
		} catch (Exception e) {
			log.error("Error occured in setClassListToForm");
		}
		log.info("Leaving into setClassListToForm");
	}
	
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered StudentSpecialPromotionAction - getCandidates");
		
		StudentSpecialPromotionForm studentSpecialPromotionForm = (StudentSpecialPromotionForm) form;
		 ActionErrors errors = studentSpecialPromotionForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<StudentTO> selectedCandidates = StudentSpecialPromotionHandler.getInstance().getListOfCandidates(studentSpecialPromotionForm, request);
				if(!selectedCandidates.isEmpty()){
					StudentTO to = selectedCandidates.get(0);
					studentSpecialPromotionForm.setCourseId(String.valueOf(to.getCourseId()));
				}
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequestedDataToForm(studentSpecialPromotionForm);
					log.info("Exit StudentSpecialPromotionAction - getCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_STUDENT_PROMOTION);
				} 
				studentSpecialPromotionForm.setList(selectedCandidates);
				Map<Integer, String> classMap =StudentSpecialPromotionHandler.getInstance().getPromotionClasses(studentSpecialPromotionForm);
				studentSpecialPromotionForm.setPromotionClassMap(classMap);
				studentSpecialPromotionForm.setPromotionClassId(null);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				studentSpecialPromotionForm.setErrorMessage(msg);
				studentSpecialPromotionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequestedDataToForm(studentSpecialPromotionForm);		
			log.info("Exit StudentSpecialPromotionAction - getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_STUDENT_PROMOTION);
		}
		log.info("Entered StudentSpecialPromotionAction - getCandidates");
		return mapping.findForward(CMSConstants.STUDENT_PROMOTION_RESULT);
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updatePromtionProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered StudentSpecialPromotionAction - updatePromtionProcess");
		
		StudentSpecialPromotionForm studentSpecialPromotionForm = (StudentSpecialPromotionForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = studentSpecialPromotionForm.validate(mapping, request);
		validatePromotionProcess(studentSpecialPromotionForm,errors);
		setUserId(request,studentSpecialPromotionForm);
		if (errors.isEmpty()) {
		
			try {
				
				boolean isUpdated = StudentSpecialPromotionHandler.getInstance().updateStudentClass(studentSpecialPromotionForm);
				if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.promotion.process.success"));
					saveMessages(request, messages);
					studentSpecialPromotionForm.resetFields();
					
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.promotion.process.failure"));
					addErrors(request, errors);
				}
				setRequestedDataToForm(studentSpecialPromotionForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				studentSpecialPromotionForm.setErrorMessage(msg);
				studentSpecialPromotionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequestedDataToForm(studentSpecialPromotionForm);
			log.info("Exit StudentSpecialPromotionAction - updatePromtionProcess errors not empty ");
			return mapping.findForward(CMSConstants.STUDENT_PROMOTION_RESULT);
		}
		log.info("Entered StudentSpecialPromotionAction - updatePromtionProcess");
		return mapping.findForward(CMSConstants.INIT_STUDENT_PROMOTION);
	}
	/**
	 * @param promotionProcessForm
	 * @param errors
	 * @throws Exception
	 */
	private void validatePromotionProcess(StudentSpecialPromotionForm promotionProcessForm, ActionErrors errors) throws Exception{
		boolean isChecked=false;
		List<StudentTO> list=promotionProcessForm.getList();
		Iterator<StudentTO> itr=list.iterator();
		while (itr.hasNext()) {
			StudentTO to = (StudentTO) itr.next();
			if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on")){
				isChecked=true;
				break;
			}
		}
		if(!isChecked){
			errors.add(CMSConstants.ERROR,new ActionError("interviewProcessForm.checkbox.select"));
		}
		if(promotionProcessForm.getPromotionClassId()==null || promotionProcessForm.getPromotionClassId().isEmpty())
			errors.add(CMSConstants.ERROR,new ActionError("errors.required","Promotion Class"));
	}
	
}
