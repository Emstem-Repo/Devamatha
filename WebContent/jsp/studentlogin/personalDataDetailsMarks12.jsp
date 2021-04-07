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
<%@page import="com.kp.cms.constants.CMSConstants"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<!-- <LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css"> -->
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

<script type="text/javascript">

var hook = true;
var jq=$.noConflict();

function resetDetailMarkClass12(count){
	 document.getElementById("totalMark").value="";
	 document.getElementById("ObtainedMark").value="";
	 if( document.getElementById("totallangMark")!=null)
	 document.getElementById("totallangMark").value="";
	 if( document.getElementById("ObtaintedlangMark")!=null)
	 document.getElementById("ObtaintedlangMark").value="";
	var i;
	for(i=1;i<=count;i++){
		if(i!=1){
		if(document.getElementById("detailMark.subjectid"+i)!=null)
		document.getElementById("detailMark.subjectid"+i).value="";
		}
		if(document.getElementById("detailMark.subject"+i+"TotalMarks")!=null)
		document.getElementById("detailMark.subject"+i+"TotalMarks").value="";
		if(document.getElementById("detailMark.subject"+i+"ObtainedMarks")!=null)
		document.getElementById("detailMark.subject"+i+"ObtainedMarks").value="";
		
	}
	 resetErrMsgs();
}

function updatetotalMarkClass12(count)
{
	var totalmark=document.getElementById("totalMark").value;
		totalmark=0;
	var i;
	for(i=1;i<=count;i++){
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
function updateObtainMarkClass12(count)
{
	var obtainmark=document.getElementById("ObtainedMark").value;
	obtainmark=0;

		var i;
		for(i=1;i<=count;i++){
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
function dupsub(val)
{
		var flag=false;
		var i;
		var j;
		var count=document.getElementById("subjectCount").value;
		var subject2=document.getElementById("detailMark.subjectid"+val).value;
		for(i=1;i<=count;i++)
		{
			if(i!=val)
			{
				if(document.getElementById("detailMark.subjectid"+i).value!="" &&document.getElementById("detailMark.subjectid"+val).value!="")
				{
					var subject1=document.getElementById("detailMark.subjectid"+i).value;
					if(subject1==subject2)
					{
						flag=true;
						document.getElementById("detailMark.subjectid"+val).value="";
					}
				}
			}	
		}
		if(flag)
		{
			jq.confirm({
				
				'message'	: 'WARNING: You entered same subject',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							jq.confirm.hide();
						}
					}
				}
			});
			
		}
		
}


function checkObtainedMarks(count)
{
	
		var i;
		for(i=1;i<=count;i++){
			var subjecobtaintmark=document.getElementById("detailMark.subject"+i+"ObtainedMarks").value;
			var subjecttotalmark=document.getElementById("detailMark.subject"+i+"TotalMarks").value;
			
			if(subjecobtaintmark.length!=0 && subjecttotalmark.length!=0){
				
			if(IsNumeric(subjecobtaintmark) && IsNumeric(subjecttotalmark))
			{
				if(parseFloat(subjecobtaintmark)>parseFloat(subjecttotalmark)){
					document.getElementById("detailMark.subject"+i+"TotalMarks").value="";
					jq.confirm({
						
						'message'	: 'WARNING:  Entered marks are greater than Maximum marks ',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									jq.confirm.hide();
									
								}
							}
				        
						}
					});
									
				}
				
			}
			
			}
		}
}


function submitAdmissionFormCalc(val){

    var outOf=false;
	var count=document.getElementById("subjectCount").value;
	var i;
	
	for(i=1;i<=count;i++){
		
		if(document.getElementById("detailMark.subjectid"+i)!=null && document.getElementById("detailMark.subject"+i+"TotalMarks")!=null && document.getElementById("detailMark.subject"+i+"ObtainedMarks")!=null){
			if(document.getElementById("detailMark.subjectid"+i).value!="" && document.getElementById("detailMark.subject"+i+"TotalMarks").value!="" && document.getElementById("detailMark.subject"+i+"ObtainedMarks").value!=""){

			var subject=document.getElementById("detailMark.subjectid"+i).value;

			var subjecttotal=parseFloat(document.getElementById("detailMark.subject"+i+"TotalMarks").value);
			var subjectobtained=parseFloat(document.getElementById("detailMark.subject"+i+"ObtainedMarks").value);

			if(subjecttotal==subjectobtained){
				outOf=true;
			}

			}
		}
		
	}

	if(outOf){
		
	jq.confirm({
		
		'message'	: 'WARNING: You have same marks in obtained and max marks,If you want to re check once please click on cancel.',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					jq.confirm.hide();
					
					 document.getElementById("method").value=val;
					 document.loginform.submit();
				}
			},
        'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					jq.confirm.hide();
				}
			}
		}
	});

	}else{

		document.getElementById("method").value=val;
		 document.loginform.submit();
	}


}

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

function submitConfirmCancelButton(){
	document.location.href=href="StudentLoginAction.do?method=personalDataEdit";
}
</script>

<style type="text/css">
.daTable th{
background-color: #50A0E3;
color: white;
padding: 10px 15px;
}
.daTable td{

}
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	 font-size: initial;
  color: initial;
  background: initial;
  text-decoration: initial;
  margin: initial;
  padding: initial;
}
input[type=text] {
  margin: 8px 0;
  box-sizing: border-box;
  background-color:white;
  color: #0079e2;
  border: 1px solid #0079e2
}
select {
 color: white;
 border: white;
 background-color: #69A1EB;
 height: 35px;
}
.row-odd, .row-even{
font-size: medium;
color: white;
 border: white;
 background-color: #ECF0F4;
color: #0079e2;
padding: 2px 3px;
border-right: 1px solid #50A0E3;
border-bottom:  1px solid #50A0E3;
}

</style>



</head>

<body>
<html:form action="/StudentLoginAction" styleId="myForm">
<html:hidden property="formName" value="loginform" />
<input type="hidden" id="subjectCount" value="<%=CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS %>" />
<html:hidden property="method" styleId="method" value="" />
<table width="100%"  style="background-color: white" align="center">

  <tr>
    <td> </td>
  </tr>
  <tr>
  	<td align="left">
  	<div id="errorMessage">
  		<html:errors/>  		
  	</div></td>  	
  </tr>
  <tr>
  	<td align="center"><br><br><br>
  		<span class="Mandatory" style="color: red">CBSE, International and ICSE students can leave the column for Second Language empty</span>
  	</td>
  </tr>
  <tr>
    <td>     <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" >
                   
                   <table class="daTable" width="80%" align="center" cellspacing="1" cellpadding="2">
                    
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22">
                      
                       
                       </td>
                     </tr>
                     
                     <tr>
						<th width="15%" style="border-top-left-radius: 25%; "><bean:message key="admissionForm.detailmark.slno.label"/></th>
						
						<th  width="20%" ><div align="center">Subject Name</div></th>
 						<th height="25"  width="15%"><div align="center"><bean:message key="admissionForm.detailmark.obtain.label"/></div></th>
						<th height="25"  width="15%" style="border-top-right-radius: 25%;"><div align="center"><bean:message key="knowledgepro.admission.maxMark"/></div></th>
 						
					</tr>
					<%
					int optionalCount=1;
					for(int i=1;i<=CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS;i++) {
						//String propertyName="detailMark.subject"+i;
						String propertyName="detailMark.subjectid"+i;
						String dynaMandatory="detailMark.subject"+i+"Mandatory";
						String propertyDetailedName="detailMark.detailedSubjects"+i+".id";
						String propertyDetailedId="detailMark.detailedSubjects"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						String dynajs="updatetotalMarkClass12("+CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS+");"+"checkObtainedMarks("+CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS+");";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						String dynajs2="updateObtainMarkClass12("+CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS+");"+"checkObtainedMarks("+CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS+");";
						String method = "putSubjectName('"+i+"',this.value)";
						String dupSub="dupsub('"+i+"')";
						System.out.println(obtainMarkprop);
				
					%>
                     <tr>
                     
                     <%if(i==1){%>
										
                         <td style="border-left:1px solid #50A0E3;" class="row-even" > &nbsp;&nbsp;<%=i %>&nbsp;&nbsp;&nbsp;English
                         
                         </td>
						
						<td  class="row-even" align="center" style="background-color: white;">
						<nested:select disabled="true" property="<%=propertyName%>" style="width: 350px;" name="loginform" styleId='<%=propertyName%>' styleClass="combolarge" onchange='<%=dupSub %>'>
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admSubjectLangMap" name="loginform" label="value" value="key"/>
						   					
						</nested:select>
						</td>
						<td class="row-even" align="center">
						<html:text name="loginform" property="<%=obtainMarkprop %>" size="6" maxlength="3" styleId='<%=obtainMarkprop %>' onblur='<%=dynajs2 %>'></html:text>
						</td>
						<td class="row-even" align="center">
						<html:text name="loginform" property="<%=totalMarkprop %>" size="6" maxlength="3" styleId='<%=totalMarkprop %>' onblur='<%=dynajs %>'></html:text>
						</td>
						
						<%}
							else if(i==2)
						{%>
						
						<td  style="border-left:1px solid #50A0E3;" class="row-even" > &nbsp;&nbsp;<%=i %>&nbsp;&nbsp;&nbsp;Second Language
                         
                         </td>
						<td  class="row-even" align="center" style="background-color: white">
						<nested:select property="<%=propertyName%>"  name="loginform" style="width: 350px;" styleId='<%=propertyName%>' styleClass="combolarge" onchange='<%=dupSub %>'>
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admSubjectLangMap" name="loginform" label="value" value="key"/>
						   					
						</nested:select>
						</td>
						
						<td   class="row-even" align="center" style="border-left:1px solid #50A0E3;">
						<html:text name="loginform" property="<%=obtainMarkprop %>" size="6" maxlength="3" styleId='<%=obtainMarkprop %>' onblur='<%=dynajs2 %>'></html:text>
						</td>
						<td class="row-even" align="center">
						<html:text name="loginform" property="<%=totalMarkprop %>" size="6" maxlength="3" styleId='<%=totalMarkprop %>' onblur='<%=dynajs %>'></html:text>
						</td>
						
					
						
						<%}
							else if(i>2)
						{%>
						
						<td style="border-left:1px solid #50A0E3;" class="row-even" > &nbsp;&nbsp;<%=i %>&nbsp;&nbsp;&nbsp;Optional Subject <%=optionalCount%>
                        
                         </td>
						<td  class="row-even" align="center" style="background-color: white">
						<nested:select property="<%=propertyName%>"  name="loginform" style="width: 350px;" styleId='<%=propertyName%>' styleClass="combolarge" onchange='<%=dupSub %>'>
                    			<html:option value="">--Select--</html:option>
                    			<html:optionsCollection property="admSubjectMap" name="loginform" label="value" value="key"/>
						   					
						</nested:select>
						</td>
						
						<td class="row-even" align="center">
						<html:text name="loginform" property="<%=obtainMarkprop %>" size="6" maxlength="3" styleId='<%=obtainMarkprop %>' onblur='<%=dynajs2 %>'></html:text>
						</td>
						<td class="row-even" align="center">
						<html:text name="loginform" property="<%=totalMarkprop %>" size="6" maxlength="3" styleId='<%=totalMarkprop %>' onblur='<%=dynajs%>'></html:text>
						</td>
					
						<%
						
						
						optionalCount++;
						
						}
						%>
						
					</tr>
					<%} %>
					
					<tr>
					   	<th width="30%" style="border-bottom-left-radius: 25%"></th>
						<th width="30%" ><div align="right">TOTAL MARKS / TOTAL GRADEPOINTS:</div></th>
						<th width="20%" align="center">
						<html:text name="loginform" property="detailMark.totalObtainedMarks" size="7" maxlength="7" readonly="true" styleId='ObtainedMark'></html:text>
						</th>
						<th width="20%"  align="center" style="border-bottom-right-radius: 25%">
						<html:text name="loginform" property="detailMark.totalMarks" size="6" maxlength="3" readonly="true" styleId='totalMark'></html:text>
						</th>
						
						
					</tr>
                   </table></td>
                 </tr>
                 
                 <tr>
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMarkClass12("+CMSConstants.MAX_CANDIDATE_CLASS12_SUBJECTS+")";
			%>
	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                 
                 
                  <tr>
                    <td width="45%" height="35" colspan="2"><div align="right">
                         <html:button property="" onclick="submitAdmissionFormCalc('submitDetailMarkConfirmClass12')" styleClass="btn btn-success" value="Submit"></html:button>
   					
                    </div></td>
                    <td width="53%">
                    &nbsp; <html:button property="" value="Clear" styleClass="btn btn-info" style="border: none;padding: 9px;" onclick='<%=resetmethod %>' /> 
                    &nbsp; 
                    <html:button property=""  styleClass="btn btn-danger" value="Cancel" style="border: none;padding: 9px;" onclick='submitConfirmCancelButton()'></html:button>
                                      
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
</html:form>
</body>
</html:html>
