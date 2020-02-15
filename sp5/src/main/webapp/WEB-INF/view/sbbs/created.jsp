<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>
<script>
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

// 서브 카테고리 리스트 불러오기
function searchScategory(o) {
$("select[name='sCategory'] option").remove();
$("select[name='sCategory']").append("<option value='-1'>: 과목 선택 :</option>");
	var num = o.value;
	if(num==-1){
		return ;
	}
	var url="<%=cp%>/sbbs/subCategory";
	var query = {num:num};
	var fn = function(data){
		var slist=data.listSubcategory;
		for (var i = 0; i < slist.length; i++) {
// 			console.log(slist[i].category);
			$("select[name='sCategory']").append("<option value='"+slist[i].categoryNum+"'>"+slist[i].category+"</option>");
		};
	};
	ajaxJSON(url, "get", query, fn);
}

//글쓰기 등록
function sendOk() {
	var f = document.boardForm;
	if(f.bCategory.value==-1 && f.sCategory.value==-1){
		alert("카테고리를 선택해주세요.");
		f.bCategory.focus();
		return;
	}else if(f.sCategory.value==-1){
		alert("중분류 카테고리를 선택해주세요.");
		f.sCategory.focus();
		return;
	}
	
	f.groupCategoryNum.value=f.bCategory.value;
	f.categoryNum.value=f.sCategory.value;
	
	var str = f.subject.value;
	if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

	str = f.content.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }
	
	f.action="<%=cp%>/sbbs/created";
	f.submit();
}
</script>

<div class="body-container" style="width: 700px;">
	<div class="body-title">
		<h3><i class="fas fa-chalkboard-teacher"></i> 스터디 질문과 답변 </h3>
	</div>

	
    <div>
			<form name="boardForm" method="post">
			<input type="hidden" name="categoryNum"/>
			<input type="hidden" name="groupCategoryNum" value=""/>
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">분&nbsp;&nbsp;&nbsp;&nbsp;류</td>
			      <td style="padding-left:10px;"> 
			      	<select name="bCategory" class="boxTF" onchange="searchScategory(this);">
			      		<option value='-1'>: 분류 선택 :</option>
			      		
			      		<c:forEach var="dto" items="${categoryList}">
			      			<option value="${dto.categoryNum}">${dto.category}</option>
			      		</c:forEach>
			      		</select>
			      		<select name="sCategory" class="boxTF">
			      		<option value='-1'>: 과목 선택 :</option>
			      		</select>
			      </td>
			  </tr>
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			      <td style="padding-left:10px;"> 
			        <input type="text" name="subject" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.subject}">
			      </td>
			  </tr>
			
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
			      <td style="padding-left:10px;"> 
			          ${sessionScope.member.userName}
			      </td>
			  </tr>
			
			  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			        <textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
			      </td>
			  </tr>
			  </table>
			
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			        <button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/sbbs/list';">${mode=='update'?'수정취소':'등록취소'}</button>
			         <c:if test="${mode=='update'}">
			         	 <input type="hidden" name="num" value="${dto.num}">
			        	 <input type="hidden" name="page" value="${page}">
			        </c:if>
			      </td>
			    </tr>
			  </table>
			</form>
    </div>
    
</div>