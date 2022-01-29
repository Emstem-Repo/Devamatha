package com.kp.cms.actions.exam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.exam.AdminHallTicketForm;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.forms.exam.AssignExternalToDepartmentForm;
import com.kp.cms.handlers.exam.AssignExternalToDepartmentHandler;
import com.kp.cms.to.exam.AssignExternalToDepartmentTO;

public class AssignExternalToDepartmentAction extends BaseDispatchAction  {
	private static final Log log = LogFactory.getLog(AssignExternalToDepartmentAction.class);
	
	public ActionForward initAssignExternalToDepartmentAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAssignExternalToDepartmentAction input");
		AssignExternalToDepartmentForm assignExternalToDepartmentForm = (AssignExternalToDepartmentForm) form;
		assignExternalToDepartmentForm.resetFields();
		setRequiredDatatoForm(assignExternalToDepartmentForm);
		log.info("Exit initAssignExternalToDepartmentAction input");
		
		return mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
	}
	private void setRequiredDatatoForm(AssignExternalToDepartmentForm assignExternalToDepartmentForm) throws Exception {
		List<AssignExternalToDepartmentTO> deptList=AssignExternalToDepartmentHandler.getInstance().getDepartments(assignExternalToDepartmentForm);
		assignExternalToDepartmentForm.setDeptList(deptList);
	}
	
	public ActionForward getExternaDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into getExternaDetails of AssignExternalToDepartmentAction");
		AssignExternalToDepartmentForm assignExternalToDepartmentForm = (AssignExternalToDepartmentForm) form;
		 ActionErrors errors = assignExternalToDepartmentForm.validate(mapping, request);
		if (errors.isEmpty()) {
		try {
			List<AssignExternalToDepartmentTO> evlList=AssignExternalToDepartmentHandler.getInstance().getExternalDetails(assignExternalToDepartmentForm);
			if (evlList !=null && !evlList.isEmpty()) 
			assignExternalToDepartmentForm.setEvlList(evlList);
		} catch (Exception e) {
			log.error("Error occured in getExternaDetails of AssignExternalToDepartmentAction", e);
			String msg = super.handleApplicationException(e);
			assignExternalToDepartmentForm.setErrorMessage(msg);
			assignExternalToDepartmentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else {
			addErrors(request, errors);
			setRequiredDatatoForm(assignExternalToDepartmentForm);
			log.info("Exit AssignExternalToDepartmentAction - getExternaDetails errors not empty ");
			return mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
		}	
		assignExternalToDepartmentForm.setFlag(true);
		log.info("Leaving into getExternaDetails of AssignExternalToDepartmentAction");
		return  mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
	}
	
	public ActionForward saveSelectedDept(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		AssignExternalToDepartmentForm assignExternalToDepartmentForm = (AssignExternalToDepartmentForm) form;
		setUserId(request,assignExternalToDepartmentForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				isAdded = AssignExternalToDepartmentHandler.getInstance().addAssignEvaluators(assignExternalToDepartmentForm);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.exam.assign.evaluators.addsuccess"));
					saveMessages(request, messages);
			   } else {
				   if(!assignExternalToDepartmentForm.isSelected()){
				    errors.add("error", new ActionError( "knowledgepro.exam.assign.evaluators.notSelected"));
					addErrors(request, errors);
					saveErrors(request, errors);
				    return  mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
				    }
				    else
				    {
					errors.add("error", new ActionError( "knowledgepro.exam.assign.evaluators.addfailure"));
					addErrors(request, errors);
				    }
		     	}
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				assignExternalToDepartmentForm.setErrorMessage(msg);
				assignExternalToDepartmentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
		}
		setRequiredDatatoForm(assignExternalToDepartmentForm);
		assignExternalToDepartmentForm.resetFields();
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		return mapping.findForward(CMSConstants.ASSIGN_EXTERNAL_TO_DEPARTMENT);
	}

	

}
