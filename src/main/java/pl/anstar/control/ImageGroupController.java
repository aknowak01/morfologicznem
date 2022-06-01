package pl.anstar.control;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pl.anstar.algorithm.MorphologicalOperation;
import pl.anstar.algorithm.MorphologicalOperation.MorphologicalAlgorithmChoice;
import pl.anstar.algorithm.Skeletonization;;

public class ImageGroupController implements Initializable {
	
	protected MorfEditController parentController;
	private static MorphologicalOperation currentOperation;
	
	public MorphologicalOperation getCurrentOperation() {
		return currentOperation;
	}
	
	@FXML
	Label algorithmStepSliderLabel;
	@FXML
	Slider algorithmStepSlider;
	int sliderValue;
	
	private void setUpSlider() {
		this.algorithmStepSliderLabel.setVisible(true);
		this.algorithmStepSlider.setMax(currentOperation.getAmountofResults() - 1);
		this.algorithmStepSlider.setValue(1);
		sliderValue = 1;
	}
	
	@FXML
	private void sliderDragDroppedListener(MouseEvent event) {
		if (algorithmStepSlider.getValue() == sliderValue) {
			return;
		}
		try {
			sliderValue = (int) algorithmStepSlider.getValue();
			this.targetMat = currentOperation.getResult(sliderValue);
			System.out.println(targetMat);
			System.out.println(currentOperation.getResult(sliderValue));
			System.out.println(sliderValue);
			this.targetImageView.setImage(mat2Img(targetMat));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	// Replace button (for testing as of now)
	@FXML
	Button replaceButton;
	
	@FXML
	private void fireReplaceButton(ActionEvent event) {
		this.sourceImageView.setImage(targetImageView.getImage());
		this.sourceMat.setTo(targetMat);
	}
	
	@FXML
	private ImageView sourceImageView;
	private Mat sourceMat;
	@FXML
	private ImageView targetImageView;
	private Mat targetMat;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.algorithmStepSliderLabel.setVisible(false);
	}
	
	public void setUpImages(Mat uploadedImage) {
		this.sourceMat = uploadedImage;
		this.sourceImageView.setImage(mat2Img(uploadedImage));
		calculateTarget(parentController.getSelectedAlgorithm());
		setUpSlider();
	}
	
	public void calculateTarget(MorphologicalAlgorithmChoice selectedAlgorithm) {
		targetMat = switch (selectedAlgorithm) {
		case BRANCH -> cutBranches(sourceMat);
		case CENTROID -> centroids(sourceMat);
		case SKEL -> skeletonization(sourceMat);
		};
		targetImageView.setImage(mat2Img(targetMat));
	}
	
	public static Mat skeletonization(Mat sourceImg) {
		Skeletonization skel = new Skeletonization();
		ImageGroupController.currentOperation = skel;
		skel.calculate(sourceImg);
		
		return skel.getCurrentResult();
	}
	
	public static Mat centroids(Mat sourceImg) {
		// TODO
		return null;
	}
	
	public static Mat cutBranches(Mat sourceImg) {
		// TODO
		return null;
	}
	
	ImageView getSourceImageView() {
		return sourceImageView;
	}
	
	ImageView getTargetImageView() {
		return targetImageView;
	}
	
	// Util function
	
	// private static Mat loadImage(String imagePath) {
	// return Imgcodecs.imread(imagePath);
	// }
	
	private static Image mat2Img(Mat mat) {
		MatOfByte bytes = new MatOfByte();
		Imgcodecs.imencode("img.bmp", mat, bytes);
		InputStream inputStream = new ByteArrayInputStream(bytes.toArray());
		return new Image(inputStream);
	}
}
