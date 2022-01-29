package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.forms.exam.UploadPreviousClassForm;
import com.kp.cms.helpers.exam.UploadPreviousClassHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.PreviousClassDetailsTO;
import com.kp.cms.transactions.exam.IUploadPreviousClassTransaction;
import com.kp.cms.transactionsimpl.exam.UploadPreviousClassTransImpl;

	public class UploadPreviousClassHandler {
		private static final Log log = LogFactory.getLog(UploadPreviousClassHandler.class);
		
		private static volatile UploadPreviousClassHandler exportPreviousClassHandler = null;
		IUploadPreviousClassTransaction  transaction= new UploadPreviousClassTransImpl();
		public static UploadPreviousClassHandler getInstance() {
			
			if (exportPreviousClassHandler == null) {
				exportPreviousClassHandler = new UploadPreviousClassHandler();
			}
			return exportPreviousClassHandler;
		}
		
		/**
		 * @return
		 * @throws Exception
		 */
		public Map<String,StudentTO> getStudentDetails() throws Exception {
			log.info("call of getStudentDetails method in ExportPreviousClassHandler class.");
			
			Map<String,StudentTO> map = transaction.getStudentDetails();
			log.info("end of getStudentDetails method in ExportPreviousClassHandler class.");
			return map;
		}

		/**
		 * @param previousClassDetailsTOList
		 * @return
		 * @throws Exception
		 */
		public boolean addUploadedData(UploadPreviousClassForm exportPreviousClassForm, List<PreviousClassDetailsTO> previousClassDetailsTOList) throws Exception {
			List<ExamStudentPreviousClassDetailsBO> studentPreviousClassBOList = UploadPreviousClassHelper.getInstance().addUploadedData(exportPreviousClassForm, previousClassDetailsTOList);
			return transaction.addUploadedData(studentPreviousClassBOList);
		}
		
		/**
		 * get classes with year based
		 * @return
		 * @throws Exception
		 */
		public Map<String, Integer> getClassMap() throws Exception {
			return transaction.getclassMap();
		}
}
