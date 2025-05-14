package controller;
import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

import java.io.File;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;



    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }

    public boolean doMove(int row, int col, Direction direction) {
        if (model.getId(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    return true;
                }
            }
        }
        //橫向占2格的情況
        if(model.getId(row, col) == 2) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if(model.checkInHeightSize(nextRow)){
                if(direction == Direction.RIGHT){
                    if(model.checkInWidthSize( nextCol + 1)){
                        if(model.getId(nextRow, nextCol + 1) == 0){
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[nextRow][nextCol + 1] = 2;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                if(direction == Direction.LEFT){
                    if(model.checkInWidthSize(nextCol)){
                         if(model.getId(nextRow, nextCol) == 0){
                             model.getMatrix()[row][col+1] = 0;
                             model.getMatrix()[nextRow][nextCol] = 2;
                             BoxComponent box = view.getSelectedBox();
                             box.setRow(nextRow);
                             box.setCol(nextCol);
                             box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                             box.repaint();
                             return true;
                         }
                         return false;
                    }
                    return false;
                }
                if(direction == Direction.UP||direction == Direction.DOWN){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow,nextCol+1)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row][col+1] = 0;
                        model.getMatrix()[nextRow][nextCol] = 2;
                        model.getMatrix()[nextRow][nextCol+1] = 2;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;

            }
        }

        //纵向占2格的情况
        if(model.getId(row, col) == 3){
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if(model.checkInWidthSize(nextCol)){
                if(direction == Direction.UP){
                    if(model.checkInHeightSize(nextRow)){
                        if(model.getId(nextRow, nextCol) == 0){
                            model.getMatrix()[row+1][col] = 0;
                            model.getMatrix()[nextRow][nextCol] = 3;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                if(direction == Direction.DOWN){
                    if(model.checkInHeightSize(nextRow+1)){
                        if(model.getId(nextRow+1, nextCol) == 0){
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[nextRow+1][nextCol] = 3;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                if(direction == Direction.LEFT||direction == Direction.RIGHT){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow+1, nextCol)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row+1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 3;
                        model.getMatrix()[nextRow+1][nextCol] = 3;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        }

        //占4格的情况
        if(model.getId(row, col) == 4){
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if(direction == Direction.UP||direction == Direction.DOWN){
                if(model.checkInHeightSize(nextRow)&&model.checkInHeightSize(nextRow+1)){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow,nextCol+1)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row][col+1] = 0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if(direction == Direction.LEFT||direction == Direction.RIGHT){
                if(model.checkInWidthSize(nextCol)&&model.checkInWidthSize(nextCol+1)){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow+1, nextCol)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row+1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow+1][nextCol] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        }

        return false;
    }

    //todo: add other methods such as loadGame, saveGame...


}


