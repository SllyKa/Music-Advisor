package main.argsread;

import java.util.ArrayList;
import java.util.List;

public class ArgsProcess {
    private final String[] argString;
    private List<Pair> args = new ArrayList<>();

    public ArgsProcess(String[] argString) {
        this.argString = argString;
        parse();
    }

    private void parse() {
        if (argString.length == 0) {
            return ;
        }

        for (int i = 0; i < argString.length - 1; i++) {
            Pair pair = new Pair(argString[i], argString[i + 1]);
            args.add(pair);
        }
    }

    public String get(String name) {
        if (args.size() == 0) {
            return null;
        }
        for (Pair p : args) {
            if (p.getKey().equals(name)) {
                return p.getVal();
            }
        }
        return null;
    }

    private class Pair {
        private String key;
        private String val;

        Pair(String key, String val) {
            this.key = key;
            this.val = val;
        }

        public String getKey() {
            return this.key;
        }

        public String getVal() {
            return this.val;
        }
    }
}
