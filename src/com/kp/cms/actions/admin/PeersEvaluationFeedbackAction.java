package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackForm;
import com.kp.cms.handlers.admin.PeersEvaluationFeedbackHandler;
import com.kp.cms.handlers.admin.PeersEvaluationFeedbackInstructionsHandler;
import com.kp.cms.to.admin.PeersEvaluationFeedbackTO;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;

public class PeersEvaluationFeedbackAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPeersEvaluationFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackForm peersEvaluationFeedbackForm =  (PeersEvaluationFeedbackForm)form;
		setUserId(request, peersEvaluationFeedbackForm);
		HttpSession session = request.getSession(false);
		/* All Properties in the formbean are clearing here  
		  */
		peersEvaluationFeedbackForm.setDeptId(0);
		peersEvaluationFeedbackForm.setDepartmentName(null);
		peersEvaluationFeedbackForm.setPeerId(0);
		peersEvaluationFeedbackForm.setPeerName(null);
		peersEvaluationFeedbackForm.setPeerGroupId(0);
		peersEvaluationFeedbackForm.setPeersNo(0);
		peersEvaluationFeedbackForm.setPeerEvaluationToList(null);
		peersEvaluationFeedbackForm.setSessionId(null);
		peersEvaluationFeedbackForm.setEvaFacultyQuestionsToList(null);
		peersEvaluationFeedbackForm.setTempPeersEvaList(null);
		peersEvaluationFeedbackForm.setTotalPeers(0);
		peersEvaluationFeedbackForm.setTotalQuestions(0);
		peersEvaluationFeedbackForm.setExist(false);
		peersEvaluationFeedbackForm.setSubmitSuccessfully(false);
		peersEvaluationFeedbackForm.setRemarks(null);
		peersEvaluationFeedbackForm.setOldEvaFacultyQuestionsToList(null);
		peersEvaluationFeedbackForm.setInstructionsTOsList(null);
		peersEvaluationFeedbackForm.setAssignGroupId(0);
		peersEvaluationFeedbackForm.setFacultyGroupIdsMap(null);
		try{
			String sessionId =session.getAttribute("SessionId").toString();
			peersEvaluationFeedbackForm.setSessionId(sessionId);
			String departmentId = session.getAttribute("DepartmentId").toString();
			peersEvaluationFeedbackForm.setDeptId(Integer.parseInt(departmentId));
			String departmentName = session.getAttribute("DepartmentName").toString();
			peersEvaluationFeedbackForm.setDepartmentName(departmentName);
			/* 
			 * Checking that this user is already given Rating to his(or)her department(or)group members
			 * if it is true , then displaying already submitted msg.
			 * else ,getting the Instructions list
			 */
			/*boolean isExist = PeersEvaluationFeedbackHandler.getInstance().alreadySubmittedEvaluation(peersEvaluationFeedbackForm);
			if(isExist){
				peersEvaluationFeedbackForm.setExist(true);
				return mapping.findForward(CMSConstants.ALREADY_EXIST_OR_SUBMITTED_SUCCESSFULLY);
			}else{
				List<StudentFeedbackInstructionsTO> toList = PeersEvaluationFeedbackInstructionsHandler.getInstance().getPeersInstructionsList();
				peersEvaluationFeedbackForm.setInstructionsTOsList(toList);
			}*/
			List<StudentFeedbackInstructionsTO> toList = PeersEvaluationFeedbackInstructionsHandler.getInstance().getPeersInstructionsList();
			peersEvaluationFeedbackForm.setInstructionsTOsList(toList);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			peersEvaluationFeedbackForm.setErrorMessage(msg);
			peersEvaluationFeedbackForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_EVAL_FEEDBACK);
	}
	/** Getting the teachers list according to department(or)AssignGroup based on userId.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTeachersDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackForm peersEvaluationFeedbackForm =  (PeersEvaluationFeedbackForm)form;
		setUserId(request, peersEvaluationFeedbackForm);
		ActionMessages messages = new ActionMessages();
		try{
			/* Getting teachers details list of tos and setting it into the formbean*/
			List<PeersEvaluationFeedbackTO> toList =PeersEvaluationFeedbackHandler.getInstance().getTeachersDetails(peersEvaluationFeedbackForm);
			peersEvaluationFeedbackForm.setPeerEvaluationToList(toList);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationFeedbackForm.setErrorMessage(msg);
			peersEvaluationFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SELECT_PEERS_EVAL_FEEDBACK);
	}
	/** Fetching the questions from the EvaFacultyFeedbackQuestion Bo to give the rating for the teacher.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward startPeersEvaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackForm peersEvaluationFeedbackForm =  (PeersEvaluationFeedbackForm)form;
		setUserId(request, peersEvaluationFeedbackForm);
		ActionMessages messages = new ActionMessages();
		//HttpSession session = request.getSession();
		try{
			peersEvaluationFeedbackForm.setErrorMsg(null);
			peersEvaluationFeedbackForm.setSubmitSuccessfully(false);
			peersEvaluationFeedbackForm.setEvaluationCompleted(false);
			peersEvaluationFeedbackForm.setLastPeer(false);
			/* iterating the list of teachers and picking one teachers details */
			 PeersEvaluationFeedbackHandler.getInstance().setPeerNameAndDepartmentToForm(peersEvaluationFeedbackForm);
			 /* taking the list of questions and putting it into the formbean*/
			 List<EvaFacultyFeedBackQuestionTo> questionTos = PeersEvaluationFeedbackHandler.getInstance().getQuestionListForPeers(peersEvaluationFeedbackForm);
			 peersEvaluationFeedbackForm.setEvaFacultyQuestionsToList(questionTos);
			 if(peersEvaluationFeedbackForm.isEvaluationCompleted()){
				 	peersEvaluationFeedbackForm.setExist(true);
					return mapping.findForward(CMSConstants.ALREADY_EXIST_OR_SUBMITTED_SUCCESSFULLY);
			 }
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationFeedbackForm.setErrorMessage(msg);
			peersEvaluationFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.START_PEERS_EVAL_FEEDBACK);
	}
	/**This method executes once the user giving rating for his department (or) AssignGroup members
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward submitPeersEvaluationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationFeedbackForm peersEvaluationFeedbackForm =  (PeersEvaluationFeedbackForm)form;
		setUserId(request, peersEvaluationFeedbackForm);
		ActionMessages messages = new ActionMessages();
		//HttpSession session = request.getSession();
		try{
			peersEvaluationFeedbackForm.setErrorMsg(null);
			peersEvaluationFeedbackForm.setPreviousEmpName(peersEvaluationFeedbackForm.getPeerName());
			Integer currentPeerNo = null ;
			Integer totalPeerNo = null;
			if(peersEvaluationFeedbackForm.getPeersNo()!=0){
				 currentPeerNo = peersEvaluationFeedbackForm.getPeersNo();
			}
			if(peersEvaluationFeedbackForm.getTotalPeers()!=0){
				 totalPeerNo = peersEvaluationFeedbackForm.getTotalPeers();
			}
			/* putting into the PeersEvaluationFeedbackTO list of each submitted Peer Rating Details and setting that tosList to the form */
			boolean isDuplicate = PeersEvaluationFeedbackHandler.getInstance().storeEachPeerRatingDetails(peersEvaluationFeedbackForm);
			 /*  save the bo into the datebase   */
			if(!isDuplicate){
			boolean isAdded = PeersEvaluationFeedbackHandler.getInstance().savePeersRatingDetailsInToBO(peersEvaluationFeedbackForm);
			if(isAdded){
				/* it will display details of another teacher, once finish the rating given for previous teacher*/ 
				//PeersEvaluationFeedbackHandler.getInstance().setPeerNameAndDepartmentToForm(peersEvaluationFeedbackForm);
				 /*if(peersEvaluationFeedbackForm.isEvaluationCompleted()){
				List<EvaFacultyFeedBackQuestionTo> questionTos = PeersEvaluationFeedbackHandler.getInstance().getQuestionListForPeers(peersEvaluationFeedbackForm);
				peersEvaluationFeedbackForm.setEvaFacultyQuestionsToList(questionTos);
				}else{
					 peersEvaluationFeedbackForm.setSubmitSuccessfully(true);
				}*/
				peersEvaluationFeedbackForm.setSubmitSuccessfully(true);
				peersEvaluationFeedbackForm.setTempPeersEvaList(null);
				
				}
			}else {
				peersEvaluationFeedbackForm.setErrorMsg("Already Feedback has been given for this Faculty "+peersEvaluationFeedbackForm.getPeerName());
			}
			if(totalPeerNo.equals(currentPeerNo)){
				List<PeersEvaluationFeedbackTO> toList =PeersEvaluationFeedbackHandler.getInstance().getTeachersDetails(peersEvaluationFeedbackForm);
				peersEvaluationFeedbackForm.setPeerEvaluationToList(toList);
				peersEvaluationFeedbackForm.setSubmitSuccessfully(false);
				peersEvaluationFeedbackForm.setLastPeer(true);
				//return mapping.findForward(CMSConstants.SELECT_PEERS_EVAL_FEEDBACK);
			}
			peersEvaluationFeedbackForm.setRemarks(null);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationFeedbackForm.setErrorMessage(msg);
			peersEvaluationFeedbackForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.START_PEERS_EVAL_FEEDBACK);
	}
}
