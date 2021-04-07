package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;
import com.kp.cms.transactions.exam.IPublishSupplementaryImpApplicationTxn;
import com.kp.cms.transactionsimpl.exam.PublishSupplementaryImpApplicationTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class PublishSupplementaryImpApplicationHelper {
	/**
	 * Singleton object of PublishSupplementaryImpApplicationHelper
	 */
	private static volatile PublishSupplementaryImpApplicationHelper publishSupplementaryImpApplicationHelper = null;
	private static final Log log = LogFactory.getLog(PublishSupplementaryImpApplicationHelper.class);
	private PublishSupplementaryImpApplicationHelper() {
		
	}
	/**
	 * return singleton object of PublishSupplementaryImpApplicationHelper.
	 * @return
	 */
	public static PublishSupplementaryImpApplicationHelper getInstance() {
		if (publishSupplementaryImpApplicationHelper == null) {
			publishSupplementaryImpApplicationHelper = new PublishSupplementaryImpApplicationHelper();
		}
		return publishSupplementaryImpApplicationHelper;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<PublishSupplementaryImpApplicationTo> convertBotoToList( List<PublishSupplementaryImpApplication> boList) throws Exception {
		
		log.info("Entered into convertBotoToList");
		List<PublishSupplementaryImpApplicationTo> toList=new ArrayList<PublishSupplementaryImpApplicationTo>();
		PublishSupplementaryImpApplicationTo to=null;
		for (PublishSupplementaryImpApplication bo : boList) {
			to=new PublishSupplementaryImpApplicationTo();
			to.setId(bo.getId());
			to.setExamName(bo.getExam().getName());
			if(bo.getStartDate()!=null)
			to.setStartDate(CommonUtil.formatSqlDate1(bo.getStartDate().toString()));
			if(bo.getEndDate()!=null)
			to.setEndDate(CommonUtil.formatSqlDate1(bo.getEndDate().toString()));
			int id=bo.getClassCode().getId();
			if(id!=0 && id>0)
			{
			IPublishSupplementaryImpApplicationTxn txnImpl=PublishSupplementaryImpApplicationTxnImpl.getInstance();
			List list=txnImpl.getClassNameAndYearByClassId(id);
			Iterator iterator=list.iterator();
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
				to.setClassName(object[0].toString()+"("+object[1].toString()+")");
				}
			}
			}
			toList.add(to);
		}
		log.info("Exit From convertBotoToList");
		return toList;
	}
}
