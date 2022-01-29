package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.constants.CMSExamConstants;
import com.kp.cms.forms.exam.ExamStudentSpecializationForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamStudentSpecializationHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.exam.ExamStudentSpecializationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class ExamStudentSpecializationAction extends BaseDispatchAction {

	ExamStudentSpecializationHandler handler = new ExamStudentSpecializationHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	// To initialize drop down
	public ActionForward initExamStudentSpl(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentSpecializationForm objform = (ExamStudentSpecializationForm) form;
		objform.clearPage();
		errors.clear();
		messages.clear();
		if (objform.getAcademicYear() == null) {
			String str = CommonUtil.getTodayDate();
			str = str.substring(6, str.length());

			setUserId(request, objform);
			objform.setAcademicYear(str);
		} else
			objform.setAcademicYear(objform.getAcademicYear());
		
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		//objform.setListCourses(handler.init());
		objform.setListSpecialization(handler.getSpecializationList());

		return mapping
				.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
	}

	// On - SEARCH
	public ActionForward Search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamStudentSpecializationForm objform = (ExamStudentSpecializationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		setUserId(request, objform);
		if(objform.getSearchSpec()==null || objform.getSearchSpec().isEmpty()){
			errors.add("error",new ActionError("errors.required","Search Specialization"));
		}
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			searchMain(objform);
			Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationByCourse(Integer.parseInt(objform.getCourse()));
			request.setAttribute("specializationMap", specializationMap);
			
			if (objform.getListStudentSpec() != null
					&& objform.getListStudentSpec().size() == 0) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				// objform.setAcademicYear(objform.getAcademicYear());
				objform.clearPage();

				return mapping
						.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
			} else {
				objform.setSearchSpec("");
				request.setAttribute("Operation", "search");

				return mapping.findForward("examStudentSpecSearch");
			}
		} else {
			if (objform.getCourse().isEmpty()) {
				objform.setSchemeNameList(null);
			} else {
				objform = retainVals(objform,request);
				request.setAttribute("reatain", "yes");
				request.setAttribute("Operation", "search");
			}
		}
		return mapping.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
	}

	// On - ASSIGN
	public ActionForward Assign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamStudentSpecializationForm objform = (ExamStudentSpecializationForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);

		if (errors.isEmpty()) {
			assignMain(objform);
			if (objform.getListStudentSpec().size() == 0) {
				ActionMessage message = new ActionMessage("knowledgepro.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				objform.clearPage();
				return mapping.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
			}
			Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationByCourse(Integer.parseInt(objform.getCourse()));
			request.setAttribute("specializationMap", specializationMap);
			
			return mapping.findForward("examStudentSpecSearch");
		}
		objform = retainVals(objform,request);
		request.setAttribute("reatain", "yes");
		request.setAttribute("Operation", "search");
		
		return mapping.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
	}

	// On - SEACH (APPLY for Update)
	public ActionForward Apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamStudentSpecializationForm objform = (ExamStudentSpecializationForm) form;
		errors.clear();
		messages.clear();

		setUserId(request, objform);
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);

		if (errors.isEmpty()) {
			int specId = Integer.parseInt(objform.getSearchSpec());

			ArrayList<Integer> listOfIds = new ArrayList<Integer>();
			ArrayList<ExamStudentSpecializationTO> finalList=new ArrayList<ExamStudentSpecializationTO>();
			ArrayList<ExamStudentSpecializationTO> tolist = objform
					.getListStudentSpec();
			boolean flag = true;
			for (ExamStudentSpecializationTO to : tolist) {
				if ((to.getDummyOnOrOff() != null)
						&& (to.getDummyOnOrOff().equalsIgnoreCase("on"))) {
					flag = false;
					listOfIds.add(to.getId());
					finalList.add(to);
				}

			}
			if (flag == true) {
				for (ExamStudentSpecializationTO to : tolist) {
					if (to.getDummyOnOrOff() != null) {
						to.setDummyOnOrOff(null);
						to.setIsCheckedDummy(true);
					} else {
						to.setIsCheckedDummy(false);
					}

				}

				errors.add("error",new ActionError("knowledgepro.exam.ExamSpecializationSubjectgroup.isChecked"));
				saveErrors(request, errors);

				//objform.setListSpecialization(handler.getSpecializationList());
				Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance()
								.getSpecializationByCourse(Integer.parseInt(objform.getCourseId()));
				request.setAttribute("specializationMap", specializationMap);
				request.setAttribute("Operation", "search");
				return mapping.findForward("examStudentSpecSearch");

			}

//			handler.updateStudentSpecialization(specId, objform.getUserId(),
//					listOfIds);
			// Code has been modified by Balaji
			handler.updateStudentSpecialization1(specId, objform.getUserId(),finalList);
			ActionMessage message = new ActionMessage("knowledgepro.exam.specialization.updated");
			messages.add("messages", message);
			saveMessages(request, messages);

			List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
			if(courseList!=null && !courseList.isEmpty()){
				objform.setListCourses(courseList);
			}			
			
		//	objform.setListCourses(handler.init());
			Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationByCourse(Integer.parseInt(objform.getCourseId()));
			request.setAttribute("specializationMap", specializationMap);
			objform.clearPage();
			errors.clear();
			return mapping.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
		}
		retailAllCheckValues(objform, request);
		//objform.setListSpecialization(handler.getSpecializationList());
		Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance()
		.getSpecializationByCourse(Integer.parseInt(objform.getCourseId()));
		request.setAttribute("specializationMap", specializationMap);
		objform = retainVals(objform,request);
		request.setAttribute("Operation", "search");
		return mapping.findForward("examStudentSpecSearch");
	}

	private ExamStudentSpecializationForm retailAllCheckValues(
			ExamStudentSpecializationForm objform, HttpServletRequest request) {
		ArrayList<ExamStudentSpecializationTO> listOfIds = objform
				.getListStudentSpec();
		for (ExamStudentSpecializationTO to : listOfIds) {
			if (to.getDummyOnOrOff() != null) {
				to.setDummyOnOrOff(null);
				to.setIsCheckedDummy(true);
			} else {
				to.setIsCheckedDummy(false);
			}
		}
		return objform;
	}

	// On - ASSIGN (APPLY for Add)
	public ActionForward updateUnAssignedStudents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamStudentSpecializationForm objform = (ExamStudentSpecializationForm) form;
		errors.clear();
		messages.clear();
		setUserId(request, objform);
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		if (errors.isEmpty()) {
			int specId = Integer.parseInt(objform.getSearchSpec());
			ArrayList<Integer> listOfIds = new ArrayList<Integer>();
			ArrayList<ExamStudentSpecializationTO> tolist = objform
					.getListStudentSpec();
			boolean flag = true;
			for (ExamStudentSpecializationTO to : tolist) {
				if ((to.getDummyOnOrOff() != null)
						&& (to.getDummyOnOrOff().equalsIgnoreCase("on"))) {
					flag = false;
					listOfIds.add(to.getStudentId());
				}
			}
			if (flag == true) {
				for (ExamStudentSpecializationTO to : tolist) {
					if (to.getDummyOnOrOff() != null) {
						to.setDummyOnOrOff(null);
						to.setIsCheckedDummy(true);
					} else {
						to.setIsCheckedDummy(false);
					}
				}
				errors.add("error",new ActionError("knowledgepro.exam.ExamSpecializationSubjectgroup.isChecked"));
				saveErrors(request, errors);
				objform.setListSpecialization(handler.getSpecializationList());
				return mapping.findForward("examStudentSpecSearch");
			}
			String schemeNo = objform.getSchemeNo();
			String sectionName = objform.getSection();
			handler.add(specId, objform.getUserId(), listOfIds, Integer
					.parseInt(objform.getCourse()), schemeNo, sectionName);
			ActionMessage message = new ActionMessage("knowledgepro.exam.studentSpl.addedsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			objform.clearPage();
			List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
			if(courseList!=null && !courseList.isEmpty()){
				objform.setListCourses(courseList);
			}
			
			//objform.setListCourses(handler.init());
			objform.setListSpecialization(handler.getSpecializationList());
			return mapping.findForward(CMSExamConstants.EXAM_STUDENT_SPECIALIZATION);
		}
		retailAllCheckValues(objform, request);
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		//objform.setListCourses(handler.init());
		objform.setListSpecialization(handler.getSpecializationList());
		return mapping.findForward("examStudentSpecSearch");
	}

	private void assignMain(ExamStudentSpecializationForm objform) throws Exception {
		int sectionId = 0;
		objform.setListSpecialization(handler.getSpecializationList());

		if (!objform.getSectionId().isEmpty()
				|| objform.getSectionId().length() != 0) {
			sectionId = Integer.parseInt(objform.getSectionId());
		}
		int schemeId = Integer.parseInt(objform.getScheme().split("_")[0]);
		int schemeNo = Integer.parseInt(objform.getScheme().split("_")[1]);

		objform.setSection(handler.getSectionByClass(sectionId));
		objform.setSectionId(Integer.toString(sectionId));
		objform.setSchemeNo(Integer.toString(schemeNo));
//		objform.setListStudentSpec(handler.getUnAssignedStudent(Integer
//				.parseInt(objform.getAcademicYear()), Integer.parseInt(objform
//				.getCourse()), schemeNo, sectionId, schemeId));
		
		//Code Written By Balaji
		objform.setListStudentSpec(handler.getUnAssignedStudent1(Integer
				.parseInt(objform.getAcademicYear()), Integer.parseInt(objform
				.getCourse()), schemeNo, sectionId, schemeId));
		
		objform.setAcademicYear(handler.getacademicYear(Integer
				.parseInt(objform.getAcademicYear())));
		objform.setYear(objform.getAcademicYear());
		objform.setCourseName(handler.getCourseName(Integer.parseInt(objform
				.getCourse())));
		objform.setCourse(objform.getCourse());
		objform.setSchemeName(handler.getSchemeName(schemeNo));
	}

	private void searchMain(ExamStudentSpecializationForm objform) throws Exception {
		int specId = 0;
		int courseId = 0;

		int schemeNo = 0;
		int sectionId = 0;

		if (objform.getSearchSpec() != null
				&& objform.getSearchSpec().trim().length() != 0) {
			specId = Integer.parseInt(objform.getSearchSpec());

		}

		if (objform.getSectionId() != null
				&& objform.getSectionId().trim().length() != 0) {
			sectionId = Integer.parseInt(objform.getSectionId());
		}

		courseId = Integer.parseInt(objform.getCourse());
		schemeNo = Integer.parseInt(objform.getScheme().split("_")[1]);
		objform.setSchemeNo(Integer.toString(schemeNo));

		objform.setCourseId(Integer.toString(courseId));
		objform.setSpecId(Integer.toString(specId));

		objform.setListStudentSpec(handler.getSpecializationStudent(Integer
				.toString(schemeNo), courseId, sectionId, specId, Integer.parseInt(objform.getAcademicYear())));
		objform.setSectionId(Integer.toString(sectionId));
		objform.setSection(handler.getSectionByClass(sectionId));

		objform.setSchemeNo(Integer.toString(schemeNo));

		// objform.setAcademicYear_value(objform.getAcademicYear());
		objform.setAcademicYear(handler.getacademicYear(Integer
				.parseInt(objform.getAcademicYear())));
		// objform.setAcademicYear(objform.getAcademicYear());

		objform.setCourseName(handler.getCourseName(Integer.parseInt(objform
				.getCourse())));
		objform.setSchemeName(handler.getSchemeName(schemeNo));
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		//objform.setListCourses(handler.init());
		objform.setListSpecialization(handler.getSpecializationList());

	}

	private ExamStudentSpecializationForm retainVals(ExamStudentSpecializationForm objform,HttpServletRequest request) throws Exception {

		objform.setAcademicYear(objform.getAcademicYear());
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		//objform.setListCourses(handler.init());
		if(objform.getCourse()!=null && !objform.getCourse().isEmpty()){
		Map<Integer, String> specializationMap = CommonAjaxHandler.getInstance().getSpecializationByCourse(Integer.parseInt(objform.getCourse()));
		request.setAttribute("specializationMap", specializationMap);
		}
//		objform.setListSpecialization(handler.getSpecializationList());
		objform = handler.retainAllValues(objform);

		return objform;
	}

}
