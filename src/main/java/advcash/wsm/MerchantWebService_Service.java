
package advcash.wsm;

public interface MerchantWebService_Service extends javax.xml.rpc.Service {
    String getMerchantWebServicePortAddress();

    MerchantWebService_PortType getMerchantWebServicePort() throws javax.xml.rpc.ServiceException;

    MerchantWebService_PortType getMerchantWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
