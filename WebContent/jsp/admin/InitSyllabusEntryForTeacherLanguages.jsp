<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
 <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#report1 .tdIMG").addClass("odd");
    $("#report1 .data").hide();
    $("#report1 tr:first-child").show();
    
    $("#report1 tr.odd").click(function(){
        $(this).next("tr").toggle();
        $(this).find(".arrow").toggleClass("up");
    });
});
$(document).ready(function() {
    // Tooltip only Text
    $('.dropt').tooltipster();
});
function syllabusEntry(subId){
	document.getElementById("method").value = "initSyllabusEntryForTeacherSecondPage";
	document.getElementById("subjectId").value = subId;
	document.syllabusEntryForm.submit();
}
function cancel(){
	document.location.href = "syllabusEntry.do?method=checkIsSyllabusEntryOpen";
}
function preview(subId,status){
	if(status=='Pending'){
		 $.confirm({
				'message'	: "Syllabus Entry status is pending",
				'buttons'	: {
	     		'Ok'	:  {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		}else{
			var year=document.getElementById("batchYear").value;
			url = "syllabusEntry.do?method=preview&subjectId="+subId+"&batchYear="+year;
			myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
}
</script>
<html:form action="/syllabusEntry" method="post">
<html:hidden property="formName" value="syllabusEntryForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" value="getsubjectAndStatus" styleId="method"/>
<html:hidden property="subjectId" value="" styleId="subjectId"/>
<html:hidden property="batchYear" styleId="batchYear"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Syllabus Entry&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Syllabus Entry</strong></div></td>
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
                       <table width="100%" cellspacing="1" cellpadding="2" >
                       <logic:notEmpty name="syllabusEntryForm" property="subjectMapBySem">
                       				<tr>
		                  				<td width="5%"  height="5%" class="row-odd" ><div align="center"><bean:message key="admissionForm.detailmark.semester.label"/></div></td>
			               				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.code"/></td>
			               				<td width="56%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.template.candidate.name"/></td>
			               				<td width="19%" height="30%" class="row-odd" align="center" ><bean:message key="employee.info.job.achievement.status"/></td>
			               				<td width="9%" height="30%" class="row-odd" align="center" >Preview</td>
		                  			</tr>
                       <nested:iterate id="to" name="syllabusEntryForm" property="subjectMapBySem">
	                  				<nested:iterate id="to1" name="to" property="value" indexId="count">
					                   	<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
										</c:choose>
										<tr>
		                  					<td  height="25" class="row-even" ><div align="center"><bean:write name="to1" property="semester"/></div></td>
		                  					<td  height="25" class="row-even" align="left"><bean:write name="to1" property="subjectCode"/></td>
		                  					<logic:notEmpty name="to1" property="display">
		                  						<td  height="25" class="row-even" align="left"><a href="#" onclick="syllabusEntry('<bean:write name="to1" property="subjId"/>')"><span class="dropt" title="Click here for syllabus entry."><bean:write name="to1" property="subjectName"/></span></a></td>
		                  					</logic:notEmpty>
		                  					<logic:empty name="to1" property="display">
		                  						<td  height="25" class="row-even" align="left"><bean:write name="to1" property="subjectName"/></td>
		                  					</logic:empty>
		                  					<td  height="25" align="center" class="row-even">
		                  						<nested:equal value="Pending" property="status">
		                  							<b><bean:write name="to1" property="status"/></b>
		                  						</nested:equal>
		                  						<nested:equal value="In-progress" property="status">
		                  							<font color="#E036DA "><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  						<nested:equal value="Rejected" property="status">
		                  							<font color="#1E9C83"><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  						<nested:equal value="HOD Rejected" property="status">
		                  							<font color="#F20D42"><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  						<nested:equal value="Approved By HOD" property="status">
		                  							<font color="#05A959"><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  						<nested:equal value="Approved" property="status">
		                  							<font color="0DF24E"><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  						<nested:equal value="Completed" property="status">
		                  							<font color="#200DF2"><b><bean:write name="to1" property="status"/></b></font>
		                  						</nested:equal>
		                  					</td>
		                  					<td  height="25" align="center" class="row-even"> <img src="images/View_icon.gif"
						 					height="18" style="cursor:pointer" onclick="preview('<bean:write name="to1" property="subjId"/>','<bean:write name="to1" property="status"/>')"></td>
		                  				</tr>
                  					</nested:iterate>
		                  
	                	</nested:iterate>
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