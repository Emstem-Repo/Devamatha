package com.kp.cms.handlers.admission;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.DisciplineAndAchivement;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;
import com.kp.cms.helpers.admission.StudentDisciplinaryDetailsHelper;
import com.kp.cms.helpers.hostel.HostelDisciplinaryDetailsHelper;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.transactions.admission.IStudentDisciplinaryDetailsTransaction;
import com.kp.cms.transactions.hostel.IHostelDisciplinaryDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.StudentDisciplinaryDetailsTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelDisciplinaryDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import common.Logger;

public class StudentDisciplinaryDetailsHandler {
	
	private static final Logger log = Logger.getLogger(StudentDisciplinaryDetailsHandler.class);
	
	public static volatile StudentDisciplinaryDetailsHandler detailsHandler=null;
	IStudentDisciplinaryDetailsTransaction iDetailsTransaction=null;
	
	public StudentDisciplinaryDetailsHandler(){		
	}
	
	public static StudentDisciplinaryDetailsHandler getInstance() {
		if(detailsHandler==null){
			detailsHandler=new StudentDisciplinaryDetailsHandler();			
		}
		return detailsHandler;
	}
	
	public List<DisciplineAndAchivementTo> getStudentDisciplinesList(StudentEditForm studentEditForm) throws Exception{
		log.info("Start of getHostelDisciplinesList of HostelDisciplinaryDetailsHandler");
		
		iDetailsTransaction=StudentDisciplinaryDetailsTransactionImpl.getInstance();
		List<DisciplineAndAchivement> disciplineAndAchivement=iDetailsTransaction.getHostelDisciplinesList(studentEditForm);
		List<DisciplineAndAchivementTo> disciplineAndAchivementToLis=StudentDisciplinaryDetailsHelper.getInstance().
																copyDisciplineListFromBOsToTOs(disciplineAndAchivement);
		log.info("End of getHostelDisciplinesList of HostelDisciplinaryDetailsHandler");		
		return disciplineAndAchivementToLis;
	}

	public boolean addHostelStudentDisciplineDetails(StudentEditForm studentEditForm) throws Exception{
		
		log.info("Start of addHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		boolean addFlag=false;
		DisciplineAndAchivementTo detailsTO=new DisciplineAndAchivementTo();
				
		setDisciplinaryDetailsTOAttributes(studentEditForm,detailsTO);		
		DisciplineAndAchivement bo=StudentDisciplinaryDetailsHelper.getInstance().copyDisciplineDetailsTOtoBO(detailsTO);
		iDetailsTransaction=StudentDisciplinaryDetailsTransactionImpl.getInstance();
		addFlag=iDetailsTransaction.addHostelStudentDisciplineDetails(bo);					
		
		log.info("End of addHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return addFlag;
	}
	
	public void setDisciplinaryDetailsTOAttributes(StudentEditForm studentEditForm,DisciplineAndAchivementTo detailsTO){
		log.info("Start of setDisciplinaryDetailsTOAttributes of HostelDisciplinaryDetailsHandler");
		
		
		detailsTO.setStudentId(studentEditForm.getStudentId());
		detailsTO.setDate(studentEditForm.getDiscipDate());
		detailsTO.setDescryption(studentEditForm.getDisciplineDescy());
		detailsTO.setCreatedBy(studentEditForm.getUserId());
		detailsTO.setLastModifiedBy(studentEditForm.getUserId());
		detailsTO.setCreatedDate(new Date());
		detailsTO.setLastModifiedDate(new Date());
		detailsTO.setType(studentEditForm.getDiscipOrAchieve());
		detailsTO.setActive(true);
		detailsTO.setFileName(studentEditForm.getFileName());
		log.info("End of setDisciplinaryDetailsTOAttributes of HostelDisciplinaryDetailsHandler");
	}
	
	
	/*public HlAdmissionBo checkStudentPresent(String year, String regNo)throws Exception
	{
		log.info("Inside into checkStudentPresent of HostelDisciplinaryDetailsHandler");
        iDetailsTransaction=HostelDisciplinaryDetailsTransactionImpl.getInstance();
		return iDetailsTransaction.checkStudentPresent(year, regNo);
	}*/
	
	
	/*public List<HlDisciplinaryDetailsTO> getDisciplineDetailsAcc(HostelDisciplinaryDetailsForm detailsForm)throws Exception
	{
		log.info("Inside of getHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		List<HlDisciplinaryDetails> disciplineList =iDetailsTransaction.getDisciplineDetailsAcc(detailsForm);
		if(disciplineList!=null && !disciplineList.isEmpty()){
			return HostelDisciplinaryDetailsHelper.getInstance().pupulateDisciplineBOtoTO(disciplineList);
		}
		log.info("Leaving from getHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return new ArrayList<HlDisciplinaryDetailsTO>();
	}*/
	
	public DisciplineAndAchivementTo getDetailsonId(int disId) throws Exception{
		log.info("Inside into getDetailsonId of HostelDisciplinaryDetailsHandler");
		iDetailsTransaction=StudentDisciplinaryDetailsTransactionImpl.getInstance();
		DisciplineAndAchivement DisciplineAndAchivement=iDetailsTransaction.getDetailsonId(disId);
		log.info("End of getDetailsonId of HostelDisciplinaryDetailsHandler");
		return StudentDisciplinaryDetailsHelper.getInstance().populateBotoToEdit(DisciplineAndAchivement);
	}
	
	public boolean updateHostelStudentDisciplineDetails(StudentEditForm studentEditForm)throws Exception
	{
		log.info("Inside of updateHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		DisciplineAndAchivementTo to=new DisciplineAndAchivementTo();
		
		if(studentEditForm!=null){
			
			to.setId(Integer.parseInt(studentEditForm.getDiscipAndAchivId()));
			to.setDescryption(studentEditForm.getDisciplineDescy());
			to.setType(studentEditForm.getDiscipOrAchieve());
			to.setDate(studentEditForm.getDiscipDate());
			to.setStudentId(studentEditForm.getStudentId());
			to.setLastModifiedDate(new Date());
			to.setFileName(studentEditForm.getFileName());
		}
		if(to!=null){
			DisciplineAndAchivement disciplineAndAchivement=StudentDisciplinaryDetailsHelper.getInstance().populateTotoBoUpdate(to);
			if(iDetailsTransaction!=null){
				return iDetailsTransaction.updateHostelStudentDisciplineDetails(disciplineAndAchivement);				
			}
		}
		log.info("Leaving of updateHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return false;
	}
	
	public boolean deleteStudentDisciplineDetails(int disciplineId, String userId)throws Exception{
		log.info("Inside of deleteHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		if(iDetailsTransaction!=null){
			return iDetailsTransaction.deleteStudentDisciplineDetails(disciplineId, userId);
		}
		log.info("Leaving of deleteHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return false;
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc(BaseActionForm actionForm) throws Exception
	{
		IHostelDisciplinaryDetailsTransaction iDetailsTransaction = HostelDisciplinaryDetailsTransactionImpl.getInstance();
		 HlAdmissionBo hlAdmissionBo=iDetailsTransaction.verifyRegisterNumberAndGetNameAcc(actionForm);
		return hlAdmissionBo;
		
	}
	
	public HlAdmissionBo verifyRegisterNumberAndGetNameAcc1(HostelDisciplinaryDetailsForm byForm) throws Exception
	{
		IHostelDisciplinaryDetailsTransaction iDetailsTransaction = HostelDisciplinaryDetailsTransactionImpl.getInstance();
		 HlAdmissionBo hlAdmissionBo=iDetailsTransaction.verifyRegisterNumberAndGetNameAcc1(byForm);
		return hlAdmissionBo;
		
	}

	/**
	 * @param hldForm
	 * @return
	 * @throws Exception
	 */
	public List<HlDisciplinaryDetailsTO> searchDisciplinaryDetailsByRegNoAndAcademicYear( HostelDisciplinaryDetailsForm hldForm) throws Exception{
		IHostelDisciplinaryDetailsTransaction iDetailsTransaction = HostelDisciplinaryDetailsTransactionImpl.getInstance();
		List<HlDisciplinaryDetails> disciplineList = iDetailsTransaction.getDisciplinaryDetailsByRegNoAndYear(hldForm);
		if(disciplineList!=null && !disciplineList.isEmpty()){
			return HostelDisciplinaryDetailsHelper.getInstance().pupulateDisciplineBOtoTO(disciplineList);
		}
		log.info("Leaving from searchDisciplinaryDetailsByRegNoAndAcademicYear of HostelDisciplinaryDetailsHandler");
		return new ArrayList<HlDisciplinaryDetailsTO>();
	}

	public boolean saveDocument(StudentEditForm studentEditForm) {
		StudentDisciplinaryDetailsTransactionImpl.getInstance().saveDocument(studentEditForm);
		return true;
	}
	
}