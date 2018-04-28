package TieFighter;

public class Pilot implements Comparable<Pilot> {
    protected String name;
    protected Double length;
    protected Boolean valid;

    public Pilot(String name, Double length, Boolean valid) {
        this.name = name;
        this.length = length;
        this.valid = valid;
    }

    @Override
    public int compareTo(Pilot p) {
        if (this.valid ^ p.valid) {
            return p.valid.compareTo(this.valid);
        }
        if (this.length.equals(p.length)) {
            return this.name.compareTo(p.name);
        }
        return this.length.compareTo(p.length);
    }

    @Override
    public String toString() {
        return "\nPilot{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", valid=" + valid +
                "}";
    }
}
