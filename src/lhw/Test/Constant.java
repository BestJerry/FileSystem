package lhw.Test;


public class Constant {
	public static  double FPWIDTH = 1672, FPHEIGHT = 1003;
	private static double row = 9,col = 15;
	public static double WIDTHUNIT = FPWIDTH / col,HEIGHTUNIT = FPHEIGHT / row;


	
	
	public static void setRow(double r){
		row = r;
		HEIGHTUNIT = FPHEIGHT / row;
	}
	public static void  setCol(double c){
		col = c;
		WIDTHUNIT = FPWIDTH / col;
	}
}
