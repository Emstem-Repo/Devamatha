package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.DisplayValueTO;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamDefinitionForm extends BaseActionForm {

	private String programTypeName;
	private String programName;
	private int id;
	private String[] selectedProgramType;
	private String[] selectedProgram;
	private String programIds;
	private String alias;

	public List<KeyValueTO> getProgramList() {
		return programList;
	}

	public void setProgramList(List<KeyValueTO> programList) {
		this.programList = programList;
	}

	private List<ExamDefinitionTO> examDefinitionList;
	List<KeyValueTO> programTypeList;
	List<KeyValueTO> programList;
	private String academicYear1;
	private String academicYear;
	private int examTypeId;
	private String examName;
	private String examCode;
	private String month;
	private String year;
	private int joiningBatchYear;
	private int maxFailedSubjects;
	private String current = "off";
	private boolean currentDummy = false;

	private String[] internalExamId = { "" };
	// private String internalExamName;
	private int internalExamTypeId;
	private String checkedActive="off";
	private boolean checkedActiveDummy;
	private String listProgramTypes;
	private String listPrograms;
	
	private ArrayList<String> prgTypeList;
	private ArrayList<String> prgList;
	
	

	private List<KeyValueTO> examTypeList;
	// private List<KeyValueTO> internalExamList;
	private Map<Integer, String> mapInternalExam;
	private List<KeyValueTO> internalExamTypeList;
	private int examCourseSchemeListSize;
	private List<ExamCourseSchemeDetailsTO> examCourseSchemeList;
	private String schemeType;

	// used in update function
	private String origExamName;
	private String origExamCode;
	private String origMonth;
	private String origYear;

	private int origExamTypeId;

	private String courseSchemeSelected;
	private String examDefId_progId;
	private String courseSchemeUnSelected;
	private String tempAcademicYear;
	private String tempYear;
	private String regularExamIdForRedo;
	private Map<Integer, String> regularExams;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);

		this.programTypeName = null;
		this.programName = null;
		this.id = 0;
		this.selectedProgramType = null;
		this.selectedProgram = null;
		this.programIds = null;
		this.academicYear = null;
		this.examTypeId = 0;
		this.examName = null;
		this.examCode = null;
		this.month = null;
		this.year = null;
		this.joiningBatchYear = 0;
		this.maxFailedSubjects = 0;
		this.current = null;
		this.currentDummy = false;
		this.internalExamId = null;
		this.internalExamTypeId = 0;
		this.origExamName = null;
		this.origExamCode = null;
		this.origMonth = null;
		this.origYear = null;
		this.origExamTypeId = 0;
		this.schemeType=null;
		this.regularExamIdForRedo = null;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getSelectedProgramType() {
		return selectedProgramType;
	}

	public void setSelectedProgramType(String[] selectedProgramType) {
		this.selectedProgramType = selectedProgramType;
	}

	public String[] getSelectedProgram() {
		return selectedProgram;
	}

	public void setSelectedProgram(String[] selectedProgram) {
		this.selectedProgram = selectedProgram;
	}

	public List<ExamDefinitionTO> getExamDefinitionList() {
		return examDefinitionList;
	}

	public void setExamDefinitionList(List<ExamDefinitionTO> examDefinitionList) {
		this.examDefinitionList = examDefinitionList;
	}

	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getListProgramTypes() {
		return listProgramTypes;
	}

	public void setListProgramTypes(String listProgramTypes) {
		this.listProgramTypes = listProgramTypes;
	}

	public String getListPrograms() {
		return listPrograms;
	}

	public void setListPrograms(String listPrograms) {
		this.listPrograms = listPrograms;
	}

	public List<KeyValueTO> getExamTypeList() {
		return examTypeList;
	}

	public void setExamTypeList(List<KeyValueTO> examTypeList) {
		this.examTypeList = examTypeList;
	}

	public List<KeyValueTO> getInternalExamTypeList() {
		return internalExamTypeList;
	}

	public void setInternalExamTypeList(List<KeyValueTO> internalExamTypeList) {
		this.internalExamTypeList = internalExamTypeList;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(int examTypeId) {
		this.examTypeId = examTypeId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getJoiningBatchYear() {
		return joiningBatchYear;
	}

	public void setJoiningBatchYear(int joiningBatchYear) {
		this.joiningBatchYear = joiningBatchYear;
	}

	public int getMaxFailedSubjects() {
		return maxFailedSubjects;
	}

	public void setMaxFailedSubjects(int maxFailedSubjects) {
		this.maxFailedSubjects = maxFailedSubjects;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;

	}

	public int getInternalExamTypeId() {
		return internalExamTypeId;
	}

	public void setInternalExamTypeId(int internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}

	public String getCheckedActive() {
		return checkedActive;
	}

	public void setCheckedActive(String checkedActive) {
		this.checkedActive = checkedActive;

	}

	public List<ExamCourseSchemeDetailsTO> getExamCourseSchemeList() {
		return examCourseSchemeList;
	}

	public void setExamCourseSchemeList(
			List<ExamCourseSchemeDetailsTO> examCourseSchemeList) {
		this.examCourseSchemeList = examCourseSchemeList;
	}

	public void setExamCourseSchemeListSize(int examCourseSchemeListSize) {
		this.examCourseSchemeListSize = examCourseSchemeListSize;
	}

	public int getExamCourseSchemeListSize() {
		return examCourseSchemeListSize;
	}

	public void setCourseSchemeSelected(String courseSchemeSelected) {
		this.courseSchemeSelected = courseSchemeSelected;
	}

	public String getCourseSchemeSelected() {
		return courseSchemeSelected;
	}

	public void setInternalExamId(String[] internalExamId) {
		this.internalExamId = internalExamId;
	}

	public String[] getInternalExamId() {
		return internalExamId;
	}

	public void setProgramIds(String programIds) {
		this.programIds = programIds;
	}

	public String getProgramIds() {
		return programIds;
	}

	public String getOrigExamName() {
		return origExamName;
	}

	public void setOrigExamName(String origExamName) {
		this.origExamName = origExamName;
	}

	public String getOrigExamCode() {
		return origExamCode;
	}

	public void setOrigExamCode(String origExamCode) {
		this.origExamCode = origExamCode;
	}

	public String getOrigMonth() {
		return origMonth;
	}

	public void setOrigMonth(String origMonth) {
		this.origMonth = origMonth;
	}

	public String getOrigYear() {
		return origYear;
	}

	public void setOrigYear(String origYear) {
		this.origYear = origYear;
	}

	public int getOrigExamTypeId() {
		return origExamTypeId;
	}

	public void setOrigExamTypeId(int origExamTypeId) {
		this.origExamTypeId = origExamTypeId;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public Map<Integer, String> getMapInternalExam() {
		return mapInternalExam;
	}

	public void setMapInternalExam(Map<Integer, String> mapInternalExam) {
		this.mapInternalExam = mapInternalExam;
	}

	public void setExamDefId_progId(String examDefId_progId) {
		this.examDefId_progId = examDefId_progId;
	}

	public String getExamDefId_progId() {
		return examDefId_progId;
	}

	public boolean getCurrentDummy() {
		return currentDummy;
	}

	public void setCurrentDummy(boolean currentDummy) {
		this.currentDummy = currentDummy;
	}

	public boolean getCheckedActiveDummy() {
		return checkedActiveDummy;
	}

	public void setCheckedActiveDummy(boolean checkedActiveDummy) {
		this.checkedActiveDummy = checkedActiveDummy;
	}
	public ArrayList<String> getPrgTypeList() {
		return prgTypeList;
	}

	public void setPrgTypeList(ArrayList<String> prgTypeList) {
		this.prgTypeList = prgTypeList;
	}

	public ArrayList<String> getPrgList() {
		return prgList;
	}

	public void setPrgList(ArrayList<String> prgList) {
		this.prgList = prgList;
	}

	public String getCourseSchemeUnSelected() {
		return courseSchemeUnSelected;
	}

	public void setCourseSchemeUnSelected(String courseSchemeUnSelected) {
		this.courseSchemeUnSelected = courseSchemeUnSelected;
	}

	public String getTempAcademicYear() {
		return tempAcademicYear;
	}

	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}

	public String getTempYear() {
		return tempYear;
	}

	public void setTempYear(String tempYear) {
		this.tempYear = tempYear;
	}
	public String getAcademicYear1() {
		return academicYear1;
	}

	public void setAcademicYear1(String academicYear1) {
		this.academicYear1 = academicYear1;
	}

	public String getRegularExamIdForRedo() {
		return regularExamIdForRedo;
	}

	public void setRegularExamIdForRedo(String regularExamIdForRedo) {
		this.regularExamIdForRedo = regularExamIdForRedo;
	}

	public Map<Integer, String> getRegularExams() {
		return regularExams;
	}

	public void setRegularExams(Map<Integer, String> regularExams) {
		this.regularExams = regularExams;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
