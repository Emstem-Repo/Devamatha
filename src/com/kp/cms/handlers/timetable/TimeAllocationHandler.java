package com.kp.cms.handlers.timetable;

import java.util.ArrayList;

import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.forms.timetable.TimeAllocationForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.TimeAllocationHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.TimeAllocationTo;
import com.kp.cms.transactionsimpl.timetable.TimeAllocationImpl;

public class TimeAllocationHandler extends ExamGenHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static volatile TimeAllocationHandler timeAllocation;
	private static TimeAllocationHelper helper = TimeAllocationHelper
			.getInstance();
	private static TimeAllocationImpl impl = TimeAllocationImpl.getInstance();

	private TimeAllocationHandler() {

	}

	public static TimeAllocationHandler getinstance() {
		if (timeAllocation == null) {
			timeAllocation = new TimeAllocationHandler();
			return timeAllocation;
		}
		return timeAllocation;

	}

	public TimeAllocationTo fetchData(int academicYear, int teachingStaffId,
			String userId) {
		return helper.convertBoListtoToList(impl.getDetailsOfStaff(
				academicYear, teachingStaffId), helper
				.convertBoListtoToList(impl.getBottomGrid(teachingStaffId,academicYear)));
	}

	public ArrayList<KeyValueTO> getTeachingStaffList() {
		return helper.convertBotoTo(impl.getTeachingStaffList());
	}

	public int addData(TimeAllocationForm objForm) {
		return impl.insert_List(helper.getBoFromForm(objForm));
	}

	public int delete(Integer id) {

		return impl.delete(new TimeAllocationBo(), id);
	}
}
