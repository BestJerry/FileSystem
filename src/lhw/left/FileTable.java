package lhw.left;

import lhw.left.TextFile;

public class FileTable {
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
	 * @return  true �����ļ���Type�ķ�ʽ��
	 */
	private static boolean exist(TextFile NewFile, int Type) {
		for (int i = 0; i < CurNumOfOpenFile; i++) {
			if (file[i].equals(NewFile)) {
				return type[i] == Type;
			}
		}
		return false;
	}
	
	/*
	 * @param  ��Type�ķ�ʽ��һ���ļ�
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
	 *
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
	 *
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
