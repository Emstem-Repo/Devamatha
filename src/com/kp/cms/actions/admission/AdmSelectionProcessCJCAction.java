package com.kp.cms.actions.admission;

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
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admission.AdmSelectionProcessCJCForm;
import com.kp.cms.handlers.admission.AdmSelectionProcessCJCHandler;
import com.kp.cms.helpers.admission.AdmSelectionProcessCJCHelper;
import com.kp.cms.transactions.admission.IAdmSelectProcessCJCTransaction;
import com.kp.cms.transactionsimpl.admission.AdmSelectProcessCJCImpl;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AdmSelectionProcessCJCAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(AdmSelectionProcessCJCAction.class);
	
	IAdmSelectProcessCJCTransaction transaction = AdmSelectProcessCJCImpl.getInstance();
	AdmSelectionProcessCJCHandler handler=AdmSelectionProcessCJCHandler.getInstance();
	AdmSelectionProcessCJCHelper helper=AdmSelectionProcessCJCHelper.getInstance();
	
	
	public ActionForward initAdmSelectionProcess(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		AdmSelectionProcessCJCForm admSelProcessCJCForm = (AdmSelectionProcessCJCForm) form;
		admSelProcessCJCForm.clearAll();
		setRequiredDatatoForm(admSelProcessCJCForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.INIT_ADM_SELECTION_CJC);
	}
	/**
	 * @param examMidsemRepratForm
	 * @param year
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(AdmSelectionProcessCJCForm admSelProcessCJCForm, int year,HttpServletRequest request) throws Exception{
		
		Map<String, String> religionMap = transaction.getReligionMap();
		admSelProcessCJCForm.setReligionMap(religionMap);
		
		Map<String, String> casteMap = transaction.getCasteMap();
		admSelProcessCJCForm.setCasteMap(casteMap);
		
		Map<String, String> institutionMap = transaction.getInstutionMap();
		admSelProcessCJCForm.setInstitutionMap(institutionMap);
		
		Map<String, String> courseMap = transaction.getCourseMap();
		admSelProcessCJCForm.setCourseMaps(courseMap);
		
		Map<String, String> universityMap = transaction.getUniversityMap();
		admSelProcessCJCForm.setUniversityMap(universityMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchRunProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AdmSelectionProcessCJCForm admSelProcessCJCForm = (AdmSelectionProcessCJCForm) form;
		 ActionErrors errors = admSelProcessCJCForm.validate(mapping, request);
		 ActionMessages messages = new ActionMessages();
		 boolean flag=false;
		setUserId(request,admSelProcessCJCForm);
		try {
			if(errors.isEmpty()){
				flag= handler.getRunSetDataToTable(admSelProcessCJCForm);
				if(flag){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.Selection.process.success"));
					saveMessages(request, messages);
				}
				admSelProcessCJCForm.clearAll();
				setRequiredDatatoForm(admSelProcessCJCForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
				return mapping.findForward(CMSConstants.INIT_ADM_SELECTION_CJC);
			}
		}catch(DataNotFoundException e) {
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.norecords"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ADM_SELECTION_CJC);
		}catch(SubjectGroupNotDefinedException e) {
	 		errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.Selection.process.over",admSelProcessCJCForm.getNotDefinedIntPgmCourse() ));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.INIT_ADM_SELECTION_CJC);
	 	}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				admSelProcessCJCForm.setErrorMessage(msg);
				admSelProcessCJCForm.setErrorStack(e.getMessage());
		}
		saveErrors(request, errors);
		setRequiredDatatoForm(admSelProcessCJCForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.INIT_ADM_SELECTION_CJC);
	}
	

}
