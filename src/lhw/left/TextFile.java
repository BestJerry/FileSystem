package lhw.left;

import java.io.File;

public class TextFile extends Attribute {
	private String content = "";
	private boolean only_read = false;
	private boolean is_open = false;

	protected TextFile(String path) {
		this.name = path.substring(path.lastIndexOf(File.separator) + 1);
		this.path = path;
	}

	/**
	 * ��ȡ�ı�����
	 * @return �ı�����
	 */
	public String getContent(){
		return content;
	}

	/**
	 * 
	 * @param content
	 *            �ı�����
	 */
	protected void setContent(String content) {
		if (content == null)
			content = "";
		this.content = content;
		this.size = content.length() - this.size;// ÿ���ļ�Ĭ����8Byte���޸�ʱ���¸��ļ���С
		this.updateSize();
		this.size = content.length();
		FAT.assignDisk(this.getStartDisk(), ((content.length() == 0 ? 1 : content.length()) + sz - 1) / sz - disklen);
		this.disklen = FAT.getFileDisk(this.getStartDisk()).size();
	}

	public void open() {
		setOpenOrClose(true);
	}

	/**
	 * ֻ�д��ļ�����ܽ��д˲����� ���Ҫ���棬������content��Ϊ�������룬����Ϊnull
	 * 
	 * @param is_save
	 *            true�򱣴�
	 * @param content
	 *            is_saveΪfalseʱ��content��Ч
	 * @throws Exception
	 *             δ���ļ����׳��쳣
	 */
	public void close(boolean is_save, String content) throws Exception {
		if (is_open == false)
			throw new Exception("�ļ�δ�򿪣��޷����д˲���");
		if (is_save)
			save(content);
		setOpenOrClose(false);
	}

	/**
	 * ���ļ�����ܽ��б������
	 * 
	 * @param content
	 *            ������ı�����
	 * @throws Exception
	 *             δ���ļ����׳��쳣
	 */
	public void save(String content) throws Exception {
		if (is_open == false)
			throw new Exception("�ļ�δ�򿪣��޷����д˲���");
		if (is_open)
			setContent(content);
	}

	/**
	 * @return true��ֻ����false���д
	 */
	public boolean isOnly_read() {
		return only_read;
	}

	/**
	 * 
	 * @param only_read
	 *            Ϊtrue��ֻ����falseΪ��д
	 */
	public void setOnly_read(boolean only_read) {
		this.only_read = only_read;
	}

	@Override
	public type get_type() {
		// TODO Auto-generated method stub
		return type.FILE;
	}

	private boolean setOpenOrClose(boolean open) {
		is_open = open;
		if (is_open == false)
			return FileTable.close(this);
		else {
			if (isOnly_read())
				return FileTable.Read(this);
			else
				return FileTable.Write(this);
		}
		// operate
	}

	public boolean is_open() {
		return is_open;
	}

}
