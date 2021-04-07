package com.kp.cms.to.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.DocTypeExams;

public class PreviousQualificationWeightageTO {
		
		private int docTypeExamId;
		private String docTypeName;
	    private String examName;
	    private List<String> examNames;
		private Integer weightageId;
		private String weightagePercentage;
		
	    
		public int getDocTypeExamId() {
			return docTypeExamId;
		}
		public void setDocTypeExamId(int docTypeExamId) {
			this.docTypeExamId = docTypeExamId;
		}
		
		public String getDocTypeName() {
			return docTypeName;
		}
		public void setDocTypeName(String docTypeName) {
			this.docTypeName = docTypeName;
		}
		public String getExamName() {
			return examName;
		}
		public void setExamName(String examName) {
			this.examName = examName;
		}
		public Integer getWeightageId() {
			return weightageId;
		}
		public void setWeightageId(Integer weightageId) {
			this.weightageId = weightageId;
		}
		public String getWeightagePercentage() {
			return weightagePercentage;
		}
		public void setWeightagePercentage(String weightagePercentage) {
			this.weightagePercentage = weightagePercentage;
		}

		public List<String> getExamNames() {
			return examNames;
		}
		public void setExamNames(List<String> examNames) {
			this.examNames = examNames;
		}
				
	}



