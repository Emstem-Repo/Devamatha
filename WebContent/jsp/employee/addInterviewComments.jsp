	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	
	<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
	
	<script type="text/javascript" src="js/jquery.js">
	</script>
	<script type="text/javascript">
	
	$(document).ready(function(){
		$(".text1").change(function(){
			 changeValue();
		});
		changeValue();
		});
				
	 function changeValue(){
		 $(".text1").each(function() {
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	            	if(this.value == 0){
			        	  $(".txt").hide(); $(".txt").val=""; $(".txt1").hide();
			        	  $(".txt1").val=""; $(".int2").hide(); $(".sum1").hide();
			        	  $(".txt2").hide(); $(".txt2").val=""; $(".int3").hide();
			        	  $(".sum2").hide(); $(".int7").hide(); $(".int8").hide();
			        	  $(".int9").hide(); $(".txt8").hide(); $(".txt8").val="";
			        	  $(".txt9").hide(); $(".txt9").val=""; $(".txt10").hide();
			        	  $(".txt10").val=""; $(".sum8").hide(); $(".sum9").hide();
			        	  $(".sum10").hide(); $(".internal1").hide(); $(".internal2").hide();
			        	  $(".internal3").hide(); $(".internal4").hide(); $(".internal5").hide();
			        	  $(".internal6").hide();$(".sum").hide();$(".int1").hide();
			        	 
			          }
		          if(this.value == 1){
		        	  $(".txt").show(); $(".txt").val=""; $(".txt1").hide();
		        	  $(".txt1").val=""; $(".int2").hide(); $(".sum1").hide();
		        	  $(".txt2").hide(); $(".txt2").val=""; $(".int3").hide();
		        	  $(".sum2").hide(); $(".int7").hide(); $(".int8").hide();
		        	  $(".int9").hide(); $(".txt8").hide(); $(".txt8").val="";
		        	  $(".txt9").hide(); $(".txt9").val=""; $(".txt10").hide();
		        	  $(".txt10").val=""; $(".sum8").hide(); $(".sum9").hide();
		        	  $(".sum10").hide(); $(".internal1").show(); $(".internal2").hide();
		        	  $(".internal3").hide(); $(".internal4").hide(); $(".internal5").hide();
		        	  $(".internal6").hide();$(".sum").show();
		        	 
		          }
		          if(this.value == 2){
		        	  $(".txt").show(); $(".sum").show(); $(".int1").show();
		        	  $(".txt1").show(); $(".sum1").show(); $(".int2").show();
		        	  $(".txt2").hide(); $(".sum2").hide(); $(".int3").hide();
		        	  $(".txt2").val=""; $(".internal1").show(); $(".internal2").show();
		        	  $(".internal3").hide(); $(".internal4").hide(); $(".internal5").hide();
		        	  $(".internal6").hide(); $(".txt8").hide(); $(".txt9").hide();
		        	  $(".txt10").hide(); $(".sum8").hide(); $(".sum9").hide();
		        	  $(".sum10").hide(); $(".int7").hide(); $(".int8").hide(); $(".int9").hide();
		          }
		          if(this.value == 3){
		        	  $(".txt").show(); $(".txt1").show(); $(".txt2").show();
		        	  $(".sum").show(); $(".sum1").show(); $(".sum2").show();
		        	  $(".int1").show(); $(".int2").show(); $(".int3").show();
		        	  $(".internal1").show(); $(".internal2").show(); $(".internal3").show();
		        	  $(".internal4").hide(); $(".internal5").hide(); $(".internal6").hide();
		        	  $(".txt8").hide(); $(".txt9").hide(); $(".txt10").hide();
		        	  $(".sum8").hide(); $(".sum9").hide(); $(".sum10").hide();
		        	  $(".int7").hide(); $(".int8").hide(); $(".int9").hide();
		          }
		          if(this.value == 4){
		        	  $(".txt").show(); $(".txt1").show(); $(".txt2").show(); $(".txt8").show();
		        	  $(".sum").show(); $(".sum1").show(); $(".sum2").show(); $(".sum8").show();
		        	  $(".int1").show(); $(".int2").show(); $(".int3").show(); $(".int7").show();
		        	  $(".internal1").show(); $(".internal2").show(); $(".internal3").show(); $(".internal4").show(); 
		        	  $(".internal5").hide(); $(".internal6").hide();
		        	  $(".txt9").hide(); $(".txt10").hide(); 
		        	  $(".sum9").hide(); $(".sum10").hide();
		        	  $(".int8").hide(); $(".int9").hide();
		        	  }
		          if(this.value == 5){
		        	  $(".txt").show(); $(".txt1").show(); $(".txt2").show(); $(".txt8").show(); $(".txt9").show();
		        	  $(".sum").show(); $(".sum1").show(); $(".sum2").show(); $(".sum8").show(); $(".sum9").show();
		        	  $(".int1").show(); $(".int2").show(); $(".int3").show(); $(".int7").show(); $(".int8").show();
		        	  $(".internal1").show(); $(".internal2").show(); $(".internal3").show(); $(".internal4").show(); $(".internal5").show();
		        	  $(".internal6").hide(); $(".txt10").hide();$(".sum10").hide();$(".int9").hide();
		          }
		          if(this.value == 6){
		        	  $(".txt").show(); $(".txt1").show(); $(".txt2").show(); $(".txt8").show(); $(".txt9").show(); $(".txt10").show();
		        	  $(".sum").show(); $(".sum1").show(); $(".sum2").show(); $(".sum8").show(); $(".sum9").show(); $(".sum10").show();
		        	  $(".int1").show(); $(".int2").show(); $(".int3").show(); $(".int7").show(); $(".int8").show(); $(".int9").show();
		        	  $(".internal1").show(); $(".internal2").show(); $(".internal3").show(); $(".internal4").show(); $(".internal5").show(); $(".internal6").show();
		          }
	            }
	        });
		 }
	 $(document).ready(function(){
			$(".text2").change(function(){
				 changeValue1();
			}); 
			changeValue1();
			});
	 function changeValue1(){
		 $(".text2").each(function() {
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	            	if(this.value == 0){
	            		 $(".txt3").hide(); $(".sum3").hide(); $(".int4").hide(); $(".txt4").val="";
			        	  $(".txt4").hide(); $(".sum4").hide(); $(".int5").hide();
			        	  $(".txt5").hide(); $(".txt5").val=""; $(".int6").hide(); $(".sum5").hide();
			        	  $(".txt11").hide(); $(".txt12").hide(); $(".txt13").hide(); 
			        	  $(".sum11").hide(); $(".sum12").hide(); $(".sum13").hide();
			        	  $(".int10").hide(); $(".int11").hide(); $(".int12").hide();
			        	  $(".external1").hide();$(".int4").hide();
			        	  $(".external2").hide(); $(".external3").hide(); $(".external4").hide(); $(".external5").hide(); $(".external6").hide();
			          }
		          if(this.value == 1){
		        	  $(".txt3").show(); $(".sum3").show(); $(".int4").show(); $(".txt4").val="";
		        	  $(".txt4").hide(); $(".sum4").hide(); $(".int5").hide();
		        	  $(".txt5").hide(); $(".txt5").val=""; $(".int6").hide(); $(".sum5").hide();
		        	  $(".txt11").hide(); $(".txt12").hide(); $(".txt13").hide(); 
		        	  $(".sum11").hide(); $(".sum12").hide(); $(".sum13").hide();
		        	  $(".int10").hide(); $(".int11").hide(); $(".int12").hide();
		        	  $(".external1").show();
		        	  $(".external2").hide(); $(".external3").hide(); $(".external4").hide(); $(".external5").hide(); $(".external6").hide();
		          }
		          if(this.value == 2){
		        	  $(".txt3").show(); $(".sum3").show(); $(".int4").show(); $(".txt4").show();
		        	  $(".sum4").show(); $(".int5").show(); $(".txt5").hide(); $(".int6").hide();
		        	  $(".sum5").hide(); $(".txt5").val=""; $(".txt11").hide(); $(".txt12").hide();
		        	  $(".sum11").hide(); $(".sum12").hide(); $(".sum13").hide();
		        	  $(".int10").hide(); $(".int11").hide(); $(".int12").hide();
		        	  $(".txt13").hide(); $(".external1").show(); $(".external2").show(); $(".external3").hide();
		        	  $(".external4").hide(); $(".external5").hide(); $(".external6").hide(); 
		        	  }
		          if(this.value == 3){
		        	  $(".txt3").show(); $(".txt4").show(); $(".txt5").show(); $(".sum3").show();
		        	  $(".sum4").show(); $(".sum5").show(); $(".int4").show(); $(".int5").show();
		        	  $(".int6").show(); $(".txt11").hide(); $(".txt12").hide(); $(".txt13").hide();
		        	  $(".sum11").hide(); $(".sum12").hide(); $(".sum13").hide();
		        	  $(".int10").hide(); $(".int11").hide(); $(".int12").hide();
		        	  $(".external1").show(); $(".external2").show(); $(".external3").show(); $(".external4").hide();
		        	  $(".external5").hide(); $(".external6").hide(); 
		        	  }
		          if(this.value == 4){
		        	  $(".txt3").show(); $(".txt4").show(); $(".txt5").show(); $(".sum3").show();
		        	  $(".sum4").show(); $(".sum5").show(); $(".int4").show(); $(".int5").show();
		        	  $(".int6").show(); $(".txt11").show(); $(".sum11").show(); $(".int10").show();$(".sum12").hide(); $(".sum13").hide();
		        	  $(".txt12").hide(); $(".txt13").hide(); $(".external1").show(); $(".external2").show();  $(".int11").hide(); $(".int12").hide();
		        	  $(".external3").show(); $(".external4").show(); $(".external5").hide(); $(".external6").hide();
		          }
		          if(this.value == 5){
		        	  $(".txt3").show(); $(".txt4").show(); $(".txt5").show();
		        	  $(".sum3").show(); $(".sum4").show(); $(".sum5").show();
		        	  $(".int4").show(); $(".int5").show(); $(".int6").show();
		        	  $(".txt11").show(); $(".txt12").show(); $(".sum11").show(); $(".sum13").hide();$(".int12").hide();
		        	  $(".sum12").show(); $(".int10").show(); $(".int11").show();
		        	  $(".txt13").hide(); $(".external1").show(); $(".external2").show();
		        	  $(".external3").show(); $(".external4").show(); $(".external5").show(); $(".external6").hide();
		          }
		          if(this.value == 6){
		        	  $(".txt3").show(); $(".txt4").show(); $(".txt5").show(); $(".sum3").show(); $(".sum4").show();
		        	  $(".sum5").show(); $(".int4").show(); $(".int5").show(); $(".int6").show(); $(".txt11").show();
		        	  $(".txt12").show(); $(".txt13").show(); $(".sum11").show(); $(".sum12").show(); $(".sum13").show();
		        	  $(".int10").show(); $(".int11").show(); $(".int12").show(); $(".external1").show(); $(".external2").show();
		        	  $(".external3").show(); $(".external4").show(); $(".external5").show(); $(".external6").show();
		          }
	            }
	        });
		 }
	 
		function closeWindow(){
			document.location.href="newInterviewComments.do?method=searchDetails";
		}
		function addDetails(){
			
			document.getElementById("method").value="addInterviewComments";
			document.newInterviewCommentsForm.submit();
		}
	
		
		function printWindow(){
			var url= "newInterviewComments.do?method=printInterviewComments";
			myRef = window.open(url, " ",
					"left=10,top=10,width=800,height=900,toolbar=0,resizable=1,scrollbars=1");
		}
		function calc(count){ 
				var count1=0;
				var max=document.getElementById("maxScore_"+count).value;
				
				if(document.getElementById("internalInterviewer1_"+count).value <= +max){
					count1 +=document.getElementById("internalInterviewer1_"+count).value*1;
					
				}else{
					alert("Interview score should not be more than Maximum Score");
					document.getElementById("internalInterviewer1_"+count).focus();
				}
				
				if(document.getElementById("internalInterviewer2_"+count).value <= +max){
					 count1 +=document.getElementById("internalInterviewer2_"+count).value*1;
				 }else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("internalInterviewer2_"+count).focus();
				 }
				 
				if(document.getElementById("internalInterviewer3_"+count).value <= +max){
					count1 +=document.getElementById("internalInterviewer3_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("internalInterviewer3_"+count).focus();
				 }

				if(document.getElementById("internalInterviewer4_"+count).value <= +max){
					count1 +=document.getElementById("internalInterviewer4_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("internalInterviewer4_"+count).focus();
				 }

				if(document.getElementById("internalInterviewer5_"+count).value <= +max){
					count1 +=document.getElementById("internalInterviewer5_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("internalInterviewer5_"+count).focus();
				 }

				if(document.getElementById("internalInterviewer6_"+count).value <= +max){
					count1 +=document.getElementById("internalInterviewer6_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("internalInterviewer6_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer1_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer1_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer1_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer2_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer2_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer2_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer3_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer3_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer3_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer4_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer4_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer4_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer5_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer5_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer5_"+count).focus();
				 }
				 
				if(document.getElementById("externalInterviewer6_"+count).value <= +max){
					count1 +=document.getElementById("externalInterviewer6_"+count).value*1;
				}else{
					 alert("Interview score should not be more than Maximum Score");
					 document.getElementById("externalInterviewer6_"+count).focus();
				 }
				 
				 var total= 0;
				 if(document.getElementById("internalInterviewer1_"+count)!=null){
					 if(document.getElementById("internalInterviewer1_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("internalInterviewer2_"+count)!=null){
					 if(document.getElementById("internalInterviewer2_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("internalInterviewer3_"+count)!=null){
					 if(document.getElementById("internalInterviewer3_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("internalInterviewer4_"+count)!=null){
					 if(document.getElementById("internalInterviewer4_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("internalInterviewer5_"+count)!=null){
					 if(document.getElementById("internalInterviewer5_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("internalInterviewer6_"+count)!=null){
					 if(document.getElementById("internalInterviewer6_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer1_"+count)!=null){
					 if(document.getElementById("externalInterviewer1_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer2_"+count)!=null){
					 if(document.getElementById("externalInterviewer2_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer3_"+count)!=null){
					 if(document.getElementById("externalInterviewer3_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer4_"+count)!=null){
					 if(document.getElementById("externalInterviewer4_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer5_"+count)!=null){
					 if(document.getElementById("externalInterviewer5_"+count).value!=''){
						 total=total+1;
					 }
				 }
				 if(document.getElementById("externalInterviewer6_"+count)!=null){
					 if(document.getElementById("externalInterviewer6_"+count).value!=''){
						 total=total+1;
					 }
				 }
				var avg=avg=count1/total;
				
				var dec=2;
				var result=Math.round(avg*Math.pow(10,dec))/Math.pow(10,dec);
				if(!isNaN(result)){
				document.getElementById("avg_"+count).value=result;
				}else{
					result = 0;
					document.getElementById("avg_"+count).value=result;
				}
				
				$(document).ready(function(){
					
			        //iterate through each textboxes and add keyup
			        //handler to trigger sum event
			        $(".txt6").each(function() {
			        	
			            $(this).change(function(){
			               calculateSum6();
			         });
			           calculateSum6();
			        });
						//calculateSum6();
			    });
			}
		
		function isNumberKey(evt) {
			
			var charCode = (evt.which) ? evt.which : event.keyCode
					if((evt.ctrlKey || evt.keyCode == 86)  ){
						return true;
					}
		    if ((charCode > 45 && charCode < 58) || charCode == 8){ 
		        return true; 
		    } 
		    return false;  
		}
		// 1.
		$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt").each(function() 
	    	     {
	 
	            $(this).keyup(function(){
	                calculateSum();
	            });
	            calculateSum();
	        });
	    });
	 
	    function calculateSum() {
	 
	        var sum = 0;
	        //iterate through each textboxes and add the values
	        $(".txt").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	            	
	                sum += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum").html(sum.toFixed());
	    }
		//2.
	    $(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt1").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum1();
	            });
	            calculateSum1();
	        });
	 
	    });
	 
	    function calculateSum1() {
	 
	        var sum1 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt1").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum1 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum1").html(sum1.toFixed());
	    }

		//3.
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt2").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum2();
	            });
	            calculateSum2();
	        });
	 
	    });
	 
	    function calculateSum2() {
	 
	        var sum2 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt2").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum2 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum2").html(sum2.toFixed());
	    }

	    //4.
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt3").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum3();
	            });
	            calculateSum3();
	        });
	 
	    });
	 
	    function calculateSum3() {
	 
	        var sum3 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt3").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum3 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum3").html(sum3.toFixed());
	    }

	    //5.//
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt4").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum4();
	            });
	            calculateSum4();
	        });
	 
	    });
	 
	    function calculateSum4() {
	 
	        var sum4 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt4").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum4 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum4").html(sum4.toFixed());
	    }

	    //6.
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt5").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum5();
	            });
	            calculateSum5();
	        });
	 
	    });
	 
	    function calculateSum5() {
	 
	        var sum5 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt5").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum5 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum5").html(sum5.toFixed());
	    }


	  //7.
	    //
			$(document).ready(function(){
				
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt6").each(function() {
	        	
	            $(this).change(function(){
	               calculateSum6();
	         });
	           calculateSum6();
	        });
				calculateSum6();
	    });
	 
	    function calculateSum6() {
	 		
	        var sum6 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt6").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	               // sum6 += Math.round(parseFloat(this.value));
	            	 sum6 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 4 decimal places
	        
	        $("#sum6").html(sum6.toFixed());
	    }

	  //8.
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt7").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum7();
	            });
	            calculateSum7();
	        });
	 
	    });
	 
	    function calculateSum7() {
	 
	        var sum7 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt7").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum7 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum7").html(sum7.toFixed());
	    }
	    //9.txt8
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt8").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum8();
	            });
	            calculateSum8();
	        });
	 
	    });
	 
	    function calculateSum8() {
	 
	        var sum8 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt8").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum8 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum8").html(sum8.toFixed());
	    }
	    //10.txt9
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt9").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum9();
	            });
	            calculateSum9();
	        });
	 
	    });
	 
	    function calculateSum9() {
	 
	        var sum9 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt9").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum9 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum9").html(sum9.toFixed());
	    }
	    //11.txt10
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt10").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum10();
	            });
	            calculateSum10();
	        });
	 
	    });
	 
	    function calculateSum10() {
	 
	        var sum10 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt10").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum10 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum10").html(sum10.toFixed());
	    }
	    //12.txt11
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt11").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum11();
	            });
	            calculateSum11();
	        });
	 
	    });
	 
	    function calculateSum11() {
	 
	        var sum11 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt11").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum11 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum11").html(sum11.toFixed());
	    }
	    //13.txt12
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt12").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum12();
	            });
	            calculateSum12();
	        });
	 
	    });
	 
	    function calculateSum12() {
	 
	        var sum12 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt12").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum12 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum12").html(sum12.toFixed());
	    }
	    //14.txt13
	    //
			$(document).ready(function(){
			 
	        //iterate through each textboxes and add keyup
	        //handler to trigger sum event
	        $(".txt13").each(function() {
	 
	            $(this).keyup(function(){
	                calculateSum13();
	            });
	            calculateSum13();
	        });
	 
	    });
	 
	    function calculateSum13() {
	 
	        var sum13 = 0;
	        //iterate through each textboxes and add the values
	        $(".txt13").each(function() {
	 
	            //add only if the value is number
	            if(!isNaN(this.value) && this.value.length!=0) {
	                sum13 += parseFloat(this.value);
	            }
	 
	        });
	        //.toFixed() method will roundoff the final sum to 2 decimal places
	        $("#sum13").html(sum13.toFixed());
	    }
	    
	</script>
	</head>
	
	<body>
	<html:form action="/newInterviewComments">
	<html:hidden property="formName" value="newInterviewCommentsForm"/>
	<html:hidden property="method" styleId="method" value="addInterviewComments"/>
	<table width="98%" border="0">
	  <tr>
	    <td><span class="heading">
					Employee Info
				<span class="Bredcrumbs">&gt;&gt; Interview Comments&gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
	<td width="1271" background="images/Tcenter.gif" class="body" >
	<div align="left">
	<strong class="boxheader">Interview Comments</strong>
	</div>
	</td>
	<td width="15" >
	<img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
	</tr>
	<tr>
	<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
	<td valign="top" class="news"><div align="center">
	<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td height="20" colspan="6" align="left">
	<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
	<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div>
	</td>
	</tr>
	<tr>
	<td height="20" colspan="6" valign="top" class="body" >
	
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
	<td><img src="images/01.gif" width="5" height="5"></td>
	<td width="914" background="images/02.gif"></td>
	<td><img src="images/03.gif" width="5" height="5"></td>
	</tr>
	<tr>
	<td width="5" background="images/left.gif"></td>
	<td width="100%" height="29" valign="top">
	
	<table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
			<logic:notEmpty name="newInterviewCommentsForm" property="newInterviewCommentsList">
			<logic:iterate id="commentsList" name="newInterviewCommentsForm" property="newInterviewCommentsList">
		<tr>
				<td class="rows-odd"><div align="left" >Application No:</div></td>
				<td class="row-even" colspan="1"> <bean:write name="commentsList" property="applicationNo"/>
				 </td>
		  		</tr>
				<tr>
					<td class="rows-odd"><div align="left" >Name:</div></td>
					<td class="row-even"><bean:write name="commentsList" property="name"/>
				</td>
			</tr>
			<tr>
				<td class="rows-odd"><div align="left" >Department:</div></td>
				<td class="row-even">
				<input type="hidden"  id="tempDeptId" value='<bean:write name="newInterviewCommentsForm" property="department"/>' />
				<html:select property="department" styleClass="comboLarge" styleId="departmentId">
				<html:option value="">
				<bean:message key="knowledgepro.select" />-</html:option>
				<html:optionsCollection name="newInterviewCommentsForm" property="departmentMap"
							label="name" value="id" />
				</html:select>
				</td>
			</tr>
		</logic:iterate>
		</logic:notEmpty>
		
		<logic:empty name="newInterviewCommentsForm" property="newInterviewCommentsList">
			
		<tr>
				<td class="rows-odd"><div align="left" >Application No:</div></td>
				<td class="row-even" colspan="1"> 
				 </td>
		  		</tr>
				<tr>
					<td class="rows-odd"><div align="left" >Name:</div></td>
					<td class="row-even">
				</td>
			</tr>
			<tr>
				<td class="rows-odd"><div align="left" >Department:</div></td>
				<td class="row-even">
				<input type="hidden"  id="tempDeptId" value='<bean:write name="commentsList" property="department"/>' />
				<html:select property="department" styleClass="comboLarge" styleId="departmentId">
				<html:option value="">
				<bean:message key="knowledgepro.select" />-</html:option>
				<html:optionsCollection name="newInterviewCommentsForm" property="departmentMap"
							label="name" value="id" />
				</html:select>
				</td>
			</tr>
		
		</logic:empty>
			<tr>
				<td class="rows-odd"><div align="left" >No.of Internal Interviewers:</div></td>
				<td class="row-even">
				<input type="hidden" name="s1" id="s1" value='<bean:write name="newInterviewCommentsForm" property="noOfInternalInterviewers"/>'/>
				<html:select property="noOfInternalInterviewers" styleClass="text1" name="newInterviewCommentsForm">
				
				<logic:notEmpty name="newInterviewCommentsForm" property="map">
											<html:optionsCollection property="map" label="key" value="value" />
										</logic:notEmpty>
				</html:select>
				</td>
			</tr>
			<tr>
				<td class="rows-odd"><div align="left" >No.of External Interviewers:</div></td>
				<td class="row-even">
				<input type="hidden" name="s1" id="s1" value='<bean:write name="newInterviewCommentsForm" property="noOfExternalInterviewers"/>'/>
				<html:select property="noOfExternalInterviewers" styleClass="text2" name="newInterviewCommentsForm" >
				
				<logic:notEmpty name="newInterviewCommentsForm" property="map">
											<html:optionsCollection property="map" label="value" value="key" />
										</logic:notEmpty>
				</html:select>
				</td>
			</tr>
	</table>
	
	</td>
	<td background="images/right.gif" width="5" height="29"></td>
	</tr>
	<tr>
	<td height="5"><img src="images/04.gif" width="5" height="5"></td>
	<td background="images/05.gif"></td>
	<td><img src="images/06.gif"></td>
	</tr>
	</table>
	
	<table width="100%" cellspacing="1" cellpadding="2">
	  <tr>
	    <td colspan="2" class="heading" align="left">Grades </td>
	  </tr>
	  <tr>
	    <td colspan="3" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	      <tr>
	        <td ><img src="images/01.gif" width="5" height="5" /></td>
	        <td width="914" background="images/02.gif"></td>
	        <td><img src="images/03.gif" width="5" height="5" /></td>
	      </tr>
	      <tr>
	        <td width="5"  background="images/left.gif"></td>
	        <td valign="top">
	         <table width="100%" cellspacing="1" cellpadding="2">
	          <tr>
			<td class="row-odd" align="center">
				<label> Item </label></td>
			
			<td class="row-odd" colspan="6" align="center">
				<label> Internal Interviewers </label><br/>	
				</td>
			<td class="row-odd" colspan="6" align="center">
				<label> External Interviewers </label><br/>	
				</td>
			<td class="row-odd" align="center">
				<label>Average</label>	</td>
			<td class="row-odd" align="center">
				<label>Max</label>		</td>
		</tr>
		<tr>
		<td class="row-odd" align="center"></td>
		<td class="row-odd" align="center"><span class="int1">1</span></td> 
		<td class="row-odd" align="center"><span class="int2">2</span></td> 
		<td class="row-odd" align="center"><span class="int3">3</span></td> 
		<td class="row-odd" align="center"><span class="int7">4</span></td> 
		<td class="row-odd" align="center"><span class="int8">5</span></td> 
		<td class="row-odd" align="center"><span class="int9">6</span></td> 
		<td class="row-odd" align="center"><span class="int4">1</span></td> 
		<td class="row-odd" align="center"><span class="int5">2</span></td>
		<td class="row-odd" align="center"><span class="int6">3</span></td> 
		<td class="row-odd" align="center"><span class="int10">4</span></td> 
		<td class="row-odd" align="center"><span class="int11">5</span></td> 
		<td class="row-odd" align="center"><span class="int12">6</span></td> 
		<td class="row-odd" align="center"></td>
		<td class="row-odd" align="center"></td>
		</tr>
		<tr>
		<logic:notEmpty name="newInterviewCommentsForm" property="interviewRatingFactorTOs">
						<nested:iterate id="id" name="newInterviewCommentsForm" property="interviewRatingFactorTOs" indexId="count"  >
						<% String internalInterviewer1="internalInterviewer1_"+count;
							String internalInterviewer2="internalInterviewer2_"+count;
							String internalInterviewer3="internalInterviewer3_"+count;
							String internalInterviewer4="internalInterviewer4_"+count;
							String internalInterviewer5="internalInterviewer5_"+count;
							String internalInterviewer6="internalInterviewer6_"+count;
							String externalInterviewer1="externalInterviewer1_"+count;
							String externalInterviewer2="externalInterviewer2_"+count;
							String externalInterviewer3="externalInterviewer3_"+count;
							String externalInterviewer4="externalInterviewer4_"+count;
							String externalInterviewer5="externalInterviewer5_"+count;
							String externalInterviewer6="externalInterviewer6_"+count;
							String max="maxScore_"+count;
							String avgMethod="calc("+count+")";
							String avgId="avg_"+count;
							%>
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>		
				                <td align="center"><nested:write property="ratingFactor"/></td>
				                	
						                <td align="center">
						                <%  int tab1 = 1;
						                String tabOrder1 = String.valueOf(tab1++); %>
						                <nested:text property="internalInterviewer1"  styleClass="txt" styleId='<%=internalInterviewer1 %>'  size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder1 %>" >
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab2 = 2;
						                String tabOrder2 = String.valueOf(tab2++); %>
						                <nested:text property="internalInterviewer2"  styleClass="txt1" styleId='<%=internalInterviewer2 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder2 %>">
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab3 = 3;
						                String tabOrder3 = String.valueOf(tab3++); %>
						                <nested:text property="internalInterviewer3"  styleClass="txt2" styleId='<%=internalInterviewer3 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder3 %>">
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab4 = 4;
						                String tabOrder4 = String.valueOf(tab4++); %>
						                <nested:text property="internalInterviewer4"  styleClass="txt8" styleId='<%=internalInterviewer4 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder4 %>">
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab5 = 5;
						                String tabOrder5 = String.valueOf(tab5++); %>
						                <nested:text property="internalInterviewer5"  styleClass="txt9" styleId='<%=internalInterviewer5 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder5 %>">
						                </nested:text>
						                </td>
						                 <td align="center">
						                 <% int tab6 = 6;
						                String tabOrder6 = String.valueOf(tab6++); %>
						                <nested:text property="internalInterviewer6"  styleClass="txt10" styleId='<%=internalInterviewer6 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder6 %>">
						                </nested:text>
						                </td>	
						                
						                <td align="center">
						                <% int tab7 = 7;
						                String tabOrder7 = String.valueOf(tab7++); %>
						                <nested:text property="externalInterviewer1" styleClass="txt3" styleId='<%=externalInterviewer1 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder7 %>">
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab8 = 8;
						                String tabOrder8 = String.valueOf(tab8++); %>
						                <nested:text property="externalInterviewer2" styleClass="txt4" styleId='<%=externalInterviewer2 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder8 %>">
						                </nested:text>
						                </td>
						                <td align="center">
						                <% int tab9 = 9;
						                String tabOrder9 = String.valueOf(tab9++); %>
						                <nested:text property="externalInterviewer3" styleClass="txt5" styleId='<%=externalInterviewer3 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder9 %>"> 
						                </nested:text>
						                </td>
						                 <td align="center">
						                 <% int tab10 = 10;
						                String tabOrder10 = String.valueOf(tab10++); %>
						                <nested:text property="externalInterviewer4" styleClass="txt11" styleId='<%=externalInterviewer4 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder10 %>"> 
						                </nested:text>
						                </td>
						                 <td align="center">
						                 <% int tab11 = 11;
						                String tabOrder11 = String.valueOf(tab11++); %>
						                <nested:text property="externalInterviewer5" styleClass="txt12" styleId='<%=externalInterviewer5 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder11 %>"> 
						                </nested:text>
						                </td>
						                 <td align="center">
						                 <% int tab12 = 12;
						                String tabOrder12 = String.valueOf(tab12++); %>
						                <nested:text property="externalInterviewer6" styleClass="txt13" styleId='<%=externalInterviewer6 %>' size="6" maxlength="5" onkeypress="return isNumberKey(event)" onchange="<%=avgMethod%>" tabindex="<%= tabOrder12 %>"> 
						                </nested:text>
						                </td>
				             	<td align="center" id="summation">
      							   <input type="text"  id='<%=avgId %>' value="0" class="txt6" size="6" disabled="disabled" />
      								
      							<script type="text/javascript">
      								calc(<%=count %>);
      							</script>
        							</td>
				                <td align="center">
				                <input type="hidden" name="max" id='<%=max %>' value='<nested:write property="maxScore"/>'/> 
				                <nested:text property="maxScore" size="6"  styleClass="txt7"  disabled="true" onchange="<%=avgMethod%>" />
				              <script type="text/javascript">
      								calc(<%=count %>);
      							</script>
				                </td>
						</nested:iterate>
					</logic:notEmpty>
		</tr>
		<tr>
			<td class="row-odd" align="center">
				<label >Total </label></td>
			<td class="row-even" id="summation" align="center"><span id="sum" class="sum"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum1" class="sum1"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum2" class="sum2"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum8" class="sum8"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum9" class="sum9"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum10" class="sum10"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum3" class="sum3"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum4" class="sum4"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum5" class="sum5"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum11" class="sum11"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum12" class="sum12"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum13" class="sum13"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum6" class="sum6"></span></td>
			<td class="row-even" id="summation" align="center"><span id="sum7" class="sum7"></span></td>
		</tr>
		 <tr>
	    <td colspan="7" class="heading" align="left">Name of the interviewers(Internal):</td>
	      <td colspan="8" class="heading" align="left">Name of the interviewers(External):</td>
	  </tr>
	  <% int tab13 = 13;
	 String tabOrder13 = String.valueOf(tab13++); %>
	 <% int tab14 = 14;
	 String tabOrder14 = String.valueOf(tab14++); %>
	  <tr >
	  <td class="row-even" colspan="7" >1.<html:text property="nameOfInternalInterviewer1" name="newInterviewCommentsForm" size="40" styleClass="internal1" tabindex="<%= tabOrder13 %>"></html:text></td>
	  <td class="row-even" colspan="8">1.<html:text property="nameOfExternalInterviewer1" name="newInterviewCommentsForm" size="40" styleClass="external1" tabindex="<%= tabOrder14 %>"></html:text></td>
	  </tr>
	   <tr>
	   <td class="row-even" colspan="7">2.<html:text property="nameOfInternalInterviewer2" name="newInterviewCommentsForm" size="40" styleClass="internal2" tabindex="<%= tabOrder13 %>"></html:text></td>
	   <td class="row-even" colspan="8">2.<html:text property="nameOfExternalInterviewer2" name="newInterviewCommentsForm" size="40" styleClass="external2" tabindex="<%= tabOrder14 %>"></html:text></td>
	   </tr>
	    <tr>
	    <td class="row-even" colspan="7">3.<html:text property="nameOfInternalInterviewer3" name="newInterviewCommentsForm" size="40" styleClass="internal3" tabindex="<%= tabOrder13 %>"></html:text></td>
	    <td class="row-even" colspan="8">3.<html:text property="nameOfExternalInterviewer3" name="newInterviewCommentsForm" size="40" styleClass="external3" tabindex="<%= tabOrder14 %>"></html:text></td>
	    </tr>
	    <tr>
	    <td class="row-even" colspan="7">4.<html:text property="nameOfInternalInterviewer4" name="newInterviewCommentsForm" size="40" styleClass="internal4" tabindex="<%= tabOrder13 %>"></html:text></td>
	    <td class="row-even" colspan="8">4.<html:text property="nameOfExternalInterviewer4" name="newInterviewCommentsForm" size="40" styleClass="external4" tabindex="<%= tabOrder14 %>"></html:text></td>
	    </tr>
	    <tr>
	    <td class="row-even" colspan="7">5.<html:text property="nameOfInternalInterviewer5" name="newInterviewCommentsForm" size="40" styleClass="internal5" tabindex="<%= tabOrder13 %>"></html:text></td>
	    <td class="row-even" colspan="8">5.<html:text property="nameOfExternalInterviewer5" name="newInterviewCommentsForm" size="40" styleClass="external5" tabindex="<%= tabOrder14 %>"></html:text></td>
	    </tr>
	    <tr>
	    <td class="row-even" colspan="7" >6.<html:text property="nameOfInternalInterviewer6" name="newInterviewCommentsForm" size="40" styleClass="internal6" tabindex="<%= tabOrder13 %>"></html:text></td>
	    <td class="row-even" colspan="8">6.<html:text property="nameOfExternalInterviewer6" name="newInterviewCommentsForm" size="40" styleClass="external6" tabindex="<%= tabOrder14 %>"></html:text></td>
	    </tr>
		</table>
		<table width="100%"  cellspacing="1" cellpadding="2">
		<tr>
			<td class="row-odd">
				<label>Joining Time:</label>
			</td> 
			<td class="row-even">
				<html:text property="joiningTime" name="newInterviewCommentsForm" size="40"></html:text>
			</td>
	  </tr>
		<tr>
			<td class="row-odd">
				<label>Comments:</label>
			</td> 
			<td class="row-even">
				<html:text property="comments" name="newInterviewCommentsForm" maxlength="100"  size="40"></html:text>
			</td>
	  </tr>
	</table>
		<table>
	<tr>
								<td width="100%" height="29" valign="top">
								<table width="100%"  cellspacing="1" cellpadding="2">
								<tr>
								<td width="49%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Print" onclick="printWindow()"/></td>
	           					<td width="49%" height="35" align="left">
	           					<html:submit property="" styleClass="formbutton" value="Submit"></html:submit>
	          					&nbsp;<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>
	          					</td>
								</tr>
								</table> 								
								</td>
								<td background="images/right.gif" width="5" height="29"></td>
							</tr>
	</table>
	</td>
	        <td width="5" height="30"  background="images/right.gif"></td>
	      </tr>
	      <tr>
	        <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	        <td background="images/05.gif"></td>
	        <td><img src="images/06.gif" /></td>
	      </tr>
	    </table></td>
	    <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	  </tr>
	</table>
	
	</td>
	</tr>
	<tr>
	<td height="10" colspan="6" class="body" ></td>
	</tr>
	</table>
	</div></td>
	<td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	</tr>
	<tr>
	<td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
	<td width="100%" background="images/TcenterD.gif"></td>
	<td><img src="images/Tright_02.gif" width="9" height="29"/></td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	</html:form>
	</body>
	<script type="text/javascript">
	var deptId = document.getElementById("tempDeptId").value;
	if (deptId != null && deptId.length != 0) {
		document.getElementById("departmentId").value = deptId;
	}
	
</script>
	</html>	
	