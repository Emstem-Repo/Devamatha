package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamMidsemExemptionForm;
import com.kp.cms.handlers.exam.ExamMidsemExemptionHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemExemptionAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ExamMidsemExemptionAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMidsemExemption(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("end of initExamMidsemExemption method in ExamMidsemExemptionAction class.");
		ExamMidsemExemptionForm examMidsemExemptionForm=(ExamMidsemExemptionForm) form;
		examMidsemExemptionForm.clearAll();
		
		setRequiredDatatoForm(examMidsemExemptionForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		log.debug("Leaving initExamMidsemExemption");
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
	}
	
	/**
	 * @param examForm
	 * @param year
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamMidsemExemptionForm examForm, int year,HttpServletRequest request) throws Exception{
		
		Map<Integer, String> examNameMap = ExamMidsemExemptionHandler.getInstance().getExamNameList(year);
		examForm.setExamList(examNameMap);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assignClassListToForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExamMidsemExemptionForm exemptionForm = (ExamMidsemExemptionForm) form;
		ActionErrors errors = new ActionErrors();
		try {
					
			Map<Integer, String> classMap = ExamMidsemExemptionHandler.getInstance().getClassListAccReg(Integer.parseInt(exemptionForm.getRegNo()));
			if(!classMap.isEmpty())
				exemptionForm.setClassList(classMap);
			else{
				errors.add("error", new ActionError("knowledgepro.hostel.disciplinary.action.details.norecords"));
				addErrors(request, errors);
				setRequiredDatatoForm(exemptionForm, Integer.parseInt(exemptionForm.getYear()), request);
				return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
			}
		} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				exemptionForm.setErrorMessage(msg);
				exemptionForm.setErrorStack(e.getMessage());
		}
		setRequiredDatatoForm(exemptionForm, Integer.parseInt(exemptionForm.getYear()), request);
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentSubjects(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExamMidsemExemptionForm exemptionForm = (ExamMidsemExemptionForm) form;
		 ActionErrors errors = exemptionForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				setExamName(exemptionForm, request);
				StudentTO stuTO= ExamMidsemExemptionHandler.getInstance().getStudentClassandSubjects(exemptionForm);
				exemptionForm.setStudent(stuTO);
				
				return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION_DETAILS);
			}
		} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				exemptionForm.setErrorMessage(msg);
				exemptionForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		setRequiredDatatoForm(exemptionForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
	}

	
	/**
	 * @param exemptionForm
	 * @param request
	 * @throws Exception
	 */
	private void setExamName(ExamMidsemExemptionForm exemptionForm, HttpServletRequest request) throws Exception 
	{
		ExamDefinitionBO examDefinitionBO = (ExamDefinitionBO) ExamMidsemExemptionHandler.getInstance().getExamName(exemptionForm);
		exemptionForm.setExamName(examDefinitionBO.getName());
			
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveExemptionSubjects(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExamMidsemExemptionForm exemptionForm = (ExamMidsemExemptionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, exemptionForm);
		
		try {
			
			List<Integer> idList = ExamMidsemExemptionHandler.getInstance().getAnySelected(exemptionForm);
			if(idList==null || idList.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.exammidsem.exemption.available"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION_DETAILS);
			}
			
			boolean isSaved = false;

			isSaved = ExamMidsemExemptionHandler.getInstance().saveSubjects(exemptionForm);
			//If add operation is success then display the success message.
			if(isSaved)
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.exammidsem.exemption.add.success"));
				saveMessages(request, messages);
				exemptionForm.clearAll();
				return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
			}
			//If add operation is failure then add the error message.
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.exammidsem.exemption.add.fail"));
				saveErrors(request, errors);
				}
			
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			exemptionForm.setErrorMessage(msg);
			exemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_MIDSEM_EXEMPTION);
	}
	
	
}
