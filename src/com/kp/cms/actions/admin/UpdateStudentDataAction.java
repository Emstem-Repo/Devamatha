package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.UpdateStudentDataForm;
import com.kp.cms.handlers.admin.UpdateStudentDataHandler;
import com.kp.cms.to.admin.UpdateStudentDataTO;

public class UpdateStudentDataAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UpdateStudentDataAction.class);
	
	public ActionForward initUpdateStudentData(ActionMapping mapping,ActionForm form,HttpServletRequest
			request,HttpServletResponse response) throws Exception {
		UpdateStudentDataForm updateStudentDataForm = (UpdateStudentDataForm)form;
		updateStudentDataForm.clear();
		return mapping.findForward(CMSConstants.INIT_UPDATE_STUDENT_DATA);
	}
	
	public ActionForward getStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		UpdateStudentDataForm updateStudentDataForm = (UpdateStudentDataForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors error = updateStudentDataForm.validate(mapping, request);
		List<UpdateStudentDataTO> studentList = null;
		try{
			int classId = Integer.parseInt(updateStudentDataForm.getClassId());
			String className = UpdateStudentDataHandler.getInstance().getClassName(updateStudentDataForm);
			updateStudentDataForm.setClassName(className);
			studentList = UpdateStudentDataHandler.getInstance().getStudentLIst(updateStudentDataForm);
			if(studentList == null || studentList.isEmpty()){
				studentList = UpdateStudentDataHandler.getInstance().getPrevStudentList(updateStudentDataForm);
			}
			updateStudentDataForm.setStudentList(studentList);
			if (studentList==null || studentList.isEmpty()) {
				error.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, error);
				log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
				return mapping.findForward(CMSConstants.INIT_UPDATE_STUDENT_DATA);
		} 
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			updateStudentDataForm.setErrorMessage(msg);
			updateStudentDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_UPDATE_STUDENT_DATA_RESULTS);
	}
	
	public ActionForward updateStudentData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UpdateStudentDataForm updateStudentDataForm = (UpdateStudentDataForm)form;
		ActionErrors errors = updateStudentDataForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try{
			List<UpdateStudentDataTO> list = updateStudentDataForm.getStudentList();
			boolean update = UpdateStudentDataHandler.getInstance().updateStudentData(list,updateStudentDataForm);
			if(update){
				 ActionMessage message = new ActionMessage("knowledgepro.admin.updateStudentsuccess"," Student data");
					messages.add("messages", message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_UPDATE_STUDENT_DATA);
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.updateStudentfailure", "Student data"));
				saveErrors(request,errors);
				return mapping.findForward(CMSConstants.INIT_UPDATE_STUDENT_DATA);
			}
			
		}catch (Exception exception) {
			exception.printStackTrace();
			String msg = super.handleApplicationException(exception);
			updateStudentDataForm.setErrorMessage(msg);
			updateStudentDataForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);	
		}
	}
}
