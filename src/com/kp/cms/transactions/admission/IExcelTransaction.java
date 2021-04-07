package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.admission.ExcelDataTO;

public interface IExcelTransaction {

	public Map<Integer,Integer> getAdmAppDetails(int year,int csDurationId,int courseId) throws Exception;
	
	public Map<String,Integer> getClasses(int courseId,int csId,int academicYear) throws Exception;
	
	public boolean isDataUploaded(List<Student> list) throws Exception;
	
	public int getClassId(int courseId, String className,int semNO,Integer year)  throws Exception;
	
	public int getClassSchemeWiseId(int classId, int csId) throws Exception;
	
	public boolean checkAdmApplnId(int admApplnId) throws Exception;
	
	public int getStudentId(int admId) throws Exception;
	
	public boolean checkDuplicateSectionName(int courseId, int semesterId, String sectionName) throws Exception;
	
	public boolean updateStudent(List<ExcelDataTO> dataTO) throws Exception; 
	
	public boolean updateChallan(List<ExcelDataTO> dataTO) throws Exception;
	public List<String> getChallanList(String year) throws Exception;
	public boolean updateChallanExam(List<ExcelDataTO> dataTO) throws Exception;
	public boolean verifyChallanExam(List<ExcelDataTO> dataTOList) throws Exception;
	public List<String> getChallanListForExam(String year,int examId) throws Exception;
	public List<String> getChallanListForFee() throws Exception;
	boolean updateChallanFee(List<ExcelDataTO> dataTOList) throws Exception;

	public boolean updateStudentData(List<ExcelDataTO> updateList) throws Exception;
	public int getStudentObj(int admId) throws Exception;

	public int getClassSchId(int studId) throws Exception;
}
