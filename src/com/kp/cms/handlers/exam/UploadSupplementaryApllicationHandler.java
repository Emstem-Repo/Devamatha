package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.helpers.exam.UploadSupplementaryApllicationHelper;
import com.kp.cms.to.exam.UploadSupplementaryAppTO;
import com.kp.cms.transactions.exam.IUploadSupplementaryApllicationTransaction;
import com.kp.cms.transactionsimpl.exam.UploadSupplementaryApllicationTransImpl;

	public class UploadSupplementaryApllicationHandler {
		private static volatile UploadSupplementaryApllicationHandler exportSupplementaryApllicationHandler = null;
		IUploadSupplementaryApllicationTransaction transaction = new UploadSupplementaryApllicationTransImpl();
		private static final Log log = LogFactory.getLog(UploadSupplementaryApllicationHandler.class);
		private UploadSupplementaryApllicationHandler() {
			
		}
		public static UploadSupplementaryApllicationHandler getInstance() {
			if (exportSupplementaryApllicationHandler == null) {
				exportSupplementaryApllicationHandler = new UploadSupplementaryApllicationHandler();
			}
			return exportSupplementaryApllicationHandler;
		}
		public boolean saveSupplementaryDetails(List<UploadSupplementaryAppTO> supplementaryAppTOList) throws Exception {
			List<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOs=UploadSupplementaryApllicationHelper.getInstance().convertTOToBO(supplementaryAppTOList);
			return transaction.saveSupplementaryDetails(examSupplementaryImprovementApplicationBOs);
		}

}
