package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaHiddenSubjectTeacher;
import com.kp.cms.forms.studentfeedback.EvaHiddenSubjectTeacherForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;

public class EvaHiddenSubjectTeacherHelpers
{

	private static final Log log=LogFactory.getLog(EvaHiddenSubjectTeacherHelpers.class);
	public static volatile EvaHiddenSubjectTeacherHelpers evaHiddenSubjectTeacherHelpers = null;
   

    public static EvaHiddenSubjectTeacherHelpers getInstance()
    {
        if(evaHiddenSubjectTeacherHelpers == null)
        {
        	evaHiddenSubjectTeacherHelpers = new EvaHiddenSubjectTeacherHelpers();
            return evaHiddenSubjectTeacherHelpers;
        } else
        {
            return evaHiddenSubjectTeacherHelpers;
        }
    }
    /**
	 * @param hideTeacherList
	 * @return
	 */
	public List<EvaHiddenSubjectTeacherTo> setBostoTo(List<EvaHiddenSubjectTeacher> hideTeacherList) {
		List<EvaHiddenSubjectTeacherTo> hideTeacherto=new ArrayList<EvaHiddenSubjectTeacherTo>();
		if(hideTeacherList!=null){
			Iterator itr=hideTeacherList.iterator();
			while (itr.hasNext()) {
				EvaHiddenSubjectTeacher hiddenSubjectTeacher = (EvaHiddenSubjectTeacher)itr.next();
				EvaHiddenSubjectTeacherTo hiddenSubjectTeacherTo=new EvaHiddenSubjectTeacherTo();
				
				hiddenSubjectTeacherTo.setId(hiddenSubjectTeacher.getId());
				hiddenSubjectTeacherTo.setClassName(hiddenSubjectTeacher.getClassId().getClasses().getName());
				hiddenSubjectTeacherTo.setTeacherName(hiddenSubjectTeacher.getTeacherId().getUserName());
				String subject=hiddenSubjectTeacher.getSubjectId().getCode();
				hiddenSubjectTeacherTo.setSubjectName(subject+" - "+hiddenSubjectTeacher.getSubjectId().getName());
				hideTeacherto.add(hiddenSubjectTeacherTo);
			}
		}
		return hideTeacherto;
	}

	public List<TeacherClassEntryTo> setTeacherClassList(List<Object[]> teacherClassBoList,EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) {
		List<TeacherClassEntryTo> teacherList=new ArrayList<TeacherClassEntryTo>();
		if(teacherClassBoList!=null && !teacherClassBoList.isEmpty()){
			Iterator iter=teacherClassBoList.iterator();
			while (iter.hasNext()) {
				Object[] object = (Object[]) iter.next();
				TeacherClassEntryTo teacherClassEntryTo=new TeacherClassEntryTo();
				if(object[0]!=null){
					teacherClassEntryTo.setId(Integer.parseInt(object[0].toString()));
				}if(object[1]!=null){
					teacherClassEntryTo.setClassName(object[1].toString());
					evaHiddenSubjectTeacherForm.setClassName(object[1].toString());
				}if(object[2]!=null){
					teacherClassEntryTo.setTeacherName(object[2].toString());
				}if(object[3]!=null && object[4]!=null){
					teacherClassEntryTo.setSubjectName(object[4].toString()+" - "+object[3].toString());
				}
				teacherList.add(teacherClassEntryTo);
			}
			
		}
		return teacherList;
	}

}
