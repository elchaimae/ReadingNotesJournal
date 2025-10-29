<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>新規登録 - Reading Notes Journal</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
  <!-- 共通ヘッダー -->
  <jsp:include page="/common/header.jsp" />

  <main class="register-main">
    <div class="form-container">

      <form action="${pageContext.request.contextPath}/RegisterServlet" 
      method="post" class="register-form">
      

        <!-- 左側：本の基本情報 -->
        <div class="form-left">
          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              本のタイトル
            </label>
            <input type="text" name="title" required>
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
            <!-- ジャンル -->
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

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              感想タグ
            </label>
             <select name="emotions">
			  <option value="">選択してください</option>
			  <option value="1">感動した</option>
			  <option value="2">笑えた</option>
			  <option value="3">考えさせられた</option>
			  <option value="4">怖かった</option>
			  <option value="5">勇気がでた</option>
			</select>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              投稿日時
            </label>
            <input type="date" name="date" readonly class="no-calendar">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読書ステータス
            </label>
            <select name="status">
              <option value="">選択してください</option>
              <option>読書中</option>
              <option>読了</option>
            </select>
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              進捗
            </label>
            <input type="text" name="progress">
          </div>
        </div>

        <!-- 右側：日付・画像・メモ -->
        <div class="form-right">
          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読み始めた日
            </label>
            <input type="date" name="start_date">
          </div>

          <div class="form-row">
            <label>
              <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
              読了日
            </label>
            <input type="date" name="end_date">
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

 	<!-- 本の画像の中にメモを入力 -->
	<div class="book-note-area">
	  <!-- 右側：感想やメモ -->
	  <div class="note-area">
	    <div class="note-label">
	      <img src="${pageContext.request.contextPath}/images/pencil.png" alt="" class="icon-pencil">
	       <span>感想やメモ</span>
	    </div>
	    <div class="book-note-box">
	      <img src="${pageContext.request.contextPath}/images/book.png" alt="book illustration" class="book-bg">
	      <textarea name="memo" class="note-text" placeholder="感想やメモを書いてください..."></textarea>
	    </div>
	  </div>
	</div>
	
	<button type="submit" class="btn-submit">登録する</button>
 		
      </form>

	<div class="bottom-title">
	  <img src="${pageContext.request.contextPath}/images/cup1.png" alt="ティーカップ" class="icon-cup">
	  <span>新規登録</span>
	</div>
    </div>
</main>
	

	<!-- 投稿日時自動入力 -->
	<script>
	document.addEventListener("DOMContentLoaded", function() {
	  const input = document.querySelector('input[name="date"]');
	  if (input) {
	    const d = new Date();
	    input.value = d.toISOString().split('T')[0]; // ← 最短・確実な日付設定
	  }
	});
	</script>
	 <!-- 共通フッター -->
	<jsp:include page="/common/footer.jsp" />
</body>
</html>
	