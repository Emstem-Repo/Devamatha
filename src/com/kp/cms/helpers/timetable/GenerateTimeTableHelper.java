package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.DurationAllocationBo;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.forms.timetable.GenerateTimeTableForm;
import com.kp.cms.to.timetable.GenerateTimeTableTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.DateIncrement;

public class GenerateTimeTableHelper {
	public static GenerateTimeTableHelper generatTimeTable = null;

	public static GenerateTimeTableHelper getInstance() {
		if (generatTimeTable == null) {
			return new GenerateTimeTableHelper();
		}
		return generatTimeTable;
	}

	private GenerateTimeTableHelper() {
	}

	private HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();

	public String convertIntoCorrectDateFormat(Object[] schemeDuration,
			StringBuffer buffer) {
		StringBuffer conctenatedDate = new StringBuffer();
		String startDate = null, endDate = null;
		if (schemeDuration[2] != null) {
			conctenatedDate.append("Semester " + schemeDuration[2] + "(");
		}
		if (schemeDuration[0] != null) {
			startDate = CommonUtil.ConvertStringToDateFormat(schemeDuration[0]
					.toString(), "yyyy-mm-dd", "dd/mm/yyyy");
			conctenatedDate.append(startDate);
		}
		if (schemeDuration[1] != null) {
			conctenatedDate.append(" till ");
			endDate = CommonUtil.ConvertStringToDateFormat(schemeDuration[1]
					.toString(), "yyyy-mm-dd", "dd/mm/yyyy");
			conctenatedDate.append(endDate);
		}
		conctenatedDate.append(")");
		buffer.append(calculateNoofWeeksBetweenDates(startDate, endDate));
		return conctenatedDate.toString();
	}

	private Object calculateNoofWeeksBetweenDates(String startDate,
			String endDate) {
		DateIncrement dI = new DateIncrement();
		int dateDifference = dI.getDays(startDate, endDate).size();
		int weeks = (int) dateDifference / 7;
		return weeks;
	}

	public ArrayList<GenerateTimeTableTo> getListOfTo(
			List<DurationAllocationBo> allocatedHoursForSubjects,
			List<TimeAllocationBo> enteredHoursForSubjects, int noOfWeeks,
			GenerateTimeTableForm objForm, ArrayList<Integer> subjectIds) {
		HashMap<Integer, Integer> enteredHoursPerWeek = getEnteredMap(enteredHoursForSubjects);
		HashMap<Integer, String> allocatedHoursPerWeek = getAllocatedMap(allocatedHoursForSubjects);
		ArrayList<GenerateTimeTableTo> listOfValues = new ArrayList<GenerateTimeTableTo>();
		GenerateTimeTableTo to = null;
		for (Integer subjectId : subjectIds) {
			to = new GenerateTimeTableTo();
			int totalHoursRequired = 1;
			int totalHoursAllocated = 1;
			if (enteredHoursPerWeek.containsKey(subjectId)) {
				totalHoursAllocated = enteredHoursPerWeek.get(subjectId)
						* noOfWeeks;
			}
			if (allocatedHoursPerWeek.containsKey(subjectId)) {
				totalHoursRequired = Integer.parseInt(allocatedHoursPerWeek
						.get(subjectId))
						/ noOfWeeks;
			}
			to.setStatus(getStatus(totalHoursAllocated, totalHoursRequired));
			to.setNoOfHours(getHours(totalHoursAllocated, totalHoursRequired));
			to.setSubjectName(subjectMap.get(subjectId));
			to.setClassName(objForm.getClasses());
			to.setEndDate(DateIncrement.getEnddate(totalHoursAllocated));
			listOfValues.add(to);
		}
		return listOfValues;
	}

	private String getHours(int totalHoursAllocated, int totalHoursRequired) {
		int hours = 0;
		if (totalHoursAllocated > totalHoursRequired) {
			hours = totalHoursAllocated - totalHoursRequired;
		}
		if (totalHoursAllocated < totalHoursRequired) {
			hours = totalHoursRequired - totalHoursAllocated;
		}
		if (totalHoursAllocated == totalHoursRequired) {
			hours = 0;
		}
		return Integer.toString(hours);
	}

	private String getStatus(int totalHoursAllocated, int totalHoursRequired) {
		String status = "";
		if (totalHoursAllocated > totalHoursRequired) {
			status = "shortage";
		}
		if (totalHoursAllocated < totalHoursRequired) {
			status = "FreeHours";
		}
		if (totalHoursAllocated == totalHoursRequired) {
			status = "Normal";
		}
		return status;
	}

	private HashMap<Integer, Integer> getEnteredMap(
			List<TimeAllocationBo> enteredHoursForSubjects) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (TimeAllocationBo bo : enteredHoursForSubjects) {
			int subjectId = bo.getPreferredSubjectId().getId();
			if (map.containsKey(subjectId)) {
				int mapId = map.remove(subjectId);
				map.put(subjectId, (++mapId));
			} else {
				map.put(subjectId, 1);
			}
		}

		return map;
	}

	private HashMap<Integer, String> getAllocatedMap(
			List<DurationAllocationBo> allocatedHoursForSubjects) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (DurationAllocationBo bo : allocatedHoursForSubjects) {
			map.put(bo.getSubjectId().getId(), bo.getMaximumLectureHours());
		}
		return map;
	}

	public void convertSubjectMap(List<Object[]> subjects,
			ArrayList<Integer> subjectIds) {
		Iterator<Object[]> itr = subjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			subjectMap
					.put(Integer.parseInt(row[0].toString()), (String) row[1]);
			subjectIds.add(Integer.parseInt(row[0].toString()));
		}

	}
}