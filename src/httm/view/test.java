package httm.view;

import java.util.List;

public class test {
    public static void main(String[] args) {
        Function f = new Function();
        
        String s = f.getFromAPI("http://26.155.72.159:5000/api/recommend/565012671");
        List<Integer> li = f.convertJsonToIdList(s);
        System.out.println(li);
        
//        f.sendLog(381380229, 4962, "purchase");
    }
}
