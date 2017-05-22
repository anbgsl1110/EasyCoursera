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
                <li><a id="ec-side-message" href="/message" class=""><i class="lnr lnr-alarm"></i> <span>消息管理</span></a></li>
                <li><a id="ec-side-answer" href="/answer" class=""><i class="lnr lnr-linearicons"></i> <span>答题批阅</span></a></li>
            </ul>
        </nav>
    </div>
</div>

<script>
    $('#ec-side-${viewType}').attr("class", "active");
</script>