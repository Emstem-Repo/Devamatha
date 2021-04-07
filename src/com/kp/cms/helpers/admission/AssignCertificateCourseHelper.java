package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.bo.admin.AssignCertificateCourseDetails;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admission.AssignCertificateCourseForm;
import com.kp.cms.to.admission.AssignCertificateCourseDetailsTO;
import com.kp.cms.to.admission.AssignCertificateCourseTO;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactionsimpl.admission.CertificateCourseEntryTxnImpl;

public class AssignCertificateCourseHelper {
	/**
	 * Singleton object of AssignCertificateCourseHelper
	 */
	private static volatile AssignCertificateCourseHelper assignCertificateCourseHelper = null;
	private static final Log log = LogFactory.getLog(AssignCertificateCourseHelper.class);
	private AssignCertificateCourseHelper() {
		
	}
	/**
	 * return singleton object of AssignCertificateCourseHelper.
	 * @return
	 */
	public static AssignCertificateCourseHelper getInstance() {
		if (assignCertificateCourseHelper == null) {
			assignCertificateCourseHelper = new AssignCertificateCourseHelper();
		}
		return assignCertificateCourseHelper;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AssignCertificateCourseTO> convertBosToTOs(List<AssignCertificateCourse> list) throws Exception {
		List<AssignCertificateCourseTO> assignCertificateCourseTOs=new ArrayList<AssignCertificateCourseTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<AssignCertificateCourse> itr=list.iterator();
			while (itr.hasNext()) {
				AssignCertificateCourse bo = (AssignCertificateCourse) itr.next();
				AssignCertificateCourseTO to=new AssignCertificateCourseTO();
				to.setId(bo.getId());
				to.setAcademicYear(bo.getAcademicYear());
				if(bo.getCourse()!=null)
				to.setCourseName(bo.getCourse().getName());
				to.setSemType(bo.getSemType());
				assignCertificateCourseTOs.add(to);
			}
		}
		return assignCertificateCourseTOs;
	}
	/**
	 * @param assignCertificateCourseForm
	 * @param mode
	 * @return
	 */
	public AssignCertificateCourse convertFormTOBO(AssignCertificateCourseForm assignCertificateCourseForm, String mode) throws Exception {
		AssignCertificateCourse bo =new AssignCertificateCourse();
		if(mode.equals("add")){
			bo.setCreatedBy(assignCertificateCourseForm.getUserId());
			bo.setCreatedDate(new Date());
		}else{
			bo.setId(assignCertificateCourseForm.getId());
		}
		bo.setModifiedBy(assignCertificateCourseForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setSemType(assignCertificateCourseForm.getSemType());
		bo.setAcademicYear(Integer.parseInt(assignCertificateCourseForm.getAcademicYear()));
		Course course=new Course();
		course.setId(Integer.parseInt(assignCertificateCourseForm.getCourseId()));
		bo.setCourse(course);
		if(assignCertificateCourseForm.getAssignCertificateCourseDetailsTOs()!=null && !assignCertificateCourseForm.getAssignCertificateCourseDetailsTOs().isEmpty()){
			Set<AssignCertificateCourseDetails> bos=new HashSet<AssignCertificateCourseDetails>();
			Iterator<AssignCertificateCourseDetailsTO> itr=assignCertificateCourseForm.getAssignCertificateCourseDetailsTOs().iterator();
			while (itr.hasNext()) {
				AssignCertificateCourseDetailsTO to= (AssignCertificateCourseDetailsTO) itr.next();
				if(to.getChecked()!=null && to.getChecked()){
				AssignCertificateCourseDetails detailBo=new AssignCertificateCourseDetails();
				if(to.getId()>0)
					detailBo.setId(to.getId());
				CertificateCourse certificateCourse=new CertificateCourse();
				certificateCourse.setId(to.getCertificateCourseId());
				detailBo.setCertificateCourse(certificateCourse);
				bos.add(detailBo);
				}	
			}
			bo.setAssignCertificateCourseDetails(bos);
			bo.setIsActive(true);
		}
		return bo;
	}
	/**
	 * @param courseBoList
	 * @param existsMap
	 * @return
	 */
	public List<AssignCertificateCourseDetailsTO> getAssignCertificateCourseDetails(List<CertificateCourse> courseBoList,Map<Integer,Integer> existsMap) throws Exception{
		List<AssignCertificateCourseDetailsTO> assignCertificateCourseDetailsTOs=new ArrayList<AssignCertificateCourseDetailsTO>();
		if(courseBoList!=null && !courseBoList.isEmpty()){
			Iterator<CertificateCourse> itr=courseBoList.iterator();
			while (itr.hasNext()) {
				CertificateCourse bo = (CertificateCourse) itr.next();
				AssignCertificateCourseDetailsTO to=new AssignCertificateCourseDetailsTO();
				if(existsMap.containsKey(bo.getId())){
					to.setId(existsMap.get(bo.getId()));
					to.setTempChecked(true);
				}else{
					to.setTempChecked(false);
				}
				to.setCertificateCourseId(bo.getId());
				to.setCertificateCourseName(bo.getCertificateCourseName());
				assignCertificateCourseDetailsTOs.add(to);
			}
		}
		Collections.sort(assignCertificateCourseDetailsTOs);
		return assignCertificateCourseDetailsTOs;
	}
	/**
	 * @param bo
	 * @throws Exception
	 */
	public void convertBoToTo(AssignCertificateCourse bo,AssignCertificateCourseForm assignCertificateCourseForm) throws Exception {
		assignCertificateCourseForm.setId(bo.getId());
		assignCertificateCourseForm.setSemType(bo.getSemType());
		assignCertificateCourseForm.setAcademicYear(Integer.toString(bo.getAcademicYear()));
		assignCertificateCourseForm.setCourseId(Integer.toString(bo.getCourse().getId()));
		Set<AssignCertificateCourseDetails> set=bo.getAssignCertificateCourseDetails();
		Map<Integer,Integer> existsMap=new HashMap<Integer, Integer>();
		if(set!=null && !set.isEmpty()){
			Iterator<AssignCertificateCourseDetails> itr=set.iterator();
			while (itr.hasNext()) {
				AssignCertificateCourseDetails assignCertificateCourseDetails = (AssignCertificateCourseDetails) itr.next();
				if(assignCertificateCourseDetails.getCertificateCourse().getIsActive())
				existsMap.put(assignCertificateCourseDetails.getCertificateCourse().getId(),assignCertificateCourseDetails.getId());
			}
		}
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		List<CertificateCourse> courseBoList=iCertificateCourseEntryTxn.getActiveCertificateCourses(bo.getAcademicYear(),assignCertificateCourseForm.getSemType());
		
		assignCertificateCourseForm.setAssignCertificateCourseDetailsTOs(AssignCertificateCourseHelper.getInstance().getAssignCertificateCourseDetails(courseBoList,existsMap));
	}
	/**
	 * @param assignCertificateCourseForm
	 * @return
	 * @throws Exception
	 */
	public String getDuplicateCheckQuery(AssignCertificateCourseForm assignCertificateCourseForm) throws Exception{
		String query="from AssignCertificateCourse a where a.course.id="+assignCertificateCourseForm.getCourseId()+" and a.semType='"+assignCertificateCourseForm.getSemType()+"' and a.academicYear="+assignCertificateCourseForm.getAcademicYear();
		return query;
	}
}
