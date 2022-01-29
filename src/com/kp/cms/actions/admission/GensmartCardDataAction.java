package com.kp.cms.actions.admission;

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
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class GensmartCardDataAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(GensmartCardDataAction.class);
	
	/**
	 * Method to set the required data to the form to display it in GensmartCardData.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGensmartCardData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Generate Smart Card Data Batch input");
		GensmartCardDataForm genSmartCardDataForm = (GensmartCardDataForm) form;
		genSmartCardDataForm.resetFields();
		genSmartCardDataForm.setPrint(false);
		setRequiredDatatoForm(genSmartCardDataForm, request);
		log.info("Exit Generate Smart Card Data Batch input");
		
		return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_INIT);
	}

	/** 
	 * Setting the required data to the form to display in jsp
	 * @param genSmartCardDataForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm( GensmartCardDataForm genSmartCardDataForm, HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler .getInstance().getProgramType();
		genSmartCardDataForm.setProgramTypeList(programTypeList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentsData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered GensmartCardDataAction - getStudentsData");
		
		GensmartCardDataForm genscDataForm = (GensmartCardDataForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = genscDataForm.validate(mapping, request);
		genscDataForm.setPrint(false);
		setUserId(request, genscDataForm);
		if (errors.isEmpty()) {
			try {
				 Map<Integer,List<StudentTO>> studentsData = GensmartCardDataHandler.getInstance().getListOfStudents(genscDataForm, request);
				 if(genscDataForm.getStudentIds()!=null && !genscDataForm.getStudentIds().isEmpty())
				 checkErrors(genscDataForm,errors,messages);
				if (studentsData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.smartCardDataDownload.generated"));
					saveErrors(request, errors);
					setRequiredDatatoForm(genscDataForm, request);
					genscDataForm.setPrint(false);
					log.info("Exit GensmartCardDataAction - getStudentsData size 0");
					return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_INIT);
				} 
				genscDataForm.setSmartCardData(studentsData);
				genscDataForm.setPrint(true);
				saveErrors(request, errors);
				saveMessages(request, messages);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				genscDataForm.setErrorMessage(msg);
				genscDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(genscDataForm, request);
			genscDataForm.setPrint(false);
			log.info("Exit GensmartCardDataAction - getStudentsData errors not empty ");
			return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_INIT);
		}
		
		log.info("Exit GensmartCardDataAction - getStudentsData");
		return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_INIT);
	}

	/**
	 * @param genscDataForm
	 * @param errors
	 * @param messages
	 */
	private void checkErrors(GensmartCardDataForm genscDataForm, ActionErrors errors, ActionMessages messages) throws Exception{ 
		GensmartCardDataHandler.getInstance().getStudentsWithoutPhotosAndRegNos(genscDataForm);
		String noPhotos="";
		String noRegNos="";
		if(genscDataForm.getStudentsWithoutPhotos()!=null && !genscDataForm.getStudentsWithoutPhotos().isEmpty()){
			List<String> list=genscDataForm.getStudentsWithoutPhotos();
			Iterator<String> itr=list.iterator();
			while (itr.hasNext()) {
				String str = (String) itr.next();
				if(noPhotos.isEmpty()){
					noPhotos=str;
				}
				else{
					noPhotos = noPhotos+","+str;
				}
			}
		}
		if(genscDataForm.getStudentsWithoutRegNos()!=null && !genscDataForm.getStudentsWithoutRegNos().isEmpty()){
			List<String> listApplnNos=genscDataForm.getStudentsWithoutRegNos();
			Iterator<String> it=listApplnNos.iterator();
			while (it.hasNext()) {
				String strApplnNos = (String) it.next();
				if(noRegNos.isEmpty()){
				noRegNos=strApplnNos;	
				}
				else {
					noRegNos=noRegNos+","+strApplnNos;
				}
			}
		}
		if(noPhotos !=null && !noPhotos.isEmpty() || (noRegNos!=null && !noRegNos.isEmpty())){
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.successfullWithMsg"));	
			if(noPhotos !=null && !noPhotos.isEmpty()){	
				noPhotos=CommonUtil.splitString(noPhotos,56);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.withoutPhotos",noPhotos));	
			}
			
			if(noRegNos!=null && !noRegNos.isEmpty()){
				noRegNos=CommonUtil.splitString(noRegNos,56);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.withoutRegNos",noRegNos));
			}
		}else{
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.successfull"));
		}
		
	}
	
	/**
	 * Method to set the required data to the form to display it in GensmartCardDataEmployee.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGensmartCardDataEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Generate Smart Card Data Employee Batch input");
		GensmartCardDataForm genSmartCardDataForm = (GensmartCardDataForm) form;
		genSmartCardDataForm.resetFields();
		genSmartCardDataForm.setPrint(false);
		GensmartCardDataHandler.getInstance().getInitialPageData(genSmartCardDataForm);
		log.info("Exit Generate Smart Card Data Employee Batch input");
		return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_EMP_INIT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEmployeeData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered GensmartCardDataAction - getEmployeeData");
		GensmartCardDataForm genscDataForm = (GensmartCardDataForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		genscDataForm.setPrint(false);
			try {
				Map<Integer, List<EmployeeTO>> employeeData = GensmartCardDataHandler.getInstance().getListOfEmployees(genscDataForm, request);
				 if(genscDataForm.getEmployeeIds()!=null && !genscDataForm.getEmployeeIds().isEmpty())
					 checkErrorsEmp(genscDataForm,errors,messages);
				if (employeeData.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.smartCardDataDownload.generated"));
					saveErrors(request, errors);
					setRequiredDatatoForm(genscDataForm, request);
					genscDataForm.setPrint(false);
					log.info("Exit GensmartCardDataAction - getEmployeeData size 0");
					return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_EMP_INIT);
				} 
				genscDataForm.setSmartCardDataEmp(employeeData);
				genscDataForm.setPrint(true);
				saveErrors(request, errors);
				saveMessages(request, messages);
				GensmartCardDataHandler.getInstance().getInitialPageData(genscDataForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				genscDataForm.setErrorMessage(msg);
				genscDataForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Exit GensmartCardDataAction - getEmployeeData");
		return mapping.findForward(CMSConstants.GEN_SMART_CARD_DATA_EMP_INIT);
	}
	
	/**
	 * @param genscDataForm
	 * @param errors
	 * @param messages
	 */
	private void checkErrorsEmp(GensmartCardDataForm genscDataForm, ActionErrors errors, ActionMessages messages) throws Exception{ 
		GensmartCardDataHandler.getInstance().getEmployeesWithoutPhotosAndRegNos(genscDataForm);
		String noPhotos="";
		String noFingerPrintIds="";
		if(genscDataForm.getEmployeesWithoutPhotos()!=null && !genscDataForm.getEmployeesWithoutPhotos().isEmpty()){
			List<String> list=genscDataForm.getEmployeesWithoutPhotos();
			Iterator<String> itr=list.iterator();
			while (itr.hasNext()) {
				String str = (String) itr.next();
				if(noPhotos.isEmpty()){
					noPhotos=str;
				}
				else{
					noPhotos = noPhotos+","+str;
				}
			}
		}
		if(genscDataForm.getEmployeesWithoutFingerPrintIds()!=null && !genscDataForm.getEmployeesWithoutFingerPrintIds().isEmpty()){
			List<String> listFirstNames=genscDataForm.getEmployeesWithoutFingerPrintIds();
			Iterator<String> it=listFirstNames.iterator();
			while (it.hasNext()) {
				String strFirstNames = (String) it.next();
				if(noFingerPrintIds.isEmpty()){
					noFingerPrintIds=strFirstNames;	
				}
				else {
					noFingerPrintIds=noFingerPrintIds+","+strFirstNames;
				}
			}
		}
		if(noPhotos !=null && !noPhotos.isEmpty() || (noFingerPrintIds!=null && !noFingerPrintIds.isEmpty())){
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.employee.successfullWithMsg"));	
			if(noPhotos !=null && !noPhotos.isEmpty()){	
				noPhotos=CommonUtil.splitString(noPhotos,56);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.employee.withoutPhotos",noPhotos));	
			}
			
			if(noFingerPrintIds!=null && !noFingerPrintIds.isEmpty()){
				noFingerPrintIds=CommonUtil.splitString(noFingerPrintIds,56);
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.employee.withoutFingerPrintIds",noFingerPrintIds));
			}
		}else{
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.successfull"));
		}
		
	}
}


