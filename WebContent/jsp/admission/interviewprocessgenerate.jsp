<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>



<html:html>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
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
</head>
<script type="text/javascript">
checked = false;
function checkedAll () {
  if (checked == false){checked = true}else{checked = false}
for (var i = 0; i < document.getElementById('myform').elements.length; i++) {
document.getElementById('myform').elements[i].checked = checked;
}
}

function showIntGenerate(){
	
	document.location.href="interviewprocess.do?method=submitInterviewProcessGenerate";
}
function updateValue(id)
{
	alert("in update value");
	if(document.getElementById(id).checked==true){
		document.getElementById(id).value=true;
	}
	if(document.getElementById(id).checked==false){
		document.getElementById(id).value=false;
	}
	
}
</script>
<body>
<html:form action="interviewprocess" styleId="myform">
<html:hidden property="method" styleId="method" value="submitInterviewProcessGenerate"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">Admission<span class="Bredcrumbs">&gt;&gt; Interview Process Workflow &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.interview.Workflow"/> </strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="74" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td height="20" colspan="6" class="body" align="left">
             <div id="errorMessage">
            <FONT color="red"><html:errors/></FONT>
             <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages>
            </FONT>
            </div>
            </td>
          </tr>
          <tr >
            <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.ProgramType"/></div></td>
            <td width="22%" height="25" class="row-even" ><bean:write name="interviewProcessForm" property="progamTypeName"/></td>
            <td width="22%" class="row-odd"><div align="right" ><bean:message key="knowledgepro.interview.Program"/></div></td>
            <td width="31%" class="row-even"><bean:write name="interviewProcessForm" property="progamName"/></td>
          </tr>
          <tr >
            <td class="row-odd"><div align="right"><bean:message key="knowledgepro.interview.Course"/></div></td>
            <td height="25" class="row-even"><bean:write name="interviewProcessForm" property="courseName"/></td>
            <td class="row-odd"><div align="right"><bean:message key="knowledgepro.interview.InterviewType"/></div></td>
            <td class="row-even"><bean:write name="interviewProcessForm" property="interviewTypeName"/></td>
          </tr>

        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="115" valign="top" background="/images/Tright_03_03.gif"></td>
        <td class="news">
          <table width="100%" cellspacing="1" cellpadding="2">
            <tr >
              <td height="25" class="row-odd" ><div align="center">
                  <input type='checkbox' name='selectcheckall'/>
              </div></td>
              <td height="25" class="row-odd" ><bean:message key="knowledgepro.interview.ApplicationNo"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.CandidateName"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.Date"/></td>
              <td class="row-odd" ><bean:message key="knowledgepro.interview.Time"/></td>
              <td class="row-odd"><div align="center"><bean:message key="knowledgepro.interview.Interviewer"/> </div></td>
            </tr>
            <logic:notEmpty name="interviewProcessForm" property="studentList">
            <logic:iterate  name="interviewProcessForm" id="interviewCardTO" property="studentList" indexId="count">
			<%
				String dynamicStyle="";				
				if(count%2!=0){
					dynamicStyle="row-white";

				}else{
					dynamicStyle="row-even";

				}
				String rowid="Student"+count;
			%>
           
            <tr >
              <td width="5%" height="25" class='<%= dynamicStyle %>'><div align="center">                  
                  <input type="checkbox" id='<%=rowid %>'  name='studentList[<%=count%>].admApplnTO.isInterviewSelected' value='<bean:write name="interviewCardTO" property="admApplnTO.isInterviewSelected"/>' checked='<bean:write name="interviewCardTO" property="admApplnTO.isInterviewSelected"/>' onclick="updateValue('<%=rowid %>')"/>
              </div></td>
              <td width="20%" height="25" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.applnNo"/></td>
              <td width="22%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.personalData.firstName"/></td>
              <td width="22%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interview.date"/></td>
              <td width="17%" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="time"/></td>
              <td width="14%" height="25" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interviewer"/></td>
            </tr>
 			</logic:iterate>   
 			</logic:notEmpty>         
          </table>
                
        </td>
        <td valign="top" background="/images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td><div align="center">
                      
                        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="47%" height="35"><div align="right">
                                <html:reset styleClass="formbutton" value="reset" />
                            </div></td>
                            <td width="2%"></td>
                            <td width="11%"><html:submit styleClass="formbutton" value="Generate" /></td>
                            <td width="40%">&nbsp;</td>
                          </tr>
                        </table>
                      




                      
                    
                      </div></td>
                </tr>
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>
</body>

</html:html>