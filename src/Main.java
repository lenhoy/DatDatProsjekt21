public class Main {
    public static void main(String[] args) throws Exception {

        //TODO app logic here 
        LoginCtrl login1 = new LoginCtrl(); 
        login1.connect();
        login1.login("a@b.c", "123");
        //PostCtrl Session = new PostCtrl(1);
        SearchCtrl search1 = new SearchCtrl();
        search1.connect();
        search1.search("WAL");

        // Session.startThread();

    }
}
