package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.forms.attendance.AttendanceTypeForm;

public interface IAttendanceTypeTransaction {
	
	/**
	 * Used in inserting attendanceTypes
	 */
	public boolean saveAttendanceType(AttendanceType attendanceType)throws Exception;
	
	/**
	 * Used in viewing all the attendanceType present in DB
	 */
	public List<AttendanceType>getAttendanceTypesAll()throws Exception;
	
	public List<AttendanceType> getAttendanceType() throws Exception;
	
	/**
	 * Used in delete
	 */
	public boolean deleteAttendanceType(int id,Boolean activate,AttendanceTypeForm attendanceTypeForm)throws Exception;
	
	/**
	 * Used while duplicate checking if default is true
	 */
	public AttendanceType getAttendanceTypeOnDefault(boolean defaultValue)throws Exception;
	
	/**
	 * Used in edit operation
	 * Gets details based on Id
	 */
	
	public AttendanceType getDetailsById(int id)throws Exception;
	
	/**
	 * Used while update
	 */
	public boolean updateAttendanceType(AttendanceType attendanceType)throws Exception;
	/**
	 * Check duplicate for attendance type based on the name
	 */
	public AttendanceType getAttendanceTypeDetailsonAttendanceType(String name)throws Exception;
}
