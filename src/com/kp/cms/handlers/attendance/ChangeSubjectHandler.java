package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.helpers.attendance.ChangeSubjectHelper;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.transactions.attandance.IChangeSubject;
import com.kp.cms.transactionsimpl.attendance.ChangeSubjectTransactionImpl;
import com.kp.cms.utilities.CommonUtil;





public class ChangeSubjectHandler {
	private static volatile ChangeSubjectHandler changeSubjectHandler = null;
	private static final Log log = LogFactory.getLog(ChangeSubjectHandler.class);
	
	/**
	 * @return
	 */
	public static ChangeSubjectHandler getInstance() {
		if (changeSubjectHandler == null) {
			changeSubjectHandler = new ChangeSubjectHandler();
		}
		return changeSubjectHandler;
	}
	
	 /**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public boolean  getSubjectName(ChangeSubjectForm form) throws Exception{
			boolean isvalidSubjectId=false;
			IChangeSubject transaction=ChangeSubjectTransactionImpl.getInstance();
			String subjectName=transaction.subjectNameBySubjectCode(form);
			if(subjectName!=null && !subjectName.isEmpty()){
				form.setSubjectName(subjectName);
				isvalidSubjectId=true;
			}
			
			return isvalidSubjectId;
		}

	
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTO>  getClassBySubjectId(ChangeSubjectForm form) throws Exception{
		IChangeSubject transaction=ChangeSubjectTransactionImpl.getInstance();
		List<Attendance> boList=transaction.getClassBySubjectId(form);
		List<AttendanceTO> tos = ChangeSubjectHelper.getInstance().convertBOtoTO(boList,form);
		return tos;
	}
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public boolean saveSelectedSubjectData(List<Integer> idList,ChangeSubjectForm form) throws Exception{
		boolean isSaved=false;
		IChangeSubject transaction=ChangeSubjectTransactionImpl.getInstance();
		  List<Attendance> boList=transaction.SelectedSubjectList(idList);
		  isSaved=transaction.saveSubjectId(boList,form);
		return isSaved;
	}
}
