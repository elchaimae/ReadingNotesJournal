// ===============================
// 本の編集・削除アクション制御
// ===============================

// プルダウン操作
function handleBookAction(select, bookId) {
  const action = select.value;

  if (action === "delete") {
    // モーダル表示
    const modal = document.getElementById("deleteModal");
    modal.style.display = "flex";
    modal.dataset.bookId = bookId;
    select.value = "";
  } else if (action === "edit") {
    // 編集ページへ
    window.location.href = `${window.location.origin}/ReadingNotesJournal/editBook.jsp?id=${bookId}`;
  }
}

// キャンセルボタン
function closeModal() {
  document.getElementById("deleteModal").style.display = "none";
}

// 削除ボタン
function confirmDelete() {
  const modal = document.getElementById("deleteModal");
  const bookId = modal.dataset.bookId;
  modal.style.display = "none";

  // Servletに削除リクエスト送信
  fetch(`${window.location.origin}/ReadingNotesJournal/DeleteBookServlet?id=${bookId}`, {
    method: "POST"
  })
  .then(response => {
    if (response.ok) {
      // 成功 → トップ画面をリロード
      window.location.href = `${window.location.origin}/ReadingNotesJournal/index.jsp`;
    } else {
      alert("削除に失敗しました。");
    }
  })
  .catch(error => {
    console.error("Error:", error);
    alert("通信エラーが発生しました。");
  });
}
