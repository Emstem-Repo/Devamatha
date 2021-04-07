package com.kp.cms.transactionsimpl.teacherfeedback;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.teacherfeedback.TeacherFeedbackInstructions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.teacherfeedback.TeacherFeedbackInstructionsForm;
import com.kp.cms.transactions.teacherfeedback.ITeacherFeedbackInstructionsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class TeacherFeedbackInstructionsTrasImpl implements ITeacherFeedbackInstructionsTransaction {
	public static volatile TeacherFeedbackInstructionsTrasImpl feedbackInstructionsTrasImpl = null;
	public static TeacherFeedbackInstructionsTrasImpl getInstance(){
		if(feedbackInstructionsTrasImpl == null){
			feedbackInstructionsTrasImpl = new TeacherFeedbackInstructionsTrasImpl();
			return feedbackInstructionsTrasImpl;
		}
		return feedbackInstructionsTrasImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.teacherfeedback.ITeacherFeedbackInstructionsTransaction#getInstructions(com.kp.cms.forms.teacherfeedback.TeacherFeedbackInstructionsForm)
	 */
	
	@Override
	public List<TeacherFeedbackInstructions> getInstructions()
			throws Exception {
		Session session = null;
		List<TeacherFeedbackInstructions> feedbackInstructions;
		try{
			session = HibernateUtil.getSession();
			feedbackInstructions = session.createQuery("from TeacherFeedbackInstructions instructions").list();
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
	 * @see com.kp.cms.transactions.teacherfeedback.ITeacherFeedbackInstructionsTransaction#checkDuplicateInstructions(com.kp.cms.forms.admin.TeacherFeedbackInstructionsForm)
	 */
	@Override
	public boolean checkDuplicateInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)
			throws Exception {
		Session session = null;
		boolean isExist = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select instructions.description from TeacherFeedbackInstructions instructions" );
			List<TeacherFeedbackInstructions> list = query.list();
			if(list!=null && !list.isEmpty() && list.contains(teacherFeedbackInstructionsForm.getDescription())){
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
	public boolean addTeacherFeedbackInstructions( TeacherFeedbackInstructions feedbackInstructions,String mode) throws Exception {
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
	 * @see com.kp.cms.transactions.teacherfeedback.ITeacherFeedbackInstructionsTransaction#editFeedbackInstructions(com.kp.cms.forms.admin.TeacherFeedbackInstructionsForm)
	 */
	@Override
	public TeacherFeedbackInstructions editFeedbackInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)
			throws Exception {
		Session session =null;
		TeacherFeedbackInstructions instructions;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherFeedbackInstructions inst where inst.id="+teacherFeedbackInstructionsForm.getId());
			instructions = (TeacherFeedbackInstructions) query.uniqueResult();
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
	public boolean deleteInstructions( TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			TeacherFeedbackInstructions instructions = (TeacherFeedbackInstructions)session.get(TeacherFeedbackInstructions.class, teacherFeedbackInstructionsForm.getId());
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
