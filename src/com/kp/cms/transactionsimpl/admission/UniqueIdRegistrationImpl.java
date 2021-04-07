package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.UniqueIdRegistrationForm;
import com.kp.cms.transactions.admission.IUniqueIdRegistration;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

@SuppressWarnings("unchecked")
public class UniqueIdRegistrationImpl implements IUniqueIdRegistration {
	/* Singleton Design Pattern */
	private static volatile UniqueIdRegistrationImpl txnImpl = null;

	public static UniqueIdRegistrationImpl getInstance() {
		if (txnImpl == null) {
			txnImpl = new UniqueIdRegistrationImpl();
		}
		return txnImpl;
	}

	private UniqueIdRegistrationImpl() {

	}

	@Override
	public boolean registerOnlineApplication( StudentOnlineApplication onlineApplication) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isSuccess = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(onlineApplication);
			tx.commit();
			session.flush();
			session.close();
			isSuccess = true;
		}catch (Exception e) {
			System.out.println("************************ error details in online admission registerOnlineApplication*************************"+e.getCause().toString());
			
			if(tx!=null && tx.isActive()){
				tx.rollback();
				session.close();
			}
			throw e;
		}
		return isSuccess;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentOnlineApplicationTransaction#getLoginOnlineApplicationDetails(com.kp.cms.forms.usermanagement.UniqueIdRegistrationForm)
	 */
	public StudentOnlineApplication getLoginOnlineApplicationDetails( UniqueIdRegistrationForm objForm,String mode) throws Exception {
		Session session = null;
		StudentOnlineApplication onlineApplication = null;
		
		try{
			/*session = HibernateUtil.getSession();
			String hqlQuery ="";
			if(mode.equalsIgnoreCase("login")){
				 hqlQuery = "from StudentOnlineApplication onlineApp where onlineApp.uniqueId='"+objForm.getUniqueId()+"'"+
				 " and onlineApp.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(objForm.getLoginDateOfBirth())+"'"+
				" and onlineApp.isActive=1";
			}else if(mode.equalsIgnoreCase("register")){
				 hqlQuery = "from StudentOnlineApplication onlineApp where "+
				 	" onlineApp.emailId='"+objForm.getEmailId()+"'"+
				 	" and onlineApp.mobileNo='"+objForm.getMobileNo()+"'"+
					" and onlineApp.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(objForm.getRegisterDateOfBirth())+"'"+
					" and onlineApp.year="+Integer.parseInt(objForm.getYear())+
					" and onlineApp.isActive=1";
			}else if(mode.equalsIgnoreCase("forgotUniqueId")){
				 hqlQuery = "from StudentOnlineApplication onlineApp where "+
				 	" onlineApp.emailId='"+objForm.getEmailId()+"'"+
					" and onlineApp.dateOfBirth='"+CommonUtil.ConvertStringToSQLDate(objForm.getRegisterDateOfBirth())+"'"+
					" and onlineApp.year="+Integer.parseInt(objForm.getYear())+
					" and onlineApp.isActive=1";
			}
			Query query = session.createQuery(hqlQuery);
			onlineApplication = (StudentOnlineApplication) query.uniqueResult();*/
 		
			session = HibernateUtil.getSession();
			Query query =null;
			if(mode.equalsIgnoreCase("login")){
				query=session.createQuery("from StudentOnlineApplication onlineApp where onlineApp.uniqueId=:UniqueId"+
				 " and onlineApp.dateOfBirth=:DOb"+
				 " and onlineApp.year=:Year"+
				" and onlineApp.isActive=1");
				query.setString("UniqueId", objForm.getUniqueId());
				query.setDate("DOb", CommonUtil.ConvertStringToSQLDate(objForm.getLoginDateOfBirth()));
				query.setInteger("Year", Integer.parseInt(objForm.getYear()));
			}else if(mode.equalsIgnoreCase("register")){
				query=session.createQuery("from StudentOnlineApplication onlineApp where "+
				 	" onlineApp.emailId=:Email and onlineApp.mobileNo=:MobileNo"+
					" and onlineApp.dateOfBirth=:DOB and onlineApp.year=:Year"+
					" and onlineApp.isActive=1 and onlineApp.isAided=:isAided");
				query.setString("Email", objForm.getEmailId());
				query.setString("MobileNo", objForm.getMobileNo());
				query.setDate("DOB", CommonUtil.ConvertStringToSQLDate(objForm.getRegisterDateOfBirth()));
				query.setInteger("Year", Integer.parseInt(objForm.getYear()));
				query.setBoolean("isAided",objForm.isAided());
			}else if(mode.equalsIgnoreCase("forgotUniqueId")){
				query=session.createQuery("from StudentOnlineApplication onlineApp where "+
				 	" onlineApp.emailId=:Email and onlineApp.dateOfBirth=:DOB"+
					" and onlineApp.year=:Year and onlineApp.isActive=1");
				query.setString("Email", objForm.getEmailId());
				query.setDate("DOB", CommonUtil.ConvertStringToSQLDate(objForm.getRegisterDateOfBirth()));
				query.setInteger("Year", Integer.parseInt(objForm.getYear()));
			}
			onlineApplication = (StudentOnlineApplication) query.uniqueResult();
 		
			
			
			
		}catch (Exception e) {
			System.out.println("************************ error details in online admission getLoginOnlineApplicationDetails*************************"+e.getCause().toString());
			
			throw e;
		}
		return onlineApplication;
	}


	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentOnlineApplicationTransaction#getAppliedCoursesList(com.kp.cms.bo.admin.StudentOnlineApplication)
	 */
	@Override
	public List<AdmAppln> getAppliedCoursesList( StudentOnlineApplication onlineApplication) throws Exception {
		Session session = null;
		List<AdmAppln> admApplns = new ArrayList<AdmAppln>();
		try{
			session  = HibernateUtil.getSession();
			String hqlQuery = "from AdmAppln admAppln where admAppln.studentOnlineApplication.isActive=1" +
								" and admAppln.studentOnlineApplication.id="+onlineApplication.getId();
			Query query = session.createQuery(hqlQuery);
			admApplns = query.list();
		}catch (Exception e) {
			throw e;
		}
		return admApplns;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentOnlineApplicationTransaction#IsExistedMail(java.lang.String)
	 */
	@Override
	public boolean IsExistedMail(UniqueIdRegistrationForm objForm) throws Exception {
		Session session =null;
		boolean isExistedMail = false;
		try{
			String academicYear = txnImpl.getAdmissionAcademicYear();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentOnlineApplication onlineAppln where onlineAppln.isActive=1 and onlineAppln.emailId=:emailId  and onlineAppln.year=:Year and onlineAppln.isAided = :isAided");
			query.setString("emailId", objForm.getEmailId());
			query.setInteger("Year", Integer.parseInt(academicYear));
			query.setBoolean("isAided", objForm.isAided());
			List<StudentOnlineApplication> application =  query.list();
			if(application!=null && !application.isEmpty()){
				isExistedMail = true;
			}
		}catch (Exception e) {
			System.out.println("************************ error details in online admission IsExistedMail*************************"+e.getCause().toString());
			
			throw e;
			
		}
		return isExistedMail;
	}

	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.usermanagement.IStudentOnlineApplicationTransaction#getApplicationNoDetails(com.kp.cms.forms.usermanagement.UniqueIdRegistrationForm, java.lang.String)
	 */
	@Override
	public AdmAppln getApplicationNoDetails( UniqueIdRegistrationForm objForm, String mode) throws Exception {
		Session session = null;
		AdmAppln admAppln = null;
		try {
			session = HibernateUtil.getSession();
			// If an applicant login through application number then following
			// if condition will execute.
			if(StringUtils.isNumeric(objForm.getUniqueId())){
				Query query=null;
				if (mode.equalsIgnoreCase("login")) {
					 query= session.createQuery(" from AdmAppln admAppln where"
							+ " admAppln.applnNo=:UniqueId" 
							+ // here uniqueId is application number.
							" and admAppln.personalData.dateOfBirth=:DOB ");
					query.setInteger("UniqueId", Integer.parseInt(objForm.getUniqueId()));
					query.setDate("DOB", CommonUtil.ConvertStringToSQLDate(objForm .getLoginDateOfBirth()));
					admAppln = (AdmAppln) query.uniqueResult();
				}
			}
		} catch (Exception e) {
			System.out.println("************************ error details in online admission getApplicationNoDetails*************************"+e.getCause().toString());
			
			throw e;
		}
		return admAppln;
	}

	@Override
	public String getAdmissionAcademicYear() throws Exception {
		Session session = null;
		String  academicYear = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select academicYear.year from AcademicYear academicYear where academicYear.isActive=1 and academicYear.isCurrentForAdmission=1";
			Query query = session.createQuery(hqlQuery);
			Integer year = (Integer) query.uniqueResult();
			academicYear = year.toString();
			
		}catch (Exception e) {
			System.out.println("************************ error details in online admission getAdmissionAcademicYear*************************"+e.getCause().toString());
			

			throw e;
		}
		return academicYear;
	}

	@Override
	public List<CandidatePGIDetails> getOnlinePaymentDetails(int uniqueId) throws Exception {
		Session session =null;
		List<CandidatePGIDetails> list = new ArrayList<CandidatePGIDetails>();
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from CandidatePGIDetails candidatePGIDetails where  " +
					" candidatePGIDetails.admAppln is null and candidatePGIDetails.txnStatus='Success'" +
					" and candidatePGIDetails.uniqueId="+uniqueId;
			Query query = session.createQuery(hqlQuery);
			list = query.list();
		}catch (Exception e) {
			System.out.println("************************ error details in online admission getOnlinePaymentDetails*************************"+e.getCause().toString());
			
			throw e;
		}
		return list;
	}
	
	
	

	@Override
	public String getApplicationFees(String year,String programTypeID,String category)
			throws Exception {
		Session session = null;
		String  amount = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "select af.amount from ApplicationFee af where af.isActive=1 and af.programType.id=:pgtype and af.religionSection.id=:relsec and af.accademicYear=:year";
			Query query = session.createQuery(hqlQuery);
			query.setInteger("pgtype", Integer.parseInt(programTypeID));
			query.setInteger("relsec", Integer.parseInt(category));
			query.setString("year", year);
			
			amount = (String) query.uniqueResult();
			
			
		}catch (Exception e) {
			System.out.println("************************ error details in online admission getApplicationFees*************************"+e.getCause().toString());
			
			throw e;
		}
		return amount;
	}
	
	
	@Override
	public boolean checkDuplicateNumber(Integer year, String challanNo)throws Exception {
		
		boolean duplicate=false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = " from StudentOnlineApplication s where s.isActive=1 and s.challanNumber=:challanNo and s.year=:year";
			Query query = session.createQuery(hqlQuery);
			query.setString("challanNo", challanNo);
			query.setInteger("year", year);
			
			List challanNumber = query.list();
			if(challanNumber!=null && challanNumber.size()!=0){
				duplicate=true;
			}
			
		}catch (Exception e) {
			System.out.println("************************ error details in online admission checkDuplicateNumber*************************"+e.getCause().toString());
			throw e;
		}
		
		return duplicate;
	}
	
	@Override
	public boolean checkDuplicateUniqueId(Integer year, String uniqueID)throws Exception {
		
		boolean duplicate=false;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = " from StudentOnlineApplication s where s.isActive=1 and s.uniqueId=:uniqueId and s.year=:year";
			Query query = session.createQuery(hqlQuery);
			query.setString("uniqueId", uniqueID);
			query.setInteger("year", year);
			
			List challanNumber = query.list();
			if(challanNumber!=null && challanNumber.size()!=0){
				duplicate=true;
			}
			
		}catch (Exception e) {
			System.out.println("************************ error details in online admission checkDuplicateUniqueId*************************"+e.getCause().toString());
			throw e;
		}
		
		return duplicate;
	}
	
	public boolean IsExistedPhoneNumber(UniqueIdRegistrationForm objForm) throws Exception {
		Session session =null;
		boolean isExistedMobileNumber = false;
		try{
			String academicYear = txnImpl.getAdmissionAcademicYear();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentOnlineApplication onlineAppln where onlineAppln.isActive=1 and onlineAppln.mobileNo=:mobileNo and onlineAppln.year=:Year and onlineAppln.isAided = :isAided")
								 .setString("mobileNo", objForm.getMobileNo())
								 .setInteger("Year", Integer.parseInt(academicYear))
								 .setBoolean("isAided", objForm.isAided());
			List<StudentOnlineApplication> application =  query.list();
			if(application!=null && !application.isEmpty()){
				isExistedMobileNumber = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return isExistedMobileNumber;
	}
	
}
