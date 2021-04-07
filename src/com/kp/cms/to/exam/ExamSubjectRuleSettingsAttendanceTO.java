package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsAttendanceTO implements Serializable {
	private String id;
	private HashMap<Integer, String> attendanceTypeList;
	private String attendanceType="";
	private String leaveAttendance;
	private String coCurricularAttendance;
	private String isTheoryPractical;
	private boolean checkedActiveDummyCoCurr;
	private boolean checkedActiveDummyLeave;

	public ExamSubjectRuleSettingsAttendanceTO(String id,
			String attendanceType, HashMap<Integer, String> attendanceTypeList,
			boolean coCurricular, String isTheoryPractical, boolean leave) {
		super();
		this.id = id;
		this.attendanceType = attendanceType;
		this.attendanceTypeList = attendanceTypeList;
		this.isTheoryPractical = isTheoryPractical;

	}

	public ExamSubjectRuleSettingsAttendanceTO() {
	
	}

	public ExamSubjectRuleSettingsAttendanceTO(String attendanceType,
			String coCurricularAttendance, String leaveAttendance,
			String isTheoryPractical) {
		super();
		this.attendanceType = attendanceType;
		this.coCurricularAttendance = coCurricularAttendance;
		this.leaveAttendance = leaveAttendance;
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getCoCurricularAttendance() {
		return coCurricularAttendance;
	}

	public void setCoCurricularAttendance(String coCurricularAttendance) {
		this.coCurricularAttendance = coCurricularAttendance;
	}

	public String getIsTheoryPractical() {
		return isTheoryPractical;
	}

	public void setIsTheoryPractical(String isTheoryPractical) {
		this.isTheoryPractical = isTheoryPractical;
	}

	public String getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}

	public String getLeaveAttendance() {
		return leaveAttendance;
	}

	public void setLeaveAttendance(String leaveAttendance) {
		this.leaveAttendance = leaveAttendance;
	}

	public void setAttendanceTypeList(
			HashMap<Integer, String> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}

	public HashMap<Integer, String> getAttendanceTypeList() {
		return attendanceTypeList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCheckedActiveDummyCoCurr(boolean checkedActiveDummyCoCurr) {
		this.checkedActiveDummyCoCurr = checkedActiveDummyCoCurr;
	}

	public boolean getCheckedActiveDummyCoCurr() {
		return checkedActiveDummyCoCurr;
	}

	public void setCheckedActiveDummyLeave(boolean checkedActiveDummyLeave) {
		this.checkedActiveDummyLeave = checkedActiveDummyLeave;
	}

	public boolean getCheckedActiveDummyLeave() {
		return checkedActiveDummyLeave;
	}

}
