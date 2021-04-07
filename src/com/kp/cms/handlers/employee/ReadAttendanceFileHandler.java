package com.kp.cms.handlers.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.actions.employee.ReadAttendanceFileAction;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpBiometricLogSetupBO;
import com.kp.cms.bo.admin.EmpReadAttendanceFileBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.ReadAttendanceFileForm;
import com.kp.cms.helpers.employee.ReadAttendanceFileHelper;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.to.admin.StudentUploadPhotoTO;
import com.kp.cms.to.employee.AttendanceFileTO;
import com.kp.cms.to.employee.EmpAttributeTO;
import com.kp.cms.transactions.employee.IBiometricLogSetupTransaction;
import com.kp.cms.transactions.employee.IReadAttendanceFileTransaction;
import com.kp.cms.transactionsimpl.employee.BiometricLogSetupTransactionImpl;
import com.kp.cms.transactionsimpl.employee.ReadAttendanceFileTransactionImpl;

public class ReadAttendanceFileHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ReadAttendanceFileHandler.class);
	public static volatile ReadAttendanceFileHandler objHandler = null;

	public static ReadAttendanceFileHandler getInstance() {
		if (objHandler == null) {
			objHandler = new ReadAttendanceFileHandler();
			return objHandler;
		}
		return objHandler;
	}

	public HashMap<String, String> getBioMetricDetails() throws Exception {
		IBiometricLogSetupTransaction iTransaction = BiometricLogSetupTransactionImpl
				.getInstance();
		EmpBiometricLogSetupBO BO = iTransaction.getBiometricLogDetails().get(0);
		HashMap<String, String> map = new HashMap<String, String>();
		if (BO != null) {
			if (BO.getTerminalId() != null && !BO.getTerminalId().isEmpty()) {
				map.put("terminalId", BO.getTerminalId());
			}
			if (BO.getFingerPrintId() != null
					&& !BO.getFingerPrintId().isEmpty()) {
				map.put("fingerPrintId", BO.getFingerPrintId());
			}
			if (BO.getEmployeeCode() != null && !BO.getEmployeeCode().isEmpty()) {
				map.put("employeeCode", BO.getEmployeeCode());
			}
			if (BO.getEmployeeName() != null && !BO.getEmployeeName().isEmpty()) {
				map.put("employeeName", BO.getEmployeeName());
			}
			if (BO.getDatetime() != null && !BO.getDatetime().isEmpty()) {
				map.put("dateTime", BO.getDatetime());
			}
			if (BO.getFunctionkey() != null && !BO.getFunctionkey().isEmpty()) {
				map.put("functionkey", BO.getFunctionkey());
			}
			if (BO.getStatus() != null && !BO.getStatus().isEmpty()) {
				map.put("status", BO.getStatus());
			}
			if (BO.getDelimitedWith() != null
					&& !BO.getDelimitedWith().isEmpty()) {
				map.put("delimitedWith", BO.getDelimitedWith());
			}
			if (BO.getDateFormat() != null) {

				map.put("dateFormat", BO.getDateFormat());
			}
			if(BO.getTestCode()!=null)
			{
				map.put("testCode", BO.getTestCode());
			}
			if(BO.getTextFilePath()!=null)
			{
				map.put("textFilePath", BO.getTextFilePath());
			}
		}

		return map;
	}

	public boolean readAttandanceFile(ReadAttendanceFileForm formObj) throws Exception
	{
		List<EmpAttendanceTo> attendanceTOList=new ArrayList<EmpAttendanceTo>();
		Map<String, Integer> codeIdMap=new HashMap<String, Integer>();
		IReadAttendanceFileTransaction trans=new ReadAttendanceFileTransactionImpl();
		ReadAttendanceFileHelper  helper=ReadAttendanceFileHelper.getInstance();
		HashMap<String, String> fileFormat = getBioMetricDetails();
		Properties prop = new Properties();
		InputStream in = ReadAttendanceFileAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        boolean isAdded=false;
        helper.getAttendanceList(attendanceTOList,formObj.getThefile(),codeIdMap,fileFormat);
        List<EmpAttendance> empAttendance=helper.convertTotoBo(attendanceTOList,formObj,fileFormat.get("dateFormat"));
        if(empAttendance.size()!=0)
          isAdded=trans.addAttendance(empAttendance);
         if(isAdded)
         {	
         //   	helper.copyDirectory(f,new File(destPath));
         }
        return isAdded;
	}

	public EmpAttendanceTo getEmployeeAttendanceForDate(int employeeId,	String attendanceDate) throws Exception
	{
		IReadAttendanceFileTransaction trans=new ReadAttendanceFileTransactionImpl();
		ReadAttendanceFileHelper  helper=ReadAttendanceFileHelper.getInstance();
		EmpAttendance empAttendance=trans.getEmployeeAttendanceForDate(employeeId,attendanceDate);
		EmpAttendanceTo empAttendanceTo=helper.convertBotoTo(empAttendance);
		return empAttendanceTo;
	}
	
	public boolean CheckDuplicate(int employeeId,String attendanceDate)throws Exception
	{
		boolean isDuplicate=false;
		IReadAttendanceFileTransaction trans=new ReadAttendanceFileTransactionImpl();
		EmpAttendance empAttendance=trans.getEmployeeAttendanceForDate(employeeId,attendanceDate);
		if(empAttendance!=null)
		 isDuplicate=true;
		return isDuplicate;
	}
	
	
}
