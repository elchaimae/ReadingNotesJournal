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
 * {@code RegisterServlet} は、新しい書籍データを登録するためのサーブレットです。
 *
 * <p>このサーブレットは主に2つの機能を持ちます。</p>
 * <ul>
 *   <li>GETリクエスト時：新規登録画面（register.jsp）を表示</li>
 *   <li>POSTリクエスト時：フォーム送信データを受け取り、データベースに登録</li>
 * </ul>
 *
 * <p>登録成功時はトップ画面（TopServlet）へリダイレクトし、失敗時はエラーメッセージ付きで
 * 登録画面に戻ります。</p>
 *
 * @author エルコルテ
 * @version 1.0
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    /** シリアルバージョンID（バージョン管理用） */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。
     * <p>特別な初期化処理は行いません。</p>
     */
    public RegisterServlet() {
        super();
    }

    /**
     * GETメソッドのリクエストを処理します。
     *
     * <p>新規登録ページ（register.jsp）を表示します。</p>
     *
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 新規登録ページへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTメソッドのリクエストを処理します。
     *
     * <p>フォームで入力された書籍情報を受け取り、{@link Book} オブジェクトを生成し、
     * {@link BookDAO#insert(Book)} を通してデータベースに登録します。</p>
     *
     * <p>登録が成功した場合はトップページ（TopServlet）へリダイレクトし、
     * 失敗または例外発生時にはエラーメッセージを設定して
     * 登録画面（register.jsp）へ戻ります。</p>
     *
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
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
                genreId = Integer.parseInt(genreStr);
            }

            // ▼ 感想タグ（外部キー）
            String emotionStr = request.getParameter("emotions");
            Integer emotionId = null;
            if (emotionStr != null && !emotionStr.isEmpty()) {
                emotionId = Integer.parseInt(emotionStr);
            }

            // ▼ 読書ステータスと進捗率
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

            // ▼ 日付・メモ・画像
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

            // 出版年（空欄はnull扱い）
            if (yearStr != null && !yearStr.isEmpty()) {
                book.setPublishedYear(yearStr.trim());
            } else {
                book.setPublishedYear(null);
            }

            // 外部キー設定
            book.setGenreId(genreId);
            book.setEmotionsId(emotionId);

            // 状態と進捗
            book.setStatus(status);
            book.setProgress(progress);

            // 日付関連
            book.setStartedDay(startDate);
            book.setFinishedDay((endDate != null && !endDate.isEmpty()) ? endDate : null);
            book.setCreateDay(createDay);

            // メモと画像
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
                // 登録失敗 → エラーメッセージを設定して登録画面に戻る
                request.setAttribute("errorMsg", "登録に失敗しました。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            // 想定外の例外 → ログ出力＆エラーメッセージ設定
            e.printStackTrace();
            request.setAttribute("errorMsg", "登録処理中にエラーが発生しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
