package com.kp.cms.handlers.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.StaffAllocationBo;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.helpers.timetable.StaffAllocationHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.StaffAllocationTo;
import com.kp.cms.transactionsimpl.timetable.StaffAllocationImpl;

public class StaffAllocationHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static final Log log = LogFactory
			.getLog(StaffAllocationHandler.class);

	private static volatile StaffAllocationHandler staffAllocation;
	private static StaffAllocationHelper helper = StaffAllocationHelper
			.getInstance();
	private static StaffAllocationImpl impl = StaffAllocationImpl.getInstance();

	private StaffAllocationHandler() {

	}

	public static StaffAllocationHandler getinstance() {
		if (staffAllocation == null) {
			staffAllocation = new StaffAllocationHandler();
			return staffAllocation;
		}
		return staffAllocation;

	}

	public ArrayList<KeyValueTO> getTeachingStaffList() {
		return helper.convertBotoTo(impl.getTeachingStaffList());
	}

	public ArrayList<StaffAllocationTo> setBottomGrid() {

		return helper.convertBoListtoToList(impl.getBottomGrid());
	}

	public int addData(int teachingStaff, int academicYear, String className,
			String classValues, String userId,
			ArrayList<String> duplicateMessageList) {
		StringTokenizer tokens = new StringTokenizer(classValues, ",");
		ClassSchemewise classSchemeWise;
		Subject subject;
		StaffAllocationBo bo;
		Employee emp;
		ArrayList<StaffAllocationBo> listBO = new ArrayList<StaffAllocationBo>();
		while (tokens.hasMoreElements()) {
			int subid = Integer.parseInt(tokens.nextElement().toString());

			bo = new StaffAllocationBo();
			bo.setAcademicYear(academicYear);
			classSchemeWise = new ClassSchemewise();
			classSchemeWise.setId(Integer.parseInt(className));
			bo.setClassSchemeWise(classSchemeWise);
			subject = new Subject();
			subject.setId(subid);
			bo.setPreferredSubjectId(subject);
			emp = new Employee();
			emp.setId(teachingStaff);
			bo.setTeachingStaffId(emp);
			bo.setModifiedBy(userId);
			bo.setCreatedBy(userId);
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			if (impl.checkDuplicateValue(emp, academicYear, subject)) {
				listBO.add(bo);
			} else {
				duplicateMessageList.add("duplicate Values");
			}
		}
		return impl.insert_List(listBO);
	}

	public int delete(int id) {

		return impl.delete(id);
	}

}
