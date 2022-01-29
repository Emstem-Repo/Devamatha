package com.kp.cms.bo.phd;

/**
 * Dec 29, 2009
 * Created By 9Elements Team
 */
import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;

public class PhdSynopsisDefenseBO implements Serializable {
    
	private int id;
	private Student studentId;
	private Classes classId;
	private Course courseId;
	private String type;
	private String name;
	private String contactNo;
	private String email;
	private String pinCode;
	private Boolean selectedMember;
	private String addressLine1;
	private String remarks;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private Boolean isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;

	public PhdSynopsisDefenseBO() {
	}

	public PhdSynopsisDefenseBO(int id,Student studentId,Classes classId,Course courseId,String name,
			String contactNo,String email,String remarks,String pinCode,Boolean selectedMember,String type,
			String addressLine1,String addressLine2,String addressLine3,String addressLine4,
			Boolean isActive,String createdBy,String modifiedBy,Date createdDate,Date lastModifiedDate) 
	{
		
		this.id = id;
		this.studentId=studentId;
		this.classId=classId;
		this.courseId=courseId;
		this.name=name;
		this.contactNo=contactNo;
		this.email=email;
		this.remarks=remarks;
		this.pinCode=pinCode;
		this.selectedMember=selectedMember;
		this.addressLine1=addressLine1;
		this.addressLine2=addressLine2;
		this.addressLine3=addressLine3;
		this.addressLine4=addressLine4;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.type=type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudentId() {
		return studentId;
	}

	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}
	public Classes getClassId() {
		return classId;
	}

	public void setClassId(Classes classId) {
		this.classId = classId;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(Boolean selectedMember) {
		this.selectedMember = selectedMember;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



}
