package com.kp.cms.handlers.timetable;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ManageHolidayListBo;
import com.kp.cms.helpers.timetable.ManageWorkingDaysHelper;
import com.kp.cms.to.timetable.ManageHolidayListTo;
import com.kp.cms.transactionsimpl.timetable.ManageWorkingDaysImpl;
import com.kp.cms.utilities.CommonUtil;

public class ManageWorkingDaysHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static final Log log = LogFactory
			.getLog(ManageWorkingDaysHandler.class);

	private static volatile ManageWorkingDaysHandler manageWorkingDays;
	private static ManageWorkingDaysHelper helper = ManageWorkingDaysHelper
			.getInstance();
	private static ManageWorkingDaysImpl impl = ManageWorkingDaysImpl
			.getInstance();

	private ManageWorkingDaysHandler() {

	}

	public static ManageWorkingDaysHandler getinstance() {
		if (manageWorkingDays == null) {
			manageWorkingDays = new ManageWorkingDaysHandler();
			return manageWorkingDays;
		}
		return manageWorkingDays;

	}

	public ArrayList<ManageHolidayListTo> getBottomGrid() {
		return helper.convertBoListtoToList(impl.getBottomGrid());
	}

	public int addDetails(int academicYr, String endDate, String startDate,
			String holidayName, String userId) {
		ManageHolidayListBo bo = new ManageHolidayListBo();
		bo.setAcademicYear(academicYr);
		bo.setEndDate(CommonUtil.ConvertStringToDate(endDate));
		bo.setStartDate(CommonUtil.ConvertStringToDate(startDate));
		bo.setHoliday(holidayName);
		bo.setModifiedBy(userId);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		return impl.insert(bo);
	}

	public ManageHolidayListTo editDetails(Integer id) {

		return helper.convertBotoTo(impl.getEditDetails(id));

	}

	public int delete(Integer id) {

		return impl.delete(id);

	}

	public int updateDetails(int academicYr, String endDate, String startDate,
			String holidayName, String userId, ManageHolidayListTo metaTo) {
		ManageHolidayListBo bo = new ManageHolidayListBo();
		bo.setId(metaTo.getId());
		bo.setAcademicYear(academicYr);
		bo.setEndDate(CommonUtil.ConvertStringToDate(endDate));
		bo.setStartDate(CommonUtil.ConvertStringToDate(startDate));
		bo.setHoliday(holidayName);
		bo.setModifiedBy(userId);
		bo.setCreatedBy(userId);
		bo.setCreatedDate(metaTo.getCreatedDate());
		bo.setLastModifiedDate(new Date());
		bo.setIsActive(true);
		return impl.update(bo);
	}

}
