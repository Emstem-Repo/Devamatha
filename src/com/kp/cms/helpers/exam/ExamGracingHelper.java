package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamGracingBO;
import com.kp.cms.bo.exam.ExamGracingProcessBO;
import com.kp.cms.bo.exam.ExamGracingSubjectMarksBo;
import com.kp.cms.forms.exam.ExamGracingForm;
import com.kp.cms.to.exam.ExamGracingSubjectMarksTo;
import com.kp.cms.to.exam.ExamGracingTo;

public class ExamGracingHelper {
	DecimalFormat decimalFormat=new DecimalFormat("#");

	public List<ExamGracingTo> convertGracingBotoTo(List<ExamGracingBO> boList) {
		List<ExamGracingTo> list = new LinkedList<ExamGracingTo>();
		Iterator<ExamGracingBO> itr = boList.iterator();
		while (itr.hasNext()) {
			ExamGracingBO bo = (ExamGracingBO) itr.next();
			ExamGracingTo to = new ExamGracingTo();
			to.setAcademicYear(bo.getAcademicYear());
			to.setCourseId(bo.getCourse().getId());
			to.setAppliedYear(bo.getAppliedYear());
			to.setId(bo.getId());
			to.setProcessed(bo.getProcessed());
			to.setPercentage(bo.getPercentage());
			to.setRemark(bo.getRemark());
			to.setSemester(bo.getSemester());
			to.setOnlyPass(bo.getOnlyPass());
			to.setStudentId(bo.getStudent().getId());
			list.add(to);	
		}
		return list;
	}

	public List<ExamGracingSubjectMarksTo> convertConsolidatedtoMarkTo(
			List<ConsolidateMarksCard> consolList, ExamGracingForm graceForm, ExamGracingTo gracingTo) {
		List<ExamGracingSubjectMarksTo> finalList = new ArrayList<ExamGracingSubjectMarksTo>();
		List<ExamGracingSubjectMarksTo> tempFinalList = new ArrayList<ExamGracingSubjectMarksTo>();
		List<ExamGracingSubjectMarksTo> rejectList = new ArrayList<ExamGracingSubjectMarksTo>();
		List<ExamGracingSubjectMarksTo> failedList = new ArrayList<ExamGracingSubjectMarksTo>();
		List<ExamGracingSubjectMarksTo> confirmedList = new ArrayList<ExamGracingSubjectMarksTo>();
		
		Iterator<ConsolidateMarksCard> boItr = consolList.iterator();
		Double totMark = 0.0;
		Double totGrace = 0.0;
		Double elegibleGrace = 0.0;
		Double percent = Double.parseDouble(gracingTo.getPercentage().toString());
		while (boItr.hasNext()) {
			ConsolidateMarksCard bo = (ConsolidateMarksCard) boItr
					.next();
			Double totSubjExtMark = 0.0;
			Double totSubjExtMarkMax = 0.0;
			Double obtain = 0.0;
			Double obtainMax =0.0;
			boolean isAbsent=false;
			ExamGracingSubjectMarksTo to = new ExamGracingSubjectMarksTo();
			
			//GENERAL SETTINGS
			to.setSortingDiff(0.0);
			to.setAcademicYear(Integer.parseInt(gracingTo.getAcademicYear()));
			to.setAppliedYear(Integer.parseInt(gracingTo.getAppliedYear()));
			to.setClassesId(bo.getClasses().getId());
			to.setCourseId(gracingTo.getCourseId());
			to.setExamId(bo.getExam().getId());
			to.setSectionId(bo.getSectionId());
			to.setStudentId(bo.getStudent().getId());
			to.setSubjectId(bo.getSubject().getId());
			to.setSubjectOrder(bo.getSubjectOrder());
			to.setSubType(bo.getSubType());
			to.setTermNumber(bo.getTermNumber());
			to.setPassOrFail(bo.getPassOrFail());
			to.setTheoryEseGrace(0.0);
			to.setPracticalEseGrace(0.0);
		    to.setIsGraced(false);
			
           //PRACTICAL
			if(bo.getSubType().equalsIgnoreCase("Practical") || bo.getSubType().equalsIgnoreCase("Both")){
				
				if(bo.getPracticaleseMaximumMark()!=null){
					to.setPracticaleseMaximumMark(bo.getPracticaleseMaximumMark());	
					totMark = totMark+Double.valueOf(bo.getPracticaleseMaximumMark().toString());
					totSubjExtMarkMax = totSubjExtMarkMax+Double.valueOf(bo.getPracticaleseMaximumMark().toString());
				}
				if(bo.getPracticaleseMinimumMark()!=null)
					  to.setPracticaleseMinimumMark(bo.getPracticaleseMinimumMark());
				if(bo.getFinalPracticalInternalMaximumMark()!=null)
					  to.setFinalPracticalInternalMaximumMark(bo.getFinalPracticalInternalMaximumMark());
				if(bo.getFinalPracticalInternalMinimumMark()!=null)
					  to.setFinalPracticalInternalMinimumMark(bo.getFinalPracticalInternalMinimumMark());
				if(bo.getPracticalMax()!=null)
				     to.setPracticalMax(bo.getPracticalMax());
				if(bo.getPracticalMin()!=null)
				     to.setPracticalMin(bo.getPracticalMin());
                if(bo.getPracticalTotalSubInternalMark()!=null){
                	/*if(bo.getPracticalTotalAttendanceMark()!=null){
                		Double pInternal = Double.parseDouble(bo.getPracticalTotalSubInternalMark())+Double.parseDouble(bo.getPracticalTotalAttendanceMark());
				        to.setPracticalTotalSubInternalMark(pInternal.toString());
                	}else{*/
                	   to.setPracticalTotalSubInternalMark(bo.getPracticalTotalSubInternalMark());
                	//}
                }
                if(bo.getStudentPracticalMark()!=null){
				     to.setStudentPracticalMark(bo.getStudentPracticalMark());
				     if(!bo.getStudentPracticalMark().equalsIgnoreCase("AB")){
				       totSubjExtMark= totSubjExtMark+Double.valueOf(bo.getStudentPracticalMark());
				       to.setSortingDiff(Double.parseDouble(bo.getPracticaleseMaximumMark().toString())-Double.parseDouble(bo.getStudentPracticalMark().toString()));
				     }
				     else
				    	 isAbsent=true;
                }
				to.setPracticalObtain(bo.getPracticalObtain());
			
			}
			
			//THEORY
			if(bo.getSubType().equalsIgnoreCase("Theory") || bo.getSubType().equalsIgnoreCase("Both")){
				if(bo.getTheoryeseMaximumMark()!=null){
					to.setTheoryeseMaximumMark(bo.getTheoryeseMaximumMark());	
					totMark = totMark+Double.valueOf(bo.getTheoryeseMaximumMark().toString());
					totSubjExtMarkMax = totSubjExtMarkMax+Double.valueOf(bo.getTheoryeseMaximumMark().toString());
				}
				if(bo.getTheoryeseMinimumMark()!=null)
					  to.setTheoryeseMinimumMark(bo.getTheoryeseMinimumMark());
				if(bo.getFinalTheoryInternalMaximumMark()!=null)
					  to.setFinalTheoryInternalMaximumMark(bo.getFinalTheoryInternalMaximumMark());
			    if(bo.getFinalTheoryInternalMinimumMark()!=null)
					  to.setFinalTheoryInternalMinimumMark(bo.getFinalTheoryInternalMinimumMark());
			    if(bo.getTheoryMax()!=null){
				   to.setTheoryMax(bo.getTheoryMax());
				   obtainMax =  Double.parseDouble(bo.getTheoryMax().toString());
			    }
			    if(bo.getTheoryMin()!=null)
				   to.setTheoryMin(bo.getTheoryMin());
	            if(bo.getTheoryTotalSubInternalMark()!=null){
	                	/*if(bo.getTheoryTotalAttendanceMark()!=null){
	                		Double pInternal = Double.parseDouble(bo.getTheoryTotalSubInternalMark())+Double.parseDouble(bo.getTheoryTotalAttendanceMark());
					        to.setTheoryTotalSubInternalMark(pInternal.toString());
	                	}else{*/
	                	   to.setTheoryTotalSubInternalMark(bo.getTheoryTotalSubInternalMark());
	                	//}
	                }
			    if(bo.getStudentTheoryMark()!=null){
			    	to.setStudentTheoryMark(bo.getStudentTheoryMark());
			    	if(!bo.getStudentTheoryMark().equalsIgnoreCase("AB")){
				      totSubjExtMark= totSubjExtMark+Double.valueOf(bo.getStudentTheoryMark());
				      to.setSortingDiff(Double.parseDouble(bo.getTheoryeseMaximumMark().toString())-Double.parseDouble(bo.getStudentTheoryMark().toString()));
			    	}
			    	else
			    		isAbsent=true;
			    }
				
				to.setTheoryObtain(bo.getTheoryObtain());
				obtain = bo.getTheoryObtain();
			}
            to.setTotalExtMax(totSubjExtMarkMax);
            to.setTotalExtObain(totSubjExtMark);
            boolean suplyflag = false;
            if(gracingTo.getSuppplyIds().containsKey(bo.getClasses().getId())){
            	if(gracingTo.getSuppplyIds().get(bo.getClasses().getId())!=0 && bo.getExam().getId()>gracingTo.getSuppplyIds().get(bo.getClasses().getId()))
            		suplyflag = true;
            }
            if(suplyflag){
	    		to.setNoGracingReason("Not first supply");
	    		to.setIsGraced(false);
	    		rejectList.add(to);
            }
            else if(bo.getPassOrFail().equalsIgnoreCase("Pass")){
		    	boolean rejected = false;
		    	String rsn = "";
		    	if(bo.getSubType().equalsIgnoreCase("Theory") || bo.getSubType().equalsIgnoreCase("Both")){
		    		if(bo.getStudentTheoryMark()!=null && bo.getTheoryeseMaximumMark()!=null){
			    	  if(Double.parseDouble(bo.getStudentTheoryMark())==Double.parseDouble(bo.getTheoryeseMaximumMark().toString())){
			    		rejected  = true;
			    		rsn="Full mark for external";
			    		to.setNoGracingReason(rsn);
			    		to.setIsGraced(false);
			    	  }
		    	  }
		    	}	
		    	if(bo.getSubType().equalsIgnoreCase("Practical") /*|| bo.getSubType().equalsIgnoreCase("Both")*/){

		    		/*if(bo.getStudentPracticalMark()!=null && bo.getPracticaleseMaximumMark().toString()!=null){
			    	   if(Double.parseDouble(bo.getStudentPracticalMark())==Double.parseDouble(bo.getPracticaleseMaximumMark().toString())){
			    		 rejected  = true;
			    		 rsn="Full mark for external";
			    		 to.setNoGracingReason(rsn);
			    		 to.setIsGraced(false);
			    	   }
		    		}
		    		if(bo.getSubType().equalsIgnoreCase("Practical")){*/
		    			rejected  = true;
			    		 rsn="Practical";
			    		 to.setNoGracingReason(rsn);
			    		 to.setIsGraced(false);
		    		/*}*/
		    	}
		    	if(rejected)
		    		rejectList.add(to);	
		    	else
		    	    finalList.add(to);
		    }else{
		    	String rsn = "";
		    	boolean rejected = false;
		    	
		    	double eligPer = (totSubjExtMark/totSubjExtMarkMax)*100;
		    	double obtainEligiblePer = (obtain/obtainMax)*100;
		    	if(eligPer<5.0 || obtainEligiblePer<5.0 || isAbsent){
		    		rejected  = true;
		    		rsn="External or total Less than 5%";
		    		to.setNoGracingReason(rsn);
		    		to.setIsGraced(false);
		    	}
	    		if(bo.getSubType().equalsIgnoreCase("Practical")){
	    			rejected  = true;
		    		 rsn="Practical";
		    		 to.setNoGracingReason(rsn);
		    		 to.setIsGraced(false);
	    		}else{
			    	if(gracingTo.getOnlyPass()){
			    		rejected = true;
			    		rsn = "Applied for passed subjects only";
			    		to.setNoGracingReason(rsn);
			    		to.setIsGraced(false);
			    	}
			    	if(rejected){
			    		rejectList.add(to);	
			    	}else{
			    		failedList.add(to);
			    	}
	    			
	    			
	    		}

		    	
		    	
		    }
		}//END OF boItr
		
		totGrace = (percent/100)*totMark;
		elegibleGrace = (50.0/100.0)*totGrace;
		graceForm.setTotalGraceMark(totGrace);
		graceForm.setTotalSubMark(totMark);
		Double remainGrace = totGrace;
		Double thryIntPass = 0.0;
		Double pracIntPass = 0.0;
		Double practEligble = 0.0;
		Double theoryEligible = 0.0;
		Double practGen = 0.0;
		Double theoryGen =0.0;
		
		//PROCESSING FAILED SUBJECTS
		Iterator<ExamGracingSubjectMarksTo> faildItr = failedList.iterator();
		while (faildItr.hasNext()) {
			ExamGracingSubjectMarksTo failTo = (ExamGracingSubjectMarksTo) faildItr
					.next();
		     thryIntPass = 0.0;
			 pracIntPass = 0.0;
			 practEligble = 0.0;
		     theoryEligible = 0.0;
		     practGen =0.0;
		     theoryGen=0.0;
			
			//calculating overall needed mark to pass 
/*			if(failTo.getSubType().equalsIgnoreCase("Practical") || failTo.getSubType().equalsIgnoreCase("Both")){
			       if(Double.parseDouble(failTo.getPracticalTotalSubInternalMark())<Double.parseDouble(failTo.getFinalPracticalInternalMinimumMark().toString()))
			    	   pracIntPass = Double.parseDouble(failTo.getFinalPracticalInternalMinimumMark().toString()) - Double.parseDouble(failTo.getPracticalTotalSubInternalMark());

			       if(Double.parseDouble(failTo.getStudentPracticalMark())<Double.parseDouble(failTo.getPracticaleseMinimumMark().toString()))
			    	   practEligble = Double.parseDouble(failTo.getPracticaleseMinimumMark().toString()) - Double.parseDouble(failTo.getStudentPracticalMark());			       
			       
			       if((failTo.getPracticalObtain()+pracIntPass+practEligble)<Double.parseDouble(failTo.getPracticalMin().toString()))
					   practGen = Double.parseDouble(failTo.getPracticalMin().toString())-(failTo.getPracticalObtain()+pracIntPass+practEligble);
			       practEligble = pracIntPass+practEligble+practGen;
			}*/
			if(failTo.getSubType().equalsIgnoreCase("Theory") || failTo.getSubType().equalsIgnoreCase("Both")){
			       if(Double.parseDouble(failTo.getTheoryTotalSubInternalMark())<Double.parseDouble(failTo.getFinalTheoryInternalMinimumMark().toString()))
			    	   thryIntPass = Double.parseDouble(failTo.getFinalTheoryInternalMinimumMark().toString()) - Double.parseDouble(failTo.getTheoryTotalSubInternalMark());

			       if(Double.parseDouble(failTo.getStudentTheoryMark())<Double.parseDouble(failTo.getTheoryeseMinimumMark().toString()))
			    	   theoryEligible = Double.parseDouble(failTo.getTheoryeseMinimumMark().toString()) - Double.parseDouble(failTo.getStudentTheoryMark());			       
			       
			       if((failTo.getTheoryObtain()+thryIntPass+theoryEligible)<Double.parseDouble(failTo.getTheoryMin().toString()))
					   theoryGen = Double.parseDouble(failTo.getTheoryMin().toString())-(failTo.getTheoryObtain()+thryIntPass+theoryEligible);
			       theoryEligible = thryIntPass+theoryEligible+theoryGen;
			}
			//checking needed mark is greater than 50% of grace and remaining gracing is enough
			if(/*(practEligble+theoryEligible)<=elegibleGrace &&*/ (practEligble+theoryEligible)<=remainGrace){	
				failTo.setIsGraced(true);
				failTo.setPassOrFail("Pass");
				remainGrace = remainGrace-(practEligble+theoryEligible);
				failTo.setTheoryObtainGraced(theoryEligible);
				failTo.setPracticalObtainGraced(practEligble);
/*				if((failTo.getSubType().equalsIgnoreCase("Practical") || failTo.getSubType().equalsIgnoreCase("Both"))&& practEligble>0){
					   failTo.setPracticalInternalGrace(pracIntPass);
					   practEligble = practEligble-pracIntPass;
			           failTo.setPracticalEseGrace(practEligble);
				}*/
				if((failTo.getSubType().equalsIgnoreCase("Theory") || failTo.getSubType().equalsIgnoreCase("Both"))&& theoryEligible>0){
				       failTo.setTheoryInternalGrace(thryIntPass);
					   theoryEligible = theoryEligible-thryIntPass;
				       failTo.setTheoryEseGrace(theoryEligible);
				}
			  rejectList.add(failTo);
			}else{
				//Avoiding practical subjects from further process
/*			  if(failTo.getSubType().equalsIgnoreCase("Practical")){
				  rejectList.add(failTo);
				  failTo.setNoGracingReason("Practical/viva/project");
				  failTo.setIsGraced(false);
			  }
			  else*/
			      finalList.add(failTo);
			}
      }//END OF failedItr
		
  do{	
	  Collections.sort(finalList);
	  tempFinalList = new ArrayList<ExamGracingSubjectMarksTo>();
	  Double subSize = new Double(finalList.size());
	  Double divisor = 0.0;
	  if(subSize>0){
		  if((remainGrace/subSize)>1){
			  divisor=remainGrace/subSize;
			  if(remainGrace%subSize>0){
				  divisor=new Double(divisor.intValue());
			  }
		  }else
			  divisor = 1.0; 	  
	  }
	  
	  Iterator<ExamGracingSubjectMarksTo> finalItr = finalList.iterator();
	  while (finalItr.hasNext()) {
		ExamGracingSubjectMarksTo finalTo = (ExamGracingSubjectMarksTo) finalItr.next();
		Double theoryGrace = finalTo.getTheoryEseGrace();
		Double practicalGrace = finalTo.getPracticalEseGrace();
		boolean exitSub = false;
		if(divisor>0 && remainGrace>0){
		   if(finalTo.getSubType().equalsIgnoreCase("Theory") || finalTo.getSubType().equalsIgnoreCase("Both")){
				if((Double.parseDouble(finalTo.getStudentTheoryMark())+divisor+theoryGrace)>=Double.parseDouble(finalTo.getTheoryeseMaximumMark().toString())){
					finalTo.setTheoryEseGrace(Double.parseDouble(finalTo.getTheoryeseMaximumMark().toString())-Double.parseDouble(finalTo.getStudentTheoryMark()));
					remainGrace = remainGrace-(finalTo.getTheoryEseGrace()-theoryGrace);
					exitSub = true;
				}else{
					finalTo.setTheoryEseGrace(divisor+theoryGrace);
					remainGrace = remainGrace-divisor;
				}
				finalTo.setIsGraced(true);
		   }/*else if(finalTo.getSubType().equalsIgnoreCase("Practical") || finalTo.getSubType().equalsIgnoreCase("Both")){
				if((Double.parseDouble(finalTo.getStudentPracticalMark())+divisor+practicalGrace)>=Double.parseDouble(finalTo.getPracticaleseMaximumMark().toString())){
					finalTo.setPracticalEseGrace(Double.parseDouble(finalTo.getPracticaleseMaximumMark().toString())-Double.parseDouble(finalTo.getStudentPracticalMark()));
					remainGrace = remainGrace-(finalTo.getPracticalEseGrace()-practicalGrace);
					exitSub = true;
				}else{
					finalTo.setPracticalEseGrace(divisor+practicalGrace);
					remainGrace = remainGrace-(divisor);
				}
				finalTo.setIsGraced(true);
		  }*/
		}else{
			if(!finalTo.getIsGraced())
			 finalTo.setNoGracingReason("insufficient gracemark");
		}
		if(exitSub)
		 confirmedList.add(finalTo);
		else
		 tempFinalList.add(finalTo);
	}
	  finalList = tempFinalList;
   }while(remainGrace>0 && tempFinalList.size()>0);	  
	  
	  
	  confirmedList.addAll(rejectList);
	  confirmedList.addAll(finalList);
		return confirmedList;
  }

	public ExamGracingProcessBO convertMarkToToBo(
			List<ExamGracingSubjectMarksTo> marksTo, ExamGracingTo gracingTo, ExamGracingForm graceForm) {
		ExamGracingProcessBO processbo = new ExamGracingProcessBO();
		processbo.setCreatedBy(graceForm.getUserId());
		processbo.setCreatedDate(new Date());
		processbo.setLastModifiedDate(new Date());
		processbo.setModifiedBy(graceForm.getUserId());
		processbo.setTotalGraceMark(graceForm.getTotalGraceMark().toString());
		processbo.setTotalSubMark(graceForm.getTotalSubMark().toString());
        ExamGracingBO graceBo = new ExamGracingBO();
        graceBo.setId(gracingTo.getId());
        processbo.setGracingBo(graceBo);
		Set<ExamGracingSubjectMarksBo> submarkboset = new HashSet<ExamGracingSubjectMarksBo>();
		Iterator<ExamGracingSubjectMarksTo> toItr = marksTo.iterator();
		while (toItr.hasNext()) {
			ExamGracingSubjectMarksTo to = (ExamGracingSubjectMarksTo) toItr
					.next();
			ExamGracingSubjectMarksBo bo = new ExamGracingSubjectMarksBo();
			bo.setAcademicYear(to.getAcademicYear());
			bo.setAppliedYear(to.getAppliedYear());
			Classes cls = new Classes();
			cls.setId(to.getClassesId());
			bo.setClasses(cls);
			Course crs = new Course();
			crs.setId(to.getCourseId());
			bo.setCourse(crs);
			bo.setCreatedBy(graceForm.getUserId());
			bo.setCreatedDate(new Date());
			ExamDefinition exm = new ExamDefinition();
			exm.setId(to.getExamId());
			bo.setExam(exm);
			bo.setGracingProcessBo(processbo);
			bo.setIsGraced(to.getIsGraced());
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(graceForm.getUserId());
			bo.setNoGracingReason(to.getNoGracingReason());
			bo.setPassOrFail(to.getPassOrFail());
			bo.setPracticalEseGrace(to.getPracticalEseGrace());
			bo.setPracticalInternalGrace(to.getPracticalInternalGrace());
			bo.setPracticalObtain(to.getPracticalObtain());
			bo.setPracticalObtainGraced(to.getPracticalEseGrace()+to.getPracticalInternalGrace());
			bo.setPracticalTotalSubInternalMark(to.getPracticalTotalSubInternalMark());
			Student student = new Student();
			student.setId(to.getStudentId());
			bo.setStudent(student);
			if(to.getStudentPracticalMark()!=null)
			 bo.setStudentPracticalMark(to.getStudentPracticalMark());
			if(to.getStudentTheoryMark()!=null)
			 bo.setStudentTheoryMark(to.getStudentTheoryMark());
			Subject sub = new Subject();
			sub.setId(to.getSubjectId());
			bo.setSubject(sub);
			bo.setSubType(to.getSubType());
			bo.setTermNumber(to.getTermNumber());
			bo.setTheoryEseGrace(to.getTheoryEseGrace());
			bo.setTheoryInternalGrace(to.getTheoryInternalGrace());
			bo.setTheoryObtainGraced(to.getTheoryInternalGrace()+to.getTheoryEseGrace());
			bo.setTheoryObtain(to.getTheoryObtain());
			bo.setTheoryTotalSubInternalMark(to.getTheoryTotalSubInternalMark());
			if(to.getTheoryeseMaximumMark()!=null)
			 bo.setTheoryeseMaximumMark(to.getTheoryeseMaximumMark());
			if(to.getPracticaleseMaximumMark()!=null)
			 bo.setPracticaleseMaximumMark(to.getPracticaleseMaximumMark());
			submarkboset.add(bo);
		processbo.setSubjectMarkBoSet(submarkboset);
			
		}
		return processbo;
	}

	public ExamGracingBO convertGracingFormtoBo(ExamGracingForm graceForm) {
		ExamGracingBO bo = new ExamGracingBO();
		bo.setAcademicYear(graceForm.getAcademicYear());
		bo.setIsActive(true);
		bo.setAppliedYear(graceForm.getAppliedYear());
		Course course = new Course();
		 course.setId(Integer.valueOf(graceForm.getCourseId()));
		bo.setCourse(course);
		bo.setCreatedBy(graceForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		bo.setModifiedBy(graceForm.getUserId());
		bo.setPercentage(BigDecimal.valueOf(Double.parseDouble(graceForm.getPercentage())));
		bo.setProcessed(false);
		bo.setRemark(graceForm.getRemark());
		bo.setSemester(Integer.valueOf(graceForm.getSchemeId()));
		bo.setOnlyPass(graceForm.getOnlyPass());
		Student student = new Student();
		student.setId(Integer.valueOf(graceForm.getStudentId()));
		bo.setStudent(student);
		return bo;
	}

}