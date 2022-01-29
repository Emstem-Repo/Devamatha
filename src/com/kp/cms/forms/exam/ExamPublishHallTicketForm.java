package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamPublishHallTicketForm extends BaseActionForm {
	private String examType;
	private int id;
	private String agreementName;
	private String publishFor;
	private String footerName;
	private String programType;
	private String examName;
	private String[] stayClass;

	private Map<Integer, String> mapAgree;
	private Map<Integer, String> mapFooter;
	private String examMap;
	private String[] classCodeIdsFrom;
	private String[] classCodeIdsTo;

	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private Map<Integer, String> selectedClassCode;
	private String downLoadStartDate;
	private String downLoadEndDate;
	private String classValues;

	private ArrayList<KeyValueTO> listExamtype;
	private ArrayList<KeyValueTO> listExamName;
	private List<KeyValueTO> programTypeList;
	private Map<Integer, String> agreementList;
	private Map<Integer, String> footerList;
	private List<KeyValueTO> publishForList;
	private ArrayList<ExamPublishHallTicketTO> mainList;
	public int getAgreementID;
	public int getFooterId;

	private Date downloadStart;
	private Date downloadEnd;
	private String examCenterCode;
	private String examCenter;
	private boolean examCenterDispaly;
	//added by smitha
	private Map<Integer,String> examNameMap;
	private String revaluationEndDate;
	
	//added by giri
	private Map<String,String> deaneryMap;
	private String extendedDate;
	//end by giri
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
	public Map<String, String> getDeaneryMap() {
		return deaneryMap;
	}


	public void setDeaneryMap(Map<String, String> deaneryMap) {
		this.deaneryMap = deaneryMap;
	}


	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getAgreementName() {
		return agreementName;
	}

	public String[] getStayClass() {
		return stayClass;
	}

	public void setStayClass(String[] stayClass) {
		this.stayClass = stayClass;
	}

	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}

	public String getPublishFor() {
		return publishFor;
	}

	public void setPublishFor(String publishFor) {
		this.publishFor = publishFor;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String[] getClassCodeIdsFrom() {
		return classCodeIdsFrom;
	}

	public void setClassCodeIdsFrom(String[] classCodeIdsFrom) {
		this.classCodeIdsFrom = classCodeIdsFrom;
	}

	public Map<Integer, String> getSelectedClassCode() {
		return selectedClassCode;
	}

	public void setSelectedClassCode(Map<Integer, String> selectedClassCode) {
		this.selectedClassCode = selectedClassCode;
	}

	public String getDownLoadStartDate() {
		return downLoadStartDate;
	}

	public void setDownLoadStartDate(String downLoadStartDate) {
		this.downLoadStartDate = downLoadStartDate;
	}

	public String getDownLoadEndDate() {
		return downLoadEndDate;
	}

	public void setDownLoadEndDate(String downLoadEndDate) {
		this.downLoadEndDate = downLoadEndDate;
	}

	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setMainList(ArrayList<ExamPublishHallTicketTO> mainList) {

		this.mainList = mainList;
	}

	public ArrayList<ExamPublishHallTicketTO> getMainList() {
		return mainList;
	}

	public ArrayList<KeyValueTO> getListExamtype() {
		return listExamtype;
	}

	public void setListExamtype(ArrayList<KeyValueTO> listExamtype) {
		this.listExamtype = listExamtype;
	}

	public Map<Integer, String> getMapClass() {
		return mapClass;
	}

	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}

	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}

	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}

	public String getFooterName() {
		return footerName;
	}

	public void setFooterName(String footerName) {
		this.footerName = footerName;
	}

	public List<KeyValueTO> getPublishForList() {
		return publishForList;
	}

	public void setPublishForList(List<KeyValueTO> publishForList) {
		this.publishForList = publishForList;
	}

	public void setMapAgree(Map<Integer, String> mapAgree) {
		this.mapAgree = mapAgree;
	}

	public Map<Integer, String> getMapAgree() {
		return mapAgree;
	}

	public void setMapFooter(Map<Integer, String> mapFooter) {
		this.mapFooter = mapFooter;
	}

	public Map<Integer, String> getMapFooter() {
		return mapFooter;
	}

	public void setExamMap(String examMap) {
		this.examMap = examMap;
	}

	public String getExamMap() {
		return examMap;
	}

	public String getClassValues() {
		return classValues;
	}

	public void setClassValues(String classValues) {
		this.classValues = classValues;
	}

	public int getGetAgreementID() {
		return getAgreementID;
	}

	public void setGetAgreementID(int getAgreementID) {
		this.getAgreementID = getAgreementID;
	}

	public int getGetFooterId() {
		return getFooterId;
	}

	public void setGetFooterId(int getFooterId) {
		this.getFooterId = getFooterId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setClassCodeIdsTo(String[] classCodeIdsTo) {
		this.classCodeIdsTo = classCodeIdsTo;
	}

	public String[] getClassCodeIdsTo() {
		return classCodeIdsTo;
	}

	public void setFooterList(Map<Integer, String> footerList) {
		this.footerList = footerList;
	}

	public Map<Integer, String> getFooterList() {
		return footerList;
	}

	public void setAgreementList(Map<Integer, String> agreementList) {
		this.agreementList = agreementList;
	}

	public Map<Integer, String> getAgreementList() {
		return agreementList;
	}

	public void setListExamName(ArrayList<KeyValueTO> listExamName) {
		this.listExamName = listExamName;
	}

	public ArrayList<KeyValueTO> getListExamName() {
		return listExamName;
	}

	public void setDownloadStart(Date downloadStart) {
		this.downloadStart = downloadStart;
	}

	public Date getDownloadStart() {
		return downloadStart;
	}

	public void setDownloadEnd(Date downloadEnd) {
		this.downloadEnd = downloadEnd;
	}

	public Date getDownloadEnd() {
		return downloadEnd;
	}

	public String getExamCenterCode() {
		return examCenterCode;
	}

	public void setExamCenterCode(String examCenterCode) {
		this.examCenterCode = examCenterCode;
	}

	public String getExamCenter() {
		return examCenter;
	}

	public void setExamCenter(String examCenter) {
		this.examCenter = examCenter;
	}

	public void pageclear() {
		this.examType = null;
		this.examName = " ";
		this.classCodeIdsTo = null;
		this.publishFor = " ";
		this.programType = null;
		this.downLoadStartDate = null;
		this.downLoadEndDate = null;
		this.examCenter = null;
		this.examCenterCode = null;
		this.examNameMap=null;
		this.revaluationEndDate=null;
		super.clear();
	}
	

	public boolean isExamCenterDispaly() {
		return examCenterDispaly;
	}

	public void setExamCenterDispaly(boolean examCenterDispaly) {
		this.examCenterDispaly = examCenterDispaly;
	}

	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}

	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}

	public String getRevaluationEndDate() {
		return revaluationEndDate;
	}

	public void setRevaluationEndDate(String revaluationEndDate) {
		this.revaluationEndDate = revaluationEndDate;
	}
	

	public String getExtendedDate() {
		return extendedDate;
	}


	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}


	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

}