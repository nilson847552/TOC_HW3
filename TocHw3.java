/**
 * Author:		王景逸
 * ID:			F74996170
 * Description:	讀入JSON格式檔案，並依照Cmd args 輸出
 * 				
 * */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

public class TocHw3 {
	

	/**
	 * 取得網頁內容
	 * 
	 * @param httpAddress
	 *            網址
	 * @return 該網頁內容
	 */
	public static String getPageData(String httpAddress) {

		URL u = null;
		InputStream in = null;
		InputStreamReader r = null;
		BufferedReader br = null;
		StringBuffer message = null;

		try {

			u = new URL(httpAddress);
			in = u.openStream();

			r = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(r);

			String tempstr = null;
			message = new StringBuffer();
						
			while ((tempstr = br.readLine()) != null) {
				message.append(tempstr);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				u = null;
				in.close();
				r.close();
				br.close();
			} catch (Exception e) {

			}

		}
		return message.toString();
	}

	public static String getFileData(String filename) {
		StringBuffer message = new StringBuffer();
		try {
			// open file (coding UTF-8)
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			try {
				do {
					String buffer = reader.readLine();
					if (buffer == null)
						break;
					message.append(buffer);
				} while (true);
			} catch (Exception e) {
			} finally {
				reader.close();
			}
		} catch (Exception e) {
		}
		return message.toString();
	}

	public static void main(String[] args) throws Exception {
		//String[] args = in.split(" ");
		// 格式不對
		if (args.length != 4) {
			System.out.println("格式錯誤");
			return;
		}
		// 來源URL
		String URL = args[0];
		// 鄉鎮市區
		String location = args[1];
		// 道路名稱
		String road = args[2];
		// 年分(因為包含月份就乾脆乘以100)
		int year = Integer.valueOf(args[3]) * 100;
		// 網頁內容
		// TODO
		String contents = getPageData(URL);
		//System.out.println(contents);
		//String contents = getFileData(URL);
		// 轉成JSONArray
		JSONArray JSONArray = new JSONArray(contents);
		// 總價格
		double total_price = 0;
		// 符合條件的物件數
		int count = 0;
		//System.out.println(JSONArray.length());
		for (int index = 0; index < JSONArray.length(); index++) {
			JSONObject c = JSONArray.getJSONObject(index);
			try {
				if (c.getString("鄉鎮市區").contains(location) && c.getString("土地區段位置或建物區門牌").contains(road)
						&& c.getInt("交易年月") >= year) {
					total_price += c.getInt("總價元");
					count++;
				}
			} catch (Exception e) {
			}
		}

		double avg_price = total_price / count;
		System.out.println((int) avg_price);

	}
}
