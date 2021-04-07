package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IDownloadHostelImagesTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadHostelImagesTxnImpl implements IDownloadHostelImagesTransaction{
	private static final Log log = LogFactory.getLog(DownloadHostelImagesTxnImpl.class);
	public static volatile DownloadHostelImagesTxnImpl impl = null;
	public static DownloadHostelImagesTxnImpl getInstance(){
		if(impl==null){
			impl = new DownloadHostelImagesTxnImpl();
			return impl;
		}
		return impl;
	}
	@Override
	public int getImages(int year, int hostelId, int blockId, int unitId)
			throws Exception {
		Session session = null;
		int count = 0;
		try{
			session = HibernateUtil.getSession();
			StringBuffer HQL_QUERY = new StringBuffer(); 
			 HQL_QUERY.append(	" select count(doc) from HlAdmissionBo bo left join bo.studentId.admAppln.applnDocs doc"+
								" where bo.isActive = 1" +
								" and bo.academicYear ="+year +
								" and bo.hostelId ="+hostelId );
								if(blockId !=0){
									HQL_QUERY = HQL_QUERY.append(" and bo.roomId.hlBlock ="+blockId);
								}
								if(unitId!=0){
									HQL_QUERY = HQL_QUERY.append(" and bo.roomId.hlBlock ="+unitId);
								}
									HQL_QUERY = HQL_QUERY.append(" and bo.roomId.isActive = 1" +
															 	 " and bo.studentId.isAdmitted = 1" +
															 	 " and bo.studentId.admAppln.isCancelled =0" +
															 	 " and doc.isPhoto=1 and  (doc.document <> '' or doc.document <> null)");
			Query query = session.createQuery(HQL_QUERY.toString());
			count = Integer.parseInt(query.uniqueResult().toString());
			session.flush();
		}catch (Exception e) {
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return count;
	}
	@Override
	public List<ApplnDoc> getImages(int year, int hostelId, int blockId,
			int unitId, int page, int pagesize) throws Exception {
		Session session  = null;
		 List<ApplnDoc> applnDocs;
		 try{
			 session = HibernateUtil.getSession();
				StringBuffer HQL_QUERY = new StringBuffer(); 
				 HQL_QUERY.append(	" select doc from HlAdmissionBo bo left join bo.studentId.admAppln.applnDocs doc"+
									" where bo.isActive = 1" +
									" and bo.academicYear ="+year +
									" and bo.hostelId ="+hostelId );
									if(blockId !=0){
										HQL_QUERY = HQL_QUERY.append(" and bo.roomId.hlBlock ="+blockId);
									}
									if(unitId!=0){
										HQL_QUERY = HQL_QUERY.append(" and bo.roomId.hlBlock ="+unitId);
									}
										HQL_QUERY = HQL_QUERY.append( //" and bo.roomId.isActive = 1" +
																 	 " and bo.studentId.isAdmitted = 1" +
																 	 " and bo.studentId.admAppln.isCancelled =0" +
																 	 " and doc.isPhoto=1 and  (doc.document <> '' or doc.document <> null)");
				Query query = session.createQuery(HQL_QUERY.toString());
				query.setFirstResult((page - 1) * pagesize); 
				query.setMaxResults(pagesize); 
				applnDocs = query.list();
				session.flush();
		 }catch (Exception e) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		return applnDocs;
	}
	
}
