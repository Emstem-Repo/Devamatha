package com.kp.cms.handlers.hostel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.HostelDisciplinaryDetailsForm;
import com.kp.cms.helpers.hostel.HostelDisciplinaryDetailsHelper;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.transactions.hostel.IHostelDisciplinaryDetailsTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelDisciplinaryDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import common.Logger;

public class HostelDisciplinaryDetailsHandler {
	
	private static final Logger log = Logger.getLogger(HostelDisciplinaryDetailsHandler.class);
	
	public static volatile HostelDisciplinaryDetailsHandler detailsHandler=null;
	IHostelDisciplinaryDetailsTransaction iDetailsTransaction=null;
	
	public HostelDisciplinaryDetailsHandler(){		
	}
	
	public static HostelDisciplinaryDetailsHandler getInstance() {
		if(detailsHandler==null){
			detailsHandler=new HostelDisciplinaryDetailsHandler();			
		}
		return detailsHandler;
	}
	
	public List<HlDisciplinaryDetailsTO> getHostelDisciplinesList() throws Exception{
		log.info("Start of getHostelDisciplinesList of HostelDisciplinaryDetailsHandler");
		
		iDetailsTransaction=HostelDisciplinaryDetailsTransactionImpl.getInstance();
		List<HlDisciplinaryType> disciplinaryTypeList=iDetailsTransaction.getHostelDisciplinesList();
		List<HlDisciplinaryDetailsTO> disciplinaryTypeTOList=HostelDisciplinaryDetailsHelper.getInstance().
																copyDisciplineListFromBOsToTOs(disciplinaryTypeList);
		log.info("End of getHostelDisciplinesList of HostelDisciplinaryDetailsHandler");		
		return disciplinaryTypeTOList;
	}

	public boolean addHostelStudentDisciplineDetails(HostelDisciplinaryDetailsForm detailsForm) throws Exception{
		
		log.info("Start of addHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		boolean addFlag=false;
		HlDisciplinaryDetailsTO detailsTO=new HlDisciplinaryDetailsTO();
				
		setDisciplinaryDetailsTOAttributes(detailsForm,detailsTO);		
		HlDisciplinaryDetails hlDisciplinaryDetails=HostelDisciplinaryDetailsHelper.getInstance().copyDisciplineDetailsTOtoBO(detailsTO);
		iDetailsTransaction=HostelDisciplinaryDetailsTransactionImpl.getInstance();
		addFlag=iDetailsTransaction.addHostelStudentDisciplineDetails(hlDisciplinaryDetails);					
		
		log.info("End of addHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return addFlag;
	}
	
	public void setDisciplinaryDetailsTOAttributes(HostelDisciplinaryDetailsForm detailsForm,HlDisciplinaryDetailsTO detailsTO){
		log.info("Start of setDisciplinaryDetailsTOAttributes of HostelDisciplinaryDetailsHandler");
		
		HlAdmissionTo hlAdmissionTo = new HlAdmissionTo();
		hlAdmissionTo.setId(detailsForm.getBoId());
		detailsTO.setHlAdmissionTo(hlAdmissionTo);
		
		DisciplinaryTypeTO disciplinaryTypeTO = new DisciplinaryTypeTO();
		disciplinaryTypeTO.setId(Integer.parseInt(detailsForm.getDisciplineId()));
		detailsTO.setDisciplinaryTypeTO(disciplinaryTypeTO);
		
		detailsTO.setDate(detailsForm.getDate());
		detailsTO.setDescription(detailsForm.getDescription());
		detailsTO.setCreatedBy(detailsForm.getUserId());
		detailsTO.setModifiedBy(detailsForm.getUserId());
		detailsTO.setCreatedDate(new Date());
		detailsTO.setLastModifiedDate(new Date());
		detailsTO.setIsActive(true);
		log.info("End of setDisciplinaryDetailsTOAttributes of HostelDisciplinaryDetailsHandler");
	}
	
	
	public HlAdmissionBo checkStudentPresent(String year, String regNo)throws Exception
	{
		log.info("Inside into checkStudentPresent of HostelDisciplinaryDetailsHandler");
        iDetailsTransaction=HostelDisciplinaryDetailsTransactionImpl.getInstance();
		return iDetailsTransaction.checkStudentPresent(year, regNo);
	}
	
	
	public List<HlDisciplinaryDetailsTO> getDisciplineDetailsAcc(HostelDisciplinaryDetailsForm detailsForm)throws Exception
	{
		log.info("Inside of getHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		List<HlDisciplinaryDetails> disciplineList =iDetailsTransaction.getDisciplineDetailsAcc(detailsForm);
		if(disciplineList!=null && !disciplineList.isEmpty()){
			return HostelDisciplinaryDetailsHelper.getInstance().pupulateDisciplineBOtoTO(disciplineList);
		}
		log.info("Leaving from getHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return new ArrayList<HlDisciplinaryDetailsTO>();
	}
	
	public HlDisciplinaryDetailsTO getDetailsonId(int disId) throws Exception{
		log.info("Inside into getDetailsonId of HostelDisciplinaryDetailsHandler");
		IHostelDisciplinaryDetailsTransaction transaction = HostelDisciplinaryDetailsTransactionImpl.getInstance();
		HlDisciplinaryDetails hlDisciplinaryDetails=transaction.getDetailsonId(disId);
		log.info("End of getDetailsonId of HostelDisciplinaryDetailsHandler");
		return HostelDisciplinaryDetailsHelper.getInstance().populateBotoToEdit(hlDisciplinaryDetails);
	}
	
	public boolean updateHostelStudentDisciplineDetails(HostelDisciplinaryDetailsForm byForm)throws Exception
	{
		log.info("Inside of updateHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		HlDisciplinaryDetailsTO byTO=new HlDisciplinaryDetailsTO();
		
		if(byForm!=null){
			
			byTO.setId(byForm.getId());
			byTO.setDescription(byForm.getDescription());
			HlAdmissionTo hlAdmissionTo = new HlAdmissionTo();
			hlAdmissionTo.setId(byForm.getBoId());
			byTO.setHlAdmissionTo(hlAdmissionTo);
			
			DisciplinaryTypeTO disciplinaryTypeTO = new DisciplinaryTypeTO();
			disciplinaryTypeTO.setId(Integer.parseInt(byForm.getDisciplineId()));
			byTO.setDisciplinaryTypeTO(disciplinaryTypeTO);
			
			byTO.setDate(byForm.getDate());
			byTO.setModifiedBy(byForm.getUserId());
			byTO.setLastModifiedDate(new Date());
		}
		if(byTO!=null){
			HlDisciplinaryDetails hlDisciplinaryDetails=HostelDisciplinaryDetailsHelper.getInstance().populateTotoBoUpdate(byTO);
			if(iDetailsTransaction!=null){
				return iDetailsTransaction.updateHostelStudentDisciplineDetails(hlDisciplinaryDetails);				
			}
		}
		log.info("Leaving of updateHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		return false;
	}
	
	public boolean deleteHostelStudentDisciplineDetails(int disciplineId, String userId)throws Exception{
		log.info("Inside of deleteHostelStudentDisciplineDetails of HostelDisciplinaryDetailsHandler");
		if(iDetailsTransaction!=null){
			return iDetailsTransaction.deleteHostelStudentDisciplineDetails(disciplineId, userId);
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
	
}