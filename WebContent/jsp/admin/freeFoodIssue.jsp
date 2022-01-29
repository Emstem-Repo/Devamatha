<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/freeFoodJqueryConfirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>


<head>
<script type="text/javascript">

	function cancelAction() {
		document.location.href = "freeFoodIssue.do?method=initFreeFoodIssue";
	}
	function submitRegisterNumber(){
		
		var numSize=document.getElementById("registrNo").value;
		
		if (numSize.length ==7){
			 //setTimeout('document.freeFoodIssueForm.submit()', 1000);
			 document.freeFoodIssueForm.submit();
		}
		
	}
	
	function saveNotEligibleStudentData(){
		document.getElementById("method").value="saveStudentDetails";
		document.freeFoodIssueForm.submit();
	}

</script>
</head>
<html:form action="/freeFoodIssue" method="POST">
<html:hidden property="formName" value="freeFoodIssueForm" />
<html:hidden property="method" value="searchStudentDetails" styleId="method" />
<html:hidden property="pageType" value="1" />
<input type="hidden"  id="studentid" name="studentid" value="<bean:write name="freeFoodIssueForm" property="studentId"/>"/>
<input type="hidden"  id="isEligible" name="isEligible" value="<bean:write name="freeFoodIssueForm" property="isEligible"/>"/>
<input type="hidden"  id="flag1" name="flag1" value="<bean:write name="freeFoodIssueForm" property="flag1"/>"/>
<input type="hidden"  id="foodIssuedTimes" name="foodIssuedTimes" value="<bean:write name="freeFoodIssueForm" property="foodIssuedTimes"/>"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; Free Food Issue &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Free Food Issue </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <!--<tr><logic:equal value="true" name="freeFoodIssueForm" property="flag">
		<FONT  size="2" color="red"><bean:message key="knowledgepro.admin.student.free.food.issue.not.eligible"/></FONT>
		</logic:equal>
		</tr>
      
      --><tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="70%" height="80%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="3" cellpadding="2">
              <tr >
 				<td width="10%" height="25" class="row-odd" ><div align="right"><font size="2" ><bean:message key="knowledgepro.hostel.reservation.registerNo"/></font></div></td>
 				<td width="10%" height="25" class="row-even" ><html:text property="registerNo" styleId="registrNo" size="7" maxlength="7" styleClass="combo"
										onkeyup="submitRegisterNumber()" onchange="submitRegisterNumber()"/></td>
               </tr>
                <logic:notEmpty name="freeFoodIssueForm" property="toList">
               		<logic:iterate name="freeFoodIssueForm" property="toList"
									id="id" indexId="count">
					<tr>
						<td width="10%" height="25" class="row-odd" >
							<div align="right"><font size="2" ><bean:message key="knowledgepro.fee.studentname"/>:</font></div>
						</td>
						<td width="8%" height="20" class="row-even"><div >
						<font size="5" ><bean:write name="id" property="studentName" /></font></div>
						</td>
					</tr>
					<tr>
						<td width="10%" height="25" class="row-odd" >
							<div align="right"><font size="2" ><bean:message key="knowledgepro.exam.blockUnblock.class"/>:</font></div>
						</td>
						<td width="8%" height="20" class="row-even"><div>
						<font size="5" ><bean:write name="id" property="className" /></font></div>
						</td>
					</tr>
						
					<tr>
						<td width="10%" height="25" class="row-odd" >
							<div align="right"><font size="2" ><bean:message key="knowledgepro.inventory.status.col"/></font></div>
						</td>
						<td width="8%" height="20" class="row-even">
						<logic:equal value="true" name="id" property="isElijible">
						<font size="5" color="green"><bean:message key="knowledgepro.admin.student.eligible"/></font>
						</logic:equal>	
						<logic:equal value="false" name="id" property="isElijible">
						<font size="5" color="red"><bean:message key="knowledgepro.admin.student.notEligible"/></font>
						</logic:equal>	
						</td>
					</tr>
				</logic:iterate> 
               
               
               
               
               
				</logic:notEmpty>
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
      
      
      <!--<tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          
          <logic:notEmpty name="freeFoodIssueForm" property="toList">	
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				
				
                <logic:iterate name="freeFoodIssueForm" property="toList"
									id="id" indexId="count">
					<tr>
						
						<td width="8%" height="20" class="row-even"><div align="center">
						<bean:write name="id" property="studentName" /></div>
						</td>
						<td width="8%" height="20" class="row-even">
						<logic:equal value="true" name="id" property="isElijible">
						<font size="5" color="green"><bean:message key="knowledgepro.admin.student.eligible"/></font>
						</logic:equal>	
						<logic:equal value="false" name="id" property="isElijible">
						<font size="5" color="red"><bean:message key="knowledgepro.admin.student.notEligible"/></font>
						</logic:equal>	
						</td>
					</tr>
				</logic:iterate> 
                
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          </logic:notEmpty>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      -->
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
document.getElementById("registrNo").focus();

    hook=false;
	var studentid = document.getElementById("studentid").value;
	var isEligible = document.getElementById("isEligible").value;
	var flag1 = document.getElementById("flag1").value;
	var foodIssuedTimes=document.getElementById("foodIssuedTimes").value;
	if(isEligible=='true'){
	document.getElementById("method").value="saveStudentDetails";
	setInterval('saveData()', 2000);

	function saveData(){
	    document.forms["freeFoodIssueForm"].submit();
	}
	}else if(flag1=='true'){

		if(foodIssuedTimes > 0){
		   $.confirm({
				'message'	: 'This student is not eligible for free food as per the records.Issue is already done for '+ foodIssuedTimes +' times.Are you sure you want to continue and issue?. ',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.getElementById("method").value="saveStudentDetails";
							document.freeFoodIssueForm.submit();
						}
					},
   	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
							document.location.href = "freeFoodIssue.do?method=initFreeFoodIssue";
						}
					}
				}
			});
	}else{
		$.confirm({
			'message'	: 'This student is not eligible for free food as per the records .Are you sure you want to continue and issue?. ',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
						document.getElementById("method").value="saveStudentDetails";
						document.freeFoodIssueForm.submit();
					}
				},
	       'Cancel'	:  {
					'class'	: 'gray',
					'action': function(){
						$.confirm.hide();
						document.location.href = "freeFoodIssue.do?method=initFreeFoodIssue";
					}
				}
			}
		});
	}
}
	

	

</script>