package com.kp.cms.transactions.serviceLearning;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryAdminBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.serviceLearning.ServiceLearningMarksEntryForm;

public interface IServiceLearningMarksEntryTransaction {
	public ProgrammeEntryBo getProgrammesById(
			ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws ApplicationException;
	public  List<ServiceLearningActivityBo> getServiceLearningActivityByProgrameId(
			ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws ApplicationException;
	public boolean saveServiceLearningActivity(List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList) throws Exception ;
	public List getOverallMarkByStudentId(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception;
	public ServiceLearningMarksEntryBo getServiceLearningmark(int studentId,int programId,int deptId) throws ApplicationException;
	public List<ServiceLearningMarksEntryBo> getStudentDetailsById(int stdId) throws Exception ;
	public ServiceLearningActivityBo getServiceLearningActivity(int programId,int deptId,int studentId) throws ApplicationException;
	public  List<Integer> getStudentIds() throws Exception ;
	public boolean deleteRecord(int id) throws ApplicationException;
	public List<Student> getOverallMark(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception;
	public List<Integer> getStudentIdsAdmin() throws Exception;
	public ServiceLearningMarksEntryAdminBo getServiceLearningmarkAdmin(int studentId,int programId,int deptId) throws ApplicationException ;
	public boolean saveServiceLearningActivityAdmin(List<ServiceLearningMarksEntryAdminBo> serviceLearningMarksEntryBoList) throws Exception;
	public ServiceLearningMarksEntryBo getServiceLearningmarkEntry(int studentId,int programId) throws ApplicationException;
	public ServiceLearningActivityBo getServiceLearningActivityWithoutDept(
			int programId, int studentId)throws Exception;
	public ServiceLearningMarksEntryBo getServiceLearningmark(int StudentId,
			int programId)throws Exception;

}
