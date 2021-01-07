package classes;

public class DatabaseInfo {
    public final int newUnique;
    public final int newDuplicate;
    public final int totalUnique;
    public DatabaseInfo(int newUnique, int totalUnique, int newDuplicate) {
        this.newUnique = newUnique;
        this.totalUnique = totalUnique;
        this.newDuplicate = newDuplicate;
    }
}
