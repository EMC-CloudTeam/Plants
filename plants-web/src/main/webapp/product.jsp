<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2003
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<link href="/plants-web/PlantMaster.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>
function verifyQty(prodform)
{
	var result = true;
	if (isNaN(parseInt(prodform.qty.value)))
	{
       result = false;
	}
	else if (prodform.qty.value <= 0) 
    {
       result = false;
	}
    else
	{
 		for (i=0; i < prodform.qty.value.length; i++)
		{
		  if ( isNaN(parseInt(prodform.qty.value.charAt(i))) )
		  {
             result = false;
             break;
		  }
		}
	}               

    if (!result) 
	{
        alert("Quantity must be a valid number.");
	}

    return result;
}
</script>

</head>

<%
	com.emc.plants.persistence.Inventory inv = 
      (com.emc.plants.persistence.Inventory) 
      request.getAttribute(com.emc.plants.utils.Util.ATTR_INVITEM);

String categoryName = com.emc.plants.utils.Util.getCategoryString(inv.getCategory());

//based on the categoryName, select the correct menu item to highlight

String menu=null;

if (categoryName.equals("Flowers")) 
   menu="menu1";
else
   if (categoryName.equals("Fruits & Vegetables"))
       menu="menu2";
   else
       if (categoryName.equals("Trees"))
           menu="menu3";
       else
           menu="menu4";
%>

<body marginwidth="0" leftmargin="0" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4');top.selectMenu('<%=menu%>')"> 

<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="trail"><p class="trail"><a class="trail" href="/plants-web/promo.html" target="work">Home</a> &gt;
                      <a class="trail" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=<%=inv.getCategory()%>" target="work"><%= categoryName %></a></p></td>
  </tr>
  <tr>
    <td colspan="5" width="100%">
      <table width="600" cellpadding="4" cellspacing="5" border="0">
        <tr>
          <td colspan="2" valign="bottom"><h1><%= inv.getName() %></h1></td>
	</tr>
	<tr>
	  <td rowspan="9" valign="top">
          <img src="/plants-web/servlet/ImageServlet?action=getimage&inventoryID=<%=inv.getInventoryId()%>" border="0" width="220" height="250" alt="<%= inv.getName() %>"><br>
          <img src="/plants-web/images/1x1_trans.gif" width="220" height="10" border="0" alt=""><br>
          <br>
          </td>
          <td valign="top"><font face="verdana, arial, sans serif" size="2" color="666666"><i><b><%= inv.getHeading()%></b></i></font><br>
           <font size="1" color="666666" face="Verdana,Arial, Helvetica"><%= inv.getDescription()%></font></td>
        </tr>
        <tr>
          <td valign="top"><hr size="1" noshade></td>
        </tr>
        <tr>
          <td valign="top">
            <form onsubmit="return verifyQty(this);" target="_self" name="prodform" method="post" action="/plants-web/servlet/ShoppingServlet">
              <table width="100%" cellpadding="0" cellspacing="0" border="0" >
                <tr>
                  <td><img src="/plants-web/images/item_selection.jpg" width=73 height=20 alt="Item selection" border="0"><br></td>
                </tr>
                <tr bgcolor="#eeeecc">
                  <th class="item">ITEM#</th>
                  <th class="item">DESCRIPTION</th>
                  <th class="item">PRICE</th>
                  <th class="item">QUANTITY</th>
                </tr>
                <tr bgcolor="#ffffdd" >
                  <td class="item"><%= inv.getInventoryId()%></td>
                  <td class="item"><%= inv.getPkginfo()%></td>
                  <td class="item"><%= java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(inv.getPrice()))%></td>
                  <td class="item"><input type="text" name="qty" value="1" size="3" maxlength="3"></td>
                </tr>
                <tr>
                  <td valign="top">&nbsp;</td>
                </tr>
                <tr>
                  <td valign="top">		
            		  <input type="image" name="submit" alt="Add to Cart" src="/plants-web/images/button_add_to_cart.gif"><a href="javascript:verifyQty(this);"></a>
		              <input type="hidden" name="itemID" value="<%= inv.getInventoryId()%>">
		              <input type="hidden" name="itemQuantity" value="1">
		              <input type="hidden" name="action" value="addtocart">
		            </td>
                </tr>
              </table>
            </form>
          </td>  
        </tr>
        </table>
      </td>
    </tr>
</table>
<br><br>
<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
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