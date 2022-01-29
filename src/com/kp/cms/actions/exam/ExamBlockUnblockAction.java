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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamBlockUnblockForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.helpers.exam.ExamBlockUnblockHallTicketHelper;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamBlockUnblockAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamBlockUnblockHallTicketHandler handler = new ExamBlockUnblockHallTicketHandler();
	ExamBlockUnblockHallTicketHelper helper = new ExamBlockUnblockHallTicketHelper();
	String EXAM_BLOCK_UNBLOCK_RESULT_NEW = "blockUnblockResultNew";

	public ActionForward initBlockUnblock(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
	//	objform.setListExamName(handler.getExamNameList());
		
		objform.resetFields();
		setExamNameToForm(objform);
/*		int examId = 0;
		if (objform.getListExamName() != null
				&& objform.getListExamName().size() != 0) {
			KeyValueTO to = objform.getListExamName().get(0);
			examId = to.getId();
		}*/
		if(objform.getExamId()!=null && objform.getExamId().length()>0){
			objform.setExamName(objform.getExamId());
		}
		if(objform.getExamName()!=null && !objform.getExamName().trim().isEmpty())
		objform.setMapClass(CommonUtil.sortMapByValue(handler.getClassCodeByExamName(Integer.parseInt(objform.getExamName()))));

		return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
	}

	/**
	 *  new input parameters-Academic Year,Exam Type, Exam Name List dynamic loading added by Smitha
	 * @param objform
	 * @throws Exception
	 */
	private void setExamNameToForm(ExamBlockUnblockForm objform) throws Exception{
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objform.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		if(objform.getExamType()==null || objform.getExamType().trim().isEmpty())
		objform.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if(currentExam!=null){
			objform.setExamName(currentExam);
		}
		
		
	}

	@SuppressWarnings("deprecation")
	public ActionForward blockUnBlockExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		boolean block = true;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		
		setUserId(request, objform);
		String[] str = request.getParameterValues("classValue");
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			
			String name = handler.getExamName(objform.getExamName());
			objform.setExamId(objform.getExamName());

			if (objform.getBlockType() != null && objform.getBlockType().equalsIgnoreCase("Unblock")) {
				request.setAttribute("examBlockUnBlockOperation", "UnBlock");
			}else{
				request.setAttribute("examBlockUnBlockOperation", "Block");
			}
			if(str==null || str.length==0){
				errors.add("error",new ActionError("knowledgepro.exam.blockUnblock.classRequired"));
			}
			ArrayList<Integer> listClassIds = new ArrayList<Integer>();
			if (str != null && str.length != 0) {
				for (int x = 0; x < str.length; x++) {
					if (str[x] != null && str[x].trim().length() > 0) {
						listClassIds.add(Integer.parseInt(str[x]));
					} else {
						errors.add("error",new ActionError("knowledgepro.exam.blockUnblock.classRequired"));
						saveErrors(request, errors);
					}
				}
			}
			if (errors.isEmpty()) {
				List<ExamBlockUnBlockCandidatesTO> listCandidates;
				int examId = Integer.parseInt(objform.getExamName());
				boolean useHallTicket = true;
				String type="";
				if ("2".equals(objform.getTypeId())) {
					useHallTicket = false;
					objform.setType("Marks Card");
					type="M";
				} 
				 else if("3".equals(objform.getTypeId())){
					 useHallTicket = false;
					objform.setType("Regular Application");
					type="A";
				}
				else {
					objform.setType("Hall Ticket");
					type="H";
				}
				
				//raghu
				if (objform.getBlockType() != null && objform.getBlockType().equalsIgnoreCase("Block")) {
					listCandidates = handler.getData_block(examId,
							listClassIds, useHallTicket, objform.getIsPreviousExam(),type);
				} else {
					listCandidates = handler.getData_unblock(examId,
							listClassIds, useHallTicket, objform.getIsPreviousExam(),type);
				}
				objform.setExamName(name);
				Collections.sort(listCandidates);
				objform.setListCandidatesName(listCandidates);
				objform.setListCandidatesSize(listCandidates.size());
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examSettings.addsuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK_RESULT);
			} else {
				// Multi select values are not clearing the data thats y we have to get values from request scope and set into form
				// Below Line Written By Balaji
				objform.setClassValue(str);
	//			objform.setListExamName(handler.getExamNameList());
				//added by Smitha
				setExamNameToForm(objform);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
			}
		} else {

		//	objform.setListExamName(handler.getExamNameList());
			setExamNameToForm(objform);
			if (objform.getExamName() != null
					&& objform.getExamName().trim().length() > 0) {
				int examId = Integer.parseInt(objform.getExamName());

				objform.setMapClass(handler.getClassCodeByExamName(examId));
			}
			// Multi select values are not clearing the data thats y we have to get values from request scope and set into form
			// Below Line Written By Balaji
			objform.setClassValue(str);
			return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
		}

	}

	@SuppressWarnings("deprecation")
	public ActionForward blockUnBlockExamResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean block = true;
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		errors.clear();
		messages.clear();

		setUserId(request, objform);

		String stringOfClassId_StudentId = objform.getClassId_studentId();
		/*if (stringOfClassId_StudentId != null
				&& stringOfClassId_StudentId.length() == 0) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.blockUnblock.studentsId"));
		}
		saveErrors(request, errors);*/
		String type = request.getParameter("BlockUnBlock");
		if (type.contains("UnBlock")) {
			block = false;
		}
		validateStudentList(objform,errors,block);
		if (errors.isEmpty()) {
			String H_M_A = "M";
			if ("1".equals(objform.getTypeId())) {
				H_M_A = "H";
			} else if("3".equals(objform.getTypeId())){
				H_M_A = "A";
			}
			if (block) {
				ArrayList<ExamBlockUnblockHallTicketBO> listBO = helper.createBOList(objform.getExamId(), H_M_A,
						stringOfClassId_StudentId, objform.getUserId(), objform.getListCandidatesName());
				handler.block(listBO,objform);
				
				if (listBO == null
						|| listBO.size() == 0) {
					errors.add("error", new ActionError("knowledgepro.exam.blockUnblock.studentsId"));
					saveErrors(request, errors);
				//	objform.setListExamName(handler.getExamNameList());
					//added by smitha
					setExamNameToForm(objform);
					objform.clearPage();
					objform.resetFields();
					return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
				}

				
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.blockUnblock.block", "");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {

				ArrayList<Integer> deleteList =  handler.unblock(stringOfClassId_StudentId, objform.getListCandidatesName());
				if (deleteList == null
						|| deleteList.size() == 0) {
					errors.add("error", new ActionError("knowledgepro.exam.blockUnblock.studentsId"));
					saveErrors(request, errors);
				//	objform.setListExamName(handler.getExamNameList());
					//added by smitha
					setExamNameToForm(objform);
					objform.clearPage();
					objform.resetFields();
					return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
				}
				handler.unblockStudents(deleteList);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.blockUnblock.unblock", "");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		//	objform.setListExamName(handler.getExamNameList());
			//added by smitha
			setExamNameToForm(objform);
			objform.resetFields();
			objform.clearPage();
			return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
		} else {
			saveErrors(request, errors);
			if (block) {
				request.setAttribute("examBlockUnBlockOperation", "Block");
			} else {
				request.setAttribute("examBlockUnBlockOperation", "UnBlock");
			}
//			objform.setListExamName(handler.getExamNameList());
//			objform.setTypeId("");
//			objform.clearPage();
			return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK_RESULT);
		}

	}

	/**
	 * This is method has written by Balaji
	 */
	private void validateStudentList(ExamBlockUnblockForm objform,
			ActionErrors errors,boolean block) throws Exception {
		// Checking atleast one check box is selected are not
		List<Integer> list=new ArrayList<Integer>();
		List<ExamBlockUnBlockCandidatesTO> listCandidateNames=objform.getListCandidatesName();
		String reasonRegisterNo="";
		if(listCandidateNames!= null && listCandidateNames.size() > 0){
			Iterator<ExamBlockUnBlockCandidatesTO> listItr = listCandidateNames.iterator();
			int count=0;
			while (listItr.hasNext()) {
				ExamBlockUnBlockCandidatesTO examBlockUnBlockCandidatesTO = (ExamBlockUnBlockCandidatesTO) listItr
						.next();
				count++;
				if(examBlockUnBlockCandidatesTO.getIsSelected()){
					list.add(count);
					if((examBlockUnBlockCandidatesTO.getReason()==null || examBlockUnBlockCandidatesTO.getReason().isEmpty()) && block){
						if(reasonRegisterNo.isEmpty())
							reasonRegisterNo=examBlockUnBlockCandidatesTO.getRegNumber();
						else
							reasonRegisterNo=reasonRegisterNo+","+examBlockUnBlockCandidatesTO.getRegNumber();
					}
				}
			} 
		}else{
			errors.add("error", new ActionError("knowledgepro.exam.blockUnblock.studentsId"));
		}
		if(list.isEmpty()){
			errors.add("error", new ActionError("knowledgepro.exam.blockUnblock.studentsId"));
		}
		if(!reasonRegisterNo.isEmpty())
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.block.unblock.required.reason",reasonRegisterNo));
		
	}
	
	/**
	 * code written by balaji
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward editReason(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		
		setUserId(request, objform);
		String[] str = request.getParameterValues("classValue");
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			
			String name = handler.getExamName(objform.getExamName());
			objform.setExamId(objform.getExamName());

			if(str==null || str.length==0){
				errors.add("error",new ActionError("knowledgepro.exam.blockUnblock.classRequired"));
			}
			ArrayList<Integer> listClassIds = new ArrayList<Integer>();
			if (str != null && str.length != 0) {
				for (int x = 0; x < str.length; x++) {
					if (str[x] != null && str[x].trim().length() > 0) {
						listClassIds.add(Integer.parseInt(str[x]));
					} else {
						errors.add("error",new ActionError("knowledgepro.exam.blockUnblock.classRequired"));
						saveErrors(request, errors);
					}
				}
			}
			if (errors.isEmpty()) {
				List<ExamBlockUnBlockCandidatesTO> listCandidates;
				int examId = Integer.parseInt(objform.getExamName());
				boolean useHallTicket = true;
				char type='H';
				if ("2".equals(objform.getTypeId())) {
					useHallTicket = false;
					objform.setType("Marks Card");
					type='M';
				} 
				 else if("3".equals(objform.getTypeId())){
					 useHallTicket = false;
					objform.setType("Regular Application");
					type='A';
				}
				else {
					objform.setType("Hall Ticket");
				}
				//raghu
				listCandidates = handler.getListOfCandidates(examId,listClassIds, useHallTicket,type);
				objform.setExamName(name);
				Collections.sort(listCandidates);
				objform.setListCandidatesName(listCandidates);
				return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK_RESULT_EDIT);
			} else {
				// Multi select values are not clearing the data thats y we have to get values from request scope and set into form
				// Below Line Written By Balaji
				objform.setClassValue(str);
		//		objform.setListExamName(handler.getExamNameList());
				//added by smitha
				setExamNameToForm(objform);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
			}
		} else {

		//	objform.setListExamName(handler.getExamNameList());
			//added by smitha
			setExamNameToForm(objform);
			if (objform.getExamName() != null
					&& objform.getExamName().trim().length() > 0) {
				int examId = Integer.parseInt(objform.getExamName());

				objform.setMapClass(handler.getClassCodeByExamName(examId));
			}
			// Multi select values are not clearing the data thats y we have to get values from request scope and set into form
			// Below Line Written By Balaji
			objform.setClassValue(str);
			return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public ActionForward updateblockUnBlockRemarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean block = true;
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		try {
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		if (errors.isEmpty()) {
				handler.updateStudentsRemarks(objform.getListCandidatesName());
				ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess", "");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			//objform.setListExamName(handler.getExamNameList());
			//added by smitha
			setExamNameToForm(objform);
			objform.resetFields();
			objform.clearPage();
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
			return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
		} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		try {
			errors.clear();
			if(objform.getBlockType() != null && objform.getBlockType().equalsIgnoreCase("Unblock")){
				request.setAttribute("examBlockUnBlockOperation", "UnBlock");
			}else{
				request.setAttribute("examBlockUnBlockOperation", "Edit");
			}
			objform.setListCandidatesName(null);
			ArrayList<ExamBlockUnBlockCandidatesTO> candidatesList = handler.getListOfBlockedCandidates(objform);
			objform.setListCandidatesName(candidatesList);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		return mapping.findForward(EXAM_BLOCK_UNBLOCK_RESULT_NEW);
	} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateUnblockAndReason(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamBlockUnblockForm objform = (ExamBlockUnblockForm) form;
		try {
			messages.clear();
			setUserId(request, objform);
			handler.updateStudentsUnBlockAndReason(objform);
			ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess", "");
			messages.add("messages", message);
			saveMessages(request, messages);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objform.setErrorMessage(msg);
			objform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		return mapping.findForward(CMSConstants.EXAM_BLOCK_UNBLOCK);
	} 
	
}
