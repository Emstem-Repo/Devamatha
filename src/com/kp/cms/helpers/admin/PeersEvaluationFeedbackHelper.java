package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.HideTeachersForPeersEvaluation;
import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.bo.admin.PeersEvaluationFeedback;
import com.kp.cms.bo.admin.PeersEvaluationFeedbackAnswers;
import com.kp.cms.bo.admin.PeersEvaluationFeedbackPeers;
import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackForm;
import com.kp.cms.to.admin.PeersEvaluationFeedbackTO;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;
import com.kp.cms.transactions.admin.IPeersEvaluationFeedbackTransaction;
import com.kp.cms.transactionsimpl.admin.PeersEvaluationFeedbackTxnImpl;
import com.kp.cms.utilities.HibernateUtil;

public class PeersEvaluationFeedbackHelper {
	public static volatile PeersEvaluationFeedbackHelper helper = null;
	public static PeersEvaluationFeedbackHelper getInstance(){
		if(helper == null){
			helper = new PeersEvaluationFeedbackHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public String selectQuery( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		int userId = Integer.parseInt(peersEvaluationFeedbackForm.getUserId());
		String query = "from AssignPeersGroups assignPeersGrp where assignPeersGrp.users.id ="+userId+" and assignPeersGrp.isActive = 1";
		return query;
	}
	/**
	 * @param assignPeersGroupsToList
	 * @param listOfFacultyAlreadyEvaluate 
	 * @return
	 * @throws Exception
	 */
	public List<PeersEvaluationFeedbackTO> convertBOListToTOList( List<AssignPeersGroups> assignPeersGroupsToList, List<Integer> listOfFacultyAlreadyEvaluate) throws Exception{
		List<PeersEvaluationFeedbackTO> toList = new ArrayList<PeersEvaluationFeedbackTO>();
		int i = 0;
		List<Integer> totalPeersList = new ArrayList<Integer>();
		if(assignPeersGroupsToList!=null && !assignPeersGroupsToList.toString().isEmpty()){
			Iterator<AssignPeersGroups> iterator =  assignPeersGroupsToList.iterator();
			while (iterator.hasNext()) {
				AssignPeersGroups assignPeersGroups = (AssignPeersGroups) iterator .next();
				PeersEvaluationFeedbackTO to = new PeersEvaluationFeedbackTO();
				if(assignPeersGroups.getId()!=0){
					to.setAssignGrpId(assignPeersGroups.getId());
				}
				if(assignPeersGroups.getDepartment().getId()!=0){
					to.setDepartmentId(assignPeersGroups.getDepartment().getId());
				}
				
				if(assignPeersGroups.getUsers()!=null && assignPeersGroups.getUsers().getId()!=0){
					to.setPeerId(assignPeersGroups.getUsers().getId());
				}
				/*newly added code */
				if(listOfFacultyAlreadyEvaluate.contains(to.getPeerId())){
					to.setDone(true);
				}else{
					to.setDone(false);
				}
				/*newly added code */
				if(assignPeersGroups.getUsers().getEmployee()!=null && !assignPeersGroups.getUsers().getEmployee().toString().isEmpty()){
					if(assignPeersGroups.getUsers().getEmployee().getFirstName()!=null && !assignPeersGroups.getUsers().getEmployee().getFirstName().isEmpty()){
						to.setPeerName(assignPeersGroups.getUsers().getEmployee().getFirstName());
					}
				}else if(assignPeersGroups.getUsers().getUserName()!=null && !assignPeersGroups.getUsers().getUserName().isEmpty()){
					to.setPeerName(assignPeersGroups.getUsers().getUserName());
				}
				if(assignPeersGroups.getPeersEvaluationGroups()!=null && !assignPeersGroups.getPeersEvaluationGroups().toString().isEmpty()){
					if(assignPeersGroups.getPeersEvaluationGroups().getId()!=0){
						to.setPeerGroupId(assignPeersGroups.getPeersEvaluationGroups().getId());
					}
					if(assignPeersGroups.getPeersEvaluationGroups().getName()!=null && !assignPeersGroups.getPeersEvaluationGroups().getName().isEmpty()){
						to.setAssignGrpName(assignPeersGroups.getPeersEvaluationGroups().getName());
					}
				}
				if(assignPeersGroups.getDepartment().getName()!=null && !assignPeersGroups.getDepartment().getName().isEmpty()){
					to.setDepartmentName(assignPeersGroups.getDepartment().getName()+" "+"("+to.getAssignGrpName()+")");
				}
				i++;
				to.setPeersNo(i);
				totalPeersList.add(to.getPeersNo());
				to.setTotalPeers(totalPeersList);
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public String selectQueryList(AssignPeersGroups bo)throws Exception { 
		int peersGrpId = 0 ;
		int userId = 0;
		if(bo.getUsers()!=null && !bo.getUsers().toString().isEmpty()){
			userId = bo.getUsers().getId();
		}
		if(bo.getPeersEvaluationGroups()!=null && !bo.getPeersEvaluationGroups().toString().isEmpty()){
			peersGrpId = bo.getPeersEvaluationGroups().getId();
		}
		String query = "from AssignPeersGroups assignGrp where assignGrp.peersEvaluationGroups.id="+peersGrpId+ "and not assignGrp.users.id="+userId+" and assignGrp.isActive = 1";
		return query;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public String selectQuery1( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		String query = "from AssignPeersGroups assignGrp where assignGrp.department.id="+peersEvaluationFeedbackForm.getDeptId()+" and assignGrp.isActive = 1";;
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public List<AssignPeersGroups> checkHiddenFaculty(
			List<AssignPeersGroups> list) throws Exception {
		List<AssignPeersGroups> assignPeersGrpList = new ArrayList<AssignPeersGroups>();
		Session session = null;
		if (list != null && !list.toString().isEmpty()) {
			Iterator<AssignPeersGroups> iterator = list.iterator();
			while (iterator.hasNext()) {
				session = HibernateUtil.getSession();
				AssignPeersGroups bo = (AssignPeersGroups) iterator.next();
				String str = "from HideTeachersForPeersEvaluation hideTeachers where hideTeachers.isActive = 1"
						+ "and hideTeachers.department.id="
						+ bo.getDepartment().getId()
						+ " and hideTeachers.users.id ="
						+ bo.getUsers().getId();
				Query query =session.createQuery(str);
				HideTeachersForPeersEvaluation hideTeachers = (HideTeachersForPeersEvaluation) query.uniqueResult();
				if (hideTeachers == null) {
					assignPeersGrpList.add(bo);
				}
			}
		}
		return assignPeersGrpList;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public String getPeersListQueryFromEmployeeBO( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception {
		String query = "select u.id,e.firstName from Users u join u.employee e with (e.active=1 and e.isActive=1) "
				+ "where u.employee.id = e.id and u.isActive=1 and u.active =1 and "
				+ "u.isTeachingStaff=1 and u.userName is not null and "
				+ "e.department.id = "
				+ peersEvaluationFeedbackForm.getDeptId()
				+ " and not u.id = "
				+ peersEvaluationFeedbackForm.getUserId();
		return query;

	}
	/** converting from   Map to TO and returns the tolist.
	 * @param users1
	 * @param assignPeersGrpMap
	 * @param listOfFacultyAlreadyEvaluate 
	 * @return
	 * @throws Exception
	 */
	
	public List<PeersEvaluationFeedbackTO> copyTeachersListToTOList( Map<Integer,String> users1, Map<Integer, Integer> assignPeersGrpMap,
			PeersEvaluationFeedbackForm form, List<Integer> listOfFacultyAlreadyEvaluate)throws Exception {
		List<PeersEvaluationFeedbackTO> toList = new ArrayList<PeersEvaluationFeedbackTO>();
		int i = 0;
		List<Integer> totalPeersList = new ArrayList<Integer>();
		if(users1!=null && !users1.toString().isEmpty()){
			Iterator<Map.Entry<Integer, String>> iterator = users1.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, String> entry =iterator.next();
				/* checking  assignPeersGrpMap contains the key of users1 map 
				 * if not contains key, then iterates the user1 map and add to the toList
				 * and returns the tolist*/
				if(!assignPeersGrpMap.containsKey(entry.getKey())){					
					PeersEvaluationFeedbackTO to = new PeersEvaluationFeedbackTO();
					to.setPeerId(entry.getKey());
					to.setPeerName(entry.getValue());
					to.setDepartmentId(form.getDeptId());
					to.setDepartmentName(form.getDepartmentName());
					i++;
					to.setPeersNo(i);
					totalPeersList.add(to.getPeersNo());
					to.setTotalPeers(totalPeersList);
					/*newly added code */
					if(listOfFacultyAlreadyEvaluate!=null && !listOfFacultyAlreadyEvaluate.isEmpty()){
						if(listOfFacultyAlreadyEvaluate.contains(to.getPeerId())){
							to.setDone(true);
						}else {
							to.setDone(false);
						}
					}else{
						to.setDone(false);
					}
					/*newly added code */
					toList.add(to);
			}
		}
		}
		return toList;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 */
	public String getPeersListFromGuestBO(
			PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)
			throws Exception {
		String query = "select u.id,g.firstName from Users u join u.guest g with (g.active=1 and g.isActive=1) "
				+ "where u.guest.id = g.id and u.isActive=1 and u.active =1 and u.isTeachingStaff=1 "
				+ "and u.userName is not null and g.department.id ="
				+ peersEvaluationFeedbackForm.getDeptId()
				+ " and not u.id = "
				+ peersEvaluationFeedbackForm.getUserId();
		return query;
	}
	/**
	 * @param peersEvaluationFeedbackForm
	 * @return
	 * @throws Exception
	 */
	public String getPeersListFromUsersBO(
			PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)
			throws Exception {
		String query = "select u.id,u.userName from Users u where u.isActive=1 and u.active =1 and u.isTeachingStaff=1 and u.userName is not null and u.department.id = "
				+ peersEvaluationFeedbackForm.getDeptId()
				+ " and not u.id = "
				+ peersEvaluationFeedbackForm.getUserId();
		return query;
	}
	/** checking whether the teachers are in hide.
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> checkHiddenFaculty1(Map<Integer,String> users,PeersEvaluationFeedbackForm form)
			throws Exception {
		Map<Integer,String> usersMap = new HashMap<Integer, String>();
		if (users != null && !users.toString().isEmpty()) {
			Session session = null;
			Iterator<Map.Entry<Integer, String>> iterator = users.entrySet().iterator();
			while (iterator.hasNext()) {
				session = HibernateUtil.getSession();
				Map.Entry<Integer, String> entry = iterator.next();
				String str = "from HideTeachersForPeersEvaluation hideTeachers "
						+ "where hideTeachers.isActive = 1 and hideTeachers.department.id="
						+ form.getDeptId()
						+ "and hideTeachers.users.id ="
						+ entry.getKey();
				Query query = session.createQuery(str);
				HideTeachersForPeersEvaluation hideTeachers = (HideTeachersForPeersEvaluation) query .uniqueResult();
				/* if the HideTeachersForPeersEvaluation object is not null then remove from the map*/
				if (hideTeachers == null) {
					//users.remove(hideTeachers.getUsers().getId());
					usersMap.put(entry.getKey(), entry.getValue());
				}
			}
		}

		return usersMap;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String peersQuestionsQuery() throws Exception{
		String query = "from EvaFacultyFeedbackQuestion question where question.isActive =1 order by question.order";
		return query;
	}
	/**
	 * @param facultyFeedbackQuestionsBo
	 * @return
	 * @throws Exception
	 */
	public List<EvaFacultyFeedBackQuestionTo> copyQuestionBoListTOToList(
			List<EvaFacultyFeedbackQuestion> facultyFeedbackQuestionsBo,PeersEvaluationFeedbackForm peersForm) throws Exception{
		List<EvaFacultyFeedBackQuestionTo> questionTOList = new ArrayList<EvaFacultyFeedBackQuestionTo>();
		int totalQuestions = 0;
		if(facultyFeedbackQuestionsBo!=null && !facultyFeedbackQuestionsBo.isEmpty()){
			Iterator<EvaFacultyFeedbackQuestion> iterator = facultyFeedbackQuestionsBo.iterator();
			while (iterator.hasNext()) {
				EvaFacultyFeedbackQuestion bo = (EvaFacultyFeedbackQuestion) iterator .next();
				EvaFacultyFeedBackQuestionTo to = new EvaFacultyFeedBackQuestionTo();
				if(bo.getId()!=0){
					to.setId(bo.getId());
				}
				if(bo.getQuestion()!=null && !bo.getQuestion().isEmpty()){
					to.setQuestion(bo.getQuestion());
				}
				questionTOList.add(to);
				totalQuestions++;
			}
			peersForm.setTotalQuestions(totalQuestions);
		}
		return questionTOList;
	}
	/** Adding the each PeersRating details to the templist
	 * and setting it into the formbean
	 * @param peersEvaluationFeedbackForm
	 * @throws Exception
	 */
	public boolean tempSavePeerRatingDetails( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm) throws Exception{
		IPeersEvaluationFeedbackTransaction transaction = PeersEvaluationFeedbackTxnImpl.getInstance();
		boolean isDuplicate = transaction.checkPeerIsDuplicate(peersEvaluationFeedbackForm);
		if(!isDuplicate){
			PeersEvaluationFeedbackTO peersEvaluationFeedbackTO = new PeersEvaluationFeedbackTO();
			peersEvaluationFeedbackTO.setPeerId(peersEvaluationFeedbackForm.getPeerId());
			if(peersEvaluationFeedbackForm.getRemarks()!=null && !peersEvaluationFeedbackForm.getRemarks().isEmpty()){
				peersEvaluationFeedbackTO.setRemarks(peersEvaluationFeedbackForm.getRemarks());
			}
			/* getting the questionTolist from formbean and setting it to the PeersEvaluationFeedbackTO to*/
			List<EvaFacultyFeedBackQuestionTo> questionList = peersEvaluationFeedbackForm.getEvaFacultyQuestionsToList();
			peersEvaluationFeedbackTO.setQuestionTosList(questionList);
			peersEvaluationFeedbackForm.setTempPeersEvaList(peersEvaluationFeedbackTO);
		}
		return isDuplicate;
	}
	/** Iterates the tolist and setting to the PeersEvaluationFeedback Bo 
	 * and returns the PeersEvaluationFeedback Bo 
	 * @param peersEvaluationFeedbackForm
	 * @param existBO 
	 * @return
	 * @throws Exception
	 */
	public PeersEvaluationFeedback convertToListTOBo(PeersEvaluationFeedbackForm peersEvaluationFeedbackForm, PeersEvaluationFeedback existBO)throws Exception {
		PeersEvaluationFeedback evaluationFeedbackBo = null;
		if(existBO!=null && !existBO.toString().isEmpty()){
			evaluationFeedbackBo = existBO;
			PeersEvaluationFeedbackTO feedbackTO = peersEvaluationFeedbackForm.getTempPeersEvaList();
			//Set<PeersEvaluationFeedbackPeers> feedbackPeersSet = evaluationFeedbackBo.getFeedbackFaculty();
			Set<PeersEvaluationFeedbackPeers> feedbackPeersSet = new HashSet<PeersEvaluationFeedbackPeers>();
			/*Map<Integer,PeersEvaluationFeedbackPeers> feedbackPeersMap = convertFromSetTOMap(feedbackPeersSet);
			if(!feedbackPeersMap.containsKey(feedbackTO.getPeerId())){
				PeersEvaluationFeedbackPeers feedbackPeers = feedbackPeersMap.get(feedbackTO.getPeerId());
			}*/
			PeersEvaluationFeedbackPeers feedbackPeers = new PeersEvaluationFeedbackPeers();
				if(feedbackTO.getPeerId()!=0){
					Users users = new Users();
					users.setId(feedbackTO.getPeerId());
					feedbackPeers.setPeerId(users);
				}
				if(feedbackTO.getRemarks()!=null && !feedbackTO.getRemarks().isEmpty()){
					feedbackPeers.setRemarks(feedbackTO.getRemarks());
				}
				feedbackPeers.setPeersEvaluationFeedback(evaluationFeedbackBo);
				feedbackPeers.setCreatedBy(peersEvaluationFeedbackForm.getUserId());
				feedbackPeers.setCreatedDate(new Date());
				feedbackPeers.setLastModifiedDate(new Date());
				feedbackPeers.setModifiedBy(peersEvaluationFeedbackForm.getUserId());
				feedbackPeers.setIsActive(true);
				
				if(feedbackTO.getQuestionTosList()!=null && !feedbackTO.getQuestionTosList().isEmpty()){
					List<EvaFacultyFeedBackQuestionTo> questionTosList = feedbackTO.getQuestionTosList();
					Set<PeersEvaluationFeedbackAnswers> feedbackAnswersSet =new HashSet<PeersEvaluationFeedbackAnswers>();
					Iterator<EvaFacultyFeedBackQuestionTo> iterator2 = questionTosList.iterator();
					while (iterator2.hasNext()) {
						EvaFacultyFeedBackQuestionTo evaFacultyFeedBackQuestionTo = (EvaFacultyFeedBackQuestionTo) iterator2 .next();
						PeersEvaluationFeedbackAnswers answersBo = new PeersEvaluationFeedbackAnswers();
						answersBo.setPeersEvaluationFeedbackPeers(feedbackPeers);
						if(evaFacultyFeedBackQuestionTo.getId()!=0){
							EvaFacultyFeedbackQuestion question = new EvaFacultyFeedbackQuestion();
							question.setId(evaFacultyFeedBackQuestionTo.getId());
							answersBo.setQuestionsId(question);
						}
						if(evaFacultyFeedBackQuestionTo.getAnswer()!=null && !evaFacultyFeedBackQuestionTo.getAnswer().isEmpty()){
							answersBo.setAnswer(evaFacultyFeedBackQuestionTo.getAnswer());
						}
						feedbackAnswersSet.add(answersBo);
					}
					feedbackPeers.setEvaluationFeedbackAnswers(feedbackAnswersSet);
				}
					feedbackPeersSet.add(feedbackPeers);
			evaluationFeedbackBo.setFeedbackFaculty(feedbackPeersSet);
		}else{
			evaluationFeedbackBo= new PeersEvaluationFeedback();
			if(peersEvaluationFeedbackForm.getUserId()!=null && !peersEvaluationFeedbackForm.getUserId().isEmpty()){
				Users users = new Users();
				users.setId(Integer.parseInt(peersEvaluationFeedbackForm.getUserId()));
				evaluationFeedbackBo.setTeacherId(users);
			}
			if(peersEvaluationFeedbackForm.getAssignGroupId()!=0){
				PeersEvaluationGroups peersGroups = new PeersEvaluationGroups();
				peersGroups.setId(peersEvaluationFeedbackForm.getAssignGroupId());
				evaluationFeedbackBo.setPeersGroups(peersGroups);
			}else if(peersEvaluationFeedbackForm.getDeptId()!=0){
				Department department = new Department();
				department.setId(peersEvaluationFeedbackForm.getDeptId());
				evaluationFeedbackBo.setDepartment(department);
			}
			if(peersEvaluationFeedbackForm.getSessionId()!=null && !peersEvaluationFeedbackForm.getSessionId().isEmpty()){
				PeerFeedbackSession session = new PeerFeedbackSession();
				session.setId(Integer.parseInt(peersEvaluationFeedbackForm.getSessionId()));
				evaluationFeedbackBo.setSessionid(session);
			}
			evaluationFeedbackBo.setCancel(false);
			evaluationFeedbackBo.setCreatedBy(peersEvaluationFeedbackForm.getUserId());
			evaluationFeedbackBo.setModifiedBy(peersEvaluationFeedbackForm.getUserId());
			evaluationFeedbackBo.setCreatedDate(new Date());
			evaluationFeedbackBo.setLastModifiedDate(new Date());
			evaluationFeedbackBo.setIsActive(true);
			if(peersEvaluationFeedbackForm.getTempPeersEvaList()!=null && !peersEvaluationFeedbackForm.getTempPeersEvaList().toString().isEmpty()){
				PeersEvaluationFeedbackTO feedbackTO = peersEvaluationFeedbackForm.getTempPeersEvaList();
				Set<PeersEvaluationFeedbackPeers> feedbackPeersSet = new HashSet<PeersEvaluationFeedbackPeers>();
				PeersEvaluationFeedbackPeers feedbackPeers = new PeersEvaluationFeedbackPeers();
					if(feedbackTO.getPeerId()!=0){
						Users users = new Users();
						users.setId(feedbackTO.getPeerId());
						feedbackPeers.setPeerId(users);
					}
					if(feedbackTO.getRemarks()!=null && !feedbackTO.getRemarks().isEmpty()){
						feedbackPeers.setRemarks(feedbackTO.getRemarks());
					}
					feedbackPeers.setPeersEvaluationFeedback(evaluationFeedbackBo);
					feedbackPeers.setCreatedBy(peersEvaluationFeedbackForm.getUserId());
					feedbackPeers.setCreatedDate(new Date());
					feedbackPeers.setLastModifiedDate(new Date());
					feedbackPeers.setModifiedBy(peersEvaluationFeedbackForm.getUserId());
					feedbackPeers.setIsActive(true);
					if(feedbackTO.getQuestionTosList()!=null && !feedbackTO.getQuestionTosList().isEmpty()){
						List<EvaFacultyFeedBackQuestionTo> questionTosList = feedbackTO.getQuestionTosList();
						Set<PeersEvaluationFeedbackAnswers> feedbackAnswersSet = new HashSet<PeersEvaluationFeedbackAnswers>();
						Iterator<EvaFacultyFeedBackQuestionTo> iterator2 = questionTosList.iterator();
						while (iterator2.hasNext()) {
							EvaFacultyFeedBackQuestionTo evaFacultyFeedBackQuestionTo = (EvaFacultyFeedBackQuestionTo) iterator2 .next();
							PeersEvaluationFeedbackAnswers answersBo = new PeersEvaluationFeedbackAnswers();
							answersBo.setPeersEvaluationFeedbackPeers(feedbackPeers);
							if(evaFacultyFeedBackQuestionTo.getId()!=0){
								EvaFacultyFeedbackQuestion question = new EvaFacultyFeedbackQuestion();
								question.setId(evaFacultyFeedBackQuestionTo.getId());
								answersBo.setQuestionsId(question);
							}
							if(evaFacultyFeedBackQuestionTo.getAnswer()!=null && !evaFacultyFeedBackQuestionTo.getAnswer().isEmpty()){
								answersBo.setAnswer(evaFacultyFeedBackQuestionTo.getAnswer());
							}
							feedbackAnswersSet.add(answersBo);
						}
						feedbackPeers.setEvaluationFeedbackAnswers(feedbackAnswersSet);
					}
					feedbackPeersSet.add(feedbackPeers);
				evaluationFeedbackBo.setFeedbackFaculty(feedbackPeersSet);
			}
		}
		return evaluationFeedbackBo;
	}
}
