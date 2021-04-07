package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.ValuationStatisticsTO;

public class ValuationStatisticsForm extends BaseActionForm{
	
	private String examType;
	private String examId; 
	private Map<Integer, String> examNameList;
	private ValuationStatisticsTO statistics;
	private List<ValuationStatisticsTO> deaneryWise;
	private List<ValuationStatisticsTO> departmentWise;
	private List<ValuationStatisticsTO> subjectWise;
	private List<Integer> totalSubjects;
	private List<Subject> subjectList;
	private Map<Integer, Map<Integer, List<Integer>>> deaneryWiseMarksEntry;
	private Map<Integer, Map<Integer, List<Integer>>> departmentWiseMarksEntry;
	private Map<Integer,List<Integer>> studentsForSubjects;
	private List<Integer> schemeNoList;
	private int deptStreamId;
	private String deaneryName;
	private String departmentName;
	private int deptId;
	private List<ExamValuationStatusTO> valuationStatusSubjectWise;
	private Map<Integer,List<Student>> verificationMap;
	private List<StudentTO>  students;
	private String evaluatorTypeId; 
	private String forUser;
	private List<Integer> totalValCompletedSubjects;
	private List<Integer> valInProgress;
	private int termNumber;
	private Map<Integer, String> firstIssuedDate;
	private Map<Integer, String> examStartDate;
	private Map<Integer, String> valuationEndDate;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void reset() {
		this.examType="Regular";
		this.examId=null;
		this.deaneryWise=null;
		this.departmentWise=null;
		this.valuationStatusSubjectWise=null;
		this.totalSubjects=null;
		this.forUser="false";
		this.firstIssuedDate = null;
		this.examStartDate=null;
		this.valuationEndDate=null;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public Map<Integer, String> getExamNameList() {
		return examNameList;
	}

	public void setExamNameList(Map<Integer, String> examNameList) {
		this.examNameList = examNameList;
	}

	public ValuationStatisticsTO getStatistics() {
		return statistics;
	}

	public void setStatistics(ValuationStatisticsTO statistics) {
		this.statistics = statistics;
	}

	public List<Integer> getTotalSubjects() {
		return totalSubjects;
	}

	public void setTotalSubjects(List<Integer> totalSubjects) {
		this.totalSubjects = totalSubjects;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public List<ValuationStatisticsTO> getDeaneryWise() {
		return deaneryWise;
	}

	public void setDeaneryWise(List<ValuationStatisticsTO> deaneryWise) {
		this.deaneryWise = deaneryWise;
	}


	public Map<Integer, Map<Integer, List<Integer>>> getDeaneryWiseMarksEntry() {
		return deaneryWiseMarksEntry;
	}

	public void setDeaneryWiseMarksEntry(
			Map<Integer, Map<Integer, List<Integer>>> deaneryWiseMarksEntry) {
		this.deaneryWiseMarksEntry = deaneryWiseMarksEntry;
	}

	public Map<Integer, List<Integer>> getStudentsForSubjects() {
		return studentsForSubjects;
	}

	public void setStudentsForSubjects(
			Map<Integer, List<Integer>> studentsForSubjects) {
		this.studentsForSubjects = studentsForSubjects;
	}

	public List<Integer> getSchemeNoList() {
		return schemeNoList;
	}

	public void setSchemeNoList(List<Integer> schemeNoList) {
		this.schemeNoList = schemeNoList;
	}

	public List<ValuationStatisticsTO> getDepartmentWise() {
		return departmentWise;
	}

	public void setDepartmentWise(List<ValuationStatisticsTO> departmentWise) {
		this.departmentWise = departmentWise;
	}

	public int getDeptStreamId() {
		return deptStreamId;
	}

	public void setDeptStreamId(int deptStreamId) {
		this.deptStreamId = deptStreamId;
	}


	public Map<Integer, Map<Integer, List<Integer>>> getDepartmentWiseMarksEntry() {
		return departmentWiseMarksEntry;
	}

	public void setDepartmentWiseMarksEntry(
			Map<Integer, Map<Integer, List<Integer>>> departmentWiseMarksEntry) {
		this.departmentWiseMarksEntry = departmentWiseMarksEntry;
	}

	public List<ValuationStatisticsTO> getSubjectWise() {
		return subjectWise;
	}

	public void setSubjectWise(List<ValuationStatisticsTO> subjectWise) {
		this.subjectWise = subjectWise;
	}

	public String getDeaneryName() {
		return deaneryName;
	}

	public void setDeaneryName(String deaneryName) {
		this.deaneryName = deaneryName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public List<ExamValuationStatusTO> getValuationStatusSubjectWise() {
		return valuationStatusSubjectWise;
	}

	public void setValuationStatusSubjectWise(
			List<ExamValuationStatusTO> valuationStatusSubjectWise) {
		this.valuationStatusSubjectWise = valuationStatusSubjectWise;
	}

	public Map<Integer, List<Student>> getVerificationMap() {
		return verificationMap;
	}

	public void setVerificationMap(Map<Integer, List<Student>> verificationMap) {
		this.verificationMap = verificationMap;
	}

	public List<StudentTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentTO> students) {
		this.students = students;
	}

	public String getEvaluatorTypeId() {
		return evaluatorTypeId;
	}

	public void setEvaluatorTypeId(String evaluatorTypeId) {
		this.evaluatorTypeId = evaluatorTypeId;
	}

	public String getForUser() {
		return forUser;
	}

	public void setForUser(String forUser) {
		this.forUser = forUser;
	}

	public List<Integer> getTotalValCompletedSubjects() {
		return totalValCompletedSubjects;
	}

	public void setTotalValCompletedSubjects(List<Integer> totalValCompletedSubjects) {
		this.totalValCompletedSubjects = totalValCompletedSubjects;
	}

	public List<Integer> getValInProgress() {
		return valInProgress;
	}

	public void setValInProgress(List<Integer> valInProgress) {
		this.valInProgress = valInProgress;
	}

	public int getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}

	public Map<Integer, String> getFirstIssuedDate() {
		return firstIssuedDate;
	}

	public void setFirstIssuedDate(Map<Integer, String> firstIssuedDate) {
		this.firstIssuedDate = firstIssuedDate;
	}

	public Map<Integer, String> getExamStartDate() {
		return examStartDate;
	}

	public void setExamStartDate(Map<Integer, String> examStartDate) {
		this.examStartDate = examStartDate;
	}

	public Map<Integer, String> getValuationEndDate() {
		return valuationEndDate;
	}

	public void setValuationEndDate(Map<Integer, String> valuationEndDate) {
		this.valuationEndDate = valuationEndDate;
	}

		
	
}
