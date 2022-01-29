package com.kp.cms.handlers.timetable;

import java.util.ArrayList;

import com.kp.cms.forms.timetable.StaffWiseTimeTableViewForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.StaffWiseTimeTableViewHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.timetable.StaffWiseTimeTableViewImpl;

public class StaffWiseTimeTableViewHandler extends ExamGenHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static volatile StaffWiseTimeTableViewHandler staffWiseHandler;
	private static StaffWiseTimeTableViewHelper helper = StaffWiseTimeTableViewHelper
			.getInstance();
	private static StaffWiseTimeTableViewImpl impl = StaffWiseTimeTableViewImpl
			.getInstance();

	private StaffWiseTimeTableViewHandler() {

	}

	public static StaffWiseTimeTableViewHandler getinstance() {
		if (staffWiseHandler == null) {
			staffWiseHandler = new StaffWiseTimeTableViewHandler();
			return staffWiseHandler;
		}
		return staffWiseHandler;

	}

	public ArrayList<KeyValueTO> getTeachingStaffList() {
		return helper.convertBotoTo(impl.getTeachingStaffList());
	}

	public void fetchData(StaffWiseTimeTableViewForm objForm) {

		helper.convertBoListToTimeTableViewToList(impl.getStaffTimeTable(
				Integer.parseInt(objForm.getTeachingStaff()), objForm
						.getAcademicYr()), objForm);
	}

}

