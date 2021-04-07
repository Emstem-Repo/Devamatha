package com.kp.cms.utilities;

import java.util.Comparator;
import com.kp.cms.to.exam.MarksCardTO;

public class MarkComparator implements Comparator<MarksCardTO> {
	private int compare=0;
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(MarksCardTO arg0,MarksCardTO arg1) {
		if(arg0!=null && arg1!=null){
			if(compare==0){
				if (Integer.parseInt(arg0.getSemNo())!= 0 && Integer.parseInt(arg1.getSemNo())!=0){
				if(Integer.parseInt(arg0.getSemNo()) <  Integer.parseInt(arg1.getSemNo()))
					return 1;
				}
			}else if(compare==1){
				if(arg0.getTermNo() >  arg1.getTermNo())
				return 1;
			}else if(compare==2){
				if(arg0.getRegNo()!=null && arg1.getRegNo()!=null){
					return arg0.getRegNo().compareTo(arg1.getRegNo());
				}else
					return 0;
			
			}
			}
		return 0;
	}
	public int getCompare() {
		return compare;
	}

	public void setCompare(int compare) {
		this.compare = compare;
	}
}