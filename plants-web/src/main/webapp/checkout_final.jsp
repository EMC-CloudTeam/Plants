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

<link href="/plants-web/PlantMaster.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>

</head>
<body class="work" marginwidth="0" leftmargin="0" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">

<%@ page import="com.emc.plants.pojo.beans.ShoppingCartItem" session="true" isThreadSafe="true" isErrorPage="false" %>
<jsp:useBean id="cartitems" type="java.util.Collection" scope="request" />
<%
	com.emc.plants.pojo.beans.OrderInfo orderInfo = 
      (com.emc.plants.pojo.beans.OrderInfo) 
      request.getAttribute(com.emc.plants.utils.Util.ATTR_ORDERINFO);

float subtotal = 0.0f;
ShoppingCartItem si;
for (Object o : cartitems) 
{
   si = (ShoppingCartItem) o;
   subtotal += si.getQuantity() * si.getPrice();
}
float shiptotal = com.emc.plants.utils.Util.getShippingMethodPrice(orderInfo.getShippingMethod());
float total = subtotal + shiptotal;
String shipMethod = com.emc.plants.utils.Util.getShippingMethod(orderInfo.getShippingMethod());
%>

<!--
<form method="post" action="/plants-web/servlet/ShoppingServlet">
-->
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a> &gt; <a class="trail" class="footer" href="/plants-web/cart.jsp" target="work">Shopping Cart</a> &gt; <a class="footer" href="/plants-web/orderinfo.jsp" target="work">Checkout</a><br></td>
  </tr>
  <tr>
    <td width="100%">
      <table cellpadding="0" cellspacing="0" border="0" width="600">
        <tr>
          <td width="600" valign="middle"><H1>Review Your Order</H1></td>
          <td align="right" valign="middle" nowrap></td>
	      <td valign="middle"></td>
	     </tr>
        <tr>
          <td colspan="3"><p>Review your order below and select 'Submit Order'
            at the bottom to place your order.
            You can also add more items to your order by selecting 'Continue
            Shopping'.<br><br></p></td>
        </tr>
        <tr>
          <td colspan="3">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<caption>Order Information</caption>
<tbody>
  <tr>
    <td valign="top" width="32%" bgcolor="#ffffdd">
          <table width="100%" border="0" cellpadding="4" cellspacing="0" width="100%" bgcolor="#ffffdd">
            <tr bgcolor="#eeeecc">
      <th class="item">ORDER TOTAL</th>
      </tr>
        <tr>
          <td width="100%" width="34%"><p><b><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(total))%></b></p></td>
        </tr>
      </table></td>
      <td bgcolor="#ffffff"><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" border="0" alt=""></td>
    <td valign="top" width="34%" bgcolor="#ffffdd">
    
      <table width="100%" border="0" cellpadding="4" cellspacing="0" width="100%">
            <tr bgcolor="#eeeecc">
      <th class="item">SHIPPING ADDRESS</th>
      </tr>
        <tr>
          <td width="100%"><p><%=orderInfo.getShipName()%><br>
            <%=orderInfo.getShipAddr1()%><br>
            <%=orderInfo.getShipAddr2()%><br>
            <%=orderInfo.getShipCity()%>, <%=orderInfo.getShipState()%> <%=orderInfo.getShipZip()%><br>
            <%=orderInfo.getShipPhone()%><br>
          </td>
        </tr>
        <!--
        <tr>
          <td width="100%"><img border="0" src="/plants-web/images/button_change.gif" width="58" height="27" alt="Change button"></td>
        </tr>
        -->
      </table>
      
    </td>
          <td bgcolor="#ffffff"><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" border="0" alt=""></td>
    <td valign="top" width="34%" bgcolor="#ffffdd">
      <table width="100%" border="0" cellpadding="4" cellspacing="0" width="100%">
      <tr bgcolor="#eeeecc">
      <th class="item">BILLING ADDRESS</th>
      </tr>
        <tr>
          <td width="100%"><p><%=orderInfo.getBillName()%><br>
            <%=orderInfo.getBillAddr1()%><br>
            <%=orderInfo.getBillAddr2()%><br>
            <%=orderInfo.getBillCity()%>, <%=orderInfo.getBillState()%> <%=orderInfo.getBillZip()%><br>
            <%=orderInfo.getBillPhone()%><br>
          </td>
        </tr>
        <!--
        <tr>
          <td width="100%"><img border="0" src="/plants-web/images/button_change.gif" width="58" height="27" alt="Change button"></td>
        </tr>
        -->
      </table>
    </td>
  </tr>
  </tbody>
</table>
&nbsp;
            <table width="600" border="0" cellpadding="2" cellspacing="0">
            <caption>Order Details</caption>
              <tr bgcolor="#eeeecc">
                <th><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" align="left" border="0" alt=""></th>
                <th class="item" align="left">ITEM #</th>
    	        <th class="item" align="left" nowrap>ITEM DESCRIPTION</th>
    	        <th class="item" align="left">PACKAGING</th>
    	        <th class="item" align="left">QUANTITY</th>
    	        <th class="item" align="left">PRICE</th>
    	        <th class="item" align="left">SUBTOTAL</th>
                <th><img src="/plants-web/images/1x1_trans.gif" width="4" height="4" align="left" border="0" alt=""></th>
              </tr>

                <%
                for (Object o : cartitems) 
                {
			String        id =  ((ShoppingCartItem)o).getID();
			String      name =  ((ShoppingCartItem)o).getName();
			String   pkgInfo =  ((ShoppingCartItem)o).getPkginfo();
			int     quantity =  ((ShoppingCartItem)o).getQuantity();
			float      price =  ((ShoppingCartItem)o).getPrice(); 
%>                                           
              
              <tr bgcolor="#ffffdd">
    	       <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
    	       <td valign="top" nowrap><p><%= id%></p></td>
    	       <td valign="top" nowrap><p><%= name%></p></td>
               <td valign="top"><p><%= pkgInfo%></p></td>
               <td valign="top"><p><%= quantity%></p></td>
               <td align="center" valign="top"><p><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(price))%></p></td>
               <td align="center" valign="top"><p><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(price * quantity))%></p></td>
    	       <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
             </tr>

             <%} %>

             
             <tr bgcolor="#ffffdd">
               <td align="right" valign="top" colspan="8"><hr size="1" noshade></td>
             </tr>
             <tr bgcolor="#ffffdd">
               <td align="right" valign="top" colspan="6"><p>Order Subtotal: </p></td>
               <td valign="top" align="right"><p><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(subtotal))%></p></td>
               <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
             </tr>
             <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="6"><p>Shipping, <%=shipMethod%>: </p></td>
              <td align="right" valign="top"><p><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(shiptotal))%></p></td>
              <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
            </tr>
            <!--
            <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="5"><p>Tax: </p></td>
    	       <td align="right" valign="top"><p>$0</p></td>
    	       <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
            </tr>
            -->
            <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="8"><hr size="1" noshade></td>
            </tr>
            <tr bgcolor="#ffffdd">
              <td align="right" valign="top" colspan="6"><p><b>Order Total: </b></p></td>
              <td align="right" valign="top"><p><b><%=java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US).format(new Float(total))%></b></p></td>
    	       <td valign="top"><img src="/plants-web/images/1x1_trans.gif" width="2" height="22" align="left" border="0" alt=""></td>
            </tr>
            <tr>
              <td colspan="8" valign="top">&nbsp;</td>
            </tr>
            <tr>
              <td valign="top" colspan="8">
                <table cellpadding="0" cellspacing="0" border="0">
                  <tr>
                    <td nowrap>
                      <form method="post" action="/plants-web/servlet/ShoppingServlet">
                        <input type="image" name="Continue Shopping" value="shopping" alt="Continue Shopping" src="/plants-web/images/button_continue_shopping.gif">
                        <input type="hidden" name="action" value="shopping">
                      </form>
                    </td>
                    <td>
                      <form method="post" action="/plants-web/servlet/ShoppingServlet">
                        <input type="image" name="Checkout Now" value="completecheckout" alt="Checkout Now" src="/plants-web/images/button_submit_order.gif">
                        <input type="hidden" name="action" value="completecheckout">
                      </form>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table><br>
<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
<!--
</form>
-->
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