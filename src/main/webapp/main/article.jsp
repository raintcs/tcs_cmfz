<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<script>
    $(function () {
        $("#articleList").jqGrid({
            url: "${pageContext.request.contextPath}/article/getAll",
            datatype: "json",
            colNames: ["id", "标题", "作者", "状态", "内容", "上传时间", "发行时间", "操作"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "author", hidden: true},
                {name: "status", editable: true, edittype: "select", editoptions: {value: "展示:展示;不展示:不展示"}},
                {name: "content", hidden: true},
                {name: "uploadDate"},
                {name: "publishDate"},
                {
                    name: "",
                    formatter: function (cellvalues, options, rowObject) {
                        console.log(rowObject);
                        return "&nbsp;&nbsp;&nbsp;&nbsp;<a  href='#'><span class='glyphicon glyphicon-th-list' onclick=\"viewDetails('" + rowObject.id + "')\"></span></a>" + "      " +
                            "<a href='#'><span class='glyphicon glyphicon-pencil'></span></a>";
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "60%",
            pager: "#articlePager",
            viewrecords: true,
            page: "1",
            rowNum: "2",
            rowList: [2, 4, 6],
            //用来控制是否显示checkbox
            multiselect: true
        })
    });

    /*点击"添加文章"触发的事件*/
    function showModel() {
        //展示模态框
        $("#myModal").modal("show");
        //清空表单中的数据
        $("#addArticleFrom")[0].reset();
        //清空隐藏的id值
        $("#articleId").val("");
        //通过发送的ajax请求动态的添加按钮
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"addArticle()\">添加</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        )

        //初始化kindeditor富文本编辑器
        KindEditor.create('#editor',
            {
                //指定上传文件的服务器端程序
                uploadJson: "${path}/kindeditor/upload",
                //指定浏览远程图片的服务器端程序
                fileManagerJson: "${path}/kindeditor/allImages",
                //true时显示浏览远程服务器按钮
                allowFileManager: true,
                //指定上传文件form名称
                filePostName: "imgFile",
                //编辑器失去焦点(blur)时执行的回调函数,将kindeditor中的内容同步到表单的content中
                afterBlur: function () {
                    this.sync();
                }
            }
        );
        //清空kindeditor中的数据
        KindEditor.html("#editor", "")
    }

    /*添加文章*/
    function addArticle() {
        //发ajax
        $.ajax({
            url: "${path}/article/add",
            datatype: "json",
            type: "post",
            //表单中的参数通过序列化传递
            data: $("#addArticleFrom").serialize(),
            //当点击"添加"按钮后,隐藏模态框
            success: function (data) {
                $("#myModal").modal("toggle")
            }
        })
    }

    /*查看文章详情信息*/
    function viewDetails(id) {
        //模态框中信息的回显
        //展示模态框
        $("#myModal").modal("show");
        //根据当前行的id,得到该行所有信息
        var value = $("#articleList").jqGrid("getRowData", id);
        //给input框赋值
        $("#articleId").val(value.id);
        $("#title").val(value.title);
        $("#author").val(value.author);
        $("#status").val(value.status);
        //创建按钮
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\">修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        )
        //初始化
        KindEditor.create('#editor',
            {
                //指定上传文件的服务器端程序
                uploadJson: "${path}/kindeditor/upload",
                //指定浏览远程图片的服务器端程序
                fileManagerJson: "${path}/kindeditor/allImages",
                //true时显示浏览远程服务器按钮
                allowFileManager: true,
                //指定上传文件form名称
                filePostName: "imgFile",
                //编辑器失去焦点(blur)时执行的回调函数,将kindeditor中的内容同步到表单的content中
                afterBlur: function () {
                    this.sync();
                }
            }
        );
        //先清空kindeditor中的数据
        KindEditor.html("#editor", "")
        //将内容信息展示在kindeditor中
        KindEditor.appendHtml("#editor", value.content);
    }

    /*修改*/
    function updateArticle() {
        $.ajax({
            url: "${path}/article/update",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function (data) {
                alert("确认修改吗?");
                $("#myModal").modal("toggle");
            }

        })
    }

</script>

<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">文章信息</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab"
                                   onclick="showModel()">添加文章</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="articleList" class="table table-hover table-condensed" style="text-align: center"></table>
            <div style="height: 50px" id="articlePager"></div>
        </div>
        <%--        <div role="tabpanel" class="tab-pane" id="profile">

                </div>--%>
    </div>

    <%--========================================================================================--%>
    <%--自定义模态框--%>
    <div class="modal fade" id="myModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content" style="width:750px">
                <!--模态框标题-->
                <div class="modal-header">
                    <!--
                        用来关闭模态框的属性:data-dismiss="modal"
                    -->
                    <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                    <h4 class="modal-title">编辑用户信息</h4>
                </div>

                <!--模态框内容体-->
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/article/editArticle"
                          class="form-horizontal" id="addArticleFrom">

                        <%--id--%>
                        <div class="form-group">
                            <div class="col-sm-5">
                                <input type="hidden" name="id" id="articleId" class="form-control">
                            </div>
                        </div>
                        <%--标题--%>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">标题</label>
                            <div class="col-sm-5">
                                <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                            </div>
                        </div>
                        <%--作者--%>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">作者</label>
                            <div class="col-sm-5">
                                <input type="text" name="author" id="author" placeholder="请输入作者" class="form-control">
                            </div>
                        </div>
                        <%--状态--%>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">状态</label>
                            <div class="col-sm-5">
                                <select class="form-control" name="status" id="status">
                                    <option value="展示">展示</option>
                                    <option value="不展示">不展示</option>
                                </select>
                            </div>
                        </div>
                        <%--kindeditor--%>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                            </div>
                        </div>
                        <input id="addInsertImg" name="insertImg" hidden>
                    </form>
                </div>
                <!--模态页脚-->
                <div class="modal-footer" id="modal_footer">
                    <%--根据发送的ajax请求动态的创建按钮--%>
                </div>
            </div>
        </div>
    </div>
    <%--========================================================================================--%>

</div>





