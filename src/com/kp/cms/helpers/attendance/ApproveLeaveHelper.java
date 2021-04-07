package com.kp.cms.helpers.attendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.StudentLeaveDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Singleton class for the approve leave.
 *
 */
public class ApproveLeaveHelper {
	
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	/**
	 * Represents single ton object of the ApproveLeaveHelper
	 */
	private static volatile ApproveLeaveHelper approveLeaveHelper = null;
	private static final Log log = LogFactory.getLog(ApproveLeaveHelper.class);
	private ApproveLeaveHelper() {
		// empty single instance constructor.
	}

	/**
	 * 
	 * @return Single ton instance of the ApproveLeaveHelper object.
	 */
	public static ApproveLeaveHelper getInstance() {
		if (approveLeaveHelper == null) {
			approveLeaveHelper = new ApproveLeaveHelper();
		}
		return approveLeaveHelper;
	}

	/**
	 * Returns empty string if the register no,s are valid, invalid register no,s otherwise.
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 */
	public String isRegisterNoValied(ApproveLeaveForm approveLeaveForm,
			List<String> registerNoList) throws ApplicationException {
		log.info("entering into isRegisterNoValied of ApproveLeaveHelper class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		List<Student> studentList = activityAttendenceTransaction
				.getStudentListByClassSchemeWiseId(Integer
						.valueOf(approveLeaveForm.getClassSchemewiseId()));

		Iterator<Student> studentIterator = studentList.iterator();
		HashMap<String, Student> studentRegisterNoHashMap = new HashMap<String, Student>();
		StringBuffer invalidRegisterNo = null;
		if (approveLeaveForm.isRegisterNo()) {
			while (studentIterator.hasNext()) {
				Student student = (Student) studentIterator.next();
				studentRegisterNoHashMap.put(student.getRegisterNo(), student);
			}
		} else {
			while (studentIterator.hasNext()) {
				Student student = (Student) studentIterator.next();
				studentRegisterNoHashMap.put(student.getRollNo(), student);
			}
		}

		Iterator<String> registerNoIterator = registerNoList.iterator();
		while (registerNoIterator.hasNext()) {
			String registerNo = (String) registerNoIterator.next();
			if (studentRegisterNoHashMap.get(registerNo) == null) {
				if(invalidRegisterNo == null) {
					invalidRegisterNo = new StringBuffer(registerNo);
				} else {
					invalidRegisterNo.append(',');
					invalidRegisterNo.append(registerNo);
				}
				
			}

		}
		if(invalidRegisterNo == null) {
			invalidRegisterNo = new StringBuffer();
		}
		log.info("exit of isRegisterNoValied of ApproveLeaveHelper class.");
		return invalidRegisterNo.toString();
	}

	/**
	 * Converts from BO to TO
	 * @param attendenceBoList
	 * @param approveLeaveToList
	 * @return
	 */
	public List<ApproveLeaveTO> convertBOtoTO(
			List<AttendanceStudent> attendenceBoList,
			List<ApproveLeaveTO> approveLeaveToList) {
		log.info("entering into convertBOtoTO of ApproveLeaveHelper class.");
		if (attendenceBoList != null) {
			Iterator<AttendanceStudent> attendenceIterator = attendenceBoList
					.iterator();
			while (attendenceIterator.hasNext()) {
				AttendanceStudent attendance = (AttendanceStudent) attendenceIterator
						.next();
				if(attendance.getIsPresent() == null || !attendance.getIsPresent() ) {
					ApproveLeaveTO approveLeaveTO = new ApproveLeaveTO();
					approveLeaveTO.setAttendenceId(String.valueOf(attendance
							.getId()));
					approveLeaveToList.add(approveLeaveTO);
				}
			}
		}
		log.info("exit of convertBOtoTO of ApproveLeaveHelper class.");
		return approveLeaveToList;
	}

	/**
	 * Converts from BO to TO
	 * @param registerNoList
	 * @param approveLeaveForm
	 * @param periodStartTime
	 * @param periodEndTime
	 * @return
	 * @throws ApplicationException
	 */
	public Map<String, ApproveLeaveTO> convertBOtoTO(
			ArrayList<String> registerNoList,
			ApproveLeaveForm approveLeaveForm, String periodStartTime,
			String periodEndTime) throws ApplicationException {
		log.info("entering into convertBOtoTO of ApproveLeaveHelper class.");
		Map<String, ApproveLeaveTO> approveLeaveTOMap = new LinkedHashMap<String, ApproveLeaveTO>();
		Iterator<String> registerNoIterator = registerNoList.iterator();
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date startTime;
		Date endTime;
		try {
			startTime = sdf.parse(periodStartTime);
			endTime = sdf.parse(periodEndTime);
			if (approveLeaveForm.isRegisterNo()) {
				while (registerNoIterator.hasNext()) {
					String registerNo = (String) registerNoIterator.next();
					ApproveLeaveTO approveLeaveTo = new ApproveLeaveTO();
					approveLeaveTo.setEndDate(CommonUtil
							.ConvertStringToDate(approveLeaveForm.getToDate()));
					approveLeaveTo
							.setStartDate(CommonUtil
									.ConvertStringToDate(approveLeaveForm
											.getFromDate()));
					approveLeaveTo.setRegisterNo(registerNo);
					approveLeaveTo.setStartTime(startTime);
					approveLeaveTo.setEndTime(endTime);
					approveLeaveTOMap.put(registerNo, approveLeaveTo);
				}
			} else {
				while (registerNoIterator.hasNext()) {
					String registerNo = (String) registerNoIterator.next();
					ApproveLeaveTO approveLeaveTo = new ApproveLeaveTO();
					approveLeaveTo.setEndDate(CommonUtil
							.ConvertStringToDate(approveLeaveForm.getToDate()));
					approveLeaveTo
							.setStartDate(CommonUtil
									.ConvertStringToDate(approveLeaveForm
											.getFromDate()));
					approveLeaveTo.setRollNo(registerNo);
					approveLeaveTo.setStartTime(startTime);
					approveLeaveTo.setEndTime(endTime);
					approveLeaveTOMap.put(registerNo, approveLeaveTo);
				}
			}
		} catch (ParseException e) {

			throw new ApplicationException(e);
		}
		log.info("exit of convertBOtoTO of ApproveLeaveHelper class.");
		return approveLeaveTOMap;
	}

	/**
	 * Converts from BO to TO
	 * @param list
	 * @return
	 */
	public List<StudentLeaveTO> convertStudentLeaveBOtoTO(
			List<StudentLeave> list) {
		log.info("entering into convertStudentLeaveBOtoTO of ApproveLeaveHelper class.");
		List<StudentLeaveTO> studentList = new ArrayList<StudentLeaveTO>();
		StudentLeaveTO studentLeaveTO;
		StudentLeave studentLeave;
		Iterator<StudentLeave> itr = list.iterator();
		
		while (itr.hasNext()) {
			studentLeave = itr.next();
			StringBuffer registerNo = new StringBuffer();
			studentLeaveTO = new StudentLeaveTO();
			studentLeaveTO.setId(studentLeave.getId());
			studentLeaveTO.setLeaveType(studentLeave.getLeaveType().getName());
			studentLeaveTO.setStartDate(studentLeave.getStartDate());
			studentLeaveTO.setEndDate(studentLeave.getEndDate());
			
			if(studentLeave.getStartPeriod()!=null){
				int st=Integer.parseInt(studentLeave.getStartPeriod().getStartTime().substring(0,2));
				int st1=Integer.parseInt(studentLeave.getStartPeriod().getEndTime().substring(0,2));
				if(st<=12){
					studentLeaveTO.setStartPeriod(studentLeave.getStartPeriod().getPeriodName()+"("+studentLeave.getStartPeriod().getStartTime().substring(0,5)+"-"+studentLeave.getStartPeriod().getEndTime().substring(0,5)+")");
				}else{
					studentLeaveTO.setStartPeriod(studentLeave.getStartPeriod().getPeriodName()+"("+pMap.get(st)+studentLeave.getStartPeriod().getStartTime().substring(2,5)+"-"+pMap.get(st1)+studentLeave.getStartPeriod().getEndTime().substring(2,5)+")");
				}
			}
			if(studentLeave.getEndPeriod()!=null){
				int et=Integer.parseInt(studentLeave.getEndPeriod().getStartTime().substring(0,2));
				int et1=Integer.parseInt(studentLeave.getEndPeriod().getEndTime().substring(0,2));
				if(et<=12){
					studentLeaveTO.setEndPeriod(studentLeave.getEndPeriod().getPeriodName()+"("+studentLeave.getEndPeriod().getStartTime().substring(0,5)+"-"+studentLeave.getEndPeriod().getEndTime().substring(0,5)+")");
				}else{
					studentLeaveTO.setEndPeriod(studentLeave.getEndPeriod().getPeriodName()+"("+pMap.get(et)+studentLeave.getEndPeriod().getStartTime().substring(2,5)+"-"+pMap.get(et1)+studentLeave.getStartPeriod().getEndTime().substring(2,5)+")");
				}
			}
//			studentLeaveTO.setStartPeriod(studentLeave.getStartPeriod()
//					.getPeriodName());
//			studentLeaveTO.setEndPeriod(studentLeave.getEndPeriod()
//					.getPeriodName());
			Iterator<StudentLeaveDetails> itr1 = studentLeave.getStudentLeaveDetails().iterator();
			boolean isRegCheck = studentLeave.getClassSchemewise().getClasses().getCourse().getProgram().getIsRegistrationNo();
			while(itr1.hasNext()){
				if(isRegCheck){
					if(registerNo.length()>0) {
						registerNo.append(',');
					}
					registerNo.append(itr1.next().getStudent().getRegisterNo());
					
				} else {
					if(registerNo.length()>0) {
						registerNo.append(',');
					}
					registerNo.append(itr1.next().getStudent().getRollNo());
					
				}	
			}
			if(registerNo != null && registerNo.length()>0) {
				registerNo.substring(0,registerNo.length()-1);
			}
		
			
			studentLeaveTO.setRollOrRegNos(registerNo.toString());
			studentList.add(studentLeaveTO);
		}
		log.info("exit of convertStudentLeaveBOtoTO of ApproveLeaveHelper class.");
		return studentList;
	}

	/**
	 * Copy data from BO to ApproveLeaveForm
	 * @param approveLeaveForm
	 * @param studentLeave
	 * @throws Exception
	 */
	public void coypBoToForm(ApproveLeaveForm approveLeaveForm,
			StudentLeave studentLeave) throws Exception {
		log.info("entering into coypBoToForm of ApproveLeaveHelper class.");
		approveLeaveForm.setId(String.valueOf(studentLeave.getId()));
		approveLeaveForm.setClassSchemewiseId(String.valueOf(studentLeave
				.getClassSchemewise().getId()));
		approveLeaveForm.setLeaveType(String.valueOf(studentLeave
				.getLeaveType().getId()));
		approveLeaveForm.setFromDate(CommonUtil.formatDate(studentLeave
				.getStartDate(), "dd/MM/yyyy"));
		approveLeaveForm.setToDate(CommonUtil.formatDate(studentLeave
				.getEndDate(), "dd/MM/yyyy"));
		approveLeaveForm.setFromPeriod(String.valueOf(studentLeave
				.getStartPeriod().getId()));
		approveLeaveForm.setToPeriod(String.valueOf(studentLeave.getEndPeriod()
				.getId()));

		Iterator<StudentLeaveDetails> itr = studentLeave
				.getStudentLeaveDetails().iterator();
		StudentLeaveDetails studentLeaveDetails;
		StringBuffer registerNos = new StringBuffer();
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		boolean isRegnoCheck = activityAttendenceTransaction
				.checkIsRegisterNo(studentLeave.getClassSchemewise().getId());
		while (itr.hasNext()) {
			studentLeaveDetails = itr.next();
			if (isRegnoCheck) {
				registerNos.append(studentLeaveDetails.getStudent().getRegisterNo());
				registerNos.append(',');
				
			} else {
				registerNos.append(studentLeaveDetails.getStudent().getRollNo());
				registerNos.append(',');
			}	
		}
		

		approveLeaveForm.setRegisterNoEntry(registerNos.substring(0, (registerNos.length() - 1)));

		approveLeaveForm.setOldRegisterNoEntry(registerNos.substring(0, (registerNos.length() - 1)));
		StudentLeaveTO studentLeaveTO = new StudentLeaveTO();
		studentLeaveTO.setStartDate(studentLeave.getStartDate());
		studentLeaveTO.setStDate(CommonUtil.formatDate(studentLeave
				.getStartDate(), "dd/MM/yyyy"));
		studentLeaveTO.setEdDate(CommonUtil.formatDate(studentLeave
				.getEndDate(), "dd/MM/yyyy"));
		studentLeaveTO.setLeaveType(String.valueOf(studentLeave
				.getLeaveType().getId()));
		studentLeaveTO.setEndDate(studentLeave.getEndDate());
		studentLeaveTO.setStartPeriod(approveLeaveForm.getFromPeriod());
		studentLeaveTO.setEndPeriod(approveLeaveForm.getToPeriod());
		studentLeaveTO.setClassSchemewiseId(studentLeave.getClassSchemewise()
				.getId());
		approveLeaveForm.setStudentLeaveTO(studentLeaveTO);
		log.info("exit of coypBoToForm of ApproveLeaveHelper class.");
	}
}