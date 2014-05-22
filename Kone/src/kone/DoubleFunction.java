package kone;

import javax.media.opengl.GL2;

import kone.core.Vector3d;
import kone.core.Vectord;

public class DoubleFunction {
	
	public Vectord input;
	public double output;
	public int numMeshPoints;
	public MeshPoints coords;
	
	public DoubleFunction()
	{
		input = new Vectord(1);
		output = 0.0d;
		numMeshPoints = 0;
	}
	
	public DoubleFunction(int dimension)
	{
		input = new Vectord(dimension);
		output = 0.0d;
		numMeshPoints = 50;
	}
	
	public double solve()
	{
		return output;
		
	}
	
	public MeshPoints CalculateMeshCoordinates(double min, double max)
	{
		double x, y, z;
		
		double interval = max - min;
		
		coords = new MeshPoints(numMeshPoints);
		
		for (int i = 0; i < numMeshPoints; i++)
		{
			x = min + i * interval / numMeshPoints;
			
			for (int j = 0; j < numMeshPoints; j++)
			{
				y = min + j* interval / numMeshPoints;
				
				Vectord pos = new Vectord(2);
				pos.entry[0] = x;
				pos.entry[1] = y;
				
				input = pos;
				
				z = solve();
								
				coords.meshX[i][j] = x;
				coords.meshY[i][j] = y;
				coords.meshZ[i][j] = z;
				
				
				
			}
		}
		
		return coords;
		
	}
	
	protected Vector3d CalcSurfaceNormal(double x1, double y1, double z1, double x2,
			double y2, double z2, double x3, double y3, double z3) {
		
		double d1x = x2 - x1;
		double d1y = y2 - y1;
		double d1z = z2 - z1;
		
		double d2x = x3 - x2;
		double d2y = y3 - y2;
		double d2z = z3 - z2;
		
		double crossx = d1y*d2z - d1z*d2y;
	    double crossy = d1y*d2x - d1x*d2z;
	    double crossz = d1x*d2y - d1y*d2x;
	    
	    double dist = Math.sqrt(crossx*crossx + crossy*crossy + crossz*crossz);
	    
	    return new Vector3d(crossx/dist, crossy/dist, crossz/dist);
	}
	
	
	public void render(GL2 gl2)
	{
		gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		//Draw two triangles between sets of 4 points
		for (int j = numMeshPoints-1; j > 0; j--)
		{
			for (int i = 1; i < numMeshPoints; i++)
			{
				
				Vector3d normal = CalcSurfaceNormal(coords.meshX[i-1][j-1], 
						coords.meshY[i-1][j-1],
						coords.meshZ[i-1][j-1],
						coords.meshX[i-1][j],
						coords.meshY[i-1][j],
						coords.meshZ[i-1][j],
						coords.meshX[i][j-1],
						coords.meshY[i][j-1],
						coords.meshZ[i][j-1]
						);
				
				
				
				gl2.glBegin(GL2.GL_TRIANGLES);
				
				/*
				gl2.glNormal3d(normal.x, normal.y, normal.z);
				gl2.glVertex3d(coords.meshX[i-1][j-1], coords.meshY[i-1][j-1], coords.meshZ[i-1][j-1]);
				gl2.glVertex3d(coords.meshX[i-1][j], coords.meshY[i-1][j], coords.meshZ[i-1][j]);
				gl2.glVertex3d(coords.meshX[i][j-1], coords.meshY[i][j-1], coords.meshZ[i][j-1]);
				*/
				
				gl2.glNormal3d(normal.x, normal.z, normal.y);
				gl2.glVertex3d(coords.meshX[i-1][j-1], coords.meshZ[i-1][j-1], coords.meshY[i-1][j-1]);
				gl2.glVertex3d(coords.meshX[i-1][j], coords.meshZ[i-1][j], coords.meshY[i-1][j]);
				gl2.glVertex3d(coords.meshX[i][j-1], coords.meshZ[i][j-1], coords.meshY[i][j-1]);
				
				/*
				gl2.glNormal3d(normal.x, normal.y, normal.z);
				gl2.glVertex3d(coords.meshX[i][j-1], coords.meshY[i][j-1], coords.meshZ[i][j-1]);
				gl2.glVertex3d(coords.meshX[i-1][j], coords.meshY[i-1][j], coords.meshZ[i-1][j]);
				gl2.glVertex3d(coords.meshX[i][j], coords.meshY[i][j], coords.meshZ[i][j]);
				
				*/
				gl2.glNormal3d(normal.x, normal.z, normal.y);
				gl2.glVertex3d(coords.meshX[i][j-1], coords.meshZ[i][j-1], coords.meshY[i][j-1]);
				gl2.glVertex3d(coords.meshX[i-1][j], coords.meshZ[i-1][j], coords.meshY[i-1][j]);
				gl2.glVertex3d(coords.meshX[i][j], coords.meshZ[i][j], coords.meshY[i][j]);
				gl2.glEnd();
				
			}
		}
		
		gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK,  GL2.GL_FILL);
	}

}
