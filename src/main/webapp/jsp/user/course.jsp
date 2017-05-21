<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/jsp/include/common.jsp" %>
<%@include file="/jsp/include/header.jsp" %>
<body>

<button id="ec-modal-open" type="button" style="display: none" class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#ec-modal">
</button>

<div id="wrapper">
    <%@include file="/jsp/include/nav.jsp" %>
    <%@include file="/jsp/include/leftside.jsp" %>
    <link rel="stylesheet" href="/resources/dist/bootstrap-table.css">
    <script src="/resources/dist/bootstrap-table.js"></script>
    <script src="/resources/dist/locale/bootstrap-table-zh-CN.js"></script>


    <div class="main">
        <div class="main-content">
            <div class="container-fluid">

                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">我选择的课程</h3>
                    </div>
                    <div class="panel-body">
                        <table id="ec-user-table">
                        </table>
                    </div>
                </div>

                <script>

                    $(document).ready(function () {
                        $('#ec-teacher-table').bootstrapTable({
                            url: '/user/api/course/select/list',
                            pagination: true,
                            sidePagination: "server",
                            showRefresh: true,
                            responseHandler: tableDataSerialize,
                            queryParams: tableDataQuery,
                            pageNumber: 1,
                            pageSize: 10,
                            columns: columns
                        });
                    });

                    function tableDataQuery(param) {
                        return {
                            size: param.limit,
                            page: ((param.offset / param.limit) + 1),
                            user: thisUserId
                        }
                    }

                    var columns = [{
                        checkbox: true
                    }, {
                        field: 'id',
                        title: '课程 ID'
                    }, {
                        field: 'name',
                        title: '课程名称'
                    }];

                </script>

            </div>
        </div>
    </div>
</body>
</html>
