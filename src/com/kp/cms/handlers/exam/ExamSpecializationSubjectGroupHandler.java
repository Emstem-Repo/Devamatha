package com.kp.cms.handlers.exam;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.exam.ExamSpecializationSubjectGroupBO;
import com.kp.cms.forms.exam.ExamSpecializationSubjectGroupForm;
import com.kp.cms.helpers.exam.ExamSpecializationSubjectGroupHelper;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamSpecializationSubjectGroupTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamSpecializationSubjectGroupImpl;

@SuppressWarnings("unchecked")
public class ExamSpecializationSubjectGroupHandler extends ExamGenHandler {

	ExamSpecializationSubjectGroupImpl impl = new ExamSpecializationSubjectGroupImpl();

	ExamSpecializationSubjectGroupHelper helper = new ExamSpecializationSubjectGroupHelper();

	public void add(int academicYear, int courseId, int schemeId,
			ArrayList<String> listSpecializationSubjects, String userId)
			throws Exception {

		// Every add here first deletes previous entry and adds newly selected
		// entry
		ArrayList<Integer> listSplId = new ArrayList<Integer>();
		ArrayList<ExamSpecializationSubjectGroupBO> listOfBo = new ArrayList<ExamSpecializationSubjectGroupBO>();
		ExamSpecializationSubjectGroupBO objBO = null;
		for (String splSubject : listSpecializationSubjects) {
			String[] mainSplSub = splSubject.split("-");
			if (mainSplSub[0] != null && mainSplSub[0].length() > 0) {

				if (!listSplId.contains(Integer.parseInt(mainSplSub[0]))) {
					impl.delete_Specialization(academicYear, courseId,
							schemeId, Integer.parseInt(mainSplSub[0]));
					listSplId.add(Integer.parseInt(mainSplSub[0]));
				}
			}
			objBO = new ExamSpecializationSubjectGroupBO(academicYear,
					courseId, schemeId, Integer.parseInt(mainSplSub[0]),
					Integer.parseInt(mainSplSub[1]), userId);
			listOfBo.add(objBO);
		}
		impl.insert_List(listOfBo);
	}

	public List<ExamSpecializationSubjectGroupTO> getSpecializationData(
			int academicYear, int courseId, int schemeId) {
		
		List<Object[]> listOfSubjectGroup=impl.getSubjectGroupList(academicYear,courseId,schemeId);
		if(listOfSubjectGroup.size()!=0)
			
		{
		
	    	return helper.convertBOtoTO(new ArrayList(impl
				.select_ExamSpec(courseId)), helper.convertBOtoTO(listOfSubjectGroup));
		}
		else
		{
			return new ArrayList<ExamSpecializationSubjectGroupTO>();
		}
	}

	public String getacademicYear(int academicYear) {
		return Integer.toString(academicYear).concat("-").concat(
				Integer.toString(academicYear + 1));
	}

	public List<KeyValueTO> init() {
		return getCourseListNotAppln();

	}

	public String[] getSpecializationSubject(int academicYear, int courseid,
			int schemeId) {
		return helper.convertBOtoArraylist(impl.getSpecializationSubject(
				academicYear, courseid, schemeId));
	}

	public ExamSpecializationSubjectGroupForm setToList(
			ExamSpecializationSubjectGroupForm objform,
			HttpServletRequest request,
			List<ExamSpecializationSubjectGroupTO> list1spl, String[] list2) {
		ArrayList<ExamSpecializationSubjectGroupTO> a = new ArrayList<ExamSpecializationSubjectGroupTO>();
		for (ExamSpecializationSubjectGroupTO to : list1spl) {
			to.setSubjectIds(list2);
			a.add(to);
		}
		objform.setListSpecializations(a);
		return objform;
	}
	public String getSchemeName(int courseId, int schemeNo, int year) throws Exception {
		return impl.getSchemeName(courseId, schemeNo, year);
	}

}
