package com.kp.cms.to.attendance;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kp.cms.to.admin.SubjectTO;

public class AttendanceReEntryTo implements Serializable,Comparable<AttendanceReEntryTo> {
	private String attendanceDate;
	private List<SubjectTO> subList;
	
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public List<SubjectTO> getSubList() {
		return subList;
	}
	public void setSubList(List<SubjectTO> subList) {
		this.subList = subList;
	}
	@Override
	public int compareTo(AttendanceReEntryTo arg0) {
		if(arg0!=null ){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Date startTime;
			try {
				startTime = dateFormat.parse(this.getAttendanceDate());
				Date startTime1 = dateFormat.parse(arg0.getAttendanceDate());
				if(startTime.compareTo(startTime1) > 0)
					return 1;
				else if(startTime.compareTo(startTime1) < 0){
					return -1;
				}else
					return 0;
			} catch (ParseException e) {
			}
		}	
		return 0;
	}
	
}
