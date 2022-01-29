package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.PeersEvaluationFeedback;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackForm;

public interface IPeersEvaluationFeedbackTransaction {

	public boolean getAlreadySubmittedDetails(PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;

	public List<AssignPeersGroups> getEvaluatorDetailsFromAssignPeersGrp( String query)throws Exception;

	public List<AssignPeersGroups> getAssignPeersList( String assignPeersGroupQuery, List<AssignPeersGroups> list, PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;

	public Map<Integer, Integer> getCheckAssignPeersGrp(String query1)throws Exception;

	public Map<Integer,String> getTeacherList(String teachersListQuery, Map<Integer,String> usersMap)throws Exception;

	public Users getUserDetails(String userId)throws Exception;

	public List<EvaFacultyFeedbackQuestion> peersQuestionsQuery( String query)throws Exception;

	public boolean savePeersEvaluationFeedback( PeersEvaluationFeedback peersEvaluationFeedback, PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;

	public List<Integer> getFacultiesAlreadyEvaluationCompleted( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;

	public PeersEvaluationFeedback getExistsBO( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;

	public boolean checkPeerIsDuplicate( PeersEvaluationFeedbackForm peersEvaluationFeedbackForm)throws Exception;


}
