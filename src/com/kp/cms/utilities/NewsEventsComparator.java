package com.kp.cms.utilities;
import java.util.Comparator;

import com.kp.cms.to.admin.NewsEventsDetailsTO;

public class NewsEventsComparator implements Comparator<NewsEventsDetailsTO>{

		private int compare=0;

		public int getCompare() {
			return compare;
		}

		public void setCompare(int compare) {
			this.compare = compare;
		}

		public int compare(NewsEventsDetailsTO arg0,NewsEventsDetailsTO arg1) {
			if(compare==0){
				if(arg0!=null && arg1!=null && arg0.getStatus()!=null && arg1.getStatus()!=null){
					return arg1.getStatus().compareTo(arg0.getStatus());
				}		
			}else if(compare==1){
				if (arg0.getDateFrom()!=null  && arg1.getDateFrom()!=null){
					return arg0.getDateFrom().compareTo( arg1.getDateFrom());
				}
			}
			return 0;
		}

		
	}


