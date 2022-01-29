package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.PucApprovedLeaves;
import com.kp.cms.bo.admin.PucClassHeld;
import com.kp.cms.bo.admin.PucDefineClassSubject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.PucAttendanceForm;
import com.kp.cms.transactions.attandance.IPucAttendanceTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PucAttendanceTxnImpl implements IPucAttendanceTransaction{
	private static volatile PucAttendanceTxnImpl pucAttendanceTxn = null;
	public static PucAttendanceTxnImpl getInstance(){
		if(pucAttendanceTxn == null){
			pucAttendanceTxn = new PucAttendanceTxnImpl();
			return pucAttendanceTxn;
		}
		return pucAttendanceTxn;
	}
	@Override
	public boolean addPucClassHeld(List<PucClassHeld> list,
			PucAttendanceForm pucAttendanceForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		//int count=0;
		//List<String> codes=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				Iterator<PucClassHeld> iterator= list.iterator();
				//int size=list.size();
				while (iterator.hasNext()) {
					
					PucClassHeld secondLang = (PucClassHeld) iterator .next();
					//String str= "from PucClassHeld lang where lang.secondLanguage = '"+secondLang.getSecondLanguage()+"' or lang.langCode ='"+secondLang.getLangCode()+"'";
					//Query query = session.createQuery(str);
					//PromoteSecondLang secondLan = (PromoteSecondLang) query.uniqueResult();
					session.save(secondLang);
				}
				//admPromote.setCodes(codes);
				tx.commit();
				session.flush();
				//if(size==count)
					//isAdded=false;
				//else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			isAdded=false;
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public boolean addPucClassSubjectDefine(List<PucDefineClassSubject> list,
			PucAttendanceForm pucAttendanceForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		//int count=0;
		//List<String> codes=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				Iterator<PucDefineClassSubject> iterator= list.iterator();
				//int size=list.size();
				while (iterator.hasNext()) {
					
					PucDefineClassSubject defineSubject = (PucDefineClassSubject) iterator .next();
					//String str= "from PucClassHeld lang where lang.secondLanguage = '"+secondLang.getSecondLanguage()+"' or lang.langCode ='"+secondLang.getLangCode()+"'";
					//Query query = session.createQuery(str);
					//PromoteSecondLang secondLan = (PromoteSecondLang) query.uniqueResult();
					session.save(defineSubject);
				}
				//admPromote.setCodes(codes);
				tx.commit();
				session.flush();
				//if(size==count)
					//isAdded=false;
				//else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			isAdded=false;
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public boolean addPucApprovedLeaves(List<PucApprovedLeaves> list,
			PucAttendanceForm pucAttendanceForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction tx = null;
		List<String> regNosList=new ArrayList<String>();
		int count=0;
		//int count=0;
		//List<String> codes=new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			if(list!=null){
				int size=list.size();
				Iterator<PucApprovedLeaves> iterator= list.iterator();
				//int size=list.size();
				while (iterator.hasNext()) {
					
					PucApprovedLeaves leavess = (PucApprovedLeaves) iterator .next();
					if(leavess.getRegNo()!=null && !leavess.getRegNo().isEmpty()){
					String str= "from PucApprovedLeaves leaves where leaves.regNo = '"+leavess.getRegNo()+"' and leaves.leaveStartDate ='"+leavess.getLeaveStartDate()+"' and leaves.leaveEndDate ='"+leavess.getLeaveEndDate()+"' and leaves.academicYear="+leavess.getAcademicYear();
					Query query = session.createQuery(str);
					PucApprovedLeaves approvedLeaves = (PucApprovedLeaves) query.uniqueResult();
					if(approvedLeaves!=null){
						regNosList.add(leavess.getRegNo());
						count++;
					}else
						session.save(leavess);
				     }
				}
				pucAttendanceForm.setRegNosList(regNosList);
				tx.commit();
				session.flush();
				if(size==count)
					isAdded=false;
				else
				    isAdded = true;
			}
		}catch (ConstraintViolationException e) {
			tx.rollback();
			
			throw new BusinessException(e);
		} catch (Exception e) {
			isAdded=false;
			tx.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	
}
