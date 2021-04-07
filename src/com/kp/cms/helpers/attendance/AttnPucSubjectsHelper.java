package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucCetMarks;
import com.kp.cms.bo.admin.AttnPucDefineRange;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.forms.attendance.AttnPucSubjectForm;
import com.kp.cms.to.attendance.AttnMarksUploadTo;
import com.kp.cms.to.attendance.AttnPucCetMarksTo;
import com.kp.cms.to.attendance.AttnPucDefineRangeTo;
import com.kp.cms.to.attendance.AttnPucSubjectsTo;
import com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn;
import com.kp.cms.transactions.attandance.IAttnPucSubjectsTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceDataMigrationReportTxnImpl;
import com.kp.cms.transactionsimpl.attendance.AttnPucSubjectsTransactionImpl;

public class AttnPucSubjectsHelper {
	private static volatile AttnPucSubjectsHelper attnPucSubjectsHelper = null;
	public static AttnPucSubjectsHelper getInstance(){
		if(attnPucSubjectsHelper == null){
			attnPucSubjectsHelper = new AttnPucSubjectsHelper();
			return attnPucSubjectsHelper;
		}
		return attnPucSubjectsHelper;
	}
	public List<AttnPucSubjects> convertTOToBO( List<AttnPucSubjectsTo> attnPucSubjectsTos)throws Exception {
		List<AttnPucSubjects> list = new ArrayList<AttnPucSubjects>();
		if(attnPucSubjectsTos!=null){
			Iterator<AttnPucSubjectsTo> iterator = attnPucSubjectsTos.iterator();
			while (iterator.hasNext()) {
				AttnPucSubjectsTo attnPucSubjectsTo = (AttnPucSubjectsTo) iterator .next();
				AttnPucSubjects subjects =new AttnPucSubjects();
				if(attnPucSubjectsTo.getClasses()!=null && !attnPucSubjectsTo.getClasses().isEmpty()){
					subjects.setClasses(attnPucSubjectsTo.getClasses());
				}
				if(attnPucSubjectsTo.getSubCde1()!=null && !attnPucSubjectsTo.getSubCde1().isEmpty()){
					subjects.setSubCde1(attnPucSubjectsTo.getSubCde1());
				}
				if(attnPucSubjectsTo.getSubCde2()!=null && !attnPucSubjectsTo.getSubCde2().isEmpty()){
					subjects.setSubCde2(attnPucSubjectsTo.getSubCde2());
				}
				if(attnPucSubjectsTo.getSubCde3()!=null && !attnPucSubjectsTo.getSubCde3().isEmpty()){
					subjects.setSubCde3(attnPucSubjectsTo.getSubCde3());
				}
				if(attnPucSubjectsTo.getSubCde4()!=null && !attnPucSubjectsTo.getSubCde4().isEmpty()){
					subjects.setSubCde4(attnPucSubjectsTo.getSubCde4());
				}
				if(attnPucSubjectsTo.getSubCde5()!=null && !attnPucSubjectsTo.getSubCde5().isEmpty()){
					subjects.setSubCde5(attnPucSubjectsTo.getSubCde5());
				}
				if(attnPucSubjectsTo.getSubCde6()!=null && !attnPucSubjectsTo.getSubCde6().isEmpty()){
					subjects.setSubCde6(attnPucSubjectsTo.getSubCde6());
				}
				if(attnPucSubjectsTo.getSubCde7()!=null && !attnPucSubjectsTo.getSubCde7().isEmpty()){
					subjects.setSubCde7(attnPucSubjectsTo.getSubCde7());
				}
				if(attnPucSubjectsTo.getSubCde8()!=null && !attnPucSubjectsTo.getSubCde8().isEmpty()){
					subjects.setSubCde8(attnPucSubjectsTo.getSubCde8());
				}
				if(attnPucSubjectsTo.getSubCde9()!=null && !attnPucSubjectsTo.getSubCde9().isEmpty()){
					subjects.setSubCde9(attnPucSubjectsTo.getSubCde9());
				}
				if(attnPucSubjectsTo.getSubcde10()!=null && !attnPucSubjectsTo.getSubcde10().isEmpty()){
					subjects.setSubcde10(attnPucSubjectsTo.getSubcde10());
				}
				if(attnPucSubjectsTo.getSubject1()!=null && !attnPucSubjectsTo.getSubject1().isEmpty()){
					subjects.setSubject1(attnPucSubjectsTo.getSubject1());
				}
				if(attnPucSubjectsTo.getSubject2()!=null && !attnPucSubjectsTo.getSubject2().isEmpty()){
					subjects.setSubject2(attnPucSubjectsTo.getSubject2());
				}
				if(attnPucSubjectsTo.getSubject3()!=null && !attnPucSubjectsTo.getSubject3().isEmpty()){
					subjects.setSubject3(attnPucSubjectsTo.getSubject3());
				}
				if(attnPucSubjectsTo.getSubject4()!=null && !attnPucSubjectsTo.getSubject4().isEmpty()){
					subjects.setSubject4(attnPucSubjectsTo.getSubject4());
				}
				if(attnPucSubjectsTo.getSubject5()!=null && !attnPucSubjectsTo.getSubject5().isEmpty()){
					subjects.setSubject5(attnPucSubjectsTo.getSubject5());
				}
				if(attnPucSubjectsTo.getSubject6()!=null && !attnPucSubjectsTo.getSubject6().isEmpty()){
					subjects.setSubject6(attnPucSubjectsTo.getSubject6());
				}
				if(attnPucSubjectsTo.getSubject7()!=null && !attnPucSubjectsTo.getSubject7().isEmpty()){
					subjects.setSubject7(attnPucSubjectsTo.getSubject7());
				}
				if(attnPucSubjectsTo.getSubject8()!=null && !attnPucSubjectsTo.getSubject8().isEmpty()){
					subjects.setSubject8(attnPucSubjectsTo.getSubject8());
				}
				if(attnPucSubjectsTo.getSubject9()!=null && !attnPucSubjectsTo.getSubject9().isEmpty()){
					subjects.setSubject9(attnPucSubjectsTo.getSubject9());
				}
				if(attnPucSubjectsTo.getSubject10()!=null && !attnPucSubjectsTo.getSubject10().isEmpty()){
					subjects.setSubject10(attnPucSubjectsTo.getSubject10());
				}
				if(attnPucSubjectsTo.getPraCode1()!=null && !attnPucSubjectsTo.getPraCode1().isEmpty()){
					subjects.setPraCode1(attnPucSubjectsTo.getPraCode1());
				}
				if(attnPucSubjectsTo.getPraCode2()!=null && !attnPucSubjectsTo.getPraCode2().isEmpty()){
					subjects.setPraCode2(attnPucSubjectsTo.getPraCode2());
				}
				if(attnPucSubjectsTo.getPraCode3()!=null && !attnPucSubjectsTo.getPraCode3().isEmpty()){
					subjects.setPraCode3(attnPucSubjectsTo.getPraCode3());
				}
				if(attnPucSubjectsTo.getPraCode4()!=null && !attnPucSubjectsTo.getPraCode4().isEmpty()){
					subjects.setPraCode4(attnPucSubjectsTo.getPraCode4());
				}
				if(attnPucSubjectsTo.getPractical1()!=null && !attnPucSubjectsTo.getPractical1().isEmpty()){
					subjects.setPractical1(attnPucSubjectsTo.getPractical1());
				}
				if(attnPucSubjectsTo.getPractical2()!=null && !attnPucSubjectsTo.getPractical2().isEmpty()){
					subjects.setPractical2(attnPucSubjectsTo.getPractical2());
				}
				if(attnPucSubjectsTo.getPractical3()!=null && !attnPucSubjectsTo.getPractical3().isEmpty()){
					subjects.setPractical3(attnPucSubjectsTo.getPractical3());
				}
				if(attnPucSubjectsTo.getPractical4()!=null && !attnPucSubjectsTo.getPractical4().isEmpty()){
					subjects.setPractical4(attnPucSubjectsTo.getPractical4());
				}
				if(attnPucSubjectsTo.getMaintFees()!=null && !attnPucSubjectsTo.getMaintFees().isEmpty()){
					subjects.setMaintFees(Float.parseFloat(attnPucSubjectsTo.getMaintFees()));
				}
				
				if(attnPucSubjectsTo.getElecPre()!=null && !attnPucSubjectsTo.getElecPre().isEmpty()){
					if(attnPucSubjectsTo.getElecPre().equalsIgnoreCase("TRUE")){
						subjects.setElecPre(true);
					}else{
						subjects.setElecPre(false);
					}
				}
				if(attnPucSubjectsTo.getAcademicYear()!=null && !attnPucSubjectsTo.getAcademicYear().isEmpty()){
					subjects.setAcademicYear(Integer.parseInt(attnPucSubjectsTo.getAcademicYear()));
				}
				list.add(subjects);
			}
			
		}
		return list;
	}
	
	public List<AttnPucCetMarks> convertCetTOToBO( List<AttnPucCetMarksTo> attnCetMarksTos)throws Exception {
		List<AttnPucCetMarks> list = new ArrayList<AttnPucCetMarks>();
		if(attnCetMarksTos!=null){
			Iterator<AttnPucCetMarksTo> iterator = attnCetMarksTos.iterator();
			while (iterator.hasNext()) {
				AttnPucCetMarksTo attnCetMarksTo = (AttnPucCetMarksTo) iterator .next();
				AttnPucCetMarks cetMarks =new AttnPucCetMarks();
				if(attnCetMarksTo.getRegNo()!=null && !attnCetMarksTo.getRegNo().isEmpty()){
					cetMarks.setRegNo(attnCetMarksTo.getRegNo());
				}
				if(attnCetMarksTo.getClasses()!=null && !attnCetMarksTo.getClasses().isEmpty()){
					cetMarks.setClasses(attnCetMarksTo.getClasses());
				}
				if(attnCetMarksTo.getTestId()!=null && !attnCetMarksTo.getTestId().isEmpty()){
					cetMarks.setTestId(attnCetMarksTo.getTestId());
				}
				if(attnCetMarksTo.getMrkSub1()!=null && !attnCetMarksTo.getMrkSub1().isEmpty()){
					cetMarks.setMrkSub1(attnCetMarksTo.getMrkSub1());
				}
				if(attnCetMarksTo.getMrkSub2()!=null && !attnCetMarksTo.getMrkSub2().isEmpty()){
					cetMarks.setMrkSub2(attnCetMarksTo.getMrkSub2());
				}
				if(attnCetMarksTo.getMrkSub3()!=null && !attnCetMarksTo.getMrkSub3().isEmpty()){
					cetMarks.setMrkSub3(attnCetMarksTo.getMrkSub3());
				}
				if(attnCetMarksTo.getMrkSub4()!=null && !attnCetMarksTo.getMrkSub4().isEmpty()){
					cetMarks.setMrkSub4(attnCetMarksTo.getMrkSub4());
				}
				if(attnCetMarksTo.getUserCode()!=null && !attnCetMarksTo.getUserCode().isEmpty()){
					cetMarks.setUserCode(attnCetMarksTo.getUserCode());
				}
				if(attnCetMarksTo.getPcbRank()!=null && !attnCetMarksTo.getPcbRank().isEmpty()){
					cetMarks.setPcbRank(attnCetMarksTo.getPcbRank());
				}
				if(attnCetMarksTo.getPcmRank()!=null && !attnCetMarksTo.getPcmRank().isEmpty()){
					cetMarks.setPcmRank(attnCetMarksTo.getPcmRank());
				}
				if(attnCetMarksTo.getAieee()!=null && !attnCetMarksTo.getAieee().isEmpty()){
					if(attnCetMarksTo.getAieee().equalsIgnoreCase("true"))
						cetMarks.setAieee(true);
					else
						cetMarks.setAieee(false);
				}
				if(attnCetMarksTo.getAcademicYear()!=null && !attnCetMarksTo.getAcademicYear().isEmpty()){
					cetMarks.setAcademicYear(Integer.parseInt(attnCetMarksTo.getAcademicYear()));
				}
				list.add(cetMarks);
			}
			
		}
		return list;
	}
	
	public List<AttnPucCetMarksTo> convertAttnCetMarksBoToTO(List<AttnPucCetMarks> attnCetMarks)throws Exception{
		List<AttnPucCetMarksTo> attnCetMarksTo=new ArrayList<AttnPucCetMarksTo>();
		Iterator<AttnPucCetMarks> itr=attnCetMarks.iterator();
		try{
		while(itr.hasNext()){
			AttnPucCetMarks attCetMarks=itr.next();
			AttnPucCetMarksTo cetMarksTo=new AttnPucCetMarksTo();
			if(attCetMarks.getRegNo()!=null)
				cetMarksTo.setRegNo(attCetMarks.getRegNo());
			if(attCetMarks.getClasses()!=null)
				cetMarksTo.setClasses(attCetMarks.getClasses());
			if(attCetMarks.getTestId()!=null)
				cetMarksTo.setTestId(attCetMarks.getTestId());
			if(attCetMarks.getMrkSub1()!=null)
				cetMarksTo.setMrkSub1(attCetMarks.getMrkSub1());
			if(attCetMarks.getMrkSub2()!=null)
				cetMarksTo.setMrkSub2(attCetMarks.getMrkSub2());
			if(attCetMarks.getMrkSub3()!=null)
				cetMarksTo.setMrkSub3(attCetMarks.getMrkSub3());
			if(attCetMarks.getMrkSub4()!=null)
				cetMarksTo.setMrkSub4(attCetMarks.getMrkSub4());
			if(attCetMarks.getUserCode()!=null)
				cetMarksTo.setUserCode(attCetMarks.getUserCode());
			if(attCetMarks.getPcbRank()!=null)
				cetMarksTo.setPcbRank(attCetMarks.getPcbRank());
			if(attCetMarks.getPcmRank()!=null)
				cetMarksTo.setPcmRank(attCetMarks.getPcmRank());
			if(attCetMarks.getAieee()!=null){
			if(attCetMarks.getAieee())
				cetMarksTo.setAieee("Yes");
			else
				cetMarksTo.setAieee("No");
			}
			if(attCetMarks.getAcademicYear()!=null)
				cetMarksTo.setAcademicYear(attCetMarks.getAcademicYear().toString());
			attnCetMarksTo.add(cetMarksTo);
		}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return attnCetMarksTo;
	}
	
	public void setSubjectNamesToSession(AttnPucSubjectForm attnPucSubjectForm,HttpSession session)throws Exception{
		IAttnPucSubjectsTransaction transaction = AttnPucSubjectsTransactionImpl.getInstance();
		AttnPucSubjects subjects=transaction.getAttnSubject(attnPucSubjectForm.getClassName());
			if(subjects.getSubject1()!=null){
			    session.setAttribute("subject1", subjects.getSubject1().toUpperCase());
			}
			else{
				session.setAttribute("subject1", "SUBJECT-1");
				
			}
			if(subjects.getSubject2()!=null){
			    session.setAttribute("subject2", subjects.getSubject2().toUpperCase());
			}
			else{
				session.setAttribute("subject2", "SUBJECT-2");
				
			}
			if(subjects.getSubject3()!=null){
			    session.setAttribute("subject3", subjects.getSubject3().toUpperCase());
			}
			else{
				session.setAttribute("subject3", "SUBJECT-3");
			}
			if(subjects.getSubject4()!=null){
			    session.setAttribute("subject4", subjects.getSubject4().toUpperCase());
			}
			else{
				session.setAttribute("subject4", "SUBJECT-4");
			}
		}
	
	public List<AttnPucDefineRange> convertDefineRangeTOToBO( List<AttnPucDefineRangeTo> attnDefineRangeTo)throws Exception {
		List<AttnPucDefineRange> list = new ArrayList<AttnPucDefineRange>();
		if(attnDefineRangeTo!=null){
			Iterator<AttnPucDefineRangeTo> iterator = attnDefineRangeTo.iterator();
			while (iterator.hasNext()) {
				AttnPucDefineRangeTo defineRangeTo = (AttnPucDefineRangeTo) iterator .next();
				AttnPucDefineRange defineRange =new AttnPucDefineRange();
				if(defineRangeTo.getClasses()!=null)
					defineRange.setClasses(defineRangeTo.getClasses());
				if(defineRangeTo.getStartRegNo()!=null)
					defineRange.setStartRegNo(defineRangeTo.getStartRegNo());
				if(defineRangeTo.getEndRegNo()!=null)
					defineRange.setEndRegNo(defineRangeTo.getEndRegNo());
				if(defineRangeTo.getTheoryOrPractical()!=null)
					defineRange.setTheoryOrPractical(defineRangeTo.getTheoryOrPractical());
				if(defineRangeTo.getCombineClass1()!=null)
					defineRange.setCombineClass1(defineRangeTo.getCombineClass1());
				if(defineRangeTo.getCombineClass2()!=null)
					defineRange.setCombineClass2(defineRangeTo.getCombineClass2());
				if(defineRangeTo.getCombineClass3()!=null)
					defineRange.setCombineClass3(defineRangeTo.getCombineClass3());
				if(defineRangeTo.getCombineClass4()!=null)
					defineRange.setCombineClass4(defineRangeTo.getCombineClass4());
				if(defineRangeTo.getCombineClass5()!=null)
					defineRange.setCombineClass5(defineRangeTo.getCombineClass5());
				if(defineRangeTo.getCombineClass6()!=null)
					defineRange.setCombineClass5(defineRangeTo.getCombineClass6());
				if(defineRangeTo.getCombineClass7()!=null)
					defineRange.setCombineClass7(defineRangeTo.getCombineClass7());
				if(defineRangeTo.getCombineClass8()!=null)
					defineRange.setCombineClass8(defineRangeTo.getCombineClass8());
				if(defineRangeTo.getCombineClass9()!=null)
					defineRange.setCombineClass9(defineRangeTo.getCombineClass9());
				if(defineRangeTo.getClass1StartRegNo()!=null)
					defineRange.setClass1StartRegNo(defineRangeTo.getClass1StartRegNo());
				if(defineRangeTo.getClass1EndRegNo()!=null)
					defineRange.setClass1EndRegNo(defineRangeTo.getClass1EndRegNo());
				if(defineRangeTo.getClass2StartRegNo()!=null)
					defineRange.setClass2StartRegNo(defineRangeTo.getClass2StartRegNo());
				if(defineRangeTo.getClass2EndRegNo()!=null)
					defineRange.setClass2EndRegNo(defineRangeTo.getClass2EndRegNo());
				if(defineRangeTo.getClass3StartRegNo()!=null)
					defineRange.setClass3StartRegNo(defineRangeTo.getClass3StartRegNo());
				if(defineRangeTo.getClass3EndRegNo()!=null)
					defineRange.setClass3EndRegNo(defineRangeTo.getClass3EndRegNo());
				if(defineRangeTo.getClass4StartRegNo()!=null)
					defineRange.setClass4StartRegNo(defineRangeTo.getClass4StartRegNo());
				if(defineRangeTo.getClass4EndRegNo()!=null)
					defineRange.setClass4EndRegNo(defineRangeTo.getClass4EndRegNo());
				if(defineRangeTo.getClass5StartRegNo()!=null)
					defineRange.setClass5StartRegNo(defineRangeTo.getClass5StartRegNo());
				if(defineRangeTo.getClass5EndRegNo()!=null)
					defineRange.setClass5EndRegNo(defineRangeTo.getClass5EndRegNo());
				if(defineRangeTo.getClass6StartRegNo()!=null)
					defineRange.setClass6StartRegNo(defineRangeTo.getClass6StartRegNo());
				if(defineRangeTo.getClass6EndRegNo()!=null)
					defineRange.setClass6EndRegNo(defineRangeTo.getClass6EndRegNo());
				if(defineRangeTo.getClass7StartRegNo()!=null)
					defineRange.setClass7StartRegNo(defineRangeTo.getClass7StartRegNo());
				if(defineRangeTo.getClass7EndRegNo()!=null)
					defineRange.setClass7EndRegNo(defineRangeTo.getClass7EndRegNo());
				if(defineRangeTo.getClass8StartRegNo()!=null)
					defineRange.setClass8StartRegNo(defineRangeTo.getClass8StartRegNo());
				if(defineRangeTo.getClass8EndRegNo()!=null)
					defineRange.setClass8EndRegNo(defineRangeTo.getClass8EndRegNo());
				if(defineRangeTo.getClass9StartRegNo()!=null)
					defineRange.setClass9StartRegNo(defineRangeTo.getClass9StartRegNo());
				if(defineRangeTo.getClass9EndRegNo()!=null)
					defineRange.setClass9EndRegNo(defineRangeTo.getClass9EndRegNo());
				if(defineRangeTo.getAcademicYear()!=null && !defineRangeTo.getAcademicYear().isEmpty()){
					defineRange.setAcademicYear(Integer.parseInt(defineRangeTo.getAcademicYear()));
				}
				list.add(defineRange);
			}
			
		}
		return list;
	}
}
