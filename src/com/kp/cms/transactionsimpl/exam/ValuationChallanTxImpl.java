package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamRemunerationDetails;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ValuationChallanNumber;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.bo.exam.ValuatorMeetingChargesBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuationChallanForm;
import com.kp.cms.to.exam.ValuationChallanDetailsTO;
import com.kp.cms.transactions.exam.IValuationChallanTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ValuationChallanTxImpl implements IValuationChallanTransaction{

	/**
	 * Singleton object of NewExamMarksEntryTransactionImpl
	 */
	private static volatile ValuationChallanTxImpl impl = null;
	private static final Log log = LogFactory.getLog(ValuationChallanTxImpl.class);
	private ValuationChallanTxImpl() {
		
	}
	/**
	 * return singleton object of NewExamMarksEntryTransactionImpl.
	 * @return
	 */
	public static ValuationChallanTxImpl getInstance() {
		if (impl == null) {
			impl = new ValuationChallanTxImpl();
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmployeeMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getEmployeeMap(HttpSession s) throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<ExamValidationDetails> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ExamValidationDetails e where e.isActive=1 group by e.users.id,e.otherEmployee");
			list= query.list();
			if(list!=null){
				Iterator<ExamValidationDetails> iterator=list.iterator();
				while (iterator.hasNext()) {
					ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
					if(examValidationDetails.getUsers() != null){
						Users user=examValidationDetails.getUsers();
						if(user.getEmployee() != null && user.getEmployee().getFirstName() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName()+"("+user.getEmployee().getDepartment().getName()+")");
						}else if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
							map.put(user.getId(), user.getEmployee().getFirstName());
						}else if(user.getGuest() != null && user.getGuest().getFirstName() != null){
							map.put(user.getId(), user.getGuest().getFirstName());
						}else if(user.getDepartment() != null && user.getDepartment().getName() != null ){
							map.put(user.getId(), user.getUserName().toUpperCase()+"("+user.getDepartment().getName()+")");
						}else{
							map.put(user.getId(), user.getUserName().toUpperCase());
						}
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
			s.setAttribute("evaluatorsMap", map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getValuatorDetails(com.kp.cms.forms.exam.ValuationChallanForm)
	 */
	@Override
	public List<ExamValidationDetails> getValuatorDetails(ValuationChallanForm valuationChallanForm)throws Exception {
		Session session = null;
		List<ExamValidationDetails> list = null;
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "select e from ExamValidationDetails e join e.answerScripts ans where e.isActive=1";
			if(valuationChallanForm.getEmployeeId() != null && !valuationChallanForm.getEmployeeId().isEmpty() && !valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
				hqlQuery = hqlQuery + " and e.users.id="+valuationChallanForm.getEmployeeId();
				valuationChallanForm.setPrintExternal(false);
			}
			if(valuationChallanForm.getOtherEmpId() != null && !valuationChallanForm.getOtherEmpId().isEmpty()){
				hqlQuery = hqlQuery + " and e.examValuators.id="+valuationChallanForm.getOtherEmpId();
				valuationChallanForm.setPrintExternal(true);
			}
			if(valuationChallanForm.getStartDate() != null && !valuationChallanForm.getStartDate().isEmpty() && valuationChallanForm.getEndDate() != null && !valuationChallanForm.getEndDate().isEmpty()){
				hqlQuery = hqlQuery + " and Date(ans.issueDate) between '"+CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getStartDate())+"' and '"+ CommonUtil.ConvertStringToSQLDate(valuationChallanForm.getEndDate())+"' ";
			}
			hqlQuery = hqlQuery + " group by e.id";
			Query query=session.createQuery(hqlQuery);
			list= query.list();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			list = new ArrayList<ExamValidationDetails>();
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#saveDetails(com.kp.cms.bo.exam.ExamValuationRemuneration)
	 */
	@Override
	public boolean saveDetails(ExamValuationRemuneration remuneration,ValuationChallanForm valuationChallanForm )throws Exception {
		Session session = null;
		boolean save=false;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			ValuationChallanNumber challanNumber = (ValuationChallanNumber)session.createQuery("select v from ValuationChallanNumber v where v.isActive=1").uniqueResult();
			if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getId() != 0){
				if(challanNumber != null && challanNumber.getExternalCurrentBillNo() != null){
					remuneration.setBillNumber(Integer.parseInt(challanNumber.getExternalCurrentBillNo()));
					valuationChallanForm.setPrintExternal(true);
					valuationChallanForm.setChallanNo(challanNumber.getExternalCurrentBillNo());
				}
			}
			else if(remuneration.getUsers() != null && remuneration.getUsers().getId() != 0){
				if(challanNumber != null && challanNumber.getCurrentBillNo() != null){
					remuneration.setBillNumber(Integer.parseInt(challanNumber.getCurrentBillNo()));
					valuationChallanForm.setPrintExternal(false);
					valuationChallanForm.setChallanNo(challanNumber.getCurrentBillNo());
				}
			}
			session.save(remuneration);
			int currentBillNumber=0;
			if(remuneration.getExamValuators() != null && remuneration.getExamValuators().getId() != 0){
			    currentBillNumber = Integer.parseInt(challanNumber.getExternalCurrentBillNo());
				currentBillNumber = currentBillNumber +1;
				challanNumber.setExternalCurrentBillNo(String.valueOf(currentBillNumber));
			}else if(remuneration.getUsers() != null && remuneration.getUsers().getId() != 0){
				currentBillNumber = Integer.parseInt(challanNumber.getCurrentBillNo());
				currentBillNumber = currentBillNumber +1;
				challanNumber.setCurrentBillNo(String.valueOf(currentBillNumber));
			}
			challanNumber.setLastModifiedDate(new Date());
			challanNumber.setModifiedBy(remuneration.getModifiedBy());
			session.update(challanNumber);
			if(valuationChallanForm.getAnswerScripts() != null){
				Iterator<ExamValuationAnswerScript> iterator = valuationChallanForm.getAnswerScripts().iterator();
				while (iterator.hasNext()) {
					ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator.next();
					examValuationAnswerScript.setChallanGenerated(true);
					session.update(examValuationAnswerScript);
				}
			}
			save=true;
			transaction.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
		}
		return save;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getSubjectGroupForSubject(int)
	 */
	public Integer getSubjectGroupForSubject(int id) throws Exception{
		Session session = null;
		Integer programTypeId = 0;
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "select sub.subjectGroup.course.program.programType.id from SubjectGroupSubjects sub where sub.isActive=1 and sub.subjectGroup.isActive=1 and sub.subject.isActive=1 and sub.subject.id="+id+" group by sub.subject.id";
			Query query=session.createQuery(hqlQuery);
			programTypeId = (Integer)query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return programTypeId;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getValuatorChargerPerScript(int)
	 */
	public ValuatorChargesBo getValuatorChargerPerScript(int id) throws Exception{
		Session session = null;
		ValuatorChargesBo valuatorChargesBo = new ValuatorChargesBo();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "from ValuatorChargesBo v where v.isActive=1 and v.programType.id="+id;
			Query query=session.createQuery(hqlQuery);
			valuatorChargesBo = (ValuatorChargesBo)query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return valuatorChargesBo;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getValuatorMeetingCharges()
	 */
	@Override
	public ValuatorMeetingChargesBo getValuatorMeetingCharges()	throws Exception {
		Session session = null;
		ValuatorMeetingChargesBo meetingChargesBo = new ValuatorMeetingChargesBo();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "from ValuatorMeetingChargesBo v where v.isActive=1";
			Query query=session.createQuery(hqlQuery);
			meetingChargesBo = (ValuatorMeetingChargesBo)query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return meetingChargesBo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getRemunerationDetails(com.kp.cms.forms.exam.ValuationChallanForm)
	 */
	@Override
	public ExamValuationRemuneration getRemunerationDetails(
			ValuationChallanForm valuationChallanForm) throws Exception {
		Session session = null;
		ExamValuationRemuneration examValuationRemuneration=new ExamValuationRemuneration();
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			if(valuationChallanForm.getRemunerationId() != 0){
				String hqlQuery = "from ExamValuationRemuneration e where e.isActive=1 and e.id="+valuationChallanForm.getRemunerationId();
				examValuationRemuneration = (ExamValuationRemuneration)session.createQuery(hqlQuery).uniqueResult();
			}
			session.flush();
//			session.close();
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return examValuationRemuneration;
			
		}
		return examValuationRemuneration;
	}
	@Override
	public boolean saveChallanDetails(ValuationChallanForm valuationChallanForm)
			throws Exception {
		Session session = null;
		boolean save=false;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Map<Integer,ValuationChallanDetailsTO> valuationDetailsMap=new HashMap<Integer,ValuationChallanDetailsTO>();
			List<ValuationChallanDetailsTO> valuationDetailsList=valuationChallanForm.getValuationDetailsList();
			if(valuationDetailsList!=null && !valuationDetailsList.isEmpty()){
				Iterator<ValuationChallanDetailsTO> iterator1 = valuationDetailsList.iterator();
				while (iterator1.hasNext()) {
					ValuationChallanDetailsTO valuationChallanDetailsTO = (ValuationChallanDetailsTO)iterator1.next();
					valuationDetailsMap.put(valuationChallanDetailsTO.getRemunerationDetailId(), valuationChallanDetailsTO);
				}
			}
			if(valuationChallanForm.getRemunerationId() !=0){
				String hqlQuery = "from ExamValuationRemuneration e where e.isActive=1 and e.id="+valuationChallanForm.getRemunerationId();
				
				ExamValuationRemuneration remuneration = (ExamValuationRemuneration)session.createQuery(hqlQuery).uniqueResult();
				
				
				if(valuationChallanForm.getTotalBoardMeetings() != null && !valuationChallanForm.getTotalBoardMeetings().trim().isEmpty()){
					remuneration.setBoardMeetings(Integer.parseInt(valuationChallanForm.getTotalBoardMeetings()));
				}
				if(valuationChallanForm.getTotalNoOfConveyance() != null && !valuationChallanForm.getTotalNoOfConveyance().trim().isEmpty()){
					remuneration.setConveyanceCharges(Integer.parseInt(valuationChallanForm.getTotalNoOfConveyance()));
				}
					remuneration.setAnyOther(valuationChallanForm.getAnyOther());
				if(valuationChallanForm.getOtherCost() != null && !valuationChallanForm.getOtherCost().trim().isEmpty()){
					remuneration.setAnyOtherCost(new java.math.BigDecimal(valuationChallanForm.getOtherCost()));
				}
				if(valuationChallanForm.getTa() != null && !valuationChallanForm.getTa().trim().isEmpty()){
					remuneration.setTa(new java.math.BigDecimal(valuationChallanForm.getTa()));
				}
				if(valuationChallanForm.getDa() != null && !valuationChallanForm.getDa().trim().isEmpty()){
					remuneration.setDa(new java.math.BigDecimal(valuationChallanForm.getDa()));
				}
				remuneration.setLastModifiedDate(new Date());
				remuneration.setModifiedBy(valuationChallanForm.getUserId());
				if(valuationDetailsMap!=null && !valuationDetailsMap.isEmpty()){
				Set<ExamRemunerationDetails> remunerationDetailsSet=remuneration.getExamRemunerationDetails();
					if(remunerationDetailsSet!=null && !remunerationDetailsSet.isEmpty()){
						Iterator<ExamRemunerationDetails> remunerationDetailsItr=remunerationDetailsSet.iterator();
	    				while (remunerationDetailsItr.hasNext()) {
	    					ExamRemunerationDetails remunerationDetails= remunerationDetailsItr .next();
	    					if(valuationDetailsMap.containsKey(remunerationDetails.getId())){
	    						ValuationChallanDetailsTO to=valuationDetailsMap.get(remunerationDetails.getId());
	    						if(to.getChecked()==null || !to.getChecked().equalsIgnoreCase("on")){
	    							remunerationDetails.setIsBoardMeetingApplicable(false);
	    						}else{
	    							remunerationDetails.setIsBoardMeetingApplicable(true);
	    						}
	    					}
					}
				}
				}
				session.update(remuneration);
			}
			save=true;
			transaction.commit();
			session.flush();
			session.close();
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			throw new ApplicationException();
			
		}
		return save;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getExamValuator(java.lang.String)
	 */
	@Override
	public ExamValuators getExamValuator(String otherEmpId) throws Exception {
		Session session = null;
		ExamValuators bo = new ExamValuators();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "from ExamValuators v where v.isActive=1 and v.id="+otherEmpId;
			Query query=session.createQuery(hqlQuery);
			bo = (ExamValuators)query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return bo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IValuationChallanTransaction#getUser(int)
	 */
	@Override
	public Users getUser(int userId) throws Exception {
		Session session = null;
		Users bo = new Users();
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "from Users v where v.isActive=1 and v.id="+userId;
			Query query=session.createQuery(hqlQuery);
			bo = (Users)query.uniqueResult();
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return bo;
	}
	
	
	public boolean getGuestId(String userId) throws Exception {
		Session session = null;
		Users bo = new Users();
		boolean isGuest=false;
		try {
			session=HibernateUtil.getSession();
			String hqlQuery = "from Users v where v.isActive=1 and v.id="+userId;
			Query query=session.createQuery(hqlQuery);
			bo = (Users)query.uniqueResult();
			if(bo!=null){
				if(bo.getEmployee()==null ){
					isGuest=true;
				}else{
					isGuest=false;
				}
			}
			
			session.flush();
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isGuest;
	}
	
	
	public List<Integer> getSemisternumbers(int examId)
			throws Exception {
		Session session = null;
		List<Integer> schemeNo=null ;
		try{
			session = HibernateUtil.getSession();
			
			schemeNo = session.createQuery("select e.schemeNo from ExamExamCourseSchemeDetailsBO e where " +
									"e.examId="+examId+" group by e.schemeNo").list();
			
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return schemeNo;

	}
	
	public String  getExamTypeByExamId(int examId)throws Exception {
	
		Session session = null;
		String examType="";
		try{
			session = HibernateUtil.getSession();
			
			String Sql_query = "select e.examTypeUtilBO.name from ExamDefinitionBO e where " +
								" e.delIsActive = 1 and e.isActive = 1 and e.id="+examId;
			Query query=session.createQuery(Sql_query);
			examType = (String)query.uniqueResult();
		}catch (Exception e) {
			if(session!=null){
				session.flush();
				session.close();
			}
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return examType;

	}
}