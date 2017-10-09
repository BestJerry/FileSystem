package lhw.Test;

import lhw.left.Attribute;
import lhw.left.FileIO;
import lhw.left.Folder;
import lhw.left.TextFile;

import java.io.File;
import java.util.ArrayList;



public class test {
	public static void main(String[] args) {
		ArrayList<File> data_files = FileIO.get_DataFile();//获取所有数据文件
		File d_file1 = data_files.get(0);
		FileIO rw = new FileIO(d_file1);
		try {
			Folder root = rw.read_data();
			for (Attribute son : root.listFolder()) {//遍历文件夹的所有子节点
				System.out.println(son.getName());
				if (son instanceof Folder) {//如果son是文件夹
					for(Attribute SonOfSon:((Folder) son).listFolder()){
						//一直for或者递归，遍历所有儿子
					}
				} else {//是文本文件就可以进行这些操作
					System.out.println(((TextFile) son).isOnly_read());
					
					((TextFile) son).open();//先打开文件
					System.out.println(((TextFile) son).getContent());
					
					((TextFile) son).save("lhw is sb.");//保存内容
					System.out.println(((TextFile) son).getContent());
					
					String content = "Yes,lhw is sb.";
					boolean is_save = true;
					((TextFile) son).close(is_save, content);//关闭并保存内容
					System.out.println(((TextFile) son).getContent());
				}
			}
			int length = root.get_length();// 所占磁盘块大小
			long  size = root.getSize2();// 文件夹的大小，该大小包括所有子文件的总和
			if (root.get_type() == Attribute.type.FILE) {//这个没啥用，直接instanceof就行
				System.out.println("这是文件");
			} else {
				System.out.println("这是文件夹");
			}
			
			rw.write_data(root);//当程序关闭，把数据保留到文件里
			for(int i = 1;i<FileIO.get_DataFile().size();i++){
				rw.changeDataFile(FileIO.get_DataFile().get(0));//更改数据文件
				//然后xjb搞，感觉没什么用
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
