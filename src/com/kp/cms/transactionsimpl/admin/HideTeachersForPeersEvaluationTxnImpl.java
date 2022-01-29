package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HideTeachersForPeersEvaluation;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm;
import com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HideTeachersForPeersEvaluationTxnImpl implements IHideTeachersForPeersEvaluationTransaction{
	public static volatile HideTeachersForPeersEvaluationTxnImpl txnImpl = null;
	public static HideTeachersForPeersEvaluationTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl = new HideTeachersForPeersEvaluationTxnImpl();
			return txnImpl;
		}
		return txnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction#hideTeachers(com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm)
	 */
	@Override
	public boolean hideTeachers( HideTeachersForPeersEvaluation hideTeachersBo) throws Exception{
		Session session = null;
		Transaction tx = null;
		boolean isHidden = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(hideTeachersBo);
			tx.commit();
			isHidden = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isHidden;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction#duplicateCheck(int, int)
	 */
	@Override
	public boolean duplicateCheck(String deleteQuery) throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery(deleteQuery);
			HideTeachersForPeersEvaluation teachersForPeersEvaluation = (HideTeachersForPeersEvaluation) query.uniqueResult();
			if(teachersForPeersEvaluation != null && !teachersForPeersEvaluation.toString().isEmpty()){
				isDuplicate = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDuplicate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction#getHiddenTeachers(com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm)
	 */
	@Override
	public List<HideTeachersForPeersEvaluation> getHiddenTeachers( HideTeachersForPeersEvaluationForm hideTeacherform)
			throws Exception {
		Session session = null;
		List<HideTeachersForPeersEvaluation> hiddenTeachers;
		try{
			int deptId= Integer.parseInt(hideTeacherform.getDepartmentId().trim());
			session = HibernateUtil.getSession();
			String str = "from HideTeachersForPeersEvaluation hideTeacher where hideTeacher.isActive = 1 " +
							"and hideTeacher.department.id ="+deptId;
			Query query = session.createQuery(str);
			hiddenTeachers = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return hiddenTeachers;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction#deleteHiddenTeachers(com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm)
	 */
	@Override
	public boolean deleteHiddenTeachers(String deleteQuery, HideTeachersForPeersEvaluationForm hideTeacherform) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			Query query = session.createQuery(deleteQuery);
			HideTeachersForPeersEvaluation teachersForPeersEvaluation = (HideTeachersForPeersEvaluation) query.uniqueResult();
			if(teachersForPeersEvaluation != null && !teachersForPeersEvaluation.toString().isEmpty()){
				teachersForPeersEvaluation.setIsActive(false);
				teachersForPeersEvaluation.setLastModifiedBy(new Date());
				teachersForPeersEvaluation.setModifiedBy(hideTeacherform.getUserId());
				session.update(teachersForPeersEvaluation);
			}
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDeleted;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction#getDepartmentName(int)
	 */
	@Override
	public String getDepartmentName(int departmentId) throws Exception {
		Session session = null;
		String deptName = "";
		try{
			session = HibernateUtil.getSession();
			String str = "select dept.name from Department dept where dept.isActive = 1 and dept.id ="+departmentId;
			Query query = session.createQuery(str);
			 deptName = (String) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return deptName;
	}
}
