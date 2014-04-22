package kone.core;

public class Matrixd {
	
	int numRows;
	int numColumns;
	int elements;
	
	public double[][] entry;
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}
	
	public Matrixd(int rows, int columns)
	{
		this.numRows = rows;
		this.numColumns = columns;
		elements = rows*columns;
		
		entry = new double[numRows][numColumns];
		
	}
	
	public Matrixd(Matrixd mat)
	{
		this.numRows = mat.getNumRows();
		this.numColumns = mat.getNumColumns();
		elements = numRows*numColumns;
		
		entry = new double[mat.getNumRows()][mat.getNumColumns()];
		
		for (int i = 0; i < mat.getNumRows(); i++)
		{
			for (int j = 0; j < mat.getNumColumns(); j++)
			{
				entry[i][j] = mat.entry[i][j];
				
			}
		}
		
	}

}
