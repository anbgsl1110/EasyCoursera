<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/include/common.jsp" %>
<html>
<head>
    <title>简课-登录</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
</head>
<body>
<div style="background-color: rgba(0, 0, 0, 0.6);height:100%;width:100%;position:absolute;top:0;left:0;z-index: 10;text-align: center;">
    <div style="
		background-color: #fff;
		width: 300px;
		border-radius: 15px;
		margin: 100px auto;
		font-size: 14px;
		padding: 20px 20px 30px;
">

        <p style="size: 18em">使用「微信扫一扫」安全登录</p>

        <div id="login-qrcode"></div>
    </div>
</div>

<script>
    jQuery('#login-qrcode').qrcode({
        width: 256,
        height: 256,
        text: "${qrcodeContent}"
    });

    var isRequestBack = true;
    var _isQrScaned = function () {
        if (isRequestBack) {
            isRequestBack = false;
            $.get('/api/login/check?token=${token}',
                function (resp) {
                    isRequestBack = true;
                    if (resp.message == 'stop') {
                        clearInterval(scanTimer);
                        alert("请求超时，请刷新后重试")
                    }

                    if (!resp.error) {
                        clearInterval(scanTimer);
                        location.href = '${redirectUrl}';
                    }
                }
            );
        }

    };
    scanTimer = window.setInterval(_isQrScaned, 2000);
</script>
</body>
</html>
