package Utility;

import lhw.left.FileIO;
import lhw.left.Folder;

import java.io.File;
import java.util.ArrayList;

public class ReadAndWrite {
    public static ArrayList<File> data_files;//获取所有数据文件
    public static File d_file1;
    public static Folder root;

    static {
        data_files = FileIO.get_DataFile();
        d_file1 = data_files.get(0);
        FileIO rw = new FileIO(d_file1);
        try {
            root = rw.read_data();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ReadAndWrite ourInstance = new ReadAndWrite();

    public static ReadAndWrite getInstance() {
        return ourInstance;
    }

    private ReadAndWrite() {

    }
}
