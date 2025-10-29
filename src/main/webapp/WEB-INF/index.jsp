<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>Reading Notes Journal</title>

  <!-- CSS読み込み -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

  <!-- 共通ヘッダー -->
  <jsp:include page="/common/header.jsp" />

  <main>
    <!-- サブタイトル -->
    <p class="subtitle-en">
      A little journal of my favorite books and the worlds I’ve discovered within them.
    </p>
    <p class="subtitle-ja">
      お気に入りの本と、その中で出会った世界を綴る小さな日記。
    </p>

    <!-- ✅ 検索結果 or 通常一覧タイトル -->
    <c:choose>
      <c:when test="${not empty param.title or not empty param.genre or not empty param.year}">
        <h2 class="section-title">検索結果</h2>
      </c:when>
    </c:choose>

    <!-- ✅ 結果が0件の場合 -->
    <c:if test="${empty bookList}">
      <p class="no-result">該当する本が見つかりません。</p>
    </c:if>

    <!-- ✅ 本一覧 -->
    <div class="book-list">
      <c:forEach var="book" items="${bookList}">
        <div class="book-card">
          <!-- 画像クリックで詳細ページへ遷移 -->
          <a href="${pageContext.request.contextPath}/BookDetailServlet?id=${book.id}">
            <img src="${pageContext.request.contextPath}/images/${book.booksImage}" alt="${book.title}">
          </a>

          <!-- タイトル -->
          <div class="book-title">${book.title}</div>

          <!-- ステータス -->
          <p>読書ステータス：${book.status}</p>

          <!-- 編集／削除プルダウン -->
          <select class="action-select" onchange="handleBookAction(this, ${book.id})">
            <option value="" disabled selected hidden>　</option>
            <option value="edit">編集</option>
            <option value="delete">削除</option>
          </select>
        </div>
      </c:forEach>
    </div>

    <!-- 削除確認モーダル -->
    <div id="deleteModal" class="modal-overlay">
      <div class="modal-content">
        <p>本当に削除しますか？</p>
        <div class="modal-buttons">
          <button class="btn-cancel" onclick="closeModal()">キャンセル</button>
          <button class="btn-delete" onclick="confirmDelete()">削除</button>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/bookActions.js"></script>
  </main>

  <!-- 共通フッター -->
  <jsp:include page="/common/footer.jsp" />

</body>
</html>
