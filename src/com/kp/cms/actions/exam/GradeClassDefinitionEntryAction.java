package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.kp.cms.forms.exam.GradeClassDefinitionEntryForm;
import com.kp.cms.handlers.exam.AttendanceMarksEntryHandler;
import com.kp.cms.handlers.exam.GradeClassDefinitionEntryHandler;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.GradeClassDefinitionTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class GradeClassDefinitionEntryAction  extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(GradeClassDefinitionEntryAction.class);
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
	public ActionForward initGradeClassDefinitionEntry(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call  of initGradeClassDefinitionEntry method in GradeClassDefinitionEntryAction.class");
		GradeClassDefinitionEntryForm  gradeClassDefinitionEntryForm =  (GradeClassDefinitionEntryForm)form;
		try
		{
			gradeClassDefinitionEntryForm.clearPage();
			setRequiredDatatoForm(gradeClassDefinitionEntryForm, request);
		}
		catch (ApplicationException e1) {
			e1.printStackTrace();
			log.error("Error in initGradeClassDefinitionEntry method in GradeClassDefinitionEntryAction.class");
			log.error("Error is .." + e1.getMessage());
			String msg = super.handleApplicationException(e1);
			gradeClassDefinitionEntryForm.setErrorMessage(msg);
			gradeClassDefinitionEntryForm.setErrorStack(e1.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

			// TODO: handle exception
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error in initGradeClassDefinitionEntry method in GradeClassDefinitionEntryAction.class");
			log.error("Error is .." + e.getMessage());
			String msg = super.handleApplicationException(e);
			gradeClassDefinitionEntryForm.setErrorMessage(msg);
			gradeClassDefinitionEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		log.debug("end  of initGradeClassDefinitionEntry method in GradeClassDefinitionEntryAction.class");
		return mapping.findForward(CMSConstants.GRADE_CLASS_DEFINITION_ENTRY);
	}
	private void setRequiredDatatoForm(GradeClassDefinitionEntryForm gradeClassDefinitionEntryForm,HttpServletRequest request) throws Exception {
		log.debug("call  of setRequiredDatatoForm method in GradeClassDefinitionEntryAction.class");
		gradeClassDefinitionEntryForm.clearPage();
		try
		{
			List<GradeClassDefinitionTO> gradeDefinitionList = new ArrayList<GradeClassDefinitionTO>();
			List<GradeClassDefinitionTO> displayGradeDefinitionList = new ArrayList<GradeClassDefinitionTO>();
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
            Map<Integer, Integer> schemeMap = new HashMap<Integer, Integer>();
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
            GradeClassDefinitionTO gradeClassDefinitionTO = new GradeClassDefinitionTO();
            gradeDefinitionList.add(gradeClassDefinitionTO);
            gradeClassDefinitionEntryForm.setGradeDefinitionList(gradeDefinitionList);
            gradeClassDefinitionEntryForm.setSchemeMap(schemeMap);
			List<ExamCourseUtilTO> coursList = new ArrayList<ExamCourseUtilTO>();
			coursList = AttendanceMarksEntryHandler.getInstance().getCourseList();
			gradeClassDefinitionEntryForm.setListExamCourseUtilTO(coursList);
			displayGradeDefinitionList = GradeClassDefinitionEntryHandler.getInstance().getGradeDefinitionList();
			gradeClassDefinitionEntryForm.setDisplayGradeDefinitionList(displayGradeDefinitionList);
		}
		catch (Exception e) {
			log.error("Error in setRequiredDatatoForm method in  AttendanceMarksEntryAction.class");
			throw new ApplicationException(e);
			
		}
		
		log.debug("end  of setRequiredDatatoForm method in GradeClassDefinitionEntryAction.class");
		
	}
	
	public ActionForward addMoreGradeClassMarks(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of addMoreGradeClassMarks method in GradeClassDefinitionEntryAction.class ");
		List<GradeClassDefinitionTO> gradeClassTOList =null;
		List<Boolean> addNewList= new ArrayList<Boolean>();
		ActionErrors errors= new ActionErrors();
		GradeClassDefinitionEntryForm  gradeClassDefinitionEntryForm =  (GradeClassDefinitionEntryForm)form;
		GradeClassDefinitionTO classDefinitionTO =  new GradeClassDefinitionTO();
		gradeClassTOList = gradeClassDefinitionEntryForm.getGradeDefinitionList();
		Iterator<GradeClassDefinitionTO> iterator = gradeClassTOList.iterator();
		while(iterator.hasNext())
		{
			Boolean addnew = false;
			GradeClassDefinitionTO to = iterator.next();
			if(to.getFromPercentage()!=null && to.getGrade()!= null && to.getToPercentage()!=null && !to.getFromPercentage().isEmpty() 
					&& !to.getToPercentage().isEmpty() && !to.getGrade().isEmpty() && to.getFromPercentage()!="" && 
					to.getToPercentage()!= "" && to.getGrade()!="" && to.getGradePoint()!=null && !to.getGradePoint().isEmpty()
					&& to.getGradePoint()!="" && to.getResultClass()!=null && !to.getResultClass().isEmpty() && to.getResultClass()!="")
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
			if(to.getGrade()==null || to.getGrade().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.admin.grade.col"));
			}
			if(to.getResultClass()==null || to.getResultClass().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.exam.gradeDefinition.resultClass"));
			}
			if(to.getGradePoint()== null || to.getGradePoint().isEmpty())
			{
				errors.add("error", new ActionError("knowledgepro.exam.gradeDefinition.gradePoint"));
			}
			addNewList.add(addnew);
		}
		if(!addNewList.contains(false))
		{ 
			gradeClassTOList.add(classDefinitionTO);
		}
		
		saveErrors(request,errors);
		gradeClassDefinitionEntryForm.setGradeDefinitionList(gradeClassTOList);
		log.debug("end of addMoreGradeClassMarks method in GradeClassDefinitionEntryAction.class ");
		return mapping.findForward(CMSConstants.GRADE_CLASS_DEFINITION_ENTRY);
	}
	public ActionForward removeMoreGradeMarks(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of removeMoreGradeMarks method in GradeClassDefinitionEntryAction.class ");
		List<GradeClassDefinitionTO> gradeClassMarkList =null;
		GradeClassDefinitionEntryForm  gradeClassDefinitionEntryForm =  (GradeClassDefinitionEntryForm)form;
		gradeClassMarkList = gradeClassDefinitionEntryForm.getGradeDefinitionList();
		if(gradeClassMarkList.size()>=1)
		{
			gradeClassMarkList.remove(gradeClassMarkList.size()-1);
		}
		gradeClassDefinitionEntryForm.setGradeDefinitionList(gradeClassMarkList);
		log.debug("end of removeMoreGradeMarks method in GradeClassDefinitionEntryAction.class ");
		return mapping.findForward(CMSConstants.GRADE_CLASS_DEFINITION_ENTRY);
	}
	
	public ActionForward addGradeClassDefinition(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of addGradeClassDefinition method in GradeClassDefinitionEntryAction.class");
		GradeClassDefinitionEntryForm  gradeClassDefinitionEntryForm =  (GradeClassDefinitionEntryForm)form;
		ActionErrors errors = gradeClassDefinitionEntryForm.validate(mapping, request);
		setUserId(request, gradeClassDefinitionEntryForm);
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		try
		{
			if(gradeClassDefinitionEntryForm.getGradeDefinitionList().size()>0)
			{
				Iterator<GradeClassDefinitionTO> iterator = gradeClassDefinitionEntryForm.getGradeDefinitionList().iterator();
				while(iterator.hasNext())
				{
					Boolean addnew = false;
					GradeClassDefinitionTO to = iterator.next();
					if(to.getFromPercentage()!=null && to.getGrade()!= null && to.getToPercentage()!=null && !to.getFromPercentage().isEmpty() 
							&& !to.getToPercentage().isEmpty() && !to.getGrade().isEmpty() && to.getFromPercentage()!="" && 
							to.getToPercentage()!= "" && to.getGrade()!="" && to.getGradePoint()!=null && !to.getGradePoint().isEmpty()
							&& to.getGradePoint()!="" && to.getResultClass()!=null && !to.getResultClass().isEmpty() && to.getResultClass()!="")
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
					if(to.getGrade()==null || to.getGrade().isEmpty())
					{
						errors.add("error", new ActionError("knowledgepro.admin.grade.col"));
					}
					if(to.getResultClass()==null || to.getResultClass().isEmpty())
					{
						errors.add("error", new ActionError("knowledgepro.exam.gradeDefinition.resultClass"));
					}
					if(to.getGradePoint()== null || to.getGradePoint().isEmpty())
					{
						errors.add("error", new ActionError("knowledgepro.exam.gradeDefinition.gradePoint"));
					}
				}
				if(errors.isEmpty())
				{

					isAdded = GradeClassDefinitionEntryHandler.getInstance().addGradeClassDefinition(gradeClassDefinitionEntryForm,request,errors);
					if(isAdded)
					{
						ActionMessage message = new ActionMessage("knowledgepro.sub.definition.coursewise.new.grade.class.definition.require.define.attendance.add.success" );
						messages.add("messages", message);
						saveMessages(request, messages);
						gradeClassDefinitionEntryForm.clearPage();
						setRequiredDatatoForm(gradeClassDefinitionEntryForm, request);
					}
					else
					{
						ActionMessage message = new ActionMessage("knowledgepro.sub.definition.coursewise.new.grade.class.definition.require.define.attendance.add.failed");
						messages.add("messages", message);
						saveMessages(request, messages);
						gradeClassDefinitionEntryForm.clearPage();
						setRequiredDatatoForm(gradeClassDefinitionEntryForm, request);
					}
				}
				else
				{
					saveErrors(request,errors);
					setRequiredDatatoForm(gradeClassDefinitionEntryForm, request);
				}
				
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.pettycash.norecord");
				messages.add("messages", message);
				saveMessages(request, messages);
				gradeClassDefinitionEntryForm.clearPage();
				setRequiredDatatoForm(gradeClassDefinitionEntryForm, request);
			}
		}
		catch (Exception e) {
			log.error("Error in addGradeClassDefinition method in GradeClassDefinitionEntryAction.class");
			throw new ApplicationException(e);
			// TODO: handle exception
		}
		log.debug("end of addGradeClassDefinition method in GradeClassDefinitionEntryAction.class");
		return mapping.findForward(CMSConstants.GRADE_CLASS_DEFINITION_ENTRY);
	}
	
}
