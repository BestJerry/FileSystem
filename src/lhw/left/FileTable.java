package lhw.left;

import lhw.left.TextFile;

public class FileTable {
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
	 * @return  true 代表文件以Type的方式打开
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
	 * @param  以Type的方式打开一个文件
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
	 *
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
	 *
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
