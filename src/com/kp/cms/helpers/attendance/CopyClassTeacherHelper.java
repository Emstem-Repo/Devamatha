package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.forms.attendance.CopyClassTeacherForm;

public class CopyClassTeacherHelper {
	private static Log log = LogFactory.getLog(CopyClassTeacherHelper.class);
	private static volatile CopyClassTeacherHelper copyClassTeacherHelper = null;
	public static CopyClassTeacherHelper getInstance(){
		if(copyClassTeacherHelper == null){
			copyClassTeacherHelper = new CopyClassTeacherHelper();
			return copyClassTeacherHelper;
		}
		return copyClassTeacherHelper;
	}
	
	public List<TeacherClass> convertCopyClasses(Map<String, Integer> map,List<TeacherClass> teacherList,CopyClassTeacherForm copyClassTeacherForm) throws Exception {
		List<TeacherClass> teacherlist=new ArrayList();
		if(teacherList !=null){
			Iterator<TeacherClass> iterator = teacherList.iterator();
			while (iterator.hasNext()) {
				TeacherClass teacherClas = (TeacherClass) iterator.next();
				if(map.containsKey(teacherClas.getClassId().getClasses().getName())){
					TeacherClass teacherClass=new TeacherClass();
					teacherClass.setTeacherId(teacherClas.getTeacherId());
					
					ClassSchemewise classSchemewise = new ClassSchemewise();
					classSchemewise.setId(map.get(teacherClas.getClassId().getClasses().getName()));
					teacherClass.setClassId(classSchemewise);
					teacherClass.setIsActive(true);
					teacherClass.setTeacherType(teacherClas.getTeacherType());
					//teacherClass.setAcademicYear(assignClassTeacherForm.getAcademicYear());
					teacherClass.setCreatedBy(copyClassTeacherForm.getUserId());
					teacherClass.setModifiedBy(copyClassTeacherForm.getUserId());
					teacherClass.setCreatedDate(new Date());
					teacherClass.setLastModifiedDate(new Date());
					teacherlist.add(teacherClass);
				}
			}
		}
		 log.info("end of convertCopyClasses method in CopyClassTeacherHelper class.");
		return teacherlist;
	}

}
