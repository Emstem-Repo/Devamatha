package com.kp.cms.to.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * FeePAymentDisplay will be used displaying the paymentdetails.
 */
public class FeePaymentDisplayTO {

		private String semester;
		private Map<Integer,String> feeAccountMap;
		private Map<Integer,String> feeGroupMap;
		private List<FeeGroupAccountTO> feeGroupAccountList;
		private String message;
		private List<FeeDisplayTO> feeDispTOList;
		
		public FeePaymentDisplayTO() {
			this.feeAccountMap = new HashMap<Integer, String>();
		    this.feeGroupMap = new HashMap<Integer,String>();
		    this.semester = null;
		    this.feeGroupAccountList = new ArrayList<FeeGroupAccountTO>();
		    this.message = null;
			// TODO Auto-generated constructor stub
		}

		/**
		 * @return the semester
		 */
		public String getSemester() {
			return semester;
		}

		/**
		 * @param semester the semester to set
		 */
		public void setSemester(String semester) {
			this.semester = semester;
		}

		/**
		 * @return the feeAccountMap
		 */
		public Map<Integer, String> getFeeAccountMap() {
			return feeAccountMap;
		}

		/**
		 * @param feeAccountMap the feeAccountMap to set
		 */
		public void setFeeAccountMap(Map<Integer, String> feeAccountMap) {
			this.feeAccountMap = feeAccountMap;
		}

		/**
		 * @return the feeGroupMap
		 */
		public Map<Integer, String> getFeeGroupMap() {
			return feeGroupMap;
		}

		/**
		 * @param feeGroupMap the feeGroupMap to set
		 */
		public void setFeeGroupMap(Map<Integer, String> feeGroupMap) {
			this.feeGroupMap = feeGroupMap;
		}

		/**
		 * @return the feeGroupAccountList
		 */
		public List<FeeGroupAccountTO> getFeeGroupAccountList() {
			return feeGroupAccountList;
		}

		/**
		 * @param feeGroupAccountList the feeGroupAccountList to set
		 */
		public void setFeeGroupAccountList(List<FeeGroupAccountTO> feeGroupAccountList) {
			this.feeGroupAccountList = feeGroupAccountList;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		public List<FeeDisplayTO> getFeeDispTOList() {
			return feeDispTOList;
		}

		public void setFeeDispTOList(List<FeeDisplayTO> feeDispTOList) {
			this.feeDispTOList = feeDispTOList;
		}
		
		
			
}
