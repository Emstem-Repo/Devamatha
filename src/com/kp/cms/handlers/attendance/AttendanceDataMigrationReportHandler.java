package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.InternalMarkUpload;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.helpers.attendance.AttendanceDataMigrationReportHelper;
import com.kp.cms.to.attendance.AttendanceBioDataTo;
import com.kp.cms.to.attendance.AttnBioDataPucTo;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn;
import com.kp.cms.transactionsimpl.admission.PromoteMarksUploadTxnImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceDataMigrationReportTxnImpl;

public class AttendanceDataMigrationReportHandler {
	private static volatile AttendanceDataMigrationReportHandler handler = null;
	public static AttendanceDataMigrationReportHandler getInstance(){
		if(handler == null){
			handler = new AttendanceDataMigrationReportHandler();
			return handler;
		}
		return handler;
	}
	IAttendanceDataMigrationReportTxn transaction = AttendanceDataMigrationReportTxnImpl.getInstance();
	/**
	 * @param attnDataMigrationForm
	 * @return
	 * @throws Exception
	 */
	public List<AttnBioDataPucTo> getAttnBioDataSearch( AttendanceDataMigrationForm attnDataMigrationForm) throws Exception{
		AttendanceDataMigrationReportHelper helper = AttendanceDataMigrationReportHelper.getInstance();
		StringBuffer query = helper.getSelectedQuery(attnDataMigrationForm);
		List<AttnBiodataPuc> listBo = transaction.getAttnBioDataDetails(query);
		List<AttnBioDataPucTo> bioDataPucTos = helper.convertBOToTO(listBo);
		return bioDataPucTos;
	}
	/**
	 * @param attnDataMigrationForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel( AttendanceDataMigrationForm attnDataMigrationForm,
			HttpServletRequest request)throws Exception {
		boolean isUpdated = false;
		AttendanceDataMigrationReportHelper helper = AttendanceDataMigrationReportHelper.getInstance();
		AttendanceBioDataTo attBioDataTo = helper.getInstance().selectedColumn(attnDataMigrationForm);
		isUpdated = helper.populateBOToTO(attBioDataTo,attnDataMigrationForm,request);
		return isUpdated;
	}
	
	public List<AttnMarksUploadTo> getAttendanceMarks(AttendanceDataMigrationForm attnDataMigrationForm,HttpSession session)throws Exception{
		AttendanceDataMigrationReportHelper.getInstance().setSubjectNamesToSession(attnDataMigrationForm, session);
		List<AttnMarksUpload> attnMarksList=transaction.getAttendanceMarks(attnDataMigrationForm);
		List<AttnMarksUploadTo> attnMarksToList=AttendanceDataMigrationReportHelper.getInstance().convertAttnMarksBoToTO(attnMarksList);
		return attnMarksToList;
	}
	public Map<String,String> getClasses(String mode)throws Exception{
		Map<String,String> classMap=transaction.getClasses(mode);
		return classMap;
	}
	public Map<String,String> getTestIdents(String mode)throws Exception{
		Map<String,String> testMap=transaction.getTestIdent(mode);
		return testMap;
	}
	public List<AttnMarksUploadTo> getInternalMarks(AttendanceDataMigrationForm attnDataMigrationForm,HttpSession session)throws Exception{
		AttendanceDataMigrationReportHelper.getInstance().setSubjectNamesToSession(attnDataMigrationForm, session);
		List<InternalMarkUpload> interMarksList=transaction.getInternalMarks(attnDataMigrationForm);
		List<AttnMarksUploadTo> interMarksToList=AttendanceDataMigrationReportHelper.getInstance().convertInterMarksBoToTO(interMarksList);
		return interMarksToList;
	}
}
