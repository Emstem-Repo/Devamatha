package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PeersEvaluationFeedbackInstructions;
import com.kp.cms.bo.admin.StudentFeedbackInstructions;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm;
import com.kp.cms.transactions.admin.IPeersEvaFeedbackInstructionsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PeersEvaluationFeedbackInstructionsTxnImpl implements IPeersEvaFeedbackInstructionsTransaction{

	@Override
	public List<PeersEvaluationFeedbackInstructions> getInstructionsList()
			throws Exception {
		Session session = null;
		List<PeersEvaluationFeedbackInstructions> instructionsList = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationFeedbackInstructions instructions";
			Query query = session.createQuery(str);
			instructionsList = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally  {
			if (session != null){
				session.flush();
			}
		}
		return instructionsList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaFeedbackInstructionsTransaction#getCheckDuplicateInstructions(com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm)
	 */
	@Override
	public boolean getCheckDuplicateInstructions(PeersEvaluationFeedbackInstuctionsForm instructionsForm)
			throws Exception {
		Session session = null;
		boolean isExist = false;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select instructions.description from PeersEvaluationFeedbackInstructions instructions" );
			List<StudentFeedbackInstructions> list = query.list();
			if(list!=null && !list.isEmpty() && list.contains(instructionsForm.getDescription())){
				isExist = true;
			}
		}
		catch (Exception e) {
			isExist = false;
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isExist;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaFeedbackInstructionsTransaction#saveOrUpdateInstructions(com.kp.cms.bo.admin.PeersEvaluationFeedbackInstructions, java.lang.String)
	 */
	@Override
	public boolean saveOrUpdateInstructions( PeersEvaluationFeedbackInstructions instructionsBo,String mode)
			throws Exception {
		Session session = null;
		boolean isAdded = false;
		Transaction transaction = null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(instructionsBo);
			}else if(mode.equalsIgnoreCase("Edit")){
				session.update(instructionsBo);
			}
			transaction.commit();
			isAdded = true;
		}catch (Exception e) {
			isAdded = false;
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isAdded;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IPeersEvaFeedbackInstructionsTransaction#getEditFeedbackInstructions(com.kp.cms.forms.admin.PeersEvaluationFeedbackInstuctionsForm)
	 */
	@Override
	public PeersEvaluationFeedbackInstructions getEditFeedbackInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm)
			throws Exception {
		Session session = null;
		PeersEvaluationFeedbackInstructions instructions = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PeersEvaluationFeedbackInstructions inst where inst.id="+instructionsForm.getId());
			instructions = (PeersEvaluationFeedbackInstructions) query.uniqueResult();
		}catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return instructions;
	}

	@Override
	public boolean deletePeersInstructions( PeersEvaluationFeedbackInstuctionsForm instructionsForm)
			throws Exception {
		Session session = null;
		boolean isDelete = false;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			PeersEvaluationFeedbackInstructions instructions = (PeersEvaluationFeedbackInstructions)session.get(PeersEvaluationFeedbackInstructions.class, instructionsForm.getId());
			if(instructions!=null && !instructions.toString().isEmpty()){
				session.delete(instructions);
			}
			tx.commit();
			isDelete = true;
		}catch (Exception e) {
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isDelete;
	}

}
