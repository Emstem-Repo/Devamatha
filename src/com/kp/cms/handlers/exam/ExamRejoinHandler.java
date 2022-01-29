package com.kp.cms.handlers.exam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.kp.cms.bo.exam.ExamRejoinBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.helpers.exam.ExamRejoinHelper;
import com.kp.cms.transactionsimpl.exam.ExamRejoinImpl;
import com.kp.cms.transactionsimpl.exam.ExamRevaluationApplicationImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Dec 30, 2009 Created By 9Elements
 */
public class ExamRejoinHandler extends ExamGenHandler {
	ExamRejoinImpl impl = new ExamRejoinImpl();
	ExamRejoinHelper helper = new ExamRejoinHelper();

	public Map<Integer, String> getClasesByJoingBatch(String joiningBatch) {
		return helper.convertBOToTO_Classes_HashMap(impl
				.selectClassesByJoingBatch(Integer.parseInt(joiningBatch)));
	}

	public void add(String oldRegNumber, String oldRollNumber,
			String newRegNumber, String newRollNumber, String joiningBatch,
			int readmittedClass, String joiningDate, String userId)
			throws Exception {
		boolean oldRegPresent = false;
		boolean oldRollPresent = false;
		String value = "";

		if (oldRollNumber != null && oldRollNumber.length() > 0) {
			oldRollPresent = true;
			value = oldRollNumber;
		}
		if (oldRegNumber != null && oldRegNumber.length() > 0) {
			oldRegPresent = true;
			value = oldRegNumber;
		}

		if (oldRegPresent && oldRollPresent) {
			if (!validate_Stu_rollReg(oldRollNumber, oldRegNumber)) {
				throw new BusinessException();
			}
		}

		StudentUtilBO s;
		if (oldRollPresent) {
			s = impl.select_student_Only(oldRollNumber, oldRegNumber,
					oldRollPresent);

		} else {
			s = impl.select_student_Only(oldRollNumber, oldRegNumber,
					oldRollPresent);
		}
		int studentId = s.getId();
		String rollNo = s.getRollNo();
		String regNo = s.getRegisterNo();

		if (studentId != 0) {
			impl.isDuplicated(newRegNumber, newRollNumber, oldRollNumber,
					oldRegNumber);
			ExamRejoinBO r = new ExamRejoinBO(readmittedClass, newRegNumber,
					newRollNumber, regNo, rollNo, CommonUtil
							.ConvertsqlStringToDate(joiningDate), "",
					studentId, userId);

			impl.insert(r);
		}

	}
}
