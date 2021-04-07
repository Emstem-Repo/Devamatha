package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.PromoteBioData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admission.IPromoteBioDataUploadTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class PromoteBioDataUploadTxnImpl implements IPromoteBioDataUploadTransaction{
	private static volatile PromoteBioDataUploadTxnImpl bioDataUploadTxnImpl = null;
	public static PromoteBioDataUploadTxnImpl getInstance(){
		if(bioDataUploadTxnImpl == null){
			bioDataUploadTxnImpl = new PromoteBioDataUploadTxnImpl();
			return bioDataUploadTxnImpl;
		}
		return bioDataUploadTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPromoteBioDataUploadTransaction#uploadPromoteBioData(java.util.List)
	 */
	@Override
	public boolean uploadPromoteBioData(List<PromoteBioData> bioDataUploadList)
			throws Exception {
		boolean isAdded= false;
		Session session = null;
		Transaction transaction = null;
		PromoteBioData bioData = null;
		try{
			session=HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(bioDataUploadList!=null && !bioDataUploadList.isEmpty()){
				Iterator<PromoteBioData> iterator = bioDataUploadList.iterator();
				while (iterator.hasNext()) {
					PromoteBioData promoteBioData = (PromoteBioData) iterator .next();
					String str = "from PromoteBioData bioData where bioData.academicYear="+promoteBioData.getAcademicYear()+"and bioData.regNo='"+promoteBioData.getRegNo()+"'";
					Query query = session.createQuery(str);
					bioData=(PromoteBioData) query.uniqueResult();
					if(bioData == null){
						session.save(promoteBioData);
					}
				}
				transaction.commit();
				session.flush();
				session.close();
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
   @Override
	public Map<Integer, String> getCourseMap() throws Exception {
		Session session = null;
		Map<Integer ,String> courseMap = new HashMap<Integer ,String>();
		try{
			session = HibernateUtil.getSession();
			String str ="from Course course where course.isActive= 1";
			Query query = session.createQuery(str);
			List<Course> courseList = query.list();
			if(courseList != null){
				Iterator<Course> iterator= courseList.iterator();
				while (iterator.hasNext()) {
					Course course = (Course) iterator.next();
					if(course!=null && course.getId()>0 && course.getName()!=null && !course.getName().isEmpty()){
						courseMap.put(course.getId(), course.getCode());
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		courseMap=(Map<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
@Override
public List<PromoteBioData> getProBioDataDetails(StringBuffer query) throws Exception {
	 List<PromoteBioData> proBioDataList;
	 Session session = null;
	 try{
		 session = HibernateUtil.getSession();
		 Query qry = session.createQuery(query.toString());
		 proBioDataList = qry.list();
	 }catch (Exception e) {
			throw new ApplicationException(e);
		}
	return proBioDataList;
}

}
