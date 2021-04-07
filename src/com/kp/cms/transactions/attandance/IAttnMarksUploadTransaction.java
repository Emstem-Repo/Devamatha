package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.PucAttnInternalMarks;
import com.kp.cms.bo.admin.PucttnAttendance;
import com.kp.cms.forms.attendance.AttnMarksUploadForm;
import com.kp.cms.to.attendance.AttnMarksUploadTo;

public interface IAttnMarksUploadTransaction {

	public boolean addAttnMarksUpload( List<AttnMarksUpload> marksUploadsList) throws Exception;

	public boolean addPucttnAttendanceUpload( List<PucttnAttendance> pucttnAttendances)throws Exception;
	
	public boolean addAttnInternalMarks( List<PucAttnInternalMarks> internalMarksList, AttnMarksUploadForm attnMarksUploadForm) throws Exception;

}
