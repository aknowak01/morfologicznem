package pl.anstar.control;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.opencv.imgcodecs.Imgcodecs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import pl.anstar.MorfologiczneApplication;
import pl.anstar.algorithm.MorphologicalOperation.MorphologicalAlgorithmChoice;;

public class MorfEditController implements Initializable {
	
	@FXML
	private ImageGroupController imageGroupController;
	
	@FXML
	private RadioGroupController radioGroupController;
	
	@FXML
	ChoiceBox<String> algorithmChoiceBox;
	
	@FXML
	Button selectImageButton;
	@FXML
	Button resetButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageGroupController.parentController = this;
		radioGroupController.setParentControllers(this, this.imageGroupController);
		setUpChoiceBox();
	}
	
	@FXML
	private void imageSelect(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle(MorfologiczneApplication.getBundle().getString("key.selectImage"));
			List<String> list = Arrays.asList("*.bmp", "*.png", "*.jpg");
			fc.getExtensionFilters()
					.add(new FileChooser.ExtensionFilter("*.bmp *.png *.jpg", list));
			File selectedFile = fc.showOpenDialog(null);
			imageGroupController.setUpImages(Imgcodecs.imread(selectedFile.getAbsolutePath()));
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	private void reset() {
		imageGroupController.getSourceImageView().setImage(null);
		imageGroupController.getTargetImageView().setImage(null);
		imageGroupController.algorithmStepSliderLabel.setVisible(false);
	}
	
	void setUpChoiceBox() {
		ResourceBundle bundle = MorfologiczneApplication.getBundle();
		final String skel = bundle.getString("key.skel");
		final String centroid = bundle.getString("key.centroid");
		final String branch = bundle.getString("key.branch");
		String[] tab = { skel, centroid, branch };
		ObservableList<String> choices = FXCollections.observableArrayList(tab);
		algorithmChoiceBox.setItems(choices);
		algorithmChoiceBox.setValue(choices.get(0));
		algorithmChoiceBox.setOnAction(action -> {
			reset();
		});
	}
	
	public MorphologicalAlgorithmChoice getSelectedAlgorithm() {
		ResourceBundle bundle = MorfologiczneApplication.getBundle();
		String skel = bundle.getString("key.skel");
		String centroid = bundle.getString("key.centroid");
		String branch = bundle.getString("key.branch");
		String value = algorithmChoiceBox.getValue();
		System.out.println("GetSelectedAlgorithm: value: " + value);
		if (value.equals(skel)) {
			return MorphologicalAlgorithmChoice.SKEL;
		}
		if (value.equals(branch)) {
			return MorphologicalAlgorithmChoice.BRANCH;
		}
		if (value.equals(centroid)) {
			return MorphologicalAlgorithmChoice.CENTROID;
		}
		return null;
	}
	
}
