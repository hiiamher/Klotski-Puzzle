package model;

/**
 * This class is to record the map of one game. For example:
 */
//记录游戏的地图信息
public class MapModel {
    int[][] matrix;
    private int[][] originalMatrix;


    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.originalMatrix = copyMatrix(matrix);
    }


    //用原矩阵恢复矩阵的方法
    public void resetOriginalMatrix() {
        this.matrix= copyMatrix(originalMatrix);
    }


    //复制矩阵的方法
    public int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        if (checkInHeightSize(row) && checkInWidthSize(col)) {
            return matrix[row][col];
        }
        throw new IllegalArgumentException("行或列索引超出范围");
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }
}
