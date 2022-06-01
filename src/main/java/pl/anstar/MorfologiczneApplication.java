package pl.anstar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MorfologiczneApplication extends Application {
	
	private static Scene currentScene;
	private static Stage mainStage;
	private static ResourceBundle bundle;
	
	@Override
	public void start(Stage stage) throws IOException {
		bundle = ResourceBundle.getBundle("internationalization/lang",
				new Locale("pl_PL"));
		currentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/control/MorfEdit.fxml")), bundle);
		mainStage = stage;
		stage.setHeight(800);
		stage.setWidth(1400);
		stage.setScene(currentScene);
		stage.setTitle(bundle.getString("window.title"));
		stage.show();
		letterbox(currentScene, (Pane) currentScene.getRoot());
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	private void letterbox(final Scene scene, final Pane contentPane) {
		final double initWidth = scene.getWidth();
		final double initHeight = scene.getHeight();
		final double ratio = initWidth / initHeight;
		SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight,
				initWidth, contentPane);
		scene.widthProperty().addListener(sizeListener);
		scene.heightProperty().addListener(sizeListener);
	}
	
	public static void updateStageTitle(ResourceBundle bundle) {
		mainStage.setTitle(bundle.getString("window.title"));
	}
	
	/**
	 * 
	 * @return ResouceBundle associated with selected language locale properties
	 */
	public static ResourceBundle getBundle() {
		return bundle;
	}
	
	public static ResourceBundle setBundle(Locale locale) {
		bundle = ResourceBundle.getBundle("internationalization/lang",
				locale);
		return bundle;
	}
	
}
