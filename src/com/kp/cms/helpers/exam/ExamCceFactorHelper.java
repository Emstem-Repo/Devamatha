package com.kp.cms.helpers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamCceFactorBO;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.forms.exam.ExamCceFactorForm;
import com.kp.cms.to.exam.ExamCceFactorTO;
import com.kp.cms.utilities.HibernateUtil;

public class ExamCceFactorHelper {
	private static final Log log = LogFactory.getLog(ExamCceFactorHelper.class);
	public static volatile ExamCceFactorHelper examCceFactorHelper = null;

	public static ExamCceFactorHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new ExamCceFactorHelper();
		}
		return examCceFactorHelper;
	}


	public List<ExamCceFactorBO> convertFormToBos(ExamCceFactorForm objForm) {
		
		List<ExamCceFactorBO> listBo= new ArrayList<ExamCceFactorBO>();
	try{
		for(int e=0;objForm.getSelectExam().length>e; e++){
			String examId = objForm.getSelectExam()[e];
			for( int s=0;objForm.getSelectedSubject().length>s;s++)
			{
				String subjectId = objForm.getSelectedSubject()[s];
				ExamCceFactorBO examCceFactorBO = new ExamCceFactorBO();
				ExamDefinition exam=new ExamDefinition();
				Subject subject=new Subject();
				exam.setId(Integer.parseInt(examId));
				examCceFactorBO.setExamId(exam);
				subject.setId(Integer.parseInt(subjectId));
				examCceFactorBO.setSubjectId(subject);
				examCceFactorBO.setAcademicYear(Integer.parseInt(objForm.getYear()));
				examCceFactorBO.setCceFactor(new BigDecimal(objForm.getCceFactor()));
				examCceFactorBO.setCreatedBy(objForm.getUserId());
				examCceFactorBO.setCreatedDate(new Date());
				examCceFactorBO.setLastModifiedDate(new Date());
				examCceFactorBO.setModifiedBy(objForm.getUserId());
				examCceFactorBO.setIsActive(Boolean.valueOf(true));
				examCceFactorBO.setId(objForm.getId());
				listBo.add(examCceFactorBO);
			}
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listBo;
			
   }

	public List<ExamCceFactorTO> convertBoToTos(List<ExamCceFactorBO> examCceFactorBO) {
        List<ExamCceFactorTO> ExamCceFactorList = new ArrayList<ExamCceFactorTO>();
        if(examCceFactorBO != null && !examCceFactorBO.isEmpty())
        {
        	Iterator itr=examCceFactorBO.iterator();
    		while (itr.hasNext()) {
    			ExamCceFactorBO ExamCceFactorBo = (ExamCceFactorBO)itr.next();
    			ExamCceFactorTO ExamCceFactorTo= new ExamCceFactorTO();
    			
    			ExamCceFactorTo.setId(ExamCceFactorBo.getId());
    			ExamCceFactorTo.setExamName(ExamCceFactorBo.getExamId().getName());
    			ExamCceFactorTo.setCode(ExamCceFactorBo.getSubjectId().getCode());
    			ExamCceFactorTo.setSubjectName(ExamCceFactorBo.getSubjectId().getName());
    			ExamCceFactorTo.setCceFactor(String.valueOf(ExamCceFactorBo.getCceFactor()));
    			ExamCceFactorList.add(ExamCceFactorTo);
            }

        }
        return ExamCceFactorList;
    }

	public void setDataBoToForm(ExamCceFactorForm objForm,ExamCceFactorBO examCceFactorBO) {
		 Session session = null;
		 ExamDefinition   examD=null;
        if(examCceFactorBO != null)
        {
        	objForm.setYear(String.valueOf(examCceFactorBO.getAcademicYear()));
        	objForm.setCceFactor(String.valueOf(examCceFactorBO.getCceFactor()));
        	String exam[]=new String[1];
        	exam[0] = String.valueOf(examCceFactorBO.getExamId().getId());
        	objForm.setSelectExam(exam);
        	String sub[]=new String[1];
        	sub[0]=String.valueOf(examCceFactorBO.getSubjectId().getId());
        	objForm.setSelectedSubject(sub);
        	String[] id=objForm.getSelectExam();
        	objForm.setId(examCceFactorBO.getId());
        try
            {
                session = HibernateUtil.getSession();
                String str = "from ExamDefinition a where a.id="+id[0];
                Query query = session.createQuery(str);
                 examD = (ExamDefinition)query.uniqueResult();
                session.flush();
                }
            catch(Exception e)
            {
                log.error("Error during getting getFeedBackQuestionById by id...", e);
                session.flush();
                session.close();
            }
            objForm.setExamType(String.valueOf(examD.getExamType().getId()));
        }
    }


	public List<ExamCceFactorBO> convertToBos(ExamCceFactorForm objForm) {
		
		List<ExamCceFactorBO> listBo= new ArrayList<ExamCceFactorBO>();
	try{
		for(int e=0;objForm.getSelectExam().length>e; e++){
			String examId = objForm.getSelectExam()[e];
			for( int s=0;objForm.getSelectedSubject().length>s;s++)
			{
				String subjectId = objForm.getSelectedSubject()[s];
				ExamCceFactorBO examCceFactorBO = new ExamCceFactorBO();
				ExamDefinition exam=new ExamDefinition();
				Subject subject=new Subject();
				exam.setId(Integer.parseInt(examId));
				examCceFactorBO.setExamId(exam);
				subject.setId(Integer.parseInt(subjectId));
				examCceFactorBO.setSubjectId(subject);
				examCceFactorBO.setAcademicYear(Integer.parseInt(objForm.getYear()));
				examCceFactorBO.setCceFactor(new BigDecimal(objForm.getCceFactor()));
				examCceFactorBO.setCreatedBy(objForm.getUserId());
				examCceFactorBO.setCreatedDate(new Date());
				examCceFactorBO.setLastModifiedDate(new Date());
				examCceFactorBO.setModifiedBy(objForm.getUserId());
				examCceFactorBO.setIsActive(Boolean.valueOf(true));
				listBo.add(examCceFactorBO);
			}
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listBo;
			
   }
}
