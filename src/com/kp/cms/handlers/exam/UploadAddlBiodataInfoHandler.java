package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.helpers.exam.UploadAddlBiodataInfoHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.StudentBioDataTO;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.transactions.exam.IUploadAddlBiodataInfoTrans;
import com.kp.cms.transactionsimpl.exam.UploadAddlBiodataInfoTransImpl;

	public class UploadAddlBiodataInfoHandler {
		private static final Log log = LogFactory.getLog(UploadAddlBiodataInfoHandler.class);
		
		private static volatile UploadAddlBiodataInfoHandler exportAddlBiodataInfoHandler = null;
		IUploadAddlBiodataInfoTrans transaction = new UploadAddlBiodataInfoTransImpl();
		
		private UploadAddlBiodataInfoHandler() {
		}

		public static UploadAddlBiodataInfoHandler getInstance() {
			
			if (exportAddlBiodataInfoHandler == null) {
				exportAddlBiodataInfoHandler = new UploadAddlBiodataInfoHandler();
			}
			return exportAddlBiodataInfoHandler;
		}
		
		public Map<String,StudentTO> getStudentDetails() throws Exception {
			log.info("call of getStudentDetails method in ExportAddlBiodataInfoHandler class.");
			
			Map<String,StudentTO> map = transaction.getStudentDetails();
			log.info("end of getStudentDetails method in ExportAddlBiodataInfoHandler class.");
			return map;
		}
		
		public Map<String,ExamSpecializationTO> getSpecializationDetails() throws Exception {
			log.info("call of getStudentDetails method in ExportAddlBiodataInfoHandler class.");
			Map<String,ExamSpecializationTO> map = transaction.getSpecializationDetails();
			log.info("end of getStudentDetails method in ExportAddlBiodataInfoHandler class.");
			return map;
		}

		public boolean addUploadedData(List<StudentBioDataTO> stuBioDataTO) throws Exception {
			List<ExamStudentBioDataBO> studentBOList = UploadAddlBiodataInfoHelper.getInstance().addUploadedData(stuBioDataTO);
			return transaction.addUploadedData(studentBOList);
		}
}
