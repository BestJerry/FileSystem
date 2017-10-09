package lhw.left;


public abstract class Attribute {
	/**
	 * 文件路径
	 * @return
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 获取文件名
	 * @return
	 */
	public String getName() {
		return name;
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

	protected static boolean is_correctName(String name) {
		if (name == null || name.length() > 3 || name.length() == 0)
			return false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '$' || c == '/' || c == '.')
				return false;
		}
		return true;
	}

	protected static boolean is_correctStartDisk(int startDisk) {
		return startDisk >= 2 && startDisk < 128;
	}

	protected static boolean is_correctDiskLen(int disklen) {
		return disklen >= 1 && disklen <= 125;
	}
}