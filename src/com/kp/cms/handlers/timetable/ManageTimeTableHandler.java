package com.kp.cms.handlers.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.kp.cms.bo.admin.ManageTimeTableBo;
import com.kp.cms.bo.admin.TimeAllocationBo;
import com.kp.cms.forms.timetable.ManageTimeTableForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.ManageTimeTableHelper;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.timetable.ManageTimeTableTo;
import com.kp.cms.transactionsimpl.timetable.ManageTimeTableImpl;

public class ManageTimeTableHandler extends ExamGenHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static volatile ManageTimeTableHandler manageTimeTable;
	private static ManageTimeTableHelper helper = ManageTimeTableHelper
			.getInstance();
	private static ManageTimeTableImpl impl = ManageTimeTableImpl.getInstance();

	private ManageTimeTableHandler() {

	}

	public static ManageTimeTableHandler getinstance() {
		if (manageTimeTable == null) {
			manageTimeTable = new ManageTimeTableHandler();
			return manageTimeTable;
		}
		return manageTimeTable;

	}

	public ArrayList<ManageTimeTableTo> fetchData(int academicYear,
			int classes, String userId, ManageTimeTableForm objForm) {

		return helper.getViewTo(impl.getTeachingStaff(academicYear, classes),
				helper.convertBoListtoToList(impl.getBottomGrid(classes,
						academicYear)), helper.convertMap(impl.getSubjects(
						classes, academicYear)), impl
						.getRoomsByClassSchemewiseIds(classes), helper
						.convertMap(impl.getPeriodsList(classes)), impl
						.getTimeAllocatedList(classes, academicYear),objForm);
	}

	public int addData(ManageTimeTableForm objForm) {
		return impl.insert_List(helper.getBoFromForm(objForm));
	}

	public int delete(Integer id) {

		return impl.delete(new TimeAllocationBo(), id);
	}

}
