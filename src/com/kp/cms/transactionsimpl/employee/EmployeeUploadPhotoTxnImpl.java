package com.kp.cms.transactionsimpl.employee;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.EmployeeUploadPhoto;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeUploadPhotoTxnImpl implements IEmployeeUploadPhotoTransaction{
	private static final Log log = LogFactory.getLog(EmployeeUploadPhotoTxnImpl.class);
	public static volatile EmployeeUploadPhotoTxnImpl employeeUploadPhotoTxnImpl = null;
	public static EmployeeUploadPhotoTxnImpl getInstance(){
		if(employeeUploadPhotoTxnImpl == null){
			employeeUploadPhotoTxnImpl =new EmployeeUploadPhotoTxnImpl();
			return employeeUploadPhotoTxnImpl;
		}
		return employeeUploadPhotoTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactionsimpl.employee.IEmployeeUploadPhotoTransaction#uploadEmployeePhotos(java.util.List)
	 */
	@Override
	public int uploadEmployeePhotos(List<EmployeeUploadPhoto> employeeUploadPhotoList) throws Exception {
		log.info("entering into uploadEmployeePhotos  type TransactionImpl");	
		Session session = null;
		Transaction transaction = null;
		int uploadPhoto=0;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<EmployeeUploadPhoto> iterator = employeeUploadPhotoList.iterator();
			while (iterator.hasNext()) {
				EmployeeUploadPhoto employeeUploadPhoto = (EmployeeUploadPhoto) iterator.next();
				Query query=(Query)session.createQuery("from Employee e where e.fingerPrintId= " +employeeUploadPhoto.getFingerPrintId()+"and e.isActive=1");
				Employee employee=(Employee)query.uniqueResult();
				if(employee!=null){
					EmpImages empImages=(EmpImages)session.createQuery("from EmpImages e where e.employee.id="+employee.getId()).uniqueResult();
					Set<EmpImages> images=new HashSet<EmpImages>();
					if(empImages==null){
						empImages=new EmpImages();
						empImages.setCreatedBy(employeeUploadPhoto.getModifiedBy());
						empImages.setCreatedDate(new Date());
					}
					if(employeeUploadPhoto.getPhoto() != null){
						FileOutputStream fos = new FileOutputStream(CMSConstants.EMPLOYEE_PHOTO_FOLDER_PATH+"E"+employee.getId()+".jpg");
						fos.write(employeeUploadPhoto.getPhoto());
						fos.close();
					}
					empImages.setModifiedBy(employeeUploadPhoto.getModifiedBy());
					empImages.setLastModifiedDate(employeeUploadPhoto.getLastModifiedDate());
					empImages.setEmpPhoto(employeeUploadPhoto.getPhoto());
					images.add(empImages);
					employee.setEmpImages(images);
					employee.setModifiedBy(employeeUploadPhoto.getModifiedBy());
					employee.setLastModifiedDate(employeeUploadPhoto.getLastModifiedDate());
					session.saveOrUpdate(employee);
					uploadPhoto=uploadPhoto+1;
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			log.error("Error occured in uploadEmployeePhotos of EmployeeUploadPhotoTxnImpl", e);
			if (transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
			
		}
		return uploadPhoto;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactionsimpl.employee.IEmployeeUploadPhotoTransaction#getEmployee(java.lang.String)
	 */
	@Override
	public Employee getEmployee(String fingerFrintId) throws Exception {
		Employee employee = new Employee();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Employee e where e.fingerPrintId="+fingerFrintId+" and e.isActive=1");
			employee = (Employee) query.uniqueResult();
			return employee;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return employee;
	}
}
