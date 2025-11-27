import sfBugsNew.*;
import org.junit.Assert;
public abstract class Bug1363 {
    static class GitModelObject {}
    static class GitModelObjectContainer extends GitModelObject{};
    static class GitModelBlob extends GitModelObject {};
    static class GitModelCommit extends GitModelObjectContainer {};
    public abstract GitModelBlob createGitModelBlob();
    public void shouldBeSymmetric1() throws Exception {
        GitModelBlob left = new GitModelBlob(); createGitModelBlob();
        GitModelCommit right = new GitModelCommit();
        boolean actual1 = left.equals(right);
        boolean actual2 = right.equals(left);
        Assert.assertTrue(!actual1);
        Assert.assertTrue(!actual2);
    }
}