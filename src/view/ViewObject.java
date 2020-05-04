package view;

import java.util.List;

public class ViewObject {

    private static MakeViewObjectStrategy view;
    private static boolean update = false;

    public static void setObject(MakeViewObjectStrategy view) {
        ViewObject.view = view;
        setUpdate(true);
    }

    public static void setUpdate(Boolean up) {
        ViewObject.update = up;
    }

    public static boolean isUpdate() {
        return ViewObject.update;
    }

    public static List<String> makeViewObj() {
        return view.makeView();
    }
}
