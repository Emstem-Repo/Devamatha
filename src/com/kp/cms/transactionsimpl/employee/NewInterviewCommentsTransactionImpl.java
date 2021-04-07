package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.bo.employee.NewInterviewComments;
import com.kp.cms.bo.employee.NewInterviewCommentsDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.NewInterviewCommentsForm;
import com.kp.cms.transactions.employee.INewInterviewCommentsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class NewInterviewCommentsTransactionImpl implements INewInterviewCommentsTransaction {
	private static final Log log = LogFactory.getLog(NewInterviewCommentsTransactionImpl.class);
	public static volatile NewInterviewCommentsTransactionImpl newInterviewCommentsTransactionImpl =null;
	public static NewInterviewCommentsTransactionImpl getInstance(){
		if(newInterviewCommentsTransactionImpl ==null){
			newInterviewCommentsTransactionImpl =new NewInterviewCommentsTransactionImpl();
			return newInterviewCommentsTransactionImpl;
		}
		return newInterviewCommentsTransactionImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#addInterviewComments(com.kp.cms.bo.employee.NewInterviewComments)
	 */
	@Override
	public boolean addInterviewComments(NewInterviewComments newInterviewComments, NewInterviewCommentsForm interviewCommentsForm)throws Exception {
		boolean isAdded = false;
		Session session=null;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(newInterviewComments);
			tx.commit();
			session.flush();
			session.close();
			isAdded = true;
		}catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during saving Interview Comments data..." + e);
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getInterviewRatingFactor()
	 */
	@Override
	public List<InterviewRatingFactor> getInterviewRatingFactor(NewInterviewCommentsForm form) throws Exception {
		Session session=null;
		List<InterviewRatingFactor> list=null;
		try{
			session=HibernateUtil.getSession();
			String str = "";
			if(form.getIsTeachingStaff()){
				 str="from InterviewRatingFactor i where i.teaching = 1 and i.isActive = 1";
			}else{
				 str="from InterviewRatingFactor i where i.teaching = 0 and i.isActive = 1";
			}
			Query query=session.createQuery(str);
			list=query.list();
			session.flush();
		}catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getEmpOnlineResumeList()
	 */
	public List<Object[]> getEmpOnlineResumeList() throws Exception {
		Session session=null;
		List<Object[]> interviewComments=null;
		try{
			session=HibernateUtil.getSession();
			String str="select e.id,e.name,e.email from EmpOnlineResume e where e.isActive = 1";
			Query query=session.createQuery(str);
			interviewComments=query.list();
			session.flush();
		}catch (Exception e) {
			 log.error("Error in getEducationDetails...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
		return interviewComments;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getEmpOnlineResumeList1(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public List<Object[]> getEmpOnlineResumeList1(NewInterviewCommentsForm interviewCommentsForm)throws Exception {
		Session session=null;
		List<Object[]> objects=null;
		
		try{
			session=HibernateUtil.getSession();
			if(interviewCommentsForm.getName() != null && !interviewCommentsForm.getName().isEmpty() && interviewCommentsForm.getApplicationNo()!=null && !interviewCommentsForm.getApplicationNo().isEmpty()){
				String str="select emp.id,emp.name,emp.email from EmpOnlineResume emp where emp.isActive = 1 and " +
				"emp.name like '%" + interviewCommentsForm.getName() + "%'"; 
				Query query=session.createQuery(str);
				objects = query.list();
			}
			else if(interviewCommentsForm.getName() != null && !interviewCommentsForm.getName().isEmpty()){
				String str="select emp.id,emp.name,emp.email from EmpOnlineResume emp where emp.isActive = 1 and " +
				"emp.name like '%" + interviewCommentsForm.getName() + "%'";
				Query query=session.createQuery(str);
				objects = query.list();
			}else if(interviewCommentsForm.getApplicationNo()!=null && !interviewCommentsForm.getApplicationNo().isEmpty()){
				String str = "select emp.id,emp.name,emp.email from EmpOnlineResume emp where emp.isActive = 1 and " +
						"emp.applicationNo=" +interviewCommentsForm.getApplicationNo();
				Query query=session.createQuery(str);
				objects = query.list();
			}else {
				String str="select e.id,e.name,e.email from EmpOnlineResume e where e.isActive = 1";
				Query query=session.createQuery(str);
				objects=query.list();
			}
			session.flush();
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		return objects;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getEmpInfo(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public NewInterviewComments getEmpInfo(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		NewInterviewComments onlineResume = new NewInterviewComments();
		try{
			session=HibernateUtil.getSession();
			String string = "from EmpOnlineResume emp where emp.id=" +interviewCommentsForm.getId() + " and emp.isActive = 1";
			Query query1 = session.createQuery(string);
			EmpOnlineResume empOnlineResume = (EmpOnlineResume) query1.uniqueResult();
			if(empOnlineResume.getPostAppliedDesig().equalsIgnoreCase("Teaching")){
				String str= "from NewInterviewComments n where n.empOnlineResume.id=" +interviewCommentsForm.getId();
				Query query = session.createQuery(str);
				onlineResume = (NewInterviewComments) query.uniqueResult();
				interviewCommentsForm.setIsTeachingStaff(true);
			} else if(empOnlineResume.getPostAppliedDesig().equalsIgnoreCase("Non-Teaching")){
				String str= "from NewInterviewComments n where n.empOnlineResume.id=" +interviewCommentsForm.getId();
				Query query = session.createQuery(str);
				onlineResume = (NewInterviewComments) query.uniqueResult();
				interviewCommentsForm.setIsTeachingStaff(false);
			} else if(empOnlineResume.getPostAppliedDesig().equalsIgnoreCase("Guest")){
				String str= "from NewInterviewComments n where n.empOnlineResume.id=" +interviewCommentsForm.getId();
				Query query = session.createQuery(str);
				onlineResume = (NewInterviewComments) query.uniqueResult();
				interviewCommentsForm.setIsTeachingStaff(true);
			}
			session.flush();
			return onlineResume;
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
	}
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getPrintDetailsList(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public List<NewInterviewComments> getPrintDetailsList(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		List<NewInterviewComments> comments;
		try{
			session=HibernateUtil.getSession();
			String str="from NewInterviewComments i where i.id=" +interviewCommentsForm.getInterviewCommentId();
			Query query=session.createQuery(str);
			comments = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return comments;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getPrintCommentsDetailsList(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public List<NewInterviewCommentsDetails> getPrintCommentsDetailsList(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		List<NewInterviewCommentsDetails> details;
		try{
			session=HibernateUtil.getSession();
			String str="from NewInterviewCommentsDetails i where i.newInterviewComments.id=" +interviewCommentsForm.getInterviewCommentId() + " order by i.interviewRatingFactor.displayOrder";
			Query query=session.createQuery(str);
			details=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return details;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getEmpDetails(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public Object[] getEmpDetails(NewInterviewCommentsForm interviewCommentsForm)
			throws Exception {
		Session session = null;
		Object[] objects = null;
		try {
			session = HibernateUtil.getSession();
			String str = "select emp.applicationNo,emp.name,d.id from EmpOnlineResume emp"
					+ " left join emp.department d"
					+ " where emp.id="
					+ interviewCommentsForm.getId();
			Query query = session.createQuery(str);
			objects = (Object[]) query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return objects;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getViewEmpInfoDetails(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public List<EmpOnlineResume> getEmpInfoDetails(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		List<EmpOnlineResume> onlineResumes;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpOnlineResume emp where emp.id=" +interviewCommentsForm.getId();
			Query query=session.createQuery(str);
			onlineResumes = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return onlineResumes;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#getNoOfInterviewers(com.kp.cms.forms.employee.NewInterviewCommentsForm)
	 */
	@Override
	public Object[] getNoOfInterviewers(NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		Object[] objects=null;
		try{
			session=HibernateUtil.getSession();
			String str="select i.noOfInternalInterviewers,i.noOfExternalInterviewers from NewInterviewComments i where i.id=" +interviewCommentsForm.getInterviewCommentId();
			Query query=session.createQuery(str);
			objects=(Object[]) query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return objects;
	}

	@Override
	public List<EmpOnlineResume> getEmpEduDetails(
			NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object[]> getEmpOnlineEducationalList( NewInterviewCommentsForm interviewCommentsForm) throws Exception {
		Session session=null;
		List<Object[]> onlineResumes;
		try{
			session=HibernateUtil.getSession();
			String str="select empEdu.course,empEdu.yearOfCompletion,empEdu.grade,empEdu.institute,empEdu.empQualificationLevel.name from EmpOnlineResume emp join emp.educationalDetailsSet empEdu where emp.id=" +interviewCommentsForm.getId() + " order by empEdu.empQualificationLevel.displayOrder";
			Query query=session.createQuery(str);
			onlineResumes = query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		return onlineResumes;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.INewInterviewCommentsTransaction#updateDepartmentIdInEmpOnlineResume(int)
	 */
	@Override
	public void updateDepartmentIdInEmpOnlineResume(NewInterviewCommentsForm interviewCommentsForm)throws Exception {
		Session session =null;
		Transaction tx=null;
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			EmpOnlineResume empOnlineResume = (EmpOnlineResume) session.get(EmpOnlineResume.class, interviewCommentsForm.getId());
			Department dept = new Department();
			if(interviewCommentsForm.getDepartment()!=null && !interviewCommentsForm.getDepartment().isEmpty()){
				dept.setId(Integer.parseInt(interviewCommentsForm.getDepartment()));
				empOnlineResume.setDepartment(dept);
			}else {
				empOnlineResume.setDepartment(null);
			}
			empOnlineResume.setLastModifiedDate(new Date());
			empOnlineResume.setModifiedBy(interviewCommentsForm.getUserId());
			session.update(empOnlineResume);
			tx.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
		}
		
	}
	
}
