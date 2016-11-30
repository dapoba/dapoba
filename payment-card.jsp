<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head lang = "en">
    <jsp:include page="header.jsp" flush = "true"/>

    <div class="container">
      <div class="row">
        <div class="col-md-10 col-md-offset-2">
          <h4>ī�� ����</h4>
        </div>
      </div>
        <form method = "POST" action = "Milege_Coin" class="form-horizontal col-md-6 col-md-offset-3 well select-box">
          <fieldset>
            <div class="form-group">
              <label for="inputID" class="col-md-2 control-label"><b>ī���</b></label>
              <div class="col-md-9">
                <select name = "cardBank" class="form-control" id="select" required>
                  <option value = "shinhan">����ī��</option>
                  <option value = "kukmin">����ī��</option>
                  <option value = "woori">�츮ī��</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label for="inputCardNumber" class="col-md-2 control-label"><b>ī���ȣ</b></label>
              <div class="col-md-9">
                <input name = "cardNo" type="text" class="form-control" id="inputCardNumber" placeholder="Card Number" required>
              </div>
            </div>
            <div class="form-group">
              <label for="inputPassword" class="col-md-2 control-label"><b>��й�ȣ</b></label>
              <div class="col-md-9">
                <input name = "cardPW" type="password" class="form-control" id="inputPassword" placeholder="Password" required>
              </div>
            </div>
            <div class="form-group">
              <label for="inputValidDate" class="col-md-2 control-label"><b>��ȿ�Ⱓ</b></label>
              <div class="col-md-9">
                <input name = "cardDate" type="text" class="form-control" id="inputValidDate" placeholder="Valid Date" required>
              </div>
              <div class="row">
                <div class="col-md-offset-2 col-md-8 margin-custom"><span class="help-block">��(MONTH/YEAR ������ ���ڸ� ���ڸ� �����Ͽ� �Է�)</span></div>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-8 col-md-offset-2">
                <input type="submit" class="btn1" value="�Է� �Ϸ�"></input>
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