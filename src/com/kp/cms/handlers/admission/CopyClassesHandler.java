package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.forms.admission.CopyClassesForm;
import com.kp.cms.helpers.admission.CopyClassesHelper;
import com.kp.cms.to.admission.CopyClassesTO;
import com.kp.cms.transactions.admission.ICopyClassesTransaction;
import com.kp.cms.transactionsimpl.admission.CopyClassesTransactionImpl;

	public class CopyClassesHandler {
		private static final Log log = LogFactory.getLog(CopyClassesHandler.class);
		private static volatile CopyClassesHandler copyClassesHandler = null;
		ICopyClassesTransaction iTransaction = new CopyClassesTransactionImpl();
		public static CopyClassesHandler getInstance() {
			if (copyClassesHandler == null) {
				copyClassesHandler = new CopyClassesHandler();
			}
			return copyClassesHandler;
		}

		public List<CopyClassesTO> getClassesByYear(int year) throws Exception{
			List<Classes> classesListBO = iTransaction.getClassesByYear(year);
			List<CopyClassesTO> classesListTO = CopyClassesHelper.getInstance().convertBOToTO(classesListBO);
			return classesListTO;
		}

		public boolean saveClasses(List<CopyClassesTO> classesList, int toYear,CopyClassesForm copyClassesForm) throws Exception{
			boolean isCopied =false;
			Map<String,Integer> schemeMap = iTransaction.getClassesByToYear(toYear);
			List<Classes> classesBO	=CopyClassesHelper.getInstance().convertTOToBO(classesList,toYear,schemeMap,copyClassesForm);
				if(!classesBO.isEmpty()){
					isCopied=iTransaction.saveClasses(classesBO);
				}
				 return isCopied;
			}
}
