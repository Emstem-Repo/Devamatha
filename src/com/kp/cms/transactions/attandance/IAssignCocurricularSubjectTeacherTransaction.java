package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CoCurricularTeacherBO;
import com.kp.cms.forms.attendance.AssignCocurricularSubjectTeacherForm;

public interface IAssignCocurricularSubjectTeacherTransaction {
	public Map<Integer, String> getUsers()throws Exception;
	public Map<Integer, String> getActivityMap()throws Exception;
	public List<CoCurricularTeacherBO> checkDuplicate(String teacherID) throws Exception;
	public boolean saveCocurricularSubjectTeacher(CoCurricularTeacherBO coCurricularTeacherBO) throws Exception;
	public List<CoCurricularTeacherBO> getCocurricularList() throws Exception;
	public boolean deleteCocurricularTeacher(AssignCocurricularSubjectTeacherForm assignCocurricularSubjectTeacherForm) throws Exception;
	public boolean reactiveCocurricularSubjectTeacher(String dupliateId,String userId) throws Exception;
	public CoCurricularTeacherBO editCocurricularTeacher(String id) throws Exception;
	public boolean updateCocurricularSubjectTeacher(CoCurricularTeacherBO coCurricularTeacherBO) throws Exception;
}
