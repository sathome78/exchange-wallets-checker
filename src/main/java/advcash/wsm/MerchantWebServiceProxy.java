package advcash.wsm;

public class MerchantWebServiceProxy implements advcash.wsm.MerchantWebService_PortType {
  private String _endpoint = null;
  private advcash.wsm.MerchantWebService_PortType merchantWebService_PortType = null;
  
  public MerchantWebServiceProxy() {
    _initMerchantWebServiceProxy();
  }
  
  public MerchantWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initMerchantWebServiceProxy();
  }
  
  private void _initMerchantWebServiceProxy() {
    try {
      merchantWebService_PortType = (new advcash.wsm.MerchantWebService_ServiceLocator()).getMerchantWebServicePort();
      if (merchantWebService_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)merchantWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)merchantWebService_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (merchantWebService_PortType != null)
      ((javax.xml.rpc.Stub)merchantWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public advcash.wsm.MerchantWebService_PortType getMerchantWebService_PortType() {
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType;
  }
  
  public void validationSendMoneyToAdvcashCard(AuthDTO arg0, advcash.wsm.AdvcashCardTransferRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.CardIsNotActiveException, advcash.wsm.LimitPerCardPerDayException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, LifetimeLimitException, advcash.wsm.CardDoesNotExistException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToAdvcashCard(arg0, arg1);
  }
  
  public void validationCurrencyExchange(AuthDTO arg0, CurrencyExchangeRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CallRestrictionException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationCurrencyExchange(arg0, arg1);
  }
  
  public advcash.wsm.OutcomingTransactionDTO[] history(AuthDTO arg0, advcash.wsm.MerchantAPITransactionFilter arg1) throws java.rmi.RemoteException, AccessDeniedException, LimitsException, advcash.wsm.CallRestrictionException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, WrongParamsException, DatabaseException, InternalException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.history(arg0, arg1);
  }
  
  public advcash.wsm.ValidateAccountResultDTO validateAccount(AuthDTO arg0, advcash.wsm.ValidateAccountRequestDTO arg1) throws java.rmi.RemoteException, AccessDeniedException, LimitsException, advcash.wsm.CallRestrictionException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, advcash.wsm.UserDoesNotExistException, WrongParamsException, DatabaseException, InternalException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.validateAccount(arg0, arg1);
  }
  
  public AccountPresentDTO[] validateAccounts(AuthDTO arg0, String[] arg1) throws java.rmi.RemoteException, AccessDeniedException, LimitsException, advcash.wsm.CallRestrictionException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, WrongParamsException, DatabaseException, InternalException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.validateAccounts(arg0, arg1);
  }
  
  public void validateCurrencyExchange(AuthDTO arg0, TransferRequestDTO arg1, boolean arg2) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CallRestrictionException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateCurrencyExchange(arg0, arg1, arg2);
  }
  
  public advcash.wsm.SendMoneyToExmoResultHolder sendMoneyToExmo(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToExmo(arg0, arg1);
  }
  
  public void register(AuthDTO arg0, RegistrationRequest arg1) throws java.rmi.RemoteException, RegistrationException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, WrongParamsException, DatabaseException, advcash.wsm.EmailAlreadyExistException, InternalException, BadParametersException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.register(arg0, arg1);
  }
  
  public void validationSendMoneyToWex(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToWex(arg0, arg1);
  }
  
  public advcash.wsm.OutcomingTransactionDTO findTransaction(AuthDTO arg0, String arg1) throws java.rmi.RemoteException, AccessDeniedException, LimitsException, advcash.wsm.CallRestrictionException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, WrongParamsException, DatabaseException, InternalException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.findTransaction(arg0, arg1);
  }
  
  public String makeCurrencyExchange(AuthDTO arg0, TransferRequestDTO arg1, boolean arg2) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CallRestrictionException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.makeCurrencyExchange(arg0, arg1, arg2);
  }
  
  public String sendMoneyToEmail(AuthDTO arg0, SendMoneyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, WrongEmailException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.EmailAlreadyExistException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToEmail(arg0, arg1);
  }
  
  public void validationSendMoneyToBankCard(AuthDTO arg0, BankCardTransferRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, advcash.wsm.NotSupportedBankBinException, AccessDeniedException, advcash.wsm.CardNumberIsNotValidException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, advcash.wsm.AdditionalDataRequiredException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, advcash.wsm.NotSupportedCountryException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToBankCard(arg0, arg1);
  }
  
  public String sendMoneyToAdvcashCard(AuthDTO arg0, advcash.wsm.AdvcashCardTransferRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.CardIsNotActiveException, advcash.wsm.LimitPerCardPerDayException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, LifetimeLimitException, advcash.wsm.CardDoesNotExistException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToAdvcashCard(arg0, arg1);
  }
  
  public String transferBankCard(AuthDTO arg0, advcash.wsm.BankCardTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CardNumberIsNotValidException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, advcash.wsm.AdditionalDataRequiredException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.transferBankCard(arg0, arg1);
  }
  
  public String currencyExchange(AuthDTO arg0, CurrencyExchangeRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.currencyExchange(arg0, arg1);
  }
  
  public String sendMoney(AuthDTO arg0, SendMoneyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, advcash.wsm.WalletCurrencyIncorrectException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoney(arg0, arg1);
  }
  
  public void validationSendMoneyToEcurrency(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToEcurrency(arg0, arg1);
  }
  
  public String sendMoneyToEcurrency(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToEcurrency(arg0, arg1);
  }
  
  public String transferAdvcashCard(AuthDTO arg0, advcash.wsm.AdvcashCardTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, advcash.wsm.UserDoesNotExistException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, BadParametersException, advcash.wsm.CardIsNotActiveException, InternalException, advcash.wsm.LimitPerCardPerDayException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, LifetimeLimitException, advcash.wsm.CardDoesNotExistException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.transferAdvcashCard(arg0, arg1);
  }
  
  public void validateBankCardTransfer(AuthDTO arg0, advcash.wsm.BankCardTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CardNumberIsNotValidException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, advcash.wsm.AdditionalDataRequiredException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateBankCardTransfer(arg0, arg1);
  }
  
  public String makeTransfer(AuthDTO arg0, TypeOfTransaction arg1, TransferRequestDTO arg2) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CallRestrictionException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, advcash.wsm.WalletCurrencyIncorrectException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, CodeIsNotValidException, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.makeTransfer(arg0, arg1, arg2);
  }
  
  public String emailTransfer(AuthDTO arg0, EmailTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, WrongEmailException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, LimitPerMonthException, advcash.wsm.LimitPerTransactionException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.EmailAlreadyExistException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.emailTransfer(arg0, arg1);
  }
  
  public void validationSendMoneyToEmail(AuthDTO arg0, SendMoneyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, WrongEmailException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.EmailAlreadyExistException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToEmail(arg0, arg1);
  }
  
  public String withdrawalThroughExternalPaymentSystem(AuthDTO arg0, advcash.wsm.WithdrawalThroughExternalPaymentSystemRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.withdrawalThroughExternalPaymentSystem(arg0, arg1);
  }
  
  public String sendMoneyToBankCard(AuthDTO arg0, BankCardTransferRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, advcash.wsm.NotSupportedBankBinException, AccessDeniedException, advcash.wsm.CardNumberIsNotValidException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, advcash.wsm.AdditionalDataRequiredException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, advcash.wsm.NotSupportedCountryException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToBankCard(arg0, arg1);
  }
  
  public void validationSendMoneyToExmo(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoneyToExmo(arg0, arg1);
  }
  
  public void validateAdvcashCardTransfer(AuthDTO arg0, advcash.wsm.AdvcashCardTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, advcash.wsm.UserDoesNotExistException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, BadParametersException, advcash.wsm.CardIsNotActiveException, InternalException, advcash.wsm.LimitPerCardPerDayException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, LifetimeLimitException, advcash.wsm.CardDoesNotExistException, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateAdvcashCardTransfer(arg0, arg1);
  }
  
  public void validateWithdrawalThroughExternalPaymentSystem(AuthDTO arg0, advcash.wsm.WithdrawalThroughExternalPaymentSystemRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateWithdrawalThroughExternalPaymentSystem(arg0, arg1);
  }
  
  public void validateEmailTransfer(AuthDTO arg0, EmailTransferRequestDTO arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, WrongEmailException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, WrongParamsException, LimitPerMonthException, advcash.wsm.LimitPerTransactionException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.EmailAlreadyExistException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateEmailTransfer(arg0, arg1);
  }
  
  public void validateTransfer(AuthDTO arg0, TypeOfTransaction arg1, TransferRequestDTO arg2) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.CallRestrictionException, advcash.wsm.MerchantDisabledException, UserBlockedException, WrongIpException, advcash.wsm.WalletCurrencyIncorrectException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, LimitsException, NotAuthException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validateTransfer(arg0, arg1, arg2);
  }
  
  public void validationSendMoney(AuthDTO arg0, SendMoneyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, advcash.wsm.WalletCurrencyIncorrectException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, LimitPerDayException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    merchantWebService_PortType.validationSendMoney(arg0, arg1);
  }
  
  public advcash.wsm.CreateBitcoinInvoiceResult createBitcoinInvoice(AuthDTO arg0, advcash.wsm.CreateBitcoinInvoiceRequest arg1) throws java.rmi.RemoteException, advcash.wsm.NotAvailableDepositSystemException, AccessDeniedException, advcash.wsm.MerchantDisabledException, JAXBException, WrongIpException, advcash.wsm.DuplicateOrderIdException, WrongParamsException, BadParametersException, InternalException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, WalletDoesNotExist, DatabaseException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.createBitcoinInvoice(arg0, arg1);
  }
  
  public advcash.wsm.CheckCurrencyExchangeResultHolder checkCurrencyExchange(AuthDTO arg0, advcash.wsm.CheckCurrencyExchangeRequest arg1) throws java.rmi.RemoteException, AccessDeniedException, UserBlockedException, DatabaseException, BadParametersException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.checkCurrencyExchange(arg0, arg1);
  }
  
  public WalletBalanceDTO[] getBalances(AuthDTO arg0) throws java.rmi.RemoteException, AccessDeniedException, LimitsException, advcash.wsm.CallRestrictionException, NotAuthException, advcash.wsm.MerchantDisabledException, WrongIpException, WrongParamsException, DatabaseException, InternalException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.getBalances(arg0);
  }
  
  public advcash.wsm.SendMoneyToWexResultHolder sendMoneyToWex(AuthDTO arg0, advcash.wsm.WithdrawToEcurrencyRequest arg1) throws java.rmi.RemoteException, advcash.wsm.TransactionTemporaryNotAvailableException, advcash.wsm.TransactionIsNotAvailableException, AccessDeniedException, advcash.wsm.MerchantDisabledException, WrongIpException, UserBlockedException, WrongParamsException, advcash.wsm.LimitPerTransactionException, LimitPerMonthException, InternalException, BadParametersException, advcash.wsm.TransactionFailureException, advcash.wsm.NotEnoughMoneyException, NotAuthException, advcash.wsm.NotEnoughMoneyApiException, WalletDoesNotExist, DatabaseException, advcash.wsm.ExchangeCurrencyException, LimitPerDayException, ApiException{
    if (merchantWebService_PortType == null)
      _initMerchantWebServiceProxy();
    return merchantWebService_PortType.sendMoneyToWex(arg0, arg1);
  }
  
  
}