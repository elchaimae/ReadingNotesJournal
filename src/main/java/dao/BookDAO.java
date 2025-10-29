package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookDAO {

    // ==============================
    // 一覧取得（降順）
    // ==============================
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT b.*, e.review_tag, g.genres_name
            FROM books b
            LEFT JOIN emotions e ON b.emotions_id = e.id
            LEFT JOIN genres g ON b.genre_id = g.id
            ORDER BY b.id DESC
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setReviewTag(rs.getString("review_tag"));
                book.setGenreName(rs.getString("genres_name"));
                book.setBooksImage(rs.getString("books_image"));
                book.setStatus(rs.getString("status"));
                list.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==============================
    // タイトル順で最大15冊取得
    // ==============================
    public List<Book> findAllOrderByTitle() {
        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT b.*, e.review_tag, g.genres_name
            FROM books b
            LEFT JOIN emotions e ON b.emotions_id = e.id
            LEFT JOIN genres g ON b.genre_id = g.id
            ORDER BY b.title ASC
            LIMIT 15
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setReviewTag(rs.getString("review_tag"));
                book.setGenreName(rs.getString("genres_name"));
                book.setBooksImage(rs.getString("books_image"));
                book.setStatus(rs.getString("status"));
                list.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==============================
    // 検索機能（タイトル／ジャンル／出版年）
    // ==============================
    public List<Book> searchBooks(String title, Integer year, Integer genreId) {
        List<Book> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT b.*, e.review_tag, g.genres_name
            FROM books b
            LEFT JOIN emotions e ON b.emotions_id = e.id
            LEFT JOIN genres g ON b.genre_id = g.id
            WHERE 1=1
        """);

        // 条件を動的に追加
        if (title != null && !title.isEmpty()) {
            sql.append(" AND b.title LIKE ?");
        }
        if (year != null) {
            sql.append(" AND b.published_year LIKE ?");
        }
        if (genreId != null) {
            sql.append(" AND b.genre_id = ?");
        }

        sql.append(" ORDER BY b.title ASC");

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;

            if (title != null && !title.isEmpty()) {
                ps.setString(idx++, "%" + title + "%");
            }
            if (year != null) {
                ps.setString(idx++, "%" + year + "%");
            }
            if (genreId != null) {
                ps.setInt(idx++, genreId);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setReviewTag(rs.getString("review_tag"));
                book.setGenreName(rs.getString("genres_name"));
                book.setBooksImage(rs.getString("books_image"));
                book.setStatus(rs.getString("status"));
                list.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==============================
    // 新規登録
    // ==============================
    public boolean insert(Book book) {
        String sql = """
            INSERT INTO books (
                emotions_id, genre_id, title, published_year, books_image, status,
                progress, started_day, finished_day, text, create_day
            )
            VALUES (
                (SELECT id FROM emotions WHERE review_tag = ? LIMIT 1),
                (SELECT id FROM genres WHERE genres_name = ? LIMIT 1),
                ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getReviewTag());
            ps.setString(2, book.getGenreName());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getPublishedYear());
            ps.setString(5, book.getBooksImage());
            ps.setString(6, book.getStatus());
            ps.setInt(7, book.getProgress());
            ps.setString(8, book.getStartedDay());
            ps.setString(9, book.getFinishedDay());
            ps.setString(10, book.getText());
            ps.setString(11, book.getCreateDay());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==============================
    // 本を削除
    // ==============================
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==============================
    // 本をIDで検索
    // ==============================
    public Book findById(int id) {
        Book book = new Book();
        String sql = """
            SELECT 
                b.*, 
                e.review_tag, 
                g.genres_name
            FROM books b
            LEFT JOIN emotions e ON b.emotions_id = e.id
            LEFT JOIN genres g ON b.genre_id = g.id
            WHERE b.id = ?
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setEmotionsId(rs.getInt("emotions_id"));
                book.setGenreId(rs.getInt("genre_id"));
                book.setTitle(rs.getString("title"));
                book.setPublishedYear(rs.getString("published_year"));
                book.setBooksImage(rs.getString("books_image"));
                book.setStatus(rs.getString("status"));
                book.setProgress(rs.getInt("progress"));
                book.setStartedDay(rs.getString("started_day"));
                book.setFinishedDay(rs.getString("finished_day"));
                book.setText(rs.getString("text"));
                book.setCreateDay(rs.getString("create_day"));
                book.setReviewTag(rs.getString("review_tag"));
                book.setGenreName(rs.getString("genres_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    // ==============================
    // 本の情報を更新
    // ==============================
    public boolean update(Book book) {String sql = """
    	    UPDATE books SET
            title = ?,
            published_year = ?,
            genre_id = ?,
            emotions_id = ?,
            status = ?,
            progress = ?,
            started_day = ?,
            finished_day = ?,
            text = ?,
            create_day = ?
        WHERE id = ?
    """;

    try (Connection con = DBManager.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, book.getTitle());
        ps.setString(2, book.getPublishedYear());
        ps.setInt(3, book.getGenreId());
        ps.setInt(4, book.getEmotionsId());
        ps.setString(5, book.getStatus());
        ps.setInt(6, book.getProgress());

        // started_day
        if (book.getStartedDay() == null || book.getStartedDay().isEmpty()) {
            ps.setNull(7, java.sql.Types.DATE);
        } else {
            ps.setString(7, book.getStartedDay());
        }

        // finished_day ←ここが重要！
        if (book.getFinishedDay() == null || book.getFinishedDay().isEmpty()) {
            ps.setNull(8, java.sql.Types.DATE);
        } else {
            ps.setString(8, book.getFinishedDay());
        }

        ps.setString(9, book.getText());
        ps.setString(10, book.getCreateDay());
        ps.setInt(11, book.getId());

        int result = ps.executeUpdate();
        return result > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}