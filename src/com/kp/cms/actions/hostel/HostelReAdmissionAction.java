package com.kp.cms.actions.hostel;

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
import com.kp.cms.forms.hostel.HostelReAdmissionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelReAdmissionHandler;
import com.kp.cms.handlers.hostel.ReadmissionSelectionUploadHandler;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.HostelReAdmissionTo;

@SuppressWarnings("deprecation")
public class HostelReAdmissionAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(HostelReAdmissionAction.class);
   
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelReAdmission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelReAdmissionForm reAdmissionForm=(HostelReAdmissionForm) form;
		reAdmissionForm.resetFields();
		setRequiredDataToForm(reAdmissionForm);
		log.info("Entered in initHostelReAdmission Method");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_READMISSION_SELECTION);
	}

	/**
	 * @param reAdmissionForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(HostelReAdmissionForm reAdmissionForm) throws Exception {
		Map<Integer, String> hostelMap=CommonAjaxHandler.getInstance().getHostel();
		reAdmissionForm.setHostelMap(hostelMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentByHostelIdAndYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelReAdmissionForm admissionForm=(HostelReAdmissionForm) form;
		ActionErrors errors=new ActionErrors();
		setUserId(request, admissionForm);
		try{
			List<HostelReAdmissionTo> admissionTos=HostelReAdmissionHandler.getInstance().getStudentDetailsByHosteIdAndYear(admissionForm);
			if(admissionTos!=null && !admissionTos.isEmpty()){
				admissionForm.setReAdmissionToList(admissionTos);
				Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance().getRoomTypesByHostel(Integer.parseInt(admissionForm.getHostelIdForReAdm()));
				if(roomTypeMap!=null && !roomTypeMap.isEmpty()){
					admissionForm.setRoomTypeMap(roomTypeMap);
				}
				request.setAttribute("operation", "continueToUpdate");
			}else{
				errors.add("error", new ActionError("knowledgepro.norecords"));
				addErrors(request, errors);
			}
		   } catch (Exception e) {
				log.error("error in method searchStudentByHostelIdAndYear...", e);
				String msg = super.handleApplicationException(e);
				admissionForm.setErrorMessage(msg);
				admissionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_READMISSION_SELECTION);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateHostelOnlineApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HostelReAdmissionForm admissionForm=(HostelReAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		setUserId(request, admissionForm);
		try{
			boolean isUpdated=HostelReAdmissionHandler.getInstance().updateHostelOnlineApplication(admissionForm);
			if(isUpdated){
				//sending SMS and Email to selected students added by chandra
				List<HostelOnlineApplicationTo> updatedStudentList=admissionForm.getUpdatedStudentList();
				if(updatedStudentList!=null && !updatedStudentList.isEmpty()){
					ReadmissionSelectionUploadHandler.getInstance().sendingSMSToStudents(updatedStudentList);
					ReadmissionSelectionUploadHandler.getInstance().sendingMailToStudents(updatedStudentList);
				}
				
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.hostel.readmission.selection.online.application.updated.success"));
				saveMessages(request, messages);
				admissionForm.resetFields();
				setRequiredDataToForm(admissionForm);
			}else{
				errors.add("error", new ActionError("knowledgepro.hostel.readmission.selection.online.application.updated.failed"));
				addErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("error in method updateHostelOnlineApplication...", e);
			String msg = super.handleApplicationException(e);
			admissionForm.setErrorMessage(msg);
			admissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_READMISSION_SELECTION);
	}
}
