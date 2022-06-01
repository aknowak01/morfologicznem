package pl.anstar.algorithm;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Skeletonization extends MorphologicalOperation {
	
	@Override
	public Mat calculate(Mat input) {
		Mat source = input.clone();
		Imgproc.cvtColor(source, source, Imgproc.COLOR_RGB2GRAY);
		source.convertTo(source, CvType.CV_8UC1);
		// int row = 0, col = 0;
		try {
			
			// source.convertTo(source, CvType.CV_8UC1);
			// Imgproc.threshold(source, source, 127, 255, CvType.CV_8UC1);
			// Imgproc.morphologyEx(source, source, Imgproc.MORPH_DILATE, source);
			int size = source.cols() * source.rows();
			Size size2 = new Size(source.cols(), source.rows());
			Mat skel = Mat.zeros(size2, CvType.CV_8UC1);
			// this.structureElement = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS,
			// new Size(3, 3));
			this.structureElement = new Mat(3, 3, CvType.CV_8UC1);
//			// @formatter:off
			this.structureElement.put(0, 0,
					1, 1, 1,
					0, 1, 0,
					0, 1, 0);
//			// @formatter:on
			boolean done = false;
			var eroded = Mat.zeros(size2, CvType.CV_8UC1);
			var temp = Mat.zeros(size2, CvType.CV_8UC1);
			
			while (!done) {
				Imgproc.erode(source, eroded, this.structureElement);
				Imgproc.dilate(eroded, temp, this.structureElement);
				this.results.add(eroded.clone());
				Core.subtract(source, temp, temp);
				Core.bitwise_or(skel, temp, skel);
				this.results.add(skel.clone());
				eroded.copyTo(source);
				var zeros = size - Core.countNonZero(source);
				if (zeros == size) {
					done = true;
				}
			}
			this.results.add(skel);
			return skel;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	@SuppressWarnings("unused")
	private void debug(Mat skel, Mat temp) {
		System.out.println(skel);
		System.out.println(temp);
	}
	
}
