import java.util.Random;
class Bug2798271 {
    private Random random;
    public Random getRandom() {
        if (null == random) { setRandom(new Random()); }
        return random;
    }
    void setRandom(Random random) { this.random = random; }
    public String getRandomString(final String label) { return "-= random " + label + ": " + getRandom().nextInt(10000) + " =-"; }
}