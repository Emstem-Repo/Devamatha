package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.bo.exam.StudentsImprovementExamDetailsBO;
import com.kp.cms.forms.exam.StudentsImprovementExamDetailsForm;
import com.kp.cms.transactions.exam.IStudentsImprovementExamDetailsTransaction;
import com.kp.cms.transactionsimpl.exam.StudentsImprovementExamDetailsTransactionsImpl;

public class StudentsImprovementExamDetailsHandler {
	
	private static volatile StudentsImprovementExamDetailsHandler handler = null;
	private static final Log log = LogFactory.getLog(StudentsImprovementExamDetailsHandler.class);
	private StudentsImprovementExamDetailsHandler() {
		
	}
	public static StudentsImprovementExamDetailsHandler getInstance() {
		if (handler == null) {
			handler = new StudentsImprovementExamDetailsHandler();
		}
		return handler;
	}
	
	public Map<Integer, String> loadClassByExamNameAndYear(StudentsImprovementExamDetailsForm actionForm) throws Exception
	{
		IStudentsImprovementExamDetailsTransaction applicationTxn=StudentsImprovementExamDetailsTransactionsImpl.getInstance();
		List applicationBOList =applicationTxn.loadClassByExamNameAndYear(actionForm);
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		if(applicationBOList!=null )
		{
		Iterator iterator=applicationBOList.iterator();
		while(iterator.hasNext())
		{
		 	Object[] objects=(Object[]) iterator.next();
		 	if(objects[0]!=null && objects[1]!=null && objects[2]!=null )
		 	{
			classMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString()+"("+objects[2]+")");
		 	}
		}
		}
		return classMap;
		
	}
	public Map<String,ExamStudentFinalMarkDetailsBO> getStudentsImprovementExamDetails(StudentsImprovementExamDetailsForm form) throws Exception{
		IStudentsImprovementExamDetailsTransaction Txn=StudentsImprovementExamDetailsTransactionsImpl.getInstance();
		List<ExamStudentFinalMarkDetailsBO> studentFinalMarkDetailsBOList = Txn.getStudentsImpExamDetails(form);
		List<Integer> subjectList = new ArrayList<Integer>();
		Map<String,ExamStudentFinalMarkDetailsBO> stuSuppMarksMap = new HashMap<String, ExamStudentFinalMarkDetailsBO>();
		Map<String,ExamStudentFinalMarkDetailsBO> stuImpFinalExamMarksMap = new HashMap<String, ExamStudentFinalMarkDetailsBO>();
		ExamStudentFinalMarkDetailsBO boFromMap =null;
		Iterator itr = studentFinalMarkDetailsBOList.iterator();
		if(studentFinalMarkDetailsBOList.size()>0){
			while(itr.hasNext()){
				ExamStudentFinalMarkDetailsBO bo = (ExamStudentFinalMarkDetailsBO) itr.next();
				if(stuSuppMarksMap.containsKey(bo.getStudentId()+"-"+bo.getSubjectId())){

					double theoryMarks=0;
					double practMarks=0;

					if(bo.getStudentTheoryMarks()!=null && !bo.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
							!bo.getStudentTheoryMarks().equalsIgnoreCase("nr") && !bo.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
							(bo.getStudentPracticalMarks()==null )){					
						boFromMap = stuSuppMarksMap.get(bo.getStudentId()+"-"+bo.getSubjectId());

						if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
								!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
								(boFromMap.getStudentPracticalMarks()==null )){

							if(Double.parseDouble(bo.getStudentTheoryMarks())>Double.parseDouble(boFromMap.getStudentTheoryMarks()))
								stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);
						}
					}

					else if(bo.getStudentPracticalMarks()!=null && !bo.getStudentPracticalMarks().equalsIgnoreCase("Ab")
							&& !bo.getStudentPracticalMarks().equalsIgnoreCase("nr") && !bo.getStudentPracticalMarks().equalsIgnoreCase("MP")
							&& (bo.getStudentTheoryMarks()==null )){
						boFromMap = stuSuppMarksMap.get(bo.getStudentId()+"-"+bo.getSubjectId());

						if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
								!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
								(boFromMap.getStudentPracticalMarks()==null )){
							if(Double.parseDouble(bo.getStudentPracticalMarks())>Double.parseDouble(boFromMap.getStudentPracticalMarks()))
								stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);
						}

					}

					// both subject part
					else{
						if(bo.getStudentPracticalMarks()!=null && bo.getStudentTheoryMarks()!=null){
							if(bo.getStudentTheoryMarks()!=null && !bo.getStudentTheoryMarks().equalsIgnoreCase("Ab") && !bo.getStudentTheoryMarks().equalsIgnoreCase("nr")
									&& !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP")){
								boFromMap = stuSuppMarksMap.get(bo.getStudentId()+"-"+bo.getSubjectId());
								if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
										!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
										(boFromMap.getStudentPracticalMarks()==null )){
									if(Double.parseDouble(bo.getStudentTheoryMarks())>Double.parseDouble(boFromMap.getStudentTheoryMarks()))
										stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);
								}
							}
							else if(bo.getStudentPracticalMarks()!=null && !bo.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
									!bo.getStudentPracticalMarks().equalsIgnoreCase("nr") && !bo.getStudentPracticalMarks().equalsIgnoreCase("MP"))
								boFromMap = stuSuppMarksMap.get(bo.getStudentId()+"-"+bo.getSubjectId());
							if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
									!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
									(boFromMap.getStudentPracticalMarks()==null )){
								if(Double.parseDouble(bo.getStudentPracticalMarks())>Double.parseDouble(boFromMap.getStudentPracticalMarks()))
									stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);
							}
						}

					}
				}
				else{
					if(bo.getStudentTheoryMarks()!=null && bo.getStudentPracticalMarks()==null ){						
						stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);
					}
					else if(bo.getStudentPracticalMarks()!=null && bo.getStudentTheoryMarks()==null ){						
						stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(), bo);

					}
					// both subject part
					else{
						if(bo.getStudentPracticalMarks()!=null && bo.getStudentTheoryMarks()!=null){
							if(bo.getStudentTheoryMarks()!=null)
								stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(),bo);
							else if(bo.getStudentPracticalMarks()!=null)
								stuSuppMarksMap.put(bo.getStudentId()+"-"+bo.getSubjectId(),bo);

						}
					}

				}

			}
			List<ExamStudentFinalMarkDetailsBO> studentRegularExamMarkDetailsBOList = Txn.getStudentsRegularExamMarks(form);
			itr = studentRegularExamMarkDetailsBOList.iterator();

			while(itr.hasNext()){
				ExamStudentFinalMarkDetailsBO bo = (ExamStudentFinalMarkDetailsBO) itr.next();
				if(stuSuppMarksMap.containsKey(bo.getStudentId()+"-"+bo.getSubjectId())){
					//if(bo.getStudentId()==146){
					boFromMap =stuSuppMarksMap.get(bo.getStudentId()+"-"+bo.getSubjectId());
					if(bo.getStudentTheoryMarks()!=null && bo.getStudentPracticalMarks()==null ){
						if(bo.getStudentTheoryMarks()!=null && !bo.getStudentTheoryMarks().equalsIgnoreCase("Ab") && !bo.getStudentTheoryMarks().equalsIgnoreCase("nr") && !bo.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
								(bo.getStudentPracticalMarks()==null )){

							if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
									!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
									(boFromMap.getStudentPracticalMarks()==null )){
								if(bo.getStudentTheoryMarks()!=null && Double.parseDouble(boFromMap.getStudentTheoryMarks())> Double.parseDouble(bo.getStudentTheoryMarks())){
									boFromMap.setImpFlag(true);
									stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);

								}
								else{
									boFromMap.setImpFlag(false);
									stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
								}
							}
							else{
								boFromMap.setImpFlag(false);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
							}
						}
						// if for regular exam student is absent and attended supp/improvement 
						else{

							if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
									!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
									(boFromMap.getStudentPracticalMarks()==null )){
								boFromMap.setImpFlag(true);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);

							}
							else{
								boFromMap.setImpFlag(false);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
							}

						}
					}
					else if(bo.getStudentPracticalMarks()!=null && bo.getStudentTheoryMarks()==null){
						if(bo.getStudentPracticalMarks()!=null && !bo.getStudentPracticalMarks().equalsIgnoreCase("Ab") && !bo.getStudentPracticalMarks().equalsIgnoreCase("nr") && !bo.getStudentPracticalMarks().equalsIgnoreCase("MP")
								&& (bo.getStudentTheoryMarks()==null)){

							if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
									!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
									(boFromMap.getStudentPracticalMarks()==null )){

								if(bo.getStudentPracticalMarks()!=null && Double.parseDouble(boFromMap.getStudentPracticalMarks())> Double.parseDouble(bo.getStudentPracticalMarks())){
									boFromMap.setImpFlag(true);
									stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);

								}
								else{
									boFromMap.setImpFlag(false);
									stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
								}
							}
							else{
								boFromMap.setImpFlag(false);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
							}
						}
						else{
							if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
									!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
									(boFromMap.getStudentPracticalMarks()==null )){
								boFromMap.setImpFlag(true);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);

							}
							else{
								boFromMap.setImpFlag(false);
								stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
							}
						}
					}
					else{

						if(bo.getStudentPracticalMarks()!=null && bo.getStudentTheoryMarks()!=null){
							if(bo.getStudentTheoryMarks()!=null){
								if(bo.getStudentTheoryMarks()!=null && !bo.getStudentTheoryMarks().equalsIgnoreCase("Ab")&& 
										!bo.getStudentTheoryMarks().equalsIgnoreCase("MP") && !bo.getStudentTheoryMarks().equalsIgnoreCase("nr")){

									if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
											!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
											(boFromMap.getStudentPracticalMarks()==null )){

										if(Double.parseDouble(boFromMap.getStudentTheoryMarks())> Double.parseDouble(bo.getStudentTheoryMarks()))
											boFromMap.setImpFlag(true);
									}
									else{
										boFromMap.setImpFlag(true);
									}
								}
								else{
									if(boFromMap.getStudentTheoryMarks()!=null && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("Ab") && 
											!boFromMap.getStudentTheoryMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentTheoryMarks().equalsIgnoreCase("MP") &&
											(boFromMap.getStudentPracticalMarks()==null )){
										boFromMap.setImpFlag(true);
										stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);

									}
									else{
										boFromMap.setImpFlag(false);
										stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
									}
								}
							}
							if(bo.getStudentPracticalMarks()!=null){
								if(bo.getStudentPracticalMarks()!=null && !bo.getStudentPracticalMarks().equalsIgnoreCase("Ab") && !bo.getStudentPracticalMarks().equalsIgnoreCase("nr")
										&& !bo.getStudentPracticalMarks().equalsIgnoreCase("MP")){

									if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
											!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
											(boFromMap.getStudentPracticalMarks()==null )){

										if(Double.parseDouble(boFromMap.getStudentPracticalMarks())> Double.parseDouble(bo.getStudentPracticalMarks())){
											boFromMap.setImpFlag(true);
										}
									}
									else{
										boFromMap.setImpFlag(false);
									}

								}
								else{
									if(boFromMap.getStudentPracticalMarks()!=null && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("Ab") && 
											!boFromMap.getStudentPracticalMarks().equalsIgnoreCase("nr") && !boFromMap.getStudentPracticalMarks().equalsIgnoreCase("MP") &&
											(boFromMap.getStudentTheoryMarks()==null )){
										boFromMap.setImpFlag(true);

									}
									else{
										boFromMap.setImpFlag(false);
									}
								}
							}
							stuImpFinalExamMarksMap.put(boFromMap.getStudentId()+"-"+boFromMap.getSubjectId(), boFromMap);
						}
					}
					//}
				}
			}
		}

		return stuImpFinalExamMarksMap;


	}
	
	public List<StudentsImprovementExamDetailsBO>  saveStudentsImprovementExamMarksFlag(StudentsImprovementExamDetailsForm form,Map<String,ExamStudentFinalMarkDetailsBO> stuImpExamMarksMap) throws Exception{

		Iterator entries = stuImpExamMarksMap.entrySet().iterator();
		List<StudentsImprovementExamDetailsBO> boList = new ArrayList<StudentsImprovementExamDetailsBO>();
		//StudentsImprovementExamDetailsBO studentsImprovementExamDetailsBO =null;
		Classes classes = new Classes();
		
		classes.setId(Integer.parseInt(form.getClassCodeIdsFrom()));
		ExamDefinition def = new ExamDefinition();
		ExamStudentFinalMarkDetailsBO bo=null;
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			StudentsImprovementExamDetailsBO studentsImprovementExamDetailsBO = new StudentsImprovementExamDetailsBO();
			bo  = (ExamStudentFinalMarkDetailsBO) thisEntry.getValue();
			studentsImprovementExamDetailsBO.setClasses(classes);
			Student student = new Student();
			student.setId(bo.getStudentId());
			studentsImprovementExamDetailsBO.setStudent(student);
			def.setId(bo.getExamId());
			studentsImprovementExamDetailsBO.setExamDef(def);
			studentsImprovementExamDetailsBO.setCreatedBy(form.getUserId());
			studentsImprovementExamDetailsBO.setCreatedDate(new Date());
			studentsImprovementExamDetailsBO.setImprovementFlag(bo.getImpFlag());
			studentsImprovementExamDetailsBO.setSubjectId(String.valueOf(bo.getSubjectId()));
			boList.add(studentsImprovementExamDetailsBO);
		}
		return boList;
	}
	

}
