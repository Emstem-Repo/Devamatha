package com.kp.cms.to.employee;
	import java.util.List;

	public class GuestFacultyInfoTo {
					
			private List<GuestPreviousExperienceTO> experiences;
			private List<GuestPreviousExperienceTO> teachingExperience;
			private List<EmpQualificationLevelTo> empQualificationLevelTos;
			private List<EmpQualificationLevelTo> empQualificationFixedTo;
			private List<GuestImagesTO> empImages;
			private List<GuestPreviousChristWorkDetailsTO> previousworkDetails;
			public List<GuestPreviousExperienceTO> getExperiences() {
				return experiences;
			}
			public void setExperiences(List<GuestPreviousExperienceTO> experiences) {
				this.experiences = experiences;
			}
			public List<GuestPreviousExperienceTO> getTeachingExperience() {
				return teachingExperience;
			}
			public void setTeachingExperience(
					List<GuestPreviousExperienceTO> teachingExperience) {
				this.teachingExperience = teachingExperience;
			}
			
			public List<GuestImagesTO> getEmpImages() {
				return empImages;
			}
			public void setEmpImages(List<GuestImagesTO> empImages) {
				this.empImages = empImages;
			}
			public List<EmpQualificationLevelTo> getEmpQualificationLevelTos() {
				return empQualificationLevelTos;
			}
			public void setEmpQualificationLevelTos(
					List<EmpQualificationLevelTo> empQualificationLevelTos) {
				this.empQualificationLevelTos = empQualificationLevelTos;
			}
			public List<EmpQualificationLevelTo> getEmpQualificationFixedTo() {
				return empQualificationFixedTo;
			}
			public void setEmpQualificationFixedTo(
					List<EmpQualificationLevelTo> empQualificationFixedTo) {
				this.empQualificationFixedTo = empQualificationFixedTo;
			}
			public List<GuestPreviousChristWorkDetailsTO> getPreviousworkDetails() {
				return previousworkDetails;
			}
			public void setPreviousworkDetails(
					List<GuestPreviousChristWorkDetailsTO> previousworkDetails) {
				this.previousworkDetails = previousworkDetails;
			}
			
			
				
	}