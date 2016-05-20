/**
 * 
 */
package com.zhicai.byteera.activity.traincamp.util;

import android.os.Environment;

import com.file.zip.ZipEntry;
import com.file.zip.ZipFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/**
 * @author loading
 * 
 */
public class GuessUtils {

	public static String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static String GUESS_DOWNLOAD_PATH = SDCARD_PATH + File.separator + "byteera" + File.separator + "download" + File.separator + "guess" + File.separator;

	public static String GUESS_RESOURCE_PATH = SDCARD_PATH + File.separator + "byteera" + File.separator + "resource" + File.separator + "guess" + File.separator;

	/**
	 * 解压缩功能. 将zipFile文件解压到folderPath目录下.
	 * 
	 * @throws Exception
	 */
	public static int upZipFile(File zipFile, String folderPath)
			throws IOException {
		ZipFile zfile = new ZipFile(zipFile, "GB2312");
		Enumeration<ZipEntry> zList = zfile.getEntries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = zList.nextElement();
			if (ze.isDirectory()) {
				String dirstr = folderPath + ze.getName();
				// dirstr.trim();
				dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
				File f = new File(dirstr);
				f.mkdir();
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zfile.close();
		return 0;
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		String substr = null;
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				substr = dirs[i];
				try {
					// substr.trim();
					substr = new String(substr.getBytes("8859_1"), "GB2312");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ret = new File(ret, substr);

			}
			if (!ret.exists())
				ret.mkdirs();
			substr = dirs[dirs.length - 1];
			// try {
			substr.trim();
			// substr = new String(substr.getBytes("8859_1"), "GB2312");
			// Log.d("upZipFile", "substr = " + substr);
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }

			ret = new File(ret, substr);
			return ret;
		}
		return ret;
	}

	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "gbk");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 读取SD卡中文本文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readSDFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(GUESS_RESOURCE_PATH + fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			return getString(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
