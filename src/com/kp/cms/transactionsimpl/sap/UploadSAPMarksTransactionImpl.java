package com.kp.cms.transactionsimpl.sap;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.sap.UploadSAPMarksForm;
import com.kp.cms.transactions.sap.IUploadSAPMarksTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadSAPMarksTransactionImpl implements IUploadSAPMarksTransaction{


	private static volatile UploadSAPMarksTransactionImpl uploadSAPMarksTransactionImpl = null;
	private static final Log log = LogFactory.getLog(UploadSAPMarksTransactionImpl.class);
	public static UploadSAPMarksTransactionImpl getInstance() {
		if (uploadSAPMarksTransactionImpl == null) {
			uploadSAPMarksTransactionImpl = new UploadSAPMarksTransactionImpl();
		}
		return uploadSAPMarksTransactionImpl;
	}
	
	
	
	 public boolean saveUploadSAPMarks(Map<Integer,UploadSAPMarksBo> sapbo,UploadSAPMarksForm objform) throws Exception {
			log.debug("inside uploadBlockListForHallticketOrMarkscard");
			
			//ExamBlockUnblockHallTicketBO examBo = new ExamBlockUnblockHallTicketBO();
			
			Session session = null;
			Transaction transaction = null;
			boolean isSaved=false;
				try {
				
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				
				if(sapbo!=null && !sapbo.isEmpty()){
				Iterator<Entry<Integer, UploadSAPMarksBo>> iterator = sapbo.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, UploadSAPMarksBo> entry = (Map.Entry<Integer, UploadSAPMarksBo>) iterator.next();
					UploadSAPMarksBo bo=entry.getValue();
				if(bo!=null){	
					Query query = session.createQuery("from UploadSAPMarksBo e where  e.studentId=:studentId" +
														" and e.date=:date");
					if( bo.getStudentId()!= null){
						query.setInteger("studentId", bo.getStudentId().getId());
					}
					if( bo.getDate()!= null){
						query.setDate("date", bo.getDate());
					}
					UploadSAPMarksBo examBo = (UploadSAPMarksBo)query.uniqueResult();
					if(examBo !=null){
						examBo.setMarks(bo.getMarks());
						examBo.setStatus(bo.getStatus());
						examBo.setLastModifiedDate(new Date());
						examBo.setModifiedBy(objform.getUserId());
						session.update(examBo);
						
					}else{
						session.save(bo);
					}
					isSaved=true;
					
				}
					
			}
				transaction.commit();
				session.flush();
		}else{
					isSaved=false;
				}
				session.close();
			} catch (Exception e) {
				log.error("Error during duplcation checking..." , e);
				session.flush();
				throw new ApplicationException(e);
			}
			log.debug("leaving isDocTypeDuplcated");
			return isSaved;
		}

	 public	Map<String,Integer> getStudentIdByStudentRegNum(String year,List<String> regNumList) throws Exception{

			Session session=null;
			Map<String,Integer> studentIdMap=new HashMap<String,Integer>();
			try{
				session=HibernateUtil.getSession();
				Query query = session.createQuery("from Student s where s.registerNo != null and s.isHide = 0 and s.admAppln.isCancelled=0 " +
												  " and s.classSchemewise.curriculumSchemeDuration.academicYear=:year" +
												  " and s.registerNo in (:regNumList)");
			  query.setParameter("year", (Integer.parseInt(year)));
			  query.setParameterList("regNumList", regNumList);
			  List<Student> listl=query.list();
				Iterator<Student> itr = listl.iterator();
				while (itr.hasNext()) {
					Student student = (Student) itr.next();
					studentIdMap.put(student.getRegisterNo(), student.getId());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				throw new ApplicationException();
			}finally{
				if(session!=null)
					session.flush();
				session.close();
			}
			
			return studentIdMap;
	    }



}
