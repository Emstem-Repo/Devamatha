package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamExamResultsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.ExamResultsHandler;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamExamResultsAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamResultsHandler handler = new ExamResultsHandler();

	public ActionForward initExamResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		objform.setExamType(null);
		objform.setExamName(null);
		setRequiredDataToForm(objform);
		//getListExamResult(objform);
	//	objform.setListExamName(handler.getExamNameList());
    //  objform.setListExamResult(handler.getListExamResult());
		int examId=0;
		if(objform.getExamName()!=null && objform.getExamName().trim().isEmpty()){
		//	KeyValueTO to= objform.getListExamName().get(0);
			examId=Integer.parseInt(objform.getExamName());
		}
		//objform.setMapClass(handler.getClassCodeByExamName(examId));
		objform.setPublishOverallInternalComponentsOnly("off");
		objform.setInternalComponents(handler.getinternalComponents());
		getListExamResult(objform);
		return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}

	private void getListExamResult(ExamExamResultsForm objform) throws Exception{
		objform.setListExamResult(handler.getListExamResult(objform)) ;
	}

	/**
	 * added for loading exam name based on academic year and exam type - Smitha
	 * @param objform
	 * @throws Exception
	 */
	/**
	 * @param objform
	 * @throws Exception
	 */
	private void setRequiredDataToForm(ExamExamResultsForm objform) throws Exception {
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear().trim());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objform.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		if(objform.getExtype()==null || objform.getExtype().trim().isEmpty())
		objform.setExtype("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExtype().trim(),year); 
		objform.setExamNameMap(examMap);
		//objform.setExname(examMap.get(objform.getExamName()));
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExtype().trim());
		if((objform.getExamName()==null || objform.getExamName().trim().isEmpty()) && currentExam!=null){
			objform.setExamName(currentExam);
			objform.setExname(currentExam);
		}
		objform.setExtype(objform.getExtype().trim());
		
		if(objform.getExamName()!=null && !objform.getExamName().isEmpty()
				  && objform.getExtype()!=null && !objform.getExtype().isEmpty() && (objform.getDeanaryName()==null || objform.getDeanaryName().isEmpty())){
				objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getClassesMapByExam(objform.getExamName(),objform.getExtype(),objform.getProgramId()));
					}
		//objform.setExname(objform.getExamName().trim());
		objform.setYear(String.valueOf(year).trim());
		handler.getDeaneryMap(objform);
	}

	public ActionForward addExamExamResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		errors.clear();
		messages.clear();
		int examId=0;
		if(objform.getExname()!=null && !objform.getExname().isEmpty())
		{
	     examId = Integer.parseInt(objform.getExname());
		}
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
//		if (objform.getPublishDate() != null
//				&& objform.getPublishDate().length() > 0 && errors.isEmpty()) {
//			errors = validateDate(objform.getPublishDate(), examId);
//		}
		saveErrors(request, errors);
		setUserId(request, objform);
		String[] str = objform.getClassValues().split(",");
		ArrayList<Integer> listClass = new ArrayList<Integer>();
		if (str != null && str.length != 0) {
			listClass = new ArrayList<Integer>();
			for (int x = 0; x < str.length; x++) {
				if (str[x] != null && str[x].length() != 0) {
					listClass.add(Integer.parseInt(str[x]));
				}

			}
		}
		String programId=null;
		if (str != null && str.length != 0) {
			//objform.setMapClass(handler.getClassCodeByExamName(examId));
			
			objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(examId),
					objform.getExtype(),programId,objform.getDeanaryName()));
			objform = setMapClass(objform, listClass);
		} else {
			//objform.setMapClass(handler.getClassCodeByExamName(examId));
			if((objform.getExtype()!=null && !objform.getExtype().isEmpty()) && 
  		 			(objform.getDeanaryName()!=null && !objform.getDeanaryName().isEmpty()) && examId!=0){
				objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(examId),
					objform.getExtype(),programId,objform.getDeanaryName()));
			}
		}

		if (errors.isEmpty()) {
			try {

				int publishComponents = 0;

				if (objform.getPublishOverallInternalComponentsOnly() != null) {
					if (objform.getPublishOverallInternalComponentsOnly()
							.contains("on")) {
						publishComponents = 1;
					}
				}

				handler.addExamResults(examId, objform.getPublishDate(),
						listClass, publishComponents, objform.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamResults.success", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
				objform.getMapSelectedClass().clear();
			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.ExamResults.exists"));
				saveErrors(request, errors);
			}
			objform.setPublishOverallInternalComponentsOnly("off");
			objform.setInternalComponents(handler.getinternalComponents());

		} else {
			if (objform.getPublishOverallInternalComponentsOnly() != null
					&& objform.getPublishOverallInternalComponentsOnly()
							.contains("on")) {
				objform.setPublishOverallInternalComponentsOnly("on");
			} else {
				objform.setPublishOverallInternalComponentsOnly("off");
			}
			objform.setInternalComponents(handler
					.getInternalComponentsByClasses(examId));
		}

	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		getListExamResult(objform);
		objform.setExamName(String.valueOf(examId));
		//objform.setDeanaryName(objform.getDeanaryName());
	//objform.setListExamResult(handler.getListExamResult());

		return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}

	private ExamExamResultsForm setMapClass(ExamExamResultsForm objform,
			ArrayList<Integer> listClass) {

		Map<Integer, String> mapClas = objform.getMapClass();
		Map<Integer, String> mapSelectedClas = new HashMap<Integer, String>();
		for (int i = 0; i < listClass.size(); i++) {
			Integer lcint = listClass.get(i);
			mapSelectedClas.put(lcint, mapClas.get(lcint));
			mapClas.remove(lcint);
		}
		objform.setMapClass(mapClas);
		objform.setMapSelectedClass(mapSelectedClas);

		return objform;
	}

	public ActionForward deleteExamExamResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.delete(Integer.parseInt(id));
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.ExamResults.deletesuccess", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		//objform.setMapClass(handler.getClassList());
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		getListExamResult(objform);
	//	objform.setListExamResult(handler.getListExamResult());
		objform.setPublishOverallInternalComponentsOnly("off");
		objform.setInternalComponents(handler.getinternalComponents());
		return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}

	public ActionForward editExamExamResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		errors.clear();
		messages.clear();
		String rowId = request.getParameter("id");
		objform = handler.getUpdatableForm(objform, Integer.parseInt(rowId));
		setRequestToList(objform, request);
		request.setAttribute("ExamExamResultsOperation", "edit");
		request.setAttribute("Update", "Update");
		objform.setExname(objform.getExname().trim());
		return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}

	private void setRequestToList(ExamExamResultsForm objform,
			HttpServletRequest request) throws Exception{
	//	objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
		getListExamResult(objform);
	//	objform.setListExamResult(handler.getListExamResult());

	}

	public ActionForward updateExamExamResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		errors.clear();
		messages.clear();
		int examId = Integer.parseInt(objform.getExamName());
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
//		if (objform.getPublishDate() != null
//				&& objform.getPublishDate().length() > 0 && errors.isEmpty()) {
//			errors = validateDate(objform.getPublishDate(), examId);
//		}
		saveErrors(request, errors);
		setUserId(request, objform);
		int classId = Integer.parseInt(objform.getClassName());
		String programId=null;
		if (errors.isEmpty()) {

			try {

				int publishComponents = 0;

				if (objform.getPublishOverallInternalComponentsOnly() != null) {
					if (objform.getPublishOverallInternalComponentsOnly()
							.contains("on")) {
						publishComponents = 1;
					}
				}

				handler.update(objform.getId(), examId, objform
						.getPublishDate(), classId, publishComponents, objform
						.getUserId());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.ExamResults.updatesuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();

			} catch (DuplicateException e1) {
				errors.add("error", new ActionError(

				"knowledgepro.exam.ExamResults.exists"));
				saveErrors(request, errors);
				if((objform.getExtype()!=null && !objform.getExtype().isEmpty()) && 
	  		 			(objform.getDeanaryName()!=null && !objform.getDeanaryName().isEmpty()) && examId!=0){
				objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(examId),
						objform.getExtype(),programId,objform.getDeanaryName()));
				}
				ArrayList<Integer> listClass = new ArrayList<Integer>();
				listClass.add(classId);
				objform = setMapClass(objform, listClass);
			}
		} else {
			request.setAttribute("ExamExamResultsOperation", "edit");
			ArrayList<Integer> listClass = new ArrayList<Integer>();
			if((objform.getExtype()!=null && !objform.getExtype().isEmpty()) && 
  		 			(objform.getDeanaryName()!=null && !objform.getDeanaryName().isEmpty()) && examId!=0){
				
				objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(examId),
					objform.getExtype(),programId,objform.getDeanaryName()));
			}
			listClass.add(classId);
			objform = setMapClass(objform, listClass);
			if (objform.getPublishOverallInternalComponentsOnly() != null
					&& objform.getPublishOverallInternalComponentsOnly()
							.contains("on")) {
				objform.setPublishOverallInternalComponentsOnly("on");
			} else {
				objform.setPublishOverallInternalComponentsOnly("off");
			}
			objform.setInternalComponents(handler
					.getInternalComponentsByClasses(examId));
		}

//		objform.setListExamName(handler.getExamNameList());
		setRequiredDataToForm(objform);
//	    objform.setListExamResult(handler.getListExamResult());
		return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}

	private ActionErrors validateDate(String publishDate, int examId) throws Exception {

	/*	String formattedString = CommonUtil.ConvertStringToDateFormat(
				publishDate, "dd/MM/yyyy", "MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();

		if (date.compareTo(origdate) < 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamResults.InvalidDate"));
		}*/

		String dateTimeStr = publishDate + " " + 12 + ":" + 0 + ":00";
		String formatDate = CommonUtil.ConvertStringToDateFormat(dateTimeStr,
				"dd/MM/yyyy hh:mm:ss", "MM/dd/yyyy h:mm:ss a");

		String a = handler.isDateTimeValid(formatDate, examId);
		if (a.contains("NotValidDate")) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.ExamResults.InvalidExamDate"));
		}

//		if (a.contains("TimetableNotreated")) {
//			errors.add("error", new ActionError(
//					"knowledgepro.exam.ExamResults.InvalidExam"));
//		}

		return errors;
	}
	
	public ActionForward getExamListByClick(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		ActionMessages messages = new ActionMessages();
		objform.setListExamResult(null);
		try {
			setUserId(request, objform);
			String year=objform.getYear();
			String programId=null;
		     if(year==null){
			 Calendar calendar = Calendar.getInstance(); 
			 year =String.valueOf(calendar.get(Calendar.YEAR));
		     }
            setRequiredDataToForm(objform);
			// getListExamResult(objform);
			 int examId=0;
  		 if((objform.getExamName()!=null) && !(objform.getExamName().trim().isEmpty())){
    		examId=Integer.parseInt(objform.getExamName());
		}
  		 	if((objform.getExtype()!=null && !objform.getExtype().isEmpty()) && 
  		 			(objform.getDeanaryName()!=null && !objform.getDeanaryName().isEmpty()) && examId!=0){
			objform.setMapClass(ExamPublishHallTicketHandler.getInstance().getclassesMap(String.valueOf(examId),
					objform.getExtype(),programId,objform.getDeanaryName()));
  		 	}
  		 	objform.setPublishOverallInternalComponentsOnly("off");
			objform.setInternalComponents(handler.getinternalComponents());
				
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	 objform.clearPage();
	 return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	}
	
	/**
	 * this method added by mahiboob
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getExamDetailsResults(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{
		ExamExamResultsForm objform = (ExamExamResultsForm) form;
		ActionMessages messages = new ActionMessages();
		objform.setListExamResult(null);
		int year=0;
		try {
			setUserId(request, objform);
			setRequiredDataToForm(objform);
			 getListExamResult(objform);
				/*Map<Integer, String> classMap = new HashMap<Integer, String>();
				int examId = Integer.parseInt(objform.getExamName());
				if (objform.getExamName() != null
						&& objform.getExamName().length() != 0) {
					classMap=ExamGenHandler.getInstance().getClassCodeByExamName(examId);
					
					not required this  below method
						//classMap = ExamResultsHandler.getInstance().getClassesForExam(examId,objform.getExtype());	
					
					classMap =   CommonUtil.sortMapByValueForAlphaNumeric(classMap);

				}
				objform.setMapClass(classMap);*/
				objform.setPublishOverallInternalComponentsOnly("off");
				objform.setInternalComponents(handler.getinternalComponents());
				objform.setExname(objform.getExamName().trim());
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	 objform.clearPage();
	 return mapping.findForward(CMSConstants.EXAM_EXAM_RESULTS);
	
	}
}
