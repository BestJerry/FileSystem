package Utility;

import Model.FileIO;
import Model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadAndWrite {
    public static ArrayList<File> data_files;//获取所有数据文件
    public static File d_file1;
    public static Folder root;
    public static FileIO rw;

    static {
        data_files = FileIO.get_DataFile();
        d_file1 = data_files.get(0);
        rw = new FileIO(d_file1);
        try {
            root = rw.read_data();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void save(){
        Folder.print(root);
        try {
            rw.write_data(root);
        } catch (IOException e) {
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
