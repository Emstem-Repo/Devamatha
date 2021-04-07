package com.kp.cms.actions.timetable;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.ClassWiseTimeTableViewForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.timetable.ClassWiseTimeTableViewHandler;

public class ClassWiseTimeTableViewAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(ClassWiseTimeTableViewAction.class);
	ClassWiseTimeTableViewHandler handler=ClassWiseTimeTableViewHandler.getinstance();
	
	public ActionForward initClassWiseTimeTableView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			ClassWiseTimeTableViewForm objForm=(ClassWiseTimeTableViewForm)form;
			try
			{
				objForm.setListOfClasses(CommonAjaxHandler.getInstance().getClassesByYear(new Date().getYear() + 1900));
			}
			catch (Exception e) {
				log.error("Error occured in initClassWiseTimeTableView of ClassWiseTimeTableViewAction", e);
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_CLASS_WISE_TIME_TABLE);
	}
	
	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ClassWiseTimeTableViewForm objForm = (ClassWiseTimeTableViewForm) form;
		try
		{
			log.info("entering into fetch data Method of ClassAllocationAction.");
			
			 ActionErrors errors = objForm.validate(mapping, request);
			saveErrors(request, errors);
			setUserId(request, objForm);
			if (errors.isEmpty()) {
				handler.fetchData(objForm);
				if(objForm.getPeriodList().size()==0)
				{	
					errors.add("error", new ActionError("knowledgepro.viewTimeTable.norecordsfound"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CLASS_WISE_TIME_TABLE);
				}	
				return mapping
						.findForward(CMSConstants.CLASS_WISE_TIME_TABLE_DISPLAY);
			}
			log.info("exit of initStaffAllocation of StaffAllocationAction.");
			return mapping.findForward(CMSConstants.INIT_CLASS_WISE_TIME_TABLE);
		}
		catch (Exception e) {
			log.error("Error occured in initClassWiseTimeTableView of ClassWiseTimeTableViewAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	public ActionForward printClassWiseTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//ClassWiseTimeTableViewForm objForm = (ClassWiseTimeTableViewForm) form;
		return mapping.findForward("printClassWiseTimeTable");
	}
	
	
}
