package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.PeersEvaluationFeedback;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackForm;
import com.kp.cms.helpers.admin.PeersEvaluationFeedbackHelper;
import com.kp.cms.to.admin.AssignPeersGroupsTo;
import com.kp.cms.to.admin.PeersEvaluationFeedbackTO;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;
import com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction;
import com.kp.cms.transactionsimpl.admin.PeersEvaluationFeedbackTxnImpl;

public class PeersEvaluationFeedbackHandler {
	public static volatile PeersEvaluationFeedbackHandler handler = null;
	public static PeersEvaluationFeedbackHandler getInstance(){
		if(handler == null){
			handler = new PeersEvaluationFeedbackHandler();
			return handler;
		}
		return handler;
	}
	IPeersEvaluationFeedbackTransaction transaction = PeersEvaluationFeedbackTxnImpl.getInstance();
	/**checking that the teacher is submitted  evaluation or not. 
	 * if submitted return true,
	 * otherwise false
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public boolean alreadySubmittedEvaluation(PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		boolean isSubmitted = transaction.getAlreadySubmittedDetails(peersEvaluationFeedbackForm);
		return isSubmitted;
	}
	/**In this method, if the user is in AssignPeersGroups Bo then fetching the records belonging to his groupId.
	 * otherwise it checks in user table for the faculty who belongs to the user department
	 * and getting that list and setting to the TOList.
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public List<PeersEvaluationFeedbackTO> getTeachersDetails( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		List<PeersEvaluationFeedbackTO> toList = new ArrayList<PeersEvaluationFeedbackTO>();
		List<Integer> listOfFacultyAlreadyEvaluate = transaction.getFacultiesAlreadyEvaluationCompleted(peersEvaluationFeedbackForm);
		String query = PeersEvaluationFeedbackHelper.getInstance().selectQuery(peersEvaluationFeedbackForm);
		/*   newly modified part */
		/* In the below query, checking that the user belongs to how many Groups and getting that list of BO's
		 *  Iterating the List of BO's and fetching the records of faculties in the each Group 
		 *  and finally adding it into the List of  */
		List<AssignPeersGroups> boList = transaction.getEvaluatorDetailsFromAssignPeersGrp(query);
		if(boList!=null && !boList.isEmpty()){
			Iterator<AssignPeersGroups> iterator = boList.iterator();
			List<AssignPeersGroups> list = new ArrayList<AssignPeersGroups>();
			while (iterator.hasNext()) {
				AssignPeersGroups assignPeersGroups = (AssignPeersGroups) iterator .next();
				peersEvaluationFeedbackForm.setAssignGroupId(assignPeersGroups.getPeersEvaluationGroups().getId());
				String assignPeersGroupQuery = PeersEvaluationFeedbackHelper.getInstance().selectQueryList(assignPeersGroups);
				list = transaction.getAssignPeersList(assignPeersGroupQuery,list,peersEvaluationFeedbackForm);
			}
		/*----------------------*/
			/* here checking that the peers are hide,if they are in hidden then that records should not take */
			List<AssignPeersGroups> assignPeersBO = PeersEvaluationFeedbackHelper.getInstance().checkHiddenFaculty(list);
			/* after checking the hide faculty ,converting assignPeersBOlist  to Tolist */
			toList=PeersEvaluationFeedbackHelper.getInstance().convertBOListToTOList(assignPeersBO,listOfFacultyAlreadyEvaluate);
		}else{
			/* checking whether any peer is in the assignPeersGrp with the users department
			 * if there, putting their userid and department in map*/
			Map<Integer,String> usersMap = new HashMap<Integer, String>();
			String query1 = PeersEvaluationFeedbackHelper.getInstance().selectQuery1(peersEvaluationFeedbackForm);
			Map<Integer,Integer> assignPeersGrpMap = transaction.getCheckAssignPeersGrp(query1);
			/*other than user , getting the remaining peers who are belongs to the department and putting in map*/
			String teachersListQuery = PeersEvaluationFeedbackHelper.getInstance().getPeersListQueryFromEmployeeBO(peersEvaluationFeedbackForm);
			usersMap = transaction.getTeacherList(teachersListQuery,usersMap);
			 
			String teachersListQuery1 = PeersEvaluationFeedbackHelper.getInstance().getPeersListFromGuestBO(peersEvaluationFeedbackForm);
			usersMap = transaction.getTeacherList(teachersListQuery1,usersMap);
			
			String teachersListQuery2 = PeersEvaluationFeedbackHelper.getInstance().getPeersListFromUsersBO(peersEvaluationFeedbackForm);
			usersMap = transaction.getTeacherList(teachersListQuery2,usersMap);
			/* here checking that the peers are hide,if they are in hidden then that records should not take */
			Map<Integer,String> users1 = PeersEvaluationFeedbackHelper.getInstance().checkHiddenFaculty1(usersMap,peersEvaluationFeedbackForm);
			/* passing assignPeersGrpMap and users1 map to the helper*/
			toList = PeersEvaluationFeedbackHelper.getInstance().copyTeachersListToTOList(users1,assignPeersGrpMap,peersEvaluationFeedbackForm,listOfFacultyAlreadyEvaluate);
		}
		return toList;
	}
	
	
	/** getting users details from database and putting into the formbean.
	 * @param peersEvaluationFeedbackForm
	 * @throws Exception
	 */
	public void getUserDetails(
			PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)
			throws Exception {
		Users users = transaction.getUserDetails(peersEvaluationFeedbackForm
				.getUserId());

		if (users.getEmployee() != null
				&& !users.getEmployee().toString().isEmpty()) {
			peersEvaluationFeedbackForm.setDeptId(users.getEmployee()
					.getDepartment().getId());
			peersEvaluationFeedbackForm.setDepartmentName(users.getEmployee()
					.getDepartment().getName());
		} else if (users.getGuest() != null && !users.getGuest().toString().isEmpty()) {
			if (users.getGuest().getDepartment() != null && !users.getGuest().getDepartment().toString().isEmpty()) {
				peersEvaluationFeedbackForm.setDeptId(users.getGuest()
						.getDepartment().getId());
				peersEvaluationFeedbackForm.setDepartmentName(users.getGuest()
						.getDepartment().getName());
			}
		} else if (users.getDepartment() != null
				&& !users.getDepartment().toString().isEmpty()) {
			peersEvaluationFeedbackForm
					.setDeptId(users.getDepartment().getId());
			peersEvaluationFeedbackForm.setDepartmentName(users.getDepartment()
					.getName());
		}
	}
	/**iterating the peerEvaluationTOList , takes one record each time and terminating
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public void setPeerNameAndDepartmentToForm(
			PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)
			throws Exception {
		List<PeersEvaluationFeedbackTO> toList = peersEvaluationFeedbackForm .getPeerEvaluationToList();
		boolean evaluationCompleted = true;
		if (toList != null && !toList.isEmpty()) {
			Iterator<PeersEvaluationFeedbackTO> iterator = toList.iterator();
			int totalPeers = 0;
			while (iterator.hasNext()) {
				PeersEvaluationFeedbackTO to = (PeersEvaluationFeedbackTO) iterator .next();
				if (!to.getDone()) {
					toList.remove(to);
					evaluationCompleted = false;
					peersEvaluationFeedbackForm.setPeerId(to.getPeerId());
					peersEvaluationFeedbackForm.setPeerName(to.getPeerName());
					peersEvaluationFeedbackForm.setDepartmentName(to .getDepartmentName());
					List<Integer> totPeersList = to.getTotalPeers();
					Iterator<Integer> iterator2 = totPeersList.iterator();
					while (iterator2.hasNext()) {
						Integer totalNo = (Integer) iterator2.next();
						totalPeers = totalNo;
					}
					peersEvaluationFeedbackForm.setPeersNo(to.getPeersNo());
					peersEvaluationFeedbackForm.setTotalPeers(totalPeers);
					to.setDone(true);
					toList.add(to);
					peersEvaluationFeedbackForm.setPeerEvaluationToList(toList);
					break;
				}
			}
			if(evaluationCompleted){
				peersEvaluationFeedbackForm.setEvaluationCompleted(true);
				
			}
		}
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<EvaFacultyFeedBackQuestionTo> getQuestionListForPeers(
			PeersEvaluationFeedbackForm peersForm) throws Exception {
		String query = PeersEvaluationFeedbackHelper.getInstance() .peersQuestionsQuery();
		List<EvaFacultyFeedbackQuestion> facultyFeedbackQuestionsBo = transaction .peersQuestionsQuery(query);
		List<EvaFacultyFeedBackQuestionTo> questionToList = PeersEvaluationFeedbackHelper .getInstance().copyQuestionBoListTOToList( facultyFeedbackQuestionsBo, peersForm);
		return questionToList;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @throws Exception
	 */
	public boolean storeEachPeerRatingDetails( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		boolean isDuplicate = PeersEvaluationFeedbackHelper.getInstance().tempSavePeerRatingDetails(peersEvaluationFeedbackForm);
		return isDuplicate;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public boolean savePeersRatingDetailsInToBO( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		PeersEvaluationFeedback existBO = transaction.getExistsBO(peersEvaluationFeedbackForm);
		PeersEvaluationFeedback peersEvaluationFeedback= PeersEvaluationFeedbackHelper.getInstance().convertToListTOBo(peersEvaluationFeedbackForm,existBO);
		boolean isAdded = transaction.savePeersEvaluationFeedback(peersEvaluationFeedback,peersEvaluationFeedbackForm);
		return isAdded;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @throws Exception
	 */
	public void clearQuestionsToList( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		List<EvaFacultyFeedBackQuestionTo> questionToList = new ArrayList<EvaFacultyFeedBackQuestionTo>();
		if(peersEvaluationFeedbackForm.getEvaFacultyQuestionsToList()!=null && !peersEvaluationFeedbackForm.getEvaFacultyQuestionsToList().isEmpty()){
			Iterator<EvaFacultyFeedBackQuestionTo> iterator = peersEvaluationFeedbackForm.getEvaFacultyQuestionsToList().iterator();
			while (iterator.hasNext()) {
				EvaFacultyFeedBackQuestionTo to = (EvaFacultyFeedBackQuestionTo) iterator .next();
				to.setAnswer(null);
				questionToList.add(to);
			}
			peersEvaluationFeedbackForm.setEvaFacultyQuestionsToList(questionToList);
		}
	}
}
