package com.kp.cms.helpers.timetable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ManageHolidayListBo;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.to.timetable.ManageHolidayListTo;
import com.kp.cms.utilities.CommonUtil;

public class ManageHolidayListHelper {
	private static Log log = LogFactory.getLog(ManageHolidayListHelper.class);
	public static ManageHolidayListHelper manageHolidayListHelper = null;

	public static ManageHolidayListHelper getInstance() {
		if (manageHolidayListHelper == null) {
			return new ManageHolidayListHelper();
		}
		return manageHolidayListHelper;
	}

	private ManageHolidayListHelper() {

	}

	public ArrayList<ManageHolidayListTo> convertBoListtoToList(
			List<ManageHolidayListBo> bottomGridList) {
		log.info("Entry of convertBotoTo method in ManageHolidayListHelper");
		ManageHolidayListTo to = null;
		Iterator<ManageHolidayListBo> listBo = bottomGridList.iterator();
		ArrayList<ManageHolidayListTo> listValues = new ArrayList<ManageHolidayListTo>();
		while (listBo.hasNext()) {
			ManageHolidayListBo bo = (ManageHolidayListBo) listBo.next();
			to = new ManageHolidayListTo();
			to.setId(bo.getId());
			to.setAcademicYear(bo.getAcademicYear());
			to.setFromDate(bo.getStartDate());
			to.setToDate(bo.getEndDate());
			to.setStartDate(CommonUtil.formatDate(bo.getStartDate(),
					"dd/MM/yyyy"));
			to.setEndDate(CommonUtil.formatDate(bo.getEndDate(), "dd/MM/yyyy"));
			to.setHolidayName(bo.getHoliday());
			listValues.add(to);

		}
		log.info("Exit of convertBotoTo method in ManageHolidayListHelper");
		return listValues;
	}

	public ManageHolidayListTo convertBotoTo(ManageHolidayListBo bo) {
		ManageHolidayListTo to = new ManageHolidayListTo();
		to.setId(bo.getId());
		to.setAcademicYear(bo.getAcademicYear());
		to.setCreatedBy(bo.getCreatedBy());
		to.setModifiedBy(bo.getModifiedBy());
		to.setCreatedDate(bo.getCreatedDate());
		to.setLastModifiedDate(bo.getLastModifiedDate());
		to.setHolidayName(bo.getHoliday());
		to.setFromDate(bo.getStartDate());
		to.setToDate(bo.getEndDate());
		return to;
	}
}
