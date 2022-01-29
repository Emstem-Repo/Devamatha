<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
 <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript">
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function syllabusEntry(batch,deptId){
	document.location.href = "syllabusEntryHodApproval.do?method=initSyllabusEntryHodApprovalFirstPage&year="+batch+"&departmentId="+deptId+"&type=subjects";
    }
function syllabusEntryForSecondLanguages(batch,deptId){
	document.location.href = "syllabusEntryHodApproval.do?method=initSyllabusEntryHodApprovalFirstPage&year="+batch+"&departmentId="+deptId+"&type=languages";
    }
</script>
<html:form action="/syllabusEntryHodApproval" method="post">
<html:hidden property="formName" value="syllabusEntryHodApprovalForm" />
<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Syllabus Entry HOD Approval&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Syllabus Entry HOD Approval</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <table width="100%" cellspacing="1" cellpadding="2" id="report1">
                         <logic:notEmpty name="syllabusEntryHodApprovalForm" property="batchYearList">
                          <c:forEach var="list" items="${syllabusEntryHodApprovalForm.batchYearList}" >
					     	<logic:equal value="open" name="syllabusEntryOpen" scope="session">
					     		<tr>
					     		<td>
					     		<img src="images/bullet_img.gif" width="13" height="9" />
					    		<blink><a href="#" onclick="syllabusEntry('<c:out value="${list}"/>','<bean:write name="DepartmentId"/>')"><font color="red"><b>Syllabus Entry HOD Approval for <c:out value="${list}"/> Batch</b></font></a></blink><br><br>
					    		</td>
					    		</tr>
					     	</logic:equal>
					     	<logic:equal value="openLanguage" name="syllabusEntryOpenForLanguages" scope="session">
					     		<tr>
					     			<td>
					     			<img src="images/bullet_img.gif" width="13" height="9" />
					    			<blink><a href="#" onclick="syllabusEntryForSecondLanguages('<c:out value="${list}"/>','<bean:write name="DepartmentId"/>')"><font color="red"><b>Syllabus Entry HOD Approval for Second languages <c:out value="${list}"/> Batch</b></font></a></blink><br><br>
					     			</td>
					     		</tr>
					     	</logic:equal>
					     	</c:forEach>
		                 </logic:notEmpty> 
		                  
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
                 <tr><td height="10"></td></tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                      		<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
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
       <td width="100%" background="images/TcenterD.gif"></td>
       <td><img src="images/Tright_02.gif" width="9" height="29"></td>
     </tr>
   </table>
   </td>
 </tr>
</table>
</html:form>