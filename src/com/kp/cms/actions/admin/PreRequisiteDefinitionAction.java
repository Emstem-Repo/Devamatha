package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;

import com.kp.cms.forms.admin.PreRequisiteDefinitionForm;
import com.kp.cms.handlers.admin.PreReqDefinitionHandler;
import com.kp.cms.handlers.admin.PreRequesiteExamHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.PrerequisiteTO;
import com.kp.cms.to.admin.ProgramTypeTO;



@SuppressWarnings("deprecation")
public class PreRequisiteDefinitionAction extends BaseDispatchAction
{
	private static final Log log = LogFactory.getLog(PreRequisiteDefinitionAction.class);
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPreRequisiteDef(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		PreRequisiteDefinitionForm prereqform = (PreRequisiteDefinitionForm) form;

		try
		{
			setRequiredDatatoForm(prereqform, request);
			setUserId(request, prereqform);
			return mapping.findForward("preReqDefinition");

		}catch (Exception e) {
			log.error("error in initPreRequisiteDef...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				prereqform.setErrorMessage(msg);
				prereqform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

	}

	/**
	 * this will add a new record in table
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPreReqDef( ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		PreRequisiteDefinitionForm prereqform = (PreRequisiteDefinitionForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = prereqform.validate(mapping, request);
		String mode="Add";

		boolean isAdded=false;
		try
		{
			if(isCancelled(request))
			{
				prereqform.reset(mapping, request);
				setRequiredDatatoForm(prereqform, request);
				return mapping.findForward("preReqDefinition");
			}
			if(errors.isEmpty())
			{
				if(prereqform.getPrereqid2() != null && !prereqform.getPrereqid2().isEmpty() && prereqform.getPrereqid1() != null && !prereqform.getPrereqid1().isEmpty())
				{
					if(prereqform.getPrereqid2().equalsIgnoreCase(prereqform.getPrereqid1()))
					{
						errors.add("error", new ActionError("knowledgepro.admin.prereqdef.same"));
						saveErrors(request,errors);
						setRequiredDatatoForm(prereqform, request);
						return mapping.findForward("preReqDefinition");
					}
				}
				if((prereqform.getPrereqid2() != null) && (!prereqform.getPrereqid2().isEmpty()) && (prereqform.getPercentage2() == null || prereqform.getPercentage2().isEmpty())){
					errors.add("error", new ActionError("knowledgepro.admin.prereqdef.percentage2.required"));
					saveErrors(request,errors);
					setRequiredDatatoForm(prereqform, request);
					return mapping.findForward("preReqDefinition");
				}
				if((prereqform.getPrereqid2() != null) && (!prereqform.getPrereqid2().isEmpty()) && (prereqform.getTotalMark2() == null || prereqform.getTotalMark2().isEmpty())){
					errors.add("error", new ActionError("knowledgepro.admin.prereqdef.totalMark2.required"));
					saveErrors(request,errors);
					setRequiredDatatoForm(prereqform, request);
					return mapping.findForward("preReqDefinition");
				}
				if(prereqform.getTotalMark1() != null && !prereqform.getTotalMark1().isEmpty() && prereqform.getPercentage1() != null && !prereqform.getPercentage1().isEmpty()){
					if(Double.parseDouble(prereqform.getPercentage1()) > Double.parseDouble(prereqform.getTotalMark1())){
						errors.add("error", new ActionError("knowledgepro.admin.mark.greater.total.mark"));
						saveErrors(request,errors);
						setRequiredDatatoForm(prereqform, request);
						return mapping.findForward("preReqDefinition");
					}
				}
				if(prereqform.getTotalMark2() != null && !prereqform.getTotalMark2().isEmpty() && prereqform.getPercentage2() != null && !prereqform.getPercentage2().isEmpty()){
					if(Double.parseDouble(prereqform.getPercentage2()) > Double.parseDouble(prereqform.getTotalMark2())){
						errors.add("error", new ActionError("knowledgepro.admin.mark.greater.total.mark"));
						saveErrors(request,errors);
						setRequiredDatatoForm(prereqform, request);
						return mapping.findForward("preReqDefinition");
					}
				}
				
				isAdded = PreReqDefinitionHandler.getInstance().addPreRequesiteExam(prereqform,mode);
				if (isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.prereqdef.addsuccess"
							);
					messages.add("messages", message);
					saveMessages(request, messages);
					prereqform.reset(mapping, request);
				} else {
					errors.add("error", new ActionError("knowledgepro.admin.prereqdef.addfail"
							));
					saveErrors(request,errors);
				}
			}else
			{
				saveErrors(request,errors);
			}

		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.PreRequesitedef.addexist"));
			saveErrors(request,errors);
			setRequiredDatatoForm(prereqform, request);
			return mapping.findForward("preReqDefinition");
		}
		catch (Exception e) {
			log.error("error in addPreReqDef...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);

			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				prereqform.setErrorMessage(msg);
				prereqform.setErrorStack(e.getMessage());

			} else {
				throw e;
			}

		}

		if(!errors.isEmpty())
		{
			saveErrors(request,errors);
		}
		setRequiredDatatoForm(prereqform, request);

		return mapping.findForward("preReqDefinition");
	}

	/**
	 * this will delete record from pre-requisite based on the ID
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePreReqDef(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		PreRequisiteDefinitionForm prereqform = (PreRequisiteDefinitionForm) form;
		ActionMessages messages=new ActionMessages();
		boolean isDeleted=false;
		ActionErrors errors = prereqform.validate(mapping, request);
		String mode="Delete";
		setUserId(request, prereqform);
		isDeleted = PreReqDefinitionHandler.getInstance().addPreRequesiteExam(prereqform,mode);
		setRequiredDatatoForm(prereqform, request);
		if(isDeleted)
		{
			ActionMessage message = new ActionMessage("knowledgepro.admin.prereqdef.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			prereqform.reset(mapping, request);

		}else
		{
			errors.add("error", new ActionError("knowledgepro.admin.prereqdef.deletefail"));
			saveErrors(request,errors);
		}
		return mapping.findForward("preReqDefinition");
    }


	/**
	 * 
	 * @param prereqform
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(PreRequisiteDefinitionForm prereqform, HttpServletRequest request) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		List<PrerequisiteTO> prerequisiteTOList = PreRequesiteExamHandler.getInstance().getPrerequisite();
		List<CoursePrerequisiteTO> CourseprerequisiteTOList=PreReqDefinitionHandler.getInstance().getPreReqDef();
		prereqform.setPrerequsitedeflist(CourseprerequisiteTOList);
		request.setAttribute("prereqexam", prerequisiteTOList);
		request.setAttribute("programTypeList", programTypeList);
		log.debug("Action: leaving setRequiredDatatoForm");
	}

}
