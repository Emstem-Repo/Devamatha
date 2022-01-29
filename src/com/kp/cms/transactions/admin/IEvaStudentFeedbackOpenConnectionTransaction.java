package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;

public interface IEvaStudentFeedbackOpenConnectionTransaction {

	public boolean checkDuplicate(EvaStudentFeedbackOpenConnectionForm connectionForm)throws Exception;

	public boolean submitOpenConnectionDetails(List<EvaStudentFeedbackOpenConnection> boList)throws Exception;

	public EvaStudentFeedbackOpenConnection getOpenConnectionDetails(int id)throws Exception;

	public List<EvaStudentFeedbackOpenConnection> getConnectionList(int year, String sessionId)throws Exception;

	public boolean deleteOpenConnection( EvaStudentFeedbackOpenConnectionForm connectionForm)throws Exception;

	public boolean updateOpenConnection( EvaStudentFeedbackOpenConnectionForm connectionForm)throws Exception;

	public List<EvaluationStudentFeedbackSession> getSessionDetails(Integer year)throws Exception;

	public String getSpecializationName(String specializationId)throws Exception;

	public Map<Integer, Integer> getSpecializationIds(String[] classesId, String specializationName)throws Exception;


}
