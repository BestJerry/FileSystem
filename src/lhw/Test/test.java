package lhw.Test;

import lhw.left.Attribute;
import lhw.left.FileIO;
import lhw.left.Folder;
import lhw.left.TextFile;

import java.io.File;
import java.util.ArrayList;



public class test {
	public static void main(String[] args) {
		ArrayList<File> data_files = FileIO.get_DataFile();//��ȡ���������ļ�
		File d_file1 = data_files.get(0);
		FileIO rw = new FileIO(d_file1);
		try {
			Folder root = rw.read_data();
			for (Attribute son : root.listFolder()) {//�����ļ��е������ӽڵ�
				System.out.println(son.getName());
				if (son instanceof Folder) {//���son���ļ���
					for(Attribute SonOfSon:((Folder) son).listFolder()){
						//һֱfor���ߵݹ飬�������ж���
					}
				} else {//���ı��ļ��Ϳ��Խ�����Щ����
					System.out.println(((TextFile) son).isOnly_read());
					
					((TextFile) son).open();//�ȴ��ļ�
					System.out.println(((TextFile) son).getContent());
					
					((TextFile) son).save("lhw is sb.");//��������
					System.out.println(((TextFile) son).getContent());
					
					String content = "Yes,lhw is sb.";
					boolean is_save = true;
					((TextFile) son).close(is_save, content);//�رղ���������
					System.out.println(((TextFile) son).getContent());
				}
			}
			int length = root.get_length();// ��ռ���̿��С
			long  size = root.getSize2();// �ļ��еĴ�С���ô�С�����������ļ����ܺ�
			if (root.get_type() == Attribute.type.FILE) {//���ûɶ�ã�ֱ��instanceof����
				System.out.println("�����ļ�");
			} else {
				System.out.println("�����ļ���");
			}
			
			rw.write_data(root);//������رգ������ݱ������ļ���
			for(int i = 1;i<FileIO.get_DataFile().size();i++){
				rw.changeDataFile(FileIO.get_DataFile().get(0));//���������ļ�
				//Ȼ��xjb�㣬�о�ûʲô��
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
