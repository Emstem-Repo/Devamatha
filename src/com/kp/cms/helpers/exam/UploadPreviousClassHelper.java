package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.forms.exam.UploadPreviousClassForm;
import com.kp.cms.to.exam.PreviousClassDetailsTO;

	public class UploadPreviousClassHelper {
		private static final Log log = LogFactory.getLog(UploadPreviousClassHelper.class);
		
		private static volatile UploadPreviousClassHelper exportPreviousClassHelper = null;

		public static UploadPreviousClassHelper getInstance() {
			
			if (exportPreviousClassHelper == null) {
				exportPreviousClassHelper = new UploadPreviousClassHelper();
			}
			return exportPreviousClassHelper;
		}
		
		public List<ExamStudentPreviousClassDetailsBO> addUploadedData(UploadPreviousClassForm exportPreviousClassForm, List<PreviousClassDetailsTO> previousClassDetailsTOList) throws Exception {
			List<ExamStudentPreviousClassDetailsBO> studentPreviousClassBOList = new ArrayList<ExamStudentPreviousClassDetailsBO>();
			Iterator<PreviousClassDetailsTO> iterator = previousClassDetailsTOList.iterator();
			ExamStudentPreviousClassDetailsBO studentPreviousClassBO =null;
			while (iterator.hasNext()) {
				PreviousClassDetailsTO studentPreviousClassTO = (PreviousClassDetailsTO) iterator.next();
				studentPreviousClassBO = new ExamStudentPreviousClassDetailsBO();
				if(studentPreviousClassTO.getYear()!=null)
				studentPreviousClassBO.setAcademicYear(Integer.parseInt(studentPreviousClassTO.getYear()));
				studentPreviousClassBO.setClassId(studentPreviousClassTO.getClassId());
				studentPreviousClassBO.setStudentId(studentPreviousClassTO.getStudentId());
				if(studentPreviousClassTO.getSchemeNo()!=null)
				studentPreviousClassBO.setSchemeNo(Integer.parseInt(studentPreviousClassTO.getSchemeNo()));
				studentPreviousClassBO.setCreatedBy(exportPreviousClassForm.getUserId());
				studentPreviousClassBO.setModifiedBy(exportPreviousClassForm.getUserId());
				studentPreviousClassBO.setCreatedDate(new Date());
				studentPreviousClassBO.setLastModifiedDate(new Date());
				studentPreviousClassBOList.add(studentPreviousClassBO);
			}
			return studentPreviousClassBOList;
		}
}
