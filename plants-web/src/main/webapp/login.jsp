<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>

function verifyFields(signinForm)
{
    if ((signinForm.userid.value == "") ||
      (signinForm.passwd.value == ""))
   {
      alert("All required fields must be filled in.");
      return false;
   }
    else
   {
      return true;
   }
}
</script>


</head>
<body class="work" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">

<%
	String servletParm = "updating=false";
  String updating = (String) request.getAttribute(com.emc.plants.utils.Util.ATTR_UPDATING);
  if ((updating != null) && (updating == "true"))
  {
     servletParm = "updating=true";
  }
%>  

<form onsubmit="return verifyFields(this);" target="_self" name="signinForm" method="post" action="/plants-web/servlet/AccountServlet?action=login&<%=servletParm%>">
<table border="0" cellpadding="0" cellspacing="5" width="100%">
  <tr>
    <td><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a></p></td>
  </tr>
  <tr>
    <td width="100%">
      <table border="0" cellpadding="5" cellspacing="5" width="600">
        <tr>
          <td width="100%" valign="middle"><h1>Login or Register</h1></td>
          <td align="right" valign="middle" nowrap></td>
          <td valign="middle"></td>
        </tr>
        <tr>
          <td colspan="3">
            <table width="600" border="0" cellpadding="2" cellspacing="0">
              <tr>
                <td colspan="2"><p>If you are a returning customer and previously
                  set up an account, please enter your <nobr>e-mail</nobr>
                  address and password below.</p><br>
                </td>
              </tr>
              <tr>
                <td nowrap><p><label for="email">E-mail address:&nbsp;</label></p></td>
                <td width="100%"><p><input type="text" name="userid" size="20" id="email"></p></td>
              </tr>
              <tr>
                <td nowrap><p><label for="password">Password:&nbsp;</label></p></td>
                <td><p><input type="password" name="passwd" size="20" id="password"></p></td>
              </tr>
              <tr>
                <td></td>
              </tr>
              <tr>          
                <td colspan="2"> <font color="#ff0033">
                  <%
                  	String results;
                                       results = (String) request.getAttribute(com.emc.plants.utils.Util.ATTR_RESULTS);
                                       if ( results != null )
                                          out.print(results);
                  %></font>
                </td>
              </tr>          
              <tr>
                <td colspan="2">
                  <input type="image" name="action" VALUE="login" alt="Sign in" SRC="/plants-web/images/button_sign_in.gif"><a href="javascript:verifyFields(this);"></a>
                  <br><br></td>
                <td></td>
              </tr>
              <tr>
                <td> </td>
              </tr>
              <tr>
                <td colspan="2"><p>If you are a <i>New</i> customer you can
                  <a href="/plants-web/register.jsp" target="work">register for your own account here</a>.
                  </p><br>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>

<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
</form>
<br>

<table border="0" cellpadding="5" cellspacing="0" width="100%">
  <tr>
    <td>
      <img src="/plants-web/images/poweredby_WebSphere.gif" alt="Powered by WebSphere">
    </td> 
    <td>
      <p class="footer"><a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=0" target="work">Flowers</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=1" target="work">Fruits &amp; Vegetables</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=2" target="work">Trees</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=3" target="work">Accessories</a><br>
                  <a class="footer" href="/plants-web/index.html" target="_top">Home</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=gotocart" target="work">Shopping Cart</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/AccountServlet?action=account" target="work">My Account</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/login.jsp" target="work">Login</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/help.jsp" target="_blank">Help</a></p>
    </td>
  </tr>
</table>
</body>
</html>