package com.kp.cms.handlers.admin;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.PreRequisiteDefinitionForm;
import com.kp.cms.helpers.admin.PrerequisiteDefHelper;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.transactions.admin.IPreReqDefTransaction;
import com.kp.cms.transactionsimpl.admin.PreReqTransactionImpl;

public class PreReqDefinitionHandler {
	public static volatile PreReqDefinitionHandler preReqDefinitionHandler = null;
	private static Log log = LogFactory.getLog(PreReqDefinitionHandler.class);

	public static PreReqDefinitionHandler getInstance() {
		if (preReqDefinitionHandler == null) {
			preReqDefinitionHandler = new PreReqDefinitionHandler();
			return preReqDefinitionHandler;
		}
		return preReqDefinitionHandler;
	}
	/**
	 * 
	 * @param form
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addPreRequesiteExam(PreRequisiteDefinitionForm prereqform,String mode) throws Exception {
		IPreReqDefTransaction iPreReqDefTransaction=new PreReqTransactionImpl();
		boolean result=false;
		int lissize=0;
		String order="first";
		CoursePrerequisite cprerequisite1=null;
		CoursePrerequisite cprerequisite2=null;
		List<CoursePrerequisite> existanceCheckObjects=null;
		cprerequisite1=PrerequisiteDefHelper.getInstance().createBoObject(prereqform,mode,order);
		
		existanceCheckObjects=iPreReqDefTransaction.existanceCheck(cprerequisite1);
		if(existanceCheckObjects!=null){
			lissize=existanceCheckObjects.size();
			}
		
		if(lissize==0)
		{
			result=iPreReqDefTransaction.addPreReqDef(cprerequisite1,mode);
			if(prereqform.getPrereqid2()!=null && !prereqform.getPrereqid2().isEmpty())
			{
				order="second";
				cprerequisite2=PrerequisiteDefHelper.getInstance().createBoObject(prereqform,mode,order);
				result=iPreReqDefTransaction.addPreReqDef(cprerequisite2,mode);
			}
		}else
		{
			if(mode.equalsIgnoreCase("Delete"))
			{
				Iterator itr1=existanceCheckObjects.iterator();
				while(itr1.hasNext())
				{
					cprerequisite2=(CoursePrerequisite) itr1.next();
					cprerequisite2=PrerequisiteDefHelper.getInstance().createnewBoObject(cprerequisite2,mode, prereqform);
					result=iPreReqDefTransaction.addPreReqDef(cprerequisite2,mode);
				}
				
			}else
			{
				throw new DuplicateException();
			}
		}
		log.debug("Handler:leaving addPreRequesiteExam");
		return result;
	}
		
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CoursePrerequisiteTO> getPreReqDef()throws Exception {
		List<CoursePrerequisite> deflist=null;
		IPreReqDefTransaction iPreReqDefTransaction=new PreReqTransactionImpl();
		deflist=iPreReqDefTransaction.getPreReqDef();
		List<CoursePrerequisiteTO> detTO=PrerequisiteDefHelper.getInstance().createTOObjcet(deflist);
		log.debug("Handler: leaving getPreReqDef");
		return detTO;
	}

}
