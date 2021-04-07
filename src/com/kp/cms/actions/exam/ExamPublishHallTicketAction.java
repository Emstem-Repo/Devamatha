package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamPublishHallTicketForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamPublishHallTicketAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamPublishHallTicketHandler handler = new ExamPublishHallTicketHandler();

	// gets initial list of Exam Definition
	public ActionForward initExamPublishHallTicket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		errors.clear();
		objform.pageclear();
		setIntialValues(objform);
		//start by giri
		handler.getDeaneryMap(objform);
		//end by giri
		HttpSession session = request.getSession(false);		
		if(objform.isExamCenterDispaly()){
			session.setAttribute("examCenterDisp", "true");
		}
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);

	}

	// On - PUBLISH
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
        String[] stayClass = objform.getStayClass();
		Integer agreeName = null;
		Integer footerName = null;
		if (objform.getAgreementName() != null
				&& objform.getAgreementName().length() > 0)
			agreeName = Integer.parseInt(objform.getAgreementName());
		if (objform.getFooterName() != null
				&& objform.getFooterName().length() > 0)
			footerName = Integer.parseInt(objform.getFooterName());

		
		if (errors.isEmpty()) {

			 ActionErrors errorsDate = validateData(objform);
			saveErrors(request, errors);

			if (errorsDate.isEmpty()) {
				int programTypeId = 0;
				if (objform.getProgramTypeId().length() != 0) {
					programTypeId = Integer
							.parseInt(objform.getProgramTypeId());
				}

				try {
					if(stayClass!=null && !stayClass[0].isEmpty())
					{
						
					handler.addList(Integer.parseInt(objform.getExamName()),
							programTypeId, objform.getStayClass(),
							agreeName, footerName, objform.getPublishFor(),
							objform.getDownLoadStartDate(), objform
									.getDownLoadEndDate(), objform.getUserId(), objform.getExamCenterCode(), objform.getExamCenter(),objform.getRevaluationEndDate());

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.addsuccess", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					
					// /*code added by chandra
					//sending SMS to students for publishing Halticket or Marks-card
					String examType=handler.getExamTypeBYExamTypeId(objform.getExamType());						
				/*	if(examType !=null && !examType.isEmpty()){
						if(examType.equalsIgnoreCase("Regular")){
							handler.sendingSMStoStudents(objform.getStayClass(),objform,examType);
						}else if(examType.equalsIgnoreCase("Supplementary")){
							handler.sendingSMSForSupplimentoryStudents(objform.getStayClass(),objform,examType);
						}
					}*/
						
					
					// */code added by chandra	

//					objform.pageclear();
//					objform.clear();
//					objform.reset(mapping, request);
//					setIntialValues(objform);
//					return mapping
//							.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);
					}
					else
					{
						errors.add("error", new ActionError(
								"knowledgepro.exam.publish.select.classes"));
						saveErrors(request, errors);
						
					}
				} catch (DuplicateException e1) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.publish.hall.ticket.already.exists",objform.getPublishFor(),e1.getMessage()));
					saveErrors(request, errors);
				}
				 catch (ApplicationException e2) {
						errors.add("error", new ActionError(
								"knowledgepro.exam.publish.marks.card.error",objform.getPublishFor(),e2.getMessage()));
						saveErrors(request, errors);
					}
			}
		}

		//objform.setMainList(handler.getList(objform));
		objform.setListExamtype(handler.getExamTypeList(objform));
		if(objform.getExamType()!=null && !objform.getExamType().isEmpty()){
		//	objform.setListExamName(new ExamTimeTableHandler().getExamName(Integer.parseInt(objform.getExamType())));
			setExamNameMapToForm(objform);
		}
		objform.setProgramTypeList(handler.getProgramTypeList());
		objform=retainAllValues(objform);
		objform.setMapSelectedClass(null);
		//by giri
		if(!errors.isEmpty()){
			handler.mapSelectedClassByStyleClass(objform);
		}
		//end by giri
		request.setAttribute("editPublishHallTicket", "add");
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);

	}

	// On - EDIT
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		errors = objform.validate(mapping, request);

		saveErrors(request, errors);
			if(isCancelled(request)){
				objform = handler.getUpdatableForm(objform);
				request.setAttribute("editPublishHallTicket", "edit");
				//objform.setMainList(handler.getList(objform));
				request.setAttribute("Update", "Update");
				HttpSession session = request.getSession(false);
				if((session.getAttribute("examCenterDisp")!= null && session.getAttribute("examCenterDisp").toString().equalsIgnoreCase("true"))){
					objform.setExamCenterDispaly(true);
				}

				return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);
			}
		if (errors.isEmpty()) {
			try {

				Integer agreeName = null;
				Integer footerName = null;
				if (objform.getAgreementName() != null
						&& objform.getAgreementName().length() > 0)
					agreeName = Integer.parseInt(objform.getAgreementName());
				if (objform.getFooterName() != null
						&& objform.getFooterName().length() > 0)
					footerName = Integer.parseInt(objform.getFooterName());

				 ActionErrors errorsDate = validateData(objform);
				saveErrors(request, errors);
				if (errorsDate.isEmpty()) {

					int programTypeId = 0;
					if (objform.getProgramTypeId().length() != 0) {
						programTypeId = Integer.parseInt(objform
								.getProgramTypeId());
					}

					handler.update(objform.getId(), Integer.parseInt(objform
							.getExamName()), objform.getClassCodeIdsTo(),
							agreeName, footerName, objform.getPublishFor(),
							objform.getDownLoadStartDate(), objform
									.getDownLoadEndDate(), programTypeId, objform.getExamCenterCode(), objform.getExamCenter(),objform.getRevaluationEndDate());

					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.attendanceMarks.updated", "");
					messages.add("messages", message);
					saveMessages(request, messages);
	/**	to set the required data to form before redirecting to init jsp
	 			return initExamPublishHallTicket(mapping,
							 form,  request,
							 response); **/
				}
			} catch (DuplicateException e1) {
				request.setAttribute("editPublishHallTicket", "edit");
				errors.add("error", new ActionError("knowledgepro.exam.publish.hall.ticket.already.exists",objform.getPublishFor(),e1.getMessage()));
				saveErrors(request, errors);
			}
		}
		//request.setAttribute("editPublishHallTicket", "edit");
		//objform.setMainList(handler.getList(objform));
		objform.setListExamtype(handler.getExamTypeList(objform));
		if(objform.getExamType()!=null && !objform.getExamType().isEmpty()){
		//	objform.setListExamName(new ExamTimeTableHandler().getExamName(Integer.parseInt(objform.getExamType())));
			setExamNameMapToForm(objform);
		}
		objform.setProgramTypeList(handler.getProgramTypeList());
	//setting class list to form after update	
		if (objform.getExamName() != null && objform.getExamName().length() > 0 && objform.getProgramTypeId()!=null && !objform.getProgramTypeId().isEmpty()) {
			objform.setMapClass(ExamGenHandler.getInstance().getclassesMap(objform.getExamName(), objform.getExamType(), objform.getProgramTypeId(), objform.getDeanaryName()));

		} else {
			objform.setMapClass(new HashMap<Integer, String>());
		}
		objform.setPublishFor(null);
		objform.setDownLoadStartDate(null);
		objform.setDownLoadEndDate(null);
		request.setAttribute("Update", "Update");
		//objform = retainAllValues(objform);
		if(!errors.isEmpty()){
			handler.mapSelectedClassByStyleClass(objform);
		}
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);

	}

	// On - EDIT (To retain all values)
	private ExamPublishHallTicketForm retainAllValues(
			ExamPublishHallTicketForm objform) throws Exception {
		//start by giri
		handler.getDeaneryMap(objform);
		//end by giri
		objform = handler.getUpdatableFormAfterErrorMessages(objform);
		objform.setId(objform.getId());
		objform.setAgreementName(objform.getAgreementName());
		objform.setFooterName(objform.getFooterName());
		objform.setExamName(objform.getExamName());
		objform.setExamType(objform.getExamType());
		objform.setExamTypeId(objform.getExamTypeId());
		objform.setProgramType(objform.getProgramType());
		//objform.setClassCodeIdsFrom(objform.getClassCodeIdsFrom());
		objform.setClassCodeIdsTo(objform.getStayClass());
		objform.setDownLoadStartDate(objform.getDownLoadStartDate());
		objform.setDownLoadEndDate(objform.getDownLoadEndDate());
		objform.setExamCenterCode(objform.getExamCenterCode());
		objform.setExamCenter(objform.getExamCenter());
		return objform;
	}

	/**
	 * @param objform
	 * @return
	 */
	private ActionErrors validateData(ExamPublishHallTicketForm objform) {
		Date startDate=null;
		Date endDate=null;
		if ((objform.getDownLoadStartDate() != null && objform
				.getDownLoadStartDate().trim().length() > 0)) {
			startDate = new Date(objform.getDownLoadStartDate());

		}
		if ((objform.getDownLoadEndDate() != null && objform
				.getDownLoadEndDate().trim().length() > 0)) {
			endDate = new Date(objform.getDownLoadEndDate());
		}

		startDate = CommonUtil.ConvertStringToSQLDate(objform
				.getDownLoadStartDate());
		endDate = CommonUtil.ConvertStringToSQLDate(objform
				.getDownLoadEndDate());
		int flag  = CommonUtil.getDaysDiff(startDate, endDate);
		if (flag < 0) {
			errors.add("error", new ActionError( "knowledgepro.exam.publishHM.MarksCard.validDate"));

		}else{
			if(objform.getRevaluationEndDate()!=null && !objform.getRevaluationEndDate().isEmpty() && CommonUtil.isValidDate(objform.getRevaluationEndDate())){
				Date d=CommonUtil.ConvertStringToSQLDate(objform
						.getRevaluationEndDate());
				if(!(d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0)){
					errors.add("error", new ActionError( "knowledgepro.admission.empty.err.message","Revaluation End Date Should Between Download Start Date And Download End Date"));
				}
			}
			

		}

		return errors;
	}

	// On - EDIT to set the values to form
	public ActionForward editPublishHallTicket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		setUserId(request, objform);
		objform = handler.getUpdatableForm(objform);
		request.setAttribute("editPublishHallTicket", "edit");
		//objform.setMainList(handler.getList(objform));
		request.setAttribute("Update", "Update");
		HttpSession session = request.getSession(false);
		if((session.getAttribute("examCenterDisp")!= null && session.getAttribute("examCenterDisp").toString().equalsIgnoreCase("true"))){
			objform.setExamCenterDispaly(true);
		}

		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);

	}

	// To get Exam Type List, Program Type List, Main Grid
	public void setIntialValues(ExamPublishHallTicketForm objform) throws Exception {
		objform.setListExamtype(handler.getExamTypeList(objform));
		String currentExamName=null;
		if(objform.getExamType()!=null && !objform.getExamType().isEmpty()){
			if(objform.getExamName()==null || objform.getExamName().trim().isEmpty()){
		   currentExamName=handler.getCurrentExamName(Integer.parseInt(objform.getExamType()));
			}
		if(currentExamName!=null && !currentExamName.isEmpty()){
			objform.setExamName(currentExamName);
		}
		
		//	objform.setListExamName(new ExamTimeTableHandler().getExamName(Integer.parseInt(objform.getExamType())));
			setExamNameMapToForm(objform);
		}
		//Setting the class list to form if current exam name exists
		/*if (objform.getExamName() != null && !objform.getExamName().trim().isEmpty()) {
			objform.setMapClass(handler.getClassCodeByExamNameProgramType(Integer
					.parseInt(objform.getExamName()),objform.getProgramTypeId()));

		} else {*/
			objform.setMapClass(new HashMap<Integer, String>());
		//}
		objform.setProgramTypeList(handler.getProgramTypeList());
		//objform.setMainList(handler.getList(objform));

	}

	/**
	 * setting the exam name map according to exam type and academic year -Smitha
	 * @param objform
	 * @throws Exception
	 */
	private void setExamNameMapToForm(ExamPublishHallTicketForm objform) throws Exception{
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
	}

	// On - DELETE
	public ActionForward deletePublishHallTicket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		String id = request.getParameter("id");
		handler.getExamTypeAndExamName(id,objform);
		handler.delete(Integer.parseInt(id));
		ActionMessage message = new ActionMessage(
				"knowledgepro.exam.attendanceMarks.delte", "");
		messages.add("messages", message);
		saveMessages(request, messages);
		
		setIntialValues(objform);
		//start by giri
		handler.getDeaneryMap(objform);
		//end by giri
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);
	}
	
	/**
	 * To set the classes and display list by exam name
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setListByExamName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
		
		//objform.setMainList(handler.getList(objform));
		objform.setListExamtype(handler.getExamTypeList(objform));
		if(objform.getExamType()!=null && !objform.getExamType().isEmpty()){
		//	objform.setListExamName(new ExamTimeTableHandler().getExamName(Integer.parseInt(objform.getExamType())));
			setExamNameMapToForm(objform);
		}
		objform.setProgramTypeList(handler.getProgramTypeList());
		objform=retainAllValues(objform);
		request.setAttribute("editPublishHallTicket", "add");
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTheGridList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamPublishHallTicketForm objform = (ExamPublishHallTicketForm) form;
	    	List<ExamPublishHallTicketTO> list= handler.getList(objform);
	    	if(list!=null && !list.isEmpty()){
	    		request.setAttribute("list", list);
	    		return mapping.findForward("ajaxResponseForGridList"); 
	    	}
		return mapping.findForward(CMSConstants.EXAM_PUBLISH_HALL_TICKET);
	}

}
