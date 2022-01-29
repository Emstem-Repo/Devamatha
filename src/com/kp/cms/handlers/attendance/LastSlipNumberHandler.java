package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.forms.attendance.LastSlipNumberForm;
import com.kp.cms.helpers.attendance.LastSlipNumberHelper;
import com.kp.cms.to.attendance.LastSlipNumberTo;
import com.kp.cms.to.pettycash.FinancialYearTO;
import com.kp.cms.transactions.attandance.ILastSlipNumberTransaction;
import com.kp.cms.transactionsimpl.attendance.LastSlipNumberTransactionsImpl;

public class LastSlipNumberHandler {
	
	private static final Log log = LogFactory.getLog(LastSlipNumberHandler.class);
	
	ILastSlipNumberTransaction transaction = new LastSlipNumberTransactionsImpl();
	private static volatile LastSlipNumberHandler lastSlipNumberHandler;
	
	/**
	 * @return
	 */
	public static LastSlipNumberHandler getInstance()
	{
		if(lastSlipNumberHandler == null)
		{
			lastSlipNumberHandler = new LastSlipNumberHandler();
			return lastSlipNumberHandler;
		}
		return lastSlipNumberHandler;
		
	}
	
	/**
	 * used to get slip number details from Bo
	 * @return
	 * @throws Exception
	 */
	public List<LastSlipNumberTo> getSlipNumberDetails() throws Exception {
		log.info("Start of getSlipNumberDetails of LastSlipNumberHandler");
		if (transaction != null) {
			List<AttendanceSlipNumber> attendanceSlipNumberBo = transaction
					.getSlipNumberDetails();
			if (attendanceSlipNumberBo != null) {
				return LastSlipNumberHelper.getInstance()
						.convertBoListtoToList(attendanceSlipNumberBo);
			}
		}
		log.info("Existing getSlipNumberDetails of LastSlipNumberHandler");
		return new ArrayList<LastSlipNumberTo>();
	}
	
	/**
	 * used to get financial year from Bo
	 * @return
	 * @throws Exception
	 */
	public List<FinancialYearTO> getFinancialYear() throws Exception{
		
		if(transaction!= null)
		{
			List<PcFinancialYear> FinancialYearListBo = transaction.getFinancialYearList();
			if(FinancialYearListBo != null)
			{
				return LastSlipNumberHelper.getInstance().convertToListToBo(FinancialYearListBo);
			}
		}
		return  new ArrayList<FinancialYearTO>();
		
	}
	
	/**
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public AttendanceSlipNumber getLastSlipNumberYear(int year)
			throws Exception {
		log.info("Start of getLastSlipNumberYear of LastSlipNumberHandler");
		if (transaction != null) {
			return transaction.getLastSlipNumberYear(year);
		}
		log.info("Exiting getLastSlipNumberYear of LastSlipNumberHandler");
		return new AttendanceSlipNumber();

	}

	/**
	 * used to adding last slip number from form to To
	 * @param lastSlipNumberForm
	 * @return
	 * @throws Exception
	 */
	public boolean addLastSlipNumber(LastSlipNumberForm lastSlipNumberForm)
			throws Exception {
		log.info("Start of addLastSlipNumber of LastSlipNumberHandler");
		LastSlipNumberTo lastSlipNumberTo = new LastSlipNumberTo();
		lastSlipNumberTo.setSlipNo(lastSlipNumberForm.getSlipNo());
		lastSlipNumberTo.setAcademicYear(lastSlipNumberForm.getAcademicYear());
		lastSlipNumberTo.setCreatedBy(lastSlipNumberForm.getUserId());
		lastSlipNumberTo.setModifiedBy(lastSlipNumberForm.getUserId());
		if (lastSlipNumberTo != null) {
			AttendanceSlipNumber attendanceSlipNumberBo = LastSlipNumberHelper
					.getInstance().populateTotoBO(lastSlipNumberTo);
			if(transaction != null)
			{
				return transaction.addLastSlipNumber(attendanceSlipNumberBo);
			}
		}
		log.info("Existing addLastSlipNumber of LastSlipNumberHandler");
		return false;

	}
	
	/**
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteLastSlipNumber(int id, String userId) throws Exception
	{
		log.info("Start of deleteLastSlipNumber of LastSlipNumberHandler");
		if(transaction != null)
		{			
			return transaction.deleteLastSlipNumber(id, userId);
		}
		log.info("End of deleteLastSlipNumber of LastSlipNumberHandler");
		return false;
		
	}
	
	/**
	 * @param year
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateLastSlipNumber(int year, String userId)
			throws Exception {
		log.info("Start of reActivateLastSlipNumber of LastSlipNumberHandler");
		if (transaction != null) {
			return transaction.reActivateLastSlipNumber(year, userId);
		}
		log.info("End of reActivateLastSlipNumber of LastSlipNumberHandler");
		return false;

	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LastSlipNumberTo getLastSlipNumberDetailsonId(int id)
			throws Exception {
		log
				.info("Start of getLastSlipNumberDetailsonId of FeeBillNumberHandler");
		if (transaction != null) {
			AttendanceSlipNumber attendanceSlipNumberBo = transaction
					.getLastSlipNumberDetailsonId(id);
			if (attendanceSlipNumberBo != null) {
				return LastSlipNumberHelper.getInstance().populateBotoTOEdit(
						attendanceSlipNumberBo);
			}
		}
		log
				.info("Exiting getLastSlipNumberDetailsonId of FeeBillNumberHandler");
		return new LastSlipNumberTo();
	}
	
	/**
	 * @param lastSlipNumberForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateLastSlipNumber(LastSlipNumberForm lastSlipNumberForm)
			throws Exception {
		log.info("Start of updateLastSlipNumber of LastSlipNumberHandler");
		LastSlipNumberTo lastSlipNumberTo = new LastSlipNumberTo();
		if (lastSlipNumberForm != null) {
			lastSlipNumberTo.setId(lastSlipNumberForm.getId());
			lastSlipNumberTo.setAcademicYear(lastSlipNumberForm
					.getAcademicYear());
			lastSlipNumberTo
					.setModifiedBy(lastSlipNumberForm.getAcademicYear());
			lastSlipNumberTo.setSlipNo(lastSlipNumberForm.getSlipNo());

		}
		if (lastSlipNumberTo != null) {
			AttendanceSlipNumber attendanceSlipNumberBo = LastSlipNumberHelper
					.getInstance().populateTotoBOUpdate(lastSlipNumberTo);
			if (transaction != null) {
				return transaction.updateLastSlipNumber(attendanceSlipNumberBo);
			}
		}
		log.info("Existing of updateLastSlipNumber of LastSlipNumberHandler");
		return false;

	}

}
