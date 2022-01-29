package com.kp.cms.handlers.hostel;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.helpers.hostel.UploadHostelStudentsHelper;
import com.kp.cms.to.admin.RoomTO;
import com.kp.cms.transactions.hostel.IUploadHostelStudentsTransaction;
import com.kp.cms.transactionsimpl.hostel.UploadHostelStudentsTransactionImpl;

public class UploadHostelStudentsHandler {
	/**
	 * Singleton object of UploadHostelStudentsHandler
	 */
	IUploadHostelStudentsTransaction transaction=new UploadHostelStudentsTransactionImpl();
	private static volatile UploadHostelStudentsHandler uploadHostelStudentsHandler = null;
	private static final Log log = LogFactory.getLog(UploadHostelStudentsHandler.class);
	private UploadHostelStudentsHandler() {
		
	}
	/**
	 * return singleton object of uploadHostelStudentsHandler.
	 * @return
	 */
	public static UploadHostelStudentsHandler getInstance() {
		if (uploadHostelStudentsHandler == null) {
			uploadHostelStudentsHandler = new UploadHostelStudentsHandler();
		}
		return uploadHostelStudentsHandler;
	}
	/**
	 * @param applicationYear
	 * @param applnReg
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getAppDetails(int applicationYear, String applnReg) throws Exception {
		String query =UploadHostelStudentsHelper.getInstance().getAdmQuery(applicationYear,applnReg);
		return transaction.getAdmMap(query);
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Integer> getMaxCountForRoomType(String hostelId) throws Exception {
		String query =UploadHostelStudentsHelper.getInstance().getMaxCountForRoomType(hostelId);
		return transaction.getMaxCountForRoomType(query);
	}
	/**
	 * @param hostelId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getRoomTypeForHostel(String hostelId) throws Exception {
		String query =UploadHostelStudentsHelper.getInstance().getRoomTypeForHostel(hostelId);
		return transaction.getAdmMap(query);
	}
	/**
	 * @param results
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<HlRoomTransaction> results) throws Exception {
		return transaction.addUploadData(results);
	}
	
}
