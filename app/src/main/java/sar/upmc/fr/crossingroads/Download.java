package sar.upmc.fr.crossingroads;

import android.os.StrictMode;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class Download {

	private final static int size = 4096;

	public static byte[] DownloadFromUrl(String Url) {

		URL url;
		URLConnection ucon;
		BufferedInputStream bis;
		ByteArrayOutputStream tmp;
		byte [] result;

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			url = new URL(Url);

			ucon = url.openConnection();

			bis = new BufferedInputStream(ucon.getInputStream());


			byte [] tab = new byte[size];
			int nb_byte_read = 0;

			tmp = new ByteArrayOutputStream();
			while ((nb_byte_read = bis.read(tab)) > 0) {
				tmp.write(tab, 0, nb_byte_read);
			}
            result = tmp.toByteArray();
			tmp.close();
			bis.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
