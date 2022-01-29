package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.forms.timetable.StaffWiseTimeTableViewForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.StaffWiseTimeTableViewTo;

public class StaffWiseTimeTableViewHelper {
	public static StaffWiseTimeTableViewHelper staffWise = null;
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

	public static StaffWiseTimeTableViewHelper getInstance() {
		if (staffWise == null) {
			return new StaffWiseTimeTableViewHelper();
		}
		return staffWise;
	}

	private StaffWiseTimeTableViewHelper() {
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

	public void convertBoListToTimeTableViewToList(
			List<TimeAllocationBo> staffTimeTable,
			StaffWiseTimeTableViewForm objForm) {
		HashMap<Integer, HashMap<Integer, String>> dayPeriodMapList = new HashMap<Integer, HashMap<Integer, String>>();
		ArrayList<StaffWiseTimeTableViewTo> subTimeList = new ArrayList<StaffWiseTimeTableViewTo>();
		ArrayList<Integer> periodIds = new ArrayList<Integer>();
		ArrayList<String> periodListOfStaff = getPeriodList(staffTimeTable,
				periodIds);
		StringBuffer staffName = new StringBuffer();
		boolean firstRun = true;
		for (TimeAllocationBo bo : staffTimeTable) {
			if (firstRun) {
				Employee emp = bo.getTeachingStaffId();
				if (emp.getCode() != null) {
					staffName.append(emp.getFirstName() + "(" + emp.getCode()
							+ ")");
				} else {
					staffName.append(emp.getFirstName());
				}
				firstRun = false;
			}
			Period period = bo.getPeriodId();
			if (dayPeriodMapList.containsKey(bo.getDay())) {
				HashMap<Integer, String> innerPeriodMap = dayPeriodMapList
						.remove(bo.getDay());
				if (!innerPeriodMap.containsKey(period.getId())) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(bo.getClassId().getClasses().getName());
					buffer.append(bo.getPreferredSubjectId().getName());
					buffer.append(bo.getRoomId().getRoomNo());
					innerPeriodMap.put(period.getId(), buffer.toString());
				}
				dayPeriodMapList.put(bo.getDay(), innerPeriodMap);
			} else {
				HashMap<Integer, String> innerMap = new HashMap<Integer, String>();
				StringBuffer buffer = new StringBuffer();
				buffer.append(bo.getClassId().getClasses().getName() + "-");
				buffer.append(bo.getPreferredSubjectId().getName() + "-");
				buffer.append(bo.getRoomId().getRoomNo());
				innerMap.put(period.getId(), buffer.toString());
				dayPeriodMapList.put(bo.getDay(), innerMap);
			}
		}
		for (int i = 1; i <= 7; i++) {
			if (dayPeriodMapList.containsKey(i)) {
				HashMap<Integer, String> map = dayPeriodMapList.get(i);
				ArrayList<String> periods = new ArrayList<String>();
				for (Integer periodId : periodIds) {
					if (map.containsKey(periodId)) {
						periods.add(map.get(periodId));
					} else {
						periods.add("");
					}
				}
				subTimeList.add(new StaffWiseTimeTableViewTo(dayMap.get(i),
						periods));
			} else {
				subTimeList.add(new StaffWiseTimeTableViewTo(dayMap.get(i),
						makeAdjustableList(periodIds.size())));
			}
		}
		objForm.setTeachingStaff(staffName.toString());
		objForm.setSubTimeList(subTimeList);
		objForm.setPeriodList(periodListOfStaff);

	}

	private ArrayList<String> makeAdjustableList(int size) {
		ArrayList<String> adjustableList = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			adjustableList.add("");
		}
		return adjustableList;
	}

	private ArrayList<String> getPeriodList(
			List<TimeAllocationBo> staffTimeTable, ArrayList<Integer> periodIds) {
		ArrayList<String> periodList = new ArrayList<String>();
		for (TimeAllocationBo bo : staffTimeTable) {
			StringBuffer buffer = new StringBuffer();
			Period period = bo.getPeriodId();
			buffer.append(period.getPeriodName());
			buffer.append("\n");
			buffer.append("Time:   " + period.getStartTime() + " to "
					+ period.getEndTime());
			periodIds.add(period.getId());
			periodList.add(buffer.toString());
		}

		return periodList;
	}

}