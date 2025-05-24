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
    public static List<int[][]> getNeighbors(int[][] map) {
        List<int[][]> neighbors = new ArrayList<>();
        MapModel mapModel1 = new MapModel(map);
        GamePanel gamePanel = new GamePanel(mapModel1);
        GameController gameController = new GameController(gamePanel, mapModel1, null);

        for (int i = 0; i < gamePanel.getBoxes().size(); i++) {
            BoxComponent box = gamePanel.getBoxes().get(i);
            gamePanel.setSelectedBox(box);

            // 遍历四个方向
            Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
            for (Direction direction : directions) {
                // 保存原始地图状态
                int[][] originalMap = cloneState(mapModel1.getMatrix());

                // 尝试向指定方向移动
                if (gameController.doMove(box.getRow(), box.getCol(), direction)) {
                    neighbors.add(cloneState(mapModel1.getMatrix()));
                }

                // 无论移动是否成功，都恢复原始地图状态
                mapModel1.setMatrix(originalMap);
            }
        }
        return neighbors;
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

        /*
        List<PuzzleNode> result = new ArrayList<>();
        if (queue.isEmpty()) {
            return result;
        }

        // 取出队首元素
        PuzzleNode firstNode = queue.poll();
        result.add(firstNode);
        int firstPriority = firstNode.getF();

        // 持续取出优先级相同的元素
        while (!queue.isEmpty()) {
            PuzzleNode nextNode = queue.peek();
            int nextPriority = nextNode.getF();
            if (nextPriority == firstPriority) {
                result.add(queue.poll());
            } else {
                break;
            }
        }
        return result;*/
    }


}