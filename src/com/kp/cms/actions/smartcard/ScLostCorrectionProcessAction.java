package com.kp.cms.actions.smartcard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.handlers.smartcard.ScLostCorrectionProcessHandler;
import com.kp.cms.handlers.smartcard.ScLostCorrectionViewHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionProcessTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionProcessAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionProcessAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScLostCorrectionProcess(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("start of initScLostCorrection method in initScLostCorrectionProcess class.");
		ScLostCorrectionProcessForm scForm=(ScLostCorrectionProcessForm) form;
		scForm.clear();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		log.debug("Leaving initScLostCorrectionProcess");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScLostCorrectionProcessSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		
		ScLostCorrectionProcessForm scForm=(ScLostCorrectionProcessForm) form;
		ActionErrors errors=new ActionErrors();

			try{
				List<ScLostCorrectionProcessTO> scProcess = ScLostCorrectionProcessHandler.getInstance().getDetailsList(scForm);
				
				if(scProcess==null || scProcess.isEmpty() || scProcess.size()==0){
					errors.add("error", new ActionError("knowledgepro.norecords"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS);
				}else{
					
					scForm.setScList(scProcess);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setChangeStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScLostCorrectionProcessAction - setChangeStatus");
		ScLostCorrectionProcessForm scForm = (ScLostCorrectionProcessForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		
			try {
				if(scForm.getStatus2().equalsIgnoreCase("Reject") && scForm.getReasonForRejection().isEmpty()){
					
					errors.add("error", new ActionError( "knowledgepro.smartcard.lostcorrection.view.reasonforrejection.needed"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
					
				}
				boolean isStatusChanged;

				isStatusChanged = ScLostCorrectionProcessHandler.getInstance().setStatus(scForm);
				//If add operation is success then display the success message.
				if(isStatusChanged)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_PROCESS_ADD_SUCCESS));
					saveMessages(request, messages);
					List<ScLostCorrectionProcessTO> scProcess = ScLostCorrectionProcessHandler.getInstance().getDetailsList(scForm);
					scForm.setScList(scProcess);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
				}
				//If add operation is failure then add the error message.
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SC_LOST_CORRECTION_PROCESS_ADD_FAIL));
					saveErrors(request, errors);
					}
			
				}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		log.info("Exit ScLostCorrectionProcessAction - setChangeStatus");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
	}
	
	
	/**
	 * @param genscDataForm
	 * @param errors
	 * @param messages
	 */
	private void checkErrors(ScLostCorrectionProcessForm scForm, ActionErrors errors, ActionMessages messages) throws Exception
	{
		ScLostCorrectionProcessHandler.getInstance().getStudentsWithoutPhotosAndRegNos(scForm);
		String noPhotos="";
		String noRegNos="";
		if(scForm.getStudentsWithoutPhotos()!=null && !scForm.getStudentsWithoutPhotos().isEmpty()){
			List<String> list=scForm.getStudentsWithoutPhotos();
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
		if(scForm.getStudentsWithoutRegNos()!=null && !scForm.getStudentsWithoutRegNos().isEmpty()){
			List<String> listApplnNos=scForm.getStudentsWithoutRegNos();
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSelectedSmartCardFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScLostCorrectionProcessAction - getAllSmartCardFiles");
		ScLostCorrectionProcessForm scForm = (ScLostCorrectionProcessForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		if (errors.isEmpty()) {
			try {
				
				List<Integer> idList = ScLostCorrectionProcessHandler.getInstance().getAnySelected(scForm);
				if(idList==null || idList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.smartcard.lostcorrection.view.selectany.download"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
				}
				if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
					Map<Integer,List<StudentTO>> studentSmartCardData = ScLostCorrectionProcessHandler.getInstance().getListOfAllStudents(scForm, idList);
					if(scForm.getStudentIds()!=null && !scForm.getStudentIds().isEmpty())
						checkErrors(scForm,errors,messages);
					if (studentSmartCardData.isEmpty()) {
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.interview.norecords"));
						saveErrors(request, errors);
						log.info("Exit ScLostCorrectionProcessAction - getAllSmartCardFiles size 0");
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
					} 
					scForm.setSmartCardData(studentSmartCardData);
				}
				else{
					Map<Integer,List<EmployeeTO>> employeeSmartCardData = ScLostCorrectionProcessHandler.getInstance().getListOfAllEmployees(scForm, idList);
					if(scForm.getEmployeeIds()!=null && !scForm.getEmployeeIds().isEmpty())
						checkErrorsEmployee(scForm,errors,messages);
					if (employeeSmartCardData.isEmpty()) {
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.interview.norecords"));
						saveErrors(request, errors);
						log.info("Exit ScLostCorrectionViewAction - getAllSmartCardFiles size 0");
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
					} 
					scForm.setSmartCardDataEmployee(employeeSmartCardData);
				}
				
				scForm.setPrint(true);
				scForm.setDownloadExcel(null);
				saveErrors(request, errors);
				saveMessages(request, messages);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit ScLostCorrectionProcessAction - getAllSmartCardFiles errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
		}
		
		log.info("Exit ScLostCorrectionProcessAction - getStudentSmartCardData");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ScLostCorrectionProcessForm scForm = (ScLostCorrectionProcessForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		if (errors.isEmpty()) {
			try {
				
				List<Integer> idList = ScLostCorrectionProcessHandler.getInstance().getAnySelected(scForm);
				if(idList==null || idList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.smartcard.lostcorrection.view.selectany.exporttoexcel"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
				}
				
				boolean isUpdated =	ScLostCorrectionProcessHandler.getInstance().exportTOExcel(scForm, request, idList);
				
				if(isUpdated){
					scForm.setPrint(false);
					scForm.setDownloadExcel("download");
					ActionMessage message = new ActionMessage("knowledgepro.smartcard.lostcorrection.view.reports.columnsUpdate");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			saveErrors(request, errors);
		}
		log.info("Exit ScLostCorrectionProcessAction - exportToExcel");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_PROCESS_DETAILS);
	}
	
	/**
	 * @param scForm
	 * @param errors
	 * @param messages
	 * @throws Exception
	 */
	private void checkErrorsEmployee(ScLostCorrectionProcessForm scForm, ActionErrors errors, ActionMessages messages) throws Exception{
		 
		ScLostCorrectionProcessHandler.getInstance().getEmployeesWithoutPhotosAndRegNos(scForm);
		String noPhotos="";
		String noFingerPrintIds="";
		if(scForm.getEmployeesWithoutPhotos()!=null && !scForm.getEmployeesWithoutPhotos().isEmpty()){
			List<String> list=scForm.getEmployeesWithoutPhotos();
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
		if(scForm.getEmployeesWithoutFingerPrintIds()!=null && !scForm.getEmployeesWithoutFingerPrintIds().isEmpty()){
			List<String> listFirstNames=scForm.getEmployeesWithoutFingerPrintIds();
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
