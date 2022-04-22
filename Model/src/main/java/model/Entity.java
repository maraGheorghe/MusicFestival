package model;

import java.io.Serializable;
import java.util.Objects;

public class Entity implements Serializable {

    public Entity() {}

    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(ID, entity.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
