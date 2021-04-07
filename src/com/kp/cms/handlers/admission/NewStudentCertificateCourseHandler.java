package com.kp.cms.handlers.admission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.bo.admin.AssignCertificateCourseDetails;
import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.forms.admission.NewStudentCertificateCourseForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admission.NewStudentCertificateCourseHelper;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.admission.INewStudentCertificateCourseTxn;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.fee.IFeePaymentTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admission.NewStudentCertificateCourseTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.fee.FeePaymentTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtils;

public class NewStudentCertificateCourseHandler {
	/**
	 * Singleton object of NewStudentCertificateCourseHandler
	 */
	private static volatile NewStudentCertificateCourseHandler newStudentCertificateCourseHandler = null;
	private static final Log log = LogFactory.getLog(NewStudentCertificateCourseHandler.class);
	private NewStudentCertificateCourseHandler() {
		
	}
	/**
	 * return singleton object of NewStudentCertificateCourseHandler.
	 * @return
	 */
	public static NewStudentCertificateCourseHandler getInstance() {
		if (newStudentCertificateCourseHandler == null) {
			newStudentCertificateCourseHandler = new NewStudentCertificateCourseHandler();
		}
		return newStudentCertificateCourseHandler;
	}
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 */
	public String getStudentSchemeNoByRegNo(String regNo) throws Exception {
		log.info("Entered into getStudentSchemeNoByRegNo");
		String query="select concat(s.classSchemewise.classes.termNumber,'') from Student s where s.registerNo='"+regNo+"'";
		log.info("Exit from getStudentSchemeNoByRegNo");
		return PropertyUtil.getDataForUnique(query);
	}
	/**
	 * @param newStudentCertificateCourseForm
	 * @throws Exception
	 */
	public void getCertificateCourseByStudent(NewStudentCertificateCourseForm newStudentCertificateCourseForm) throws Exception {
		log.info("Entered into getCertificateCourseByStudent");
		List<CertificateCourseTO> mandatorycourseList = new ArrayList<CertificateCourseTO>();
		List<CertificateCourseTO> optionalCourseList = new ArrayList<CertificateCourseTO>();
		List<CertificateCourseTO> extraCurricularCourseList = new ArrayList<CertificateCourseTO>();
		
		String extraCurricularQuery="from StudentCertificateCourse s where s.isCancelled=0 and s.isExtraCurricular=1 and s.student.id="+newStudentCertificateCourseForm.getStudentId();
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List<StudentCertificateCourse> extraCurricularList=txn.getDataForQuery(extraCurricularQuery);
		
		if(extraCurricularList!=null && !extraCurricularList.isEmpty()){
			newStudentCertificateCourseForm.setExtraCurricularApplied(true);
			newStudentCertificateCourseForm.setExtraCertificateName(extraCurricularList.get(0).getCertificateCourse().getCertificateCourseName());
		}else{
			newStudentCertificateCourseForm.setExtraCurricularApplied(false);
		}
		
		String certificateCourseAppliedQuery="from StudentCertificateCourse s where s.isCancelled=0 and s.isExtraCurricular=0 and s.schemeNo="+newStudentCertificateCourseForm.getSchemeNo()+" and s.student.id="+newStudentCertificateCourseForm.getStudentId();
		List<StudentCertificateCourse> certificateAppliedList=txn.getDataForQuery(certificateCourseAppliedQuery);
		if(certificateAppliedList!=null && !certificateAppliedList.isEmpty()){
			newStudentCertificateCourseForm.setCertificateApplied(true);
			newStudentCertificateCourseForm.setCertificateName(certificateAppliedList.get(0).getCertificateCourse().getCertificateCourseName());
		}else{
			newStudentCertificateCourseForm.setCertificateApplied(false);
		}
		
		/*String courseAndYearQuery="select c.course.id,cd.academicYear from CurriculumScheme c join c.curriculumSchemeDurations cd where c.id=" +
				"( select s.classSchemewise.curriculumSchemeDuration.curriculumScheme.id from Student s " +
				"where s.id="+newStudentCertificateCourseForm.getStudentId()+") and cd.semesterYearNo="+newStudentCertificateCourseForm.getSchemeNo();*/
		
		String courseAndYearQuery="select css.course.id, csd.academicYear from Student s join s.classSchemewise cs join cs.curriculumSchemeDuration csd "+
		" join csd.curriculumScheme css where s.id="+newStudentCertificateCourseForm.getStudentId();
		
		List<Object[]> courseAndYearList=txn.getDataForQuery(courseAndYearQuery);
		if(courseAndYearList!=null && !courseAndYearList.isEmpty()){
			int courseId = 0;
			int year = 0;
			
			Iterator<Object[]> itr=courseAndYearList.iterator();
			while (itr.hasNext()) {
				Object[] obj=itr.next();
				if (obj[0] != null) {
					courseId = Integer.parseInt(obj[0].toString());
				}
				if (obj[1] != null) {
					year = Integer.parseInt(obj[1].toString());
				}
			}
			String gId=PropertyUtil.getDataForUnique("select concat(c.ccGroup.id,'') from CCGroupCourse c where c.isActive=1 and c.course.id="+courseId);
			int groupId=0;
			if(gId!=null && !gId.isEmpty()){
				groupId=Integer.parseInt(gId);
			}
			newStudentCertificateCourseForm.setGroupId(groupId);
			
			Map<Integer, String> groupMaxIntakeMap = new HashMap<Integer, String>();
			Map<Integer, Boolean> groupMaxIntakeCompleteMap = new HashMap<Integer, Boolean>();
			
			String groupByMaxIntake="select c.certificateCourse.id, count(s.id)," +
					"c.maxIntake from CertificateCourseGroup c left join c.certificateCourse cc " +
					"left join cc.studentCertificateCourses s with (s.isCancelled=0 and s.groups.id="+groupId+") where c.certificateCourse.isActive=1 " +
					" and c.certificateCourse.year="+year+" and c.certificateCourse.semType='"+newStudentCertificateCourseForm.getSemType()+"' and c.groups.id=" +groupId+
					" group by c.groups.id,c.certificateCourse.id";
			List<Object[]> groupMaxIntakeList=txn.getDataForQuery(groupByMaxIntake);
			if(groupMaxIntakeList!=null && !groupMaxIntakeList.isEmpty()){
				Iterator<Object[]> grpItr=groupMaxIntakeList.iterator();
				while (grpItr.hasNext()) {
					Object[] obj = grpItr.next();
					groupMaxIntakeMap.put(Integer.parseInt(obj[0].toString()), obj[1].toString() + "/" + obj[2].toString());
					if(obj[1].toString().equalsIgnoreCase( obj[2].toString()))
						groupMaxIntakeCompleteMap.put(Integer.parseInt(obj[0].toString()), true);
					else
						groupMaxIntakeCompleteMap.put(Integer.parseInt(obj[0].toString()), false);
				}
			}
			
			Map<Integer, CCGroupTo> maxIntakeMap = new HashMap<Integer, CCGroupTo>();
			String maxIntakeQuery = "select c.id,count(s.id),c.maxIntake from CertificateCourse c left join c.studentCertificateCourses s with s.isCancelled=0 where c.isActive=1"
				+ " and c.year="
				+ year
				+ " and c.semType='"
				+ newStudentCertificateCourseForm.getSemType()
				+ "' group by c.id ";
			List maxIntakeList = txn.getDataForQuery(maxIntakeQuery);
			if (maxIntakeList != null && !maxIntakeList.isEmpty()) {
				Iterator<Object[]> maxItr = maxIntakeList.iterator();
				CCGroupTo to=null;
				while (maxItr.hasNext()) {
					Object[] maxIntake = (Object[]) maxItr.next();
					to=new CCGroupTo();
					to.setId(Integer.parseInt(maxIntake[0].toString()));
					to.setMaxInTake( maxIntake[1].toString() + "/" + maxIntake[2].toString());
					if(groupMaxIntakeMap.containsKey(Integer.parseInt(maxIntake[0].toString()))){
						to.setTotalMaxInTake(groupMaxIntakeMap.get(Integer.parseInt(maxIntake[0].toString())));
						to.setCheckBoxDisplay(groupMaxIntakeCompleteMap.get(Integer.parseInt(maxIntake[0].toString())));
						to.setDisplay(true);
					}else
						to.setTotalMaxInTake("0/0");
					maxIntakeMap.put(Integer.parseInt(maxIntake[0].toString()), to);
				}
			}
			
			List<Integer> existsList = new ArrayList<Integer>();
			String query = "select a from AssignCertificateCourse a join a.assignCertificateCourseDetails acd where a.isActive=1 and acd.certificateCourse.isActive=1 and a.semType='"
					+ newStudentCertificateCourseForm.getSemType()
					+ "' and a.academicYear="
					+ year
					+ " and a.course.id=" + courseId + " group by a.id";
			List mandatoryList = txn.getDataForQuery(query);
			if (mandatoryList != null && !mandatoryList.isEmpty()) {
				Iterator<AssignCertificateCourse> mandatoryitr = mandatoryList .iterator();
				while (mandatoryitr.hasNext()) {
					AssignCertificateCourse bo = mandatoryitr .next();
					if (bo.getAssignCertificateCourseDetails() != null
							&& !bo.getAssignCertificateCourseDetails()
									.isEmpty()) {
						Iterator<AssignCertificateCourseDetails> acdItr = bo
								.getAssignCertificateCourseDetails().iterator();
						while (acdItr.hasNext()) {
							AssignCertificateCourseDetails acdBo = (AssignCertificateCourseDetails) acdItr
									.next();
							CertificateCourseTO to = new CertificateCourseTO();
							to.setId(acdBo.getCertificateCourse().getId());
							to.setCourseName(acdBo.getCertificateCourse()
									.getCertificateCourseName());
							if (maxIntakeMap.containsKey(acdBo .getCertificateCourse().getId())) {
								to.setMaxIntake((maxIntakeMap.get(acdBo.getCertificateCourse().getId())).getMaxInTake());
								to.setGroupMaxIntake((maxIntakeMap.get(acdBo.getCertificateCourse().getId())).getTotalMaxInTake());
								to.setDisplay((maxIntakeMap.get(acdBo.getCertificateCourse().getId())).isDisplay());
								to.setCheckBoxDisplay((maxIntakeMap.get(acdBo.getCertificateCourse().getId())).isCheckBoxDisplay());
							}
							existsList.add(to.getId());
							mandatorycourseList.add(to);
						}
					}

				}
			}
			String optionalQuery = " from CertificateCourse c where c.isActive=1 and c.semType='" + newStudentCertificateCourseForm.getSemType() + "' and c.year=" + year;
			List optionalList = txn.getDataForQuery(optionalQuery);
			if (optionalList != null && !optionalList.isEmpty()) {
				Iterator<CertificateCourse> mandatoryitr = optionalList .iterator();
				while (mandatoryitr.hasNext()) {
					CertificateCourse bo = (CertificateCourse) mandatoryitr
							.next();
					if (!existsList.contains(bo.getId())) {
					
						CertificateCourseTO to = new CertificateCourseTO();
						to.setId(bo.getId());
						to.setCourseName(bo.getCertificateCourseName());
						to.setMaxIntake(Integer.toString(bo.getMaxIntake()));
						if(bo.getExtracurricular()!=null && !bo.getExtracurricular())
							if (maxIntakeMap.containsKey(bo.getId())) {
								if((maxIntakeMap.get(bo.getId())).isDisplay()){
									to.setMaxIntake((maxIntakeMap.get(bo.getId())).getMaxInTake());
									to.setGroupMaxIntake((maxIntakeMap.get(bo.getId())).getTotalMaxInTake());
									to.setDisplay((maxIntakeMap.get(bo.getId())).isDisplay());
									to.setCheckBoxDisplay((maxIntakeMap.get(bo.getId())).isCheckBoxDisplay());
									optionalCourseList.add(to);
								}
							}
						else{
							extraCurricularCourseList.add(to);
						}
					}
				}
			}
		}
		
		
		Collections .sort(mandatorycourseList);
		Collections.sort(optionalCourseList);
		Collections.sort(extraCurricularCourseList);
		newStudentCertificateCourseForm.setMandatorycourseList(mandatorycourseList);
		newStudentCertificateCourseForm.setOptionalCourseList(optionalCourseList);
		newStudentCertificateCourseForm.setExtraCurricularCourseList(extraCurricularCourseList);
		
		log.info("Exit from getCertificateCourseByStudent");
	}
	/**
	 * @param newStudentCertificateCourseForm
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param newStudentCertificateCourseForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveCertificateCourse( NewStudentCertificateCourseForm newStudentCertificateCourseForm) throws Exception {
		log.info("Entered into saveCertificateCourse");
		StudentCertificateCourse studentCertificateCourse = new StudentCertificateCourse();
		CertificateCourse certificateCourse = new CertificateCourse();
		Student student = new Student();
		if(newStudentCertificateCourseForm.getGroupId()>0){
			CCGroup group=new CCGroup();
			group.setId(newStudentCertificateCourseForm.getGroupId());
			studentCertificateCourse.setGroups(group);
		}
		List<CertificateCourseTO> mandatoryCourseTOList = newStudentCertificateCourseForm .getMandatorycourseList();
		if (mandatoryCourseTOList != null && mandatoryCourseTOList.size() > 0) {
			Iterator<CertificateCourseTO> itr = mandatoryCourseTOList
					.iterator();
			while (itr.hasNext()) {
				CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr .next();
				if (certificateCourseTO.getCourseCheck() == null || !certificateCourseTO.getCourseCheck() .equalsIgnoreCase("on")) {
					continue;
				}
				student.setId(newStudentCertificateCourseForm.getStudentId());
				studentCertificateCourse.setStudent(student);
				certificateCourse.setId(certificateCourseTO.getId());
				studentCertificateCourse .setCertificateCourse(certificateCourse);
				studentCertificateCourse.setSchemeNo(Integer.parseInt(newStudentCertificateCourseForm.getSchemeNo()));
				studentCertificateCourse.setCreatedBy(newStudentCertificateCourseForm.getUserId());
				studentCertificateCourse.setModifiedBy(newStudentCertificateCourseForm.getUserId());
				studentCertificateCourse.setCreatedDate(new Date());
				studentCertificateCourse.setLastModifiedDate(new Date());
				studentCertificateCourse.setIsCancelled(false);
				String year=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(newStudentCertificateCourseForm.getStudentId(),"Student",true,"admAppln.appliedYear");
				if(year!=null && Integer.parseInt(year)>=2011)
					studentCertificateCourse.setIsOptional(false);
				else
					studentCertificateCourse.setIsOptional(true);
				studentCertificateCourse.setIsExtraCurricular(false);
				String subjectId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(certificateCourseTO.getId(),"CertificateCourse",true,"subject.id");
				if(subjectId!=null && !subjectId.isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(subjectId));
					studentCertificateCourse.setSubject(subject);
				}
				
			}
		}
		if (newStudentCertificateCourseForm.getOptionalId() != null
				&& !newStudentCertificateCourseForm.getOptionalId().isEmpty()) {
			student.setId(newStudentCertificateCourseForm.getStudentId());
			studentCertificateCourse.setStudent(student);
			certificateCourse.setId(Integer.parseInt(newStudentCertificateCourseForm .getOptionalId()));
			studentCertificateCourse.setCertificateCourse(certificateCourse);
			studentCertificateCourse.setSchemeNo(Integer.parseInt(newStudentCertificateCourseForm.getSchemeNo()));
			studentCertificateCourse.setCreatedBy(newStudentCertificateCourseForm.getUserId());
			studentCertificateCourse.setModifiedBy(newStudentCertificateCourseForm.getUserId());
			studentCertificateCourse.setCreatedDate(new Date());
			studentCertificateCourse.setLastModifiedDate(new Date());
			studentCertificateCourse.setIsCancelled(false);
			studentCertificateCourse.setIsOptional(true);
			studentCertificateCourse.setIsExtraCurricular(false);
			String subjectId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(newStudentCertificateCourseForm.getOptionalId()),"CertificateCourse",true,"subject.id");
			if(subjectId!=null && !subjectId.isEmpty()){
				Subject subject=new Subject();
				subject.setId(Integer.parseInt(subjectId));
				studentCertificateCourse.setSubject(subject);
			}
		}
		
		List<CertificateCourseTO> extraCourseTOList = newStudentCertificateCourseForm .getExtraCurricularCourseList();
		if (extraCourseTOList != null && extraCourseTOList.size() > 0) {
			Iterator<CertificateCourseTO> itr = extraCourseTOList .iterator();
			while (itr.hasNext()) {
				CertificateCourseTO certificateCourseTO = (CertificateCourseTO) itr
						.next();
				if (certificateCourseTO.getCourseCheck() == null
						|| !certificateCourseTO.getCourseCheck()
								.equalsIgnoreCase("on")) {
					continue;
				}
				student.setId(newStudentCertificateCourseForm.getStudentId());
				studentCertificateCourse.setStudent(student);
				studentCertificateCourse.setIsExtraCurricular(true);
				certificateCourse.setId(certificateCourseTO.getId());
				studentCertificateCourse .setCertificateCourse(certificateCourse);
				studentCertificateCourse.setSchemeNo(Integer.parseInt(newStudentCertificateCourseForm.getSchemeNo()));
				studentCertificateCourse.setCreatedBy(newStudentCertificateCourseForm.getUserId());
				studentCertificateCourse.setModifiedBy(newStudentCertificateCourseForm.getUserId());
				studentCertificateCourse.setCreatedDate(new Date());
				studentCertificateCourse.setLastModifiedDate(new Date());
				studentCertificateCourse.setIsCancelled(false);
				studentCertificateCourse.setIsOptional(false);
				String subjectId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(certificateCourseTO.getId(),"CertificateCourse",true,"subject.id");
				if(subjectId!=null && !subjectId.isEmpty()){
					Subject subject=new Subject();
					subject.setId(Integer.parseInt(subjectId));
					studentCertificateCourse.setSubject(subject);
				}
				
			}
		}
		studentCertificateCourse.setFeeAmount(new BigDecimal(newStudentCertificateCourseForm.getFeeAmt()));
		studentCertificateCourse.setIsOnline(newStudentCertificateCourseForm.isOnline());
		INewStudentCertificateCourseTxn txn=NewStudentCertificateCourseTransactionImpl.getInstance();
		int id=txn.saveCertificateCourse(studentCertificateCourse,newStudentCertificateCourseForm.getGroupId());
		if(newStudentCertificateCourseForm.isPaymentRequired()){
		if(id>0){
			ISingleFieldMasterTransaction transaction=SingleFieldMasterTransactionImpl.getInstance();
			StudentCertificateCourse bo=(StudentCertificateCourse)transaction.getMasterEntryDataById(StudentCertificateCourse.class,id);
			bo.setIsCancelled(true);
			String feeQuery="select sum(amount),f.feeHeading.feeGroup.id,f.feeAccount.feeDivision.id, f.feeAccount.id,f.feeAdditional.id from FeeAdditionalAccountAssignment f where f.isActive = 1 and f.amount>0 and f.feeAdditional.certificateCourse.id ="+bo.getCertificateCourse().getId();
			Object[] obj=(Object[])PropertyUtil.getDataForUniqueObject(feeQuery);
			SMSUtils.dedactAmountFromAccount(bo,newStudentCertificateCourseForm.getFeeAmt());
			if(!bo.getIsPaymentFailed()){
				if(obj!=null && obj[0]!=null && obj[1]!=null && obj[2]!=null && obj[3]!=null && obj[4]!=null){
					FeePayment feePayment=NewStudentCertificateCourseHelper.getInstance().getFeePaymentFromStudentCC(id,newStudentCertificateCourseForm,obj);
					IFeePaymentTransaction feeTransaction=FeePaymentTransactionImpl.getInstance();
					//raghu
					//int billNo=feeTransaction.addNewPayment(feePayment,newStudentCertificateCourseForm.getFeeFinancialYearId());
					int billNo=0;
					if(billNo>0){
						bo.setIsCancelled(false);
						PropertyUtil.getInstance().update(bo);
						return true;
					}
					else{
						bo.setIsCancelled(true);
						bo.setIsPaymentFailed(true);
						bo.setStatus("Fee Bill No is Not Generated");
						PropertyUtil.getInstance().update(bo);
						return false;
					}
				}else{
					bo.setIsCancelled(true);
					bo.setIsPaymentFailed(true);
					bo.setStatus("Fee Is not Defined");
					PropertyUtil.getInstance().update(bo);
					return false;
				}
			}else{
				bo.setIsCancelled(true);
				PropertyUtil.getInstance().update(bo);
				newStudentCertificateCourseForm.setMsg(bo.getStatus());
				return false;
			}
		}else{
			return false;
		}
		}else{
			if(id>0)
			return true;
			else return false;
		}
	}
	/**
	 * @param studentId
	 * @throws Exception
	 */
	public void sendSMSToStudent(int studentId,String templateName) throws Exception {
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		Student bo=(Student)txn.getMasterEntryDataById(Student.class,studentId);
		if(bo!=null){
			if(bo.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()){
				String mobileNo="";
				if(bo.getAdmAppln().getPersonalData().getMobileNo1()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
					if(bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
							|| bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || bo.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=bo.getAdmAppln().getPersonalData().getMobileNo1();
				}else{
					mobileNo="91";
				}
				if(bo.getAdmAppln().getPersonalData().getMobileNo2()!=null && !bo.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					mobileNo=mobileNo+bo.getAdmAppln().getPersonalData().getMobileNo2();
				}
				//Application No Added By Manu	
				if(mobileNo.length()==12){
					ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,templateName,Integer.toString(bo.getAdmAppln().getApplnNo()));
				}
			}
		}
	}
	/**
	 * @param newStudentCertificateCourseForm
	 * @return
	 */
	public boolean verifySmartCard( NewStudentCertificateCourseForm newStudentCertificateCourseForm) throws Exception {
		String query="select s.smartCardNo from Student s where s.id="+newStudentCertificateCourseForm.getStudentId()+" and s.smartCardNo like '%"+newStudentCertificateCourseForm.getSmartCardNo()+"'";
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List list=txn.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			newStudentCertificateCourseForm.setPaymentRequired(true);
			return true;
		}else{
			return false;
		}
	}
}
