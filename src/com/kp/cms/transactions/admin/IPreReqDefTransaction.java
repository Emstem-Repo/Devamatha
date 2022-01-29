package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.CoursePrerequisite;

public interface IPreReqDefTransaction {
	public boolean addPreReqDef(CoursePrerequisite  coursePrerequisite1,String mode) throws Exception;
	public List<CoursePrerequisite>getPreReqDef()throws Exception;
	public List<CoursePrerequisite> existanceCheck(CoursePrerequisite coursePrerequisite) throws Exception;
}
