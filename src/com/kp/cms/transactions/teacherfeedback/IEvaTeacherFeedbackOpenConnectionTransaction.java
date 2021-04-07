package com.kp.cms.transactions.teacherfeedback;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.teacherfeedback.EvaTeacherFeedbackOpenConnection;
import com.kp.cms.bo.teacherfeedback.EvaluationTeacherFeedbackSession;
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedbackOpenConnectionForm;

public interface IEvaTeacherFeedbackOpenConnectionTransaction {

	public boolean checkDuplicate(EvaTeacherFeedbackOpenConnectionForm connectionForm)throws Exception;

	public boolean submitOpenConnectionDetails(List<EvaTeacherFeedbackOpenConnection> boList)throws Exception;

	public EvaTeacherFeedbackOpenConnection getOpenConnectionDetails(int id)throws Exception;

	public List<EvaTeacherFeedbackOpenConnection> getConnectionList(int year, String sessionId)throws Exception;

	public boolean deleteOpenConnection( EvaTeacherFeedbackOpenConnectionForm connectionForm)throws Exception;

	public boolean updateOpenConnection( EvaTeacherFeedbackOpenConnectionForm connectionForm)throws Exception;

	public List<EvaluationTeacherFeedbackSession> getSessionDetails(Integer year)throws Exception;

	public String getSpecializationName(String specializationId)throws Exception;

	public Map<Integer, Integer> getSpecializationIds(String[] classesId, String specializationName)throws Exception;


}
