package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.BoardDetailsForm;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.transactions.admission.IBoardDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BoardDetailsTransactionImpl implements IBoardDetailsTransaction {
	private static final Log log = LogFactory.getLog(BoardDetailsTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IBoardDetailsTransaction#getStudentDetails(java.lang.String)
	 */
	@Override
	public List<Student> getStudentDetails(String query) throws Exception {
		Session session = null;
		List<Student> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public Map<String,Integer> getList(String query) throws Exception {
		Session session = null;
		Map<String,Integer> selectedCandidatesList = new HashMap<String, Integer>();
		List<Object[]> objList=null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			objList = selectedCandidatesQuery.list();
			if(objList!=null && !objList.isEmpty()){
				Iterator<Object[]> itr=objList.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
					selectedCandidatesList.put(objects[0].toString(),Integer.parseInt(objects[1].toString()));
				}
			}
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

	@Override
	public boolean updateDetails(BoardDetailsForm boardDetailsForm)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		BoardDetailsTO boardDetailsTO;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<BoardDetailsTO> tcIterator = boardDetailsForm.getBoardList().iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				boardDetailsTO = tcIterator.next();
				Student s=(Student)session.get(Student.class,boardDetailsTO.getStudentId());
				s.setExamRegisterNo(boardDetailsTO.getExamRegNo());
				s.setStudentNo(boardDetailsTO.getStudentNo());
				s.setLastModifiedDate(new Date());
				s.setModifiedBy(boardDetailsForm.getUserId());
				session.update(s);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IBoardDetailsTransaction#getYearMap()
	 */
	public Map<Integer, Integer> getYearMap() throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery(
							"select distinct cls.termNumber from Classes cls order by cls.termNumber");
					
			List<Integer> yearList = query.list();
			Map<Integer, Integer> yearMap = new LinkedHashMap<Integer, Integer>();
			Iterator<Integer> itr = yearList.iterator();
			Classes classes;

			while (itr.hasNext()) {
				int year=itr.next();
				yearMap.put(year, year);
						
			}
			session.flush();
			return yearMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, Integer>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IBoardDetailsTransaction#getProgramMap()
	 */
	@Override
	public Map<Integer, String> getProgramMap() throws Exception {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery(
							" from Program p where p.isActive=1 order by p.name");
					
			List<Program> programList = query.list();
			Map<Integer, String> programMap = new HashMap<Integer, String>();
			Iterator<Program> itr = programList.iterator();
			Program programs;

			while (itr.hasNext()) {
				programs=(Program)itr.next();
				programMap.put(programs.getId(), programs.getName());
			}
			session.flush();
			// session.close();
			//yearMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(yearMap);
			return programMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getClassSchemwiseIds(String search)
			throws Exception {
		List<Integer> ids=null;
		StringBuilder clsids=new StringBuilder();
		try{
		Session session = HibernateUtil.getSession();
		Query query=session.createQuery(search);
		 ids=query.list();
		Iterator<Integer> itr=ids.iterator();
		while(itr.hasNext()){
			if(!clsids.toString().isEmpty())
				clsids.append(",");
			clsids.append(itr.next().toString());
			
		}
		}catch(Exception ex){
			log.debug("Exception" + ex.getMessage());
		}
		return clsids.toString();
	}

	@Override
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception {
		List<Integer> studentList=null;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			/*	String query="select s.student.id from ExamStudentDetentionRejoinDetails s " +
							"where s.id=(select max(id) from ExamStudentDetentionRejoinDetails b " +
							"where b.student.id=s.student.id) and (s.detain=1 or s.discontinued=1)";
			*/
			String query="select s.student.registerNo from ExamStudentDetentionRejoinDetails s " +
			"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)";
			studentList=session.createQuery(query).list();
		}
		catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
		
		return studentList;
	}

}
