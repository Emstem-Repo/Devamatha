package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ConvocationCourse;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PublishStudentEditForm;
import com.kp.cms.transactions.admission.IPublishStudentEditTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class PublishStudentEditTxnImpl implements IPublishStudentEditTransaction{
	
	private static volatile PublishStudentEditTxnImpl obj;
	
	public static PublishStudentEditTxnImpl getInstance(){
		if(obj==null){
			obj=new PublishStudentEditTxnImpl();
		}
		return obj;
	}

	@Override
	public List<Student> getStudentList(PublishStudentEditForm publishStudentEditForm) throws Exception {
		
		Session session =null;
		List<Student> list = new ArrayList<Student>();
		try{
			session=HibernateUtil.getSession();
			String string ="from Student student" +
					 " where student.isActive = 1 and student.admAppln.isCancelled=0 " ;
					
			if(publishStudentEditForm.getRegNoFrom()!=null && !publishStudentEditForm.getRegNoFrom().isEmpty()
						&& publishStudentEditForm.getRegNoTo()!=null && !publishStudentEditForm.getRegNoTo().isEmpty()){
				string=string+" and student.registerNo between '" + publishStudentEditForm.getRegNoFrom() + "' and '"+ publishStudentEditForm.getRegNoTo() +"' order by student.registerNo";
			}
			Query query= session.createQuery(string);
			list = query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateStudentlist(List<PublishStudentEdit> students) throws Exception {
		
		Session session =null;
		Transaction transaction  =null;
		int count=0;
		try{
			session =HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			Iterator<PublishStudentEdit> iterator = students.iterator();
			while (iterator.hasNext()) {
				PublishStudentEdit student = iterator.next();
				session.save(student);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateClasswiseList(	List<PublishStudentEditClasswise> classwises) throws Exception {
		Session session =null;
		Transaction transaction =null;
		int count=0;
		try{
			session= HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			Iterator<PublishStudentEditClasswise> iterator = classwises.iterator();
			while (iterator.hasNext()) {
				PublishStudentEditClasswise classwise = iterator.next();
				session.save(classwise);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PublishStudentEditClasswise> getClasswiseList(PublishStudentEditForm publishStudentEditForm) throws Exception {
		Session session =null;
		List<PublishStudentEditClasswise> classwises= new ArrayList<PublishStudentEditClasswise>();
		try{
			session=HibernateUtil.getSession();
			String string = "from PublishStudentEditClasswise p where p.isActive=1";
			Query query = session.createQuery(string);
			classwises = query.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classwises;
	}

	@Override
	public Map<Integer, PublishStudentEdit> getPublishStudentEdit(PublishStudentEditForm publishStudentEditForm) throws Exception {
		Session session =null;
		Map<Integer, PublishStudentEdit> map=new HashMap<Integer, PublishStudentEdit>();
		List<PublishStudentEdit> classwises;
		try{
			session=HibernateUtil.getSession();
			String string = "from PublishStudentEdit p where p.isActive=1";
			Query query = session.createQuery(string);
			classwises = query.list();
			if(classwises!=null && !classwises.isEmpty()){
				Iterator<PublishStudentEdit> iterator = classwises.iterator();
				while (iterator.hasNext()) {
					PublishStudentEdit edit = iterator.next();
					map.put(edit.getStudent().getId(), edit);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<Integer, PublishStudentEditClasswise> getpublishStudentEditClass(PublishStudentEditForm publishStudentEditForm) throws Exception {
		
		Session session =null;
		Map<Integer, PublishStudentEditClasswise> map=new HashMap<Integer, PublishStudentEditClasswise>();
		List<PublishStudentEditClasswise> classwises;
		try{
			session=HibernateUtil.getSession();
			String string = "from PublishStudentEditClasswise p where p.isActive=1" +
			" and ('"+CommonUtil.ConvertStringToSQLDate(publishStudentEditForm.getEditEndDate())+"' between p.startDate and p.endDate)";
			Query query = session.createQuery(string);
			classwises = query.list();
			if(classwises!=null && !classwises.isEmpty()){
				Iterator<PublishStudentEditClasswise> iterator = classwises.iterator();
				while (iterator.hasNext()) {
					PublishStudentEditClasswise edit = iterator.next();
					map.put(edit.getClasses().getId(), edit);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public boolean deletePublishEdit(PublishStudentEditForm publishStudentEditForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		PublishStudentEditClasswise classwise = new PublishStudentEditClasswise();
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query =session.createQuery("from PublishStudentEditClasswise p where p.id= " + publishStudentEditForm.getId() + "  and p.isActive=1");
			classwise=(PublishStudentEditClasswise)query.uniqueResult();
			if(classwise != null){
				classwise.setIsActive(false);
				classwise.setLastModifiedDate(new Date());
				classwise.setModifiedBy(publishStudentEditForm.getUserId());
			}
			session.update(classwise);
			transaction.commit();
			isDeleted = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return isDeleted;
	}

	@Override
	public PublishStudentEditClasswise getDupentry(PublishStudentEditClasswise classwise) throws Exception {
		Session session =null;
		PublishStudentEditClasswise classwises= new PublishStudentEditClasswise();
		try{
			session=HibernateUtil.getSession();
			String string = "from PublishStudentEditClasswise p where p.academicYear= " + classwise.getAcademicYear() + " and p.classes.id= " +classwise.getClasses().getId() + "  and  p.isActive=0";
			Query query = session.createQuery(string);
			classwises = (PublishStudentEditClasswise)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classwises;
	}

	@Override
	public boolean reactiveClassId(int dupId, boolean activate,PublishStudentEditForm publishStudentEditForm) 
									throws Exception {
		Session session = null;
		Transaction tx = null;
		try
		{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			PublishStudentEditClasswise classwise = (PublishStudentEditClasswise) session.get(PublishStudentEditClasswise.class, dupId);
			if(activate)
			{
				classwise.setIsActive(true);
			}
			else
			{
				classwise.setIsActive(false);
			}
			classwise.setModifiedBy(publishStudentEditForm.getUserId());
			classwise.setLastModifiedDate(new Date());
			session.update(classwise);
			tx.commit();
			session.flush();
			return true;
		}
		catch(ConstraintViolationException e) 
		{
			if(tx!=null)
				tx.rollback();
			throw new BusinessException(e);
		}
		catch(Exception e) 
		{
			if(tx!=null)
			     tx.rollback();
			throw new ApplicationException(e);
		}
	}

	@Override
	public PublishStudentEditClasswise getEditDetails(PublishStudentEditForm publishStudentEditForm) throws Exception {
		Session session =null;
		PublishStudentEditClasswise classwise = new PublishStudentEditClasswise();
		try{
			session = HibernateUtil.getSession();
			String string ="from PublishStudentEditClasswise p where p.id="+publishStudentEditForm.getId();
			Query query = session.createQuery(string);
			classwise=(PublishStudentEditClasswise)query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return classwise;
	}

	@Override
	public boolean updateClass(PublishStudentEditClasswise editClasswise)throws Exception {
		Session session =null;
		boolean isUpdated=false;
		Transaction tx =null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.update(editClasswise);
			tx.commit();
			isUpdated=true;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
}
