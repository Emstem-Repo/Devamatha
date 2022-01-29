package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.RepeatMidSemAppForm;
import com.kp.cms.handlers.exam.ExamMidsemRepeatHandler;
import com.kp.cms.utilities.CurrentAcademicYear;

public class RepeatMidSemExemptionAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(RepeatMidSemExemptionAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMidsemRepeat(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		RepeatMidSemAppForm examMidsemRepratForm=(RepeatMidSemAppForm) form;
		examMidsemRepratForm.resetFields();
		setRequiredDatatoForm(examMidsemRepratForm, CurrentAcademicYear.getInstance().getAcademicyear(), request);
		return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
	}
	
	private void setRequiredDatatoForm(RepeatMidSemAppForm examMidsemRepratForm, int year,HttpServletRequest request) throws Exception{
			
			Map<Integer, String> midexamNameMap = ExamMidsemRepeatHandler.getInstance().getExamNameList(year);
			examMidsemRepratForm.setMidsemExamList(midexamNameMap);
		}
	
	public ActionForward getStudentExamMidsemRepeatData(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		RepeatMidSemAppForm examMidsemRepratForm=(RepeatMidSemAppForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors = examMidsemRepratForm.validate(mapping, request);
		if (errors == null || errors.isEmpty()) {
		boolean alreadyExempted=ExamMidsemRepeatHandler.getInstance().getStudentAlreadyExempted(examMidsemRepratForm);
		if(!alreadyExempted){
			boolean dataset=ExamMidsemRepeatHandler.getInstance().getStudentDataForExemption(examMidsemRepratForm);
			if(dataset){
				return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
			}
			else{
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
			}
		}else
		{
			ActionMessage message = new ActionError("knowledgepro.admin.Repaet.Exemption.already");
			messages.add("messages", message);
			saveMessages(request, messages);
			
			return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
		}
		}else
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
		}
	}
	
	public ActionForward SaveExemption (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		RepeatMidSemAppForm examMidsemRepratForm=(RepeatMidSemAppForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		setUserId(request, examMidsemRepratForm);
		boolean isApplied=false;
		if (errors != null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
			
		}
		else{
			try {
				boolean alreadyExempted=ExamMidsemRepeatHandler.getInstance().getStudentAlreadyExempted(examMidsemRepratForm);
				if(!alreadyExempted){
					isApplied=ExamMidsemRepeatHandler.getInstance().setDataToBosExemption(examMidsemRepratForm);
					ActionMessage message = new ActionError("knowledgepro.admin.Repaet.Exemption.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					
					return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
				}
				else
				{
					ActionMessage message = new ActionError("knowledgepro.admin.Repaet.Exemption.already");
					messages.add("messages", message);
					saveMessages(request, messages);
					
					return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
				}
			}catch(DataNotFoundException e) {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.data.not.selected"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mapping.findForward(CMSConstants.REPEAT_MID_SEM_EXEMPTION);
	}

}
