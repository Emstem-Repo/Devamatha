package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.to.exam.UploadExamInternalMarkSupplementaryTO;

	public class UploadExamInternalMarkSupplementaryHelper {
		private static final Log log = LogFactory.getLog(UploadExamInternalMarkSupplementaryHelper.class);
		private static volatile UploadExamInternalMarkSupplementaryHelper examInternalMarkSupplementaryHelper = null;
		private UploadExamInternalMarkSupplementaryHelper() {
			
		}
		public static UploadExamInternalMarkSupplementaryHelper getInstance() {
			if (examInternalMarkSupplementaryHelper == null) {
				examInternalMarkSupplementaryHelper = new UploadExamInternalMarkSupplementaryHelper();
			}
			return examInternalMarkSupplementaryHelper;
		}
		public List<ExamInternalMarkSupplementaryDetailsBO> convertToListToBOList(List<UploadExamInternalMarkSupplementaryTO> result, String user)throws Exception {
			List<ExamInternalMarkSupplementaryDetailsBO> finalList=new ArrayList<ExamInternalMarkSupplementaryDetailsBO>();
			if(result!=null && !result.isEmpty()){
				Iterator<UploadExamInternalMarkSupplementaryTO> itr=result.iterator();
				while (itr.hasNext()) {
					UploadExamInternalMarkSupplementaryTO uploadExamInternalMarkSupplementaryTO = (UploadExamInternalMarkSupplementaryTO) itr.next();
					ExamInternalMarkSupplementaryDetailsBO bo=new ExamInternalMarkSupplementaryDetailsBO();
					Student student = new Student();
					student.setId(Integer.parseInt(uploadExamInternalMarkSupplementaryTO.getStudentId()));
					bo.setStudent(student);
					Subject subject = new Subject();
					subject.setId(Integer.parseInt(uploadExamInternalMarkSupplementaryTO.getSubjectId()));
					bo.setSubject(subject);
					Classes classes = new Classes();
					classes.setId(Integer.parseInt(uploadExamInternalMarkSupplementaryTO.getClassId()));
					bo.setClasses(classes);
					ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
					examDefinitionBO.setId(Integer.parseInt(uploadExamInternalMarkSupplementaryTO.getExamId()));
					bo.setExamDefinitionBO(examDefinitionBO);
					bo.setTheoryTotalSubInternalMarks(uploadExamInternalMarkSupplementaryTO.getTheoryTotalSubInternalMarks());
					bo.setPracticalTotalSubInternalMarks(uploadExamInternalMarkSupplementaryTO.getPracticalTotalSubInternalMarks());
					bo.setTheoryTotalAttendenceMarks(uploadExamInternalMarkSupplementaryTO.getTheoryTotalAttendenceMarks());
					bo.setPracticalTotalAttendenceMarks(uploadExamInternalMarkSupplementaryTO.getPracticalTotalAttendenceMarks());
					if(bo.getTheoryTotalSubInternalMarks()==null)
						bo.setTheoryTotalSubInternalMarks("0");
					if(bo.getTheoryTotalAttendenceMarks()==null)
						bo.setTheoryTotalAttendenceMarks("0");
					if(bo.getPracticalTotalAttendenceMarks()==null)
						bo.setPracticalTotalAttendenceMarks("0");
					if(bo.getPracticalTotalSubInternalMarks()==null)
						bo.setPracticalTotalSubInternalMarks("0");
					int theroryTotalMark=0;
					int practicalTotalMark=0;
					if(StringUtils.isNumeric(bo.getTheoryTotalSubInternalMarks()))
						theroryTotalMark+=Integer.parseInt(bo.getTheoryTotalSubInternalMarks());
					if(StringUtils.isNumeric(bo.getTheoryTotalAttendenceMarks()))
						theroryTotalMark+=Integer.parseInt(bo.getTheoryTotalAttendenceMarks());
					
					if(StringUtils.isNumeric(bo.getPracticalTotalSubInternalMarks()))
						practicalTotalMark+=Integer.parseInt(bo.getTheoryTotalSubInternalMarks());
					if(StringUtils.isNumeric(bo.getTheoryTotalAttendenceMarks()))
						practicalTotalMark+=Integer.parseInt(bo.getPracticalTotalAttendenceMarks());
					bo.setTheoryTotalMarks(String.valueOf(theroryTotalMark));
					bo.setPracticalTotalMarks(String.valueOf(practicalTotalMark));
					bo.setCreatedBy(user);
					bo.setCreatedDate(new Date());
					bo.setModifiedBy(user);
					bo.setLastModifiedDate(new Date());
					bo.setIsActive(true);
					if(uploadExamInternalMarkSupplementaryTO.isPass())
					bo.setPassOrFail("p");
					else
						bo.setPassOrFail("f");
					finalList.add(bo);
				}
			}
			return finalList;
		}
}
