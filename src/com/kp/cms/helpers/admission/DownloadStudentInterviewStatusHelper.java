package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GenerateProcess;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.admission.DownloadStudentInterviewStatusForm;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;

public class DownloadStudentInterviewStatusHelper {
	/**
	 * Singleton object of DownloadStudentInterviewStatusHelper
	 */
	private static volatile DownloadStudentInterviewStatusHelper helper = null;
	private static final Log log = LogFactory.getLog(DownloadStudentInterviewStatusHelper.class);
	private DownloadStudentInterviewStatusHelper() {
		
	}
	/**
	 * return singleton object of DownloadStudentInterviewStatusHelper.
	 * @return
	 */
	public static DownloadStudentInterviewStatusHelper getInstance() {
		if (helper == null) {
			helper = new DownloadStudentInterviewStatusHelper();
		}
		return helper;
	}
	/**
	 * @param downloadStudentInterviewStatusForm
	 * @return
	 * @throws Exception
	 */
	public String getsearchQuery(
			DownloadStudentInterviewStatusForm downloadStudentInterviewStatusForm) throws Exception{
		String query="select g from GenerateProcess g where g.admAppln.isCancelled=0 and g.admAppln.appliedYear="+downloadStudentInterviewStatusForm.getYear()+
		" and g.admAppln.courseBySelectedCourseId.program.id="+downloadStudentInterviewStatusForm.getProgramId()+
		" and g.admAppln.courseBySelectedCourseId.program.programType.id="+downloadStudentInterviewStatusForm.getProgramTypeId();
		if(downloadStudentInterviewStatusForm.getCourseId()!=null && !downloadStudentInterviewStatusForm.getCourseId().isEmpty()){
			query=query+" and g.admAppln.courseBySelectedCourseId.id="+downloadStudentInterviewStatusForm.getCourseId();
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AdmApplnTO> convertBotoTo(List<GenerateProcess> list) throws Exception {
		List<AdmApplnTO> finalList=new ArrayList<AdmApplnTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<GenerateProcess> itr=list.iterator();
			while (itr.hasNext()) {
				GenerateProcess bo = (GenerateProcess) itr.next();
				AdmApplnTO to=new AdmApplnTO();
				to.setApplnNo(bo.getApplnNo());
				PersonalDataTO pto=new PersonalDataTO();
				PersonalData pbo=bo.getAdmAppln().getPersonalData();
				if(pbo!=null){
					pto.setFirstName(pbo.getFirstName());
					if(pbo.getMobileNo2()!=null){
						pto.setMobileNo(pbo.getMobileNo2());
					}
				}
				to.setPersonalData(pto);
				if(bo.getStatus()!=null && !bo.getStatus().isEmpty()){
					to.setStatus(bo.getStatus());
				}else{
					to.setStatus(bo.getInterviewComments());
				}
				finalList.add(to);
			}
		}
		return finalList;
	}
}
