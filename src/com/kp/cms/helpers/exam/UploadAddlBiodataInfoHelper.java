package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.to.admission.StudentBioDataTO;

	public class UploadAddlBiodataInfoHelper {
		private static final Log log = LogFactory.getLog(UploadAddlBiodataInfoHelper.class);
		
		private static volatile UploadAddlBiodataInfoHelper exportAddlBiodataInfoHelper = null;
		private UploadAddlBiodataInfoHelper() {
		}

		public static UploadAddlBiodataInfoHelper getInstance() {
			
			if (exportAddlBiodataInfoHelper == null) {
				exportAddlBiodataInfoHelper = new UploadAddlBiodataInfoHelper();
			}
			return exportAddlBiodataInfoHelper;
		}

		public List<ExamStudentBioDataBO> addUploadedData(List<StudentBioDataTO> stuBioDataTO) throws Exception {
			List<ExamStudentBioDataBO> studentBOList = new ArrayList<ExamStudentBioDataBO>();
			Iterator<StudentBioDataTO> iterator = stuBioDataTO.iterator();
			ExamStudentBioDataBO studentBioDataBO =null;
			while (iterator.hasNext()) {
				StudentBioDataTO studentBioDataTO = (StudentBioDataTO) iterator.next();
				studentBioDataBO = new ExamStudentBioDataBO();
				studentBioDataBO.setStudentId(studentBioDataTO.getStudentId());
				studentBioDataBO.setSpecializationId(studentBioDataTO.getSpecializationId());
				if(studentBioDataTO.getConsolidatedMarksCardNo()!=null && !studentBioDataTO.getConsolidatedMarksCardNo().isEmpty()){
					studentBioDataBO.setConsolidatedMarksCardNo(studentBioDataTO.getConsolidatedMarksCardNo());
				}
				if(studentBioDataTO.getCourseNameForMarksCard()!=null && !studentBioDataTO.getCourseNameForMarksCard().isEmpty())
				studentBioDataBO.setCourseNameForMarksCard(studentBioDataTO.getCourseNameForMarksCard());
				studentBOList.add(studentBioDataBO);
			}
			return studentBOList;
		}
}
