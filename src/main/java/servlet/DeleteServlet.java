package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.BookDAO;

/**
 * 書籍情報を削除するためのサーブレットクラス。
 * 
 * <p>
 * このサーブレットは、対象書籍のIDを受け取り、
 * {@link dao.BookDAO} を使用してデータベースから該当レコードを削除します。
 * </p>
 *
 * <p>
 * 主な処理の流れ：
 * <ul>
 *   <li>POSTリクエストで削除対象の書籍IDを受け取る</li>
 *   <li>BookDAOのdeleteBook()メソッドを呼び出して削除実行</li>
 *   <li>削除結果に応じてHTTPステータスコードを返す</li>
 * </ul>
 * </p>
 *
 * <p>
 * URLパターン: <code>/DeleteServlet</code>
 * </p>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ。
     * <p>
     * 特別な初期化処理は行わないが、サーブレット生成時に呼び出される。
     * </p>
     */
    public DeleteServlet() {
        super();
    }

    /**
     * 確認用のGETリクエスト処理。
     * <p>
     * 主に動作確認・デバッグ用途で使用される。
     * 通常の削除処理は {@link #doPost(HttpServletRequest, HttpServletResponse)} を使用する。
     * </p>
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * 書籍削除処理（POSTリクエスト）。
     * <p>
     * クライアントから送信された書籍IDを受け取り、データベース上から該当書籍を削除する。
     * 結果に応じて適切なHTTPステータスコードを返す。
     * </p>
     *
     * <ul>
     *   <li>200 OK：削除成功</li>
     *   <li>404 Not Found：対象IDが存在しない</li>
     *   <li>400 Bad Request：不正なパラメータ</li>
     *   <li>500 Internal Server Error：その他の例外発生</li>
     * </ul>
     *
     * @param request  クライアントからのHTTPリクエスト（削除対象のIDを含む）
     * @param response サーバーからのHTTPレスポンス（処理結果をHTTPステータスで返す）
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 削除対象のIDを取得
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            // DAOを呼び出して削除処理を実行
            BookDAO dao = new BookDAO();
            boolean result = dao.deleteBook(id);

            if (result) {
                // 成功
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // IDが存在しないなどの失敗
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException e) {
            // IDが数値でない場合
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } catch (Exception e) {
            // その他の予期せぬエラー
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
