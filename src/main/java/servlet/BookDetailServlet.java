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

@WebServlet("/BookDetailServlet")
public class BookDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // パラメータ取得
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
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
