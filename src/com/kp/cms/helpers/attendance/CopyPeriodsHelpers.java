package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.CopyPeriodsForm;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.transactionsimpl.attendance.CopyPeriodsTxnImpl;
import com.kp.cms.utilities.TeacherReportComparator;

public class CopyPeriodsHelpers {
	private static volatile CopyPeriodsHelpers copyPeriodsHelpers = null;

	public static CopyPeriodsHelpers getInstance() {
		if (copyPeriodsHelpers == null) {
			copyPeriodsHelpers = new CopyPeriodsHelpers();
			return copyPeriodsHelpers;
		}
		return copyPeriodsHelpers;
	}

	private CopyPeriodsHelpers() {

	}

	public List<ClassSchemewiseTO> convertToTOBo(
			List<ClassSchemewise> classSchemewisesList) throws Exception {

		List<ClassSchemewiseTO> list = new ArrayList<ClassSchemewiseTO>();
		if (classSchemewisesList != null) {
			Iterator<ClassSchemewise> iterator = classSchemewisesList
					.iterator();
			while (iterator.hasNext()) {
				ClassSchemewise classSchemewise = (ClassSchemewise) iterator
						.next();
				ClassSchemewiseTO classSchemewiseTO = new ClassSchemewiseTO();

				ClassesTO classesTO = new ClassesTO();
				classSchemewiseTO.setId(classSchemewise.getId());
				classSchemewiseTO.setClassId(classSchemewise.getClasses() .getId());
				classesTO.setId(classSchemewise.getClasses().getId());
				classesTO.setClassName(classSchemewise.getClasses().getName());
				classesTO.setSectionName(classSchemewise.getClasses() .getSectionName());
				classesTO.setTermNo(classSchemewise.getClasses() .getTermNumber());
				classesTO.setCourseId(classSchemewise .getCurriculumSchemeDuration().getCurriculumScheme() .getCourse().getId());
				classSchemewiseTO.setClassesTo(classesTO);
				Set<Period> set = classSchemewise.getPeriods();
				if (set != null && !set.isEmpty()) {
					List<PeriodTO> periodTOs = new ArrayList<PeriodTO>();
					Iterator<Period> iterator2 = set.iterator();
					while (iterator2.hasNext()) {
						Period period = (Period) iterator2.next();
						PeriodTO to = new PeriodTO();
						if (period.getId() != 0) {
							to.setId(period.getId());
						}
						if (period.getPeriodName() != null
								&& !period.getPeriodName().isEmpty()) {
							to.setPeriodName(period.getPeriodName());
						}
						if (period.getStartTime() != null
								&& !period.getStartTime().isEmpty()) {
							to.setStartTime(period.getStartTime());
						}
						if (period.getEndTime() != null
								&& !period.getEndTime().isEmpty()) {
							to.setEndTime(period.getEndTime());
						}
						to.setClassSchemewiseId(period.getClassSchemewise()
								.getId());
						if (period.getIsActive()) {
							to.setIsActive("True");
						} else {
							to.setIsActive("False");
						}
						periodTOs.add(to);
						TeacherReportComparator comparator=new TeacherReportComparator();
						comparator.setCompare(2);
						Collections.sort(periodTOs,comparator);
					}
					classSchemewiseTO.setPeriodTO(periodTOs);
				}
				list.add(classSchemewiseTO);
			}

		}
		return list;
	}

	/**
	 * @param map
	 * @param classSchemeWiseList
	 * @param toYear
	 * @param copyPeriodsForm
	 * @return
	 * @throws Exception
	 */
	public List<Period> convertToTOBo(Map<String, ClassSchemewise> map,
			List<ClassSchemewiseTO> classSchemeWiseList, int toYear,
			CopyPeriodsForm copyPeriodsForm) throws Exception {
		List<Period> periodList = new ArrayList<Period>();
		Period period = null;
		if (classSchemeWiseList != null) {
			Iterator<ClassSchemewiseTO> iterator = classSchemeWiseList .iterator();
			while (iterator.hasNext()) {
				ClassSchemewiseTO classSchemewiseTO = (ClassSchemewiseTO) iterator .next();
				String className = classSchemewiseTO.getClassesTo() .getClassName();
				if (map != null) {
					Iterator<Map.Entry<String, ClassSchemewise>> itr = map .entrySet().iterator();
					while (itr.hasNext()) {
						Map.Entry<java.lang.String, com.kp.cms.bo.admin.ClassSchemewise> entry = (Map.Entry<java.lang.String, com.kp.cms.bo.admin.ClassSchemewise>) itr
								.next();
						String className1 = entry.getKey();
						ClassSchemewise classSchemewise = entry.getValue();
						if (className.equalsIgnoreCase(className1)) {
							String query = "from Period p where p.classSchemewise.id="
									+ classSchemewise.getId() + "and p.classSchemewise.curriculumSchemeDuration.academicYear="
									+ classSchemewise .getCurriculumSchemeDuration() .getAcademicYear()
									+ "and p.classSchemewise.classes.termNumber="
									+ classSchemewise.getClasses() .getTermNumber()
									+ " and p.classSchemewise.classes.course.id="
									+ classSchemewise.getClasses().getCourse() .getId();
							if (classSchemewise.getClasses().getSectionName() != null
									&& !classSchemewise.getClasses() .getSectionName().isEmpty()) {
								query = query + "and p.classSchemewise.classes.sectionName='"
										+ classSchemewise.getClasses() .getSectionName() + "'";
							}
							boolean isDup;
							isDup = CopyPeriodsTxnImpl.getInstance() .checkDuplicate(query);
							if (!isDup) {
								ClassSchemewise clsSchemewise = new ClassSchemewise();
								clsSchemewise.setId(classSchemewise.getId());
								List<PeriodTO> list = classSchemewiseTO .getPeriodTO();
								if (list != null) {
									Iterator<PeriodTO> iterator2 = list .iterator();
									while (iterator2.hasNext()) {
										PeriodTO periodTO = (PeriodTO) iterator2 .next();
										period = new Period();
										if (periodTO.getPeriodName() != null
												&& !periodTO.getPeriodName() .isEmpty()) {
											period.setPeriodName(periodTO .getPeriodName());
										}
										if (periodTO.getStartTime() != null
												&& !periodTO.getStartTime() .isEmpty()) {
											period.setStartTime(periodTO .getStartTime());
										}
										if (periodTO.getEndTime() != null
												&& !periodTO.getEndTime() .isEmpty()) {
											period.setEndTime(periodTO .getEndTime());
										}
										period .setClassSchemewise(clsSchemewise);
										if (periodTO.getIsActive() .equalsIgnoreCase("True")) {
											period.setIsActive(true);
										} else {
											period.setIsActive(false);
										}
										period.setCreatedBy(copyPeriodsForm .getUserId());
										period.setCreatedDate(new Date());
										period.setModifiedBy(copyPeriodsForm .getUserId());
										period.setLastModifiedDate(new Date());
										periodList.add(period);
									}
								}
							}
						}
					}
				}
			}
		}

		return periodList;
	}
}
