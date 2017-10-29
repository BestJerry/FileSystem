package lhw.left;


public abstract class Attribute {
    /**
     * 文件路径
     * @return
     */
    public String getPath() {
        return path;
    }

    public boolean setName(String name) {
        if(!is_correctName(name)) return false;
        this.name = name;
        return true;
    }
    /**
     * 获取文件名
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * @return 文件所在起始磁盘块号
     */
    public int getStartDisk() {
        return startDisk;
    }

    /**
     * @return true则隐藏
     */
    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    protected void updateSize() {
        Attribute tmp = this.faNode;
        while (tmp != null) {
            tmp.size += this.size;

            // System.out.println(tmp.size + " " +tmp.name);
            tmp = tmp.faNode;
        }
    }
    /**
     * 获得文件所占磁盘块个数
     * @return 磁盘块个数
     */
    public int get_length() {
        return disklen;
    }
    /**
     * 获得文件的总大小，包括子文件
     * @return size
     */
    public long getSize2() {
        return this.size;
    }
    /**
     * 返回文件类型
     * @return Attribute.type.FOLDER 或者 Attribute.type.FILE
     */
    public abstract type get_type();// 文件类型

    protected Attribute faNode;// 文件的父节点
    protected String path;
    protected String name;// 英文字母或数字
    // private int size = 8;//getSize
    protected long size = 0;// 起初大小为0
    protected int startDisk = -1;
    protected boolean hide = false;// 是否隐藏
    protected int disklen = 1;// 所占磁盘长度

    protected static final int sz = 64;// 一个磁盘64B

    public static enum type {
        FOLDER, FILE;
    }
    public  Folder getFaNode(){
        return (Folder) faNode;
    }

    public static boolean is_correctName(String name) {
        if (name == null || name.length() > 5 || name.length() == 0)
            return false;

        boolean flag = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '$' || c == '/' || c == '.')
                return false;
            if(c < 0 || c > 255) return  false;
            if(c !=' ')
                flag = true;
        }
        return flag;
    }

    protected static boolean is_correctStartDisk(int startDisk) {
        return startDisk >= 2 && startDisk < 128;
    }

    protected static boolean is_correctDiskLen(int disklen) {
        return disklen >= 1 && disklen <= 125;
    }
}
class FileTable {
    private static int MaxNumOfOpenFile = 8; // 最大打开文件数量
    private static int CurNumOfOpenFile = 0; // 当前打开文件数量
    private static TextFile file[] = null;
    private static int type[] = null;
    private static int write = 0;
    private static int read = 1;
    static{
        CurNumOfOpenFile = 0;
        file = new TextFile[MaxNumOfOpenFile];
        type = new int[MaxNumOfOpenFile];
    }

    /**
     *
     * @param NewFile
     * @param Type
     * @return true代表文件以Type的方式打开
     */
    private static boolean exist(TextFile NewFile, int Type) {
        for (int i = 0; i < CurNumOfOpenFile; i++) {
            if (file[i].equals(NewFile)) {
                return type[i] == Type;
            }
        }
        return false;
    }

    /**
     *
     * @param NewFile
     * @param Type 以Type的方式打开一个文件
     */
    private static void add(TextFile NewFile, int Type) {
        file[CurNumOfOpenFile] = NewFile;
        type[CurNumOfOpenFile] = Type;
        CurNumOfOpenFile++;
    }

    /**
     *
     * @param NewFile
     * @return true代表以写操作打开文件成功，false代表以写操作打开文件失败
     */

    protected static boolean Write(TextFile NewFile) {
        if (exist(NewFile, write) == true) {
            return true;
        }
        if (CurNumOfOpenFile >= MaxNumOfOpenFile) {
            return false;
        }
        if (NewFile.isOnly_read() == true) {
            return false;
        }
        add(NewFile, write);
        return true;
    }

    /**
     *
     * @param NewFile
     * @return true代表以读操作打开文件成功，false代表以读操作打开文件失败
     */

    protected static boolean Read(TextFile NewFile) {
        if (exist(NewFile, read) == true) {
            return true;
        }
        if (CurNumOfOpenFile >= MaxNumOfOpenFile) {
            return false;
        }
        add(NewFile, read);
        return true;
    }


    /**
     *
     * @param NewFile
     * @return true代表关闭文件成功，false代表关闭文件失败
     */

    protected static boolean close(TextFile NewFile) {
        for (int i = 0; i < CurNumOfOpenFile; i++) {
            if (file[i].equals(NewFile)) {
                for (int j = i; j < CurNumOfOpenFile - 1; j++) {
                    file[j] = file[j + 1];
                    type[j] = type[j + 1];
                }
                CurNumOfOpenFile--;
                return true;
            }
        }
        return false;
    }
}