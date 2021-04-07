<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


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
    <script language='JavaScript'>
	checked = false;
    function checkAll () {
		if (checked == false) {
			checked = true;
		} else {
			checked = false;
		}
		
		for (var i=0;i<document.forms[0].elements.length;i++)
		{	
			var e=document.forms[0].elements[i];
			if ((e.type=='checkbox'))
			{
				e.checked=checked;
			}
		}
		
    }

    function printICard(){
        document.interviewProcessForm.method.value="printInterviewProcess";
    	document.interviewProcessForm.selectedType.value=1;
    	document.interviewProcessForm.submit();
        }
    
    function printNotice(){
    	document.interviewProcessForm.method.value="printInterviewNotice";
    	document.interviewProcessForm.selectedType.value=2;   
    	document.interviewProcessForm.submit();
        }
    function cancelMe() {
    	document.location.href = "interviewprocess.do?method=initPrintInterviewProcess";
    }
    function unCheckSelectAll(field) {
    	 
        if(field.checked == false) {
         document.getElementById("selectall").checked = false;
        }
     
    }
    </script>


</head>

<body>
<html:form action="interviewprocess" focus="selectall" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="selectedType" styleId="selectedType" value=""/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.interview.PrintInterviewCard" />
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.interview.PrintInterviewCardSearch"/></strong></div></td>
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
                  <table width="100%" cellspacing="1" cellpadding="2">
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
                      <td class="row-odd"><div align="center">
                        <input type="checkbox" name = "selectall" id="selectall" onclick= "checkAll()" />
                      </div></td>
                      <td style="padding-left: 35px; text-align: center;"  height="25" class="row-odd" ><bean:message key="knowledgepro.interview.ApplicationNo"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.interview.Name"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.interview.Coursenocol"/></td>
					  <td style="padding-left: 55px; text-align: center;" class="row-odd" ><bean:message key="knowledgepro.interview.InterviewTypenocol"/></td>
                    </tr>
            <logic:notEmpty name="interviewProcessForm" property="applicationList">
            <nested:iterate  name="interviewProcessForm" id="interviewCardTO" property="applicationList" indexId="count">
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
              <td class='<%= dynamicStyle %>'><div align="center">
                <input type="checkbox" id='<%=rowid %>'
							name="selectedCandidates" onclick="unCheckSelectAll(this)"
							value='<nested:write name="interviewCardTO" property="admApplnTO.applnNo"/>_<nested:write name="interviewCardTO" property="interviewType"/>'>
				<input type="hidden" id='<%=rowid %>' name="typeInterview" value="'<nested:write name="interviewCardTO" property="interviewType"/>'" >
              </div></td>
              <td style="padding-left: 35px; text-align: center;" height="25" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.applnNo"/></td>
              <td style="padding-left: 35px; text-align: center;" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.personalData.firstName"/>&nbsp;
				<bean:write name="interviewCardTO" property="admApplnTO.personalData.middleName"/>&nbsp;
				<bean:write name="interviewCardTO" property="admApplnTO.personalData.lastName"/>&nbsp;
				</td>
              <td style="padding-left: 35px; text-align: center;" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="admApplnTO.course.name"/></td>
			  <td style="padding-left: 35px; text-align: center;" class='<%= dynamicStyle %>' ><bean:write name="interviewCardTO" property="interviewType"/></td>
            </tr>
 
 			</nested:iterate> 
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
      <tr>
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
           
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="40%" height="35"><div align="right">
                      
                  </div></td>
                  <td width="2%" style="padding-right: 5px;"><html:button property="" value="Print" onclick="printICard()"  styleClass="formbutton"></html:button></td>
                  <td width="8%" style="padding-right: 5px;"><html:button property="" value="Print for Notice Board" onclick="printNotice()"  styleClass="formbutton"></html:button></td>
                  <td width="44"><html:button property="cancel" onclick="cancelMe()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button></td>
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
<script type="text/javascript">
var print = "<c:out value='${interviewProcessForm.printChallan}'/>";
if(print.length != 0 && print == "true") {
	var url ="interviewprocess.do?method=dispalyChallan";
	myRef = window.open(url,"interviewprocess","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>
</body>
</html:html>
