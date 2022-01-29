package com.kp.cms.transactionsimpl.supportrequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.supportrequest.CategoryForm;
import com.kp.cms.transactions.supportrequest.ICategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CategoryTransactionImpl implements ICategoryTransaction{
	public static volatile CategoryTransactionImpl categoryTransactionImpl=null;
	private CategoryTransactionImpl(){
		
	}
	public static CategoryTransactionImpl getInstance(){
		if (categoryTransactionImpl == null) {
			categoryTransactionImpl = new CategoryTransactionImpl();
			return categoryTransactionImpl;
		}
		return categoryTransactionImpl;
	}
	@Override
	public Map<Integer, String> getDepartmentMap()throws Exception {
		Session session=null;
		Map<Integer,String> departmentMap=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from Department a where a.isActive=1");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department department=iterator.next();
					departmentMap.put(department.getId(),department.getName());
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
			return departmentMap;
	
}
	@Override
	public boolean addOrUpdateCategory(CategoryBo categoryBo,String mode) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
				if(mode.equalsIgnoreCase("Add")){
					session.save(categoryBo);
				}else if(mode.equalsIgnoreCase("update")){
					session.merge(categoryBo);
				}
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
	public boolean checkDuplicate(CategoryForm categoryForm) throws Exception {
		boolean flag=false;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1 and a.name='"+categoryForm.getName()+"'");
			CategoryBo categoryBo=(CategoryBo)query.uniqueResult();
			if(categoryBo!=null && categoryForm.getMode().equalsIgnoreCase("Add")){
				flag=true;
			}else if(categoryBo!=null && categoryForm.getMode().equalsIgnoreCase("Update") && categoryBo.getId()!=categoryForm.getId()){
				flag=true;
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
	public List<CategoryBo> getCategory() throws Exception {
		Session session = null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1");
			List<CategoryBo> list = query.list();
			session.flush();
			return list;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}
	@Override
	public boolean deleteFineCategory(int id, boolean activate,
			CategoryForm categoryForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				CategoryBo categoryBo = (CategoryBo) session.get(CategoryBo.class, id);
				if (activate) {
					categoryBo.setIsActive(true);
				} else {
					categoryBo.setIsActive(false);
				}
				categoryBo.setModifiedBy(categoryForm.getUserId());
				categoryBo.setLastModifiedDate(new Date());
				session.update(categoryBo);
			tx.commit();
			session.flush();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		return result;
	}
	@Override
	public CategoryBo getCategoryById(int id) throws Exception {
		Session session = null;
		CategoryBo categoryBo=null;
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			Query query = session.createQuery("from CategoryBo a where a.isActive=1 and a.id="+id);
			categoryBo =(CategoryBo) query.uniqueResult();
			session.flush();
			return categoryBo;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
			}
			throw new ApplicationException(e);
		}
}

}
