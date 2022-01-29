package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.CertificateCourseEntryForm;
import com.kp.cms.helpers.admin.SubjectHelper;
import com.kp.cms.helpers.admission.CertificateCourseEntryHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactionsimpl.admission.CertificateCourseEntryTxnImpl;

public class CertificateCourseEntryHandler {
	private static final Log log = LogFactory.getLog(CertificateCourseEntryHandler.class);
	public static volatile CertificateCourseEntryHandler certificateCourseEntryHandler = null;

	public static CertificateCourseEntryHandler getInstance() {
		if (certificateCourseEntryHandler == null) {
			certificateCourseEntryHandler = new CertificateCourseEntryHandler();
			return certificateCourseEntryHandler;
		}
		return certificateCourseEntryHandler;
	}
	/**
	 * @param certificateCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean addCerticateCourse(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception {
		log.debug("inside addCerticateCourse");
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		boolean isAdded = false;
		
		CertificateCourse duplCertificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm); 
		duplCertificateCourse = iCertificateCourseEntryTxn.isCertificateCourseNameDuplcated(duplCertificateCourse);
		if (duplCertificateCourse != null && duplCertificateCourse.getIsActive()) {
			throw new DuplicateException();
		}
		else if(duplCertificateCourse != null && !duplCertificateCourse.getIsActive()){
			certificateCourseEntryForm.setDuplId(duplCertificateCourse.getId());
			throw new ReActivateException();
		}
		duplCertificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm);
		duplCertificateCourse = iCertificateCourseEntryTxn.isSubjectDuplicate(duplCertificateCourse);
		if (duplCertificateCourse != null && duplCertificateCourse.getIsActive()) {
			throw new DuplicateException();
		}
		else if(duplCertificateCourse != null && !duplCertificateCourse.getIsActive()){
			certificateCourseEntryForm.setDuplId(duplCertificateCourse.getId());
			throw new ReActivateException();
		}
				
		CertificateCourse certificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm);
		Set<CertificateCourseGroup> groups=new HashSet<CertificateCourseGroup>();
		List<CCGroupTo> groupTos=certificateCourseEntryForm.getGroupList1();
		if(groupTos!=null && !groupTos.isEmpty()){
			Iterator<CCGroupTo> itr=groupTos.iterator();
			while (itr.hasNext()) {
				CCGroupTo ccGroupTo = (CCGroupTo) itr.next();
				if(ccGroupTo.getMaxInTake()!=null && !ccGroupTo.getMaxInTake().isEmpty()){
					CertificateCourseGroup bo=new CertificateCourseGroup();
					bo.setMaxIntake(Integer.parseInt(ccGroupTo.getMaxInTake()));
					CCGroup group2=new CCGroup();
					group2.setId(ccGroupTo.getId());
					bo.setGroups(group2);
					groups.add(bo);
				}
			}
		}
		certificateCourse.setGroups(groups);
		isAdded = iCertificateCourseEntryTxn.addCertificateCourse(certificateCourse);
		log.debug("leaving addCerticateCourse");
		return isAdded;
	}
	/**
	 * 
	 * @param semType 
	 * @return
	 * @throws Exception
	 */
	public List<CertificateCourseTO> getActiveCourses(int year)throws Exception{
		log.info("Start of getActiveCourses of CertificateCourseEntryHandler");
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		List<CertificateCourse> courseBoList=iCertificateCourseEntryTxn.getActiveCertificateCourses(year);
		if(courseBoList != null && !courseBoList.isEmpty() ){
			return CertificateCourseEntryHelper.getInstance().populateCourseBOtoTO(courseBoList, new ArrayList<Integer>());
		}
		log.info("End of getActiveCourses of CertificateCourseEntryHandler");
		return null;
	}
	/**
	 * 
	 * @param id
	 * @param activate
	 * @param certificateCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCertificateCourse(int id, Boolean activate, CertificateCourseEntryForm certificateCourseEntryForm)throws Exception {
		log.debug("inside deleteCertificateCourse");
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		boolean result = iCertificateCourseEntryTxn.deleteCertificateCourseEntry(id, activate, certificateCourseEntryForm);
		log.debug("leaving deleteCertificateCourse");
		return result;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SubjectTO> getSubjectList() throws Exception {
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		List<Subject> subjectBoList = iCertificateCourseEntryTxn.getSubjects();
		List<SubjectTO> getstudentTypeList = SubjectHelper.getInstance()
				.convertBOstoTos(subjectBoList);
		return getstudentTypeList;
	}
	/**
	 * @param certificateCourseEntryForm
	 * @throws Exception
	 */
	public void getDetailsById(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception{
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		CertificateCourse bo = iCertificateCourseEntryTxn.editCertificateCourse(certificateCourseEntryForm);
		 CertificateCourseEntryHelper.getInstance().copyDataFromBotoForm(bo,certificateCourseEntryForm);
	}
	
	/**
	 * @param certificateCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateCertificateCourse(CertificateCourseEntryForm certificateCourseEntryForm) throws Exception{
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		boolean isUpdated = false;
		CertificateCourse duplCertificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm); 
		CertificateCourse  duplCertificateCour = CertificateCourseEntryTxnImpl.getInstance().isCertificateCourseNameDuplcated(duplCertificateCourse);
		if(duplCertificateCour != null && duplCertificateCour.getId()!=certificateCourseEntryForm.getId()){
			if (duplCertificateCour != null && duplCertificateCour.getIsActive()) {
				throw new DuplicateException();
			}
			else if(duplCertificateCour != null && !duplCertificateCour.getIsActive()){
				certificateCourseEntryForm.setDuplId(duplCertificateCour.getId());
				throw new ReActivateException();
			}
		}	
		duplCertificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm);
		duplCertificateCourse = iCertificateCourseEntryTxn.isSubjectDuplicate(duplCertificateCourse);
		if (duplCertificateCourse != null && duplCertificateCourse.getIsActive()) {
			throw new DuplicateException();
		}
		else if(duplCertificateCourse != null && !duplCertificateCourse.getIsActive()){
			certificateCourseEntryForm.setDuplId(duplCertificateCourse.getId());
			throw new ReActivateException();
		}		
		 CertificateCourse certificateCourse = CertificateCourseEntryHelper.getInstance().copyDataFromFormToBO(certificateCourseEntryForm);
		Map<Integer,Integer> groupMap= certificateCourseEntryForm.getGroupId();
		Set<CertificateCourseGroup> groups=new HashSet<CertificateCourseGroup>();
		List<CCGroupTo> groupTos=certificateCourseEntryForm.getGroupList1();
		if(groupTos!=null && !groupTos.isEmpty()){
			Iterator<CCGroupTo> itr=groupTos.iterator();
			while (itr.hasNext()) {
				CCGroupTo ccGroupTo = (CCGroupTo) itr.next();
				if(ccGroupTo.getMaxInTake()!=null && !ccGroupTo.getMaxInTake().isEmpty()){
					CertificateCourseGroup bo=new CertificateCourseGroup();
					if(groupMap.containsKey(ccGroupTo.getId()))
						bo.setId(groupMap.get(ccGroupTo.getId()));
					bo.setMaxIntake(Integer.parseInt(ccGroupTo.getMaxInTake()));
					CCGroup group2=new CCGroup();
					group2.setId(ccGroupTo.getId());
					bo.setGroups(group2);
					groups.add(bo);
				}
			}
		}
		certificateCourse.setGroups(groups);
		isUpdated = iCertificateCourseEntryTxn.updateCertificateCourse(certificateCourse);
		return isUpdated;
	}
	/**
	 * @param currentYear
	 * @param semType
	 * @return
	 * @throws Exception 
	 */
	public List<CertificateCourseTO> getActiveCourses1(int year, String semType) throws Exception {
		log.info("Start of getActiveCourses of CertificateCourseEntryHandler");
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		/* code modified by sudhir 
		 * added semtype argument to the method*/
		List<CertificateCourse> courseBoList=iCertificateCourseEntryTxn.getActiveCertificateCourses(year,semType);
		/* code modified by sudhir*/
		if(courseBoList != null && !courseBoList.isEmpty() ){
			return CertificateCourseEntryHelper.getInstance().populateCourseBOtoTO(courseBoList, new ArrayList<Integer>());
		}
		log.info("End of getActiveCourses of CertificateCourseEntryHandler");
		return null;
	}
}
