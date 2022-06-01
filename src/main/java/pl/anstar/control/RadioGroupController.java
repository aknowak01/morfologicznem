package pl.anstar.control;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pl.anstar.MorfologiczneApplication;

public class RadioGroupController implements Initializable {
	
	private MorfEditController parentController;
	
	private ImageGroupController imageGroupController;
	
	@FXML
	private RadioButton languagePLRadio;
	@FXML
	private RadioButton languageENGRadio;
	@FXML
	private Label languageLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup languageRadioToggleGroup = new ToggleGroup();
		this.languagePLRadio.setToggleGroup(languageRadioToggleGroup);
		this.languageENGRadio.setToggleGroup(languageRadioToggleGroup);
		this.languagePLRadio.setSelected(true);
	}
	
	public void setParentControllers(MorfEditController mainController,
			ImageGroupController imageController) {
		this.imageGroupController = imageController;
		this.parentController = mainController;
	}
	
	@FXML
	private void setLanguageRadio(ActionEvent event) {
		RadioButton source = (RadioButton) event.getSource();
		if (source.equals(languagePLRadio)) {
			this.setLanguage("pl", "PL");
		} else if (source.equals(languageENGRadio)) {
			this.setLanguage("en", "UK");
		}
	}
	
	public void setLanguage(String langCode, String countryCode) {
		Locale locale = new Locale(langCode, countryCode);
		ResourceBundle bundle = MorfologiczneApplication.setBundle(locale);
		imageGroupController.replaceButton.setText(bundle.getString("key.replace"));
		parentController.selectImageButton.setText(bundle.getString("key.selectImage"));
		parentController.resetButton.setText(bundle.getString("key.reset"));
		parentController.setUpChoiceBox();
		MorfologiczneApplication.updateStageTitle(bundle);
	}
	
}