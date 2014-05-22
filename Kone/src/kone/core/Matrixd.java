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
	
	public Vectord getRow(int row)
	{
		Vectord vec = new Vectord(this.getNumColumns());
		
		for (int i = 0; i < this.getNumColumns(); i++)
		{
			vec.entry[i] = this.entry[row][i];
		}
		
		return vec;
		
	}
	
	public Vectord getColumn(int column)
	{
		Vectord vec = new Vectord(this.getNumRows());
		
		for (int i = 0; i < this.getNumRows(); i++)
		{
			vec.entry[i] = this.entry[i][column];
		}
		
		return vec;
		
	}
	
	public void setRow(int row, double[] data)
	{
		for (int i = 0; i < this.getNumColumns(); i++)
		{
			this.entry[row][i] = data[i];
		}
	}
	
	public void setRow(int row, Vectord data)
	{
		for (int i = 0; i < this.getNumColumns(); i++)
		{
			this.entry[row][i] = data.entry[i];
		}
	}
	
	
	public void setColumn(int column, double[] data)
	{
		for (int i = 0; i < this.getNumRows(); i++)
		{
			this.entry[i][column] = data[i];
		}
	}
	
	public void setRow(int row, Double[] data)
	{
		for (int i = 0; i < this.getNumColumns(); i++)
		{
			this.entry[row][i] = (double) data[i];
		}
	}
	
	
	public void setColumn(int column, Double[] data)
	{
		for (int i = 0; i < this.getNumRows(); i++)
		{
			this.entry[i][column] = (double) data[i];
		}
	}
	
	public void setColumn(int column, Vectord data)
	{
		for (int i = 0; i < this.getNumRows(); i++)
		{
			this.entry[i][column] = data.entry[i];
		}
	}
	
	public void Add(Matrixd other)
	{
		if ((getNumRows() == other.getNumRows()) && (getNumColumns() == other.getNumColumns()))
		{
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < getNumColumns(); j++)
				{
					entry[i][j] += other.entry[i][j];
					
				}
			}
			
		}
		
	}
	
	public double Trace()
	{
		double sum = 0.0d;
		
		if (getNumRows() == getNumColumns())
		{
			for (int i = 0; i < getNumRows(); i++)
			{
				sum += entry[i][i];
				
			}
			
		}
		
		return sum;
		
	}
	
	public Matrixd Transpose()
	{
		// Transpose's rownumber is originals's columnnumber
		Matrixd mat = new Matrixd(getNumColumns(), getNumRows());
		
		for (int i = 0; i < getNumColumns(); i++)
		{
			for (int j = 0; j < getNumRows(); j++)
			{
				mat.entry[i][j] = entry[j][i];
			}
		}
		
		return mat;
	}
	
	public Matrixd Multiply(Matrixd other)
	{
		if (getNumColumns() == other.getNumRows())
		{
			Matrixd mat = new Matrixd(getNumRows(), other.getNumColumns());
			
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < other.getNumColumns(); j++)
				{
					double sum = 0.0d;
					
					for (int n = 0; n < getNumColumns(); n++)
					{
						sum += entry[i][n]*other.entry[n][j];
						
					}
						
					mat.entry[i][j] = sum;
					
				}
			}
			
			return mat;
			
		}
		
		return null;
	}
	
	public Vectord Multiply(Vectord vec)
	{
		if (getNumColumns() == vec.dimension)
		{
			Vectord ret = new Vectord(vec.dimension);
			
			for (int i = 0; i < getNumRows(); i++)
			{
				double sum = 0.0;
				
				for (int j = 0; j < getNumColumns(); j++)
				{
					sum += entry[i][j] * vec.entry[j];
					
				}
				
				ret.entry[i] = sum;
				
			}
			
			return ret;
		}
		
		return null;
	}
	
	public Matrixd TransposeWith(Matrixd other)
	{
		Matrixd b = other.Transpose();
		Matrixd a = Transpose();
		
		return b.Multiply(a);
	}
	
	public boolean isSymmetric()
	{
		
		if ( this.equals(Transpose()))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean equals(Matrixd other)
	{
		if ((getNumRows() == other.getNumRows()) && (getNumColumns() == other.getNumColumns()))
		{
			boolean eq = true;
			
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < getNumColumns(); j++)
				{
					if (entry[i][j] == other.entry[i][j])
					{
						eq = eq & true;
					}
					else
					{
						eq = eq & false;
					}
					
				}
			}
			
			return eq;
			
		}
		
		return false;
		
	}
	
	public static Matrixd Identity(int rows, int columns)
	{
		// Pad with zeroes
		Matrixd mat = new Matrixd(rows, columns);
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if (i == j)
				{
					mat.entry[i][j] = 1.0;
				}
			}
		}
		
		return mat;
		
		
	}
	
	public boolean isInverse(Matrixd inv)
	{
		if (getNumRows() == getNumColumns())
		{
			Matrixd pro1 = Multiply(inv);
			Matrixd pro2 = inv.Multiply(this);
			Matrixd id = Matrixd.Identity(numRows, numColumns);
			
			if (pro1.equals(pro2) && (pro1.equals(id)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}
	
	public boolean isOrthogonal()
	{
		Matrixd trans = Transpose();
		
		if (isInverse(trans))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		String ret = "";
		
		for (int i = 0; i < getNumRows(); i++)
		{
			ret += '\n';
		
			for (int j = 0; j < getNumColumns(); j++)
				ret += entry[i][j] + " ";
		}
		
		return ret;
	}
	

	

}
