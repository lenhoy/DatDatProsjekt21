public class Main {
    public static void main(String[] args) throws Exception {

        //Use Case 1:
        LoginCtrl login1 = new LoginCtrl(); 
        login1.connect();
        login1.login("a@b.c", "123");


        //Use Case 2: 
        PostCtrl postSession = new PostCtrl("a@b.c");
        postSession.connect();
        postSession.postAThread(4145, "Exam", "Min tredje Thread", "Tag2", "Min tredje Header");


        //USe Case 3:
        PostCtrl replySession = new PostCtrl("ins@ntnu.no");
        replySession.connect();
        replySession.postAReply(6, "Reply 1");


        //Use Case 4:
        SearchCtrl search1 = new SearchCtrl();
        search1.connect();
        search1.search("WAL");

        //Use Case 5: 
        StatsCtrl stats1 = new StatsCtrl();
        stats1.connect();
        stats1.getStats("ins@ntnu.no");
        
    }
}
