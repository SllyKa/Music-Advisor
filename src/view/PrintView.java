package view;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

public class PrintView {
    private static int pageSize;
    private int curPage;
    private int maxPage;
    private List<String> objs;

    public PrintView(List<String> objs) {
        this.objs = objs;
        this.curPage = 0;
        int tmp = objs.size() % pageSize;
        this.maxPage = tmp == 0 ? objs.size() / pageSize : objs.size() / pageSize + 1;
    }

    public static void setPageSize(int size) {
        PrintView.pageSize = size;
    }

    public String print(Scanner scan) {
        boolean exit = false;
        String choice = null;

        while (!exit) {
            int printed = curPage * pageSize;
            printPage(printed, printed + pageSize);
            do {
                choice = scan.nextLine();
                if ("next".equals(choice)) {
                    if (curPage + 1>= maxPage) {
                        System.out.println("No more pages.");
                        continue;
                    }
                    curPage++;
                    break;
                } else if ("prev".equals(choice)) {
                    if (curPage - 1 < 0) {
                        System.out.println("No more pages.");
                        continue;
                    }
                    curPage--;
                    break;
                } else {
                    exit = true;
                }
            } while (!exit);
        }
        return choice;
    }

    private void printPage(int start, int end) {
        for (int i = start; i < objs.size() && i < end; i++) {
            System.out.print(objs.get(i));
        }
        printPageFooter();
    }

    private void printPageFooter() {
        String page = "---page " + (this.curPage + 1) + " of " + this.maxPage + "---";
        System.out.println(page.toUpperCase());
    }
}
