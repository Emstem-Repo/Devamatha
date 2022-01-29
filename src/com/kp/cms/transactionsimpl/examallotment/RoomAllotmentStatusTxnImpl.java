package com.kp.cms.transactionsimpl.examallotment;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.examallotment.GroupClasses;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class RoomAllotmentStatusTxnImpl implements IRoomAllotmentStatusTransaction {

	private static final Log log = LogFactory.getLog(RoomAllotmentStatusTxnImpl.class);
	
	public static volatile RoomAllotmentStatusTxnImpl roomAllotmentStatusTxnImpl=null;
	
	/**
	 * @return
	 */
	public static RoomAllotmentStatusTxnImpl getInstance(){
		if(roomAllotmentStatusTxnImpl==null){
			roomAllotmentStatusTxnImpl=new RoomAllotmentStatusTxnImpl();
		}
			return roomAllotmentStatusTxnImpl;
	}
	
	public RoomAllotmentStatusTxnImpl() {
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction#getExamNameByAcademicYear(int)
	 */
	@Override
	public List<ExamDefinitionBO> getExamNameByAcademicYear(int year)
			throws Exception {
		Session session = null;
		List<ExamDefinitionBO> examDefinitionList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamDefinitionBO e where e.delIsActive = 1 and e.isActive = 1 and e.academicYear="+year;
			Query query =  session.createQuery(hqlQuery);
			examDefinitionList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return examDefinitionList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction#getCycleByMidOrEnd(java.lang.String)
	 */
	@Override
	public List<ExamRoomAllotmentCycle> getCycleByMidOrEnd(String midOrEnd)
			throws Exception {
		Session session = null;
		List<ExamRoomAllotmentCycle> allotmentCycleList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamRoomAllotmentCycle cycle where cycle.isActive=1 and cycle.midOrEnd='"+midOrEnd+"' order by cycle.cycle";
			Query query =  session.createQuery(hqlQuery);
			allotmentCycleList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return allotmentCycleList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction#getRoomNoByWorkLocationId(int)
	 */
	@Override
	public List<RoomMaster> getRoomNoByWorkLocationId(int workLocationId)
			throws Exception {
		Session session = null;
		List<RoomMaster> roomMasterList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from RoomMaster room where room.blockId.locationId.id="+workLocationId+" and room.isActive=1 " +
							" order by room.blockId.blockOrder,room.floor,room.roomNo";
			Query query =  session.createQuery(hqlQuery);
			roomMasterList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return roomMasterList;
	}

	@Override
	public List<ExamRoomAllotmentDetails> getStudentDetailsByRoom(
			RoomAllotmentStatusForm allotmentStatusForm) throws Exception {
		Session session = null;
		List<ExamRoomAllotmentDetails> allotmentDetailsList=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery =" select roomDetails from ExamRoomAllotmentDetails roomDetails " +
					         " where roomDetails.isActive=1 " +
					         " and roomDetails.allotment.examDefinition.id='"+allotmentStatusForm.getExamid()+"'" +
					         " and roomDetails.allotment.midOrEndSem='"+allotmentStatusForm.getMidEndSem()+"'" +
					         " and roomDetails.allotment.room.id='"+allotmentStatusForm.getRoomNo()+"' and roomDetails.allotment.isActive=1" +
					         " and roomDetails.allotment.room.blockId.locationId='"+allotmentStatusForm.getCampusName()+"'";
			if(allotmentStatusForm.getCycleId()!=null && !allotmentStatusForm.getCycleId().isEmpty()){
				hqlQuery=hqlQuery+" and roomDetails.allotment.cycle.id="+Integer.parseInt(allotmentStatusForm.getCycleId());
			}
			if(allotmentStatusForm.getAllottedDate()!=null && !allotmentStatusForm.getAllottedDate().isEmpty()){
				hqlQuery=hqlQuery+" and roomDetails.allotment.date='"+CommonUtil.ConvertStringToSQLDate(allotmentStatusForm.getAllottedDate())+"'";
			}
			if(allotmentStatusForm.getSessionId()!=null && !allotmentStatusForm.getSessionId().isEmpty()){
				hqlQuery=hqlQuery+" and roomDetails.allotment.examinationSessions.id="+Integer.parseInt(allotmentStatusForm.getSessionId());
			}
		      hqlQuery=hqlQuery+" order by roomDetails.columnNO,roomDetails.rowNO,roomDetails.seatingPosition";
			Query query =  session.createQuery(hqlQuery);
			allotmentDetailsList = query.list();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return allotmentDetailsList;
	}

	@Override
	public ExamRoomAllotmentDetails checkStudentAlreadyAllocatedByRegNo(String registerNo,RoomAllotmentStatusForm allotmentStatusForm)
			throws Exception {
		Session session = null;
		ExamRoomAllotmentDetails allotmentDetails=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamRoomAllotmentDetails roomDetails where roomDetails.isActive=1" +
					" and roomDetails.allotment.isActive=1 " +
					" and roomDetails.allotment.examDefinition.id='"+allotmentStatusForm.getExamid()+"'" +
					" and roomDetails.student.registerNo='"+registerNo+"'"+
					" and roomDetails.allotment.midOrEndSem='"+allotmentStatusForm.getMidEndSem()+"'" ;
			if(allotmentStatusForm.getCycleId()!=null && !allotmentStatusForm.getCycleId().isEmpty()){
				hqlQuery = hqlQuery + " and roomDetails.allotment.cycle="+Integer.parseInt(allotmentStatusForm.getCycleId());
			}
			if(allotmentStatusForm.getAllottedDate()!=null && !allotmentStatusForm.getAllottedDate().isEmpty()){
				hqlQuery=hqlQuery+" and roomDetails.allotment.date='"+CommonUtil.ConvertStringToSQLDate(allotmentStatusForm.getAllottedDate())+"'";
			}
			if(allotmentStatusForm.getSessionId()!=null && !allotmentStatusForm.getSessionId().isEmpty()){
				hqlQuery=hqlQuery+" and roomDetails.allotment.examinationSessions.id="+Integer.parseInt(allotmentStatusForm.getSessionId());
			}
			Query query =  session.createQuery(hqlQuery);
			allotmentDetails = (ExamRoomAllotmentDetails) query.uniqueResult();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return allotmentDetails;
	}

	@Override
	public Student getStudentDetailsByRegNo(String registerNo) throws Exception {
		Session session = null;
		Student student=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from Student student where student.isActive=1 and student.registerNo='"+registerNo+"'" +
					          " and (student.isHide=0 or student.isHide is  null)";
			Query query =  session.createQuery(hqlQuery);
			student = (Student) query.uniqueResult();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return student;
	}

	@Override
	public boolean updateStudentDetailsForAllotment(
			List<ExamRoomAllotmentDetails> allotmentDetailsList)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean updated=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!allotmentDetailsList.isEmpty()){
			for (ExamRoomAllotmentDetails allotmentDetails : allotmentDetailsList) {
			    session.saveOrUpdate(allotmentDetails);
			}
			updated=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return updated;
	}

	@Override
	public ExamRoomAllotmentDetails getDataFromOrigRegNo(RoomAllotmentStatusDetailsTo statusDetailsTo ,
			RoomAllotmentStatusForm allotmentStatusForm) throws Exception {
		Session session = null;
		ExamRoomAllotmentDetails allotmentDetails=null;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = "from ExamRoomAllotmentDetails roomDetails where roomDetails.isActive=1" +
					" and roomDetails.allotment.isActive=1 " +
					" and roomDetails.allotment.examDefinition.id='"+allotmentStatusForm.getExamid()+"'"+
					" and roomDetails.allotment.midOrEndSem='"+allotmentStatusForm.getMidEndSem()+"'" ;
					if(statusDetailsTo.getOrigRegisterNo()==null){
						hqlQuery=hqlQuery+" and roomDetails.student="+null;	
					}else{
						hqlQuery=hqlQuery+" and roomDetails.student.registerNo='"+statusDetailsTo.getOrigRegisterNo()+"'";	
					}
					if(allotmentStatusForm.getCycleId()!=null && !allotmentStatusForm.getCycleId().isEmpty()){
						hqlQuery=hqlQuery+" and roomDetails.allotment.cycle.id='"+allotmentStatusForm.getCycleId()+"'";
					}
					if(allotmentStatusForm.getAllottedDate()!=null && !allotmentStatusForm.getAllottedDate().isEmpty()){
						hqlQuery=hqlQuery+" and roomDetails.allotment.date='"+CommonUtil.ConvertStringToSQLDate(allotmentStatusForm.getAllottedDate())+"'";
					}
					if(allotmentStatusForm.getSessionId()!=null && !allotmentStatusForm.getSessionId().isEmpty()){
						hqlQuery=hqlQuery+" and roomDetails.allotment.examinationSessions.id="+Integer.parseInt(allotmentStatusForm.getSessionId());
					}
					hqlQuery=hqlQuery+" and roomDetails.columnNO="+statusDetailsTo.getColumnNO()+" " +
							         " and roomDetails.rowNO="+statusDetailsTo.getRowNO()+" " +
							         " and roomDetails.seatingPosition="+statusDetailsTo.getSeatingPosition();
					if(statusDetailsTo.getRoomNo()!=null && !statusDetailsTo.getRoomNo().isEmpty()){
						hqlQuery=hqlQuery+" and roomDetails.allotment.room.id="+Integer.parseInt(statusDetailsTo.getRoomNo());
					}
			Query query =  session.createQuery(hqlQuery);
			allotmentDetails = (ExamRoomAllotmentDetails) query.uniqueResult();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		
		return allotmentDetails;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction#getRoomById(java.lang.String)
	 */
	@Override
	public List<RoomEndMidSemRows> getRoomById(int roomId,String endMidSem) throws Exception {
		Session session = null;
		List<RoomEndMidSemRows> endMidSemRows = new ArrayList<RoomEndMidSemRows>();
		try{
			session = HibernateUtil.getSession();
			String str = "select endMidDetails from RoomMaster room " +
						 " join room.endMidSemRows endMidDetails"+
						 " where room.id="+roomId+" and room.isActive=1"+
						 " and endMidDetails.endMidSem='"+endMidSem+"'"+
						 " and endMidDetails.isActive=1" +
						 " order by endMidDetails.columnNumber";
			Query query = session.createQuery(str);
			endMidSemRows =  query.list();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}finally{
			if(session!=null)
				session.flush();
		}
		return endMidSemRows;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction#getRoomAllotment(com.kp.cms.forms.examallotment.RoomAllotmentStatusForm)
	 */
	@Override
	public ExamRoomAllotment getRoomAllotment( RoomAllotmentStatusForm allotmentStatusForm) throws Exception {
		Session session= null;
		ExamRoomAllotment allotment = new ExamRoomAllotment();
		try{
			session = HibernateUtil.getSession();
			String str = "from ExamRoomAllotment roomAllotment "+
						" where roomAllotment.examDefinition.id="+allotmentStatusForm.getExamid()+
						" and roomAllotment.room.id="+allotmentStatusForm.getRoomNo()+
						" and roomAllotment.midOrEndSem='"+allotmentStatusForm.getMidEndSem()+"'" ;
						if(allotmentStatusForm.getCycleId()!=null && !allotmentStatusForm.getCycleId().isEmpty()){
							str = str + " and roomAllotment.cycle="+Integer.parseInt(allotmentStatusForm.getCycleId());
						}
						if(allotmentStatusForm.getAllottedDate()!=null && !allotmentStatusForm.getAllottedDate().isEmpty()){
							str=str+" and roomAllotment.date='"+CommonUtil.ConvertStringToSQLDate(allotmentStatusForm.getAllottedDate())+"'";
						}
						if(allotmentStatusForm.getSessionId()!=null && !allotmentStatusForm.getSessionId().isEmpty()){
							str=str+" and roomAllotment.examinationSessions.id="+Integer.parseInt(allotmentStatusForm.getSessionId());
						}
						str=str+" and roomAllotment.isActive =1";
			Query query = session.createQuery(str);
			allotment = (ExamRoomAllotment) query.uniqueResult();
			
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}
		return allotment;
	}

	@Override
	public int getMaxRowCount(int roomId, String midEndSem) throws Exception {
		Session session = null;
		int maxRowCount = 0;
		try{
			session= HibernateUtil.getSession();
			String str = "SELECT max(cast(room_master_column_details.no_of_rows as SIGNED INTEGER))" +
					" FROM room_master_column_details"+
					" join room_master ON room_master_column_details.room_master_id = room_master.id" +
					" where room_master.id="+roomId+
					" and room_master_column_details.end_mid_sem ='"+midEndSem+"'"+
					" and room_master.is_active =1" +
					" and room_master_column_details.is_active =1";
			Query query = session.createSQLQuery(str);
		BigInteger	bigInteger = (BigInteger) query.uniqueResult();
		maxRowCount = bigInteger.intValue();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
				throw new ApplicationException(e);
			}
		}
		
		return maxRowCount;
	}
	
}
