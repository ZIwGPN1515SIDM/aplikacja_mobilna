package com.example.magda.systeminformacyjny.models;

/**
 * Created by piotrek on 04.06.17.
 */

public class VisitedNamespace {

    private String namespace;

    private Long id;


    public VisitedNamespace(String namespace, Long id) {
        this.namespace = namespace;
        this.id = id;
    }

    public VisitedNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof VisitedNamespace))
            return false;
        VisitedNamespace other = (VisitedNamespace) obj;
        return namespace == null ? other.namespace == null : namespace.equals(other.namespace);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + namespace.hashCode();
        return result;
    }
}
