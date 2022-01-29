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

<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
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
	
	var totalmark=document.getElementById("totalobtainedmark").value;
		totalmark=0;
		
	var i;
	for(i=0;i<9;i++){
		var subjectmark=document.getElementById("obtainedmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("totalobtainedmark").value=totalmark;
			//}
		}
	}
}

function updatetotalMarkForRank()
{
	 
	var totalmark=document.getElementById("totalmaxmark").value;
		totalmark=0;
		
	var i;
	for(i=0;i<9;i++){
		var subjectmark=document.getElementById("maxmark_"+i).value;
		if(subjectmark.length==0){
			subjectmark=0;
		}
		if(IsNumeric(totalmark))
		{
			totalmark=parseFloat(subjectmark)+parseFloat(totalmark);
			//if(totalmark==0){
				//document.getElementById("totalMark").value=0;
			//}else{
				document.getElementById("totalmaxmark").value=totalmark;
			//}
		}
	}
}
function reset1(){
	alert('hi');
	var i;
	for(i=0;i<9;i++){
		if (document.getElementById("obtainedmark_"+i).value!=null)
		{
			document.getElementById("obtainedmark_"+i).value="";
		}
		if (document.getElementById("maxmark_"+i).value!=null)
		{
			document.getElementById("maxmark_"+i).value="";
		}
		if (document.getElementById("subid_"+i).value!=null)
		{
			document.getElementById("subid_"+i).value="";
		}
}
	if (document.getElementById("totalobtainedmark").value!=null)
	{
		document.getElementById("totalobtainedmark").value="";
	}
	if (document.getElementById("totalmaxmark").value!=null)
	{
		document.getElementById("totalmaxmark").value="";
	}
	if (document.getElementById("admLangsubId").value!=null)
	{
		document.getElementById("admLangsubId").value="";
	}
	
}


function CheckDecimal(myData)   
{   
//var decimal=  /^[-+]?[0-9]+\.[0-9]+$/;  
var numbersOnly = /^\d+$/;
var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
var uppercaseOnly = /^[A-Z]+$/;
var lowercaseOnly = /^[a-z]+$/;
var stringOnly = /^[A-Za-z0-9]+$/;

if(decimalOnly.test(myData))   
{   
alert('Correct, try another...')  
return true;  
}  
else  
{   
alert('Wrong...!')  
return false;  
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
<html:form action="/applicationEdit">
<html:hidden property="method" value=""/>
 <html:hidden property="pageType" value="4"/>
<html:hidden property="formName" value="applicationEditForm"/>

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
                   <td height="35" colspan="6" class="body" ><table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22"><h3><font color="red">Please Enter your qualifying exam marks</font></h3>
                      
                       
                       </td>
                     </tr>
                     <tr>
						<td class="row-odd" width="10%"><bean:message key="admissionForm.detailmark.slno.label"/></td>
						
						<td class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admin.subject.subject.name.disp"/> </div></td>
 						<td height="25" class="row-odd" width="22%"><div align="center"><bean:message key="admissionForm.detailmark.obtain.label"/></div></td>
						<td height="25" class="row-odd" width="22%"><div align="center"><bean:message key="knowledgepro.admission.maxMark"/></div></td>
 						</tr>
 						
					
					
						
						
						
						
						<%for(int count=0;count<9 ;count++) {
						String subid="subid_"+count;
						String obtainedmark="obtainedmark_"+count; 
						String maxmark="maxmark_"+count;
						String primarykeyid="admsubmarkid_"+count;
						String admsubgrpname="admsubgrpname_"+count;
						int slno=count+1;
						
						%>
					
								<tr>
										
										<%if(count<=1){%>
										 
										 <td width="25%" height="25" class="row-even">
										
										 <%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="1" >Language</font></td>
										  <td width="25%" height="25" class="row-even">
										 <nested:select property="<%=subid%>" styleId='<%="subid_"+count%>' name="applicationEditForm">
                    						<html:option value="">--Select--</html:option>
                    							
						   							<html:optionsCollection property="admSubjectLangMap"  name="applicationEditForm" label="value" value="key"/>
						   						
						   					</nested:select></td>
						   					<nested:hidden property="<%=primarykeyid%>"></nested:hidden>
						   					<nested:hidden property="<%=admsubgrpname%>"></nested:hidden>
						   					
										<%}
										else if(count>1)
										{
										%>
										
										
										<td width="25%" height="25" class="row-even">
										
										  <%=slno%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="1">Optional Subject</font></td>
										<td width="25%" height="25" class="row-even">
								 <nested:select property="<%=subid%>" styleId='<%="subid_"+count%>' name="applicationEditForm">
                    						<html:option value="">--Select--</html:option>
                    							
						   							<html:optionsCollection property="admSubjectMap"  name="applicationEditForm" label="value" value="key"/>
						   						
						   					</nested:select>
							 </td>
							 <nested:hidden property="<%=primarykeyid%>" ></nested:hidden>
							 <nested:hidden property="<%=admsubgrpname%>"></nested:hidden>
									<% 
									
									}
										
										%>
										
										
                     <td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=obtainedmark%>" size="6" styleId='<%="obtainedmark_"+count%>' maxlength="6" name="applicationEditForm" onkeypress='validate(event)' onblur="updateobtainedMarkForRank();" ></nested:text></div></td>
						 
                      <td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="<%=maxmark%>" size="6" styleId='<%="maxmark_"+count%>' maxlength="6" name="applicationEditForm" onkeypress='validate(event)' onblur="updatetotalMarkForRank();" ></nested:text></div></td>
						 
                     
                        
						
					</tr>
					<%} %>
					
						
						
					<tr>
					<td height="25" class="row-odd" width="22%" colspan="2"><div align="center">TotalMark</div></td>
 						
					<td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="totalobtainedmark" styleId="totalobtainedmark" size="6" maxlength="6" name="applicationEditForm" readonly="true"></nested:text></div></td>
						 	
					<td width="25%" height="25" class="row-even">
                     <div align="center"><nested:text property="totalmaxmark" styleId="totalmaxmark" size="6" maxlength="6" name="applicationEditForm" readonly="true"></nested:text></div></td>
						 
					</tr>
                   </table></td>
                 </tr>
                 
                 <tr>
                   <td height="35" colspan="6" class="body" >
			
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitApplicationEditForm('submitDetailMarkConfirmfor12thForEdit')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><html:button property=""  styleClass="formbutton" value="Reset" onclick='reset1()'></html:button></td>
                   <td width="53%"><html:button property=""  styleClass="formbutton" value="Cancel" onclick='submitEditCancelButton()'></html:button></td>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>