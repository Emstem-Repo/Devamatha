package com.kp.cms.to.fee;

import java.io.Serializable;

import com.kp.cms.to.admin.AdmittedThroughTO;

/**
 * Transfer object for FeeAccountAssignment
 *
 */
public class FeeAccountAssignmentTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int feeId;
	private int feeAccountId;
	private int feeHeadingId;
	private int feeAdmittedThroughId;
	
	private int id;
	private FeeTO feeTo;
	private FeeAccountTO feeAccountTo;
	private FeeHeadingTO feeHeadingTo;
	private AdmittedThroughTO admittedThroughTO;
	
	private String currencyId;
	private String casteAmount;
	private String ligAmount;
	private String casteCurrencyId;
	private String ligCurrencyId;
	
	private Double amount;
	private String currencyName;
	private String casteCurrencyName;
	private String ligCurrencyName;
	private FeeCategoryTo feeCategoryTo;
	private int feeFeeCategoryId;

	public FeeAccountAssignmentTO(){};
	
	/**
	 * @param amount
	 * @param feeAccountId
	 * @param feeAdmittedThroughId
	 * @param feeApplicableId
	 * @param feeId
	 */
	public FeeAccountAssignmentTO(Double amount, int feeAccountId,
			int feeAdmittedThroughId, int feeHeadingId, int feeId,
			String currencyId,String casteAmount,String ligAmount,
			String casteCurrencyId,String ligCurrencyId, int feeFeeCategoryId) {
		super();
		this.amount = amount;
		this.feeAccountId = feeAccountId;
		this.feeAdmittedThroughId = feeAdmittedThroughId;
		this.feeHeadingId = feeHeadingId;
		this.feeId = feeId;
		this.currencyId=currencyId;
		this.casteAmount=casteAmount;
		this.casteCurrencyId=casteCurrencyId;
		this.ligAmount=ligAmount;
		this.ligCurrencyId=ligCurrencyId;
		this.feeFeeCategoryId = feeFeeCategoryId;
	}
	/**
	 * @return the feeId
	 */
	public int getFeeId() {
		return feeId;
	}
	/**
	 * @param feeId the feeId to set
	 */
	public void setFeeId(int feeId) {
		this.feeId = feeId;
	}
	/**
	 * @return the feeAccountId
	 */
	public int getFeeAccountId() {
		return feeAccountId;
	}
	/**
	 * @param feeAccountId the feeAccountId to set
	 */
	public void setFeeAccountId(int feeAccountId) {
		this.feeAccountId = feeAccountId;
	}
	
	/**
	 * @return the feeAdmittedThroughId
	 */
	public int getFeeAdmittedThroughId() {
		return feeAdmittedThroughId;
	}
	/**
	 * @param feeAdmittedThroughId the feeAdmittedThroughId to set
	 */
	public void setFeeAdmittedThroughId(int feeAdmittedThroughId) {
		this.feeAdmittedThroughId = feeAdmittedThroughId;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the admittedThroughTO
	 */
	public AdmittedThroughTO getAdmittedThroughTO() {
		return admittedThroughTO;
	}
	/**
	 * @param admittedThroughTO the admittedThroughTO to set
	 */
	public void setAdmittedThroughTO(AdmittedThroughTO admittedThroughTO) {
		this.admittedThroughTO = admittedThroughTO;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the feeTo
	 */
	public FeeTO getFeeTo() {
		return feeTo;
	}
	/**
	 * @param feeTo the feeTo to set
	 */
	public void setFeeTo(FeeTO feeTo) {
		this.feeTo = feeTo;
	}

	/**
	 * @return the feeHeadingId
	 */
	public int getFeeHeadingId() {
		return feeHeadingId;
	}

	/**
	 * @param feeHeadingId the feeHeadingId to set
	 */
	public void setFeeHeadingId(int feeHeadingId) {
		this.feeHeadingId = feeHeadingId;
	}

	/**
	 * @return the feeAccountTo
	 */
	public FeeAccountTO getFeeAccountTo() {
		return feeAccountTo;
	}

	/**
	 * @param feeAccountTo the feeAccountTo to set
	 */
	public void setFeeAccountTo(FeeAccountTO feeAccountTo) {
		this.feeAccountTo = feeAccountTo;
	}

	/**
	 * @return the feeHeadingTo
	 */
	public FeeHeadingTO getFeeHeadingTo() {
		return feeHeadingTo;
	}

	/**
	 * @param feeHeadingTo the feeHeadingTo to set
	 */
	public void setFeeHeadingTo(FeeHeadingTO feeHeadingTo) {
		this.feeHeadingTo = feeHeadingTo;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCasteAmount() {
		return casteAmount;
	}

	public void setCasteAmount(String casteAmount) {
		this.casteAmount = casteAmount;
	}

	public String getLigAmount() {
		return ligAmount;
	}

	public void setLigAmount(String ligAmount) {
		this.ligAmount = ligAmount;
	}

	public String getCasteCurrencyId() {
		return casteCurrencyId;
	}

	public void setCasteCurrencyId(String casteCurrencyId) {
		this.casteCurrencyId = casteCurrencyId;
	}

	public String getLigCurrencyId() {
		return ligCurrencyId;
	}

	public void setLigCurrencyId(String ligCurrencyId) {
		this.ligCurrencyId = ligCurrencyId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCasteCurrencyName() {
		return casteCurrencyName;
	}

	public void setCasteCurrencyName(String casteCurrencyName) {
		this.casteCurrencyName = casteCurrencyName;
	}

	public String getLigCurrencyName() {
		return ligCurrencyName;
	}

	public void setLigCurrencyName(String ligCurrencyName) {
		this.ligCurrencyName = ligCurrencyName;
	}

	public FeeCategoryTo getFeeCategoryTo() {
		return feeCategoryTo;
	}

	public void setFeeCategoryTo(FeeCategoryTo feeCategoryTo) {
		this.feeCategoryTo = feeCategoryTo;
	}

	public int getFeeFeeCategoryId() {
		return feeFeeCategoryId;
	}

	public void setFeeFeeCategoryId(int feeFeeCategoryId) {
		this.feeFeeCategoryId = feeFeeCategoryId;
	}	
}
