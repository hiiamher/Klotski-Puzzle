package controller;
import model.Direction;
import model.MapModel;
import user.User;
import view.game.BoxComponent;
import view.game.GamePanel;

import javax.swing.*;
import java.io.File;
//<<<<<<< HEAD
//=======
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static SaveAndRead.SavaAndRead.Read;
import static SaveAndRead.SavaAndRead.Save;
import static animation.Animation.translation;
//>>>>>>> a11f1a71dfa5c1c019cf2bf00f5a61e93094f46b

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private User user;
    private JFrame gameframe;







    public GameController(GamePanel view, MapModel model, User user) {
        this.view = view;
        this.model = model;
        this.user = user;
        view.setController(this);
//>>>>>>> a11f1a71dfa5c1c019cf2bf00f5a61e93094f46b
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetOriginalMatrix();
        this.view.clearAllBoxFromPanel();
        this.view.initialGame(model.getMatrix());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveGame(User user) {
        int[][] map = model.getMatrix();
        List<String> gameData = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int[] line : map) {
            for(int value:line){
                sb.append(value).append(" ");
            }
            gameData.add(sb.toString());
            sb.setLength(0);
        }
        for(String s:gameData){
            System.out.println(s);
        }
        String path = String.format("save/%s", user.getUsername());
        File dir = new File(path);
        dir.mkdirs();
        try {
            Files.write(Path.of(path+"/mapdata.txt"),gameData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Save ( String.format("%s",user.getSteps()) ,path, "stepdata");
        //create folder

        System.out.println("save successfully");
    }


    public void loadGame(String path)  {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            int[][] map = new int[lines.size()][lines.get(0).split(" ").length];
            for(int i = 0; i < lines.size(); i++) {
                String[] values = lines.get(i).split(" ");
                for(int j = 0; j < values.length; j++) {
                    map[i][j] = Integer.parseInt(values[j]);
                }
            }


            //检验数据是否被修改
            if(!model.textnumofbox(map)){
                System.out.println("数据被修改");
                //加提示框
                System.out.println("数据被修改");
                // 加提示框
                JOptionPane.showMessageDialog(
                        gameframe,
                        "数据已被修改，请谨慎操作。",
                        "数据修改警告",
                        JOptionPane.WARNING_MESSAGE
                );

            }

            this.model.setMatrix(map);
            this.view.clearAllBoxFromPanel();
            this.view.initialGame(map);

            //更新步数
            int SavedSteps = Integer.parseInt(Read(String.format("save/%s/stepdata.txt", user.getUsername())).get(0));
            this.view.setSteps(SavedSteps);
            this.view.ChangeStepsLabel(SavedSteps);
            this.view.getStepLabel().repaint();

        } catch (IOException e) {
            // 处理文件读取异常，弹出错误提示框
            System.err.println("文件读取失败: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    gameframe,
                    "文件读取失败: " + e.getMessage()+"点击Sava按钮覆盖保存",
                    "文件读取错误",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (NumberFormatException e) {
            // 处理字符串转换为整数时的异常，弹出错误提示框
            System.err.println("数据格式错误: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    gameframe,
                    "数据格式错误: " + e.getMessage()+"点击Sava按钮覆盖保存",
                    "数据格式错误",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            // 处理其他未预料到的异常，弹出错误提示框
            System.err.println("发生未知错误: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    gameframe,
                    "发生未知错误: " + e.getMessage()+"点击Sava按钮覆盖保存",
                    "未知错误",
                    JOptionPane.ERROR_MESSAGE
            );
        }

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
                    translation( box,1,nextCol ,nextRow );
                    //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    //box.repaint();
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
                            translation( box,1,nextCol ,nextRow );
                            //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                           //box.repaint();
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
                             translation( box,1,nextCol ,nextRow );
                             //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                             //box.repaint();
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
                        translation( box,1,nextCol ,nextRow );
                       // box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                       // box.repaint();
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
                            translation( box,1,nextCol ,nextRow );
                           // box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                           // box.repaint();
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
                            translation( box,1,nextCol ,nextRow );
                          //  box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                           // box.repaint();
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
                        translation( box,1,nextCol ,nextRow );
                        //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        //box.repaint();
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
            if(direction == Direction.UP){
                if(model.checkInHeightSize(nextRow)){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow,nextCol+1)==0){
                        model.getMatrix()[row+1][col] = 0;
                        model.getMatrix()[row+1][col+1] = 0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        translation( box,1,nextCol ,nextRow );
                        //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        //box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if(direction == Direction.DOWN){
                if(model.checkInHeightSize(nextRow+1)){
                    if(model.getId(nextRow+1, nextCol) == 0&&model.getId(nextRow+1,nextCol+1)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row][col+1] = 0;
                        model.getMatrix()[nextRow+1][nextCol] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        translation( box,1,nextCol ,nextRow );
                        //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        //box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if(direction == Direction.LEFT){
                if(model.checkInWidthSize(nextCol)){
                    if(model.getId(nextRow, nextCol) == 0&&model.getId(nextRow+1, nextCol)==0){
                        model.getMatrix()[row][col+1] = 0;
                        model.getMatrix()[row+1][col+1] = 0;
                        model.getMatrix()[nextRow][nextCol] = 4;
                        model.getMatrix()[nextRow+1][nextCol] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        translation( box,1,nextCol ,nextRow );
                        //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        //box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if(direction == Direction.RIGHT){
                if(model.checkInWidthSize(nextCol+1)){
                    if(model.getId(nextRow, nextCol+1) == 0&&model.getId(nextRow+1, nextCol+1)==0){
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row+1][col] = 0;
                        model.getMatrix()[nextRow][nextCol+1] = 4;
                        model.getMatrix()[nextRow+1][nextCol+1] = 4;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        translation( box,1,nextCol ,nextRow );
                        //box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        //box.repaint();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public void save_path(){
       //保存每步状态
        String path = String.format("save/%s/path/%d", user.getUsername(),user.getSteps());
        File dir = new File(path);
        dir.mkdirs();
        int[][] map = model.getMatrix();
        List<String> gameData = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int[] line : map) {
            for(int value:line){
                sb.append(value).append(" ");
            }
            gameData.add(sb.toString());
            sb.setLength(0);
        }
        Save (gameData,path,"Path_Map");
    }



    public void WithDraw(int steps){
            String path = String.format("save/%s/path/%d/Path_Map.txt", user.getUsername(), steps);
            try {
                List<String> lines = Files.readAllLines(Path.of(path));
                int[][] map = new int[lines.size()][lines.get(0).split(" ").length];
                for (int i = 0; i < lines.size(); i++) {
                    String[] values = lines.get(i).split(" ");
                    for (int j = 0; j < values.length; j++) {
                        map[i][j] = Integer.parseInt(values[j]);
                    }
                }

                this.model.setMatrix(map);
                this.view.clearAllBoxFromPanel();
                this.view.initialGame(map);
                this.view.setSteps(steps);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }





    public JFrame getGameframe() {
        return gameframe;
    }

    public void setGameframe(JFrame gameframe) {
        this.gameframe = gameframe;
    }






}


