package pl.anstar.algorithm;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.opencv.core.Mat;

import pl.anstar.MorfologiczneApplication;

public abstract class MorphologicalOperation {
	
	public enum MorphologicalAlgorithmChoice {
		SKEL, BRANCH, CENTROID;
		
		@Override
		public String toString() {
			ResourceBundle languageBundle = MorfologiczneApplication.getBundle();
			String keyString = "key." + super.toString().toLowerCase();
			if (languageBundle.containsKey(keyString)) {
				return languageBundle.getString(keyString);
			}
			return super.toString().toLowerCase();
		}
	}
	
	protected ArrayList<Mat> results = new ArrayList<>();
	protected int currentIter;
	
	public int getCurrentIter() {
		return currentIter;
	}
	
	public Mat getCurrentResult() {
		return results.get(this.getCurrentIter());
	}
	
	public Mat getFinalResult() {
		return results.get(results.size() - 1);
	}
	
	public Mat getResult(int index) {
		return results.get(index);
	}
	
	public int getAmountofResults() {
		return results.size();
	}
	
	protected Mat structureElement;
	
	public Mat getStructureElement() {
		return structureElement;
	}
	
	public void setStructureElement(Mat structureElement) {
		this.structureElement = structureElement;
	}
	
	public abstract Mat calculate(Mat source);
}
