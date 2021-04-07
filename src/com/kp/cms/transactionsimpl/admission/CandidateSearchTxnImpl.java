package com.kp.cms.transactionsimpl.admission;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ICandidateSearchTxnImpl;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author kalyan.c
 * DAO Class for Admission Report
 */
public class CandidateSearchTxnImpl implements ICandidateSearchTxnImpl{
	private static volatile CandidateSearchTxnImpl self=null;
	private static Log log = LogFactory.getLog(CandidateSearchTxnImpl.class);
//	private static int saveCount = 0;
	
	/**
	 * @return
	 * This method will return the instance of this classes
	 */
	public static CandidateSearchTxnImpl getInstance(){
		if(self==null)
			self= new CandidateSearchTxnImpl();
		return self;
	}
	
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ICandidateSearchTxnImpl#getStudentSearch(java.lang.String)
	 * The method will return the list of the candidates
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AdmAppln> getStudentSearch(String searchCriteria) throws ApplicationException {
		Session session = null;
		List<AdmAppln> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			 studentSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student search results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.clear();
				//session.close();
			}
		}
		return studentSearchResult;
	}

	@SuppressWarnings("unchecked")
	public List<ConfigReportsColumn> getSelectedColumns() throws ApplicationException {
		Session session = null;
		List<ConfigReportsColumn> studentSearchResult = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();			
			session = HibernateUtil.getSession();
			String searchCriteria = "from ConfigReportsColumn c where c.reportName = 'Admission Report' and showColumn = 1";
			 studentSearchResult = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the student search results  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return studentSearchResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigReportsColumn> getColumnsReportList() throws Exception {
		Session session = null;
		List<ConfigReportsColumn> columnsList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();		
			session = HibernateUtil.getSession();
			String searchCriteria = "from ConfigReportsColumn c where c.reportName = 'Admission Report'";
			columnsList = session.createQuery(searchCriteria).list();
			
		} catch (Exception e) {
			log.error("error while getting the getColumnsReportList  ",e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return columnsList;
	}

		public ArrayList<Object[]>  executeAdmissionReport(String searchCriteria,Connection conn){
				
		        ArrayList<Object[]> recorsList = new ArrayList<Object[]>(); 
			    try
			    {
			    	
			        Statement s = conn.createStatement ();
			        s.executeQuery (searchCriteria);
			        ResultSet rs = s.getResultSet();
			        ResultSetMetaData metaData = rs.getMetaData(); 
			        int columns = metaData.getColumnCount(); 
			        while (rs.next()) { 
			                Object[] object = new Object[columns];
			                for (int i = 1; i <= columns; i++) { 
			                        Object value = rs.getObject(i);
			                        object[i - 1] = value;
		//	                        record.add(value); 
			                } 
			                recorsList.add(object); 
			        } 
			        s.close();
					
			    }
			    catch (Exception e)
			    {
			        System.err.println ("Cannot connect to database server");
			    }
			    finally
			    {
			        
			    }
				return recorsList;
			
			}
		
		/**
		 * 
		 * @return
		 * @throws ApplicationException
		 */
		public Integer getMaxColumnNo() throws ApplicationException {
			Session session = null;
			List<ConfigReportsColumn> studentSearchResult = null;
			Integer maxNo = 0;
			try {
				session = HibernateUtil.getSession();
				String searchCriteria = "select max(position) from ConfigReportsColumn c where showColumn = 1";
				Query query = session.createQuery(searchCriteria);
				
 				maxNo = (Integer) query.uniqueResult();
				
			} catch (Exception e) {
				log.error("error while getting the student search results  ",e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			return maxNo;
		}



		@Override
		public TreeMap<String, String> getDocTypesMap(String q)
				throws Exception {
			log.debug("inside getDocTypesMap");
			Session session = null;
			TreeMap<String, String> docTypeMap = new TreeMap<String, String>();
			//DocType docType;
			try {
				
				session = HibernateUtil.getSession();
				Query query = session.createQuery(q);
				List<DocType> list = query.list();
				//docType = new DocType();
				if(list!= null && list.size() > 0){
					Iterator<DocType> itr = list.iterator();
					while (itr.hasNext()) {
						DocType docType = (DocType) itr.next();
						docTypeMap.put(docType.getName().trim(), null );
					}
				}
				
				session.flush();
				
				log.debug("leaving getDocTypesMap");
				return docTypeMap;
			 } catch (Exception e) {
				 log.error("Error during getDocTypes...",e);
				 session.flush();
				 //session.close();
				 throw  new ApplicationException(e);
			 }
		}



		@Override
		public TreeMap<String, String> getInterviewTypeHeading(String intQuery)
				throws Exception {
			log.debug("inside getDocTypesMap");
			Session session = null;
			TreeMap<String, String> docTypeMap = new TreeMap<String, String>();
			try {
				session = HibernateUtil.getSession();
				Query query = session.createQuery(intQuery);
				List<InterviewProgramCourse> list = query.list();
				//docType = new DocType();
				if(list!= null && list.size() > 0){
					Iterator<InterviewProgramCourse> itr = list.iterator();
					while (itr.hasNext()) {
						InterviewProgramCourse i = (InterviewProgramCourse) itr.next();
						docTypeMap.put(i.getName().trim(), null );
					}
				}
				session.flush();
				log.debug("leaving getDocTypesMap");
				return docTypeMap;
			 } catch (Exception e) {
				 log.error("Error during getDocTypes...",e);
				 session.flush();
				 //session.close();
				 throw  new ApplicationException(e);
			 }
		}



		@Override
		public TreeMap<String, String> getPreRequisteMap(
				String preReqQuery) throws Exception {
			log.debug("inside getPreRequisteMap");
			Session session = null;
			TreeMap<String, String> preReqMap = new TreeMap<String, String>();
			try {
				
				session = HibernateUtil.getSession();
				Query query = session.createQuery(preReqQuery);
				List<Prerequisite> list = query.list();
				if(list!= null && list.size() > 0){
					Iterator<Prerequisite> itr = list.iterator();
					while (itr.hasNext()) {
						Prerequisite docType = (Prerequisite) itr.next();
						preReqMap.put(docType.getName().trim(), null );
					}
				}
				
				session.flush();
				
				log.debug("leaving getPreRequisteMap");
				return preReqMap;
			 } catch (Exception e) {
				 log.error("Error during getPreRequisteMap...",e);
				 session.flush();
				 //session.close();
				 throw  new ApplicationException(e);
			 }
		}



		/* (non-Javadoc)
		 * returning the list of students who have photos
		 * @see com.kp.cms.transactions.admission.ICandidateSearchTxnImpl#getStudentListWithPhotos(java.util.List)
		 */
		@Override
		public List<Integer> getStudentListWithPhotos(List<Integer> admIdsForSearch)
			 {
			Session session = null;
			try {
				
				session = HibernateUtil.getSession();
				Query query = session.createQuery("select a.admAppln.id " +
						" from ApplnDoc a where a.isPhoto=1 and a.admAppln.id in (:admIds)" +
						" and (a.document!='' or a.document!=null)").setParameterList("admIds", admIdsForSearch);
				List<Integer> admIds = query.list();
				
				session.flush();
				
				log.debug("leaving getPreRequisteMap");
				return admIds;
			 } catch (Exception e) {
				 log.error("Error during getPreRequisteMap...",e);
				 session.flush();
				 //session.close();
				 return null;
			 }
		
		}
		

}