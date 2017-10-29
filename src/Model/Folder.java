package Model;

import Interface.addCallback;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Folder extends Attribute {
    private LinkedList<Attribute> linkedList = new LinkedList<>();

    protected Folder(String path) {
        this.name = path.substring(path.lastIndexOf(File.separator) + 1);
        this.path = path;
    }

    protected LinkedList<Attribute> getLinkedList() {
        return linkedList;// 只读不修改的话DiskFile没法用
    }

    /**
     * 该文件下的所有文件（包括文件夹）
     *
     * @return 文件的链表
     */
    public LinkedList<Attribute> listFolder() {
        LinkedList<Attribute> list = new LinkedList<>();
        for (Attribute temp : linkedList) {
            list.add(temp);
        }
        return list;
    }

    /**
     * 添加文件，子文件不能超过8个
     *
     * @param name        文件名，不能有'$' ， '.' ， '/'
     * @param is_folder   true则建立文件夹，false则建立文件
     * @param addCallback
     * @return false 则(name不合法或子文件超过8个或磁盘已满)
     */
    public void add(String name, boolean is_folder, addCallback addCallback) throws IOException {
        String message = "";
        Attribute temp = null;
        boolean flag = true;

        if (is_correctName(name) == false) {
            //new ModDialog().messageDialog("错误的文件名！");
            message = "错误的文件名！";
            addCallback.getResult(message, temp);
            return;
        } else if (linkedList.size() == 8) {
            //new ModDialog().messageDialog("文件夹已满！");
            message = "文件夹已满！";
            addCallback.getResult(message, temp);
            return;
        } else {

            for (Attribute attribute : this.listFolder()) {
                if (attribute.getName().equals(name) == true && (attribute instanceof Folder == is_folder)) {
                    //new ModDialog().messageDialog("名字与其他文件重复");
                    message = "名字与其他文件重复！";

                    flag = false;

                    break;
                }
            }
            if (!flag){
                addCallback.getResult(message, temp);
                return;
            }

        }
        int startDiskNum = FAT.assignDisk();
        if (is_folder) {
            temp = new Folder(this.path + File.separator + name);
        } else {
            temp = new TextFile(this.path + File.separator + name);
        }

        if (startDiskNum == -1) {
            //new ModDialog().messageDialog("空闲磁盘不足！");
            message = "空闲磁盘不足！";
            addCallback.getResult(message, temp);
            return;
        }else{
            temp.startDisk = startDiskNum;
            linkedList.add(temp);
            temp.faNode = this;
            temp.updateSize();
            message = "创建成功！";
        }
        addCallback.getResult(message, temp);
    }

    protected boolean add(Attribute node) {
        this.linkedList.add(node);
        node.faNode = this;
        node.updateSize();
        return true;
    }

    /**
     * 删除子文件
     *
     * @param file 子文件的对象
     * @return false则文件可能处于打开状态
     */
    public boolean remove(Attribute file) {
        if (file instanceof TextFile && ((TextFile) file).is_open()) return false;
        if (linkedList.remove(file)) {
            file.size = -file.size;
            file.updateSize();
            file.faNode = null;
            recycleAll(file);
            return true;
        }
        return false;
    }

    private void recycleAll(Attribute file) {
        FAT.recycleDisk(file.startDisk);
        if (file instanceof Folder) {
            Folder folder = (Folder) file;
            for (Attribute son : folder.listFolder()) {
                recycleAll(son);
            }
        }
    }

    @Override
    public type get_type() {
        // TODO Auto-generated method stub
        return type.FOLDER;
    }

    public static void print(Attribute head) {
        System.out.println("\nNAME: " + head.getName());
        System.out.println("PATH: " + head.getPath());
        System.out.println("SIZE: " + head.getSize2());
        System.out.print("DISK:");
        for (int disk : FAT.getFileDisk(head.getStartDisk()))
            System.out.print(" " + disk);
        System.out.println("\nTYPE: " + head.get_type());
        System.out.println("HIDE: " + head.isHide());
        if (head instanceof Folder) {
            Folder folder = (Folder) head;
            System.out.print("SON:");
            for (Attribute son : folder.listFolder()) {
                System.out.print(" " + son.getName());
            }
            System.out.println();
            for (Attribute son : folder.listFolder()) {
                print(son);
            }
        } else {
            TextFile tf = (TextFile) head;
            System.out.println("CONTENT: " + tf.getContent());

        }
    }
}


