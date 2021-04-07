package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kp.cms.to.admin.SubjectTO;

	public class PreviousClassDetailsTO  implements Serializable,Comparable<PreviousClassDetailsTO>{
		private String year;
		private String regNo;
		private String className;
		private int classId;
		private String schemeNo;
		private int studentId;
		private Map<String, List<SubjectTO>> previousSubjectGrpMap;
		private String subjectGroupName;
		
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getRegNo() {
			return regNo;
		}
		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public int getClassId() {
			return classId;
		}
		public void setClassId(int classId) {
			this.classId = classId;
		}
		public String getSchemeNo() {
			return schemeNo;
		}
		public void setSchemeNo(String schemeNo) {
			this.schemeNo = schemeNo;
		}
		public int getStudentId() {
			return studentId;
		}
		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}
		public Map<String, List<SubjectTO>> getPreviousSubjectGrpMap() {
			return previousSubjectGrpMap;
		}
		public void setPreviousSubjectGrpMap(
				Map<String, List<SubjectTO>> previousSubjectGrpMap) {
			this.previousSubjectGrpMap = previousSubjectGrpMap;
		}
		public String getSubjectGroupName() {
			return subjectGroupName;
		}
		public void setSubjectGroupName(String subjectGroupName) {
			this.subjectGroupName = subjectGroupName;
		}
		@Override
		public int compareTo(PreviousClassDetailsTO arg0) {
			if(arg0!=null && this!=null && arg0.getSchemeNo()!=null 
					&& this.getSchemeNo()!=null){
				return this.getSchemeNo().compareTo(arg0.getSchemeNo());
			}else
				return 0;
		}
}