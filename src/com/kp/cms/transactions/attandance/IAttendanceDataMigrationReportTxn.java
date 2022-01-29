package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.bo.admin.InternalMarkUpload;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;

public interface IAttendanceDataMigrationReportTxn {

	public List<AttnBiodataPuc> getAttnBioDataDetails(StringBuffer query)throws Exception;
	public List<AttnMarksUpload> getAttendanceMarks(AttendanceDataMigrationForm attnDataMigrationForm)throws Exception;
	public String getSecondLang(String regNo, String year)throws Exception;
	public AttnPucSubjects getAttnSubject(String className)throws Exception;
	public Map<String,String> getClasses(String mode)throws Exception;
	public Map<String,String> getTestIdent(String mode)throws Exception;

	public List<InternalMarkUpload> getInternalMarks(AttendanceDataMigrationForm attnDataMigrationForm) throws Exception;
}
