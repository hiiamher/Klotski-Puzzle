package SaveAndRead;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//此类存放了保存和读取的方法
public class SavaAndRead {

    //读取指定路径下是否有txt文件，path写到到txt文件
    public static boolean isExist (String targetPath) {
        boolean hasSave = false;
        if (SavaAndRead.Read(targetPath)!=null) {
            hasSave = true;
        }
        return hasSave;
    }

    //检验是否存在该文件夹
    public static boolean isDirectoryExistsUsingFile(String path) {
        File directory = new File(path);
        System.out.println(directory.exists());
        return directory.exists();
    }

    //该方法用于读取指定路径下的txt文件
public static List<String> Read (String targetPath) {
    try {
        return Files.readAllLines(Path.of(targetPath));
    } catch (IOException e) {
       return null;
    }
}

//把数据保存到指定的路径下
    public static void Save (String content ,String targetPath, String txtname) {
        String path = String.format(targetPath);
        File dir = new File(path);
        dir.mkdirs();
        try {
            Files.write(Path.of(path+String.format("/%s.txt",txtname)), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static void Save (List<String> content , String targetPath, String txtname) {
        String path = String.format(targetPath);
        File dir = new File(path);
        dir.mkdirs();
        try {
            Files.write(Path.of(path+"/txtname.txt"), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
