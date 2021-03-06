package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 磁盘文件读写类
 */
public class FileIO {
    private static final int size = Attribute.sz;
    private File disk_file;

    /**
     * 选定一个数据文件进行读写，file为null则自创一个文件读写
     *
     * @param file 数据文件
     */
    public FileIO(File file) {
        changeDataFile(file);
    }

    /**
     * 更换数据文件读写，file为null则新创一个文件进行读写
     *
     * @param file 数据文件
     */
    public void changeDataFile(File file) {
        if (file != null)
            disk_file = file;
        else
            disk_file = new File(correctFileName());
    }

    /**
     * 获得所有以"data_"开头，以".dat"结尾的数据文件
     *
     * @return 文件的动态数组
     */
    public static ArrayList<File> get_DataFile() {
        ArrayList<File> files = new ArrayList<>();
        File thisFile = new File(System.getProperty("user.dir"));
        for (File f : thisFile.listFiles()) {
            if (f.getName().endsWith(".dat") && f.getName().startsWith("data_"))
                files.add(f);
        }
        if (files.size() == 0) {
            File f = new File("data_0.dat");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            files.add(f);
        }
        files.sort(new Comparator<File>() {
            public int compare(File o1, File o2) {
                int end1 = o1.getName().lastIndexOf(".dat") - 1, end2 = o2.getName().lastIndexOf(".dat") - 1;
                char c1 = o1.getName().charAt(end1), c2 = o2.getName().charAt(end2);
                int num1 = 0, num2 = 0;
                while (c1 >= '0' && c1 <= '9') {
                    num1 = num1 * 10 + c1 - '0';
                    end1--;
                    c1 = o1.getName().charAt(end1);
                }
                while (c2 >= '0' && c2 <= '9') {
                    num2 = num2 * 10 + c2 - '0';
                    end2--;
                    c2 = o2.getName().charAt(end2);
                }
                return c1 - c2;
            }

        });
        return files;
    }

    /**
     * 往文件中写数据
     *
     * @param head 根目录的对象
     * @throws IOException
     */
    public void write_data(Folder head) throws IOException {
        clear();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(disk_file, "rw");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        write(raf, head);
        raf.close();
    }

    private void write(RandomAccessFile raf, Attribute head) throws IOException {
        raf.seek(0);
        for (int i = 0; i < 128; i++) {
            raf.writeByte(FAT.FAT[i]);
        }
        if (head == null)
            return;
        for (int i = 0; i < ((Folder) head).getLinkedList().size(); i++) {
            Attribute son = ((Folder) head).getLinkedList().get(i);
            writeNode(raf, son, head.getStartDisk(), i * 8);
        }
    }

    private void writeNode(RandomAccessFile raf, Attribute node, int disk, int offset) throws IOException {
        raf.seek(disk * size + offset);
        byte[] b = new byte[8];
        byte[] names = node.getName().getBytes("gbk");
        for (int i = 0; i < 5 && i < names.length; i++) {
            b[i] = names[i];
        }

        /*// 标记是否是个目录项
		for (int i = 3; i < 5; i++) {
			b[i] = 1;
		}*/

        b[5] = (byte) (0 | ((node instanceof Folder) ? 0 : 4) | (node.getHide() ? 2 : 0));
        // 00000100b --is file,00000010b is hide,00000001 is onlyread
        if ((b[5] & 4) == 4) {
            b[5] = (byte) (b[5] | (((TextFile) node).isOnly_read() ? 1 : 0));
        }
        b[6] = (byte) node.getStartDisk();
        b[7] = (byte) node.get_length();
        // raf.seek(disk * size);
        // 往disk = 3,offset = 8 （3，8）写完数据，再跳到（4，0）写数据，（3，16）-（4，0）之间自动填0
        raf.write(b, 0, 8);
        if (node instanceof Folder) {
            // 写目录项
            for (int i = 0; i < ((Folder) node).getLinkedList().size(); i++) {
                Attribute son = ((Folder) node).getLinkedList().get(i);
                writeNode(raf, son, node.startDisk, i * 8);
            }
        } else {
            // 写文件内容
            writeContent(raf, (TextFile) node, node.getStartDisk());
        }
    }

    private void writeContent(RandomAccessFile raf, TextFile file, int disk) throws IOException {

        raf.seek(disk * size);
        byte[] b = file.getContent().getBytes("gbk");
        int next = disk, start = 0;

        while (b.length > start) {
            raf.write(b, start, size > b.length - start ? b.length - start : size);
            next = FAT.FAT[next];
            raf.seek(next * size);
            start += size;
        }
    }

    /**
     * 从文件中读取数据
     *
     * @return 数据的根目录
     * @throws Exception 可能文件为空或者文件被修改过，文件为空则不进行读取
     */
    public Folder read_data() throws Exception {
        FAT.initialFAT();
        String rootPath = System.getProperty("user.dir");
        rootPath = rootPath.substring(rootPath.lastIndexOf(File.separator)+1);
        Folder head = new Folder(rootPath);
        head.startDisk = FAT.assignDisk();
        try
                (RandomAccessFile raf = new RandomAccessFile(disk_file, "r")) {
            try {
                head = read(raf, head);
            } catch (Exception e) {
                head = new Folder(rootPath);
                FAT.initialFAT();
                head.startDisk = FAT.assignDisk();
                clear();// 可能数据有毒=_=
                // throw new Exception("data error or file is empty!");
            }
            raf.close();
        }


        return head;
    }

    private Folder read(RandomAccessFile raf, Folder head) throws IOException {

        raf.seek(0);
        for (int i = 0; i < 128; i++) {
            int end = -1;
            try {
                end = raf.readUnsignedByte();
            } catch (Exception e) {
                throw new IOException("file is empty");
            }
            FAT.setFAT(i, end);
        }
        for (int i = 0; i < size / 8; i++) {
            Attribute son = readNode(raf, head, i * 8);
            if (son == null)
                break;
            head.add(son);
        }
        return head;
    }

    private Attribute readNode(RandomAccessFile raf, Attribute node, int offset) throws IOException {
        int disk = node.getStartDisk();
        raf.seek(disk * size + offset);
        byte[] b = new byte[8];
        if (raf.read(b, 0, 8) == -1) {
            return null;
        }
        //if (b[3] == 0)
        //return null;
        //String name;
        //char[] c = new char[5];
        int i = 0;
        for (i = 0; i < 5; i++) {
            if (b[i] == 0)
                break;
            //c[i] = (char) b[i];
        }
        byte[] nameByte = new byte[i];

        for(int j=0;j<i;j++){
            nameByte[j] = b[j];
        }
        String name = new String(nameByte,"gbk");
        //name = String.copyValueOf(c, 0, i);
        if (Attribute.is_correctName(name) == false)
            return null;
        boolean is_Hide, is_File, is_Only_read;
        is_File = ((b[5] & 4) == 4);
        is_Hide = ((b[5] & 2) == 2);
        is_Only_read = ((b[5] & 1) == 1);
        Attribute attribute;
        if (is_File) {
            attribute = new TextFile(node.getPath() + File.separator + name);
            ((TextFile) attribute).setOnly_read(is_Only_read);
        } else {
            attribute = new Folder(node.getPath() + File.separator + name);
        }
        attribute.setHide(is_Hide);
        attribute.startDisk = b[6];
        if (Attribute.is_correctStartDisk(attribute.startDisk) == false)
            return null;
        attribute.disklen = b[7];
        if (Attribute.is_correctDiskLen(attribute.disklen) == false)
            return null;
        if (!is_File) {
            for (i = 0; i < size / 8; i++) {
                Attribute son = readNode(raf, attribute, i * 8);
                if (son == null)
                    break;
                ((Folder) attribute).add(son);
            }
        } else {
            readContent(raf, (TextFile) attribute);
        }

        return attribute;
    }

    private void readContent(RandomAccessFile raf, TextFile file) throws IOException {
        byte[] b = new byte[128 * 64];
        int start = 0;
        for (int disk : FAT.getFileDisk(file.startDisk)) {
            raf.seek(disk * size);
            raf.read(b, start, 64);//由于随机读取没有文件结束，所以读了很多0
            start += 64;
        }
        int i;
        for(i=0;i<b.length;i++){
            if(b[i] == 0){
                break;
            }
        }
        byte[] bytes = new byte[i];
        for(int j=0;j<i;j++)
            bytes[j] = b[j];//筛掉这些0..
        String content = new String(bytes, "gbk");
        file.setContent(content);
    }

	/*public static void main(String[] args) {

		File file = new File("data_1.dat");
		FileIO df = new FileIO(file);
		// df.changeFile(new File("data_0.dat"));
		Folder head = null;
		try {
			head = df.read_data();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Folder.print(head);
		//head = df.test(head);
		//head.remove(((TextFile) head.getLinkedList().get(1)));
		//head.add("ls",true);
		try {
			df.write_data(head);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Folder test(Folder head) {
		head = new Folder("F:\\Eclipse\\code\\OS");
		head.startDisk = FAT.assignDisk();
		head.add("CS", true);
		head.add("as", false);

		TextFile test = ((TextFile) head.getLinkedList().get(1));
		test.setContent("abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz012");
		// System.out.println("最开始 "+test.getStartDisk());
		((Folder) head.getLinkedList().get(0)).add("bs", true);
		((Folder) head.getLinkedList().get(0)).add("ds", false);
		TextFile test2 = (TextFile) ((Folder) head.getLinkedList().get(0)).getLinkedList().get(1);
		test2.setContent("bilibilibilibil");
		((Folder) ((Folder) head.getLinkedList().get(0)).getLinkedList().get(0)).add("fs", true);
		return head;
	}*/

    private static String correctFileName() {
        int currentNum = 1000000;
        String name = null;
        int numend = 0;
        for (File file : get_DataFile()) {
            String filename = file.getName();
            name = filename;
            numend = filename.lastIndexOf(".dat") - 1;
            int num = 0;
            char c = filename.charAt(numend);
            while (c <= '9' && c >= '0') {
                num = num * 10 + c - '0';
                numend--;
                c = filename.charAt(numend);
            }
            if (currentNum < num)
                break;
            if (currentNum == 1000000)
                currentNum = num + 1;
            else
                currentNum = num + 1;
        }
        if (name == null)
            return "data_0.dat";
        name = name.substring(0, numend + 1) + currentNum + ".dat";
        return name;
    }

    /**
     * 清空被读写文件的内容
     *
     * @throws IOException
     */
    private void clear() throws IOException {
        String path = disk_file.getAbsolutePath();
        disk_file.delete();
        disk_file = new File(path);
        disk_file.createNewFile();
    }
}
