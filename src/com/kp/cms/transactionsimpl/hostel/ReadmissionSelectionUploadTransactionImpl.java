package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
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

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.ReadmissionSelectionUploadForm;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.transactions.hostel.IReadmissionSelectionUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ReadmissionSelectionUploadTransactionImpl implements IReadmissionSelectionUploadTransaction{
	private static final Log log = LogFactory.getLog(ReadmissionSelectionUploadTransactionImpl.class);
	private static volatile ReadmissionSelectionUploadTransactionImpl readmissionUploadTnxImpl = null;
	
	private ReadmissionSelectionUploadTransactionImpl() {
	}
	
	/**
	 * @return
	 */
	public static ReadmissionSelectionUploadTransactionImpl getInstance() {
		if (readmissionUploadTnxImpl == null) {
			readmissionUploadTnxImpl = new ReadmissionSelectionUploadTransactionImpl();
		}
		return readmissionUploadTnxImpl;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IReadmissionSelectionUploadTransaction#getStudentIdByStudentRegNum(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public	Map<String,Integer> getStudentIdByStudentRegNum(List<String> regNumList) throws Exception{

		Session session=null;
		Map<String,Integer> studentIdMap=new HashMap<String,Integer>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("select hl.student from HostelOnlineApplication hl where hl.student != null and hl.student.isHide = 0 and hl.student.admAppln.isCancelled=0 " +
											  " and hl.student.registerNo in (:regNumList) and hl.isActive=1");
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
	
	
	/**
	 * @param sapbo
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public boolean saveUploadOfflineApplication(Map<Integer,HostelOnlineApplication> sapbo,ReadmissionSelectionUploadForm objform,Map<String,Integer> registerNumMap) throws Exception {
		log.debug("inside uploadBlockListForHallticketOrMarkscard");
		Map<Integer,String> regNoMap=new HashMap<Integer,String>();
		List<HostelOnlineApplication> selectedStudentList=new ArrayList<HostelOnlineApplication>();
		List<HostelOnlineApplicationTo> selectedStudentListTo=new ArrayList<HostelOnlineApplicationTo>();
		//ExamBlockUnblockHallTicketBO examBo = new ExamBlockUnblockHallTicketBO();
		Iterator<Entry<String,Integer>> iterator1 = registerNumMap.entrySet().iterator();
		while (iterator1.hasNext()) {
			Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) iterator1.next();
			regNoMap.put(entry.getValue(),entry.getKey());
		}
		
		
		Session session = null;
		Transaction transaction = null;
		boolean isSaved=false;
			try {
				String str= "";
				String str1="File uploaded successfully except the following register numbers:";
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			if(sapbo!=null && !sapbo.isEmpty()){
			Iterator<Entry<Integer, HostelOnlineApplication>> iterator = sapbo.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, HostelOnlineApplication> entry = (Map.Entry<Integer, HostelOnlineApplication>) iterator.next();
				HostelOnlineApplication bo=entry.getValue();
			if(bo!=null){	
				Query query = session.createQuery("from HostelOnlineApplication e where  e.student.id=:studentId" +
													" and e.hlHostel.id=:hostelId and e.academicYear=:academicYear");
				if( bo.getStudent()!= null){
					query.setInteger("studentId", bo.getStudent().getId());
				}
				if(bo.getHlHostel()!=null ){
					query.setInteger("hostelId", bo.getHlHostel().getId());
				}
				if(objform.getAcademicYear1()!=null && !objform.getAcademicYear1().isEmpty()){
					query.setInteger("academicYear", Integer.parseInt(objform.getAcademicYear1()));
				}
				
				
				HostelOnlineApplication examBo = (HostelOnlineApplication)query.uniqueResult();
				if(examBo !=null){
					if(bo.getSelectedRoomType()!=null){
						examBo.setLastModifiedDate(new Date());
						examBo.setModifiedBy(objform.getUserId());
						examBo.setSelectedRoomType(bo.getSelectedRoomType());
						examBo.setIsSelected(true);
						session.update(examBo);
						isSaved=true;
						selectedStudentList.add(examBo);
					}
				}else{
					
					if(str==null || str.isEmpty()){
						str=str+removeFileExtension(regNoMap.get((bo.getStudent().getId())).toString());
					}else{
						str=str+","+removeFileExtension(regNoMap.get(bo.getStudent().getId()).toString());
					}
					//isDupRegNo=true;
					
				}
			}
		}
			
			if(isSaved){
				objform.setIsRegNosUpload(true);
				objform.setNotUploadRegNos(str);
				objform.setNotUploadRegNosMsg(str1);
				if(selectedStudentList!=null && !selectedStudentList.isEmpty()){
					Iterator<HostelOnlineApplication> itr = selectedStudentList.iterator();
					while (itr.hasNext()) {
						HostelOnlineApplication bo = (HostelOnlineApplication) itr.next();
						HostelOnlineApplicationTo to=new HostelOnlineApplicationTo();
						 to.setStudentRegNo(bo.getStudent().getRegisterNo());
						 to.setStudentname(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
						 to.setHlHostelName(bo.getHlHostel().getName());
						to.setSelectedRoomType(bo.getSelectedRoomType().getName());
						to.setMobileNo1(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo1());
						to.setMobileNo2(bo.getStudent().getAdmAppln().getPersonalData().getMobileNo2());
						to.setEmail(bo.getStudent().getAdmAppln().getPersonalData().getUniversityEmail());
						selectedStudentListTo.add(to);
					}
					objform.setSelectedStudentList(selectedStudentListTo);
					
				}
			}
			transaction.commit();
	}else{
				isSaved=false;
			}
			session.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return isSaved;
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
			if(null != fileName && fileName.contains("."))
			{
			return fileName.substring(0, fileName.lastIndexOf("."));
			}
		return fileName;
	}
}
