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
 * 書籍の詳細情報を表示するためのサーブレット。
 * 
 * <p>
 * クライアントから送信された書籍IDをもとに、データベースから該当書籍の情報を取得し、  
 * 詳細画面（<code>bookDetail.jsp</code>）へ転送して表示する。
 * </p>
 * 
 * <p>
 * 主な機能：
 * <ul>
 *   <li>URLパターン：<code>/BookDetailServlet</code></li>
 *   <li>GETリクエスト時：IDに対応する書籍を検索し、詳細ページを表示</li>
 *   <li>IDが指定されていない場合：トップページ（index.jsp）へリダイレクト</li>
 * </ul>
 * </p>
 * 
 * <p>
 * 使用クラス: {@link dao.BookDAO}, {@link model.Book}
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@WebServlet("/BookDetailServlet")
public class BookDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 書籍詳細画面の表示処理（GETリクエスト）。
     * 
     * <p>
     * URLパラメータで渡された書籍IDをもとに、
     * {@link BookDAO} を使用してデータベースから該当書籍を取得する。
     * 取得したデータをリクエストスコープに格納し、
     * 詳細表示用のJSP（<code>/WEB-INF/bookDetail.jsp</code>）へフォワードする。
     * </p>
     * 
     * <p>
     * IDが指定されていない、または不正な場合は、
     * トップページ（<code>index.jsp</code>）へリダイレクトする。
     * </p>
     * 
     * @param request  クライアントからのHTTPリクエスト（書籍IDを含む）
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException JSP転送時にエラーが発生した場合
     * @throws IOException      入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ取得
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            // IDが指定されていない場合はトップページへ
            response.sendRedirect("index.jsp");
            return;
        }

        int id = Integer.parseInt(idStr);

        // DAOから対象の本を取得
        BookDAO dao = new BookDAO();
        Book book = dao.findById(id);

        // JSPに渡す
        request.setAttribute("book", book);

        // 詳細画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookDetail.jsp");
        dispatcher.forward(request, response);
    }
}
