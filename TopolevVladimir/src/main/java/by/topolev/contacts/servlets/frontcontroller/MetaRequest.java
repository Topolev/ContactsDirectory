package by.topolev.contacts.servlets.frontcontroller;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class MetaRequest {
    private final String nameURI;
    private final String methodType;

    public MetaRequest(String nameURI, String methodType){
        this.nameURI = nameURI;
        this.methodType = methodType;
    }
    public String getNameURI() {
        return nameURI;
    }

    public String getMethodType() {
        return methodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetaRequest)) return false;

        MetaRequest that = (MetaRequest) o;

        if (!nameURI.equals(that.nameURI)) return false;
        return methodType.equals(that.methodType);

    }

    @Override
    public int hashCode() {
        int result = nameURI.hashCode();
        result = 31 * result + methodType.hashCode();
        return result;
    }
}
