package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;

public interface IConductCertificateTransaction {
	public List<ConductCertificateDetails> getStudentList(String classId,String fromReg, String toReg,String studentId)throws Exception;
	public List<StudentTCDetails> getStudentsByName(String name, String classId) throws Exception;
	public int getClassTermNumber(int classId) throws Exception;
	public List<String> getStudentroleNo(String classes, String fromUsn, String toUsn, String studentId);
	public int getPtype(String classes);
}
