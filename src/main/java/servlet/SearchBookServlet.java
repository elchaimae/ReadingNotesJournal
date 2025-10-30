package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.BookDAO;
import model.Book;

/**
 * {@code SearchBookServlet} は、ユーザーが入力した検索条件に基づいて
 * 書籍情報をデータベースから検索し、検索結果を表示するサーブレットです。
 * 
 * <p>検索条件（タイトル・出版年・ジャンル）を取得し、
 * {@link dao.BookDAO} を利用して該当する {@link model.Book} のリストを取得します。</p>
 * 
 * <p>検索結果はリクエストスコープに保存され、トップページ（index.jsp）で表示されます。</p>
 * 
 * @author エルコルテ
 * @version 1.0
 */
@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
    /** シリアルバージョンID（クラスのバージョン管理用） */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。
     * <p>特別な初期化処理は行いません。</p>
     */
    public SearchBookServlet() {
        super();
    }

    /**
     * GETメソッドのリクエストを処理します。
     * 
     * <p>検索フォームから送信された条件（タイトル、出版年、ジャンルID）を受け取り、
     * {@link BookDAO#searchBooks(String, Integer, Integer)} を利用して該当する書籍を検索します。</p>
     * 
     * <p>検索結果のリストはリクエストスコープに {@code bookList} として格納され、
     * {@code /WEB-INF/index.jsp} へフォワードして結果を表示します。</p>
     * 
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト  
     *                 （検索フォームの入力値を含む）
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力処理中にエラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストパラメータの文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // 検索条件の取得
        String title = request.getParameter("title");
        String yearStr = request.getParameter("year");
        String genreStr = request.getParameter("genre");

        // 出版年（yyyy-MM-dd形式の先頭4桁のみ使用）
        Integer year = null;
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr.substring(0, 4));
            } catch (Exception e) {
                year = null; // パース失敗時はnull扱い
            }
        }

        // ジャンルIDを整数に変換
        Integer genreId = null;
        if (genreStr != null && !genreStr.isEmpty()) {
            genreId = Integer.parseInt(genreStr);
        }

        // BookDAOを呼び出して検索処理を実行
        BookDAO dao = new BookDAO();
        List<Book> searchResults = dao.searchBooks(title, year, genreId);

        // 検索結果をリクエストスコープに格納
        request.setAttribute("bookList", searchResults);

        // トップ画面（index.jsp）へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTメソッドのリクエストを処理します。
     * 
     * <p>このサーブレットでは、POSTリクエストも doGet() に委譲しており、
     * GETと同じ動作（検索処理）を行います。</p>
     *
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力処理中にエラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
