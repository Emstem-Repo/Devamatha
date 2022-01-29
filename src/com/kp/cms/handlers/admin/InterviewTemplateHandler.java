package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.InterviewTemplateForm;
import com.kp.cms.helpers.admin.InterviewTemplateHelper;
import com.kp.cms.to.admin.InterviewTemplateTo;
import com.kp.cms.transactions.admin.IInterviewTemplate;
import com.kp.cms.transactionsimpl.admin.InterviewTemplateTransactionImpl;

public class InterviewTemplateHandler {
	/**
	 * Singleton object of InterviewTemplateHandler
	 */
	private static volatile InterviewTemplateHandler interviewTemplateHandler = null;
	private static final Log log = LogFactory.getLog(InterviewTemplateHandler.class);
	private InterviewTemplateHandler() {
		
	}
	/**
	 * return singleton object of InterviewTemplateHandler.
	 * @return
	 */
	public static InterviewTemplateHandler getInstance() {
		if (interviewTemplateHandler == null) {
			interviewTemplateHandler = new InterviewTemplateHandler();
		}
		return interviewTemplateHandler;
	}
	/**
	 * @param interviewTemplateForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveInterviewTemplate(
			InterviewTemplateForm interviewTemplateForm) throws Exception {
		GroupTemplateInterview groupTemplate = InterviewTemplateHelper.getInstance().getGroupTemplateInterviewObject(interviewTemplateForm,CMSConstants.ADD);
		IInterviewTemplate template=InterviewTemplateTransactionImpl.getInstance();
		template.saveGroupTemplateInterview(groupTemplate);
		log.debug("Leaving createTemplate ");
		return true;
	}
	public List<InterviewTemplateTo> getInterviewTemplateList(int templateId) throws Exception {
		IInterviewTemplate template=InterviewTemplateTransactionImpl.getInstance();
		List<GroupTemplateInterview> list= template.getGroupTemplateInterviews(templateId);
		log.debug("Leaving getTemplateList ");
		return InterviewTemplateHelper.getInstance().convertBOtoTO(list);
	}
	/**
	 * @param interviewTemplateForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateInterviewTemplate(
			InterviewTemplateForm interviewTemplateForm) throws Exception{
		GroupTemplateInterview groupTemplate = InterviewTemplateHelper.getInstance().getGroupTemplateInterviewObject(interviewTemplateForm,CMSConstants.UPDATE);
		IInterviewTemplate template=InterviewTemplateTransactionImpl.getInstance();
		groupTemplate.setId(interviewTemplateForm.getTemplateId());
		template.saveGroupTemplateInterview(groupTemplate);
		log.debug("Leaving createTemplate ");
		return true;
	}
	/**
	 * @param courseId
	 * @param templateName
	 * @param programId
	 * @return
	 * @throws Exception
	 */
	public List<GroupTemplateInterview> checkDuplicate(int courseId, String templateName,
			int programId,int interviewId,int subroundId,int year) throws Exception {
		IInterviewTemplate template=InterviewTemplateTransactionImpl.getInstance();
		log.debug("Leaving getTemplateList ");
		return template.checkDuplicate(courseId, templateName, programId,interviewId,subroundId,year);
	}
	/**
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteInterviewTemplate(int templateId) throws Exception{
		GroupTemplateInterview groupTemplate = new GroupTemplateInterview();
		IInterviewTemplate template=InterviewTemplateTransactionImpl.getInstance();
		groupTemplate.setId(templateId);
		template.deleteGroupTemplateInterview(groupTemplate);
		
		log.debug("Leaving createTemplate ");
		return true;
	}
}