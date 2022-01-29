package com.kp.cms.transactionsimpl.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.FeeAccountAssignment;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeePaymentDetailFeegroup;
import com.kp.cms.bo.admin.Sports;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.transactions.admin.ICommonTemplateTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class CommonTemplateTransactionImpl implements ICommonTemplateTransaction{
	public static volatile CommonTemplateTransactionImpl commonTemplateTransactionImpl=null;

	/**
	 * @return
	 */
	public static CommonTemplateTransactionImpl getInstance() {
		if(commonTemplateTransactionImpl == null){
			commonTemplateTransactionImpl = new CommonTemplateTransactionImpl();
			return commonTemplateTransactionImpl;
		}
		return commonTemplateTransactionImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAddressPrintTransaction#getRequiredRegdNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo)
			throws Exception {
		Session session = null;
		List<Student> regNoList;
		try {
			session = HibernateUtil.getSession();
			if(StringUtils.isNumeric(regNoFrom) && StringUtils.isNumeric(regNoTo)){
				regNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.registerNo between '" + regNoFrom + "' and '"+ regNoTo +"' and student.admAppln.isCancelled=0").list();
			}
			else{
				regNoList = session.createQuery("from Student student" + " where student.isActive = 1 and student.registerNo between '" + regNoFrom +"' and '"+ regNoTo+"' and student.admAppln.isCancelled=0").list();
			}		
			session.flush();
			return regNoList;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/* 
	 * 
	 * 
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getFeeDetails(java.lang.String)
	 */
	@Override
	public List<FeePayment> getFeeDetails(String stuId, CommonTemplateForm commonTemplateForm)
			throws Exception {
		Session session = null;
		List<FeePayment> feePayment = new ArrayList<FeePayment>();
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select feePayment" +
				 		" from FeePayment feePayment" +
				 		" where feePayment.student.id = '"+stuId + "'"+
				 		" and feePayment.academicYear = '"+commonTemplateForm.getAcademicYear()+"'"+
				 		" and feePayment.isCancelChallan = 0" +
				 		" group by feePayment.id");
			 if(query.list() != null && !query.list().isEmpty()){
					feePayment =   (List<FeePayment>) query.list();
				}
			session.flush();
			return feePayment;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Sports> getSportsList() throws Exception  {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String hqlQuery = "from Sports where isActive=1";
			List<Sports> sportsNames = session.createQuery(hqlQuery).list();
//			session.flush();
			//session.close();
			return sportsNames;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getTuitionFeeDetails(java.lang.String, com.kp.cms.forms.admin.CommonTemplateForm)
	 */
	public BigDecimal getTuitionFeeDetails(String stuId, CommonTemplateForm commonTemplateForm)
	throws Exception {
		Session session = null;
		int feePaymentId = 0;
		int feeDetailId = 0;
		BigDecimal tuitionFee;
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select feePayment.id from FeePayment feePayment where feePayment.student.id = '"+stuId+"' and feePayment.isCancelChallan = 0 and feePayment.academicYear = '"+commonTemplateForm.getAcademicYear()+"'";
			feePaymentId =(Integer)session.createQuery(hqlQuery).uniqueResult();
			String hqlQuery1 = "select feeDetail.id from FeePaymentDetail feeDetail where feeDetail.feePayment.id = '"+feePaymentId+"'";
			feeDetailId = (Integer)session.createQuery(hqlQuery1).uniqueResult();
			String hqlQuery2 = "select feeHeading.feeGroup.id from FeeHeading feeHeading where feeHeading.name = 'TuiTion Fee'";
			List<Integer> feeGroupId = session.createQuery(hqlQuery2).list();
			Query query = session.createQuery("select feePaymentGroup.amount from FeePaymentDetailFeegroup feePaymentGroup where feePaymentGroup.feeGroup.id in(:feeGroupId) and feePaymentGroup.feePaymentDetail.id = "+feeDetailId);
			query.setParameterList("feeGroupId", feeGroupId);
			tuitionFee = (BigDecimal) query.uniqueResult();
			session.flush();
			return tuitionFee;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getFeeDetails(java.lang.String, com.kp.cms.forms.admin.CommonTemplateForm)
	 */
	public List<FeePaymentDetailFeegroup> getFeeGroupDetails(String stuId, CommonTemplateForm commonTemplateForm)throws Exception {

		Session session = null;
		List<Integer> feeDetailId = new ArrayList<Integer>();
		List<FeePaymentDetailFeegroup> feePayment = new ArrayList<FeePaymentDetailFeegroup>();
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "select feePayment.id from FeePayment feePayment where feePayment.student.id = '"+stuId+"' and feePayment.isCancelChallan = 0 and feePayment.academicYear = '"+commonTemplateForm.getAcademicYear()+"'";
			List<Integer> feePaymentId =session.createQuery(hqlQuery).list();
			if(feePaymentId != null && !feePaymentId.isEmpty()){
				Query query1 = session.createQuery("select feeDetail.id from FeePaymentDetail feeDetail where feeDetail.feePayment.id in(:feePaymentId)");
				query1.setParameterList("feePaymentId", feePaymentId);
				feeDetailId = query1.list();
			}
			if(feeDetailId != null && !feeDetailId.isEmpty()){
				Query query = session.createQuery("from FeePaymentDetailFeegroup f where f.feePaymentDetail.id in(:FeeDatailId)");
				query.setParameterList("FeeDatailId", feeDetailId);
				feePayment = query.list();
			}
			session.flush();
			return feePayment;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getStudentMarksDetails(com.kp.cms.bo.admin.Student)
	 */
	@Override
	public List<Object[]> getStudentMarksDetails(Student student) throws Exception {
		Session session = null;
		List<Object[]> marksList =null;
		try{
			session = HibernateUtil.getSession();
			String sqlQuery = 	 " SELECT student.register_no,"
								+" EXAM_definition.name,"
								+" EXAM_definition.year,"
								+" ifnull((select E.month+1 from EXAM_definition E"
								+" inner join EXAM_supplementary_improvement_application ESIA on (ESIA.exam_id=E.id)"
								+" where ESIA.subject_id=subject.id"
								+" and ESIA.student_id=student.id"
								+" and ESIA.scheme_no=classes.term_number limit 1),EXAM_definition.month+1) as SUPmonth,"
								+" subject.name as subjectname,"
								+" EXAM_subject_sections.name as sectionname,"
	    			 
								+" if(if(exam_type.name like '%Regular%' and supdet.theory_marks is not null, supdet.theory_marks,EXAM_marks_entry_details.theory_marks) is not null,"
										+" if(exam_type.name like '%Regular%' and supdet.theory_marks is not null, supdet.theory_marks,EXAM_marks_entry_details.theory_marks),0)+"
										+" if(if(exam_type.name like '%Regular%' and supdet.practical_marks is not null, supdet.practical_marks,EXAM_marks_entry_details.practical_marks) is not null,"
										+" if(exam_type.name like '%Regular%' and supdet.practical_marks is not null, supdet.practical_marks,EXAM_marks_entry_details.practical_marks),0) as totObtain,"
			 
										+" if(exam_type.name like '%Regular%',(if(EXAM_subject_rule_settings.theory_ese_entered_max_mark is not null,EXAM_subject_rule_settings.theory_ese_entered_max_mark,0)+"
										+" if(EXAM_subject_rule_settings.practical_ese_entered_max_mark is not null,EXAM_subject_rule_settings.practical_ese_entered_max_mark,0)),(EXAM_subject_rule_settings_sub_internal.maximum_mark)) as maxTot,"
										
										+" if(exam_type.name like '%Regular%',(if(EXAM_subject_rule_settings.theory_ese_minimum_mark is not null,EXAM_subject_rule_settings.theory_ese_minimum_mark,0)+"
												+" if(EXAM_subject_rule_settings.practical_ese_minimum_mark is not null,EXAM_subject_rule_settings.practical_ese_minimum_mark,0)),(EXAM_subject_rule_settings_sub_internal.minimum_mark)) as minTot"
												
	
		
	
								+" FROM   student "
								+" INNER JOIN  adm_appln ON student.adm_appln_id = adm_appln.id"
								+" AND adm_appln.is_cancelled = 0"
								+" INNER JOIN  personal_data ON adm_appln.personal_data_id = personal_data.id"
								+" INNER JOIN 	course ON adm_appln.selected_course_id = course.id "
								+" and course.is_active = 1"
								+" INNER JOIN EXAM_marks_entry ON EXAM_marks_entry.student_id=student.id"
								+" INNER JOIN classes ON EXAM_marks_entry.class_id=classes.id"
								+" INNER JOIN class_schemewise ON class_schemewise.class_id=classes.id"
								+" INNER JOIN curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id =curriculum_scheme_duration.id"
								+" INNER JOIN EXAM_marks_entry_details ON EXAM_marks_entry_details.marks_entry_id=EXAM_marks_entry.id"
								+" INNER JOIN subject ON EXAM_marks_entry_details.subject_id =subject.id"
								+" AND subject.is_active = 1"
								+" AND subject.code not like '%VED%'"
								+" AND subject.code not like '%GD%'"
								+" AND subject.code not like '%CA%'"
								+" AND subject.code not like '%EEE%'"
								+" INNER JOIN EXAM_subject_rule_settings ON EXAM_subject_rule_settings.subject_id = subject.id" 
								+" AND EXAM_subject_rule_settings.course_id = course.id"
								+" AND EXAM_subject_rule_settings.academic_year=curriculum_scheme_duration.academic_year"
								+" AND EXAM_subject_rule_settings.scheme_no=classes.term_number"
								+" INNER JOIN EXAM_sub_definition_coursewise ON EXAM_sub_definition_coursewise.subject_id = subject.id"
								+" AND EXAM_sub_definition_coursewise.course_id = EXAM_subject_rule_settings.course_id"
								+" and EXAM_sub_definition_coursewise.scheme_no=classes.term_number"
								+" and EXAM_sub_definition_coursewise.academic_year=curriculum_scheme_duration.academic_year"
								+" INNER JOIN EXAM_definition ON EXAM_marks_entry.exam_id = EXAM_definition.id"
								+" and EXAM_definition.del_is_active = 1"
								+" INNER JOIN EXAM_subject_sections ON EXAM_sub_definition_coursewise.subject_section_id=EXAM_subject_sections.id"
								+" LEFT JOIN EXAM_student_detention_rejoin_details ON EXAM_student_detention_rejoin_details.student_id = student.id" 
								+" and EXAM_student_detention_rejoin_details.scheme_no = classes.term_number "
								+" LEFT JOIN  EXAM_update_exclude_withheld ON EXAM_update_exclude_withheld.student_id = student.id" 
								+" and EXAM_update_exclude_withheld.exam_id = EXAM_definition.id "
								+" and EXAM_update_exclude_withheld.course_id = course.id "
								+" and EXAM_update_exclude_withheld.scheme_no = classes.term_number"
								+" LEFT JOIN EXAM_internal_exam_type ON EXAM_definition.internal_exam_type_id = EXAM_internal_exam_type.id"
								+" INNER JOIN exam_type ON exam_type.id=EXAM_definition.exam_type_id"
								+" LEFT JOIN  EXAM_subject_rule_settings_sub_internal ON EXAM_subject_rule_settings_sub_internal.internal_exam_type_id=EXAM_internal_exam_type.id"
								+" AND EXAM_subject_rule_settings_sub_internal.subject_rule_settings_id=EXAM_subject_rule_settings.id"
								+" AND EXAM_subject_rule_settings_sub_internal.is_theory_practical='t'"
								+" LEFT JOIN EXAM_marks_entry as supent ON supent.student_id=student.id"
								+" and (supent.exam_id in (select id from EXAM_definition where EXAM_definition.name like '%sup%'))"
								+" LEFT JOIN EXAM_marks_entry_details as supdet ON supdet.marks_entry_id=supent.id"
								+" and supdet.subject_id =subject.id"
								+" where student.register_no='"+student.getRegisterNo()+"'"
								+" and EXAM_definition.name like '%I PU%'"
								+" group by student.register_no,EXAM_definition.id,subject.id,EXAM_subject_sections.id"
								+" order by  EXAM_sub_definition_coursewise.subject_order";
			
			Query query = session.createSQLQuery(sqlQuery);
			marksList = query.list();
		}catch (Exception e) {
			throw  new BusinessException(e);
		}
		return marksList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getTuitionFeeForClass(com.kp.cms.bo.admin.Student, com.kp.cms.forms.admin.CommonTemplateForm)
	 */
	@Override
	public List<FeeAccountAssignment> getTuitionFeeForClass(Student student, CommonTemplateForm commonTemplateForm) throws Exception {

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			int courseId = student.getClassSchemewise().getClasses().getCourse().getId(); 
			int programId = student.getClassSchemewise().getClasses().getCourse().getProgram().getId();
			String hqlQuery = "from FeeAccountAssignment faa"
								+" where faa.fee.course.id = "+courseId
								+" and faa.fee.program.id = "+programId
								+" and faa.fee.isActive = 1"
								+" and faa.feeHeading.id in (select feeheading.id from FeeHeading feeheading where feeheading.name = 'Tuition Fee')"
								+" and faa.fee.academicYear ="+ commonTemplateForm.getAcademicYear()
								+" and faa.amount <> 0";
			List<FeeAccountAssignment> feeAccountAssignments = session.createQuery(hqlQuery).list();
			session.flush();
			return feeAccountAssignments;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getStudent(com.kp.cms.forms.admin.CommonTemplateForm)
	 */
	@Override
	public Student getStudent(CommonTemplateForm commonTemplateForm) {
		Session session = null;
		Student student = new Student();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String hqlQuery = "from Student s where s.registerNo='"+commonTemplateForm.getRegNo()+"'";
			student = (Student) session.createQuery(hqlQuery).uniqueResult();
//			session.flush();
			//session.close();
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
		}
		return student;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ICommonTemplateTransaction#getFeeHeadingDetails(java.lang.String, com.kp.cms.forms.admin.CommonTemplateForm)
	 */
	@Override
	public List<Object[]> getFeeHeadingDetails(String stuId,
			CommonTemplateForm commonTemplateForm) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String sqlQuery =  "SELECT fee_payment.bill_no,"+
			 				   " DATE_FORMAT(fee_payment.fee_paid_date,'%d/%m/%y') as date,"+
						       " fee_payment_detail.total_amount,"+  
						       " fee_payment_detail.concession_amount,"+  
							   " fee_payment.total_amount,"+         
						       " student.register_no,"+  
						       " fee_heading.name,"+         
							   " fee_payment_detail.total_nonadditional_amount,"+  
						       " fee_payment_detail.total_additional_amount,"+  
									" if(adm_appln.applied_year=2010 and fee_payment.academic_year=2010,fee_payment_detail_feegroup.amount,sum(fee_account_assignment.amount)) as amount,"+  
									" fee_payment_detail.id,"+ 
									" fee_payment_applicant_details.semester_no"+
									" FROM student"+  
									" INNER JOIN adm_appln ON student.adm_appln_id = adm_appln.id"+  
									" INNER JOIN course ON adm_appln.selected_course_id = course.id"+  
									" INNER JOIN personal_data ON adm_appln.personal_data_id = personal_data.id"+  
									" INNER JOIN fee_payment ON fee_payment.student_id = student.id"+  
									" AND fee_payment.course_id=course.id"+  
									" INNER JOIN fee_payment_detail ON fee_payment_detail.fee_payment_id = fee_payment.id"+  
									" INNER JOIN fee_payment_applicant_details ON fee_payment.id=fee_payment_applicant_details.fee_payment_id"+
									" INNER JOIN fee_payment_detail_feegroup ON fee_payment_detail_feegroup.fee_payment_detail_id =fee_payment_detail.id"+  
									" INNER JOIN fee_group ON fee_payment_detail_feegroup.fee_group_id = fee_group.id"+  
									" INNER JOIN fee_division ON fee_payment.fee_division_id = fee_division.id"+  
									" INNER JOIN fee_account ON fee_payment_detail.fee_acount_id = fee_account.id"+  
									" AND fee_account.fee_division_id = fee_division.id"+  
									" INNER JOIN fee_heading ON fee_heading.fee_group_id = fee_group.id"+  
									" INNER JOIN admitted_through ON adm_appln.admitted_through_id = admitted_through.id"+  
									" INNER JOIN fee ON fee.course_id = course.id"+  
									" AND fee.fee_division_id = fee_division.id"+  
									" AND fee.fee_group_id = fee_group.id"+  
									" and fee.academic_year = adm_appln.applied_year"+ 
									" and fee_payment_applicant_details.semester_no=fee.semester_no"+
									" and fee.is_active = 1"+  
															
									" INNER JOIN fee_account_assignment ON fee_account_assignment.admitted_through_id = admitted_through.id"+  
									" AND fee_account_assignment.fee_id = fee.id"+  
									" AND fee_account_assignment.fee_account_id = fee_account.id"+  
									" AND fee_account_assignment.fee_heading_id = fee_heading.id"+  
																		
									" where student.is_admitted=1"+  
									" and adm_appln.is_cancelled=0"+  
									" and fee_payment.is_challan_canceled = 0"+
									" and fee_payment.is_fee_paid = 1"+
									" and fee_payment.academic_year='"+commonTemplateForm.getAcademicYear()+"'"+ 
									" and student.register_no = '"+stuId+"'"+  
									" and student.is_admitted=1"+  
									" and adm_appln.is_cancelled=0"+  
																		 
									" and if(adm_appln.applied_year=2010 and fee_payment.academic_year=2010,fee_account_assignment.amount>=0,fee_account_assignment.amount>0)"+  
									" group by student.register_no,if(adm_appln.applied_year=2010 and fee_payment.academic_year=2010,fee_group.id,fee_heading.id),fee_payment.bill_no";
						           
			Query query = session.createSQLQuery(sqlQuery);
			List<Object[]> feePayment = query.list();
			session.flush();
			return feePayment;
		} catch (Exception e) {
			throw  new BusinessException(e);
		}
	}

	/**
	 * @param feePaymentDetailId
	 * @param feePaymentDetailId1 
	 * @return
	 */
	public List<FeePaymentDetailFeegroup> getAdditionalFeeDetails(int feePaymentDetailId, int feePaymentDetailId1) {
		Session session = null;
		List<FeePaymentDetailFeegroup> feeGroup = new ArrayList<FeePaymentDetailFeegroup>();
		try {
			session = HibernateUtil.getSession();
			String hqlQuery = "from FeePaymentDetailFeegroup f where f.isOptional= 1 and f.feePaymentDetail.id='"+feePaymentDetailId+"'";
			feeGroup = session.createQuery(hqlQuery).list();
			String hqlQuery1 = "from FeePaymentDetailFeegroup f where f.isOptional= 1 and f.feePaymentDetail.id='"+feePaymentDetailId1+"'";
			List<FeePaymentDetailFeegroup> feeGroup1 = session.createQuery(hqlQuery1).list();
			feeGroup.addAll(feeGroup1);
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}	
		}
		return feeGroup;
	}
}
