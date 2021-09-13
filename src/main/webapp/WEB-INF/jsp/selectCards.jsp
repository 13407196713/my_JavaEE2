<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="static/css/bootstrap.min.css" />
	<script src="static/js/jquery.min.js"></script>
	<script type="text/javascript">
		function deleteCards(tid){
			if(window.confirm("真的删除该名片吗？")){
				$.ajax(
					{
						//请求路径
						url : "card/delete",
						//请求类型
						type : "post",
						//data表示发送的数据
						data : {
							id : tid
						},
						//成功响应的结果
						success : function(obj){//obj响应数据
							//获取路径
							var pathName=window.document.location.pathname;
							//截取，得到项目名称
							var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
							window.location.href = projectName + obj;
						},
						error : function() {
							alert("处理异常！");
						}
					}
				);
			}
		}
		</script>
</head>

<body>
	<!-- 加载header.jsp -->
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>

	<br><br><br>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">名片列表</h3>
			</div>

			<div class="panel-body">
				<div class="table table-responsive">
					<table class="table table-bordered table-hover">
						<tbody class="text-center">
							<tr>
								<th>名片ID</th>
								<th>姓名</th>
								<th>单位</th>
								<th>职位</th>
								<th>详情</th>
								<c:if test="${act=='updateSelect'}">
									<th>操作</th>
								</c:if>
								<c:if test="${act=='deleteSelect'}">
									<th>操作</th>
								</c:if>
							</tr>

							<c:if test="${totalPage != 0 }">

								<c:forEach items="${allCards}" var="card">
									<tr>
										<td>${card.id}</td>
										<td>${card.name}</td>
										<td>${card.company}</td>
										<td>${card.post}</td>
										<td>
											<a href="card/detail?id=${card.id}&act=detail" target="_blank">详情</a>
										</td>
										<c:if test="${act=='updateSelect'}">
											<td><a href="card/detail?id=${card.id}&act=update">修改</a></td>	
										</c:if>
										<c:if test="${act=='deleteSelect'}">
											<td><a href="javascript:deleteCards('${card.id}')" >删除</a></td>
										</c:if>
									</tr>
								</c:forEach>

								<!-- 查询 -->
								<c:if test="${act=='select'}">
									<tr>
										<td colspan="5" align="right">
											<ul class="pagination">
												<li><a>第<span>${currentPage}</span>页</a></li>
												<li><a>共<span>${totalPage}</span>页</a></li>
												<li>
													<c:if test="${currentPage != 1}">
														<a href="card/selectAllCardsByPage?act=select&currentPage=${currentPage - 1}">上一页</a>
													</c:if>
													<c:if test="${currentPage != totalPage}">
														<a href="card/selectAllCardsByPage?act=select&currentPage=${currentPage + 1}">下一页</a>
													</c:if>
												</li>
											</ul>
										</td>
									</tr>
								</c:if>

								<!-- 修改查询 -->
								<c:if test="${act=='updateSelect'}">
									<tr>
										<td colspan="6" align="right">
											<ul class="pagination">
												<li><a>第<span>${currentPage}</span>页</a></li>
												<li><a>共<span>${totalPage}</span>页</a></li>
												<li>
													<c:if test="${currentPage != 1}">
														<a href="card/selectAllCardsByPage?act=updateSelect&currentPage=${currentPage - 1}">上一页</a>
													</c:if>
													<c:if test="${currentPage != totalPage}">
														<a href="card/selectAllCardsByPage?act=updateSelect&currentPage=${currentPage + 1}">下一页</a>
													</c:if>
												</li>
											</ul>
										</td>
									</tr>
								</c:if>

								<!-- 删除查询 -->
								<c:if test="${act=='deleteSelect'}">
									<tr>
										<td colspan="6" align="right">
											<ul class="pagination">
												<li><a>第<span>${currentPage}</span>页</a></li>
												<li><a>共<span>${totalPage}</span>页</a></li>
												<li>
													<c:if test="${currentPage != 1}">
														<a href="card/selectAllCardsByPage?act=deleteSelect&currentPage=${currentPage - 1}">上一页</a>
													</c:if>
													<c:if test="${currentPage != totalPage}">
														<a href="card/selectAllCardsByPage?act=deleteSelect&currentPage=${currentPage + 1}">下一页</a>
													</c:if>
												</li>
											</ul>
										</td>
									</tr>
								</c:if>

							</c:if>
						</tbody>
					</table>

				</div>
			</div>
		</div>
	</div>
</body>
</html>