package Model;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class TextFile extends Attribute {
    private String content = "";
    private boolean only_read = false;
    private boolean is_open = false;


    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
        setOpenOrClose(is_open);
    }

    protected TextFile(String path) {
        this.name = path.substring(path.lastIndexOf(File.separator) + 1);
        this.path = path;
    }

    /**
     * 获取文本内容
     * @return 文本内容
     */
    public String getContent(){
        return content;
    }

    /**
     *
     * @param content
     *            文本内容
     */
    protected boolean setContent(String content) throws UnsupportedEncodingException {
        if (content == null)
            content = "";

        if(FAT.assignDisk(this.getStartDisk(), ((content.length() == 0 ? 1 : content.getBytes("gbk").length) + sz - 1) / sz - disklen) == false)
            return false;

        this.content = content;
        this.size = content.getBytes("gbk").length - this.size;// 修改时更新父文件大小
        this.updateSize();
        this.size = content.getBytes("gbk").length;//要改
        this.disklen = FAT.getFileDisk(this.getStartDisk()).size();
        this.setOpenOrClose(false);
        return  true;
    }


    /**
     * 只有打开文件后才能进行此操作。 如果要保存，则将内容content作为参数输入，否则为null
     *
     * @param is_save
     *            true则保存
     * @param content
     *            is_save为false时，content无效
     * @return false则保存文件失败
     * @throws Exception
     *             未打开文件则抛出异常
     */
    public boolean close(boolean is_save, String content) throws Exception {

        if (is_open == false)
            throw new Exception("文件未打开，无法进行此操作");
        if (is_save) {
            return save(content);
        }

        return true;
    }

    /**
     * 打开文件后才能进行保存操作
     *
     * @param content
     *            保存的文本内容
     * @return false则说明磁盘不足，保存失败
     * @throws Exception
     *             未打开文件则抛出异常
     */
    public boolean save(String content) throws Exception {
        if (is_open == false)
            throw new Exception("文件未打开，无法进行此操作");
        if (is_open)
            return setContent(content);
        return false;
    }

    /**
     * @return true则只读，false则读写
     */
    public boolean isOnly_read() {
        return only_read;
    }

    /**
     *
     * @param only_read
     *            为true则只读，false为读写
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
