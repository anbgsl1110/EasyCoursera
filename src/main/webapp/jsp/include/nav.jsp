<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="brand" style="margin: 0px;padding: 0">
        <a href="/"><img src="/resources/img/logo.png" alt="简课，让课程更简单" style="height: 80px;padding: 20px 60px;"></a>
    </div>
    <div class="container-fluid">
        <div class="navbar-btn">
            <button type="button" class="btn-toggle-fullwidth"><i class="lnr lnr-arrow-left-circle"></i></button>
        </div>
        <form class="navbar-form navbar-left">
            <div class="input-group">
                <input id="ec-input-search" class="form-control" value="" placeholder="知识点关键词...">
                <span class="input-group-btn"><button id="ec-btn-search" type="button" class="btn btn-primary"
                                                      style="background-color: #323848;border-color: #323848;">搜索</button></span>
            </div>
        </form>
        <div id="navbar-menu">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="/message" class="dropdown-toggle icon-menu">
                        <i class="lnr lnr-alarm"></i>
                        <span id="ec-data-user-message-count" class="badge bg-danger"></span>
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img id="ec-user-avatar"
                                                                                    src="${userAvatar}"
                                                                                    class="img-circle" alt="头像"><span
                            id="ec-user-name">${userName}</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="/user/profile"><i class="lnr lnr-user"></i> <span>个人资料</span></a></li>
                        <li><a href="/logout"><i class="lnr lnr-exit"></i> <span>退出登录</span></a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <script>
        function refreshProfile() {
            var data = getOne("/user/api/account", "GET");
            $("#ec-user-name").text(data.nickname);
            if (data.messageCount > 0) $("#ec-data-user-message-count").text(data.messageCount);
            else $("#ec-data-user-message-count").text("");
            $("#ec-user-avatar").attr("src", data.avatar);
        }

        $('#ec-btn-search').click(function () {
            var val = $("#ec-input-search").val();
            if (val) window.location.href = "/search?keyword=" + encodeURIComponent(val);
        });

//        $(function () {
//            setInterval(function () {
//                refreshProfile();
//            }, 2000);
//        });
    </script>
</nav>

