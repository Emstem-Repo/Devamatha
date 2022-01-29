<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/admission/admissionform.js"></script>

 <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>

<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var jq=$.noConflict();
function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
   }


function updateobtainedMarkForRank()
{
	//alert('hi');
	var totalmark=document.getElementById("degtotalobtainedmark").value;
		totalmark=0;
		
	var i;
	for(i=0;i<7;i++){
		var subjectmark=document.getElementById("degobtainedmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("degtotalobtainedmark").value=totalmark;
			//}
		}
	}
}

function updatetotalMarkForRank()
{
	// alert('hi');
	var totalmark=document.getElementById("degtotalmaxmark").value;
		totalmark=0;
		
	var i;
	for(i=0;i<7;i++){
		var subjectmark=document.getElementById("degmaxmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("degtotalmaxmark").value=totalmark;
			//}
		}
	}
}

function updateobtainedMarkForRank1()
{
	//alert('hi');
	var totalmark=document.getElementById("degtotalobtainedmarkother").value;
		totalmark=0;
		
	var i;
	for(i=7;i<14;i++){
		var subjectmark=document.getElementById("degobtainedmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("degtotalobtainedmarkother").value=totalmark;
			//}
		}
	}
}

function updatetotalMarkForRank1()
{
	// alert('hi');
	var totalmark=document.getElementById("degtotalmaxmarkother").value;
		totalmark=0;
		
	var i;
	for(i=7;i<14;i++){
		var subjectmark=document.getElementById("degmaxmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("degtotalmaxmarkother").value=totalmark;
			//}
		}
	}
}
function reset1(){
	//alert('hi');
	var i;
	for(i=0;i<15;i++){
		if (document.getElementById("degobtainedmark_"+i).value!=null)
		{
			document.getElementById("degobtainedmark_"+i).value="";
		}
		if (document.getElementById("degmaxmark_"+i).value!=null)
		{
			document.getElementById("degmaxmark_"+i).value="";
		}
		if (document.getElementById("degsub_"+i).value!=null)
		{
			document.getElementById("degsub_"+i).value="";
		}
		if (document.getElementById("degsubother_"+i).value!=null)
		{
			document.getElementById("degsubother_"+i).value="";
		}
		if (document.getElementById("degmaxcgpa_"+i).value!=null)
		{
			document.getElementById("degmaxcgpa_"+i).value="";
		}
		
}
	if (document.getElementById("degtotalobtainedmark").value!=null)
	{
		document.getElementById("degtotalobtainedmark").value="";
	}
	if (document.getElementById("degtotalmaxmark").value!=null)
	{
		document.getElementById("degtotalmaxmark").value="";
	}
	if (document.getElementById("degtotalobtainedmarkother").value!=null)
	{
		document.getElementById("degtotalobtainedmarkother").value="";
	}
	if (document.getElementById("degtotalmaxmarkother").value!=null)
	{
		document.getElementById("degtotalmaxmarkother").value="";
	}
	
}

function displayOtherForSubject(count){
	//alert('======='+count);
	if((document.getElementById("degsub_"+count).value=="374") || (document.getElementById("degsub_"+count).value=="274") || (document.getElementById("degsub_"+count).value=="284")){
		document.getElementById("degother_"+count).style.display = "block";
	}else{
		document.getElementById("degother_"+count).style.display = "none";
		document.getElementById("degsubother_"+count).value = "";
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

	jq("#OTHERRadio").click(function(){
		     jq(".CBCSS").hide();
			 jq(".OTHER").show(2000);
			
		  });

	

	  
	});




</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<html:form action="/admissionFormSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="4"/>
<html:hidden property="formName" value="admissionFormForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.edit.detailmarkedit.label"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.detailmark.main.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" ><table width="70%" cellspacing="1" cellpadding="2" align="center">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22"><h3><font color="red">Enter Your Qualifying Examination Marks</font></h3></td></tr>
                     
                      <tr> <td height="5" colspan="20" align="center"><h3><font color="blue">Choose the pattern which you studied for UnderGraduate program</font></h3></td></tr>
                      <tr>
                        <td width="50%" class="heading" colspan="1" id="CBCSS" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio styleId="CBCSSRadio" property="patternofStudy" value="CBCSS">CBCSS pattern</html:radio></td>
                         <td width="50%" class="heading" id="OTHER" colspan="1" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio styleId="OTHERRadio" property="patternofStudy" value="OTHER">Pre CBCSS pattern</html:radio></td>
         
                     </tr>
                     <tr><td><p></p></td></tr>
                     <tr><td><p></p></td></tr>
                     <tr class="CBCSS">
                    
						<td class="row-odd" width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="admissionForm.detailmark.slno.label"/></td>
						<td class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admin.subject.subject.name.disp"/> </div></td>
 						<td height="25" class="row-odd" width="12%"><div align="center">GPA</div></td>
 						<td height="25" class="row-odd" width="12%"><div align="center">Max GPA</div></td>
						<td height="25" class="row-odd" width="12%"><div align="center">Credit</div></td>
 					</tr>
 						
					 <tr class="OTHER">
                    
						
						<td class="row-odd" width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="admissionForm.detailmark.slno.label"/></td>
						<td class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admin.subject.subject.name.disp"/> </div></td>
 						<td height="25" class="row-odd" width="12%"><div align="center"><bean:message key="admissionForm.detailmark.obtain.label"/></div></td>
 						<td height="25" class="row-odd" width="12%"><div align="center"><bean:message key="knowledgepro.admission.maxMark"/></div></td>
 					</tr>
					
						
						
						
						
						<%
						int i=1;
						for(int count=0;count<5 ;count++) {
						String degsub="degsubid_"+count;
						String degobtainedmark="degobtainedmark_"+count; 
						String degmaxcgpa="degmaxcgpa_"+count;
						String degmaxMark="degmaxmark_"+count;
						int slno=count+1;
						String degsubother="degsubidother_"+count;
						
						%>
						<tr class="CBCSS">
										
										<%if(count<=2){%>
										 
										 <td width="25%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Core <%=slno%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+count+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admCoreMap" name="admissionFormForm">
						   							<html:optionsCollection property="admCoreMap" styleClass="comboLarge" label="value" value="key"/>
						   						
						   						</logic:notEmpty>
						   					</nested:select>
						   					
						   					<br/>
						   					<logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:empty>
						   					
						   					</td>
										<%}
										else if(count>2)
										{
											
										%>
										
										
										<td width="25%" height="25" class="row-even">
										
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2">Complementary <%=i%></font></td>
										
										<td width="25%" height="25" class="row-even">
								 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+count+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admComplMap" name="admissionFormForm">
						   							<html:optionsCollection property="admComplMap" styleClass="comboLarge" label="value" value="key"/>
						   						
						   						</logic:notEmpty>
						   					</nested:select>
						   					
						   					<br/>
												 <logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:empty>
						   					
						   					
							 </td>
									<% 
								i++;
									}
										
										%>
										   <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						 
						  <td width="15%" height="25" class="row-even">
						   <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank()" ></nested:text></div></td>
						 
                     
                      <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degmaxcgpa%>" size="6" styleId='<%="degmaxcgpa_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						
                     
                        
						
					</tr>
					
					
					
					
					
						<% 
									
									}
										
										%>
											
										
										
						<%
						int j=1;
						String description="(English)";
						for(int count=5;count<7 ;count++) {
						String degsub="degsubid_"+count;
						String degobtainedmark="degobtainedmark_"+count; 
						String degmaxcgpa="degmaxcgpa_"+count;
						String degmaxMark="degmaxmark_"+count;
						int slno=count+1;
						String degsubother="degsubidother_"+count;
						
						%>
						
						
						
						
						
						<tr class="CBCSS">
				
										<%if(count==5){%>
										 
										 <td width="35%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Common <%=j%><%=description%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm"   disabled="false">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admCommonMap" name="admissionFormForm">
						   							<html:optionsCollection property="admCommonMap" styleClass="comboLarge" label="value" value="key" />
						   						
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
												
						   					
						   					</td>
						   					<td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						  <td width="15%" height="25" class="row-even">
                      <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank()" ></nested:text></div></td>
						 
                     
                      <td width="15%" height="25" class="row-even">
                      <div align="center"><nested:text property="<%=degmaxcgpa%>" size="6" styleId='<%="degmaxcgpa_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						 
                    
                        
										<%
										
										j++;
										}
										
										
						
										%>
							 
						</tr>
						
						<tr class="CBCSS">
				
										<%if(count==6){
										
											description="(Second Language)";
										%>
										 
										 <td width="35%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Common <%=j%><%=description%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+count+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admSubMap" name="admissionFormForm">
						   							<html:optionsCollection property="admSubMap" styleClass="comboLarge" label="value" value="key"/>
						   						
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
												<logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:notEmpty>
						   					<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:empty>
						   					
						   					</td>
						   					<td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						  <td width="15%" height="25" class="row-even">
                      <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank()" ></nested:text></div></td>
						 
                     
                      <td width="15%" height="25" class="row-even">
                      <div align="center"><nested:text property="<%=degmaxcgpa%>" size="6" styleId='<%="degmaxcgpa_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						 
                    
                        
										<%}
										
										
						}
										%>
							 
						</tr>
				
						
						<tr class="CBCSS">
					 <td width="25%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Open Course </font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="degsubid_14" styleId='<%="degsub_14"%>' name="admissionFormForm" onchange='displayOtherForSubject(14)'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admMainMap" name="admissionFormForm">
						   							<html:optionsCollection property="admMainMap" styleClass="comboLarge" label="value" value="key"/>
						   						
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
						   					<logic:notEmpty property="degsubidother_14" name="admissionFormForm">

												 <div id="degother_14" style="display: block;">
														 &nbsp;<nested:text property="degsubidother_14" size="20" maxlength="50" styleId="degsubother_14"/>
												 </div>
						   					</logic:notEmpty>
						   					<logic:empty property="degsubidother_14" name="admissionFormForm">

												 <div id="degother_14" style="display: none;">
														 &nbsp;<nested:text property="degsubidother_14" size="20" maxlength="50" styleId="degsubother_14"/>
												 </div>
						   					</logic:empty>
						   					
						   					</td>
						   					<td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="degobtainedmark_14" size="6"  maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank1()" ></nested:text></div></td>
						  <td width="15%" height="25" class="row-even">
						    <div align="center"><nested:text property="degmaxmark_14" size="6"  maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank1()" ></nested:text></div></td>
						 
                     
                      <td width="15%" height="25" class="row-even">
                   
                     <div align="center"><nested:text property="degmaxcgpa_14" size="6" styleId="degmaxcgpa_14" maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank()" ></nested:text></div></td>
						 
                        </tr>
                        
                        
                        
                        
                        
						<tr class="CBCSS">
					<td height="25" class="row-odd" width="22%" colspan="1"><div align="center">Core Group Result</div></td>
 						
					<td width="25%" height="25" class="row-even"  colspan="1">
                     <div align="center"><nested:text property="degtotalobtainedmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Obtained CGPA)</div></td>
						 	
					<td width="25%" height="25" class="row-even"  colspan="3" align="left">
                     <div align="center"><nested:text property="degtotalmaxmark" onkeypress='validate(event)' size="6" maxlength="6"></nested:text>(Max CGPA)</div></td>
						 
					</tr >
						
						
										
									
						<%
						int slno=1;
						int slno1=1;
						for(int cnt=7;cnt<12 ;cnt++) {
						String degsub="degsubid_"+cnt;
						String degobtainedmark="degobtainedmark_"+cnt; 
						String degmaxMark="degmaxmark_"+cnt;
						String degsubother="degsubidother_"+cnt;
						%>
					
									
									
						<tr class="OTHER">
										
										<%if(cnt<=9){
										%>
										 
										 <td width="25%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Main <%=slno%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+cnt%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+cnt+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admCoreMap" name="admissionFormForm">
						   							<html:optionsCollection property="admCoreMap" styleClass="comboLarge" label="value" value="key"/>
						   							
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
												<logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+cnt%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+cnt%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+cnt%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+cnt%>'/>
												 </div>
											</logic:empty>
						   					
						   					</td>
										<%
										slno++;
										}
										
										else if(cnt>9)
										{
										%>
										
										
										<td width="25%" height="25" class="row-even">
										
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2">Sub <%=slno1%></font></td>
										<td width="25%" height="25" class="row-even">
								 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+cnt%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+cnt+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admComplMap" name="admissionFormForm">
						   							<html:optionsCollection property="admComplMap" styleClass="comboLarge" label="value" value="key"/>
						   							
						   						</logic:notEmpty>
						   					</nested:select>
						   					
						   					<br/>
												 <logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+cnt%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+cnt%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+cnt%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+cnt%>'/>
												 </div>
											</logic:empty>
						   					
							 </td>
									<% 
									slno1++;
									slno++;
									}
										
									%>
										
										
                     <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+cnt%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank1()" ></nested:text></div></td>
						 
                      <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+cnt%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank1()" ></nested:text></div></td>
						 
                     
                        
						
					</tr>
					
					
					
					
					
					
					
						<% 
									
									}
										
						%>
						<%
						String description1="(English)";
						int k=1;
						for(int count=12;count<14 ;count++) {
						String degsub="degsubid_"+count;
						String degobtainedmark="degobtainedmark_"+count; 
						String degmaxMark="degmaxmark_"+count;
						String degsubother="degsubidother_"+count;
						%>
					
					
					
					
					
					
					<tr class="OTHER">
				
					<%if(count==12){%>
										 
										 <td width="35%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Common <%=k%><%=description1%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+count+")"%>' disabled="false">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admCommonMap" name="admissionFormForm">
						   							<html:optionsCollection property="admCommonMap" styleClass="comboLarge" label="value" value="key"/>
						   							
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
												 <logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:empty>
						   					
						   					</td>
						   					<td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank1()" ></nested:text></div></td>
						 
                      <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank1()" ></nested:text></div></td>
						 
                     
                        
						<%
						k++;
						
						}
					slno++;			
					description1="(Second Language)";
						
						%>
					</tr>
					
					
					
					
					<tr class="OTHER">
				
					<%if(count==13){%>
										 
										 <td width="35%" height="25" class="row-even">
										
										 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" >Common <%=k%><%=description1%></font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=degsub%>" styleId='<%="degsub_"+count%>' name="admissionFormForm" onchange='<%="displayOtherForSubject("+count+")"%>'>
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="admSubMap" name="admissionFormForm">
						   							<html:optionsCollection property="admSubMap" styleClass="comboLarge" label="value" value="key"/>
						   							
						   						</logic:notEmpty>
						   					</nested:select>
						   					<br/>
												 <logic:notEmpty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: block;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:notEmpty>
											<logic:empty property="<%=degsubother%>" name="admissionFormForm">
						   						
												 <div id="<%="degother_"+count%>" style="display: none;">
														 &nbsp;<nested:text property="<%=degsubother%>" size="20" maxlength="50" styleId='<%="degsubother_"+count%>'/>
												 </div>
											</logic:empty>
						   					
						   					</td>
						   					<td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degobtainedmark%>" size="6" styleId='<%="degobtainedmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank1()" ></nested:text></div></td>
						 
                      <td width="15%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=degmaxMark%>" size="6" styleId='<%="degmaxmark_"+count%>' maxlength="6" name="admissionFormForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank1()" ></nested:text></div></td>
						 
                     
                        
						<%
						}
										
						}
						%>
					</tr>
					
					
					
								
				
					
					
					
					<tr class="OTHER">
					<td height="25" class="row-odd" width="22%" colspan="2"><div align="center">TotalMark</div></td>
 						
					<td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="degtotalobtainedmarkother" styleId="degtotalobtainedmarkother" size="6" maxlength="6" readonly="true"></nested:text></div></td>
						 	
					<td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="degtotalmaxmarkother" styleId="degtotalmaxmarkother" size="6" maxlength="6" readonly="true"></nested:text></div></td>
						 
					</tr>
					
					
					
                   </table>
                   </td>
                 </tr>
                 
                 
                 
                 <tr>
                   <td height="35" colspan="6" class="body" >
			
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitAdmissionForm('submitDetailMarkConfirmfordeg')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><html:button property=""  styleClass="formbutton" value="Reset" onclick='reset1()'></html:button></td>
                   <td width="53%"> <%--<html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitConfirmCancelButton()'></html:button>--%></td>
                  </tr>
                </table>
                </td>
                 </tr>
                 
                 
                 
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 
                 
              </table>
            </div>
            </td>
            
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
      
    </table>
    </td>
  </tr>
  
</table>

</html:form>

</body>
</html:html>