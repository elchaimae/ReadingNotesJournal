package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.BookDAO;
import model.Book;

@WebServlet("/RegisterServlet")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,   // メモリ上保持 2MB
	    maxFileSize = 1024 * 1024 * 20,        // 1ファイル最大 20MB
	    maxRequestSize = 1024 * 1024 * 100     // 全体最大 100MB
	)
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

        // ==== 画像アップロード ====
        Part filePart = request.getPart("image");
        String fileName = null;

        if (filePart != null && filePart.getSize() > 0) {
            // ファイル名を取得
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // 保存先（/webapp/images）
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // ファイル保存
            filePart.write(uploadPath + File.separator + fileName);
        }

        // ==== Book オブジェクトに格納 ====
        Book book = new Book();
        book.setTitle(title);

        // 出版年（ 年だけ抽出）
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                book.setPublishedYear(Integer.parseInt(yearStr.substring(0, 4)));
            } catch (Exception e) {
                book.setPublishedYear(0);
            }
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
        book.setFinishedDay(endDate);
        book.setText(memo);
        book.setCreateDay(createDay);
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
