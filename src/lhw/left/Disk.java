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
	 * �����Ƿ�ռ��
	 * @return false == δʹ��
	 */
	public boolean isUsed(){
		return used;
	}
	
}
