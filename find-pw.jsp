<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>���̵� ã��</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="find.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>��й�ȣ ã��</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 col-md-offset-3 input-box2">
          <h6><b>���̵�� �̸�, �޴�����ȣ�� �Է����ּ���.</b></h6>
          <form action ="pw_Find" id="opt_margin"><br><br>
            <div class="form-group">
              <label for="inputID" class="col-md-3">���̵�</label>
              <input type="text" name = "ID" id="inputID" class="col-md-6" required></input>����
            </div><br>
            <div class="form-group">
              <label for="inputName" class="col-md-3">�̸�</label>
              <input type="text" name = "name" id="inputName" class="col-md-6" required></input>����
            </div><br><br>
            <div class="form-group">
              <label for="inputPhone" class="col-md-3">�޴�����ȣ</label>
              <input type="text" name = "phone_number" id="inputPhone" class="col-md-6" required></input>
              <button type="submit" class="btn1" id="custom_btn">�Է� �Ϸ�</button>

            </div>
          </form>
        </div>
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