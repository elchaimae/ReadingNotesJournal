<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>本の詳細 - Reading Notes Journal</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
  <!-- 共通ヘッダー -->
  <jsp:include page="/common/header.jsp" />

  <main class="register-main">
    <div class="form-container">
      <!-- タイトル -->

      <div class="detail-flex">
        <!-- 左カラム：本の情報 -->
        <div class="detail-left">
          <div class="book-card">
            <img src="${pageContext.request.contextPath}/images/${book.booksImage}" 
                 alt="${book.title}" class="detail-image">
            <p class="book-title">${book.title}</p>
          </div>

	     <c:choose>
		  <c:when test="${not empty book.publishedYear}">
		    ${book.publishedYear}
		  </c:when>
		  <c:otherwise>不明</c:otherwise>
		</c:choose>
         

            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> ジャンル： ${book.genreName}</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 感想タグ： ${book.reviewTag}</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 読書ステータス： ${book.status}</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 進捗： ${book.progress}%</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 読み始めた日： ${book.startedDay}</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 読了日： ${book.finishedDay}</p>
            <p><img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil"> 投稿日時： ${book.createDay}</p>
          </div>
        </div>

        <!-- 右カラム：感想やメモ -->
        <div class="detail-right">
          <div class="note-label">
            <img src="${pageContext.request.contextPath}/images/pencil.png" class="icon-pencil" alt="">
            <span>感想・メモ</span>
          </div>

          <div class="book-note-box">
            <img src="${pageContext.request.contextPath}/images/book.png" alt="book illustration" class="book-bg">

            <div class="note-text">
              <c:if test="${empty book.text}">
                <em>（感想・メモはまだ登録されていません）</em>
              </c:if>
              <c:if test="${not empty book.text}">
                ${book.text}
              </c:if>
            </div>
          </div>
        </div> <!-- detail-right 終了 -->
      </div> <!-- detail-flex 終了 -->

      <!-- 下部タイトル（ティーカップ付き） -->
      <div class="detail-bottom-title">
        <img src="${pageContext.request.contextPath}/images/cup1.png" alt="ティーカップ" class="icon-cup">
        <span>本の詳細</span>
      </div>
    </div> <!-- form-container 終了 -->
  </main>

  <!-- 共通フッター -->
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
