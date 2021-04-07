package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.forms.timetable.TimeAllocationForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.TimeAllocationTo;

public class TimeAllocationHelper {
	public static TimeAllocationHelper manageWorkingDays = null;
	private static Map<Integer, String> dayMap = null;
	static {
		dayMap = new HashMap<Integer, String>();
		dayMap.put(1, "Monday");
		dayMap.put(2, "Tuesday");
		dayMap.put(3, "Wednesday");
		dayMap.put(4, "Thursday");
		dayMap.put(5, "Friday");
		dayMap.put(6, "Saturday");
		dayMap.put(7, "Sunday");
	}

	public static TimeAllocationHelper getInstance() {
		if (manageWorkingDays == null) {
			return new TimeAllocationHelper();
		}
		return manageWorkingDays;
	}

	private TimeAllocationHelper() {
	}

	public TimeAllocationTo convertBoListtoToList(
			List<StaffAllocationBo> detailsOfStaff,
			ArrayList<TimeAllocationTo> arrayList) {
		TimeAllocationTo to = new TimeAllocationTo();
		HashMap<Integer, String> classMap = new HashMap<Integer, String>();
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		boolean firstRun = true;
		ClassSchemewise classSchemewise;
		Classes classes;
		Subject subject;
		for (StaffAllocationBo bo : detailsOfStaff) {
			if (firstRun) {
				to.setTeachingStaffId(bo.getTeachingStaffId().getId());
				firstRun = false;
			}
			classSchemewise = bo.getClassSchemeWise();
			classes = classSchemewise.getClasses();
			subject = bo.getPreferredSubjectId();
			classMap.put(classSchemewise.getId(), classes.getName());
			subjectMap.put(subject.getId(), subject.getName());
		}
		to.setClassMap(classMap);
		to.setSubjectMap(subjectMap);
		to.setBottomGrid(arrayList);
		return to;
	}

	public ArrayList<KeyValueTO> convertBotoTo(List<Object[]> teachingStaffList) {
		ArrayList<KeyValueTO> teacherList = new ArrayList<KeyValueTO>();
		KeyValueTO to = null;
		for (Iterator<Object[]> iterator = teachingStaffList.iterator(); iterator
				.hasNext();) {
			to = new KeyValueTO();
			Object[] objects = (Object[]) iterator.next();
			if (objects[0] != null) {
				to.setId(Integer.parseInt(objects[0].toString()));
			}
			if (objects[1] != null) {
				to.setDisplay(objects[1].toString());
			}
			teacherList.add(to);

		}
		return teacherList;

	}

	public ArrayList<TimeAllocationBo> getBoFromForm(TimeAllocationForm objForm) {
		TimeAllocationBo bo = null;
		ArrayList<TimeAllocationBo> listBo = new ArrayList<TimeAllocationBo>();
		TimeAllocationTo viewTo = objForm.getViewTo();
		String[] classSchemwiseIds = viewTo.getClassValues();
		String[] batchIds = viewTo.getBatchValues();
		String[] periodsIds = viewTo.getPeriods();
		ClassSchemewise classSchemewise = null;
		Batch batch = null;
		Period period = null;
		for (String id : classSchemwiseIds) {
			classSchemewise = new ClassSchemewise();
			classSchemewise.setId(Integer.parseInt(id));
			if (batchIds != null && batchIds.length > 0) {
				for (String batchId : batchIds) {
					if (batchId.trim().length() > 0) {
						batch = new Batch();
						batch.setId(Integer.parseInt(batchId));
					}
					if (periodsIds != null && periodsIds.length > 0) {
						for (String periodId : periodsIds) {
							if (periodId.trim().length() > 0) {
								period = new Period();
								period.setId(Integer.parseInt(periodId));
								bo = getBo(objForm, viewTo);
								bo.setClassId(classSchemewise);
								bo.setBatchId(batch);
								bo.setPeriodId(period);
								listBo.add(bo);
							}
						}
					} else {
						bo = getBo(objForm, viewTo);
						bo.setClassId(classSchemewise);
						bo.setBatchId(batch);
						listBo.add(bo);
					}
				}
			} else {
				bo = getBo(objForm, viewTo);
				bo.setClassId(classSchemewise);
				int flag = 0;
				for (String periodId : periodsIds) {
					if (periodId.trim().length() > 0) {
						flag = 1;
						period = new Period();
						period.setId(Integer.parseInt(periodId));
						bo = getBo(objForm, viewTo);
						bo.setClassId(classSchemewise);
						bo.setPeriodId(period);
						listBo.add(bo);
					}
				}
				if (flag == 0) {
					listBo.add(bo);
				}
			}
		}

		return listBo;
	}

	public TimeAllocationBo getBo(TimeAllocationForm objForm,
			TimeAllocationTo viewTo) {
		TimeAllocationBo bo = new TimeAllocationBo();
		Subject subject = new Subject();
		bo.setAcademicYearId(0);
		bo.setAcademicYear(objForm.getAcademicYr());
		Employee emp = new Employee();
		emp.setId(viewTo.getTeachingStaffId());
		bo.setTeachingStaffId(emp);
		subject.setId(viewTo.getSubjectId());
		bo.setPreferredSubjectId(subject);
		if (viewTo.getSubjectType() != null) {
			if (viewTo.getSubjectType().equalsIgnoreCase("t")) {
				bo.setSubjectType(1);
			} else {
				bo.setSubjectType(0);
			}
		}
		bo.setCreatedBy(objForm.getUserId());
		bo.setModifiedBy(objForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		ExamRoomMasterBO room = new ExamRoomMasterBO();
		if (viewTo.getRoomCodeId() != null && viewTo.getRoomCodeId() > 0) {
			room.setId(viewTo.getRoomCodeId());
		}
		bo.setRoomId(room);
		bo.setDay(viewTo.getDay());
		return bo;
	}

	public ArrayList<TimeAllocationTo> convertBoListtoToList(
			List<TimeAllocationBo> bottomGrid) {
		ArrayList<TimeAllocationTo> listValues = new ArrayList<TimeAllocationTo>();
		TimeAllocationTo to = null;
		for (TimeAllocationBo bo : bottomGrid) {
			to = new TimeAllocationTo();
			to.setId(bo.getId());
			to.setDayName(dayMap.get(bo.getDay()));
			to.setAcademicYear(bo.getAcademicYear() + "-"
					+ (bo.getAcademicYear() + 1));
			to.setTeachingStaffName(bo.getTeachingStaffId().getFirstName());
			to.setClassName(bo.getClassId().getClasses().getName());
			to.setSubjectName(bo.getPreferredSubjectId().getName());
			if (bo.getSubjectType() != null) {
				if (bo.getSubjectType() == 0)
					to.setSubjectType("Practical");
				else
					to.setSubjectType("Theory");
			}
			if (bo.getBatchId() != null) {
				to.setBatchName(bo.getBatchId().getBatchName());
			}
			if (bo.getPeriodId() != null) {
				to.setPeriodName(bo.getPeriodId().getPeriodName());
			}
			if (bo.getRoomId() != null) {
				to.setRoomCode(bo.getRoomId().getRoomNo());
			}
			listValues.add(to);
		}
		return listValues;
	}
}