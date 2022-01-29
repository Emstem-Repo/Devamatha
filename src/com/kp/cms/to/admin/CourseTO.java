package com.kp.cms.to.admin;

// Generated Jan 7, 2009 2:40:32 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;


/**
 * Course generated by hbm2java
 */
public class CourseTO implements java.io.Serializable,Comparable<CourseTO> {

	private int id;
	private ProgramTO programTo;
	private String code;
	private String description;
	private Boolean hasSecondLanguage;
	private Boolean isActive;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private String programCode;
	private String programTypeCode;
	private int programTypeId;
	private String name;
	private Integer maxInTake;
	private String isAutonomous;
	private List<SeatAllocationTO> seatAllocation;
	private int programId;
	private String isWorkExperienceRequired;
	private String payCode;
	private BigDecimal amount;
	private String nameCode;
	
	
	private String created;
	private String modified;
	private String active;
	private String autonomous;
	private String maxIntake;	
	private String amountField;
	private String cDate;
	private String lDate;
	private String isDetailMarkPrint;
	private String certificateCourseName;;
	private boolean isWorkExpMandatory;
	private boolean isAppearInOnline;
	private boolean isApplicationProcessSms;
	private boolean onlyForApplication;
	private String courseMarksCard;
	private String bankName;
	private String bankNameFull;
	private boolean bankIncludeSection;
	private String checked1;
	private String tempChecked;
	private String commencementDate;
	private BigDecimal intApplicationFees;
	private CurrencyMasterTO currencyId;
	private String deptName;
	private int deptId;
	private String checked;
	private boolean tempChecked1;
	private int assignedDeptId;
	private int locationId;
	private int noOfMidsemAttempts;
	private String programName;
	private String prefNo;
	private int prefId;
	private Map<Integer,String> courseMap;
	private String prefName;
	
	//for allotment
	private Integer totseats;
	private Integer scseat;
	private Integer stseat;
	private Integer bcseat;
	private Integer museat;
	private Integer obxseat;
	private Integer obhseat;
	private Integer lcseat;
	private Integer genseat;
	private Integer oecseat;
	private Integer remainseats;
	private Integer rankcutoff;
	private Integer lctaseat;
	
	//OBC		SC		ST		MU		OBX		OBH		LC

	private Integer sccount;
	private Integer stcount;
	private Integer bccount;
	private Integer mucount;
	private Integer obxcount;
	private Integer obhcount;
	private Integer lccount;
	private Integer oeccount;
	private Integer gencount;
	private Integer totcount;
	private Integer lctacount;
	
	private Integer scremain;
	private Integer stremain;
	private Integer bcremain;
	private Integer muremain;
	private Integer obxremain;
	private Integer obhremain;
	private Integer lcremain;
	private Integer oecremain;
	private Integer genremain;
	private Integer lctaremain;
	private Integer communityremain;
	
	private Integer highscrank;
	private Integer highstrank;
	private Integer highbcrank;
	private Integer highmurank;
	private Integer highobxrank;
	private Integer highobhrank;
	private Integer highlcrank;
	private Integer highoecrank;
	private Integer highgenrank;
	private Integer highlctarank;
	private Integer highcommunityrank;
	
	private boolean scover;
	private boolean stover;
	private boolean bcover;
	private boolean muover;
	private boolean obxover;
	private boolean obhover;
	private boolean lcover;
	private boolean oecover;
	private boolean genover;
	private boolean lctaover;
	private boolean communityover;
	
	private Integer gencurrank;
	private Integer sccurrank;
	private Integer stcurrank;
	private Integer bccurrank;
	private Integer mucurrank;
	private Integer obxcurrank;
	private Integer obhcurrank;
	private Integer lccurrank;
	private Integer oeccurrank;
	private Integer lctacurrank;
	private Integer communitycurrank;
	
	//new admission
	private String departmentId;
	private Integer webProgId;
	private String originalCode;
	private String originalName;
	private int originalId;
	private Boolean isMotherTongue;
	private Boolean isDisplayLanguageKnown;
	private Boolean isHeightWeight;
	private Boolean isDisplayTrainingCourse;
	private Boolean isAdditionalInfo;
	private Boolean isExtraDetails;
	private Boolean isSecondLanguage;
	private Boolean isFamilyBackground;
	private Boolean isLateralDetails;
	private Boolean isEntranceDetails;
	private Boolean isTransferCourse;
	private Boolean isTCDetails;
	private Boolean isExamCenterRequired;
	private Boolean isRegistrationNo;
	private Boolean isOpen;
	private String statusTextOnSubmisstionOfApplnOnline;
	private String statusTextOnSubmisstionOfApplnOffline;
	private String statusTextOnAcknowledgementOnline;
	private String statusTextOnAcknowledgementOffline;
	private Course courseBo;
	private Boolean blockMarkEntryQualifyingExam;
	//private List<CourseUniversityMarksSettingsTO> courseUnivMarksSettingsTO;
	
	private String dateTime;
	private String generalFee;
	private String casteFee;
	private String aided;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getGeneralFee() {
		return generalFee;
	}

	public void setGeneralFee(String generalFee) {
		this.generalFee = generalFee;
	}

	public String getCasteFee() {
		return casteFee;
	}

	public void setCasteFee(String casteFee) {
		this.casteFee = casteFee;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getProgramTypeCode() {
		return programTypeCode;
	}

	public void setProgramTypeCode(String programTypeCode) {
		this.programTypeCode = programTypeCode;
	}

	public ProgramTO getProgramTo() {
		return programTo;
	}

	public void setProgramTo(ProgramTO programTo) {
		this.programTo = programTo;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHasSecondLanguage() {
		return this.hasSecondLanguage;
	}

	public void setHasSecondLanguage(Boolean hasSecondLanguage) {
		this.hasSecondLanguage = hasSecondLanguage;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getMaxInTake() {
		return maxInTake;
	}

	public void setMaxInTake(Integer maxInTake) {
		this.maxInTake = maxInTake;
	}

	public String getIsAutonomous() {
		return isAutonomous;
	}

	public void setIsAutonomous(String isAutonomous) {
		this.isAutonomous = isAutonomous;
	}

	public List<SeatAllocationTO> getSeatAllocation() {
		return seatAllocation;
	}

	public void setSeatAllocation(List<SeatAllocationTO> seatAllocation) {
		this.seatAllocation = seatAllocation;
	}

	public int getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getIsWorkExperienceRequired() {
		return isWorkExperienceRequired;
	}

	public void setIsWorkExperienceRequired(String isWorkExperienceRequired) {
		this.isWorkExperienceRequired = isWorkExperienceRequired;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}


	public String getAutonomous() {
		return autonomous;
	}

	public void setAutonomous(String autonomous) {
		this.autonomous = autonomous;
	}

	public String getMaxIntake() {
		return maxIntake;
	}

	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
	}


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAmountField() {
		return amountField;
	}

	public void setAmountField(String amountField) {
		this.amountField = amountField;
	}

	public String getIsDetailMarkPrint() {
		return isDetailMarkPrint;
	}

	public void setIsDetailMarkPrint(String isDetailMarkPrint) {
		this.isDetailMarkPrint = isDetailMarkPrint;
	}

	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}

	public String getCertificateCourseName() {
		return certificateCourseName;
	}

	public boolean isWorkExpMandatory() {
		return isWorkExpMandatory;
	}

	public void setWorkExpMandatory(boolean isWorkExpMandatory) {
		this.isWorkExpMandatory = isWorkExpMandatory;
	}

	public boolean isAppearInOnline() {
		return isAppearInOnline;
	}

	public void setAppearInOnline(boolean isAppearInOnline) {
		this.isAppearInOnline = isAppearInOnline;
	}

	public boolean isApplicationProcessSms() {
		return isApplicationProcessSms;
	}

	public void setApplicationProcessSms(boolean isApplicationProcessSms) {
		this.isApplicationProcessSms = isApplicationProcessSms;
	}

	public boolean isOnlyForApplication() {
		return onlyForApplication;
	}

	public void setOnlyForApplication(boolean onlyForApplication) {
		this.onlyForApplication = onlyForApplication;
	}

	public String getCourseMarksCard() {
		return courseMarksCard;
	}

	public void setCourseMarksCard(String courseMarksCard) {
		this.courseMarksCard = courseMarksCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNameFull() {
		return bankNameFull;
	}

	public void setBankNameFull(String bankNameFull) {
		this.bankNameFull = bankNameFull;
	}

	public boolean isBankIncludeSection() {
		return bankIncludeSection;
	}

	public void setBankIncludeSection(boolean bankIncludeSection) {
		this.bankIncludeSection = bankIncludeSection;
	}

	public String getChecked1() {
		return checked1;
	}

	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}

	public String getTempChecked() {
		return tempChecked;
	}

	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}
	
	/*public int compareTo(CourseTO o) {
		if(o!=null && o.getProgramCode()!=null && this.programCode!=null){
			return this.programCode.compareTo(o.programCode);
		}else
			return 0;
	}
*/
	public CurrencyMasterTO getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(CurrencyMasterTO currencyId) {
		this.currencyId = currencyId;
	}

	public String getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}

	public BigDecimal getIntApplicationFees() {
		return intApplicationFees;
	}

	public void setIntApplicationFees(BigDecimal intApplicationFees) {
		this.intApplicationFees = intApplicationFees;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public boolean isTempChecked1() {
		return tempChecked1;
	}

	public void setTempChecked1(boolean tempChecked1) {
		this.tempChecked1 = tempChecked1;
	}

	public int getAssignedDeptId() {
		return assignedDeptId;
	}

	public void setAssignedDeptId(int assignedDeptId) {
		this.assignedDeptId = assignedDeptId;
	}

	public int getNoOfMidsemAttempts() {
		return noOfMidsemAttempts;
	}

	public void setNoOfMidsemAttempts(int noOfMidsemAttempts) {
		this.noOfMidsemAttempts = noOfMidsemAttempts;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getPrefNo() {
		return prefNo;
	}

	public void setPrefNo(String prefNo) {
		this.prefNo = prefNo;
	}

	public int getPrefId() {
		return prefId;
	}

	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	public String getlDate() {
		return lDate;
	}

	public void setlDate(String lDate) {
		this.lDate = lDate;
	}

	public Integer getTotseats() {
		return totseats;
	}

	public void setTotseats(Integer totseats) {
		this.totseats = totseats;
	}

	public Integer getScseat() {
		return scseat;
	}

	public void setScseat(Integer scseat) {
		this.scseat = scseat;
	}

	public Integer getStseat() {
		return stseat;
	}

	public void setStseat(Integer stseat) {
		this.stseat = stseat;
	}

	public Integer getBcseat() {
		return bcseat;
	}

	public void setBcseat(Integer bcseat) {
		this.bcseat = bcseat;
	}

	public Integer getMuseat() {
		return museat;
	}

	public void setMuseat(Integer museat) {
		this.museat = museat;
	}

	public Integer getObxseat() {
		return obxseat;
	}

	public void setObxseat(Integer obxseat) {
		this.obxseat = obxseat;
	}

	public Integer getObhseat() {
		return obhseat;
	}

	public void setObhseat(Integer obhseat) {
		this.obhseat = obhseat;
	}

	public Integer getLcseat() {
		return lcseat;
	}

	public void setLcseat(Integer lcseat) {
		this.lcseat = lcseat;
	}

	public Integer getGenseat() {
		return genseat;
	}

	public void setGenseat(Integer genseat) {
		this.genseat = genseat;
	}

	public Integer getOecseat() {
		return oecseat;
	}

	public void setOecseat(Integer oecseat) {
		this.oecseat = oecseat;
	}

	public Integer getRemainseats() {
		return remainseats;
	}

	public void setRemainseats(Integer remainseats) {
		this.remainseats = remainseats;
	}

	public Integer getRankcutoff() {
		return rankcutoff;
	}

	public void setRankcutoff(Integer rankcutoff) {
		this.rankcutoff = rankcutoff;
	}

	public Integer getSccount() {
		return sccount;
	}

	public void setSccount(Integer sccount) {
		this.sccount = sccount;
	}

	public Integer getStcount() {
		return stcount;
	}

	public void setStcount(Integer stcount) {
		this.stcount = stcount;
	}

	public Integer getBccount() {
		return bccount;
	}

	public void setBccount(Integer bccount) {
		this.bccount = bccount;
	}

	public Integer getMucount() {
		return mucount;
	}

	public void setMucount(Integer mucount) {
		this.mucount = mucount;
	}

	public Integer getObxcount() {
		return obxcount;
	}

	public void setObxcount(Integer obxcount) {
		this.obxcount = obxcount;
	}

	public Integer getObhcount() {
		return obhcount;
	}

	public void setObhcount(Integer obhcount) {
		this.obhcount = obhcount;
	}

	public Integer getLccount() {
		return lccount;
	}

	public void setLccount(Integer lccount) {
		this.lccount = lccount;
	}

	public Integer getOeccount() {
		return oeccount;
	}

	public void setOeccount(Integer oeccount) {
		this.oeccount = oeccount;
	}

	public Integer getGencount() {
		return gencount;
	}

	public void setGencount(Integer gencount) {
		this.gencount = gencount;
	}

	public Integer getTotcount() {
		return totcount;
	}

	public void setTotcount(Integer totcount) {
		this.totcount = totcount;
	}

	public Integer getScremain() {
		return scremain;
	}

	public void setScremain(Integer scremain) {
		this.scremain = scremain;
	}

	public Integer getStremain() {
		return stremain;
	}

	public void setStremain(Integer stremain) {
		this.stremain = stremain;
	}

	public Integer getBcremain() {
		return bcremain;
	}

	public void setBcremain(Integer bcremain) {
		this.bcremain = bcremain;
	}

	public Integer getMuremain() {
		return muremain;
	}

	public void setMuremain(Integer muremain) {
		this.muremain = muremain;
	}

	public Integer getObxremain() {
		return obxremain;
	}

	public void setObxremain(Integer obxremain) {
		this.obxremain = obxremain;
	}

	public Integer getObhremain() {
		return obhremain;
	}

	public void setObhremain(Integer obhremain) {
		this.obhremain = obhremain;
	}

	public Integer getLcremain() {
		return lcremain;
	}

	public void setLcremain(Integer lcremain) {
		this.lcremain = lcremain;
	}

	public Integer getOecremain() {
		return oecremain;
	}

	public void setOecremain(Integer oecremain) {
		this.oecremain = oecremain;
	}

	public Integer getGenremain() {
		return genremain;
	}

	public void setGenremain(Integer genremain) {
		this.genremain = genremain;
	}

	
	public boolean isScover() {
		return scover;
	}

	public void setScover(boolean scover) {
		this.scover = scover;
	}

	public boolean isStover() {
		return stover;
	}

	public void setStover(boolean stover) {
		this.stover = stover;
	}

	public boolean isBcover() {
		return bcover;
	}

	public void setBcover(boolean bcover) {
		this.bcover = bcover;
	}

	public boolean isMuover() {
		return muover;
	}

	public void setMuover(boolean muover) {
		this.muover = muover;
	}

	public boolean isObxover() {
		return obxover;
	}

	public void setObxover(boolean obxover) {
		this.obxover = obxover;
	}

	public boolean isObhover() {
		return obhover;
	}

	public void setObhover(boolean obhover) {
		this.obhover = obhover;
	}

	public boolean isLcover() {
		return lcover;
	}

	public void setLcover(boolean lcover) {
		this.lcover = lcover;
	}

	public boolean isOecover() {
		return oecover;
	}

	public void setOecover(boolean oecover) {
		this.oecover = oecover;
	}

	public boolean isGenover() {
		return genover;
	}

	public void setGenover(boolean genover) {
		this.genover = genover;
	}

	public Integer getGencurrank() {
		return gencurrank;
	}

	public void setGencurrank(Integer gencurrank) {
		this.gencurrank = gencurrank;
	}

	public Integer getHighscrank() {
		return highscrank;
	}

	public void setHighscrank(Integer highscrank) {
		this.highscrank = highscrank;
	}

	public Integer getHighstrank() {
		return highstrank;
	}

	public void setHighstrank(Integer highstrank) {
		this.highstrank = highstrank;
	}

	public Integer getHighbcrank() {
		return highbcrank;
	}

	public void setHighbcrank(Integer highbcrank) {
		this.highbcrank = highbcrank;
	}

	public Integer getHighmurank() {
		return highmurank;
	}

	public void setHighmurank(Integer highmurank) {
		this.highmurank = highmurank;
	}

	public Integer getHighobxrank() {
		return highobxrank;
	}

	public void setHighobxrank(Integer highobxrank) {
		this.highobxrank = highobxrank;
	}

	public Integer getHighobhrank() {
		return highobhrank;
	}

	public void setHighobhrank(Integer highobhrank) {
		this.highobhrank = highobhrank;
	}

	public Integer getHighlcrank() {
		return highlcrank;
	}

	public void setHighlcrank(Integer highlcrank) {
		this.highlcrank = highlcrank;
	}

	public Integer getHighoecrank() {
		return highoecrank;
	}

	public void setHighoecrank(Integer highoecrank) {
		this.highoecrank = highoecrank;
	}

	public Integer getHighgenrank() {
		return highgenrank;
	}

	public void setHighgenrank(Integer highgenrank) {
		this.highgenrank = highgenrank;
	}

	public Integer getLctaseat() {
		return lctaseat;
	}

	public void setLctaseat(Integer lctaseat) {
		this.lctaseat = lctaseat;
	}

	public Integer getLctacount() {
		return lctacount;
	}

	public void setLctacount(Integer lctacount) {
		this.lctacount = lctacount;
	}

	public Integer getLctaremain() {
		return lctaremain;
	}

	public void setLctaremain(Integer lctaremain) {
		this.lctaremain = lctaremain;
	}

	public Integer getHighlctarank() {
		return highlctarank;
	}

	public void setHighlctarank(Integer highlctarank) {
		this.highlctarank = highlctarank;
	}

	public boolean isLctaover() {
		return lctaover;
	}

	public void setLctaover(boolean lctaover) {
		this.lctaover = lctaover;
	}

	public Integer getSccurrank() {
		return sccurrank;
	}

	public void setSccurrank(Integer sccurrank) {
		this.sccurrank = sccurrank;
	}

	public Integer getStcurrank() {
		return stcurrank;
	}

	public void setStcurrank(Integer stcurrank) {
		this.stcurrank = stcurrank;
	}

	public Integer getBccurrank() {
		return bccurrank;
	}

	public void setBccurrank(Integer bccurrank) {
		this.bccurrank = bccurrank;
	}

	public Integer getMucurrank() {
		return mucurrank;
	}

	public void setMucurrank(Integer mucurrank) {
		this.mucurrank = mucurrank;
	}

	public Integer getObxcurrank() {
		return obxcurrank;
	}

	public void setObxcurrank(Integer obxcurrank) {
		this.obxcurrank = obxcurrank;
	}

	public Integer getObhcurrank() {
		return obhcurrank;
	}

	public void setObhcurrank(Integer obhcurrank) {
		this.obhcurrank = obhcurrank;
	}

	public Integer getLccurrank() {
		return lccurrank;
	}

	public void setLccurrank(Integer lccurrank) {
		this.lccurrank = lccurrank;
	}

	public Integer getOeccurrank() {
		return oeccurrank;
	}

	public void setOeccurrank(Integer oeccurrank) {
		this.oeccurrank = oeccurrank;
	}

	public Integer getLctacurrank() {
		return lctacurrank;
	}

	public void setLctacurrank(Integer lctacurrank) {
		this.lctacurrank = lctacurrank;
	}

	public void setCommunityremain(Integer communityremain) {
		this.communityremain = communityremain;
	}

	public Integer getCommunityremain() {
		return communityremain;
	}

	public void setHighcommunityrank(Integer highcommunityrank) {
		this.highcommunityrank = highcommunityrank;
	}

	public Integer getHighcommunityrank() {
		return highcommunityrank;
	}

	public void setCommunityover(boolean communityover) {
		this.communityover = communityover;
	}

	public boolean isCommunityover() {
		return communityover;
	}

	public void setCommunitycurrank(Integer communitycurrank) {
		this.communitycurrank = communitycurrank;
	}

	public Integer getCommunitycurrank() {
		return communitycurrank;
	}

//raghu
	
	public int compareTo(CourseTO o) {
		if(o!=null && o.getPrefNo()!=null && this.prefNo!=null){
			return this.prefNo.compareTo(o.prefNo);
		}else
			return 0;
	}



	public Integer getWebProgId() {
		return webProgId;
	}

	public void setWebProgId(Integer webProgId) {
		this.webProgId = webProgId;
	}

	public String getOriginalCode() {
		return originalCode;
	}

	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public int getOriginalId() {
		return originalId;
	}

	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}

	public Boolean getIsMotherTongue() {
		return isMotherTongue;
	}

	public void setIsMotherTongue(Boolean isMotherTongue) {
		this.isMotherTongue = isMotherTongue;
	}

	public Boolean getIsDisplayLanguageKnown() {
		return isDisplayLanguageKnown;
	}

	public void setIsDisplayLanguageKnown(Boolean isDisplayLanguageKnown) {
		this.isDisplayLanguageKnown = isDisplayLanguageKnown;
	}

	public Boolean getIsHeightWeight() {
		return isHeightWeight;
	}

	public void setIsHeightWeight(Boolean isHeightWeight) {
		this.isHeightWeight = isHeightWeight;
	}

	public Boolean getIsDisplayTrainingCourse() {
		return isDisplayTrainingCourse;
	}

	public void setIsDisplayTrainingCourse(Boolean isDisplayTrainingCourse) {
		this.isDisplayTrainingCourse = isDisplayTrainingCourse;
	}

	public Boolean getIsAdditionalInfo() {
		return isAdditionalInfo;
	}

	public void setIsAdditionalInfo(Boolean isAdditionalInfo) {
		this.isAdditionalInfo = isAdditionalInfo;
	}

	public Boolean getIsExtraDetails() {
		return isExtraDetails;
	}

	public void setIsExtraDetails(Boolean isExtraDetails) {
		this.isExtraDetails = isExtraDetails;
	}

	public Boolean getIsSecondLanguage() {
		return isSecondLanguage;
	}

	public void setIsSecondLanguage(Boolean isSecondLanguage) {
		this.isSecondLanguage = isSecondLanguage;
	}

	public Boolean getIsFamilyBackground() {
		return isFamilyBackground;
	}

	public void setIsFamilyBackground(Boolean isFamilyBackground) {
		this.isFamilyBackground = isFamilyBackground;
	}

	public Boolean getIsLateralDetails() {
		return isLateralDetails;
	}

	public void setIsLateralDetails(Boolean isLateralDetails) {
		this.isLateralDetails = isLateralDetails;
	}

	public Boolean getIsEntranceDetails() {
		return isEntranceDetails;
	}

	public void setIsEntranceDetails(Boolean isEntranceDetails) {
		this.isEntranceDetails = isEntranceDetails;
	}

	public Boolean getIsTransferCourse() {
		return isTransferCourse;
	}

	public void setIsTransferCourse(Boolean isTransferCourse) {
		this.isTransferCourse = isTransferCourse;
	}

	public Boolean getIsTCDetails() {
		return isTCDetails;
	}

	public void setIsTCDetails(Boolean isTCDetails) {
		this.isTCDetails = isTCDetails;
	}

	public Boolean getIsExamCenterRequired() {
		return isExamCenterRequired;
	}

	public void setIsExamCenterRequired(Boolean isExamCenterRequired) {
		this.isExamCenterRequired = isExamCenterRequired;
	}

	public Boolean getIsRegistrationNo() {
		return isRegistrationNo;
	}

	public void setIsRegistrationNo(Boolean isRegistrationNo) {
		this.isRegistrationNo = isRegistrationNo;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getStatusTextOnSubmisstionOfApplnOnline() {
		return statusTextOnSubmisstionOfApplnOnline;
	}

	public void setStatusTextOnSubmisstionOfApplnOnline(
			String statusTextOnSubmisstionOfApplnOnline) {
		this.statusTextOnSubmisstionOfApplnOnline = statusTextOnSubmisstionOfApplnOnline;
	}

	public String getStatusTextOnSubmisstionOfApplnOffline() {
		return statusTextOnSubmisstionOfApplnOffline;
	}

	public void setStatusTextOnSubmisstionOfApplnOffline(
			String statusTextOnSubmisstionOfApplnOffline) {
		this.statusTextOnSubmisstionOfApplnOffline = statusTextOnSubmisstionOfApplnOffline;
	}

	public String getStatusTextOnAcknowledgementOnline() {
		return statusTextOnAcknowledgementOnline;
	}

	public void setStatusTextOnAcknowledgementOnline(
			String statusTextOnAcknowledgementOnline) {
		this.statusTextOnAcknowledgementOnline = statusTextOnAcknowledgementOnline;
	}

	public String getStatusTextOnAcknowledgementOffline() {
		return statusTextOnAcknowledgementOffline;
	}

	public void setStatusTextOnAcknowledgementOffline(
			String statusTextOnAcknowledgementOffline) {
		this.statusTextOnAcknowledgementOffline = statusTextOnAcknowledgementOffline;
	}

	public Course getCourseBo() {
		return courseBo;
	}

	public void setCourseBo(Course courseBo) {
		this.courseBo = courseBo;
	}

	public Boolean getBlockMarkEntryQualifyingExam() {
		return blockMarkEntryQualifyingExam;
	}

	public void setBlockMarkEntryQualifyingExam(Boolean blockMarkEntryQualifyingExam) {
		this.blockMarkEntryQualifyingExam = blockMarkEntryQualifyingExam;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getAided() {
		return aided;
	}

	public void setAided(String aided) {
		this.aided = aided;
	}
	

	
}