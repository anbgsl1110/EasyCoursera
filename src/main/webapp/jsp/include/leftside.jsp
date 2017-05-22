<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="sidebar-nav" class="sidebar">
    <div class="sidebar-scroll">
        <nav>
            <ul class="nav">
                <li><a id="ec-side-index" href="/"><i class="lnr lnr-home"></i> <span>主界面</span></a></li>
                <li><a id="ec-side-course" href="/course" class=""><i class="lnr lnr-file-empty"></i> <span>课程管理</span></a></li>
                <li><a id="ec-side-examination" href="/examination" class=""><i class="lnr lnr-text-format"></i> <span>题库管理</span></a></li>
                <li><a id="ec-side-tag" href="/tag" class=""><i class="lnr lnr-chart-bars"></i> <span>知识点管理</span></a></li>
                <li><a id="ec-side-qrcode" href="/qrcode" class=""><i class="lnr lnr-dice"></i> <span>二维码管理</span></a></li>
                <li><a href="notifications.html" class=""><i class="lnr lnr-alarm"></i> <span>Notifications</span></a></li>
                <li>
                    <a href="#subPages" data-toggle="collapse" class="collapsed"><i class="lnr lnr-file-empty"></i> <span>Pages</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
                    <div id="subPages" class="collapse ">
                        <ul class="nav">
                            <li><a href="page-profile.html" class="">Profile</a></li>
                            <li><a href="page-login.html" class="">Login</a></li>
                            <li><a href="page-lockscreen.html" class="">Lockscreen</a></li>
                        </ul>
                    </div>
                </li>
                <li><a href="tables.html" class=""><i class="lnr lnr-dice"></i> <span>Tables</span></a></li>
                <li><a href="typography.html" class=""><i class="lnr lnr-text-format"></i> <span>Typography</span></a></li>
                <li><a href="icons.html" class=""><i class="lnr lnr-linearicons"></i> <span>Icons</span></a></li>
            </ul>
        </nav>
    </div>
</div>

<script>
    $('#ec-side-${viewType}').attr("class", "active");
</script>