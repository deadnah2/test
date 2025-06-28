package entity.media.category;

import java.io.IOException;
import java.time.LocalDate;

import dao.DAOFactory;
import dao.ISpecificMediaDAO;
import utils.Config;
import views.screen.admin.create.CdCreateScreen;
import views.screen.admin.create.SpecificMediaCreateScreen;
import views.screen.admin.edit.SpecificMediaUpdateScreen;
import views.screen.item.CdDetailScreen;
import views.screen.item.SpecificMediaDetailScreen;
import exception.EmptyFieldsException;
import exception.InvalidDateException;
import views.screen.admin.edit.CdUpdateScreen;
import views.screen.admin.edit.SpecificMediaUpdateScreen;

public class CD extends SpecificMedia{
	private int id;
	private String artist;
	private String recordLabel;
	private String trackList;
	private String genre;
	private LocalDate releaseDate;
	
	public CD() {}

	public CD(String artist, String recordLabel, String trackList, String genre, LocalDate releaseDate) {
		super();
		this.artist = artist;
		this.recordLabel = recordLabel;
		this.trackList = trackList;
		this.genre = genre;
		this.releaseDate = releaseDate;
	}

	public CD(int id, int mediaId, String artist, String recordLabel, String trackList, String genre, LocalDate releaseDate) {
		super();
		this.id = id;
		this.mediaId = mediaId;
		this.artist = artist;
		this.recordLabel = recordLabel;
		this.trackList = trackList;
		this.genre = genre;
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public String getArtist() {
		return artist;
	}

	public String getRecordLabel() {
		return recordLabel;
	}

	public String getTrackList() {
		return trackList;
	}

	public String getGenre() {
		return genre;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void validateInfo() throws InvalidDateException, EmptyFieldsException {
		if (!validateEmptyFields()) throw new EmptyFieldsException("All fields must be filled");
		if (!validateDateField()) throw new InvalidDateException("Release date must be before today");
	}
	
	private boolean validateEmptyFields() {
		if (artist.isEmpty() || recordLabel.isEmpty() || trackList.isEmpty() || genre.isEmpty() || releaseDate==null) return false;
		return true;
	}
	
	private boolean validateDateField() {
		return releaseDate.isBefore(LocalDate.now());
	}
	
	@Override
	public ISpecificMediaDAO getSpecificMediaDAO() {
		return DAOFactory.getInstance().getCDDAO();
	}
	
	@Override
	public SpecificMediaCreateScreen getCreateScreen() throws IOException {
		return new CdCreateScreen(Config.CD_CREATE_SCREEN_PATH);
	}
	
	@Override
	public SpecificMediaDetailScreen getDetailScreen() throws IOException {
		// TODO Auto-generated method stub
		return new CdDetailScreen(Config.CD_DETAIL_SCREEN_PATH);
	}
	
	@Override
	public SpecificMediaUpdateScreen getUpdateScreen() throws IOException {
		return new CdUpdateScreen(Config.CD_CREATE_SCREEN_PATH);
	}
}
