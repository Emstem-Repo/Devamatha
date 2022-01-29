package com.kp.cms.transactionsimpl.admin;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * This is an Implementation class for Template. 
 * will save / loads the templates.
 */
public class TemplateImpl implements ITemplatePassword{

	private static final Log log = LogFactory.getLog(TemplateImpl.class);
	public static volatile TemplateImpl templateImpl = null;

	public static TemplateImpl getInstance() {
		if (templateImpl == null) {
			templateImpl = new TemplateImpl();
			return templateImpl;
		}
		return templateImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#saveGroupTemplate(com.kp.cms.bo.admin.GroupTemplate)
	 */
	@Override
	public boolean saveGroupTemplate(GroupTemplate groupTemplate) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 tx = session.beginTransaction();
			 tx.begin();
			 session.saveOrUpdate(groupTemplate);
			 tx.commit();
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 if(tx!=null)
			     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 if(tx!=null)
			     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception");
			 throw e;				 
		 }finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		 } 
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getGroupTemplates(int)
	 */
	@Override
	public List<GroupTemplate> getGroupTemplates(int id,String programTypeId,String programId,String templateName) throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		String sqlQuery="";
		boolean programExists=false;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (id != 0) {
				 sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program"
						+" left join groupTemplate.course cou where groupTemplate.id = " +id+
						" order by groupTemplate.programType.name,groupTemplate.program.name," +
						" groupTemplate.course.name,groupTemplate.templateName";
			} //Modifications newly added by chandra- to display the group template list according to programtype or program or template name dynamically in jsp
			 else if(programTypeId!=null && !programTypeId.trim().isEmpty() || (templateName!=null && !templateName.trim().isEmpty())){
				if ((programTypeId!=null && !programTypeId.trim().isEmpty()) && (programId !=null && !programId.trim().isEmpty()&& (templateName !=null && !templateName.trim().isEmpty())) ){
					sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
					 
					 if(!programTypeId.trim().isEmpty()){
						 sqlQuery=sqlQuery+" groupTemplate.programType='" +programTypeId+"'";
					 }
					 if(!templateName.trim().isEmpty()){
						 sqlQuery=sqlQuery+" and groupTemplate.templateName='" +templateName+"'";
					 }
					 if(!programId.trim().isEmpty()){
						 sqlQuery=sqlQuery+	" and  program.id=" +programId;
						 }
					 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
				
				}else if((programTypeId!=null && !programTypeId.trim().isEmpty()) && (templateName !=null && !templateName.trim().isEmpty())){
					sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
					 
					 if(!programTypeId.trim().isEmpty()){
						 sqlQuery=sqlQuery+" groupTemplate.programType='" +programTypeId+"'";
					 }
					 if(!templateName.trim().isEmpty()){
						 sqlQuery=sqlQuery+" and groupTemplate.templateName='" +templateName+"'";
					 }
					 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
					
				} else if((programTypeId!=null && !programTypeId.trim().isEmpty()) && (programId !=null && !programId.trim().isEmpty())){
					sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
					 
					 if(!programTypeId.trim().isEmpty()){
						 sqlQuery=sqlQuery+" groupTemplate.programType='" +programTypeId+"'";
					 }
					 if(!programId.trim().isEmpty()){
					 sqlQuery=sqlQuery+	" and  program.id=" +programId;
					 }
					 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
				
				}else if ((programTypeId!=null && !programTypeId.trim().isEmpty()) && (programId ==null || programId.trim().isEmpty())&& (templateName ==null || templateName.trim().isEmpty())) {
					sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
					 
					 if(!programTypeId.trim().isEmpty()){
						 sqlQuery=sqlQuery+" groupTemplate.programType='" +programTypeId+"'";
					 }
					 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
				}else if (templateName!=null && !templateName.trim().isEmpty()){
					 sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
					 
					 if(!templateName.trim().isEmpty()){
						 sqlQuery=sqlQuery+" groupTemplate.templateName='" +templateName+"'";
					 }
					 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
				 }
				
			}//end
/*// Modifications newly added by Smitha- to display the group template list according to program or template name dynamically in jsp
			 else if ((programId!=null && !programId.trim().isEmpty()) || (templateName!=null && !templateName.trim().isEmpty())){
				 sqlQuery="select groupTemplate from GroupTemplate groupTemplate left join groupTemplate.programType ptype left join groupTemplate.program program left join groupTemplate.course cou where" ;
				 if(programId!=null && !programId.trim().isEmpty()){
					  programExists=true;
				 sqlQuery=sqlQuery+	" program.id=" +programId;
				 }
				 if(templateName!=null && !templateName.trim().isEmpty()){
					 if(programExists)
						 sqlQuery=sqlQuery+" and groupTemplate.templateName='" +templateName+"'";
					 else sqlQuery=sqlQuery+" groupTemplate.templateName='" +templateName+"'";
				 }
				 sqlQuery=sqlQuery+" order by ptype.name,cou.name,program.name,groupTemplate.templateName";
			 }//ends
*/			 else {
				 sqlQuery = "select groupTemplate from GroupTemplate groupTemplate  left join groupTemplate.programType ptype left join groupTemplate.program program" +
						" left join groupTemplate.course cou order by ptype.name,cou.name,program.name,groupTemplate.templateName";
				
			}
			 Query query=session.createQuery(sqlQuery);
			 list = query.list();
			 return list;
		 } 
		catch (RuntimeException runtime) {
			 log.debug("Txn Impl : Leaving getTemplates with RuntimeException"+runtime.getMessage());
			 throw runtime;
		 }catch (Exception e) {
			 log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			 throw e;
		 }finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		 }
	}
		
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#deleteGroupTemplate(com.kp.cms.bo.admin.GroupTemplate)
	 */
	@Override
	public boolean deleteGroupTemplate(GroupTemplate groupTemplate) throws Exception {
		Session session = null; 
		Transaction tx = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 tx = session.beginTransaction();
			 tx.begin();
			 session.delete(groupTemplate);
			 tx.commit();
	    	 return true;
		 } catch (ConstraintViolationException e) {
			 if(tx!=null)
			      tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
			 throw e;				 
		 } catch (Exception e) {
			 if(tx!=null)
		  	     tx.rollback();
			 log.debug("Txn Impl : Leaving saveTemplate with Exception");
			 throw e;				 
		 }finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		 }  
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getDuplicateCheckList(int, java.lang.String)
	 */
	@Override
	public List<GroupTemplate> getDuplicateCheckList(int courseId, String templateName)
			throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.course.id = :courseId and groupTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
				if(list.size() <= 0){
					int programId = getProgrameId(courseId);
					Query query1 = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.program.id = :programId and groupTemplate.templateName = :templateName");
					query1.setInteger("programId", programId);
					query1.setString("templateName", templateName);
					list = query1.list();
					
				}
			} else {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
	/**
	 * 
	 */
	public int getProgrameId(int courseId) throws Exception {
		log.debug("inside getProgrameId");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Program p where isActive = 1 and programType.isActive = 1 and id = " + courseId);
			Program program = (Program) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getPrograme");
			if(program!= null){
				return program.getId();
			}
			else{
				return 0;
			}
		 } catch (Exception e) {
			 log.error("Error during getting program...",e);
			// session.flush();
			 //session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getDuplicateCheckList(int, java.lang.String)
	 */
	@Override
	public List<GroupTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.course.id = :courseId and groupTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
			 }
			 else if (programId != 0){
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.program.id = :programId and groupTemplate.templateName = :templateName");
				query.setInteger("programId", programId);
				query.setString("templateName", templateName);
				list = query.list();
			} else {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
//	@Override
//	public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(int certId,String certName) throws Exception {
//		log.debug("Txn Impl : Entering getTemplates ");
//		Session session = null;
//		List<CertificateDetailsTemplate> list = null;
//		try {
//			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
//			session = HibernateUtil.getSession();
//			 if (certId != 0) {
//				Query query = session.createQuery("from CertificateDetailsTemplate cd where cd.certificateId=:certId and cd.templateName=:certName");
//				query.setInteger("certificateId", certId);
//				query.setString("templateName", certName);
//				list = query.list();
//			 }
//			 
//			 log.debug("Txn Impl : Leaving getTemplates with success");
//			 return list;
//		} catch (Exception e) {
//			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
//			throw e;
//		}finally{
//			if (session != null) {
//				session.flush();
//				//session.close();
//			}
//		} 
//	}
	
	
	
	
	@Override
	public List<GroupTemplate> getGroupTemplate(int courseId, String templateName, int programId)
			throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.course.id = :courseId and groupTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
				
			} 
			 if(programId!= 0 && (list == null || list.size() <= 0)){
				 Query query1 = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.program.id = :programId and groupTemplate.templateName = :templateName");
					query1.setInteger("programId", programId);
					query1.setString("templateName", templateName);
					list = query1.list();				 
			 }
			 if(list == null || list.size()<= 0){
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
	@SuppressWarnings("unchecked")
	public List<GroupTemplate> getDuplicateCheckList(String templateName)
			throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName");

			query.setString("templateName", templateName);
			list = query.list();

			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"
					+ e.getMessage());
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.ITemplatePassword#getTemplateForNRI(java.lang.String)
	 */
	@Override
	public List<GroupTemplate> getTemplateForNRI(String templateName)
			throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			session = HibernateUtil.getSession();

			Query query = session
					.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName and course.id is null and program.id is null and programType.id is null");

			query.setString("templateName", templateName);
			list = query.list();

			return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplateForNRI with Exception"
					+ e.getMessage());
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
	}
	
	@Override
	public List<GroupTemplate> getGroupTemplateForPT(int courseId, String templateName, int programId,  int ProgramTypeId)
			throws Exception {
		Session session = null;
		List<GroupTemplate> list = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (courseId != 0) {
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.course.id = :courseId and groupTemplate.templateName = :templateName");
				query.setInteger("courseId", courseId);
				query.setString("templateName", templateName);
				list = query.list();
				
			} 
			 if(programId!= 0 && (list == null || list.size() <= 0)){
				 Query query1 = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.program.id = :programId and groupTemplate.templateName = :templateName");
					query1.setInteger("programId", programId);
					query1.setString("templateName", templateName);
					list = query1.list();				 
			 }
			 if(ProgramTypeId!= 0 && (list == null || list.size() <= 0)){
				 Query query1 = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.	programType.id = :ProgramTypeId and groupTemplate.templateName = :templateName");
					query1.setInteger("ProgramTypeId", ProgramTypeId);
					query1.setString("templateName", templateName);
					list = query1.list();				 
			 }
 			 if(list == null || list.size()<= 0){
				Query query = session.createQuery("from GroupTemplate groupTemplate where groupTemplate.templateName = :templateName");
				query.setString("templateName", templateName);
				list = query.list();
			}
			 return list;
		} catch (Exception e) {
			log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
			throw e;
		}finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		} 
	}
	
}
