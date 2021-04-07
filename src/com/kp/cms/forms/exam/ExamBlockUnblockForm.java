package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Dec 25, 2009 Created By 9Elements
 */
public class ExamBlockUnblockForm extends BaseActionForm {
	private int id;
	private String examName;
	private String examId;
	private String typeId;
	private String[] classValue;
	private String studentsId;
	private String type;
	private String classId_studentId;
	private int listCandidatesSize;
	private Map<Integer,String> mapClass;
	//added by Smitha 
	 private HashMap<Integer, String> examTypeList;
	 private Map<Integer,String> examNameMap;
	 
	private String blockType;
	
	public int getListCandidatesSize() {
		return listCandidatesSize;
	}
	public void setListCandidatesSize(int listCandidatesSize) {
		this.listCandidatesSize = listCandidatesSize;
	}
	public void setClassValue(String[] classValue) {
		this.classValue = classValue;
	}

	private List<KeyValueTO> listExamName;
	private List<KeyValueTO> listClassName;
	private List<ExamBlockUnBlockCandidatesTO> listCandidatesName;
	private Map<Integer, String> classesMap;


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clearPage() {
		this.typeId="";
	//	this.examName = "";
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public List<KeyValueTO> getListClassName() {
		return listClassName;
	}

	public void setListClassName(List<KeyValueTO> listClassName) {
		this.listClassName = listClassName;
	}

	public Map<Integer, String> getClassesMap() {
		return classesMap;
	}

	public void setClassesMap(Map<Integer, String> classesMap) {
		this.classesMap = classesMap;
	}

	public List<ExamBlockUnBlockCandidatesTO> getListCandidatesName() {
		return listCandidatesName;
	}

	public void setListCandidatesName(
			List<ExamBlockUnBlockCandidatesTO> listCandidatesName) {
		this.listCandidatesName = listCandidatesName;
	}

	public String getStudentsId() {
		return studentsId;
	}

	public void setStudentsId(String studentsId) {
		this.studentsId = studentsId;
	}

	public String[] getClassValue() {
		return classValue;
	}

	public void setClassName(String[] classValue) {
		this.classValue = classValue;
	}

	public void setClassId_studentId(String classId_studentId) {
		this.classId_studentId = classId_studentId;
	}

	public String getClassId_studentId() {
		return classId_studentId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamId() {
		return examId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}
	public void setMapClass(Map<Integer,String> mapClass) {
		this.mapClass = mapClass;
	}
	public Map<Integer,String> getMapClass() {
		return mapClass;
	}
	
	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}
	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public void resetFields(){
		this.setClassValue(null);
		this.setTypeId(null);
		super.setIsPreviousExam("true");
	}
	public String getBlockType() {
		return blockType;
	}
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
}
