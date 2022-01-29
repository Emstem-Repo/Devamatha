package com.kp.cms.handlers.studentfeedback;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaHiddenSubjectTeacher;
import com.kp.cms.forms.studentfeedback.EvaHiddenSubjectTeacherForm;
import com.kp.cms.helpers.studentfeedback.EvaHiddenSubjectTeacherHelpers;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;
import com.kp.cms.transactions.studentfeedback.IEvaHiddenSubjectTeacherTransaction;
import com.kp.cms.transactionsimpl.studentfeedback.EvaHiddenSubjectTeacherImpl;

public class EvaHiddenSubjectTeacherHandler
{

	private static final Log log=LogFactory.getLog(EvaHiddenSubjectTeacherHandler.class);
	public static volatile EvaHiddenSubjectTeacherHandler evaHiddenSubjectTeacherHandler=null;
	
	public static EvaHiddenSubjectTeacherHandler getInstance()
    {
        if(evaHiddenSubjectTeacherHandler == null)
        {
        	evaHiddenSubjectTeacherHandler = new EvaHiddenSubjectTeacherHandler();
            return evaHiddenSubjectTeacherHandler;
        } else
        {
            return evaHiddenSubjectTeacherHandler;
        }
    }
   IEvaHiddenSubjectTeacherTransaction transaction=EvaHiddenSubjectTeacherImpl.getInstance();

    public boolean hideTeacherSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm, String UserId)
        throws Exception
    {  
    	EvaHiddenSubjectTeacher hidelist=new EvaHiddenSubjectTeacher();
    	TeacherClassSubject hide=transaction.setHideTeacherList(evaHiddenSubjectTeacherForm);
    	ClassSchemewise classesId = new ClassSchemewise();
    	classesId.setId(hide.getClassId().getId());
    	hidelist.setClassId(classesId);
    	hidelist.setTeacherId(hide.getTeacherId());
    	hidelist.setSubjectId(hide.getSubject());
    	hidelist.setCreatedBy(evaHiddenSubjectTeacherForm.getUserId());
    	hidelist.setCreatedDate(new Date());
    	hidelist.setModifiedBy(evaHiddenSubjectTeacherForm.getUserId());
    	hidelist.setLastModifiedDate(new Date());
    	hidelist.setIsActive(Boolean.TRUE);
    	boolean isAdded = transaction.addHidelist(hidelist);
		log.info("end of addDesignationEntry method in DesignationEntryHandler class.");
		return isAdded;
    	
    }

	public List<EvaHiddenSubjectTeacherTo> getHideTeacherList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception{
		List<EvaHiddenSubjectTeacher> hideTeacherList=transaction.getHideTeacherList(evaHiddenSubjectTeacherForm);
		List<EvaHiddenSubjectTeacherTo> hideTeacherToList=EvaHiddenSubjectTeacherHelpers.getInstance().setBostoTo(hideTeacherList);
		return hideTeacherToList;
	}

	public List<TeacherClassEntryTo> getTeacherClassList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm, ActionErrors errors) throws Exception{
		List<Object[]> teacherClassBoList=transaction.getTeacherClassList(evaHiddenSubjectTeacherForm);
		if(teacherClassBoList==null || teacherClassBoList.isEmpty()){
			errors.add("error", new ActionError("knowledgepro.norecords"));
		}
		List<TeacherClassEntryTo> teacherClassTo=EvaHiddenSubjectTeacherHelpers.getInstance().setTeacherClassList(teacherClassBoList,evaHiddenSubjectTeacherForm);
		return teacherClassTo;
	}

	public boolean showTeacherSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception{
		boolean isShow = transaction.deleteHiddenSubject(evaHiddenSubjectTeacherForm);
		return isShow;
	}

	public boolean isDuplicate(	EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm,ActionErrors errors) throws Exception{
		   boolean duplicate = transaction.duplicateCheck(evaHiddenSubjectTeacherForm, errors);
	       return duplicate;
	}

}
