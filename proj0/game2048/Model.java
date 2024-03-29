package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        board.setViewingPerspective(side);

        for (int col = 0; col < 4; col++) {
            if (rowArrange(col)) {
                changed = true;
            }
        }
        board.setViewingPerspective(Side.NORTH);
//        for(int col =0; col < board.size(); col++){
//            for(int row = 0; row < board.size(); row++){
//                Tile t = board.tile(col, row);
//                if(t != null){
//                    board.move(col, 3, t);
//                    changed = true;
//                    score += 7;
//                }
//            }
//        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    public boolean rowArrange(int col) {
        int[] rowState = new int[4];
        rowStateInit(col, rowState);
        return selectTile(col, rowState);
    }

    public void rowStateInit(int col, int[] rowState) {
        for (int i = 0; i < 4; i++) {
            if (this.board.tile(col, i) != null) {
                rowState[i] = this.board.tile(col, i).value();
            } else {
                rowState[i] = 0;
            }
        }
    }

    public void rowStateUpdate(int col, int[] rowState) {
        for (int i = 0; i < 4; i++) {
            if (this.board.tile(col, i) != null) {
                rowState[i] = this.board.tile(col, i).value();
            } else if (rowState[i] != -1) {
                rowState[i] = 0;
            }
        }
    }

    public boolean selectTile(int col, int[] rowState) {
        boolean changed = false;
        for (int row = 2; row >= 0; row--) {
            if (this.board.tile(col, row) != null) {
                if (rowMove(col, row, rowState)) {
                    changed = true;
                }
            }
        }
        return changed;
    }

    public boolean rowMove(int col, int row, int[] rowState) {
        boolean changed = false;
        int moveToRow = rowMoveCalculate(col, row, rowState);
        if (row != moveToRow) {
            changed = true;
            this.board.move(col, moveToRow, this.board.tile(col, row));
            rowStateUpdate(col, rowState);
        }
        return changed;
    }

    public int rowMoveCalculate(int col, int row, int[] rowState) {
        int pointer = row;
        int value = board.tile(col, row).value();
        for (int i = pointer + 1; i < 4; i++) {
            if (rowState[i] == 0) {
                pointer = i;
            } else if (rowState[i] == value) {
                pointer = i;
                this.score += rowState[i] * 2;
                rowState[i - 1] = -1;
                break;
            } else if (rowState[i] == -1) {
                pointer = i;
                break;
            } else {
                break;
            }
        }
        return pointer;
    }

//        for(int i = 3; i > row; i--){
//            if (rowState[i] == 0) {
//                if (i != 3) {
//                    if (mergeCheck(i, rowState)) {
//                        rowState[i + 1] = rowState[i + 1] * 2;
//                        this.board.move(col, i + 1, this.board.tile(col, row));
//                        rowState[row] = 0;
//                        this.score += rowState[i];
//                        rowState[i + 1] = -1;
//                        break;
//                    } else {
//                        rowState[i] = this.board.tile(col, row).value();
////                        System.out.println("value is" + this.board.tile(col, row).value());
//                        this.board.move(col, i, this.board.tile(col, row));
//                        rowState[row] = 0;
//                        break;
//                    }
//                } else {
//                    rowState[i] = this.board.tile(col, row).value();
//                    this.board.move(col, i, this.board.tile(col, row));
//                    rowState[row] = 0;
//                    break;
//                }
//            } else if (rowState[i] == -1) {
//                continue;
//            } else if (rowState[i] == this.board.tile(col, row).value()){
//                rowState[i] = rowState[i] * 2;
//                this.score += rowState[i];
//                rowState[i] = -1;
//                this.board.move(col, i, this.board.tile(col, row));
//                rowState[row] = 0;
//                break;
//            }else {
//                changed = false;
//            }
//        }
//        return changed;
//    }
//
//    public boolean mergeCheck(int currentRow, int[] rowState){
//        if(rowState[currentRow + 1] == rowState[currentRow]){
//            return true;
//        }else{
//            return false;
//        }
//    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int size = b.size();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(b.tile(i, j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int size = b.size();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(b.tile(i, j)!= null && b.tile(i, j).value() == MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if(emptySpaceExists(b)){
            return true;
        }else{
            int size = b.size();
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(equalValueExist(b, i, j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean equalValueExist(Board b, int col, int row){
        int size = b.size();
        int value = b.tile(col, row).value();
        if(col + 1 < size && b.tile(col + 1, row).value() == value){
            return true;
        }else if(col - 1 > 0 && b.tile(col - 1, row).value() == value){
            return true;
        }else if(row + 1 < size && b.tile(col, row + 1).value() == value){
            return true;
        }else if(row - 1 > 0 && b.tile(col, row - 1).value() == value){
            return true;
        }else{
            return false;
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
