package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.DisableStudentLoginForm;
import com.kp.cms.transactions.admission.IDisableStudentLoginTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DisableStudentLoginTxnImpl implements IDisableStudentLoginTransaction {
	public static volatile DisableStudentLoginTxnImpl disableStudentLoginTxnImpl = null;
	private static final Log log = LogFactory.getLog(DisableStudentLoginTxnImpl.class);
	public static DisableStudentLoginTxnImpl getInstance(){
		if(disableStudentLoginTxnImpl == null){
			disableStudentLoginTxnImpl = new DisableStudentLoginTxnImpl();
			return disableStudentLoginTxnImpl;
		}
		return disableStudentLoginTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IDisableStudentLoginTransaction#getStudentDetails(java.lang.StringBuffer)
	 */
	@Override
	public List<String> getStudentDetails(StringBuffer query) throws Exception {
		
		Session session = null;
		List<String> list = new ArrayList<String>();
		try{
			session = HibernateUtil.getSession();
			String str = query.toString();
			Query qry = session.createSQLQuery(str);
			List<Object[]> objects = qry.list();
			if(objects!= null && !objects.toString().isEmpty()){
				Iterator<Object[]> iterator = objects.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					if(obj[1].toString().equalsIgnoreCase("0")){
						list.add(obj[0].toString());
					}
				}
			}
		}catch (Exception e) {
			log.error("Error during getting getStudentDetails..." + e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IDisableStudentLoginTransaction#checkForDisableStudentLogin(java.util.List)
	 */
	@Override
	public boolean checkForDisableStudentLogin(List<String> studentIds,DisableStudentLoginForm disableStudentLoginForm) throws Exception {
		Session session = null;
		boolean isSubmit = false;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(studentIds!= null && !studentIds.isEmpty()){
				Iterator<String> iterator = studentIds.iterator();
				while (iterator.hasNext()) {
					String str= (String) iterator.next();
					String qry = "from StudentLogin login where login.student.id=" +Integer.parseInt(str)+" and login.isActive = 1 and login.isStudent=1";
					Query query = session.createQuery(qry);
					StudentLogin stuLogin = (StudentLogin) query.uniqueResult();
					if(stuLogin!=null && !stuLogin.toString().isEmpty()){
						stuLogin.setIsActive(false);
						stuLogin.setRemarks(disableStudentLoginForm.getRemarks());
						stuLogin.setModifiedBy(disableStudentLoginForm.getUserId());
						stuLogin.setLastModifiedDate(new Date());
						session.update(stuLogin);
					}
					isSubmit = true;
				}
				tx.commit();
				session.flush();
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during saving admitted Through data..." ,e);
			throw new ApplicationException(e);
		}
		return isSubmit;
	}
}
