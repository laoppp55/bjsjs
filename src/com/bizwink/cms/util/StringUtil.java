package com.bizwink.cms.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String iso2gb(String str) {
        return str;
    }

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    public static String gb2jis(String str) {
        try {
            return new String(str.getBytes(), "Shift_JIS");
        }
        catch (Exception e) {
            return str;
        }
    }



    public static final String replace(String line, String oldString, String newString) {
        int i = 0;
        if (line != null) {
            if ((i = line.indexOf(oldString, i)) >= 0) {
                char[] line2 = line.toCharArray();
                char[] newString2 = newString.toCharArray();
                int oLength = oldString.length();
                StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);
                i += oLength;
                int j = i;
                while ((i = line.indexOf(oldString, i)) > 0) {
                    buf.append(line2, j, i - j).append(newString2);
                    i += oLength;
                    j = i;
                }
                buf.append(line2, j, line2.length - j);
                return buf.toString();
            }
        }
        return line;
    }

    public static final String replace(String line, String oldString, String newString, int[] count) {
        if (line == null) {
            return null;
        }

        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }



    public static final String convTemplate(Object s) {
        if (s == null) {
            return "";
        }

        String t = (String) s;
        t = replaceJS(t, "\\", "\\\\"); // replace backslash with \\
        t = replaceJS(t, "'", "\\\'");  // replace an single quote with \'
        t = replaceJS(t, "\"", "\\\""); // replace a double quote with \"
        t = replaceJS(t, "\r", "\\r"); // replace CR with \r;
        t = replaceJS(t, "\n", "\\n"); // replace LF with \n;
        return t;
    }

    public static final String replaceJS(String s, String one, String another) {
        if (s.equals("")) return "";
        String res = "";
        int i = s.indexOf(one, 0);
        int lastpos = 0;
        while (i != -1) {
            res += s.substring(lastpos, i) + another;
            lastpos = i + one.length();
            i = s.indexOf(one, lastpos);
        }
        res += s.substring(lastpos);  // the rest
        return res;
    }

    //获取标记之间的内容
    public static final List getContentByTag(String content, String startTag, String endTag) {
        List list = new ArrayList();
        String tempBuf = content.toLowerCase();
        String lowcaseStartTag = startTag.toLowerCase();
        String lowcaseEndTag = endTag.toLowerCase();
        String field = "";
        int num = 1;
        int posi = 0;
        int startTagPosi = 0;
        int endTagPosi = 0;
        int startTagLength = startTag.length();
        int endTagLength = endTag.length();

        startTagPosi = tempBuf.indexOf(lowcaseStartTag);
        while (startTagPosi != -1) {
            endTagPosi = tempBuf.indexOf(lowcaseEndTag);
            if (endTagPosi != -1) {
                field = content.substring(startTagPosi + startTag.length(), endTagPosi);
                list.add(field);
                tempBuf = tempBuf.substring(endTagPosi + endTagLength);
                content = content.substring(endTagPosi + endTagLength);
            }
            startTagPosi = tempBuf.indexOf(lowcaseStartTag);
        }

        return list;
    }

    //获取标记之间的内容
    public static final String getContentFromHTML(String content, String startTag, String endTag) {
        List list = new ArrayList();
        String tempBuf = content.toLowerCase();
        tempBuf = StringUtil.replace(tempBuf, "&lt;", "<");
        tempBuf = StringUtil.replace(tempBuf, "&gt;", ">");
        String lowcaseStartTag = startTag.toLowerCase();
        String lowcaseEndTag = endTag.toLowerCase();
        String field = "";
        int startTagPosi = 0;
        int endTagPosi = 0;
        int endTagLength = endTag.length();
        startTagPosi = tempBuf.indexOf(lowcaseStartTag);

        while (startTagPosi != -1) {
            endTagPosi = tempBuf.indexOf(lowcaseEndTag);
            if (endTagPosi != -1) {
                field = content.substring(startTagPosi + startTag.length(), endTagPosi);
                list.add(field);
                tempBuf = tempBuf.substring(endTagPosi + endTagLength);
                content = content.substring(endTagPosi + endTagLength);
            }
            startTagPosi = tempBuf.indexOf(lowcaseStartTag);
        }

        tempBuf = "";
        for (int i=0; i<list.size(); i++) {
            tempBuf = tempBuf + (String)list.get(i);
        }

        return tempBuf;
    }

    //获取HTML文件的文本内容
    public static final String getContentFromHTML(String content) {
        String tempBuf = "";
        Pattern p = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
        if (content != null) {
            String buf[] = p.split(content);

            for(int i=0; i<buf.length; i++) {
                tempBuf = tempBuf + buf[i];
            }
        }

        return tempBuf;
    }

    public static final Hashtable getImageAttribute(String imgstr) {
        Hashtable hash = new Hashtable();

        if (imgstr != null && imgstr.trim().length() > 0) {
            //分析宽和高
            int width = 0;
            int height = 0;
            Pattern p = Pattern.compile("style(\\s*)=(\\s*)['\"]width(\\s*):(\\s*)[0-9]*px(\\s*);(\\s*)height(\\s*):(\\s*)[0-9]*px(\\s*)['\"]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(imgstr);
            if (m.find()) {
                String style = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]*px", Pattern.CASE_INSENSITIVE);
                m = p.matcher(style);
                if (m.find()) {
                    String widths = style.substring(m.start(), m.end());
                    width = Integer.parseInt(widths.substring(0, widths.indexOf("px")));
                }
                if (m.find()) {
                    String heights = style.substring(m.start(), m.end());
                    height = Integer.parseInt(heights.substring(0, heights.indexOf("px")));
                }
            } else {
                String heights = "";
                p = Pattern.compile("height(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) heights = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
                m = p.matcher(heights);
                if (m.find()) height = Integer.parseInt(heights.substring(m.start()));

                String widths = "";
                p = Pattern.compile("width(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) widths = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
                m = p.matcher(widths);
                if (m.find()) width = Integer.parseInt(widths.substring(m.start()));
            }

            //分析src
            String src = "";
            p = Pattern.compile("src(\\s*)=(\\s*)['\"][^'\"]*(\\.gif|\\.jpg|\\.jpeg|\\.png|\\.bmp)['\"]", Pattern.CASE_INSENSITIVE);
            m = p.matcher(imgstr);
            if (m.find()) {
                src = imgstr.substring(m.start(), m.end());
            } else {
                p = Pattern.compile("src(\\s*)=(\\s*)[^'\"]*(\\.gif|\\.jpg|\\.jpeg|\\.png|\\.bmp)", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) src = imgstr.substring(m.start(), m.end());
            }
            if (src != null && src.trim().length() > 0) {
                if (src.indexOf("\"") > -1)
                    src = src.substring(src.indexOf("\"") + 1, src.lastIndexOf("\""));
                else if (src.indexOf("'") > -1)
                    src = src.substring(src.indexOf("'") + 1, src.lastIndexOf("'"));
                else
                    src = src.substring(src.indexOf("=") + 1);
            }

            hash.put("width", String.valueOf(width));
            hash.put("height", String.valueOf(height));
            hash.put("alt", processStringAttr("alt", imgstr));                         //分析alt
            hash.put("src", processStringAttr("src", imgstr));                         //分析alt
            hash.put("align", processStringAttr("align", imgstr));                     //分析align
            hash.put("border", String.valueOf(processIntegerAttr("border", imgstr)));  //分析border
            hash.put("hspace", String.valueOf(processIntegerAttr("hspace", imgstr)));  //分析hspace
            hash.put("vspace", String.valueOf(processIntegerAttr("vspace", imgstr)));  //分析vspace
        }
        return hash;
    }

    private final static String processStringAttr(String attrName, String imgStr) {
        String attrValue = "";
        Pattern p = Pattern.compile(attrName + "(\\s*)=(\\s*)['\"][^'\"]*['\"]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(imgStr);
        if (m.find()) {
            attrValue = imgStr.substring(m.start(), m.end());
        } else {
            p = Pattern.compile(attrName + "(\\s*)=(\\s*)[^>|\\s]*", Pattern.CASE_INSENSITIVE);
            m = p.matcher(imgStr);
            if (m.find()) attrValue = imgStr.substring(m.start(), m.end());
        }

        if (attrValue != null && attrValue.trim().length() > 0) {
            if (attrValue.indexOf("\"") > -1)
                attrValue = attrValue.substring(attrValue.indexOf("\"") + 1, attrValue.lastIndexOf("\""));
            else if (attrValue.indexOf("'") > -1)
                attrValue = attrValue.substring(attrValue.indexOf("'") + 1, attrValue.lastIndexOf("'"));
            else
                attrValue = attrValue.substring(attrValue.indexOf("=") + 1);
        }
        return attrValue;
    }

    private final static int processIntegerAttr(String attrName, String imgStr) {
        int attrValue = 0;
        Pattern p = Pattern.compile(attrName + "(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(imgStr);
        if (m.find()) {
            String temp = imgStr.substring(m.start(), m.end());
            if (temp.indexOf("\"") > -1)
                temp = temp.substring(temp.indexOf("\"") + 1, temp.lastIndexOf("\""));
            else if (temp.indexOf("'") > -1)
                temp = temp.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));
            else
                temp = temp.substring(temp.indexOf("=") + 1);

            if (temp != null) attrValue = Integer.parseInt(temp);
        }
        return attrValue;
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("D:\\webbuilder\\webapps\\cms\\sites\\petersong_coosite_com\\shoucang\\multimedia\\20120115\\1.txt");
            BufferedReader br = new BufferedReader(reader);
            String s1 = null;
            s1 = br.readLine();
            br.close();
            reader.close();

            System.out.println(s1);

            char c[] = s1.toCharArray();
            for (int i = 0; i < c.length; i++) {
                int m = (int) c[i];
                String word = Integer.toBinaryString(m);
                System.out.println(word);
            }


            byte[] fullByte = gbk2utf8("<html> <head>#$%中文mmmmm");
            String fullStr = new String(fullByte);
            System.out.println("string from GBK to UTF-8 byte:  " + fullStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] gbk2utf8(String chenese) {
        // Step 1: 得到GBK编码下的字符数组，一个中文字符对应这里的一个c
        char c[] = chenese.toCharArray();

        // Step 2: UTF-8使用3个字节存放一个中文字符，所以长度必须为字符的3 倍
        byte[] fullByte = new byte[3 * c.length];
        StringBuffer buf = new StringBuffer();

        //UTF-8编码的单字节空间0000-007f
        int onebyte_min_value = Integer.parseInt("0000", 16);
        int onebyte_max_value = Integer.parseInt("007F", 16);

        //UTF-8编码的双字节空间00080-07ff
        int twobyte_min_value = Integer.parseInt("0080", 16);
        int twobyte_max_value = Integer.parseInt("07FF", 16);

        //UTF-8编码的双字节空间00800-ffff
        int threebyte_min_value = Integer.parseInt("0800", 16);
        int threebyte_max_value = Integer.parseInt("FFFF", 16);

        System.out.println("three_max_value=" + threebyte_max_value);
        System.out.println("three_min_value=" + threebyte_min_value);

        System.out.println("two_max_value=" + twobyte_max_value);
        System.out.println("two_min_value=" + twobyte_min_value);

        System.out.println("one_max_value=" + onebyte_max_value);
        System.out.println("one_min_value=" + onebyte_min_value);

        // Step 3: 循环将字符的GBK编码转换成UTF-8编码
        int posi = 0;
        for (int i = 0; i < c.length; i++) {
            // Step 3-1：将字符的ASCII编码转换成2进制值
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            System.out.println(word);
            System.out.println(c[i]);

            if (m>threebyte_min_value && m<threebyte_max_value) {
                // Step 3-2：将2进制值补足16位(2个字节的长度)
                StringBuffer sb = new StringBuffer();
                int len = 16 - word.length();
                for (int j = 0; j < len; j++) {
                    sb.append("0");
                }
                // Step 3-3：得到该字符最终的2进制GBK编码
                // 形似：1000 0010 0111 1010
                sb.append(word);
                System.out.println(sb.toString());

                // Step 3-4：最关键的步骤，根据UTF-8的汉字编码规则，首字节
                // 以1110开头，次字节以10开头，第3字节以10开头。在原始的2进制
                // 字符串中插入标志位。最终的长度从16--->16+3+2+2=24。
                sb.insert(0, "1110");
                sb.insert(8, "10");
                sb.insert(16, "10");
                System.out.println(sb.toString());

                // Step 3-5：将新的字符串进行分段截取，截为3个字节
                String s1 = sb.substring(0, 8);
                String s2 = sb.substring(8, 16);
                String s3 = sb.substring(16);

                // Step 3-6：最后的步骤，把代表3个字节的字符串按2进制的方式
                // 进行转换，变成2进制的整数，再转换成16进制值
                byte b0 = Integer.valueOf(s1, 2).byteValue();
                byte b1 = Integer.valueOf(s2, 2).byteValue();
                byte b2 = Integer.valueOf(s3, 2).byteValue();

                // Step 3-7：把转换后的3个字节按顺序存放到字节数组的对应位置
                byte[] bf = new byte[3];
                bf[0] = b0;
                bf[1] = b1;
                bf[2] = b2;

                buf.append(new String(bf));
                fullByte[posi] = bf[0];
                fullByte[posi + 1] = bf[1];
                fullByte[posi + 2] = bf[2];
                posi = posi +3;
            } else if (m>twobyte_min_value && m<twobyte_max_value){       //双字节的UTF-8编码
                // Step 3-2：将2进制值补足16位(2个字节的长度)
                StringBuffer sb = new StringBuffer();
                int len = 16 - word.length();
                for (int j = 0; j < len; j++) {
                    sb.append("0");
                }
                // Step 3-3：得到该字符最终的2进制GBK编码
                // 形似：1000 0010 0111 1010
                sb.append(word);

                // Step 3-4：最关键的步骤，根据UTF-8的编码规则，两字节长的字符首字节
                // 以110开头，次字节以10开头。在原始的2进制
                // 字符串中插入标志位。
                sb.insert(0, "110");
                sb.insert(8, "10");
                System.out.println(sb.toString());

                // Step 3-5：将新的字符串进行分段截取，截为3个字节
                String s1 = sb.substring(0, 8);
                String s2 = sb.substring(8, 16);

                // Step 3-6：最后的步骤，把代表2个字节的字符串按2进制的方式
                // 进行转换，变成2进制的整数，再转换成16进制值
                byte b0 = Integer.valueOf(s1, 2).byteValue();
                byte b1 = Integer.valueOf(s2, 2).byteValue();

                // Step 3-7：把转换后的2个字节按顺序存放到字节数组的对应位置
                byte[] bf = new byte[2];
                bf[0] = b0;
                bf[1] = b1;

                buf.append(new String(bf));
                fullByte[posi] = bf[0];
                fullByte[posi + 1] = bf[1];
                posi = posi +2;
            } else {                                                       //单字节的UTF-8编码
                StringBuffer sb = new StringBuffer();
                int len = 7 - word.length();
                for (int j = 0; j < len; j++) {
                    sb.append("0");
                }
                sb.append(word);

                // Step 3-4：最关键的步骤，根据UTF-8的编码规则，单字节长的字符首字节
                // 以0开头。在原始的2进制字符串中插入标志位。
                sb.insert(0, "0");
                String s1 = sb.substring(0, 8);

                // Step 3-6：最后的步骤，把代表2个字节的字符串按2进制的方式
                // 进行转换，变成2进制的整数，再转换成16进制值
                byte b0 = Integer.valueOf(s1, 2).byteValue();

                // Step 3-7：把转换后的1个字节按顺序存放到字节数组的对应位置
                byte[] bf = new byte[1];
                bf[0] = b0;

                buf.append(new String(bf));
                fullByte[posi] = bf[0];
                posi = posi +1;
            }
            // Step 3-8：返回继续解析下一个中文字符
        }

        return fullByte;
    }



}