package com.kp.cms.actions.admin;

import java.util.Calendar;
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
import com.kp.cms.forms.admin.ExamMidSemRepeatSettingForm;
import com.kp.cms.handlers.admin.ExamMidSemRepeatSettingHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ExamMidSemRepeatSettingTo;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamMidSemRepeatSettingAction extends BaseDispatchAction {
	
	
	private static Log log = LogFactory.getLog(ExamMidSemRepeatSettingAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMidSemRepeatSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			  ExamMidSemRepeatSettingForm settingForm=(ExamMidSemRepeatSettingForm) form;
			  settingForm.reset();
		 try{
			  setRequiredDataToForm(settingForm);
		    }catch (Exception e) {
				log.error("error in editing venueDetails...", e);
				String msg = super.handleApplicationException(e);
				settingForm.setErrorMessage(msg);
				settingForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
		return mapping.findForward(CMSConstants.INIT_MID_SEM_REPEAT_SETTING);
	}
	
	/**
	 * @param settingForm
	 * @throws Exception
	 */
	public void setRequiredDataToForm(ExamMidSemRepeatSettingForm settingForm) throws Exception{
		
		Map<Integer, String> examTypeMap=ExamMidSemRepeatSettingHandler.getInstance().getExamTypeList(settingForm);
		if(examTypeMap!=null && !examTypeMap.isEmpty()){
			settingForm.setExamTypeMap(examTypeMap);
		 }
		String currentExamName=null;
		if(settingForm.getExamType()!=null && !settingForm.getExamType().isEmpty()){
			if(settingForm.getExamName()==null || settingForm.getExamName().trim().isEmpty()){
				currentExamName=ExamMidSemRepeatSettingHandler.getInstance().getCurrentExamName(Integer.parseInt(settingForm.getExamType()));
			 }
			if(currentExamName!=null && !currentExamName.isEmpty()){
				settingForm.setExamName(currentExamName);
			}
		}
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(settingForm.getAcademicYear()!=null && !settingForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(settingForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),year); 
		settingForm.setExamNameMap(examMap);
		
		Map<Integer, ExamMidSemRepeatSettingTo> repeatSettingMap=ExamMidSemRepeatSettingHandler.getInstance().getMidSemRepeatSettingList();
		if(repeatSettingMap!=null && !repeatSettingMap.isEmpty()){
			settingForm.setRepeatSettingMap(repeatSettingMap);
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
	public ActionForward saveMidSemRepeatSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamMidSemRepeatSettingForm settingForm=(ExamMidSemRepeatSettingForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, settingForm);
		String mode="Save";
		try{
			boolean isDuplicate=ExamMidSemRepeatSettingHandler.getInstance().duplicateCheckRepeatSetting(settingForm);
			if(!isDuplicate){
				boolean isSaved=ExamMidSemRepeatSettingHandler.getInstance().saveOrUpdateMidSemRepeatSetting(settingForm,mode);
				if(isSaved){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.mid.sem.repeat.setting.saved.success"));
					saveMessages(request, messages);
					settingForm.reset();
					setRequiredDataToForm(settingForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.mid.sem.repeat.setting.saved.failure"));
					addErrors(request, errors);
					Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),Integer.parseInt(settingForm.getAcademicYear())); 
					settingForm.setExamNameMap(examMap);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.mid.sem.repeat.setting.duplicate.exist"));
				addErrors(request, errors);
				Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),Integer.parseInt(settingForm.getAcademicYear())); 
				settingForm.setExamNameMap(examMap);
			}
			
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			settingForm.setErrorMessage(msg);
			settingForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MID_SEM_REPEAT_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editMidSemRepeatSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamMidSemRepeatSettingForm settingForm=(ExamMidSemRepeatSettingForm) form;
		setUserId(request, settingForm);
		try{
			ExamMidSemRepeatSettingHandler.getInstance().editMidSemRepeatSetting(settingForm);
			Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),Integer.parseInt(settingForm.getAcademicYear())); 
			settingForm.setExamNameMap(examMap);
			request.setAttribute("operation", "edit");
		   } catch (Exception e) {
				log.error("error in editing venueDetails...", e);
				String msg = super.handleApplicationException(e);
				settingForm.setErrorMessage(msg);
				settingForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_MID_SEM_REPEAT_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMidSemRepeatSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamMidSemRepeatSettingForm settingForm=(ExamMidSemRepeatSettingForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, settingForm);
		String mode="Update";
		try{
			boolean isDuplicate=ExamMidSemRepeatSettingHandler.getInstance().duplicateCheckRepeatSetting(settingForm);
			if(!isDuplicate){
				boolean isUpdated=ExamMidSemRepeatSettingHandler.getInstance().saveOrUpdateMidSemRepeatSetting(settingForm,mode);
				if(isUpdated){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.mid.sem.repeat.setting.updated.success"));
					saveMessages(request, messages);
					settingForm.reset();
					setRequiredDataToForm(settingForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.mid.sem.repeat.setting.update.failure"));
					addErrors(request, errors);
					Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),Integer.parseInt(settingForm.getAcademicYear())); 
					settingForm.setExamNameMap(examMap);
				}
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.mid.sem.repeat.setting.duplicate.exist"));
				addErrors(request, errors);
				Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(settingForm.getExamType(),Integer.parseInt(settingForm.getAcademicYear())); 
				settingForm.setExamNameMap(examMap);
			}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			settingForm.setErrorMessage(msg);
			settingForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MID_SEM_REPEAT_SETTING);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMidSemRepeatSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamMidSemRepeatSettingForm settingForm=(ExamMidSemRepeatSettingForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, settingForm);
		try{
				boolean isDeleted=ExamMidSemRepeatSettingHandler.getInstance().deleteMidSemRepeatSetting(settingForm);
				if(isDeleted){
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admin.mid.sem.repeat.setting.deleted.success"));
					saveMessages(request, messages);
					settingForm.reset();
					setRequiredDataToForm(settingForm);
				}else{
					errors.add("error", new ActionError("knowledgepro.admin.mid.sem.repeat.setting.delete.failure"));
					addErrors(request, errors);
				}
		} catch (Exception e) {
			log.error("error in editing venueDetails...", e);
			String msg = super.handleApplicationException(e);
			settingForm.setErrorMessage(msg);
			settingForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_MID_SEM_REPEAT_SETTING);
	}
}
