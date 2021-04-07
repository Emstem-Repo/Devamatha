package com.kp.cms.actions.examallotment;

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
import com.kp.cms.forms.examallotment.ExamRoomAllotmentGroupWiseForm;
import com.kp.cms.handlers.examallotment.ExamRoomAllotmentGroupWiseHandler;
import com.kp.cms.to.examallotment.ExamRoomAllotmentGroupWiseTo;

public class ExamRoomAllotmentGroupWiseAction extends BaseDispatchAction {

	
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentGroupWiseAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamRoomAllotmentGroupWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		allotmentGroupWiseForm.reset();
		setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm,request);
		
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}

	/**
	 * @param allotmentGroupWiseForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredGroupWiseDataToToForm(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm,
			HttpServletRequest request) throws Exception {
		Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> groupWiseMap=ExamRoomAllotmentGroupWiseHandler.getInstance().getCourseDetailsByMidOrEndAndSchemeNo(allotmentGroupWiseForm,"M",1);
		allotmentGroupWiseForm.setGroupWiseMap(groupWiseMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExamRoomAllotGroupWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentGroupWiseForm);
		ActionErrors errors=allotmentGroupWiseForm.validate(mapping, request);
		if(errors.isEmpty()){
	    	try{
	    		if(allotmentGroupWiseForm.getSelectedCourses()==null || allotmentGroupWiseForm.getSelectedCourses().isEmpty()){
	    			errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.wise.settings.course.required"));
	    			saveErrors(request, errors);
	    		}else{
	    		boolean isAdded=false;
	    		isAdded=ExamRoomAllotmentGroupWiseHandler.getInstance().addExamRoomAllotMentGroupWise(allotmentGroupWiseForm);
	    		if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.group.wise.addsuccess"));
					saveMessages(request, messages);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm, request);
					log.info("added addExamRoomAllotGroupWise method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.group.wise.addfailure"));
					addErrors(request, errors);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm,request);
				}
	    		}
		    	} catch (Exception e) {
				log.error("Error occured in addExamRoomAllotGroupWise", e);
				String msg = super.handleApplicationException(e);
				allotmentGroupWiseForm.setErrorMessage(msg);
				allotmentGroupWiseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editGroupWiseAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		try{
			ExamRoomAllotmentGroupWiseHandler.getInstance().editGroupWiseAllotment(allotmentGroupWiseForm);
			allotmentGroupWiseForm.setIsDisable(true);
			request.setAttribute("RoomAllotmentGroupWiseOperation", "edit");
		} catch (Exception e) {
			log.error("Error occured in editGroupWiseAllotment", e);
			String msg = super.handleApplicationException(e);
			allotmentGroupWiseForm.setErrorMessage(msg);
			allotmentGroupWiseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExamRoomAllotGroupWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentGroupWiseForm);
		ActionErrors errors=allotmentGroupWiseForm.validate(mapping, request);
		if(errors.isEmpty()){
	    	try{
	    		String mode="Update";
	    		boolean isUpdated=false;
	    		isUpdated=ExamRoomAllotmentGroupWiseHandler.getInstance().updateExamRoomAllotGroupWise(allotmentGroupWiseForm,mode);
	    		if (isUpdated) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.group.wise.update.success"));
					saveMessages(request, messages);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm, request);
					log.info("added updateExamRoomAllotGroupWise method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.group.wise.update.failure"));
					addErrors(request, errors);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm,request);
				}
		    	} catch (Exception e) {
				log.error("Error occured in updateExamRoomAllotGroupWise", e);
				String msg = super.handleApplicationException(e);
				allotmentGroupWiseForm.setErrorMessage(msg);
				allotmentGroupWiseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRoomAllotGroupWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, allotmentGroupWiseForm);
		String mode="Delete";
	    	try{
	    		boolean isDeleted=false;
	    		isDeleted=ExamRoomAllotmentGroupWiseHandler.getInstance().updateExamRoomAllotGroupWise(allotmentGroupWiseForm, mode);
	    		if (isDeleted) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.exam.allotment.group.wise.deleted.success"));
					saveMessages(request, messages);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm, request);
					log.info("added deleteRoomAllotGroupWise method success");
				} else {
					errors.add("error", new ActionError("knowledgepro.exam.allotment.group.wise.delete.failure"));
					addErrors(request, errors);
					allotmentGroupWiseForm.reset();
					setRequiredGroupWiseDataToToForm(allotmentGroupWiseForm, request);
				}
		    	} catch (Exception e) {
				log.error("Error occured in deleteRoomAllotGroupWise", e);
				String msg = super.handleApplicationException(e);
				allotmentGroupWiseForm.setErrorMessage(msg);
				allotmentGroupWiseForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCourseListByMidOrEndAndSchemeNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm=(ExamRoomAllotmentGroupWiseForm) form;
		try{
			Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> groupWiseMap=ExamRoomAllotmentGroupWiseHandler.getInstance().getCourseDetailsByMidOrEndAndSchemeNo(allotmentGroupWiseForm,allotmentGroupWiseForm.getMidOrEndSem(),Integer.parseInt(allotmentGroupWiseForm.getSchemeNo()));
			allotmentGroupWiseForm.setGroupWiseMap(groupWiseMap);
		} catch (Exception e) {
			log.error("Error occured in deleteRoomAllotGroupWise", e);
			String msg = super.handleApplicationException(e);
			allotmentGroupWiseForm.setErrorMessage(msg);
			allotmentGroupWiseForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_EXAM_ROOM_ALLOTMENT_GROUP_WISE);
	}
}
