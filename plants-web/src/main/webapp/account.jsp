<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<link href="/plants-web/PlantMaster.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>
function verifyForm(reginfo)
{
    if ((!exists(reginfo.fname.value)) ||
        (!exists(reginfo.lname.value)) ||
        (!exists(reginfo.addr1.value)) ||
        (!exists(reginfo.city.value)) ||
        (!exists(reginfo.state.value)) ||
        (!exists(reginfo.zip.value)) ||
        (!exists(reginfo.phone.value)))
   {
      alert("All required fields must be filled in.");
      return false;
   }
   else if (!verifyZip(reginfo.zip.value)) {
       alert("Zip Code is not valid.");
       return false;
   }
   else if (!verifyPhone(reginfo.phone.value)) {
       alert("Phone is not valid.");
       return false;
   }
   return true;
}

function exists(inputVal)
{
    var result = false;
    for (var i = 0; i <= inputVal.length; i++) {
        if ((inputVal.charAt(i) != " ") && (inputVal.charAt(i) != "")) {
            result = true;
            break;
        }
    }
    return result;
}

function verifyZip(zipVal)
{
    var result = false;
    for (var i = 0; i < zipVal.length; i++) {
        if (parseFloat(zipVal.charAt(i))) {
            result = true;
            break;
        }
    }
    return result;
}

function verifyPhone(phoneVal)
{
    var result = false;
    var cnt = 0;
    for (var i = 0; i < phoneVal.length; i++) {
        if (parseFloat(phoneVal.charAt(i))) {
            cnt++;
            if (cnt >= 7) {
                result = true;
                break;
            }
        }
    }
    return result;
}
</script>

</head>
<body class="work" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">
<%
	com.emc.plants.pojo.beans.CustomerInfo customerInfo = 
      (com.emc.plants.pojo.beans.CustomerInfo) 
      request.getAttribute(com.emc.plants.utils.Util.ATTR_EDITACCOUNTINFO);
String fname = "";
String lname = "";
String addr1 = "";
String addr2 = "";
String city = "";
String state = "";
String zip = "";
String phone = "";
if (customerInfo != null)
{
   fname = customerInfo.getFirstName();
   lname = customerInfo.getLastName();
   addr1 = customerInfo.getAddr1();
   addr2 = customerInfo.getAddr2();
   city = customerInfo.getAddrCity();
   state = customerInfo.getAddrState();
   zip = customerInfo.getAddrZip();
   phone = customerInfo.getPhone();
}
%>
<form onsubmit="return verifyForm(this);" target="_self" name="reginfo" method="POST" action="/plants-web/servlet/AccountServlet?action=accountUpdate">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a> &gt; <a class="trail" class="footer" href="/plants-web/login.jsp" target="work">Sign in</a></p></td>
  </tr>
  <tr>
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="10" width="600">
        <tr>
          <td width="100%" valign="middle"><h1>Account Update</h1></td>
          <td align="right" valign="middle" nowrap></td>
          <td valign="middle"></td>
        </tr>
        <tr>
          <td colspan="3"><p>Enter the information below to update your account.
          This information will not be shared without your permission. With your
          permission we will only share your name and email address with our
          trusted business partners.<br><br></p></td>
        </tr>
        <tr>
          <td colspan="3"><p class="small">Required fields are denoted with a red asterisk
          (<img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required
          indicator">).<br><br></p></td>
        </tr>
        <tr>
          <td colspan="3">

<table border="0" cellpadding="0" cellspacing="0">
<caption>Contact Information</caption>
<colgroup>
<colgroup>
<colgroup>
<tbody>  
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td nowrap width="120"><p><label for="fname">First Name </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td width="100%"><p><input type="text" name="fname" value="<%=fname%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="lname">Last Name </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td width="100%"><p><input type="text" name="lname" value="<%=lname%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="addr1">Address Line 1 </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td width="100%"><p><input type="text" name="addr1" value="<%=addr1%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="addr2">Address Line 2 </label></td>
    <td></td>
    <td><p><input type="text" name="addr2" value="<%=addr2%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="city">City </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="city" value="<%=city%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="state">State </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="state" value="<%=state%>" size="20"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="zip">Zip Code </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="zip" value="<%=zip%>" size="5" maxlength="10"></p></td>
  </tr>
  <tr>
    <td nowrap><p><label for="phone">Phone (daytime) </label></td>
    <td><img border="0" src="/plants-web/images/required.gif" width="10" height="15" alt="Required indicator"></td>
    <td><p><input type="text" name="phone" value="<%=phone%>" size="14" maxlength="14"></p></td>
  </tr>
  <tr><td> <br> </td></tr>
  <tr>
    <td></td> 
    <td colspan="2"><input type="image" alt="Register" src="/plants-web/images/button_update.gif"><a href="javascript:verifyForm(this);"></a></td>
  </tr>
  </tbody>
</table>   <!-- end of Contact Information table -->
<br>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>

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

