package com.kp.cms.handlers.attendance;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.forms.attendance.CopyClassTeacherForm;
import com.kp.cms.helpers.attendance.CopyClassTeacherHelper;
import com.kp.cms.transactions.attandance.ICopyClassTeacherTransaction;
import com.kp.cms.transactionsimpl.attendance.CopyClassTeacherTransactionImpl;

public class copyClassTeacherHandler {
	private static Log log = LogFactory.getLog(copyClassTeacherHandler.class);
	private static volatile copyClassTeacherHandler copyClassTeacherHandler = null;
	public static copyClassTeacherHandler getInstance(){
		if(copyClassTeacherHandler == null){
			copyClassTeacherHandler = new copyClassTeacherHandler();
			return copyClassTeacherHandler;
		}
		return copyClassTeacherHandler;
	}
	
	/**
	 * @param copyClassTeacherForm
	 * @return
	 * @throws Exception
	 */
	public boolean copyTeachers(CopyClassTeacherForm copyClassTeacherForm) throws Exception {
		boolean IsAdded=false;
		
		ICopyClassTeacherTransaction txImpl=CopyClassTeacherTransactionImpl.getInstance();
		List<TeacherClass> getFromTeacherClasses=txImpl.getTeachers(copyClassTeacherForm);
		Map<String, Integer> toyearclasses=txImpl.getToYearClasses(copyClassTeacherForm);
		List<TeacherClass> teacher=CopyClassTeacherHelper.getInstance().convertCopyClasses(toyearclasses,getFromTeacherClasses,copyClassTeacherForm);
		IsAdded=txImpl.copyTeachers(teacher);
		 log.info("end of copyTeachers method in copyClassTeacherHandler class.");
		return IsAdded;
	}

}
