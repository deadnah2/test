package dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.ISpecificMediaDAO;
import entity.media.category.SpecificMedia;
import entity.media.category.Book;
import entity.media.category.CD;
import entity.media.category.DVD;

public class SqliteDvdDAO implements ISpecificMediaDAO {
	private Connection connection;

	public SqliteDvdDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public SpecificMedia getByMediaId(int media_id) throws SQLException {
		String sql = "select * from dvd where media_id=?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, media_id);
		ResultSet res = pstmt.executeQuery();
		DVD dvd = new DVD();
		if (res.next()) {
			int id = res.getInt("id");
			String discType = res.getString("disc_type");
			String runtime = res.getString("runtime");
			String studio = res.getString("studio");
			String language = res.getString("language");
			String subtitle = res.getString("subtitle");
			String releaseDate = res.getString("release_date");
			String genre = res.getString("genre");
			
			int runTime = Integer.parseInt(runtime);
			dvd = new DVD(id, media_id, discType, runTime, studio, language, subtitle, LocalDate.parse(releaseDate), genre);
		}
		return dvd;
	}

	@Override
	public void deleteByMediaId(int media_id) throws SQLException {
		String query = "delete from dvd where media_id = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, media_id);
		stmt.executeUpdate();
	}

	@Override
	public void create(SpecificMedia specificMedia) throws SQLException {
		DVD dvd = (DVD)specificMedia;
		String sql = "INSERT INTO dvd (media_id, disc_type, runtime, studio, language, subtitle, release_date, genre) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, dvd.getMediaId());
        pstmt.setString(2, dvd.getDiscType());
        pstmt.setInt(3, dvd.getRuntime());
        pstmt.setString(4, dvd.getStudio());
        pstmt.setString(5, dvd.getLanguage());
        pstmt.setString(6, dvd.getSubtitle());
        pstmt.setString(7, dvd.getReleaseDate().toString());
        pstmt.setString(8, dvd.getGenre());
        pstmt.executeUpdate();
        System.out.println("Create successfully");
		
	}
	
	@Override
	public void updateMediaById(SpecificMedia specificMedia) throws SQLException {
		try{
	    	String sql = "UPDATE dvd SET disc_type=?, runtime=?, studio=?, language=?, "
	    							+ "subtitle=?, release_date=?, genre=? WHERE media_id=?";
	    	DVD dvd = (DVD)specificMedia;
		    PreparedStatement stmt = connection.prepareStatement(sql);
		    stmt.setString(1, dvd.getDiscType());
		    stmt.setInt(2, dvd.getRuntime());
		    stmt.setString(3, dvd.getStudio());
		    stmt.setString(4, dvd.getLanguage());
		    stmt.setString(5, dvd.getSubtitle());
		    stmt.setString(6, dvd.getReleaseDate().toString());
		    stmt.setString(7, dvd.getGenre());
		    stmt.setInt(8, dvd.getMediaId());
		    
		    stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
		
	}
}
