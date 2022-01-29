package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.SubjectGroupUtilBO;
import com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm;

public interface IAssignSubjectGroupHistoryTxn {

	public ClassSchemewise getClassSchemwises( AssignSubjectGroupHistoryForm assignSubGrpHistory)throws Exception;

	//public List<ExamStudentPreviousClassDetailsBO> getPreviousClassDetails( ClassSchemewise classSchemewises)throws Exception;

	public Map<Integer, SubjectGroup> getSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistory)throws Exception;

	public List<SubjectGroup> getSubjectGroupsList( AssignSubjectGroupHistoryForm assignSubGrpHistory,ClassSchemewise classSchemewises)throws Exception;

	public List<StudentPreviousClassHistory> getStudentPreviousClassDetails(ClassSchemewise classSchemewises, AssignSubjectGroupHistoryForm assignSubGrpHistory)throws Exception;

	public boolean addStudentSubjectGroup( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm)throws Exception;

	public Map<Integer, Integer> getEditSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm)throws Exception;

}
