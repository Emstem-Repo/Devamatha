package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.helpers.exam.ExamUnvSubCodeHelper;
import com.kp.cms.to.exam.ExamUnvSubCodeTO;
import com.kp.cms.transactionsimpl.exam.ExamUnvSubCodeImpl;

public class ExamUnvSubCodeHandler extends ExamGenHandler {

	ExamUnvSubCodeHelper helper = new ExamUnvSubCodeHelper();
	ExamUnvSubCodeImpl impl = new ExamUnvSubCodeImpl();

	public ArrayList<ExamUnvSubCodeTO> getData_OnSearch(int year, int courseId,
			int schmeNo,String mode) throws BusinessException {
		
		
		
		int schmeId=getSchemeIdByCourse(courseId, year);
		return helper.convertBOToForm(impl.select_UnvSubCode(courseId, schmeNo,
				year,schmeId),mode);
	}

	public boolean update(String subjectId_UnvCodeList) throws Exception {
		subjectId_UnvCodeList = subjectId_UnvCodeList.substring(0,
				subjectId_UnvCodeList.length() - 1);
		String unvCode = "";
		boolean isError=false;
		boolean flag=true;
		
		String[] subjectUnvCode = subjectId_UnvCodeList.split(",");
		//-----------------------------------
		for (int i = 0; i < subjectUnvCode.length; i++) {
			String[] id_code = subjectUnvCode[i].split("_");
			if (id_code.length > 1) {

				if(id_code[1]!=null && id_code[1].trim().length()>0)
				{		
				  unvCode = id_code[1];
				  flag=false;
				  break;
				}
			}
		}
				
			
		if(flag)
		{
			return true;
		}
				
		for (int i = 0; i < subjectUnvCode.length; i++) {
			String[] id_code = subjectUnvCode[i].split("_");
			if (id_code.length > 1) {

				unvCode = id_code[1];
			} else {
				unvCode = "";
				
			}
			
			if(unvCode==null || unvCode.isEmpty())
			{
				flag=true;
				break;
			}
			impl.update(Integer.parseInt(id_code[0]), unvCode);
		}
		
		return isError;
	}
	
	private int getSchemeIdByCourse(int courseId,int academicYear) {

		return helper.convertBOToTO_SchemeNo_usingMaxValue(impl
				.select_Scheme_IdBy_Course(courseId,academicYear));

	}
}
