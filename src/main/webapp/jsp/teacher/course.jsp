<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/include/common.jsp" %>
<%@include file="/jsp/include/header.jsp" %>
<body>
<div id="ec-modal" class="modal fade" tabindex="-1" role="dialog" data-ec-id="0">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="ec-modal-close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 id="ec-modal-title" class="modal-title">发布课程</h4>
            </div>
            <div class="modal-body">
                <form id="ec-modal-form" class="form-inline">
                    <label for="ec-course-input-name">课程名称：</label>
                    <input type="text" id="ec-course-input-name" name="name">
                    <br><br>

                    <label>需要审核：</label>
                    <input class="input-checkbox" type="checkbox" name="needCheck" value="true"/>
                    <br><br>

                    <label for="ec-course-input-content">课程内容：</label>
                    <textarea class="form-control" id="ec-course-input-content" rows="10" cols="70"
                              name="content"></textarea>
                    <button id="ec-modal-save" style="display: none"></button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitModal()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<button id="ec-modal-open" type="button" style="display: none" class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#ec-modal">
</button>

<div id="wrapper">
    <%@include file="/jsp/include/nav.jsp" %>
    <%@include file="/jsp/include/leftside.jsp" %>


    <div class="main">
        <div class="main-content">
            <div class="container-fluid">

                <%--MAIN--%>
                <div id="toolbar" class="btn-group">
                    <button id="ec-modal-add-btn" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                </div>

                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">我发布的课程</h3>
                    </div>
                    <div class="panel-body">
                        <table id="ec-teacher-table">
                        </table>
                    </div>
                </div>

                <script>
                    function tableDataQuery(param) {
                        return {
                            size: param.limit,
                            page: ((param.offset / param.limit) + 1),
                            creator: thisUserId
                        }
                    }

                    var columns = [{
                        field: 'id',
                        title: '课程 ID'
                    }, {
                        field: 'name',
                        title: '课程名称'
                    }, {
                        field: 'chooseCount',
                        title: '已选人数'
                    }, {
                        field: 'needCheck',
                        title: '是否需要审核',
                        formatter:formatterBoolean
                    }, {
                        field: 'createTime',
                        title: '创建时间',
                        formatter:formatterDate
                    }];

                </script>

            </div>
        </div>
    </div>
</body>
</html>
