package com.kp.cms.actions.attendance;

import java.util.List;

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
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceTypeForm;
import com.kp.cms.handlers.attendance.AttendanceTypeHandler;
import com.kp.cms.to.attendance.AttendanceTypeTO;

	
@SuppressWarnings("deprecation")
public class AttendanceTypeAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(AttendanceTypeAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes Attendance Type page
	 * @throws Exception
	 */
	
	public ActionForward initAttendanceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initAttendanceType of AttendanceTypeAction");
		AttendanceTypeForm attendanceTypeForm = (AttendanceTypeForm) form;
		try {
			//Assigns all the records of attendance type to from
			assignListToForm(attendanceTypeForm);
			attendanceTypeForm.clear();
			//Checks for the session and clearing
			HttpSession session=request.getSession(false);
			if(session!=null){
				session.removeAttribute(CMSConstants.OPERATION);
			}
		} catch (Exception e) {
				log.error("Error occured in initAttendanceType of AttendanceTypeAction",e);
				String msg = super.handleApplicationException(e);
				attendanceTypeForm.setErrorMessage(msg);
				attendanceTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving into initAttendanceType of AttendanceTypeAction");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns Used to insert attendanceType
	 * @throws Exception
	 */
	public ActionForward saveAttendanceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into saveAttendanceType of AttendanceTypeAction");
		AttendanceTypeForm attendanceTypeForm=(AttendanceTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attendanceTypeForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
			setUserId(request, attendanceTypeForm);
			boolean isAdded;
			//Get all the record from attendance Type
			List<AttendanceTypeTO> attendanceTypeList=AttendanceTypeHandler.getInstance().getAttendanceTypesAll();
			if(attendanceTypeList!=null && !attendanceTypeList.isEmpty()){
				String attendanceTypeName=attendanceTypeForm.getAttendanceType();
				//If records are there then check for duplicate of attendanceType Name
				AttendanceType type=AttendanceTypeHandler.getInstance().getAttendanceTypeDetailsonAttendanceType(attendanceTypeName);
				//If any record found for that attenndance type then append the error message.
				if(type != null){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_NAME_EXISTS));
					assignListToForm(attendanceTypeForm);
					saveErrors(request, errors);
					assignListToForm(attendanceTypeForm);
					return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
				}
				//Check for default value
				//If any record exists with default value yes then it should not allow to add more with default yes
				if(attendanceTypeForm.isDefaultValue()){
				AttendanceType attendanceType= AttendanceTypeHandler.getInstance().getAttendanceTypeOnDefault(attendanceTypeForm.isDefaultValue());
				//If any record found with default value yes, then append the appropriate error message
					if(attendanceType != null){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_EXISTS));
						assignListToForm(attendanceTypeForm);
						saveErrors(request, errors);
						assignListToForm(attendanceTypeForm);
						return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
					}
				}
				//Request the handler method to insert the attendance type				
				isAdded=AttendanceTypeHandler.getInstance().saveAttendanceType(attendanceTypeForm);
				//If insert is success then append the success message else add the error message.
				if (isAdded)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_ADD_SUCCESS));
					attendanceTypeForm.clear();
					assignListToForm(attendanceTypeForm);
				}	
			}	
			else{
			//Request the handler method to insert the attendance type		
			isAdded=AttendanceTypeHandler.getInstance().saveAttendanceType(attendanceTypeForm);
			//If insert is success then append the success message else add the error message.
				if (isAdded)
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_ADD_SUCCESS));
					attendanceTypeForm.clear();
					assignListToForm(attendanceTypeForm);
				}
			}
			}
		} catch (Exception e) {
			log.error("Error occured in saveAttendanceType AttendanceTypeAction",e);
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_ADD_FAILURE));
			assignListToForm(attendanceTypeForm);
		}
		saveErrors(request, errors);
		saveMessages(request, messages);
		assignListToForm(attendanceTypeForm);
		log.info("Leaving into saveAttendanceType of AttendanceTypeAction");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
	}
	
	/**
	 * @param attendanceTypeForm
	 * Returns all the Attendance Type exists in DB (Both default value yes and no)
	 * @throws Exception
	 */
	public void assignListToForm(AttendanceTypeForm attendanceTypeForm) throws Exception
	{		
		log.info("Inside of assignListToForm of AttendanceTypeAction");
		/**
		 * Get all the Attendance Type available in DB
		 */
		List<AttendanceTypeTO> attendanceTypeList=AttendanceTypeHandler.getInstance().getAttendanceTypesAll();
		//Set the list to form too.
		attendanceTypeForm.setAttendanceTypeList(attendanceTypeList);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Used to delete attendanceType
	 * @throws Exception
	 */

	public ActionForward deleteAttendanceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into deleteAttendanceType of AttendanceTypeAction");
		AttendanceTypeForm attendanceTypeForm = (AttendanceTypeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			boolean isDeleted;
			//Get the Id from formbean and request the handler method to delete the same
			isDeleted=AttendanceTypeHandler.getInstance().deleteAttendanceType(attendanceTypeForm.getId(),false,attendanceTypeForm);
			//If delete operation is success then appened the success message otherwise append the error message.
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.ATTANDANCE_TYPE_DELETE_SUCCESS));
				attendanceTypeForm.clear();
				assignListToForm(attendanceTypeForm);
			}
		} catch (Exception e) {
			if (e instanceof ApplicationException) {
				assignListToForm(attendanceTypeForm);
				return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
			}
			else{
			log.error("Error occured in deleteAttendanceType AttendanceTypeAction",e);
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_DELETE_FAILURE));
			assignListToForm(attendanceTypeForm);
			}
		}
		saveErrors(request, errors);
		saveMessages(request, messages);
		log.info("Leaving into deleteAttendanceType of AttendanceTypeAction");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Used to edit the attendanceType
	 * @return Populates the fields
	 * @throws Exception
	 */

	public ActionForward editAttendanceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editAttendanceType of AttendanceTypeAction");
		AttendanceTypeForm attendanceTypeForm=(AttendanceTypeForm)form;
		attendanceTypeForm.setMandatory(null);
		//Take the id from formbean and get the details from DB and set to formbean		
		try {
			AttendanceTypeHandler.getInstance().getDetailsById(attendanceTypeForm);
			assignListToForm(attendanceTypeForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
				log.error("Error occured in editAttendanceType of AttendanceTypeAction",e);
				String msg = super.handleApplicationException(e);
				attendanceTypeForm.setErrorMessage(msg);
				attendanceTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving into editAttendanceType of AttendanceTypeAction");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Used to update the attendanceType
	 * @returns updated values
	 * @throws Exception
	 */

	public ActionForward updateAttendanceType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateAttendanceType of AttendanceTypeAction");
		AttendanceTypeForm attendanceTypeForm=(AttendanceTypeForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = attendanceTypeForm.validate(mapping, request);
		try {
			if(isCancelled(request)){
				AttendanceTypeHandler.getInstance().getDetailsById(attendanceTypeForm);
				assignListToForm(attendanceTypeForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				assignListToForm(attendanceTypeForm);
				return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
			}
			if (errors.isEmpty()) {
				setUserId(request, attendanceTypeForm);
				boolean isUpdated;
				String oldName=attendanceTypeForm.getOldName();
				String newName=attendanceTypeForm.getAttendanceType();
				boolean oldDefaultValue = attendanceTypeForm.isOldDefaultValue();
				boolean newDefaultValue = attendanceTypeForm.isDefaultValue();
				//If old attendanceType is not same with the new attendanceType then check for the duplicate if exists.
				if(!oldName.equalsIgnoreCase(newName)&& oldDefaultValue == newDefaultValue){
					//Check for duplicate of attendanceTypeName
					AttendanceType type=AttendanceTypeHandler.getInstance().getAttendanceTypeDetailsonAttendanceType(newName);
					//If any record exists with the entered attendanceType then add the error message.
						if(type != null){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_NAME_EXISTS));
							assignListToForm(attendanceTypeForm);
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
						}
						else{
							//Requests the handler to update the attendancetype
							isUpdated=AttendanceTypeHandler.getInstance().updateAttendanceType(attendanceTypeForm);
							//If success then append the success message
									if (isUpdated)
									{
										messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_UPDATE_SUCCESS));
										attendanceTypeForm.clear();
										saveMessages(request, messages);
										assignListToForm(attendanceTypeForm);
										return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
									}
									//If failure append the error message
									else{
										errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.attendancetype.update.failure"));
										assignListToForm(attendanceTypeForm);
										saveErrors(request, errors);
										request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
										return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
									}
							}
					}
				//In update mode checking if the user keeps the name and defaut as it was then update the same 
				if(oldName.equalsIgnoreCase(newName) && oldDefaultValue == newDefaultValue){
					//Requests the handler to update the attendancetype
					isUpdated=AttendanceTypeHandler.getInstance().updateAttendanceType(attendanceTypeForm);
					//If success then append the success message
						if (isUpdated)
						{
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_UPDATE_SUCCESS));
							attendanceTypeForm.clear();
							saveMessages(request, messages);
							assignListToForm(attendanceTypeForm);
							return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
						}
						//If failure append the error message
						else{
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.attendancetype.update.failure"));
							assignListToForm(attendanceTypeForm);
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
						}
				}
				
					//Condition is used to check if default is only true. Then check for the duplicate
				else if(attendanceTypeForm.isDefaultValue() == true){
						AttendanceType attendanceType= AttendanceTypeHandler.getInstance().getAttendanceTypeOnDefault(attendanceTypeForm.isDefaultValue());
						//If duplicate found then add the error message.
							if(attendanceType != null){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTANDANCE_TYPE_EXISTS));
								assignListToForm(attendanceTypeForm);
								saveErrors(request, errors);
								request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
								return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
							}
							
							//Requests the handler to update the attendancetype
							isUpdated=AttendanceTypeHandler.getInstance().updateAttendanceType(attendanceTypeForm);
							//If success then append the success message
								if (isUpdated)
								{
									messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_UPDATE_SUCCESS));
									attendanceTypeForm.clear();
									saveMessages(request, messages);
									assignListToForm(attendanceTypeForm);
									return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
								}
								//If failure append the error message
								else{
									errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.attendancetype.update.failure"));
									assignListToForm(attendanceTypeForm);
									saveErrors(request, errors);
									request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
									return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
								}
					}
				//Otherwise update the attendancetype
				else{
					isUpdated=AttendanceTypeHandler.getInstance().updateAttendanceType(attendanceTypeForm);
						if (isUpdated)
						{
							messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ATTANDANCE_TYPE_UPDATE_SUCCESS));
							attendanceTypeForm.clear();
							saveMessages(request, messages);
							assignListToForm(attendanceTypeForm);
							return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
						}
						else{
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.attendancetype.update.failure"));
						}
				}					
			}
		} catch (Exception e) {
			log.error("Error occured in updateAttendanceType AttendanceTypeAction",e);
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.attendancetype.update.failure"));
			assignListToForm(attendanceTypeForm);
		}
		assignListToForm(attendanceTypeForm);
		saveErrors(request, errors);
		saveMessages(request, messages);
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		log.info("Leaving into updateAttendanceType of AttendanceTypeAction");
		return mapping.findForward(CMSConstants.INIT_ATTENDANCETYPE);
	}		
}
