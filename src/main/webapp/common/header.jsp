<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="top-border"></div>

<header class="site-header">
  <div class="header-inner">
    <div class="title-area">
      <img src="${pageContext.request.contextPath}/images/glasses.png" alt="ロゴ" class="icon-glasses">
      <div class="titles">
        <h1>Reading Notes Journal</h1>
        <p>読書日記アプリ</p>
      </div>
    </div>

    <div class="nav-area">
      <span class="icon-pen"></span>
      <a href="${pageContext.request.contextPath}/TopServlet" class="nav-top">TOPページ</a>
    </div>
  </div>

  <!-- RegisterServlet経由で index.jsp に遷移 -->
  <nav class="main-nav">
    <a href="${pageContext.request.contextPath}/RegisterServlet">新規登録</a>
    <a href="${pageContext.request.contextPath}/SearchServlet">検索</a>
  </nav>
</header>
