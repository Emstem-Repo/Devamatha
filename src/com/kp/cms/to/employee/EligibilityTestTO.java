	package com.kp.cms.to.employee;
	
	import java.io.Serializable;
import java.util.Date;
	
	public class EligibilityTestTO implements Serializable {
		private String checked;
		private String tempChecked;
		private String eligibilityTestNET;
		private String eligibilityTestSLET;
		private String eligibilityTestSET;
		private String eligibilityTestNone;
		private String eligibilityTestOther;
		private String eligibilityTest;
		
		public String getChecked() {
			return checked;
		}
		public void setChecked(String checked) {
			this.checked = checked;
		}
		public String getTempChecked() {
			return tempChecked;
		}
		public void setTempChecked(String tempChecked) {
			this.tempChecked = tempChecked;
		}
		public String getEligibilityTestNET() {
			return eligibilityTestNET;
		}
		public void setEligibilityTestNET(String eligibilityTestNET) {
			this.eligibilityTestNET = eligibilityTestNET;
		}
		public String getEligibilityTestSLET() {
			return eligibilityTestSLET;
		}
		public void setEligibilityTestSLET(String eligibilityTestSLET) {
			this.eligibilityTestSLET = eligibilityTestSLET;
		}
		public String getEligibilityTestSET() {
			return eligibilityTestSET;
		}
		public void setEligibilityTestSET(String eligibilityTestSET) {
			this.eligibilityTestSET = eligibilityTestSET;
		}
		public String getEligibilityTestNone() {
			return eligibilityTestNone;
		}
		public void setEligibilityTestNone(String eligibilityTestNone) {
			this.eligibilityTestNone = eligibilityTestNone;
		}
		public String getEligibilityTestOther() {
			return eligibilityTestOther;
		}
		public void setEligibilityTestOther(String eligibilityTestOther) {
			this.eligibilityTestOther = eligibilityTestOther;
		}
		public String getEligibilityTest() {
			return eligibilityTest;
		}
		public void setEligibilityTest(String eligibilityTest) {
			this.eligibilityTest = eligibilityTest;
		}
		
	}
