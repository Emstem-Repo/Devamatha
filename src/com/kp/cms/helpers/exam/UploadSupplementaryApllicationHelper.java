package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.to.exam.UploadSupplementaryAppTO;

	public class UploadSupplementaryApllicationHelper {
		private static volatile UploadSupplementaryApllicationHelper exportSupplementaryApllicationHelper = null;
		private static final Log log = LogFactory.getLog(UploadSupplementaryApllicationHelper.class);
		private UploadSupplementaryApllicationHelper() {
			
		}
		public static UploadSupplementaryApllicationHelper getInstance() {
			if (exportSupplementaryApllicationHelper == null) {
				exportSupplementaryApllicationHelper = new UploadSupplementaryApllicationHelper();
			}
			return exportSupplementaryApllicationHelper;
		}
		public List<ExamSupplementaryImprovementApplicationBO> convertTOToBO(List<UploadSupplementaryAppTO> supplementaryAppTOList) throws Exception {
			List<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOList=new ArrayList<ExamSupplementaryImprovementApplicationBO>();
			Iterator<UploadSupplementaryAppTO> iterator = supplementaryAppTOList.iterator();
			ExamSupplementaryImprovementApplicationBO examBo; 
			while (iterator.hasNext()) {
				UploadSupplementaryAppTO exportSupplementaryAppTO = (UploadSupplementaryAppTO) iterator.next();
				examBo=new ExamSupplementaryImprovementApplicationBO();
				examBo.setExamId(exportSupplementaryAppTO.getExamId());
				examBo.setStudentId(exportSupplementaryAppTO.getStudentId());
				examBo.setSubjectId(exportSupplementaryAppTO.getSubjectId());
				examBo.setChance(exportSupplementaryAppTO.getChance());
				examBo.setSchemeNo(exportSupplementaryAppTO.getSchemeNo());
				examBo.setIsFailedTheory(exportSupplementaryAppTO.getIsFailedTheory());
				examBo.setIsFailedPractical(exportSupplementaryAppTO.getIsFailedPractical());
				examBo.setIsAppearedTheory(exportSupplementaryAppTO.getIsAppearedTheory());
				examBo.setIsAppearedPractical(exportSupplementaryAppTO.getIsAppearedPractical());
				examBo.setIsSupplementary(1);
				if(exportSupplementaryAppTO.getFees()!=null)
					examBo.setFees(exportSupplementaryAppTO.getFees());
				examSupplementaryImprovementApplicationBOList.add(examBo);
			}
			return examSupplementaryImprovementApplicationBOList;
		}
}
