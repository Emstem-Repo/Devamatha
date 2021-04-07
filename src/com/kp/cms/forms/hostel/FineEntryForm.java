package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.FineEntryTo;

public class FineEntryForm extends BaseActionForm{
	private int id;
	Map<String,String> hostelMap;
	private int reactivateid;
	private String categoryId;
	private String originalValue;
	private String amount;
	private String originalValue1;
	Map<String,String> categoryMap;
	private String remarks;
	private String paid;
	private boolean flag;
	private String hlAdminId;
	private String printFineEntry;
	private String pStudentName;
	private String pRegisterNo;
	private String pHostel;
	private String pCategory;
	private String pAmount;
	private String studentName;
	private String studentCourse;
	private String studentHostel;
	private String studentBlock;
	private String studentUnit;
	private String studentRoom;
	private String studentBed;
	private String categoryName;
	List<FineEntryTo> fineEntryToList;
	private String isHlTransaction;
	private String rgNoFromHlTransaction;
	private String year;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentCourse() {
		return studentCourse;
	}
	public void setStudentCourse(String studentCourse) {
		this.studentCourse = studentCourse;
	}
	public String getStudentHostel() {
		return studentHostel;
	}
	public void setStudentHostel(String studentHostel) {
		this.studentHostel = studentHostel;
	}
	public String getStudentBlock() {
		return studentBlock;
	}
	public void setStudentBlock(String studentBlock) {
		this.studentBlock = studentBlock;
	}
	public String getStudentUnit() {
		return studentUnit;
	}
	public void setStudentUnit(String studentUnit) {
		this.studentUnit = studentUnit;
	}
	public String getStudentRoom() {
		return studentRoom;
	}
	public void setStudentRoom(String studentRoom) {
		this.studentRoom = studentRoom;
	}
	public String getStudentBed() {
		return studentBed;
	}
	public void setStudentBed(String studentBed) {
		this.studentBed = studentBed;
	}
	public String getpStudentName() {
		return pStudentName;
	}
	public void setpStudentName(String pStudentName) {
		this.pStudentName = pStudentName;
	}
	public String getpRegisterNo() {
		return pRegisterNo;
	}
	public void setpRegisterNo(String pRegisterNo) {
		this.pRegisterNo = pRegisterNo;
	}
	
	public String getpHostel() {
		return pHostel;
	}
	public void setpHostel(String pHostel) {
		this.pHostel = pHostel;
	}
	public String getpCategory() {
		return pCategory;
	}
	public void setpCategory(String pCategory) {
		this.pCategory = pCategory;
	}
	public String getpAmount() {
		return pAmount;
	}
	public void setpAmount(String pAmount) {
		this.pAmount = pAmount;
	}
	public String getPrintFineEntry() {
		return printFineEntry;
	}
	public void setPrintFineEntry(String printFineEntry) {
		this.printFineEntry = printFineEntry;
	}
	public String getHlAdminId() {
		return hlAdminId;
	}
	public void setHlAdminId(String hlAdminId) {
		this.hlAdminId = hlAdminId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}

	public List<FineEntryTo> getFineEntryToList() {
		return fineEntryToList;
	}
	public void setFineEntryToList(List<FineEntryTo> fineEntryToList) {
		this.fineEntryToList = fineEntryToList;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Map<String, String> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<String, String> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOriginalValue1() {
		return originalValue1;
	}
	public void setOriginalValue1(String originalValue1) {
		this.originalValue1 = originalValue1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	/**
	 * form validation
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getIsHlTransaction() {
		return isHlTransaction;
	}
	public void setIsHlTransaction(String isHlTransaction) {
		this.isHlTransaction = isHlTransaction;
	}
	public String getRgNoFromHlTransaction() {
		return rgNoFromHlTransaction;
	}
	public void setRgNoFromHlTransaction(String rgNoFromHlTransaction) {
		this.rgNoFromHlTransaction = rgNoFromHlTransaction;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
