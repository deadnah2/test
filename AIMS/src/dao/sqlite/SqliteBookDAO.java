package dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ISpecificMediaDAO;
import entity.media.Media;
import entity.media.category.Book;
import entity.media.category.SpecificMedia;

public class SqliteBookDAO implements ISpecificMediaDAO{
	private Connection connection;

	public SqliteBookDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public SpecificMedia getByMediaId(int media_id) throws SQLException {
		String sql = "select * from Book where media_id=?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, media_id);
		ResultSet res = pstmt.executeQuery();
		Book book = new Book();
		if (res.next()) {
			int id = res.getInt("id");
			String author = res.getString("author");
			String publisher = res.getString("publisher");
			String coverType = res.getString("cover_type");
			String publicationDate = res.getString("publication_date");
			int pages = res.getInt("pages");
			String genre = res.getString("genre");
			String language = res.getString("language");
			
			book = new Book(id, media_id, author, publisher, coverType, LocalDate.parse(publicationDate), pages, genre, language);
		}
		return book;
	}
	
	@Override
	public void create(SpecificMedia specificMedia) throws SQLException {
		Book book = (Book)specificMedia;
		String sql = "INSERT INTO book (media_id, author, publisher, cover_type, publication_date, pages, genre, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, book.getMediaId());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getPublisher());
        pstmt.setString(4, book.getCoverType());
        pstmt.setString(5, book.getPublicationDate().toString());
        pstmt.setInt(6, book.getPages());
        pstmt.setString(7, book.getGenre());
        pstmt.setString(8, book.getLanguage());
        pstmt.executeUpdate();
	}

	@Override
	public void deleteByMediaId(int media_id) throws SQLException {
		String query = "delete from Book where media_id = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, media_id);
		stmt.executeUpdate();
	}
	
	@Override
	public void updateMediaById(SpecificMedia specificMedia) throws SQLException {
		try{
	    	String sql = "UPDATE Book SET author=?, publisher=?, cover_type=?, publication_date=?, "
	    							+ "pages=?, genre=?, language=? WHERE media_id=?";
	    	Book book = (Book)specificMedia;
		    PreparedStatement stmt = connection.prepareStatement(sql);
		    stmt.setString(1, book.getAuthor());
		    stmt.setString(2, book.getPublisher());
		    stmt.setString(3, book.getCoverType());
		    stmt.setString(4, book.getPublicationDate().toString());
		    stmt.setInt(5, book.getPages());
		    stmt.setString(6, book.getGenre());
		    stmt.setString(7, book.getLanguage());
		    stmt.setInt(8, book.getMediaId());
		    
		    stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
		
	}
	
}
