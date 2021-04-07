package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.to.admission.PromoteSubjectsUploadTo;

public class PromoteSubjectsUploadHelper {
	private static volatile PromoteSubjectsUploadHelper helper = null;
	public static PromoteSubjectsUploadHelper getInstance(){
		if(helper == null){
			helper = new PromoteSubjectsUploadHelper();
			return helper;
		}
		return helper;
	}
	public List<PromoteSubjects> convertTOToBO( List<PromoteSubjectsUploadTo> promoteSubjectsList) {
		List<PromoteSubjects> promoteSubjectList = new ArrayList<PromoteSubjects>();
		if(promoteSubjectsList!=null){
			Iterator<PromoteSubjectsUploadTo> iterator = promoteSubjectsList.iterator();
			while (iterator.hasNext()) {
				PromoteSubjectsUploadTo promoteSubjectsUploadTo = (PromoteSubjectsUploadTo) iterator .next();
				PromoteSubjects promoteSubjects = new PromoteSubjects();
				if(promoteSubjectsUploadTo.getAcademicYear()!=null && !promoteSubjectsUploadTo.getAcademicYear().isEmpty()){
					if(promoteSubjectsUploadTo.getAcademicYear()!=null && !promoteSubjectsUploadTo.getAcademicYear().isEmpty()){
						promoteSubjects.setAcademicYear(Integer.parseInt(promoteSubjectsUploadTo.getAcademicYear()));
					}
					if(promoteSubjectsUploadTo.getClassCode()!=null && !promoteSubjectsUploadTo.getClassCode().isEmpty()){
						promoteSubjects.setClassCode(promoteSubjectsUploadTo.getClassCode());
					}
					if(promoteSubjectsUploadTo.getSection()!=null && !promoteSubjectsUploadTo.getSection().isEmpty()){
						promoteSubjects.setSection(promoteSubjectsUploadTo.getSection());
					}
					if(promoteSubjectsUploadTo.getStartNo()!=null && !promoteSubjectsUploadTo.getStartNo().isEmpty()){
						promoteSubjects.setStartNo(Integer.parseInt(promoteSubjectsUploadTo.getStartNo()));
					}
					if(promoteSubjectsUploadTo.getEndNo()!=null && !promoteSubjectsUploadTo.getEndNo().isEmpty()){
						promoteSubjects.setEndNo(Integer.parseInt(promoteSubjectsUploadTo.getEndNo()));
					}
					if(promoteSubjectsUploadTo.getGroupCode()!=null && !promoteSubjectsUploadTo.getGroupCode().isEmpty()){
						promoteSubjects.setGroupCode(promoteSubjectsUploadTo.getGroupCode());
					}
					if(promoteSubjectsUploadTo.getSubCde1()!=null && !promoteSubjectsUploadTo.getSubCde1().isEmpty()){
						promoteSubjects.setSubCde1(promoteSubjectsUploadTo.getSubCde1());
					}
					if(promoteSubjectsUploadTo.getSubCde3()!=null && !promoteSubjectsUploadTo.getSubCde3().isEmpty()){
						promoteSubjects.setSubCde3(promoteSubjectsUploadTo.getSubCde3());
					}
					if(promoteSubjectsUploadTo.getSubCde4()!=null && !promoteSubjectsUploadTo.getSubCde4().isEmpty()){
						promoteSubjects.setSubCde4(promoteSubjectsUploadTo.getSubCde4());
					}
					if(promoteSubjectsUploadTo.getSubCde5()!=null && !promoteSubjectsUploadTo.getSubCde5().isEmpty()){
						promoteSubjects.setSubCde5(promoteSubjectsUploadTo.getSubCde5());
					}
					if(promoteSubjectsUploadTo.getSubCde6()!=null && !promoteSubjectsUploadTo.getSubCde6().isEmpty()){
						promoteSubjects.setSubCde6(promoteSubjectsUploadTo.getSubCde6());
					}
					if(promoteSubjectsUploadTo.getSubCde7()!=null && !promoteSubjectsUploadTo.getSubCde7().isEmpty()){
						promoteSubjects.setSubCde7(promoteSubjectsUploadTo.getSubCde7());
					}
					if(promoteSubjectsUploadTo.getSubject1()!=null && !promoteSubjectsUploadTo.getSubject1().isEmpty()){
						promoteSubjects.setSubject1(promoteSubjectsUploadTo.getSubject1());
					}
					if(promoteSubjectsUploadTo.getSubject3()!=null && !promoteSubjectsUploadTo.getSubject3().isEmpty()){
						promoteSubjects.setSubject3(promoteSubjectsUploadTo.getSubject3());
					}
					if(promoteSubjectsUploadTo.getSubject4()!=null && !promoteSubjectsUploadTo.getSubject4().isEmpty()){
						promoteSubjects.setSubject4(promoteSubjectsUploadTo.getSubject4());
					}
					if(promoteSubjectsUploadTo.getSubject5()!=null && !promoteSubjectsUploadTo.getSubject5().isEmpty()){
						promoteSubjects.setSubject5(promoteSubjectsUploadTo.getSubject5());
					}
					if(promoteSubjectsUploadTo.getSubject6()!=null && !promoteSubjectsUploadTo.getSubject6().isEmpty()){
						promoteSubjects.setSubject6(promoteSubjectsUploadTo.getSubject6());
					}
					if(promoteSubjectsUploadTo.getSubject7()!=null && !promoteSubjectsUploadTo.getSubject7().isEmpty()){
						promoteSubjects.setSubject7(promoteSubjectsUploadTo.getSubject7());
					}
					if(promoteSubjectsUploadTo.getHasPract1()!=null && !promoteSubjectsUploadTo.getHasPract1().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract1().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract1(true);
						}else{
							promoteSubjects.setHasPract1(false);
						}
					}
					if(promoteSubjectsUploadTo.getHasPract3()!=null && !promoteSubjectsUploadTo.getHasPract3().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract3().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract3(true);
						}else{
							promoteSubjects.setHasPract3(false);
						}
					}
					if(promoteSubjectsUploadTo.getHasPract4()!=null && !promoteSubjectsUploadTo.getHasPract4().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract4().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract4(true);
						}else{
							promoteSubjects.setHasPract4(false);
						}
					}
					if(promoteSubjectsUploadTo.getHasPract5()!=null && !promoteSubjectsUploadTo.getHasPract5().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract5().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract5(true);
						}else{
							promoteSubjects.setHasPract5(false);
						}
					}
					if(promoteSubjectsUploadTo.getHasPract6()!=null && !promoteSubjectsUploadTo.getHasPract6().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract6().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract6(true);
						}else{
							promoteSubjects.setHasPract6(false);
						}
					}
					if(promoteSubjectsUploadTo.getHasPract7()!=null && !promoteSubjectsUploadTo.getHasPract7().isEmpty()){
						if(promoteSubjectsUploadTo.getHasPract7().equalsIgnoreCase("TRUE")){
							promoteSubjects.setHasPract7(true);
						}else{
							promoteSubjects.setHasPract7(false);
						}
					}
					if(promoteSubjectsUploadTo.getUnSubCd1()!=null && !promoteSubjectsUploadTo.getUnSubCd1().isEmpty()){
						promoteSubjects.setUnSubCd1(Integer.parseInt(promoteSubjectsUploadTo.getUnSubCd1()));
					}
					if(promoteSubjectsUploadTo.getUnSubcd3()!=null && !promoteSubjectsUploadTo.getUnSubcd3().isEmpty()){
						promoteSubjects.setUnSubcd3(Integer.parseInt(promoteSubjectsUploadTo.getUnSubcd3()));
					}
					if(promoteSubjectsUploadTo.getUnSubcd4()!=null && !promoteSubjectsUploadTo.getUnSubcd4().isEmpty()){
						promoteSubjects.setUnSubcd4(Integer.parseInt(promoteSubjectsUploadTo.getUnSubcd4()));
					}
					if(promoteSubjectsUploadTo.getUnSubcd5()!=null && !promoteSubjectsUploadTo.getUnSubcd5().isEmpty()){
						promoteSubjects.setUnSubcd5(Integer.parseInt(promoteSubjectsUploadTo.getUnSubcd5()));
					}
					if(promoteSubjectsUploadTo.getUnSubcd6()!=null && !promoteSubjectsUploadTo.getUnSubcd6().isEmpty()){
						promoteSubjects.setUnSubcd6(Integer.parseInt(promoteSubjectsUploadTo.getUnSubcd6()));
					}
					if(promoteSubjectsUploadTo.getUnSubcd7()!=null && !promoteSubjectsUploadTo.getUnSubcd7().isEmpty()){
						promoteSubjects.setUnSubcd7(Integer.parseInt(promoteSubjectsUploadTo.getUnSubcd7()));
					}
					promoteSubjectList.add(promoteSubjects);
				}
			}
		}
		return promoteSubjectList;
	}
}
