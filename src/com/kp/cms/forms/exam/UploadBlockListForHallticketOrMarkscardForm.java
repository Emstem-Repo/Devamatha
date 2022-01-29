package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.KeyValueTO;

public class UploadBlockListForHallticketOrMarkscardForm extends BaseActionForm {
	private int id;
	private String examName;
	private String examId;
	private String typeId;
	private String type;
	private HashMap<Integer, String> examTypeList;
	private Map<Integer,String> examNameMap;
	private List<KeyValueTO> listExamName;
	private FormFile thefile;
	private String unMatchRegNum;
	private String dupRegNum;
	private String emptyReason;
    private String dupRegNumMsg;
    private boolean flag;
    private String classNotFound;
    private boolean flag1;
    private boolean flag2;
    private String duplireason;
    private String duplireasonNum;
    private boolean flag3;
    private String unMatchRegNumMsg;
    private boolean flag4;
    private String unMatchClasses;
    private boolean flag5;
    private String unMatchRegByClass;
    
    
    
	public String getClassNotFound() {
		return classNotFound;
	}

	public void setClassNotFound(String classNotFound) {
		this.classNotFound = classNotFound;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public String getDupRegNumMsg() {
		return dupRegNumMsg;
	}

	public void setDupRegNumMsg(String dupRegNumMsg) {
		this.dupRegNumMsg = dupRegNumMsg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getEmptyReason() {
		return emptyReason;
	}

	public void setEmptyReason(String emptyReason) {
		this.emptyReason = emptyReason;
	}

	public String getDupRegNum() {
		return dupRegNum;
	}

	public void setDupRegNum(String dupRegNum) {
		this.dupRegNum = dupRegNum;
	}

	public String getUnMatchRegNum() {
		return unMatchRegNum;
	}

	public void setUnMatchRegNum(String unMatchRegNum) {
		this.unMatchRegNum = unMatchRegNum;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
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
	 public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);

			return actionErrors;
		}
	 public void resetFields(){
		 	this.setYear(null);
			this.setTypeId(null);
			this.setExamTypeList(null);
			this.setType(null);
			this.setUnMatchRegNum(null);
			this.setDupRegNum(null);
			this.setFlag(false);
			this.setFlag1(false);
			this.setFlag2(false);
			this.setFlag3(false);
			this.setFlag4(false); this.setFlag5(false);
			 this.setFlag5(false);
			this.setUnMatchClasses(null);
			this.setUnMatchRegByClass(null);
		}
	 public void reset(){
		 this.setFlag(false);
		 this.setFlag1(false);
		 this.setFlag2(false);
		 this.setFlag3(false);
		 this.setFlag4(false);
		 this.setFlag5(false);
		 this.setUnMatchClasses(null);
		 this.setUnMatchRegByClass(null);
	 }

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public String getDuplireason() {
		return duplireason;
	}

	public void setDuplireason(String duplireason) {
		this.duplireason = duplireason;
	}

	public String getDuplireasonNum() {
		return duplireasonNum;
	}

	public void setDuplireasonNum(String duplireasonNum) {
		this.duplireasonNum = duplireasonNum;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public String getUnMatchRegNumMsg() {
		return unMatchRegNumMsg;
	}

	public void setUnMatchRegNumMsg(String unMatchRegNumMsg) {
		this.unMatchRegNumMsg = unMatchRegNumMsg;
	}

	public boolean isFlag4() {
		return flag4;
	}

	public void setFlag4(boolean flag4) {
		this.flag4 = flag4;
	}

	public String getUnMatchClasses() {
		return unMatchClasses;
	}

	public void setUnMatchClasses(String unMatchClasses) {
		this.unMatchClasses = unMatchClasses;
	}

	public boolean isFlag5() {
		return flag5;
	}

	public void setFlag5(boolean flag5) {
		this.flag5 = flag5;
	}

	public String getUnMatchRegByClass() {
		return unMatchRegByClass;
	}

	public void setUnMatchRegByClass(String unMatchRegByClass) {
		this.unMatchRegByClass = unMatchRegByClass;
	}
}
