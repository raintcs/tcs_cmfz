<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script>
        $(function () {
            //验证用户名
            $("#form-username").blur(function () {
                //获取当前输入框的值
                var username = $(this).val();
                if (username == "") {
                    $("#usernameMess").html("<font color='red' size='2'>&nbsp;&nbsp;用户名不能为空</font>");
                } else {
                    $("#usernameMess").html("");
                }
            });

            //验证密码
            $("#form-password").blur(function () {
                //获取当前输入框的值
                var password = $(this).val();
                if (password == "") {
                    $("#passwordMess").html("<font color='red'size='2'>&nbsp;&nbsp;密码不能为空</font>");
                } else {
                    if (password.length < 6) {
                        $("#passwordMess").html("<font color='red'size='2'>&nbsp;&nbsp;密码长度不能小于6位</font>");
                    } else {
                        $("#passwordMess").html("<font color='green'>√</font>");

                    }
                }
            });

            /***************************验证手机号******************************/

            $("#form-phoneNumber").blur(function () {
                var phoneNumber = $(this).val();
                if (phoneNumber == "") {
                    $("#phoneNumberMess").html("<font color='red'size='2'>&nbsp;&nbsp;手机号不能为空</font>")
                } else {
                    if (phoneNumber.length < 11 && phoneNumber > 11) {
                        $("#phoneNumberMess").html("<font color='red'size='2'>&nbsp;&nbsp;手机号不合法</font>");
                    } else {
                        $("#passwordMess").html("<font color='green'>√</font>");
                    }
                }
            })

            $("#verify_refresh").click(function (that) {
                // 设置按钮倒计时

                //
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/sendCode?phoneNumber=" + $("#form-phoneNumber").val(),
                    datatype: "json",
                    type: "get",
                    success: function (data) {
                        console.log(data)

                    },
                })
            })

            /********************************************************/
            //验证验证码
            $("#form-code").blur(function () {
                //获取当前输入框的值
                var code = $(this).val();
                if (code == "") {
                    $("#codeMess").html("<font color='red' size='2'>&nbsp;&nbsp;验证码不能为空</font>");
                } else {
                    if (code.length != 6) {
                        $("#codeMess").html("<font color='red' size='2'>&nbsp;&nbsp;验证码必须为6位</font>");
                    } else {
                        $("#codeMess").html("<font color='green'>√</font>");
                    }
                }
            });


            /*
                       //$("#captchaImage").click(function () {
                          // $("#captchaImage").prop("src","/code/getCode?t="+new Date().getTime());
                       //});*/
            /*           $.extend($.validator.messages, {
                           required: "<span style='color: red' size='2'>这是必填字段</span>"
                       })*/

            $("#loginButtonId").click(function () {
                var flag = $("#loginForm").valid();
                if (flag) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/login",
                        datatype: "json",
                        type: "post",
                        data: $("#loginForm").serialize(),
                        success: function (data) {
                            if (data.message == "loginSuccess") {
                                location.href = "${pageContext.request.contextPath}/main/main.jsp";
                            } else {
                                $("#msgDiv").html(data.message);
                            }
                        }
                    })
                }
            })

        })

        /* var wait = 120; // 短信验证码120秒后才可获取下一个
         function setButtonStatus(that) {
             if (wait == 0) {
                 that.removeAttribute("disabled");
                 that.value="免费获取验证码";
                 wait = 60;
             } else {
                 that.setAttribute("disabled",true);
                 that.value=wait+"秒后可以重新发送";
                 wait--;
                 setTimeout(function() {
                     setButtonStatus(that)
                 }, 1000)
             }

         }
 */
        /**********************************************/


    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span style="color: red" id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input required type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                                <p id="usernameMess"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input required type="password" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                                <p id="passwordMess"/>
                            </div>
                            <%----%>
                            <div class="form-group">
                                <label class="sr-only" for="form-phoneNumber">手机号</label>
                                <input required type="text" name="phoneNumber" placeholder="请输入手机号..."
                                       class="form-phoneNumber form-control" id="form-phoneNumber">
                                <p id="phoneNumberMess"/>
                            </div>

                            <%--=================================--%>
                            <div class="row">
                                <div class="col-xs-6 pull_left">
                                    <div class="form-group">
                                        <input required class="form-control" name="enCode" id="form-code"
                                               placeholder="请输入验证码">
                                    </div>
                                </div>
                                <div class="col-xs-6 pull_center">
                                    <div class="form-group">
                                        <input type="button" class="btn btn-block btn-flat" id="verify_refresh"
                                               value="免费获取验证码">
                                    </div>
                                </div>
                                <p id="codeMess"/>
                            </div>
                            <%----%>
                            <%--<div class="form-group">
                                &lt;%&ndash;<img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/code/getCode">&ndash;%&gt;
                                &lt;%&ndash;<input required style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="enCode" id="form-code"  >&ndash;%&gt;
                                <input required type="text" name="enCode" placeholder="请输入手机验证码..."
                                       class="form-enCode form-control" id="form-code">
                                <p id="codeMess"/>
                            </div>--%>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>