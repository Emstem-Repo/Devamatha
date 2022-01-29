var jq=$.noConflict();
	jq(document).ready(function(){
		 
	    jq("#casteRadio").click(function(){
	    	alert("casteRadio click");
	    	 jq("#caste").show();
	    	 jq("#religion").hide();
	    	 jq("#institution").hide();
	    });
	    jq("#religionRadio").click(function(){
	    	alert("religionRadio click");
	    	 jq("#caste").hide();
	    	 jq("#religion").show();
	    	 jq("#institution").hide();
	    });
	    jq("#institutionRadio").click(function(){
	    	alert("institutionRadio click");
	    	 jq("#caste").hide();
	    	 jq("#religion").hide();
	    	 jq("#institution").show();
	    });
	    
	    
	    
		if(jq('#casteRadio').is(':checked')){
			alert("casteRadio");
			 jq("#caste").show();
			 jq("#religion").hide();
	    	 jq("#institution").hide();
			 
		}else if(jq('#religionRadio').is(':checked')){
			alert("religionRadio");
			 jq("#caste").hide();
	    	 jq("#religion").show();
	    	 jq("#institution").hide();
		}
		else if(jq('#institutionRadio').is(':checked')){
			alert("institutionRadio");
			 jq("#caste").hide();
	    	 jq("#religion").hide();
	    	 jq("#institution").show();
		}else
		{
			alert("hide all");
			 jq("#caste").hide();
	    	 jq("#religion").hide();
	    	 jq("#institution").hide();
		}
	});
		