package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.constants.CMSExamConstants;
import com.kp.cms.forms.exam.ExamSpecializationSubjectGroupForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.exam.ExamSpecializationSubjectGroupHandler;
import com.kp.cms.to.exam.ExamSpecializationSubjectGroupTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("deprecation")
public class ExamSpecializationSubjectGroupAction extends BaseDispatchAction {
	ExamSpecializationSubjectGroupHandler handler = new ExamSpecializationSubjectGroupHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();

	public ActionForward initExamSplSubGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSpecializationSubjectGroupForm objform = (ExamSpecializationSubjectGroupForm) form;
		//objform.setListCourses(handler.init());
		objform.clearPage();
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		return mapping.findForward(CMSConstants.EXAM_SPL_SUBJ_GROUP);

	}

	public ActionForward setExamSplSubGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSpecializationSubjectGroupForm objform = (ExamSpecializationSubjectGroupForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		
		if (errors.isEmpty()) {
			String academicYear = (objform.getAcademicYear()!=null && objform.getAcademicYear().trim().length()>0?objform.getAcademicYear():"");
			String courseId = (objform.getCourseId()!=null && objform.getCourseId().trim().length()>0?objform.getCourseId():"");
			String schemeId = (objform.getSchemeId()!=null && objform.getSchemeId().trim().length()>0?objform.getSchemeId():"");
			// List<ExamSpecializationSubjectGroupTO> list1 = ;
			// String[] list2 = ;
			// objform = handler.setToList(objform, request, list1, list2);
			List<ExamSpecializationSubjectGroupTO> listOfSpecialisation=handler.getSpecializationData(
					(academicYear.isEmpty()?0:Integer.parseInt(academicYear)),(courseId.isEmpty()?0:Integer.parseInt(courseId)),
					(schemeId.isEmpty()?0:Integer.parseInt(schemeId)));
			if(listOfSpecialisation.size()==0)
			{
				ActionMessage message=new ActionMessage("knowledgepro.interview.norecords");
				messages.add("messages", message);
				saveMessages(request, messages);
				Map<Integer, String> schemeNOMap=handler.getSchemeNoByCourse(courseId);
				objform.setSchemeNOMap(schemeNOMap);
				objform.setCourseId(courseId);
				objform.setAcademicYear(academicYear);
				objform.setSchemeId(schemeId);
				List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
				if(courseList!=null && !courseList.isEmpty()){
					objform.setListCourses(courseList);
				}
				//objform.setListCourses(handler.init());
				return mapping.findForward(CMSConstants.EXAM_SPL_SUBJ_GROUP);
				
			}
			else
			{
			objform.setListSpecializations(listOfSpecialisation);
			objform.setSubjectIds(handler.getSpecializationSubject((academicYear.isEmpty()?0:Integer.parseInt(academicYear)), (courseId==""?0:Integer.parseInt(courseId)),
					(schemeId.isEmpty()?0:Integer.parseInt(schemeId))));

			objform.setCourseId(courseId);
			objform.setAcademicYear_value(academicYear);
			objform.setSchemeId(schemeId);
			objform.setCourseName(handler.getCourseName((courseId.isEmpty()?0:Integer.parseInt(courseId))));
			objform.setAcademicYear(handler.getacademicYear((academicYear.isEmpty()?0:Integer.parseInt(academicYear))));
			//String schemeName = handler.getSchemeName((schemeId==""?0:Integer.parseInt(schemeId)));
			String schemeName = handler.getSchemeName(courseId.isEmpty()?0:Integer.parseInt(courseId), schemeId==""?0:Integer.parseInt(schemeId),
					academicYear.isEmpty()?0:Integer.parseInt(academicYear));
			/*String name = "";	
			if (schemeName != null && schemeName.length() > 0) {
				name = name.concat("-").concat(schemeName);
			}*/
			//objform.setSchemeName(schemeId.concat(name));
			objform.setSchemeName(schemeId.concat(schemeName));
			return mapping.findForward(CMSConstants.EXAM_SPL_SUBJ_GROUP_ADD);
			}
		}
		List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
		if(courseList!=null && !courseList.isEmpty()){
			objform.setListCourses(courseList);
		}
		//objform.setListCourses(handler.init());
		objform.clearPage();
		return mapping.findForward(CMSConstants.EXAM_SPL_SUBJ_GROUP);
	}

	public ActionForward setExamSplSubGroupAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamSpecializationSubjectGroupForm objform = (ExamSpecializationSubjectGroupForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		String academicYear = objform.getAcademicYear_value();
		int courseid = (objform.getCourseId()!=null && objform.getCourseId().trim().length()>0?Integer.parseInt(objform.getCourseId()):0);
		int schemeId =(objform.getSchemeId()!=null && objform.getSchemeId().trim().length()>0?Integer.parseInt(objform.getSchemeId()):0); 

		if (errors.isEmpty()) {
			String[] subjectIds = request.getParameterValues("subjectIds");

			ArrayList<String> listSubjects = new ArrayList<String>();
			if (subjectIds != null && subjectIds.length != 0) {
				for (int x = 0; x < subjectIds.length; x++) {
					listSubjects.add(subjectIds[x]);
				}
			}

			handler.add(Integer.parseInt(academicYear), courseid, schemeId,
					listSubjects, objform.getUserId());
			ActionMessage message=new ActionMessage(CMSExamConstants.EXAM_SPL_SUBJ_GROUP_ADDED_SUCCESS,CMSExamConstants.EXAM_SPL_SUBJ_GROUP_MESSAGE);
			messages.add("messages", message);
			saveMessages(request, messages);
			objform.clearPage();
			List<KeyValueTO> courseList=CourseHandler.getInstance().getCoursesKey();
			if(courseList!=null && !courseList.isEmpty()){
				objform.setListCourses(courseList);
			}
		//	objform.setListCourses(handler.init());
			return mapping.findForward(CMSExamConstants.EXAM_SPL_SUBJ_GROUP);
		} else {
			
			List<ExamSpecializationSubjectGroupTO> list1 = handler
					.getSpecializationData(Integer.parseInt(academicYear),
							courseid, schemeId);
			String[] list2 = handler.getSpecializationSubject(Integer
					.parseInt(academicYear),courseid,
					schemeId);
			objform = handler.setToList(objform, request, list1, list2);

			objform.setListSpecializations(list1);
			objform.setSubjectIds(list2);
			objform.setCourseId(Integer.toString(courseid));
			objform.setAcademicYear_value(academicYear);
			objform.setSchemeId(Integer.toString(schemeId));
			objform.setCourseName(handler.getCourseName(Integer
					.parseInt(Integer.toString(courseid))));
			objform.setAcademicYear(handler.getacademicYear(Integer
					.parseInt(academicYear)));
			String schemeName = handler.getSchemeName((schemeId));
			String name = "";
			if (schemeName != null && schemeName.length() > 0) {
				name = name.concat("-").concat(schemeName);
			}
			objform.setSchemeName(Integer.toString(schemeId).concat(name));
			return mapping.findForward(CMSConstants.EXAM_SPL_SUBJ_GROUP_ADD);
		}
		
		
	}
}
