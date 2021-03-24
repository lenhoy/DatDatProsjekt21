public class Main {
    public static void main(String[] args) throws Exception {

        //TODO app logic here 
        // LoginCtrl login1 = new LoginCtrl(); 
        // login1.connect();
        // login1.login("a@b.c", "123");
        PostCtrl postSession = new PostCtrl("a@b.c");
        postSession.connect();
        //postSession.postAThread(4145, "General", "Min tredje Thread", "Tag2", "Min tredje Header");

        postSession.postAReply(6, "Reply 1");

        
    }
}
