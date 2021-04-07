package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.exceptions.ApplicationException;

public interface IBelowRequiredPercentageTransaction {

	public List getStudentAttendance(String searchCriteria) throws Exception;
	public int getClassesHeld(String searchCriteria) throws ApplicationException ;
	public int getClassesAttended(String searchCriteria) throws ApplicationException ;
	public int getActivityHeld(String searchCriteria) throws ApplicationException ;
	public int getActivityAttended(String searchCriteria) throws ApplicationException ;
	public int getSelectedActivityHeld(String searchCriteria) throws ApplicationException ;
	public int getSelectedActivityAttended(String searchCriteria) throws ApplicationException ;
}
