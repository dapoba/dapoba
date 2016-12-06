<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">

    <title>회원가입</title>

    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="sign-up.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <div class="container">
      <form action = "signUp" method="post" enctype="multipart/form-data" class="form-horizontal col-md-6 col-md-offset-3 well sign-in-form-box">
        <fieldset>
          <legend>회원가입</legend>
          아래의 양식을 모두 작성해주세요.<br><br>
          <div class="form-group">
            <label for="inputID" class="col-md-2 control-label">아이디</label>
            <div class="col-md-8">
              <input type="text" class="form-control" name = "id" id="inputID" placeholder="ID" required>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword" class="col-md-2 control-label">비밀번호</label>
            <div class="col-md-8">
              <input type="password" class="form-control" name = "pw"  id="inputPassword" placeholder="Password" required>
            </div>
          </div>
          <div class="form-group">
            <label for="inputName" class="col-md-2 control-label">이름</label>
            <div class="col-md-8">
              <input type="text" class="form-control"  name = "name" id="inputName" placeholder="Name" required>
            </div>
          </div>
          <div class="form-group">
            <label for="inputBirth" class="col-md-2 control-label">생년월일</label>
            <div class="col-md-8">
              <input type="text" class="form-control"  name = "birth" id="inputBirth" placeholder="Birth (주민등록번호 앞 6자리)" required>
            </div>
          </div>
          <div class="form-group">
            <label for="inputEmail" class="col-md-2 control-label">이메일</label>
            <div class="col-md-3 no-right-padding">
              <input type="text" class="form-control"  name = "email1" id="inputEmail1" placeholder="E-mail" required>
            </div>
            <div class="col-md-1 no-padding center-align">
              @
            </div>
            <div class="col-md-4 no-left-padding">
              <input type="text" class="form-control" name = "email2" id="inputEmail2" placeholder=" E-mail" required>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPhone" class="col-md-2 control-label">전화번호</label>
            <div class="col-md-8">
              <input type="text" class="form-control" name = "phone_number" id="inputPhone" placeholder="Phone">
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-8 col-md-offset-2">
              <button type="reset" class="btn btn-default">취소</button>
              <button  class="btn btn-primary" name="check_button" value="sign">가입</button>
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
    <script>
      function radioClicked() {

      switch (this.value) {
          case "one" :
              document.forms[0].elements["intext1"].disabled = false;
              document.forms[0].elements["intext2"].disabled = true;
              document.forms[0].elements["intext3"].disabled = true;
              break;
          case "two" :
              document.forms[0].elements["intext1"].disabled = true;
              document.forms[0].elements["intext2"].disabled = false;
              document.forms[0].elements["intext3"].disabled = true;
              break;
          case "three" :
              document.forms[0].elements["intext1"].disabled = true;
              document.forms[0].elements["intext2"].disabled = true;
              document.forms[0].elements["intext3"].disabled = false;
              break;
      }
  }
    </script>
  </body>
</html>