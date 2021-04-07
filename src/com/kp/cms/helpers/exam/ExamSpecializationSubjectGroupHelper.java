package com.kp.cms.helpers.exam;

/**
 * Dec 26, 2009
 * Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamSpecializationSubjectGroupBO;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.forms.exam.ExamSpecializationSubjectGroupForm;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamSpecializationSubjectGroupTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamSpecializationSubjectGroupHelper {

	public HashMap<Integer, String> convertBOToTO_SubGrp(
			List<SubjectGroupUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (SubjectGroupUtilBO bo : listBO) {
			map.put(bo.getId(), bo.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public ExamSpecializationSubjectGroupForm createFormObjcet(
			ExamSpecializationSubjectGroupForm form,
			ExamSpecializationSubjectGroupBO objBO) {
		objBO.setId(objBO.getId());
		objBO.setAcademicYear(objBO.getAcademicYear());

		return form;
	}

	public ArrayList<ExamSpecializationSubjectGroupBO> convertTOToBO() {
		ArrayList<ExamSpecializationSubjectGroupBO> list = new ArrayList<ExamSpecializationSubjectGroupBO>();

		return list;
	}

	public ArrayList<KeyValueTO> convertBOToTO_Course(
			ArrayList<ExamCourseUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamCourseUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getCourseID(), bo.getCourseName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;

	}

	public HashMap<Integer, String> convertBOToTO_Scheme(
			List<CurriculumSchemeUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (CurriculumSchemeUtilBO bo : listBO) {
			map.put(bo.getCourseSchemeId(), bo.getCourseSchemeUtilBO()
					.getName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public List<ExamSpecializationSubjectGroupTO> convertBOtoTO(
			ArrayList<ExamSpecializationBO> listExamSpecializationBO,
			ArrayList<KeyValueTO> subjectGroupList) {
		ArrayList<ExamSpecializationSubjectGroupTO> listSTO = new ArrayList<ExamSpecializationSubjectGroupTO>();
		ExamSpecializationSubjectGroupTO sTO = null;
		for (ExamSpecializationBO sBO : listExamSpecializationBO) {
			sTO = new ExamSpecializationSubjectGroupTO();
			sTO.setId(sBO.getId());
			sTO.setSpecialization(sBO.getName());
			if(listExamSpecializationBO.size()>0){
				sTO.setListSubjects(getConcatedList(sBO.getId(), subjectGroupList));	
			}
			
			listSTO.add(sTO);
		}
		Collections.sort(listSTO);
		return listSTO;

	}

	private List<DisplayValueTO> getConcatedList(int id,
			ArrayList<KeyValueTO> subjectGroupList) {
		ArrayList<DisplayValueTO> returnSubjectGroupList = new ArrayList<DisplayValueTO>();
		for (KeyValueTO k : subjectGroupList) {
			returnSubjectGroupList.add(new DisplayValueTO(k.getDisplay(), id
					+ "-" + k.getId()));
		}
		Collections.sort(returnSubjectGroupList);
		return returnSubjectGroupList;
	}

	public String[] convertBOtoArraylist(List specializationSubject) {
		Iterator itr = specializationSubject.iterator();
		
		int i = 0;
		String[] listvalues = new String[specializationSubject.size()];
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null && row[0].toString().trim().length() > 0) {
				String id = row[0].toString().trim().concat("-").concat(
						row[1].toString().trim());
				listvalues[i] = id;
				i = i + 1;
				// list.add(id);
			}
		}
		return listvalues;
	}

	public ArrayList<KeyValueTO> convertBOtoTO(List subjectGroupList) {
		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
		Iterator itr = subjectGroupList.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			list.add(new KeyValueTO(Integer.parseInt(row[0].toString()), row[1]
					.toString()));
		}
		Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

}
