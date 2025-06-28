package views.screen.admin.create;

import java.io.IOException;
import javafx.fxml.FXML;
import entity.media.Media;
import views.screen.BaseScreen;

public abstract class SpecificMediaCreateScreen extends BaseScreen{
	protected Media media;
	public SpecificMediaCreateScreen(String screenPath) throws IOException {
		super(screenPath);
	}
	
	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
	
	@FXML
    protected abstract void handleCancelAction();

    @FXML
    protected abstract void handleSaveAction();
}
