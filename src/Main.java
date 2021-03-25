public class Main {
    public static void main(String[] args) throws Exception {

        //Use Case 1:
        //En bruker logger inn
        LoginCtrl login1 = new LoginCtrl(); 
        login1.connect();
        login1.login("a@b.c", "123");


        //Use Case 2: 
        //En bruker poster et innlegg
        PostCtrl postSession = new PostCtrl("a@b.c");
        postSession.connect();
        postSession.postAThread(4145, "Exam", "Min Thread", "Question", "Min Header");


        //USe Case 3:
        //En instruktør svarer på post med ID: 16
        PostCtrl replySession = new PostCtrl("ins@ntnu.no");
        replySession.connect();
        replySession.postAReply(16, "Reply 1");


        //Use Case 4:
        //En bruker søker etter alle poster som inneholder 'WAL'
        SearchCtrl search1 = new SearchCtrl();
        search1.connect();
        search1.search("WAL");

        //Use Case 5: 
        //En instruktør henter frem statistikk
        StatsCtrl stats1 = new StatsCtrl();
        stats1.connect();
        stats1.getStats("ins@ntnu.no");
        
    }
}
