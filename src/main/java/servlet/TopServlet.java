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
 * {@code TopServlet} は、トップページ（一覧画面）を表示するためのサーブレットです。<br>
 * 
 * <p>このサーブレットは、データベースから全ての書籍データを取得し、
 * タイトル順に並べ替えた上で JSP（index.jsp） にフォワードします。</p>
 * 
 * <p>主な処理内容：</p>
 * <ul>
 *   <li>GETリクエスト時に {@link BookDAO#findAllOrderByTitle()} を使用して書籍一覧を取得</li>
 *   <li>取得したリストをリクエストスコープ属性 {@code bookList} として保存</li>
 *   <li>{@code /WEB-INF/index.jsp} にフォワードし、画面表示を行う</li>
 * </ul>
 *
 * <p>POSTリクエストも GET と同じ処理を行います。</p>
 * 
 * @author あなたの名前
 * @version 1.0
 */
@WebServlet("/TopServlet")
public class TopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。特別な初期化処理は行いません。
     */
    public TopServlet() {
        super();
    }

    /**
     * GETリクエストを処理します。<br>
     * 書籍一覧をデータベースから取得し、トップページ（index.jsp）にフォワードします。
     *
     * @param request  クライアントからのリクエストオブジェクト
     * @param response クライアントへのレスポンスオブジェクト
     * @throws ServletException サーブレットエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO dao = new BookDAO();
        List<Book> bookList = dao.findAllOrderByTitle();

        request.setAttribute("bookList", bookList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエストを処理します。<br>
     * 処理内容は {@link #doGet(HttpServletRequest, HttpServletResponse)} と同じです。
     *
     * @param request  クライアントからのリクエストオブジェクト
     * @param response クライアントへのレスポンスオブジェクト
     * @throws ServletException サーブレットエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
