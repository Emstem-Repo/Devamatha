<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<%@page import="java.util.Map,java.util.HashMap"%>



<link href="../css/styles.css" rel="stylesheet" type="text/css">


<html:form action="/ExamSubjectDefinitionCourseWise.do" method="POST">

<table width="99%" border="0">
  
  <tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCourseWise" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > Subjects Definition   </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><div align="right" class="mandatoryfield">*Mandatory fields</div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Subject Code  :</div></td>
                  <td width="26%" class="row-even" ><input name="textfield14" type="text" id="textfield" size="15"></td>
                 
                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>Subject Name  :</div></td>
                  <td class="row-even" ><input name="textfield15" type="text" id="textfield2" size="15"></td>
                </tr>
                <tr >
                  <td width="26%" height="25" rowspan="2" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Subject Type  :</div></td>
                  <td class="row-even" ><select name="select" id="goto" class="combo" onChange="fun1(document.getElementById('goto').value)">
                    <option selected="selected">Select</option>
                    <option  value="1">Open Elective</option>
                    <option >Allieds</option>
                    <option >Core</option>
                    <option  >Specialisation</option>
                    <option >Skill Based Paper</option>
                    <option >Subject elective</option>
                   
                  </select></td>
                  
                  <td width="20%" rowspan="2" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Theory/Practical :</div></td>
                  <td width="28%" rowspan="2" class="row-even" ><select name="select5" id="select5" class="combo">
                    <option selected="selected">Select</option>
                    <option >Theory</option>
                    <option >Practical</option>
                    <option >Both</option>
                  </select></td>
                </tr>
                <tr >
                  
                </tr>
                <tr >
                  <td   class="row-odd" ><div align="right">Consolidated Marks Card Subject Name  :</div></td>
                  <td class="row-even"  ><input name="textfield3" type="text" size="15"></td>
                
                 
                  <td height="25"  class="row-odd" ><div align="right">Subject Name prefix  :</div></td>
                  <td class="row-even"  ><input name="textfield3" type="text" size="15"></td>
                  </tr>
                  <tr>
                  <td height="25"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>Is Optional Subject ?  :</div></td>
                  <td class="row-even"   ><input type="radio" name="secondlanguage" value="Yes" id="secondlanguage3">
Yes
  <input type="radio" name="secondlanguage" value="No" id="secondlanguage4">
No        </td> 
<td width="20%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>Is Second Language ?:</div></td>
                  <td width="28%"  class="row-even" ><input type="radio" name="secondlanguage" value="Yes" id="secondlanguage1">
                  Yes
                    <input type="radio" name="secondlanguage" value="No" id="secondlanguage2">
                    No</td>         
                </tr>
                <tr>
                <td class="row-odd" height="25"><div align="right">Major Department Code</div></td>
                <td class="row-even" colspan="3"><select name="select2" id="select" class="combo">
                  <option selected="selected">Select</option>
                  <option>Physics</option>
                  <option>Chemistry</option>
                                </select></td>
                </tr>
                
				
                
              </table>
                <div class="hide1" id="div"  style="border:1px solid #e2e2e2">
                  <table border="0" width="100%">
                    <tr>
                      <td width="18%" class="row-even" align="right">From Date</td>
                      <td class="row-even"><table border="0" width="100" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><input name="testinput2" type="text" size="10" maxlength="10" ></td>
                            <td><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'testform',
		// input name
		'controlname': 'testinput'
	});</script></td>
                          </tr>
                      </table></td>
                      <td class="row-even" align="right">To Date</td>
                      <td class="row-even"><table width="100" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><input name="testinput3" type="text" size="10" maxlength="10" ></td>
                            <td><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'testform',
		// input name
		'controlname': 'testinput'
	});</script></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table>
                </div>
              <div class="hide1" id="div2"  style="border:1px solid #e2e2e2">
                  <table width="100%">
                    <tr>
                      <td width="625" class="row-even" align="right">Reg No.</td>
                      <td width="" class="row-even"><input name="testinput2" type="text" size="10" maxlength="10" ></td>
                    </tr>
                  </table>
              </div></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      
      
      <tr>
        <td height="50" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0"><tr><td width="15%" align="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0"><tr><td width="15%" align="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="15%" align="center"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                <tr>
                  <td width="39%" height="37" align="right">&nbsp;</td>
                  <td width="19%" align="center"><input name="button2" type="submit" class="formbutton" value="Submit" /></td>
                  <td width="42%"><input type="button" class="formbutton" value="Cancel" /></td>
                </tr>                
                <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                
            </table></td>
          </tr>
        </table>
        </td>
            </tr>
        </table></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
            <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5" background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr>
                    <td height="25" colspan="4"><table width="100%" cellspacing="1" cellpadding="2">
                        <tr>
                          <td height="25" class="row-odd"><div align="center">Sl.No</div></td>
                          <td height="25" class="row-odd" align="center">Subject Code</td>
                          <td class="row-odd" align="center">Subject Name</td>
                            <td class="row-odd" align="center">Subject Type</td>
                          <td class="row-odd" align="center">Is Second Language ?</td>
                           <td width="11%" align="center" class="row-odd">Theory/Practical</td>
                           <td width="13%" align="center" class="row-odd">Is Optional Subject ?</td>
                          <td width="11%" class="row-odd"><div align="center">Edit</div></td>
                          <td width="7%" class="row-odd"><div align="center">Delete</div></td>
                        </tr>

                        <tr>
                          <td height="25" class="row-white"><div align="center">1</div></td>
                          <td height="25" class="row-white" align="center">cs2</td>
                          <td class="row-white" align="center">c#</td>
                          <td class="row-white" align="center">Open elective</td>
                          <td class="row-white" align="center">No</td>
                           <td class="row-white" align="center">Theory</td>
                           <td class="row-white" align="center">No</td>
                          <td height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editSubject('5')" /></div></td>
                          <td height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteSubject('5')" /></div></td>
                        </tr>
                        <tr>
                          <td width="4%" height="25" class="row-even"><div align="center">2</div></td>
                          <td width="13%" height="25" class="row-even" align="center">cs3</td>
                          <td width="14%" class="row-even" align="center">dos</td>
                          <td width="14%" class="row-even" align="center">Allieds</td>
                          <td width="13%" class="row-even" align="center">No</td>
                           <td class="row-even" align="center">Theory</td>
                            <td width="13%" class="row-even" align="center">No</td>
                          <td width="11%" height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editSubject('6')" /></div></td>
                          <td width="7%" height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteSubject('6')" /></div></td>
                        </tr>
                        <tr>
                          <td height="25" class="row-white"><div align="center">3</div></td>
                          <td height="25" class="row-white" align="center">cs4</td>
                          <td class="row-white" align="center">os</td>
                          <td class="row-white" align="center">No</td>
                          <td class="row-white" align="center">No</td>
                          <td class="row-white" align="center">Theory</td>
                          <td class="row-white" align="center">No</td>
                          <td height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editSubject('7')" /></div></td>
                          <td height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteSubject('7')" /></div></td>
                        </tr>

                    </table></td>
                  </tr>
              </table></td>
              <td width="5" height="29" background="images/right.gif"></td>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
        
      </tr>
    </table></td>
  </tr>
</table>
</html:form>

