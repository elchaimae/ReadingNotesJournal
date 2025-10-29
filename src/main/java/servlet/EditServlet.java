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

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ===============================
    // 編集画面表示
    // ===============================
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);
        BookDAO dao = new BookDAO();
        Book book = dao.findById(id);
        request.setAttribute("book", book);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(request, response);
    }

    // ===============================
    // 編集フォーム送信（更新処理）
    // ===============================
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // -------------------------------
            // パラメータ取得
            // -------------------------------
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isBlank()) {
                throw new IllegalArgumentException("IDが取得できません");
            }
            int id = Integer.parseInt(idStr);

            String title = request.getParameter("title");
            String publishedYear = request.getParameter("published_year");
            if (publishedYear != null && publishedYear.isEmpty()) {
                publishedYear = null;
            }

            // ▼ 外部キー（ジャンルID）
            String genreStr = request.getParameter("genre");
            Integer genreId = null;
            if (genreStr != null && !genreStr.isEmpty()) {
                genreId = Integer.parseInt(genreStr);
            }

            // ▼ 外部キー（感想タグID）
            String emotionStr = request.getParameter("emotions");
            Integer emotionId = null;
            if (emotionStr != null && !emotionStr.isEmpty()) {
                emotionId = Integer.parseInt(emotionStr);
            }

            // 日付・ステータス・進捗
            String startedDay = request.getParameter("start_date");
            String finishedDay = request.getParameter("end_date");
            String status = request.getParameter("status");

            // 進捗（空欄なら0）
            String progressStr = request.getParameter("progress");
            int progress = 0;
            if (progressStr != null && !progressStr.isBlank()) {
                try {
                    progress = Integer.parseInt(progressStr);
                } catch (NumberFormatException e) {
                    progress = 0;
                }
            }

            String createDay = request.getParameter("date");
            String text = request.getParameter("memo");
            String fileName = request.getParameter("books_image");

            // -------------------------------
            // Bookオブジェクト作成
            // -------------------------------
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPublishedYear(publishedYear);
            book.setGenreId(genreId);
            book.setEmotionsId(emotionId);
            book.setStartedDay(startedDay);
            book.setFinishedDay(finishedDay);
            book.setStatus(status);
            book.setProgress(progress);
            book.setCreateDay(createDay);
            book.setText(text);

            if (fileName != null) {
                book.setBooksImage(fileName);
            }

            // -------------------------------
            // 更新実行
            // -------------------------------
            BookDAO dao = new BookDAO();
            boolean result = dao.update(book);
            System.out.println("★ dao.update 実行結果: " + result + " (book.id=" + book.getId() + ")");

            if (result) {
                // 編集後は詳細ページへ遷移
                response.sendRedirect(request.getContextPath() + "/BookDetailServlet?id=" + id);
            } else {
                request.setAttribute("error", "更新に失敗しました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "更新中にエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
            dispatcher.forward(request, response);
        }
    }
}
