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

@WebServlet("/RegisterServlet")

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 新規登録画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ==== 入力データ取得 ====
        String title = request.getParameter("title");
        String yearStr = request.getParameter("year");
        String genre = request.getParameter("genre");
        String emotion = request.getParameter("emotions");
        String status = request.getParameter("status");
        String progressStr = request.getParameter("progress");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String memo = request.getParameter("memo");
        String createDay = request.getParameter("date");

     // finished_dayが空の場合はnullを設定
        String finishedDay = (endDate != null && !endDate.isEmpty()) ? endDate : null;
        
        // ==== Book オブジェクトに格納 ====
        Book book = new Book();
        book.setTitle(title);

        // 出版年（ 年だけ抽出）
     // 出版年（日付または文字列としてそのまま保存）
        if (yearStr != null && !yearStr.isEmpty()) {
            book.setPublishedYear(yearStr.trim());
        } else {
            book.setPublishedYear(null);
        }


        book.setGenreName(genre);
        book.setReviewTag(emotion);
        book.setStatus(status);

        int progress = 0;
        if (progressStr != null && !progressStr.isEmpty()) {
            try {
                progress = Integer.parseInt(progressStr);
            } catch (NumberFormatException e) {
                progress = 0;
            }
        }
        book.setProgress(progress);

        book.setStartedDay(startDate);
        book.setFinishedDay(finishedDay);
        book.setText(memo);
        book.setCreateDay(createDay);
        String fileName = request.getParameter("books_image");
        book.setBooksImage(fileName);

        // ==== DB登録 ====
        BookDAO dao = new BookDAO();
        boolean success = dao.insert(book);

        if (success) {
            // 成功 → トップページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/TopServlet");
        } else {
            // 失敗 → エラーメッセージ付きで戻る
            request.setAttribute("errorMsg", "登録に失敗しました。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
