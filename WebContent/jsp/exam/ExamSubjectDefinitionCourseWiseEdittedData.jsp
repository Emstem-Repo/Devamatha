<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:form action="/ExamSubjectDefinitionCourseWise.do" method="POST">
<table width="100%" border="0">
  <tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCourseWise" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.subjectSectionMaster" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div></td>
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
                  <td height="25" class="row-odd" ><div align="right">Academic Year : </div></td>
                  <td  height="25"   class="row-even" >2009-10</td>
                  <td width="20%" height="25" class="row-odd" ><div align="right">Course  :</div></td>
                <td width="29%" height="25" class="row-even" >BCom</td>
                </tr>
                <tr>
                 <td width="28%" class="row-odd" ><div align="right">Scheme  :</div></td>
                <td class="row-even" colspan="3" >Semester</td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="../images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="../images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" class="row-odd" ><div align="center">Sl.No </div></td>
                <td width="45%" class="row-odd" >Specialisation</td>
                <td width="40%" class="row-odd" > Subject Group</td>
                </tr>
              <tr >
                <td width="15%" height="25" class="row-even"><div align="center">1</div></td>
                <td class="row-even" >MBA in Finance</td>
                <td class="row-even" ><select name="select2" id="select3" size="6" multiple style="width:175px">
                  <option>Select</option>
                  <option>S1</option>
                  <option>S2</option>
                </select></td>
                </tr>
              <tr >
                <td height="25" class="row-white"><div align="center">2</div></td>
                <td class="row-white" >MBA in HR</td>
                <td class="row-white" >                
                  <select name="select" id="select" size="6" multiple style="width:175px">
                    <option>Select</option>
                    <option>S1</option>
                    <option>S2</option>
                  </select>                </td>
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
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
          <tr>
            <td width="45%" height="35"><div align="right">
              <input name="button2" type="button" class="formbutton" value="Submit" />
            </div></td>
            <td width="2%"></td>
            <td width="53%"><input type="button" class="formbutton" value="Reset" /></td>
          </tr>          
          <tr><td style="height: 10px" align="center" colspan="3"></td></tr>          
        </table> </td>
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
