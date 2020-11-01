import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Adham Ibrahim on Oct 5, 2020
 */

public class SeamCarver {

	//the picture to compute on
	private Picture picture;
	
	//the energy of a border pixel, larger than any non border energy
	private final double BORDER_ENERGY;
	
	//store the energies to avoid excessive recomputation
	private double[][] energy;
	
	public SeamCarver(Picture picture) {
		//enforce immutability
		this.picture = new Picture(picture);

		BORDER_ENERGY = 1000.0;
		
		energy = new double[width()][height()];
		
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < height(); y++) {
				recalculateEnergy(x, y);
			}
		}
	}
	
	public Picture picture() {
		return this.picture;
	}
	
	public int width() {
		return this.picture.width();
	}
	
	public int height() {
		return this.picture.height();
	}
	
	private boolean isValidCoordinate(int x, int y) {
		return x >= 0 && x < width() &&
				y >= 0 && y < height();
	}
	
	private void validateCoordinate(int x, int y) {
		if (!isValidCoordinate(x, y))
			throw new IllegalArgumentException("Pixel out of range: " + x + " " + y);
	}
	
	private void recalculateEnergy(int x, int y) {
		validateCoordinate(x, y);
		energy[x][y] = energy(x, y);
	}
	
	public double energy(int x, int y) {
		validateCoordinate(x, y);
		
		if (x == 0 || x == width()-1 || 
			y == 0 || y == height()-1) return BORDER_ENERGY;
		
		int leftRGB = picture.getRGB(x-1, y), rightRGB = picture.getRGB(x+1, y);
		
		double Rx = ((leftRGB >> 16) & 255) - ((rightRGB >> 16) & 255);
		double Gx = ((leftRGB >>  8) & 255) - ((rightRGB >>  8) & 255);
		double Bx = ((leftRGB >>  0) & 255) - ((rightRGB >>  0) & 255);
		
		double deltaXSquared = Rx * Rx + Gx * Gx + Bx * Bx;
		
		int aboveRGB = picture.getRGB(x, y-1), belowRGB = picture.getRGB(x, y+1);
		
		double Ry = ((aboveRGB >> 16) & 255) - ((belowRGB >> 16) & 255);
		double Gy = ((aboveRGB >>  8) & 255) - ((belowRGB >>  8) & 255);
		double By = ((aboveRGB >>  0) & 255) - ((belowRGB >>  0) & 255);
		
		double deltaYSquared = Ry * Ry + Gy * Gy + By * By;
		
		return Math.sqrt(deltaXSquared + deltaYSquared);
	}
	
	private void transpose() {
		
	}
	
	public void removeHorizontalSeam(int[] seam) {
		if (seam.length != width()) throw new IllegalArgumentException("Tried to remove seam of length " + seam.length + " from picture with width of " + width());
		
		Picture tempPicture = new Picture(width(), height()-1);
		double[][] tempEnergy = new double[width()][height()-1];
		
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < seam[x]; y++) {
				tempPicture.setRGB(x, y, picture.getRGB(x, y));
				tempEnergy[x][y] = energy[x][y];
			}
			
			for (int y = seam[x]+1; y < height(); y++) {
				tempPicture.setRGB(x, y-1, picture.getRGB(x, y));
				tempEnergy[x][y-1] = energy[x][y];
			}
		}
		
		this.picture = tempPicture;
		this.energy = tempEnergy;
		
		for (int x = 0; x < width(); x++) {
			if (isValidCoordinate(x, seam[x]-1)) 
				recalculateEnergy(x, seam[x]-1);
			
			if (isValidCoordinate(x, seam[x]))
				recalculateEnergy(x, seam[x]);
		}
	}
	
	public void removeVerticalSeam(int[] seam) {
		//TODO transpose then do horizontal seam
	}
	
	private void printEnergy() {
		for (int y = 0; y < height(); y++) {
			for (int x = 0; x < width(); x++) {
				StdOut.printf("%5.0f ", energy[x][y]);
			}
			StdOut.println();
		}
	}
	
	public static void main(String[] args) {
		String file = "assets/3x4.png";
		
		SeamCarver sc = new SeamCarver(new Picture(file));
		
		StdOut.println(sc.picture());
		sc.printEnergy();
		
		sc.removeVerticalSeam(new int[] {0, 1, 1, 2});
		
		StdOut.println(sc.picture());
		sc.printEnergy();
	}
	
}