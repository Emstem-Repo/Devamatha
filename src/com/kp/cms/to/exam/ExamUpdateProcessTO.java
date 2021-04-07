package com.kp.cms.to.exam;

import java.io.Serializable;

/**
 * Feb 21, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamUpdateProcessTO implements Serializable,Comparable<ExamUpdateProcessTO>{

	private Integer internalExamId;
	private Integer internalExamTypeId;

	public void setInternalExamId(Integer internalExamId) {
		this.internalExamId = internalExamId;
	}

	public Integer getInternalExamId() {
		return internalExamId;
	}

	public void setInternalExamTypeId(Integer internalExamTypeId) {
		this.internalExamTypeId = internalExamTypeId;
	}

	public Integer getInternalExamTypeId() {
		return internalExamTypeId;
	}

	@Override
	public int compareTo(ExamUpdateProcessTO arg0) {
		/*if(arg0!=null && this!=null && arg0.getInternalExamTypeId()!=null
				 && this.getInternalExamTypeId()!=null){
			return this.getInternalExamTypeId().compareTo(arg0.getInternalExamTypeId());
		}else
		return 0;*/
		if(arg0!=null && this!=null ){
			if (arg0.getInternalExamTypeId() != null && this.getInternalExamTypeId()!=null){
				if(this.getInternalExamTypeId() >  arg0.getInternalExamTypeId())
					return 1;
				else if(this.getInternalExamTypeId() <  arg0.getInternalExamTypeId()){
					return -1;
				}else
					return 0;
		    }
		}
		return 0;
	}

}
