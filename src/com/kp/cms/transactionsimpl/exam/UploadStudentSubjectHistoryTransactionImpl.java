package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IUploadStudentSubjectHistoryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadStudentSubjectHistoryTransactionImpl implements
		IUploadStudentSubjectHistoryTransaction {


	@Override
	public boolean addUploadedDate(Map<String, List<Integer>> resultMap,
			String user) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator it = resultMap.entrySet().iterator();
			int count = 0;
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        String register=pairs.getKey().toString();
		        String[] reg=register.split("_");
		        List<SubjectGroup> list=session.createQuery("select cs.subjectGroup from CurriculumSchemeSubject cs" +
		        		" join cs.subjectGroup.subjectGroupSubjectses s" +
		        		" where cs.curriculumSchemeDuration.curriculumScheme.course.id=(select s.admAppln.courseBySelectedCourseId.id" +
		        		" from Student s where s.id="+reg[1]+") and cs.curriculumSchemeDuration.semesterYearNo="+reg[2]+" and cs.curriculumSchemeDuration.curriculumScheme.year="+reg[3]+
		        		" and s.subject.isActive=1 and s.isActive=1 and s.subject.id in (:subject) group by cs.subjectGroup").setParameterList("subject", (ArrayList)pairs.getValue()).list();
		        if(list!=null && !list.isEmpty()){
		        	Iterator<SubjectGroup> itr=list.iterator();
		        	while (itr.hasNext()) {
						SubjectGroup subjectGroup = (SubjectGroup) itr.next();
						boolean check =false;
			        	if(reg[4].equals("e")){
			        		if(!subjectGroup.getIsCommonSubGrp()){
			        			check=true;
			        		}
			        	}else if(reg[4].equals("o")){
			        		if(subjectGroup.getIsCommonSubGrp()){
			        			check=true;
			        		}
			        	}else if(reg[4].equals("s")){
			        		if(!subjectGroup.getIsCommonSubGrp() && subjectGroup.getSecondLanguageId()!=null){
			        			check=true;
			        		}
			        	}
			        	if(check){
			        		List<ExamStudentSubGrpHistoryBO> grouplist=session.createQuery("from ExamStudentSubGrpHistoryBO e" +
			        				" where e.schemeNo="+reg[2]+" and e.studentId="+reg[1]+" and e.subjectGroupId="+subjectGroup.getId()).list();
			        		if(grouplist==null || grouplist.isEmpty()){
			        			ExamStudentSubGrpHistoryBO bo=new ExamStudentSubGrpHistoryBO();
			        			bo.setIsActive(true);
			        			bo.setCreatedBy(user);
			        			bo.setCreatedDate(new Date());
			        			bo.setLastModifiedDate(new Date());
			        			bo.setModifiedBy(user);
			        			bo.setSchemeNo(Integer.parseInt(reg[2]));
			        			bo.setStudentId(Integer.parseInt(reg[1]));
			        			bo.setSubjectGroupId(subjectGroup.getId());
			        			session.save(bo);
			        			if(++count % 20 == 0){
			    					session.flush();
			    					session.clear();
			    				}
			        		}
			        	}
						
					}
		        }
		    }
		    transaction.commit();
		    return true;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}

}
