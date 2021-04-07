<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.*"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ page import="com.kp.cms.to.admin.DownloadEmployeeResumeTO" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
    function submitValues(){
        var status = document.getElementById("status").value;
        var date = document.getElementById("statusDate").value;
        document.location.href = "DownLoadEmployeeResume.do?method=submitSelectedStatus&status="+status+"&statusDate="+date;
    	//document.getElementById("method").Value="submitSelectedStatus";
    	//document.downloadEmployeeResumeForm.submit();
	}
	function resetFields(){
		document.getElementById("applicationNo").value = "";
	}
	
	//function subm(){
		//document.getElementById("method").Value="getEmployeeData";
		//document.location.href = "DownLoadEmployeeResume.do?method=getEmployeeData";
	//}
</script>
<html:form action="/DownLoadEmployeeResume">
<html:hidden property="method" value="getEmployeeData"/>
<html:hidden property="formName" value="downloadEmployeeResumeForm"/>
<html:hidden property="pageType" value="1"/>
<table width="100%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.employee"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.statusChange"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.statusChange"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
         <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
		  </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="45%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.applicationno"/></div></td>
                <td width="55%" class="row-even"><span class="star">
                  <html:text property="applicationNo" styleClass="TextBox" styleId="applicationNo" size="16" maxlength="20"/>
                </span></td>
              </tr>
            </table></td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <% String appNo = session.getAttribute("appNo").toString(); %>
      <tr>
            <td width="45%" height="35"><div align="right">
            <html:submit  styleClass="formbutton" value="Search"></html:submit>
            </div></td>
            <td width="2%"></td>
            <td width="53%">
            <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()"></html:button>
      </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="70%" border="0" align="left" cellpadding="0" cellspacing="0">
         
          <logic:notEmpty scope="session" name="downloadResumeTo"> <tr>
          
            <td ><img src="images/01.gif"  height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif"  height="5" /></td>
          </tr>
          <% DownloadEmployeeResumeTO resumeTo= (DownloadEmployeeResumeTO)session.getAttribute("downloadResumeTo"); 
          %>
          
         
          <tr>
            <td   background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.interview.Name"/> </div></td>
                    <td width="25%" height="25" class="row-even"><bean:write name="downloadResumeTo" property="employeeName" scope="session"/></td>
                  </tr>
                  <tr>
                    <td height="25" class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.employee.post.applied"/></div></td>
                    <td width="25%" height="25" class="row-even" ><bean:write name="downloadResumeTo" property="postAppliedFor" scope="session"/></td>
                  </tr>
                  <tr>
                    <td class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.employee.Department"/> </div></td>
                     <td width="25%" height="25" class="row-even" ><bean:write name="downloadResumeTo" property="departmentName" scope="session"/></td>
                    </tr>
                  <tr>
                    <td class="row-odd" width="25%"><div align="center"><bean:message key="knowledgepro.employee.CurrentStatus"/></div></td>
                    <td width="25%" height="25" class="row-even" ><bean:write name="downloadResumeTo" property="currentStatus" scope="session"/></td>
                  </tr>
	              
                  
              </table>
            </td>
            <td  height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif"  height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr></logic:notEmpty>
        </table></td>
        <td  valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr></tr>
      <logic:notEmpty scope="session" name="downloadResumeTo">
      <tr>
      <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="70%" border="0" align="left" cellpadding="0" cellspacing="0">
         <tr>
            <td ><img src="images/01.gif"  height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif"  height="5" /></td>
          </tr>
      <tr>
       <td   background="images/left.gif"></td>
            <td valign="top"> <table width="100%" cellspacing="1" cellpadding="2">
           
            <tr>
	              <td  height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.statusChange"/>
	               </div>
	              </td>
	              <td  height="25" class="row-even" width="25%">
	              <div align="left">
	             
                      <html:select property="status"  styleId="status"  name="downloadEmployeeResumeForm" >
                	     <html:option value="">Select</html:option>
                	     <html:option value="Application Submitted Online">Application Submitted Online</html:option>
                	     <html:option value="Forwarded">Forwarded</html:option>
                	     <html:option value="Not Selected">Not Selected</html:option>
                	     <html:option value="Pending">Pending</html:option>
                	     <html:option value="Selected">Selected</html:option>
                	     <html:option value="Shortlisted">Shortlisted</html:option>
                	</html:select> 
                	</div>
                	</td>
                	 <td  height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.status.date"/>
	               </div>
	               
	              </td>
	               <td  height="25" class="row-even" >
                	<table width="100" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td>
                      			<html:text name="downloadEmployeeResumeForm" property="statusDate" styleId="statusDate" size="10" maxlength="16"/> 
                      		</td >
                      		<td width="2"><div align="left">
                      			<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'downloadEmployeeResumeForm',
										// input name
										'controlname': 'statusDate'
									});
								</script></div>
							</td>
                    	</tr>
                	</table>
                </td>
                  </tr>
                  <tr>
                     <td height="25"  align="center" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                     <tr><td><div align="center">
                        <html:button  styleClass="formbutton" value="Submit" property="" onclick="submitValues()"></html:button>
                    </div></td></tr></table></td>                 
                  </tr>
                </table></td>
                     
                  <td  height="30"  background="images/right.gif"></td>
      </tr>
      <tr>
            <td height="5"><img src="images/04.gif"  height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
          </table></td>
          <td  valign="top" background="images/Tright_3_3.gif" class="news"></td>
          </tr></logic:notEmpty>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif"  height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif"  height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>
<script type="text/javascript">
     document.getElementById("status").value = "";
     document.getElementById("statusDate").value = "";

 		date = new Date();
 		var month = date.getMonth()+1;
 		var day = date.getDate();
 		var year = date.getFullYear();
 		if(day<10){
 			day = "0"+day;
 		}
 		if(month<10){
 			month = "0"+month;
 		}

 		if (document.getElementById('statusDate').value == ''){
 		document.getElementById('statusDate').value = day + '/' + month + '/' + year;
 		}
</script>