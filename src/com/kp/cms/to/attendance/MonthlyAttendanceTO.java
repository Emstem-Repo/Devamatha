package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.bo.admin.Student;

public class MonthlyAttendanceTO implements Serializable,Comparable<MonthlyAttendanceTO> {
	
	private String attendanceId;
	
	private String registerNo;
	
	private String  studentName;
	
	private List<HourseHeldTO> hoursHeldTOList;
	
	private int dayTabIndex;
	
	public List<HourseHeldTO> getHoursHeldTOList() {
		return hoursHeldTOList;
	}

	public void setHoursHeldTOList(List<HourseHeldTO> hoursHeldTOList) {
		this.hoursHeldTOList = hoursHeldTOList;
	}

	private Student student;

	

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getDayTabIndex() {
		return dayTabIndex;
	}

	public void setDayTabIndex(int dayTabIndex) {
		this.dayTabIndex = dayTabIndex;
	}

	@Override
	public int compareTo(MonthlyAttendanceTO arg0) {
		if (arg0 != null    && this != null  ) {
			if(arg0.getRegisterNo() == null) {
				arg0.setRegisterNo("");
			}
			if(this.getRegisterNo() == null) {
				this.setRegisterNo("");
			}
			return this.getRegisterNo().compareTo(arg0.getRegisterNo());
		}
		return 0;
	}

	

}
