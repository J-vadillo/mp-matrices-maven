package edu.grinnell.csc207.util;

import java.util.Comparator;
import java.util.function.Predicate;

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
  // | helper method |
  // +--------------+

  private void rInBounds(int row) throws IndexOutOfBoundsException {
    if ((row < 0) || (row > (this.height - 1))) {
      throw new IndexOutOfBoundsException();
    } // check if its out of bounds
    return;
  }// RinBounds(row)

  private void cInBounds(int col) throws IndexOutOfBoundsException {
    if ((col < 0) || (col > (this.width - 1))) {
      throw new IndexOutOfBoundsException();
    } // check if its out of bounds
    return;
  }// cinBounds(int)

  /**
   * throws an index out of bounds if the row and col are out of bounds.
   * 
   * @param row row to check
   * @param col column to check
   * @throws IndexOutOfBoundsException when index is out of bounds.
   */
  private void inBounds(int row, int col) throws IndexOutOfBoundsException {
    if ((row < 0) || (col < 0) || (row > (this.height - 1)) || (col > (this.width - 1))) {
      throw new IndexOutOfBoundsException();
    } // check if its out of bounds
    return;
  }// inBounds(row, col)

  private void shiftRow(int shiftFrom, int modifier) throws IndexOutOfBoundsException {
    int start;
    Predicate<Integer> pred;
    int nMod;

    if (modifier > 0) {
      start = this.height - 1;
      pred = n -> n >= shiftFrom;
      nMod = -1;
    } else {
      start = shiftFrom;
      pred = n -> n < this.height;
      nMod = 1;
    } // define what direction to start in to avoid overwriting.

    if ((shiftFrom < 0) || (shiftFrom >= this.height)) {
      throw new IndexOutOfBoundsException();
    } // check in bounds

    for (int r = start; pred.test(r); r += nMod) {
      for (int c = 0; c < this.width; c++) {
        Index cIndex = new Index(r, c);
        Index newIndex = new Index(r + modifier, c);
        try {
          this.pairs.updateKey(cIndex, newIndex);

        } catch (Exception e) {
        } // try catch to only update as needed
      }
    }
  }

  private void shiftCol(int shiftFrom, int modifier) throws IndexOutOfBoundsException {
    int start;
    Predicate<Integer> pred;
    int nMod;

    if (modifier > 0) {
      start = this.width - 1;
      pred = n -> n >= shiftFrom;
      nMod = -1;
    } else {
      start = shiftFrom;
      pred = n -> n < this.width;
      nMod = 1;
    } // define what direction to start in to avoid overwriting.

    if ((shiftFrom < 0) || (shiftFrom >= this.width)) {
      throw new IndexOutOfBoundsException();
    } // check in bounds

    for (int c = start; pred.test(c); c += nMod) {
      for (int r = 0; r < this.width; r++) {
        Index cIndex = new Index(r, c);
        Index newIndex = new Index(r, c + modifier);
        try {
          this.pairs.updateKey(cIndex, newIndex);

        } catch (Exception e) {
        } // try catch to only update as needed
      }
    }
  }

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row
   *            The row of the element.
   * @param col
   *            The column of the element.
   *
   * @return the value at the specified location.
   *
   * @throws IndexOutOfBoundsException
   *                                   If either the row or column is out of
   *                                   reasonable bounds.
   */
  public T get(int row, int col) throws IndexOutOfBoundsException {
    inBounds(row, col);

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
  public void set(int row, int col, T val) throws IndexOutOfBoundsException {
    inBounds(row, col);
    try {
      this.pairs.set(new Index(row, col), val);
    } catch (NullKeyException e) {
    } // try catch to deal with the null key exception
  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height() {
    return this.height;
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
  public void insertRow(int row) throws IndexOutOfBoundsException {
    rInBounds(row);
    this.height++;
    shiftRow(row, 1);
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
  public void insertRow(int row, T[] vals) throws ArraySizeException, IndexOutOfBoundsException {
    if (vals.length != this.width) {
      throw new ArraySizeException();
    } // check size of input
    insertRow(row);
    for (int c = 0; c < this.width; c++) {
      set(row, c, vals[c]);
    } // itterate through and set the values in the list
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
    cInBounds(col);
    this.width++;
    shiftCol(col, 1);
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
    if (vals.length != this.height) {
      throw new ArraySizeException();
    } // check size of input
    insertCol(col);
    for (int r = 0; r < this.height; r++) {
      set(r, col, vals[r]);
    } // itterate through and set the values in the list
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
    rInBounds(row);

    for (int c = 0; c < this.width(); c++) {
      Index indx = new Index(row, c);
      try {
        this.pairs.remove(indx);
      } catch (Exception e) {
      }

    } // remove its elements from the list if they exist

    this.height--;
    shiftRow(row + 1, -1);

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
    cInBounds(col);

    for (int r = 0; r < this.width(); r++) {
      Index indx = new Index(r, col);
      try {
        this.pairs.remove(indx);
      } catch (Exception e) {
      }

    } // remove its elements from the list if they exist

    this.width--;
    shiftCol(col + 1, -1);
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
      T val) throws IndexOutOfBoundsException {
    inBounds(endRow, endCol);
    inBounds(startRow, startCol);
    if ((endRow < startRow) || (endCol < startCol)) {
      throw new IndexOutOfBoundsException();
    } // check if start and end will lead to an infinite

    for (int r = startRow; r < endRow; r++) {
      for (int c = startCol; c < endRow; c++) {
        this.set(r, c, val);
      } // iterate through columns
    } // iterate through rows
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

    inBounds(endRow, endCol);
    inBounds(startRow, startCol);
    Predicate<Integer> cPred;
    Predicate<Integer> rPred;

    if (deltaCol > 0){
      cPred = n -> n < endCol;
    }else{
      cPred = n -> n > endCol;
    }// set the correct  check depending on the slope

    if (deltaRow > 0){
      rPred = n -> n < endRow;
    }else{
      rPred = n -> n > endRow;
    }// set the correct row check depending on the slope.


    if (!(rPred.test(startRow) || cPred.test(startCol))) {
      throw new IndexOutOfBoundsException();
    } // check if start and end will lead to an infinite

    int r = startRow;
    int c = startCol;

    while ((rPred.test(r)) && (cPred.test(c))){
      set(r, c, val);
      r += deltaRow;
      c += deltaCol;
    }
  } // fillLine(int, int, int, int, int, int, T)

  public void changePairs(AssociativeArray<Index, T> newPair){
    this.pairs = newPair;
  }

  /**
   * A make a copy of the matrix. May share references (e.g., if individual
   * elements are mutable, mutating them in one matrix may affect the other
   * matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  public Matrix<T> clone() {
    MatrixV0<T> newClone = new MatrixV0<T>(this.width, this.height, this.def);
    newClone.changePairs(this.pairs);
    return newClone; 
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
    return ((this.hashCode() == other.hashCode()));
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
