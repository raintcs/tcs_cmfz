<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    $(function () {
        $("#userList").jqGrid({
            url: "${pageContext.request.contextPath}/user/getAll",
            datatype: "json",
            colNames: ["id", "姓名", "密码", "手机号", "法号", "性别", "状态", "创建时间", "头像"],
            colModel: [
                {name: "id", hidden: true},
                {name: "name"},
                {name: "password", hidden: true},
                {name: "phone"},
                {name: "dharma", editable: true},
                {name: "sex", edittype: "select", editoptions: {value: "男:男;女:女"}},
                {name: "status", editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}},
                {name: "createDate"},
                {
                    name: "icon",
                    editable: true, edittype: "file",
                    formatter: function (cellvalues, options, rowObject) {
                        return "<img style='height:90px;width:120px' src='${pageContext.request.contextPath}/imgUser/" + cellvalues + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "50%",
            pager: "#userPager",
            viewrecords: true,
            page: "1",
            rowNum: "2",
            rowList: [2, 4, 6],
            //用来控制是否显示checkbox
            multiselect: true

        }).jqGrid("navGrid", "#userPager",
            {
                //这个模块修改增删查按钮的状态等信息
                search: false,
                del: false,
            },
            { //执行修改时进行的操作


            },
            {//执行添加时进行额外操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/upload",
                        fileElementId: "imgUser",
                        //向后台传递的参数
                        data: {userId: userId},
                        success: function () {
                            $("#userList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {//删除时。。。。

            }
        )
    });

    //导出数据
    function outData() {
        location.href = "${path}/user/exportAll";
    }

    function fenbu() {
        location.href = "${path}/echarts/echarts_user.jsp";
    }

    function bianhu() {
        location.href = "${path}/echarts/echarts_userRegistCount.jsp";
    }
</script>

<div class="page-header">
    <h3>用户管理</h3>
</div>
<%--按钮组--%>
<div>
    <button onclick="outData()" type="button" class="btn btn-primary dropdown-toggle btn-sm">
        导出数据 &nbsp;<span class="glyphicon glyphicon-log-out"></span>
    </button>
    &nbsp;&nbsp;
    <button onclick="fenbu()" type="button" class="btn btn-primary dropdown-toggle btn-sm">
        用户分布 &nbsp;<span class="glyphicon glyphicon-globe"></span>
    </button>
    &nbsp;
    <button onclick="bianhu()" type="button" class="btn btn-primary dropdown-toggle btn-sm">
        七天注册变化 &nbsp;<span class="glyphicon glyphicon-check"></span>
    </button>
</div>
<br>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">用户信息</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="userList" style="text-align: center"></table>
            <div style="height: 60px" id="userPager"></div>
        </div>

    </div>
</div>
