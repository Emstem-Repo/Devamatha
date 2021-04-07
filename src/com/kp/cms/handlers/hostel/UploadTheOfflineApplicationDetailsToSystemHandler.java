package com.kp.cms.handlers.hostel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.forms.hostel.UploadTheOfflineApplicationDetailsToSystemForm;
import com.kp.cms.helpers.hostel.UploadTheOfflineApplicationDetailsToSystemHelper;
import com.kp.cms.to.hostel.UploadTheOfflineApplicationDetailsToSystemTo;
import com.kp.cms.transactions.hostel.IUploadOfflineAppliDetailsTransaction;
import com.kp.cms.transactionsimpl.hostel.UploadTheOfflineApplicationDetailsToSystemTransactionImpl;

public class UploadTheOfflineApplicationDetailsToSystemHandler {
	private static final Log log = LogFactory.getLog(UploadTheOfflineApplicationDetailsToSystemHandler.class);
	private static volatile UploadTheOfflineApplicationDetailsToSystemHandler offlineAppDetailsHandler = null;
	
	private UploadTheOfflineApplicationDetailsToSystemHandler() {
	}
	
	/**
	 * @return
	 */
	public static UploadTheOfflineApplicationDetailsToSystemHandler getInstance() {
		if (offlineAppDetailsHandler == null) {
			offlineAppDetailsHandler = new UploadTheOfflineApplicationDetailsToSystemHandler();
		}
		return offlineAppDetailsHandler;
	}
	IUploadOfflineAppliDetailsTransaction transaction=UploadTheOfflineApplicationDetailsToSystemTransactionImpl.getInstance();
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getHlRoomType(int hostelId)throws Exception{
		Map<String,Integer> map=new HashMap<String,Integer>();
		map=transaction.getRoomType(hostelId);
		return map;
	}
	
	/**
	 * @param results
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public boolean uploadOfflineApplication(List<UploadTheOfflineApplicationDetailsToSystemTo> results,UploadTheOfflineApplicationDetailsToSystemForm objform)throws Exception {
		Map<Integer,HostelOnlineApplication> boList=UploadTheOfflineApplicationDetailsToSystemHelper.getInstance().covertToToBo(results,objform);
		boolean issaved=transaction.saveUploadOfflineApplication(boList,objform);
		return issaved;
	}
	
	
	/**
	 * @param regNumList
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer>  getStudentIdByStudentRegNum(List<String> regNumList)throws Exception {
		return transaction.getStudentIdByStudentRegNum(regNumList);
	}
	
	
}
