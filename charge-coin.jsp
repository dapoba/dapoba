<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "java.sql.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<jsp:include page="header.jsp" flush = "true"/>
<script type="text/javascript">
</script>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>코인 충전</h4>
        </div>
      </div>
        <div class="row">
          <div class="col-md-4 col-md-offset-2 select-box optional-border">
            <h6><b>구매하실 코인 수를 선택하세요.</b></h6>
            <form method = "POST" action = "Milege_Coin"><br>
              <input type="radio" name="coin" value="500" id="radio-design" checked = "checked">　500 coin (1,000원)<br>
              <input type="radio" name="coin" value="1000" id="radio-design">　1000 coin (2,000원)<br>
              <input type="radio" name="coin" value="2000" id="radio-design">　2000 coin (4,000원)<br>
              <input type="radio" name="coin" value="4000" id="radio-design">　4000 coin (8,000원)<br>
              <input type="radio" name="coin" value="5000" id="radio-design">　5000 coin (10,000원)<br><br>
          </div>
          <div class="col-md-4 select-box">
            <h6><b>결제 수단을 선택하세요.</b></h6><br>
              <input type="radio" name="pay_method" value="cash" id="radio-design" checked = "checked">　무통장 입금<br>
              <input type="radio" name="pay_method" value="card" id="radio-design">　카드 결제<br><br>
            <input type="submit" class="btn1" value="결제"></input>
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