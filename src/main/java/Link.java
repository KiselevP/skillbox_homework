import java.util.List;

public class Link {
    private volatile String address;
    private volatile int level;
    private volatile List<Link> childList;
    private volatile String parentAddress;

    public Link(String address) {
        this.address = address;
    }

    public String getParentName() {
        return parentAddress;
    }

    public void setParentName(String parentName) {
        this.parentAddress = parentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Link> getChildList() {
        return childList;
    }

    public void setChildList(List<Link> childList) {
        this.childList = childList;
    }
}
