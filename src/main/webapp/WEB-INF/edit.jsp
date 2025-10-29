<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>編集 - Reading Notes Journal</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
  <!-- 共通ヘッダー -->
  <jsp:include page="/common/header.jsp" />

  <main class="register-main">
    <div class="form-container">

      <form action="${pageContext.request.contextPath}/EditServlet" 
            method="post" class="register-form">
        
        <!-- 編集対象のIDを隠して送信 -->
        <input type="hidden" name="id" value="${book.id}">

        <!-- 左側：本の基本情報 -->
        <div class="form-left">

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              本のタイトル
            </label>
            <input type="text" name="title" value="${book.title}" required>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              出版年
            </label> 
            	<input type="date" name="published_year"value="${book.publishedYear != null ? book.publishedYear.substring(0,10) : ''}">
          </div>
	
          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              ジャンル
            </label>
            <select name="genre">
              <option value="">選択してください</option>
              <option value="ミステリー" ${book.genreName == 'ミステリー' ? 'selected' : ''}>ミステリー</option>
              <option value="SF" ${book.genreName == 'SF' ? 'selected' : ''}>SF</option>
              <option value="歴史" ${book.genreName == '歴史' ? 'selected' : ''}>歴史</option>
              <option value="恋愛" ${book.genreName == '恋愛' ? 'selected' : ''}>恋愛</option>
              <option value="ファンタジー" ${book.genreName == 'ファンタジー' ? 'selected' : ''}>ファンタジー</option>
              <option value="ホラー" ${book.genreName == 'ホラー' ? 'selected' : ''}>ホラー</option>
              <option value="ノンフィクション" ${book.genreName == 'ノンフィクション' ? 'selected' : ''}>ノンフィクション</option>
              <option value="文学" ${book.genreName == '文学' ? 'selected' : ''}>文学</option>
              <option value="自己啓発" ${book.genreName == '自己啓発' ? 'selected' : ''}>自己啓発</option>
              <option value="ビジネス" ${book.genreName == 'ビジネス' ? 'selected' : ''}>ビジネス</option>
            </select>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              感想タグ
            </label>
            <select name="emotions">
              <option value="">選択してください</option>
              <option value="感動した" ${book.reviewTag == '感動した' ? 'selected' : ''}>感動した</option>
              <option value="笑えた" ${book.reviewTag == '笑えた' ? 'selected' : ''}>笑えた</option>
              <option value="考えさせられた" ${book.reviewTag == '考えさせられた' ? 'selected' : ''}>考えさせられた</option>
              <option value="怖かった" ${book.reviewTag == '怖かった' ? 'selected' : ''}>怖かった</option>
              <option value="勇気がでた" ${book.reviewTag == '勇気がでた' ? 'selected' : ''}>勇気がでた</option>
            </select>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              投稿日時
            </label>
            <input type="date" name="date" value="${book.createDay != null ? book.createDay.substring(0,10) : ''}">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読書ステータス
            </label>
            <select name="status">
              <option value="">選択してください</option>
              <option value="読書中" ${book.status == '読書中' ? 'selected' : ''}>読書中</option>
              <option value="読了" ${book.status == '読了' ? 'selected' : ''}>読了</option>
            </select>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              進捗
            </label>
            <input type="text" name="progress" value="${book.progress}">
          </div>
        </div>

        <!-- 右側 -->
        <div class="form-right">
          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読み始めた日
            </label>
            <input type="date" name="start_date" value="${book.startedDay != null ? book.startedDay.substring(0,10) : ''}">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読了日
            </label>
            <input type="date" name="end_date" value="${book.finishedDay != null ? book.finishedDay.substring(0,10) : ''}">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              表紙画像を選択
            </label>
            <select name="books_image">
              <option value="">選択してください</option>
              <option value="morocco.png" ${book.booksImage == 'morocco.png' ? 'selected' : ''}>アルマグリブ</option>
              <option value="English.png" ${book.booksImage == 'English.png' ? 'selected' : ''}>OneTwoEnglish</option>
              <option value="witch.png" ${book.booksImage == 'witch.png' ? 'selected' : ''}>星を辿る魔女の道標</option>
              <option value="sword.png" ${book.booksImage == 'sword.png' ? 'selected' : ''}>剣の遣い</option>
              <option value="ribirth.png" ${book.booksImage == 'ribirth.png.png' ? 'selected' : ''}>再生の記録</option>
            </select>
          </div>

          <div class="book-note-area">
            <div class="note-area">
              <div class="note-label">
                <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
                <span>感想やメモ</span>
              </div>
              <div class="book-note-box">
                <img src="${pageContext.request.contextPath}/images/book.png" alt="book illustration" class="book-bg">
                <textarea name="memo" class="note-text">${book.text}</textarea>
              </div>
            </div>
          </div>

          <!-- 編集ボタン -->
          <button type="submit" class="btn-submit">編集する</button>
        </div>
      </form>
    </div>
  </main>

  <!-- 共通フッター -->
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
