package Model;

import java.io.File;

public class TextFile extends Attribute {
    private String content = "";
    private boolean only_read = false;
    private boolean is_open = true;


    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

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
    protected boolean setContent(String content) {
        if (content == null)
            content = "";
        if(FAT.assignDisk(this.getStartDisk(), ((content.length() == 0 ? 1 : content.length()) + sz - 1) / sz - disklen) == false)
            return false;
        this.content = content;
        this.size = content.length() - this.size;// ÿ���ļ�Ĭ����8Byte���޸�ʱ���¸��ļ���С
        this.updateSize();
        this.size = content.length();
        this.disklen = FAT.getFileDisk(this.getStartDisk()).size();
        System.out.println(disklen+" "+size+" "+content+" "+this.name);
        this.setOpenOrClose(false);
        return  true;
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
     * @return false�򱣴��ļ�ʧ��
     * @throws Exception
     *             δ���ļ����׳��쳣
     */
    public boolean close(boolean is_save, String content) throws Exception {
        if (is_open == false)
            throw new Exception("�ļ�δ�򿪣��޷����д˲���");
        if (is_save) {
            return save(content);
        }
        return true;
    }

    /**
     * ���ļ�����ܽ��б������
     *
     * @param content
     *            ������ı�����
     * @return false��˵�����̲��㣬����ʧ��
     * @throws Exception
     *             δ���ļ����׳��쳣
     */
    public boolean save(String content) throws Exception {
        if (is_open == false)
            throw new Exception("�ļ�δ�򿪣��޷����д˲���");
        if (is_open)
            return setContent(content);
        return false;
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
