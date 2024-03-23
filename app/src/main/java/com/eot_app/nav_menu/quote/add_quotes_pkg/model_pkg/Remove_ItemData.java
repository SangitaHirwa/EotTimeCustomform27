package com.eot_app.nav_menu.quote.add_quotes_pkg.model_pkg;

import java.util.ArrayList;

public class Remove_ItemData {
    ArrayList<String> iqmmId;
//    String invId;
    /*Remove invId variable after discuss with Rani Yadav for 2.92 release on 26 Dec 2023*/
    public Remove_ItemData(ArrayList<String> iqmmId/*, String invId*/) {
        this.iqmmId = iqmmId;
//        this.invId = invId;
    }
}
