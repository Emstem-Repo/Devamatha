package com.kp.cms.transactionsimpl.studentLogin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.studentLogin.StudentCarPass;
import com.kp.cms.forms.studentLogin.StudentCarPassForm;
import com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentCarPassTransactionImpl implements IStudentCarPassTransaction{
	private static final Log log = LogFactory.getLog(StudentCarPassTransactionImpl.class);	
	

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction#checkAndSaveStudentCarDetails(com.kp.cms.bo.studentLogin.StudentCarPass)
	 */
	@Override
	public boolean SaveStudentCarDetails(StudentCarPass studentCarPass)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean checkStudentCar =false;
		try{
			session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
			    session.save(studentCarPass);
				transaction.commit();
				checkStudentCar=true;
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return checkStudentCar;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction#getStudentCarDetailsByStudentId(int)
	 */
	public StudentCarPass getStudentCarDetailsByStudentId(int studentId){
		
		Session session=null;
		Transaction transaction=null;
		StudentCarPass studentCarPass=null;
		try{
			session=HibernateUtil.getSession();
			String str="from StudentCarPass carPass where carPass.studentId.id="+studentId+" and carPass.isActive=1 ";
			studentCarPass= (StudentCarPass) session.createQuery(str).uniqueResult();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return studentCarPass;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction#checkStudentCarPass(com.kp.cms.forms.studentLogin.StudentCarPassForm)
	 */
	@Override
	public StudentCarPass checkStudentCarPass(StudentCarPassForm studentCarPassForm)
			throws Exception {
		
            Session session=null;
            Transaction transaction=null;
            StudentCarPass studentCarPass=null;
				try{
					session=HibernateUtil.getSession();
					String str="from StudentCarPass carPass where carPass.studentId.id='"+studentCarPassForm.getStudentId()+"'" +
					" and carPass.vehicleNumber='"+studentCarPassForm.getVehicleNo()+"' and carPass.isActive=1 " +
					"and carPass.emergencyContactNo='"+studentCarPassForm.getEmergencyContactNo()+"'";
					studentCarPass= (StudentCarPass) session.createQuery(str).uniqueResult();
				}catch(Exception exception){
					if (transaction != null)
						transaction.rollback();
					log.debug("Error during saving data...", exception);
				}
				finally{
					session.flush();
				}
				return studentCarPass;
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.studentLogin.IStudentCarPassTransaction#getRegisterCarPasses()
	 */
	@Override
	public Long getRegisterCarPasses() throws Exception {
		 Session session=null;
         Transaction transaction=null;
         Long regCarPasses = null;
				try{
					session=HibernateUtil.getSession();
					String str="select count(*) from StudentCarPass carPass where carPass.isActive=1";
					regCarPasses= (Long) session.createQuery(str).uniqueResult();
				}catch(Exception exception){
					if (transaction != null)
						transaction.rollback();
					log.debug("Error during saving data...", exception);
				}
				finally{
					session.flush();
				}
				return regCarPasses;
	}
	
}
