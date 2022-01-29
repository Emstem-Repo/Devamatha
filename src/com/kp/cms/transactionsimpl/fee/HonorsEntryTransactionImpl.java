package com.kp.cms.transactionsimpl.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeePaymentApplicantDetails;
import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.forms.fee.HonorsEntryForm;
import com.kp.cms.transactions.fee.IHonorsEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HonorsEntryTransactionImpl implements IHonorsEntryTransaction
{
	public HonorsEntryBo checkDuplicate(HonorsEntryForm entryForm, Student student)throws Exception
	{
		Session session=null;
		HonorsEntryBo honorsEntryBo;
		try
		{
			session=HibernateUtil.getSession();
			String query="from HonorsEntryBo h where h.student.id='"+student.getId()+"' and h.semister="+entryForm.getSemister();
			honorsEntryBo = (HonorsEntryBo) session.createQuery(query).uniqueResult();
			return honorsEntryBo;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		finally
		{
			if(session!=null)
			{
				session.clear();
				session.flush();
			}
		}
		
	}
	
	public boolean updateHonorsEntry(HonorsEntryBo entryBo)throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			session.save(entryBo);
			transaction.commit();
			session.flush();
			session.clear();
			isUpdated=true;
		}
		catch (Exception e) 
		{
			transaction.rollback();
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
				session.flush();
		}
		return isUpdated;
	}
	
	public List<Program> getHonorPrograms()throws Exception
	{
		List<Program> programList;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="from Program p where p.programType.id=2 and p.name like '%Hono%' ";
			programList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw new ApplicationException(e);
		}
		return programList;
	}
	
	public HonorsEntryBo getHonorsDetails(HonorsEntryForm entryForm, Student student) throws Exception
	{
		Session session=null;
		HonorsEntryBo honorsEntryBo;
		try
		{
			session=HibernateUtil.getSession();
			String query="from HonorsEntryBo h where h.isActive = 1 and h.student.id="+student.getId()+" and h.course.id='"+entryForm.getCourseId()+"' and h.academicYear='"+entryForm.getAcademicYear()+"' and h.semister="+entryForm.getSemister();
			honorsEntryBo = (HonorsEntryBo) session.createQuery(query).uniqueResult();
				return honorsEntryBo;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		finally
		{
			if(session!=null)
			{
				session.clear();
				session.flush();
			}
		}
	}
	
	public boolean deleteHonorsEntry(HonorsEntryBo entryBo) throws Exception
	{
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try
		{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			String query="select fee_payment.* from fee_payment inner join fee_payment_applicant_details on fee_payment_applicant_details.fee_payment_id = fee_payment.id" +
					" inner join fee_group on fee_payment_applicant_details.fee_group_id = fee_group.id" +
					" where student_id =" +entryBo.getStudent().getId()+
					" and course_id=" +entryBo.getCourse().getId()+
					" and fee_payment_applicant_details.semester_no=" +entryBo.getSemister()+
					" and fee_group.is_optional=0 and fee_payment.is_challan_canceled=0";
			
			List feeDetails=session.createSQLQuery(query).list();
			if(feeDetails!=null && !feeDetails.isEmpty()){
				throw new BillGenerationException();
			}
			session.update(entryBo);
			transaction.commit();
			session.flush();
			session.clear();
			isUpdated=true;
		}catch (BillGenerationException e) {
			transaction.rollback();
			 throw e;				 
		 }
		catch (Exception e) 
		{
			transaction.rollback();
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
				session.flush();
		}
		return isUpdated;
	}
	
	public boolean reActivateHonorsEntry(int id, String userId) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			HonorsEntryBo honorsEntryBo = (HonorsEntryBo) session
					.get(HonorsEntryBo.class, id);
			honorsEntryBo.setIsActive(true);
			honorsEntryBo.setModifiedBy(userId);
			honorsEntryBo.setModifiedDate(new Date());
			session.update(honorsEntryBo);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	public Student getStudent(HonorsEntryForm entryForm)throws Exception
	{
		Student student=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String query="from Student s where s.admAppln.appliedYear="+entryForm.getAcademicYear()+" and s.registerNo='"+entryForm.getRegNo()+"'";
			student=(Student) session.createQuery(query).uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exceptions
			throw new ApplicationException();
		}
		return student;
	}
}
