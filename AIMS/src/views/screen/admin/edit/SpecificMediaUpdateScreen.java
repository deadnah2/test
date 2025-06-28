package views.screen.admin.edit;

import java.io.IOException;
import javafx.fxml.FXML;
import entity.media.Media;
import views.screen.BaseScreen;

public abstract class SpecificMediaUpdateScreen extends BaseScreen{
	protected Media media;
	public SpecificMediaUpdateScreen(String screenPath) throws IOException {
		super(screenPath);
	}
	
	public Media getMedia() {
		return media;
	}
	
	public SpecificMediaUpdateScreen setMedia(Media media) {
		this.media = media;
		return this;
	}
	
	public abstract SpecificMediaUpdateScreen initScreen();
	
	@FXML
    protected abstract void handleCancelAction();

    @FXML
    protected abstract void handleSaveAction();
}
