package pro.dianwanjia.official.website.util.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import pro.dianwanjia.official.website.util.pinyin.CharacterHelper;

/**
 * @author LX
 * @date: 2019/8/6 12:03
 * @description:
 * @version V1.0.0
 */
public class PinyinUtil {

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String cn2FirstSpell(String chinese) {
        if (chinese == null || chinese.equals("")) {
            return "";
        }
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    return "";
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String cn2Spell(String chinese) {
        if (chinese == null || chinese.equals("")) {
            return "";
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chinese.length(); i++) {
                String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese
                        .charAt(i), format);
                if (array == null || array.length == 0) {
                    continue;
                }
                String s = array[0];
                char c = s.charAt(0);
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                sb.append(pinyin);
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getCoverUrl(String coverUrl) {
        if (coverUrl == null || coverUrl.equals("")) {
            return "";
        }
        String[] split = coverUrl.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i > 2) {
                sb.append("/" + split[i]);
            }
        }
        return sb.toString();
    }

    public static String escape(String str) {
        String replace = CharacterHelper.escapeEspa(str);
        return replace;
    }

    /**
     * 测试程序
     */
    public static void main(String[] args) {
        String cnStr = "";
        System.out.println("字符串全拼音：" + CharacterHelper.escapeEspa(cn2Spell(cnStr)));
        System.out.println("汉字串首字母：" + cn2FirstSpell(cnStr).toUpperCase());
    }

}