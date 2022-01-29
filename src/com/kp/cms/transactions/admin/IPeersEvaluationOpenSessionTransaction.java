package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm;
import com.kp.cms.to.admin.PeersEvaluationOpenSessionTo;

public interface IPeersEvaluationOpenSessionTransaction {

	public List<Department> getDepartmentList()throws Exception;

	public List<PeersEvaluationOpenSession> getOpenConnectionList()throws Exception;

	public Map<Integer, String> getSessionsByYear(int currentYear)throws Exception;

	public boolean checkDuplicate( int deptId, PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception;

	public boolean submitOpenSession( List<PeersEvaluationOpenSession> openSessions)throws Exception;

	public boolean updatePeersOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception;

	public boolean deletePeersOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception;

	public PeersEvaluationOpenSession editPeersEvaluationOpenSession( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception;

}
