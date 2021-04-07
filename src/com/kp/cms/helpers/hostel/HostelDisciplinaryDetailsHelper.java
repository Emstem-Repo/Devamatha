package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.helpers.exam.ValuationScheduleHelper;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;
import common.Logger;

public class HostelDisciplinaryDetailsHelper {
	
	private static final Logger log = Logger.getLogger(HostelDisciplinaryDetailsHelper.class);
	
	public static volatile HostelDisciplinaryDetailsHelper hostelDisciplinaryDetailsHelper;
	public static HostelDisciplinaryDetailsHelper getInstance(){
		if(hostelDisciplinaryDetailsHelper==null){
			hostelDisciplinaryDetailsHelper=new HostelDisciplinaryDetailsHelper();
		}
		return hostelDisciplinaryDetailsHelper;		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	
	public List<HlDisciplinaryDetailsTO> copyDisciplineListFromBOsToTOs(List<HlDisciplinaryType> disciplinaryTypeList) throws Exception{
		log.info("Start of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		
		List<HlDisciplinaryDetailsTO> disciplinaryTypeTOList=new ArrayList<HlDisciplinaryDetailsTO>();
		java.util.Iterator<HlDisciplinaryType> iterator=disciplinaryTypeList.iterator();
		HlDisciplinaryType hlDisciplinaryType=null;
		
		HlDisciplinaryDetailsTO disciplinaryTypeTO=null;
		while(iterator.hasNext()){
			disciplinaryTypeTO=new HlDisciplinaryDetailsTO();
			hlDisciplinaryType=iterator.next();			
			disciplinaryTypeTO.setId(hlDisciplinaryType.getId());
			if(hlDisciplinaryType.getName()!=null){
				disciplinaryTypeTO.setDisciplineTypeName(hlDisciplinaryType.getName());
			}
			disciplinaryTypeTOList.add(disciplinaryTypeTO);
		}
		log.info("End of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		return disciplinaryTypeTOList;		
	}
		
	public HlDisciplinaryDetails copyDisciplineDetailsTOtoBO(HlDisciplinaryDetailsTO hlDisciplinaryDetailsTO) throws Exception{
		log.info("Start of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		
		HlDisciplinaryDetails hlDisciplinaryDetails=new HlDisciplinaryDetails();
		HlDisciplinaryType hlDisciplinaryType=new HlDisciplinaryType();
		HlAdmissionBo hlAdmissionBo = new HlAdmissionBo();
		
		hlDisciplinaryDetails.setDate(CommonUtil.ConvertStringToSQLDate(hlDisciplinaryDetailsTO.getDate()));
		hlDisciplinaryDetails.setCreatedBy(hlDisciplinaryDetailsTO.getCreatedBy());
		hlDisciplinaryDetails.setCreatedDate(new Date());
		hlDisciplinaryDetails.setModifiedBy(hlDisciplinaryDetailsTO.getModifiedBy());
		hlDisciplinaryDetails.setLastModifiedDate(new Date());
		hlDisciplinaryDetails.setIsActive(true);
		hlDisciplinaryDetails.setComments(hlDisciplinaryDetailsTO.getDescription());
		
		hlDisciplinaryType.setId(hlDisciplinaryDetailsTO.getDisciplinaryTypeTO().getId());
		hlDisciplinaryDetails.setHlDisciplinaryType(hlDisciplinaryType);
		
		hlAdmissionBo.setId(hlDisciplinaryDetailsTO.getHlAdmissionTo().getId());
		hlDisciplinaryDetails.setHlAdmissionBo(hlAdmissionBo);
		
		log.info("End of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		return hlDisciplinaryDetails;
	}
	
	public List<HlDisciplinaryDetailsTO> pupulateDisciplineBOtoTO(List<HlDisciplinaryDetails> disciplineList)throws Exception {
		log.info("Entering into pupulateExamValuatorsBOtoTO of ExternalEvaluatorHelper");
		HlDisciplinaryDetailsTO hlDisciplinaryDetailsTO = null;
		DisciplinaryTypeTO disciplinaryTypeTO = null;

		List<HlDisciplinaryDetailsTO> newDiscList = new ArrayList<HlDisciplinaryDetailsTO>();
		if (disciplineList != null && !disciplineList.isEmpty()) {
			Iterator<HlDisciplinaryDetails> iterator = disciplineList.iterator();
			while (iterator.hasNext()) {
				HlDisciplinaryDetails hlDisciplinaryDetails = iterator.next();
				hlDisciplinaryDetailsTO = new HlDisciplinaryDetailsTO();
				
					hlDisciplinaryDetailsTO.setId(hlDisciplinaryDetails.getId());
					hlDisciplinaryDetailsTO.setDescription(hlDisciplinaryDetails.getComments());
					hlDisciplinaryDetailsTO.setRegisterNo(hlDisciplinaryDetails.getHlAdmissionBo().getStudentId().getRegisterNo());
					hlDisciplinaryDetailsTO.setDate(CommonUtil.formatDates(hlDisciplinaryDetails.getDate()));
					
					disciplinaryTypeTO = new DisciplinaryTypeTO();
					if(hlDisciplinaryDetails.getHlDisciplinaryType() != null){
						disciplinaryTypeTO.setId(hlDisciplinaryDetails.getHlDisciplinaryType().getId());
						hlDisciplinaryDetailsTO.setDisciplineTypeName(hlDisciplinaryDetails.getHlDisciplinaryType().getName());
					}
					hlDisciplinaryDetailsTO.setDisciplinaryTypeTO(disciplinaryTypeTO);
					newDiscList.add(hlDisciplinaryDetailsTO);
				
			}
		}
		log.info("Leaving from pupulateDisciplineBOtoTO of HostelDisciplinaryDetailsHelper");
		return newDiscList;
	}
	
	public HlDisciplinaryDetailsTO populateBotoToEdit(HlDisciplinaryDetails hld)throws Exception {
		log.info("Entering into populateBotoToEdit of HostelDisciplinaryDetailsHelper");
		HlDisciplinaryDetailsTO hldTO = new HlDisciplinaryDetailsTO();
		if (hld != null) {
			hldTO.setId(hld.getId());
			hldTO.setDescription(hld.getComments());
			if(hld.getHlAdmissionBo()!=null){
				hldTO.setYear(hld.getHlAdmissionBo().getAcademicYear());
				hldTO.setRegisterNo(hld.getHlAdmissionBo().getStudentId().getRegisterNo());
			}
			
			HlAdmissionTo hlAdmissionTo = new HlAdmissionTo();
			if(hld.getHlAdmissionBo()!=null){
				hlAdmissionTo.setId(hld.getHlAdmissionBo().getId());
				hldTO.setHlAdmissionTo(hlAdmissionTo);
			}
			
			DisciplinaryTypeTO disciplinaryTypeTO = new DisciplinaryTypeTO();
			if(hld.getHlDisciplinaryType()!=null){
				disciplinaryTypeTO.setId(hld.getHlDisciplinaryType().getId());
				disciplinaryTypeTO.setName(hld.getHlDisciplinaryType().getName());
			}
			
			hldTO.setDisciplinaryTypeTO(disciplinaryTypeTO);
			
			String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(hld.getDate()), HostelDisciplinaryDetailsHelper.SQL_DATEFORMAT,	HostelDisciplinaryDetailsHelper.FROM_DATEFORMAT);
			hldTO.setDate(date);
			
//			hldTO.setDate(hld.getDate());
			
		}
		if (hldTO != null) {
			return hldTO;
		}
		log.info("Leaving from populateBotoToEdit of HostelDisciplinaryDetailsHelper");
		return null;
	}
	
	public HlDisciplinaryDetails populateTotoBoUpdate(HlDisciplinaryDetailsTO byTO) throws Exception{
		log.info("Entering into populateTotoBoUpdate of HostelDisciplinaryDetailsHelper");
		
		if (byTO != null) {
			HlDisciplinaryDetails hldDetails = new HlDisciplinaryDetails();
			HlDisciplinaryType hlDisciplinaryType=new HlDisciplinaryType();
			HlAdmissionBo hlAdmissionBo = new HlAdmissionBo();
			
			hldDetails.setId(byTO.getId());
			hldDetails.setDate(CommonUtil.ConvertStringToSQLDate(byTO.getDate()));
			hldDetails.setComments(byTO.getDescription());
			
			hlAdmissionBo.setId(byTO.getHlAdmissionTo().getId());
			hldDetails.setHlAdmissionBo(hlAdmissionBo);
			hlDisciplinaryType.setId(byTO.getDisciplinaryTypeTO().getId());
			hldDetails.setHlDisciplinaryType(hlDisciplinaryType);
			hldDetails.setIsActive(true);
			hldDetails.setLastModifiedDate(new Date());
			hldDetails.setModifiedBy(byTO.getModifiedBy());
			if (hldDetails != null) {
				return hldDetails;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of HostelDisciplinaryDetailsHelper");
		return null;
	}
}