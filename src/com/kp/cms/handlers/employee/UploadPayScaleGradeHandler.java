package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.transactions.employee.IUploadPayScaleGradeTransaction;
import com.kp.cms.transactionsimpl.employee.UploadPayScaleGradeTransactionimpl;

public class UploadPayScaleGradeHandler {
	/**
	 * Singleton object of UploadPayScaleGradeHandler
	 */
	private static volatile UploadPayScaleGradeHandler uploadPayScaleGradeHandler = null;
	private static final Log log = LogFactory.getLog(UploadPayScaleGradeHandler.class);
	private UploadPayScaleGradeHandler() {
		
	}
	/**
	 * return singleton object of UploadPayScaleGradeHandler.
	 * @return
	 */
	public static UploadPayScaleGradeHandler getInstance() {
		if (uploadPayScaleGradeHandler == null) {
			uploadPayScaleGradeHandler = new UploadPayScaleGradeHandler();
		}
		return uploadPayScaleGradeHandler;
	}
	IUploadPayScaleGradeTransaction txn=UploadPayScaleGradeTransactionimpl.getInstance();
	/**
	 * getting the map of empFingerPrintID and empId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getEmpDetails() throws Exception {
		Map<String, Integer> empMap=txn.getEmpMap();
		return empMap;
	}
	/**
	 * getting payscaleBo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getPayScaleBo() throws Exception{
		Map<String, Integer> payScale=txn.getPayScale();
		return payScale;
	}
	/**
	 * getting the map of allowance name and id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getAllowanceDetails() throws Exception {
		Map<String, Integer> allowanceMap=txn.getAllowanceDetails();
		return allowanceMap;
	}
	/**
	 * returns map with key as payScale name and value as Scale
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPayScaleMap() throws Exception{
		Map<String, String> payScaleMap=txn.getPayScaleMap();
		return payScaleMap;
	}
	/**
	 * adding the uploaded data
	 * @param empBoList
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(List<Employee> empBoList, String user) throws Exception {
		boolean isAdded=false;
		isAdded=txn.addUploadedData(empBoList,user);
		return isAdded;
	}
}
