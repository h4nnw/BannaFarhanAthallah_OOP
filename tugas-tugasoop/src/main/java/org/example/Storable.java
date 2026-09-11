public interface Storable {
    void save(String location);
    String load(String location);
}

public class UserProfile implements Storable {
    @Override
    public void save(String location){}
    @Override
    public String load(String location){}
}

public class GameSettings implements Storable{
    @Override
    public void save(String location){}
    @Override
    public String load(String location){}
}

public void backupData(Storable item, String backupLocation){
    item.save(backupLocation);
}

void main() {
}