<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>DAPOBA LOGIN</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="login.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <div class="container">
      <form action ="Log_In" id="opt_margin">
        <h2 class="form-login-heading">
          DAPOBA
          <img src="img/icon/printer-1.png" class="main-logo" width="70px" height="70px">
        </h2>
        <label for="inputID" class="sr-only">ID</label>
        <input type="text" name="id" id="inputID" class="form-control" placeholder="ID" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="pw" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> ID/Password 기억하기
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
        <button class="btn not-hover" type="button" onClick="goReplace(find.jsp)">ID/Password 찾기</button>
        <button class="btn not-hover" type="button" onClick="goReplace('Sign-in.jsp')">회원가입</button>
        
      </form>
    </div> <!-- /container -->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>