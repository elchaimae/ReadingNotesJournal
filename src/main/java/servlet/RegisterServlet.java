package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.BookDAO;
import model.Book;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    // ===============================
    // 新規登録画面表示
    // ===============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 新規登録ページへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
        dispatcher.forward(request, response);
    }

    // ===============================
    // 登録処理（フォーム送信後）
    // ===============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // ------------------------------
            // フォーム入力値の取得
            // ------------------------------
            String title = request.getParameter("title");
            String yearStr = request.getParameter("year");

            // ▼ ジャンル（外部キー）
            String genreStr = request.getParameter("genre");
            Integer genreId = null;
            if (genreStr != null && !genreStr.isEmpty()) {
                genreId = Integer.parseInt(genreStr); // ← 数値IDが送られる想定
            }

            // ▼ 感想タグ（外部キー）
            String emotionStr = request.getParameter("emotions");
            Integer emotionId = null;
            if (emotionStr != null && !emotionStr.isEmpty()) {
                emotionId = Integer.parseInt(emotionStr);
            }

            String status = request.getParameter("status");

            String progressStr = request.getParameter("progress");
            int progress = 0;
            if (progressStr != null && !progressStr.isEmpty()) {
                try {
                    progress = Integer.parseInt(progressStr);
                } catch (NumberFormatException e) {
                    progress = 0;
                }
            }

            String startDate = request.getParameter("start_date");
            String endDate = request.getParameter("end_date");
            String memo = request.getParameter("memo");
            String createDay = request.getParameter("date");
            String fileName = request.getParameter("books_image");

            // ------------------------------
            // Book オブジェクト作成
            // ------------------------------
            Book book = new Book();
            book.setTitle(title);

            // 出版年（日付または文字列のまま保存）
            if (yearStr != null && !yearStr.isEmpty()) {
                book.setPublishedYear(yearStr.trim());
            } else {
                book.setPublishedYear(null);
            }

            // 外部キー項目
            book.setGenreId(genreId);
            book.setEmotionsId(emotionId);

            // ステータス・進捗
            book.setStatus(status);
            book.setProgress(progress);

            // 日付関連
            book.setStartedDay(startDate);
            book.setFinishedDay((endDate != null && !endDate.isEmpty()) ? endDate : null);
            book.setCreateDay(createDay);

            // その他
            book.setText(memo);
            book.setBooksImage(fileName);

            // ------------------------------
            // DB登録処理
            // ------------------------------
            BookDAO dao = new BookDAO();
            boolean success = dao.insert(book);

            if (success) {
                // 登録成功 → トップページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/TopServlet");
            } else {
                // 登録失敗 → エラー付きで戻す
                request.setAttribute("errorMsg", "登録に失敗しました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "登録処理中にエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
