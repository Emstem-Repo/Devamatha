package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admission.PromoteMarks;
import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.to.admission.PromoteMarksUploadTo;
import com.kp.cms.to.exam.PromoteSupliMarksTo;
import com.kp.cms.transactions.admission.IPromoteMarksUploadTransaction;
import com.kp.cms.transactionsimpl.admission.PromoteMarksUploadTxnImpl;

public class PromoteMarksUploadHelper {
	private static volatile PromoteMarksUploadHelper marksUploadHelper = null;
	IPromoteMarksUploadTransaction transaction = PromoteMarksUploadTxnImpl.getInstance();
	public static PromoteMarksUploadHelper getInstance(){
		if(marksUploadHelper == null){
			marksUploadHelper = new PromoteMarksUploadHelper();
			return marksUploadHelper;
		}
		return marksUploadHelper;
	}
	/**
	 * @param promoteMarksToList
	 * @return
	 * @throws Exception
	 */
	public List<PromoteMarks> convertToTOBo( List<PromoteMarksUploadTo> promoteMarksToList) throws Exception{
		List<PromoteMarks> promoteMarksList = new ArrayList<PromoteMarks>();
		if(promoteMarksToList!=null && !promoteMarksToList.isEmpty()){
			Iterator<PromoteMarksUploadTo> iterator= promoteMarksToList.iterator();
			while (iterator.hasNext()) {
				PromoteMarksUploadTo promoteMarksUploadTo = (PromoteMarksUploadTo) iterator .next();
				PromoteMarks marks = new PromoteMarks();
				if(promoteMarksUploadTo.getRegNo()!=null && !promoteMarksUploadTo.getRegNo().isEmpty()){
					marks.setRegNo(promoteMarksUploadTo.getRegNo());
				}
				if(promoteMarksUploadTo.getClassCode()!=null && !promoteMarksUploadTo.getClassCode().isEmpty()){
					marks.setClassCode(promoteMarksUploadTo.getClassCode());
				}
				if(promoteMarksUploadTo.getMrkSub1()!=null && !promoteMarksUploadTo.getMrkSub1().isEmpty()){
					marks.setMrkSub1(promoteMarksUploadTo.getMrkSub1());
				}
				if(promoteMarksUploadTo.getMrkSub2()!=null && !promoteMarksUploadTo.getMrkSub2().isEmpty()){
					marks.setMrkSub2(promoteMarksUploadTo.getMrkSub2());
				}
				if(promoteMarksUploadTo.getMrkSub3()!=null && !promoteMarksUploadTo.getMrkSub3().isEmpty()){
					marks.setMrkSub3(promoteMarksUploadTo.getMrkSub3());
				}
				if(promoteMarksUploadTo.getMrkSub4()!=null && !promoteMarksUploadTo.getMrkSub4().isEmpty()){
					marks.setMrkSub4(promoteMarksUploadTo.getMrkSub4());
				}
				if(promoteMarksUploadTo.getMrkSub5()!=null && !promoteMarksUploadTo.getMrkSub5().isEmpty()){
					marks.setMrkSub5(promoteMarksUploadTo.getMrkSub5());
				}
				if(promoteMarksUploadTo.getMrkSub6()!=null && !promoteMarksUploadTo.getMrkSub6().isEmpty()){
					marks.setMrkSub6(promoteMarksUploadTo.getMrkSub6());
				}
				if(promoteMarksUploadTo.getMrkSub7()!=null && !promoteMarksUploadTo.getMrkSub7().isEmpty()){
					marks.setMrkSub7(promoteMarksUploadTo.getMrkSub7());
				}
				if(promoteMarksUploadTo.getGradeSub1()!=null && !promoteMarksUploadTo.getGradeSub1().isEmpty()){
					marks.setGradeSub1(promoteMarksUploadTo.getGradeSub1());
				}
				if(promoteMarksUploadTo.getGradeSub2()!=null && !promoteMarksUploadTo.getGradeSub2().isEmpty()){
					marks.setGradeSub2(promoteMarksUploadTo.getGradeSub2());
				}
				if(promoteMarksUploadTo.getGradeSub3()!=null && !promoteMarksUploadTo.getGradeSub3().isEmpty()){
					marks.setGradeSub3(promoteMarksUploadTo.getGradeSub3());
				}
				if(promoteMarksUploadTo.getGradeSub4()!=null && !promoteMarksUploadTo.getGradeSub4().isEmpty()){
					marks.setGradeSub4(promoteMarksUploadTo.getGradeSub4());
				}
				if(promoteMarksUploadTo.getGradeSub5()!=null && !promoteMarksUploadTo.getGradeSub5().isEmpty()){
					marks.setGradeSub5(promoteMarksUploadTo.getGradeSub5());
				}
				if(promoteMarksUploadTo.getGradeSub6()!=null && !promoteMarksUploadTo.getGradeSub6().isEmpty()){
					marks.setGradeSub6(promoteMarksUploadTo.getGradeSub6());
				}
				if(promoteMarksUploadTo.getGradeSub7()!=null && !promoteMarksUploadTo.getGradeSub7().isEmpty()){
					marks.setGradeSub7(promoteMarksUploadTo.getGradeSub7());
				}
				if(promoteMarksUploadTo.getSection()!=null && !promoteMarksUploadTo.getSection().isEmpty()){
					marks.setSection(promoteMarksUploadTo.getSection());
				}
				if(promoteMarksUploadTo.getWithHeld()!=null && !promoteMarksUploadTo.getWithHeld().isEmpty()){
					if(promoteMarksUploadTo.getWithHeld().equalsIgnoreCase("TRUE")){
						marks.setWithHeld(true);
					}
					else{
						marks.setWithHeld(false);
					}
				}
				if(promoteMarksUploadTo.getSupAttend()!=null && !promoteMarksUploadTo.getSupAttend().isEmpty()){
					if(promoteMarksUploadTo.getSupAttend().equalsIgnoreCase("TRUE")){
						marks.setSupAttend(true);
					}
					else{
						marks.setSupAttend(false);
					}
				}
				if(promoteMarksUploadTo.getAcademicYear()!=null && !promoteMarksUploadTo.getAcademicYear().isEmpty()){
					marks.setAcademicYear(promoteMarksUploadTo.getAcademicYear());
				}
				promoteMarksList.add(marks);
			}
		}
		return promoteMarksList;
	}
	/**
	 * @param marksUploadForm
	 * @param session
	 * @throws Exception
	 */
	public void setSubjectNamesToForm(PromoteMarksUploadForm marksUploadForm,HttpSession session)throws Exception{
		
		PromoteSubjects subjects=transaction.getPromoteSubjects(marksUploadForm.getCourseName());
		String practical="-PRACTICAL";
		if(subjects.getSubject1()!=null){
		    session.setAttribute("subject1", subjects.getSubject1().toUpperCase());
		    session.setAttribute("pracSubj1", subjects.getSubject1().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject1", "");
			session.setAttribute("pracSubj1", "PRACTICAL1");
			
		}
		if(subjects.getSubject3()!=null){
		    session.setAttribute("subject3", subjects.getSubject3().toUpperCase());
		    session.setAttribute("pracSubj3", subjects.getSubject3().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject3", "");
			session.setAttribute("pracSubj3", "PRACTICAL3");
		}
		if(subjects.getSubject4()!=null){
		    session.setAttribute("subject4", subjects.getSubject4().toUpperCase());
		    session.setAttribute("pracSubj4", subjects.getSubject4().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject4", "");
			session.setAttribute("pracSubj4", "PRACTICAL4");
		}
		if(subjects.getSubject5()!=null){
		    session.setAttribute("subject5", subjects.getSubject5().toUpperCase());
		    session.setAttribute("pracSubj5", subjects.getSubject5().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject5", "");
			
			session.setAttribute("pracSubj5", "PRACTICAL5");
		}
		if(subjects.getSubject6()!=null){
		    session.setAttribute("subject6", subjects.getSubject6().toUpperCase());
		    session.setAttribute("pracSubj6", subjects.getSubject6().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject6", "");
			session.setAttribute("pracSubj6", "PRACTICAL6");
		}
		if(subjects.getSubject7()!=null){
		    session.setAttribute("subject7", subjects.getSubject7().toUpperCase());
		    session.setAttribute("pracSubj7", subjects.getSubject7().toUpperCase()+practical);
		}
		else{
			session.setAttribute("subject7", "");
			session.setAttribute("pracSubj7", "PRACTICAL7");
		}
	}
	/**
	 * @param pMarksList
	 * @return
	 * @throws Exception
	 */
	public List<PromoteMarksUploadTo> convertMarksBoToTO(List<PromoteMarks> pMarksList)throws Exception{
		List<PromoteMarksUploadTo> promoteMarksToList=new ArrayList<PromoteMarksUploadTo>();
		Iterator<PromoteMarks> itr=pMarksList.iterator();
		while(itr.hasNext()){
			PromoteMarks marks=itr.next();
			PromoteMarksUploadTo promoteMarksTo=new PromoteMarksUploadTo();
			promoteMarksTo.setRegNo(marks.getRegNo());
			promoteMarksTo.setClassCode(marks.getClassCode());
			promoteMarksTo.setMrkSub1(marks.getMrkSub1());
			promoteMarksTo.setMrkSub2(marks.getMrkSub2());
			promoteMarksTo.setMrkSub3(marks.getMrkSub3());
			promoteMarksTo.setMrkSub4(marks.getMrkSub4());
			promoteMarksTo.setMrkSub5(marks.getMrkSub5());
			promoteMarksTo.setMrkSub6(marks.getMrkSub6());
			if(marks.getMrkSub7()!=null && !marks.getMrkSub7().isEmpty())
			     promoteMarksTo.setMrkSub7(marks.getMrkSub7());
			if(marks.getGradeSub1()!=null &&!marks.getGradeSub1().isEmpty())
				promoteMarksTo.setGradeSub1(marks.getGradeSub1());
			if(marks.getGradeSub2()!=null &&!marks.getGradeSub2().isEmpty())
			    promoteMarksTo.setGradeSub2(marks.getGradeSub2());
			if(marks.getGradeSub3()!=null && !marks.getGradeSub3().isEmpty())
			    promoteMarksTo.setGradeSub3(marks.getGradeSub3());
			if(marks.getGradeSub4()!=null && !marks.getGradeSub4().isEmpty())
			    promoteMarksTo.setGradeSub4(marks.getGradeSub4());
			if(marks.getGradeSub5()!=null && !marks.getGradeSub5().isEmpty())
			    promoteMarksTo.setGradeSub5(marks.getGradeSub5());
			if(marks.getGradeSub6()!=null && !marks.getGradeSub6().isEmpty())
			    promoteMarksTo.setGradeSub6(marks.getGradeSub6());
			if(marks.getGradeSub7()!=null && !marks.getGradeSub7().isEmpty())
				promoteMarksTo.setGradeSub7(marks.getGradeSub7());
			if(marks.getWithHeld()!=null && marks.getWithHeld()){
				promoteMarksTo.setWithHeld("Yes");
			}else
				promoteMarksTo.setWithHeld("No");
			if(marks.getSupAttend()!=null && marks.getSupAttend())
				promoteMarksTo.setSupAttend("Yes");
			else
				promoteMarksTo.setSupAttend("No");
			promoteMarksTo.setSection(marks.getSection());
			String secondLang=transaction.getSecondLang(marks.getRegNo(),Integer.parseInt(marks.getAcademicYear()));
			promoteMarksTo.setSecondLanguage(secondLang);
			promoteMarksTo.setAcademicYear(marks.getAcademicYear());
			promoteMarksToList.add(promoteMarksTo);
		}
		return promoteMarksToList;
	}
	
	public List<PromoteSupliMarksTo> convertSupliMarksBoToTO(List<PromoteSupliMarks> pSupliMarksList)throws Exception{
		List<PromoteSupliMarksTo> promoteSupliMarksToList=new ArrayList<PromoteSupliMarksTo>();
		Iterator<PromoteSupliMarks> itr=pSupliMarksList.iterator();
		while(itr.hasNext()){
			PromoteSupliMarks marks=itr.next();
			PromoteSupliMarksTo promoteSupliMarksTo=new PromoteSupliMarksTo();
			promoteSupliMarksTo.setRegNo(marks.getRegNo());
			promoteSupliMarksTo.setClassCode(marks.getClassCode());
			if(marks.getMarkSub1()!=null)
			   promoteSupliMarksTo.setMarkSub1(marks.getMarkSub1().toString());
			if(marks.getMarkSub2()!=null)
			   promoteSupliMarksTo.setMarkSub2(marks.getMarkSub2().toString());
			if(marks.getMarkSub3()!=null)
			   promoteSupliMarksTo.setMarkSub3(marks.getMarkSub3().toString());
			if(marks.getMarkSub4()!=null)
			   promoteSupliMarksTo.setMarkSub4(marks.getMarkSub4().toString());
			if(marks.getMarkSub5()!=null)
			   promoteSupliMarksTo.setMarkSub5(marks.getMarkSub5().toString());
			if(marks.getMarkSub6()!=null)
			   promoteSupliMarksTo.setMarkSub6(marks.getMarkSub6().toString());
			if(marks.getMarkSub7()!=null)
				promoteSupliMarksTo.setMarkSub7(marks.getMarkSub7().toString());
			if(marks.getGradeSub1()!=null &&!marks.getGradeSub1().isEmpty())
				promoteSupliMarksTo.setGradeSub1(marks.getGradeSub1());
			if(marks.getGradeSub2()!=null &&!marks.getGradeSub2().isEmpty())
				promoteSupliMarksTo.setGradeSub2(marks.getGradeSub2());
			if(marks.getGradeSub3()!=null && !marks.getGradeSub3().isEmpty())
				promoteSupliMarksTo.setGradeSub3(marks.getGradeSub3());
			if(marks.getGradeSub4()!=null && !marks.getGradeSub4().isEmpty())
				promoteSupliMarksTo.setGradeSub4(marks.getGradeSub4());
			if(marks.getGradeSub5()!=null && !marks.getGradeSub5().isEmpty())
				promoteSupliMarksTo.setGradeSub5(marks.getGradeSub5());
			if(marks.getGradeSub6()!=null && !marks.getGradeSub6().isEmpty())
				promoteSupliMarksTo.setGradeSub6(marks.getGradeSub6());
			if(marks.getGradeSub7()!=null && !marks.getGradeSub7().isEmpty())
				promoteSupliMarksTo.setGradeSub7(marks.getGradeSub7());
			if(marks.getWithHeld()!=null){
			if(marks.getWithHeld()){
				promoteSupliMarksTo.setWithHeld("Yes");
			}else
				promoteSupliMarksTo.setWithHeld("No");
			}if(marks.getSupAttend()!=null){
			if(marks.getSupAttend())
				promoteSupliMarksTo.setSupAttend("Yes");
			else
				promoteSupliMarksTo.setSupAttend("No");
			}
			if(marks.getSection()!=null && !marks.getSection().isEmpty())
			{
			promoteSupliMarksTo.setSection(marks.getSection());
			}
			String secondLang=transaction.getSecondLang(marks.getRegNo(),marks.getAcademicYear());
			promoteSupliMarksTo.setSecondLang(secondLang);
			promoteSupliMarksTo.setAcademicYear(marks.getAcademicYear());
			if(marks.getSuplSub1()!=null){
			if(marks.getSuplSub1())
				promoteSupliMarksTo.setSuplSub1("Yes");
			else
				promoteSupliMarksTo.setSuplSub1("No");
			}if(marks.getSuplSub2()!=null){
			if(marks.getSuplSub2())
				promoteSupliMarksTo.setSuplSub2("Yes");
			else
				promoteSupliMarksTo.setSuplSub2("No");
			}if(marks.getSuplSub3()!=null){
			if(marks.getSuplSub3())
				promoteSupliMarksTo.setSuplSub3("Yes");
			else
				promoteSupliMarksTo.setSuplSub3("No");
			}if(marks.getSuplSub4()!=null){
			if(marks.getSuplSub4())
				promoteSupliMarksTo.setSuplSub4("Yes");
			else
				promoteSupliMarksTo.setSuplSub4("No");
			}if(marks.getSuplSub5()!=null){
			if(marks.getSuplSub5())
				promoteSupliMarksTo.setSuplSub5("Yes");
			else
				promoteSupliMarksTo.setSuplSub5("No");
			}if(marks.getSuplSub6()!=null){
			if(marks.getSuplSub6())
				promoteSupliMarksTo.setSuplSub6("Yes");
			else
				promoteSupliMarksTo.setSuplSub6("No");
			}if(marks.getSuplSub7()!=null){
			if(marks.getSuplSub7())
				promoteSupliMarksTo.setSuplSub7("Yes");
			else
				promoteSupliMarksTo.setSuplSub7("No");
			}
			
			promoteSupliMarksToList.add(promoteSupliMarksTo);
		}
		return promoteSupliMarksToList;
	}
	
public void setSupliSubjectNamesToForm(PromoteMarksUploadForm marksUploadForm,HttpSession session)throws Exception{
		
		PromoteSubjects subjects=transaction.getPromoteSubjects(marksUploadForm.getCourseName());
		String practical="-PRACTICAL";
		String supliment="SUPLI-";
		if(subjects.getSubject1()!=null){
		    session.setAttribute("subject1", subjects.getSubject1().toUpperCase());
		    session.setAttribute("pracSubj1", subjects.getSubject1().toUpperCase()+practical);
		    session.setAttribute("suplSubj1", supliment+subjects.getSubject1().toUpperCase());
		}
		else{
			session.setAttribute("subject1", "SUBJECT1");
			session.setAttribute("pracSubj1", "PRACTICAL1");
			session.setAttribute("suplSubj1", "SUPLI-SUBJECT1");
			
		}
		if(subjects.getSubject3()!=null){
		    session.setAttribute("subject3", subjects.getSubject3().toUpperCase());
		    session.setAttribute("pracSubj3", subjects.getSubject3().toUpperCase()+practical);
		    session.setAttribute("suplSubj3", supliment+subjects.getSubject3().toUpperCase());
		}
		else{
			session.setAttribute("subject3", "SUBJECT3");
			session.setAttribute("pracSubj3", "PRACTICAL3");
			session.setAttribute("suplSubj3", "SUPLI-SUBJECT3");
		}
		if(subjects.getSubject4()!=null){
		    session.setAttribute("subject4", subjects.getSubject4().toUpperCase());
		    session.setAttribute("pracSubj4", subjects.getSubject4().toUpperCase()+practical);
		    session.setAttribute("suplSubj4", supliment+subjects.getSubject4().toUpperCase());
		}
		else{
			session.setAttribute("subject4", "SUBJECT4");
			session.setAttribute("pracSubj4", "PRACTICAL4");
			session.setAttribute("suplSubj4", "SUPLI-SUBJECT4");
		}
		if(subjects.getSubject5()!=null){
		    session.setAttribute("subject5", subjects.getSubject5().toUpperCase());
		    session.setAttribute("pracSubj5", subjects.getSubject5().toUpperCase()+practical);
		    session.setAttribute("suplSubj5", supliment+subjects.getSubject5().toUpperCase());
		}
		else{
			session.setAttribute("subject5", "SUBJECT5");
			
			session.setAttribute("pracSubj5", "PRACTICAL5");
			session.setAttribute("suplSubj5", "SUPLI-SUBJECT5");
		}
		if(subjects.getSubject6()!=null){
		    session.setAttribute("subject6", subjects.getSubject6().toUpperCase());
		    session.setAttribute("pracSubj6", subjects.getSubject6().toUpperCase()+practical);
		    session.setAttribute("suplSubj6", supliment+subjects.getSubject6().toUpperCase());
		}
		else{
			session.setAttribute("subject6", "SUBJECT6");
			session.setAttribute("pracSubj6", "PRACTICAL6");
			session.setAttribute("suplSubj6", "SUPLI-SUBJECT6");
		}
		if(subjects.getSubject7()!=null){
		    session.setAttribute("subject7", subjects.getSubject7().toUpperCase());
		    session.setAttribute("pracSubj7", subjects.getSubject7().toUpperCase()+practical);
		    session.setAttribute("suplSubj7", supliment+subjects.getSubject7().toUpperCase());
		}
		else{
			session.setAttribute("subject7", "SUBJECT7");
			session.setAttribute("pracSubj7", "PRACTICAL7");
			session.setAttribute("suplSubj7", "SUPLI-SUBJECT7");
		}
	}
}
