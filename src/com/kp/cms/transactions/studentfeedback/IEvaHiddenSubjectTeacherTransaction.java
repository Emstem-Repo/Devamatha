package com.kp.cms.transactions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedBackGroup;
import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.EvaHiddenSubjectTeacher;
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;
import com.kp.cms.forms.studentfeedback.EvaHiddenSubjectTeacherForm;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;

public interface IEvaHiddenSubjectTeacherTransaction
{

    public List<EvaHiddenSubjectTeacher> getHideTeacherList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception;

	public List<Object[]> getTeacherClassList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception;

	public boolean deleteHiddenSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception;

	public TeacherClassSubject setHideTeacherList(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception;

	public boolean addHidelist(EvaHiddenSubjectTeacher hidelist)throws Exception;

	public boolean duplicateCheck(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm,ActionErrors errors) throws Exception;
}
