package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.PromoteBioData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.PromoteBioDataUploadForm;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admission.PromoteBioDataUploadTo;
import com.kp.cms.utilities.HibernateUtil;

public class PromoteBioDataUploadHelper {
	private static volatile PromoteBioDataUploadHelper bioDataUploadHelper = null;
	public static PromoteBioDataUploadHelper getInstance(){
		if(bioDataUploadHelper == null){
			bioDataUploadHelper = new PromoteBioDataUploadHelper();
			return bioDataUploadHelper;
		}
		return bioDataUploadHelper;
	}
	/**
	 * @param promoteBiodataList
	 * @return
	 */
	public List<PromoteBioData> convertToTOBo( List<PromoteBioDataUploadTo> promoteBiodataList) {
		List<PromoteBioData> bioDataList = new ArrayList<PromoteBioData>();
		if(promoteBiodataList!=null && !promoteBiodataList.toString().isEmpty()){
			Iterator<PromoteBioDataUploadTo> iterator = promoteBiodataList.iterator();
			while (iterator.hasNext()) {
				PromoteBioDataUploadTo promoteBioDataUploadTo = (PromoteBioDataUploadTo) iterator .next();
				PromoteBioData bioData = new PromoteBioData();
				if(promoteBioDataUploadTo.getRegNo()!=null && !promoteBioDataUploadTo.getRegNo().isEmpty()){
					bioData.setRegNo(promoteBioDataUploadTo.getRegNo());
				}
				if(promoteBioDataUploadTo.getClassCode()!=null && !promoteBioDataUploadTo.getClassCode().isEmpty()){
					bioData.setClassCode(promoteBioDataUploadTo.getClassCode());
				}
				if(promoteBioDataUploadTo.getName()!=null && !promoteBioDataUploadTo.getName().isEmpty()){
					bioData.setName(promoteBioDataUploadTo.getName());
				}
				if(promoteBioDataUploadTo.getSection()!=null && !promoteBioDataUploadTo.getSection().isEmpty()){
					bioData.setSection(promoteBioDataUploadTo.getSection());
				}
				if(promoteBioDataUploadTo.getFatherName()!=null && !promoteBioDataUploadTo.getFatherName().isEmpty()){
					bioData.setFatherName(promoteBioDataUploadTo.getFatherName());
				}
				if(promoteBioDataUploadTo.getMotherName()!=null && !promoteBioDataUploadTo.getMotherName().isEmpty()){
					bioData.setMotherName(promoteBioDataUploadTo.getMotherName());
				}
				if(promoteBioDataUploadTo.getSecndLang()!=null && !promoteBioDataUploadTo.getSecndLang().isEmpty()){
					bioData.setSecndLang(promoteBioDataUploadTo.getSecndLang());
				}
				if(promoteBioDataUploadTo.getRank()!=null && !promoteBioDataUploadTo.getRank().isEmpty()){
					bioData.setRank(promoteBioDataUploadTo.getRank());
				}
				if(promoteBioDataUploadTo.getStudentNo()!=null && !promoteBioDataUploadTo.getStudentNo().isEmpty()){
					bioData.setStudentNo(promoteBioDataUploadTo.getStudentNo());
				}
				if(promoteBioDataUploadTo.getAcademicYear()!=null && !promoteBioDataUploadTo.getAcademicYear().isEmpty()){
					bioData.setAcademicYear(promoteBioDataUploadTo.getAcademicYear());
				}
				bioDataList.add(bioData);
			}
		}
		return bioDataList;
	}
	public StringBuffer selectedQuery(PromoteBioDataUploadForm promoteBioDataForm) throws Exception{
		StringBuffer stringBuffer = new StringBuffer("from PromoteBioData promoteBioData where");
		if(promoteBioDataForm.getAcademicYear()!=null && !promoteBioDataForm.getAcademicYear().isEmpty()){
			stringBuffer = stringBuffer.append(" promoteBioData.academicYear='"+promoteBioDataForm.getAcademicYear()+"'");
		}
		
			Session session = null;
			Transaction tx =null;
			try{
				session = HibernateUtil.getSession();
				tx=session.beginTransaction();
				stringBuffer = stringBuffer.append(" and promoteBioData.classCode='"+promoteBioDataForm.getCourseName()+"'");
				
			}catch (Exception e) {
				throw new ApplicationException(e);
				}
		
		return stringBuffer;
	}
	public List<PromoteBioDataUploadTo> populateBOToTO(List<PromoteBioData> proBioDataCJCList) throws Exception{
		List<PromoteBioDataUploadTo> proBioToList = new ArrayList<PromoteBioDataUploadTo>();
		
		if(proBioDataCJCList!=null && !proBioDataCJCList.isEmpty()){
			Iterator<PromoteBioData> iterator = proBioDataCJCList.iterator();
			while (iterator.hasNext()){
				PromoteBioData proBioData = (PromoteBioData) iterator.next();
				PromoteBioDataUploadTo proBioDataCJCTo = new PromoteBioDataUploadTo();
				
				if(proBioData.getRegNo()!=null && !proBioData.getRegNo().isEmpty()){
					proBioDataCJCTo.setRegNo(proBioData.getRegNo());
				}
				String classes = "";
				if(proBioData.getClassCode()!=null && !proBioData.getClassCode().isEmpty()){
					String cls = proBioData.getClassCode();
					classes = classes + cls;
					if(proBioData.getSection()!=null && !proBioData.getSection().isEmpty()){
						String section = proBioData.getSection();
						classes = classes +" "+ section;
					}
					proBioDataCJCTo.setClassCode(classes);
				}
				if(proBioData.getName()!=null && !proBioData.getName().isEmpty()){
					proBioDataCJCTo.setName(proBioData.getName());
				}
				if(proBioData.getFatherName()!=null && !proBioData.getFatherName().isEmpty()){
					proBioDataCJCTo.setFatherName(proBioData.getFatherName());
				}
				if(proBioData.getSecndLang()!=null && !proBioData.getSecndLang().isEmpty()){
					proBioDataCJCTo.setSecndLang(proBioData.getSecndLang());
				}
				if(proBioData.getRank()!=null && !proBioData.getRank().isEmpty()){
					proBioDataCJCTo.setRank(proBioData.getRank());
				}
				if(proBioData.getStudentNo()!=null && !proBioData.getStudentNo().isEmpty()){
					proBioDataCJCTo.setStudentNo(proBioData.getStudentNo());
				}
				if(proBioData.getMotherName()!=null && !proBioData.getMotherName().isEmpty()){
					proBioDataCJCTo.setMotherName(proBioData.getMotherName());
				}
				if(proBioData.getAcademicYear()!=null && !proBioData.getAcademicYear().isEmpty()){
					proBioDataCJCTo.setAcademicYear(proBioData.getAcademicYear());
				}
				proBioToList.add(proBioDataCJCTo);
			}			
		}return proBioToList;
	}
}
