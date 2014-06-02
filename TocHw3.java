/**
 * Author:		�����h
 * ID:			F74996170
 * Description:	Ū�JJSON�榡�ɮסA�è̷�Cmd args ��X
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
	 * ���o�������e
	 * 
	 * @param httpAddress
	 *            ���}
	 * @return �Ӻ������e
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
		// �榡����
		if (args.length != 4) {
			System.out.println("�榡���~");
			return;
		}
		// �ӷ�URL
		String URL = args[0];
		// �m����
		String location = args[1];
		// �D���W��
		String road = args[2];
		// �~��(�]���]�t����N���ܭ��H100)
		int year = Integer.valueOf(args[3]) * 100;
		// �������e
		// TODO
		String contents = getPageData(URL);
		//System.out.println(contents);
		//String contents = getFileData(URL);
		// �নJSONArray
		JSONArray JSONArray = new JSONArray(contents);
		// �`����
		double total_price = 0;
		// �ŦX���󪺪����
		int count = 0;
		//System.out.println(JSONArray.length());
		for (int index = 0; index < JSONArray.length(); index++) {
			JSONObject c = JSONArray.getJSONObject(index);
			try {
				if (c.getString("�m����").contains(location) && c.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").contains(road)
						&& c.getInt("����~��") >= year) {
					total_price += c.getInt("�`����");
					count++;
				}
			} catch (Exception e) {
			}
		}

		double avg_price = total_price / count;
		System.out.println((int) avg_price);

	}
}
