package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.attendance.AssignClassToTeacherForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;

public class AssignClassToTeacherHelper {
	public static AssignClassToTeacherHelper assignClassToTeacherHelper=null;
	static{
		assignClassToTeacherHelper=new AssignClassToTeacherHelper();
	}
	public static AssignClassToTeacherHelper getInstance(){
		return assignClassToTeacherHelper;
	}
	/**
	 * @param assignClassTeacherForm
	 * @return
	 */
	public String getDuplicateEntryQuery(
			AssignClassToTeacherForm assignClassTeacherForm) {
		String query1="from TeacherClass teacherClass where teacherClass.isActive=1 and teacherClass.teacherId.isActive=1 and teacherClass.teacherId.id='"
			+assignClassTeacherForm.getTeachers()+"' and teacherClass.teacherType='"+assignClassTeacherForm.getTeacherType()+"' and teacherClass.classId.id='"+assignClassTeacherForm.getClassesSelected()+"'";
		return query1;
	}
	/**
	 * @param teacherClass
	 * @return
	 * @throws Exception
	 */
	public List<TeacherClassEntryTo> ConvertBOToTO(List<TeacherClass> teacherClass) throws Exception{
		ArrayList<TeacherClassEntryTo> list=new ArrayList<TeacherClassEntryTo>();
		Iterator<TeacherClass> it=teacherClass.iterator();
		while (it.hasNext()) {
			TeacherClass teacherClassBO = (TeacherClass) it.next();
			TeacherClassEntryTo teTo=new TeacherClassEntryTo();
			if(teacherClassBO!=null)
			{
			teTo.setId(teacherClassBO.getId());
			if(teacherClassBO.getClassId() != null && teacherClassBO.getClassId().getClasses() != null && teacherClassBO.getClassId().getClasses().getName() != null){
				teTo.setClassName(teacherClassBO.getClassId().getClasses().getName());
			}
			if(teacherClassBO.getTeacherId() != null && teacherClassBO.getTeacherId().getEmployee() != null && teacherClassBO.getTeacherId().getEmployee().getFirstName() != null){
				teTo.setTeacherName(teacherClassBO.getTeacherId().getEmployee().getFirstName());
			}else if(teacherClassBO.getTeacherId() != null && teacherClassBO.getTeacherId().getUserName() != null)
			{
			
				teTo.setTeacherName(teacherClassBO.getTeacherId().getUserName().toUpperCase());
			}
			if(teacherClassBO.getTeacherType()!= null && !teacherClassBO.getTeacherType().isEmpty()){
				teTo.setTeacherType(teacherClassBO.getTeacherType());
			}
			
			list.add(teTo);
			}
		}
		return list;
	}
	public void setDepartmentDetails(AssignClassToTeacherForm aTeacherForm,TeacherClass teacherClass) throws Exception{
		if(teacherClass!=null)
		{
			aTeacherForm.setTeachers(String.valueOf(teacherClass.getTeacherId().getId()));
			aTeacherForm.setYear(String.valueOf(teacherClass.getClassId().getCurriculumSchemeDuration().getAcademicYear()));
			aTeacherForm.setClassesSelected(String.valueOf(teacherClass.getClassId().getId()));
			if(teacherClass.getTeacherType()!= null && !teacherClass.getTeacherType().isEmpty()){
				aTeacherForm.setTeacherType(teacherClass.getTeacherType());
			}
			
		}
	
	}
	public TeacherClass convertFormToBo(AssignClassToTeacherForm assignClassTeacherForm, TeacherClass teacherClass) throws Exception{
		
		Users user = new Users();
		user.setId(Integer.parseInt(assignClassTeacherForm.getTeachers()));
		teacherClass.setTeacherId(user);
		ClassSchemewise classSchemewise = new ClassSchemewise();
		classSchemewise.setId(Integer.parseInt(assignClassTeacherForm.getClassesSelected()));
    	teacherClass.setClassId(classSchemewise);
    	teacherClass.setTeacherType(assignClassTeacherForm.getTeacherType());
    	//teacherClass.setAcademicYear(assignClassTeacherForm.getAcademicYear());
    	
    	return teacherClass;
    }
    
	
	
}
