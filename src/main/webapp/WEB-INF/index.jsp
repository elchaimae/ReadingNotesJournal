<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>Reading Notes Journal</title>

  <!-- CSS読み込み（EL式で安全） -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

  <!-- 共通ヘッダー呼び出し -->
  <jsp:include page="/common/header.jsp" />

  <main>
    <p class="subtitle-en">
      A little journal of my favorite books and the worlds I’ve discovered within them.
    </p>
    <p class="subtitle-ja">
      お気に入りの本と、その中で出会った世界を綴る小さな日記。
    </p>

    <!-- ここに動的な本一覧を挿入 -->
    <div class="book-list">
  <c:forEach var="book" items="${bookList}">
    <div class="book-card">
      <img src="${pageContext.request.contextPath}/images/${book.booksImage}" alt="${book.title}">

      <!-- タイトル -->
      <div class="book-title">
        ${book.title}
      </div>

      <!-- ステータス -->
      <p>読書ステータス：${book.status}</p>

      <!-- 編集／削除セレクト -->
      <select class="action-select" onchange="handleBookAction(this, '${book.id}')">
        <option value="" disabled selected hidden>　</option>
        <option value="edit">編集</option>
        <option value="delete">削除</option>
      </select>
    </div>
  </c:forEach>
</div>
    

  <!-- 削除確認popup -->
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

  <!-- 共通フッター呼び出し -->
  <jsp:include page="/common/footer.jsp" />

</body>
</html>
