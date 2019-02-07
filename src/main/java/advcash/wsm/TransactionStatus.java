/**
 * TransactionStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package advcash.wsm;

public class TransactionStatus implements java.io.Serializable {
    private String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TransactionStatus(String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final String _PENDING = "PENDING";
    public static final String _PROCESS = "PROCESS";
    public static final String _COMPLETED = "COMPLETED";
    public static final String _CANCELED = "CANCELED";
    public static final String _NOT_IDENTIFIED = "NOT_IDENTIFIED";
    public static final String _ERROR = "ERROR";
    public static final String _NOT_CONFIRMED = "NOT_CONFIRMED";
    public static final String _CONFIRMED = "CONFIRMED";
    public static final TransactionStatus PENDING = new TransactionStatus(_PENDING);
    public static final TransactionStatus PROCESS = new TransactionStatus(_PROCESS);
    public static final TransactionStatus COMPLETED = new TransactionStatus(_COMPLETED);
    public static final TransactionStatus CANCELED = new TransactionStatus(_CANCELED);
    public static final TransactionStatus NOT_IDENTIFIED = new TransactionStatus(_NOT_IDENTIFIED);
    public static final TransactionStatus ERROR = new TransactionStatus(_ERROR);
    public static final TransactionStatus NOT_CONFIRMED = new TransactionStatus(_NOT_CONFIRMED);
    public static final TransactionStatus CONFIRMED = new TransactionStatus(_CONFIRMED);
    public String getValue() { return _value_;}
    public static TransactionStatus fromValue(String value)
          throws IllegalArgumentException {
        TransactionStatus enumeration = (TransactionStatus)
            _table_.get(value);
        if (enumeration==null) throw new IllegalArgumentException();
        return enumeration;
    }
    public static TransactionStatus fromString(String value)
          throws IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public String toString() { return _value_;}
    public Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransactionStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsm.advcash/", "transactionStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
