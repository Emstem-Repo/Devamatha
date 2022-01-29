package com.kp.cms.actions.admission;

import java.util.List;

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
import com.kp.cms.forms.admission.GroupCourseEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.GroupCourseEntryHandler;
import com.kp.cms.handlers.admission.GroupEntryHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CCGroupTo;

/**
 * @author user
 *
 */
public class GroupCourseEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(GroupCourseEntryAction.class);
	
	
	/**
	 * setting the required data to the request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGetGroupCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GroupCourseEntryForm groupCourseEntryForm=(GroupCourseEntryForm)form;
		log.info("Entering into initGetDocumentExams in GroupEntryAction");
		try{
			groupCourseEntryForm.resetFields();
			setRequestedDataToForm(groupCourseEntryForm);
			setUserId(request, groupCourseEntryForm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in GroupEntryAction", e);
			String msg = super.handleApplicationException(e);
			groupCourseEntryForm.setErrorMessage(msg);
			groupCourseEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Exit from initGetDocumentExams in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUP_COURSE);
	}


	/**
	 * @param groupCourseEntryForm
	 */
	private void setRequestedDataToForm( GroupCourseEntryForm groupCourseEntryForm) throws Exception {
		
		List<CCGroupTo> groupToList=GroupEntryHandler.getInstance().getGroupList();
		groupCourseEntryForm.setGroupToList(groupToList);
		
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		groupCourseEntryForm.setProgramTypeList(programTypeList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCourses(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		GroupCourseEntryForm groupCourseEntryForm=(GroupCourseEntryForm)form;
		 ActionErrors errors = groupCourseEntryForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			try {
				GroupCourseEntryHandler.getInstance().getListOfCourse(groupCourseEntryForm);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				groupCourseEntryForm.setErrorMessage(msg);
				groupCourseEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_GET_GROUP_COURSE);
		}
		setRequestedDataToForm(groupCourseEntryForm);			
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_GET_GROUP_COURSE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addGroupCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addGroup in GroupEntryAction");
		GroupCourseEntryForm groupCourseEntryForm=(GroupCourseEntryForm)form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = groupCourseEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try{
				boolean isGroupAdded= GroupCourseEntryHandler.getInstance().addGroupCourse(groupCourseEntryForm);
				if(isGroupAdded){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.CCGroup.addsuccess",""));
					saveMessages(request, messages);
					groupCourseEntryForm.resetFields();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.CCGroup.addfailure",""));
					addErrors(request, errors);
				}
			
			}catch (Exception e) {
				log.error("Error occured in Group Entry Action", e);
					String msg = super.handleApplicationException(e);
					groupCourseEntryForm.setErrorMessage(msg);
					groupCourseEntryForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequestedDataToForm(groupCourseEntryForm);
		log.info("Exit from addGroup in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUP_COURSE); 
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroupCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into addGroup in GroupEntryAction");
		GroupCourseEntryForm groupCourseEntryForm=(GroupCourseEntryForm)form;
		
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = groupCourseEntryForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try{
				boolean isGroupCourseDelete= GroupCourseEntryHandler.getInstance().deleteGroupCourse(groupCourseEntryForm,"delete");
				if(isGroupCourseDelete){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admin.CCGroup.addsuccess",""));
					saveMessages(request, messages);
					groupCourseEntryForm.resetFields();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.CCGroup.addfailure",""));
					addErrors(request, errors);
				}
			
			}catch (Exception e) {
				log.error("Error occured in Group Entry Action", e);
				String msg = super.handleApplicationException(e);
				groupCourseEntryForm.setErrorMessage(msg);
				groupCourseEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequestedDataToForm(groupCourseEntryForm);
		log.info("Exit from addGroup in GroupEntryAction");
		return mapping.findForward(CMSConstants.INIT_GET_GROUP_COURSE); 
	}
}