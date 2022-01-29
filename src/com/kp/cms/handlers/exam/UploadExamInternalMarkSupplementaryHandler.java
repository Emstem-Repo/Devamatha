package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.helpers.exam.UploadExamInternalMarkSupplementaryHelper;
import com.kp.cms.to.exam.UploadExamInternalMarkSupplementaryTO;
import com.kp.cms.transactions.exam.IUploadExamInternalMarkSupplementaryTransaction;
import com.kp.cms.transactionsimpl.exam.UploadExamInternalMarkSupplementaryTransImpl;

	public class UploadExamInternalMarkSupplementaryHandler {
		private static final Log log = LogFactory.getLog(UploadExamInternalMarkSupplementaryHandler.class);
		private static volatile UploadExamInternalMarkSupplementaryHandler examInternalMarkSupplementaryHandler = null;
		IUploadExamInternalMarkSupplementaryTransaction transaction=new UploadExamInternalMarkSupplementaryTransImpl();
		private UploadExamInternalMarkSupplementaryHandler() {
			
		}
		public static UploadExamInternalMarkSupplementaryHandler getInstance() {
			if (examInternalMarkSupplementaryHandler == null) {
				examInternalMarkSupplementaryHandler = new UploadExamInternalMarkSupplementaryHandler();
			}
			return examInternalMarkSupplementaryHandler;
		}
		/**
		 * @param result
		 * @param user
		 * @return
		 * @throws Exception
		 */
		public boolean addUploadedData(List<UploadExamInternalMarkSupplementaryTO> result, String user) throws Exception {
				List<ExamInternalMarkSupplementaryDetailsBO> list=UploadExamInternalMarkSupplementaryHelper.getInstance().convertToListToBOList(result,user);
				return transaction.saveExamInternalMarkSupplementaryDetailsBOList(list);
			}
}
