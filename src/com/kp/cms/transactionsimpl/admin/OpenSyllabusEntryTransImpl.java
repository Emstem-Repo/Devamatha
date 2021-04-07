package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.kp.cms.bo.admin.OpenSyllabusEntry;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.OpenSyllabusEntryForm;
import com.kp.cms.transactions.admin.IOpenSyllabusEntryTrans;
import com.kp.cms.utilities.HibernateUtil;

public class OpenSyllabusEntryTransImpl implements IOpenSyllabusEntryTrans{
	public static volatile OpenSyllabusEntryTransImpl openSyllabusEntryTransImpl=null;
	//private constructor
	private OpenSyllabusEntryTransImpl(){
		
	}
	//singleton object
	public static OpenSyllabusEntryTransImpl getInstance(){
		if(openSyllabusEntryTransImpl==null){
			openSyllabusEntryTransImpl=new OpenSyllabusEntryTransImpl();
			return openSyllabusEntryTransImpl;
		}
		return openSyllabusEntryTransImpl;
	}
	@Override
	public boolean checkDuplicate(OpenSyllabusEntryForm openEntryForm, String mode)
			throws Exception {
		boolean flag=false;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from OpenSyllabusEntry a where a.isActive=1 and a.batch="+Integer.parseInt(openEntryForm.getBatch()));
			OpenSyllabusEntry openSyllabusEntry=(OpenSyllabusEntry)query.uniqueResult();
			if(openSyllabusEntry!=null){
				if(mode.equalsIgnoreCase("add")){
					flag=true;
				}else if(mode.equalsIgnoreCase("update") && openEntryForm.getId()!=openSyllabusEntry.getId()){
					flag=true;
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return flag;
	}
	@Override
	public boolean save(OpenSyllabusEntry openSyllabusEntry) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				session.save(openSyllabusEntry);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
	@Override
	public OpenSyllabusEntry getOpenSyllabusEntry(int id) throws Exception {
		Session session=null;
		OpenSyllabusEntry openSyllabusEntry=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from OpenSyllabusEntry a where a.id="+id);
			openSyllabusEntry=(OpenSyllabusEntry)query.uniqueResult();
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return openSyllabusEntry;
	}
	@Override
	public boolean update(OpenSyllabusEntry openSyllabusEntry) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				session.merge(openSyllabusEntry);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				}
		}
		return flag;
	}
	@Override
	public List<OpenSyllabusEntry> getAllOpenSyllabusEntries() throws Exception {
		List<OpenSyllabusEntry> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from OpenSyllabusEntry a where a.isActive=1 order by a.batch");
			list=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return list;
	}
}
