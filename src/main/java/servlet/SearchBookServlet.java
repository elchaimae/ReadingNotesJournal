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
 * Servlet implementation class SearchBookServlet
 */
@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // パラメータ取得
        String title = request.getParameter("title");
        String yearStr = request.getParameter("year");
        String genreStr = request.getParameter("genre");

        Integer year = null;
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                year = Integer.parseInt(yearStr.substring(0, 4)); // yyyy-MM-dd の先頭4桁だけ使用
            } catch (Exception e) {
                year = null;
            }
        }

        Integer genreId = null;
        if (genreStr != null && !genreStr.isEmpty()) {
            genreId = Integer.parseInt(genreStr);
        }

        // DAO 呼び出し
        BookDAO dao = new BookDAO();
        List<Book> searchResults = dao.searchBooks(title, year, genreId);

        // 検索結果をリクエストスコープに保存
        request.setAttribute("bookList", searchResults);

        // トップ画面（index.jsp）へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
        dispatcher.forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
