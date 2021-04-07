package com.kp.cms.handlers.serviceLearning;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.serviceLearning.DepartmentNameEntry;
import com.kp.cms.bo.serviceLearning.ProgrammeEntryBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningActivityBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryAdminBo;
import com.kp.cms.bo.serviceLearning.ServiceLearningMarksEntryBo;
import com.kp.cms.forms.serviceLearning.ServiceLearningMarksEntryForm;
import com.kp.cms.helpers.serviceLearning.ServiceLearningMarksEntryHelper;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningmarksOverallEntryTo;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.serviceLearning.IServiceLearningMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.serviceLearning.ServiceLearningMarksEntryTransactionImpl;

public class ServiceLearningMarksEntryHandler {
	
	private static volatile ServiceLearningMarksEntryHandler serviceLearningMarksEntryHandler = null;
	private ServiceLearningMarksEntryHandler(){

	}
	public static ServiceLearningMarksEntryHandler getInstance() {
		if (serviceLearningMarksEntryHandler == null) {
			serviceLearningMarksEntryHandler = new ServiceLearningMarksEntryHandler();
		}
		return serviceLearningMarksEntryHandler;
	}
	
	public ProgrammeEntryBo getProgrammesById(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		ProgrammeEntryBo programmeEntryBo = transaction.getProgrammesById(serviceLearningMarksEntryForm);
		return programmeEntryBo;
	}
	
	public void getServiceLearningActivityByProgrameId(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		List<ServiceLearningActivityBo> serviceLearningActivityBoList = transaction.getServiceLearningActivityByProgrameId(serviceLearningMarksEntryForm);
		List<ServiceLearningActivityTo> serviceLearningActivityToList=ServiceLearningMarksEntryHelper.getInstance().convertBOsToTos(serviceLearningActivityBoList);
		ProgrammeEntryBo programmeEntryBo = transaction.getProgrammesById(serviceLearningMarksEntryForm);
		if(programmeEntryBo.getDepartmentNameEntry()!=null && !programmeEntryBo.getDepartmentNameEntry().toString().isEmpty()){
			serviceLearningMarksEntryForm.setDepartmentName(programmeEntryBo.getDepartmentNameEntry().getDepartmentName());
		}
		serviceLearningMarksEntryForm.setInchargeName(programmeEntryBo.getInchargeName());
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String startdate = df.format(programmeEntryBo.getStartDate());
		serviceLearningMarksEntryForm.setStartDate(startdate);
		serviceLearningMarksEntryForm.setServiceLearningActivityToList(serviceLearningActivityToList);
	}
	public boolean saveServiceLearningActivity(List<ServiceLearningActivityTo> serviceLearningActivityToList,ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		boolean isSaved=false;
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		List<Integer> stuids = transaction.getStudentIds();
		List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList = new ArrayList<ServiceLearningMarksEntryBo>();
		Iterator<ServiceLearningActivityTo> itr = serviceLearningActivityToList.iterator();
		while (itr.hasNext()) {
			ServiceLearningActivityTo serviceLearningActivityTo = (ServiceLearningActivityTo) itr.next();
			ServiceLearningMarksEntryBo serviceLearningMarksEntryBo = new ServiceLearningMarksEntryBo();
			ProgrammeEntryBo programmeEntryBo = new ProgrammeEntryBo();
		    Student student = new Student();
			DepartmentNameEntry  departmentNameEntry = new DepartmentNameEntry();
			if(stuids.size()!=0){
			if(stuids.contains(Integer.parseInt(serviceLearningActivityTo.getStudentId()))){
				ServiceLearningMarksEntryBo mbo = transaction.getServiceLearningmark(Integer.parseInt(serviceLearningActivityTo.getStudentId()),Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()),Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
				if(mbo!=null &&  mbo.getMark()!=null){
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
					mbo.setMark(serviceLearningActivityTo.getMark());
					mbo.setRemark(serviceLearningActivityTo.getRemark());
					mbo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					mbo.setLastModifiedDate(new Date());
				   serviceLearningMarksEntryBoList.add(mbo);
					}else{
						boolean isDeleted = transaction.deleteRecord(mbo.getId());
						if(isDeleted)
							serviceLearningActivityTo.setId(null);
							System.out.println(" record is deletd");
					}
				}
				else{
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
					}
			}
			}else{
				if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
				}
			}
			
		}else{
			if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
			    if(serviceLearningActivityTo.getRemark()!=null)
				serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
				if(serviceLearningActivityTo.getMark()!=null)
				serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
				programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
				departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
				student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
				serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
				serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
				serviceLearningMarksEntryBo.setStudent(student);
				serviceLearningMarksEntryBo.setCreatedDate(new Date());
				serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
				serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setIsActive(true);
				if(serviceLearningActivityTo.getId()!=null)
				serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
				serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
		}
		}	
			
		}
		isSaved = transaction.saveServiceLearningActivity(serviceLearningMarksEntryBoList);
		if(isSaved){
			//boolean isUpdated = transaction.updateServiceLearningActivity(serviceLearningMarksEntryBoList);
		}
		return isSaved;
	}
	
	public boolean saveServiceLearningMarkEntry(List<ServiceLearningmarksOverallEntryTo> serviceLearningActivityToList,ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		boolean isSaved=false;
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		List<Integer> stuids = transaction.getStudentIds();
		//List<Integer> stuids = transaction.getStudentIdsAdmin();
		//List<ServiceLearningMarksEntryAdminBo> serviceLearningMarksEntryBoList = new ArrayList<ServiceLearningMarksEntryAdminBo>();
		List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList = new ArrayList<ServiceLearningMarksEntryBo>();
		Iterator<ServiceLearningmarksOverallEntryTo> itr = serviceLearningActivityToList.iterator();
		while (itr.hasNext()) {

			ServiceLearningmarksOverallEntryTo serviceLearningActivityTo = (ServiceLearningmarksOverallEntryTo) itr.next();
			ServiceLearningMarksEntryBo serviceLearningMarksEntryBo = new ServiceLearningMarksEntryBo();
			ProgrammeEntryBo programmeEntryBo = new ProgrammeEntryBo();
		    Student student = new Student();
			DepartmentNameEntry  departmentNameEntry = new DepartmentNameEntry();
			if(stuids.size()!=0){
			if(stuids.contains(Integer.parseInt(serviceLearningActivityTo.getStudentId()))){
				ServiceLearningMarksEntryBo mbo=null;
				if(serviceLearningActivityTo.getDepartmentNameEntryTo()!=null && serviceLearningActivityTo.getDepartmentNameEntryTo().toString().isEmpty()){
					 mbo = transaction.getServiceLearningmark(Integer.parseInt(serviceLearningActivityTo.getStudentId()),Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()),Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
				}else{
					 mbo = transaction.getServiceLearningmark(Integer.parseInt(serviceLearningActivityTo.getStudentId()),Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
				}
					if(mbo!=null &&  mbo.getMark()!=null){
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
					mbo.setMark(serviceLearningActivityTo.getMark());
					mbo.setRemark(serviceLearningActivityTo.getRemark());
					mbo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					mbo.setLastModifiedDate(new Date());
				   serviceLearningMarksEntryBoList.add(mbo);
					}else{
						boolean isDeleted = transaction.deleteRecord(mbo.getId());
						if(isDeleted)
							serviceLearningActivityTo.setId(null);
							System.out.println(" record is deletd");
					}
				}
				else{
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					if(serviceLearningActivityTo.getDepartmentNameEntryTo()!=null && !serviceLearningActivityTo.getDepartmentNameEntryTo().toString().isEmpty() ){
						departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
						serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					}
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
					}
			}
			}else{
				if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					if(serviceLearningActivityTo.getDepartmentNameEntryTo()!=null && !serviceLearningActivityTo.getDepartmentNameEntryTo().toString().isEmpty() ){
						departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
						serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					}
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
				}
			}
			
		}else{
			if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
			    if(serviceLearningActivityTo.getRemark()!=null)
				serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
				if(serviceLearningActivityTo.getMark()!=null)
				serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
				programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
				if(serviceLearningActivityTo.getDepartmentNameEntryTo()!=null && !serviceLearningActivityTo.getDepartmentNameEntryTo().toString().isEmpty() ){
					departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
					serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
				}
				student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
				serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
				serviceLearningMarksEntryBo.setStudent(student);
				serviceLearningMarksEntryBo.setCreatedDate(new Date());
				serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
				serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setIsActive(true);
				if(serviceLearningActivityTo.getId()!=null)
				serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
				serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
		}
		}	
			
		
			
			
			
			
			
			
			/*
			ServiceLearningmarksOverallEntryTo serviceLearningActivityTo = (ServiceLearningmarksOverallEntryTo) itr.next();
			ServiceLearningMarksEntryAdminBo serviceLearningMarksEntryBo = new ServiceLearningMarksEntryAdminBo();
			ProgrammeEntryBo programmeEntryBo = new ProgrammeEntryBo();
		    Student student = new Student();
			DepartmentNameEntry  departmentNameEntry = new DepartmentNameEntry();
			if(stuids.size()!=0){
			if(stuids.contains(Integer.parseInt(serviceLearningActivityTo.getStudentId()))){
				ServiceLearningMarksEntryAdminBo mbo = transaction.getServiceLearningmarkAdmin(Integer.parseInt(serviceLearningActivityTo.getStudentId()),Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()),Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
				if(mbo!=null &&  mbo.getMark()!=null){
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
					mbo.setMark(serviceLearningActivityTo.getMark());
					mbo.setRemark(serviceLearningActivityTo.getRemark());
					mbo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					mbo.setLastModifiedDate(new Date());
				   serviceLearningMarksEntryBoList.add(mbo);
					}else{
						boolean isDeleted = transaction.deleteRecord(mbo.getId());
						if(isDeleted)
							serviceLearningActivityTo.setId(null);
							System.out.println(" record is deletd");
					}
				}
				else{
					if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
					}
			}
			}else{
				if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
				    if(serviceLearningActivityTo.getRemark()!=null)
					serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
					if(serviceLearningActivityTo.getMark()!=null)
					serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
					programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
					departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
					student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
					serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
					serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
					serviceLearningMarksEntryBo.setStudent(student);
					serviceLearningMarksEntryBo.setCreatedDate(new Date());
					serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
					serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
					serviceLearningMarksEntryBo.setIsActive(true);
					if(serviceLearningActivityTo.getId()!=null)
					serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
					serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
				}
			}
			
		}else{
			if(!serviceLearningActivityTo.getMark().trim().equalsIgnoreCase("")){
			    if(serviceLearningActivityTo.getRemark()!=null)
				serviceLearningMarksEntryBo.setRemark(serviceLearningActivityTo.getRemark());
				if(serviceLearningActivityTo.getMark()!=null)
				serviceLearningMarksEntryBo.setMark(serviceLearningActivityTo.getMark());
				programmeEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getProgrammeEntryTo().getId()));
				departmentNameEntry.setId(Integer.parseInt(serviceLearningActivityTo.getDepartmentNameEntryTo().getId()));
				student.setId(Integer.parseInt(serviceLearningActivityTo.getStudentId()));
				serviceLearningMarksEntryBo.setProgrammeEntryBo(programmeEntryBo);
				serviceLearningMarksEntryBo.setDepartmentNameEntry(departmentNameEntry);
				serviceLearningMarksEntryBo.setStudent(student);
				serviceLearningMarksEntryBo.setCreatedDate(new Date());
				serviceLearningMarksEntryBo.setModifiedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setLastModifiedDate(new Date());
				serviceLearningMarksEntryBo.setCreatedBy(serviceLearningMarksEntryForm.getUserId());
				serviceLearningMarksEntryBo.setIsActive(true);
				if(serviceLearningActivityTo.getId()!=null)
				serviceLearningMarksEntryBo.setId(Integer.parseInt(serviceLearningActivityTo.getId()));
				serviceLearningMarksEntryBoList.add(serviceLearningMarksEntryBo);
		}
		}	
			
		*/}
		isSaved = transaction.saveServiceLearningActivity(serviceLearningMarksEntryBoList);
		if(isSaved){
			//boolean isUpdated = transaction.updateServiceLearningActivity(serviceLearningMarksEntryBoList);
		}
		return isSaved;
	}
	
	public List<ServiceLearningmarksOverallEntryTo> getOverallMarkByStudentId(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		List<ServiceLearningmarksOverallEntryTo>  serviceLearningmarksOverallEntryToList = new ArrayList<ServiceLearningmarksOverallEntryTo>();
		List<Object[]> overallMark = transaction.getOverallMarkByStudentId(serviceLearningMarksEntryForm);
		if(overallMark!=null){
			Iterator<Object[]> itr=overallMark.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ServiceLearningmarksOverallEntryTo to = new ServiceLearningmarksOverallEntryTo();
				if(obj[2].toString()!=null)
					to.setOverallMark(obj[2].toString());
				if(obj[3].toString()!=null)
					to.setStudentName(obj[3].toString());
				if(obj[4].toString()!=null)
					to.setRegisterNo(obj[4].toString());
				if(obj[1].toString()!=null)
				to.setStudentId(obj[1].toString());
				if(obj[5].toString()!=null)
				serviceLearningMarksEntryForm.setCourseTitle(obj[5].toString());
				serviceLearningmarksOverallEntryToList.add(to);
			}
		}
		return serviceLearningmarksOverallEntryToList;

		
	}
	public List<ServiceLearningmarksOverallEntryTo> getOverallMark(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm) throws Exception{
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		
		List<ServiceLearningmarksOverallEntryTo>  serviceLearningmarksOverallEntryToList = new ArrayList<ServiceLearningmarksOverallEntryTo>();
		List<Student> overallMark = transaction.getOverallMark(serviceLearningMarksEntryForm);
		if(overallMark!=null){
			Iterator<Student> itrr = overallMark.iterator();
			while (itrr.hasNext()) {
				Student student = (Student) itrr.next();
				//if(student.getId()==2264){
				ProgrammeEntryTo programmeEntryTo = new ProgrammeEntryTo();
				DepartmentNameEntryTo departmentNameEntryTo = new DepartmentNameEntryTo();
				ServiceLearningmarksOverallEntryTo to = new ServiceLearningmarksOverallEntryTo();
				to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
				to.setRegisterNo(student.getRegisterNo());
				to.setStudentId(String.valueOf(student.getId()));
				if(serviceLearningMarksEntryForm.getProgramId()!=null)
				programmeEntryTo.setId(serviceLearningMarksEntryForm.getProgramId());
				to.setProgrammeEntryTo(programmeEntryTo);
				if(serviceLearningMarksEntryForm.getDepartmentId()!=null && !serviceLearningMarksEntryForm.getDepartmentId().isEmpty()){
					departmentNameEntryTo.setId(serviceLearningMarksEntryForm.getDepartmentId());
					to.setDepartmentNameEntryTo(departmentNameEntryTo);
				}
				serviceLearningMarksEntryForm.setCourseTitle(student.getAdmAppln().getCourse().getName());
				
				ServiceLearningMarksEntryBo serviceLearningMarksEntryBo = transaction.getServiceLearningmarkEntry(student.getId(),Integer.parseInt(serviceLearningMarksEntryForm.getProgramId()));
				if(serviceLearningMarksEntryBo!=null){
					to.setMark(serviceLearningMarksEntryBo.getMark());
					to.setRemark(serviceLearningMarksEntryBo.getRemark());
					to.setId(String.valueOf(serviceLearningMarksEntryBo.getId()));
				}
				
				
				
				serviceLearningmarksOverallEntryToList.add(to);
				//}
			}
			
		}
		return serviceLearningmarksOverallEntryToList;

		
	}
	public List<ServiceLearningMarksEntryTo> getStudentDetailsById(ServiceLearningMarksEntryForm serviceLearningMarksEntryForm,HttpServletRequest request) throws Exception{
		IServiceLearningMarksEntryTransaction transaction = new ServiceLearningMarksEntryTransactionImpl();
		List<ServiceLearningMarksEntryTo>  serviceLearningMarksEntryToList = new ArrayList<ServiceLearningMarksEntryTo>();
		List<ServiceLearningMarksEntryBo> serviceLearningMarksEntryBoList = transaction.getStudentDetailsById(Integer.parseInt(serviceLearningMarksEntryForm.getStudentId()));
		List<Integer> studentIds=new ArrayList<Integer>();
		studentIds.add(Integer.parseInt(serviceLearningMarksEntryForm.getStudentId()));
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		
		double totalCredits=0.0;
		if(serviceLearningMarksEntryBoList!=null){
			Iterator<ServiceLearningMarksEntryBo> itr=serviceLearningMarksEntryBoList.iterator();
			while (itr.hasNext()) {
				ServiceLearningMarksEntryBo bo = itr.next();
				ServiceLearningMarksEntryTo to = new ServiceLearningMarksEntryTo();
				
				to.setProgramName(bo.getProgrammeEntryBo().getProgrammeName());
				if(bo.getDepartmentNameEntry()!=null && !bo.getDepartmentNameEntry().toString().isEmpty()){
					to.setDepttName(bo.getDepartmentNameEntry().getDepartmentName());
				}
				to.setCredit(bo.getMark());
				totalCredits = totalCredits+Double.parseDouble(bo.getMark());
				serviceLearningMarksEntryForm.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
				serviceLearningMarksEntryForm.setRegisterNo(bo.getStudent().getRegisterNo());
				Map<Integer,byte[]> photoMap=txn.getStudentPhtosMap(studentIds,false);
				if(photoMap.containsKey(bo.getStudent().getId())){
					request.getSession().setAttribute(bo.getStudent().getRegisterNo(), photoMap.get(bo.getStudent().getId()));
				}
				ServiceLearningActivityBo bo1 = new ServiceLearningActivityBo();
				if(bo.getDepartmentNameEntry()!=null && !bo.getDepartmentNameEntry().toString().isEmpty()){
				 bo1 = transaction.getServiceLearningActivity(bo.getProgrammeEntryBo().getId(),bo.getDepartmentNameEntry().getId(),bo.getStudent().getId());
				}else{
				 bo1 = transaction.getServiceLearningActivityWithoutDept(bo.getProgrammeEntryBo().getId(),bo.getStudent().getId());
				}
				if(bo1!=null){
					to.setLearningAndOutcome(bo1.getLearningAndActivity());
				}
				serviceLearningMarksEntryToList.add(to);
			}
		}
		serviceLearningMarksEntryForm.setOverallMark(String.valueOf(totalCredits));
		return serviceLearningMarksEntryToList;

		
	}	

}
