package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Iterator;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.MarksCardSlNo;
import com.kp.cms.forms.exam.MarksCardSiNoForm;
import com.kp.cms.to.exam.MarksCardSiNoTO;

public class MarksCardSiNoHelper {
	private static MarksCardSiNoHelper marksCardSiNoHelper = null;
	public static MarksCardSiNoHelper getInstance(){
		if(marksCardSiNoHelper==null){
			marksCardSiNoHelper = new MarksCardSiNoHelper();
		}
		return marksCardSiNoHelper;
	}
	public MarksCardSlNo convertFormToBo(MarksCardSiNoForm cardSiNoForm)throws Exception {
		MarksCardSlNo bo = new MarksCardSlNo();
		if(cardSiNoForm.getStartNo() != null && !cardSiNoForm.getStartNo().isEmpty()) {
			bo.setStartNo(Integer.parseInt(cardSiNoForm.getStartNo()));
			bo.setCurrentNo(Integer.parseInt(cardSiNoForm.getStartNo()));
		}
		
		bo.setIsActive(true);
		
		bo.setCreatedDate(new Date());
		bo.setCreatedBy(cardSiNoForm.getUserId());
		bo.setAcademicYear(Integer.parseInt(cardSiNoForm.getAcademicYear()));
		bo.setScheme(Integer.parseInt(cardSiNoForm.getSemister()));

		if(cardSiNoForm.getProgramTypeId() != null && !cardSiNoForm.getProgramTypeId().isEmpty())
		{
			ProgramType programType = new ProgramType();
			programType.setId(Integer.parseInt(cardSiNoForm.getProgramTypeId()));
			bo.setProgramType(programType);
		}		
		
		return bo;
	}
	
	
	public List<MarksCardSiNoTO> convertBotoTo(List<MarksCardSlNo> list)throws Exception {
		List<MarksCardSiNoTO> toList = new ArrayList<MarksCardSiNoTO>();
		
		try{
			if(list.size()!=0){
				Iterator<MarksCardSlNo> i=list.iterator();
				while(i.hasNext()){
				MarksCardSiNoTO to = new MarksCardSiNoTO();	
				MarksCardSlNo bo=i.next();	
				to = new MarksCardSiNoTO();
				to.setId(bo.getId());
				to.setStartNo(String.valueOf(bo.getStartNo()));
				to.setCurrentNo(String.valueOf(bo.getCurrentNo()));
				if(bo.getCreatedDate()!=null){
					to.setCreatedDate(bo.getCreatedDate());
				}
				to.setAcademicYear(bo.getAcademicYear());
				to.setProgramTypeId(bo.getProgramType().getId());
				to.setProgramTypeName(bo.getProgramType().getName());
				to.setSemister(bo.getScheme());
				
				toList.add(to);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return toList;
	}
}
