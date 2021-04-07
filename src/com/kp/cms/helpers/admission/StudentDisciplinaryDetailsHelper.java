package com.kp.cms.helpers.admission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.DisciplineAndAchivement;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlDisciplinaryType;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.helpers.exam.ValuationScheduleHelper;
import com.kp.cms.to.admission.DisciplineAndAchivementTo;
import com.kp.cms.to.hostel.DisciplinaryTypeTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;
import common.Logger;

public class StudentDisciplinaryDetailsHelper {
	
	private static final Logger log = Logger.getLogger(StudentDisciplinaryDetailsHelper.class);
	
	public static volatile StudentDisciplinaryDetailsHelper hostelDisciplinaryDetailsHelper;
	public static StudentDisciplinaryDetailsHelper getInstance(){
		if(hostelDisciplinaryDetailsHelper==null){
			hostelDisciplinaryDetailsHelper=new StudentDisciplinaryDetailsHelper();
		}
		return hostelDisciplinaryDetailsHelper;		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	
	public List<DisciplineAndAchivementTo> copyDisciplineListFromBOsToTOs(List<DisciplineAndAchivement> boList) throws Exception{
		log.info("Start of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		
		List<DisciplineAndAchivementTo> disciplineAndAchivementToList=new ArrayList<DisciplineAndAchivementTo>();
		java.util.Iterator<DisciplineAndAchivement> iterator=boList.iterator();
		DisciplineAndAchivement bo=null;
		
		DisciplineAndAchivementTo detailsTO=null;
		while(iterator.hasNext()){
			detailsTO=new DisciplineAndAchivementTo();
			bo=iterator.next();	
			Date date=bo.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		    String strDate = formatter.format(date);  
			detailsTO.setDate(strDate);
			detailsTO.setStudentId(bo.getStudent().getId());
			detailsTO.setId(bo.getId());
			detailsTO.setDescryption(bo.getDescryption());
			detailsTO.setActive(bo.isActive());
			detailsTO.setType(bo.getType());
			if (bo.getFileName()!=null && !bo.getFileName().isEmpty() ) {
				detailsTO.setFileName(bo.getFileName());
			}
			detailsTO.setStudentId(bo.getStudent().getId());
			detailsTO.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			disciplineAndAchivementToList.add(detailsTO);
		}
		log.info("End of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		return disciplineAndAchivementToList;		
	}
		
	public DisciplineAndAchivement copyDisciplineDetailsTOtoBO(DisciplineAndAchivementTo to) throws Exception{
		log.info("Start of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		
		DisciplineAndAchivement bo=new DisciplineAndAchivement();
		HlDisciplinaryType hlDisciplinaryType=new HlDisciplinaryType();
		HlAdmissionBo hlAdmissionBo = new HlAdmissionBo();
		Student student=new Student();
		bo.setDate(CommonUtil.ConvertStringToSQLDate(to.getDate()));
		bo.setCreatedBy(to.getCreatedBy());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedBy(to.getLastModifiedBy());
		bo.setLastModifiedDate(new Date());
		bo.setActive(true);
		bo.setDescryption(to.getDescryption());
		student.setId(to.getStudentId());
		bo.setStudent(student);
		bo.setFileName(to.getFileName());
		bo.setType(to.getType());
		
		log.info("End of copyDisciplineListFromBOsToTOs of HostelDisciplinaryDetailsHelper");
		return bo;
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
	
	public DisciplineAndAchivementTo populateBotoToEdit(DisciplineAndAchivement bo)throws Exception {
		log.info("Entering into populateBotoToEdit of HostelDisciplinaryDetailsHelper");
		DisciplineAndAchivementTo detailsTO = new DisciplineAndAchivementTo();
		if (bo != null) {
			detailsTO.setDate(bo.getDate().toString());
			detailsTO.setStudentId(bo.getStudent().getId());
			detailsTO.setId(bo.getId());
			detailsTO.setDescryption(bo.getDescryption());
			detailsTO.setType(bo.getType());
			if (bo.getFileName()!=null && !bo.getFileName().isEmpty() ) {
				detailsTO.setFileName(bo.getFileName());
			}
		}
		if (detailsTO != null) {
			return detailsTO;
		}
		log.info("Leaving from populateBotoToEdit of HostelDisciplinaryDetailsHelper");
		return null;
	}
	
	public DisciplineAndAchivement populateTotoBoUpdate(DisciplineAndAchivementTo byTO) throws Exception{
		log.info("Entering into populateTotoBoUpdate of HostelDisciplinaryDetailsHelper");
		
		if (byTO != null) {
			DisciplineAndAchivement bo = new DisciplineAndAchivement();
			Student student=new Student();
			student.setId(byTO.getStudentId());
				bo.setId(byTO.getId());
				bo.setType(byTO.getType());
				bo.setStudent(student);
				bo.setDescryption(byTO.getDescryption());
				bo.setDate(CommonUtil.ConvertStringToSQLDate(byTO.getDate()));
				bo.setLastModifiedDate(byTO.getLastModifiedDate());
				bo.setActive(true);
				bo.setFileName(byTO.getFileName());
			
			if (bo != null) {
				return bo;
			}
		}
		log.info("Leaving from populateTotoBoUpdate of HostelDisciplinaryDetailsHelper");
		return null;
	}
}