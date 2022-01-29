package com.kp.cms.actions.exam;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.NewUpdateProccessHandler;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class NewUpdateProccessAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewUpdateProccessAction.class);
	
	/**
	 * Method to set the required data to the form to display it in updateProcess.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUpdateProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initExamMarksEntry input");
		NewUpdateProccessForm newUpdateProccessForm = (NewUpdateProccessForm) form;// Type casting the Action form to Required Form
		newUpdateProccessForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(newUpdateProccessForm, request);// setting the requested data to form
		log.info("Exit initExamMarksEntry input");
		return mapping.findForward(CMSConstants.UPDATE_PROCESS_INIT);
	}

	/**
	 * @param newUpdateProccessForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(NewUpdateProccessForm newUpdateProccessForm,HttpServletRequest request) throws Exception{
		ExamGenHandler handler = ExamGenHandler.getInstance();
		//added by smitha - new academic year parameter
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(newUpdateProccessForm.getAcademicYear()!=null && !newUpdateProccessForm.getAcademicYear().isEmpty()){
			year=Integer.parseInt(newUpdateProccessForm.getAcademicYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> batch=NewUpdateProccessHandler.getInstance().getBatchYear();
//		HttpSession session = request.getSession();
//		session.setAttribute("batchYearList", batch);
		if(!batch.isEmpty())
		{
		newUpdateProccessForm.setBatchYearList(batch);
		}
		
		if(newUpdateProccessForm.getProcess()!=null && !newUpdateProccessForm.getProcess().isEmpty()){
			Map<Integer, String> examMap = handler.getExamNameByProcessType(newUpdateProccessForm.getProcess(),String.valueOf(year));
			
			newUpdateProccessForm.setExamMap(examMap);
		}else{
			newUpdateProccessForm.setExamMap(new HashMap<Integer, String>());
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
	public ActionForward getClasses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		NewUpdateProccessForm newUpdateProccessForm = (NewUpdateProccessForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = newUpdateProccessForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<ClassesTO> classesList=NewUpdateProccessHandler.getInstance().getClassesByExamAndExamType(newUpdateProccessForm);
				if (classesList.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(newUpdateProccessForm, request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.UPDATE_PROCESS_INIT);
				} 
				newUpdateProccessForm.setClassesList(classesList);
				setNamesToForm(newUpdateProccessForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				newUpdateProccessForm.setErrorMessage(msg);
				newUpdateProccessForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newUpdateProccessForm, request);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.UPDATE_PROCESS_INIT);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.UPDATE_PROCESS_RESULT);
	}
	/**
	 * @param newUpdateProccessForm
	 */
	private void setNamesToForm(NewUpdateProccessForm newUpdateProccessForm) throws Exception {
		newUpdateProccessForm.setExamName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newUpdateProccessForm.getExamId()),"ExamDefinitionBO",true,"name"));
		if(newUpdateProccessForm.getProcess().equalsIgnoreCase("1")){
			newUpdateProccessForm.setProcessName("Regular Over All");
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("2")){
			newUpdateProccessForm.setProcessName("Internal Over All");
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("3")){
			newUpdateProccessForm.setProcessName("Supplementary Data Creation");
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("4")){
			newUpdateProccessForm.setProcessName("Update Pass Or Fail");
		}
//		else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("5")){
//			newUpdateProccessForm.setProcessName("Update Revaluation/Moderation"); 
//		}
		else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("6")){
			newUpdateProccessForm.setProcessName("Update Internal Grace"); 
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("7")){
			newUpdateProccessForm.setProcessName("Update External Grace"); 
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("8")){
			newUpdateProccessForm.setProcessName("Update Revaluation/Scrutiny/Challenge Valuation"); 
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("9")){
			newUpdateProccessForm.setProcessName("Internal Redo Data Creation"); 
		}else if(newUpdateProccessForm.getProcess().equalsIgnoreCase("10")){
			newUpdateProccessForm.setProcessName("Update Internal Redo Marks"); 
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
	public ActionForward updateProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		
		NewUpdateProccessForm newUpdateProccessForm = (NewUpdateProccessForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = newUpdateProccessForm.validate(mapping, request);
		setUserId(request,newUpdateProccessForm);
		if (errors.isEmpty()) {
			try {
				validateUpdateProcess(newUpdateProccessForm,errors);
				if (errors.isEmpty()) {
					newUpdateProccessForm.setErrorMessage("");
					boolean isUpdated =NewUpdateProccessHandler.getInstance().updateProcess(newUpdateProccessForm);
					if (isUpdated) {
						newUpdateProccessForm.resetFields();
						messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess",newUpdateProccessForm.getProcessName()));
						saveMessages(request, messages);
					}else{						
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.update.process.failure",newUpdateProccessForm.getProcessName()));
						addErrors(request, errors);
					}
				}else {
						addErrors(request, errors);
//						newUpdateProccessForm.setValidateProcess(true); // un comment when you provide provision to generate the proccess if supplementary applicaion already submitted
						return mapping.findForward(CMSConstants.UPDATE_PROCESS_RESULT);
					}
				setRequiredDatatoForm(newUpdateProccessForm, request);
			}  
			catch (Exception exception) {
				if(exception instanceof DataNotFoundException) {
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.gracing.no.data.for.processing"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_PROCESS_INIT);
				}
				String msg = super.handleApplicationException(exception);
				exception.printStackTrace();
				newUpdateProccessForm.setErrorMessage(msg);
				newUpdateProccessForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(newUpdateProccessForm, request);
			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
			return mapping.findForward(CMSConstants.UPDATE_PROCESS_RESULT);
		}
		log.info("Entered NewExamMarksEntryAction - saveMarks");
		return mapping.findForward(CMSConstants.UPDATE_PROCESS_INIT);
	}

	/**
	 * @param errorList
	 * @return
	 * @throws Exception
	 */
	private String getErrorMessage(List<String> errorList) throws Exception {
		StringBuffer error=new StringBuffer();
		Iterator<String> itr=errorList.iterator();
		int cout=0;
		while (itr.hasNext()) {
			if(cout==0)
				error.append(itr.next());
			else
				error.append(","+itr.next());
			cout++;
		}
		return error.toString();
	}

	/**
	 * @param newUpdateProccessForm
	 * @param errors
	 */
	private void validateUpdateProcess(NewUpdateProccessForm newUpdateProccessForm, ActionErrors errors) throws Exception {
		switch (Integer.parseInt(newUpdateProccessForm.getProcess())) {
		case 3:
			validateSupplementaryDataCreation(newUpdateProccessForm,errors);
		}
		
	}

	/**
	 * @param newUpdateProccessForm
	 * @param errors
	 */
	private void validateSupplementaryDataCreation(NewUpdateProccessForm newUpdateProccessForm, ActionErrors errors) throws Exception {
		if(!newUpdateProccessForm.isValidateProcess()){
			String classIds="";
			List<ClassesTO> classList=newUpdateProccessForm.getClassesList();
			Iterator<ClassesTO> itr=classList.iterator();
			while (itr.hasNext()) {
				ClassesTO to = (ClassesTO) itr.next();
				if(to.getChecked()!=null && !to.getChecked().isEmpty() && to.getChecked().equalsIgnoreCase("on")){
					if(classIds.isEmpty())
						classIds=String.valueOf(to.getId());
					else
						classIds=classIds+","+to.getId();
				}
			}
			List<String> regList=NewUpdateProccessHandler.getInstance().getAlreadyModifiedStudents(classIds,newUpdateProccessForm);
			String reg="";
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
			if(!reg.isEmpty()){
				newUpdateProccessForm.setValidateMsg("In Supplementary/Improvement Application Appeared is already marked for this exam. If you re-run the process, the Appeared mark will be removed. Do you wish to proceed? ");
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.newUpdateProcess.supplimentary.msg"));
			}
		}
	}

}
