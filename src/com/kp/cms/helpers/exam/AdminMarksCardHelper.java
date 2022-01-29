package com.kp.cms.helpers.exam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.AdminMarksCardForm;

public class AdminMarksCardHelper {
	/**
	 * Singleton object of AdminMarksCardHelper
	 */
	private static volatile AdminMarksCardHelper adminMarksCardHelper = null;
	private static final Log log = LogFactory.getLog(AdminMarksCardHelper.class);
	private AdminMarksCardHelper() {
		
	}
	/**
	 * return singleton object of AdminMarksCardHelper.
	 * @return
	 */
	public static AdminMarksCardHelper getInstance() {
		if (adminMarksCardHelper == null) {
			adminMarksCardHelper = new AdminMarksCardHelper();
		}
		return adminMarksCardHelper;
	}
	/**
	 * @param classId
	 * @return
	 */
	public String getCurrentClassStudentsQuery(AdminMarksCardForm adminMarksCardForm) throws Exception {
		String query="select s.id from Student s where (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 and s.classSchemewise.classes.id="+adminMarksCardForm.getClassId(); 
		if(adminMarksCardForm.getRegNoFrom()!=null && !adminMarksCardForm.getRegNoFrom().isEmpty()){
			query=query+" and s.registerNo>='"+adminMarksCardForm.getRegNoFrom()+"'";
		}
		if(adminMarksCardForm.getRegNoTo()!=null && !adminMarksCardForm.getRegNoTo().isEmpty()){
			query=query+" and s.registerNo<='"+adminMarksCardForm.getRegNoTo()+"'";
		}
		query=query+" order by s.registerNo";
		return query;
	}
	/**
	 * @param adminMarksCardForm
	 * @return
	 */
	public String getPreviousClassStudentsQuery( AdminMarksCardForm adminMarksCardForm)  throws Exception{
		String query="select s.student.id from StudentPreviousClassHistory s where (s.student.isHide = 0 or s.student.isHide is null) and s.student.admAppln.isCancelled=0" +
				" and s.classes.id="+adminMarksCardForm.getClassId();
		if(adminMarksCardForm.getRegNoFrom()!=null && !adminMarksCardForm.getRegNoFrom().isEmpty()){
			query=query+" and s.student.registerNo>='"+adminMarksCardForm.getRegNoFrom()+"'";
		}
		if(adminMarksCardForm.getRegNoTo()!=null && !adminMarksCardForm.getRegNoTo().isEmpty()){
			query=query+" and s.student.registerNo<='"+adminMarksCardForm.getRegNoTo()+"'";
		}
		query=query+" order by s.student.registerNo";
		return query;
	}
	public String getCurrentClassStudentsYearQuery(int studentId) throws Exception {
		String query="select s.admAppln.appliedYear from Student s where (s.isHide = 0 or s.isHide is null) and s.admAppln.isCancelled=0 and s.id= "+studentId; 
		
		return query;
	}
}
