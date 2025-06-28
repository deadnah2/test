package entity.media.category;

import java.io.IOException;
import java.time.LocalDate;

import dao.DAOFactory;
import dao.ISpecificMediaDAO;
import exception.EmptyFieldsException;
import exception.InvalidDateException;
import utils.Config;
import views.screen.admin.create.BookCreateScreen;
import views.screen.admin.create.DvdCreateScreen;
import views.screen.admin.create.SpecificMediaCreateScreen;
import views.screen.admin.edit.BookUpdateScreen;
import views.screen.admin.edit.SpecificMediaUpdateScreen;
import views.screen.item.DvdDetailScreen;
import views.screen.item.SpecificMediaDetailScreen;
import views.screen.admin.edit.DvdUpdateScreen;
import views.screen.admin.edit.SpecificMediaUpdateScreen;

public class DVD extends SpecificMedia{
	private int id;
	private String discType;
	private int runtime;
	private String studio;
	private String language;
	private String subtitle;
	private LocalDate releaseDate;
	private String genre;
	
	public DVD() {}

	public DVD(String discType, int runtime, String studio, String language, String subtitle, LocalDate releaseDate,
			String genre) {
		super();
		this.discType = discType;
		this.runtime = runtime;
		this.studio = studio;
		this.language = language;
		this.subtitle = subtitle;
		this.releaseDate = releaseDate;
		this.genre = genre;
	}

	public DVD(int id, int mediaId, String discType, int runtime, String studio, String language, String subtitle,
			LocalDate releaseDate, String genre) {
		super();
		this.id = id;
		this.mediaId = mediaId;
		this.discType = discType;
		this.runtime = runtime;
		this.studio = studio;
		this.language = language;
		this.subtitle = subtitle;
		this.releaseDate = releaseDate;
		this.genre = genre;
	}

	@Override
	public ISpecificMediaDAO getSpecificMediaDAO() {
		return DAOFactory.getInstance().getDVDDAO();
	}

	@Override
	public SpecificMediaCreateScreen getCreateScreen() throws IOException {
		return new DvdCreateScreen(Config.DVD_CREATE_SCREEN_PATH);
	}

	public int getId() {
		return id;
	}

	public String getDiscType() {
		return discType;
	}

	public int getRuntime() {
		return runtime;
	}

	public String getStudio() {
		return studio;
	}

	public String getLanguage() {
		return language;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public String getGenre() {
		return genre;
	}
	
	public void validateInfo() throws InvalidDateException, EmptyFieldsException {
		if (!validateEmptyFields()) throw new EmptyFieldsException("All fields, except substitle, must be filled");
		if (!validateDateField()) throw new InvalidDateException("Release date must be before today");
	}
	
	private boolean validateEmptyFields() {
		if (discType.isEmpty() || studio.isEmpty() || language.isEmpty() || genre.isEmpty() || releaseDate==null) return false;
		return true;
	}
	
	private boolean validateDateField() {
		return releaseDate.isBefore(LocalDate.now());
	}

	@Override
	public SpecificMediaDetailScreen getDetailScreen() throws IOException {
		// TODO Auto-generated method stub
		return new DvdDetailScreen(Config.DVD_DETAIL_SCREEN_PATH);
	}
	
	@Override
	public SpecificMediaUpdateScreen getUpdateScreen() throws IOException {
	    return new DvdUpdateScreen(Config.DVD_CREATE_SCREEN_PATH);
	}

}
