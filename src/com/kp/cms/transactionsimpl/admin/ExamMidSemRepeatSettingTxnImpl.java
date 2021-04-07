package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.admin.ExamMidSemRepeatSettingForm;
import com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ExamMidSemRepeatSettingTxnImpl implements IExamMidSemRepeatSettingTransaction {

	 public static volatile ExamMidSemRepeatSettingTxnImpl examMidSemRepeatSettingTxnImpl = null;
	 private static Log log = LogFactory.getLog(ExamMidSemRepeatSettingTxnImpl.class);
	 
	 /**
	 * @return
	 */
	public static ExamMidSemRepeatSettingTxnImpl getInstance() {
			if (examMidSemRepeatSettingTxnImpl == null) {
				examMidSemRepeatSettingTxnImpl = new ExamMidSemRepeatSettingTxnImpl();
				return examMidSemRepeatSettingTxnImpl;
			}
			return examMidSemRepeatSettingTxnImpl;
		}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMidSemRepeatSettingTransaction#getExamTypeList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamTypeUtilBO> getExamTypeList() {
		Session session=null;
		List<ExamTypeUtilBO> examTypeList=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamTypeUtilBO type";
			Query query=session.createQuery(quer);
			examTypeList = query.list();
		}catch(Exception e){
			log.info("exception occured in getExamTypeList Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return examTypeList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMidSemRepeatSettingTransaction#getCurrentExamNameByExamType(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCurrentExamNameByExamType(int examTypeId) {
		Session session=null;
		String examId=null;
		try{
			session=HibernateUtil.getSession();
			String quer="select d.id from ExamDefinitionBO d where d.isCurrent=1 and d.examTypeUtilBO.id="+examTypeId+ " and d.delIsActive = 1 and d.isActive=1";
			Query query=session.createQuery(quer);
			List list = query.list();
			if (list != null && list.size() > 0) {

				examId = list.get(0).toString();
			}
		}catch(Exception e){
			log.info("exception occured in getExamTypeList Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return examId;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction#saveOrUpdateMidSemRepeatSetting(com.kp.cms.bo.exam.ExamMidsemRepeatSetting, java.lang.String)
	 */
	@Override
	public boolean saveOrUpdateMidSemRepeatSetting(ExamMidsemRepeatSetting repeatSetting,String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isSaved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Save")){
				session.save(repeatSetting);
			}else{
				session.merge(repeatSetting);
			}
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
	 * @see com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction#getMidSemRepeatSettingList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamMidsemRepeatSetting> getMidSemRepeatSettingList()throws Exception {
		Session session=null;
		List<ExamMidsemRepeatSetting> repeatSettings=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamMidsemRepeatSetting repeat where repeat.isActive=1";
			Query query=session.createQuery(quer);
			repeatSettings = query.list();
		}catch(Exception e){
			log.info("exception occured in getExamTypeList Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return repeatSettings;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction#duplicatCheckRepeatSetting(int)
	 */
	@Override
	public boolean duplicatCheckRepeatSetting(ExamMidSemRepeatSettingForm settingForm) throws Exception {
		Session session=null;
		boolean isDuplicate=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamMidsemRepeatSetting repeat where repeat.isActive=1" +
					" and ((('"+CommonUtil.ConvertStringToSQLDate(settingForm.getApplicationOpenFrom())+"') between repeat.applnStartDate and repeat.applnEndDate )" +
					" or (('"+CommonUtil.ConvertStringToSQLDate(settingForm.getApplicationOpenTill())+"') between repeat.applnStartDate and repeat.applnEndDate ))" ;
			Query query=session.createQuery(quer);
			ExamMidsemRepeatSetting repeatSetting = (ExamMidsemRepeatSetting) query.uniqueResult();
			if(repeatSetting!=null){
				if(repeatSetting.getId()!=settingForm.getRepeatSettingId()){
					isDuplicate=true;
				}
			}
		}catch(Exception e){
			log.info("exception occured in getExamTypeList Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return isDuplicate;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction#getMidSemRepeatSettingById(int)
	 */
	@Override
	public ExamMidsemRepeatSetting getMidSemRepeatSettingById(int repeatSettingId) throws Exception {
		Session session=null;
		ExamMidsemRepeatSetting repeatSetting=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamMidsemRepeatSetting repeat where repeat.isActive=1 and repeat.id="+repeatSettingId;
			Query query=session.createQuery(quer);
			repeatSetting = (ExamMidsemRepeatSetting) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in getExamTypeList Method..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return repeatSetting;
	}
}
