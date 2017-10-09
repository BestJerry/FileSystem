package lhw.left;
/** 
 * 
 */
public class Disk {
	public static enum State{
		USED,UNUSED
	}
	private boolean used = false;
	protected Disk() {
		// TODO Auto-generated constructor stub
	}
	protected void setUsed(boolean used){
		this.used = used;
	}
	/**
	 * 磁盘是否被占用
	 * @return false == 未使用
	 */
	public boolean isUsed(){
		return used;
	}
	
}
