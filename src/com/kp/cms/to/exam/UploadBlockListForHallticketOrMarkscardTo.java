package com.kp.cms.to.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

public class UploadBlockListForHallticketOrMarkscardTo {
	private int classId;
	private String examName;
	private String examId;
	private String typeId;
	private String type;
	private String blockReason;
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	private List<KeyValueTO> listExamName;
	private  FormFile thefile;
	private int studentId;
	private String hallTktOrMarksCard;
	public String getHallTktOrMarksCard() {
		return hallTktOrMarksCard;
	}
	public void setHallTktOrMarksCard(String hallTktOrMarksCard) {
		this.hallTktOrMarksCard = hallTktOrMarksCard;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public List<KeyValueTO> getListExamName() {
		return listExamName;
	}
	public void setListExamName(List<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getBlockReason() {
		return blockReason;
	}
	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	
}
