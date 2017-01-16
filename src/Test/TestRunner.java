package Test;


import org.junit.runner.JUnitCore;
//this is used for command line excution only
//If you are running this using Idea, don't run this class.

public class TestRunner {
    public static void main(String[] args) {
        new JUnitCore().run(TrelloClientTest.class);
    }
}
