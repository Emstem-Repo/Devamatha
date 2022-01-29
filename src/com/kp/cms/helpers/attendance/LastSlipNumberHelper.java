package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.to.attendance.LastSlipNumberTo;
import com.kp.cms.to.pettycash.FinancialYearTO;

public class LastSlipNumberHelper {

	private static Log log = LogFactory.getLog(LastSlipNumberHelper.class);
	private static volatile LastSlipNumberHelper lastSlipNumberHelper;

	/**
	 * @return
	 */
	public static LastSlipNumberHelper getInstance() {

		if (lastSlipNumberHelper == null) {
			lastSlipNumberHelper = new LastSlipNumberHelper();
			return lastSlipNumberHelper;
		}
		return lastSlipNumberHelper;

	}

	/**
	 * @param attendanceSlipNumber
	 * @return
	 * @throws Exception
	 */
	public List<LastSlipNumberTo> convertBoListtoToList(
			List<AttendanceSlipNumber> attendanceSlipNumber) throws Exception {
		log.info("Start of covertBoListtoToList of LastSlipNumberHelper");
		List<LastSlipNumberTo> slipNumberToList = new ArrayList<LastSlipNumberTo>();

		if (attendanceSlipNumber != null && !attendanceSlipNumber.isEmpty()) {
			Iterator<AttendanceSlipNumber> iterator = attendanceSlipNumber
					.iterator();
			LastSlipNumberTo lastSlipNumberTo;
			while (iterator.hasNext()) {
				lastSlipNumberTo = new LastSlipNumberTo();
				AttendanceSlipNumber attendanceSlipNumberBo = (AttendanceSlipNumber) iterator
						.next();
				lastSlipNumberTo.setId(attendanceSlipNumberBo.getId());
				if (attendanceSlipNumberBo.getPcFinancialYear() != null
						&& attendanceSlipNumberBo.getIsActive() != null
						&& attendanceSlipNumberBo.getPcFinancialYear()
								.getIsActive()
						&& attendanceSlipNumberBo.getPcFinancialYear()
								.getFinancialYear() != null)

				{
					lastSlipNumberTo.setAcademicYear(attendanceSlipNumberBo
							.getPcFinancialYear().getFinancialYear());

				}
				if (attendanceSlipNumberBo.getStartingNumber() != null
						&& !attendanceSlipNumberBo.getStartingNumber()
								.isEmpty()) {
					lastSlipNumberTo.setSlipNo(attendanceSlipNumberBo
							.getStartingNumber());
				}
				slipNumberToList.add(lastSlipNumberTo);

			}
		}
		log.info("existing covertBoListtoToList of LastSlipNumberHelper");
		return slipNumberToList;

	}

	/**
	 * @param finanicalYearListBo
	 * @return
	 * @throws Exception
	 */
	public List<FinancialYearTO> convertToListToBo(
			List<PcFinancialYear> finanicalYearListBo) throws Exception {
		log.info("Start of convertToListToBo of LastSlipNumberHelper");
		List<FinancialYearTO> financialYearTolist = new ArrayList<FinancialYearTO>();
		if (finanicalYearListBo != null && !finanicalYearListBo.isEmpty()) {
			Iterator<PcFinancialYear> it = finanicalYearListBo.iterator();
			while (it.hasNext()) {
				PcFinancialYear pcfinancailYear = it.next();
				FinancialYearTO yearTo = new FinancialYearTO();
				yearTo.setId(pcfinancailYear.getId());
				yearTo.setFinancialYear(pcfinancailYear.getFinancialYear());
				financialYearTolist.add(yearTo);
			}
		}
		log.info("Exiting convertToListToBo of LastSlipNumberHelper");
		return financialYearTolist;
	}

	/**
	 * @param lastSlipNumberTO
	 * @return
	 * @throws Exception
	 */
	public AttendanceSlipNumber populateTotoBO(LastSlipNumberTo lastSlipNumberTO)
			throws Exception {
		log.info("Start of populateTotoBO of LastSlipNumberHelper");
		AttendanceSlipNumber attendanceSlipNumber = new AttendanceSlipNumber();
		if (lastSlipNumberTO != null) {
			attendanceSlipNumber.setStartingNumber(String
					.valueOf(lastSlipNumberTO.getSlipNo()));

			PcFinancialYear financialYear = new PcFinancialYear();
			financialYear.setId(Integer.parseInt(lastSlipNumberTO
					.getAcademicYear()));
			attendanceSlipNumber.setPcFinancialYear(financialYear);
			attendanceSlipNumber.setCreatedBy(lastSlipNumberTO.getCreatedBy());
			attendanceSlipNumber
					.setModifiedBy(lastSlipNumberTO.getModifiedBy());
			attendanceSlipNumber.setCreatedDate(new Date());
			attendanceSlipNumber.setLastModifiedDate(new Date());
			attendanceSlipNumber.setIsActive(true);
			attendanceSlipNumber.setCurrentNo(Integer.parseInt(lastSlipNumberTO
					.getSlipNo()));
		}
		log.info("Exiting populateTotoBO of LastSlipNumberHelper");
		return attendanceSlipNumber;
	}

	/**
	 * Used in editing
	 * 
	 * @param slipNumber
	 * @return
	 * @throws Exception
	 */
	public LastSlipNumberTo populateBotoTOEdit(AttendanceSlipNumber slipNumber)
			throws Exception {
		log.info("Start of populateBotoTOEdit of LastSlipNumberHelper");
		LastSlipNumberTo lastSlipNumberTo = new LastSlipNumberTo();
		if (slipNumber != null) {
			lastSlipNumberTo
					.setSlipNo(slipNumber.getStartingNumber() != null ? slipNumber
							.getStartingNumber()
							: null);
			if (slipNumber.getPcFinancialYear() != null) {
				if (slipNumber.getPcFinancialYear().getFinancialYear() != null) {
					lastSlipNumberTo.setAcademicYear(slipNumber
							.getPcFinancialYear().getFinancialYear());
				}
				lastSlipNumberTo.setFinacialYearID(slipNumber
						.getPcFinancialYear().getId());
			}
			lastSlipNumberTo.setId(slipNumber.getId());
		}
		log.info("Existing populateBotoTOEdit of LastSlipNumberHelper");
		return lastSlipNumberTo;
	}

	/**
	 * Used in update
	 * 
	 * @param lastSlipNumberTo
	 * @return
	 * @throws Exception
	 */
	public AttendanceSlipNumber populateTotoBOUpdate(
			LastSlipNumberTo lastSlipNumberTo) throws Exception {
		log.info("Start of populateTotoBOUpdate of LastSlipNumberHelper");
		AttendanceSlipNumber attendanceSlipNumber = null;
		attendanceSlipNumber = new AttendanceSlipNumber();
		attendanceSlipNumber.setId(lastSlipNumberTo.getId());
		attendanceSlipNumber.setStartingNumber(String.valueOf(lastSlipNumberTo
				.getSlipNo()));
		PcFinancialYear financialYear = new PcFinancialYear();
		financialYear.setId(Integer
				.parseInt(lastSlipNumberTo.getAcademicYear()));
		attendanceSlipNumber.setPcFinancialYear(financialYear);
		attendanceSlipNumber.setIsActive(true);
		attendanceSlipNumber.setModifiedBy(lastSlipNumberTo.getModifiedBy());
		attendanceSlipNumber.setLastModifiedDate(new Date());
		// attendanceSlipNumber.setCurrentNo(Integer.parseInt(lastSlipNumberTo.getSlipNo()));
		log.info("End of populateTotoBOUpdate of LastSlipNumberHelper");
		return attendanceSlipNumber;
	}

}
