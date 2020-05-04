package api.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

class Server {
    private HttpServer server;
    private String req;
    private boolean isAccept = false;

    public Server() throws IOException {
        server = HttpServer.create();
    }

    public void set() throws IOException {
        server.bind(new InetSocketAddress(8080), 0);
        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                String message;
                req = exchange.getRequestURI().getQuery();
                if (req == null || req.split("=")[0].equals("error")) {
                    message = "Not found authorization code. Try again.";
                } else {
                    message = "Got the code. Return back to your program.";
                    isAccept = true;
                }
                exchange.sendResponseHeaders(200, message.length());
                exchange.getResponseBody().write(message.getBytes());
                exchange.getResponseBody().close();
            }
        });
    }

    public String getReq() {
        return this.req;
    }

    public boolean isAccept() {
        return this.isAccept;
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(1);
    }
}
