package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.CertificateCourseGroup;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;

public class CertificateCourseEntryHelper {
	
	private static final Log log = LogFactory.getLog(CertificateCourseEntryHelper.class);
	public static volatile CertificateCourseEntryHelper certificateCourseEntryHelper = null;
	public static CertificateCourseEntryHelper getInstance() {
	      if(certificateCourseEntryHelper == null) {
	    	  certificateCourseEntryHelper = new CertificateCourseEntryHelper();
	    	  return certificateCourseEntryHelper;
	      }
	      return certificateCourseEntryHelper;
	}
	/**
	 * 
	 * @param certificateCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public CertificateCourse copyDataFromFormToBO(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		CertificateCourse certificateCourse = new CertificateCourse();
		if(certificateCourseEntryForm.getId() != 0) {
			certificateCourse.setId(certificateCourseEntryForm.getId());
		}
		certificateCourse.setCertificateCourseName(certificateCourseEntryForm.getCertificateCourseName());
		certificateCourse.setCreatedBy(certificateCourseEntryForm.getUserId());
		certificateCourse.setModifiedBy(certificateCourseEntryForm.getUserId());
		certificateCourse.setCreatedDate(new Date());
		certificateCourse.setLastModifiedDate(new Date());
		certificateCourse.setIsActive(true);
		certificateCourse.setMaxIntake(Integer.parseInt(certificateCourseEntryForm.getMaxIntake()));
		if(certificateCourseEntryForm.getEndHours()!=null && !certificateCourseEntryForm.getEndHours().isEmpty() && certificateCourseEntryForm.getEndMins()!=null && !certificateCourseEntryForm.getEndMins().isEmpty())
		certificateCourse.setEndTime(certificateCourseEntryForm.getEndHours()+":"+certificateCourseEntryForm.getEndMins());
		if(certificateCourseEntryForm.getStartHours()!=null && !certificateCourseEntryForm.getStartHours().isEmpty() && certificateCourseEntryForm.getStartMins()!=null && !certificateCourseEntryForm.getStartMins().isEmpty())
		certificateCourse.setStartTime(certificateCourseEntryForm.getStartHours()+":"+certificateCourseEntryForm.getStartMins());
		certificateCourse.setVenue(certificateCourseEntryForm.getVenue());
		certificateCourse.setYear(Integer.parseInt(certificateCourseEntryForm.getAcademicYear()));
		Users users = new Users();
		users.setId(Integer.parseInt(certificateCourseEntryForm.getTeacherId()));
		certificateCourse.setUsers(users);
		certificateCourse.setSemType(certificateCourseEntryForm.getSemType());
		certificateCourse.setExtracurricular(certificateCourseEntryForm.getExtracurricular().equalsIgnoreCase("true")? true:false);
		if(certificateCourseEntryForm.getSubjectId()!=null && !certificateCourseEntryForm.getSubjectId().isEmpty()){
			Subject subject=new Subject();
			subject.setId(Integer.parseInt(certificateCourseEntryForm.getSubjectId()));
			certificateCourse.setSubject(subject);
		}
		if(certificateCourseEntryForm.getDescription()!=null && !certificateCourseEntryForm.getDescription().isEmpty())
		certificateCourse.setDescription(certificateCourseEntryForm.getDescription());
		return certificateCourse;
		}
	
	/**
	 * Populates all the active course Bos to TO
	 */
	
	public List<CertificateCourseTO> populateCourseBOtoTO(List<CertificateCourse> courseList, List<Integer> appliedList){
		log.info("Start of populateCourseBOtoTO of CertificateCourseHelper");
		CertificateCourseTO certificateCourseTO;
		List<CertificateCourseTO> courseToList = new ArrayList<CertificateCourseTO>();
		Iterator<CertificateCourse> iterator=courseList.iterator();
		while (iterator.hasNext()) {
			CertificateCourse certificateCourse = iterator.next();
			if(appliedList.contains(certificateCourse.getId())){
				continue;
			}
			certificateCourseTO = new CertificateCourseTO();
			certificateCourseTO.setId(certificateCourse.getId());
			certificateCourseTO.setMaxIntake(String.valueOf(certificateCourse.getMaxIntake()));
			certificateCourseTO.setCourseName(certificateCourse.getCertificateCourseName());
			certificateCourseTO.setCourseNameWithSemType(certificateCourse.getCertificateCourseName()+"("+certificateCourse.getSemType()+")");
			certificateCourseTO.setSemType(certificateCourse.getSemType());
			if(certificateCourse.getStartTime()!=null && !certificateCourse.getStartTime().isEmpty()){
				certificateCourseTO.setStartTime(certificateCourse.getStartTime());
			}
			if(certificateCourse.getEndTime()!=null && !certificateCourse.getEndTime().isEmpty()){
				certificateCourseTO.setEndTime(certificateCourse.getEndTime());
			}
			if(certificateCourse.getVenue()!=null && !certificateCourse.getVenue().isEmpty()){
				certificateCourseTO.setVenue(certificateCourse.getVenue());
			}
			if(certificateCourse.getUsers()!=null && certificateCourse.getUsers().getUserName()!=null && !certificateCourse.getUsers().getUserName().isEmpty()){
				certificateCourseTO.setTeacherName(certificateCourse.getUsers().getUserName());
			}
			Users users = new Users();
			if(certificateCourse.getUsers()!=null && certificateCourse.getUsers().getId()!= 0){
				users.setId(certificateCourse.getUsers().getId());
				certificateCourseTO.setUsers(users);
			}
			if(certificateCourse.getYear()!=null){
				certificateCourseTO.setYear(certificateCourse.getYear());
			}
			if(certificateCourse.getExtracurricular())
			{
				certificateCourseTO.setExtracurricular("Yes");
			}
			else{
				certificateCourseTO.setExtracurricular("No");
			}
			if(certificateCourse.getDescription()!=null && !certificateCourse.getDescription().isEmpty())
				certificateCourseTO.setDescription(certificateCourse.getDescription());
			courseToList.add(certificateCourseTO);
			
		}
		log.info("End of populateCourseBOtoTO of CertificateCourseHelper");
		return courseToList;		
	}
	/**
	 * @param certificateCourse
	 * @param certificateCourseEntryForm
	 */
	public void copyDataFromBotoForm(CertificateCourse certificateCourse, CertificateCourseEntryForm certificateCourseEntryForm) throws Exception{
		certificateCourseEntryForm.setCertificateCourseName(certificateCourse.getCertificateCourseName());
		certificateCourseEntryForm.setId(certificateCourse.getId());
		certificateCourseEntryForm.setMaxIntake(String.valueOf(certificateCourse.getMaxIntake()));
		certificateCourseEntryForm.setTeacherId(Integer.toString(certificateCourse.getUsers().getId()));
		certificateCourseEntryForm.setVenue(certificateCourse.getVenue());
		if(certificateCourse.getStartTime()!=null && !certificateCourse.getStartTime().isEmpty()){
			StringBuffer start = new StringBuffer(certificateCourse.getStartTime());
			String startHours =start.substring(0, 2);
			StringBuffer stMins = new StringBuffer(certificateCourse.getStartTime());
			String startMins = stMins.substring(3, 5);
			certificateCourseEntryForm.setStartHours(startHours);
			certificateCourseEntryForm.setStartMins(startMins);
		}
		if(certificateCourse.getStartTime()!=null && !certificateCourse.getStartTime().isEmpty()){
			StringBuffer end = new StringBuffer(certificateCourse.getEndTime());
			String endHours =end.substring(0, 2);
			StringBuffer enMins = new StringBuffer(certificateCourse.getEndTime());
			String endMins = enMins.substring(3, 5);
			certificateCourseEntryForm.setEndHours(endHours);
			certificateCourseEntryForm.setEndMins(endMins);
		}
		if (certificateCourse.getYear()!=null) {
			certificateCourseEntryForm.setAcademicYear(String.valueOf(certificateCourse.getYear()));
		}
		if(certificateCourse.getSemType()!=null){
			certificateCourseEntryForm.setSemType(certificateCourse.getSemType());
		}
		if(certificateCourse.getSubject()!=null){
			certificateCourseEntryForm.setSubjectId(String.valueOf(certificateCourse.getSubject().getId()));
		}
		if(certificateCourse.getExtracurricular()!=null){
			String Value=String.valueOf(certificateCourse.getExtracurricular());
			if(Value.equalsIgnoreCase("true"))
				certificateCourseEntryForm.setExtracurricular("true");
			else
				certificateCourseEntryForm.setExtracurricular("false");
			
		}
		if(certificateCourse.getDescription()!=null && !certificateCourse.getDescription().isEmpty())
			certificateCourseEntryForm.setDescription(certificateCourse.getDescription());
		Map<Integer,CCGroupTo> groupMap=new HashMap<Integer, CCGroupTo>();
		Map<Integer,Integer> ccGroupIds=new HashMap<Integer, Integer>();
		if(certificateCourse.getGroups()!=null){
			Set<CertificateCourseGroup> groups=certificateCourse.getGroups();
			Iterator<CertificateCourseGroup> itr=groups.iterator();
			while (itr.hasNext()) {
				CertificateCourseGroup bo =itr .next();
				CCGroupTo to=new CCGroupTo();
				to.setId(bo.getGroups().getId());
				to.setName(bo.getGroups().getName());
				to.setMaxInTake(String.valueOf(bo.getMaxIntake()));
				ccGroupIds.put(bo.getGroups().getId(),bo.getId());
				groupMap.put(bo.getGroups().getId(),to);
			}
		}
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		List<CCGroup> list=txn.getMasterEntryData(CCGroup.class);
		List<CCGroupTo> grouptos=new ArrayList<CCGroupTo>();
		if(list!=null && !list.isEmpty()){
			Iterator<CCGroup> itr=list.iterator();
			while (itr.hasNext()) {
				CCGroup ccGroup = (CCGroup) itr.next();
				if(!groupMap.containsKey(ccGroup.getId())){
					CCGroupTo to=new CCGroupTo();
					to.setId(ccGroup.getId());
					to.setName(ccGroup.getName());
					grouptos.add(to);
				}else{
					grouptos.add(groupMap.remove(ccGroup.getId()));
				}
			}
		}
		Collections.sort(grouptos);
		certificateCourseEntryForm.setGroupList1(grouptos);
		certificateCourseEntryForm.setGroupId(ccGroupIds);
	}
}

