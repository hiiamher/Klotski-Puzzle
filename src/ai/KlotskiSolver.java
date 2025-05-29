package ai;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class KlotskiSolver {
    // 定义方向数组，分别表示上、下、左、右四个方向
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    //待改进
    public static List<int[][]> solve(int[][] initialState) {
        List<int[][]> solutions = new ArrayList<>();
        List<PuzzleNode> openSet1 = new ArrayList<>();
        List<PuzzleNode> openSet2 = new ArrayList<>();
        PuzzleNode lastNode = null;

        PuzzleNode startNode = new PuzzleNode(initialState, null, 0);
        openSet1.add(startNode);

        int count = 0;


        while (lastNode == null) {
            count++;
            // 求出所有的邻居节点
            openSet2.clear();
            for (PuzzleNode node1 : openSet1) {
                List<PuzzleNode> a = getNeighborNodes(node1);
                for (PuzzleNode node2 : a) {
                        openSet2.add(node2);
                }
            }
            for (PuzzleNode node3 : openSet2) {
                if (isGoalState(node3.getState())) {
                    lastNode = node3;
                    break;
                }
            }
            //去除重复节点
            openSet2 = removeSameNode(openSet2);

            //List<PuzzleNode> b = getsmallfnode(openSet2);
            openSet1.clear();
            for (PuzzleNode node1 : openSet2) {
                openSet1.add(node1);
            }

            //数值不确定
            if (count == 100000) {
                System.out.println("无法求解");
                return null;
            }

        }

        solutions = reconstructPath(lastNode);
        return solutions;




        /*
        Set<PuzzleNode> closedSet = new HashSet<>();

        PuzzleNode startNode = new PuzzleNode(initialState, null, 0);
        openSet1.add(startNode);

        while (!openSet1.isEmpty()) {
            count++;


            PuzzleNode current = openSet1.poll();

            if (isGoalState(current.state)) {
                return reconstructPath(current);
            }

            if (closedSet.contains(current)) {
                continue;
            }
            closedSet.add(current);

            List<PuzzleNode> neighbors = getNeighborNodes(current);
            for (PuzzleNode neighborNode : neighbors) {
                if (closedSet.contains(neighborNode)) {
                    continue;
                }
                openSet1.add(neighborNode);
            }
            if (count == 1000) {
                System.out.println("无法求解");
                break;
            }
        }
*/

    }

    //启发式搜索
    public static List<int[][]> solve2(int[][] initialState) {
        List<int[][]> solutions = new ArrayList<>();
        List<PuzzleNode> openSet1 = new ArrayList<>();
        List<PuzzleNode> openSet2 = new ArrayList<>();
        PuzzleNode lastNode = null;

        PuzzleNode startNode = new PuzzleNode(initialState, null, 0);
        openSet1.add(startNode);

        int count = 0;


        while (lastNode == null) {
            count++;
            // 求出所有的邻居节点
            openSet2.clear();
            for (PuzzleNode node1 : openSet1) {
                List<PuzzleNode> a = getNeighborNodes(node1);
                for (PuzzleNode node2 : a) {
                    openSet2.add(node2);
                }
            }
            for (PuzzleNode node3 : openSet2) {
                if (isGoalState(node3.getState())) {
                    lastNode = node3;
                    break;
                }
            }
            //去除重复节点
            openSet2 = removeSameNode(openSet2);
            List<PuzzleNode> b = getsmallfnode(openSet2);
            openSet1.clear();
            for (PuzzleNode node1 : b) {
                openSet1.add(node1);
            }

            //数值不确定
            if (count >= 1000000) {
                System.out.println("无法求解");
                return null;
            }

        }

        solutions = reconstructPath(lastNode);
        return solutions;






    }


    private static List<PuzzleNode> getNeighborNodes(PuzzleNode current) {
        List<PuzzleNode> neighborNodes = new ArrayList<>();
        int a = current.steps + 1;
        // 获取当前状态的所有邻居状态
        List<int[][]> neighborStates = getNeighbors(current.state);
        for (int[][] neighborState : neighborStates) {
            // 为每个邻居状态创建一个新的 PuzzleNode 节点
            PuzzleNode neighborNode = new PuzzleNode(neighborState, current, a);
            neighborNodes.add(neighborNode);
        }
        return neighborNodes;
    }


    //完成
    private static boolean isGoalState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 4) {
                    if (i == 3 && j == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    //待修改
    public static List<int[][]> getNeighbors(int[][] board) {
        List<int[][]> result = new ArrayList<>();
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int current = board[i][j];
                if (current == 0) continue;

                switch (current) {
                    case 1:
                        checkSingleMove(board, i, j, rows, cols, result);
                        break;
                    case 2:
                        if (j + 1 < cols && board[i][j + 1] == 2) {
                            // 传入 rows 参数以支持上下移动
                            checkHorizontalMove(board, i, j, rows, cols, result);
                        }
                        break;
                    case 3:
                        if (i + 1 < rows && board[i + 1][j] == 3) {
                            checkVerticalMove(board, i, j, rows, cols, result);
                        }
                        break;
                    case 4:
                        if (i + 1 < rows && j + 1 < cols &&
                                board[i][j + 1] == 4 &&
                                board[i + 1][j] == 4 &&
                                board[i + 1][j + 1] == 4) {
                            checkSquareMove(board, i, j, rows, cols, result);
                        }
                        break;
                }
            }
        }
        return result;
    }


    private static int[][] cloneState(int[][] state) {
        int[][] newState = new int[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            newState[i] = Arrays.copyOf(state[i], state[i].length);
        }
        return newState;
    }

    //待改进
    //向上回溯路径
    private static List<int[][]> reconstructPath(PuzzleNode node) {
        List<int[][]> path = new ArrayList<>();
        // 从目标节点回溯到起始节点
        while (node != null) {
            // 每次都添加到列表头部，保证路径顺序正确
            path.add(0, node.state);
            node = node.parent;
        }
        return path;
    }

    //测试
    public static List<PuzzleNode> removeSameNode(List<PuzzleNode> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return new ArrayList<>();
        }
        // 使用 Set 存储已经出现过的状态，利用 Set 的去重特性
        Set<String> seenStates = new HashSet<>();
        return nodes.stream()
                .filter(node -> seenStates.add(Arrays.deepToString(node.getState())))
                .collect(Collectors.toList());
    }


    public static List<PuzzleNode> getsmallfnode(List<PuzzleNode> queue) {

        int min = queue.get(0).getF();
        for (PuzzleNode node : queue) {
            if (node.getF() < min) {
                min = node.getF();
            }
        }

        List<PuzzleNode> result = new ArrayList<>();

        for (PuzzleNode node : queue) {
            if (node.getF() == min) {
                result.add(node);
            }
        }

        return result;

    }

    // 1x1方块移动（增加行/列边界检查）
    public static void checkSingleMove(int[][] original, int i, int j, int rows, int cols, List<int[][]> result) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            // 增加边界检查：ni在[0,4]，nj在[0,3]
            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && original[ni][nj] == 0) {
                int[][] newBoard = copyBoard(original);
                newBoard[ni][nj] = newBoard[i][j];
                newBoard[i][j] = 0;
                result.add(newBoard);
            }
        }
    }

    // 横向1x2方块移动（增加行/列边界检查，支持上下左右移动）
    public static void checkHorizontalMove(int[][] original, int i, int j, int rows, int cols, List<int[][]> result) {
        // 向右移动：j+2不能超过3（cols=4，索引最大3）
        if (j + 2 < cols && original[i][j + 2] == 0) {
            int[][] newBoard = copyBoard(original);
            newBoard[i][j] = 0;
            newBoard[i][j + 2] = 2;
            result.add(newBoard);
        }
        // 向左移动：j-1不能小于0
        if (j - 1 >= 0 && original[i][j - 1] == 0) {
            int[][] newBoard = copyBoard(original);
            newBoard[i][j + 1] = 0;
            newBoard[i][j - 1] = 2;
            result.add(newBoard);
        }
        // 向上移动：i-1不能小于0，且上方两个位置都为 0
        if (i - 1 >= 0 && original[i - 1][j] == 0 && original[i - 1][j + 1] == 0) {
            int[][] newBoard = copyBoard(original);
            newBoard[i][j] = 0;
            newBoard[i][j + 1] = 0;
            newBoard[i - 1][j] = 2;
            newBoard[i - 1][j + 1] = 2;
            result.add(newBoard);
        }
        // 向下移动：i+1不能超过4（rows=5，索引最大4），且下方两个位置都为 0
        if (i + 1 < rows && original[i + 1][j] == 0 && original[i + 1][j + 1] == 0) {
            int[][] newBoard = copyBoard(original);
            newBoard[i][j] = 0;
            newBoard[i][j + 1] = 0;
            newBoard[i + 1][j] = 2;
            newBoard[i + 1][j + 1] = 2;
            result.add(newBoard);
        }
    }

    // 纵向1x2方块移动（增加行/列边界检查，支持横向移动）
    public static void checkVerticalMove(int[][] original, int i, int j, int rows, int cols, List<int[][]> result) {
        // 向下移动：i+2不能超过4（rows=5，索引最大4）
        if (i + 2 < rows && original[i + 2][j] == 0) {
            int[][] newBoard = copyBoard(original);
            // 清空原位置
            newBoard[i][j] = 0;
            newBoard[i + 1][j] = 0;
            // 设置新位置
            newBoard[i + 1][j] = 3;
            newBoard[i + 2][j] = 3;
            result.add(newBoard);
        }
        // 向上移动：i-1不能小于0
        if (i - 1 >= 0 && original[i - 1][j] == 0) {
            int[][] newBoard = copyBoard(original);
            // 清空原位置
            newBoard[i][j] = 0;
            newBoard[i + 1][j] = 0;
            // 设置新位置
            newBoard[i - 1][j] = 3;
            newBoard[i][j] = 3;
            result.add(newBoard);
        }
        // 向右横向移动：j+1不能超过3（cols=4，索引最大3），且同一列下一行右侧位置也为 0
        if (j + 1 < cols && original[i][j + 1] == 0 && original[i + 1][j + 1] == 0) {
            int[][] newBoard = copyBoard(original);
            // 清空原位置
            newBoard[i][j] = 0;
            newBoard[i + 1][j] = 0;
            // 设置新位置
            newBoard[i][j + 1] = 3;
            newBoard[i + 1][j + 1] = 3;
            result.add(newBoard);
        }
        // 向左横向移动：j-1不能小于0，且同一列下一行左侧位置也为 0
        if (j - 1 >= 0 && original[i][j - 1] == 0 && original[i + 1][j - 1] == 0) {
            int[][] newBoard = copyBoard(original);
            // 清空原位置
            newBoard[i][j] = 0;
            newBoard[i + 1][j] = 0;
            // 设置新位置
            newBoard[i][j - 1] = 3;
            newBoard[i + 1][j - 1] = 3;
            result.add(newBoard);
        }
    }

    // 2x2方块移动（增加行/列边界检查）
    public static void checkSquareMove(int[][] original, int i, int j, int rows, int cols, List<int[][]> result) {
        // 定义上下左右四个移动方向
        int[][] directions = {
                {-1, 0}, // 上
                {1, 0},  // 下
                {0, -1}, // 左
                {0, 1}   // 右
        };

        for (int[] dir : directions) {
            int newRow = i + dir[0];
            int newCol = j + dir[1];

            // 检查新位置是否越界，能否容纳 2*2 方块
            if (newRow < 0 || newRow + 2 > rows || newCol < 0 || newCol + 2 > cols) {
                continue;
            }

            // 检查移动方向所需的空位
            boolean canMove = isMovePossible(original, i, j, newRow, newCol, dir);
            if (canMove) {
                int[][] newBoard = copyBoard(original);
                // 清空原位置
                clearOriginalPosition(newBoard, i, j);
                // 设置新位置
                setNewPosition(newBoard, newRow, newCol);
                result.add(newBoard);
            }
        }
    }

    /**
     * 检查 2*2 方块是否可以向指定方向移动
     * @param original 原始棋盘
     * @param i 方块左上角的行索引
     * @param j 方块左上角的列索引
     * @param newRow 移动后的行索引
     * @param newCol 移动后的列索引
     * @param dir 移动方向
     * @return 是否可以移动
     */
    public static boolean isMovePossible(int[][] original, int i, int j, int newRow, int newCol, int[] dir) {
        if (dir[0] > 0) { // 向下移动
            return original[newRow + 1][j] == 0 && original[newRow + 1][j + 1] == 0;
        } else if (dir[0] < 0) { // 向上移动
            return original[newRow][j] == 0 && original[newRow][j + 1] == 0;
        } else if (dir[1] > 0) { // 向右移动
            return original[i][newCol + 1] == 0 && original[i + 1][newCol + 1] == 0;
        } else { // 向左移动
            return original[i][newCol] == 0 && original[i + 1][newCol] == 0;
        }
    }

    /**
     * 清空 2*2 方块的原位置
     * @param board 棋盘
     * @param i 方块左上角的行索引
     * @param j 方块左上角的列索引
     */
    public static void clearOriginalPosition(int[][] board, int i, int j) {
        board[i][j] = 0;
        board[i][j + 1] = 0;
        board[i + 1][j] = 0;
        board[i + 1][j + 1] = 0;
    }

    /**
     * 设置 2*2 方块的新位置
     * @param board 棋盘
     * @param newRow 新位置的行索引
     * @param newCol 新位置的列索引
     */
    public static void setNewPosition(int[][] board, int newRow, int newCol) {
        board[newRow][newCol] = 4;
        board[newRow][newCol + 1] = 4;
        board[newRow + 1][newCol] = 4;
        board[newRow + 1][newCol + 1] = 4;
    }

    public static int[][] copyBoard(int[][] original) {
        int[][] copy = new int[5][4]; // 固定5x4棋盘
        for (int i = 0; i < 5; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 4);
        }
        return copy;
    }
}


