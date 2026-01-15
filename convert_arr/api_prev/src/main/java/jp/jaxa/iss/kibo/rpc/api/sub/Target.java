 

package jp.jaxa.iss.kibo.rpc.api.sub;

public class Target
{
    private int id;
    private boolean active;
    
    public Target(final int id, final boolean active) {
        this.active = false;
        this.setId(id);
        this.setActive(active);
    }
    
    public boolean getActive() {
        return this.active;
    }
    
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("id: " + this.id);
        sb.append("active: " + this.active);
        sb.append(" }");
        return sb.toString();
    }
}
