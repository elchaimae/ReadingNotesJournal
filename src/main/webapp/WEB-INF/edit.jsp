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
			  <option value="1" ${book.genreId == 1 ? 'selected' : ''}>ミステリー</option>
			  <option value="2" ${book.genreId == 2 ? 'selected' : ''}>SF</option>
			  <option value="3" ${book.genreId == 3 ? 'selected' : ''}>歴史</option>
			  <option value="4" ${book.genreId == 4 ? 'selected' : ''}>恋愛</option>
			  <option value="5" ${book.genreId == 5 ? 'selected' : ''}>ファンタジー</option>
			  <option value="6" ${book.genreId == 6 ? 'selected' : ''}>ホラー</option>
			  <option value="7" ${book.genreId == 7 ? 'selected' : ''}>ノンフィクション</option>
			  <option value="8" ${book.genreId == 8 ? 'selected' : ''}>文学</option>
			  <option value="9" ${book.genreId == 9 ? 'selected' : ''}>自己啓発</option>
			  <option value="10" ${book.genreId == 10 ? 'selected' : ''}>ビジネス</option>
			</select>            
          </div>

          <div class="form-row">
		  <label>
		    <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
		    感想タグ
		  </label>
		  <select name="emotions">
		    <option value="">選択してください</option>
		    <option value="1" ${book.emotionsId == 1 ? 'selected' : ''}>感動した</option>
		    <option value="2" ${book.emotionsId == 2 ? 'selected' : ''}>笑えた</option>
		    <option value="3" ${book.emotionsId == 3 ? 'selected' : ''}>考えさせられた</option>
		    <option value="4" ${book.emotionsId == 4 ? 'selected' : ''}>怖かった</option>
		    <option value="5" ${book.emotionsId == 5 ? 'selected' : ''}>勇気がでた</option>
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
