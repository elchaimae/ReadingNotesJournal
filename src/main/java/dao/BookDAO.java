package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookDAO {

    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT b.*, e.review_tag, g.genres_name
            FROM books b
            LEFT JOIN emotions e ON b.emotions_id = e.id
            LEFT JOIN genres g ON b.genre_id = g.id
            ORDER BY b.id DESC
        """;

        try (Connection conn = DBManager.getConnection();   // ← ここで共通クラスを利用
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
 // あいうえお順で最大15冊取得
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
            ps.setInt(4, book.getPublishedYear());
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

}
