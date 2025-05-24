package ai;
import java.util.Arrays;

class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] state;
    PuzzleNode parent;
    int f;
    int steps;
    int h;


    //f = g + h
    //g = steps
    //h = a曼哈顿距离+b阻挡数


    public PuzzleNode(int[][] state, PuzzleNode parent, int steps) {



        this.state = state;
        this.parent = parent;
        this.steps = steps;
        this.h = 5*calculateHeuristic(state) + 2*calculateblock();
        this.f = steps + h;

        //
        if(steps>200){
            System.out.println( "steps = " + steps);
        }
    }

    //必须传入正常数组，一个曹操，4*5
    private int calculateHeuristic(int[][] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 4) {
                    //3和1代表出口的位置
                    heuristic = Math.abs(3 - i) + Math.abs(1 - j);
                    return heuristic;
                }
            }
        }
        return heuristic;
    }


    private int calculateblock() {
        int caocaoX = -1;
        int caocaoY = -1;
        int rows = state.length;
        int cols = state[0].length;

        // 找到曹操的位置
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (state[i][j] == 4) {
                    caocaoX = i;
                    caocaoY = j;
                    break;
                }
            }
            if (caocaoX != -1) {
                break;
            }
        }

        if (caocaoX == -1 || caocaoY == -1) {
            return 0;
        }


        int blockCount = 0;
        // 假设出口在右下角，计算曹操到出口路径上的阻挡块数
        //此处粗略计算
        //待改进
        for (int i = caocaoX; i < rows; i++) {
            for (int j = caocaoY; j < cols; j++) {
                if (state[i][j] != 0 && state[i][j] != 4) {
                    blockCount++;
                }
            }
        }
        return blockCount;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public PuzzleNode getParent() {
        return parent;
    }

    public void setParent(PuzzleNode parent) {
        this.parent = parent;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.f, other.f);
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleNode that = (PuzzleNode) o;
        return Arrays.deepEquals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }*/
}