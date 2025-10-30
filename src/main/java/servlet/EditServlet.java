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

/**
 * 編集画面を表示し、フォーム送信後に書籍情報を更新するサーブレット。
 * 
 * <p>
 * URLパターン: <code>/EditServlet</code>
 * </p>
 * 
 * <p>
 * 主な機能:
 * <ul>
 *   <li>GETリクエスト時：指定IDの書籍情報を取得し、編集画面（edit.jsp）へフォワード</li>
 *   <li>POSTリクエスト時：フォーム入力内容をもとにデータベースを更新</li>
 * </ul>
 * </p>
 * 
 * <p>
 * 使用テーブル: <code>books</code>、<code>emotions</code>、<code>genres</code>  
 * 関連クラス: {@link dao.BookDAO}, {@link model.Book}
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 編集画面を表示する処理（GETリクエスト）。
     * <p>
     * パラメータとして受け取った書籍IDをもとに、データベースから該当書籍情報を取得し、
     * JSP（/WEB-INF/edit.jsp）へフォワードして編集画面を表示する。
     * </p>
     * 
     * @param request  クライアントからのHTTPリクエスト（書籍IDを含む）
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException JSPフォワード時にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);

        // DAOを利用して指定IDの書籍を取得
        BookDAO dao = new BookDAO();
        Book book = dao.findById(id);

        // 取得データをリクエストスコープにセットし、JSPへ転送
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * 編集フォーム送信時の更新処理（POSTリクエスト）。
     * <p>
     * 入力フォームの各パラメータを取得し、{@link model.Book} オブジェクトを生成して
     * データベースを更新する。更新成功時は詳細画面（BookDetailServlet）へリダイレクト。
     * 失敗時は同じ編集画面にエラーメッセージを表示する。
     * </p>
     * 
     * @param request  クライアントからのHTTPリクエスト（フォームデータを含む）
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException JSPフォワード時にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
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

            // 外部キー（ジャンルID）
            String genreStr = request.getParameter("genre");
            Integer genreId = null;
            if (genreStr != null && !genreStr.isEmpty()) {
                genreId = Integer.parseInt(genreStr);
            }

            // 外部キー（感想タグID）
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
                // 成功時 → 詳細ページへ
                response.sendRedirect(request.getContextPath() + "/BookDetailServlet?id=" + id);
            } else {
                // 失敗時 → 編集画面へ戻る
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
