package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.exam.SupplementaryDataCreationForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.SupplementaryDataCreationHandler;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SupplementaryDataCreationAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SupplementaryDataCreationAction.class);
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initSupplementaryUpdateProcess(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	SupplementaryDataCreationForm objForm = (SupplementaryDataCreationForm)form;
	try{
	objForm.resetFields();
	setRequestedDataToForm(objForm,request);
	}catch (Exception e) {
		String msg = super.handleApplicationException(e);
		objForm.setErrorMessage(msg);
		objForm.setErrorStack(e.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	return mapping.findForward(CMSConstants.INIT_SUPP_DATA_CREATION);
}

/**
 * @param objForm
 * @param request
 * @throws Exception
 */
private void setRequestedDataToForm(SupplementaryDataCreationForm objForm, HttpServletRequest request) throws Exception {
	int year=0;
	year=CurrentAcademicYear.getInstance().getAcademicyear();
	if(objForm.getAcademicYear()!=null && !objForm.getAcademicYear().isEmpty()){
		year=Integer.parseInt(objForm.getAcademicYear());
	}
	if(year==0){
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
	}
	Map<Integer,String> examMap = SupplementaryDataCreationHandler.getInstance().getExamNameByProcessType(String.valueOf(year));
	objForm.setSuppExamNameMap(examMap);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward getClasses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	SupplementaryDataCreationForm objForm = (SupplementaryDataCreationForm)form;
	 ActionErrors errors = objForm.validate(mapping, request);
	if (errors.isEmpty()) {
		try {
			List<ClassesTO> classesList = SupplementaryDataCreationHandler.getInstance().getClassesByExamId(objForm);
			if (classesList.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setRequestedDataToForm(objForm, request);
				return mapping.findForward(CMSConstants.INIT_SUPP_DATA_CREATION);
			} 
			objForm.setClassesTOList(classesList);
			setNamesToForm(objForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	}else {
		addErrors(request, errors);
		setRequestedDataToForm(objForm,request);
		return mapping.findForward(CMSConstants.INIT_SUPP_DATA_CREATION);
	}
		return mapping.findForward(CMSConstants.SUPP_DATA_CREATION_UPDATE);
	}

	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void setNamesToForm(SupplementaryDataCreationForm objForm) throws Exception {
		objForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objForm.getExamId()),"ExamDefinitionBO",true,"name"));
		objForm.setProcessName("Supplementry Data Creation");
}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSupplementryDataCreationProcess(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		SupplementaryDataCreationForm objForm = (SupplementaryDataCreationForm)form;
		 ActionErrors errors = objForm.validate(mapping, request);
		setUserId(request,objForm);
		ActionMessages messages=new ActionMessages();
		if (errors.isEmpty()) {
			try{
				validateUpdateProcess(objForm,errors);				
				if (errors.isEmpty()) {
					boolean isUpdated = SupplementaryDataCreationHandler.getInstance().updateSupplementryDataCreationProcess(objForm);
					if(isUpdated){
						objForm.resetFields();
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.supplementry.data.creation.successful"));
						saveMessages(request, messages);
					}else{
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admin.supplementry.data.creation.failed"));
						addErrors(request, errors);
					}
				}else{
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.SUPP_DATA_CREATION_UPDATE);
				}
				setRequestedDataToForm(objForm,request);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);
			setRequestedDataToForm(objForm,request);
			return mapping.findForward(CMSConstants.SUPP_DATA_CREATION_UPDATE);
		}
		return mapping.findForward(CMSConstants.INIT_SUPP_DATA_CREATION);
		}

	/**
	 * @param objForm
	 * @param errors
	 * @throws Exception 
	 */
	private void validateUpdateProcess(SupplementaryDataCreationForm objForm, ActionErrors errors) throws Exception {
		if(!objForm.isValidateProcess()){
			String classIds = "";
			List<ClassesTO> classesList = objForm.getClassesTOList();
			Iterator<ClassesTO> iterator = classesList.iterator();
			while (iterator.hasNext()) {
				ClassesTO to = (ClassesTO) iterator.next();
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on") && to.getTempChecked()){
					if(classIds.isEmpty())
						classIds=String.valueOf(to.getId());
					else
						classIds=classIds+","+to.getId();
				}
			}
			String reg="";
			if(!classIds.isEmpty()){
			List<String> regList = SupplementaryDataCreationHandler.getInstance().getAlreadyModifiedStudents(classIds,objForm);
			if(regList!=null && !regList.isEmpty()){
				Iterator<String> regItr=regList.iterator();
				while (regItr.hasNext()) {
					String s = (String) regItr.next();
					if(reg.isEmpty())
						reg=s;
					else
						reg=reg+","+s;
				}
			}
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.atleast.oneclass"));
			}
			if(!reg.isEmpty()){
				//objForm.setValidateMsg("In Supplementary/Improvement Application Appeared is already marked for this exam. If you re-run the process, the Appeared mark will be removed. Do you wish to proceed? ");
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.newUpdateProcess.supplimentary.msg"));
			}
		}
	}
}
