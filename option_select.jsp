<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>��¿ɼǼ���</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="option-select.css" rel="stylesheet">
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
            <li class="active"><a href="#">Ȩ</a></li>
            <li><a href="fileupload.html">������</a></li>
            <li><a href="option-select.html">���</a></li>
            <li><a href="coin-mileage.html">����/���ϸ���</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="user_name"><a>test1��</a></li>
            <li class=""><a href="mypage.html">���������� <span class="sr-only">(current)</span></a></li>
            <li><a href="./">�α׾ƿ�</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<form action="option_select" method="post" enctype="multipart/form-data">
    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>��¹� �ɼ� ����</h4>
        </div>
        <div class="col-md-4 col-md-offset-2">
          <div class="select-box select-place">
            <div><b>������ ����</b></div>
         
            <label class="radio-inline" id="radio-design">
            
              <input type="radio" name="option_place" id="option_new_engineering_building" value="1" checked="checked"/>
              �Ű��а�
            </label>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_place" id="option_hyehwa" value="2"/>
              ��ȭ��
            </label>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_place" id="option_myeongjin" value="3"/>
              ������
            </label>
             <button class="btn btn-primary upload-btn" name="check_button" value="check">����Ȯ��</button>
            
             </div>
      
          <div class="select-box">
          <div><b>���� ����</b></div>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_color_or_black" id="option_black" value="1" checked="checked">
              ���
            </label>
            <label class="radio-inline" id="radio-design" >
              <input type="radio" name="option_color_or_black" id="option_color" value="2" >
              �÷�
            </label>
          </div>
          <div class="select-box">
            <div><b>���� ����</b></div>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_no" value="1" checked="checked">
              ���Ҿ���
            </label>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_2" value="2">
              2����
            </label>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_4" value="4">
              4����
            </label>
          </div>
          <div class="select-box">
            <div><b>ǥ�� ����</b></div>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_cover" id="option_add_cover" value="1" >
              ǥ�� �߰� O
            </label>
            <label class="radio-inline" id="radio-design">
              <input type="radio" name="option_cover" id="option_no_cover" value="0" checked="checked">
              ǥ�� �߰� X
            </label>
          </div>
          <div class="select-box">
            <div><b>���� �ð�</b></div>
            <select class="form-control" name="select">
              <option value="9">9:00 </option>
              <option value="10">10:00 </option>
              <option value="11">11:00 </option>
              <option value="12">12:00 </option>
              <option value="13">13:00 </option>
              <option value="14">14:00 </option>
              <option value="15">15:00 </option>
              <option value="16">16:00 </option>
              <option value="17">17:00 </option>
              <option value="18">18:00 </option>
              <option value="19">19:00 </option>
              <option value="20">20:00 </option>
              <option value="21">21:00 </option>
              <option value="22">22:00 </option>
              <option value="23">23:00 </option>
              <option value="24">24:00 </option>
            </select>
          </div>
           <button class="btn btn-primary upload-btn" name="check_button" value="pay" >��<a href="payment-info.jsp" value="pay">����</a>��</button>
        </div>
         
      </div><!-- /container -->
</div>
</form>
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