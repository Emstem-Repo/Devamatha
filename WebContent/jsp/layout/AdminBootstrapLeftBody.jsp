<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
 <!--<script type="text/javascript">
	var myMenu;
	window.onload = function toTest(){
		myMenu = new SDMenu("my_menu");
		myMenu.init();
		setClocks();
		if(document.getElementById("toggle").value=="X")
			document.getElementById("toggle").value="X";
		else
			document.getElementById("toggle").value=">";
	};
</script>


<script type="text/javascript">	
	var tog=true;
	function search(searchValue){
		var len=document.getElementById("menuCount").value;
		var searchValueLen = searchValue.length;
		for(var i=0; i<len; i++){				
			var id="link_"+i;
			var divId="div_"+i;
			var ele = document.getElementById(divId);
			if(searchValue!=null && searchValue!=""){
			if(((document.getElementById(id).value).toUpperCase().search(searchValue.toUpperCase())) >=0){
				ele.style.display = "block";
			}		
			else
			{
				ele.style.display = "none";				
			}		
			}
			else
			{
				ele.style.display = "block";
			}
	}
	}
	function openClose(){
		if(tog){
			myMenu.expandAll();
			document.getElementById("toggle").value="X";
			tog=false;
		}
		else
		{
			myMenu.collapseAll();
			document.getElementById("toggle").value=">";
			tog=true;
		}
	}

</script>
<script type="text/javascript">
	function openLink(link,name){
		var url=link+"&menuName="+name;
		document.location.href=url;
	}
		
</script>

-->
<%int co=0;	%>
<html:hidden property="menuCount" styleId="menuCount" name="loginform"/>
<head>
<style>

.ScrollStyle
{
     max-height: 575px;
    overflow-y: scroll;
}
</style>
</head>
<header>
 <div class="leftpanel">
<div class="ScrollStyle">
<div style="float: left" id="my_menu" class="sdmenu">
                                       <input class="form-control" id="catInput" placeholder="Search here..." title="Search with category name." type="text" style="background-color: #fdfdfd;">
               
                            <!-- /input-group -->
                           
	 <ul class="nav nav-pills nav-stacked" id="orgCat" style="width:213px;">
	
                   
	<logic:iterate name="loginform" property="moduleMenusList" id="id" indexId="count">
	
 <li class="parent">
<a href="#"><i class="glyphicon glyphicon-circle-arrow-down"></i>                                                                                                                                                                                                                                                                                                                                                              
           <span style="color:black"> <b><bean:write name="id" property="moduleName"/></b></span></a>
	<div class="collapsed" id="subMenu">
	
	 <logic:iterate name="id" property="menuTOList" id="menuId" type="com.kp.cms.bo.admin.Menus">
	 
	 <%  	    
	 String link1="link_"+co;
	 String link2="div_"+co;
	 co++;
	 %>
	 <% String url = menuId.getUrl()+"&menuName="+menuId.getDisplayName(); %>
	 <input type="hidden" id="<%=link1 %>" value='<bean:write name="menuId" property="displayName"/>'/>
	
	
	  <ul class="children">
	 <li id="<%=link2 %>" style="display: block">
	
	 <c:choose>
	<c:when  test="${menuId.newtab}">
	
	
	<a target="_blank" href="<bean:write name="menuId" property="url"/>" class="menuLink"> 
	
	<i class="glyphicon glyphicon-circle-arrow-right"></i> <bean:write name="menuId" property="displayName"/></a>
	</c:when>
	 <c:otherwise>
	 <a href="<%=url %>" class="menuLink"><i class="glyphicon glyphicon-circle-arrow-right"></i> <bean:write name="menuId" property="displayName"/></a>
	
	</c:otherwise>
	  </c:choose>
	  </li>	
	 </ul>
	 </logic:iterate>
	

	</div>
	
	</li>
	
</logic:iterate>

</ul>
</div>
</div>
</div>
</header>
 <script>
  $('input#catInput').keyup(function() {
  var searchTerms = $(this).val();
  console.log('Input change');
  if(searchTerms == "") {
  	$expandBtns.each(function() {
    	var $subList = $(this).siblings('ul');
       $subList.hide('fast');
    });
  }
  $('#orgCat li').each(function() {
    var $li = $(this);
    var hasMatch = searchTerms.length == 0 || $li.text().toLowerCase().indexOf(searchTerms.toLowerCase()) > 0;

    $li.toggle(hasMatch);
    if ($li.is(':hidden')) {
      $li.closest("ul").show('fast');
    }
  });
});



var $expandBtns = $('.expandBtn');
//counting childs 
$expandBtns.each(function() {
  var $span = $(this).find('span#count');
  var $subList = $(this).siblings('ul').find('li')

  $span.text('  ' + $subList.length)
});

//Collapse and Expand
$('#orgCat ul').hide('li');
$expandBtns.on('click', function() {
  var $subList = $(this).siblings('ul');

  if ($subList.is(':visible')) {
    $subList.hide('fast');
  } else {
    $subList.show('fast');
  }
});
</script>