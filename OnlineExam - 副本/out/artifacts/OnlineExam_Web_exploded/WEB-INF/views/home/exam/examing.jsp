<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>${title}</title>
<link href="../../resources/home/exam/css/main.css" rel="stylesheet" type="text/css" />
<link href="../../resources/home/exam/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="../../resources/home/exam/css/test.css" rel="stylesheet" type="text/css" />

<style>
.hasBeenAnswer {
    width: 30px;
    height: 30px;
	background: #5d9cec;
	color:#fff;
}

.top{
  margin-top:20px;
}


</style>

</head>
<body>


<div class="main">
<%--    position:fixed;--%>
    <div id="outerdiv" style="position:fixed; top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">

        <div id="innerdiv"  align="center" >
            <img id="bigimg" style="border:5px solid #fff;" onmousewheel="mouseroll()" onmousedown="mousedown()"  class="top" src="" />
        </div>
    </div>

        <!--nr start-->
	<div class="test_main">
		<div class="nr_left">
			<div class="test">
				<form action="" method="post">
					<div class="test_title">
						<p class="test_time">
							<img style="float:left;margin-top:15px;margin-left:10px;" src="../../resources/home/exam/images/time.png" width="16px"><b class="alt-1">${hour }:${minitute }:${second }</b>
						</p>
						<span><input type="button" onclick="submitExam()" name="test_jiaojuan" value="交卷"></span>
					</div>
						<c:if test="${exam.singleQuestionNum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.singleQuestionNum}</i><span>题，</span><span>合计</span><i class="content_fs">${singleScore * exam.singleQuestionNum}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${singleQuestionList}" var="sq" varStatus="sqids">
									<li id="qu_${singleQuestion }_${sq.question.id}" data-key="${sq.id}">
<%--								题号，分值，题干               --%>
										<div class="test_content_nr_tt">
											<i>${sqids.index+1}</i><span>(${sq.question.score}分)</span>
											<span>${sq.question.title }</span>
											<br>
												<%--		 	  单选题习题图片               --%>
											<div  align="center">
                                                <img id="test-singleQuestion-photo"   width=60%  src="${sq.question.photo}" class="pic"  />
                                            </div>

										</div>
    

<%--								选项                   --%>
										<div class="test_content_nr_main">
											<ul>
         <%--												 选项A         --%>
													<li class="option" data-type="single" data-value="A">
														
															<input type="radio" class="radioOrCheck" name="answer${sq.id}"
																id="${singleQuestion }_answer_${sq.id}_option_1"
															/>

														<label for="${singleQuestion }_answer_${sq.id}_option_1">
															A.
															<p class="ue" style="display: inline;">${sq.question.attrA }</p>
														</label>
													</li>
		<%--												 选项B--%>
													<li class="option" data-type="single" data-value="B">
														
															<input type="radio" class="radioOrCheck" name="answer${sq.id}"
																id="${singleQuestion }_answer_${sq.id}_option_2"
															/>
														
														
														<label for="${singleQuestion }_answer_${sq.id}_option_2">
															B.
															<p class="ue" style="display: inline;">${sq.question.attrB }</p>
														</label>
													</li>


												 <c:if test="${not empty sq.question.attrC}">
													<li class="option" data-type="single" data-value="C">
														
															<input type="radio" class="radioOrCheck" name="answer${sq.id}"
																id="${singleQuestion }_answer_${sq.id}_option_3"
															/>

														<label for="${singleQuestion }_answer_${sq.id}_option_3">
															C.
															<p class="ue" style="display: inline;">${sq.question.attrC }</p>
														</label>
													</li>
												</c:if>

												 <c:if test="${not empty sq.question.attrD}">
													<li class="option" data-type="single" data-value="D">
														
															<input type="radio" class="radioOrCheck" name="answer${sq.id}"
																id="${singleQuestion }_answer_${sq.id}_option_4"
															/>
														<label for="${singleQuestion }_answer_${sq.id}_option_4">
															D.
															<p class="ue" style="display: inline;">${sq.question.attrD }</p>
														</label>
													</li>
												 </c:if>
												
											</ul>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>

						<c:if test="${exam.muiltQuestionNum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.muiltQuestionNum }</i><span>题，</span><span>合计</span><i class="content_fs">${exam.muiltQuestionNum * muiltScore}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${muiltQuestionList}" var="mq" varStatus="mqids">
									<li id="qu_${muiltQuestion }_${mq.question.id}" data-key="${mq.id}">
										<div class="test_content_nr_tt">
											<i>${mqids.index + exam.singleQuestionNum + 1 }</i><span>(${mq.question.score}分)</span><span>${mq.question.title }</span>
										</div>

											<%--								习题图片               --%>
										<br>
										<div align="center" ><img id="test-muiltQuestion-photo"  width=60%  src="${mq.question.photo}"  class="pic" ></div>

										<div class="test_content_nr_main">
											<ul>
												
													<li class="option" data-type="muilt" data-value="A">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${muiltQuestion }_answer_${mq.id}_option_1" value="A"
															/>
														
														<label for="${muiltQuestion }_answer_${mq.id}_option_1">
															A.
															<p class="ue" style="display: inline;">${mq.question.attrA}</p>
														</label>
													</li>
												
													<li class="option" data-type="muilt" data-value="B">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${muiltQuestion }_answer_${mq.id}_option_2" value="B"
															/>
														
														<label for="${muiltQuestion }_answer_${mq.id}_option_2">
															B.
															<p class="ue" style="display: inline;">${mq.question.attrB}</p>
														</label>
													</li>
												<c:if test="${not empty mq.question.attrC}">
													<li class="option" data-type="muilt" data-value="C">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${muiltQuestion }_answer_${mq.id}_option_3" value="C"
															/>
														
														<label for="${muiltQuestion }_answer_${mq.id}_option_3">
															C.
															<p class="ue" style="display: inline;">${mq.question.attrC}</p>
														</label>
													</li>
												</c:if>

												<c:if test="${not empty mq.question.attrD}">
													<li class="option" data-type="muilt" data-value="D">
														
														
															<input type="checkbox" class="radioOrCheck" name="answer1"
																id="${muiltQuestion }_answer_${mq.id}_option_4" value="D"
															/>
														
														<label for="${muiltQuestion }_answer_${mq.id}_option_4">
															D.
															<p class="ue" style="display: inline;">${mq.question.attrD}</p>
														</label>

													</li>
												</c:if>
												
											</ul>
										</div>
									</li>
									</c:forEach>
							</ul>
						</div>
					</c:if>
					
					<c:if test="${exam.chargeQuestionNum !=0 }">
						<div class="test_content">
							<div class="test_content_title">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.chargeQuestionNum }</i><span>题，</span><span>合计</span><i class="content_fs">${chargeScore * exam.chargeQuestionNum}</i><span>分</span>
								</p>
							</div>
						</div>
						<div class="test_content_nr">
							<ul>
								<c:forEach items="${chargeQuestionList}" var="cq" varStatus="cqids">
									<li id="qu_${chargeQuestion }_${cq.question.id}" data-key="${cq.id}">
										<div class="test_content_nr_tt">
											<i>${cqids.index+exam.singleQuestionNum+exam.muiltQuestionNum+1}</i><span>(${cq.question.score}分)</span><font>${cq.question.title }</font>
										</div>
											<%--								习题图片               --%>
										<br>
										<div align="center" ><img id="test-chargeQuestion-photo"  width=60%  src="${cq.question.photo}"  class="pic" ></div>

										<div class="test_content_nr_main">
											<ul>
												
													<li class="option" data-type="charge" data-value="A">
															<input type="radio" class="radioOrCheck" name="answer${cq.id}"
																id="${chargeQuestion }_answer_${cq.id}_option_1"
															/>
														<label for="${chargeQuestion }_answer_${cq.id}_option_1">
															A.
															<p class="ue" style="display: inline;">${cq.question.attrA }</p>
														</label>
													</li>
												
													<li class="option" data-type="charge" data-value="B">
															<input type="radio" class="radioOrCheck" name="answer${cq.id}"
																id="${chargeQuestion }_answer_${cq.id}_option_2"
															/>
														<label for="${chargeQuestion }_answer_${cq.id}_option_2">
															B.
															<p class="ue" style="display: inline;">${cq.question.attrB }</p>
														</label>
													</li>

											</ul>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					
				</form>
			</div>

		</div>
		<div class="nr_right">
			<div class="nr_rt_main">
				<div class="rt_nr1">
					<div class="rt_nr1_title">
						<h1>
							<span style="font-size:18px;">答题卡</span>
						</h1>
						<p class="test_time">
							<img style="float:left;margin-top:15px;margin-left:10px;" src="../../resources/home/exam/images/time.png" width="16px">
							<b class="alt-1">${hour }:${minitute }:${second }</b>
						</p>
					</div>

<%--					单选             --%>
					<c:if test="${exam.singleQuestionNum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>单选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.singleQuestionNum }</i><span>题</span>
								</p>
							</div>

							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${singleQuestionList}" var="ssq" varStatus="ssqids">
										<li><a href="#qu_${singleQuestion}_${ssq.question.id}">${ssqids.index + 1 }</a></li>
									</c:forEach>
								</ul>
							</div>

						</div>
					</c:if>

<%--					多选           --%>
						<c:if test="${exam.muiltQuestionNum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>多选题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.muiltQuestionNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${muiltQuestionList}" var="mmq" varStatus="mmqids">
										<li><a href="#qu_${muiltQuestion }_${mmq.question.id }">${mqids.index + exam.singleQuestionNum + 1 }</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>

<%--					判断              --%>
					<c:if test="${exam.chargeQuestionNum !=0 }">
						<div class="rt_content">
							<div class="rt_content_tt">
								<h2>判断题</h2>
								<p>
									<span>共</span><i class="content_lit">${exam.chargeQuestionNum }</i><span>题</span>
								</p>
							</div>
							<div class="rt_content_nr answerSheet">
								<ul>
									<c:forEach items="${chargeQuestionList}" var="ccq" varStatus="ccqids">
										<li><a href="#qu_${chargeQuestion}_${ccq.question.id}">${ccqids.index+exam.singleQuestionNum+exam.muiltQuestionNum+1}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>



				</div>

			</div>
		</div>
	</div>
	<!--nr end-->
	<div class="foot">
        
    </div>
</div>

<script src="../../resources/home/exam/js/jquery-1.11.3.min.js"></script>
<script src="../../resources/home/exam/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="../../resources/home/exam/js/jquery.countdown.js"></script>


<script>
    function mousedown() {
        var div1 = document.getElementById("bigimg");
        document.onmousedown = function (event) {
            pos(div1, -div1.offsetWidth/2, -div1.offsetHeight/2, event,true);
            // $("#bigimg").fadeIn("fast");
        }
    }

    function mouseroll() {
        document.onmousewheel=function (event) {
            var div1 = document.getElementById("bigimg");
            roll(div1,event);
            pos(div1, -div1.offsetWidth*1.1/2, -div1.offsetHeight*1.1/2, event,true);

        }
    }

    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){
            $("#bigimg").fadeOut("fast");
            $("#outerdiv").fadeOut("fast");
        }
    };

    function mouseup() {
    }
</script>



<script>
	window.jQuery(function($) {
		"use strict";
		
		$('time').countDown({
			with_separators : false
		});
		$('.alt-1').countDown({
			css_class : 'countdown-alt-1'
		});
		$('.alt-2').countDown({
			css_class : 'countdown-alt-2'
		});
		$('.alt-3').countDown({
			css_class : 'countdown-alt-3'
		});
	});

    var pos = function (o, x, y, event,flag) {  //鼠标定位赋值函数
        if(flag===true){
        var posX = 0, posY = 0;  //临时变量值
        var e = event || window.event;  //标准化事件对象
        if (e.pageX || e.pageY) {  //获取鼠标指针的当前坐标值
            posX = e.pageX;
            posY = e.pageY;
        } else if (e.clientX || e.clientY) {
            posX = event.clientX + document.documentElement.scrollLeft + document.body.scrollLeft;
            posY = event.clientY + document.documentElement.scrollTop + document.body.scrollTop;
            event.up
        }
        o.style.position = "absolute";  //定义当前对象为绝对定位
        o.style.top = (posY + y) + "px";  //用鼠标指针的y轴坐标和传入偏移值设置对象y轴坐标
        o.style.left = (posX + x) + "px";  //用鼠标指针的x轴坐标和传入偏移值设置对象x轴坐标
        }
    }

	$(function() {

		//点击图片放大
		$(".pic").click(function(){

			var _this = $(this);//将当前的pimg元素作为_this传入函数
			imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
			// roll(#bigimg", _this);
		});


		$('li.option input').click(function() {
			var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
			var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
			// 设置已答题
			if(!cardLi.hasClass('hasBeenAnswer')){
				cardLi.addClass('hasBeenAnswer');
			}
			var examArr = examId.split('_');
			var questionId = examArr[2];
			var submitExamId = ${exam.id};
			var examPaperId = ${examPaper.id};
			var examPaperAnswerId = $("#"+examId).attr("data-key");
			//console.log(questionId+'--'+submitExamId+'--'+examPaperId);
			var selectedAnswer = $(this).closest("li.option").attr("data-value")
			if($(this).closest("li.option").attr("data-type") == 'muilt'){
				//如果是多选
				selectedAnswer = '';
				var checkedBox = $(this).closest("li.option").parent("ul").find("input[type='checkbox']:checked")
				for(var i=0;i<checkedBox.length;i++){
					selectedAnswer += $(checkedBox[i]).val();
				}
				//console.log(checkedBox)
			}
			//console.log(selectedAnswer)
			
			//提交答案
			$.ajax({
				type: "POST",
				url: "submit_answer",
				dataType: "json",
				data: {"examId":submitExamId,examPaperId:examPaperId,questionId:questionId,answer:selectedAnswer,id:examPaperAnswerId},
				success: function(data){
					if(data.type == 'success'){
						//top.window.location="../exam/examing?examId="+eid;
					}else{
						alert(data.msg);
						//$(".tm_btn_primary").text('提交');
						//return;
						//window.location.reload();
					}
				},
				error: function(){
					//$(".tm_btn_primary").text('登录');
					alert('系统忙，请稍后再试');
					window.location.reload();
				}
			});
		});
	});
    //点击图片
    function imgShow(outerdiv, innerdiv, bigimg, _this){
        var src = _this.attr("src");//获取当前点击的img元素中的src属性
        $(bigimg).attr("src", src);//设置#bigimg元素的src属性
        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
        // $("<img/>").attr("src", src).load(function() {


        $("<img/>").attr("src", src).load(function() {
            var windowW = $(window).width();//获取当前窗口宽度
            var windowH = $(window).height();//获取当前窗口高度
            var realWidth = this.width;//获取图片真实宽度
            var realHeight = this.height;//获取图片真实高度
            var imgWidth, imgHeight;
            var scale = 0.9;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
            if(realHeight>windowH*scale) {//判断图片高度
                imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                    imgWidth = windowW*scale;//再对宽度进行缩放
                }
            } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
            } else {//如果图片真实高度和宽度都符合要求，高宽不变
                imgWidth = realWidth;
                imgHeight = realHeight;
            }
            // imgWidth = realWidth;
            // imgHeight = realHeight;
            $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放
            $(bigimg).css("height",imgHeight);

            $(bigimg).css("width",imgWidth);
            $(bigimg).css("height",imgHeight);
            $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
            $(bigimg).fadeIn("fast");

        });

        $(outerdiv).dblclick(function() {//再次点击淡出消失弹出层
            $(this).fadeOut("fast");
            $(bigimg).css("width",0);
            $(bigimg).css("height",0);
        });

        // $(window).keypress(function (event) {
        //    alert(String.fromCharCode(event.which));
        // })


    }

    // 滚轮放大事件

    function roll(bigimg,event){
        var UpDown = event.wheelDelta;
        //大于0滚轮向上滚动,小于0滚轮向下滚动
        if(UpDown>0){
            var wSize = document.getElementById("bigimg").getBoundingClientRect().width;
            var hSize = document.getElementById("bigimg").getBoundingClientRect().height;
            document.getElementById("bigimg").style.width = wSize*1.1+"px";
            document.getElementById("bigimg").style.height= hSize*1.1+"px";
        }
        if(UpDown<0){
            var wSize = document.getElementById("bigimg").getBoundingClientRect().width;
            var hSize = document.getElementById("bigimg").getBoundingClientRect().height;
            document.getElementById("bigimg").style.width = wSize*0.9+"px";
            document.getElementById("bigimg").style.height = hSize*0.9+"px";
        }
    }


var autoSubmit = false;
//当答题时间结束后自动提交
function autoSubmitExam(){
	if(autoSubmit)return;
	//提交
	$.ajax({
		type: "POST",
		url: "submit_exam",
		dataType: "json",
		data: {"examId":'${exam.id}',examPaperId:'${examPaper.id}'},
		success: function(data){
			if(data.type == 'success'){
				alert('答题时间到,成绩已出,请查看');
				window.location="../user/index";
			}else{
				alert(data.msg);
				//$(".tm_btn_primary").text('提交');
				//return;
				//window.location.reload();
			}
		},
		beforeSend:function(){
			autoSubmit = true;
		},
		error: function(){
			//$(".tm_btn_primary").text('登录');
			alert('系统忙，请稍后再试');
			window.location.reload();
		}
	});	
}


function submitExam(){
	var wcm = window.confirm('提交后将不能再进行答题，确认提交？');
	if(!wcm){
		return;
	}
	//提交
	$.ajax({
		type: "POST",
		url: "submit_exam",
		dataType: "json",
		data: {"examId":'${exam.id}',examPaperId:'${examPaper.id}'},
		success: function(data){
			if(data.type == 'success'){
				alert('测试成绩已出，请查看');
				window.location="../user/index";
			}else{
				alert(data.msg);
				//$(".tm_btn_primary").text('提交');
				//return;
				//window.location.reload();
			}
		},
		error: function(){
			//$(".tm_btn_primary").text('登录');
			alert('系统忙，请稍后再试');
			window.location.reload();
		}
	});
}

</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
<p></p>
</div>
</body>
</html>