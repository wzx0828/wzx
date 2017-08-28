import java.io.UnsupportedEncodingException;

public class dfgf {
public static void main(String[] args) throws UnsupportedEncodingException {
	String str ="abcd1234";
	//System.out.println(str.substring(1,4));
	byte[]  bt = str.getBytes("gbk");
	byte[] newData = new byte[5];
	System.arraycopy(bt, 0, newData, 0, 4);
	String tmpString =new String(newData, "gbk");
	System.out.println(tmpString);
}
}
