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
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.handlers.employee.EmployeeReportHandler;
import com.kp.cms.handlers.smartcard.ScLostCorrectionViewHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionViewTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionViewAction extends BaseDispatchAction {
	
	private static final Log log=LogFactory.getLog(ScLostCorrectionViewAction.class);
	
	public ActionForward initScLostCorrectionView(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
	{
		log.info("start of initScLostCorrection method in ScLostCorrectionViewAction class.");
		ScLostCorrectionViewForm scForm=(ScLostCorrectionViewForm) form;
		scForm.clearAll();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		
		log.debug("Leaving initScLostCorrectionView");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responce
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScLostCorrectionViewSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
	throws Exception{
		
		ScLostCorrectionViewForm scForm=(ScLostCorrectionViewForm) form;
		ActionErrors errors=new ActionErrors();

			try{
				List<ScLostCorrectionViewTO> scView = ScLostCorrectionViewHandler.getInstance().getDetailsList(scForm);
				
				if(scView==null || scView.isEmpty() || scView.size()==0){
					errors.add("error", new ActionError( "knowledgepro.norecords"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW);
				}
				else{
					scForm.setScList(scView);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}
		addErrors(request, errors);
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW);
		
	}
		
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllSmartCardFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScLostCorrectionViewAction - getAllSmartCardFiles");
		ScLostCorrectionViewForm scForm = (ScLostCorrectionViewForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		
		if (errors.isEmpty()) {
			try {
				
				List<Integer> idList = ScLostCorrectionViewHandler.getInstance().getAnySelected(scForm);
				if(idList==null || idList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.smartcard.lostcorrection.view.selectany.download"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
				}
				
				if(scForm.getIsEmployee().equalsIgnoreCase("Student")){
					Map<Integer,List<StudentTO>> studentSmartCardData = ScLostCorrectionViewHandler.getInstance().getListOfAllStudents(scForm, idList);
					if(scForm.getStudentIds()!=null && !scForm.getStudentIds().isEmpty())
						checkErrors(scForm,errors,messages);
					if (studentSmartCardData.isEmpty()) {
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.interview.norecords"));
						saveErrors(request, errors);
						log.info("Exit ScLostCorrectionViewAction - getAllSmartCardFiles size 0");
						return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
					} 
					scForm.setSmartCardData(studentSmartCardData);
				}
				else{
					Map<Integer,List<EmployeeTO>> employeeSmartCardData = ScLostCorrectionViewHandler.getInstance().getListOfAllEmployees(scForm, idList);
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
			log.info("Exit ScLostCorrectionViewAction - getAllSmartCardFiles errors not empty ");
			return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
		}
		
		log.info("Exit ScLostCorrectionAction - getStudentSmartCardData");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
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
		
		log.info("Entered ScLostCorrectionViewAction - setChangeStatus");
		ScLostCorrectionViewForm scForm = (ScLostCorrectionViewForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		
			try {
				boolean isStatusChanged;
				
				if(scForm.getStatus2().equalsIgnoreCase("Reject") && scForm.getReasonForRejection().isEmpty()){
					
					errors.add("error", new ActionError( "knowledgepro.smartcard.lostcorrection.view.reasonforrejection.needed"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
					
				}
				isStatusChanged = ScLostCorrectionViewHandler.getInstance().setStatus(scForm);
				//If add operation is success then display the success message.
				if(isStatusChanged)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.SC_LOST_CORRECTION_VIEW_ADD_SUCCESS));
					saveMessages(request, messages);
					List<ScLostCorrectionViewTO> scView = ScLostCorrectionViewHandler.getInstance().getDetailsListAfter(scForm);
					scForm.setScList(scView);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
				}
				//If add operation is failure then add the error message.
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.SC_LOST_CORRECTION_VIEW_ADD_FAIL));
					saveErrors(request, errors);
					}
				}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				scForm.setErrorMessage(msg);
				scForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		
		log.info("Exit ScLostCorrectionViewAction - setChangeStatus");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
	}
	
	/**
	 * @param genscDataForm
	 * @param errors
	 * @param messages
	 */
	private void checkErrors(ScLostCorrectionViewForm scForm, ActionErrors errors, ActionMessages messages) throws Exception{
		
		ScLostCorrectionViewHandler.getInstance().getStudentsWithoutPhotosAndRegNos(scForm);
		String noPhotos="";
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
		
		if(noPhotos !=null && !noPhotos.isEmpty()){
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.successfullWithMsg"));	
			noPhotos=CommonUtil.splitString(noPhotos,56);
			messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.admission.smartCardDataDownload.withoutPhotos",noPhotos));	
			
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
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ScLostCorrectionViewForm scForm = (ScLostCorrectionViewForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		scForm.setPrint(false);
		scForm.setDownloadExcel(null);
		setUserId(request, scForm);
		
		if (errors.isEmpty()) {
			try {
				
				List<Integer> idList = ScLostCorrectionViewHandler.getInstance().getAnySelected(scForm);
				if(idList==null || idList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.smartcard.lostcorrection.view.selectany.exporttoexcel"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
				}
				
				boolean isUpdated =	ScLostCorrectionViewHandler.getInstance().exportTOExcel(scForm, request, idList);
				
				if(isUpdated){
					scForm.setPrint(false);
					scForm.setDownloadExcel("download");
					ActionMessage message = new ActionMessage("knowledgepro.smartcard.lostcorrection.view.reports.columnsUpdate");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.classentry.loaderror"));
					saveErrors(request, errors);
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
		
		log.info("Exit ScLostCorrectionViewAction - exportToExcel");
		return mapping.findForward(CMSConstants.INIT_SC_LOST_CORRECTION_VIEW_DETAILS);
	}
	
	/**
	 * @param scForm
	 * @param errors
	 * @param messages
	 * @throws Exception
	 */
	private void checkErrorsEmployee(ScLostCorrectionViewForm scForm, ActionErrors errors, ActionMessages messages) throws Exception{
		 
		ScLostCorrectionViewHandler.getInstance().getEmployeesWithoutPhotosAndRegNos(scForm);
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
