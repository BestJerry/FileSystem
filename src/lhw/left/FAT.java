package lhw.left;

import lhw.Test.ModDialog;

import java.util.ArrayList;

/**
 * �ļ������FAT
 */

public class FAT {
    private static final int systemSize = 2;
    public static final int size = 128;
    private static Disk[] diskArray = new Disk[size];
    protected static int[] FAT = new int[size];// 0 unused,255 used and tail,254
    // damaged
    static {
        for (int i = 0; i < size; i++) {
            diskArray[i] = new Disk();
        }
        initialFAT();
    }

    protected static void initialFAT() {
        for (int i = 0; i < systemSize - 1; i++) {
            setFAT(i, i + 1);
        }
        for(int i = systemSize - 1;i<size;i++)
            setFAT(i, 0);
        setFAT(systemSize - 1, 255);
    }

    /**
     * �½��ļ���Ҫ������ʼ���̿�
     *
     * @return ��һ�����ô��̿��
     */
    protected static int assignDisk() {
        for (int i = systemSize; i < size; i++) {
            if (!diskArray[i].isUsed()) {
                setFAT(i, 255);
                return i;
            }
        }
        return -1;

    }

    /**
     * �޸��ļ����ļ�ռ�������num����̣��������num�����
     *
     * @param numΪ�ļ���Ҫ��ӵĴ��̿�����num>0���䣬num<0����
     * @return true��ɹ�,false��ʣ����̲������
     */
    protected static boolean assignDisk(int startDisk, int num) {
        ArrayList<Integer> fd = getFileDisk(startDisk);
        int last = fd.get(fd.size() - 1);
        if (num > 0) {
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = systemSize; i < size && num != 0; i++) {
                if (!diskArray[i].isUsed()) {
                    num--;
                    arr.add(i);
                }
            }
            if (num > 0)
                return false;
            setFAT(last, arr.get(0));
            for (int i = 0; i < arr.size(); i++) {
                if (i != arr.size() - 1)
                    setFAT(arr.get(i), arr.get(i + 1));
                else
                    setFAT(arr.get(i), 255);
            }
        } else if (num < 0) {
            for (int i = 0; i < -num; i++) {
                setFAT(fd.get(fd.size() - 1 - i), 0);
            }
            setFAT(fd.get(fd.size() - 1 + num), 255);
        }
        return true;
    }

    /**
     * ������startDisk��ʼ���ļ�ռ�õ����д���
     *
     * @param startDisk
     *            �ļ��Ŀ�ʼ���̿��
     */
    protected static void recycleDisk(int startDisk) {
        for (Integer i : getFileDisk(startDisk)) {
            setFAT(i, 0);
        }
    }

    /**
     * ���һ���ļ�ռ�õ����д���
     *
     * @param startDisk
     *            �ļ��Ĵ��̿�ʼ��
     * @return �����ļ�ռ�õ����д��̿�ŵ�����
     */
    public static ArrayList<Integer> getFileDisk(int startDisk) {
        ArrayList<Integer> arr = new ArrayList<>();
        if ( startDisk < 0 || startDisk >= 128 || FAT[startDisk] == 0)
             return null;
        while (startDisk != 255) {
            arr.add(startDisk);
            startDisk = FAT[startDisk];
        }
        return arr;
    }

    /**
     * ���diskNum�Ŵ��̵�״̬
     *
     * @param diskNum ����
     * @return Disk.USED || Disk.UNUSED || Disk.DAMAGED
     */
    public static Disk.State getDiskState(int diskNum) {
        if (FAT[diskNum] == 0)
            return Disk.State.UNUSED;
        else
            return Disk.State.USED;
    }

    protected static void setFAT(int start, int end) {
        FAT[start] = end;
        diskArray[start].setUsed(end != 0);
    }
    /**
     * ���FAT[index]��ֵ
     * @param index �±�
     * @return FAT��ĵ�index��ֵ
     */
    public static int get(int index) {
        return FAT[index];
    }
}
class Disk {
    public static enum State{
        USED,UNUSED
    }
    private boolean used = false;
    protected Disk() {
        // TODO Auto-generated constructor stub
    }
    protected void setUsed(boolean used){
        this.used = used;
    }
    /**
     * �����Ƿ�ռ��
     * @return false == δʹ��
     */
    public boolean isUsed(){
        return used;
    }

}
