package com.kp.cms.transactions.admin;

import java.util.List;

import org.apache.struts.action.ActionMessages;

import com.kp.cms.bo.admin.GroupTemplateInterview;

public interface IInterviewTemplate {

	boolean saveGroupTemplateInterview(GroupTemplateInterview groupTemplate) throws Exception;

	List<GroupTemplateInterview> getGroupTemplateInterviews(int templateId) throws Exception;

	List<GroupTemplateInterview>  checkDuplicate(int courseId, String templateName,
			int programId,int interviewId,int subroundId,int year) throws Exception;

	boolean deleteGroupTemplateInterview(GroupTemplateInterview groupTemplate) throws Exception;

}
