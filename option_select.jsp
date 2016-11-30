<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>

<html lang="en">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="YoonJiHyun">
    <link rel="icon" href="../../favicon.ico">
    <title>출력옵션선택</title>
    <link href="bootstrap.css" rel="stylesheet">
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="option-select.css" rel="stylesheet">
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>
    <script>
      function rbclick(button){
          alert(button.value+"클릭")
      }
    </script>
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
            <li class="active"><a href="#">홈</a></li>
            <li><a href="fileupload.html">문서함</a></li>
            <li><a href="option-select.html">출력</a></li>
            <li><a href="coin-mileage.html">코인/마일리지</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="user_name"><a>test1님</a></li>
            <li class=""><a href="mypage.html">마이페이지 <span class="sr-only">(current)</span></a></li>
            <li><a href="./">로그아웃</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>출력물 옵션 선택</h4>
        </div>
        <div class="col-md-4 col-md-offset-2">
          <div class="select-box select-place">
          <form action="Option" method="post" enctype="multipart/form-data">
            <div><b>출력장소 선택</b></div>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_place" id="option_new_engineering_building" value="1" checked="checked">
              신공학관
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_place" id="option_hyehwa" value="2">
              혜화관
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_place" id="option_myeongjin" value="3">
              명진관
            </lable>
            <a href="#" class="btn1 btn-custom btn-xs" name="check_button" value="check_loc">가능 확인</a>
          </div>
          <div class="select-box">
          <div><b>색상 선택</b></div>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_color_or_black" id="option_black" value="option_black" checked="checked">
              흑백
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_color_or_black" id="option_color" value="option_color" onclick="rbclick(this)">
              컬러
            </lable>
          </div>
          <div class="select-box">
            <div><b>분할 선택</b></div>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_no" value="option_split_no" checked="checked">
              분할없음
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_2" value="option_split_2">
              2분할
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_split" id="option_split_4" value="option_split_4">
              4분할
            </lable>
          </div>
          <div class="select-box">
            <div><b>표지 선택</b></div>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_cover" id="option_add_cover" value="option_add_cover" >
              표지 추가 O
            </lable>
            <lable class="radio-inline" id="radio-design">
              <input type="radio" name="option_cover" id="option_no_cover" value="option_no_cover" checked="checked">
              표지 추가 X
            </lable>
          </div>
          <div class="select-box">
            <div><b>예약 시간</b></div>
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
            </select>
          </div>
          </form>
        </div>
        <div class="col-md-4 option_preview">
          <div class="upload-box">
            <form class="" action="" method="multipart/form-data">
              <input type="file" id="ex_filename" class="upload-hidden">
            </form>
          </div>
          <input type="submit" href="payment-info.html" class="btn1 btn-custom btn-big" onclick="dataAdd()" value="결제"></a>
        </div>
      </div><!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery.min.js"><\/script>')</script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
    <script>
       function dataAdd(){
         $('#myModal').modal('show');
       }
    </script>
  </body>
</html>