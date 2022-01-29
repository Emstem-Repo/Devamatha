package com.kp.cms.actions.timetable;

import java.util.Date;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.timetable.SubjectWiseTimeTableViewForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.timetable.SubjectWiseTimeTableViewHandler;
import com.kp.cms.to.admin.CourseTO;

public class SubjectWiseTimeTableViewAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(SubjectWiseTimeTableViewAction.class);
	SubjectWiseTimeTableViewHandler handler=SubjectWiseTimeTableViewHandler.getinstance();
	
	public ActionForward initSubjectWiseTimeTableView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			SubjectWiseTimeTableViewForm objForm=(SubjectWiseTimeTableViewForm)form;
			try
			{
				List<CourseTO> courseList = CourseHandler.getInstance().getActiveCourses();
				objForm.setCourseList(courseList);
			}
			catch (Exception e) {
				log.error("Error occured in initClassWiseTimeTableView of ClassWiseTimeTableViewAction", e);
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.INIT_SUBJECT_WISE_TIME_TABLE);
	}
	
	public ActionForward fetchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectWiseTimeTableViewForm objForm=(SubjectWiseTimeTableViewForm)form;
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
					return mapping.findForward(CMSConstants.INIT_SUBJECT_WISE_TIME_TABLE);
				}	
				return mapping
						.findForward(CMSConstants.SUBJECT_WISE_TIME_TABLE_DISPLAY);
			}
			log.info("exit of initStaffAllocation of StaffAllocationAction.");
			return mapping.findForward(CMSConstants.INIT_SUBJECT_WISE_TIME_TABLE);
		}
		catch (Exception e) {
			log.error("Error occured in initClassWiseTimeTableView of ClassWiseTimeTableViewAction", e);
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	}
	
	public ActionForward printSubjectWiseTimeTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//SubjectWiseTimeTableViewForm objForm=(SubjectWiseTimeTableViewForm)form;
		return mapping.findForward("printSubjectWiseTimeTable");
	}
	
	
}
