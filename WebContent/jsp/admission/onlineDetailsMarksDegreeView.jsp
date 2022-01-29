<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<head>
</head>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">

<!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>


<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

<script type="text/javascript">
var jq=$.noConflict();
function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;
   var v;
   for (v = 0; v < sText.length && IsNumber == true; v++) 
      { 
      Char = sText.charAt(v); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
   }




function displayOtherForSubject(i){
	//alert('======='+count);
	var propertyName="detailMark.subjectid"+i;
	var propertyOtherName="detailMark.subjectOther"+i;
	var propertyOtherNameDisplay=""+propertyOtherNameDisplay+i;
	if((document.getElementById("detailMark.subjectid"+i).value=="0") || (document.getElementById("detailMark.subjectid"+i).value=="0") || (document.getElementById("detailMark.subjectid"+i).value=="0")){
		document.getElementById("propertyOtherNameDisplay"+i).style.display = "block";
	}else{
		document.getElementById("propertyOtherNameDisplay"+i).style.display = "none";
		document.getElementById("detailMark.subjectOther"+i).value = "";
	}

	
}

function resetDetailMarkDegree(count){
	 document.getElementById("totalMark").value="";
	 document.getElementById("ObtainedMark").value="";
	 if( document.getElementById("totallangMark")!=null)
	 document.getElementById("totallangMark").value="";
	 if( document.getElementById("ObtaintedlangMark")!=null)
	 document.getElementById("ObtaintedlangMark").value="";
	 if(document.getElementById("totalObtainedMarksCGPA")!=null)
	 document.getElementById("totalObtainedMarksCGPA").value="";
	 if(document.getElementById("totalMarksCGPA")!=null)
	 document.getElementById("totalMarksCGPA").value="";
			
	var i;
	for(i=1;i<=count;i++){
		if(document.getElementById("detailMark.subjectid"+i)!=null)
		document.getElementById("detailMark.subjectid"+i).value="";
		
		if(document.getElementById("detailMark.subject"+i+"TotalMarks")!=null)
		document.getElementById("detailMark.subject"+i+"TotalMarks").value="";
		if(document.getElementById("detailMark.subject"+i+"ObtainedMarks")!=null)
		document.getElementById("detailMark.subject"+i+"ObtainedMarks").value="";
		
		if(document.getElementById("detailMark.subjectOther"+i)!=null){
			document.getElementById("detailMark.subjectOther"+i).value="";
			document.getElementById("propertyOtherNameDisplay"+i).style.display = "none";
		}
		
		if(document.getElementById("detailMark.subject"+i+"Credit")!=null)
		document.getElementById("detailMark.subject"+i+"Credit").value="";
			
		
		
	}
	 resetErrMsgs();
}

function updatetotalMarkPreCBCSS(count)
{
	var totalmark=document.getElementById("totalMark").value;
		totalmark=0;
	var i;
	for(i=11;i<=count;i++){
		var subjectmark=document.getElementById("detailMark.subject"+i+"TotalMarks").value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("totalMark").value=totalmark;
			//}
		}
	}
}
function updateObtainMarkPreCBCSS(count)
{
	var obtainmark=document.getElementById("ObtainedMark").value;
	obtainmark=0;

		var i;
		for(i=11;i<=count;i++){
			var subjectmark=document.getElementById("detailMark.subject"+i+"ObtainedMarks").value;
			if(subjectmark.length==0){
				subjectmark=0;
			}
			if(IsNumeric(obtainmark))
			{
				obtainmark=parseFloat(subjectmark)+parseFloat(obtainmark);
				//if(obtainmark==0){
				//document.getElementById("ObtainedMark").value="";
			//	}else{
					document.getElementById("ObtainedMark").value=obtainmark;
				//}
			}
		}
}

function validate(evt) {
	  var theEvent = evt || window.event;
	  var key = theEvent.keyCode || theEvent.which;
	  key = String.fromCharCode( key );
	  var regex = /[0-9]|\./;
	  if( !regex.test(key) ) {
		  if (theEvent.keyCode!=8){
	    theEvent.returnValue = false;
	    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
	  }
	}

jq(document).ready(function(){
	var options = {};
	if(jq('#CBCSSRadio').is(':checked')){
		 jq(".CBCSS").show();
		 jq(".OTHER").hide();
		
	}else if(jq('#CBCSSNEWRadio').is(':checked')){
		 jq(".CBCSS").show();
		 jq(".OTHER").hide();
		
	}
	else if(jq('#OTHERRadio').is(':checked')){
		 jq(".OTHER").show();
		 jq(".CBCSS").hide();
		 
	}
	
	else{
	jq(".CBCSS").hide();
	jq(".OTHER").hide();
	
	}
	jq("#CBCSSRadio").click(function(){
		 jq(".CBCSS").show(2000);
		 jq(".OTHER").hide();
		 
	  });

	jq("#CBCSSNEWRadio").click(function(){
		 jq(".CBCSS").show(2000);
		 jq(".OTHER").hide();
		 
	  });
	  
	jq("#OTHERRadio").click(function(){
		     jq(".CBCSS").hide();
			 jq(".OTHER").show(2000);
			
		  });

	

	  
	});



var hook = true;


function appendMethodOnBrowserClose(){
	hook = false;
}
$(function() {
	 $("a,.formbutton").click(function(){
		hook =false;
	  });
});

window.onbeforeunload = confirmExit;
  function confirmExit()
  {
	  if(hook){
		  hook =false;
		}else{
			hook =true;
		}
  }

$(document).ready(function() {	
	
	var ctrlKeyDown = false;

	$(document).on("keydown", keydown);
    $(document).on("keyup", keyup);
   

    function keydown(e) { 
        if ((e.which || e.keyCode) == 116 || ((e.which || e.keyCode) == 82 && ctrlKeyDown)) {
            // Pressing F5 or Ctrl+R
            e.preventDefault();
        } else if ((e.which || e.keyCode) == 17) {
            // Pressing  only Ctrl
            ctrlKeyDown = true;
        }
    };

    function click(e){
    	e.preventDefault();
    };

    function keyup(e){
        // Key up Ctrl
        if ((e.which || e.keyCode) == 17) 
            ctrlKeyDown = false;
    };
    
    });

</script>

</head>

<body>

<table width="80%" style="background-color: #F0F8FF" align="center">

  <tr>
    <td> </td>
  </tr>
  <tr><td  align="left"><div id="errorMessage"><html:errors/></div></td></tr>
  
  <tr>
    <td>     <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
               
                   
                  
               
              <tr bgcolor="#FFFFFF">
                   <td height="10" colspan="6" align="center"><h3><font color="red">Enter Your Qualifying Examination Marks</font></h3></td>
             </tr>
                     
              <tr> <td height="5" colspan="6" align="center"><h3><font color="blue">Choose the pattern which you studied for UnderGraduate program</font></h3></td>
             </tr>
              <tr>
                        <td width="33%" class="heading" colspan="2" id="CBCSS" align="right"><html:radio disabled="true" styleId="CBCSSRadio" property="patternofStudy" value="CBCSS">CBCSS pattern</html:radio></td>
                        <td width="33%" class="heading" colspan="2" id="CBCSSNEW" align="center"><html:radio disabled="true" styleId="CBCSSNEWRadio" property="patternofStudy" value="CBCSS NEW">CBCSS NEW pattern</html:radio></td>
                         <td width="33%" class="heading" id="OTHER" colspan="2" ><html:radio disabled="true" styleId="OTHERRadio" property="patternofStudy" value="OTHER">Pre CBCSS pattern</html:radio></td>
         	</tr>
              <tr><td colspan="6"><br></br></td></tr>
              
              <tr class="CBCSS">
                   <td height="35" colspan="6" class="body" >
                   
                   <table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22">
                      
                       
                       </td>
                     </tr>
                     
                     <tr >
						<td class="row-odd" width="20%"><bean:message key="admissionForm.detailmark.slno.label"/></td>
						
						<td class="row-odd" width="25%" ><div align="center"><bean:message key="knowledgepro.admin.subject.subject.name.disp"/> </div></td>
 						<td height="25" class="row-odd" width="22%"><div align="center">Obtained Grade</div></td>
						<td height="25" class="row-odd" width="22%"><div align="center">Max Grade</div></td>
 						<%-- <td height="25" class="row-odd" width="12%"><div align="center">GPA</div></td>
 						<td height="25" class="row-odd" width="12%"><div align="center">Max GPA</div></td> --%>
						<td height="25" class="row-odd" width="10%"><div align="center">Credit</div></td>
					</tr>
					
					
					<%
					int coreCount=1;
					int complementaryCount=1;
					int commonCount=1;
					int openCount=1;
					int i=0;
					System.out.println(CMSConstants.MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS);
					for(i=1;i<=CMSConstants.MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS;i++) {
						//String propertyName="detailMark.subject"+i;
						String propertyName="detailMark.subjectid"+i;
						String dynaMandatory="detailMark.subject"+i+"Mandatory";
						String propertyDetailedName="detailMark.detailedSubjects"+i+".id";
						String propertyDetailedId="detailMark.detailedSubjects"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						//String dynajs="updatetotalMark("+CMSConstants.MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS+")";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						//String dynajs2="updateObtainMark("+CMSConstants.MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS+")";
						String method = "putSubjectName('"+i+"',this.value)";
						String propertyOtherName="detailMark.subjectOther"+i;
						String degmaxcgpa="detailMark.subject"+i+"Credit";
						String propertyOtherNameDisplay="propertyOtherNameDisplay"+i;
					%>
					
                     <tr>
                     
                     <%if(i<=3){%>
										
                         <td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Core <%=coreCount%>
                          
                         </td>
                        <%-- 
						 <td  class="row-even" >
						<logic:notEmpty property="<%=propertyName %>" name="onlineApplicationForm">
								<logic:equal value="true" property="<%=dynaMandatory %>" name="onlineApplicationForm">
							 	<span class="Mandatory">*</span><bean:write property="<%=propertyName %>" name="onlineApplicationForm" ></bean:write>
								</logic:equal>
								<logic:equal value="false" property="<%=dynaMandatory %>" name="onlineApplicationForm">
									<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</logic:equal>
							</logic:notEmpty>
							<logic:empty property="<%=propertyName %>" name="onlineApplicationForm">
							 	<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
							</logic:empty>
						</td>
						--%>
						
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>'  styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admCoreMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						
						
						
						</td>
						
											
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6"  styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6"  styleId='<%=totalMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=degmaxcgpa %>" size="6" maxlength="6"  styleId='<%=degmaxcgpa %>'></html:text></td>
						
						<%
						
						coreCount++;
                     
                     }
							else if(i<=5)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Complementary <%=complementaryCount%>
                        
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admComplMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						
						<br/>
						<nested:notEmpty  property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
					
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6"  styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6"  styleId='<%=totalMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=degmaxcgpa %>" size="6" maxlength="6"  styleId='<%=degmaxcgpa %>'></html:text></td>
						
						
						<%
						
						complementaryCount++;
						}
							else if(i==6)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Common <%=commonCount%> (English) 
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admCommonMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6"  styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6"  styleId='<%=totalMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=degmaxcgpa %>" size="6" maxlength="6"  styleId='<%=degmaxcgpa %>'></html:text></td>
						
						<%
						
						commonCount++;
						}
						else if(i==7)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Common <%=commonCount%> (Second Language)
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admSubMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6"  styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6"  styleId='<%=totalMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=degmaxcgpa %>" size="6" maxlength="6"  styleId='<%=degmaxcgpa %>'></html:text></td>
						
						<%
						
						commonCount++;
						}
						else if(i>=8)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Open Course <%=openCount%>
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admMainMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6"  styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6"  styleId='<%=totalMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=degmaxcgpa %>" size="6" maxlength="6"  styleId='<%=degmaxcgpa %>'></html:text></td>
						
						<%
						
						openCount++;
						}
						%>
						
						
					</tr>
					<%} %>
					
					<tr>
					   
					    <td class="row-odd"  align="center">CORE GROUP RESULT:</td>
						
						<td class="row-even" align="center">
						OBTAINED CGPA:
						<html:text readonly="true" property="detailMark.totalObtainedMarksCGPA" size="7" maxlength="7"  styleId="totalObtainedMarksCGPA" ></html:text>
						
						</td>
						<td class="row-even" align="center" colspan="3">
						MAX CGPA:
						<html:text readonly="true" property="detailMark.totalMarksCGPA" styleId="totalMarksCGPA" size="7" maxlength="7" ></html:text>
						
						</td>
						
						
						
					</tr>
					<%-- 
						<tr class="CBCSS">
					<td height="25" class="row-odd" width="22%" colspan="1"><div align="center">Core Group Result</div></td>
 						
					<td width="25%" height="25" class="row-even"  colspan="1">
                     <div align="center"><nested:text property="degtotalobtainedmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Obtained CGPA)</div></td>
						 	
					<td width="25%" height="25" class="row-even"  colspan="3" align="left">
                     <div align="center"><nested:text property="degtotalmaxmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Max CGPA)</div></td>
						 
					</tr >
					--%>
                   </table>
                   </td>
                 </tr>
                 
                 <tr class="CBCSS">
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMarkDegree("+CMSConstants.MAX_CANDIDATE_DEGREE_CBCSS_SUBJECTS+")";
			%>
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                          </div></td>
                    <td width="2%">
                     <html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitConfirmCancelButtonView()'></html:button>
                    </td>
                    <td width="53%"></td>
                  </tr>
                </table>
                  </td>
                 </tr>
                 
                 
                 
                 
                 <!-- start of pre cbcss marks  -->
                 
                 
                 
                   <tr class="OTHER">
                   <td height="35" colspan="6" class="body" >
                   
                   <table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22">
                      
                       
                       </td>
                     </tr>
                     
                    	
					 <tr >
                    
						
						
						<td class="row-odd" width="20%"><bean:message key="admissionForm.detailmark.slno.label"/></td>
						<td class="row-odd" width="30%"><div align="center">Subject Name </div></td>
 						<td height="25" class="row-odd" width="20%"><div align="center"><bean:message key="admissionForm.detailmark.obtain.label"/></div></td>
 						<td height="25" class="row-odd" width="20%"><div align="center"><bean:message key="knowledgepro.admission.maxMark"/></div></td>
 					
 					</tr>
 					
					<%
					int mainCount=1;
					int subCount=1;
					commonCount=1;
					openCount=1;
					
					for(i=11;i<=CMSConstants.MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS;i++) {
						//String propertyName="detailMark.subject"+i;
						String propertyName="detailMark.subjectid"+i;
						String dynaMandatory="detailMark.subject"+i+"Mandatory";
						String propertyDetailedName="detailMark.detailedSubjects"+i+".id";
						String propertyDetailedId="detailMark.detailedSubjects"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						String dynajs="updatetotalMarkPreCBCSS("+CMSConstants.MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS+")";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						String dynajs2="updateObtainMarkPreCBCSS("+CMSConstants.MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS+")";
						String method = "putSubjectName('"+i+"',this.value)";
						String propertyOtherName="detailMark.subjectOther"+i;
						String degmaxcgpa="detailMark.subject"+i+"Credit";
						String propertyOtherNameDisplay="propertyOtherNameDisplay"+i;
					%>
					
                     <tr>
                     
                     <%if(i<=13){%>
										
                         <td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Main <%=mainCount%>
                          
                         </td>
                        <%-- 
						 <td  class="row-even" >
						<logic:notEmpty property="<%=propertyName %>" name="onlineApplicationForm">
								<logic:equal value="true" property="<%=dynaMandatory %>" name="onlineApplicationForm">
							 	<span class="Mandatory">*</span><bean:write property="<%=propertyName %>" name="onlineApplicationForm" ></bean:write>
								</logic:equal>
								<logic:equal value="false" property="<%=dynaMandatory %>" name="onlineApplicationForm">
									<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</logic:equal>
							</logic:notEmpty>
							<logic:empty property="<%=propertyName %>" name="onlineApplicationForm">
							 	<html:text property="<%=propertyName %>" styleId='<%=propertyName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
							</logic:empty>
						</td>
						--%>
						
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' styleClass="combolarge" >
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admCoreMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						
						
						
						</td>
						
											
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
						<%
						
						mainCount++;
                     	}
							else if(i<=15)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Sub <%=subCount%>
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admComplMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
					
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
						
						<%
						subCount++;
						}
							else if(i==16)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Common <%=commonCount%> (English)
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admCommonMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
							<%
							
						commonCount++;
						}
						else if(i==17)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Common <%=commonCount%> (Second Language)
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admSubMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty  property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
						<%
						commonCount++;
						}
						else if(i>=18)
						{%>
						
						<td  class="row-even" > <%=i %>&nbsp;&nbsp;&nbsp;Open Course <%=openCount%>
                         
                         </td>
						<td  class="row-even" >
						<nested:select disabled="true" property="<%=propertyName%>"  name="onlineApplicationForm" styleId='<%=propertyName%>' onchange='<%="displayOtherForSubject("+i+")" %>' styleClass="combolarge">
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admMainMap" name="onlineApplicationForm" label="value" value="key"/>
						   					
						</nested:select>
						<br/>
						<nested:notEmpty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								 <div id="<%=propertyOtherNameDisplay%>" style="display: block;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								 </div>
						</nested:notEmpty>
						<nested:empty property="<%=propertyOtherName%>" name="onlineApplicationForm">
						   						
								<div id="<%=propertyOtherNameDisplay%>" style="display: none;">
								 <html:text readonly="true" property="<%=propertyOtherName %>" styleId='<%=propertyOtherName %>' name="onlineApplicationForm" size="10" maxlength="20"></html:text>
								</div>
						</nested:empty>
						</td>
						
						<td class="row-even" align="center"><html:text readonly="true" property="<%=obtainMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						<td class="row-even" align="center"><html:text readonly="true" property="<%=totalMarkprop %>" size="6" maxlength="6" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						
						<%
						openCount++;
						}
						%>
						
						
					</tr>
					<%} %>
					
					<tr>
					   
						<td class="row-odd" colspan="2" align="center">TOTAL MARKS:</td>
						
						<td class="row-even" align="center"><html:text property="detailMark.totalObtainedMarks" size="7" maxlength="7"  styleId="ObtainedMark" readonly="true"></html:text>
						</td>
						<td class="row-even" align="center" ><html:text property="detailMark.totalMarks" styleId="totalMark" size="7" maxlength="7" readonly="true"></html:text>
						</td>
						
						
					</tr>
					<%-- 
						<tr class="CBCSS">
					<td height="25" class="row-odd" width="22%" colspan="1"><div align="center">Core Group Result</div></td>
 						
					<td width="25%" height="25" class="row-even"  colspan="1">
                     <div align="center"><nested:text property="degtotalobtainedmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Obtained CGPA)</div></td>
						 	
					<td width="25%" height="25" class="row-even"  colspan="3" align="left">
                     <div align="center"><nested:text property="degtotalmaxmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Max CGPA)</div></td>
						 
					</tr >
					--%>
                   </table>
                  </td>
                 </tr>
                 
                 <tr class="OTHER">
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod1="resetDetailMarkDegree("+CMSConstants.MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS+")";
			%>
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                          </div></td>
                    <td width="2%">
                    <html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitConfirmCancelButtonView()'></html:button>
                  
                    </td>
                    <td width="53%">
                     </td>
                  </tr>
                </table>
                 </td>
                 </tr>
          
                 
                 
                 
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 
              </table>
              
            </td>
  </tr>
</table>
</body>

<script type="text/javascript">

	
	var other='<%= CMSConstants.MAX_CANDIDATE_DEGREE_PRECBCSS_SUBJECTS %>';
	
	
	//alert('cb======='+cbcss);
	//alert('ot======='+other);
	//alert(document.getElementById("CBCSSRadio").value+'ugcb======='+document.getElementById("CBCSSRadio").checked);
	//alert(document.getElementById("OTHERRadio").value+'ugoth======='+document.getElementById("OTHERRadio").checked);
	
	//if(document.getElementById("CBCSSRadio").checked==true){
	//for(var i=1;i<=cbcss;i++) {
					
	//var propertyName="detailMark.subjectid"+i;
	//var propertyOtherName="detailMark.subjectOther"+i;
	//var propertyOtherNameDisplay=""+propertyOtherNameDisplay+i;
	//if(document.getElementById("detailMark.subjectid"+i)!= null){
	//if((document.getElementById("detailMark.subjectid"+i).value=="374") || (document.getElementById("detailMark.subjectid"+i).value=="274") || (document.getElementById("detailMark.subjectid"+i).value=="284")){
	//	document.getElementById("propertyOtherNameDisplay"+i).style.display = "block";
	//}
	//}
	//}
	//}

	//if(document.getElementById("OTHERRadio").checked==true){
		for(var k=1;k<=other;k++) {
						
		var propertyName="detailMark.subjectid"+k;
		var propertyOtherName="detailMark.subjectOther"+k;
		var propertyOtherNameDisplay=""+propertyOtherNameDisplay+k;
		if(document.getElementById("detailMark.subjectid"+k)!= null){
		if((document.getElementById("detailMark.subjectid"+k).value=="0") || (document.getElementById("detailMark.subjectid"+k).value=="0") || (document.getElementById("detailMark.subjectid"+k).value=="0")){
			document.getElementById("propertyOtherNameDisplay"+k).style.display = "block";
		}
		}
		}
		//}
</script>


	

</html:html>
