package lhw.left;


public abstract class Attribute {
	/**
	 * �ļ�·��
	 * @return
	 */
	public String getPath() {
		return path;
	}
	/**
	 * ��ȡ�ļ���
	 * @return
	 */
	public String getName() {
		return name;
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