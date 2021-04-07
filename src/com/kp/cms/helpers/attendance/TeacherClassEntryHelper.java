package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class TeacherClassEntryHelper {
	private static final Log log = LogFactory
			.getLog(TeacherClassEntryHelper.class);
	public static volatile TeacherClassEntryHelper objHelper = null;

	public static TeacherClassEntryHelper getInstance() {
		if (objHelper == null) {
			objHelper = new TeacherClassEntryHelper();
			return objHelper;
		}
		return objHelper;
	}

	public List<TeacherClassEntryTo> convertBOToTo(List<Object> listOfTeachers, List<Object> listOfTeachers2, List<Object> listOfTeachers3) {
		ArrayList<TeacherClassEntryTo> list = new ArrayList<TeacherClassEntryTo>();
		Iterator itr = listOfTeachers.iterator();
		TeacherClassEntryTo objTo = null;
		while (itr.hasNext()) {
			objTo = new TeacherClassEntryTo();
			Object[] object = (Object[]) itr.next();
			if (object[0] != null) {
				objTo.setId(Integer.parseInt(object[0].toString()));
			}
			if (object[1] != null) {
				objTo.setAcademicYear((Integer.parseInt(object[1].toString())));
			}
			if (object[2] != null && object[8]!=null) {
				objTo.setSubjectName(object[2].toString()+"("+object[8].toString()+")");
			}
			if (object[3] != null) {
				objTo.setClassName(object[3].toString());
			}
			if (object[4] != null) {
				objTo.setTeacherName(object[4].toString()+"("+object[9].toString()+")");
			}
			if (object[7] != null) {
				objTo.setNumericCode(object[7].toString());
			}
			list.add(objTo);
		}
		Iterator itr1= listOfTeachers2.iterator();
		TeacherClassEntryTo objTo1 = null;
		while (itr1.hasNext()) {
			objTo1 = new TeacherClassEntryTo();
			Object[] object1 = (Object[]) itr1.next();
			if (object1[0] != null) {
				objTo1.setId(Integer.parseInt(object1[0].toString()));
			}
			if (object1[1] != null) {
				objTo1.setAcademicYear(Integer.parseInt(object1[1].toString()));
			}
			if (object1[2] != null && object1[6]!=null) {
				objTo1.setSubjectName(object1[2].toString()+"("+object1[6].toString()+")");
			}
			if (object1[3] != null) {
				objTo1.setClassName(object1[3].toString());
			}
			if (object1[4] != null) {
				objTo1.setTeacherName(object1[4].toString()+"("+object1[7].toString()+")");
			}
			if (object1[5] != null) {
				objTo1.setNumericCode(object1[5].toString());
			}
			list.add(objTo1);
		}
		Iterator itr2= listOfTeachers3.iterator();
		TeacherClassEntryTo objTo2 = null;
		while (itr2.hasNext()) {
			objTo2 = new TeacherClassEntryTo();
			Object[] object2 = (Object[]) itr2.next();
			if (object2[0] != null) {
				objTo2.setId(Integer.parseInt(object2[0].toString()));
			}
			if (object2[1] != null) {
				objTo2.setAcademicYear(Integer.parseInt(object2[1].toString()));
			}
			if (object2[2] != null && object2[6]!=null) {
				objTo2.setSubjectName(object2[2].toString()+"("+object2[6].toString()+")");
			}
			if (object2[3] != null) {
				objTo2.setClassName(object2[3].toString());
			}
			if (object2[4] != null) {
				objTo2.setTeacherName(object2[4].toString()+"("+object2[7].toString()+")");
			}
			if (object2[5] != null) {
				objTo2.setNumericCode(object2[5].toString());
			}
			list.add(objTo2);
		}
		return list;
	}

	public String getDuplicateCheckQuery(TeacherClassEntryForm teacherForm) throws Exception{
		String query="from TeacherClassSubject t where t.isActive=1 and t.teacherId.isActive=1 and t.numericCode='"+teacherForm.getNumericCode()+"'";
		return query;
	}

	public String getDuplicateEntryQuery(TeacherClassEntryForm teacherForm) throws Exception {
		String query1="from TeacherClassSubject tcs where tcs.isActive=1 and tcs.teacherId.isActive=1  and tcs.subject='"
			           +teacherForm.getSubjectId()+"' and tcs.teacherId='"+teacherForm.getTeachers()+"' and tcs.classId.id in (:classIds)";
		return query1;
	}

	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTeacherDepartmentsName(List<Object[]> list)throws Exception {
		Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
		if(list!=null){
			Iterator<Object[]> iterator=list.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				TeacherClassEntryTo teacherClassEntryTo=new TeacherClassEntryTo();
				teacherClassEntryTo.setTeacherId(Integer.parseInt(objects[0].toString()));
				int userIdEmpl=(Integer.parseInt(objects[0].toString()));
				if(objects[1] !=null && !objects[1].toString().isEmpty())
				{
					teacherClassEntryTo.setDepartmentName(objects[1].toString());
				}
				else
				{
					ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
					List<Object[]> objectList=transaction.getTeacherDepartmentsNameFromUser(userIdEmpl);
					TeacherDepartmentTO toUser=new TeacherDepartmentTO();
					 Iterator<Object[]> itrUser=objectList.iterator();
						while(itrUser.hasNext()){
							Object[] UDepartment=(Object[])itrUser.next();
										
							toUser.setId(Integer.parseInt(UDepartment[0].toString()));
							if (UDepartment[1] != null ) 
								{
									if(!UDepartment[1].toString().isEmpty())
									{
										toUser.setDepartmentName(UDepartment[1].toString());
									}
								}
							else
							{	//Added by Dilip
								ITeacherDepartmentEntryTransaction transaction1=new TeacherDepartmentEntryTransactionImpl();
								List<Object[]> objectList1=transaction1.getTeacherDepartmentsNameFromGuest(userIdEmpl);
								TeacherDepartmentTO toUser1=new TeacherDepartmentTO();
								 Iterator<Object[]> itrUser1=objectList1.iterator();
									while(itrUser1.hasNext()){
										Object[] UDepartment1=(Object[])itrUser1.next();
										toUser1.setId(Integer.parseInt(UDepartment1[0].toString()));
										if (UDepartment1[1] != null )
										{
											toUser1.setDepartmentName(UDepartment1[1].toString());
										}
										else
										{
												toUser1.setDepartmentName("-");
										}
									}
									teacherClassEntryTo.setDepartmentName(toUser1.getDepartmentName());	
									break;
							}
							teacherClassEntryTo.setDepartmentName(toUser.getDepartmentName());	
						}
				}
				teacherClassEntryTo.setTeacherName(objects[2].toString());
				teacherClassEntryTo.setDeptTeacherName(teacherClassEntryTo.getTeacherName() .concat("  ("+teacherClassEntryTo.getDepartmentName()+")"));
				teacherMap.put(teacherClassEntryTo.getTeacherId(), teacherClassEntryTo.getDeptTeacherName());
			}
		}
		teacherMap = (Map<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
		return teacherMap;
	}
}
