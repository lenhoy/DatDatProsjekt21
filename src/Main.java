public class Main {
    public static void main(String[] args) throws Exception {

        //Use Case 1:
        LoginCtrl login1 = new LoginCtrl(); 
        login1.connect();
        login1.login("a@b.c", "123");


        //Use Case 2: 
        PostCtrl postSession = new PostCtrl("a@b.c");
        postSession.connect();
        postSession.postAThread(4145, "General", "Min første Thread", "Tag1", "Min første Header");


        //Use Case 4:
        SearchCtrl search1 = new SearchCtrl();
        search1.connect();
        search1.search("WAL");


        
    }
}
