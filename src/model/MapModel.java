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

    public int[][] getOriginalMatrix() {
        return originalMatrix;
    }

    public void setOriginalMatrix(int[][] originalMatrix) {
        this.originalMatrix = originalMatrix;
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

    public boolean textnumofbox(int[][] matrix) {
        int num0=0;
        int num1=0;
        int num2=0;
        int num3=0;
        int num4=0;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int id = matrix[row][col];
                if (matrix[row][col]==0) {
                    num0++;
                }
                if (matrix[row][col]==1) {
                    num1++;
                }
                if (matrix[row][col]==2) {
                    num2++;
                }
                if (matrix[row][col]==3) {
                    num3++;
                }
                if (matrix[row][col]==4) {
                    num4++;
                }
            }
        }
        if(num0==2&num1==4&num2==2&num3==8&num4==4){
            return true;
        }
        return false;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public MapModel clone(){
        return new MapModel(matrix);
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }
}
