package kone.core;

public class MatrixComplex {
	
	int numRows;
	int numColumns;
	int elements;
	
	public Complex[][] entry;
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}
	
	public MatrixComplex(int rows, int columns)
	{
		this.numRows = rows;
		this.numColumns = columns;
		elements = rows*columns;
		
		entry = new Complex[numRows][numColumns];
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				entry[i][j] = new Complex();
				
			}
		}
		
	}
	
	public MatrixComplex(MatrixComplex mat)
	{
		this.numRows = mat.getNumRows();
		this.numColumns = mat.getNumColumns();
		elements = numRows*numColumns;
		
		entry = new Complex[mat.getNumRows()][mat.getNumColumns()];
		
		for (int i = 0; i < mat.getNumRows(); i++)
		{
			for (int j = 0; j < mat.getNumColumns(); j++)
			{
				entry[i][j] = mat.entry[i][j];
				
			}
		}
		
	}
	
	public void Add(MatrixComplex other)
	{
		if ((getNumRows() == other.getNumRows()) && (getNumColumns() == other.getNumColumns()))
		{
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < getNumColumns(); j++)
				{
					entry[i][j].Add(other.entry[i][j]);
					
				}
			}
			
		}
		
	}
	
	public Complex Trace()
	{
		Complex sum = new Complex();
		
		if (getNumRows() == getNumColumns())
		{
			for (int i = 0; i < getNumRows(); i++)
			{
				sum.Add(entry[i][i]);
				
			}
			
		}
		
		return sum;
		
	}
	
	public MatrixComplex Transpose()
	{
		// Transpose's rownumber is originals's columnnumber
		MatrixComplex mat = new MatrixComplex(getNumColumns(), getNumRows());
		
		for (int i = 0; i < getNumColumns(); i++)
		{
			for (int j = 0; j < getNumRows(); j++)
			{
				mat.entry[i][j] = entry[j][i];
			}
		}
		
		return mat;
	}
	
	public MatrixComplex Multiply(MatrixComplex other)
	{
		if (getNumColumns() == other.getNumRows())
		{
			MatrixComplex mat = new MatrixComplex(getNumRows(), other.getNumColumns());
			
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < other.getNumColumns(); j++)
				{
					Complex sum = new Complex();
					
					for (int n = 0; n < getNumColumns(); n++)
					{
						sum.Add(entry[i][n].Multiply(other.entry[n][j]));
						
					}
						
					mat.entry[i][j] = sum;
					
				}
			}
			
			return mat;
			
		}
		
		return null;
	}
	
	public MatrixComplex TransposeWith(MatrixComplex other)
	{
		MatrixComplex b = other.Transpose();
		MatrixComplex a = Transpose();
		
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
	
	public boolean equals(MatrixComplex other)
	{
		if ((getNumRows() == other.getNumRows()) && (getNumColumns() == other.getNumColumns()))
		{
			boolean eq = true;
			
			for (int i = 0; i < getNumRows(); i++)
			{
				for (int j = 0; j < getNumColumns(); j++)
				{
					if (entry[i][j].equals(other.entry[i][j]))
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
	
	public MatrixComplex Adjoint()
	{
		MatrixComplex trans = Transpose();
		
		for (int i = 0; i < trans.getNumRows(); i++)
		{
			for (int j = 0; j < trans.getNumColumns(); j++)
			{
				trans.entry[i][j] = trans.entry[i][j].Conjugate();
			}
		}
		
		return trans;
		
	}
	
	public MatrixComplex AdjointWith(MatrixComplex other)
	{
		MatrixComplex b = other.Adjoint();
		MatrixComplex a = Adjoint();
		
		return b.Multiply(a);
		
	}
	
	public boolean isHermitian()
	{
		if (getNumRows() == getNumColumns())
		{
			MatrixComplex adjoint = Adjoint();
			
			if (equals(adjoint))
			{
				return true;
			}
			else
			{
				return false; // if adjoint is not same
			}
		}
		
		return false; // if not square matrix;
	}
	
	public static MatrixComplex Identity(int rows, int columns)
	{
		// Pad with zeroes
		MatrixComplex mat = new MatrixComplex(rows, columns);
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if (i == j)
				{
					mat.entry[i][j] = new Complex(1, 0);
				}
			}
		}
		
		return mat;
		
		
	}
	
	public boolean isInverse(MatrixComplex inv)
	{
		if (getNumRows() == getNumColumns())
		{
			MatrixComplex pro1 = Multiply(inv);
			MatrixComplex pro2 = inv.Multiply(this);
			MatrixComplex id = MatrixComplex.Identity(numRows, numColumns);
			
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
	
	public boolean isUnitary()
	{
		MatrixComplex adjoint = Adjoint();
		
		if (isInverse(adjoint))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public double QuadraticForm(MatrixComplex V)
	{
		Complex sum = new Complex();
		
		for (int i = 0; i < getNumRows(); i++)
		{
			for (int j = 0; j < getNumColumns(); j++)
			{
				/*
				Complex tmp = V.entry[i][0];
				tmp = tmp.Conjugate();
				tmp = tmp.Multiply(entry[i][j]);
				tmp = tmp.Multiply(V.entry[j][0]);
				
				sum = sum.Add(tmp);
				*/
				sum = sum.Add(V.entry[i][0].Conjugate().Multiply(entry[i][j]).Multiply(V.entry[j][0]));
				
			}
		}
		
		if (sum.imag != 0.0)
		{
			System.out.println("Imaginary component is not zero");
		}
		
		return sum.real;
		
	}
	
	public boolean isNonNegative(MatrixComplex V)
	{
		double quadraticform = QuadraticForm(V);
		if (quadraticform >= 0.0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isPositive(MatrixComplex V)
	{
		double quadraticform = QuadraticForm(V);
		if (quadraticform > 0.0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public double ScalarProduct(MatrixComplex other)
	{
		MatrixComplex mat = new MatrixComplex(this);
		mat = mat.Adjoint();
		mat = mat.Multiply(other);
		
		return mat.Trace().real;
	}
	

}
