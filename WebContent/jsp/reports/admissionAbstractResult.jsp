<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer="500kb" %>
<html:html>
<head>
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "AdmissionAbstract.do?method=initAdmissionAbstract";
    }
</SCRIPT>
</head>
<body>
<html:form action="/AdmissionAbstract" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>
<table width="98%" border="0">
  <tr>
	<td><span class="Bredcrumbs"><bean:message
		key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.reports.admission.abstract"/>
		&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.reports.admission.abstract"/></strong></div></td>
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
              <td width="100%" valign="top">
			<div style="overflow: auto; width: 914px;">
			<display:table export="true" uid="studList" name="sessionScope.finalStudentList" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%"
				decorator="org.displaytag.decorator.TotalTableDecorator" >
				<display:caption class="heading"><bean:message key="knowledgepro.reports.adm.abstract"/>&nbsp;<bean:write name ="admissionAbstractForm" property="academicYear" /></display:caption>							
				<display:setProperty name="export.excel.filename" value="admissionAbstraction.xls"/>
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv.filename" value="admissionAbstraction.csv"/>
				<c:choose>
					<c:when test="${studList.programCode == 'Sub Total' }">
						<display:column property="programCode" title="DEGREE" class="row-white" headerClass="row-odd" style="width:5%"/>
						<display:column property="courseCode"  title="Course" class="row-white" headerClass="row-odd" style="width:5%"/>
						<c:choose>
							<c:when test="${studList.karStudents == '0'}">
								<display:column title="Karnataka" class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="karStudents" format="{0,number,0}" title="Karnataka" class="row-white" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.otherThanKar == '0'}">
								<display:column title="Other Than Karnataka" class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="otherThanKar" format="{0,number,0}"  title="Other Than Karnataka" class="row-white" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.otherThanInd == '0'}">
								<display:column  title="Other Than India" class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="otherThanInd" format="{0,number,0}" title="Other Than India" class="row-white" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.boysCount == '0'}">
								<display:column title="Boys" class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="boysCount" format="{0,number,0}"  title="Boys" class="row-white" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.girlsCount == '0'}">
								<display:column  title="Girls" class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="girlsCount" format="{0,number,0}"  title="Girls" class="row-white" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<% int totalNo = 0;
						   String title = "";
						   if(studList != null){%>
						<logic:notEmpty name="studList" property="admCatgList">
						<logic:iterate id="index" name="studList" property="admCatgList"  type="com.kp.cms.to.reports.AdmAbstractCatgMapTO"	>
						<%  title = index.getCategoryName();
							totalNo=index.getNoOfStudents();
						%>
						<c:set var = "test" value = "${index.noOfStudents}"></c:set>
						<c:choose>
							<c:when test="${test == '0'}">
								<display:column  title="<%=title %>"  class="row-white" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
							<display:column  title="<%=title %>" class="row-white" headerClass="row-odd" style="width:5%" >
								<bean:write name="index" property="noOfStudents" />
							</display:column>
							</c:otherwise>
						</c:choose>
						</logic:iterate>
						</logic:notEmpty>
						<%} %>
						<display:column property="total" format="{0,number,0}"  title="Total" class="row-white" headerClass="row-odd" style="width:5%" />
					</c:when>
					<c:otherwise>
						<display:column property="programCode" title="DEGREE" class="row-even" headerClass="row-odd" style="width:5%"/>
						<display:column property="courseCode" title="Course" class="row-even" headerClass="row-odd" style="width:5%"/>
						<c:choose>
							<c:when test="${studList.karStudents == '0'}">
								<display:column title="Karnataka" class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="karStudents" format="{0,number,0}" title="Karnataka" class="row-even" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.otherThanKar == '0'}">
								<display:column title="Other Than Karnataka" class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="otherThanKar" format="{0,number,0}"  title="Other Than Karnataka" class="row-even" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.otherThanInd == '0'}">
								<display:column  title="Other Than India" class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="otherThanInd" format="{0,number,0}" title="Other Than India" class="row-even" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.boysCount == '0'}">
								<display:column title="Boys" class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="boysCount" format="{0,number,0}"  title="Boys" class="row-even" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${studList.girlsCount == '0'}">
								<display:column  title="Girls" class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
								<display:column property="girlsCount" format="{0,number,0}"  title="Girls" class="row-even" headerClass="row-odd" style="width:5%"  />
							</c:otherwise>
						</c:choose>		
						<% int totalNo = 0;
						   String title = "";
						   if(studList != null){%>
						<logic:notEmpty name="studList" property="admCatgList">
						<logic:iterate id="index" name="studList" property="admCatgList"  type="com.kp.cms.to.reports.AdmAbstractCatgMapTO"	>
						<%  title = index.getCategoryName();
							totalNo=index.getNoOfStudents();
						%>
						<c:set var = "test" value = "${index.noOfStudents}"></c:set>
						<c:choose>
							<c:when test="${test == '0'}">
								<display:column  title="<%=title %>"  class="row-even" headerClass="row-odd" style="width:5%" >Nil</display:column>
							</c:when>
							<c:otherwise>
							<display:column  title="<%=title %>" class="row-even" headerClass="row-odd" style="width:5%" >
								<bean:write name="index" property="noOfStudents" />
							</display:column>
							</c:otherwise>
						</c:choose>
		
						</logic:iterate>
						</logic:notEmpty>
						<%} %>
						<display:column property="total" format="{0,number,0}"  title="Total" class="row-even" headerClass="row-odd" style="width:5%" />
					</c:otherwise>
				</c:choose>
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
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
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