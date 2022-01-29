<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
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
<SCRIPT type="text/javascript">
    function cancelAct() {
    	document.getElementById("method").value = "initAttnCetMarksReport";
		document.attnPucSubjectForm.submit();
    }
</SCRIPT>
</head>

<body>

<html:form action="attnSubjectsUpload" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="attnPucSubjectForm"/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.exam.exam"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendance.cetMarks.report" /><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendance.cetMarks.report" /></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
             
      <td valign="top">       
<div style="overflow: auto; width: 1100px; ">
	<c:set var="temp" value="0" />  
		<jsp:useBean id="form" class="com.kp.cms.forms.attendance.AttnPucSubjectForm" scope="session"></jsp:useBean>
		<% String subj1 = session.getAttribute("subject1").toString();
	       String subj2	= session.getAttribute("subject2").toString();
	       String subj3 = session.getAttribute("subject3").toString();
	       String subj4 = session.getAttribute("subject4").toString();
		%>
		<display:table export="true" uid="attnCetMarksUid"  id = "attnCetMarks" name="sessionScope.attnCetMarksToList" requestURI="" defaultorder="ascending" pagesize="10">
			<display:setProperty name="export.excel.filename" value="attnCetMarks.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="attnCetMarks.csv"/>
		
				<display:column style=" width: 200px;height: 40px" property="regNo" sortable="true" title="REGISTER NO" class="row-even" headerClass="row-odd" />
				<display:column style=" width: 300px;" property="classes" sortable="true" title="CLASS" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="testId" sortable="true" title="TEST ID" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="mrkSub1" sortable="true" title='<%=subj1 %>'  class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="mrkSub2" sortable="true" title='<%=subj2 %>' class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="mrkSub3" sortable="true" title='<%=subj3 %>' class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="mrkSub4" sortable="true" title='<%=subj4 %>' class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="userCode" sortable="true" title="User Code" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="pcbRank" sortable="true" title="Pcb Rank" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="pcmRank" sortable="true" title="Pcm Rank" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="aieee" sortable="true" title="Is Aiee" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd" media="html csv excel"/>
				
		</display:table>
		
		
	</div>	
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
      <tr>
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
          
              <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="25"><div align="center"> 
			<html:button property="" onclick="cancelAct()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
                </tr>
              </table>
            
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
