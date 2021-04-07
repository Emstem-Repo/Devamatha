package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.forms.admin.ConvocationSessionForm;

public interface IConvocationSessionTransaction {

	Map<Integer, String> getCourseMap() throws Exception;

	boolean addDetails(ConvocationSession convocationSession) throws Exception;

	List<ConvocationSession> getConvocationSessionList() throws Exception;

	ConvocationSession getConvocationSession(int convocationSessionId) throws Exception;

	boolean deleteConvocationSession(int convocationSessionId, String user) throws Exception;

	boolean checkDuplicate(ConvocationSessionForm convocationSessionForm) throws Exception;
}
