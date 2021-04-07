package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelLeaveForm;
import com.kp.cms.transactions.hostel.IHostelLeaveTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HostelLeaveTransactionImpl implements IHostelLeaveTransaction {
	private static final Log log = LogFactory
			.getLog(HostelLeaveTransactionImpl.class);

	public static volatile HostelLeaveTransactionImpl self = null;

	/**
	 * @return unique instance of LeaveReportTransactionImpl class. This method
	 *         gives instance of this method
	 */
	public static HostelLeaveTransactionImpl getInstance() {
		if (self == null)
			self = new HostelLeaveTransactionImpl();
		return self;
	}

	/*
	 * used to get leave type list from database (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getLeaveTypeList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HlLeave> getLeaveTypeList() throws Exception {
		log
				.info("entered getLeaveTypeList in HostelLeaveTransactionImpl class.");
		Session session = null;
		List<HlLeave> leaveTypeList = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			leaveTypeList = session.createQuery(
					"from HlLeaveType hl where hl.isActive = 1").list();

		} catch (Exception e) {
			log.error("error while getting the getLeaveTypeList results ", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("exit getLeaveTypeList in HostelLeaveTransactionImpl class.");
		return leaveTypeList;
	}

	/*
	 * used to save leave details to database (non-Javadoc)
	 * 
	 * @see
	 * com.kp.cms.transactions.hostel.IHostelLeaveTransaction#saveHostelLeaveDetails
	 * (com.kp.cms.bo.admin.HlLeave)
	 */
	@Override
	public boolean saveOrUpdateHostelLeaveDetails(HlLeave leaveType,String mode) throws Exception {
		log
				.info("call of saveHostelLeaveDetails in HostelLeaveTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("save"))
			{
				if(leaveType!=null){
					session.save(leaveType);
					isAdded=true;
				}else{
					isAdded = false;
				}
			}
			else
			{
				if(leaveType!=null){
					session.merge(leaveType);
					isAdded=true;
				}else{
					isAdded = false;
				}
			}
			if(isAdded)
				transaction.commit();
			
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to saveHostelLeaveDetails", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log
				.info("end of saveHostelLeaveDetails in HostelLeaveTransactionImpl class.");
		return isAdded;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#duplicateHostelLeaveCheck(java.lang.String, com.kp.cms.forms.hostel.HostelLeaveForm)
	 */
	public boolean duplicateHostelLeaveCheck(String strQuery,HostelLeaveForm hostelLeaveForm)
			throws Exception {
		Session session = null;
		boolean duplicate=false;
			try{
				session = HibernateUtil.getSession();
				List<HlLeave> list = session.createQuery(strQuery).list();
			
			if(list!=null && !list.isEmpty())
			{
				Iterator<HlLeave> iterator=list.iterator();
				while (iterator.hasNext()) {
					HlLeave leave = (HlLeave) iterator.next();
					if(hostelLeaveForm.getId()!=null && !hostelLeaveForm.getId().isEmpty())
					{
					if(leave.getId()==Integer.parseInt(hostelLeaveForm.getId())){
						duplicate=false;
						break;
					}
					else{
						duplicate=true;
					}
					}else{
						// /* condition added by chandra
							if(leave.getLeaveTo().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom()))==0){
							   if(leave.getToSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
								duplicate=true;
								break;
							 }else if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
								 if((leave.getFromSession().equalsIgnoreCase("Morning")) && (hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Morning"))){
									   duplicate=true;
										break;
								   }else if((leave.getFromSession().equalsIgnoreCase("Evening")) && (hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Evening"))){
									   		duplicate=false;
									   }
								   
							 }/*else if(leave.getToSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
								  duplicate=true;
									break;
							 }else if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
								  duplicate=true;
									break;
							 }*/else if(hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
								 if(leave.getLeaveTo().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom()))!=0){
								 // Duplicate checking for FromDate Morning
									 if((leave.getToSession().equalsIgnoreCase("Evening"))&& (hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Morning"))){
										 duplicate=true;
											break;
									 }
								 }else{
									 duplicate=false;
								 }
									 
							 }else{
								duplicate=false;
							 }
							}else if(leave.getLeaveFrom().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom()))==0){
								if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
									duplicate=true;
									break;
								 }else if(leave.getToSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
									  duplicate=true;
										break;
								 }else if(leave.getToSession().equalsIgnoreCase(hostelLeaveForm.getLeaveFromSession())){
									  duplicate=true;
										break;
								 }else if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
									 duplicate=true;
										break;
								 }else if(hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
									 
									 // Duplicate checking for FromDate Evening
									 if((leave.getFromSession().equalsIgnoreCase("Morning"))&& (hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Evening"))){
										 duplicate=true;
											break;
									 }
									 // Duplicate checking for middle of FromDate and ToDate
								 if(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).after((leave.getLeaveFrom())) 
											&& CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).before((leave.getLeaveTo()))) {
										duplicate=true;
										break;
									}
								 
								// Dulicate checking for if ToDate is more than already stored ToDate
								 if(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).after((leave.getLeaveTo()))){
									 duplicate=true;
										break;
								 }
								 
								 	// Duplicate checking for ToDate Morning
								 if(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom()).compareTo(leave.getLeaveTo())!=0){
									 if((leave.getToSession().equalsIgnoreCase("Evening"))&& (hostelLeaveForm.getLeaveFromSession().equalsIgnoreCase("Morning"))){
										 duplicate=true;
											break;
									 }
									 
								 }
								  
							 
								 }else{
									duplicate=false;
								 }
							}else if(leave.getLeaveTo().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveTo()))==0){
								duplicate=true;
								break;
							}
							
							else if(leave.getLeaveFrom().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveFrom()))>0){
								
								if(CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).after((leave.getLeaveFrom())) 
										&& CommonUtil.ConvertStringToDate(hostelLeaveForm.getLeaveTo()).before((leave.getLeaveTo()))) {
									duplicate=true;
									break;
								}else if(leave.getLeaveFrom().compareTo(CommonUtil.ConvertStringToSQLDate(hostelLeaveForm.getLeaveTo()))==0){
									if(leave.getFromSession().equalsIgnoreCase(hostelLeaveForm.getLeaveToSession())){
										 duplicate=true;
											break;
									 }else if(hostelLeaveForm.getLeaveToSession().equalsIgnoreCase("Evening")){
										 duplicate=true;
											break;
									 }else{
										 duplicate=false;
									 }
								}else{
									duplicate=false;
								}
							}
							// */ condition added by chandra
							else{
								duplicate=true;
								break;
							}
						
					}
				}
				
			}
			return duplicate;
		} catch (Exception e) {
			log.error("give nonapplied  register No", e);
			throw new ApplicationException(e);

		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#verifyRegisterNo(com.kp.cms.forms.hostel.HostelLeaveForm)
	 */
	@Override
	public boolean verifyRegisterNo(HostelLeaveForm hostelLeaveForm) throws Exception {
		log.debug("inside verifyRegisterNo");
		Session session = null;
		boolean verifyRegNo=false;
        HlAdmissionBo admissionBo=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where hl.studentId.registerNo=:registrationNo and hl.academicYear=:academicYear " +
					"and hl.isActive=1 and hl.isCancelled=0 and hl.isCheckedIn=1 and (hl.checkOut=0 or hl.checkOut is null)";
			Query query = session.createQuery(strQuery);
			query.setString("registrationNo",  hostelLeaveForm.getRegisterNo());
			query.setString("academicYear",  hostelLeaveForm.getAcademicYear1());
			admissionBo = (HlAdmissionBo) query.uniqueResult();
			if(admissionBo!=null)
			{
				verifyRegNo=true;	
//				setting Student mobile number,email and parent mobile number to the formbean to send sms and email.
//				code added by sudhir
				if(admissionBo.getStudentId()!=null && admissionBo.getStudentId().getAdmAppln() != null && admissionBo.getStudentId().getAdmAppln().getPersonalData() != null){
				String mobileNo="";
				String studentMobileNo="";
				if(admissionBo.getStudentId() != null){
					hostelLeaveForm.setRegNo(admissionBo.getStudentId().getRegisterNo());
				}
				if(admissionBo.getStudentId() != null && admissionBo.getStudentId().getAdmAppln() != null && admissionBo.getStudentId().getAdmAppln().getPersonalData() != null){
					hostelLeaveForm.setStudentName(admissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				}
				if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1()!=null 
						&& !admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().isEmpty()){
					if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("0091") || 
							admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("+91")
							|| admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("091") || 
							admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1();
				}else{
					mobileNo="91";
				}
				if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null && !admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
					mobileNo=mobileNo+admissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2();
				}
				hostelLeaveForm.setParentMob(mobileNo);
				if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null 
						&& !admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
					if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
							admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
							|| admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
							admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
						studentMobileNo = "91";
					else
						studentMobileNo=admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
				}else{
					studentMobileNo="91";
				}
				if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
					studentMobileNo=studentMobileNo+admissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
				}
				hostelLeaveForm.setStudentMobile(studentMobileNo);
				if(admissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail()!=null && !admissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail().isEmpty()){
					hostelLeaveForm.setStudentmail(admissionBo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail());
				}
				}
//				added code ends here
			}
			session.flush();
			//session.close();
			//sessionFactory.close();
			return verifyRegNo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getStudentLeaveDetails(com.kp.cms.forms.hostel.HostelLeaveForm)
	 */
	@Override
	public List<HlLeave> getStudentLeaveDetails(HostelLeaveForm leaveForm) throws Exception {
		log.debug("inside getStudentLeaveDetails");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="from HlLeave h1 where h1.hlAdmissionBo.studentId.registerNo=:registerNo and h1.isActive=1 and h1.academicYear=:academicYear and h1.isCanceled=0";
			Query query = session.createQuery(strQuery);
			query.setString("registerNo",  leaveForm.getRegisterNo());
			query.setString("academicYear",  leaveForm.getAcademicYear1());
			List<HlLeave> hlLeaveList =  query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return hlLeaveList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#editStudentLeaveDetails(int)
	 */
	@Override
	public HlLeave editStudentLeaveDetails(int id) throws Exception {
		
		log.debug("inside editStudentLeaveDetails");
		Session session = null;
        HlLeave hlLeave=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="select h1 from HlLeave h1 where h1.id=:id and h1.isActive= 1";
			Query query = session.createQuery(strQuery);
			query.setInteger("id",  id);
			hlLeave =  (HlLeave) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return hlLeave;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getHostelLeaveById(java.lang.String)
	 */
	@Override
	public HlLeave getHostelLeaveById(String id) throws Exception {
		log.debug("inside editStudentLeaveDetails");
		Session session = null;
		//List<HlLeave> leaves=new ArrayList<HlLeave>();
		try {
			session = HibernateUtil.getSession();
			String strQuery="from HlLeave h1 where h1.id=:id and h1.isActive= '1'  ";
			Query query = session.createQuery(strQuery);
			query.setInteger("id",  Integer.parseInt(id));
			HlLeave leaves =  (HlLeave) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return leaves;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getStudentHostelDetails(java.lang.String)
	 */
	@Override
	public HlUnits getStudentHostelDetails(String studentId, HostelLeaveForm hostelLeaveForm)
			throws Exception {
		log.debug("inside getStudentHostelDetails");
		Session session = null;
		HlUnits bo = null;
		try{
			session = HibernateUtil.getSession();
			HlAdmissionBo hlAdmissionBo = (HlAdmissionBo)session.createQuery(" from HlAdmissionBo h where h.isCheckedIn=1 " +
					" and h.isActive=1 and (h.checkOut is null or h.checkOut=0 )  and h.isCancelled=0 " +
					" and h.studentId.id="+studentId).uniqueResult();
			if(hlAdmissionBo != null){
				hostelLeaveForm.setHlAdmId(String.valueOf(hlAdmissionBo.getId()));
				if(hlAdmissionBo.getStudentId() != null && hlAdmissionBo.getStudentId().getRegisterNo() != null){
					hostelLeaveForm.setRegisterNo(hlAdmissionBo.getStudentId().getRegisterNo());
				}
				if(hlAdmissionBo.getStudentId() != null && hlAdmissionBo.getStudentId().getAdmAppln() != null && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData() != null){
					hostelLeaveForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				}
				if(hlAdmissionBo.getStudentId() != null && hlAdmissionBo.getStudentId().getClassSchemewise() != null &&hlAdmissionBo.getStudentId().getClassSchemewise().getCurriculumSchemeDuration() != null && hlAdmissionBo.getStudentId().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear() != null){
					hostelLeaveForm.setAcademicYear(String.valueOf(hlAdmissionBo.getStudentId().getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()));
				}
				if(hlAdmissionBo.getStudentId() != null){
					hostelLeaveForm.setRegNo(hlAdmissionBo.getStudentId().getRegisterNo());
				}
				if(hlAdmissionBo.getStudentId() != null && hlAdmissionBo.getStudentId().getAdmAppln() != null && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData() != null){
					hostelLeaveForm.setStudentName(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
				}
				if(hlAdmissionBo.getStudentId() != null && hlAdmissionBo.getStudentId().getAdmAppln() != null && hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData() != null){
					String mobileNo="";
					String studentMobileNo="";
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1()!=null 
							&& !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().isEmpty()){
						if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("0091") || 
								hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("+91")
								|| hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("091") || 
								hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1().trim().equals("0"))
							mobileNo = "91";
						else
							mobileNo=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob1();
					}else{
						mobileNo="91";
					}
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
						mobileNo=mobileNo+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getParentMob2();
					}
					hostelLeaveForm.setParentMob(mobileNo);
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null 
							&& !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
						if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
								hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
								|| hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
								hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
							studentMobileNo = "91";
						else
							studentMobileNo=hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
					}else{
						studentMobileNo="91";
					}
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
						studentMobileNo=studentMobileNo+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
					}
					hostelLeaveForm.setStudentMobile(studentMobileNo);
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getEmail().isEmpty()){
						hostelLeaveForm.setStudentmail(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getUniversityEmail());
					}
					if(hlAdmissionBo.getStudentId().getRegisterNo()!=null && !hlAdmissionBo.getStudentId().getRegisterNo().isEmpty()){
						hostelLeaveForm.setRegisterNo(hlAdmissionBo.getStudentId().getRegisterNo());
					}
					String studentName="";
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
						studentName=studentName+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getFirstName();
					}
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
						studentName=studentName+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
					}
					if(hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName()!=null && !hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName().isEmpty()){
						studentName=studentName+hlAdmissionBo.getStudentId().getAdmAppln().getPersonalData().getLastName();
					}
					hostelLeaveForm.setStudentName(studentName);
					if(hlAdmissionBo.getStudentId().getRegisterNo()!=null && !hlAdmissionBo.getStudentId().getRegisterNo().isEmpty()){
						hostelLeaveForm.setRegisterNo(hlAdmissionBo.getStudentId().getRegisterNo());
					}
				}
				if(hlAdmissionBo.getRoomId() != null)
					bo = hlAdmissionBo.getRoomId().getHlUnit();
			}
			session.flush();
		}catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			throw new ApplicationException(e);
		}
		return bo;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#saveStudentLeave(com.kp.cms.bo.admin.HlLeave)
	 */
	@Override
	public boolean saveStudentLeave(HlLeave leave) throws Exception {
		log.debug("inside getStudentLeaveDetails");
		Session session = null;
		Transaction tx = null;
		boolean isSaved=false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(leave!=null){
				session.save(leave);
				tx.commit();
				isSaved=true;
			}else{
				isSaved=false;
			}
			
			return isSaved;
		}catch (Exception e) {
			if(tx != null)
				tx.rollback();
			if(session != null)
				session.close();
			return false;
		}finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getDataForQuery(java.lang.String)
	 */
	@Override
	public List<HlLeave> getDataForQuery(String query) throws Exception {
		List<HlLeave> list = new ArrayList<HlLeave>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			list = session.createQuery(query).list();
		}catch (Exception e) {
			if(session != null)
				session.close();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getTotalLeaves(java.lang.String)
	 */
	@Override
	public List<HlLeave> getTotalLeaves(String studentId) throws Exception {
		List<HlLeave> list = new ArrayList<HlLeave>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			list = session.createQuery("from HlLeave h where h.isActive=1 and h.isCanceled=0 and h.hlAdmissionBo.studentId.id="+studentId).list();
		}catch (Exception e) {
			if(session != null)
				session.close();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#cancelLeave(int)
	 */
	@Override
	public boolean cancelLeave(int hlLeaveId) throws Exception {
		log.debug("inside getStudentLeaveDetails");
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(hlLeaveId !=0){
				HlLeave leave = (HlLeave)session.createQuery("from HlLeave h where id="+hlLeaveId).uniqueResult();
				if(leave != null){
					leave.setIsActive(false);
					leave.setIsCanceled(true);
					session.update(leave);
				}
			}
			tx.commit();
			return true;
		}catch (Exception e) {
			if(tx != null)
				tx.rollback();
			if(session != null)
				session.close();
			return false;
		}finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#checkStudentDetails(int)
	 */
	@Override
	public boolean checkStudentDetails(int studentId) throws Exception {
		log.debug("inside verifyRegisterNo");
		Session session = null;
		boolean verifyRegNo=false;
        HlAdmissionBo admissionBo=null;
		try {
			session = HibernateUtil.getSession();
			String strQuery="from HlAdmissionBo hl where  hl.isActive=1 " +
					" and (hl.checkOut is null or hl.checkOut=0 )  and hl.isCancelled=0 " +
					" and hl.isCheckedIn=1 and hl.studentId.id="+studentId ;
			Query query = session.createQuery(strQuery);
			admissionBo = (HlAdmissionBo) query.uniqueResult();
			if(admissionBo!=null && admissionBo.getRoomId() != null && admissionBo.getRoomId().getHlUnit() != null 
					&& admissionBo.getRoomId().getHlUnit().getOnlineLeave() != null && admissionBo.getRoomId().getHlUnit().getOnlineLeave()){
				verifyRegNo=true;	
			}
			session.flush();
			//session.close();
			//sessionFactory.close();
			return verifyRegNo;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveTransaction#getPreviousList(java.lang.String)
	 */
	@Override
	public List<HlLeave> getPreviousList(String regNo) throws Exception {
		log.debug("inside getPreviousList");
		Session session = null;
		List<HlLeave> hlLeavesList=new ArrayList<HlLeave>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from HlLeave leave where leave.isActive=1 and "+
							  " leave.hlAdmissionBo.studentId.registerNo='"+regNo+"'"+
							  " and leave.hlAdmissionBo.isActive=1 and leave.isCanceled=0";
			Query query= session.createQuery(hqlQuery);
			hlLeavesList = query.list();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return hlLeavesList;
	}

	@Override
	public boolean checkIsHoliday(String holiday) throws Exception {
		Session session=null;
		boolean isHoliday=false;
		try{
			String query=" from Holidays holiday where  " +
					"'"+CommonUtil.ConvertStringToSQLDate(holiday)
					+"' between holiday.startDate and holiday.endDate";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			Holidays bo=(Holidays) querry.uniqueResult();
			if(bo!=null && !bo.toString().isEmpty()){
				isHoliday = true;
			}
			session.close();
		}catch (Exception e) {
			log.error("Error during  checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return isHoliday;
	}
}