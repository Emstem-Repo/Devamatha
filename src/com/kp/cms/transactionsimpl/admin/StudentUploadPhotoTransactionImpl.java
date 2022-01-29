package com.kp.cms.transactionsimpl.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentUploadPhoto;
import com.kp.cms.bo.studentLogin.StudentPhotoUpload;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;
import com.kp.cms.to.admin.StudentUploadPhotoTO;
import com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class StudentUploadPhotoTransactionImpl implements IStudentUploadPhotoTransaction{
	private static final Log log = LogFactory.getLog(StudentUploadPhotoTransactionImpl.class);
    public static volatile StudentUploadPhotoTransactionImpl studentUploadPhotoTransactionImpl = null;

    public static StudentUploadPhotoTransactionImpl getInstance() {
    	if (studentUploadPhotoTransactionImpl == null) {
    			studentUploadPhotoTransactionImpl = new StudentUploadPhotoTransactionImpl();
    			return studentUploadPhotoTransactionImpl;
    	}
    	return studentUploadPhotoTransactionImpl;
}
	
	
	@Override
	/*public int uploadStudentPhotos(
			List<StudentUploadPhoto> studentUploadPhotoBOList) throws Exception{
		log.info("entering into uploadStudentPhotos  type TransactionImpl");	
		Session session = null;
		Transaction transaction = null;
		int uploadPhoto=0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentUploadPhoto> i=studentUploadPhotoBOList.iterator();
			while(i.hasNext()){
			StudentUploadPhoto studentUploadPhoto=(StudentUploadPhoto)i.next();	
			Query query=(Query)session.createQuery("from StudentUploadPhoto s where s.applnNo=?").setInteger(0,studentUploadPhoto.getApplnNo());
			List<StudentUploadPhoto> list=query.list();
			
			if(list.size()==0){
			session.save(studentUploadPhoto);
			}else{
				Iterator<StudentUploadPhoto> i1=list.iterator();
				while(i1.hasNext()){
					StudentUploadPhoto student=(StudentUploadPhoto)i1.next();
					student.setContentType(studentUploadPhoto.getContentType());
					student.setDoc(studentUploadPhoto.getDoc());
					student.setFileName(studentUploadPhoto.getFileName());
					student.setLastModifiedDate(studentUploadPhoto.getCreatedDate());
					student.setModifiedBy(studentUploadPhoto.getCreatedBy());
					session.update(student);
				}
			}
			uploadPhoto=uploadPhoto+1;
			}
			transaction.commit();
			session.flush();
			//session.close();
			
		} catch (Exception e) {
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoTransaction", e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
			
		}
		log.info("Leaving from uploadStudentPhotos  type TransactionImpl");
		return uploadPhoto;
		
		
	}*/
	
	
	/**
	 * @param studentUploadPhotoBOList
	 * @return
	 * @throws Exception
	 */
	public int uploadStudentPhotos(
			List<StudentUploadPhoto> studentUploadPhotoBOList, String year, StudentUploadPhotoForm studentUploadPhotoForm) throws Exception{
		Session session = null;
		Transaction transaction = null;
		int uploadPhoto=0;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<StudentUploadPhoto> photoItr=studentUploadPhotoBOList.iterator();
			while(photoItr.hasNext()){
				Query query = null;
				AdmAppln admAppln = null;
				Student student = null;
				StudentUploadPhoto studentUploadPhoto=(StudentUploadPhoto)photoItr.next();
				if(studentUploadPhotoForm.getIsApplicationNo().equalsIgnoreCase("ApplicationNo")){
					query=(Query)session.createQuery("select s from Student s join s.admAppln a where a.applnNo='" + studentUploadPhoto.getApplnNo() + "' and  a.appliedYear = '" + year + "'");
					student=(Student)query.uniqueResult();
				}else{
					query=(Query)session.createQuery("from Student s where s.registerNo='" + studentUploadPhoto.getApplnNo() + "' and s.admAppln.appliedYear= '" + year + "'");
					student=(Student)query.uniqueResult();
				}
				if(student != null)
					admAppln = student.getAdmAppln();
				boolean photoPresent=false;
				if(admAppln!=null){
					Set<ApplnDoc> applnDocSet=admAppln.getApplnDocs();
				//check document empty or not
					if(applnDocSet!=null && !applnDocSet.isEmpty()){
						Iterator<ApplnDoc> applnDocItr=applnDocSet.iterator();
				
						while(applnDocItr.hasNext()){
							ApplnDoc applnDoc=(ApplnDoc)applnDocItr.next();
							// if blank photo already available,modify
							if(applnDoc.getIsPhoto()==true){
								FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+student.getId()+".jpg");
								fos.write(studentUploadPhoto.getDoc());
								fos.close();
								applnDoc.setContentType(studentUploadPhoto.getContentType());
								applnDoc.setDocument(studentUploadPhoto.getDoc());
								applnDoc.setModifiedBy(studentUploadPhoto.getCreatedBy());
								applnDoc.setLastModifiedDate(studentUploadPhoto.getLastModifiedDate());
								applnDoc.setName(admAppln.getApplnNo()+".jpg");
								session.update(applnDoc);
								photoPresent=true;
							}
						}
						// if doc present but photo not present
						if(!photoPresent){
							ApplnDoc appDoc=new ApplnDoc();
							appDoc.setContentType(studentUploadPhoto.getContentType());
							appDoc.setCreatedBy(studentUploadPhoto.getCreatedBy());
							appDoc.setDocument(studentUploadPhoto.getDoc());
							appDoc.setCreatedDate(studentUploadPhoto.getLastModifiedDate());
							appDoc.setName("Photo");
							appDoc.setIsPhoto(true);
							applnDocSet.add(appDoc);
							admAppln.setApplnDocs(applnDocSet);
							admAppln.setModifiedBy(studentUploadPhotoForm.getUserId());
							admAppln.setLastModifiedDate(new Date());
							session.saveOrUpdate(admAppln);
						}
					
					}// if empty, create a set and add photo in it and save
					else{
						applnDocSet=new HashSet<ApplnDoc>();
						ApplnDoc appDoc=new ApplnDoc();
						appDoc.setContentType(studentUploadPhoto.getContentType());
						appDoc.setCreatedBy(studentUploadPhoto.getCreatedBy());
						appDoc.setDocument(studentUploadPhoto.getDoc());
						appDoc.setCreatedDate(studentUploadPhoto.getLastModifiedDate());
						appDoc.setName("photo");
						appDoc.setIsPhoto(true);
						applnDocSet.add(appDoc);
						admAppln.setApplnDocs(applnDocSet);
						admAppln.setModifiedBy(studentUploadPhotoForm.getUserId());
						admAppln.setLastModifiedDate(new Date());
						session.saveOrUpdate(admAppln);
					}
					uploadPhoto=uploadPhoto+1;
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			
		} 
		catch (RuntimeException e) {
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoTransaction", e);
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
			
		}catch (Exception e) {
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoTransaction", e);
			throw new ApplicationException(e);
		}
		return uploadPhoto;
		
		
	}


	@Override
	public AdmAppln validateAdmAppln(String applnNo, String year)
			throws Exception {
		AdmAppln admAppln = new AdmAppln();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmAppln a where a.applnNo='" + applnNo+ "' and  appliedYear = '" + year + "'");
			admAppln = (AdmAppln) query.uniqueResult();
			return admAppln;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return admAppln;
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#uploadFinalYearStudentPhoto(com.kp.cms.bo.studentLogin.StudentPhotoUpload)
	 */
	@Override
	public boolean uploadFinalYearStudentPhoto(StudentPhotoUpload upload)throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isSaved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(upload);
			isSaved=true;
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saveFeeRefundAmount data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return isSaved;
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#getClassesFromSTudentUploadPhotos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getClassesFromStudentUploadPhotos() throws Exception {
		List<Object[]> list=null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select upload.student.classSchemewise.id,upload.student.classSchemewise.classes.name" +
					               " from StudentPhotoUpload upload where upload.isActive=1 and upload.isRejected=0 and upload.isApproved=0" +
					               " group by upload.student.classSchemewise.id");
			list =  query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return list;
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#getStudentIdByClassId(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPhotoUpload> getStudentIdByClassId(StudentUploadPhotoForm photoForm) throws Exception {
		List<StudentPhotoUpload> studentList=null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String quer="from StudentPhotoUpload upload where upload.isActive=1 and upload.isRejected=0 and upload.isApproved=0 ";
			if(photoForm.getStudentClassId()!=null && !photoForm.getStudentClassId().isEmpty()){
				quer=quer+" and upload.student.classSchemewise.id='"+photoForm.getStudentClassId()+"'";
			}
			Query query = session.createQuery(quer);
			studentList =  query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return studentList;
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#updateFinalYearStudentPhotos(com.kp.cms.forms.admin.StudentUploadPhotoForm)
	 */
	@Override
	public boolean updateFinalYearStudentPhotos(StudentUploadPhotoForm uploadPhotoForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean uploadPhoto=false;
		List<Integer> studentIdList=new ArrayList<Integer>();
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        String path=prop.getProperty("knowledgepro.final.year.student.image.path");
		try {
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				for (StudentUploadPhotoTO uploadTO : uploadPhotoForm.getUploadPhotoTOList()){
					boolean isPhotoUploaded=false;
					if(uploadTO.getChecked()!=null && uploadTO.getChecked().equalsIgnoreCase("on")){
						Query query = null;
						AdmAppln admAppln = null;
						query=(Query)session.createQuery("from Student s where s.registerNo='" + uploadTO.getRegNo() + "' and s.id= '" + uploadTO.getStudentId() + "'");
						Student	student=(Student)query.uniqueResult();
						
						if(student != null)
							admAppln = student.getAdmAppln();
						boolean photoPresent=false;
						if(admAppln!=null){
							Set<ApplnDoc> applnDocSet=admAppln.getApplnDocs();
						//check document empty or not
							if(applnDocSet!=null && !applnDocSet.isEmpty()){
								for (ApplnDoc applnDoc : applnDocSet) {
									// if blank photo already available,modify
									if(applnDoc.getIsPhoto()==true){
										FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+student.getId()+".jpg");
										fos.write(uploadTO.getDoc());
										fos.close();
										applnDoc.setContentType(uploadTO.getContentType());
										applnDoc.setDocument(uploadTO.getDoc());
										applnDoc.setModifiedBy(uploadPhotoForm.getUserId());
										applnDoc.setLastModifiedDate(new Date());
										applnDoc.setName(admAppln.getApplnNo()+".jpg");
										session.update(applnDoc);
										photoPresent=true;
										isPhotoUploaded=true;
									}
								}
								// if doc present but photo not present
								if(!photoPresent){
									ApplnDoc appDoc=new ApplnDoc();
									appDoc.setContentType(uploadTO.getContentType());
									appDoc.setCreatedBy(uploadPhotoForm.getUserId());
									appDoc.setDocument(uploadTO.getDoc());
									appDoc.setCreatedDate(new Date());
									appDoc.setName("Photo");
									appDoc.setIsPhoto(true);
									applnDocSet.add(appDoc);
									admAppln.setApplnDocs(applnDocSet);
									admAppln.setModifiedBy(uploadPhotoForm.getUserId());
									admAppln.setLastModifiedDate(new Date());
									session.saveOrUpdate(admAppln);
									isPhotoUploaded=true;
								}
							
							}// if empty, create a set and add photo in it and save
							else{
								applnDocSet=new HashSet<ApplnDoc>();
								ApplnDoc appDoc=new ApplnDoc();
								appDoc.setContentType(uploadTO.getContentType());
								appDoc.setCreatedBy(uploadPhotoForm.getUserId());
								appDoc.setDocument(uploadTO.getDoc());
								appDoc.setCreatedDate(new Date());
								appDoc.setName("photo");
								appDoc.setIsPhoto(true);
								applnDocSet.add(appDoc);
								admAppln.setApplnDocs(applnDocSet);
								admAppln.setModifiedBy(uploadPhotoForm.getUserId());
								admAppln.setLastModifiedDate(new Date());
								session.saveOrUpdate(admAppln);
								isPhotoUploaded=true;
							}
							uploadPhoto=true;
						}
					}
					if(isPhotoUploaded){
						File f = new File(path);
		                File[] filearr =  f.listFiles();
		                if(filearr.length!=0){
		                	for(int j=0;j<filearr.length;j++){
		        	   			File f3=filearr[j];
		        	   		 if(uploadTO.getStudentId().equalsIgnoreCase(removeFileExtension(f3.getName()))){
		        	   			f3.delete();
		        	   		 }
					        }
		                }
		                StudentPhotoUpload upload=getStudentPhotoUploadByStudentId(Integer.parseInt(uploadTO.getStudentId()));
		                if(upload!=null){
		                	studentIdList.add(upload.getStudent().getId());
		                	upload.setIsApproved(true);
		                	upload.setModifiedBy(uploadPhotoForm.getUserId());
		                	upload.setLastModifiedDate(new Date());
		                	session.merge(upload);
		                	uploadPhoto=true;
		                }
				}
				}
				if(!studentIdList.isEmpty()){
					uploadPhotoForm.setStudentIdList(studentIdList);
				}
			transaction.commit();		
		   }catch (Exception e) {
			log.error("Error occured in updateFinalYearStudentPhotos of updateFinalYearStudentPhotos", e);
				if ( transaction != null){
					transaction.rollback();
				}
				if (session != null){
					session.flush();
					//session.close();
				}
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return uploadPhoto;
	}

	/**
	 * @param fileName
	 * added by mahi
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
		if(null != fileName && fileName.contains("."))
		{
		return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return null;
	}
    
	/**
	 * @param studentId
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public StudentPhotoUpload getStudentPhotoUploadByStudentId(int studentId)throws Exception{
		StudentPhotoUpload studentUpload=null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String quer="from StudentPhotoUpload upload where upload.isActive=1 and upload.isRejected=0 and upload.isApproved=0 and upload.student.id="+studentId;
			Query query = session.createQuery(quer);
			studentUpload =  (StudentPhotoUpload) query.uniqueResult();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return studentUpload;
		
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#updateStudentPhotoUploadList(java.util.List)
	 */
	@Override
	public boolean updateStudentPhotoUploadList(List<StudentPhotoUpload> photoUploadList) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!photoUploadList.isEmpty()){
			  for (StudentPhotoUpload studentPhotoUpload : photoUploadList) {
				  session.merge(studentPhotoUpload);
			  }	
			  isUpdated=true;
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saveFeeRefundAmount data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return isUpdated;
	}


	/* (non-Javadoc)
	 * added by mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#getRejectedStudentPhotos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPhotoUpload> getRejectedStudentPhotos() throws Exception {
		List<StudentPhotoUpload> studentList=null;
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			String quer="from StudentPhotoUpload upload where upload.isActive=1 and upload.isRejected=1 and upload.isApproved=0 ";
			Query query = session.createQuery(quer);
			studentList =  query.list();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return studentList;
	}


	/* (non-Javadoc)
	 * Added by Mahi
	 * @see com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction#checkStudentPhotoIsApprovedOrRejected(java.lang.String)
	 */
	@Override
	public StudentPhotoUpload checkStudentPhotoIsApprovedOrRejected(String studentId)
			throws Exception {
		StudentPhotoUpload photoUpload=null;
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			String quer="from StudentPhotoUpload upload where upload.isActive=1 and upload.student.id='"+studentId+"'";
			Query query = session.createQuery(quer);
			photoUpload =  (StudentPhotoUpload) query.uniqueResult();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return photoUpload;
	}
}
