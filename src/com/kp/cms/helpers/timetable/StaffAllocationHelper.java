package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.StaffAllocationTo;

public class StaffAllocationHelper {
	private static Log log = LogFactory.getLog(StaffAllocationHelper.class);
	public static StaffAllocationHelper staffAllocationHelper = null;

	public static StaffAllocationHelper getInstance() {
		if (staffAllocationHelper == null) {
			return new StaffAllocationHelper();
		}
		return staffAllocationHelper;
	}

	private StaffAllocationHelper() {

	}

	public ArrayList<StaffAllocationTo> convertBoListtoToList(
			List<StaffAllocationBo> bottomGridList) {
		log.info("Entry of convertBotoTo method in StaffAllocationHelper");
		StaffAllocationTo to = null;

		Iterator<StaffAllocationBo> listBo = bottomGridList.iterator();
		ArrayList<StaffAllocationTo> listValues = new ArrayList<StaffAllocationTo>();
		while (listBo.hasNext()) {
			StaffAllocationBo bo = (StaffAllocationBo) listBo.next();
			to = new StaffAllocationTo();
			to.setId(bo.getId());
			to.setClassName(bo.getClassSchemeWise().getClasses().getName());
			to.setSubjectPreference(bo.getPreferredSubjectId().getName());
			to.setTeachingStaff(bo.getTeachingStaffId().getFirstName());
			to.setAcademicYear(bo.getAcademicYear()+"-"+(bo.getAcademicYear()+1));
			listValues.add(to);

		}
		log.info("Exit of convertBotoTo method in StaffAllocationHelper");
		return listValues;
	}

	public ArrayList<KeyValueTO> convertBotoTo(List<Users> teachingStaffList) {
		ArrayList<KeyValueTO> teacherList = new ArrayList<KeyValueTO>();
		for (Iterator iterator = teachingStaffList.iterator(); iterator
				.hasNext();) {
			Users users = (Users) iterator.next();
			Employee employee=users.getEmployee();
			teacherList.add(new KeyValueTO(employee.getId(), employee.getFirstName()));
		}
		return teacherList;

	}

}
