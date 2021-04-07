package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DurationAllocationBo;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.to.timetable.DurationAllocationTo;

public class DurationAllocationHelper {
	private static Log log = LogFactory.getLog(DurationAllocationHelper.class);
	public static DurationAllocationHelper manageWorkingDays = null;

	public static DurationAllocationHelper getInstance() {
		if (manageWorkingDays == null) {
			return new DurationAllocationHelper();
		}
		return manageWorkingDays;
	}

	private DurationAllocationHelper() {

	}

	public ArrayList<DurationAllocationTo> convertBoListtoToList(
			List<Object[]> list, HashMap<Integer, String> schemeMap) {
		log.info("Entry of convertBotoTo method in StaffAllocationHelper");
		DurationAllocationTo to = null;
		Iterator<Object[]> listBo = list.iterator();
		ArrayList<DurationAllocationTo> listValues = new ArrayList<DurationAllocationTo>();
		while (listBo.hasNext()) {
			Object[] row = listBo.next();
			DurationAllocationBo bo = (DurationAllocationBo) row[0];
			Subject sBo = (Subject) row[1];
			to = new DurationAllocationTo();
			if (bo != null) {
				to.setId(bo.getId());
				to.setSubjectName(bo.getSubjectId().getName());
				to.setSubjectId(bo.getSubjectId().getId());
				to
						.setMinimumLectureHours((bo.getMinimumLectureHours() != null) ? Integer
								.parseInt(bo.getMinimumLectureHours())
								: 0);
				to
						.setMaximumLectureHours((bo.getMaximumLectureHours() != null) ? Integer
								.parseInt(bo.getMaximumLectureHours())
								: 0);
				to.setSchemeMap(schemeMap);
				to.setSchemeNo(bo.getCourseSchemeId().getId());
			} else {
				to.setSubjectName(sBo.getName());
				to.setSchemeMap(schemeMap);
				to.setSubjectId(sBo.getId());
			}
			listValues.add(to);

		}
		log.info("Exit of convertBotoTo method in StaffAllocationHelper");
		return listValues;
	}

}