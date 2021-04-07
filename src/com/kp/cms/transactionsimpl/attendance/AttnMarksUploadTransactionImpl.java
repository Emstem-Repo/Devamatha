package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.PucAttnInternalMarks;
import com.kp.cms.bo.admin.PucttnAttendance;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttnMarksUploadForm;
import com.kp.cms.transactions.attandance.IAttnMarksUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttnMarksUploadTransactionImpl implements IAttnMarksUploadTransaction {
	private static volatile AttnMarksUploadTransactionImpl attnMarksUploadTransactionImpl =null;
	public static AttnMarksUploadTransactionImpl getInstance(){
		if(attnMarksUploadTransactionImpl == null){
			attnMarksUploadTransactionImpl = new AttnMarksUploadTransactionImpl();
			return attnMarksUploadTransactionImpl;
		}
		return attnMarksUploadTransactionImpl;
	}
	@Override
	public boolean addAttnMarksUpload(List<AttnMarksUpload> marksUploadsList)
			throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(marksUploadsList!=null){
				Iterator<AttnMarksUpload> iterator = marksUploadsList.iterator();
				while (iterator.hasNext()) {
					AttnMarksUpload attnMarksUpload = (AttnMarksUpload) iterator .next();
					String str= "from AttnMarksUpload attnMarks where attnMarks.academicYear="+attnMarksUpload.getAcademicYear()+"and attnMarks.regNo = '"+attnMarksUpload.getRegNo()+"'";
					Query query = session.createQuery(str);
					AttnMarksUpload marksUpload = (AttnMarksUpload) query.uniqueResult();
					if(marksUpload!=null){
						session.update(marksUpload);
					}
					else{
						session.save(attnMarksUpload);
					}
				}
				transaction.commit();
				session.flush();
				isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttnMarksUploadTransaction#addPucttnAttendanceUpload(java.util.List)
	 */
	public boolean addPucttnAttendanceUpload( List<PucttnAttendance> pucttnAttendances) throws Exception {
		boolean isAdded=false;
		Session session = null;
		Transaction tx = null;
		PucttnAttendance attendance =null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(pucttnAttendances!=null){
				Iterator<PucttnAttendance> iterator = pucttnAttendances.iterator();
				while (iterator.hasNext()) {
					PucttnAttendance pucttnAttendance = (PucttnAttendance) iterator .next();
					String str= "from PucttnAttendance pucAttn where pucAttn.academicYear="+pucttnAttendance.getAcademicYear()+"and pucAttn.regNo='"+pucttnAttendance.getRegNo()+"'";
					Query query = session.createQuery(str);
					attendance = (PucttnAttendance) query.uniqueResult();
					if(attendance == null){
						session.save(pucttnAttendance);
					}
				}
				tx.commit();
				session.flush();
				isAdded= true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public boolean addAttnInternalMarks(
			List<PucAttnInternalMarks> internalMarksList,AttnMarksUploadForm attnMarksUploadForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			String programHibernateQuery = "select merit.regNo from PucAttnInternalMarks merit where merit.academicYear="+attnMarksUploadForm.getAcademicYear();
			List<String> admList = session.createQuery(programHibernateQuery).list();
				if(internalMarksList!=null){
					for(PucAttnInternalMarks admMerit:internalMarksList){
						if (admMerit.getRegNo()!= null) {
							if (!admList.contains(admMerit.getRegNo())) {
								session.save(admMerit);
							} 
						}
					}
				transaction.commit();
				session.flush();
				isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
}
