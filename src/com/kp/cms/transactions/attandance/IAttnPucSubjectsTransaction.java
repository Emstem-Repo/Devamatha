package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucCetMarks;
import com.kp.cms.bo.admin.AttnPucDefineRange;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.forms.attendance.AttnPucSubjectForm;

public interface IAttnPucSubjectsTransaction {

	public boolean addPucSubjects(List<AttnPucSubjects> pucSubjects)throws Exception;
	public boolean addPucCetMarks(List<AttnPucCetMarks> list,AttnPucSubjectForm attnPucSubjectForm)throws Exception;
	public List<AttnPucCetMarks> getAttnCetMarks(AttnPucSubjectForm attnPucSubjectForm)throws Exception;
	public AttnPucSubjects getAttnSubject(String className)throws Exception;
	public boolean addAttDefineRange(List<AttnPucDefineRange> list)throws Exception;
}
