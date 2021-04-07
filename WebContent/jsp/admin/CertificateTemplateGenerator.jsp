<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">

function openHtml() {
	var url = "CertificateDetails.do?method=helpMenuCertificate";
	win2 = window.open(url, "Help", "width=800,height=800,scrollbars=yes"); 
}

function addTemplate() {
	//document.location.href ="CertificateDetails.do?method=saveCertificateDetailsTemplate";
	document.getElementById("method").value = "saveCertificateDetailsTemplate";
	document.certificateDetailsForm.submit();
}
function cancelCertificateDetails(){
	document.location.href = "CertificateDetails.do?method=initCertificateDetails";
	}

	
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
	getGroupTemplateList();
}
function editTemplate(id) {
	document.getElementById("templateId").value = id;
	document.getElementById("method").value = "editGroupTemplate";
	document.certificateDetailsForm.submit();
}

function updateTemplate() {
	document.getElementById("method").value = "updateGroupTemplate";
	document.certificateDetailsForm.submit();
}


function viewTemplate(id) {
	var url = "CertificateDetails.do?method=viewTemplateDescription&templateId=" + id;
	myRef = window.open(url, "ViewTemplateDescription", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function deleteTemplate(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm == true) {
		document.location.href = "CertificateDetails.do?method=deleteGroupTemplate&templateId=" + id;
	}
}

function getGroupTemplateList(){
	var programId="";
	var templateName="";
	 programId=document.getElementById("program").value;
	 templateName=document.getElementById("templateNameId").value;
 getGroupTemplate(programId,templateName,updateGroupTemplateList);
}
function updateGroupTemplateList(req){
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("option");
	
	var htm="<table width='100%' cellspacing='1' cellpadding='2' border='1'>  <tr height='25px' class='row-odd'>";
	htm=htm+"<td>"+"Sl No"+"</td>"+"<td>"+"Template Name"+"</td>"+"<td>"+"View"+"</td>"+"<td>"+"Edit"+"</td><td>"+"Delete"+"</td></tr>";

		if(items != null ){
			 var slNo = 1;
			for ( var i = 0; i < items.length; i++) {
				 var id = items[i].getElementsByTagName("id")[0].firstChild.nodeValue;
			     var programTypeId = items[i].getElementsByTagName("programTypeId")[0].firstChild.nodeValue;
			     var programId = items[i].getElementsByTagName("programId")[0].firstChild.nodeValue;
			     var courseId = items[i].getElementsByTagName("courseId")[0].firstChild.nodeValue;
			     var programTypeName = items[i].getElementsByTagName("programType")[0].firstChild.nodeValue;
			     var programName = items[i].getElementsByTagName("program")[0].firstChild.nodeValue;
			     var courseName = items[i].getElementsByTagName("course")[0].firstChild.nodeValue;
			     var templateName = items[i].getElementsByTagName("templateName")[0].firstChild.nodeValue;
				 if(slNo%2==0){
				     htm = htm + "<tr class='row-even'> ";
				 }else{
					 htm = htm + "<tr class='row-white'> ";
				 }

			     htm=htm + "<td width='10%' height='25'>"+slNo+ "</td>"+"<td width='15%'>"+templateName+ "</td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/View_icon.gif' width='16' height='18' style='cursor: pointer' onclick='viewTemplate("+id+")'/></div></td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/edit_icon.gif' width='16' height='18' style='cursor: pointer' onclick='editTemplate("+id+")'/></div></td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/delete_icon.gif' width='16' height='16' style='cursor: pointer' onclick='deleteTemplate("+id+")'/></div></td>";
			     htm=htm+"</tr>";
			     slNo++;
			}
		}
		htm = htm + "</table>";
		document.getElementById("display_Details").innerHTML = htm;
}
</script>

<html:form action="/CertificateDetails" >
	<html:hidden property="formName" value="certificateDetailsForm" />
	<html:hidden property="templateId" styleId="templateId" />
	<html:hidden property="method" styleId="method" value="calculateAmount"/>
	<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Group Template&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Certificate Template</strong></div></td>
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
                 <tr class="row-odd">
                   <td height="49" colspan="6" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr class="row-odd">
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <table width="100%" cellspacing="1" cellpadding="2">
		<tr class="row-odd">
		<td width="50%" height="25" class="row-odd" colspan="3" align="right"><B>Help</B></td>
		<td width="50%" height="25" class="row-even" colspan="3" align="left">
		<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20" onclick="openHtml()" title="Help">
		</td>
		</tr>
		<tr class="row-odd">
                             <td width="50%" height="25" class="row-odd" colspan="6">
								<FCK:editor instanceName="EditorDefault"  toolbarSet="Default">
									<jsp:attribute name="value">
										<c:out value="${certificateDetailsForm.templateDescription}" escapeXml="false"></c:out>
									</jsp:attribute>
								</FCK:editor>
                             </td>
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
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top">
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                    
		                     <c:choose>
		                     	<c:when test="${operation == 'edit'}">
		                     	 	<html:button property="" styleClass="formbutton" value="Update" onclick="updateTemplate()"></html:button>&nbsp;&nbsp;&nbsp;
		                      		 <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelCertificateDetails()"></html:button>
		                      	</c:when>
		                      	<c:otherwise>
		                      		<html:button property="" styleClass="formbutton" value="Save" onclick="addTemplate()"></html:button>&nbsp;&nbsp;&nbsp;
		                      		 <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelCertificateDetails()"></html:button>
		                      	</c:otherwise>
		                     </c:choose>	
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="49" colspan="6" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr class="row-odd">
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <div id="display_Details">
                       <logic:notEmpty name="certificateDetailsForm" property="templateList">
                       <table width="100%" cellspacing="1" cellpadding="2">
                           <tr class="row-odd">
                             <td width="10%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.slno"/></div>
                             </td>
                             
                             <td width="15%" height="25" class="row-odd" align="center">
	                             <span class="star">
	                             	<bean:message key="knowledgepro.emailtemplate.templatename"/>
	                             </span>
	                         </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.view" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.edit" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.delete" />
                             	</div>
                             </td>
                           </tr>
                           <logic:iterate id="template" name="certificateDetailsForm" property="templateList" indexId="count">
							
						<tr class="row-even">
								
                           	<td height="25"><div align="center">
                             	<c:out value="${count + 1}"/> </div>
                             </td>
                             
                             <td height="25"><div align="center">
	                             <bean:write name="template" property="templateName"/>
	                           </div>
	                         </td>
	                         <td height="25">
								<div align="center"><img src="images/View_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="viewTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="editTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/delete_icon.gif" style="cursor:pointer"
								 width="16" height="16" onclick="deleteTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
                            </tr>
                          </logic:iterate>
                      </table></logic:notEmpty></div>
                      </td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                  </table></td>
                </tr>
                 <tr>
                  <td height="10" colspan="6" ></td>
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
		