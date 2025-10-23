<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>検索 - Reading Notes Journal</title>
  <link rel="stylesheet" href="/ReadingNotesJournal/css/styles.css">
</head>

<body>
  <!-- 共通ヘッダー呼び出し -->
  <jsp:include page="/common/header.jsp" />

  <!-- 中央寄せのフォーム -->
  <main class="search-main">
    <form action="/ReadingNotesJournal/SearchBookServlet" method="get" class="search-form">

      <div class="form-row">
        <label>
          <img src="/ReadingNotesJournal/images/pencil.png" alt="" class="icon-pencil">
          本のタイトル
        </label>
        <input type="text" name="title">
      </div>

      <div class="form-row">
        <label>
          <img src="/ReadingNotesJournal/images/pencil.png" alt="" class="icon-pencil">
          出版年
        </label>
        <input type="text" name="year">
      </div>

      <div class="form-row">
        <label>
          <img src="/ReadingNotesJournal/images/pencil.png" alt="" class="icon-pencil">
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

      <button type="submit" class="btn-search">
        検索
        <img src="/ReadingNotesJournal/images/search.png" alt="検索" style="width:20px; height:20px;">
      </button>
    </form>

    <!-- フッター上の検索タイトル -->
    <div class="bottom-title">
      <img src="/ReadingNotesJournal/images/cup1.png" alt="ティーカップ" class="icon-cup">
      <span>検索</span>
    </div>
  </main>

  <!-- 共通フッター -->
  <footer>
    <p>@Reading Notes Journal</p>
  </footer>
</body>
</html>
