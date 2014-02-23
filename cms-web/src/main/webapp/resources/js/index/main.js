$(function() {
	var cid = $("#cid").val();
	var oldObj;
	if(cid) {
		oldObj = $("#nav_"+cid);
		oldObj.addClass("nav_hover");
		var ul = oldObj.find(".child_hide_ul");
		$("#nav_child_ul").html(ul.html());
	}
	if (!oldObj) {
		$("#nav_child_ul").html($("#nav_child_first").html());
		var p = $("#nav_child_first").parent("li");
		oldObj = p;
		p.addClass("nav_hover");
	}
	$("#nav_parent li").mouseover(function() {
		if (!oldObj) {
			$(this).addClass("nav_hover");
			oldObj = this;
		} else {
			if (this != oldObj) {
				$(oldObj).removeClass("nav_hover");
				$(this).addClass("nav_hover");
				oldObj = this;
			}
		}
		var ul = $(this).find(".child_hide_ul");
		$("#nav_child_ul").html(ul.html());
	});
	$("#index_img").cycle({
		fx : 'fade',
		speed : 1000,
		timeout : 3000,
		next : '#s1',
		pause : 1,
		pager : "#nav_pager",
		before : function() {
			//alert($(this).children("img").attr("title"));
			$("#index_img_title_show").html($(this).children("img").attr("title"));
		}
	});
	
	$(".select_link select").change(function(){
		var url = $(this).val();
		window.open(url,"_blank");
	});
	
	$("#search").click(function(){
		var st = $("#searchText").val();
		if(st=="") {
			alert("请输入要查询的字符串");
			return false;
		} else {
			window.location.href = $("#contextPath").val()+"/search?qs="+st;
		}
	});
});
function onBefore() {
	$("#index_img_title_show").html($(this).attr("title"));
}