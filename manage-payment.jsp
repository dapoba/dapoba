<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>�Ա� ����</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="manage-payment.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="main.html">DAPOBA</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="admin.html">������ ������</a></li>
            <li><a href="cabinet.html">�繰�� ����</a></li>
            <li><a href="manage-payment.html">�Ա� ����</a></li>
            <!--li><a href="qna-board.html">������</a></li-->
            <!--li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li-->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>�Ա� ����</h4>
        </div>
        <div class="col-md-6 col-md-offset-3 payment_box0">
          <h5>������ �Ա� ��Ȳ ���</h5>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <div class="row">
            <div class="col-md-3 payment_box2">
              ���̵�
            </div>
            <div class="col-md-3 payment_box2">
              ȸ����
            </div>
            <div class="col-md-3 payment_box2">
              �ݾ�
            </div>
            <div class="col-md-3 payment_box2">
              �Ա� ����
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 payment_box2">
              <div class="list">
               <%
            Vector id = (Vector)request.getAttribute("id");
            for(int i = 0; i <id.size(); i++){ %>
            	<%= id.get(i) %><br>
            <% } %>
              </div>
            </div>
            <div class="col-md-3 payment_box2">
              <div class="list">
              <%
            Vector name = (Vector)request.getAttribute("name");
            for(int i = 0; i <name.size(); i++){ %>
            	<%= name.get(i) %><br>
            <% } %>
              </div>
            </div>
            <div class="col-md-3 payment_box2">
              <div class="list">
              <%
            Vector money = (Vector)request.getAttribute("money");
            for(int i = 0; i <money.size(); i++){ %>
            	<%= money.get(i) %><br>
            <% } %>
              </div>
            </div>
            <form mothod = "POST" action = "Milege_Coin">
            <div class="col-md-3 payment_box2">
            <%
            Vector confirm = (Vector)request.getAttribute("confirm");
            for(int i = 0; i <confirm.size(); i++){ 
            	 if(confirm.get(i).equals("1")){	//�Ա� ��Ȯ��%>
          	<button name = "confirmPayment" value = "<%=id.get(i) %>" class="btn1 btn-default btn-sm" id ="custom_btn">Ȯ��</button>          	 
            	 <%}
            	 else if(confirm.get(i).equals("0")){	//�Ա� Ȯ�� %>
            	     �Ϸ�<br>
             	<%} 
             }%>
            </div>
            </form>
          </div>
        </div>
      </div>
      <div class="row">

      </div>
      </div>
    </div> <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>

</html>