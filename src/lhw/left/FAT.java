package lhw.left;

import lhw.Test.ModDialog;

import java.util.ArrayList;

/**
 * 文件分配表FAT
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
     * 新建文件需要分配起始磁盘块
     *
     * @return 第一个可用磁盘块号
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
     * 修改文件后，文件占用需添加num块磁盘，请求分配num块磁盘
     *
     * @param num为文件需要添加的磁盘块数，num>0分配，num<0回收
     * @return true则成功,false则剩余磁盘不足分配
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
     * 回收以startDisk开始的文件占用的所有磁盘
     *
     * @param startDisk
     *            文件的开始磁盘块号
     */
    protected static void recycleDisk(int startDisk) {
        for (Integer i : getFileDisk(startDisk)) {
            setFAT(i, 0);
        }
    }

    /**
     * 获得一个文件占用的所有磁盘
     *
     * @param startDisk
     *            文件的磁盘开始块
     * @return 被该文件占用的所有磁盘块号的数组
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
     * 获得diskNum号磁盘的状态
     *
     * @param diskNum 磁盘
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
     * 获得FAT[index]的值
     * @param index 下标
     * @return FAT表的第index个值
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
     * 磁盘是否被占用
     * @return false == 未使用
     */
    public boolean isUsed(){
        return used;
    }

}
