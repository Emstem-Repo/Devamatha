package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.AttendanceMarksEntryForm;
import com.kp.cms.handlers.exam.AttendanceMarksEntryHandler;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class AttendanceMarksEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AttendanceMarksEntryAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method will be invoke when the particular link clicked.
	 * 
	 */
	
	public ActionForward initAttendaceMarkEntry(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.debug("call of initAttendaceMarkEntry method in AttendanceMarksEntryAction.class");
		AttendanceMarksEntryForm  attendanceMarksEntryForm  = (AttendanceMarksEntryForm)form;
		try
		{
			attendanceMarksEntryForm.clearPage();
			setRequiredDatatoForm(attendanceMarksEntryForm, request);
		}
		catch (ApplicationException e1) {
			e1.printStackTrace();
			log.error("Error in initAttendaceMarkEntry method in AttendanceMarksEntryAction.class");
			log.error("Error is .." + e1.getMessage());
			String msg = super.handleApplicationException(e1);
			attendanceMarksEntryForm.setErrorMessage(msg);
			attendanceMarksEntryForm.setErrorStack(e1.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

			// TODO: handle exception
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in initAttendaceMarkEntry method in AttendanceMarksEntryAction.class");
			log.error("Error is .." + e.getMessage());
			String msg = super.handleApplicationException(e);
			attendanceMarksEntryForm.setErrorMessage(msg);
			attendanceMarksEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		log.debug("end of initAttendaceMarkEntry method in AttendanceMarksEntryAction.class");
		return mapping.findForward(CMSConstants.ATTENDACE_MARK_ENTRY_FOR_EXAM);

	}
	private void setRequiredDatatoForm(AttendanceMarksEntryForm attendanceMarksEntryForm,HttpServletRequest request) throws Exception {
		
		log.debug("call of setRequiredDatatoForm method in  AttendanceMarksEntryAction.class");
		attendanceMarksEntryForm.clearPage();
		try
		{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
            Map<Integer, Integer> schemeMap = new HashMap<Integer, Integer>();
            List<AttendanceMarkAndPercentageTO> attendnaceList = new ArrayList<AttendanceMarkAndPercentageTO>();
            schemeMap.put(1, 1);
            schemeMap.put(2, 2);
            schemeMap.put(3, 3);
            schemeMap.put(4, 4);
            schemeMap.put(5, 5);
            schemeMap.put(6, 6);
            schemeMap.put(7, 7);
            schemeMap.put(8, 8);
            schemeMap.put(9, 9);
            schemeMap.put(10, 10);
           
            attendanceMarksEntryForm.setSchemeMap(schemeMap);
			List<ExamCourseUtilTO> coursList = new ArrayList<ExamCourseUtilTO>();
			List<AttendanceMarkAndPercentageTO> markandPercentageList = new ArrayList<AttendanceMarkAndPercentageTO>();
			AttendanceMarkAndPercentageTO percentageTO = new AttendanceMarkAndPercentageTO();
			markandPercentageList.add(percentageTO);
			attendanceMarksEntryForm.setMarkandPercentageList(markandPercentageList);
			coursList = AttendanceMarksEntryHandler.getInstance().getCourseList();
			attendanceMarksEntryForm.setListExamCourseUtilTO(coursList);
			attendnaceList = AttendanceMarksEntryHandler.getInstance().getAttendanceList();
			attendanceMarksEntryForm.setAttendaceList(attendnaceList);
			
		}
		catch (Exception e) {
			log.error("Error in setRequiredDatatoForm method in  AttendanceMarksEntryAction.class");
			throw new ApplicationException(e);
			
		}
		log.debug("end of setRequiredDatatoForm method in  AttendanceMarksEntryAction.class");
		
		
	}
	
	public ActionForward addMoreMarks(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of addMoreMarks method in AttendanceMarksEntryAction.class ");
		List<AttendanceMarkAndPercentageTO> markandPercentageList =null;
		List<Boolean> addNewList= new ArrayList<Boolean>();
		ActionErrors errors= new ActionErrors();
		AttendanceMarksEntryForm  attendanceMarksEntryForm  = (AttendanceMarksEntryForm)form;
		AttendanceMarkAndPercentageTO percentageTO = new AttendanceMarkAndPercentageTO();
		markandPercentageList = attendanceMarksEntryForm.getMarkandPercentageList();
		Iterator<AttendanceMarkAndPercentageTO> iterator = markandPercentageList.iterator();
		while(iterator.hasNext())
		{
			Boolean addnew = false;
			AttendanceMarkAndPercentageTO to = iterator.next();
			if(to.getFromPercentage()!=null && to.getMarks()!= null && to.getToPercentage()!=null && !to.getFromPercentage().isEmpty() 
					&& !to.getToPercentage().isEmpty() && !to.getMarks().isEmpty() && to.getFromPercentage()!="" && 
					to.getToPercentage()!= "" && to.getMarks()!="")
			{
				addnew = true;
			}
			if(to.getFromPercentage()==null || to.getFromPercentage().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.from.percentage.require"));
			}
			if(to.getToPercentage()==null || to.getToPercentage().isEmpty() )
			{
				errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.to.percentage.require"));
			}
			if(to.getMarks()==null || to.getMarks().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.mark.require"));
			}
			addNewList.add(addnew);
		}
		if(!addNewList.contains(false))
		{ 
			markandPercentageList.add(percentageTO);
		}
		
		saveErrors(request,errors);
		attendanceMarksEntryForm.setMarkandPercentageList(markandPercentageList);
		log.debug("end of addMoreMarks method in AttendanceMarksEntryAction.class ");
		return mapping.findForward(CMSConstants.ATTENDACE_MARK_ENTRY_FOR_EXAM);
	}
	
	public ActionForward removeMoreMarks(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of addMoreMarks method in AttendanceMarksEntryAction.class ");
		List<AttendanceMarkAndPercentageTO> markandPercentageList =null;
		AttendanceMarksEntryForm  attendanceMarksEntryForm  = (AttendanceMarksEntryForm)form;
		AttendanceMarkAndPercentageTO percentageTO = new AttendanceMarkAndPercentageTO();
		markandPercentageList = attendanceMarksEntryForm.getMarkandPercentageList();
		if(markandPercentageList.size()>=1)
		{
		markandPercentageList.remove(markandPercentageList.size()-1);
		}
		attendanceMarksEntryForm.setMarkandPercentageList(markandPercentageList);
		log.debug("end of addMoreMarks method in AttendanceMarksEntryAction.class ");
		return mapping.findForward(CMSConstants.ATTENDACE_MARK_ENTRY_FOR_EXAM);
	}
	
	public ActionForward addAttendanceMark(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of addAttendanceMark  method in AttendanceMarksEntryAction.class");
		AttendanceMarksEntryForm  attendanceMarksEntryForm  = (AttendanceMarksEntryForm)form;
		ActionErrors errors = attendanceMarksEntryForm.validate(mapping, request);
		setUserId(request, attendanceMarksEntryForm);
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		try
		{
			if(attendanceMarksEntryForm.getMarkandPercentageList().size()>0)
			{
				Iterator<AttendanceMarkAndPercentageTO> iterator = attendanceMarksEntryForm.getMarkandPercentageList().iterator();
				while(iterator.hasNext())
				{
					Boolean addnew = false;
					AttendanceMarkAndPercentageTO to = iterator.next();
					if(to.getFromPercentage()!=null && to.getMarks()!= null && to.getToPercentage()!=null && !to.getFromPercentage().isEmpty() 
							&& !to.getToPercentage().isEmpty() && !to.getMarks().isEmpty() && to.getFromPercentage()!="" && 
							to.getToPercentage()!= "" && to.getMarks()!="")
					{
						addnew = true;
					}
					if(to.getFromPercentage()==null || to.getFromPercentage().isEmpty())
					{
						errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.from.percentage.require"));
					}
					if(to.getToPercentage()==null || to.getToPercentage().isEmpty() )
					{
						errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.to.percentage.require"));
					}
					if(to.getMarks()==null || to.getMarks().isEmpty())
					{
						errors.add("error", new ActionError("knowledgepro.sub.definition.coursewise.new.attendance.mark.mark.require"));
					}
				}
				if(errors.isEmpty())
				{

					isAdded = AttendanceMarksEntryHandler.getInstance().addAttendanceMarks(attendanceMarksEntryForm,request,errors);
					if(isAdded)
					{
						ActionMessage message = new ActionMessage("knowledgepro.sub.definition.coursewise.new.attendance.mark.mark.require.define.attendance.add.success" );
						messages.add("messages", message);
						saveMessages(request, messages);
						attendanceMarksEntryForm.clearPage();
						setRequiredDatatoForm(attendanceMarksEntryForm, request);
					}
					else
					{
						ActionMessage message = new ActionMessage("knowledgepro.sub.definition.coursewise.new.attendance.mark.mark.require.define.attendance.add.failed");
						messages.add("messages", message);
						saveMessages(request, messages);
						attendanceMarksEntryForm.clearPage();
						setRequiredDatatoForm(attendanceMarksEntryForm, request);
					}
				}
				else
				{
					saveErrors(request,errors);
					setRequiredDatatoForm(attendanceMarksEntryForm, request);
				}
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.pettycash.norecord");
				messages.add("messages", message);
				saveMessages(request, messages);
				attendanceMarksEntryForm.clearPage();
				setRequiredDatatoForm(attendanceMarksEntryForm, request);
			}
		}
		catch (Exception e) {
			log.error("Error in addAttendanceMark method in AttendanceMarksEntryAction.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		log.debug("end of addAttendanceMark  method in AttendanceMarksEntryAction.class");
		return mapping.findForward(CMSConstants.ATTENDACE_MARK_ENTRY_FOR_EXAM);
	}
	
}
