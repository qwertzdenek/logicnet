package icp.a05;

public class Main {
    public static void main(String[] args) {
        MyTopicModel lda = new MyTopicModel();

        lda.train();
        lda.test();
    }
}
