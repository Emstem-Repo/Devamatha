package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.ManageTimeTableBo;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.forms.timetable.ManageTimeTableForm;
import com.kp.cms.to.timetable.ManageTimeTableTo;

public class ManageTimeTableHelper {
	public static ManageTimeTableHelper manageTimeTable = null;
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


	public static ManageTimeTableHelper getInstance() {
		if (manageTimeTable == null) {
			return new ManageTimeTableHelper();
		}
		return manageTimeTable;
	}

	private ManageTimeTableHelper() {
	}

	public ArrayList<ManageTimeTableTo> getViewTo(List<Object[]> teachingStaff,
			ArrayList<ManageTimeTableTo> bottomGrid,
			HashMap<Integer, String> subjectListMap,
			Map<Integer, String> roomMap, HashMap<Integer, String> periodMap,
			List<TimeAllocationBo> timeAllocatedList,
			ManageTimeTableForm objForm) {
		HashMap<String, String> subjectTypeMap = new HashMap<String, String>();
		ArrayList<ManageTimeTableTo> listOfData = new ArrayList<ManageTimeTableTo>();
		ManageTimeTableTo to = null;
		objForm.setBottomGrid(bottomGrid);
		for (TimeAllocationBo bo : timeAllocatedList) {
			objForm.setClassName(bo.getClassId().getClasses().getName());
			to = new ManageTimeTableTo();
			to.setRoomCodeList(roomMap);
			to.setSubjectMap(subjectListMap);
			to.setBottomGrid(bottomGrid);
			to.setTeachingStaffList(getMapOfTeachingStaff(teachingStaff));
			to.setPeriodsList(periodMap);
			to.setTeachingStaffId(bo.getTeachingStaffId().getId());
			to.setSubjectId(bo.getPreferredSubjectId().getId());
			to.setSubjectTypeMap(subjectTypeMap);
			to.setDayMap(dayMap);
			if (bo.getSubjectType() != null) {
				if (bo.getSubjectType() == 1) {
					subjectTypeMap.put("T", "Theory");
					to.setSubjectType("T");
				} else {
					subjectTypeMap.put("P", "Practical");
					to.setSubjectType("P");
				}
			}
			to.setDay(bo.getDay());
			to.setRoomCodeId(bo.getRoomId().getId());
			listOfData.add(to);
		}

		return listOfData;
	}

	public HashMap<Integer, String> getMapOfTeachingStaff(
			List<Object[]> teachingStaffList) {
		HashMap<Integer, String> teacherMap = new HashMap<Integer, String>();
		for (Iterator<Object[]> iterator = teachingStaffList.iterator(); iterator
				.hasNext();) {

			Object[] objects = (Object[]) iterator.next();

			if (objects[0] != null) {
				teacherMap.put(Integer.parseInt(objects[0].toString()),
						(objects[1] != null) ? objects[1].toString() : "");
			}

		}
		return teacherMap;

	}

	public ArrayList<TimeAllocationBo> getBoFromForm(ManageTimeTableForm objForm) {
		TimeAllocationBo bo = null;
		ArrayList<TimeAllocationBo> listBo = new ArrayList<TimeAllocationBo>();
		for (ManageTimeTableTo to : objForm.getViewTo()) {
			ManageTimeTableTo viewTo = to;
			String[] batchIds = viewTo.getBatchValues();
			String[] periodsIds = viewTo.getPeriods();
			int flag = 0;
			Batch batch = null;
			Period period = null;
			if (batchIds != null && batchIds.length > 0) {
				for (String batchId : batchIds) {
					flag = 1;
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
								bo.setBatchId(batch);
								bo.setPeriodId(period);
								listBo.add(bo);
							}
						}
					} else {
						bo = getBo(objForm, viewTo);
						bo.setBatchId(batch);
						listBo.add(bo);
					}
				}
			} else {
				if (periodsIds != null && periodsIds.length > 0) {
					flag = 1;
					for (String periodId : periodsIds) {
						if (periodId.trim().length() > 0) {
							period = new Period();
							period.setId(Integer.parseInt(periodId));
							bo = getBo(objForm, viewTo);
							bo.setPeriodId(period);
							listBo.add(bo);
						}
					}
				}
			}
			if (flag == 0) {
				listBo.add(getBo(objForm, viewTo));
			}
		}
		return listBo;
	}

	public TimeAllocationBo getBo(ManageTimeTableForm objForm,
			ManageTimeTableTo viewTo) {
		TimeAllocationBo bo = new TimeAllocationBo();
		ClassSchemewise classSchemewise = new ClassSchemewise();
		classSchemewise.setId(Integer.parseInt(objForm.getClasses()));
		bo.setClassId(classSchemewise);
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

	public ArrayList<ManageTimeTableTo> convertBoListtoToList(
			List<TimeAllocationBo> bottomGrid) {
		ArrayList<ManageTimeTableTo> listValues = new ArrayList<ManageTimeTableTo>();
		ManageTimeTableTo to = null;
		for (TimeAllocationBo bo : bottomGrid) {
			to = new ManageTimeTableTo();
			to.setId(bo.getId());
			to.setDayName(dayMap.get(bo.getDay()));
			to.setAcademicYear(bo.getAcademicYear() + "-"
					+ (bo.getAcademicYear() + 1));

			to.setClassName(bo.getClassId().getClasses().getName());
			listValues.add(to);
		}
		return listValues;
	}

	public HashMap<Integer, String> convertMap(List<Object[]> objectMap) {
		Iterator<Object[]> itr = objectMap.iterator();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			map.put(Integer.parseInt(row[0].toString()), (String) row[1]);
		}
		return map;

	}
}