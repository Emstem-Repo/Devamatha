package com.kp.cms.to.exam;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ExamOptAssSubTypeTO implements Serializable,Comparable<ExamOptAssSubTypeTO>{

	private Integer id;
	private boolean isChecked;
	private boolean isCheckedDummy;
	private Integer studentId;
	private String rollNo;
	private String registerNo;
	private String studentname;
	private String specialization;
	private String optSubGroup;
	private Integer specializationId;
	private Integer optSubGroupId;
	private Integer checkId;
	private Integer specSubGrpId;
	private int admApplnId;

	public ExamOptAssSubTypeTO() {
		super();
	}

	public ExamOptAssSubTypeTO(Integer studentId, String optSubGroup,
			String registerNo, String rollNo, String specialization,
			String studentname) {
		super();
		this.studentId = studentId;
		this.optSubGroup = optSubGroup;
		this.registerNo = registerNo;
		this.rollNo = rollNo;
		this.specialization = specialization;
		this.studentname = studentname;
	}

	public ExamOptAssSubTypeTO(boolean isChecked, Integer studentId,
			Integer specializationId, Integer optSubGroupId) {
		super();
		this.isChecked = isChecked;
		this.studentId = studentId;
		this.specializationId = specializationId;
		this.optSubGroupId = optSubGroupId;
	}

	public ExamOptAssSubTypeTO(boolean isChecked, Integer id,
			Integer optSubGroupId) {
		super();
		this.isChecked = isChecked;
		this.id = id;
		this.optSubGroupId = optSubGroupId;
	}

	public ExamOptAssSubTypeTO(ExamOptAssSubTypeTO eTO,
			KeyValueTO specId_SubGrp_KeyTO) {
		super();
		this.id = eTO.getId();
		this.studentId = eTO.getStudentId();
		this.rollNo = eTO.getRollNo();
		this.registerNo = eTO.getRegisterNo();
		this.studentname = eTO.getStudentname();
		this.specialization = eTO.getSpecialization();
		this.optSubGroup = specId_SubGrp_KeyTO.getDisplay();
		this.specializationId = eTO.getSpecializationId();
		this.optSubGroupId = specId_SubGrp_KeyTO.getId();
//		this.optSubGroupId = specId_SubGrp_KeyTO.getObjId();
		this.specSubGrpId = eTO.getSpecSubGrpId();
		this.admApplnId = eTO.getAdmApplnId();

		// if specId_SubGrp_KeyTO.getDisplay() = eTO's optSubGroup then
		if (this.optSubGroup != null
				&& this.optSubGroup.equalsIgnoreCase(eTO.getOptSubGroup())) {
			this.checkId = eTO.getCheckId();
			if (checkId != null) {
				this.isCheckedDummy = true;
			}else {
				this.isCheckedDummy = false;
			}
		} else {
			this.checkId = null;
			this.isCheckedDummy = false;
		}
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getOptSubGroup() {
		return optSubGroup;
	}

	public void setOptSubGroup(String optSubGroup) {
		this.optSubGroup = optSubGroup;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setSpecializationId(Integer specializationId) {
		this.specializationId = specializationId;
	}

	public Integer getSpecializationId() {
		return specializationId;
	}

	public void setOptSubGroupId(Integer optSubGroupId) {
		this.optSubGroupId = optSubGroupId;
	}

	public Integer getOptSubGroupId() {
		return optSubGroupId;
	}

	public void setIsCheckedDummy(boolean isCheckedDummy) {
		this.isCheckedDummy = isCheckedDummy;
	}

	public boolean getIsCheckedDummy() {
		if (checkId == null) {
			isCheckedDummy = false;
		}
		return isCheckedDummy;
	}

	public void setSpecSubGrpId(Integer specSubGrpId) {
		this.specSubGrpId = specSubGrpId;
	}

	public Integer getSpecSubGrpId() {
		return specSubGrpId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public int getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}
	public int compareTo(ExamOptAssSubTypeTO arg0) {
		if(arg0!=null && this!=null && arg0.getRegisterNo()!=null
				 && this.getRegisterNo()!=null){
				return this.getRegisterNo().compareTo(arg0.getRegisterNo());
		}else
		return 0;
	}
}
