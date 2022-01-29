package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamRevaluationTypeBO;
import com.kp.cms.bo.exam.ExamSettingCourseBO;
import com.kp.cms.bo.exam.ExamSettingCourseRevaluationBO;
import com.kp.cms.forms.exam.ExamSettingCourseForm;
import com.kp.cms.handlers.exam.ExamSettingCourseHandler;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamRevaluationTO;
import com.kp.cms.to.exam.ExamSettingCourseTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamSettingCourseHelper {

	public List<ExamSettingCourseTO> convertBOtoTO(
			List<ExamSettingCourseBO> listBO) throws Exception {
		List<ExamSettingCourseTO> list = new ArrayList<ExamSettingCourseTO>();

		for (ExamSettingCourseBO scBO : listBO) {
			ExamSettingCourseTO scTO = new ExamSettingCourseTO();
			scTO.setId(scBO.getId());
			scTO.setCourseID(scBO.getCourse().getCourseID());
			scTO.setImprovement(scBO.getImprovement());
			scTO.setSupplementaryForFailedSubject(scBO
					.getSupplementaryForFailedSubject());
			scTO.setMinReqAttendanceWithFine(getString(scBO
					.getMinReqAttendanceWithFine()));
			scTO.setMinReqAttendanceWithoutFine(getString(scBO
					.getMinReqAttendanceWithoutFine()));
			if (scBO.getAggregatePass() != null) {
				if (scBO.getAggregatePass().intValue() > new BigDecimal("0")
						.intValue()) {
					scTO.setPassCriteria("Aggregate Pass "
							+ getString(scBO.getAggregatePass()));
				}
			}
			if (scBO.getIndividualPass() != null) {
				if (scBO.getIndividualPass().intValue() > new BigDecimal("0")
						.intValue()) {
					if (scTO.getPassCriteria() != null) {
						scTO.setPassCriteria("Individual Pass "
								+ getString(scBO.getIndividualPass())+", "
								+ scTO.getPassCriteria());
					}

					else {
						scTO.setPassCriteria("Individual Pass "
								+ getString(scBO.getIndividualPass()));
					}
				}
			}
			scTO.setProgramCourse(scBO.getCourse().getProgramCourse());
			scTO.setProgramType(scBO.getCourse().getProgram().getProgramType()
					.getProgramType());
			scTO.setRevaluationList(convertRevaluationList(scBO
					.getExamSettingCourseRevaluationSet().toArray()));

			list.add(scTO);
		}
		Collections.sort(list);
		return list;
	}

	private List<DisplayValueTO> convertRevaluationList(Object[] revaluationList) throws Exception {

		ExamSettingCourseHandler h = new ExamSettingCourseHandler();
		ArrayList<ExamRevaluationTO> revList = new ArrayList<ExamRevaluationTO>(
				h.getRevaluationTypeList());
		ArrayList<DisplayValueTO> l = new ArrayList<DisplayValueTO>();
		// list of rev ids from setting course rev table
		ArrayList<Integer> rtidList = new ArrayList<Integer>();

		// list of rev ids from revtype table
		ArrayList<Integer> idsList = new ArrayList<Integer>();
		for (int i = 0; i < revList.size(); i++) {
			ExamRevaluationTO rTO = revList.get(i);

			idsList.add(rTO.getId());

		}

		for (int i = 0; i < revaluationList.length; i++) {
			int id = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getExamRevaluationTypeBO().getId();
			String a = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getExamRevaluationTypeBO().getName();
			String b = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getRevaluation();
			if (idsList.contains(id)) {
				l.add(new DisplayValueTO(a, b));
				rtidList.add(id);
			}

		}
		if (l.size() < revList.size()) {
			for (int i = 0; i < revList.size(); i++) {
				ExamRevaluationTO rTO = revList.get(i);
				if (!(rtidList.contains(rTO.getId()))) {
					rtidList.add(rTO.getId());
					l.add(new DisplayValueTO(rTO.getRevaluationType(), "-"));
				}
			}
		}
		//Collections.sort(l);
		return l;
	}

	private List<ExamRevaluationTO> getRevaluationTOList(
			Object[] revaluationList) throws Exception {

		ArrayList<ExamRevaluationTO> l = new ArrayList<ExamRevaluationTO>();
		ExamSettingCourseHandler h = new ExamSettingCourseHandler();
		ArrayList<ExamRevaluationTO> revList = new ArrayList<ExamRevaluationTO>(
				h.getRevaluationTypeList());
		ArrayList<Integer> rtidList = new ArrayList<Integer>();
		for (int i = 0; i < revaluationList.length; i++) {
			int id = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getExamRevaluationTypeBO().getId();
			String a = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getExamRevaluationTypeBO().getName();
			String b = ((ExamSettingCourseRevaluationBO) revaluationList[i])
					.getRevaluation();
			rtidList.add(id);
			l.add(new ExamRevaluationTO(id, a, b));

		}
		// if (l.size() == 0) {
		// return revList;

		if (l.size() < revList.size()) {

			for (int i = 0; i < revList.size(); i++) {
				ExamRevaluationTO rTO = revList.get(i);
				if (!(rtidList.contains(rTO.getId()))) {
					rtidList.add(rTO.getId());
					l.add(new ExamRevaluationTO(rTO.getId(), rTO
							.getRevaluationType(), ""));
				}

			}

		}
		Collections.sort(l);
		return l;
	}

	private String getString(BigDecimal bd) {
		if (bd != null) {
			return bd.toString();
		} else
			return "";
	}

	public ExamSettingCourseForm createFormObject(ExamSettingCourseForm form,
			ExamSettingCourseBO eBO) throws Exception {
		form.setProgramTypeId(Integer.toString(eBO.getCourse().getProgram()
				.getProgramType().getId()));
		form.setCourseId(Integer.toString(eBO.getCourseId()));
		String[] str = { Integer.toString(eBO.getCourseId()) };
		form.setSelectedCourse(str);
		form.setRevaluationTypeList(getRevaluationTOList(eBO
				.getExamSettingCourseRevaluationSet().toArray()));
		form.setImprovement(eBO.getImprovement());
		form.setSupplementaryForFailedSubject(eBO
				.getSupplementaryForFailedSubject());

		form.setMinReqAttendanceWithoutFine(eBO
				.getMinReqAttendanceWithoutFine().toString());
		form.setMinReqAttendanceWithFine(eBO.getMinReqAttendanceWithFine()
				.toString());
		String aggregatePass = "";
		if (eBO.getAggregatePass() != null
				&& eBO.getAggregatePass().toString().length() > 0) {
			aggregatePass = eBO.getAggregatePass().toString();

		}
		form.setAggregatePass(aggregatePass);
		String individualPass = "";
		if (eBO.getIndividualPass() != null
				&& eBO.getIndividualPass().toString().length() > 0) {
			individualPass = eBO.getIndividualPass().toString();

		}
		form.setIndividualPass(individualPass);
		return form;
	}

	public List<KeyValueTO> convertBOToTO_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listBO) throws Exception {
		List<KeyValueTO> list = new ArrayList<KeyValueTO>();
		KeyValueTO kTO;
		for (ExamProgramTypeUtilBO scBO : listBO) {
			kTO = new KeyValueTO(scBO.getId(), scBO.getProgramType());
			list.add(kTO);
		}
		Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

	public List<ExamRevaluationTO> convertBOToTO_RevaluationType(
			ArrayList<ExamRevaluationTypeBO> listRO) throws Exception {
		List<ExamRevaluationTO> list = new ArrayList<ExamRevaluationTO>();
		ExamRevaluationTO rTO;
		for (ExamRevaluationTypeBO scBO : listRO) {
			rTO = new ExamRevaluationTO(scBO.getId(), scBO.getName(), "");
			list.add(rTO);
		}
		Collections.sort(list);
		return list;
	}
}
