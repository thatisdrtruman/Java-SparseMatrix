
/* 1935938
 *
 * Optionally, if you have any comments regarding your submission, put them here.
 * For instance, specify here if your program does not generate the proper output or does not do it in the correct manner.
 None of the matrix is outputing the matrix correctly. In fact it only output the last row of matrix entry, but apart from the number of entries the file has.
 At the moment, the add, read element and multiply vector do not work and I had run out of time to fix them. I am so dissaopointed of myself.
 */

import java.util.*;
import java.io.*;

// A class that represents a dense vector and allows you to read/write its elements
class DenseVector {
	private int[] elements;

	public DenseVector(int n) {
		elements = new int[n];
	}

	public DenseVector(String filename) {
		File file = new File(filename);
		ArrayList<Integer> values = new ArrayList<Integer>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextInt()) {
				values.add(sc.nextInt());
			}

			sc.close();

			elements = new int[values.size()];
			for (int i = 0; i < values.size(); ++i) {
				elements[i] = values.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read an element of the vector
	public int getElement(int idx) {
		return elements[idx];
	}

	// Modify an element of the vector
	public void setElement(int idx, int value) {
		elements[idx] = value;
	}

	// Return the number of elements
	public int size() {
		return (elements == null) ? 0 : (elements.length);
	}

	// Print all the elements
	public void print() {
		if (elements == null) {
			return;
		}

		for (int i = 0; i < elements.length; ++i) {
			System.out.println(elements[i]);
		}
	}
}

// A class that represents a sparse matrix
public class SparseMatrix {
	// Auxiliary function that prints out the command syntax
	public static void printCommandError() {
		System.err.println("ERROR: use one of the following commands");
		System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
		System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
		System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
		System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
		System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			printCommandError();
			System.exit(-1);
		}

		if (args[0].equals("-i")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			System.out.println("The matrix has " + mat.getNumRows() + " rows and " + mat.getNumColumns() + " columns");
			System.out.println("It has " + mat.numNonZeros() + " non-zeros");
		} else if (args[0].equals("-r")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
		} else if (args[0].equals("-t")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			SparseMatrix transpose_mat = mat.transpose();
			System.out.println();
			System.out.println("Matrix elements:");
			mat.print();
			System.out.println();
			System.out.println("Transposed matrix elements:");
			transpose_mat.print();
		} else if (args[0].equals("-a")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat1 = new SparseMatrix();
			mat1.loadEntries(args[1]);
			System.out.println("Read matrix 1 from " + args[1]);
			System.out.println("Matrix elements:");
			mat1.print();

			System.out.println();
			SparseMatrix mat2 = new SparseMatrix();
			mat2.loadEntries(args[2]);
			System.out.println("Read matrix 2 from " + args[2]);
			System.out.println("Matrix elements:");
			mat2.print();
			SparseMatrix mat_sum1 = mat1.add(mat2);

			System.out.println();
			mat1.multiplyBy(2);
			SparseMatrix mat_sum2 = mat1.add(mat2);

			mat1.multiplyBy(5);
			SparseMatrix mat_sum3 = mat1.add(mat2);

			System.out.println("Matrix1 + Matrix2 =");
			mat_sum1.print();
			System.out.println();

			System.out.println("Matrix1 * 2 + Matrix2 =");
			mat_sum2.print();
			System.out.println();

			System.out.println("Matrix1 * 10 + Matrix2 =");
			mat_sum3.print();
		} else if (args[0].equals("-v")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			DenseVector vec = new DenseVector(args[2]);
			DenseVector mv = mat.multiply(vec);

			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
			System.out.println();

			System.out.println("Read vector from " + args[2] + ":");
			vec.print();
			System.out.println();

			System.out.println("Matrix-vector multiplication:");
			mv.print();
		}
	}

	// Loading matrix entries from a text file
	// You need to complete this implementation
	public void loadEntries(String filename) {
		File file = new File(filename);

		try {
			Scanner sc = new Scanner(file);
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			entries = new ArrayList<Entry>();


			while (sc.hasNextInt()) {
				// Read the row index, column index, and value of an element
				int row = sc.nextInt();
				int col = sc.nextInt();
				int val = sc.nextInt();

				// Add your code here to add the element into data member entries
				int ind = row*numCols+col;
				sc.close();
				entries.add(new Entry(ind, val));
			}
			// Add your code here for sorting non-zero elements
			sc.close();
			MSortSep(entries, 0, this.numNonZeros()-1);
	}
		catch (Exception e){
			e.printStackTrace();
			numRows=0;
			numCols=0;
			entries=null;
		}
	}

	// Default constructor
	public SparseMatrix() {
		numRows = 0;
		numCols = 0;
		entries = null;
	}

	// A class representing a pair of column index and elements
	private class Entry {
		private int position; // Position within row-major full array representation
		private int value; // Element value

		// Constructor using the column index and the element value
		public Entry(int pos, int val) {
			this.position = pos;
			this.value = val;
		}

		// Copy constructor
		public Entry(Entry entry) {
			this(entry.position, entry.value);
		}

		// Read column index
		int getPosition() {
			return position;
		}

		// Set column index
		void setPosition(int pos) {
			this.position = pos;
		}

		// Read element value
		int getValue() {
			return value;
		}

		// Set element value
		void setValue(int val) {
			this.value = val;
		}
	}

	// Adding two matrices
	public SparseMatrix add(SparseMatrix M) {
		// Add your code here
		SparseMatrix resu= new SparseMatrix();

		resu.settingNumColumns(numCols);
		resu.settingNumRows(numRows);

		ArrayList<Entry> firstMatrix=this.copyArray();
		ArrayList<Entry> secondMatrix=M.copyArray();

		resu.entries=new ArrayList<Entry>();

		for (int i = firstMatrix.size(); i > 0; --i){

			if (firstMatrix.get(0).getPosition() == secondMatrix.get(0).getPosition()){
				resu.entries.add(new Entry(firstMatrix.get(0).getPosition(),
				firstMatrix.get(0).getValue()+ secondMatrix.get(0).getValue()));
				firstMatrix.remove(0);
				secondMatrix.remove(0);
			}
			else if (firstMatrix.get(0).getPosition()< secondMatrix.get(0).getPosition()){
				resu.entries.add(new Entry(firstMatrix.get(0)));
				firstMatrix.remove(0);
			}
			else if (firstMatrix.get(0).getPosition()> secondMatrix.get(0).getPosition()){
				resu.entries.add(new Entry(secondMatrix.get(0)));
				secondMatrix.remove(0);
			}
		}
		return resu;
	}

	// Transposing a matrix
	public SparseMatrix transpose() {
		// Add your code here
		SparseMatrix tposed = new SparseMatrix();
		tposed.numRows=this.getNumRows();
		tposed.numCols=this.getNumColumns();
		tposed.entries=this.copyArray();

		for (Entry etr : tposed.entries){
			int row= etr.getPosition() / numCols;
			int colm=etr.getPosition() % numCols;
			int newPos= colm * tposed.getNumColumns() + row;
			etr.setPosition(newPos);
		}
		tposed.MSortSep(tposed.entries, 0, tposed.entries.size()-1);
		return tposed;
	}

	// Matrix-vector multiplication
	public DenseVector multiply(DenseVector v) {
		// Add your code here
		SparseMatrix spsmat= new SparseMatrix();
		DenseVector desvec = new DenseVector(numRows);

		int currentRow=0;
		int[] buff=new int [numCols];

		spsmat.entries=this.copyArray();

		for (Entry etr: spsmat.entries){
			int row=etr.getPosition()/numCols;
			int col=etr.getPosition()%numCols;
			int val=etr.getValue();

			if (row==currentRow){
				buff[col]=val*v.getElement(col);
			}
			else{
				int prod=Arrays.stream(buff).sum();
				desvec.setElement(currentRow, prod);
				buff=new int[numCols];
				currentRow=row;
				buff[col]=val*v.getElement(col);
			}
		}
		if (buff.length !=0){
			int prod=Arrays.stream(buff).sum();
			desvec.setElement(currentRow, prod);
		}
		return desvec;
	}

	// Return the number of non-zeros
	public int numNonZeros() {
		// Add your code here
		return entries.size();
	}

	// Multiply the matrix by a scalar, and update the matrix elements
	public void multiplyBy(int scalar) {
		// Add your code here
		for (Entry etr:this.entries){
			etr.setValue(etr.getValue()*scalar);
		}
	}

	// Number of rows of the matrix
	public int getNumRows() {
		return this.numRows;
	}

	// Number of columns of the matrix
	public int getNumColumns() {
		return this.numCols;
	}

	public void settingNumRows(int numRows){
		this.numRows=numRows;
	}

	public void settingNumColumns(int numCols){
		this.numCols=numCols;
	}

	// Output the elements of the matrix, including the zeros
	// Do not modify this method
	public void print() {
		int n_elem = numRows * numCols;
		int pos = 0;

		for (int i = 0; i < entries.size(); ++i) {
			int nonzero_pos = entries.get(i).getPosition();

			while (pos <= nonzero_pos) {
				if (pos < nonzero_pos) {
					System.out.print("0 ");
				} else {
					System.out.print(entries.get(i).getValue());
					System.out.print(" ");
				}

				if ((pos + 1) % this.numCols == 0) {
					System.out.println();
				}

				pos++;
			}
		}

		while (pos < n_elem) {
			System.out.print("0 ");
			if ((pos + 1) % this.numCols == 0) {
				System.out.println();
			}

			pos++;
		}
	}

	private int numRows; // Number of rows
	private int numCols; // Number of columns
	private ArrayList<Entry> entries; // Non-zero elements

	public ArrayList<Entry> copyArray(){
			ArrayList<Entry> dat = new ArrayList<Entry>();
			for (Entry etr: this.entries){
				dat.add(new Entry(etr));
			}
			return dat;
	}

	public void MSortSep(ArrayList<Entry> messedArrayL, int bIndex, int fIndex) {
			if (bIndex<fIndex){
				int mid = (bIndex+fIndex)/2;
				MSortSep(messedArrayL, bIndex, mid);
				MSortSep(messedArrayL, mid+1,fIndex);
				MSort(messedArrayL, bIndex, mid, fIndex);
			}
		}
	public void MSort(ArrayList<Entry> messedArrayL, int partOne, int mPoint, int partFinal){
			int sizeNumOne=mPoint-partOne+1;
			int sizeFinal=partFinal-mPoint;
			ArrayList<Entry> tempOne= new ArrayList<Entry>();
			ArrayList<Entry> tempFinal= new ArrayList<Entry>();
			for (int i=0; i < sizeNumOne; ++i){
				tempOne.add(messedArrayL.get(partOne+i));
			}
			for (int j=0; j < sizeFinal; ++j){
				tempFinal.add(messedArrayL.get(mPoint	+ 1 + j));
			}

			int i=0,j=0;
			int mergeSub=partOne;

			while (i <sizeNumOne && j < sizeFinal){
				if (tempFinal.get(i).getPosition() <= tempFinal.get(j).getPosition()){
					messedArrayL.set(mergeSub, tempFinal.get(i));
					i++;
				} else {
					messedArrayL.set(mergeSub, tempFinal.get(j));
					j++;
				}
				mergeSub++;
				}

			while (i < sizeNumOne){
				messedArrayL.set(mergeSub, tempOne.get(i));
				i++;
				mergeSub++;
			}
			while (j < sizeNumOne){
				messedArrayL.set(mergeSub, tempOne.get(j));
				j++;
				mergeSub++;
			}
	}
}
