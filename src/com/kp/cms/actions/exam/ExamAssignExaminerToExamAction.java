/**
 *Jan 1, 2010
 * @author developed by 9Elements Team
 */
package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamAssignExaminerToExamForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignExaminerToExamHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.exam.EmployeeTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamAssignExaminerToExamAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	ExamAssignExaminerToExamHandler handler = new ExamAssignExaminerToExamHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamAssignExaminerToExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignExaminerToExamForm objForm = (ExamAssignExaminerToExamForm) form;
		setRequiredDataToForm(objForm);
	//	objForm.setExamNameList(handler.getExamNameList());
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_TO_EXAM);
	}

	/**
	 * added by Smitha for new input parameters
	 * setting the initial data to init jsp
	 * @param objForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(ExamAssignExaminerToExamForm objForm) throws Exception {
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objForm.getYear()!=null && !objForm.getYear().isEmpty()){
			year=Integer.parseInt(objForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objForm.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		objForm.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objForm.getExamType(),year); 
		objForm.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objForm.getExamType());
		if(currentExam!=null){
		objForm.setExamName(currentExam);
		}
		
	}

	// Called when continue button is clicked
	public ActionForward assignExaminer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamAssignExaminerToExamForm objForm = (ExamAssignExaminerToExamForm) form;
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objForm);
		if (errors.isEmpty()) {
			List<EmployeeTO> list = handler.getDetails(Integer.parseInt(objForm
					.getExamName()));
			objForm.setExaminerList(list);
			objForm.setExaminerListSize(list.size());
			objForm.setExamNameId(Integer.parseInt(objForm.getExamName()));
			objForm.setExamName(handler.getExamNameByExamId(Integer
					.parseInt(objForm.getExamName())));
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_TO_EXAM_RESULT);
		}
	//	objForm.setExamNameList(handler.getExamNameList());
		setRequiredDataToForm(objForm);
		return mapping.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_TO_EXAM);

	}

	// called when submit is called
	public ActionForward addExaminerToExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamAssignExaminerToExamForm objForm = (ExamAssignExaminerToExamForm) form;
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);

		setUserId(request, objForm);
		if (errors.isEmpty()) {

			String[] examiner = request.getParameterValues("parent");

			List<Integer> listExaminer = new ArrayList<Integer>();
			if (examiner == null) {

			} else {
				listExaminer = new ArrayList<Integer>();
				for (int x = 0; x < examiner.length; x++) {
					listExaminer.add(Integer.parseInt(examiner[x]));
				}
			}
			handler.addExaminerToExam(listExaminer, objForm.getExamNameId());
			
			ActionMessage message = new ActionMessage(
					"knowledgepro.admin.addsuccess","Examiner");
			messages.add("messages", message);
			saveMessages(request, messages);

			objForm.clearPage();
//			objForm.setExamNameList(handler.getExamNameList());
			setRequiredDataToForm(objForm);
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_TO_EXAM);
		} else {
			List<EmployeeTO> list = handler.getDetails(objForm.getExamNameId());
			objForm.setExaminerList(list);
			objForm.setExaminerListSize(list.size());
			objForm.setExamNameId(objForm.getExamNameId());
			objForm.setExamName(handler.getExamNameByExamId(objForm
					.getExamNameId()));
			return mapping
					.findForward(CMSConstants.EXAM_ASSIGN_EXAMINER_TO_EXAM_RESULT);
		}

	}

}
