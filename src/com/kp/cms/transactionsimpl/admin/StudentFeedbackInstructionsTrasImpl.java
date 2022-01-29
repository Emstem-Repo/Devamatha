package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.StudentFeedbackInstructions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.StudentFeedbackInstructionsForm;
import com.kp.cms.transactions.admin.IStudentFeedbackInstructionsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentFeedbackInstructionsTrasImpl implements IStudentFeedbackInstructionsTransaction{
	public static volatile StudentFeedbackInstructionsTrasImpl feedbackInstructionsTrasImpl = null;
	public static StudentFeedbackInstructionsTrasImpl getInstance(){
		if(feedbackInstructionsTrasImpl == null){
			feedbackInstructionsTrasImpl = new StudentFeedbackInstructionsTrasImpl();
			return feedbackInstructionsTrasImpl;
		}
		return feedbackInstructionsTrasImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IStudentFeedbackInstructionsTransaction#getInstructions(com.kp.cms.forms.admin.StudentFeedbackInstructionsForm)
	 */
	@Override
	public List<StudentFeedbackInstructions> getInstructions()
			throws Exception {
		Session session = null;
		List<StudentFeedbackInstructions> feedbackInstructions;
		try{
			session = HibernateUtil.getSession();
			feedbackInstructions = session.createQuery("from StudentFeedbackInstructions instructions").list();
		}catch (Exception e) {
			throw new ApplicationException(e);
			}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return feedbackInstructions;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IStudentFeedbackInstructionsTransaction#checkDuplicateInstructions(com.kp.cms.forms.admin.StudentFeedbackInstructionsForm)
	 */
	@Override
	public boolean checkDuplicateInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)
			throws Exception {
		Session session = null;
		boolean isExist = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select instructions.description from StudentFeedbackInstructions instructions" );
			List<StudentFeedbackInstructions> list = query.list();
			if(list!=null && !list.isEmpty() && list.contains(stuFeedbackInstructionsForm.getDescription())){
				isExist = true;
			}
		}
		catch (Exception e) {
			isExist = false;
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isExist;
	}
	@Override
	public boolean addStuFeedbackInstructions( StudentFeedbackInstructions feedbackInstructions,String mode) throws Exception {
		Session session = null;
		boolean isAdded = false;
		Transaction txTransaction = null;
		try{
			session = HibernateUtil.getSession();
			txTransaction = session.beginTransaction();
			txTransaction.begin();
			if(mode.equalsIgnoreCase("add")){
				session.save(feedbackInstructions);
			}else if(mode.equalsIgnoreCase("edit")){
				session.update(feedbackInstructions);
			}
			txTransaction.commit();
			isAdded = true;
		}catch (Exception e) {
			isAdded = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IStudentFeedbackInstructionsTransaction#editFeedbackInstructions(com.kp.cms.forms.admin.StudentFeedbackInstructionsForm)
	 */
	@Override
	public StudentFeedbackInstructions editFeedbackInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)
			throws Exception {
		Session session =null;
		StudentFeedbackInstructions instructions;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from StudentFeedbackInstructions inst where inst.id="+stuFeedbackInstructionsForm.getId());
			instructions = (StudentFeedbackInstructions) query.uniqueResult();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return instructions;
	}
	@Override
	public boolean deleteInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			StudentFeedbackInstructions instructions = (StudentFeedbackInstructions)session.get(StudentFeedbackInstructions.class, stuFeedbackInstructionsForm.getId());
			session.delete(instructions);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			isDeleted = false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDeleted;
	}
}
