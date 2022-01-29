package com.kp.cms.bo.employee;

public class EmpFilmVideosDocBO {
	
	private int id;
	private String title;
	private String subtitles;
	private String genre;
	private String credits;
	private String runningTime;
	private String discFormat;
	private String technicalFormat;
	private String audioFormat;
	private String language;
	private String aspectRatio;
	private String producer;
	private String copyrights;
	private Boolean isFilmVideoDoc;
	
	public EmpFilmVideosDocBO() {
		super();
	}

	public EmpFilmVideosDocBO(int id) {
		super();
		this.id = id;
	}
	
	public EmpFilmVideosDocBO(String title, String subtitles, String genre,
			String credits, String runningTime, String discFormat,
			String technicalFormat, String audioFormat, String language,
			String aspectRatio, String producer, String copyrights,
			Boolean isFilmVideoDoc) {
		super();
		this.title = title;
		this.subtitles = subtitles;
		this.genre = genre;
		this.credits = credits;
		this.runningTime = runningTime;
		this.discFormat = discFormat;
		this.technicalFormat = technicalFormat;
		this.audioFormat = audioFormat;
		this.language = language;
		this.aspectRatio = aspectRatio;
		this.producer = producer;
		this.copyrights = copyrights;
		this.isFilmVideoDoc = isFilmVideoDoc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public Boolean getIsFilmVideoDoc() {
		return isFilmVideoDoc;
	}

	public void setIsFilmVideoDoc(Boolean isFilmVideoDoc) {
		this.isFilmVideoDoc = isFilmVideoDoc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
