package academy.prog;

import java.io.IOException;
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

				Message m = new Message(login, text);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error ocurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
