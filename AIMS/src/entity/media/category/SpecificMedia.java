package entity.media.category;

import java.io.IOException;

import dao.ISpecificMediaDAO;
import views.screen.admin.create.SpecificMediaCreateScreen;
import views.screen.admin.edit.SpecificMediaUpdateScreen;
import views.screen.item.SpecificMediaDetailScreen;

public abstract class SpecificMedia {
	protected int mediaId;
	
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	
	public int getMediaId() {
		return mediaId;
	}
	
	public abstract ISpecificMediaDAO getSpecificMediaDAO();
	public abstract SpecificMediaCreateScreen getCreateScreen() throws IOException;
	public abstract SpecificMediaUpdateScreen getUpdateScreen() throws IOException;
	public abstract SpecificMediaDetailScreen getDetailScreen() throws IOException;
}
