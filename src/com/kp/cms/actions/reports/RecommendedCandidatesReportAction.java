package com.kp.cms.actions.reports;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.RecommendedCandidatesForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.RecommendedByHandler;
import com.kp.cms.handlers.reports.CandidatesRecommenderHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.RecommendedByTO;
import com.kp.cms.to.reports.CandidatesRecommendorTO;

public class RecommendedCandidatesReportAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(LeaveReportAction.class);
	
	/**
	 * This method is called when u click the link in left menu.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward initRecommendedCandidates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedCandidatesForm recommendedCandidatesForm = (RecommendedCandidatesForm)form;
		log.info("entering of initRecommendedCandidates in RecommendedCandidatesReportAction class");
		HttpSession session = request.getSession(false);
		if(session != null){
			session.removeAttribute("recommendedCandidateList");
		}
		recommendedCandidatesForm.reset(mapping, request);
		setRequiredDataToForm(recommendedCandidatesForm);
		log.info("exit of initRecommendedCandidates in RecommendedCandidatesReportAction class");
		return mapping.findForward(CMSConstants.RECOMMENDED_CANDIDATES_INPUT);
	}

	/**
	 * This method is called when u submit the form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitRecommendedCandidates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RecommendedCandidatesForm recommendedCandidatesForm = (RecommendedCandidatesForm)form;
		log.info("entering of submitRecommendedCandidates method of RecommendedCandidatesReportAction class..");
		HttpSession session = request.getSession(false);
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = recommendedCandidatesForm.validate(mapping, request);
			if (errors.isEmpty()) {	
				try {
					List<CandidatesRecommendorTO> recommendedList = CandidatesRecommenderHandler.getInstance().getValuesBasedonQuery(recommendedCandidatesForm);
					if(recommendedList != null){
						session.setAttribute("recommendedCandidateList",recommendedList);
					}else{
						messages.add(CMSConstants.ERROR,new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS));
						addErrors(request, messages);
						setRequiredDataToForm(recommendedCandidatesForm);
						return mapping.findForward(CMSConstants.RECOMMENDED_CANDIDATES_INPUT);
					}
				}catch (BusinessException businessException) {
					log.info("Exception submitRecommendedCandidates");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					recommendedCandidatesForm.setErrorMessage(msg);
					recommendedCandidatesForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				}else {
					addErrors(request, errors);
					setRequiredDataToForm(recommendedCandidatesForm);
					return mapping.findForward(CMSConstants.RECOMMENDED_CANDIDATES_INPUT);
				}
		setRequiredDataToForm(recommendedCandidatesForm);
		log.info("Exit of submitRecommendedCandidates method of RecommendedCandidatesReportAction class..");
		return mapping.findForward(CMSConstants.RECOMMENDED_CANDIDATES_OUTPUT);
	}
	
	/**
	 * This method is used the required data to form.
	 * @param candidatesForm
	 * @throws Exception
	 */
	
	private void setRequiredDataToForm(RecommendedCandidatesForm candidatesForm) throws Exception {
		List<ProgramTO> programList = ProgramHandler.getInstance().getProgram();
		if(programList != null && programList.size() != 0){
			candidatesForm.setProgramList(programList);
		}
		List<RecommendedByTO> recommenderByList = RecommendedByHandler.getInstance().getRecommendedByDetails();
		if(recommenderByList != null && recommenderByList.size() !=0){
			candidatesForm.setRecommenderList(recommenderByList);
		}
	}

}