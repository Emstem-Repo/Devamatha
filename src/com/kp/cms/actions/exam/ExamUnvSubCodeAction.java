package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.constants.CMSExamConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamUNVSubCodeForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamUnvSubCodeHandler;
import com.kp.cms.helpers.exam.ExamUnvSubCodeHelper;
import com.kp.cms.to.exam.ExamUnvSubCodeTO;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamUnvSubCodeAction extends BaseDispatchAction {

	private static final Logger log = Logger
			.getLogger(ExamUnvSubCodeAction.class);
	ExamUnvSubCodeHandler handler = new ExamUnvSubCodeHandler();
	ExamUnvSubCodeHelper helper = new ExamUnvSubCodeHelper();
	CourseTransactionImpl implCourse= new CourseTransactionImpl();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamUnvSubCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("call of initExamUnvSubCode method in ExamUnvSubCodeAction");
		ExamUNVSubCodeForm objForm = (ExamUNVSubCodeForm) form;
		objForm.setCourse(null);
		if (objForm.getAcademicYear() == null) {
			String str = CommonUtil.getTodayDate();
			str = str.substring(6, str.length());
			Integer year = Integer.parseInt(str);
			objForm.setAcademicYear(Integer.toString(year));
		}
		request.setAttribute("values", "yes");
		//objForm.setCourseMap(handler.getCourseListHashMap());
		 try {
			 HashMap<Integer,String> courseMap=implCourse.getCourseMap();
		    if(courseMap!=null){
		    	objForm.setCourseMap(courseMap);
		 }
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.EXAM_UNV_SUB_CODE_1);

	}

	public ActionForward setExamUnvSubCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUNVSubCodeForm objForm = (ExamUNVSubCodeForm) form;
		log.info("call of setExamUnvSubCode method in ExamUnvSubCodeAction");

		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			try {
				String year = objForm.getAcademicYear();
				String courseId = objForm.getCourse();
				String schmeId = objForm.getScheme();

				ArrayList<ExamUnvSubCodeTO> list = handler.getData_OnSearch(
						Integer.parseInt(year), Integer.parseInt(courseId),
						Integer.parseInt(schmeId),"none");
				// ArrayList<ExamUnvSubCodeTO> list = handler
				// .getData_OnSearch(objForm);
				if (list.size() == 0) {
					ActionMessage message = new ActionMessage(
							"knowledgepro.norecords");
					messages.add("messages", message);
					saveMessages(request, messages);
					objForm.setScheme(null);
					// return mapping
					// .findForward(CMSConstants.EXAM_UNV_SUB_CODE_1);
					return initExamUnvSubCode(mapping, objForm, request,
							response);
				}

				objForm.setUnvSubCodeList(list);
				objForm.setAcademicYear_value(year);
				objForm.setCourse(courseId);
				objForm.setScheme(schmeId);
				objForm.setUnvSubCodeListSize(Integer.toString(list.size()));
				log
						.info("end of setExamUnvSubCode method in ExamUnvSubCodeAction");
				request.setAttribute("UNVSubCodeOperation", "edit");
				return mapping
						.findForward(CMSConstants.EXAM_UNV_SUB_CODE_DETAILS);

			} catch (BusinessException e) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			if (objForm.getCourse() != null && objForm.getCourse().length() > 0) {
				request.setAttribute("values", "yes");
				objForm.setCourseMap(handler.getCourseListHashMap());
				objForm.setSchemeNameList(handler.getSchemeNoByCourse(objForm
						.getCourse()));
			} else {
				request.setAttribute("values", "yes");
				objForm.setCourseMap(handler.getCourseListHashMap());
				objForm.setSchemeNameList(null);
			}
		}

		return mapping.findForward(CMSConstants.EXAM_UNV_SUB_CODE_1);

	}

	@SuppressWarnings("deprecation")
	public ActionForward updateExamUnvSubCodeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamUNVSubCodeForm objForm = (ExamUNVSubCodeForm) form;
		log
				.info("call of setExamUnvSubCodeDetails method in ExamUnvSubCodeAction");
		errors.clear();
		messages.clear();

		String[] subjectUnvCode = objForm.getUniversitySubjectValue()
				.split(",");

		boolean firstRun = true;
		boolean splChar = false;
				
		for (int i = 0; i < subjectUnvCode.length; i++) {
						
			String[] id_code = subjectUnvCode[i].split("_");
			if (id_code.length > 1) {
				splChar = splCharValidation(id_code[1],
						"\\/\\-\\&\\s");
				if (splChar && firstRun) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.uSubjectCode.special"));
					saveErrors(request, errors);
					firstRun = false;
				}
			}
		}

		
		if (errors.isEmpty()) {
			try {

				boolean isError=handler.update(objForm.getUniversitySubjectValue());
				if(isError)
				{
						errors.add("error", new ActionError(
						CMSExamConstants.EXAM_UNV_SUB_CODE_REQUIRED));
				        saveErrors(request, errors); 
				        objForm=setValues(objForm,"error");
				        return mapping.findForward(CMSConstants.EXAM_UNV_SUB_CODE_DETAILS);
				        
				}
				else
				{
				 ActionMessage message = new ActionMessage(
						"knowledgepro.exam.assignStudentsToRoom.addeduccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				}
			} catch (BusinessException e) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log
					.info("end of setExamUnvSubCodeDetails method in ExamUnvSubCodeAction");

			
			return initExamUnvSubCode(mapping, objForm, request, response);
		} else {
			objForm=setValues(objForm,"none");
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_UNV_SUB_CODE_DETAILS);

		}
	}

	static boolean splCharValidation(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}

	private ExamUNVSubCodeForm retainValues(ExamUNVSubCodeForm objForm)
			throws Exception {
		String year = objForm.getAcademicYear_value();
		String courseId = objForm.getCourse();
		String schmeId = objForm.getScheme();
		ArrayList<ExamUnvSubCodeTO> list = handler.getData_OnSearch(Integer
				.parseInt(year), Integer.parseInt(courseId), Integer
				.parseInt(schmeId),"none");
		objForm.setUnvSubCodeList(list);
		objForm.setAcademicYear_value(year);
		objForm.setCourse(courseId);
		objForm.setScheme(schmeId);

		objForm.setAcademicYear(year);
		objForm
				.setCourseName(handler
						.getCourseName(Integer.parseInt(courseId)));
	
		objForm.setSchemeName(objForm.getScheme());
		objForm.setUnvSubCodeListSize(Integer.toString(list.size()));

		return objForm;

	}
	private ExamUNVSubCodeForm setValues(ExamUNVSubCodeForm objForm,String mode) throws Exception
	{
		String year = (objForm.getAcademicYear_value()!=null && objForm.getAcademicYear_value().trim().length()>0?objForm.getAcademicYear_value():"0");
		String courseId = (objForm.getCourse()!=null && objForm.getCourse().trim().length()>0?objForm.getCourse():"0");
		String schmeId = (objForm.getScheme()!=null && objForm.getScheme().trim().length()>0?objForm.getScheme():"0");
		ArrayList<ExamUnvSubCodeTO> list = handler.getData_OnSearch(Integer
				.parseInt(year), Integer.parseInt(courseId), Integer
				.parseInt(schmeId),mode);
		
		
		objForm.setUnvSubCodeList(list);
		objForm.setAcademicYear_value(year);
		objForm.setCourse(courseId);
		objForm.setScheme(schmeId);

		objForm.setAcademicYear(year);
		objForm.setCourseName(handler.getCourseName(Integer
				.parseInt(courseId)));
		objForm.setSchemeName(objForm.getScheme());
		objForm.setUnvSubCodeListSize(Integer.toString(list.size()));
		
		return objForm;
		
	}

}
