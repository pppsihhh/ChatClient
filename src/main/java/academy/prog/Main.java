package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
	static String login;
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			login = scanner.nextLine();

	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				if (text.equals("#users")) {
					Gson gson = new GsonBuilder().create();
					URL url = new URL(Utils.getURL() + "/users");
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					InputStream is = http.getInputStream();
					byte[] buf1 = GetThread.responseBodyToArray(is);
					String ss1 = new String(buf1,StandardCharsets.UTF_8);
					OnlineJsp on = gson.fromJson(ss1,OnlineJsp.class);
					System.out.println(on);

				} else {

					Message m = new Message(login, text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) { // 200 OK
						System.out.println("HTTP error ocurred: " + res);
						return;
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
