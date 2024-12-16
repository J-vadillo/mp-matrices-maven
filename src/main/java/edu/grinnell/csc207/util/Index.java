package edu.grinnell.csc207.util;


/**
 * A simple class of an integer pair used to store information of an index of an element in a 2d array matrix
 * @author Jana Vadillo
 */
public class Index {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * row the index points twoards 
   */
  int row;
  /**
   * the size of the 
   */
  int column;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  public Index(int row,  int column) {
    this.row = row;
    this.column = column;
  } // Index(int, int)




  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

   public void resetRC (int r, int c){
    this.row = r;
    this.column = c;
   }

   public void changeRow (int r){
    this.row = r;
   }

   public void changeColumns(int c){
    this.column = c;
   }

   public boolean isIndex(int r, int c){
    return ((this.row == r) && (this.column == c));
   }


   public int getRow(){
    return (this.row);
   }


   public int getColumn(){
    return (this.column);
   }


  
} // class AssociativeArray
