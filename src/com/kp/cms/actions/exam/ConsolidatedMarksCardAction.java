package com.kp.cms.actions.exam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ConsolidatedMarksCardForm;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ConsolidatedMarksCardHandler;
import com.kp.cms.to.admin.ProgramTO;

public class ConsolidatedMarksCardAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ConsolidatedMarksCardAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMarksEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initExamMarksEntry input");
		ConsolidatedMarksCardForm consolidatedMarksCardForm = (ConsolidatedMarksCardForm) form;// Type casting the Action form to Required Form
		consolidatedMarksCardForm.resetFields();//Reseting the fields for input jsp
		setRequiredDatatoForm(consolidatedMarksCardForm);// setting the requested data to form
		log.info("Exit initExamMarksEntry input");
		
		return mapping.findForward(CMSConstants.NEW_CONSOLIDATED_MARKS_CARD);
	}

	/**
	 * @param consolidatedMarksCardForm
	 */
	private void setRequiredDatatoForm( ConsolidatedMarksCardForm consolidatedMarksCardForm) throws Exception {
		List<ProgramTO> programList=ProgramHandler.getInstance().getProgram();
		Collections.sort(programList);
		consolidatedMarksCardForm.setProgramList(programList);
		if(consolidatedMarksCardForm.getProgramId()!=null && !consolidatedMarksCardForm.getProgramId().isEmpty()){
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram( Integer.valueOf(consolidatedMarksCardForm.getProgramId()));
			consolidatedMarksCardForm.setCourseMap(courseMap);
		}
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward generateConsolidateMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		ConsolidatedMarksCardForm consolidatedMarksCardForm = (ConsolidatedMarksCardForm) form;// Type casting the Action form to Required Form
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = consolidatedMarksCardForm.validate(mapping, request);
		setUserId(request,consolidatedMarksCardForm);
		if (errors.isEmpty()) {
			try {
				boolean isGenerate=ConsolidatedMarksCardHandler.getInstance().generateConsolidateMarksCard(consolidatedMarksCardForm);
				if (isGenerate) {
					consolidatedMarksCardForm.resetFields();
					messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.admin.addsuccess","Consolidate Marks Card"));
					saveMessages(request, messages);
				}else{
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.update.process.failure","Consolidate Marks Card"));
					addErrors(request, errors);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				consolidatedMarksCardForm.setErrorMessage(msg);
				consolidatedMarksCardForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(consolidatedMarksCardForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.NEW_CONSOLIDATED_MARKS_CARD);
		}
		setRequiredDatatoForm(consolidatedMarksCardForm);// setting the requested data to form
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.NEW_CONSOLIDATED_MARKS_CARD);
	}
}
