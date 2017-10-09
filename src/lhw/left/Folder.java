package lhw.left;

import java.io.File;
import java.util.LinkedList;

public class Folder extends Attribute {
	private LinkedList<Attribute> linkedList = new LinkedList<>();

	protected Folder(String path) {
		this.name = path.substring(path.lastIndexOf(File.separator) + 1);
		this.path = path;
	}

	protected LinkedList<Attribute> getLinkedList() {
		return linkedList;// ֻ�����޸ĵĻ�DiskFileû����
	}
	/**
	 * ���ļ��µ������ļ��������ļ��У�
	 * @return �ļ�������
	 */
	public LinkedList<Attribute> listFolder() {
		LinkedList<Attribute> list = new LinkedList<>();
		for (Attribute temp : linkedList) {
			list.add(temp);
		}
		return list;
	}
	/**
	 * ����ļ������ļ����ܳ���8��
	 * @param name �ļ�����������'$' �� '.' �� '/'
	 * @param is_folder true�����ļ��У�false�����ļ�
	 * @return false ��(name���Ϸ������ļ�����8�����������)
	 */
	public boolean add(String name, boolean is_folder) {
		if (linkedList.size() == 8)
			return false;
		if(is_correctName(name) == false) return false;
		Attribute tmp;
		if (is_folder)
			tmp = new Folder(this.path + File.separator + name);
		else
			tmp = new TextFile(this.path + File.separator + name);
		int startDiskNum = FAT.assignDisk();
		if (startDiskNum == -1)
			return false;
		tmp.startDisk = startDiskNum;
		linkedList.add(tmp);
		tmp.faNode = this;
		tmp.updateSize();
		return true;
	}
	protected boolean add(Attribute node){
		this.linkedList.add(node);
		node.faNode = this;
		node.updateSize();
		return true;
	}
	/**
	 * ɾ�����ļ�
	 * @param file ���ļ��Ķ���
	 * @return false���ļ����ܴ��ڴ�״̬
	 */
	public boolean remove(Attribute file) {
		if(file instanceof TextFile && ((TextFile)file).is_open()) return false;
		if (linkedList.remove(file)) {
			file.size = -file.size;
			file.updateSize();
			file.faNode = null;
			FAT.recycleDisk(file.startDisk);
			return true;
		}
		return false;
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
