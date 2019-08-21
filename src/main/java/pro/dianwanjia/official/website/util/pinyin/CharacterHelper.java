package pro.dianwanjia.official.website.util.pinyin;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description:
 * @version V1.0.0
 */
public class CharacterHelper {
	/**
     * 将字符串中的回车换行等特殊符号转换成html中的转义字符
     * @param str String
     * @return String
     */
    public static String escapeHTMLTags(String str) {
        StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            /*case 60: // '<'
                stringBuffer.append("&lt;");
                break;
            case 62: // '>'
                stringBuffer.append("&gt;");
                break;*/
            case 13: // '\r'
                stringBuffer.append("<br>");
                break;
            case 10: // '\n'
                stringBuffer.append("<br>");
                break;
            /*case 34: // '"'
                stringBuffer.append("&quot");
                break;*/
           /* case 32: // ' '
                stringBuffer.append("&nbsp;");
                break;*/

            default:
                stringBuffer.append(c);
                break;
            }
        }
        return stringBuffer.toString();
    }
    
    
    public static String escapeEspa(String str) {
        StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            
            case 13: // '\r'
                stringBuffer.append("");
                break;
            case 10: // '\n'
                stringBuffer.append("");
                break;
            case 34: // '"'
                stringBuffer.append("\"");
                break;
            case 32: // ' '
                stringBuffer.append(" ");
                break;
            case 39: // ' '
                stringBuffer.append(" ");
                break;

            default:
                stringBuffer.append(c);
                break;
            }
        }
        return stringBuffer.toString();
    }

    
    public static String escapeHTMLTagsGW(String str) {
           StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
           for (int i = 0; i < str.length(); i++) {
               char c = str.charAt(i);
               switch (c) {
               case 60: // '<'
                   stringBuffer.append("");
                   break;

               case 62: // '>'
                   stringBuffer.append("");
                   break;

               case 39: // '\''
                   stringBuffer.append("");
                   break;

               case 34: // '"'
                   stringBuffer.append("");
                   break;
               case 32: // ' '
                   stringBuffer.append("");
                   break;

               default:
                   stringBuffer.append(c);
                   break;
               }
           }
           return stringBuffer.toString();
       }

    
    public static String replaceXMLTags(String str) {
        StringBuffer result = new StringBuffer(str.length() + 20);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            case 38: // '&'
                result.append("&amp;");
                break;
                
            case 39: // '\''
                result.append("&#39;");
                break;

            default:
                result.append(c);
                break;
            }
        }
        return result.toString();
    }

    public static String delHTMLTags(String str){
    	if(str==null||str.equals("")||str.equals("null"))return "";
    	str=str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
    	str=str.replaceAll("\\s*", "").replaceAll("　","");
    	 StringBuffer sb=new StringBuffer("");
    	try{
	    	ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
	        BufferedReader br = new BufferedReader(new UnicodeReader(in,"UTF-8"));
	        String line = br.readLine();
			while(line!=null){
				sb.append(line);
				line = br.readLine();
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return sb.toString();
    }
    
    public static String strBOM(String str){
    	 StringBuffer sb=new StringBuffer("");
    	try{
	    	ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
	        BufferedReader br = new BufferedReader(new UnicodeReader(in,"UTF-8"));
	        String line = br.readLine();
			while(line!=null){
				sb.append(line);
				line = br.readLine();
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	System.out.println(sb.toString());
        return sb.toString();
    }

    
    public static String escapeHTMLTagsZacadArticle(String str) {
        StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            /*case 60: // '<'
                stringBuffer.append("&lt;");
                break;
            case 62: // '>'
                stringBuffer.append("&gt;");
                break;*/
            case 13: // '\r'
                //stringBuffer.append("<br>");
                break;
            case 10: // '\n'
                //stringBuffer.append("<br>");
                break;
            case 34: // '"'
                stringBuffer.append("'");//&quot
                break;
          /* case 32: // ' '
                stringBuffer.append("&nbsp;");
                break;*/

            default:
                stringBuffer.append(c);
                break;
            }
        }
        return stringBuffer.toString();
    }
}
