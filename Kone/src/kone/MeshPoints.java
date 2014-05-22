package kone;

public class MeshPoints {
	
	double meshX[][];
	double meshY[][];
	double meshZ[][];
	
	public MeshPoints(int maxGridPoints)
	{
		meshX = new double[maxGridPoints][maxGridPoints];
		meshY = new double[maxGridPoints][maxGridPoints];
		meshZ = new double[maxGridPoints][maxGridPoints];
		
	}

}