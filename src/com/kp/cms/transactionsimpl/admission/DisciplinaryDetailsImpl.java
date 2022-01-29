package com.kp.cms.transactionsimpl.admission;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlDisciplinaryDetails;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.hostel.FineEntryBo;
import com.kp.cms.bo.hostel.HlStudentAttendance;
import com.kp.cms.bo.hostel.HostelExemptionBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.transactionsimpl.exam.ExamGenImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
@SuppressWarnings("unchecked")
public class DisciplinaryDetailsImpl extends ExamGenImpl {

	
	
	public StringBuffer getSerchedStudentsQuery(DisciplinaryDetailsForm objForm)
	throws Exception {
	String rollRegNo=objForm.getRollRegNo();
	String firstName=objForm.getTempName();
	StringBuffer query = new StringBuffer("from Student s where s.admAppln.isCancelled=0 and s.isAdmitted=1");
	if (rollRegNo != null && !StringUtils.isEmpty(rollRegNo.trim())) {
		query = query.append(" and s.rollNo='"+ rollRegNo+"' OR s.registerNo= '"+ rollRegNo+"'");
	}
	
	if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
		query = query.append(" and s.admAppln.personalData.firstName like '"
				+firstName+"%'");
	}
	
				
	query.append(" order by s.admAppln.personalData.firstName");
return query;
}
	
	public List<Integer> getSerchedStudentsPhotoList(DisciplinaryDetailsForm objForm)
			throws Exception {
		Session session = null;
		String rollRegNo=objForm.getRollRegNo();
		String firstName=objForm.getTempName();
		List<Integer> stList;
		try {
			StringBuffer query = new StringBuffer(
					"select st.id from ApplnDoc a"
					+ " join a.admAppln.students st  where  a.isPhoto=1 and  (a.document <> '' or a.document <> null) and st.isAdmitted=1 and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1 ");
			if (rollRegNo != null && !StringUtils.isEmpty(rollRegNo.trim())) {
				query = query.append(" and (st.rollNo='"+ rollRegNo+"' OR st.registerNo= '"+ rollRegNo+"')");
			}
			
			if (firstName != null && !StringUtils.isEmpty(firstName.trim())) {
				query = query.append(" and a.admAppln.personalData.firstName like '"
						+ firstName+"%'");
			}
			query = query.append(" order by a.admAppln.personalData.firstName");
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			stList = queri.list();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return stList;
	}

	
	public List<Student> getSerchedStudents(StringBuffer query)
	throws Exception {
Session session = null;
List<Student> stList;
//List<Object[]> stList = new ArrayList<Object[]>();
try {
	session = HibernateUtil.getSession();
	Query queri = session.createQuery(query.toString());
	stList = queri.list();
} catch (Exception e) {
	throw new ApplicationException(e);
}
return stList;
}	
	
	public Integer getStudentId(String rollRegNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Integer studentID = null;
		String HQL = null;
		HQL = "select s.id from Student s where (s.rollNo= :rollNo OR s.registerNo= :registerNo) and s.admAppln.isCancelled=0 and s.isAdmitted=1";

		Query query = session.createQuery(HQL);
		query.setParameter("rollNo", rollRegNo);
		query.setParameter("registerNo", rollRegNo);

		Iterator<Object> list = query.list().iterator();
		while (list.hasNext()) {
			Object object = (Object) list.next();
			if (object != null) {
				studentID = Integer.parseInt(object.toString());
			}
		}
		return studentID;
	}
	public Integer getStudentId1(String rollRegNo, String AppNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Integer studentID = null;
		StringBuffer query = new StringBuffer(
			" select s.id from Student s where s.admAppln.isCancelled=0 and s.isAdmitted=1");
			if (rollRegNo != null && !StringUtils.isEmpty(rollRegNo.trim()))
			{
			query = query.append(" and (s.rollNo= '"+rollRegNo+"' OR s.registerNo= '"+rollRegNo+"')");
			}
			else if (AppNo != null && !StringUtils.isEmpty(AppNo.trim()))
			{
				query = query.append(" and s.admAppln.applnNo= " + AppNo);
			}
		
			Query query1= session.createQuery(query.toString());
		Iterator<Object> list = query1.list().iterator();
		while (list.hasNext()) {
			Object object = (Object) list.next();
			if (object != null) {
				studentID = Integer.parseInt(object.toString());
			}
		}
		return studentID;
	}
	public boolean checkStudentIsActive(DisciplinaryDetailsForm stForm)
	throws Exception {
		Session session = null;
		String RegRollNo = stForm.getRollRegNo();
		boolean singlefld = false;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			StringBuffer query = new StringBuffer(
			" from Student st where st.admAppln.isCancelled=1");
			if (RegRollNo != null && !StringUtils.isEmpty(RegRollNo.trim()))
			{
			query = query.append(" and (st.rollNo= '"+ RegRollNo+"' OR st.registerNo= '" + RegRollNo+"')");
			
			Query query1= session.createQuery(query.toString());
			List<Student> list=  query1.list();
			if(list!=null && !list.isEmpty()){
				singlefld=true;
				Iterator<Student> iterator = list.iterator();
				while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			stForm.setRemarks(student.getAdmAppln().getCancelRemarks());
		}
	}
	}
} catch (Exception e) {
	throw new ApplicationException(e);
}
return singlefld;
}	
	
	public Student getStudentDetails(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "from Student s where s.id= :studentId ";
//		HQL = "select s from Student s"+
//		" join s.admAppln.admapplnAdditionalInfo"+
//		" where s.id= :studentId ";
		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		return (Student) query.uniqueResult();
	}
	
	
	
	public String getCourseId(String name) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select s.id  from Course s where name= :name";
		Query query = session.createQuery(HQL);
		query.setParameter("name", name);
		return (String) query.uniqueResult();
		
		
	}
	public Integer getIsRemarcks(int userId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Integer studentID = 0;
		String HQL = null;
		HQL = " from Users s where id= :userId and isActive=1";
		Query query = session.createQuery(HQL);
		query.setParameter("userId", userId);
		Users objUsers = (Users) query.uniqueResult();
		if (objUsers.getIsAddRemarks() != null) {
			if (objUsers.getIsAddRemarks()) {
				studentID = 1;
			} else {
				studentID = 0;
			}
		}
		return studentID;
	}

	public List getRemarcks(Integer studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select s.lastModifiedDate, s.comments, " +
				"(select users.employee.firstName from Users users where users.id= s.modifiedBy and users.isActive=1),s.id," +
				"(select users.userName from Users users where users.id= s.modifiedBy and users.isActive=1) " +
				"from StudentRemarks s where s.student.id= :studentId ";
		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		return query.list();
	}
	
	public HashMap getClassID(int studentId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select s.classSchemewise.classes.name, s.classSchemewise.curriculumSchemeDuration.id "
				+ "from Student s where s.id= :studentId ";

		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		Iterator<Object> itr = query.list().iterator();
		int id = 0;
		String name = null;
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			if (obj[0] != null) {
				name = obj[0].toString();
			}
			if (obj[1] != null) {
				id = Integer.parseInt(obj[1].toString());
			}
		}
		map.put(id, name);
		return map;
	}
	
	public StudentPreviousClassHistory getClassIDPrevious(int studentId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = " from StudentPreviousClassHistory c1 where c1.student.id = '"+studentId+"' and c1.schemeNo = '" +schemeNo+ "'";
		Query query = session.createQuery(HQL);
		return (StudentPreviousClassHistory) query.uniqueResult();		
		
	}
	public StudentPreviousClassHistory getClassIDPreviousView(int studentId, int classId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = " from StudentPreviousClassHistory c1 where c1.student.id = '"+studentId+"' and c1.classes.id = '" +classId+ "'";
		Query query = session.createQuery(HQL);
		return (StudentPreviousClassHistory) query.uniqueResult();		
		
	}
	public List getClassDetailsPrevious(int studentId, int schemeNo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "select c1.classes.name,c1.id from StudentPreviousClassHistory c1 where c1.student.id = '"+studentId+"' and c1.schemeNo = '" +schemeNo+ "'";
		Query query = session.createQuery(HQL);
		return query.list();	
		
	}
	
	public List<CurriculumSchemeSubject> getSubjectBySubjectGroupId(
			Integer subjectgroupId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		HQL = "from CurriculumSchemeSubject css  where css.id= :subjectgroupId ";

		Query query = session.createQuery(HQL);
		query.setParameter("subjectgroupId", subjectgroupId);
		return query.list();

	}
	public List getFeeDetails(int studentId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String HQL = null;
		/*HQL = "select f.semesterNo,f.feePayment.totalAmount,f.feePayment.totalBalanceAmount" +
				"from FeePaymentApplicantDetails f where f.feePayment.student.id= :studentId and " +
				"f.feePayment.isCancelChallan=0 group by f.semesterNo, f.feePayment.student.id"; //and f.feePayment.academicYear= :academicYear";
*/
		/*HQL="select f.semesterNo,f.feePayment.totalAmount,f.feePayment.totalFeePaid,f.feePayment.totalConcessionAmount,f.feepayment.totalInstallmentAmount,f.feepayment.id" +
				"from FeePaymentApplicantDetails f where f.feePayment.student.id= :studentId and " +
				"f.feePayment.isCancelChallan=0 group by f.semesterNo, f.feePayment.student.id";*/
		HQL="select f.semesterNo,f.feePayment.totalAmount,f.feePayment.totalFeePaid,f.feePayment.totalConcessionAmount,f.feePayment.totalConcessionAmount,f.feePayment.id "+ 
			" from FeePaymentApplicantDetails f where f.feePayment.student.id=:studentId "+
			" and f.feePayment.isCancelChallan=0 group by f.semesterNo,f.feePayment.student.id ";	
		Query query = session.createQuery(HQL);
		query.setParameter("studentId", studentId);
		return query.list();

	}

	public List<EdnQualification> getEducationDetailsByStudentId(Integer studentId) {
		Session session = null;
		List<EdnQualification> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("select s.admAppln.personalData.ednQualifications " +
					"from Student s where s.id="+studentId);
			selectedCandidatesList = selectedCandidatesQuery.list();
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return selectedCandidatesList;
	}

	public List<Integer> getPeriodList(
			DisciplinaryDetailsForm disciplinaryDetailsForm)
			throws Exception {
		Session session =null;
		List<Integer> list = null;
		try {
			session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery("SELECT period.id  FROM (period period " +
			 		"INNER JOIN class_schemewise class_schemewise " +
			 		"ON (period.class_schemewise_id = class_schemewise.id))" +
			 		" INNER JOIN student student ON (student.class_schemewise_id = class_schemewise.id) WHERE student.id =" + Integer.parseInt(disciplinaryDetailsForm.getStudentID()));
			 list=query.list();
			 session.flush();
		 } catch (Exception e) {
			 session.flush();
			 throw  new ApplicationException(e); 
		 }
		 return list;
	}
	
	
	// getting ExamStudentDetentionDetails from database by using studentId
	public List<ExamStudentDetentionRejoinDetails> getExamStudentDetentionRejoinDetails(int studentId)throws Exception{
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionDetails;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ExamStudentDetentionRejoinDetails exam where  exam.student.id = "+studentId);
			examStudentDetentionDetails=query.list();
			
		}catch(Exception exception){
		session.flush();
		throw new ApplicationException(exception);
		}
		return examStudentDetentionDetails;
	}

	

	public List<ExamStudentDetentionRejoinDetails> getExamStudentDetentionRejoinDetails(Integer year) {
		// TODO Auto-generated method stub
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetailslist=new ArrayList<ExamStudentDetentionRejoinDetails>();
		Session session=null;
		try{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			String query="from ExamStudentDetentionRejoinDetails es"
						+" where (es.detain=1 or es.discontinued=1) and (es.rejoin=0 or es.rejoin is null)";
			Query queri=session.createQuery(query);
		examStudentDetentionRejoinDetailslist=queri.list();
		}catch (Exception exception) {
			// TODO: handle exception
			if(session!=null){
				session.flush();
				session.close();
			}
			
		}
		
		return examStudentDetentionRejoinDetailslist;
	}

	public List<ExamStudentDetentionRejoinDetails> getStudentDetentionRejoinDetails(
			Integer studentId) {
		List<ExamStudentDetentionRejoinDetails> examStudentDetentionRejoinDetailslist=new ArrayList<ExamStudentDetentionRejoinDetails>();
		Session session=null;
		try{
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			String query="from ExamStudentDetentionRejoinDetails es"
						+" where (es.detain=1 or es.discontinued=1) and (es.rejoin=0 or es.rejoin is null) and es.student.id="+studentId;
			Query queri=session.createQuery(query);
		examStudentDetentionRejoinDetailslist=queri.list();
		}catch (Exception exception) {
			// TODO: handle exception
			if(session!=null){
				session.flush();
				session.close();
			}
			
		}
		
		return examStudentDetentionRejoinDetailslist;
	}
	
	public StudentRemarks getRemarksEdit(int remarksId)throws Exception
	{
		
		Session session = null;
		StudentRemarks studentRemarks = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from StudentRemarks a where a.id = "+remarksId);
			studentRemarks = (StudentRemarks)query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		
		return studentRemarks;
	}

	public void update(int remarksId, String remarksName, String userId)throws Exception { // TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			StudentRemarks remarks = (StudentRemarks)session.get(StudentRemarks.class,remarksId);
			if(remarks!=null && !remarks.toString().isEmpty()){
				remarks.setComments(remarksName);
				remarks.setLastModifiedDate(new Date());
				remarks.setModifiedBy(userId);
				session.update(remarks);
			}
			tx.commit();
			session.flush();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
	}
	/**
	 * getInstallment amount for the student 
	 * @param studentId
	 * @return
	 * @throws Exception 
	 */
	public List getInstallmentAmount(String feePaymentId) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL = null;
			HQL="select f.amountPaid	from FeePaymentDetail f where f.feePayment.id=:feepaymentId  and f.isInstallmentPayment=1";
			Query query = session.createQuery(HQL);
			query.setParameter("feepaymentId",Integer.parseInt(feePaymentId));
			return query.list();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 

	}
	public boolean getCourseCompletionStatus(String studentId)throws Exception{
		boolean finalYear;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			java.util.Date utildate=new java.util.Date();
			java.sql.Date sqlDate=new java.sql.Date(utildate.getTime());
			String HQL = null;
			HQL="from Student s where s.id="+studentId +
					" and s.classSchemewise.curriculumSchemeDuration.endDate<='"+sqlDate+
					"' and s.classSchemewise.classes.termNumber=s.classSchemewise.curriculumSchemeDuration.curriculumScheme.noScheme";
			//HQL="select s.registerNo from Student s where s.id='"+studentId+"' and s.classSchemewise.curriculumSchemeDuration.endDate <="+new Date() ;
			Query query = session.createQuery(HQL);
			Student student=(Student)query.uniqueResult();
			if(student!=null){
				finalYear=true;
			}else{
				finalYear=false;
			}
			
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 

	
		return finalYear;
	}
	public boolean getStatusOfStudent(String querystring)throws Exception{
		boolean status;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			Query query = session.createSQLQuery(querystring);
			List<Object> obj=query.list();
			int passFial=1;
			int aggregate_fail=1;
			Iterator<Object> iterator=obj.iterator();
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();
				passFial=Integer.parseInt(object[1].toString());
				aggregate_fail=Integer.parseInt(object[2].toString());
			}
			if(passFial==0 && aggregate_fail==0){
				status=true;
			}else{
				status=false;
			}
			
			
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
		return status;
	}
	public HlAdmissionBo getHostelDetailsForStudent(String studentId)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL="from HlAdmissionBo h where h.isActive=1 and (h.isCancelled=0 or h.isCancelled is null) " +
					"and h.isCheckedIn=1 and (h.checkOut=0 or h.checkOut is null) and h.studentId.id="+studentId;
			Query query = session.createQuery(HQL);
			HlAdmissionBo hlAdmissionBo=(HlAdmissionBo)query.uniqueResult();
			return hlAdmissionBo;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	public List<HlLeave> getHostelLeave(int id,Date date)throws Exception{
		String date1=CommonUtil.formatDates(date);
		java.sql.Date sqlDate=CommonUtil.ConvertStringToSQLDate(date1);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL="from HlLeave h where h.isActive=1 and (h.isCanceled=0 or h.isCanceled is null) and h.hlAdmissionBo.id="+id+" and h.leaveFrom >= '"+sqlDate+"'"; 
			Query query = session.createQuery(HQL);
			List<HlLeave> hlLeave=(List<HlLeave>)query.list();
			return hlLeave;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
public Map<java.sql.Date,HlStudentAttendance> getAbscentDetails(int id,Date date)throws Exception{
		String date1=CommonUtil.formatDates(date);
		java.sql.Date sqlDate=CommonUtil.ConvertStringToSQLDate(date1);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Map<java.sql.Date,HlStudentAttendance> map=new HashMap<java.sql.Date, HlStudentAttendance>();
		try{
			String HQL="from HlStudentAttendance h where h.isActive=1 " +
					" and h.hlAdmissionBo.id="+id+" and h.date >= '"+sqlDate+"'"; 
			Query query = session.createQuery(HQL);
			List<HlStudentAttendance> hlStudentAttendances=(List<HlStudentAttendance>)query.list();
			if(hlStudentAttendances!=null && !hlStudentAttendances.isEmpty()){
				Iterator<HlStudentAttendance> iterator=hlStudentAttendances.iterator();
				while (iterator.hasNext()) {
					HlStudentAttendance hlStudentAttendance = (HlStudentAttendance) iterator.next();
					map.put(new java.sql.Date(hlStudentAttendance.getDate().getTime()), hlStudentAttendance);
				}
			}
			return map;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	public List<HostelHolidaysBo> getHostelHolidays(int id,String hostelId,String blockId,String unitId)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL="from HostelHolidaysBo h where h.isActive=1 and h.courseId.id="+id+
					" and h.hostelId.id="+Integer.parseInt(hostelId)+" and h.blockId.id="+Integer.parseInt(blockId)+" and h.unitId.id="+Integer.parseInt(unitId); 
			Query query = session.createQuery(HQL);
			List<HostelHolidaysBo> hostelHolidaysBos=(List<HostelHolidaysBo>)query.list();
			return hostelHolidaysBos;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	public List<HlDisciplinaryDetails> getHlDisciplinaryDetails(int id)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<HlDisciplinaryDetails> hlDisciplinaryDetails=null;
		try{
			String HQL="from HlDisciplinaryDetails h where h.isActive=1 and h.hlAdmissionBo.id="+id; 
			Query query = session.createQuery(HQL);
			hlDisciplinaryDetails=(List<HlDisciplinaryDetails>)query.list();
			return hlDisciplinaryDetails;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	}
	public List<FineEntryBo> getFineDetails(int id)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<FineEntryBo> fineEntryBos=null;
		try{
			String HQL="from FineEntryBo h where h.isActive=1 and h.hlAdmissionId.id="+id; 
			Query query = session.createQuery(HQL);
			fineEntryBos=(List<FineEntryBo>)query.list();
			return fineEntryBos;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	public List<ExamDefinitionBO> getExamDefinationBo(List<Integer> list)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<ExamDefinitionBO> examDefinitionBOs=null;
		try{
			Query query = session.createQuery("from ExamDefinitionBO h where h.id in (:list)");
			query.setParameterList("list", list);
			examDefinitionBOs=(List<ExamDefinitionBO>)query.list();
			return examDefinitionBOs;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	
	public List<HlLeave> getHostelLeaveWhichApproved(int id,Date date)throws Exception{
		String date1=CommonUtil.formatDates(date);
		java.sql.Date sqlDate=CommonUtil.ConvertStringToSQLDate(date1);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL="from HlLeave h where h.isActive=1 and h.isApproved=1 and (h.isCanceled=0 or h.isCanceled is null) and h.hlAdmissionBo.id="+id+" and h.leaveFrom >= '"+sqlDate+"'"; 
			Query query = session.createQuery(HQL);
			List<HlLeave> hlLeaves=(List<HlLeave>)query.list();
			return hlLeaves;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	
	public List<HostelExemptionBo> getHostelExemptionList(int id,Date checkInDate)throws Exception{
		String date1=CommonUtil.formatDates(checkInDate);
		java.sql.Date checkedInDate=CommonUtil.ConvertStringToSQLDate(date1);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			String HQL="select h from HostelExemptionBo h inner join h.hostelExemptionDetailsBo he where he.isActive=1 and h.isActive=1 and  he.hlAdmissionBo.id="+id+" and h.fromDate >= '"+checkedInDate+"'"; 
			Query query = session.createQuery(HQL);
			List<HostelExemptionBo> hlExemption=(List<HostelExemptionBo>)query.list();
			return hlExemption;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	
	
	
	public List getDateList(int studentId,int classId)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List dateList=new LinkedList();
		try{
			String HQL="select distinct attendanceStudents.attendance.attendanceDate,attendanceStudents.attendance.attendanceDate from Student student  inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac  join attendanceStudents.attendance.attendancePeriods ap  join ap.period p where attendanceStudents.attendance.isMonthlyAttendance = 0    and   attendanceStudents.attendance.isActivityAttendance = 0    and  attendanceStudents.attendance.isCanceled = 0 and student.id = "+studentId+" and ac.classSchemewise.classes.id="+classId+" order by attendanceStudents.attendance.attendanceDate"; 
			Query query = session.createQuery(HQL);
			dateList=query.list();
			
			return dateList;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}
	
	public List getSessionAttendanceList(int studentId,int classId)throws Exception{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List attendanceList=new LinkedList();
		try{
			String HQL="select attendanceStudents.attendance.attendanceDate,attendanceStudents.attendance.id, p.periodName, p.id, p.session, attendanceStudents.isPresent, attendanceStudents.isOnLeave, attendanceStudents.isCoCurricularLeave,c.name  from Student student  inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac  join ac.classSchemewise.classes c join attendanceStudents.attendance.attendancePeriods ap  join ap.period p where attendanceStudents.attendance.isMonthlyAttendance = 0    and   attendanceStudents.attendance.isActivityAttendance = 0    and  attendanceStudents.attendance.isCanceled = 0 and student.id = "+studentId+" and ac.classSchemewise.classes.id="+classId+" and p.classSchemewise.classes.id="+classId+" order by attendanceStudents.attendance.attendanceDate,p.periodName "; 
			Query query = session.createQuery(HQL);
			attendanceList=query.list();
			
			return attendanceList;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} 
	
	}

	public int getClassId(int studentId) throws Exception{
		
       Session session = null;
		Integer classId = -1;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("select classSchemewise.classes.id from Student st where st.id = " + Integer.valueOf(studentId));

			List<Object[]> obj = studentQuery.list();
			if(obj!= null && obj.size() > 0){
				Iterator<Object[]> itr = obj.iterator();
				while (itr.hasNext()) {
					Object id = itr.next();
					if(id!=null)
					classId = Integer.parseInt(id.toString());
				}
			}

			return classId;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	public int getCourseId(int classId) throws Exception {
		 Session session = null;
			Integer courseId = -1;
			try {
				session = HibernateUtil.getSession();
				Query studentQuery = session.createQuery("select c.course.id from Classes c where c.id="+classId);

				List<Object[]> obj = studentQuery.list();
				if(obj!= null && obj.size() > 0){
					Iterator<Object[]> itr = obj.iterator();
					while (itr.hasNext()) {
						Object id = itr.next();
						if(id!=null)
						courseId = Integer.parseInt(id.toString());
					}
				}

				return courseId;
			} catch (Exception e) {
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		
	}

	public List<Object[]> getAttendenceRecord(int studentId, int courseId,
			int classId, int acadamicYear) throws Exception{
		Session session = null;
		List<Object[]> totalAttendenceList = new ArrayList<Object[]>();
		try{
			session = HibernateUtil.getSession();
			StringBuilder sql = new StringBuilder("SELECT");
			sql.append(" CLASS_MAIN.attendance_date,");
			sql.append(" CLASS_MAIN.attid,");
			sql.append(" CLASS_MAIN.subject_id,");
			sql.append(" CLASS_MAIN.is_cocurricular_leave,");
			sql.append(" CLASS_MAIN.is_present,");
			sql.append(" CLASS_MAIN.hoursheld,");
			sql.append(" CLASS_MAIN.student_id,");
			sql.append(" CLASS_MAIN.class_schemewise_id,");
			sql.append(" CLASS_MAIN.class_id1,");
			sql.append(" CLASS_MAIN.class_name1,");
			sql.append(" CLASS_MAIN.term_number,");
			sql.append(" CLASS_MAIN.academic_year,");
			sql.append(" CLASS_MAIN.course_id1,");
			sql.append(" CLASS_MAIN.course_name1,");
			sql.append(" CLASS_MAIN.student_id1,");
			sql.append(" CLASS_MAIN.register_no,");
			sql.append(" CLASS_MAIN.roll_no,");
			sql.append(" CLASS_MAIN.first_name,");
			sql.append(" CLASS_MAIN.MaxHrs,");
			sql.append(" sum(CLASS_MAIN.hours_held) as taken_hrs,");
			sql.append(" sum(if(CLASS_MAIN.is_present=1 || CLASS_MAIN.is_cocurricular_leave=1,CLASS_MAIN.hours_held,0)) as present_hrs,");
			sql.append(" sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)) as absent_hrs,");
			sql.append(" if(0=sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)),1,0) as HA,");
			sql.append(" if(1=sum(if(CLASS_MAIN.is_present=0 && IFNULL(CLASS_MAIN.is_cocurricular_leave,0)=0,CLASS_MAIN.hours_held,0)),0.5,0) as HA_halfday");
			sql.append(" FROM");
			sql.append(" ( ");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" 1 as hoursheld,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" 6 as MaxHrs");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" and student.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date");
			sql.append(" UNION");
			sql.append(" select attendance.attendance_date,");
			sql.append(" attendance.id as attid,");
			sql.append(" attendance.subject_id,");
			sql.append(" attendance.hours_held,");
			sql.append(" attendance_student.is_cocurricular_leave,");
			sql.append(" attendance_student.is_present,");
			sql.append(" 1 as hoursheld,");
			sql.append(" attendance_student.student_id,");
			sql.append(" attendance_class.class_schemewise_id,");
			sql.append(" classes.id as class_id1,");
			sql.append(" classes.name as class_name1,");
			sql.append(" classes.term_number,");
			sql.append(" curriculum_scheme_duration.academic_year,");
			sql.append(" course.id as course_id1,");
			sql.append(" course.name as course_name1,");
			sql.append(" student.id as student_id1,");
			sql.append(" student.register_no,");
			sql.append(" student.roll_no,");
			sql.append(" personal_data.first_name,");
			sql.append(" 6 as MaxHrs");
			sql.append(" from");
			sql.append(" attendance");
			sql.append(" inner join attendance_student on attendance_student.attendance_id = attendance.id");
			sql.append(" inner join attendance_class on attendance_class.attendance_id = attendance.id");
			sql.append(" inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id");
			sql.append(" inner join classes ON class_schemewise.class_id = classes.id");
			sql.append(" inner join EXAM_student_previous_class_details");
			sql.append(" on EXAM_student_previous_class_details.class_id = classes.id");
			sql.append(" and EXAM_student_previous_class_details.student_id = attendance_student.student_id");
			sql.append(" inner join student on attendance_student.student_id=student.id");
			sql.append(" inner join adm_appln on student.adm_appln_id=adm_appln.id");
			sql.append(" inner join personal_data on adm_appln.personal_data_id= personal_data.id");
			sql.append(" inner join course on classes.course_id = course.id");
			sql.append(" inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id");
			sql.append(" where attendance.is_canceled=0");
			sql.append(" and student.is_admitted=1");
			sql.append(" and adm_appln.is_cancelled=0");
			sql.append(" and curriculum_scheme_duration.academic_year= :academicYear");
			sql.append(" and course.id= :courseId");
			sql.append(" and classes.id= :classId");
			sql.append(" and attendance_student.student_id = :studentId");
			sql.append(" group by attendance.id,attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date");
			sql.append(" ) as CLASS_MAIN");
			sql.append(" group by CLASS_MAIN.student_id,CLASS_MAIN.class_schemewise_id,CLASS_MAIN.attendance_date");
			Query query = session.createSQLQuery(sql.toString())
							.setInteger("courseId", courseId)
							.setInteger("classId", classId)
							.setInteger("studentId", studentId)
							.setString("academicYear", String.valueOf(acadamicYear));
			 totalAttendenceList = query.list();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return totalAttendenceList;
	}
	
	
	
	
	
	
	
	
}
