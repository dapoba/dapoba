<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head lang = "en">
    <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>카드 결제</h4>
        </div>
      </div>
        <form method = "POST" action = "Milege_Coin" class="form-horizontal col-md-6 col-md-offset-3 well select-box">
          <fieldset>
            <div class="form-group">
              <label for="inputID" class="col-md-2 control-label"><b>카드명</b></label>
              <div class="col-md-9">
                <select name = "cardBank" class="form-control" id="select" required>
                  <option value = "shinhan">신한카드</option>
                  <option value = "kukmin">국민카드</option>
                  <option value = "woori">우리카드</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label for="inputCardNumber" class="col-md-2 control-label"><b>카드번호</b></label>
              <div class="col-md-9">
                <input name = "cardNo" type="text" class="form-control" id="inputCardNumber" placeholder="Card Number" required>
              </div>
            </div>
            <div class="form-group">
              <label for="inputPassword" class="col-md-2 control-label"><b>비밀번호</b></label>
              <div class="col-md-9">
                <input name = "cardPW" type="password" class="form-control" id="inputPassword" placeholder="Password" required>
              </div>
            </div>
            <div class="form-group">
              <label for="inputValidDate" class="col-md-2 control-label"><b>유효기간</b></label>
              <div class="col-md-9">
                <input name = "cardDate" type="text" class="form-control" id="inputValidDate" placeholder="Valid Date" required>
              </div>
              <div class="row">
                <div class="col-md-offset-2 col-md-8 margin-custom"><span class="help-block">　(MONTH/YEAR 순으로 네자리 숫자를 연속하여 입력)</span></div>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8 col-md-offset-2">
                <input type="submit" class="btn1" value="입력 완료"></input>
              </div>
            </div>
          </fieldset>
        </form>
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