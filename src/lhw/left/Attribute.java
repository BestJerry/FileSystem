package lhw.left;


public abstract class Attribute {
    /**
     * �ļ�·��
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
     * ��ȡ�ļ���
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
     * @return �ļ�������ʼ���̿��
     */
    public int getStartDisk() {
        return startDisk;
    }

    /**
     * @return true������
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
     * ����ļ���ռ���̿����
     * @return ���̿����
     */
    public int get_length() {
        return disklen;
    }
    /**
     * ����ļ����ܴ�С���������ļ�
     * @return size
     */
    public long getSize2() {
        return this.size;
    }
    /**
     * �����ļ�����
     * @return Attribute.type.FOLDER ���� Attribute.type.FILE
     */
    public abstract type get_type();// �ļ�����

    protected Attribute faNode;// �ļ��ĸ��ڵ�
    protected String path;
    protected String name;// Ӣ����ĸ������
    // private int size = 8;//getSize
    protected long size = 0;// �����СΪ0
    protected int startDisk = -1;
    protected boolean hide = false;// �Ƿ�����
    protected int disklen = 1;// ��ռ���̳���

    protected static final int sz = 64;// һ������64B

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
    private static int MaxNumOfOpenFile = 8; // �����ļ�����
    private static int CurNumOfOpenFile = 0; // ��ǰ���ļ�����
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
     * @return true�����ļ���Type�ķ�ʽ��
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
     * @param Type ��Type�ķ�ʽ��һ���ļ�
     */
    private static void add(TextFile NewFile, int Type) {
        file[CurNumOfOpenFile] = NewFile;
        type[CurNumOfOpenFile] = Type;
        CurNumOfOpenFile++;
    }

    /**
     *
     * @param NewFile
     * @return true������д�������ļ��ɹ���false������д�������ļ�ʧ��
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
     * @return true�����Զ��������ļ��ɹ���false�����Զ��������ļ�ʧ��
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
     * @return true����ر��ļ��ɹ���false����ر��ļ�ʧ��
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