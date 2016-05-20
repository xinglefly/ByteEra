package com.zhicai.byteera.commonutil;

import android.content.Context;
import android.graphics.Color;
import android.test.suitebuilder.annotation.Suppress;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class StringUtil {
    /**
     * &#x5f97;&#x5230; &#x5168;&#x62fc;
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * &#x5f97;&#x5230;&#x4e2d;&#x6587;&#x9996;&#x5b57;&#x6bcd;
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        convert = convert.toUpperCase();
        return convert;
    }

    /**
     * &#x5c06;&#x5b57;&#x7b26;&#x4e32;&#x8f6c;&#x79fb;&#x4e3a;ASCII&#x7801;
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }
//    public static void main(String[] args) {
//        String cnStr = "你是一只猪吗？";
//        System.out.println(getPingYin(cnStr));
//        System.out.println(getPinYinHeadChar(cnStr));
//        System.out.println(getCnASCII(cnStr));
//    }

    public static SpannableStringBuilder parsePointNum(Context context, String text) {
        String[] split = text.split("\\.");
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        SpannableString integerSpan = new SpannableString(split[0]);
        SpannableString decimalsSpan = new SpannableString("." + split[1]);

        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(UIUtils.dip2px(context, 40));
        AbsoluteSizeSpan span3 = new AbsoluteSizeSpan(UIUtils.dip2px(context, 18));
        integerSpan.setSpan(span2, 0, integerSpan.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        decimalsSpan.setSpan(span3, 0, decimalsSpan.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        multiWord.append(integerSpan);
        multiWord.append(decimalsSpan);

        return multiWord;
    }

    public static SpannableStringBuilder parseNumColor(String string, String... nums) {
        String[] split = string.split("@");
        SpannableStringBuilder multiWord = new SpannableStringBuilder();

        for (int i = 0; i < split.length - 1; i++) {
            ForegroundColorSpan span = new ForegroundColorSpan(Color.BLACK);
            ForegroundColorSpan span2 = new ForegroundColorSpan(Color.parseColor("#c83e3c"));
            SpannableString text = new SpannableString(split[i]);

            SpannableString num = new SpannableString(nums[i]);
            Log.e("text:", text + "");
            Log.e("num:", num + "");
            text.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            num.setSpan(span2, 0, num.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            multiWord.append(text);
            multiWord.append(num);
        }
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLACK);
        SpannableString text = new SpannableString(split[split.length - 1]);
        text.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        multiWord.append(text);
        return multiWord;
    }

    public static SpannableStringBuilder formatTopicComment(String comment) {
        String[] split = comment.split(":");
        split[0] += ": ";
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#30d0df"));
        SpannableString name = new SpannableString(split[0]);
        SpannableString content = new SpannableString(split[1]);
        name.setSpan(span, 0, name.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        multiWord.append(name);
        multiWord.append(content);
        return multiWord;
    }

    public static SpannableStringBuilder formatTopicCommentResponse(String name1, String name2, String response) {
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#30d0df"));
        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.parseColor("#30d0df"));
        ForegroundColorSpan other = new ForegroundColorSpan(Color.parseColor("#999999"));
        SpannableString name1Span = new SpannableString(name1);
        SpannableString name2Span = new SpannableString(name2 + ": ");
        SpannableString otherSpan = new SpannableString("回复");

        SpannableString responseSpan = new SpannableString(response);
        name1Span.setSpan(span, 0, name1Span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        name2Span.setSpan(span2, 0, name2Span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        otherSpan.setSpan(other, 0, otherSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        multiWord.append(name1Span);
        multiWord.append(otherSpan);
        multiWord.append(name2Span);
        multiWord.append(responseSpan);
        return multiWord;
    }


    public static String checkTime(long time) {
        time += 8*60*60*1000;
        Date date = new Date(time);
        Calendar now = Calendar.getInstance();
        int currentDayOfYear = now.get(Calendar.DAY_OF_YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        if (dayOfYear == currentDayOfYear) {
            SimpleDateFormat format = new SimpleDateFormat("今天 HH:mm", Locale.CHINA);
            return format.format(time);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd", Locale.CHINA);
            return format.format(time);
        }
    }


    public static String checkYMDTime(long time) {
        time += 8*60*60*1000;
        Date date = new Date(time);
        Calendar now = Calendar.getInstance();
        int currentDayOfYear = now.get(Calendar.DAY_OF_YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        SimpleDateFormat format = new SimpleDateFormat("dd日 HH:mm:ss ", Locale.CHINA);
        return format.format(time);
    }


}
