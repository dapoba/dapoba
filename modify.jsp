<%@ page language="java" contentType="text/html; charset=EUC-KR" import="java.sql.*"
    pageEncoding="EUC-KR"%>
    
 <% 
		String id =(String)request.getAttribute("userid");
 %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">

    <title>��������</title>

    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="mypage.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <!-- Static navbar -->
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
            <li class="active"><a href="#">Ȩ</a></li>
            <li><a href="fileupload.html">������</a></li>
            <li><a href="#contact">���</a></li>
            <li><a href="#contact">����/���ϸ���</a></li>
            <li><a href="qna-board.html">������</a></li>
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
          <ul class="nav navbar-nav navbar-right">
            <li class="user_name"><a>test1��</a></li>
            <li class=""><a href="mypage.html">���������� <span class="sr-only">(current)</span></a></li>
            <li><a href="./">�α׾ƿ�</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">
      <form action ="modify" class="form-horizontal col-md-6 col-md-offset-3 well form-box">
        <fieldset>
          <legend>���� ����</legend>
          ȸ������ ������ �����Ͻ� �� �ֽ��ϴ�.<br><br>
          <div class="form-group">
            <label for="ID" class="col-md-2 control-label"><b>���̵�</b></label>
            <div class="col-md-8 underline">
              test1
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-md-2 control-label"><b>��й�ȣ</b></label>
            <div class="col-md-8">
              <button type="submit" class="btn-xs btn1">��й�ȣ ����</button>��
            </div>
          </div>
          <div class="form-group">
            <label for="Name" class="col-md-2 control-label">�̸�</label>
            <div class="col-md-8">
              <input type="text" class="underline" name = "name" id="inputName" placeholder="Name">
            </div>
          </div>
          <div class="form-group">
              <label for="inputBirth" class="col-md-2 control-label">�������</label>
              <div class="col-md-8">
                <input type="text" class="underline" name = "Birth" id="inputBirth" placeholder="Birth">
              </div>
            </div>
            <div class="form-group">
              <label for="inputEmail" class="col-md-2 control-label">�̸���</label>
              <div class="col-md-3 no-right-padding">
                <input type="text" class="underline" name = "Email1" id="inputEmail1" placeholder="E-mail">
              </div>
              <div class="col-md-1 no-padding center-align">
                @
              </div>
              <div class="col-md-4 no-left-padding">
                <input type="text" class="underline" name = "Email2" id="inputEmail2" placeholder=" E-mail">
              </div>
            </div>
            <div class="form-group">
              <label for="inputPhone" class="col-md-2 control-label">��ȭ��ȣ</label>
              <div class="col-md-8">
                <input type="text" class="underline" name = "Phone_number" id="inputPhone" placeholder="Phone">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-8 col-md-offset-2">
              <button type="submit" class="btn-xs btn1">��������</button>��
            </div>
          </div>
        </fieldset>
      </form>
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

</body>
</html>