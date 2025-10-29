<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>検索 - Reading Notes Journal</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
  <!-- 共通ヘッダー -->
  <jsp:include page="/common/header.jsp" />

  <main class="register-main">
    <div class="form-container">

      <form action="${pageContext.request.contextPath}/SearchBookServlet" 
            method="get" class="register-form">

        <!-- 左側：検索条件 -->
        <div class="form-left">
          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              本のタイトル
            </label>
            <input type="text" name="title">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              出版年
            </label>
            <input type="date" name="year">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              ジャンル
            </label>
            <select name="genre">
              <option value="">選択してください</option>
              <option value="1">ミステリー</option>
              <option value="2">SF</option>
              <option value="3">歴史</option>
              <option value="4">恋愛</option>
              <option value="5">ファンタジー</option>
              <option value="6">ホラー</option>
              <option value="7">ノンフィクション</option>
              <option value="8">文学</option>
              <option value="9">自己啓発</option>
              <option value="10">ビジネス</option>
            </select>
          </div>
        </div>

        <!-- 右側：検索ボタン -->
        <div class="form-right" style="display: flex; align-items: center; justify-content: center;">
          <button type="submit" class="btn-submit" style="display: flex; align-items: center; gap: 8px;">
            検索
            <img src="${pageContext.request.contextPath}/images/search.png" alt="検索" style="width: 24px; height: 24px;">
          </button>
        </div>
      </form>

      <!-- ☕ フッター上の検索タイトル -->
      <div class="bottom-title">
        <img src="${pageContext.request.contextPath}/images/cup1.png" alt="ティーカップ" class="icon-cup">
        <span>検索</span>
      </div>

    </div>
  </main>

  <!-- 共通フッター -->
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
