package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * {@code SearchServlet} は「検索画面(search.jsp)」を表示するためのサーブレットです。
 * 
 * <p>GETリクエスト時に search.jsp をフォワードし、
 * POSTリクエスト時は doGet() に処理を委譲します。</p>
 * 
 * <p>主に、ユーザーが検索条件を入力するための画面表示を担当します。</p>
 * 
 * @author エルコルテ
 * @version 1.0
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    /** シリアルバージョンID（サーブレットのバージョン管理用） */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。
     * <p>特別な初期化処理は行いません。</p>
     */
    public SearchServlet() {
        super();
    }

    /**
     * GETメソッドのリクエストを処理します。
     * 
     * <p>search.jsp へフォワードし、検索画面を表示します。</p>
     *
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレットエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/search.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTメソッドのリクエストを処理します。
     * 
     * <p>このサーブレットでは、POSTリクエストも doGet() に処理を委譲します。
     * そのため、GET/POST いずれの場合も同じ動作（検索画面の表示）になります。</p>
     *
     * @param request  クライアントから送信された {@link HttpServletRequest} オブジェクト
     * @param response クライアントへ応答を返すための {@link HttpServletResponse} オブジェクト
     * @throws ServletException サーブレットエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
