package com.kp.cms.transactions.admin;

import java.util.List;
import com.kp.cms.bo.admin.ExtracurricularActivity;


public interface IExtraCurricularActivitiesTransaction {
	public List<ExtracurricularActivity> getActivities() throws Exception;
	public ExtracurricularActivity isActivityNameDuplcated(ExtracurricularActivity duplExtracurricularActivity) throws Exception;
	public boolean addActivity(ExtracurricularActivity extracurricularActivity) throws Exception;	
	public boolean updateActivity(ExtracurricularActivity extracurricularActivity) throws Exception;
	public boolean deleteActivity(int actId, Boolean activate, String userId) throws Exception ;	
	
}
