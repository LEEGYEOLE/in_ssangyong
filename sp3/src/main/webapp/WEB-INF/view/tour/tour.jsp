<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
	String serviceKey = "dn111vNAkCeqZCanIrAtZjEToFnYOFivz3EHaxrqc1Vc9f3eERYghzGJav7JkNvCMwxo2PNOgVi0rnRsNUeoxw%3D%3D";

%>
<style>
/* Fade */
.hvr-fade {
z-index:999;
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  overflow: hidden;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: color, background-color;
  transition-property: color, background-color;
}
.hvr-fade:hover, .hvr-fade:focus, .hvr-fade:active {
  background-color: #2098D1;
  color: white;
}

/* Buzz */
@-webkit-keyframes hvr-buzz {
  50% {
    -webkit-transform: translateX(3px) rotate(2deg);
    transform: translateX(3px) rotate(2deg);
  }
  100% {
    -webkit-transform: translateX(-3px) rotate(-2deg);
    transform: translateX(-3px) rotate(-2deg);
  }
}
@keyframes hvr-buzz {
  50% {
    -webkit-transform: translateX(3px) rotate(2deg);
    transform: translateX(3px) rotate(2deg);
  }
  100% {
    -webkit-transform: translateX(-3px) rotate(-2deg);
    transform: translateX(-3px) rotate(-2deg);
  }
}
.hvr-buzz {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
}
.hvr-buzz:hover, .hvr-buzz:focus, .hvr-buzz:active {
  -webkit-animation-name: hvr-buzz;
  animation-name: hvr-buzz;
  -webkit-animation-duration: 0.15s;
  animation-duration: 0.15s;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
}

/* Underline Reveal */
.element {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  position: relative;
  overflow: hidden;
}
.element:before {
  content: "";
  position: absolute;
  z-index: -1;
  left: 0;
  right: 0;
  bottom: 0;
  background: #2098D1;
  height: 4px;
  -webkit-transform: translateY(4px);
  transform: translateY(4px);
  -webkit-transition-property: transform;
  transition-property: transform;
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-timing-function: ease-out;
  transition-timing-function: ease-out;
}
.element:hover:before, .element:focus:before, .element:active:before {
  -webkit-transform: translateY(0);
  transform: translateY(0);
}
</style>
<script type="text/javascript">
function ajaxJSON(url, type, query, fn) {
	
	$.ajax({
		type:type
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			fn(data);
		}
		,beforeSend:function(jqXHR) {
	        jqXHR.setRequestHeader("AJAX", true);
	    }
	    ,error:function(jqXHR) {
	    	if(jqXHR.status==403) {
	    		login();
	    		return false;
	    	}
	    	console.log(jqXHR.responseText);
	    }
	});
}
function ajaxJSONData(subURL, type, fn) {
	var url = "<%=cp%>/tour/ajaxRequest?getUrl=";
// 	console.log(subURL);
	url+=replace(subURL);
	$.ajax({
		type:type
		,url:url
		,success:function(data) {
			fn(data);
		},
// 		async : false,
		dataType : "json",
		error: function(x,o,e){
			alert(x.status + ":" +o+":"+e);	
		}
	});
}
function replace(url) {
    url= encodeURIComponent(url);
    return url;
}

$(function(){
	//지역 리스트 가져오기
	getSelect("area");
});
//가져오는 데이터에 따라 셀박 리스트 불러오기
function getSelect(name) {
	var subURL = "";
	var $select = "select[name='"+name+"']";
	if(name=="area"){
		subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?serviceKey=<%=serviceKey%>&numOfRows=999&pageNo=1&MobileOS=ETC&MobileApp=AppTest";
	}else if(name=="sArea"){
		var aCode = $("select[name='area']").val();
		subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?serviceKey=<%=serviceKey%>&numOfRows=999&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode="+aCode;
	}else if(name=="cat1"){
		var type = $("select[name='type']").val();
		subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/categoryCode?serviceKey=<%=serviceKey%>&MobileOS=ETC&MobileApp=AppTest&contentTypeId="+type;
	}else if(name=="cat2"){
		var type = $("select[name='type']").val();
		var cat1 = $("select[name='cat1']").val();
		subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/categoryCode?serviceKey=<%=serviceKey%>&MobileOS=ETC&MobileApp=AppTest&contentTypeId="+type+"&cat1="+cat1;
	}else if(name=="cat3"){
		var type = $("select[name='type']").val();
		var cat1 = $("select[name='cat1']").val();
		var cat2 = $("select[name='cat2']").val();
		subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/categoryCode?serviceKey=<%=serviceKey%>&MobileOS=ETC&MobileApp=AppTest&contentTypeId="+type+"&cat1="+cat1+"&cat2="+cat2;
	}	
		var fn = function(result) {
		$($select).empty();
		$($select).append("<option value='-1'>전체</option>");
	      if(result == null || result == ""){
				alert("해당 주소로 얻을수 있는 좌표가 없습니다. 주소값을 다시 입력하세요");
			      }else{
			    	var item = funcParser(result);
					$.each(item, function(i,value){
						var cityname = value.getElementsByTagName('name')[0].childNodes[0].nodeValue;
						var citycode = value.getElementsByTagName('code')[0].childNodes[0].nodeValue;
						$($select).append("<option value='"+citycode+"'>"+cityname+"</option>");
					});
					if(name=="area"){
						$($select).children().first().remove();
						getSelect("sArea", item[0].getElementsByTagName('code')[0].childNodes[0].nodeValue);
					}
			      }
		   		// 리스트 가져오기
		   		return getList();
			};
	ajaxJSONData(subURL, "get", fn);
}
//조건에 맞는 리스트 불러오기
function getList() {
	if($("select[name='area']").val()==-1) return;
	
	var subURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?serviceKey=<%=serviceKey%>&numOfRows=9&MobileOS=ETC&arrange=A&MobileApp=AppTest";
	subURL+=($("select[name='area']").val()==-1?"":"&areaCode="+$("select[name='area']").val());
	subURL+=($("select[name='sArea']").val()==-1?"":"&sigunguCode="+$("select[name='sArea']").val());
	subURL+=($("select[name='type']").val()==-1?"":"&contentTypeId="+$("select[name='type']").val());
	subURL+=($("select[name='cat1']").val()==-1?"":"&cat1="+$("select[name='cat1']").val());
	subURL+=($("select[name='cat2']").val()==-1?"":"&cat2="+$("select[name='cat2']").val());
	subURL+=($("select[name='cat3']").val()==-1?"":"&cat3="+$("select[name='cat3']").val());
	
	var fn = function(result) {
		$("#infoList").empty();
		if(result == null || result == ""){
				alert("해당 주소로 얻을수 있는 좌표가 없습니다. 주소값을 다시 입력하세요");
			      }else{
			    	var item = funcParser(result);
					$.each(item, function(i,value){
						var title = value.getElementsByTagName('title')[0].childNodes[0].nodeValue;
						var firstimage = value.getElementsByTagName('firstimage')[0]==undefined?"<%=cp%>/resource/images/noimage.jpg":value.getElementsByTagName('firstimage')[0].childNodes[0].nodeValue;
						var addr1 = value.getElementsByTagName('addr1')[0]==undefined?"No Address":value.getElementsByTagName('addr1')[0].childNodes[0].nodeValue;
// 						hvr-buzz 
						var add  = "";
							add +="<div class='element hvr-fade' style='border-radius: 5px; padding: 0 0 15px; max-width: 30.1%; min-height: 200px; float: left; margin:30px 15px;'>";
							add +=	"<div class='thumbnail'>";
							add +=		"<img style='height: 250px; width: 100%;' src='"+firstimage+"'>";
							add +=		"<div class='caption' style='width: 95%;padding: 0 5px;'>";
							add +=			"<div style='margin-top: 10px'>";
							add +=				"<table style='width:100%; margin-top:10px; table-layout: fixed;'>";
							add +=					"<tr>";
							add +=						"<td style='font-size: 22px; font-weight: 900; width: 80%;text-overflow:ellipsis; overflow:hidden; white-space:nowrap;'>"+title+"</td>";
							add +=					"</tr>";
							add +=					"<tr>";
							add +=						"<td style='font-size: 18px; width: 80%;text-overflow:ellipsis; overflow:hidden; white-space:nowrap;'>"+addr1+"</td>";
							add +=					"</tr>";
							add +=				"</table>";
							add +=			"</div></div></div></div>";
							$("#infoList").append(add);
					});

					$("#infoList").append("<div style='clear:both;'></div>");
			      }
			};
	console.log(subURL);
	ajaxJSONData(subURL, "get", fn);
}

function funcParser(result){
	var values;
	  $.each(result, function(i,value){
		  values=value;
	  });
	  
	var parser=new DOMParser();
	xmlDoc=parser.parseFromString(values,"text/xml");
	var items = xmlDoc.getElementsByTagName('items');
	var item = items[0].getElementsByTagName('item');
	return item;
}

</script>

<div class="body-container" style="width: 960px;">
    <div class="body-title">
        <h3><i class="fas fa-chalkboard"></i> 관광정보 </h3>
    </div>
    
    <div>
		<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
		   <tr height="35">
		      <td align="left" width="50%">
		      </td>
		      <td align="right">
		          &nbsp;
		      </td>
		   </tr>
		</table>
		<div style="margin: 0 15px;">
			<table style="width: 100%;margin: 20px auto 0px;border-spacing: 0px;border: 1px solid #ccc;padding: 10px;border-radius: 5px;background: #ddd;">
				<tr height="35">
		   			<td align="center" width="30%">
		      			<span style="font-size: 20px; width:25%;">시/도 : </span>
		      			<select class="selectField" name="area" onchange="getSelect('sArea');" style="width: 75%;height: 35px;font-size: 20px;">
		        			<option value="-1">전체</option>
		      			</select>
		      		</td>
		      		<td align="center" width="30%">
		      			<span style="font-size: 20px; width:25%;">구/군 : </span>
		      			<select class="selectField" name="sArea" onchange="getList();" style="width: 75%;height: 35px;font-size: 20px;">
		        			<option value="-1">전체</option>
		      			</select>
		      		</td>
		      		<td align="center" width="40%">
		      			<span style="font-size: 20px; width:45%;">관광 타입 선택 : </span>
		      			<select class="selectField" name="type" onchange="getSelect('cat1');" style="width: 55%;height: 35px;font-size: 20px;">
		        			<option value="-1">전체</option>
		        			<option value="12">관광지</option>
		        			<option value="14">문화시설</option>
		        			<option value="15">행사/공연/축제</option>
		        			<option value="25">여행코스</option>
		        			<option value="28">레포츠</option>
		        			<option value="32">숙박</option>
		        			<option value="38">쇼핑</option>
		        			<option value="39">음식점</option>
		      			</select>
		   			</td>
		  		</tr>
			</table>
		</div>
		<div style="margin: 0 15px;">
		<table style="width: 100%;margin: 10px auto 0px;border-spacing: 0px;border: 1px solid #ccc;padding: 10px;border-radius: 5px;background: #ddd;">
		   <tr height="35">
		      <td align="center" width="30%">
		      	<span style="font-size: 20px; width:25%;">대분류 : </span>
		      	<select class="selectField" name="cat1" onchange="getSelect('cat2');" style="width: 70%;height: 35px;font-size: 20px;">
		        	<option value="-1">전체</option>
		      	</select>
		      </td>
		      <td align="center" width="30%">
		      	<span style="font-size: 20px; width:25%;">중분류 : </span>
		      	<select class="selectField" name="cat2" onchange="getSelect('cat3');" style="width: 71%;height: 35px;font-size: 20px;">
		        	<option value="-1">전체</option>
		      	</select>
		      </td>
		      <td align="center" width="40%">
		      	<span style="font-size: 20px; width:45%;">소분류 : </span>
		      	<select class="selectField" name="cat3" style="width: 76%;height: 35px;font-size: 20px;">
		        	<option value="-1">전체</option>
		      	</select>
		      </td>
		   </tr>
		</table>
		</div>
		<div style="margin: 0 15px;">
		<table style="width: 100%;margin: 10px auto 0px;border-spacing: 0px;border: 1px solid #ccc;padding: 10px;border-radius: 5px;background: #ddd;">
		   <tr height="35">
		      <td align="center">
		          <form name="searchForm" action="<%=cp%>/bbs/list" method="post">
		          <button type="reset" class="btn" style="width: 13%;height: 50px; font-size: 20px;">초기화</button>
		              <select name="condition" class="selectField" style="width: 15%;height: 50px;font-size: 20px;">
		                  <option value="all" ${condition=="all"?"selected='selected'":""}>모두</option>
		                  <option value="subject" ${condition=="subject"?"selected='selected'":""}>제목</option>
		                  <option value="content" ${condition=="content"?"selected='selected'":""}>내용</option>
		                  <option value="userName" ${condition=="userName"?"selected='selected'":""}>작성자</option>
		                  <option value="created" ${condition=="created"?"selected='selected'":""}>등록일</option>
		            </select>
		            <input type="text" name="keyword" value="${keyword}" class="boxTF" style="width: 55%;height: 40px;font-size: 20px;">
		            <button type="button" class="btn" style="width: 13%;height: 50px; font-size: 20px;">검색</button>
		        </form>
		      </td>
		   </tr>
		</table>
		</div>
		<div id="infoList" style="margin-top: 10px;">
				
		</div>
		
		 
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		   <tr height="35">
			<td align="center">
			       ${dataCount==0?"등록된 게시물이 없습니다.":paging}
			 </td>
		   </tr>
		</table>
    </div>

</div>