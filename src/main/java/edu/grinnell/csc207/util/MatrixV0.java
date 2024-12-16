package edu.grinnell.csc207.util;

import edu.grinnell.csc207.util.AArray.AssociativeArray;
import edu.grinnell.csc207.util.AArray.KeyNotFoundException;
import edu.grinnell.csc207.util.AArray.NullKeyException;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Your Name Here
 * @author Samuel A. Rebelsky
 *
 * @param <T>
 *            The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * the width of the current matrix
   */
  int width;
  /**
   * the height of the current matrix
   */
  int height;
  /**
   * the default value to store within the matrix itself
   */
  T def = null;

  /**
   * the associative array storing our index value pairs
   */
  AssociativeArray<Index, T> pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the
   * given value as the default.
   *
   * @param width
   *               The width of the matrix.
   * @param height
   *               The height of the matrix.
   * @param def
   *               The default value, used to fill all the cells.
   *
   * @throws NegativeArraySizeException
   *                                    If either the width or height are
   *                                    negative.
   */

  public MatrixV0(int width, int height, T def) {
    this.width = width;
    this.height = height;
    this.def = def;

    this.pairs = new AssociativeArray<Index, T>();

  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with
   * null as the default value.
   *
   * @param width
   *               The width of the matrix.
   * @param height
   *               The height of the matrix.
   *
   * @throws NegativeArraySizeException
   *                                    If either the width or height are
   *                                    negative.
   */
  public MatrixV0(int width, int height) {
    this.width = width;
    this.height = height;
  } // MatrixV0

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row
   *   The row of the element.
   * @param col
   *   The column of the element.
   *
   * @return the value at the specified location.
   *
   * @throws IndexOutOfBoundsException
   *   If either the row or column is out of reasonable bounds.
   */
public T get(int row, int col) {
  try {
    return this.pairs.get(new Index(row, col));
  } catch (KeyNotFoundException e) {
    return this.def;
  } // try/catch
} // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row
   *            The row of the element.
   * @param col
   *            The column of the element.
   * @param val
   *            The value to set.
   *
   * @throws IndexOutOfBoundsException
   *                                   If either the row or column is out of
   *                                   reasonable bounds.
   */
  public void set(int row, int col, T val) {
    if ((row > 0) && (row < this.height) && ( col > 0) && (col < this.width)){
      try{this.pairs.set(new Index(row, col), val);
      } catch (NullKeyException e){
      }
      
      return;
    }
    else{
      throw new IndexOutOfBoundsException("The index you selected is not within the width and height of the matrix");
    }

  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height() {
    return this.height; // STUB
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  public int width() {
    return this.width; // STUB
  } // width()

  /**
   * Insert a row filled with the default value.
   *
   * @param row
   *            The number of the row to insert.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the row is negative or greater than the
   *                                   height.
   */
  public void insertRow(int row) {
    this.height ++;
    for(int col = 0;  col < this.width;  col ++){
      this.set(row, col, def);
    }
  } // insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row
   *             The number of the row to insert.
   * @param vals
   *             The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the row is negative or greater than the
   *                                   height.
   * @throws ArraySizeException
   *                                   If the size of vals is not the same as the
   *                                   width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException {
    this.height ++;

    for(int pairNum = 0; pairNum < this.pairs.size();  pairNum ++){
      Index pairIndex = this.pairs.getKey(pairNum);
      int r = pairIndex.getRow();
      if (r >= row){
        pairIndex.changeRow(r + 1);
      }
    }
  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col
   *            The number of the column to insert.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the column is negative or greater than
   *                                   the width.
   */
  public void insertCol(int col) {
    // STUB
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col
   *             The number of the column to insert.
   * @param vals
   *             The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the column is negative or greater than
   *                                   the width.
   * @throws ArraySizeException
   *                                   If the size of vals is not the same as the
   *                                   height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException {
    // STUB
  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row
   *            The number of the row to delete.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the row is negative or greater than or
   *                                   equal to the height.
   */
  public void deleteRow(int row) {
    // STUB
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col
   *            The number of the column to delete.
   *
   * @throws IndexOutOfBoundsException
   *                                   If the column is negative or greater than
   *                                   or equal to the width.
   */
  public void deleteCol(int col) {
    // STUB
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow
   *                 The top edge / row to start with (inclusive).
   * @param startCol
   *                 The left edge / column to start with (inclusive).
   * @param endRow
   *                 The bottom edge / row to stop with (exclusive).
   * @param endCol
   *                 The right edge / column to stop with (exclusive).
   * @param val
   *                 The value to store.
   *
   * @throw IndexOutOfBoundsException
   *        If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol,
      T val) {
    // STUB
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow
   *                 The row to start with (inclusive).
   * @param startCol
   *                 The column to start with (inclusive).
   * @param deltaRow
   *                 How much to change the row in each step.
   * @param deltaCol
   *                 How much to change the column in each step.
   * @param endRow
   *                 The row to stop with (exclusive).
   * @param endCol
   *                 The column to stop with (exclusive).
   * @param val
   *                 The value to store.
   *
   * @throw IndexOutOfBoundsException
   *        If the rows or columns are inappropriate.
   */
  public void fillLine(int startRow, int startCol, int deltaRow, int deltaCol,
      int endRow, int endCol, T val) {
    // STUB
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual
   * elements are mutable, mutating them in one matrix may affect the other
   * matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  public Matrix clone() {
    return this; // STUB
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other
   *              The object to compare.
   *
   * @return true if the other object is a matrix with the same width,
   *         height, and equal elements; false otherwise.
   */
  public boolean equals(Object other) {
    return this == other; // STUB
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object
   * that implements `equals` is expected to implement `hashCode` and
   * ensure that the hash codes for two equal objects are the same.
   *
   * @return the hash code.
   */
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } // if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
