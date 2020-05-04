package main;

import main.argsread.ArgsProcess;
import menu.Menu;
import model.userdata.UserData;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArgsProcess argsProcess = new ArgsProcess(args);
        String domain = argsProcess.get("-access");
        String apiDomain = argsProcess.get("-resource");
        String str = argsProcess.get("-page");
        Integer pageSize = str == null ? 3 : (Integer.parseInt(str));
        pageSize = pageSize <= 0 ? 3 : pageSize;
        apiDomain = apiDomain == null ? "https://api.spotify.com" : apiDomain;
        domain = domain == null ? "https://accounts.spotify.com" : domain;
		String ClientID = "aaaaaaaaaaaaaaaaa"; /* add your ClientID */
		Strign SecretID = "bbbbbbbbbbbbbbbbb"; /* add your SecretID */
        UserData data = new UserData.Builder()
                .addDomain(domain)
                .addApiDomain(apiDomain)
                .addPath("/authorize")
                .addClientId(ClientID)
                .addSecretId(SecretID)
                .addRedirectUri("http://localhost:8080")
                .build();

        //String uri = "https://accounts.spotify.com/authorize";
        //String body = "client_id=34877b52eff54c6791e363196b112479&redirect_uri=http://localhost:8080&response_type=code";

        Menu menu = new Menu(scan, data, pageSize);
        menu.run();
        scan.close();
    }
}

