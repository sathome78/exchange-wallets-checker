/**
 * BankCardTransferRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package advcash.wsm;

public class BankCardTransferRequest  extends MoneyRequest  implements java.io.Serializable {
    private String cardHolder;

    private String cardHolderAddress;

    private String cardHolderCountryCode;

    private String cardHolderIp;

    private String cardNumber;

    private String expiryMonth;

    private String expiryYear;

    public BankCardTransferRequest() {
    }

    public BankCardTransferRequest(
           java.math.BigDecimal amount,
           Currency currency,
           String note,
           boolean savePaymentTemplate,
           String cardHolder,
           String cardHolderAddress,
           String cardHolderCountryCode,
           String cardHolderIp,
           String cardNumber,
           String expiryMonth,
           String expiryYear) {
        super(
            amount,
            currency,
            note,
            savePaymentTemplate);
        this.cardHolder = cardHolder;
        this.cardHolderAddress = cardHolderAddress;
        this.cardHolderCountryCode = cardHolderCountryCode;
        this.cardHolderIp = cardHolderIp;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }


    /**
     * Gets the cardHolder value for this BankCardTransferRequest.
     * 
     * @return cardHolder
     */
    public String getCardHolder() {
        return cardHolder;
    }


    /**
     * Sets the cardHolder value for this BankCardTransferRequest.
     * 
     * @param cardHolder
     */
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }


    /**
     * Gets the cardHolderAddress value for this BankCardTransferRequest.
     * 
     * @return cardHolderAddress
     */
    public String getCardHolderAddress() {
        return cardHolderAddress;
    }


    /**
     * Sets the cardHolderAddress value for this BankCardTransferRequest.
     * 
     * @param cardHolderAddress
     */
    public void setCardHolderAddress(String cardHolderAddress) {
        this.cardHolderAddress = cardHolderAddress;
    }


    /**
     * Gets the cardHolderCountryCode value for this BankCardTransferRequest.
     * 
     * @return cardHolderCountryCode
     */
    public String getCardHolderCountryCode() {
        return cardHolderCountryCode;
    }


    /**
     * Sets the cardHolderCountryCode value for this BankCardTransferRequest.
     * 
     * @param cardHolderCountryCode
     */
    public void setCardHolderCountryCode(String cardHolderCountryCode) {
        this.cardHolderCountryCode = cardHolderCountryCode;
    }


    /**
     * Gets the cardHolderIp value for this BankCardTransferRequest.
     * 
     * @return cardHolderIp
     */
    public String getCardHolderIp() {
        return cardHolderIp;
    }


    /**
     * Sets the cardHolderIp value for this BankCardTransferRequest.
     * 
     * @param cardHolderIp
     */
    public void setCardHolderIp(String cardHolderIp) {
        this.cardHolderIp = cardHolderIp;
    }


    /**
     * Gets the cardNumber value for this BankCardTransferRequest.
     * 
     * @return cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }


    /**
     * Sets the cardNumber value for this BankCardTransferRequest.
     * 
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    /**
     * Gets the expiryMonth value for this BankCardTransferRequest.
     * 
     * @return expiryMonth
     */
    public String getExpiryMonth() {
        return expiryMonth;
    }


    /**
     * Sets the expiryMonth value for this BankCardTransferRequest.
     * 
     * @param expiryMonth
     */
    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }


    /**
     * Gets the expiryYear value for this BankCardTransferRequest.
     * 
     * @return expiryYear
     */
    public String getExpiryYear() {
        return expiryYear;
    }


    /**
     * Sets the expiryYear value for this BankCardTransferRequest.
     * 
     * @param expiryYear
     */
    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BankCardTransferRequest)) return false;
        BankCardTransferRequest other = (BankCardTransferRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cardHolder==null && other.getCardHolder()==null) || 
             (this.cardHolder!=null &&
              this.cardHolder.equals(other.getCardHolder()))) &&
            ((this.cardHolderAddress==null && other.getCardHolderAddress()==null) || 
             (this.cardHolderAddress!=null &&
              this.cardHolderAddress.equals(other.getCardHolderAddress()))) &&
            ((this.cardHolderCountryCode==null && other.getCardHolderCountryCode()==null) || 
             (this.cardHolderCountryCode!=null &&
              this.cardHolderCountryCode.equals(other.getCardHolderCountryCode()))) &&
            ((this.cardHolderIp==null && other.getCardHolderIp()==null) || 
             (this.cardHolderIp!=null &&
              this.cardHolderIp.equals(other.getCardHolderIp()))) &&
            ((this.cardNumber==null && other.getCardNumber()==null) || 
             (this.cardNumber!=null &&
              this.cardNumber.equals(other.getCardNumber()))) &&
            ((this.expiryMonth==null && other.getExpiryMonth()==null) || 
             (this.expiryMonth!=null &&
              this.expiryMonth.equals(other.getExpiryMonth()))) &&
            ((this.expiryYear==null && other.getExpiryYear()==null) || 
             (this.expiryYear!=null &&
              this.expiryYear.equals(other.getExpiryYear())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCardHolder() != null) {
            _hashCode += getCardHolder().hashCode();
        }
        if (getCardHolderAddress() != null) {
            _hashCode += getCardHolderAddress().hashCode();
        }
        if (getCardHolderCountryCode() != null) {
            _hashCode += getCardHolderCountryCode().hashCode();
        }
        if (getCardHolderIp() != null) {
            _hashCode += getCardHolderIp().hashCode();
        }
        if (getCardNumber() != null) {
            _hashCode += getCardNumber().hashCode();
        }
        if (getExpiryMonth() != null) {
            _hashCode += getExpiryMonth().hashCode();
        }
        if (getExpiryYear() != null) {
            _hashCode += getExpiryYear().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankCardTransferRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsm.advcash/", "bankCardTransferRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardHolder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardHolder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardHolderAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardHolderAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardHolderCountryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardHolderCountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardHolderIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardHolderIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expiryMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expiryMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expiryYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expiryYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
