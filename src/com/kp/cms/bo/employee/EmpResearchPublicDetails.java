package com.kp.cms.bo.employee;

import java.util.Date;

import com.kp.cms.bo.admin.Employee;
	
	public class EmpResearchPublicDetails implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String investigators;
	private String sponsors;
	private String abstractObjectives;
	private String authorName;
	private String language;
	private String placePublication;
	private String isbn;
	private String totalPages;
	private String monthYear;
	private String companyInstitution;
	private String nameJournal;
	private String volumeNumber;
	private String issueNumber;
	private String pagesFrom;
	private String pagesTo;
	private String editorsName;
	private String titleChapterArticle;
	private String url;
	private String numberOfComments;
	private String subject;
	private String subtitles;
	private String genre;
	private String credits;
	private String runningTime;
	private String discFormat;
	private String technicalFormat;
	private String audioFormat;
	
	private String aspectRatio;
	private String producer;
	private String copyrights;
	private String namePeriodical;
	private String nameTalksPresentation;
	private String nameConferencesSeminar;
	private String abstracts;
	private String caseNoteWorkPaper;
	
	private String nameOrganisers;
	private String resoursePerson;
	private String nameStudent;
	private String nameGuide;
	
	private String approverComment;
	private String doi;
	private Date approvedDate;
	private Date entryCreateDate;
	
	private Employee approverId;
	private Employee employeeId;
	//private EmpResearchPublicMaster empResPubMasterId;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Boolean isArticleJournal;
	private Boolean isArticleInPeriodicals;
	private Boolean isBlog;
	private Boolean isBookMonographs;
	private Boolean isCasesNoteWorking;
	private Boolean isChapterArticleBook;
	private Boolean isConferencePresentation;
	private Boolean isInvitedTalk;
	private Boolean isFilmVideoDoc;
	private Boolean isResearchProject;
	private Boolean isOwnPhdMphilThesis;
	private Boolean isPhdMPhilThesisGuided;
	private Boolean isSeminarOrganized;
	private Boolean isWorkshopTraining;
	private Boolean isSeminarAttended;
	private Boolean isAwardsAchievementsOthers;
	private Date dateMonthYear;
	private Boolean isApproved;
	private String departmentInstitution;
	private String impactFactor;
	private Date dateSent;
	private Date dateAccepted;
	private Date datePublished;
	private String name;
	private String place;
	private String description;
	private String rejectReason;
	private Date rejectDate;
	private Boolean isRejected;
	private String internalExternal;
	private String type;
	private String academicYear;
	private String coAuthored;
	private String guidedAdjudicated;
	private Date submissionDate;
	private String amountGranted;
	private String organisationAwarded;
	private String nameOfPgm;
	private String otherText;
	private String typeOfPgm;
	private Date endOfPgm;
	private Boolean isEmployee;
	private GuestFaculty guestId;
	
	public EmpResearchPublicDetails() {
	}

	public EmpResearchPublicDetails(int id) {
		this.id = id;
	}
	
	
	public EmpResearchPublicDetails(int id, String title, String investigators,
			String sponsors, String abstractObjectives, String authorName,
			String language, String placePublication, String isbn,
			String totalPages, String monthYear, String companyInstitution,
			String nameJournal, String volumeNumber, String issueNumber,
			String pagesFrom, String pagesTo, String editorsName,
			String titleChapterArticle, String url, String numberOfComments,
			String subject, String subtitles, String genre, String credits,
			String runningTime, String discFormat, String technicalFormat,
			String audioFormat, String aspectRatio, String producer,
			String copyrights, String namePeriodical,
			String nameTalksPresentation, String nameConferencesSeminar,
			String abstracts, String caseNoteWorkPaper, String nameOrganisers,
			String resoursePerson, String nameStudent, String nameGuide,
			String approverComment, String doi, Date approvedDate,
			Date entryCreateDate, Employee approverId, Employee employeeId,
			String createdBy,
			Date createdDate, String modifiedBy, Date lastModifiedDate,
			Boolean isActive, Boolean isArticleJournal,
			Boolean isArticleInPeriodicals, Boolean isBlog,
			Boolean isBookMonographs, Boolean isCasesNoteWorking,
			Boolean isChapterArticleBook, Boolean isConferencePresentation,
			Boolean isFilmVideoDoc, Boolean isResearchProject,
			Boolean isOwnPhdMphilThesis, Boolean isPhdMPhilThesisGuided,
			Boolean isSeminarOrganized,Boolean isAwardsAchievementsOthers,Boolean isWorkshopTraining,
			Boolean isSeminarAttended, Date dateMonthYear, Boolean isApproved,
			String departmentInstitution, String impactFactor, Date dateSent,
			Date dateAccepted,Date datePublished,String name, String place, String description,
			String rejectReason,Date rejectDate, Boolean isRejected,String internalExternal, String type,
			String academicYear, String coAuthored, String guidedAdjudicated, Date submissionDate, String amountGranted,
			String nameOfPgm, String otherText, String typeOfPgm, Date endOfPgm, Boolean isEmployee, GuestFaculty guestId) {
		super();
		this.id = id;
		this.title = title;
		this.investigators = investigators;
		this.sponsors = sponsors;
		this.abstractObjectives = abstractObjectives;
		this.authorName = authorName;
		this.language = language;
		this.placePublication = placePublication;
		this.isbn = isbn;
		this.totalPages = totalPages;
		this.monthYear = monthYear;
		this.companyInstitution = companyInstitution;
		this.nameJournal = nameJournal;
		this.volumeNumber = volumeNumber;
		this.issueNumber = issueNumber;
		this.pagesFrom = pagesFrom;
		this.pagesTo = pagesTo;
		this.editorsName = editorsName;
		this.titleChapterArticle = titleChapterArticle;
		this.url = url;
		this.numberOfComments = numberOfComments;
		this.subject = subject;
		this.subtitles = subtitles;
		this.genre = genre;
		this.credits = credits;
		this.runningTime = runningTime;
		this.discFormat = discFormat;
		this.technicalFormat = technicalFormat;
		this.audioFormat = audioFormat;
		this.aspectRatio = aspectRatio;
		this.producer = producer;
		this.copyrights = copyrights;
		this.namePeriodical = namePeriodical;
		this.nameTalksPresentation = nameTalksPresentation;
		this.nameConferencesSeminar = nameConferencesSeminar;
		this.abstracts = abstracts;
		this.caseNoteWorkPaper = caseNoteWorkPaper;
		this.nameOrganisers = nameOrganisers;
		this.resoursePerson = resoursePerson;
		this.nameStudent = nameStudent;
		this.nameGuide = nameGuide;
		this.approverComment = approverComment;
		this.doi = doi;
		this.approvedDate = approvedDate;
		this.entryCreateDate = entryCreateDate;
		this.approverId = approverId;
		this.employeeId = employeeId;
	
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.isArticleJournal = isArticleJournal;
		this.isArticleInPeriodicals = isArticleInPeriodicals;
		this.isBlog = isBlog;
		this.isBookMonographs = isBookMonographs;
		this.isCasesNoteWorking = isCasesNoteWorking;
		this.isChapterArticleBook = isChapterArticleBook;
		this.isConferencePresentation = isConferencePresentation;
		this.isFilmVideoDoc = isFilmVideoDoc;
		this.isResearchProject = isResearchProject;
		this.isOwnPhdMphilThesis = isOwnPhdMphilThesis;
		this.isPhdMPhilThesisGuided = isPhdMPhilThesisGuided;
		this.isSeminarOrganized = isSeminarOrganized;
		this.isAwardsAchievementsOthers= isAwardsAchievementsOthers;
		this.isSeminarAttended= isSeminarAttended;
		this.isWorkshopTraining= isWorkshopTraining;
		this.isApproved=isApproved;
		this.dateMonthYear=dateMonthYear;
		this.dateAccepted=dateAccepted;
		this.datePublished=datePublished;
		this.dateSent=dateSent;
		this.departmentInstitution=departmentInstitution;
		this.impactFactor=impactFactor;
		this.name=name;
		this.place=place;
		this.description=description;
		this.rejectReason=rejectReason;
		this.rejectDate=rejectDate;
		this.isRejected=isRejected;
		this.amountGranted=amountGranted;
		this.type=type;
		this.academicYear=academicYear;
		this.internalExternal=internalExternal;
		this.coAuthored=coAuthored;
		this.guidedAdjudicated=guidedAdjudicated;
		this.submissionDate=submissionDate;
		this.nameOfPgm=nameOfPgm;
		this.otherText=otherText;
		this.typeOfPgm=typeOfPgm;
		this.endOfPgm=endOfPgm;
		this.isEmployee=isEmployee;
		this.guestId=guestId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInvestigators() {
		return investigators;
	}
	public void setInvestigators(String investigators) {
		this.investigators = investigators;
	}
	public String getSponsors() {
		return sponsors;
	}
	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}
	public String getAbstractObjectives() {
		return abstractObjectives;
	}
	public void setAbstractObjectives(String abstractObjectives) {
		this.abstractObjectives = abstractObjectives;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPlacePublication() {
		return placePublication;
	}
	public void setPlacePublication(String placePublication) {
		this.placePublication = placePublication;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getCompanyInstitution() {
		return companyInstitution;
	}
	public void setCompanyInstitution(String companyInstitution) {
		this.companyInstitution = companyInstitution;
	}
	public String getNameJournal() {
		return nameJournal;
	}
	public void setNameJournal(String nameJournal) {
		this.nameJournal = nameJournal;
	}
	public String getVolumeNumber() {
		return volumeNumber;
	}
	public void setVolumeNumber(String volumeNumber) {
		this.volumeNumber = volumeNumber;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getPagesFrom() {
		return pagesFrom;
	}
	public void setPagesFrom(String pagesFrom) {
		this.pagesFrom = pagesFrom;
	}
	public String getPagesTo() {
		return pagesTo;
	}
	public void setPagesTo(String pagesTo) {
		this.pagesTo = pagesTo;
	}
	public String getEditorsName() {
		return editorsName;
	}
	public void setEditorsName(String editorsName) {
		this.editorsName = editorsName;
	}
	public String getTitleChapterArticle() {
		return titleChapterArticle;
	}
	public void setTitleChapterArticle(String titleChapterArticle) {
		this.titleChapterArticle = titleChapterArticle;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNumberOfComments() {
		return numberOfComments;
	}
	public void setNumberOfComments(String numberOfComments) {
		this.numberOfComments = numberOfComments;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubtitles() {
		return subtitles;
	}
	public void setSubtitles(String subtitles) {
		this.subtitles = subtitles;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public String getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}
	public String getDiscFormat() {
		return discFormat;
	}
	public void setDiscFormat(String discFormat) {
		this.discFormat = discFormat;
	}
	public String getTechnicalFormat() {
		return technicalFormat;
	}
	public void setTechnicalFormat(String technicalFormat) {
		this.technicalFormat = technicalFormat;
	}
	public String getAudioFormat() {
		return audioFormat;
	}
	public void setAudioFormat(String audioFormat) {
		this.audioFormat = audioFormat;
	}
	public String getAspectRatio() {
		return aspectRatio;
	}
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCopyrights() {
		return copyrights;
	}
	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}
	public String getNamePeriodical() {
		return namePeriodical;
	}
	public void setNamePeriodical(String namePeriodical) {
		this.namePeriodical = namePeriodical;
	}
	public String getNameTalksPresentation() {
		return nameTalksPresentation;
	}
	public void setNameTalksPresentation(String nameTalksPresentation) {
		this.nameTalksPresentation = nameTalksPresentation;
	}
	public String getNameConferencesSeminar() {
		return nameConferencesSeminar;
	}
	public void setNameConferencesSeminar(String nameConferencesSeminar) {
		this.nameConferencesSeminar = nameConferencesSeminar;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getCaseNoteWorkPaper() {
		return caseNoteWorkPaper;
	}
	public void setCaseNoteWorkPaper(String caseNoteWorkPaper) {
		this.caseNoteWorkPaper = caseNoteWorkPaper;
	}
	public String getNameOrganisers() {
		return nameOrganisers;
	}
	public void setNameOrganisers(String nameOrganisers) {
		this.nameOrganisers = nameOrganisers;
	}
	public String getResoursePerson() {
		return resoursePerson;
	}
	public void setResoursePerson(String resoursePerson) {
		this.resoursePerson = resoursePerson;
	}
	public String getNameStudent() {
		return nameStudent;
	}
	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}
	public String getNameGuide() {
		return nameGuide;
	}
	public void setNameGuide(String nameGuide) {
		this.nameGuide = nameGuide;
	}
	
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	public Employee getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Employee getApproverId() {
		return approverId;
	}

	public void setApproverId(Employee approverId) {
		this.approverId = approverId;
	}

	public Date getEntryCreateDate() {
		return entryCreateDate;
	}

	public void setEntryCreateDate(Date entryCreateDate) {
		this.entryCreateDate = entryCreateDate;
	}

	public Boolean getIsArticleJournal() {
		return isArticleJournal;
	}

	public void setIsArticleJournal(Boolean isArticleJournal) {
		this.isArticleJournal = isArticleJournal;
	}

	public Boolean getIsArticleInPeriodicals() {
		return isArticleInPeriodicals;
	}

	public void setIsArticleInPeriodicals(Boolean isArticleInPeriodicals) {
		this.isArticleInPeriodicals = isArticleInPeriodicals;
	}

	public Boolean getIsBlog() {
		return isBlog;
	}

	public void setIsBlog(Boolean isBlog) {
		this.isBlog = isBlog;
	}

	public Boolean getIsBookMonographs() {
		return isBookMonographs;
	}

	public void setIsBookMonographs(Boolean isBookMonographs) {
		this.isBookMonographs = isBookMonographs;
	}

	public Boolean getIsCasesNoteWorking() {
		return isCasesNoteWorking;
	}

	public void setIsCasesNoteWorking(Boolean isCasesNoteWorking) {
		this.isCasesNoteWorking = isCasesNoteWorking;
	}

	public Boolean getIsChapterArticleBook() {
		return isChapterArticleBook;
	}

	public void setIsChapterArticleBook(Boolean isChapterArticleBook) {
		this.isChapterArticleBook = isChapterArticleBook;
	}

	public Boolean getIsConferencePresentation() {
		return isConferencePresentation;
	}

	public void setIsConferencePresentation(Boolean isConferencePresentation) {
		this.isConferencePresentation = isConferencePresentation;
	}

	public Boolean getIsFilmVideoDoc() {
		return isFilmVideoDoc;
	}

	public void setIsFilmVideoDoc(Boolean isFilmVideoDoc) {
		this.isFilmVideoDoc = isFilmVideoDoc;
	}

	public Boolean getIsResearchProject() {
		return isResearchProject;
	}

	public void setIsResearchProject(Boolean isResearchProject) {
		this.isResearchProject = isResearchProject;
	}

	public Boolean getIsOwnPhdMphilThesis() {
		return isOwnPhdMphilThesis;
	}

	public void setIsOwnPhdMphilThesis(Boolean isOwnPhdMphilThesis) {
		this.isOwnPhdMphilThesis = isOwnPhdMphilThesis;
	}

	public Boolean getIsPhdMPhilThesisGuided() {
		return isPhdMPhilThesisGuided;
	}

	public void setIsPhdMPhilThesisGuided(Boolean isPhdMPhilThesisGuided) {
		this.isPhdMPhilThesisGuided = isPhdMPhilThesisGuided;
	}

	public Boolean getIsSeminarOrganized() {
		return isSeminarOrganized;
	}

	public void setIsSeminarOrganized(Boolean isSeminarOrganized) {
		this.isSeminarOrganized = isSeminarOrganized;
	}

	public Date getDateMonthYear() {
		return dateMonthYear;
	}

	public void setDateMonthYear(Date dateMonthYear) {
		this.dateMonthYear = dateMonthYear;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Boolean getIsInvitedTalk() {
		return isInvitedTalk;
	}

	public void setIsInvitedTalk(Boolean isInvitedTalk) {
		this.isInvitedTalk = isInvitedTalk;
	}

	public String getDepartmentInstitution() {
		return departmentInstitution;
	}

	public void setDepartmentInstitution(String departmentInstitution) {
		this.departmentInstitution = departmentInstitution;
	}

	public String getImpactFactor() {
		return impactFactor;
	}

	public void setImpactFactor(String impactFactor) {
		this.impactFactor = impactFactor;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Date getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(Date dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public Boolean getIsWorkshopTraining() {
		return isWorkshopTraining;
	}

	public void setIsWorkshopTraining(Boolean isWorkshopTraining) {
		this.isWorkshopTraining = isWorkshopTraining;
	}

	public Boolean getIsSeminarAttended() {
		return isSeminarAttended;
	}

	public void setIsSeminarAttended(Boolean isSeminarAttended) {
		this.isSeminarAttended = isSeminarAttended;
	}

	public Boolean getIsAwardsAchievementsOthers() {
		return isAwardsAchievementsOthers;
	}

	public void setIsAwardsAchievementsOthers(Boolean isAwardsAchievementsOthers) {
		this.isAwardsAchievementsOthers = isAwardsAchievementsOthers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public String getInternalExternal() {
		return internalExternal;
	}

	public void setInternalExternal(String internalExternal) {
		this.internalExternal = internalExternal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getCoAuthored() {
		return coAuthored;
	}

	public void setCoAuthored(String coAuthored) {
		this.coAuthored = coAuthored;
	}

	public String getGuidedAdjudicated() {
		return guidedAdjudicated;
	}

	public void setGuidedAdjudicated(String guidedAdjudicated) {
		this.guidedAdjudicated = guidedAdjudicated;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getAmountGranted() {
		return amountGranted;
	}

	public void setAmountGranted(String amountGranted) {
		this.amountGranted = amountGranted;
	}

	public String getOrganisationAwarded() {
		return organisationAwarded;
	}

	public void setOrganisationAwarded(String organisationAwarded) {
		this.organisationAwarded = organisationAwarded;
	}

	public String getNameOfPgm() {
		return nameOfPgm;
	}

	public void setNameOfPgm(String nameOfPgm) {
		this.nameOfPgm = nameOfPgm;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getTypeOfPgm() {
		return typeOfPgm;
	}

	public void setTypeOfPgm(String typeOfPgm) {
		this.typeOfPgm = typeOfPgm;
	}

	public Date getEndOfPgm() {
		return endOfPgm;
	}

	public void setEndOfPgm(Date endOfPgm) {
		this.endOfPgm = endOfPgm;
	}

	public Boolean getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public GuestFaculty getGuestId() {
		return guestId;
	}

	public void setGuestId(GuestFaculty guestId) {
		this.guestId = guestId;
	}

	

}
